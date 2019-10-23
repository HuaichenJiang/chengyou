package com.cy.chengyou.aops;

import com.cy.chengyou.utils.SerializeUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author HuaichenJiang
 * @Title
 * @Description
 * @date 2018/11/9  12:00
 */
@Aspect
@Component
public class WebLogAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebLogAspect.class);

    @Pointcut("execution(public * com.cy.chengyou.controllers.*.*(..))")
    public void webLog(){}

    /**
     *
     * @param joinPoint
     * @throws Throwable
     */
    @Before("webLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable{
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();

        String url = request.getRequestURL().toString();
        String method = request.getMethod();
        String uri = request.getRequestURI();
        String ip = getIpAddress(request);
        String queryString = request.getQueryString();
        Object[] args = joinPoint.getArgs();
        String params = "";
        if(args.length>0){
            if("POST".equals(method)){
                params = SerializeUtil.serializeToJson(args[0]);
            }else if("GET".equals(method)){
                params = queryString;
            }
        }
        LOGGER.info("URL : {}", url);
        LOGGER.info("HTTP_METHOD : {}", method);
        LOGGER.info("IP : {}", ip);
        LOGGER.info("URI : {}", uri);
        LOGGER.info("PARAMS : {}", params);
    }

    /**
     *
     * @param ret
     * @throws Throwable
     */
    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) throws Throwable{
        LOGGER.info("RESPONSE : {}", SerializeUtil.serializeToJson(ret));
    }

    /**
     * 获取真实IP地址
     * @param request
     * @return
     */
    private static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
//            if("127.0.0.1".equals(ip)||"0:0:0:0:0:0:0:1".equals(ip)){
//                //根据网卡取本机配置的IP
//                try {
//                    InetAddress inet = InetAddress.getLocalHost();
//                    ip= inet.getHostAddress();
//                } catch (UnknownHostException e) {
//                    e.printStackTrace();
//                }
//            }
        }
        return ip;
    }


}