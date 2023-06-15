package com.kist.portal.sysconf.manager;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.kist.portal.common.login.model.UserInfo;
import com.kist.portal.common.utils.SessionUtil;

/**
 * 기존 SessionManager 사용자 영향을 줄이기 위해 해당 코드 변경.
 */
@Component
public class SessionManager {
	
	private Map<String, UserInfo> sessionMap;
	
	@PostConstruct
	public void init() {
		sessionMap = new HashMap<>();
	}
	
	public void set(String sessionId, UserInfo userInfo) {
		sessionMap.put(sessionId, userInfo);
	}
	
	public UserInfo get(String sessionId) {
		// return sessionMap.get(sessionId);
		/**
		 * SpringSecurity 적용으로 인하여 기존 코드 최대한 활용한 코드지원 방식으로 구현.
		 * 따라서, 기존에 사용하던 session 방식에 springsession을 연결하여 개발자의 혼란 최소화.
		 * FIXME: 전달받은 sessionId 활용 안함.(무조건 현재 세션정보를 return)
		 */
		return SessionUtil.getSessionUserInfo();
	}

	public UserInfo get() {
		return SessionUtil.getSessionUserInfo();
	}
	
	public void removeSession(String sessionId) {
		sessionMap.remove(sessionId);
	}
	
	public void removeUserInfo(String usrId) {
		for(String key : sessionMap.keySet()) {
			if(usrId.equals(sessionMap.get(key).getUsrId())) {
				sessionMap.remove(key);
				break;
			}
		}
	}

}
