package com.bidkoi.auctionkoi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.TemplateEngine;

import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@Configuration
public class ThymeleafConfig {

    @Bean
    public ClassLoaderTemplateResolver templateResolver() {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("templates/"); // Thư mục chứa các file html
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("HTML"); // Định dạng file
        return templateResolver;
    }


    @Bean
    public TemplateEngine templateEngine() {
        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver());
        return templateEngine;
    }

}
