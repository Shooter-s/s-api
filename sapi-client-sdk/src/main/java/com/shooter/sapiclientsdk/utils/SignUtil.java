package com.shooter.sapiclientsdk.utils;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;

/**
 * ClassName: SignUtil
 * Package: com.shooter.sapiinterface.utils
 * Description:
 * @Author:Shooter
 * @Create 2024/4/23 22:47
 * @Version 1.0
 */
public class SignUtil {
    public static String genSign(String body, String secretKey) {
        Digester md5 = new Digester(DigestAlgorithm.SHA256);
        String content = body + "." + secretKey;
        return md5.digestHex(content);
    }
}
