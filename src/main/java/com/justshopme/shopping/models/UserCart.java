package com.justshopme.shopping.models;

import java.util.Map;

public class UserCart {
	private double totalPrice;
	private Map<String, PurchaseItem> items;
	public UserCart(double totalPrice, Map<String, PurchaseItem> items) {
		super();
		this.totalPrice = totalPrice;
		this.items = items;
	}
	public double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public Map<String, PurchaseItem> getItems() {
		return items;
	}
	public void setItems(Map<String, PurchaseItem> items) {
		this.items = items;
	}
	@Override
	public String toString() {
		return "UserCart [totalPrice=" + totalPrice + ", items=" + items + "]";
	}
	
	
}
