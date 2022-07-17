package com.service.impl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.dao.BaseDao;
import com.service.CommonService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("commonService")
public class CommonServiceImpl<T> implements CommonService<T> {
	
	@Autowired
	public BaseDao baseDao; 
	
    @Override
    @Transactional
    public Serializable insert(T t) {  
        return this.baseDao.insert(t);
    }  
    
    @Override
    @Transactional
	public void insertList(List<T> list) {
		for ( T t : list ){
			this.baseDao.insert(t);
		}
	}  
  
    @Override
    @Transactional
    public void delete(T t) {  
        this.baseDao.delete(t);
    }  
  
    @Override
    @Transactional
    public void update(T t) {  
        this.baseDao.update(t); 
    }  
    
	@Override
	@Transactional
    public T queryById(Integer id, Class<T> entityClass) {  
        return (T) this.baseDao.queryById(id, entityClass);
    }
	
	@Override
	@Transactional
	public T queryUniqueObj(Class<T> entityClass, List<String> propertyNameList, List<Object> propertyValueList){
		String hql = "from " + entityClass.getName() + " where 1=1 ";
		
		List<Object> params = new ArrayList<Object>();
		if ( propertyNameList != null ){
			for ( int i = 0; i < propertyNameList.size(); i ++ ){
				hql += " and " + propertyNameList.get(i) + " = ? ";
				params.add(propertyValueList.get(i));
			}
		}
		
		hql = conditionalQueryOfDel(entityClass, hql);
		return (T) this.baseDao.queryUniqueObj(hql, params);
	}
	
	@Override
	@Transactional
	public List<T> queryAll(Class<T> entityClass){
		String hql = "from " + entityClass.getName() + " where 1=1 ";
		hql = conditionalQueryOfDel(entityClass, hql);
		return this.baseDao.queryList(hql, null);
	}
	
	@Override
	@Transactional
	public List<T> queryList(Class<T> entityClass, List<String> propertyNameList, List<Object> propertyValueList){
		String hql = "from " + entityClass.getName() + " where 1=1 ";
		
		List<Object> params = new ArrayList<Object>();
		if ( propertyNameList != null ){
			for ( int i = 0; i < propertyNameList.size(); i ++ ){
				hql += " and " + propertyNameList.get(i) + " = ? ";
				params.add(propertyValueList.get(i));
			}
		}
				
		hql = conditionalQueryOfDel(entityClass, hql);
		return this.baseDao.queryList(hql, params);
	}

	@Override
	@Transactional
	public long count(Class<T> entityClass, List<String> propertyNameList, List<Object> propertyValueList){
		String hql = "select count(*) "
				+ " from " + entityClass.getName() + " where 1=1 ";
		
		List<Object> params = new ArrayList<Object>();
		if ( propertyNameList != null ){
			for ( int i = 0; i < propertyNameList.size(); i ++ ){
				hql += " and " + propertyNameList.get(i) + " = ? ";
				params.add(propertyValueList.get(i));
			}
		}
				
		hql = conditionalQueryOfDel(entityClass, hql);
		List<Long> list = this.baseDao.queryList(hql, params);
		return list.get(0);
	}
	
	@Override
	@Transactional
	public List<T> queryByPage(Class<T> entityClass, List<String> propertyNameList, List<Object> propertyValueList,
    		int pageSize, int page){
		
		String hql = "from " + entityClass.getName() + " where 1=1 ";
		
		List<Object> params = new ArrayList<Object>();
		if ( propertyNameList != null ){
			for ( int i = 0; i < propertyNameList.size(); i ++ ){
				hql += " and " + propertyNameList.get(i) + " = ? ";
				params.add(propertyValueList.get(i));
			}
		}
		
		hql = conditionalQueryOfDel(entityClass, hql);
		return this.baseDao.queryListByPage(hql, pageSize, page, params);
	}
	
	@Override
	@Transactional
	public List<T> queryByHql(String hql, List<Object> params){
		return this.baseDao.queryList(hql, params);
	}
	
	@Override
	@Transactional
	public List<T> queryByHql(String hql, List<Object> params,boolean toMap){
		return this.baseDao.queryList(hql, params,toMap);
	}
	
	@Override
	@Transactional
	public int updateByHql(String hql, List<Object> params){
		return this.baseDao.updateByHql(hql, params);
	}
	
	@Override
	@Transactional
	public int updateBySql(String hql, List<Object> params){
		return this.baseDao.updateBySql(hql, params);
	}
	
	/**
	 * 补全hql: 
	 * and (del is null or del=0)
	 */ 
	private String conditionalQueryOfDel(Class<T> entityClass, String hql){
		
		List<String> delFieldNameList = Arrays.asList("del", "delFlag", "deleteFlag");
		
		Field[] fields = entityClass.getDeclaredFields();
		for(int i = 0;i < fields.length;i ++){
			String fieldName = fields[i].getName();
			if ( delFieldNameList.contains(fieldName) ){
				hql += " and ( " + fieldName + " is null or " + fieldName + " = 0 ) ";
				break;
			}
		}
		return hql;
	}
}
