/**
 * 
 */
package com.sivalabs.jcart;

import static org.junit.Assert.assertTrue;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.sivalabs.jcart.common.services.EmailService;
/**
 * @author Siva
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JCartCoreApplication.class)
public class JCartCoreApplicationTest
{
	@Autowired DataSource dataSource;
	@Autowired EmailService emailService;
	
	@Test
	public void testDummy() throws SQLException
	{
		String schema = dataSource.getConnection().getCatalog();
		assertTrue("jcart".equalsIgnoreCase(schema));
	}
	
	@Test
	@Ignore
	public void testSendEmail()
	{
		emailService.sendEmail("admin@gmail.com", "JCart - Test Mail", "This is a test email from JCart");
	}
	
}
