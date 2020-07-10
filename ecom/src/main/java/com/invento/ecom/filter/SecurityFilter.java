package com.invento.ecom.filter;


import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(1)
public class SecurityFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
    {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        if(httpServletRequest.getSession()!=null && httpServletRequest.getSession().getAttribute("user")!=null) {

            //call next filter in the filter chain
            chain.doFilter(request, response);
        }
        else{
            httpServletResponse.sendRedirect("/");
        }
    }

    @Override
    public void destroy() {

    }

    // other methods
}
