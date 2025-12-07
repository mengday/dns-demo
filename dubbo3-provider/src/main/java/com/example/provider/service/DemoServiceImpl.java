package com.example.provider.service;

import com.example.api.DemoService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Value;


@DubboService(version = "1.0.0")
public class DemoServiceImpl implements DemoService {

    @Value("${key1}")
    private String key1;

    @Override
    public String getString() {
        return key1;
    }
}
