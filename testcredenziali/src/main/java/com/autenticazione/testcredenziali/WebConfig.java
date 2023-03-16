//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.autenticazione.testcredenziali;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
    public WebConfig() {
    }

    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins(new String[]{"http://localhost:3000"}).allowedMethods(new String[]{"GET", "POST", "PUT", "DELETE", "HEAD"}).allowCredentials(true);
    }
}
