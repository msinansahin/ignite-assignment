package com.sprhib.init;

import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

/**
 *
 * 
 * @author mehmetsinan.sahin
 *
 */
@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    }
 
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }
 
    @Bean (name="localeResolver")
    public LocaleResolver getLocaleResolver () {
    	SessionLocaleResolver resolver = new SessionLocaleResolver();
    	resolver.setDefaultLocale(new Locale("en", "US"));
    	return resolver;
    }
    
    @Bean
    public LocaleChangeInterceptor getLocaleChangeInterceptor () {
    	LocaleChangeInterceptor interceptor = new LocaleChangeInterceptor();
    	interceptor.setParamName("lang");
    	return interceptor;
    }

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		super.addInterceptors(registry);
		registry.addInterceptor(getLocaleChangeInterceptor());
	}
 
}