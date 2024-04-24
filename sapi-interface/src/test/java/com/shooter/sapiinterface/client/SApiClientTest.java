package com.shooter.sapiinterface.client;

import com.shooter.sapiclientsdk.client.SApiClient;
import com.shooter.sapiclientsdk.model.User;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * ClassName: SApiClientTest
 * Package: com.shooter.sapiinterface.client
 * Description:
 * @Author:Shooter
 * @Create 2024/4/23 21:19
 * @Version 1.0
 */
@SpringBootTest
class SApiClientTest {

    @Resource
    private SApiClient sApiClient;
    @Test
    public void test(){
        sApiClient.getNameByGet("shooter");
        User user = new User();
        user.setUsername("shooter");
        sApiClient.getUserNameByPost(user);
    }


}