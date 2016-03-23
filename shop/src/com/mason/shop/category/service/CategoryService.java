package com.mason.shop.category.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.mason.shop.category.dao.CategoryDao;
import com.mason.shop.category.vo.Category;

/**
 * 一级分类的业务层对象
 * @author Mason
 *
 */
@Transactional
public class CategoryService {
	//注入CategoryDao
	private CategoryDao categoryDao;
	
	public void setCategoryDao(CategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}

	public List<Category> findAll() {
		return categoryDao.findAll();
	}
}
