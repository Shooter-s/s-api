package com.shooter.sapiclientsdk;

import com.shooter.sapiclientsdk.client.SApiClient;
import com.shooter.sapiclientsdk.model.User;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes={SApiClientConfig.class})
class SapiClientSdkApplicationTests {

    @Resource
    private SApiClient sApiClient;

    @Test
    void contextLoads() {
        System.out.println(sApiClient);
    }

}
