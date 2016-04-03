package com.modesteam.urutau.model.system;

import javax.enterprise.context.Dependent;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

import com.modesteam.urutau.model.UrutaUser;

@Dependent
public class EmailManager {
	
	/**
	 * Sends an email
	 * 
	 * @param subject What refers this email?
	 * @param receiver For whom?
	 * @param message Content of email
	 * 
	 * @throws EmailException case of bad connection
	 */
	public void newEmail(String subject, UrutaUser receiver, String message)
			throws EmailException{
		Email email = new SimpleEmail();
		email.setSubject(subject);
		email.addTo(receiver.getEmail());
		email.setMsg(message);
		email.setSSLOnConnect(true);
		email.setHostName("smtp.gmail.com");
		email.setSslSmtpPort("465");
		email.setAuthenticator(new DefaultAuthenticator("phwener@gmail.com", "XXX"));
		email.setFrom("phwener@gmail.com");
		email.send();
	}
}
