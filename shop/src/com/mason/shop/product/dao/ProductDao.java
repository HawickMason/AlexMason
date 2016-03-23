package com.mason.shop.product.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.mason.shop.product.vo.Product;
import com.mason.shop.util.PageHibernateCallback;

/**
 * 商品持久层代码
 * 
 * @author Mason
 *
 */
public class ProductDao {
	// 注入SessionFactory
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	// 首页商品查询
	@SuppressWarnings("unchecked")
	public List<Product> findHot() {
		// 使用离线条件查询(或者使用execute方法，原生session相关api)
		DetachedCriteria criteria = DetachedCriteria.forClass(Product.class);
		// 查询热门的商品,条件就是 is_host = 1
		criteria.add(Restrictions.eq("is_hot", 1));
		// 倒序排序输出:
		criteria.addOrder(Order.desc("pdate"));
		// 执行查询
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		List<Product> plist = criteria.getExecutableCriteria(session).list()
				.subList(0, 10);

		tx.commit();
		session.close();

		if (plist != null) {
			return plist;
		}

		return null;
	}

	// 首页上最新商品的查询
	@SuppressWarnings("unchecked")
	public List<Product> findNew() {
		// 使用离线条件查询
		DetachedCriteria criteria = DetachedCriteria.forClass(Product.class);
		// 按日期进行倒叙排序
		criteria.addOrder(Order.desc("pdate"));
		// 执行查询
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		List<Product> nlist = criteria.getExecutableCriteria(session).list()
				.subList(0, 10);

		tx.commit();
		session.close();

		if (nlist != null) {
			return nlist;
		}

		return null;
	}

	// 首页上根据点击的商品的pid进行商品的查询
	public Product findByPid(Integer pid) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Product product = (Product) session.get(Product.class, pid);

		tx.commit();
		session.close();

		if (product != null) {
			return product;
		}

		return null;
	}

	// 根据分类id查询商品个数
	@SuppressWarnings("unchecked")
	public int findCountCid(Integer cid) {
		String hql = "select count(*) from Product p where p.categorySecond.category.cid = ?";
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		List<Long> list = session.createQuery(hql).setParameter(0, cid).list();

		tx.commit();
		session.close();

		if (list != null && list.size() > 0) {
			return list.get(0).intValue();
		}

		return 0;
	}

	// 根据分类id查询商品集合
	public List<Product> findByPageCid(Integer cid, int begin, int limit) {
		// select p.* from category c,categorysecond cs,product p where c.cid =
		// cs.cid and cs.csid = p.csid and c.cid = ?
		String hql = "select p from Product p join p.categorySecond cs join cs.category c where c.cid=?";
		// 分页的另一种写法:
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		try {
			List<Product> plist = new PageHibernateCallback<Product>(hql,
					new Object[] { cid }, begin, limit).doInHibernate(session);

			tx.commit();
			session.close();

			if (plist != null && plist.size() > 0) {
				return plist;
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

	// 根据二级分类查询商品个数
	@SuppressWarnings("unchecked")
	public int findCountCsid(Integer csid) {
		String hql = "select count(*) from Product p where p.categorySecond.csid = ?";
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		List<Long> list = session.createQuery(hql).setParameter(0, csid).list();

		tx.commit();
		session.close();

		if (list != null && list.size() > 0) {
			return list.get(0).intValue();
		}

		return 0;
	}

	// 根据二级分类查询商品信息
	public List<Product> findByPageCsid(Integer csid, int begin, int limit) {
		String hql = "select p from Product p join p.categorySecond cs where cs.csid = ?";
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();

		try {
			List<Product> plist = new PageHibernateCallback<Product>(hql,
					new Object[] { csid }, begin, limit).doInHibernate(session);

			tx.commit();
			session.close();

			if (plist != null && plist.size() > 0) {
				return plist;
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
}
