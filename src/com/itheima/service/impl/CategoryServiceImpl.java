package com.itheima.service.impl;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

import com.itheima.dao.CategoryDao;
import com.itheima.dao.ProductDao;
import com.itheima.dao.impl.CategoryDaoImpl;
import com.itheima.domain.Category;
import com.itheima.service.CategoryService;
import com.itheima.service.ProductService;
import com.itheima.utils.BeanFactory;
import com.itheima.utils.DataSourceUtils;

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
			CategoryDao cd = (CategoryDao) BeanFactory.getBean("CategoryDao");

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

	/**
	 * 添加一条分类
	 */
	@Override
	public void add(Category c) throws Exception {
		
		CategoryDao cd = (CategoryDao) BeanFactory.getBean("CategoryDao");
		cd.add(c);
		//添加成功之后 更新缓存
		// 1.先创建混存处理器
		CacheManager cm = CacheManager.create(CategoryServiceImpl.class.getClassLoader().getResourceAsStream("ehcache.xml"));
		
		// 2.获取指定的缓存
		Cache cache = cm.getCache("categoryCache");
		
		//清空
		cache.remove("clist");
	}

	/**
	 * 通过cid获取一个分类对象
	 */
	@Override
	public Category getById(String cid) throws Exception {
		
		CategoryDao cd = (CategoryDao) BeanFactory.getBean("CategoryDao");

		return cd.getById(cid);
	}
	
	/**
	 * 更新分类对象
	 */
	@Override
	public void update(Category c) throws Exception {
		// TODO Auto-generated method stub
		CategoryDao cd = (CategoryDao) BeanFactory.getBean("CategoryDao");
		cd.update(c);
		
		//清空缓存
		CacheManager cm = CacheManager.create(CategoryServiceImpl.class.getClassLoader().getResourceAsStream("ehcache.xml"));
		Cache cache = cm.getCache("categoryCache");
		//清空
		cache.remove("clist");
	}

	@Override
	public void delete(String cid) throws Exception{ 
		
		try {
			//1.开启事务
			DataSourceUtils.startTransaction();

			//2.更新商品
			ProductDao pd=(ProductDao) BeanFactory.getBean("ProductDao");
			pd.updateCid(cid);
			
			//3.删除分类
			CategoryDao cd=(CategoryDao) BeanFactory.getBean("CategoryDao");
			cd.delete(cid);
			
			//4.事务控制
			DataSourceUtils.commitAndClose();
			
			//5.清空缓存
			CacheManager cm = CacheManager.create(CategoryServiceImpl.class.getClassLoader().getResourceAsStream("ehcache.xml"));
			Cache cache = cm.getCache("categoryCache");
			cache.remove("clist");
		} catch (Exception e) {
			e.printStackTrace();
			DataSourceUtils.rollbackAndClose();
			throw e;
		}
		
	}
	
//	public static void main(String[] args) {
//		InputStream is = CategoryServiceImpl.class.getClassLoader().getResourceAsStream("ehcache.xml");
//		System.out.println(is);
//	}

}
