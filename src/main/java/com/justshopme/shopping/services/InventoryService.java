package com.justshopme.shopping.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.justshopme.shopping.models.Product;

/**
 * Inventory Service that handles product APIs
 * @author Bala Kondepudi
 *
 */
@Service
public class InventoryService {
	
	/**
	 * Mock product data available for purchase
	 * @return
	 */
	public List<Product> availableProducts() {
		List<Product> products = new ArrayList<Product>();
		products.add(new Product("XYZ1", "Apple iPhone 11", 999));
		products.add(new Product("XYZ2", "Apple iPhone X", 899));
		products.add(new Product("XYZ3", "Apple iWatch", 499));
		products.add(new Product("XYZ4", "Apple iPhone 11 Pro", 1199));
		products.add(new Product("XYZ5", "Apple Macbook Pro", 1999));		
		return products;
	}

	/**
	 * API that returns Product for a given productId
	 * @param id
	 * @return
	 */
	public Product getProductById(String id) {
		for(Product product : availableProducts()) {
			if(product.getId().equals(id)) return product;
		}
		return null;
	}

}
