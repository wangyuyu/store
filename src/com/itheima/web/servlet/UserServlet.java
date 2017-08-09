package com.itheima.web.servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;

import com.itheima.constant.Constant;
import com.itheima.domain.User;
import com.itheima.myconvernter.MyConventer;
import com.itheima.service.ProductService;
import com.itheima.service.UserService;
import com.itheima.service.impl.UserServiceImpl;
import com.itheima.utils.BeanFactory;
import com.itheima.utils.MD5Utils;
import com.itheima.utils.UUIDUtils;

/**
 * 和用户相关的servlet
 */
public class UserServlet extends BaseServlet {
	
	public String add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("userServlet的add方法执行了");
		return null;
	}
	/**
	 * 跳转到注册页面
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String registUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		return "/jsp/register.jsp";
	}
	
	/**
	 * 用户注册
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public String regist(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1.封装数据
		User user = new User();
		//注册自定义转换器
		ConvertUtils.register(new MyConventer(), Date.class);
		BeanUtils.populate(user, request.getParameterMap());
		System.out.println("birthday---------------" + user.getBirthday());
		System.out.println("name---------------" + user.getName());
	
		//1.1设置用户ID
		user.setUid(UUIDUtils.getId()); 
		//1.2设置激活码
		user.setCode(UUIDUtils.getId());
		//1.3加密密码
		user.setPassword(MD5Utils.md5(user.getPassword()));
		
		//2.调用service完成注册
		UserService s = (UserService) BeanFactory.getBean("UserService");

		s.regist(user);
		
		//3.页面请求转发
		request.setAttribute("msg", "用户注册成功，请去邮箱激活～～～");
		return "/jsp/msg.jsp";
	}
	
	/**
	 * 用户激活
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public String active(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//获取激活码
		String code = request.getParameter("code");
		//调用service 完成激活
		UserService s = (UserService) BeanFactory.getBean("UserService");

		User user = s.active(code);
		
		if (user == null) {
			//通过激活码没有找到用户
			request.setAttribute("msg", "请重新激活");
		} else {
			request.setAttribute("msg", "激活成功啦");
		}
		return "/jsp/msg.jsp";
	}
	
	/**
	 * 跳转到登陆界面
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String loginUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		return "/jsp/login.jsp";
	}
	
	/**
	 * 登陆
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public String login(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//获取用户名和密码
		String usernane = request.getParameter("username");
		String password = request.getParameter("password");
		password = MD5Utils.md5(password);
		
		//调用service完成登陆操作 返回user
		UserService s = (UserService) BeanFactory.getBean("UserService");

		User user = s.login(usernane,password);
		
		//判断用户
		if (user == null) {
			//用户名密码不匹配
			request.setAttribute("msg", "用户名密码不匹配");
			return "/jsp/login.jsp";
			
		} else {
			//判断用户的状态是否被激活
			if (Constant.USER_IS_ACTIVE != user.getState()) {
				request.setAttribute("msg", "用户未激活");
				return "/jsp/login.jsp";
			}
		}
		//将user放入session域中 重定向
		request.getSession().setAttribute("user", user);
		response.sendRedirect(request.getContextPath() + "/"); //去首页
		return null;
	}
	
	/**
	 * 用户退出
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//干掉session
		request.getSession().invalidate();
		//重定向
		response.sendRedirect(request.getContextPath());
		//处理自动登录
		
		
		return null;
	}
}















