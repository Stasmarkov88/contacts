package com.contacts;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by SAMarkov on 9/14/2016.
 */
@Configuration
@ComponentScan(basePackages = "com.contacts")
@EnableWebMvc
public class TestConfig {
}
