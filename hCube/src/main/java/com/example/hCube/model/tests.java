package com.example.hCube.model;


public class tests {

	String patientId;
	String name;
	String result;
	String startTime;
	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getResult() {
		return result;
	}

	
	@Override
	public String toString() {
		return "tests [patientId=" + patientId + ", name=" + name + ", result=" + result + ", startTime=" + startTime
				+ "]";
	}

	public tests() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public String getPatientId() {
		return patientId;
	}
	public String getName() {
		return name;
	}
	
	
}
