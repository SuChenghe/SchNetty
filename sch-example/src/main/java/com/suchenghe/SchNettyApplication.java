package com.suchenghe;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class SchNettyApplication implements ApplicationRunner {

  public static void main(String[] args) {
    new SpringApplicationBuilder(SchNettyApplication.class)
            //控制台Banner输出
            //.bannerMode(Banner.Mode.OFF)
            .run(args);
  }

  @Override
  public void run(ApplicationArguments args) throws Exception {
    System.out.println("Spring Boot Run.....");
  }
}
