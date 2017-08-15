package com.sivalabs.jcart.common.services;

import com.sivalabs.jcart.JCartException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


/**
 * @author Siva
 *
 */
@Component
@Slf4j
public class EmailService 
{
	private JavaMailSender javaMailSender;

	@Autowired
	public EmailService(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	public void sendEmail(String fromEmail, String to, String subject, String content)
	{
        try
		{
            final MimeMessage mimeMessage = this.javaMailSender.createMimeMessage();
            final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
            message.setSubject(subject);
            message.setFrom(fromEmail);
            message.setTo(to);
            message.setText(content, true);
            
			javaMailSender.send(message.getMimeMessage());
		} 
        catch (MailException | MessagingException e)
		{
        	log.error(e.getMessage(), e);
			throw new JCartException("Unable to send email");
		}
	}

}
