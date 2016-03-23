package com.mason.shop.cart.vo;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 购物车对象
 * @author Mason
 *
 */
public class Cart implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//购物项集合，使用Map比较合适，删除操作的时候比较方便(通过key)
	//Map的key就是商品的id，value就是购物项
	private Map<Integer, CartItem> map = new LinkedHashMap<Integer, CartItem>();
	
	//Cart对象中有一个叫cartItems属性
	public Collection<CartItem> getCartItems(){
		return map.values();
	}
	//购物总计
	private double total;
	
	public double getTotal() {
		return total;
	}
	
	//当前购物车中的物品是否已经提交至订单
	private boolean isSubmited;
	public boolean isSubmited() {
		return isSubmited;
	}
	public void setSubmited(boolean isSubmited) {
		this.isSubmited = isSubmited;
	}

	//购物车的功能:
	//1. 将购物项添加到购物车
	public void addCartItem(CartItem cartItem){
		//判断购物车中是否存在该购物项:
		/*
		 * *如果存在:
		 * 		*数量增加
		 * 		*总计 = 总计 + 购物项小计
		 * *如果不存在
		 * 		*添加到Map中
		 * 		*总计 = 总计 + 购物项小计 
		 */
		//获得商品id
		Integer pid = cartItem.getProduct().getPid();
		if(map.containsKey(pid)){
			//存在
			CartItem _cartItem = map.get(pid);
			_cartItem.setCount(cartItem.getCount() + _cartItem.getCount());
		}else{
			//不存在
			map.put(pid, cartItem);
		}
		//设置总计的值
		total += cartItem.getSubtotal();
		setSubmited(false);
	}
	
	//2. 从购物车中移除购物项
	public void removeCartItem(Integer pid){
		//将购物项移除，并将其返回
		CartItem cartItem = map.remove(pid);
		//总计 = 总计 - 移除的购物项的小计 
		total -= cartItem.getSubtotal();
		setSubmited(false);
	}
	//3. 清空购物车
	public void clearCart(){
		//将所有的购物项清空
		map.clear();
		//将总计设为0
		total = 0;
		setSubmited(false);
	}
}
