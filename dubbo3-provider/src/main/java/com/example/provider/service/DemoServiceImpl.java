package com.example.provider.service;

import com.example.api.DemoService;
import org.apache.dubbo.config.annotation.DubboService;
import org.apache.dubbo.rpc.RpcContext;
import org.springframework.beans.factory.annotation.Value;


@DubboService(version = "1.0.0")
public class DemoServiceImpl implements DemoService {

    @Value("${key1}")
    private String key1;
    @Value("${server.port}")
    private String port;

    @Override
    public String getString() {
        String key3 = RpcContext.getContext().getAttachment("key3");
        return key1 + "-" + key3 + "-" + port + "-" + System.currentTimeMillis();
    }
}
