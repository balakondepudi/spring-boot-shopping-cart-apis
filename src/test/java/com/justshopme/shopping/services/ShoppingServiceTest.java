package com.justshopme.shopping.services;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.justshopme.shopping.exception.ShopCartException;
import com.justshopme.shopping.models.Product;
import com.justshopme.shopping.models.PurchaseItem;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/*
 * Run with JUnit4
 */
@RunWith(MockitoJUnitRunner.class)
public class ShoppingServiceTest {

	@InjectMocks
	private ShoppingService shoppingService;
	
	@Before
	public void setUp() throws Exception {
		shoppingService = new ShoppingService();
	}

	@Test
	public void given_user_and_no_items_in_cart_when_viewcart_accessed_then_returns_empty_cart() {
		Assert.assertEquals(Collections.emptyMap(), shoppingService.viewCart("user1"));
	}
	
	@Test
	public void given_user_add_some_items_in_cart_when_viewcart_accessed_then_returns_items() {
		populateMockPurchasesData();
		Assert.assertEquals(EXPECTED_TWO_PURCHASE_DATA_TOSTRING, shoppingService.viewCart("user1").toString());
	}
	
	@Test
	public void given_user_when_adding_items_then_items_should_be_added_into_cart() throws ShopCartException {
		// add a product into cart
		Product addProduct = new Product("100", "Blend Plus", 399);
		shoppingService.addItemToCart("user1", addProduct);
		Map<String, Map<String, PurchaseItem>> actualData = getPurchasesData();
		Assert.assertEquals(EXPECTED_ONE_PURCHASE_DATA_TOSTRING, actualData.toString());

		// add same product again into cart
		addProduct = new Product("100", "Blend Plus", 399);
		shoppingService.addItemToCart("user1", addProduct);
		
		// add another product into cart
		addProduct = new Product("101", "Fruit Plus", 299);
		shoppingService.addItemToCart("user1", addProduct);
		actualData = getPurchasesData();
		Assert.assertEquals(EXPECTED_TWO_PURCHASE_DATA_MAP_TOSTRING, actualData.toString());
		
		try {
		// add invalid product into cart
		shoppingService.addItemToCart("user1", null);
		actualData = getPurchasesData();
		} catch(ShopCartException sce) {
			Assert.assertEquals("Invalid Product, cannot add into cart.", sce.getMessage());
		}
	
	}
	
	@Test
	public void given_user_when_remove_items_then_items_should_be_removed_from_cart() throws ShopCartException {
		// mock purchases data
		populateMockPurchasesData();
		
		// remove a product from cart
		Product removeProduct = new Product("101", "Fruit Plus", 299);
		shoppingService.removeItemFromCart("user1", removeProduct);
		Map<String, Map<String, PurchaseItem>> actualData = getPurchasesData();
		Assert.assertEquals(EXPECTED_REMOVE_ONE_PRODUCT_MAP_TOSTRING, actualData.toString());
		
		// remove product which is twice in cart
		removeProduct = new Product("101", "Fruit Plus", 299);
		shoppingService.removeItemFromCart("user1", removeProduct);
		actualData = getPurchasesData();
		Assert.assertEquals(EXPECTED_REMOVE_TWO_PRODUCTS_MAP_TOSTRING, actualData.toString());
		
		// remove invalid product from cart
		try {
			shoppingService.removeItemFromCart("user1", null);
			actualData = getPurchasesData();
		} catch(ShopCartException sce) {
			Assert.assertEquals("Invalid Product, cannot remove from cart.", sce.getMessage());
		}
	}
	
	@Test
	public void given_user_when_does_checkout_then_should_succesfully_checkout() throws ShopCartException {
		// mock purchases data
		populateMockPurchasesData();

		// checkout cart
		Assert.assertEquals(EXPECTED_CART_CHECKOUT_STRING, shoppingService.checkout("user1"));

		// checkout removes the user1 items
		Map<String, Map<String, PurchaseItem>> actualData = getPurchasesData();
		Assert.assertEquals("{}", actualData.toString());

		
		// checkout for invalid user
		try {
			Assert.assertEquals(EXPECTED_CART_CHECKOUT_EMPTY_STRING, shoppingService.checkout("user1"));
		} catch(ShopCartException sce) {
			Assert.assertEquals("No Items to checkout", sce.getMessage());
		}
	}
	
	@Test
	public void given_user_when_requests_checkout_price_then_should_see_right_price() {
		// mock purchases data
		populateMockPurchasesData();
		Map<String, Map<String, PurchaseItem>> actualData = getPurchasesData();

		// checkout cart
		Assert.assertEquals(Double.valueOf(1097.0), shoppingService.checkoutPrice("user1", actualData.get("user1")));
		actualData.clear();
		 
		// checkout cart
		Assert.assertEquals(Double.valueOf(0.0), shoppingService.checkoutPrice("user1", actualData.get("user1")));
		
	}

	@SuppressWarnings("unchecked")
	private Map<String, Map<String, PurchaseItem>> getPurchasesData() {
		Field field = null;
		Map<String, Map<String, PurchaseItem>> object = null;
		try {
			field = shoppingService.getClass().getDeclaredField("purchases");
			field.setAccessible(true);
			object =(Map<String, Map<String, PurchaseItem>>) field.get(shoppingService);
		} catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException ignored) {}
		return object;
	}

	private void populateMockPurchasesData() {
		Field field = null;
		try {
			field = shoppingService.getClass().getDeclaredField("purchases");
			field.setAccessible(true);
			
			// 100 product added 2 times into cart
			Product product = new Product("100", "Blend Plus", 399);
			PurchaseItem item = new PurchaseItem(2, product);
			USER_DATA_MAP.put("100", item);
			
			// 101 product added into cart
			product = new Product("101", "Fruit Plus", 299);
			item = new PurchaseItem(1, product);
			USER_DATA_MAP.put("101", item);
			
			PURCHASES_DATA_MAP.put("user1", USER_DATA_MAP);
			
			field.set(shoppingService, PURCHASES_DATA_MAP);
		} catch (NoSuchFieldException | IllegalAccessException ignored) {}
		
	}
	
	private static Map<String, Map<String, PurchaseItem>> PURCHASES_DATA_MAP = new ConcurrentHashMap<String, Map<String, PurchaseItem>>();
	private static Map<String, PurchaseItem> USER_DATA_MAP = new HashMap<String, PurchaseItem>();
	private static final String EXPECTED_ONE_PURCHASE_DATA_TOSTRING = "{user1={100=Purchase [count=1, product=Product [id=100, name=Blend Plus, price=399.0]]}}";
	private static final String EXPECTED_TWO_PURCHASE_DATA_TOSTRING = 
			"{100=Purchase [count=2, product=Product [id=100, name=Blend Plus, price=399.0]], 101=Purchase [count=1, product=Product [id=101, name=Fruit Plus, price=299.0]]}";
	
	private static final String EXPECTED_TWO_PURCHASE_DATA_MAP_TOSTRING = 
			"{user1={100=Purchase [count=2, product=Product [id=100, name=Blend Plus, price=399.0]], 101=Purchase [count=1, product=Product [id=101, name=Fruit Plus, price=299.0]]}}";
	
	private static final String EXPECTED_REMOVE_ONE_PRODUCT_MAP_TOSTRING = "{user1={100=Purchase [count=2, product=Product [id=100, name=Blend Plus, price=399.0]]}}";
	private static final String EXPECTED_REMOVE_TWO_PRODUCTS_MAP_TOSTRING = "{user1={100=Purchase [count=2, product=Product [id=100, name=Blend Plus, price=399.0]]}}";
	private static final String EXPECTED_CART_CHECKOUT_STRING = "Cart for user1 checked out sucessfully. Thanks for Shopping.";
	private static final String EXPECTED_CART_CHECKOUT_EMPTY_STRING = "No Items to checkout";


}
