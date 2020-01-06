package com.example.hCube.model;

import java.util.Date;

import org.springframework.data.annotation.Id;

public class patients {
	@Id
	String _id;
	String gender;
	Integer age;
	String patientId;
	Date createTime;
	String centerId;
	public patients(String _id, String gender, Integer age, String patientId,Date createTime,String centerId) {
		this._id = _id;
		this.gender = gender;
		this.age = age;
		this.patientId = patientId;
		this.createTime = createTime;
		this.centerId = centerId;
	}
	
	public String getCenterId() {
		return centerId;
	}

	public patients() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public String getPatientId() {
		return patientId;
	}

	void setPatientId(String patientId) {
		this.patientId = patientId;
	}

	
	public Date getCreateTime() {
		return createTime;
	}


	@Override
	public String toString() {
		return "patients [_id=" + _id + ", gender=" + gender + ", age=" + age + ", patientId=" + patientId
				+ ", createTime=" + createTime + ", centerId=" + centerId + "]";
	}

	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	
}
