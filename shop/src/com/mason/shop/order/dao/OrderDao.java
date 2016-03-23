package com.mason.shop.order.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.mason.shop.order.vo.Order;
import com.mason.shop.util.PageHibernateCallback;

/**
 * 订单模块持久层代码
 * @author Mason
 *
 */
public class OrderDao {
	// 注入SessionFactory
	private SessionFactory sessionFactory;
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	//订单保存持久层代码
	public void save(Order order) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		
		session.save(order);
		
		tx.commit();
		session.close();
	}

	//DAO层我的订单的个数统计
	@SuppressWarnings("unchecked")
	public Integer findCountByUid(Integer uid) {
		String hql = "select count(*) from Order o where o.user.uid = ?";
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		
		Query query = session.createQuery(hql).setParameter(0, uid);
		List<Long> list = query.list();
		
		tx.commit();
		session.close();	
		
		if(list != null && list.size() > 0){
			return list.get(0).intValue();
		}
		return null;
	}

	//DAO层我的订单的查询
	public List<Order> findByPageUid(Integer uid, Integer begin, Integer limit) {
		String hql = "from Order o where o.user.uid = ? order by ordertime desc";
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		
		try {
			List<Order> olist = new PageHibernateCallback<Order>(hql, new Object[]{uid}, begin, limit).doInHibernate(session);
			
			tx.commit();
			session.close();
			
			if(olist != null && olist.size() > 0){
				return olist;
			}
			
			return null;
			
		} catch (HibernateException e) {
			e.printStackTrace();
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}		
	}

	public Order findByOid(Integer oid) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		
		Order order = (Order) session.get(Order.class, oid);
		
		tx.commit();
		session.close();
		
		return order;
	}

	//DAO层修改订单的操作
	public void update(Order currOrder) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		
		session.update(currOrder);
		
		tx.commit();
		session.close();
	}
}
