package com.xzc.dao;

import hibernate.HibernateUtil;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

public abstract class GenericDaoHibernate<T, PK extends Serializable> implements GenericDao<T, PK> {
	private Class<T> clazz;

	public GenericDaoHibernate() {
		clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	public T getById(PK id) {
		Session s = HibernateUtil.getSession();
		return (T) s.get(clazz, id);
	}

	public List<T> getAll() {
		Session s = HibernateUtil.getSession();
		Query q = s.createQuery("from " + clazz.getName());
		return q.list();
	}

	public T save(T entity) {
		Session s = HibernateUtil.getSession();
		s.save(entity);
		return entity;
	}

	public void update(T entity) {
		Session s = HibernateUtil.getSession();
		s.update(entity);
	}

	public void delete(T entity) {
		Session s = HibernateUtil.getSession();
		s.delete(entity);
	}

}
