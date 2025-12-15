package com.example.consumer.filter;

import com.example.mystarter.context.GrayContext;
import org.apache.dubbo.common.constants.CommonConstants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;

@Activate(group = {CommonConstants.CONSUMER, CommonConstants.PROVIDER})
public class MyDubboFilter implements Filter {
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        System.out.println("before");
        Result result = invoker.invoke(invocation);
        System.out.println("after");
        return result;
    }
}
