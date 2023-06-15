package com.kist.portal.common.login.rest;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kist.portal.common.login.dto.UserDetailDto;
import com.kist.portal.common.login.model.UserInfo;
import com.kist.portal.common.login.service.LoginService;
import com.kist.portal.common.utils.AES256CbcCipher;
import com.kist.portal.common.utils.AES256Cipher;
import com.kist.portal.common.utils.Constant;
import com.kist.portal.sysconf.manager.SessionManager;

@RestController
@RequestMapping("/api/v1/common")
public class LoginController {
	Logger logger  = LoggerFactory.getLogger(this.getClass());
	
	@Value("${spring.profiles.active}")
	private String activeProfile;
	
	@Autowired
	LoginService service;
	
	@Autowired
	private SessionManager sessionManager;
	
	@GetMapping("/whoami")
	public ResponseEntity<?> selectUserInfo(@RequestParam Map<String, String> id, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.info("============ LoginController /whoami selectUserInfo Start ===========");
		logger.info("activeProfile : " + activeProfile);
		
    	String sessionId = request.getSession().getId();
    	String LOGIN_ID = null;
    	
    	// 모바일 접속의 경우
    	if (id.get("isMobile") != null && !id.get("isMobile").isEmpty() && !id.get("isMobile").equalsIgnoreCase("undefined") && Boolean.valueOf(id.get("isMobile")) == true) {
    		logger.info("모바일 접속 여부 : " + id.get("isMobile"));
    		
    		AES256Cipher a256 = AES256Cipher.getInstance(); // 암호화 유틸
    		if(id.get("id") == null || id.get("id").isEmpty() || id.get("id").equalsIgnoreCase("undefined")) {
        		logger.error("==> 넘어온 ID 값이 없음... UNAUTHORIZED");
    			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    		} else {
    			try {
    				String decode = URLDecoder.decode(id.get("id"), "UTF-8");
    				// LOGIN_ID = a256.AES_Decode(URLDecoder.decode(id.get("id"), "UTF-8"));
    				LOGIN_ID = AES256CbcCipher.AES_Decode(URLDecoder.decode(id.get("id"), "UTF-8"), "##donggramisoft-mobilegroupware##");
    				// LOGIN_ID = id.get("id");
        		}catch(Exception ex) {
        			try {
        				LOGIN_ID = AES256CbcCipher.AES_Decode(id.get("id"), "##donggramisoft-mobilegroupware##");
        			}catch(Exception ex2) {
            			logger.error("user id : " + id.get("id"));
            			logger.error("LOGIN_ID : " + LOGIN_ID);
            			logger.error("==> user id 복호화 실패");
        				return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        			}
        		}
    		}

    		/*
        	// 사용자 전환 기능
        	if (id.get("impersonateId") != null && !id.get("impersonateId").isEmpty()) {
        		LOGIN_ID = id.get("impersonateId");
        		logger.info("==>" + activeProfile + " impersonate LOGIN_ID is \t: " + LOGIN_ID);
        	}
        	*/
        	
    	} else if ("local".equals(activeProfile) || "dev".equals(activeProfile) ||"stg".equals(activeProfile)) {
    	//if ("local".equals(activeProfile)) {
    		if(id.get("id").isEmpty() || id.get("id").equalsIgnoreCase("undefined") || id == null) {
    			LOGIN_ID = "AC926031";
    		} else {
    			LOGIN_ID = id.get("id");
    		}
    		logger.info("LOGIN_ID is \t: " + LOGIN_ID);
    	} else {
    		// sso 처리 필요
//    		AuthCheck auth = new AuthCheck(request, response);
//        	AuthStatus status = auth.checkLogon(AuthCheckLevel.Medium);
//        	logger.info("SSO Status is \t: " + status);
//        	
//    		if(status != AuthStatus.SSOSuccess) {
//    			logger.error("==> " + ErrorMessage.getMessage(Integer.parseInt(status.toString())));
//        		logger.error("==> SSO를 이용할 수 없습니다. 관리자에게 연락해주세요.");
//        		return new ResponseEntity<>(Constant.FAIL, HttpStatus.UNAUTHORIZED);
//        	}
//    		logger.info("SSO Check 완료");
//    		
//        	LOGIN_ID = auth.getSSODomainCookieValue("loginID");	//로그인 아이디
//        	logger.info("SSO 가저오기 완료 -> SSO LOGIN_ID is \t: " + LOGIN_ID);
    	}
    	
			// 에러로 인한 임시 처리
			UserInfo userInfo = sessionManager.get();
		if(userInfo == null) {
			logger.error("==> 사용자 테이블에 데이터 없음");
			return new ResponseEntity<>(Constant.FAIL, HttpStatus.UNAUTHORIZED);
		}
		
		int dayDiff = service.selectDayDiff(LOGIN_ID);
		
		sessionManager.set(sessionId, userInfo);
		logger.info("set session ID : " + sessionId);
		logger.info("set session userInfo : " + userInfo);
		
		// 90일 이상 접속하지 않았을 시
		if(dayDiff > 90) {
			Map<String, String> re = new HashMap<>();
			re.put("usrId", userInfo.getUsrId());
			String telNo = service.selectUserTelNo(re);
			re.put("result", "reauthenticate");
			re.put("telNo", telNo);
			logger.error("==> 90일 이상 로그인 기록이 없습니다. 본인 인증 후 사용해주시기 바랍니다. : " + userInfo);
			return new ResponseEntity<>(re, HttpStatus.OK);
		}
		
		// 타임존, 사업장, 언어 코드 중 하나라도 없을 경우
		if(userInfo.getTimeZoneCd() == null || userInfo.getTimeZoneCd().isEmpty() 
				|| userInfo.getWkplId() == null || userInfo.getWkplId().isEmpty()
				|| userInfo.getLangCd() == null || userInfo.getLangCd().isEmpty()
				//|| userInfo.getWorkspaceCd() == null || userInfo.getWorkspaceCd().isEmpty()
				) {
			Map<String, String> re = new HashMap<>();
			re.put("usrId", userInfo.getUsrId());
			re.put("wkpl_id", userInfo.getWkplId());
			re.put("time_zone_cd", userInfo.getTimeZoneCd());
			re.put("time_zone", userInfo.getTimeZone());
			re.put("lang_cd", userInfo.getLangCd());
			re.put("workspace_cd", userInfo.getWorkspaceCd());
			re.put("cmpy_cd", userInfo.getCmpyCd());
			re.put("result", "reconfigure");
			logger.error("==> 필수 환경이 설정되지 않았습니다. 설정 후 사용해주시기 바랍니다. : " + userInfo);
			return new ResponseEntity<>(re, HttpStatus.OK);
		}
		
    	// DB Logging
    	String ip = getClientIP(request);
    	Map<String, String> param = new HashMap<>();
    	param.put("ip", ip);
    	param.put("usrId", userInfo.getUsrId());
    	param.put("wkplId", userInfo.getWkplId());
    	param.put("langCd", userInfo.getLangCd());
    	param.put("tzonNm", userInfo.getTimeZone());
    	param.put("workspaceCd", userInfo.getWorkspaceCd());
    	
    	service.insertUserLoginLog(param);
		
    	Map<String, Object> map = new HashMap<>();
		map.put("usr_id", userInfo.getUsrId());
		map.put("usr_nm", userInfo.getUsrNm());
		map.put("dept_cd", userInfo.getDeptCd());
		map.put("dept_nm", userInfo.getDeptNm());
		map.put("cmpy_nm", userInfo.getCmpyNm());
		map.put("cmpy_cd", userInfo.getCmpyCd());
		map.put("wkpl_id", userInfo.getWkplId());
		map.put("wkpl_nm", userInfo.getWkplNm());
		map.put("lang_cd", userInfo.getLangCd());
		map.put("time_zone", userInfo.getTimeZone());
		map.put("time_zone_cd", userInfo.getTimeZoneCd());
		map.put("workspace_cd", userInfo.getWorkspaceCd());
		map.put("roles", userInfo.getRoleList());
		map.put("roleTypeCds", userInfo.getRoleTypeCdsList());
		map.put("usr_email", userInfo.getUsrEmail());
		map.put("usr_cpho_no", userInfo.getUsrCphoNo());
		map.put("dpth_usr_id", userInfo.getDpthUsrId());
		map.put("last_login_dt", userInfo.getLastLoginDt());
		map.put("last_ip_addr", userInfo.getLastIpAddr());

		logger.info("로그인 처리 완료");
		logger.info("============ LoginController /whoami selectUserInfo End ===========");
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	
	@GetMapping("/menus")
	public ResponseEntity<?> selectMenuList(HttpSession session, HttpServletRequest request) {
    	String ip = getClientIP(request);
		Map<String, String> param = new HashMap<>();
		UserInfo userInfo = sessionManager.get(session.getId());
		
		if(userInfo == null) {
			return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
		}

    param.put("ip", ip);
		param.put("usrId", userInfo.getUsrId());
		param.put("lang_cd", userInfo.getLangCd());
		param.put("workspace_cd", userInfo.getWorkspaceCd());
		
		List<Map<String, ?>> list = service.selectMenuList(param);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@GetMapping("/menus/{id}")
	public ResponseEntity<?> selectMenuList(@PathVariable String id, HttpServletRequest request) {
    	String ip = getClientIP(request);
		Map<String, String> param = new HashMap<>();
    	param.put("ip", ip);
		param.put("id", id);
		
		List<Map<String, ?>> list = service.selectMenuList(param);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@GetMapping("/sms-check")
	public ResponseEntity<?> selectSmsCheck(@RequestParam Map<String, String> param, HttpServletRequest request, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		
		String result = Constant.FAIL;
		
		String usrId = userInfo.getUsrId();
		param.put("usrId", usrId);
		
		String certNo = service.selectSmsNo(param);
		logger.info(certNo);
		if(!certNo.isEmpty() && certNo.equalsIgnoreCase(param.get("authNo"))) {
			result = Constant.SUCCESS;
		}
		
		// DB Logging
		if(result.equalsIgnoreCase(Constant.SUCCESS)) {
	    	String ip = getClientIP(request);
	    	param = new HashMap<>();
	    	param.put("ip", ip);
	    	param.put("usrId", userInfo.getUsrId());
	    	param.put("wkplId", userInfo.getWkplId());
	    	param.put("langCd", userInfo.getLangCd());
	    	param.put("tzonNm", userInfo.getTimeZone());
	    	
	    	service.insertUserLoginLog(param);
		}
    	
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
//	@PostMapping("/sms-check")
//	public ResponseEntity<?> selectSmsReq(@RequestBody Map<String, String> param, HttpServletRequest request, HttpSession session) {
//		logger.info("============ LoginController /sms-check selectSmsReq Start ===========");
//		
//		UserInfo userInfo = sessionManager.get(session.getId());
//		
//        String cpId      = "AMOM009";		//회원사ID
//        String urlCode   = "006001";		//URL코드
//        String certNo = "";
//        if(param.get("certNo") == null || param.get("certNo").isEmpty()) {
//        	//날짜 생성
//        	Calendar today = Calendar.getInstance();
//        	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
//        	String reqDate = sdf.format(today.getTime());
//
//        	java.util.Random ran = new Random();
//        	//랜덤 문자 길이
//        	int numLength = 6;
//        	String randomStr = "";
//
//        	for (int i = 0; i < numLength; i++) {
//        		//0 ~ 9 랜덤 숫자 생성
//        		randomStr += ran.nextInt(10);
//        	}
//
//        	//certNo 는 최대 40byte 까지 사용 가능
//        	certNo = reqDate + randomStr;	
//        } else {
//        	certNo    = param.get("certNo");		//인증번호
//        }
//
//		logger.info("==> certNo : " + certNo);
//		
//        String reqDate = "";
//        if(param.get("reqDate") == null || param.get("reqDate").isEmpty()) {
//        	//날짜 생성
//        	Calendar today = Calendar.getInstance();
//        	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
//        	reqDate = sdf.format(today.getTime());
//        } else {
//        	reqDate   = param.get("reqDate");		//요청일시
//        }
//        String phoneNo   = param.get("phoneNo").replaceAll("-", "");		//휴대폰번호
//        String authNo    = param.get("authNo");		//SMS승인번호
//		String extendVar = "0000000000000000";                  //확장변수
//		
//		Map<String, String> result = new HashMap<>();
//		
//		//01. 한국모바일인증(주) 암호화 모듈 선언
//		IcertSecuManager seed = new IcertSecuManager();
//		
//		//02. 1차 암호화 (tr_cert 데이터변수 조합 후 암호화)
//		String tr_cert = "";
//		String enc_tr_cert = "";
//		tr_cert = cpId +"/"+ urlCode +"/"+ certNo +"/"+ reqDate +"/"+ phoneNo +"/"+ authNo +"/"+ extendVar;
//		logger.info("==> 인증요청 원본데이터 : " + tr_cert);
//		enc_tr_cert = seed.getEnc(tr_cert, "");
//		
//		//03. 1차 암호화 데이터에 대한 위변조 검증값 생성 (HMAC)
//		IcertHmac hmac = new IcertHmac();
//		
//		@SuppressWarnings("static-access")
//		String hmacMsg = hmac.HMacEncript(enc_tr_cert);
//	
//		//04. 2차 암호화 (1차 암호화 데이터, HMAC 데이터, extendVar 조합 후 암호화)
//		tr_cert = seed.getEnc(enc_tr_cert +"/"+ hmacMsg +"/"+ extendVar, "");  
//
//		//05. SMS사용자인증 요청 URL로 암호화 데이터 넘기기        
//		String send_url = "https://www.kmcert.com/ics/smsAuth/smsAuthT_10.jsp?tr_cert="+tr_cert;	
//		
//		URL url = null;
//        BufferedReader in = null;
//        URLConnection con = null;
//
//        String rec_cert = "";
//        String urlstr   = send_url;
//        try {
//            url = new URL(urlstr);
//            con = url.openConnection();
//            con.connect();
//            in = new BufferedReader(new InputStreamReader(con.getInputStream()));
//
//            String tmp_msg = "";
//            while ((tmp_msg = in.readLine()) != null) {
//                if (!"".equals(tmp_msg)) {
//                    rec_cert = tmp_msg;
//                }
//            }
//        } catch (MalformedURLException malformedurlexception) {
//            rec_cert = "URL Error("+malformedurlexception+")";
//    		logger.error("==> MalformedURLException : " + rec_cert);
//            return new ResponseEntity<>(Constant.FAIL, HttpStatus.OK);
//        } catch (IOException ioexception) {
//            rec_cert = "IO Error("+ioexception+")";
//    		logger.error("==> IOException : " + rec_cert);
//            return new ResponseEntity<>(Constant.FAIL, HttpStatus.OK);
//		} finally {
//            try {
//                if (in != null) {
//                    in.close();
//                }
//            } catch (Exception ex3) {
//        		logger.error("==> Exception : " + ex3.toString());
//            }
//        }
//        
//        try 
//		{
//			//결과값 변수--------------------------------------------------------------------------------------------
//            String encPara      = "";	//암호화된 통합 파라미터
//            String encMsg1      = "";	//암호화된 통합 파라미터의 Hash값
//			String encMsg2      = "";	//암호화된 통합 파라미터의 Hash값 비교를 위한 Hash값
//			
//			String r_certNo		= "";	//인증번호
//			String r_reqDate    = "";	//요청일시
//			String r_result     = "";	//인증결과값 
//			String r_resultCode = "";	//인증결과코드
//			String r_phoneNo    = "";	//휴대폰번호
//			String r_authNo		= "";	//SMS승인번호 
//			//-------------------------------------------------------------------------------------------------------
//
//			//07. 1차 복호화 (인증번호를 이용해 복호화)
//			rec_cert = seed.getDec(rec_cert, "");  
//
//			int inf1 = rec_cert.indexOf("/",0);
//			int inf2 = rec_cert.indexOf("/",inf1+1);
//
//            encPara = rec_cert.substring(0, inf1);      
//            encMsg1 = rec_cert.substring(inf1+1, inf2); 
//            
//            //08. 1차 복호화 데이터에 대한 위변조 검증값 검증
//			encMsg2 = seed.getMsg(encPara);
//
//			if(!encMsg1.equals(encMsg2)){
//        		logger.error("==> 비정상적인 접근입니다.");
//				return new ResponseEntity<>(Constant.FAIL, HttpStatus.OK);
//			}
//			
//			//09. 2차 복호화 (인증번호를 이용해 복호화)
//			rec_cert  = seed.getDec(encPara, "");  
//
//			int info1  = rec_cert.indexOf("/",0);
//			int info2  = rec_cert.indexOf("/",info1+1);
//			int info3  = rec_cert.indexOf("/",info2+1);
//			int info4  = rec_cert.indexOf("/",info3+1);
//			int info5  = rec_cert.indexOf("/",info4+1);
//			int info6  = rec_cert.indexOf("/",info5+1);
//
//			r_certNo	 = rec_cert.substring(0,info1);            
//			r_reqDate    = rec_cert.substring(info1+1,info2);      
//			r_result     = rec_cert.substring(info2+1,info3);      
//			r_resultCode = rec_cert.substring(info3+1,info4);      
//			r_phoneNo    = rec_cert.substring(info4+1,info5);      
//			r_authNo     = rec_cert.substring(info5+1,info6);
//			
//			result.put("r_certNo", r_certNo);
//			result.put("r_reqDate", r_reqDate);
//			result.put("r_result", r_result);
//			result.put("r_resultCode", r_resultCode);
//			result.put("r_phoneNo", r_phoneNo);
//			result.put("r_authNo", r_authNo);
//
//			String usrId = userInfo.getUsrId();
//			result.put("usrId", usrId);
//			service.insertSmsNo(result);
//			
//		} catch (Exception ex) {
//    		logger.error("[ICS] Receive Error ->" + ex.getMessage());
//            return new ResponseEntity<>(Constant.FAIL, HttpStatus.OK);
//        }
//
//		logger.info("============ LoginController /sms-check selectSmsReq End ===========");
//		return new ResponseEntity<>(result, HttpStatus.OK);
//	}

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
}
