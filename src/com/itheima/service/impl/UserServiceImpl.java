package com.itheima.service.impl;

import com.itheima.dao.UserDao;
import com.itheima.dao.impl.UserDaoImpl;
import com.itheima.domain.User;
import com.itheima.service.UserService;
import com.itheima.utils.MailUtils;

public class UserServiceImpl implements UserService{

	@Override
	/**
	 * 用户注册
	 */
	public void regist(User user) throws Exception {
		// TODO Auto-generated method stub
		UserDao dao = new UserDaoImpl();
		dao.add(user);
		/* 暂不实现 ********************************
		// 发送邮件
		// email 收件人地址
		// emailMsg 邮件内容
		String href = "<a href='http://localhost/store/user?method=active&code="+user.getCode()+"'>点此激活</a>";
		String emailMsg = "欢迎您成为我们的一员," + href;
		MailUtils.sendMail(user.getEmail(), emailMsg);
		*/
		
	}

	/**
	 * 用户激活
	 * @throws Exception 
	 */
	@Override
	public User active(String code) throws Exception {
		// TODO Auto-generated method stub
		UserDao dao = new UserDaoImpl();
		
		//通过一个code获取一个用户
		User user = dao.getByCode(code);
		//判断用户是否为空
		if (user == null) {
			return null;
		}
		//修改用户状态
		//设置用户的状态码
		user.setState(1);
		dao.update(user);
		
		return user;
	}

	@Override
	public User login(String usernane, String password) throws Exception {
		// TODO 用户登陆
		UserDao dao = new UserDaoImpl();
		return dao.getByUsernameAndPwd(usernane,password	);

	}

}












