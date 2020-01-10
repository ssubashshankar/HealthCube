package com.example.hCube.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.example.hCube.model.DateRange;
import com.example.hCube.model.finalReport;


@Service
public class EmailService {
	
	@Autowired
	private JavaMailSender sender;

	@Autowired
	private TemplateEngine templateEngine;

	public void sendmail(finalReport generatedReports, DateRange dates) throws AddressException, MessagingException, IOException, ParseException {
		
		String templateName = "email/myTemplate";
		String Subject ="HealthCubed Weekly Report { " + dates.getStartdate() +" to " +dates.getEnddate() + " }"; 
		String[] tomail = {"subash.shankar@healthcubed.com"}; //,"nishant.gupta@healthcubed.com","nishantg04@gmail.com"
		Context context = new Context();
		context.setVariable("content", generatedReports);
		String body = templateEngine.process(templateName, context);
		
		
		MimeMessage mail = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mail, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
				StandardCharsets.UTF_8.name());
		helper.setTo(tomail);
		helper.setSubject(Subject); 
		helper.setText(body, true);
		//helper.addInline(image.getName(), image, image.getContentType());
		//helper.addInline("Subash Shankar",imageSource, image.getContentType());
		helper.setFrom("healthcubedreport@gmail.com");
		sender.send(mail);
		   
		   
		   
		}
	
}
