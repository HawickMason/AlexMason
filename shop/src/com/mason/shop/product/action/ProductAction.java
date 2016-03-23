package com.mason.shop.product.action;

import com.mason.shop.category.service.CategoryService;
import com.mason.shop.product.service.ProductService;
import com.mason.shop.product.vo.Product;
import com.mason.shop.util.PageBean;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
/**
 * 商品的Action对象
 * @author Mason
 *
 */
public class ProductAction extends ActionSupport implements ModelDriven<Product>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Product product = new Product();	
	@Override
	public Product getModel() {
		return product;
	}
	
	//接收当前的页数
	private int page;
	public void setPage(int page) {
		this.page = page;
	}
	
	//接收分类的cid
	private Integer cid;
	public void setCid(Integer cid) {
		this.cid = cid;
	}
	public Integer getCid() {
		return cid;
	}
	
	//接收二级分类的csid
	private Integer csid;
	public Integer getCsid() {
		return csid;
	}
	public void setCsid(Integer csid) {
		this.csid = csid;
	}

	//注入ProductService
	private ProductService productService;
	public void setProductService(ProductService productService) {
		this.productService = productService;
	}
	
	//注入一级分类的Service
	@SuppressWarnings("unused")
	private CategoryService categoryService;	
	public void setCategoryService(CategoryService categoryService) {
		this.categoryService = categoryService;
	}
	
	//根据商品的pid进行查询:执行方法
	public String findByPid(){
		product = productService.findByPid(product.getPid());
		return "findByPid";
	}
	
	//根据分类的cid来查询商品
	public String findByCid(){
		//session中已经有一级分类的查询结果
		//List<Category> clist = categoryService.findAll();
		PageBean<Product> pageBean = productService.findByPageCid(cid,page); //根据一级分类查询商品,带分页查询
		//将PageBean存入到值栈中
		ActionContext.getContext().getValueStack().set("pageBean", pageBean);
		return "findByCid";
	}
	
	//根据二级分类的csid来查询商品
	public String findByCsid(){
		PageBean<Product> pageBean = productService.findByPageCsid(csid,page);
		//将PageBean存入到值栈中
		ActionContext.getContext().getValueStack().set("pageBean", pageBean);
		return "findByCsid";
	}
}
