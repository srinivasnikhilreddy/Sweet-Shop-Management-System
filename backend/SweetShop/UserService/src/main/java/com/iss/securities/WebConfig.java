package com.iss.securities;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer
{
    @Value("${sweetshop.profile.upload.dir}")
    private String profileDir;

    @Value("${sweetshop.sweets.upload.dir}")
    private String sweetDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry)
    {
        registry.addResourceHandler("/profile-pictures/**")
                .addResourceLocations("file:" + profileDir);

        registry.addResourceHandler("/sweet-images/**")
                .addResourceLocations("file:" + sweetDir);
    }
}