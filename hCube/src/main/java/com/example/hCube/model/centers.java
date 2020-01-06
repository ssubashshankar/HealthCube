package com.example.hCube.model;

import org.springframework.data.annotation.Id;


public class centers {
	
	@Id
	String id;
	String name;
	String centerCode;
	public String getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	
	public String getCenterCode() {
		return centerCode;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Override
	public String toString() {
		return "centers [id=" + id + ", name=" + name + ", centerCode=" + centerCode + "]";
	}
	public centers() {
		super();
		// TODO Auto-generated constructor stub
	}
	public centers(String id, String name) {
		this.id = id;
		this.name = name;
	}
	
	
	

}
