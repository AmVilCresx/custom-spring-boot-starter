package pers.under2hump.springboot.configure.core;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @description 將处理基本类型或String类型的参数的Wrapper放到过滤器链
 * @author AmVilCresx
 */
public class ParameterFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        chain.doFilter(new AvcHttpServletRequestWrapper((HttpServletRequest) request, request.getParameterMap()), response);
    }
}
