/**
 * 
 */
package com.sivalabs.jcart;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.sivalabs.jcart.admin.web.controllers.HomeController;

/**
 * @author Siva
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JCartAdminApplication.class)
public class JCartAdminApplicationTest
{
    @Autowired
    private HomeController homeController;

    @Test
    public void contextLoads()
    {
        assertThat(homeController).isNotNull();
    }

}
