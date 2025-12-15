package com.example.mystarter.router;

import com.example.mystarter.context.GrayContext;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.RpcException;
import org.apache.dubbo.rpc.cluster.router.AbstractRouter;
import org.apache.dubbo.rpc.cluster.router.RouterResult;

import java.util.ArrayList;
import java.util.List;

public class GrayRouter extends AbstractRouter {

    public GrayRouter(URL url) {
        super(url);
    }

    @Override
    public <T> RouterResult<Invoker<T>> route(List<Invoker<T>> invokers, URL url, Invocation invocation, boolean needToPrintMessage) throws RpcException {
        if (invokers == null || invokers.isEmpty()) {
            return new RouterResult<>(invokers);
        }

        String grayTag = getGrayTag(invocation);
        InvokerGroup<T> group = partitionInvokers(invokers);
        List<Invoker<T>> result = selectInvokers(grayTag, group, invokers);

        return new RouterResult<>(result);
    }

    private String getGrayTag(Invocation invocation) {
        String env = GrayContext.get();
        return env != null ? env : invocation.getAttachment("env");
    }

    private <T> InvokerGroup<T> partitionInvokers(List<Invoker<T>> invokers) {
        List<Invoker<T>> grayInvokers = new ArrayList<>();
        List<Invoker<T>> prodInvokers = new ArrayList<>();

        for (Invoker<T> invoker : invokers) {
            String env = invoker.getUrl().getParameter("env");
            if ("gray".equals(env)) {
                grayInvokers.add(invoker);
            } else {
                prodInvokers.add(invoker);
            }
        }

        return new InvokerGroup<>(grayInvokers, prodInvokers);
    }

    private <T> List<Invoker<T>> selectInvokers(String grayTag, InvokerGroup<T> group, List<Invoker<T>> invokers) {
        if (isGrayRouting(grayTag, group)) {
            return logAndReturn(group.grayInvokers, "灰度环境");
        }

        List<Invoker<T>> prodInvokers = group.prodInvokers.isEmpty() ? invokers : group.prodInvokers;
        return logAndReturn(prodInvokers, "正式环境");
    }


    private <T> boolean isGrayRouting(String grayTag, InvokerGroup<T> group) {
        return "gray".equals(grayTag) && !group.grayInvokers.isEmpty();
    }

    private <T> List<Invoker<T>> logAndReturn(List<Invoker<T>> invokers, String env) {
        System.out.println("  → 路由到" + env + "，提供者数量: " + invokers.size());
        return invokers;
    }


    /**
     * 服务提供者分组
     */
    private static class InvokerGroup<T> {
        final List<Invoker<T>> grayInvokers;
        final List<Invoker<T>> prodInvokers;

        InvokerGroup(List<Invoker<T>> grayInvokers, List<Invoker<T>> prodInvokers) {
            this.grayInvokers = grayInvokers;
            this.prodInvokers = prodInvokers;
        }
    }
}
