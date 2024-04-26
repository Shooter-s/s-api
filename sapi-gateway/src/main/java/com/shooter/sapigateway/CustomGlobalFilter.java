package com.shooter.sapigateway;

import cn.hutool.core.util.StrUtil;
import com.shooter.sapiclientsdk.utils.SignUtil;
import com.shooter.sapicommon.model.entity.InterfaceInfo;
import com.shooter.sapicommon.model.entity.User;
import com.shooter.sapicommon.service.InnerInterfaceInfoService;
import com.shooter.sapicommon.service.InnerUserInterfaceInfoService;
import com.shooter.sapicommon.service.InnerUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;

/**
 * ClassName: CustomGlobalFilter
 * Package: com.shooter.sapigateway
 * Description:
 * @Author:Shooter
 * @Create 2024/4/26 23:13
 * @Version 1.0
 */
@Slf4j
@Component
public class CustomGlobalFilter implements GlobalFilter, Ordered {

    @DubboReference
    private InnerUserInterfaceInfoService userInterfaceInfoService;

    @DubboReference
    private InnerInterfaceInfoService interfaceInfoService;

    @DubboReference
    private InnerUserService userService;

    // 白名单： localhost -> 0:0:0:0:0:0:0:1
    private static final List<String> IP_WHITE_LIST = Arrays.asList("127.0.0.1", "0:0:0:0:0:0:0:1");

    private static final String INTERFACE_HOST = "http://localhost:8083";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 1、请求日志
        ServerHttpRequest request = exchange.getRequest();
        String path = INTERFACE_HOST + request.getPath().value();
        String method = request.getMethod().toString();
        log.info("请求唯一标识：" + request.getId());
        log.info("请求路径：" + path);
        log.info("请求方法：" + method);
        log.info("请求参数：" + request.getQueryParams());
        String sourceAddress = request.getLocalAddress().getHostString();
        log.info("请求来源地址：" + sourceAddress);
        log.info("请求来源地址：" + request.getRemoteAddress());
        // 2、黑白名单
        ServerHttpResponse response = exchange.getResponse();
        log.info("响应状态码是：{}", response.getStatusCode());
        if (!IP_WHITE_LIST.contains(sourceAddress)) {
            return handleNoAuth(response);
        }
        // 3、鉴权
        HttpHeaders headers = request.getHeaders();
        String accessKey = headers.getFirst("accessKey");
        //查库，该用户被分发的ak
        User invokeUser = null;
        try {
            invokeUser = userService.getInvokeUser(accessKey);
        }catch (Exception e){
            log.error("getInvokeUser error", e);
        }
        if (invokeUser == null){
            return handleNoAuth(response);
        }
        // 时间校验(防止重放)
        String timestamp = headers.getFirst("timestamp");
        long currentTime = System.currentTimeMillis() / 1000;
        final long FIVE_MINUTES = 60 * 5;
        if ((currentTime - Long.parseLong(timestamp)) >= FIVE_MINUTES) {
            return handleNoAuth(response);
        }
        // 校验sk
        String secretKey = invokeUser.getSecretKey();
        String sign = headers.getFirst("sign");
        String body = headers.getFirst("body");
        String serverSign = SignUtil.genSign(body, secretKey);
        if (StrUtil.isEmpty(sign) || !serverSign.equals(sign)) {
            return handleNoAuth(response);
        }
        // 4、从数据库中查询模拟接口是否存在（网关不用再引入mybatis啥依赖了，直接用别的接口写好的逻辑）
        InterfaceInfo interfaceInfo = null;
        try {
            interfaceInfo = interfaceInfoService.getInterfaceInfo(path, method);
        }catch (Exception e){
            log.error("getInterfaceInfo error", e);
        }
        if (interfaceInfo == null){
            return handleNoAuth(response);
        }
        // 该用户的是否还有接口调用次数
        if (!userInterfaceInfoService.hasLeftNum(interfaceInfo.getId(),invokeUser.getId())) {
            return handleInvokeError(response);
        }
        // 5、请求转发，调用模拟接口
        // 6、响应日志
        return handleResponse(exchange, chain,interfaceInfo.getId(),invokeUser.getId());
    }

    //处理响应
    private Mono<Void> handleResponse(ServerWebExchange exchange, GatewayFilterChain chain,long interfaceInfoId,long userId) {
        try {
            ServerHttpResponse originalResponse = exchange.getResponse();
            // 缓存数据的工厂(byte[] -> dataBuffer)
            DataBufferFactory dataBufferFactory = originalResponse.bufferFactory();
            HttpStatus statusCode = (HttpStatus) originalResponse.getStatusCode();
            if (statusCode == HttpStatus.OK) {
                // 装饰，增强能力(让response 可以处理异步结果)
                ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {
                    // 等调用完转发的接口后才会执行
                    @Override
                    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                        log.info("body instanceof Flux: {}", (body instanceof Flux));
                        if (body instanceof Flux) {
                            Flux<? extends DataBuffer> fluxBody = Flux.from(body);
                            // 往返回值中写数据
                            return super.writeWith(  // dataBuffer -> Mono
                                    fluxBody.map(dataBuffer -> {
                                        // 7. 调用成功，接口调用次数 + 1 invokeCount
                                        try {
                                            userInterfaceInfoService.invokeCount(interfaceInfoId, userId);
                                        } catch (Exception e) {
                                            log.error("invokeCount error", e);
                                        }
                                        // 构建日志
                                        byte[] content = new byte[dataBuffer.readableByteCount()];
                                        dataBuffer.read(content);
                                        DataBufferUtils.release(dataBuffer);//释放掉内存
                                        StringBuilder sb2 = new StringBuilder(200);
                                        String data = new String(content, StandardCharsets.UTF_8); //data
                                        sb2.append(data);
                                        // 打印日志
                                        log.info("响应结果：" + data);
                                        return dataBufferFactory.wrap(content); //(byte[] -> dataBuffer)
                                    }));
                        } else {
                            // 8. 调用失败，返回一个规范的错误码
                            log.error("<--- {} 响应code异常", getStatusCode());
                        }
                        return super.writeWith(body);
                    }
                };
                // 设置 response 对象为装饰过的(可以走异步处理响应结果的逻辑)
                return chain.filter(exchange.mutate().response(decoratedResponse).build());
            }
            return chain.filter(exchange); // statusCode != HttpStatus.OK 那么降级处理返回数据
        } catch (Exception e) {
            log.error("网关处理响应异常" + e);
            return chain.filter(exchange);
        }
    }

    private static Mono<Void> handleNoAuth(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.FORBIDDEN); // 403
        return response.setComplete();
    }

    private static Mono<Void> handleInvokeError(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR); // 500
        log.error("远程调用业务异常");
        return response.setComplete();
    }

    @Override
    public int getOrder() { // 要-1，否则ServerHttpResponseDecorator进不去
        return -1;
    }
}
