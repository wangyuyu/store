package com.itheima.service.impl;

import com.itheima.dao.OrderDao;
import com.itheima.domain.Order;
import com.itheima.domain.OrderItem;
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
	
}
