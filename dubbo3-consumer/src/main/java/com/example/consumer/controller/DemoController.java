package com.example.consumer.controller;

import com.example.api.DemoService;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.rpc.RpcContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoController {

    @DubboReference(version = "1.0.0", timeout = 1000, loadbalance = "roundrobin", cluster = "failfast",
            mock = "return null", stub = "com.example.consumer.controller.DemoServiceStub", parameters = {"router", "grayRouter"})
    private DemoService demoService;

    @Value("${key2}")
    private String key2;

    @GetMapping("/test")
    public String test() {
        RpcContext.getContext().setAttachment("key3", "value3");
//        RpcContext.getContext().setAttachment(Constants.TAG_KEY,"tag1");
        return demoService.getString() + "-" + key2;
    }
}
