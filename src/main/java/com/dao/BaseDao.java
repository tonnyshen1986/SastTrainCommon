package com.dao;

import java.io.Serializable;
import java.util.List;  

public interface BaseDao<T> {
	
	public Class<T> getEntityClass();
	void setEntityClass(Class<T> entityClass);
	
	Serializable insert(T t);  
    void delete(T t);  
    void update(T t);  
    T queryById(long id);  
    T queryById(Integer id);  
    T queryById(Integer id, Class<T> entityClass);
    
    List<T> queryList(String hql, List<Object> params);
    List<T> queryList(String hql, List<Object> params, boolean toMap);
    
    List<T> queryListBySql(String sql, List<Object> params);
    List<T> queryListBySql(String sql, List<Object> params, boolean toMap);
    
    public List<T> queryListByPage(String hql,int pageSize,int page, List<Object> params);
    public List<T> queryListByPage(String hql,int pageSize,int page, List<Object> params, boolean toMap);
    
    public List<T> queryListByPageBySql(String sql,int pageSize,int page, List<Object> params);
    public List<T> queryListByPageBySql(String sql,int pageSize,int page, List<Object> params, boolean toMap);
    
    public T queryUniqueObj(String hql, List<Object> params);
    public T queryUniqueObj(String hql, List<Object> params, boolean toMap);
    
    public int updateByHql(String hql);
    public int updateByHql(String hql, List<Object> params);
    public int updateBySql(String hql);
    public int updateBySql(String sql, List<Object> params);
}