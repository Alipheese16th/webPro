package com.kist.portal.sysconf.interceptor;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.kist.portal.common.login.model.UserInfo;
import com.kist.portal.common.login.service.LoginService;
import com.kist.portal.sysconf.manager.SessionManager;

@Component
public class Interceptor extends HandlerInterceptorAdapter{
	Logger logger  = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private LoginService loginService;
	
	@Autowired
	private SessionManager sessionManager;
	
	@Value("${spring.profiles.active}")
	private String activeProfile;
        
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("===================== " + request.getRequestURI() + " START! =======================");
    	logger.info("Request URL is \t: " + request.getRequestURI());
    	logger.info("Request Method is \t: " + request.getMethod());
    	logger.info("Request Client IP is \t: " + getClientIP(request));
    	logger.info("Request Session ID is \t: " + request.getSession().getId());
    	logger.info("Response remote host IP is \t: " + request.getRemoteHost());
    	logger.info("Request Parameter is \t: " + getParameter(request));

			if (true) {
				return true;
			}
    	
	    if("OPTIONS".equals(request.getMethod())) {
	        return true;
	    }
        
    	boolean passYn = false;
    	String[] urlSplit = request.getRequestURL().toString().split("/");
    	for (int i = 0; i < urlSplit.length; i++) {
    		if ("con-oprn-prms-dtl-print".equals(urlSplit[i])) {
    			passYn = true;
    		}
    	}
    	
    	if (passYn) {
    		// 권한 체크
//        	Map<String, String> param = new HashMap<>();
//        	param.put("usrId", request.getParameter("usr_id").toString());
//        	String lastPart = request.getRequestURI().substring(request.getRequestURI().lastIndexOf("/") + 1);
//        	if(lastPart.matches(".*[0-9].*")) {
//        		param.put("url", request.getRequestURI().substring(0, request.getRequestURI().lastIndexOf("/")));
//        	} else {
//        		param.put("url", request.getRequestURI());
//        	}
//        	
//        	List<Map<String, ?>> roleList = loginService.selectApiRole(param);
//        	if(roleList.size() == 0 || !checkRole(request.getMethod(), roleList)) {
//        		logger.warn("=============== Forbidden!! ===============");
//    			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
//    			return false;
//        	}
//        	
//        	// Api Logging
//        	param.put("method", request.getMethod());
//        	param.put("ip", getClientIP(request));
////        	param.put("wkplId", userInfo.getWkplId());
////        	param.put("langCd", userInfo.getLangCd());
////        	param.put("tzonNm", userInfo.getTimeZone());
//        	
//        	loginService.insertApiLog(param);
    	} else {
    		UserInfo userInfo = sessionManager.get(request.getSession().getId());
        	if(userInfo == null) {
        		logger.error("=============== 세션 내 사용자 정보 없음... Unauthorized!! ===============");
        		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        		return false;			
    	    } else {
    	    	logger.info("request User is \t: " + userInfo.getUsrId());
    	    }
        	
        	// 권한 체크
        	Map<String, String> param = new HashMap<>();
        	param.put("usrId", userInfo.getUsrId());
        	String lastPart = request.getRequestURI().substring(request.getRequestURI().lastIndexOf("/") + 1);
        	if(lastPart.matches(".*[0-9].*")) {
        		param.put("url", request.getRequestURI().substring(0, request.getRequestURI().lastIndexOf("/")));
        	} else {
        		param.put("url", request.getRequestURI());
        	}
        	
        	List<Map<String, ?>> roleList = loginService.selectApiRole(param);
        	if(roleList.size() == 0 || !checkRole(request.getMethod(), roleList)) {
        		logger.error("=============== API 권한 없음... Forbidden!! ===============");
    			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    			return false;
        	}
        	
        	// Api Logging
        	param.put("method", request.getMethod());
        	param.put("ip", getClientIP(request));
        	param.put("wkplId", userInfo.getWkplId());
        	param.put("langCd", userInfo.getLangCd());
        	param.put("tzonNm", userInfo.getTimeZone());
        	param.put("serverIp", request.getRemoteHost());
        	param.put("inputParam", getParameter(request));
        	
        	// api 정보 세션에 임시 저장
        	userInfo.setApiInfo(param);
        	sessionManager.set(request.getSession().getId(), userInfo);
        	
        	loginService.insertApiLog(param);
    	}
    	
        return true;
    }
    
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView model) throws Exception {

    	logger.info("===================== " + request.getRequestURI() + " END! =======================");
    }
    
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    	
    }
    
    private boolean checkRole(String method, List<Map<String, ?>> roleList) {
    	boolean flag = false;
    	
    	for(Map<String, ?> role : roleList) {
    		if("GET".equals(method) && "Y".equals(role.get("gemthd_yn"))) {
    			flag = true;
    			break;
    		} else if("POST".equals(method) && "Y".equals(role.get("pomthd_yn"))) {
    			flag = true;
    			break;
    		} else if("PUT".equals(method) && "Y".equals(role.get("pumthd_yn"))) {
    			flag = true;
    			break;
    		} else if("DELETE".equals(method) && "Y".equals(role.get("demthd_yn"))) {
    			flag = true;
    			break;
    		}
    	}
    	
    	return flag;
    }
    
    public static String getClientIP(HttpServletRequest request) {
	    String ip = request.getHeader("X-Forwarded-For");

	    if (ip == null) {
	        ip = request.getHeader("Proxy-Client-IP");
	    }
	    if (ip == null) {
	        ip = request.getHeader("WL-Proxy-Client-IP");
	    }
	    if (ip == null) {
	        ip = request.getHeader("HTTP_CLIENT_IP");
	    }
	    if (ip == null) {
	        ip = request.getHeader("HTTP_X_FORWARDED_FOR");
	    }
	    if (ip == null) {
	        ip = request.getRemoteAddr();
	    }

	    return ip;
	}
    
    @SuppressWarnings("rawtypes")
	public static String getParameter(HttpServletRequest request) {
    	Enumeration params = request.getParameterNames();
    	String strParam = "";
    	while(params.hasMoreElements()) {
    	    String name = (String)params.nextElement();
    	    String value = request.getParameter(name);
    	    strParam += name + "=" + value + "&";
    	}
    	
    	return strParam;
    }
}