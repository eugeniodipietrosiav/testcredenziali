////
//// Source code recreated from a .class file by IntelliJ IDEA
//// (powered by FernFlower decompiler)
////
//
//package com.autenticazione.testcredenziali;
//
//
//
//import jakarta.servlet.MultipartConfigElement;
//import org.springframework.boot.web.servlet.MultipartConfigFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.util.unit.DataSize;
//
//@Configuration
//public class AppConfig {
//    public AppConfig() {
//    }
//    @Bean
//    public MultipartConfigElement multipartConfigElement() {
//        MultipartConfigFactory factory = new MultipartConfigFactory();
//        factory.setMaxFileSize(DataSize.ofMegabytes(100000L));
//        factory.setMaxRequestSize(DataSize.ofMegabytes(100000L));
//        return factory.createMultipartConfig();
//    }
//}
