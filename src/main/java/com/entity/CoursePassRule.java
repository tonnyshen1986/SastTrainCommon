package com.entity;

// Generated 2020-7-22 10:29:49 by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * SjCoursePassRule generated by hbm2java
 */
@Entity
@Table(name = "SJ_COURSE_PASS_RULE", catalog = "sast_train")
public class CoursePassRule implements java.io.Serializable {

	private Integer id;
	private Integer projectId;
	private Integer courseId;
	private Integer checkOrHour;
	private Integer test;
	private Date testStartTime;
	private Date testEndTime;
	private Integer evaluate;
	private Date evaluateStartTime;
	private Date evaluateEndTime;
	private Integer freeEnroll;
	private Date createTime;
	private Date updateTime;

	public CoursePassRule() {
	}

	public CoursePassRule(Integer projectId, Integer courseId,Integer checkOrHour, 
			Integer test,Date testStartTime,Date testEndTime,
			Integer evaluate,Date evaluateStartTime,Date evaluateEndTime,
			Integer freeEnroll,Date createTime, Date updateTime) {
		this.projectId = projectId;
		this.courseId = courseId;
		this.checkOrHour = checkOrHour;
		this.test = test;
		this.testStartTime = testStartTime;
		this.testEndTime = testEndTime;
		this.evaluate = evaluate;
		this.evaluateStartTime = evaluateStartTime;
		this.evaluateEndTime = evaluateEndTime;
		this.freeEnroll = freeEnroll;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "PROJECT_ID")
	public Integer getProjectId() {
		return this.projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	@Column(name = "COURSE_ID")
	public Integer getCourseId() {
		return this.courseId;
	}

	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}

	@Column(name = "CHECK_OR_HOUR")
	public Integer getCheckOrHour() {
		return this.checkOrHour;
	}

	public void setCheckOrHour(Integer checkOrHour) {
		this.checkOrHour = checkOrHour;
	}

	@Column(name = "TEST")
	public Integer getTest() {
		return this.test;
	}

	public void setTest(Integer test) {
		this.test = test;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "TEST_START_TIME", length = 19)
	public Date getTestStartTime() {
		return testStartTime;
	}

	public void setTestStartTime(Date testStartTime) {
		this.testStartTime = testStartTime;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "TEST_END_TIME", length = 19)
	public Date getTestEndTime() {
		return testEndTime;
	}

	public void setTestEndTime(Date testEndTime) {
		this.testEndTime = testEndTime;
	}

	@Column(name = "EVALUATE")
	public Integer getEvaluate() {
		return this.evaluate;
	}

	public void setEvaluate(Integer evaluate) {
		this.evaluate = evaluate;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EVALUATE_START_TIME", length = 19)
	public Date getEvaluateStartTime() {
		return evaluateStartTime;
	}

	public void setEvaluateStartTime(Date evaluateStartTime) {
		this.evaluateStartTime = evaluateStartTime;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EVALUATE_END_TIME", length = 19)
	public Date getEvaluateEndTime() {
		return evaluateEndTime;
	}

	public void setEvaluateEndTime(Date evaluateEndTime) {
		this.evaluateEndTime = evaluateEndTime;
	}
	
	@Column(name = "FREE_ENROLL")
	public Integer getFreeEnroll() {
		return freeEnroll;
	}

	public void setFreeEnroll(Integer freeEnroll) {
		this.freeEnroll = freeEnroll;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_TIME", length = 19)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATE_TIME", length = 19)
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
