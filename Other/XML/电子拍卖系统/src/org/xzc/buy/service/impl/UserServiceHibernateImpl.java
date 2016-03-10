package org.xzc.buy.service.impl;

import javax.annotation.Resource;

import org.hibernate.jmx.HibernateService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.xzc.buy.dao.IUserDao;
import org.xzc.buy.domain.User;
import org.xzc.buy.service.IUserService;

@Service
public class UserServiceHibernateImpl extends HibernateService implements IUserService {
	@Resource
	private IUserDao userDao;
	
	@Transactional(propagation = Propagation.REQUIRED)
	public boolean canLogin(User u) {
		return userDao.canLogin(u);
	}

}
