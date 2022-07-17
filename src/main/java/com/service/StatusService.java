package com.service;

import java.util.Arrays;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dao.BaseDao;
import com.entity.Project;

@Service("statusService")
public class StatusService {

	private static Logger log = LogManager.getLogger(StatusService.class);

	@Autowired
	public BaseDao baseDao;
	
	/**
	 * 更新所有项目的状态 
	 * 
	 * 编辑中 -> 审核中（手动触发）
	 * 审核中 -> 待报名（手动触发）
	 * 待报名 -> 报名中
	 * 报名中 -> 待培训
	 * 待培训 -> 培训中
	 * 培训中 -> 培训结束
	 * 培训结束 -> 已完结（手动触发）
	 */
	@Transactional
	public void updateProjectStatus(){
		// 待报名 -> 报名中
		String hql = "update Project set projectStatus=4 "
				+ " where projectStatus=3 and ? > signStartTime ";
		this.baseDao.updateByHql(hql, Arrays.asList( new Date() ));
		
		// 报名中 -> 待培训
		hql = "update Project set projectStatus=5 "
				+ " where projectStatus=4 and ? > signEndTime ";
		this.baseDao.updateByHql(hql, Arrays.asList( new Date() ));
		
		// 待培训 -> 培训中
		hql = "update Project set projectStatus=6 "
				+ " where projectStatus=5 and ? > trainStartTime ";
		this.baseDao.updateByHql(hql, Arrays.asList( new Date() ));
		
		// 培训中 -> 培训结束
		hql = "update Project set projectStatus=7 "
				+ " where projectStatus=6 and ? > trainEndTime ";
		this.baseDao.updateByHql(hql, Arrays.asList( new Date() ));
	}
	
	/**
	 * 更新所有课程的状态
	 * 
	 * 待报名 -> 报名中
	 * 报名中 -> 待授课
	 * 待授课 -> 授课中
	 * 授课中 -> 授课结束
	 * 
	 */
	@Transactional
	public void updateCourseStatus(Integer projectId){
		
		Project project = (Project) this.baseDao.queryById(projectId, Project.class);
		Integer projectStatus = project.getProjectStatus();
		if ( projectStatus == 1 || projectStatus == 2 || projectStatus == 0 ){
			return;
		}
		
		// 待报名 -> 报名中
		String hql = "update Course set courseStatus=2 "
				+ " where 1=1"
				+ " and projectId=?"
				+ " and courseStatus=1 and ? > signStartTime ";
		this.baseDao.updateByHql(hql, Arrays.asList(projectId, new Date() ));
		
		// 报名中 -> 待授课
		hql = "update Course set courseStatus=3 "
				+ " where 1=1"
				+ " and projectId=?"
				+ " and courseStatus=2 and ? > signEndTime ";
		this.baseDao.updateByHql(hql, Arrays.asList(projectId, new Date() ));
		
		// 待授课 -> 授课中
		hql = "update Course set courseStatus=4 "
				+ " where 1=1"
				+ " and projectId=?"
				+ " and courseStatus=3 and ? > startTime ";
		this.baseDao.updateByHql(hql, Arrays.asList(projectId, new Date() ));
		
		// 授课中 -> 授课结束
		hql = "update Course set courseStatus=5 "
				+ " where 1=1"
				+ " and projectId=?"
				+ " and courseStatus=4 and ? > endTime ";
		this.baseDao.updateByHql(hql, Arrays.asList(projectId, new Date() ));

	}
}
