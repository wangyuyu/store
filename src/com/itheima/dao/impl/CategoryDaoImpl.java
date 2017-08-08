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

	@Override
	public List<Category> findAll() throws Exception{
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from category";
//		return qr.query(sql, new BeanHandler<>(Category.class));
//		return (List<Category>) qr.query(sql, new BeanHandler<>(Category.class));
		return qr.query(sql, new BeanListHandler<>(Category.class));
		
	}

}












