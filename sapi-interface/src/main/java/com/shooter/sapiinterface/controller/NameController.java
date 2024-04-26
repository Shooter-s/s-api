package com.shooter.sapiinterface.controller;

import com.shooter.sapiclientsdk.model.User;
import com.shooter.sapiclientsdk.utils.SignUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;


/**
 * ClassName: NameController
 * Package: com.shooter.sapiinterface.controller
 * Description:
 * @Author:Shooter
 * @Create 2024/4/23 11:25
 * @Version 1.0
 */
@RestController
@RequestMapping("/name")
public class NameController {

    @GetMapping("/get")
    public String getNameByGet(String name){
        return "GET 你的名字是：" + name;
    }

    @PostMapping("/post")
    public String getNameByPost(@RequestParam String name){
        return "POST 你的名字是：" + name;
    }

    @PostMapping("/user")
    public String getUserNameByPost(@RequestBody User user, HttpServletRequest request) {
        // 下面的鉴权逻辑都在网关做了
        /*String accessKey = request.getHeader("accessKey");
        String secretKey = request.getHeader("secretKey");
        String sign = request.getHeader("sign");
        String body = request.getHeader("body");
        // todo 实际情况要从数据库查，是否分配给用户
        if (!accessKey.equals("shooter")){
            throw new RuntimeException("没权限");
        }
        // 时间戳和随机值的校验就先不进行了，这两个参数是用来防止重放的
        // todo 实际情况中是从数据库查到sk的
        String serverSign = SignUtil.genSign(body, "abcdefg");
        if (!sign.equals(serverSign)){
            throw new RuntimeException("无权限");
        }*/
        return "POST 你的名字是：" + user.getUsername();
    }

}
