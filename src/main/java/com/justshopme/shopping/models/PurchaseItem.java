package com.justshopme.shopping.models;

public class PurchaseItem {
	
	private int count;
	private Product product;
	
	public PurchaseItem(int count, Product product) {
		super();
		this.count = count;
		this.product = product;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	@Override
	public String toString() {
		return "Purchase [count=" + count + ", product=" + product + "]";
	}
	

}
