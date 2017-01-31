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

import com.sivalabs.jcart.site.web.controllers.HomeController;

/**
 * @author Siva
 * @author rajakolli
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = JCartSiteApplication.class)
public class JCartSiteApplicationTest
{
    @Autowired
    HomeController homeController;

    @Test
    public void contextLoads()
    {
        assertThat(homeController).isNotNull();
    }

}
