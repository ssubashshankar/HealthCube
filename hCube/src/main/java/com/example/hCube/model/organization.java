package com.example.hCube.model;

import java.util.List;

public class organization {
	List<centers> centers;
	String _id;
	public List<centers> getCenters() {
		return centers;
	}
	public String get_id() {
		return _id;
	}
	@Override
	public String toString() {
		return "organization [centers=" + centers + ", _id=" + _id + "]";
	}
	public organization() {
		super();
		// TODO Auto-generated constructor stub
	}
	

}
