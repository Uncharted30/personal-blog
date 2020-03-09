package com.pan.blog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableJpaAuditing
@SpringBootApplication
@ComponentScan("com")
@EnableAsync(proxyTargetClass = true)
public class BlogApplication {

  public static void main(String[] args) {
    SpringApplication.run(BlogApplication.class, args);
  }
}
