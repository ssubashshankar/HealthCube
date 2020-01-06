package com.example.hCube.controller;


import java.util.HashMap;
import java.util.HashSet;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.example.hCube.model.DateRange;
import com.example.hCube.service.hcPatientsService;

@RestController
@CrossOrigin("*")
public class hCubeController {
	
	@Autowired
	hcPatientsService hcpatients;
	
	@PostMapping("patients_Count")
	public HashMap<String, Integer> totalPatientsCount(@RequestBody DateRange sorting)
	{
		return hcpatients.patientsTestCount(sorting);
	} 
	
	@PostMapping("blood_glucose_Count")
	public HashMap<String, Integer> bgCount(@RequestBody DateRange sorting)
	{
		return hcpatients.bloodGlucoseTestCount(sorting);
	}
	
	@PostMapping("cholesterol_Count")
	public HashMap<String, Integer> cholesterolCount(@RequestBody DateRange sorting)
	{
		return hcpatients.cholesterolTestResult(sorting);
	}
	
	@PostMapping("uric_acid_Count")
	public HashMap<String, Integer> uric_acidCount(@RequestBody DateRange sorting)
	{   
		return hcpatients.uric_acidTestResult(sorting);
	}
	@PostMapping("hemoglobin_Count")
	public HashMap<String, Integer> hemoglobinCount(@RequestBody DateRange sorting)
	{ 
		return hcpatients.hemoglobinCount(sorting);
	}
	
	@PostMapping("center_name")
	public HashSet<String> centerName(@RequestBody DateRange sorting)
	{
		return hcpatients.centerNameList(sorting);
	}
}
 