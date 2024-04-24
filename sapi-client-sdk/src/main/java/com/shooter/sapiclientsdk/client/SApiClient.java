package com.shooter.sapiclientsdk.client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.shooter.sapiclientsdk.model.User;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static com.shooter.sapiclientsdk.utils.SignUtil.genSign;


/**
 * ClassName: SApiClient
 * Package: com.shooter.sapiinterface.client
 * Description:
 * @Author:Shooter
 * @Create 2024/4/23 21:07
 * @Version 1.0
 */
public class SApiClient {

    private String accessKey;

    private String secretKey;

    public SApiClient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    public String getNameByGet(String name) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("name",name);
        String result = HttpUtil.get("http://localhost:8082/api/name/", paramMap);
        System.out.println(result);
        return result;
    }

    public String getNameByPost(String name){
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("name",name);
        String result = HttpUtil.post("http://localhost:8082/api/name/", paramMap);
        System.out.println(result);
        return result;
    }


    private Map<String ,String> getHeaderMap(String body){
        Map<String ,String> hashMap = new HashMap<>();
        hashMap.put("accessKey",accessKey);
        // 一定不可以在请求中传递密码
//        hashMap.put("secretKey", secretKey);
        hashMap.put("nonce", RandomUtil.randomNumbers(4));
        hashMap.put("body", body);
        hashMap.put("timestamp", String.valueOf(System.currentTimeMillis() / 1000));
        hashMap.put("sign",genSign(body,secretKey));
        return hashMap;
    }

    public String getUserNameByPost(User user){
        String json = JSONUtil.toJsonStr(user);
        HttpResponse httpResponse = HttpRequest.post("http://localhost:8082/api/name/user")
                // 发送请求另外额外加上请求头，为什么不把ak/sk放到url中，因为有长度限制
                .addHeaders(getHeaderMap(json))
                .body(json)
                .execute();
        System.out.println(httpResponse.getStatus());
        System.out.println(httpResponse.body());
        return httpResponse.body();
    }

}
