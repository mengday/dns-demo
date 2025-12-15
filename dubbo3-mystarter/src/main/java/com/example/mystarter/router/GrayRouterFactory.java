package com.example.mystarter.router;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.rpc.cluster.Router;
import org.apache.dubbo.rpc.cluster.RouterFactory;
import org.springframework.beans.factory.InitializingBean;

/**
 * 灰度环境路由
 */
public class GrayRouterFactory implements RouterFactory, InitializingBean {

    @Override
    public Router getRouter(URL url) {
        return new GrayRouter(url);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("load GrayRouterFactory");
    }
}