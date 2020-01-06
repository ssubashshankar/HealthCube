package com.example.hCube.model;

import java.util.List;

public class ezdx_visits {
	String patientId;
	List<tests> tests;
	String createTime;
	String centerName;
	String centerId;
	

	public String getCenterId() {
		return centerId;
	}

	public String getCenterName() {
		return centerName;
	}

	public List<tests> getTests() {
		return tests;
	}

	public String getPatientId() {
		return patientId;
	}

	



	@Override
	public String toString() {
		return "ezdx_visits [patientId=" + patientId + ", tests=" + tests + ", createTime=" + createTime
				+ ", centerName=" + centerName + ", centerId=" + centerId + "]";
	}

	public ezdx_visits() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getCreateTime() {
		return createTime;
	}
	

}
