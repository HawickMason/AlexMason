package com.mason.shop.product.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.mason.shop.product.dao.ProductDao;
import com.mason.shop.product.vo.Product;
import com.mason.shop.util.PageBean;

/**
 * 商品业务层代码
 * @author Mason
 *
 */
@Transactional
public class ProductService {
	//注入ProductDao
	private ProductDao productDao; 
	public void setProductDao(ProductDao productDao) {
		this.productDao = productDao;
	}
	
	//首页上热门商品的查询
	public List<Product> findHot() {
		return productDao.findHot();
	}

	//首页上最新商品的查询
	public List<Product> findNew() {
		return productDao.findNew();
	}

	//首页点击商品的查询
	public Product findByPid(Integer pid) {
		return productDao.findByPid(pid);
	}

	//根据一级分类的cid带有分页的查询
	public PageBean<Product> findByPageCid(Integer cid, int page) {
		PageBean<Product> pageBean = new PageBean<Product>();
		//设置当前的页数
		pageBean.setPage(page);
		//设置每页显示的记录数
		int limit = 12;
		pageBean.setLimit(limit);
		//设置总记录数
		int totalCount = 0;
		totalCount = productDao.findCountCid(cid);
		pageBean.setTotalCount(totalCount);
		//设置总页数
		int totalPage = 0;
		totalPage = (int) Math.ceil( totalCount / limit );		
		pageBean.setTotalPage(totalPage);
		//每页显示的集合
		//从哪开始:
		int begin = ( page - 1) * limit;
		List<Product> list = productDao.findByPageCid(cid,begin,limit);
		pageBean.setList(list);
		return pageBean;
	}

	//根据二级分类的csid带有分页的查询商品
	public PageBean<Product> findByPageCsid(Integer csid, int page) {
		PageBean<Product> pageBean = new PageBean<Product>();
		//设置当前的页数
		pageBean.setPage(page);
		//设置每页显示的记录数
		int limit = 12;
		pageBean.setLimit(limit);
		//设置总记录数
		int totalCount = 0;
		totalCount = productDao.findCountCsid(csid);
		pageBean.setTotalCount(totalCount);
		//设置总页数
		int totalPage = 0;
		totalPage = (int) Math.ceil( totalCount / limit );		
		pageBean.setTotalPage(totalPage);
		//每页显示的集合
		//从哪开始:
		int begin = ( page - 1) * limit;
		List<Product> list = productDao.findByPageCsid(csid,begin,limit);
		pageBean.setList(list);
		return pageBean;
	}

}
