package com.mason.shop.index.action;

import java.util.List;

import com.mason.shop.category.service.CategoryService;
import com.mason.shop.category.vo.Category;
import com.mason.shop.product.service.ProductService;
import com.mason.shop.product.vo.Product;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 首页访问的Action
 * @author Mason
 *
 */
public class IndexAction extends ActionSupport{
	/**
	 * SerialID
	 */
	private static final long serialVersionUID = 1L;
	
	//注入一级分类的Service
	private CategoryService categoryService;
	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	//注入商品的Service
	private ProductService productService;
	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	/**
	 * 执行访问首页的方法
	 */
	@Override
	public String execute() throws Exception {
		//查询所有一级分类的集合
		List<Category> clist = categoryService.findAll();
		//将所有一级分类保存到session域中
		ActionContext.getContext().getSession().put("clist", clist);
		
		//查询热门商品:
		List<Product> hlist = productService.findHot();
		//保存到值栈中:
		ActionContext.getContext().getValueStack().set("hlist", hlist);
		//查询最新的商品
		List<Product> nlist = productService.findNew();
		//保存到值栈中:
		ActionContext.getContext().getValueStack().set("nlist", nlist);
		return "index";
	}
}
