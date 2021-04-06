package com.slam.service;

import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class EmailService {

	public boolean sendEmail(String subject,String message,String to)
	{
		boolean f=false;
		String from="gur*****@gmail.com";
		
		//variable for gmail
		String host="smtp.gmail.com";
		
		//get the system properties
		Properties properties=System.getProperties();
		System.out.println("Properties" + properties);
		
		//setting imp info to prprtz objct
		//host set
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port",465);
		properties.put("mail.smtp.ssl.enable","true");
		properties.put("mail.smtp.auth","true");
		
		//STEP 1 to get the session object
		Session session=Session.getInstance(properties,new Authenticator() {
		@Override
		protected PasswordAuthentication getPasswordAuthentication()
		{
			return new PasswordAuthentication("guram****@gmail.com", "******");
		}
		
		});
		session.setDebug(true);
		
		//Step 2 compose the message text,multimedia
		MimeMessage m=new MimeMessage(session);
		try 
		{
			//from email
			m.setFrom(from);
			//addrecipeint to mesg
			m.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			//adding subject to mesg
			m.setSubject(subject);
			//adding text to mesg
		//	m.setText(message); 
			m.setContent(message,"text/html");
			
			
			//send
			
			//Step 3 send mesg usin transport class
			Transport.send(m);
			System.out.println("SENT SUCCESFULLY");
			f=true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return f;
	}
}
