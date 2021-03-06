package com.itheima.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.domain.Category;
import com.itheima.service.CategoryService;
import com.itheima.service.impl.CategoryServiceImpl;
import com.itheima.utils.BeanFactory;
import com.itheima.utils.JsonUtil;

/**
 * 关于首页分类的servlet
 */
public class CategoryServlet extends BaseServlet {
	
	/**
	 * 查询所有的分类
	 */
	public String findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		new BeanFactory();
				//调用categoryService查询所有的分类 返回值list
				CategoryService cs = (CategoryService) BeanFactory.getBean("CategoryService");
				List<Category> clist = null;
				try {
					clist = cs.findAll();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
				//将返回json格式，返回到页面上
//				request.setAttribute("clist", clist);
				String json = JsonUtil.list2json(clist);
				//写回页面
				response.setContentType("text/html;charset=utf-8");
				response.getWriter().print(json);;
				
				return null;
	}
}








