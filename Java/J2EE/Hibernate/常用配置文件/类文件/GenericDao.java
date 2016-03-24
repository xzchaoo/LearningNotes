package com.xzc.dao;

import java.io.Serializable;
import java.util.List;

public interface GenericDao<T, PK extends Serializable> {
	public T getById(PK id);
	public List<T> getAll();
	public T save(T entity);
	public void update(T entity);
	public void delete(T entity);
}
