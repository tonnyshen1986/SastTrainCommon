package com.dao.impl;

import java.io.Serializable;
import java.util.List;

import com.dao.BaseDao;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.transform.Transformers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository("baseDao")
public class BaseDaoImpl<T> implements BaseDao<T> {
	
    protected Class<T> entityClass;  
    
    @Autowired  
    private SessionFactory sessionFactory;  
	
	public BaseDaoImpl(){  
		
    }
  
	@Override
    public Class<T> getEntityClass() {
		return entityClass;
	}

    @Override
	public void setEntityClass(Class<T> entityClass) {
		this.entityClass = entityClass;
	}

    @Override  
    public Serializable insert(T t) {  
        return sessionFactory.getCurrentSession().save(t);  
    }  
  
    @Override  
    public void delete(T t) {  
        sessionFactory.getCurrentSession().delete(t);  
    }  
  
    @Override  
    public void update(T t) {  
        sessionFactory.getCurrentSession().update(t);  
    }  
    
    @Override
     public int updateByHql(String hql){
          return sessionFactory.getCurrentSession().createQuery(hql).executeUpdate();
     }
    
    @Override
    public int updateByHql(String hql, List<Object> params){
    	Query query = sessionFactory.getCurrentSession().createQuery(hql);
    	setQueryParams(query, params); 
    	return query.executeUpdate();
    }
    
    @Override
    public int updateBySql(String sql){
         return sessionFactory.getCurrentSession().createSQLQuery(sql).executeUpdate();
    }
    
    @Override
    public int updateBySql(String sql, List<Object> params){
    	Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
    	setQueryParams(query, params); 
    	return query.executeUpdate();
    }
    
    @SuppressWarnings("unchecked")
	@Override
    public T queryById(long id) {  
        return (T) sessionFactory.getCurrentSession().get(entityClass, id);  
    }  
    
    @SuppressWarnings("unchecked")
	@Override
    public T queryById(Integer id, Class<T> entityClass) {  
        return (T) sessionFactory.getCurrentSession().get(entityClass, id);  
    }  
    
    @SuppressWarnings("unchecked")
	@Override
    public T queryById(Integer id) {  
        return (T) sessionFactory.getCurrentSession().get(entityClass, id);  
    }  
    
    @SuppressWarnings("unchecked")
	@Override
    public List<T> queryList(String hql, List<Object> params) {  
    	return queryList(hql, params, false);
    } 
    
    @Override
    public List<T> queryList(String hql, List<Object> params, boolean toMap) {  
        Query query = sessionFactory.getCurrentSession().createQuery(hql);  
        if ( toMap ){
    		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);  
    	}
        setQueryParams(query, params);  
        return query.list();  
    } 
    
    @Override
    public List<T> queryListBySql(String sql, List<Object> params){
    	return queryListBySql(sql, params, false);
    }
    
    @Override
    public List<T> queryListBySql(String sql, List<Object> params, boolean toMap){
    	Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
    	if ( toMap ){
    		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);  
    	}
    	setQueryParams(query, params); 
    	return query.list();
    }
    
    @SuppressWarnings("unchecked")
    @Override
	public T queryUniqueObj(String hql, List<Object> params) {  
        return queryUniqueObj(hql, params, false);
    }
    
    @SuppressWarnings("unchecked")
    @Override
	public T queryUniqueObj(String hql, List<Object> params, boolean toMap) {  
        Query query = sessionFactory.getCurrentSession().createQuery(hql);  
        if ( toMap ){
    		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);  
    	}
        setQueryParams(query, params);  
        return (T) query.uniqueResult();  
    }
    
    @SuppressWarnings("unchecked")
    @Override
	public List<T> queryListByPage(String hql,int pageSize,int page, List<Object> params){
    	return queryListByPage(hql, pageSize, page, params, false);
    }
    
    @SuppressWarnings("unchecked")
    @Override
	public List<T> queryListByPage(String hql,int pageSize,int page, List<Object> params, boolean toMap){
    	Query query = sessionFactory.getCurrentSession().createQuery(hql);  
    	if ( toMap ){
    		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP); 
    	}
    	setQueryParams(query, params); 
    	query.setFirstResult((page-1)*pageSize); 
    	query.setMaxResults(pageSize); 
    	return query.list();
    }
    
    // 原生sql分页查询 
    @SuppressWarnings("unchecked")
    @Override
	public List<T> queryListByPageBySql(String sql,int pageSize,int page, List<Object> params){
    	return queryListByPageBySql(sql, pageSize, page, params, false);
    }
    
    @SuppressWarnings("unchecked")
    @Override
	public List<T> queryListByPageBySql(String sql,int pageSize,int page, List<Object> params, boolean toMap){
    	Query query = sessionFactory.getCurrentSession().createSQLQuery(sql);
    	if ( toMap ){
    		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP); 
    	}
    	setQueryParams(query, params); 
    	query.setFirstResult((page-1)*pageSize); 
    	query.setMaxResults(pageSize); 
    	return query.list();
    }
  
    private void setQueryParams(Query query, List<Object> params) {  
        if (null == params) {  
            return;  
        }  
        for (int i = 0; i < params.size(); i++) {  
            query.setParameter(i, params.get(i));  
        }  
    }  

}
