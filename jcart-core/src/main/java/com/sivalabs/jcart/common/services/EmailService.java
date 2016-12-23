package com.sivalabs.jcart.common.services;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.sivalabs.jcart.JCartException;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Siva
 *
 */
@Slf4j
@Component
public class EmailService
{
    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${support.email}")
    private String supportEmail;

    public void sendEmail(String to, String subject, String content)
    {
        try
        {
            // Prepare message using a Spring helper
            final MimeMessage mimeMessage = this.javaMailSender.createMimeMessage();
            final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8");
            message.setSubject(subject);
            message.setFrom(supportEmail);
            message.setTo(to);
            message.setText(content, true /* isHtml */);

            javaMailSender.send(message.getMimeMessage());
        }
        catch (MailException | MessagingException e)
        {
            log.error(e.getMessage(), e);
            throw new JCartException("Unable to send email");
        }
    }

}
