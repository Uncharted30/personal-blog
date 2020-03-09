package com.pan.blog.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class FileUploadConfig extends WebMvcConfigurationSupport {

  @Value("${web.upload-path}")
  private String uploadPath;

  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("images/**").addResourceLocations("file:" + this.uploadPath + "/images/");
    registry.addResourceHandler("files/**").addResourceLocations("file:" + this.uploadPath + "/files/");
    super.addResourceHandlers(registry);
  }
}
