package org.xzc.buy.dao.impl;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.SessionFactory;
import org.xzc.buy.dao.IBaseDao;

public class BaseDao<T> implements IBaseDao<T> {
	protected Class<T> clazz = (Class<T>) ( (ParameterizedType) ( this.getClass().getGenericSuperclass() ) ).getActualTypeArguments()[0];

	protected SessionFactory sessionFactory;

	public T get(int id) {
		return (T) sessionFactory.getCurrentSession().get( clazz, id );
	}

	@Override
	public void save(T t) {
		sessionFactory.getCurrentSession().save( t );
	}

	@Override
	public void update(T t) {
		sessionFactory.getCurrentSession().update( t );
	}

	@Override
	public void delete(int id) {
		sessionFactory.getCurrentSession().createQuery( "delete from " + clazz.getName() + " where id=?" ).setInteger( 0, id ).executeUpdate();
	}

	@Override
	public void delete(T t) {
		sessionFactory.getCurrentSession().delete( t );
	}

	public List<T> getAll() {
		return (List<T>) sessionFactory.getCurrentSession().createQuery( "select * from " + clazz.getName() ).list();
	}

	@Resource
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

}
