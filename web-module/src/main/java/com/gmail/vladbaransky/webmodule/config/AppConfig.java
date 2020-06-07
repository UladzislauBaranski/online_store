package com.gmail.vladbaransky.webmodule.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({
        "com.gmail.vladbaransky.webmodule",
        "com.gmail.vladbaransky.servicemodule",
        "com.gmail.vladbaransky.repositorymodule"})
public class AppConfig {
}

