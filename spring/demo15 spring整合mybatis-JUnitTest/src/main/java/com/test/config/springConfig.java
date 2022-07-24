package com.test.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("com.test")
@PropertySource("classpath:jdbc.properties")
@Import({jdbcConfig.class,mybatisConfig.class})
public class springConfig {
}
