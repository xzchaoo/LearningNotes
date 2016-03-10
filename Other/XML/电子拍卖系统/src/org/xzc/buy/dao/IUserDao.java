package org.xzc.buy.dao;

import org.xzc.buy.domain.User;

public interface IUserDao extends IBaseDao<User> {

	boolean canLogin(User u);

}
