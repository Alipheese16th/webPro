package com.kist.portal.common.batch;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kist.portal.common.batch.mapper.BatchMapper;
import com.kist.portal.common.email.rest.SendEmailUtil;
import com.kist.portal.common.utils.MultiLanguageUtil;
import com.kist.portal.common.utils.TransactionHandler;

@Component
public class OprbBgnChkJob implements Job {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	BatchMapper mapper;
	
	@Value("${dgrm.front-url}")
	private String frontUrl;
	
	@Autowired
	private SendEmailUtil util;
	
	@Autowired
	private MultiLanguageUtil mUtil;
	
	@Autowired
	TransactionHandler transactionHandler;
	
	private String content = "";
	private String resultCd = "";
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("OprbBgnChkJob start, jobKey: " + context.getJobDetail().getKey().getName());
        
        Map<String, String> param = new HashMap<>();
        param.put("batCd", context.getJobDetail().getKey().getName());
        mapper.insertBatch(param); // start
        logger.info("OprbBgnChkJob batNo : " + param.get("batNo"));
        
        // 배치 실행 중 상태 코드 업데이트
        param.put("resultCd", "EX");
        param.put("content", "");
        mapper.updateBatch(param);
        
        // 실제 배치 실행 후 결과 코드 
        content = "";
        resultCd = "ER";
        transactionHandler.runInTransaction(() -> {
	        try {
	        	Map<String, List<String>> sendUserList = new HashMap<String, List<String>>();
	        	
	        	// 1. 작업 전 점검 생성 - 대상의 사용여부가 Y인 데이터
	        	List<Map<String, Object>> tgtList = mapper.selectOprbBgnChkTgtList(param);
	        	for(int i = 0; i < tgtList.size(); i++) {
	        		Map<String, Object> tgtInfo = tgtList.get(i);
	        		param.put("SESS_LANG", String.valueOf(tgtInfo.get("lang_cd")));
	        		if(tgtInfo.get("lang_cd") == null || String.valueOf(tgtInfo.get("lang_cd")).isEmpty()) {
	        			tgtInfo.put("lang_cd", "KO");
	        		}
	        		
	        		// 1-1. 키 채번
	        		String key = mapper.selectOprbBgnChkKey(tgtInfo);
	        		
	        		tgtInfo.put("oprb_chk_id", key);
	        		
	        		// 1-2. 작업 시작 전 점검 관리 기본 정보 insert 처리
	        		int n = mapper.insertOprbBgnChkBasic(tgtInfo);
	        		
	        		// 1-3. 작업 시작 전 점검 관리 점검 결과 공 데이터 insert 처리
	        		int m = mapper.insertOprbBgnChkEqp(tgtInfo);
	        		
	        		if(n == 0 && m == 0) {
	        			throw new Exception("No Data");
	        		}
	        	}
	        	
	        	// 2. 순회점검 생성 - 대상의 사용여부가 Y인 데이터 조회
	        	List<Map<String, Object>> circList = mapper.selectCircChkTgtList(param);
	        	// 2-1. 주차 정보 데이터 가져오기(홀수 주차일 경우 weekcheck = 1, 요일은 week 1~7)
	        	List<Map<String, Object>> weekInfo = mapper.selectWeekInfo(param);
	        	Map<String, Object> weekData = weekInfo.get(0);
	        	
	        	String weekCheck = String.valueOf(weekData.get("weekcheck"));
	        	String dayofweek = String.valueOf(weekData.get("week"));
	        	
	        	Boolean result = false;
	        	
	        	for(int i = 0; i < circList.size(); i++) {
	        		Map<String, Object> circInfo = circList.get(i);
	        		String cycCd = String.valueOf(circInfo.get("circ_chk_cyc_cd"));
	        		// 주차 검증 로직
	        		if((cycCd.equalsIgnoreCase("CT01") && dayofweek.equalsIgnoreCase("1")) // 월요일
	        		   || (cycCd.equalsIgnoreCase("CT02") && dayofweek.equalsIgnoreCase("2")) // 화요일
	        		   || (cycCd.equalsIgnoreCase("CT03") && dayofweek.equalsIgnoreCase("3")) // 수요일
	        		   || (cycCd.equalsIgnoreCase("CT04") && dayofweek.equalsIgnoreCase("4")) // 목요일
	        		   || (cycCd.equalsIgnoreCase("CT05") && dayofweek.equalsIgnoreCase("5")) // 금요일
	        		   || (cycCd.equalsIgnoreCase("CT06") && weekCheck.equalsIgnoreCase("1") // 격일 1조, 홀수 주차
	        				  && (dayofweek.equalsIgnoreCase("1") || dayofweek.equalsIgnoreCase("3") || dayofweek.equalsIgnoreCase("5")))
	        		   || (cycCd.equalsIgnoreCase("CT06") && weekCheck.equalsIgnoreCase("0") // 격일 1조, 짝수 주차
	        				  && (dayofweek.equalsIgnoreCase("2") || dayofweek.equalsIgnoreCase("4")))
	        		   || (cycCd.equalsIgnoreCase("CT07") && weekCheck.equalsIgnoreCase("1") // 격일 2조, 홀수 주차
	        				  && (dayofweek.equalsIgnoreCase("2") || dayofweek.equalsIgnoreCase("4")))
	        		   || (cycCd.equalsIgnoreCase("CT07") && weekCheck.equalsIgnoreCase("0") // 격일 2조, 짝수 주차
		        				  && (dayofweek.equalsIgnoreCase("1") || dayofweek.equalsIgnoreCase("3") || dayofweek.equalsIgnoreCase("5")))) {	
	        			
	        			// 언어 코드 세팅
	        			param.put("SESS_LANG", String.valueOf(circInfo.get("lang_cd")));
		        		if(circInfo.get("lang_cd") == null || String.valueOf(circInfo.get("lang_cd")).isEmpty()) {
		        			circInfo.put("lang_cd", "KO");
		        		}
		        			
		        		// 2-2. 키 채번
		        		String key = mapper.selectCircChkKey(circInfo);
		        		
		        		circInfo.put("circ_chk_id", key);
		        		
		        		if(sendUserList.containsKey(circInfo.get("circ_chk_supe_usr_id"))) {
		        			List<String> tempList = sendUserList.get(circInfo.get("circ_chk_supe_usr_id"));
		        			tempList.add(key); // 기존 리스트에 키 추가
		        			sendUserList.put(String.valueOf(circInfo.get("circ_chk_supe_usr_id")), tempList); // 점검 유저 아이디를 키로 지정, 리스트 값을 insert
		        		} else {
		        			List<String> tempList = new ArrayList<String>();
		        			tempList.add(key);
		        			sendUserList.put(String.valueOf(circInfo.get("circ_chk_supe_usr_id")), tempList); // 점검 유저 아이디를 키로 지정, 리스트 값을 insert
		        		}
		        		
		        		// 2-3. 순회점검 기본 정보 insert 처리
		        		int n = mapper.insertCircChkBasic(circInfo);
		        		
		        		// 2-4. 순회점검 참석자 insert 처리
		        		int m = mapper.insertCircChkAtnd(circInfo);
		        		
		        		// 2-5. 순회점검 점검 결과 공 데이터 insert 처리
		        		int k = mapper.insertCircChkRslt(circInfo);
		        		
	        		}
	        	}
	        	result = sendCircChkNotiMail(sendUserList);
        		
        		if(!result) {
        			throw new Exception("Send Mail Error");
        		}
	        	
        		// 3. 합동점검 생성 - 대상의 사용여부가 Y인 데이터 조회
        		sendUserList = new HashMap<String, List<String>>();
        		List<Map<String, Object>> jntList = mapper.selectJntChkTgtList(param);
	        	for(int j = 0; j < jntList.size(); j++) {
	        		Map<String, Object> jntInfo = jntList.get(j);
	        		
        			// 언어코드 셋팅
        			param.put("SESS_LANG", String.valueOf(jntInfo.get("lang_cd")));
	        		if(jntInfo.get("lang_cd") == null || String.valueOf(jntInfo.get("lang_cd")).isEmpty()) {
	        			jntInfo.put("lang_cd", "KO");
	        		}
	        		
	        		// 3-1. 키 채번
	        		String key = mapper.selectJntChkKey(jntInfo);
	        		// 채번한 키 셋팅
	        		jntInfo.put("jnt_chk_id", key);

	        		// 3-2. 합동점검 기본 정보 insert 처리
	        		int n = mapper.insertJntChkBasic(jntInfo);
	        		
	        		// 3-3. 합동점검 참석자 insert 처리
	        		int m = mapper.insertJntChkAtnd(jntInfo);

	        		// 메일 수신자 셋팅
		        	sendUserList = new HashMap<String, List<String>>();
	        		if(sendUserList.containsKey(jntInfo.get("jnt_chk_supe_usr_id"))) {
	        			List<String> tempList = sendUserList.get(jntInfo.get("jnt_chk_supe_usr_id"));
	        			tempList.add(key); // 기존 리스트에 키 추가
	        			sendUserList.put(String.valueOf(jntInfo.get("jnt_chk_supe_usr_id")), tempList); // 점검 유저 아이디를 키로 지정, 리스트 값을 insert
	        		} else {
	        			List<String> tempList = new ArrayList<String>();
	        			tempList.add(key);
	        			sendUserList.put(String.valueOf(jntInfo.get("jnt_chk_supe_usr_id")), tempList); // 점검 유저 아이디를 키로 지정, 리스트 값을 insert
	        		}
	        	}
	        	
	        	Boolean result2 = sendJntChkNotiMail(sendUserList);
        		
        		if(!result2) {
        			throw new Exception("Send Mail Error");
        		}
	        	
		        resultCd = "SU";
	        } catch(Exception e) {
	        	e.printStackTrace();
	        	resultCd = "ER";
	        	content = e.getMessage();
	        }
        });
        param.put("resultCd", resultCd);
        param.put("content", content);
        mapper.updateBatch(param); // end
    }
	
	public void executeManual() throws JobExecutionException {
        logger.info("OprbBgnChkJob start, jobKey: " + "BS001");
        
        Map<String, String> param = new HashMap<>();
        param.put("batCd", "BS001");
        mapper.insertBatch(param); // start
        logger.info("OprbBgnChkJob batNo : " + param.get("batNo"));
        
        // 배치 실행 중 상태 코드 업데이트
        param.put("resultCd", "EX");
        param.put("content", "");
        mapper.updateBatch(param);
        
        // 실제 배치 실행 후 결과 코드 
        content = "";
        resultCd = "ER";
        transactionHandler.runInTransaction(() -> {
	        try {
	        	Map<String, List<String>> sendUserList = new HashMap<String, List<String>>();
	        	
	        	// 1. 작업 전 점검 생성 - 대상의 사용여부가 Y인 데이터
	        	List<Map<String, Object>> tgtList = mapper.selectOprbBgnChkTgtList(param);
	        	for(int i = 0; i < tgtList.size(); i++) {
	        		Map<String, Object> tgtInfo = tgtList.get(i);
	        		param.put("SESS_LANG", String.valueOf(tgtInfo.get("lang_cd")));
	        		if(tgtInfo.get("lang_cd") == null || String.valueOf(tgtInfo.get("lang_cd")).isEmpty()) {
	        			tgtInfo.put("lang_cd", "KO");
	        		}
	        		
	        		// 1-1. 키 채번
	        		String key = mapper.selectOprbBgnChkKey(tgtInfo);
	        		
	        		tgtInfo.put("oprb_chk_id", key);
	        		
	        		// 1-2. 작업 시작 전 점검 관리 기본 정보 insert 처리
	        		int n = mapper.insertOprbBgnChkBasic(tgtInfo);
	        		
	        		// 1-3. 작업 시작 전 점검 관리 점검 결과 공 데이터 insert 처리
	        		int m = mapper.insertOprbBgnChkEqp(tgtInfo);
	        	}
	        	
	        	// 2. 순회점검 생성 - 대상의 사용여부가 Y인 데이터 조회
	        	List<Map<String, Object>> circList = mapper.selectCircChkTgtList(param);
	        	// 2-1. 주차 정보 데이터 가져오기(홀수 주차일 경우 weekcheck = 1, 요일은 week 1~7)
	        	List<Map<String, Object>> weekInfo = mapper.selectWeekInfo(param);
	        	Map<String, Object> weekData = weekInfo.get(0);
	        	
	        	String weekCheck = String.valueOf(weekData.get("weekcheck"));
	        	String dayofweek = String.valueOf(weekData.get("week"));
	        	
	        	Boolean result = false;
	        	
	        	for(int i = 0; i < circList.size(); i++) {
	        		Map<String, Object> circInfo = circList.get(i);
	        		String cycCd = String.valueOf(circInfo.get("circ_chk_cyc_cd"));
	        		// 주차 검증 로직
	        		if((cycCd.equalsIgnoreCase("CT01") && dayofweek.equalsIgnoreCase("1")) // 월요일
	        		   || (cycCd.equalsIgnoreCase("CT02") && dayofweek.equalsIgnoreCase("2")) // 화요일
	        		   || (cycCd.equalsIgnoreCase("CT03") && dayofweek.equalsIgnoreCase("3")) // 수요일
	        		   || (cycCd.equalsIgnoreCase("CT04") && dayofweek.equalsIgnoreCase("4")) // 목요일
	        		   || (cycCd.equalsIgnoreCase("CT05") && dayofweek.equalsIgnoreCase("5")) // 금요일
	        		   || (cycCd.equalsIgnoreCase("CT06") && weekCheck.equalsIgnoreCase("1") // 격일 1조, 홀수 주차
	        				  && (dayofweek.equalsIgnoreCase("1") || dayofweek.equalsIgnoreCase("3") || dayofweek.equalsIgnoreCase("5")))
	        		   || (cycCd.equalsIgnoreCase("CT06") && weekCheck.equalsIgnoreCase("0") // 격일 1조, 짝수 주차
	        				  && (dayofweek.equalsIgnoreCase("2") || dayofweek.equalsIgnoreCase("4")))
	        		   || (cycCd.equalsIgnoreCase("CT07") && weekCheck.equalsIgnoreCase("1") // 격일 2조, 홀수 주차
	        				  && (dayofweek.equalsIgnoreCase("2") || dayofweek.equalsIgnoreCase("4")))
	        		   || (cycCd.equalsIgnoreCase("CT07") && weekCheck.equalsIgnoreCase("0") // 격일 2조, 짝수 주차
		        				  && (dayofweek.equalsIgnoreCase("1") || dayofweek.equalsIgnoreCase("3") || dayofweek.equalsIgnoreCase("5")))) {	
	        			
	        			// 조건을 만족할 경우
	        			param.put("SESS_LANG", String.valueOf(circInfo.get("lang_cd")));
		        		if(circInfo.get("lang_cd") == null || String.valueOf(circInfo.get("lang_cd")).isEmpty()) {
		        			circInfo.put("lang_cd", "KO");
		        		}
		        			
		        		// 2-2. 키 채번
		        		String key = mapper.selectCircChkKey(circInfo);
		        		
		        		circInfo.put("circ_chk_id", key);
		        		
		        		if(sendUserList.containsKey(circInfo.get("circ_chk_supe_usr_id"))) {
		        			List<String> tempList = sendUserList.get(circInfo.get("circ_chk_supe_usr_id"));
		        			tempList.add(key); // 기존 리스트에 키 추가
		        			sendUserList.put(String.valueOf(circInfo.get("circ_chk_supe_usr_id")), tempList); // 점검 유저 아이디를 키로 지정, 리스트 값을 insert
		        		} else {
		        			List<String> tempList = new ArrayList<String>();
		        			tempList.add(key);
		        			sendUserList.put(String.valueOf(circInfo.get("circ_chk_supe_usr_id")), tempList); // 점검 유저 아이디를 키로 지정, 리스트 값을 insert
		        		}
		        		
		        		// 2-3. 순회점검 기본 정보 insert 처리
		        		int n = mapper.insertCircChkBasic(circInfo);
		        		
		        		// 2-4. 순회점검 참석자 insert 처리
		        		int m = mapper.insertCircChkAtnd(circInfo);
		        		
		        		// 2-5. 순회점검 점검 결과 공 데이터 insert 처리
		        		int k = mapper.insertCircChkRslt(circInfo);
	        		}
	        	}
	        	result = sendCircChkNotiMail(sendUserList);
        		
        		if(!result) {
        			throw new Exception("Send Mail Error");
        		}
	        	
	        	// 3. 합동점검 생성 - 대상의 사용여부가 Y인 데이터 조회
        		sendUserList = new HashMap<String, List<String>>();
        		List<Map<String, Object>> jntList = mapper.selectJntChkTgtList(param);
	        	for(int j = 0; j < jntList.size(); j++) {
	        		Map<String, Object> jntInfo = jntList.get(j);
	        		
        			// 언어코드 셋팅
        			param.put("SESS_LANG", String.valueOf(jntInfo.get("lang_cd")));
	        		if(jntInfo.get("lang_cd") == null || String.valueOf(jntInfo.get("lang_cd")).isEmpty()) {
	        			jntInfo.put("lang_cd", "KO");
	        		}
	        		
	        		// 3-1. 키 채번
	        		String key = mapper.selectJntChkKey(jntInfo);
	        		// 채번한 키 셋팅
	        		jntInfo.put("jnt_chk_id", key);

	        		// 3-2. 합동점검 기본 정보 insert 처리
	        		int n = mapper.insertJntChkBasic(jntInfo);
	        		
	        		// 3-3. 합동점검 참석자 insert 처리
	        		int m = mapper.insertJntChkAtnd(jntInfo);

	        		// 메일 수신자 셋팅
		        	sendUserList = new HashMap<String, List<String>>();
	        		if(sendUserList.containsKey(jntInfo.get("jnt_chk_supe_usr_id"))) {
	        			List<String> tempList = sendUserList.get(jntInfo.get("jnt_chk_supe_usr_id"));
	        			tempList.add(key); // 기존 리스트에 키 추가
	        			sendUserList.put(String.valueOf(jntInfo.get("jnt_chk_supe_usr_id")), tempList); // 점검 유저 아이디를 키로 지정, 리스트 값을 insert
	        		} else {
	        			List<String> tempList = new ArrayList<String>();
	        			tempList.add(key);
	        			sendUserList.put(String.valueOf(jntInfo.get("jnt_chk_supe_usr_id")), tempList); // 점검 유저 아이디를 키로 지정, 리스트 값을 insert
	        		}
	        	}
	        	
	        	Boolean result2 = sendJntChkNotiMail(sendUserList);
        		if(!result2) {
        			throw new Exception("Send Mail Error");
        		}
	        	
		        resultCd = "SU";
	        } catch(Exception e) {
	        	e.printStackTrace();
	        	resultCd = "ER";
	        	content = e.getMessage();
	        }
        });
        param.put("resultCd", resultCd);
        param.put("content", content);
        mapper.updateBatch(param); // end
    }
	
	@Transactional
	public boolean sendCircChkNotiMail(Map<String, List<String>> sendUserList) throws Exception {		
		Map<String, Object> temp = new HashMap<>();		
		
		// 미점검 점검일 초과 유저 조회
		List<String> userList = new ArrayList<String>(sendUserList.keySet());
		
		Boolean mailResult = true;
		
		for(int i = 0; i < userList.size(); i++) {
			temp.put("chkList", "Y");
			String sendUser = String.valueOf(userList.get(i));
			// 유저 정보 조회
			temp.put("usr_id", sendUser);
			List<Map<String, ?>> tempInfo = mapper.selectUserInfo(temp);
			
			temp.put("circ_chk_supe_usr_id", sendUser);
			temp.put("lang_cd", String.valueOf(tempInfo.get(0).get("lang_cd")));
			// 수신자 학인
			String[] to = new String[1];
			// 수신자 저장
			to[0] = String.valueOf(sendUser);
			
			// 순회 점검 리스트 조회
			temp.put("keyList", sendUserList.get(sendUser));
			List<Map<String, Object>> circList = mapper.selectCircMailList(temp);
			
			// 다국어 처리
			String[] mlangNo = {"LB00000508", "LB00000509", "LB00000510"
								, "LB00000511", "LB00000021", "LB00000024"
								, "LB00000025", "LB00000512", "LB00000143"
								, "LB00000449", "LB00000275", "LB00000640"
								, "LB00000739", "LB00000681", "LB00000679"};
			List<Map<String, String>> mlangCntn = mUtil.getMLangList(Arrays.asList(mlangNo), String.valueOf(circList.get(0).get("lang_cd")));
			// 메일 업무 코드(정의 필요)
			String bizCd = "30";
			// 메일 제목(정의 필요)
			String title = "순회 점검 생성 목록 확인 메일(" + circList.size() + " 건)";
			// 메일 내용(정의 필요)
			String content = "<table class=\"txc-table\" width=\"846\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"border:none;border-collapse:collapse;word-break:break-all;font-family:&quot;Malgun Gothic&quot;, &quot;맑은 고딕&quot;;font-size:13.3333px\">\r\n" + 
					"    <tbody>\r\n" + 
					"        <tr>\r\n" + 
					"            <td style=\"width: 20%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(234, 234, 234);\">\r\n" + 
					"                <p style=\"text-align: center;\"><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000021") + "</span><br></p>\r\n" + 
					"            </td>\r\n" + 
					"            <td style=\"width: 20%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(234, 234, 234);\">\r\n" + 
					"                <p style=\"font-weight: bold; text-align: center;\"><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000640") + "</span></p>\r\n" + 
					"            </td>\r\n" + 
					"            <td style=\"width: 20%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(234, 234, 234);\">\r\n" + 
					"                <p style=\"font-weight: bold; text-align: center;\"><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000739") + "</span></p>\r\n" + 
					"            </td>\r\n" + 
					"            <td style=\"width: 20%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(234, 234, 234);\">\r\n" + 
					"                <p style=\"font-weight: bold; text-align: center;\"><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000681") + "</span></p>\r\n" + 
					"            </td>\r\n" + 
					"            <td style=\"width: 20%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(234, 234, 234);\">\r\n" + 
					"                <p style=\"text-align: center;\"><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000679") + "</span></p>\r\n" + 
					"            </td>\r\n" + 
					"        </tr>";
			for(int j = 0; j < circList.size(); j++) {
				Map<String, Object> tempData = circList.get(j);
				if(String.valueOf(tempData.get("gb")).equalsIgnoreCase("N")){
					content += "<tr>\r\n" + 
							"            <td style=\"width:20%;height:24px;border-bottom:1px solid #ccc;border-right:1px solid #ccc;border-left:1px solid #ccc;padding:0px 3px 0px 3px;;\">\r\n" + 
							"                <p>" + String.valueOf(tempData.get("wkpl_nm")) + "</p>\r\n" + 
							"            </td>\r\n" + 
							"            <td style=\"width:20%;height:24px;border-bottom:1px solid #ccc;border-right:1px solid #ccc;padding:0px 3px 0px 3px;;\">\r\n" + 
							"                <p style=\"text-decoration: underline;\"><a href=\"" + frontUrl + "/sftchk/wknstourchkmgntdtledt/" + String.valueOf(tempData.get("circ_chk_id")) + "\"" + ">" + String.valueOf(tempData.get("circ_chk_tgt_nm")) + "</a></p>\r\n" + 
							"            </td>\r\n" + 
							"            <td style=\"width:20%;height:24px;border-bottom:1px solid #ccc;border-right:1px solid #ccc;padding:0px 3px 0px 3px;;\">\r\n" + 
							"                <p>" + String.valueOf(tempData.get("circ_chk_wkpl_loc_nm")) + "</p>\r\n" + 
							"            </td>\r\n" + 
							"            <td style=\"width:20%;height:24px;border-bottom:1px solid #ccc;border-right:1px solid #ccc;padding:0px 3px 0px 3px;;\">\r\n" + 
							"                <p>" + String.valueOf(tempData.get("circ_chk_dtl_loc_nm")) + "</p>\r\n" + 
							"            </td>\r\n" + 
							"            <td style=\"width:20%;height:24px;border-bottom:1px solid #ccc;border-right:1px solid #ccc;padding:0px 3px 0px 3px;;\">\r\n" + 
							"                <p>" + String.valueOf(tempData.get("circ_chk_supe_usr_nm")) + "</p>\r\n" + 
							"            </td>\r\n" + 
							"        </tr>";
				} else {
					content += "<tr>\r\n" + 
							"            <td style=\"width:20%;height:24px;border-bottom:1px solid #ccc;border-right:1px solid #ccc;border-left:1px solid #ccc;padding:0px 3px 0px 3px;;\">\r\n" + 
							"                <p>" + String.valueOf(tempData.get("wkpl_nm")) + "</p>\r\n" + 
							"            </td>\r\n" + 
							"            <td style=\"width:20%;height:24px;border-bottom:1px solid #ccc;border-right:1px solid #ccc;padding:0px 3px 0px 3px;;\">\r\n" + 
							"                <p style=\"text-decoration: underline;\"><a href=\"" + frontUrl + "/sftchk/wknstourchkmgntchdtledt/" + String.valueOf(tempData.get("circ_chk_id")) + "\"" + ">" + String.valueOf(tempData.get("circ_chk_tgt_nm")) + "</a></p>\r\n" + 
							"            </td>\r\n" + 
							"            <td style=\"width:20%;height:24px;border-bottom:1px solid #ccc;border-right:1px solid #ccc;padding:0px 3px 0px 3px;;\">\r\n" + 
							"                <p>" + String.valueOf(tempData.get("circ_chk_wkpl_loc_nm")) + "</p>\r\n" + 
							"            </td>\r\n" + 
							"            <td style=\"width:20%;height:24px;border-bottom:1px solid #ccc;border-right:1px solid #ccc;padding:0px 3px 0px 3px;;\">\r\n" + 
							"                <p>" + String.valueOf(tempData.get("circ_chk_dtl_loc_nm")) + "</p>\r\n" + 
							"            </td>\r\n" + 
							"            <td style=\"width:20%;height:24px;border-bottom:1px solid #ccc;border-right:1px solid #ccc;padding:0px 3px 0px 3px;;\">\r\n" + 
							"                <p>" + String.valueOf(tempData.get("circ_chk_supe_usr_nm")) + "</p>\r\n" + 
							"            </td>\r\n" + 
							"        </tr>";
				}
			}
			content += "</tbody>\r\n" + 
					"</table>";
			// 메일 송신
			mailResult = util.sendMail(bizCd, "AC928960", title, content, to);
			// 메일 결과에 따라 메일 전송 여부 Y로 체크
			if(mailResult) {
				// 전송 성공시
			} else {
				throw new Exception("Send Mail Error");
			}
		}
		
		return mailResult; 
	}
	
	@Transactional
	public boolean sendJntChkNotiMail(Map<String, List<String>> sendUserList) throws Exception {		
		Map<String, Object> temp = new HashMap<>();
		
		// 메일 생성 유저목록 조회
		List<String> userList = new ArrayList<String>(sendUserList.keySet());
		
		Boolean mailResult = true;
		
		for(int i = 0; i < userList.size(); i++) {
			temp.put("chkList", "Y");
			String sendUser = String.valueOf(userList.get(i));
			// 유저 정보 조회
			temp.put("usr_id", sendUser);
			List<Map<String, ?>> tempInfo = mapper.selectUserInfo(temp);
			
			temp.put("jnt_chk_supe_usr_id", sendUser);
			temp.put("lang_cd", String.valueOf(tempInfo.get(0).get("lang_cd")));
			// 수신자 학인
			String[] to = new String[1];
			// 수신자 저장
			to[0] = String.valueOf(sendUser);
			// 수신자 저장(임시)
			// to[0] = "AC926550";
			
			// 순회 점검 리스트 조회
			temp.put("keyList", sendUserList.get(sendUser));
			List<Map<String, Object>> jntList = mapper.selectJntMailList(temp);
			
			// 다국어 처리
			String[] mlangNo = {"LB00000508", "LB00000509", "LB00000510"
								, "LB00000511", "LB00000021", "LB00000024"
								, "LB00000025", "LB00000512", "LB00000143"
								, "LB00000449", "LB00000275", "LB00000640"
								, "LB00001013", "LB00000679"};
			List<Map<String, String>> mlangCntn = mUtil.getMLangList(Arrays.asList(mlangNo), String.valueOf(jntList.get(0).get("lang_cd")));
			// 메일 업무 코드(정의 필요)
			String bizCd = "31";
			// 메일 제목(정의 필요)
			String title = "합동 점검 생성 목록 확인 메일(" + jntList.size() + " 건)";
			// 메일 내용(정의 필요)
			String content = "<table class=\"txc-table\" width=\"846\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"border:none;border-collapse:collapse;word-break:break-all;font-family:&quot;Malgun Gothic&quot;, &quot;맑은 고딕&quot;;font-size:13.3333px\">\r\n" + 
					"    <tbody>\r\n" + 
					"        <tr>\r\n" + 
					"            <td style=\"width: 25%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(234, 234, 234);\">\r\n" + 
					"                <p style=\"text-align: center;\"><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000021") + "</span><br></p>\r\n" + 
					"            </td>\r\n" + 
					"            <td style=\"width: 25%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(234, 234, 234);\">\r\n" + 
					"                <p style=\"font-weight: bold; text-align: center;\"><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000640") + "</span></p>\r\n" + 
					"            </td>\r\n" + 
					"            <td style=\"width: 25%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(234, 234, 234);\">\r\n" + 
					"                <p style=\"font-weight: bold; text-align: center;\"><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00001013") + "</span></p>\r\n" + 
					"            </td>\r\n" + 
					"            <td style=\"width: 25%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(234, 234, 234);\">\r\n" + 
					"                <p style=\"text-align: center;\"><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000679") + "</span></p>\r\n" + 
					"            </td>\r\n" + 
					"        </tr>";
			for(int j = 0; j < jntList.size(); j++) {
				Map<String, Object> tempData = jntList.get(j);
				content += "<tr>\r\n" + 
						"            <td style=\"width:25%;height:24px;border-bottom:1px solid #ccc;border-right:1px solid #ccc;border-left:1px solid #ccc;padding:0px 3px 0px 3px;;\">\r\n" + 
						"                <p>" + String.valueOf(tempData.get("wkpl_nm")) + "</p>\r\n" + 
						"            </td>\r\n" + 
						"            <td style=\"width:25%;height:24px;border-bottom:1px solid #ccc;border-right:1px solid #ccc;padding:0px 3px 0px 3px;;\">\r\n" + 
						"                <p style=\"text-decoration: underline;\"><a href=\"" + frontUrl + "/sftchk/wknscoprchkmgntdtledt/" + String.valueOf(tempData.get("jnt_chk_id")) + "\"" + ">" + String.valueOf(tempData.get("jnt_chk_tgt_nm")) + "</a></p>\r\n" + 
						"            </td>\r\n" + 
						"            <td style=\"width:25%;height:24px;border-bottom:1px solid #ccc;border-right:1px solid #ccc;padding:0px 3px 0px 3px;;\">\r\n" + 
						"                <p>" + String.valueOf(tempData.get("jnt_chk_dtl_loc_nm")) + "</p>\r\n" + 
						"            </td>\r\n" + 
						"            <td style=\"width:25%;height:24px;border-bottom:1px solid #ccc;border-right:1px solid #ccc;padding:0px 3px 0px 3px;;\">\r\n" + 
						"                <p>" + String.valueOf(tempData.get("jnt_chk_supe_usr_nm")) + "</p>\r\n" + 
						"            </td>\r\n" + 
						"        </tr>";
			}
			content += "</tbody>\r\n" + 
					"</table>";
			// 메일 송신
			mailResult = util.sendMail(bizCd, "AC928960", title, content, to);
			// 메일 결과에 따라 메일 전송 여부 Y로 체크
			if(mailResult) {
				// 전송 성공시
			} else {
				throw new Exception("Send Mail Error");
			}
		}
		
		return mailResult; 
	}
}
