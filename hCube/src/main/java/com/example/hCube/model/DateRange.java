package com.example.hCube.model;

public class DateRange {
	String startdate;
	String enddate;
	String centerName;
	public DateRange(String startdate, String enddate,String centerName) {
		this.startdate = startdate;
		this.enddate = enddate;
		this.centerName =centerName;
	}
	public DateRange() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getCenterName() {
		return centerName;
	}
	public String getStartdate() {
		return startdate;
	}
	public String getEnddate() {
		return enddate;
	}
	
	
	public void setCenterName(String centerName) {
		this.centerName = centerName;
	}
	@Override
	public String toString() {
		return "DateRange [startdate=" + startdate + ", enddate=" + enddate + ", centerName=" + centerName + "]";
	}
	public void setStartdate(String startdate) {
		this.startdate = startdate;
	}
	public void setEnddate(String enddate) {
		this.enddate = enddate;
	}
	

}
