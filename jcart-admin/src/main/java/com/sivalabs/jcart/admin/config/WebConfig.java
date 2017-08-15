package com.sivalabs.jcart.admin.config;

import com.sivalabs.jcart.admin.web.security.PostAuthorizationFilter;
import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.coyote.http11.AbstractHttp11Protocol;
import org.apache.tomcat.util.descriptor.web.SecurityCollection;
import org.apache.tomcat.util.descriptor.web.SecurityConstraint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;

import javax.servlet.Filter;

/**
 * @author Siva
 *
 */
@Configuration
public class WebConfig implements WebMvcConfigurer
{   
	@Value("${server.port:9443}")
	private int serverPort;
	
	@Autowired 
	private PostAuthorizationFilter postAuthorizationFilter;
	
	@Autowired
    private MessageSource messageSource;

    @Override
    public Validator getValidator() {
        LocalValidatorFactoryBean factory = new LocalValidatorFactoryBean();
        factory.setValidationMessageSource(messageSource);
        return factory;
    }
    	
	//http://stackoverflow.com/questions/25957879/filter-order-in-spring-boot
	@Bean
	public FilterRegistrationBean securityFilterChain(@Qualifier(AbstractSecurityWebApplicationInitializer.DEFAULT_FILTER_NAME) Filter securityFilter) {
	    FilterRegistrationBean registration = new FilterRegistrationBean(securityFilter);
	    registration.setOrder(Integer.MAX_VALUE - 1);
	    registration.setName(AbstractSecurityWebApplicationInitializer.DEFAULT_FILTER_NAME);
	    return registration;
	}

	@Bean
	public FilterRegistrationBean PostAuthorizationFilterRegistrationBean() {
	    FilterRegistrationBean registrationBean = new FilterRegistrationBean();
	    registrationBean.setFilter(postAuthorizationFilter);
	    registrationBean.setOrder(Integer.MAX_VALUE);
	    return registrationBean;
	}
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry)
	{
        registry.addViewController("/login").setViewName("public/login");
		registry.addRedirectViewController("/", "/home");
		
	}
	
	@Bean
	public SpringSecurityDialect securityDialect() {
	    return new SpringSecurityDialect();
	}

	@Bean
	public ServletWebServerFactory servletContainer() {
		TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
			@Override
			protected void postProcessContext(Context context) {
				SecurityConstraint securityConstraint = new SecurityConstraint();
				securityConstraint.setUserConstraint("CONFIDENTIAL");
				SecurityCollection collection = new SecurityCollection();
				collection.addPattern("/*");
				securityConstraint.addCollection(collection);
				context.addConstraint(securityConstraint);
			}
		};

		tomcat.addConnectorCustomizers(new TomcatConnectorCustomizer() {

			@Override
			public void customize(Connector connector) {
				((AbstractHttp11Protocol<?>) connector.getProtocolHandler()).setMaxSwallowSize(-1);
			}
		});

		tomcat.addAdditionalTomcatConnectors(initiateHttpConnector());
		return tomcat;
	}

	private Connector initiateHttpConnector() {
		Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
		connector.setScheme("http");
		connector.setPort(9090);
		connector.setSecure(false);
		connector.setRedirectPort(serverPort);

		//Tomcat maxSwallowSize sets to 2MB by default.
		//To set the maxSwallowSize property of Tomcat https://tomcat.apache.org/tomcat-8.0-doc/config/http.html
		//http://stackoverflow.com/questions/35748022/multipart-file-maximum-size-exception-spring-boot-embbeded-tomcat

		((AbstractHttp11Protocol<?>) connector.getProtocolHandler()).setMaxSwallowSize(11534336);
		return connector;
	}
}
