
package com.autenticazione.testcredenziali;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@PropertySource({"classpath:application.properties"})
@EnableJpaRepositories({"com.autenticazione.testcredenziali"})
@ComponentScan(
		basePackages = {"com.autenticazione.testcredenziali"}
)
@EntityScan({"com.autenticazione.testcredenziali"})
@SpringBootApplication(
		exclude = {SecurityAutoConfiguration.class}
)
public class TestcredenzialiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestcredenzialiApplication.class, args);
	}
}
