package com.itheima.web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.domain.Cart;
import com.itheima.domain.CartItem;
import com.itheima.domain.Product;
import com.itheima.service.ProductService;
import com.itheima.utils.BeanFactory;

/**
 * 购物车相关
 */
public class CartServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * 获取购物车
	 * @param request
	 * @return
	 */
   public Cart getCart(HttpServletRequest request) {
	   Cart cart = (Cart) request.getSession().getAttribute("cart");
	   //判断购物车是否为空
	   
	   if (cart == null) {
		   //创建一个cart
		   cart  = new Cart();
		   
		   //添加到sesssion
		   request.getSession().setAttribute("cart", cart);
	   }
	   return cart;
   }
	/**
	 * 添加购物车
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public String add(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//获取pid
		String pid = request.getParameter("pid");
		int count = Integer.parseInt(request.getParameter("count"));
		
		//通过pid调用productSrevice 获取一个商品
		ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
		Product product = ps.getByPid(pid);
		
		//组装程cartItem
		CartItem cartItem = new CartItem(product, count);
		//添加到购物车中
		Cart cart = getCart(request);
		cart.addCart(cartItem);
		//重定向
		response.sendRedirect(request.getContextPath() + "/jsp/cart.jsp");

		return null;
		
	}
	
	/**
	 * 购物车根据id删除一条消息
	 * @param request
	 * @return
	 */
	public String remove(HttpServletRequest request, HttpServletResponse response) throws Exception {
		  	
		//获取商品的cid
		String pid = request.getParameter("pid");
		
		//调用购物车的remove方法
		getCart(request).removeFromCart(pid);
		//重定向
		response.sendRedirect(request.getContextPath() + "/jsp/cart.jsp");
		
		return null;
	   }
	
	/**
	 * 清空购物车
	 * @param request
	 * @return
	 */
	public String clear(HttpServletRequest request, HttpServletResponse response) throws Exception {
		  	
		
		//获取购物车 清空
		getCart(request).clearCart();
		//重定向
		response.sendRedirect(request.getContextPath() + "/jsp/cart.jsp");
		
		return null;
	   }
}
