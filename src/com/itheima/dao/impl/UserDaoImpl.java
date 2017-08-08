package com.itheima.dao.impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.itheima.dao.UserDao;
import com.itheima.domain.User;
import com.itheima.utils.DataSourceUtils;
import com.sun.org.apache.bcel.internal.generic.NEW;

public class UserDaoImpl implements UserDao{

	/**
	 * 添加用户
	 * @throws SQLException 
	 */
	@Override
	public void add(User user) throws SQLException {
		// TODO Auto-generated method stub
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
	
		String sql="insert into user values(?,?,?,?,?,?,?,?,?,?);";
		qr.update(sql, user.getUid(),user.getUsername(),user.getPassword(),
				user.getName(),user.getEmail(),user.getTelephone(),
				user.getBirthday(),user.getSex(),user.getState(),user.getCode());
	}

	/**
	 * 通过激活码获取一个用户
	 */
	@Override
	public User getByCode(String code) throws Exception {
		
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from user where code = ? limit 1";
					  
		return qr.query(sql, new BeanHandler<>(User.class),code);
	}

	/**
	 * 修改用户
	 */
	@Override
	public void update(User user) throws Exception {
		// TODO Auto-generated method stub
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		
		String sql = "update user set username = ? , password = ? , name = ? ,email = ?, birthday = ?,state = ?,code = ? where uid = ?";
		qr.update(sql,user.getUsername(),user.getPassword(),user.getName(),user.getEmail(),user.getBirthday(),user.getState(),user.getCode(),user.getUid());
	}

	/**
	 * 登陆
	 */
	@Override
	public User getByUsernameAndPwd(String usernane, String password) throws Exception {
		// TODO Auto-generated method stub
		QueryRunner qr = new QueryRunner(DataSourceUtils.getDataSource());
		String sql = "select * from user where username = ? and password = ? limit 1";
		
		return qr.query(sql, new BeanHandler<>(User.class) ,usernane,password);
	}
	
}















