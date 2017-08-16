package com.itheima.service.impl;

import java.util.List;

import com.itheima.dao.OrderDao;
import com.itheima.domain.Order;
import com.itheima.domain.OrderItem;
import com.itheima.domain.PageBean;
import com.itheima.domain.User;
import com.itheima.service.OrderService;
import com.itheima.utils.BeanFactory;
import com.itheima.utils.DataSourceUtils;

public class OrderServiceimpl implements OrderService{

	@Override
	public void add(Order order) throws Exception {
		try {
			//开启事物
			DataSourceUtils.startTransaction();
			//向order表中添加一条数据
			OrderDao od = (OrderDao) BeanFactory.getBean("OrderDao");
			od.add(order);
//			int i = 1/0;
			
			
			//向orderitems添加n条数据
			for (OrderItem oi: order.getItems()) {
				od.addItem(oi);
			}
			//事物处理
			DataSourceUtils.commitAndClose();
		} catch (Exception e) {
			// TODO: handle exception
			DataSourceUtils.rollbackAndClose();
			throw e;
		}
	}
	
	/**
	 * 分页查询订单
	 */
	@Override
	public PageBean<Order> findAllByPage(int currPage, int pageSize, User user) throws Exception {
		//查询dao
		OrderDao od =  (OrderDao) BeanFactory.getBean("OrderDao");
		//查询当前页数据
		List<Order> list = od.findAllByPage(currPage,pageSize,user.getUid());
		//查询总条数
		int totalCount = od.getTotalCount(user.getUid());
		
		return new PageBean<>(list, currPage, pageSize, totalCount);
	}
	
	/**
	 * 查看订单详情
	 */
	@Override
	public Order getById(String oid) throws Exception {
		//查询dao
		OrderDao od =  (OrderDao) BeanFactory.getBean("OrderDao");
		
		return od.getById(oid);
	}

	/**
	 * 更新订单状态
	 */
	@Override
	public void update(Order order) throws Exception {
		//查询dao
		OrderDao od =  (OrderDao) BeanFactory.getBean("OrderDao");
		od.update(order);
	}

	/**
	 * 根据状态查询所有订单
	 */
	@Override
	public List<Order> findAllByState(String state) throws Exception {
		OrderDao od=(OrderDao) BeanFactory.getBean("OrderDao");
		return od.findAllByState(state);
	}
	
}
