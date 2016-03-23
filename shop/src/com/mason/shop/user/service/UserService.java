package com.mason.shop.user.service;

import org.springframework.transaction.annotation.Transactional;

import com.mason.shop.user.dao.UserDao;
import com.mason.shop.user.vo.User;
import com.mason.shop.util.MailUtils;
import com.mason.shop.util.UUIDUtils;

/**
 * 用户模块业务层的代码
 * @author Mason
 *
 */
@Transactional
public class UserService {
	//注入UserDao
	private UserDao userDao;
	
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	
	//按用户名查询用户的方法:
	public User findByUsername(String username){
		return userDao.findByUsername(username);
	}

	//业务层完成用户注册代码:
	public void save(User user) {
		//将注册用户信息保存的业务
		user.setState(0);//0代表用户未激活，1代表用户已激活
		//用户激活码
		String code = UUIDUtils.getUUID() + UUIDUtils.getUUID();
		user.setCode(code);
		userDao.save(user);
		//发送激活邮件
		MailUtils.sendMail(user.getEmail(), code);
	}
	
	//业务层完成激活码查找的代码
	public User findByCode(String code) {		
		return userDao.findByCode(code);
	}

	//业务层完成用户状态更新的代码
	public void update(User existUser) {
		userDao.update(existUser);
	}

	//用户登录的方法
	public User login(User user) {
		return userDao.login(user);
	}
}

