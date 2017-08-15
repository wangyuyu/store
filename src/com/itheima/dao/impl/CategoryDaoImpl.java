package com.itheima.dao.impl;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.itheima.dao.CategoryDao;
import com.itheima.domain.Category;
import com.itheima.domain.User;
import com.itheima.utils.DataSourceUtils;
import com.sun.org.apache.bcel.internal.generic.NEW;

public class CategoryDaoImpl implements CategoryDao{

	/**
	 * 查询所有
	 */
	@Override
	public List<Category> findAll() throws Exception{
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from category";
//		return qr.query(sql, new BeanHandler<>(Category.class));
//		return (List<Category>) qr.query(sql, new BeanHandler<>(Category.class));
		return qr.query(sql, new BeanListHandler<>(Category.class));
		
	}
	
	/**
	 * 添加分类
	 */
	@Override
	public void add(Category c) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "insert into category values(?,?)";
		qr.update(sql,c.getCid(),c.getCname());
	}
	
	/**
	 * 通过cid查询一条分类
	 */
	@Override
	public Category getById(String cid) throws Exception {
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from category where cid = ? limit 1";
		
		return qr.query(sql, new BeanHandler<>(Category.class),cid);
	}

	/**
	 * 更新分类名称
	 */
	@Override
	public void update(Category c) throws Exception {
		// TODO Auto-generated method stub
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "update category set cname = ? where cid = ?";
		qr.update(sql,c.getCname(),c.getCid());
	}

}












