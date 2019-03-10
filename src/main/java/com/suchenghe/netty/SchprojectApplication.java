package com.suchenghe.netty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class SchprojectApplication extends SpringBootServletInitializer {

  public static void main(String[] args) {
    SpringApplication.run(SchprojectApplication.class,args);
    System.out.println("启动成功");
  }
}
