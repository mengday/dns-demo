package com.example.consumer.controller;

import com.example.api.DemoService;

public class DemoServiceMock implements DemoService {
    @Override
    public String getString() {
        // 你可以伪造容错数据，此方法只在出现RpcException时被执行
        return "容错数据";
    }
}
