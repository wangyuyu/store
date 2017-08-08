package com.itheima.web.servlet;

import java.io.IOException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 通用的servlet 
 */
public class BaseServlet extends HttpServlet {
	@Override
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//1.获取子类
			Class clazz = this.getClass();
			System.out.println(this);
			//2.获取请求的方法
			String m = request.getParameter("method");
			System.out.println(m);
			if (m == null) {
				m = "index";
			}
			//3.获取方法对象
			Method method = clazz.getMethod(m, HttpServletRequest.class,HttpServletResponse.class);
			//4.让方法执行 返回值为请求转发的路径
			String s = (String) method.invoke(this, request,response);
			//5.判断s是否为空
			if (s!=null) {
				request.getRequestDispatcher(s).forward(request, response);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException();
		} 
	}
	
}












