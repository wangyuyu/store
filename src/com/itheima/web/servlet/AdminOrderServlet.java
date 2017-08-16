package com.itheima.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.domain.Order;
import com.itheima.domain.OrderItem;
import com.itheima.service.OrderService;
import com.itheima.utils.BeanFactory;
import com.itheima.utils.JsonUtil;

import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

/**
 * 查询订单的servlet
 */
public class AdminOrderServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	
	public String findAllByState(HttpServletRequest request, HttpServletResponse response) throws Exception{
		//1.接受state
		String state=request.getParameter("state");
		
		//2.调用service
		OrderService os= (OrderService) BeanFactory.getBean("OrderService");
		List<Order> list = null;
		try {
			list = os.findAllByState(state);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//3.将list放入域中 请求转发
		request.setAttribute("list", list);
		return "/admin/order/list.jsp";
	}
	
	/**
	 * 查询订单价格
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String getDetailByOid(HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		//1.接受oid
		String oid = request.getParameter("oid");
		
		//2.调用sevice查询订单详情 返回list<orderitem>
		OrderService os = (OrderService) BeanFactory.getBean("OrderService");
		List<OrderItem> items = os.getById(oid).getItems();
		//3.将list专程json
		//排除不用写回去的数据
		JsonConfig configJson = JsonUtil.configJson(new String[] {"class","itemid","order"});
		JSONArray json = JSONArray.fromObject(items,configJson);
		System.out.println(json);
		response.getWriter().println(json);
		return null;
	}
	/**
	 * 更新订单状态
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String updateState(HttpServletRequest request, HttpServletResponse response) throws Exception{
		response.setContentType("text/html;charset=utf-8");
		//1.接受oid state
		String oid = request.getParameter("oid");
		String state = request.getParameter("state");

		//2.调用service
		OrderService os  = (OrderService) BeanFactory.getBean("OrderService");
		Order order = os.getById(oid);
		order.setState(2);
		
		os.update(order);
		
		//页面重定向
		response.sendRedirect(request.getContextPath() + "/adminOrder?method=findAllByState&state=1");
		return null;
	}
	
}
