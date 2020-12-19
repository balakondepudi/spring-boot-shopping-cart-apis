package com.justshopme.shopping.services;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import com.justshopme.shopping.exception.ShopCartException;
import com.justshopme.shopping.models.Product;
import com.justshopme.shopping.models.PurchaseItem;

/**
 * 
 * The backend ShoppingService that performs the business logic involved in shopping cart management.
 * 
 * @author Bala Kondepudi
 *
 */
@Service
public class ShoppingService {
	
	// map holds <userId, <100,purchase>>
	private Map<String, Map<String, PurchaseItem>> purchases =  
			new ConcurrentHashMap<String, Map<String, PurchaseItem>>();
	
	/**
	 * Service method returns cart for a given user
	 * @param userId
	 * @return
	 */
	public Map<String, PurchaseItem> viewCart(String userId) {
		Map<String, PurchaseItem> userItems = purchases.get(userId);
		if(userItems == null) {
			userItems = new HashMap<String, PurchaseItem>(); 
		}
		return userItems;
	}

	/**
	 * Service method that adds a product into a cart for an userId.
	 * @param userId
	 * @param addProduct
	 * @return
	 * @throws ShopCartException
	 */
	public Map<String, PurchaseItem> addItemToCart(String userId, Product addProduct) throws ShopCartException {
		Map<String, PurchaseItem> userItems = viewCart(userId);
		if(addProduct != null) {
			PurchaseItem productInCart = userItems.get(addProduct.getId());
			if(productInCart != null) {
				productInCart.setCount(productInCart.getCount() + 1);
				userItems.put(addProduct.getId(), productInCart);
			} else {
				userItems.put(addProduct.getId(), new PurchaseItem(1, addProduct));
			}
			purchases.put(userId,userItems);
			System.out.printf("Added %s item into cart for userId %s\n", addProduct.getId(), userId);
		} else {
			throw new ShopCartException("Invalid Product, cannot add into cart.");
		}
		return userItems;
	}
	
	/**
	 * Service method that removes a product from cart for an userId.
	 * @param userId
	 * @param addProduct
	 * @return
	 * @throws ShopCartException
	 */
	public Map<String, PurchaseItem> removeItemFromCart(String userId, Product removeProduct) throws ShopCartException {
		Map<String, PurchaseItem> userItems = viewCart(userId);
		if(removeProduct != null) {
			PurchaseItem productInCart = userItems.get(removeProduct.getId());
			if(productInCart != null && productInCart.getCount() > 1) {
				productInCart.setCount(productInCart.getCount() - 1);
				userItems.put(removeProduct.getId(), productInCart);
			} else {
				userItems.remove(removeProduct.getId());
			}
			purchases.put(userId,userItems);
			System.out.printf("Removed %s item from cart for userId %s\n", removeProduct.getId(), userId);
		} else {
			throw new ShopCartException("Invalid Product, cannot remove from cart.");
		}
		return userItems;
	}
	
	/**
	 * Service method that performs checkout for a given user, by also removing the items from the cart.
	 * @param userId
	 * @return
	 * @throws ShopCartException
	 */
	public String checkout(String userId) throws ShopCartException {
		Map<String, PurchaseItem> userItems = viewCart(userId);
		if(userItems != null && userItems.size() > 0) {
			purchases.remove(userId);
			System.out.printf("Performing cart checkout for user %s\n", userId);
			return String.format("Cart for %s checked out sucessfully. Thanks for Shopping.", userId);
		} else {
			throw new ShopCartException("No Items to checkout");
		}
	}
	
	/**
	 * Calculate total price of the cart for a given user and items list.
	 * @param userId
	 * @param userItems
	 * @return
	 */
	public Double checkoutPrice(String userId, Map<String, PurchaseItem> userItems) {
		double total = 0;
		if(userItems != null) {
			for(PurchaseItem item : userItems.values()) {
				total = total + item.getCount() * item.getProduct().getPrice();
			}
			System.out.printf("Total cart price %f for user %s\n", total, userId);
		}
		return total;
	}
	
}
