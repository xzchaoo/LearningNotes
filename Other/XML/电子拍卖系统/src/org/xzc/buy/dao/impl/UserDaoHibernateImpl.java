package org.xzc.buy.dao.impl;

import org.springframework.stereotype.Service;
import org.xzc.buy.dao.IUserDao;
import org.xzc.buy.domain.User;

@Service
public class UserDaoHibernateImpl extends BaseDao<User> implements IUserDao {

	public boolean canLogin(User u) {
		return 1 == (Long) sessionFactory.getCurrentSession().createQuery( "select count(*) from User where name=? and password=?" )
				.setString( 0, u.getName() ).setString( 1, u.getPassword() ).uniqueResult();
	}

}
