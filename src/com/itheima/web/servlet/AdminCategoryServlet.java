package com.itheima.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.domain.Category;
import com.itheima.service.CategoryService;
import com.itheima.utils.BeanFactory;
import com.itheima.utils.UUIDUtils;

/**
 * 后台分类管理模块
 */
public class AdminCategoryServlet extends BaseServlet {

    /**
     * 查询所有分类
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
	 public String findAll(HttpServletRequest request, HttpServletResponse response) throws Exception {

		 //调用service查询所有的分类信息 返回值list
		 CategoryService cs  = (CategoryService) BeanFactory.getBean("CategoryService");
		 List<Category> list = cs.findAll();
		  
		 //将list放入request域中 请求转发
		 request.setAttribute("list", list);
		 
		 return "/admin/category/list.jsp";
	}
	 /**
	  * 跳转到添加页面上去
	  * @param request
	  * @param response
	  * @return
	  * @throws Exception
	  */
	 public String addUI(HttpServletRequest request, HttpServletResponse response) throws Exception {
		 
		 return "/admin/category/add.jsp";
	}
	 /**
	  * 添加分类
	  * @param request
	  * @param response
	  * @return
	  * @throws Exception
	  */
	 public String add(HttpServletRequest request, HttpServletResponse response) throws Exception {
		 //接受cname
		 String cname = request.getParameter("cname");
		 //声明一个service
		 CategoryService cs = (CategoryService) BeanFactory.getBean("CategoryService");
		 
		 //封装category
		 Category c = new Category();
		 //数组个数转字符串
		 c.setCid(UUIDUtils.getId());
		 c.setCname(cname);
		 
		 //调用service完成添加操作
		 cs.add(c);
		 
		 //重定向查询所有分类
		 response.sendRedirect(request.getContextPath() + "/adminCategory?method=findAll");
		 
		 return null;
	}
	 
 	/**
	 * 通过cid获取一个分类对象
	 */
	 public String getById(HttpServletRequest request, HttpServletResponse response) throws Exception {
		 //接受cname
		 String cid = request.getParameter("cid");
		 //声明一个service 
		 CategoryService cs = (CategoryService) BeanFactory.getBean("CategoryService");
		 
		 //调用service完成添加操作
		 Category c = cs.getById(cid);
		 
		 //请求转发/admin/category/edit.jsp
		 request.setAttribute("bean", c);
		 
		 return "/admin/category/edit.jsp";
	}
	 /**
	 * 更新分类信息方法
	 */
	 public String update(HttpServletRequest request, HttpServletResponse response) throws Exception {
		 //接受cname
		 String cid = request.getParameter("cid");
		 String cname = request.getParameter("cname");
		 //封装参数
		 Category c = new Category();
		 c.setCid(cid);
		 c.setCname(cname);
		 //调用service 
		 CategoryService cs = (CategoryService) BeanFactory.getBean("CategoryService");
		 cs.update(c);
		 
		 response.sendRedirect(request.getContextPath()+"/adminCategory?method=findAll");
		 return null;
	} 
	 /**
	 * 删除分类
	 */
	 public String delete(HttpServletRequest request, HttpServletResponse response) throws Exception {
		 
		 //1.获取cid
		String cid = request.getParameter("cid");
		
		//2.调用service 完成删除
		CategoryService cs=(CategoryService) BeanFactory.getBean("CategoryService");
		cs.delete(cid);
		
		//3.重定向
		response.sendRedirect(request.getContextPath()+"/adminCategory?method=findAll");
		
		 return null;
	} 
	 
}
