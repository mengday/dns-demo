package com.example.mystarter.filter;

import com.example.mystarter.context.GrayContext;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class GrayWebFilter implements javax.servlet.Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        String env = httpRequest.getHeader("env");
        if (env != null) {
            GrayContext.set(env);
        }

        try {
            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            GrayContext.remove();
        }
    }
}
