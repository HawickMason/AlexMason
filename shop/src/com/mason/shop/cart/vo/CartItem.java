package com.mason.shop.cart.vo;

import com.mason.shop.product.vo.Product;

/**
 * 购物项对象
 * @author Mason
 *
 */
public class CartItem {
	private Product product; //购物项中商品信息
	private int count;	     //购买某种商品的数量
	@SuppressWarnings("unused")
	private double subtotal; //购买某种商品的小计
	
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public double getSubtotal() {
		return product.getShop_price() * count;
	}
//	public void setSubtotal(double subtotal) {
//		this.subtotal = subtotal;
//	}	
}
