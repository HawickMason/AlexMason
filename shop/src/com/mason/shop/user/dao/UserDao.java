package com.mason.shop.user.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.mason.shop.user.vo.User;

/**
 * 用户模块持久层的代码
 * @author Mason
 *
 */
public class UserDao{
	
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	//按名称查询是否用该用户:
	@SuppressWarnings("unchecked")
	public User findByUsername(String username){
		
		Session s = sessionFactory.openSession();
		Transaction tx = s.beginTransaction();
		String hql = "from User where username = ?";
		Query query = s.createQuery(hql).setParameter(0, username);
		List<User> userList = query.list();
		
		tx.commit();
		s.close();
		
		if(userList != null && userList.size() > 0){
			return userList.get(0);
		} else {
			return null;
		}
	}

	public void save(User user) {
		Session s = sessionFactory.openSession();
		Transaction tx = s.beginTransaction();
		s.save(user);
		//System.out.println(user.getUsername());
		
		tx.commit();
		s.close();
	}

	/**
	 * 根据激活码来查询用户
	 * @param code
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public User findByCode(String code) {
		Session s = sessionFactory.openSession();
		Transaction tx = s.beginTransaction();
		String hql = "from User where code = ?";
		Query query = s.createQuery(hql).setParameter(0, code);
		List<User> userList = query.list();
		
		tx.commit();
		s.close();
		
		if(userList != null && userList.size() > 0){
			return userList.get(0);
		}else{
			return null;
		}
	}
	
	/**
	 * 修改用户的状态 
	 * @param existUser
	 */
	public void update(User existUser) {
		Session s = sessionFactory.openSession();
		Transaction tx = s.beginTransaction();
		s.update(existUser);
		
		tx.commit();
		s.close();
	}

	/**
	 * 用户登录检测查询
	 * @param user
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public User login(User user) {
		Session s = sessionFactory.openSession();
		Transaction tx = s.beginTransaction();
		String hql = "from User where username = ? and password = ? and state = ?";
		Query query = s.createQuery(hql).setParameter(0, user.getUsername()).setParameter(1, user.getPassword()).setParameter(2, 1);
		List<User> userList = query.list();

		tx.commit();
		s.close();
		
		if(userList != null && userList.size() > 0){
			return userList.get(0);
		}else{
			return null;
		}
	}
	
	

}
