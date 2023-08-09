package com.water.config;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = MybatisConfig.PACKAGE)
public class MybatisConfig {
    static final String PACKAGE = "com.water.mapper";
}
