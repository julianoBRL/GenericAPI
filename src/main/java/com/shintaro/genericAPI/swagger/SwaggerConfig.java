package com.shintaro.genericAPI.swagger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.BasicAuth;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {
	
    @Bean
    public Docket api() { 
	    return new Docket(DocumentationType.SWAGGER_2)
	      .select()
	      .apis(RequestHandlerSelectors.basePackage("com.shintaro.genericAPI.controllers"))
	      .paths(PathSelectors.any())
	      .build()
	      .securitySchemes(basicScheme())
	      .apiInfo(apiInfo());
    }
    
    private ApiInfo apiInfo() {
        return new ApiInfo(
          "Generic API", 
          "API version of 2.7.1-SNAPSHOT.", 
          "2.7.1", 
          "https://github.com/ShintaroBRL/GenericAPI/blob/main/LICENSE",
          new Contact("ShintaroBRL", "https://github.com/ShintaroBRL", "juliano0dev@gamil.com"), 
          "License of API", "https://github.com/ShintaroBRL/GenericAPI/blob/main/LICENSE", Collections.emptyList());
    }
    
    private List<SecurityScheme> basicScheme() {
        List<SecurityScheme> schemeList = new ArrayList<>();
        schemeList.add(new BasicAuth("basicAuth"));
        return schemeList;
    }
    
}