package com.companyabc.retail.config;

import java.util.ArrayList;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfig implements WebMvcConfigurer {

	public static final String TRANSACTION_TAG = "Transaction Controller";
	public static final String REWARD_TAG = "Reward Report Controller";
	public static final String CLIENT_TAG = "Client Controller";
	
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry
                .addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry
                .addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build()
				.pathMapping("/")
				.tags(new Tag(TRANSACTION_TAG, "Allows register, edit or delete all clients purchases"),
						new Tag(REWARD_TAG, "Use to get the reports of points of all users"),
						new Tag(CLIENT_TAG, "This service allows to create the Clients of ABCCompany"))
				.apiInfo(metaData());
	}
	
	private ApiInfo metaData() {
		Contact contact = new Contact("Rafael Negrette","https://github.com/rafanegrette", "rafanegrette@hotmail.com");
		return new ApiInfo("Retail Reward Program", 
				"Springboot Demo application for calculate the points rewards of clients of ABCompany",
				"1.0",
				"Term of Service: demo application",
				contact,
				"Apache License...",
				"https://www.apache.org/licenses/LICENSE-2.0",
				new ArrayList<>());
	}
}
