package com.gmail.vladbaransky.webmodule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication(exclude = UserDetailsServiceAutoConfiguration.class, scanBasePackages = {
        "com.gmail.vladbaransky.webmodule",
        "com.gmail.vladbaransky.servicemodule",
        "com.gmail.vladbaransky.repositorymodule"})
public class WebModuleApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebModuleApplication.class, args);
    }

    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }
}

