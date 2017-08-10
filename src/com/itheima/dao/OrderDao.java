package com.itheima.dao;

import com.itheima.domain.Order;
import com.itheima.domain.OrderItem;

public interface OrderDao {

	void add(Order order) throws Exception;

	void addItem(OrderItem oi) throws Exception;

}
