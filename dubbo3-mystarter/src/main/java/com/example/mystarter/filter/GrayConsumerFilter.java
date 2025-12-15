package com.example.mystarter.filter;

import com.example.mystarter.context.GrayContext;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;
import org.springframework.beans.factory.InitializingBean;

@Activate(group = {CommonConstants.CONSUMER}, order = -10000)
public class GrayConsumerFilter implements Filter, InitializingBean {
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        String env = GrayContext.get();
        if (env != null) {
            invocation.getAttachments().put("env", env);
        }
        return invoker.invoke(invocation);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("GrayProviderFilter laod");
    }
}
