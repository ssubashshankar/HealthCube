package com.example.hCube.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.hCube.model.DateRange;
import com.example.hCube.model.ezdx_visits;
import com.example.hCube.model.patients;
import com.example.hCube.model.tests;
import com.example.hCube.repository.ezdx_visitsRepo;
import com.example.hCube.repository.organizationRepo;
import com.example.hCube.repository.patientsRepo;

@Service
public class hcPatientsService {
	
	@Autowired
	patientsRepo patientsrepo;
	
	@Autowired
	ezdx_visitsRepo ezdxrepo;
	
	@Autowired
	organizationRepo organizationrepo;
	
	public List<patients> patientsDetail() 
	{
		return patientsrepo.findAll();
	}
	
	
	public HashMap<String, Integer> patientsTestCount(DateRange dates)
	{
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("adult_male", 0);
		map.put("adult_female", 0);
		map.put("male_child", 0);
		map.put("female_child", 0);
		map.put("male_65", 0);
		map.put("female_65", 0);
		SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
		if (!dates.getCenterName().equals("") ) {
			for ( Entry<String, String> iterable_elemen : patientsCenterName(dates).entrySet()) {
				if (iterable_elemen.getKey().toString().equals(dates.getCenterName().toString())) {
					patientsDetail().forEach(result->{
							try {
								if ((result.getCreateTime()).after(sdf.parse(dates.getStartdate())) && (result.getCreateTime()).before(sdf.parse(dates.getEnddate())) && (result.getCenterId().toString().equals(iterable_elemen.getValue().toString()))) {	
									if( result.getAge() >= 18 && result.getGender().equals("MALE"))
									{
										map.put("adult_male", map.get("adult_male")+1);
										if (result.getAge() > 65)
										{
											map.put("male_65", map.get("male_65")+1);
										}
									}else if (result.getAge() >= 18 && result.getGender().equals("FEMALE")) 
									{	
										map.put("adult_female", map.get("adult_female")+1);
										if (result.getAge() > 65)
										{
											map.put("female_65", map.get("female_65")+1);
										}
									}else if (result.getAge() < 18 && result.getGender().equals("FEMALE")) {
										map.put("female_child", map.get("female_child")+1);
									}else if (result.getAge() < 18 && result.getGender().equals("MALE")) {
										map.put("male_child", map.get("male_child")+1);
									}	
								}
							} catch (ParseException e) {
							
								e.printStackTrace();
							}
					});
				}
			}
		}else {
			patientsDetail().forEach(result->{
				try {
					if ((result.getCreateTime()).after(sdf.parse(dates.getStartdate())) && (result.getCreateTime()).before(sdf.parse(dates.getEnddate()))) {	
						if( result.getAge() >= 18 && result.getGender().equals("MALE"))
						{
							map.put("adult_male", map.get("adult_male")+1);
							if (result.getAge() > 65)
							{
								map.put("male_65", map.get("male_65")+1);
							}
						}else if (result.getAge() >= 18 && result.getGender().equals("FEMALE")) 
						{	
							map.put("adult_female", map.get("adult_female")+1);
							if (result.getAge() > 65)
							{
								map.put("female_65", map.get("female_65")+1);
							}
						}else if (result.getAge() < 18 && result.getGender().equals("FEMALE")) {
							map.put("female_child", map.get("female_child")+1);
						}else if (result.getAge() < 18 && result.getGender().equals("MALE")) {
							map.put("male_child", map.get("male_child")+1);
						}	
					}
				} catch (ParseException e) {
				
					e.printStackTrace();
				}
		});
		}
		return map;
	}
	 
	
	
	public List<tests> testList(DateRange dates)
	{
		List<ezdx_visits>	ezdxpatientsList = ezdxrepo.findAll();
		List<tests> testlist = new ArrayList<tests>();
		SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
			ezdxpatientsList.forEach(result->{
				try {
					if (sdf.parse(result.getCreateTime()).after(sdf.parse(dates.getStartdate())) && sdf.parse(result.getCreateTime()).before(sdf.parse(dates.getEnddate()))) {
						if (result.getCenterName() != null ) {
							if (dates.getCenterName().equals("") ) {
								if (result.getTests() != null) {
									if (result.getTests().size() > 1) {
										result.getTests().forEach(inner->{
											testlist.add(inner);
										});
									}else {
										testlist.addAll(result.getTests());
									}
								}
							}else if (result.getCenterName().toString().equals(dates.getCenterName().toString())) {
								if (result.getTests() != null) {
									if (result.getTests().size() > 1) {
										result.getTests().forEach(inner->{
											testlist.add(inner);
										});
									}else {
										testlist.addAll(result.getTests());
									}
								}
							}
						}	
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}			
			});
		return testlist;
	}
	public HashMap<String, Integer> bloodGlucoseTestCount(DateRange dates)
	{
		
		HashMap<String, Integer> bg= new HashMap<String, Integer>();
		bg.put("result_50less", 0);
		bg.put("result_400plus", 0);
		testList(dates).forEach(result->{
			if (result.getName().toString().equals("BLOOD_GLUCOSE")) {
				double testResult = Double.parseDouble(result.getResult());
				if (testResult < 50) {
					bg.put("result_50less", bg.get("result_50less")+1);	
				}else if(testResult > 400)
				{
					bg.put("result_400plus", bg.get("result_400plus")+1);
				}
			}
		});
		
		return bg;
	}

	public HashMap<String, Integer> cholesterolTestResult(DateRange dates)
	{
		HashMap<String, Integer> cholesterolTest= new HashMap<String, Integer>();
		cholesterolTest.put("adults_cholesterol", 0);
		cholesterolTest.put("child_cholesterol", 0);
		List<String> adult_List = new ArrayList<String>();
		List<String> child_List = new ArrayList<String>();
		patientsDetail().forEach(result->{
			if(result.getAge() >= 18)
			{
				adult_List.add(result.get_id());
			}else {
				child_List.add(result.get_id());
			}
		});
		
		
		
		testList(dates).stream().forEach(result->{
			if (child_List.contains(result.getPatientId())) {
				if (result.getName().toString().equals("CHOLESTEROL")) {
					double testResult = Double.parseDouble(result.getResult());
					if (testResult > 200) {
						cholesterolTest.put("child_cholesterol", cholesterolTest.get("child_cholesterol")+1);
					}
				}
			}else if(adult_List.contains(result.getPatientId()))
			{
				if (result.getName().toString().equals("CHOLESTEROL")) {
					double testResult = Double.parseDouble(result.getResult());
					if (testResult > 240) {
						cholesterolTest.put("adults_cholesterol", cholesterolTest.get("adults_cholesterol")+1);
					}
				}
			}
		});
		return cholesterolTest;
	}
	
	public HashMap<String, Integer> uric_acidTestResult(DateRange dates)
	{
		
		HashMap<String, Integer> uric_Acid = new HashMap<String, Integer>();
		uric_Acid.put("male_below_4", 0);
		uric_Acid.put("male_above_8", 0);
		uric_Acid.put("female_below_2", 0);
		uric_Acid.put("female_above_7", 0);
	
		List<String> males_List = new ArrayList<String>();
		List<String> females_List = new ArrayList<String>();
		patientsDetail().forEach(result->{
			if(result.getGender().equals("MALE"))
			{
				males_List.add(result.get_id());
			}else if(result.getGender().equals("FEMALE")) {
				females_List.add(result.get_id());
			}
		});
		
		testList(dates).stream().forEach(result->{
			if (males_List.contains(result.getPatientId())) { 
				if (result.getName().toString().equals("URIC_ACID")) {
					if(result.getResult() != null)
					{
						double testResult = Double.parseDouble(result.getResult());
						if (testResult < 4) {
							uric_Acid.put("male_below_4", uric_Acid.get("male_below_4")+1);
						}else if (testResult > 8.5) {
							uric_Acid.put("male_above_8", uric_Acid.get("male_above_8")+1);
						}
					}
				}
			}else if(females_List.contains(result.getPatientId()))
			{
				if (result.getName().toString().equals("URIC_ACID")) {
					double testResult = Double.parseDouble(result.getResult());
					if (testResult < 2.7) {
						uric_Acid.put("female_below_2", uric_Acid.get("female_below_2")+1);
					}else if (testResult > 7.3) {
						uric_Acid.put("female_above_7", uric_Acid.get("female_above_7")+1);
					}
				}
			}
		});
		return uric_Acid;
	}
	
	
	public HashMap<String, Integer> hemoglobinCount(DateRange dates)
	{
		List<String> adult_female= new ArrayList<String>();
		List<String> adult_male = new ArrayList<String>();
		List<String> child_below_5 = new ArrayList<String>();
		List<String> child_6_to_14 = new ArrayList<String>();
		List<String> childmale_15_to_18 = new ArrayList<String>();
		List<String> childfemale_15_to_18 = new ArrayList<String>();
		
		patientsDetail().forEach(result->{
			if( result.getAge() >= 18 && result.getGender().toUpperCase().equals("MALE"))
			{
				adult_male.add(result.get_id());
			}else if (result.getAge() >= 18 && result.getGender().toUpperCase().equals("FEMALE")) 
			{	
				adult_female.add(result.get_id());
			}else if (result.getAge() <= 5) {
				child_below_5.add(result.get_id());
			}else if (result.getAge() <= 14 && result.getAge() >=6 ) {
				child_6_to_14.add(result.get_id());
			}else if (result.getAge() <= 18 && result.getAge() >=15 && result.getGender().toUpperCase().equals("MALE") ) {
				childmale_15_to_18.add(result.get_id());
			}else if (result.getAge() <= 18 && result.getAge() >=15 && result.getGender().toUpperCase().equals("FEMALE")) {
				childfemale_15_to_18.add(result.get_id());
			}
		});
		
		
		HashMap<String, Integer> hemoglobin = new HashMap<String, Integer>();
		hemoglobin.put("adult_male", 0);
		hemoglobin.put("adult_female", 0);
		hemoglobin.put("child_below_5",0 );
		hemoglobin.put("childmale_15_to_18", 0);
		hemoglobin.put("child_6_to_14", 0);
		hemoglobin.put("childfemale_15_to_18", 0);
		hemoglobin.put("above_18", 0);
		testList(dates).forEach(result->{
			if (adult_male.contains(result.getPatientId())) {
				if (result.getName().toString().equals("HEMOGLOBIN")) {
					double testResult = Double.parseDouble(result.getResult());
					if (testResult < 13.5) {
						hemoglobin.put("adult_male", hemoglobin.get("adult_male")+1);
					}
				}
			}else if (adult_female.contains(result.getPatientId())) {
				if (result.getName().toString().equals("HEMOGLOBIN")) {
					double testResult = Double.parseDouble(result.getResult());
					if (testResult < 12) {
						hemoglobin.put("adult_female", hemoglobin.get("adult_female")+1);
					}
				}
			}else if (child_below_5.contains(result.getPatientId())) {
				if (result.getName().toString().equals("HEMOGLOBIN")) {
					double testResult = Double.parseDouble(result.getResult());
					if (testResult < 11) {
						hemoglobin.put("child_below_5", hemoglobin.get("child_below_5")+1);
					}
				}
			}else if (childmale_15_to_18.contains(result.getPatientId())) {
				if (result.getName().toString().equals("HEMOGLOBIN")) {
					double testResult = Double.parseDouble(result.getResult());
					if (testResult < 13.5) {
						hemoglobin.put("childmale_15_to_18", hemoglobin.get("childmale_15_to_18")+1);
					}
				}
			}else if (child_6_to_14.contains(result.getPatientId())) {
				if (result.getName().toString().equals("HEMOGLOBIN")) {
					double testResult = Double.parseDouble(result.getResult());
					if (testResult < 12) {
						hemoglobin.put("child_6_to_14", hemoglobin.get("child_6_to_14")+1);
					}
				}
			}else if (childfemale_15_to_18.contains(result.getPatientId())) {
				if (result.getName().toString().equals("HEMOGLOBIN")) {
					double testResult = Double.parseDouble(result.getResult());
					if (testResult < 12) {
						hemoglobin.put("childfemale_15_to_18", hemoglobin.get("childfemale_15_to_18")+1);
					}
				}
			}else {
				if (result.getName().toString().equals("HEMOGLOBIN")) {
					double testResult = Double.parseDouble(result.getResult());
					if (testResult > 18 ) {
						hemoglobin.put("above_18", hemoglobin.get("above_18")+1);
					}
				}
			}
		});
		return hemoglobin;
	}
	
	
	public HashSet<String> centerNameList(DateRange dates)
	{
		List<ezdx_visits>	ezdxpatientsList = ezdxrepo.findAll();
		HashSet<String> centerlist = new HashSet<String>();
		SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
		ezdxpatientsList.forEach(result->{
			if (dates.getStartdate() == null && dates.getStartdate() == null && dates.getCenterName() == null) {
				if(result.getCenterId() != null) {
					centerlist.add(result.getCenterName());
					}
			}else {
				try {
					if (sdf.parse(result.getCreateTime()).after(sdf.parse(dates.getStartdate())) && sdf.parse(result.getCreateTime()).before(sdf.parse(dates.getEnddate())) ) {
						if(result.getCenterId() != null) {
							centerlist.add(result.getCenterName());
							}
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		});
		return centerlist;	
	}
	
	public HashMap<String, String> patientsCenterName(DateRange dates)
	{
		List<ezdx_visits>	ezdxpatientsList = ezdxrepo.findAll();
		HashMap<String, String> centerlist= new HashMap<String, String>();
		SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
		ezdxpatientsList.forEach(result->{
				try {
					if (sdf.parse(result.getCreateTime()).after(sdf.parse(dates.getStartdate())) && sdf.parse(result.getCreateTime()).before(sdf.parse(dates.getEnddate())) ) {
						if(result.getCenterId() != null && result.getCenterName() != null) {
							centerlist.put(result.getCenterName(), result.getCenterId());
						}
					}
				} catch (ParseException e) { 
					e.printStackTrace();
				}
		});
		return centerlist;
	}
	
	
}
