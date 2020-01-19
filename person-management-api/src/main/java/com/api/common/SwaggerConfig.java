package com.api.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
@Import(BeanValidatorPluginsConfiguration.class)

public class SwaggerConfig {                                    
    @Bean
    public Docket api() { 
        return new Docket(DocumentationType.SWAGGER_2)
          .select()                                  
          .apis(RequestHandlerSelectors.any())
          .paths(PathSelectors.any())
          .build();                                           
    }
    private ApiInfo apinfo() {
        return new ApiInfoBuilder()
                .title("Person Management API")
                .description("Rest API to CURD Operations ")
                .termsOfServiceUrl("Some Terms of Services URL")
                .version("1.0.0")
                .license("Some License Info")
                .licenseUrl("Some License URL")
                .contact(new springfox.documentation.service.Contact("Niraj Sonawane", "https://nirajsonawane.github.io/","Niraj.Sonawane@gmail.com"))
                .build();
    }
} 