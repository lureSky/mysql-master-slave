package com.martin;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceAutoConfigure;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author martin
 * @email necaofeng@foxmail.com
 * @Date 2020/4/9 0009
 */
@SpringBootApplication(scanBasePackages = "com.martin")
@EnableAutoConfiguration(exclude={DruidDataSourceAutoConfigure.class})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
