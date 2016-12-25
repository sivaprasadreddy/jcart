/**
 * 
 */
package com.sivalabs.jcart.common.services;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSendException;
import org.springframework.test.context.junit4.SpringRunner;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetupTest;

/**
 * @author rajakolli
 *
 */
@RunWith(SpringRunner.class)
public class EmailServiceTest
{

    @MockBean
    private EmailService emailService;

    private GreenMail testSmtp;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception
    {
        testSmtp = new GreenMail(ServerSetupTest.SMTP);
        testSmtp.start();
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception
    {
        testSmtp.stop();
    }

    /**
     * Test method for
     * {@link com.sivalabs.jcart.common.services.EmailService#sendEmail(java.lang.String, java.lang.String, java.lang.String)}.
     */
    @Test
    public void testSendEmail()
    {
        doNothing().when(emailService).sendEmail(anyString(), anyString(), anyString());
        emailService.sendEmail("rajadileepkolli@gmail.com", "JCart - Test Mail",
                "This is a test email from JCart");
        assertNotNull(emailService);
    }
    
    @Test(expected=MailException.class)
    public void testSendEmailWithException()
    {
        doThrow(new MailSendException("JUNIT EMAIL")).when(emailService).sendEmail(anyString(), anyString(), any());
        emailService.sendEmail("rajadileepkolli@gmail.com", "JCart - Test Mail",
                "This is a test email from JCart");
        assertNotNull(emailService);
    }

}
