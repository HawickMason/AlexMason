package com.mason.shop.cart.action;

import org.apache.struts2.ServletActionContext;

import com.mason.shop.cart.vo.Cart;
import com.mason.shop.cart.vo.CartItem;
import com.mason.shop.product.service.ProductService;
import com.mason.shop.product.vo.Product;
import com.opensymphony.xwork2.ActionSupport;
/**
 * 购物车模块的Action
 * @author Mason
 *
 */
public class CartAction extends ActionSupport{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	//接收pid
	private Integer pid;
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	//接收count
	private Integer count;
	public void setCount(Integer count) {
		this.count = count;
	}
	
	//注入商品的Service
	private ProductService productService;
	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	//点击购物车图标时完成跳转
	public String showCart(){
		return "showCart";
	}
	
	//将购物项添加到购物车:执行的方法
	public String addCartItem(){
		//封装一个CartItem对象
		CartItem cartItem = new CartItem();
		//设置数量
		cartItem.setCount(count);
		//根据商品的id查询商品
		Product product = productService.findByPid(pid);
		//设置商品
		cartItem.setProduct(product);
		//将购物项添加到购物车
		//购物车应该存在session中
		Cart cart = getCart();
		cart.addCartItem(cartItem);
		return "addCartItem";
	}

	//获得购物车的方法:从session中获得
	private Cart getCart() {
		Cart cart = (Cart) ServletActionContext.getRequest().getSession().getAttribute("cart");
		if(cart == null){
			cart = new Cart();
			ServletActionContext.getRequest().getSession().setAttribute("cart", cart);
		}
		return cart;
	}
	
	//将购物项从购物车中删除
	public String removeCartItem(){
		Cart cart = getCart();
		cart.removeCartItem(pid);
		return "showCart";
	}
	
	//清空购物车
	public String clearCart(){
		Cart cart = getCart();
		cart.clearCart();
		return "showCart";
	}
}
