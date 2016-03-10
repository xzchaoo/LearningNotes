package org.xzc.buy.login.action;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.xzc.buy.action.BaseAction;
import org.xzc.buy.domain.User;
import org.xzc.buy.service.IUserService;

public class LoginAction extends BaseAction {
	public static final String ERROR_PASSWORD = "error_password";

	public static final String FIELD_PASSWORD = "password";
	public static final String FIELD_USERNAME = "username";
	private String username;
	private String password;

	private IUserService userService;

	public String login() throws Exception {
		User u = new User( 0, username, password );
		if (userService.canLogin( u )) {
			return TO_HOME;
		}
		addFieldError( FIELD_PASSWORD, getText( ERROR_PASSWORD ) );
		return INPUT;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Resource
	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

}
