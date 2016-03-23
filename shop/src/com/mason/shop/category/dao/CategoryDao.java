package com.mason.shop.category.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.mason.shop.category.vo.Category;

/**
 * 一级分类的持久化对象
 * @author Mason
 *
 */
public class CategoryDao {
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	//DAO层的查询所有一级分类的方法
	@SuppressWarnings("unchecked")
	public List<Category> findAll() {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		String hql = "from Category";
		Query query = session.createQuery(hql);
		List<Category> clist = query.list();
		
		tx.commit();
		session.close();
		
		if(clist != null){
			return clist;
		}
		
		return null;
	}
}
