package com.shooter.sapiclientsdk;

import com.shooter.sapiclientsdk.client.SApiClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * ClassName: SApiClientConfig
 * Package: com.shooter.sapiclientsdk
 * Description:
 * @Author:Shooter
 * @Create 2024/4/24 0:13
 * @Version 1.0
 */
@Configuration
@ConfigurationProperties(prefix = "sapi.client")
@Data
@ComponentScan
public class SApiClientConfig {

    private String accessKey;

    private String secretKey;

    @Bean
    public SApiClient sApiClient(){
        return new SApiClient(accessKey,secretKey);
    }

}
