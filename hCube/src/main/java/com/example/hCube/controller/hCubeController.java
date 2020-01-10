package com.example.hCube.controller;


import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.apache.commons.io.IOUtils;
import org.apache.lucene.analysis.CharArrayMap.EntrySet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.hCube.model.DateRange;
import com.example.hCube.model.finalReport;
import com.example.hCube.service.EmailService;
import com.example.hCube.service.hcPatientsService;

@RestController
@CrossOrigin("*")
public class hCubeController {
	
	
	
	@Autowired
	hcPatientsService hcpatients;
	
	@Autowired
	EmailService emailService;
	
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
	
	@GetMapping("last_week")
	public DateRange lastweekdates()
	{
		DateRange dates= new DateRange();
		SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		Calendar c = Calendar.getInstance();
		c.setTime(date);
	    int i = c.get(Calendar.DAY_OF_WEEK) - c.getFirstDayOfWeek();
	    c.add(Calendar.DATE, -i - 7);
	    Date start = c.getTime();
	    dates.setStartdate(sdf.format(start));
	    c.add(Calendar.DATE, 6);
	    Date end = c.getTime();
	    dates.setEnddate(sdf.format(end));
	
	    return dates;
	}
	
//	@PostMapping("sendmail")
//	public String sendMail(@RequestBody String url) throws Exception
//	{
//		MultipartFile image = getImageContent();
//		emailService.sendmail(url,image, imageSource);
//		return "sending mail";
//	}
//	
//	private MultipartFile getImageContent() throws Exception {
//		InputStream imageIs = null;
//		byte[] imageByteArray = null;
//		MultipartFile multipartFile = null;
//		imageIs = this.getClass().getClassLoader().getResourceAsStream("templates/" + templateMailBodyImageVal);
//		imageByteArray = IOUtils.toByteArray(imageIs);
//		multipartFile = new MockMultipartFile(imageIs.getClass().getName(), imageIs.getClass().getName(), "image/jpeg",
//				imageByteArray);
//		imageSource = new ByteArrayResource(imageByteArray);
//		return multipartFile;
//	}
	
	public String sendingReport(finalReport reports,DateRange sorting)
	{
		try {
			emailService.sendmail(reports,sorting);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "sending the report";
	}
	
	
//	
//	@PostMapping("outlier_centers")
//	public List<String> getOutlierCenters(@RequestBody DateRange sorting)
//	{
//		return hcpatients.checkCenterName(sorting);
//	}
	

	@PostMapping("send_report")
	public ResponseEntity<String> sendReport(@RequestBody DateRange sorting)
	{
		hcpatients.checkCenterName(sorting).forEach(result->{
			sorting.setCenterName(result);
			Integer totalCount=hcpatients.testList(sorting).size();
			sendingReport(generateReport(sorting,result,totalCount),sorting);
		});
		return ResponseEntity.ok("done");
	}
	
	public finalReport generateReport(DateRange sorting,String centerName,Integer totalCount)
	{
		finalReport report= new finalReport();
		report.setCenterName(centerName);
		report.setTotalTest_Count(totalCount.toString());
		
		/* Patients count */
		for (Entry<String, Integer> value : hcpatients.patientsTestCount(sorting).entrySet()) {
			if (value.getKey().toString().equals("adult_male")) {
				report.setPatient_am(value.getValue().toString());
			}else if (value.getKey().toString().equals("adult_female")) {
				report.setPatient_af(value.getValue().toString());
			}else if (value.getKey().toString().equals("male_child")) {
				report.setPatient_mc(value.getValue().toString());
			}else if (value.getKey().toString().equals("female_child")) {
				report.setPatient_fc(value.getValue().toString());
			}else if (value.getKey().toString().equals("male_65")) {
				report.setPatient_m65(value.getValue().toString());
			}else if (value.getKey().toString().equals("female_65")) {
				report.setPatient_f65(value.getValue().toString());
			}
		}
		
		
		/* Blood Glucose */
		for (Entry<String, Integer> value  : hcpatients.bloodGlucoseTestCount(sorting).entrySet()) {
			if (value.getKey().toString().equals("total_blood_glucose_testcount")) {
				report.setBg_total(value.getValue().toString());
			}else if (value.getKey().toString().equals("result_50less")) {
				report.setBg_below(value.getValue().toString());
			}else if (value.getKey().toString().equals("result_400plus")) {
				report.setBg_above(value.getValue().toString());
			}
		}
	
		/* Cholesterol */
		for (Entry<String, Integer> value : hcpatients.cholesterolTestResult(sorting).entrySet()) {
			if (value.getKey().toString().equals("cholesterol_total_count")) {
				report.setChol_total(value.getValue().toString());
			}else if (value.getKey().toString().equals("adults_cholesterol")) {
				report.setChol_adult(value.getValue().toString());
			}else if (value.getKey().toString().equals("child_cholesterol")) {
				report.setChol_child(value.getValue().toString());
			}
		}
		
		/* Uric Acid */
		for (Entry<String, Integer> value : hcpatients.uric_acidTestResult(sorting).entrySet()) {
			if (value.getKey().toString().equals("uric_acid_total_count")) {
				report.setUric_total(value.getValue().toString());
			}else if (value.getKey().toString().equals("male_below_4")) {
				report.setUric_m_below(value.getValue().toString());
			}else if (value.getKey().toString().equals("male_above_8")) {
				report.setUric_m_above(value.getValue().toString());
			}else if (value.getKey().toString().equals("female_below_2")) {
				report.setUric_f_below(value.getValue().toString());
			}else if (value.getKey().toString().equals("female_above_7")) {
				report.setUric_f_above(value.getValue().toString());
			}
		}
		
		/* Hemoglobin */
		for (Entry<String, Integer> value : hcpatients.hemoglobinCount(sorting).entrySet()) {
			if (value.getKey().toString().equals("total_count")) {
				report.setHg_total(value.getValue().toString());
			}else if (value.getKey().toString().equals("adult_male")) {
				report.setHg_am_below(value.getValue().toString());
			}else if (value.getKey().toString().equals("adult_female")) {
				report.setHg_af_below(value.getValue().toString());
			}else if (value.getKey().toString().equals("child_below_5")) {
				report.setHg_c5_below(value.getValue().toString());
			}else if (value.getKey().toString().equals("childmale_15_to_18")) {
				report.setHg_cm_below(value.getValue().toString());
			}else if (value.getKey().toString().equals("child_6_to_14")) {
				report.setHg_c6_below(value.getValue().toString());
			}else if (value.getKey().toString().equals("childfemale_15_to_18")) {
				report.setHg_cf_below(value.getValue().toString());
			}else if (value.getKey().toString().equals("above_18")) {
				report.setHg_above(value.getValue().toString());
			}
		}
		
		return report;
	}

}
 