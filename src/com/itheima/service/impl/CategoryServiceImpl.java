package com.itheima.service.impl;

import java.io.InputStream;
import java.util.List;

import com.itheima.dao.CategoryDao;
import com.itheima.dao.impl.CategoryDaoImpl;
import com.itheima.domain.Category;
import com.itheima.service.CategoryService;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class CategoryServiceImpl implements CategoryService{

	/**
	 * 查询所有的分类
	 */
	@Override
	public List<Category> findAll() throws Exception {
		// TODO Auto-generated method stub
		// 1.先创建混存处理器
		CacheManager cm = CacheManager.create(CategoryServiceImpl.class.getClassLoader().getResourceAsStream("ehcache.xml"));
		
		// 2.获取指定的缓存
		Cache cache = cm.getCache("categoryCache");
		
		// 3.通过缓存获取数据 将cache看成一个map集合
		Element elemet = cache.get("clist");
		
		List<Category> list = null;
		// 4.判断数据是否为空
		if (elemet == null) {
			//从数据库中获取
			CategoryDao cd = new CategoryDaoImpl();
			list = cd.findAll();
			//将list放入缓存
			cache.put(new Element("clist", list));
			
			System.out.println("缓存中没有数据 请到数据库中获取");
		} else {
			
			list = (List<Category>) elemet.getObjectValue();
			
			//直接返回
			System.out.println("缓存中是有数据的");
		}
		
		
		return list;
	} 
	
//	public static void main(String[] args) {
//		InputStream is = CategoryServiceImpl.class.getClassLoader().getResourceAsStream("ehcache.xml");
//		System.out.println(is);
//	}

}
