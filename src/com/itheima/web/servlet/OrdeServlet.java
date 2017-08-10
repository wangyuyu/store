package com.itheima.web.servlet;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.domain.Cart;
import com.itheima.domain.CartItem;
import com.itheima.domain.Order;
import com.itheima.domain.OrderItem;
import com.itheima.domain.User;
import com.itheima.service.OrderService;
import com.itheima.utils.BeanFactory;
import com.itheima.utils.UUIDUtils;

/**
 * 关于订单的servlet
 */
public class OrdeServlet extends BaseServlet {
	
	/**
	 * 生成订单
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public String add(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//判断用户是否登陆
		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			request.setAttribute("msg", "请先登陆后再来提交订单");
			return "/jsp/msg.jsp";
		}
		
		//封装数据
		Order order = new Order();
		//订单id
		order.setOid(UUIDUtils.getId());
		//订单时间
		order.setOrdertime(new Date());
		//总金额
		//获取session中cart
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		
		order.setTotal(cart.getTotal());
		//订单的所有的订单项
		/*
		 * 获取cart中的itmes
		 * 遍历items 组装成orderItem
		 * 将orderItem添加到list中
		 */
		for (CartItem cartItem : cart.getItems()) {
			OrderItem oi = new OrderItem();
			
			//设置id
			oi.setItemid(UUIDUtils.getId());
			//设置购买数量
			oi.setCount(cartItem.getCount());
			//设置小计
			oi.setSubtotal(cartItem.getSubtotal());
			//设置product
			oi.setProduct(cartItem.getProduct());
			//设置order
			oi.setOrder(order);
			//添加到list中
			order.getItems().add(oi);
			
		}
		
		//设置用户
		order.setUser(user);
		
		//调用service添加订单
		OrderService os = (OrderService) BeanFactory.getBean("OrderService");
		os.add(order);
		
		//将order放入request中，请求转发
		request.setAttribute("bean", order);
		//请空购物车
		request.getSession().removeAttribute("cart");

		return "/jsp/order_info.jsp";
	}

}
