package com.destiny.camel.config.xxsf;


import cn.hutool.core.util.StrUtil;
import com.destiny.camel.config.xxsf.XssHttpServletRequestWrapper;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * TODO
 *
 * @author duzk
 * @date 2020/10/16 13:49
 */
public class XssFilter implements Filter {
	
	//分隔符
	public static final String AUTH_DELIMITER = ",";

    private List<String> excludes = new ArrayList<>();

    private boolean enabled = false;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String strExcludes = filterConfig.getInitParameter("excludes");
        String strEnabled = filterConfig.getInitParameter("enabled");
        //将不需要xss过滤的接口添加到列表中
        if(StrUtil.isNotEmpty(strExcludes)){
            String[] urls = strExcludes.split(AUTH_DELIMITER);
            for(String url:urls){
                excludes.add(url);
            }
        }
        if(StrUtil.isNotEmpty(strEnabled)){
            enabled = Boolean.valueOf(strEnabled);
        }
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        //如果该访问接口在排除列表里面则不拦截
        if(isExcludeUrl(request.getServletPath())){
            filterChain.doFilter(servletRequest,servletResponse);
            return;
        }
        //拦截该url并进行xss过滤
        XssHttpServletRequestWrapper xssHttpServletRequestWrapper = new XssHttpServletRequestWrapper(request);
        filterChain.doFilter(xssHttpServletRequestWrapper,servletResponse);

    }

    @Override
    public void destroy() {

    }

    private boolean isExcludeUrl(String urlPath){
        if(!enabled){
            //如果xss开关关闭了，则所有url都不拦截
            return true;
        }
        if(excludes==null||excludes.isEmpty()){
            return false;
        }

        String url = urlPath;
        for(String pattern:excludes){
            Pattern p = Pattern.compile("^"+pattern);
            Matcher m = p.matcher(url);
            if(m.find()){
                return true;
            }
        }
        return false;
    }

}
