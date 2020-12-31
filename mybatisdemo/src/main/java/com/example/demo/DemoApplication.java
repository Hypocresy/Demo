package com.example.demo;

import com.example.demo.service.Oper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@SpringBootApplication
public class DemoApplication {
   @Autowired
    Oper oper;
   @PostConstruct
    public void init(){
       try {
           oper.dddd();
       } catch (Exception e) {
           e.printStackTrace();
       }
   }
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
