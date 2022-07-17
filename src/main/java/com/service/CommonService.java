package com.service;

import java.io.Serializable;
import java.util.List;

/**
 * 针对单表
 * 简单hql操作 
 */
public interface CommonService<T> {
	
	Serializable insert(T t); 
	void insertList(List<T> list); 
    void delete(T t);  
    void update(T t);  
    T queryById(Integer id, Class<T> entityClass);
    
    public T queryUniqueObj(Class<T> entityClass, List<String> propertyNameList, List<Object> propertyValueList);
    
    List<T> queryAll(Class<T> entityClass);
    
    List<T> queryList(Class<T> entityClass, List<String> propertyNameList, List<Object> propertyValueList);
    
    long count(Class<T> entityClass, List<String> propertyNameList, List<Object> propertyValueList);
    
    List<T> queryByPage(Class<T> entityClass, List<String> propertyNameList, List<Object> propertyValueList,
    		int pageSize, int page);
    
    // 在controller层直接生成hql
    List<T> queryByHql(String hql, List<Object> params);
    List<T> queryByHql(String hql, List<Object> params,boolean toMap);
    int updateByHql(String hql, List<Object> params);
    int updateBySql(String sql, List<Object> params);
    
}