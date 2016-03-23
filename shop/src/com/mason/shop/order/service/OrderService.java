package com.mason.shop.order.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.mason.shop.order.dao.OrderDao;
import com.mason.shop.order.vo.Order;
import com.mason.shop.util.PageBean;

/**
 * 订单模块业务层代码
 * @author Mason
 *
 */
@Transactional
public class OrderService {
	//注入OrderDao
	private OrderDao orderDao;
	public void setOrderDao(OrderDao orderDao) {
		this.orderDao = orderDao;
	}
	
	//保存订单信息业务层代码
	public void save(Order order) {
		orderDao.save(order);
	}

	//查询我的订单的业务层代码
	public PageBean<Order> findByPageUid(Integer uid, Integer page) {
		PageBean<Order> pageBean = new PageBean<>();
		
		//设置当前页数
		pageBean.setPage(page);
		
		//设置每页显示的记录数
		Integer limit = 8;
		pageBean.setLimit(limit);
		
		//设置总记录数
		Integer totalCount = null;
		totalCount = orderDao.findCountByUid(uid);
		pageBean.setTotalCount(totalCount);
		
		//设置总页数
		Integer totalPage= null;
		if(totalCount % limit == 0){
			totalPage = totalCount / limit;
		}else{
			totalPage = totalCount / limit + 1;
		}
		pageBean.setTotalPage(totalPage);
		//设置每页显示的数据集合
		Integer begin = (page - 1) * limit;
		List<Order> list = orderDao.findByPageUid(uid,begin,limit);
		pageBean.setList(list);
		return pageBean;
	}

	//业务层:根据订单id查询订单
	public Order findByOid(Integer oid) {
		Order order = orderDao.findByOid(oid);
		return order;
	}

	//业务层:修改订单的内容
	public void update(Order currOrder) {
		orderDao.update(currOrder);
	}
}
