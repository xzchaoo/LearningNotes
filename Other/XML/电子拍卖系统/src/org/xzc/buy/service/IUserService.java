package org.xzc.buy.service;

import org.xzc.buy.domain.User;

public interface IUserService {
	boolean canLogin(User u);
}
