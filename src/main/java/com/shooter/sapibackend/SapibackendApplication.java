package com.shooter.sapibackend;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@MapperScan("com.shooter.sapibackend.mapper")
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@EnableDubbo
// 设置基于CGLIB 技术实现，exposeProxy = true把代理对象暴露到ThreadLocal中
public class SapibackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(SapibackendApplication.class, args);
    }

}
