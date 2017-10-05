package com.example.demo;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.templateresolver.UrlTemplateResolver;

@Configuration
public class ApplicationConfiguration extends WebMvcConfigurerAdapter {

	@Autowired
	private SpringTemplateEngine templateEngine;

	@PostConstruct
	public void extension() {
		UrlTemplateResolver  resolver = new UrlTemplateResolver();
		//resolver.setPrefix("D:\\templates\\");
		resolver.setPrefix("http://www.digitalfactory.pk/tax-audit/");
	
		resolver.setSuffix(".html");
		resolver.setTemplateMode("LEGACYHTML5");
		resolver.setOrder(templateEngine.getTemplateResolvers().size());
		resolver.setCacheable(false);
		templateEngine.addTemplateResolver(resolver);
	}
}
