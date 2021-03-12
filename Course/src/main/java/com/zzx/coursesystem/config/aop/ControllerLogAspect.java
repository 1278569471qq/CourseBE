package com.zzx.coursesystem.config.aop;

import com.zzx.coursesystem.dao.mongo.LogDAO;
import com.zzx.coursesystem.manager.LoginStatusManager;
import com.zzx.coursesystem.model.bo.LoginStatusBO;
import com.zzx.coursesystem.model.constant.HttpStatusCode;
import com.zzx.coursesystem.model.entity.mongo.LogEntity;
import com.zzx.coursesystem.model.vo.response.ResultVO;
import com.zzx.coursesystem.util.HttpUtils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Aspect
@Component
public class ControllerLogAspect {
    private static final String PACKAGE_PREFIX = "com.zzx.coursesystem.controller.";
    private static final String PACKAGE_PREFIX2 = "com.zzx.coursesystem.config.handler.BindExceptionHandler.";
    private static final String CONTROLLER_POSTFIX = "Controller.";

    private final LoginStatusManager loginStatusManager;
    private final LogDAO logDAO;
    private final HttpUtils httpUtils;
    private final RedisTemplate redisTemplate;

    public ControllerLogAspect(LoginStatusManager loginStatusManager, LogDAO logDAO, HttpUtils httpUtils, RedisTemplate redisTemplate) {
        this.loginStatusManager = loginStatusManager;
        this.logDAO = logDAO;
        this.httpUtils = httpUtils;
        this.redisTemplate = redisTemplate;
    }

    @Pointcut("execution(public * com.zzx.coursesystem.controller..*.*(..)) || " +
            "execution(public * com.zzx.coursesystem.config.handler.BindExceptionHandler.handleBindException(..))")
    public void controllerLog() {
    }

    @Around("controllerLog()")
    public Object around(ProceedingJoinPoint joinPoint) {
        long startTime = System.currentTimeMillis();

        LogEntity log = new LogEntity();
        logRequest(log, joinPoint);

        Object result = null;
        try {
            result = joinPoint.proceed();
        } catch (Throwable ex) {
            ex.printStackTrace();
            log.setException(ex.getMessage());
            setResponseCode(HttpStatusCode.INTERNAL_SERVER_ERROR);
            result = new ResultVO(ResultVO.SERVER_ERROR, "未知错误", ex.getMessage());
        }

        logResult(log, result, System.currentTimeMillis() - startTime);
        logDAO.insert(log);

        return result;
    }

    private void logRequest(LogEntity logEntity, ProceedingJoinPoint joinPoint) {
        HttpServletRequest request = getRequest();
        if (request != null) {
            LoginStatusBO loginStatus = loginStatusManager.getLoginStatus(request.getSession());
            String requestUrl = request.getRequestURI();
            if (request.getQueryString() != null) {
                requestUrl += "?" + request.getQueryString();
            }
            String ip = getIpAddr(request);
            logEntity.setIp(ip);
            if (ip.equals("0:0:0:0:0:0:0:1") || ip.equals("127.0.0.1")) {
                logEntity.setLocation("测试");
            } else {
                String location = (String) redisTemplate.opsForHash().get("IP_BAIDU_LOCATION", ip);
                if (StringUtils.isEmpty(location)) {
                    location = httpUtils.getLocation(ip).getJSONObject("content").getString("address");
                    redisTemplate.opsForHash().put("IP_BAIDU_LOCATION", ip, location);
                }
                logEntity.setLocation(location);
            }
            logEntity.setRequestUrl(requestUrl);
            logEntity.setUserId(loginStatus.getUserId());
            logEntity.setUserName(loginStatus.getUsername());
            logEntity.setUserType(loginStatus.getUserType());
        }

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String businessTarget = signature.getDeclaringTypeName() + "." + signature.getMethod().getName();
        businessTarget = businessTarget
                .replace(PACKAGE_PREFIX, "")
                .replace(CONTROLLER_POSTFIX, ".")
                .replace(PACKAGE_PREFIX2, "");
        logEntity.setBusinessTarget(businessTarget);
    }

    private void logResult(LogEntity log, Object result, long executeTime) {
        if (!(result instanceof ResultVO)) {
            return;
        }

        ResultVO resultVO = (ResultVO) result;
        log.setResultCode(resultVO.getCode());
        log.setMessage(resultVO.getMessage());
        log.setExecuteTime(executeTime);
    }

    private HttpServletRequest getRequest() {
        ServletRequestAttributes attributes = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
        if (attributes == null) {
            return null;
        }

        return attributes.getRequest();
    }

    private void setResponseCode(int statusCode) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return;
        }
        HttpServletResponse response = requestAttributes.getResponse();
        if (response == null) {
            return;
        }

        response.setStatus(statusCode);
    }
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
         }
        if(ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
           ip = request.getRemoteAddr();
        }
        return ip;
    }
}
