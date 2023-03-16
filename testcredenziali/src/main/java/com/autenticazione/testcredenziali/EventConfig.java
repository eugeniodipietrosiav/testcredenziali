//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.autenticazione.testcredenziali;

import com.autenticazione.testcredenziali.model.MessageObserver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EventConfig {
    public EventConfig() {
    }

    @Bean
    public MessageObserver messageObserver() {
        return new MessageObserver();
    }
}
