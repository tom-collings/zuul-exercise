package com.example.zuul.simpleRouter.configuration;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.net.MalformedURLException;
import java.net.URL;

public class SimpleFilter extends ZuulFilter{

    private static Logger log = LoggerFactory.getLogger(SimpleFilter.class);

    @Value("${route.prefix:http://zuul-controller-}")
    private String routePrefix;

    @Value("${route.suffix:.local.pcfdev.io/version}")
    private String routeSuffix;

    @Override
    public String filterType() {
        return "route";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {

        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        String cookieVal = "latest";
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equalsIgnoreCase("controller-version")) {
                    cookieVal = cookie.getValue();
                }
            }
        }

        try {
            ctx.setRouteHost(new URL(routePrefix + cookieVal + routeSuffix));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        System.out.println("context route host, path is " + ctx.getRouteHost().getHost() + ", " + ctx.getRouteHost().getPath());
        System.out.println("controller-version is " + cookieVal);

        return null;
    }
}
