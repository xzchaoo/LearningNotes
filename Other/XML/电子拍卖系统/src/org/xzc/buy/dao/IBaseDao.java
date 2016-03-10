package org.xzc.buy.dao;

import java.util.List;

public interface IBaseDao<T> {
	T get(int id);
	void save(T t);
	void update(T t);
	void delete(int id);
	void delete(T t);
	List<T> getAll();
}
