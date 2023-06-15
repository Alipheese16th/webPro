package com.kist.portal.sysconf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.kist.portal.sysconf.interceptor.Interceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	
	@Autowired
	Interceptor interceptor;
	
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
//		registry
//			.addViewController("/")
//			.setViewName("forward:/index.html");
	}
	
	@Override
	public void configurePathMatch(PathMatchConfigurer configurer) {
//		configurer.setUseSuffixPatternMatch(false);
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/api/v1/**");
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
//		registry
//			.addResourceHandler("/resources/**")
//			.addResourceLocations("classpath:/WEB-INF/resources/");
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		WebMvcConfigurer.super.addInterceptors(registry);
		registry
		     .addInterceptor(interceptor)
		     .addPathPatterns("/api/v1/**")
		     .excludePathPatterns("/api/v1/common/whoami"
		    		 			, "/api/v1/common/test"
		    		 			, "/api/v1/comm/mlang"
		    		 			, "/api/v1/comm/mlang/i18n"
		    		 			, "/api/v1/public/**"
		    		 			, "/api/v1/common/sms-check"
		    		 			, "/api/v1/comm/comm-cd");
	}
	
//	@Override
//    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
//        converters.add(escapingConverter());
//
//    }
//
//    @Bean
//    public HttpMessageConverter escapingConverter() {
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.getFactory().setCharacterEscapes(new HtmlCharacterEscapes());
//
//        MappingJackson2HttpMessageConverter escapingConverter =
//                new MappingJackson2HttpMessageConverter();
//        escapingConverter.setObjectMapper(objectMapper);
//
//        return escapingConverter;
//    }
}
