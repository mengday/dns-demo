package com.example.mystarter.filter;

import com.example.mystarter.context.GrayContext;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;
import org.springframework.beans.factory.InitializingBean;

@Activate(group = {CommonConstants.PROVIDER}, order = -10000)
public class GrayProviderFilter implements Filter, InitializingBean {
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        String grayTag = invocation.getAttachment("env");

        if (grayTag != null) {
            GrayContext.set(grayTag);
        }

        try {
            return invoker.invoke(invocation);
        } finally {
            if (GrayContext.get() != null) {
                GrayContext.remove();
            }
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("GrayProviderFilter laod");
    }
}
