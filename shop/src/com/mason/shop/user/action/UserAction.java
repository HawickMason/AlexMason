package com.mason.shop.user.action;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.mason.shop.user.service.UserService;
import com.mason.shop.user.vo.User;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class UserAction extends ActionSupport implements ModelDriven<User>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	//模型驱动使用的对象
	private User user = new User();
	
	@Override
	public User getModel() {
		return user;
	}
	
	
	//注入UserService
	private UserService userService;
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
	//接收验证码:
	private String checkcode;
	
	public void setCheckcode(String checkcode) {
		this.checkcode = checkcode;
	}
	
	/**
	 * 跳转到注册页面的执行方法
	 */
	public String registPage(){
		return "registPage";
	}

	/**
	 * AJAX进行异步校验用户名的执行方法
	 * @return
	 * @throws IOException 
	 */
	public String findByName() throws IOException{
		//调用service进行查询
		User exitUser = userService.findByUsername(user.getUsername());
		//System.out.println("In Action:username = " + user.getUsername());
		//获得response对象，对页面输出:
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=UTF-8");
		//判断
		if(exitUser != null){
			//查询到该用户:用户名已经存在
			response.getWriter().print("<font color='red'>该用户名已经存在</font>");
		}else{
			//没查询到该用户:用户名可以使用
			response.getWriter().print("<font color='green'>用户名可以使用</font>");
		}
		return NONE;
	}
	
	/**
	 * 用户注册的方法: 
	 * @return
	 */
	public String regist(){
		//判断验证码程序:
		//从session中获取验证码的随机值:
		String checkcode1 = (String) ServletActionContext.getRequest()
				.getSession().getAttribute("checkcode");
		if(!checkcode.equalsIgnoreCase(checkcode1)){
			this.addActionError("验证码输入错误!");
			return "checkcodeFail";
		}
		userService.save(user);
		this.addActionMessage("注册成功!请去邮箱激活~");
		return "msg";
	}
	
	/**
	 * 用户激活的方法:
	 * @return
	 */
	public String activate(){
		//根据激活码查询用户:
		User existUser = userService.findByCode(user.getCode());
		System.out.println("usercode:" + user.getCode());
		if(existUser == null){
			//激活码错误的
			this.addActionMessage("激活失败:激活码错误!");
		}else{
			//激活成功
			//修改用户状态
			existUser.setState(1);
			existUser.setCode(null);
			userService.update(existUser);
			this.addActionMessage("激活成功!请登录~");
		}
		return "msg";
	}

	/**
	 * 跳转到登录页面的方法
	 * @return
	 */
	public String loginPage(){
		return "loginPage";
	}

	/**
	 * 登录的方法
	 * @return
	 */
	public String login(){
		User existUser = userService.login(user);
		//判断
		if(existUser == null){
			//登录失败
			this.addActionError("登录失败:用户名或密码错误或用户未激活!");
			return LOGIN;
		} else {
			//登录成功
			//将用户的信息存入到session中
			ServletActionContext.getRequest().getSession().setAttribute("existUser", existUser);
			//页面跳转
			return "loginSucess";
		}		
	}
	
	public String quit(){
		//销毁session
		ServletActionContext.getRequest().getSession().invalidate();
		return "quit";
	}
}
