package com.itheima.web.servlet;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.Branch;

import com.itheima.domain.PageBean;
import com.itheima.domain.Product;
import com.itheima.service.CategoryService;
import com.itheima.service.ProductService;
import com.itheima.service.impl.ProductServiceImpl;
import com.itheima.utils.BeanFactory;
import com.itheima.utils.CookUtils;

/**
 * 关于商品的servlet
 */
public class ProductServlet extends BaseServlet {
	
	/**
	 * 通过id展示商品
	 * @throws Exception 
	 */
	public String getById(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1.获取商品的ID
		String pid = request.getParameter("pid");
		
		//2.调用service
		ProductService ps = (ProductService) BeanFactory.getBean("ProductService");
		
		//设置浏览记录----------------------------------------------------------------------------------------------
		//获取指定的cookie
		Cookie c = CookUtils.getCookieByName("ids", request.getCookies());
		//获取商品的图片ID
		String pimage = request.getParameter("pimage");

		String ids = "";
		//判断cookie是否为空
		if (c == null) {
			//若cookie为空,需要将当前的商品id放入ids中
			ids = pimage;
			
		} else {
			//若cookie不为空,继续判断ids是否包含了id
			ids = c.getValue();
			String[] arr = ids.split("-");
			//将数组转成集合,此长度不变
			List<String> asList = Arrays.asList(arr);
			//将aslist放入一个新的list中
			LinkedList<String> list = new LinkedList<>(asList);
			
			if (list.contains(pimage)) {
				//若ids包含 将id移除 放到最前面
				list.remove(pimage);
				list.addFirst(pimage);
			} else {
				//若ids 不包含 继续判断长度是否大于2
				if (list.size() > 2) {
					//长度 大于等于3 移除最后一个 将当前的放入最前面
					list.removeLast();
					list.addFirst(pimage);
				} else {
					//长度小于3 将长度放入最前面
					list.addFirst(pimage);
				}
			}
			ids = "";
			//将list转成字符串
			for (String string : list) {
				ids += (string + "-");
			}
			ids = ids.substring(0, ids.length() - 1);
		}
		//将ids写回去
		c = new Cookie("ids", ids); 
		//设置访问路径
		c.setPath(request.getContextPath() + "/");
		//设置存活时间
		c.setMaxAge(3600);
		//写回浏览器
		response.addCookie(c);
		//设置浏览记录-------------------------------------------------------------------------------------------
		
		Product p = ps.getByPid(pid);
		
		//3.将结果放入request中 请求转发
		request.setAttribute("bean", p);
		
		return "/jsp/product_info.jsp";
	}
	
	/**
	 * 分页查询数据
	 * @throws Exception 
	 */
	public String findByPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1.获取了类别当前页面 设置一个pageSize
		String cid = request.getParameter("cid");
		int currPage = Integer.parseInt(request.getParameter("currPage"));
		int pageSize = 12;
		
		
		//2.调用service 返回值pageBean
		ProductService ps = (ProductService) BeanFactory.getBean("ProductService");

		PageBean<Product> bean = ps.findByPage(currPage,pageSize,cid);
		
		System.out.println("list-----" + bean.getList().size());

		//3.将结果放入request中 请求转发
		request.setAttribute("pb", bean);
		return "/jsp/product_list.jsp";
	}
	
	/**
	 * 通过id展示商品
	 * @throws Exception 
	 */
	public String GetProductByIdServlet(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1.获取商品的ID
		String pid = request.getParameter("pid");
		
		//2.调用service
		ProductService ps = (ProductService) BeanFactory.getBean("ProductService");

		Product p = ps.getByPid(pid);
		
		//3.将结果放入request中 请求转发
		request.setAttribute("bean", p);
		
		return "/jsp/product_info.jsp";
	}
	/**
	 * 清空历史记录servlet
	 * @throws Exception 
	 */
	public String ClearHistoryServlet(HttpServletRequest request, HttpServletResponse response) throws Exception {		
		
		//创建一个cookie
		Cookie c = new Cookie("ids", "");
		c.setPath(request.getContextPath() + "/");
		//设置时间
		c.setMaxAge(0);
		//写回浏览器
		response.addCookie(c);
		
		findByPage(request, response);

		return "/jsp/product_list.jsp";
	}
	
}
