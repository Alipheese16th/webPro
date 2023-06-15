package com.kist.portal.common.batch;

import java.util.Arrays;
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
public class RgltChkNotiJob implements Job {
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
        logger.info("RgltChkNotiJob start, jobKey: " + context.getJobDetail().getKey().getName());
        
        Map<String, String> param = new HashMap<>();
        param.put("batCd", context.getJobDetail().getKey().getName());
        mapper.insertBatch(param); // start
        logger.info("RgltChkNotiJob batNo : " + param.get("batNo"));
        
        // 배치 실행 중 상태 코드 업데이트
        param.put("resultCd", "EX");
        param.put("content", "");
        mapper.updateBatch(param);
        
        // 실제 배치 실행 후 결과 코드 
        content = "";
        resultCd = "ER";
        transactionHandler.runInTransaction(() -> {
	        try {
	        	Boolean result = false;
	        	// 1. 규제, MSDS 대상 유저 조회
	        	List<Map<String, String>> usrList = mapper.selectMsdsRgltUsrList(param);
	        	for(int i = 0; i < usrList.size(); i++) {
	        		Map<String, String> tempUsr = usrList.get(i);
	        		param.put("SESS_LANG", tempUsr.get("lang_cd"));
	        		if(tempUsr.get("lang_cd") == null || tempUsr.get("lang_cd").isEmpty()) {
	        			tempUsr.put("lang_cd", "KO");
	        		}
	        		
	        		// 1-1. 해당 유저 규제 검토 진행 목록 데이터 조회
	        		List<Map<String, ?>> rgltList = mapper.selectRgltList(tempUsr);

	        		// 1-2. 해당 유저 규제 검토 진행 목록 데이터 조회(MSDS 제출 대상)
	        		List<Map<String, ?>> rgltList2 = mapper.selectRgltList2(tempUsr);	// 쿼리확인 필요
	        		
	        		// 1-3. 해당 유저 MSDS 개정 진행 목록 데이터 조회
	        		List<Map<String, ?>> msdsList = mapper.selectMsdsList(tempUsr);

	        		// 1-4. 해당 유저 MSDS 개정 진행 목록 데이터 조회(MSDS 재제출 대상)
	        		List<Map<String, ?>> msdsList2 = mapper.selectMsdsList2(tempUsr);	// 쿼리확인 필요
	        		
	        		// 1-5. 알림 메일 전송
	        		if(rgltList.size() > 0 || msdsList.size() > 0) {
	        			result = sendRgltMsdsNotiMail(tempUsr, rgltList, rgltList2, msdsList, msdsList2);
	        		} else {
	        			result = true;
	        		}
	        		
	        		if(!result) {
	        			throw new Exception("Send Mail Error");
	        		}
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
        logger.info("RgltChkNotiJob start, jobKey: " + "BR004");
        
        Map<String, String> param = new HashMap<>();
        param.put("batCd", "BR004");
        mapper.insertBatch(param); // start
        logger.info("RgltChkNotiJob batNo : " + param.get("batNo"));
        
        // 배치 실행 중 상태 코드 업데이트
        param.put("resultCd", "EX");
        param.put("content", "");
        mapper.updateBatch(param);
        
        // 실제 배치 실행 후 결과 코드 
        content = "";
        resultCd = "ER";
        transactionHandler.runInTransaction(() -> {
	        try {
	        	Boolean result = false;
	        	// 1. 규제, MSDS 대상 유저 조회
	        	List<Map<String, String>> usrList = mapper.selectMsdsRgltUsrList(param);
	        	for(int i = 0; i < usrList.size(); i++) {
	        		Map<String, String> tempUsr = usrList.get(i);
	        		param.put("SESS_LANG", tempUsr.get("lang_cd"));
	        		if(tempUsr.get("lang_cd") == null || tempUsr.get("lang_cd").isEmpty()) {
	        			tempUsr.put("lang_cd", "KO");
	        		}
	        		
	        		// 1-1. 해당 유저 규제 검토 진행 목록 데이터 조회
	        		List<Map<String, ?>> rgltList = mapper.selectRgltList(tempUsr);

	        		// 1-2. 해당 유저 규제 검토 진행 목록 데이터 조회(MSDS 제출 대상)
	        		List<Map<String, ?>> rgltList2 = mapper.selectRgltList2(tempUsr);	// 쿼리확인 필요
	        		
	        		// 1-3. 해당 유저 MSDS 개정 진행 목록 데이터 조회
	        		List<Map<String, ?>> msdsList = mapper.selectMsdsList(tempUsr);

	        		// 1-4. 해당 유저 MSDS 개정 진행 목록 데이터 조회(MSDS 재제출 대상)
	        		List<Map<String, ?>> msdsList2 = mapper.selectMsdsList(tempUsr);	// 쿼리확인 필요
	        		
	        		// 1-5. 알림 메일 전송
	        		if(rgltList.size() > 0 || msdsList.size() > 0) {
	        			result = sendRgltMsdsNotiMail(tempUsr, rgltList, rgltList2, msdsList, msdsList2);
	        		} else {
	        			result = true;
	        		}
	        		
	        		if(!result) {
	        			throw new Exception("Send Mail Error");
	        		}
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
	public boolean sendRgltMsdsNotiMail(Map<String, String> recvUsr, List<Map<String, ?>> rgltList, List<Map<String, ?>> rgltList2, List<Map<String, ?>> msdsList, List<Map<String, ?>> msdsList2) throws Exception {		
		Map<String, String> temp = new HashMap<>();
		
		// 수신자 학인
		String[] to = new String[1];
		
		Boolean mailResult = true;
		// 수신자 저장
		to[0] = recvUsr.get("usr");
		// 수신자 저장(임시)
		// to[0] = "AC926550";
		// 다국어 처리
		String[] mlangNo = {"LB00000508", "LB00000509", "LB00000510"
							, "LB00000511", "LB00000021", "LB00000024"
							, "LB00000025", "LB00000512", "LB00000143"
							, "LB00000449", "LB00000275", "LB00000621", "LB00000622"};
		List<Map<String, String>> mlangCntn = mUtil.getMLangList(Arrays.asList(mlangNo), recvUsr.get("lang_cd"));
		// 메일 업무 코드
		String bizCd = "23";
		// 메일 제목
		String subject =  mUtil.getMLang(mlangCntn, "LB00000509");
		// 규제 테이블 동적 생성
		String rgltTable = "";
		if(rgltList.size() > 0) {
			rgltTable += "<p style=\"\">" + mUtil.getMLang(mlangCntn, "LB00000510") + "</p>\r\n";
			rgltTable += "<table class=\"txc-table\" width=\"846\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"border:none;border-collapse:collapse;word-break:break-all;font-family:&quot;Malgun Gothic&quot;, &quot;맑은 고딕&quot;;font-size:13.3333px\">\r\n";
			rgltTable += "    <tbody>\r\n" + 
					"        <tr>\r\n" + 
					"            <td style=\"width: 110px; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"                <p style=\"text-align: center; font-weight: bold;\"><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000511") + "</span></p>\r\n" + 
					"            </td>\r\n" + 
					"            <td style=\"width: 86px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"                <p style=\"text-align: center; font-weight: bold;\"><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000021") + "</span></p>\r\n" + 
					"            </td>\r\n" + 
					"            <td style=\"width: 92px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"                <p style=\"text-align: center; font-weight: bold;\"><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000024") + "</span></p>\r\n" + 
					"            </td>\r\n" + 
					"            <td style=\"width: 257px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"                <p style=\"text-align: center; font-weight: bold;\"><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000025") + "</span></p>\r\n" + 
					"            </td>\r\n" + 
					"            <td style=\"width: 108px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"                <p style=\"text-align: center; font-weight: bold;\"><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000512") + "</span></p>\r\n" + 
					"            </td>\r\n" + 
					"            <td style=\"width: 93px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"                <p style=\"text-align: center; font-weight: bold;\"><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000143") + "</span></p>\r\n" + 
					"            </td>\r\n" + 
					"            <td style=\"width: 86px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"                <p style=\"text-align: center; font-weight: bold;\"><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000449") + "</span></p>\r\n" + 
					"            </td>\r\n" + 
					"        </tr>\r\n";
			for(int i = 0; i < rgltList.size(); i++) {
				rgltTable += "        <tr>\r\n" + 
						"            <td style=\"width: 110px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
						"                <p style=\"text-align: center;\">" + rgltList.get(i).get("rglt_chk_no") + "<br></p>\r\n" + 
						"            </td>\r\n" + 
						"            <td style=\"width: 86px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
						"                <p style=\"text-align: left;\">" + rgltList.get(i).get("wkpl_nm") + "<br></p>\r\n" + 
						"            </td>\r\n" + 
						"            <td style=\"width: 92px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
						"                <p style=\"text-align: center;\">" + rgltList.get(i).get("mtrl_no") + "<br></p>\r\n" + 
						"            </td>\r\n" + 
						"            <td style=\"width: 257px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
						"                <p>" + rgltList.get(i).get("mtrl_nm") + "<br></p>\r\n" + 
						"            </td>\r\n" + 
						"            <td style=\"width: 108px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
						"                <p style=\"text-align: center;\">" + rgltList.get(i).get("crt_da") + "<br></p>\r\n" + 
						"            </td>\r\n" + 
						"            <td style=\"width: 93px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
						"                <p style=\"text-align: center;\">" + rgltList.get(i).get("rglt_chk_req_prog_stt_nm") + "<br></p>\r\n" + 
						"            </td>\r\n" + 
						"            <td style=\"width: 86px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
						"                <p style=\"text-align: center;\"><a style=\"font-weight: bold;\" href=\"" + frontUrl + "" + rgltList.get(i).get("url") + "\">" + mUtil.getMLang(mlangCntn, "LB00000449") + "</a><br></p>\r\n" + 
						"            </td>\r\n" + 
						"        </tr>\r\n" ;
			}
			rgltTable += "    </tbody>\r\n" + 
					"</table>\r\n";
		}
		String rgltTable2 = "";
		if(rgltList2.size() > 0) {
			rgltTable2 += "<p style=\"\">" + mUtil.getMLang(mlangCntn, "LB00000621") + "</p>\r\n";
			rgltTable2 += "<table class=\"txc-table\" width=\"846\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"border:none;border-collapse:collapse;word-break:break-all;font-family:&quot;Malgun Gothic&quot;, &quot;맑은 고딕&quot;;font-size:13.3333px\">\r\n";
			rgltTable2 += "    <tbody>\r\n" + 
					"        <tr>\r\n" + 
					"            <td style=\"width: 110px; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"                <p style=\"text-align: center; font-weight: bold;\"><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000511") + "</span></p>\r\n" + 
					"            </td>\r\n" + 
					"            <td style=\"width: 86px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"                <p style=\"text-align: center; font-weight: bold;\"><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000021") + "</span></p>\r\n" + 
					"            </td>\r\n" + 
					"            <td style=\"width: 92px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"                <p style=\"text-align: center; font-weight: bold;\"><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000024") + "</span></p>\r\n" + 
					"            </td>\r\n" + 
					"            <td style=\"width: 257px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"                <p style=\"text-align: center; font-weight: bold;\"><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000025") + "</span></p>\r\n" + 
					"            </td>\r\n" + 
					"            <td style=\"width: 108px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"                <p style=\"text-align: center; font-weight: bold;\"><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000512") + "</span></p>\r\n" + 
					"            </td>\r\n" + 
					"            <td style=\"width: 93px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"                <p style=\"text-align: center; font-weight: bold;\"><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000143") + "</span></p>\r\n" + 
					"            </td>\r\n" + 
					"            <td style=\"width: 86px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"                <p style=\"text-align: center; font-weight: bold;\"><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000449") + "</span></p>\r\n" + 
					"            </td>\r\n" + 
					"        </tr>\r\n";
			for(int i = 0; i < rgltList2.size(); i++) {
				rgltTable2 += "        <tr>\r\n" + 
						"            <td style=\"width: 110px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
						"                <p style=\"text-align: center;\">" + rgltList2.get(i).get("rglt_chk_no") + "<br></p>\r\n" + 
						"            </td>\r\n" + 
						"            <td style=\"width: 86px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
						"                <p style=\"text-align: left;\">" + rgltList2.get(i).get("wkpl_nm") + "<br></p>\r\n" + 
						"            </td>\r\n" + 
						"            <td style=\"width: 92px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
						"                <p style=\"text-align: center;\">" + rgltList2.get(i).get("mtrl_no") + "<br></p>\r\n" + 
						"            </td>\r\n" + 
						"            <td style=\"width: 257px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
						"                <p>" + rgltList2.get(i).get("mtrl_nm") + "<br></p>\r\n" + 
						"            </td>\r\n" + 
						"            <td style=\"width: 108px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
						"                <p style=\"text-align: center;\">" + rgltList2.get(i).get("crt_da") + "<br></p>\r\n" + 
						"            </td>\r\n" + 
						"            <td style=\"width: 93px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
						"                <p style=\"text-align: center;\">" + rgltList2.get(i).get("rglt_chk_req_prog_stt_nm") + "<br></p>\r\n" + 
						"            </td>\r\n" + 
						"            <td style=\"width: 86px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
						"                <p style=\"text-align: center;\"><a style=\"font-weight: bold;\" href=\"" + frontUrl + "" + rgltList2.get(i).get("url") + "\">" + mUtil.getMLang(mlangCntn, "LB00000449") + "</a><br></p>\r\n" + 
						"            </td>\r\n" + 
						"        </tr>\r\n" ;
			}
			rgltTable2 += "    </tbody>\r\n" + 
					"</table>\r\n";
		}
		String msdsTable = "";
		if(msdsList.size() > 0) {
			msdsTable += "<p style=\"\">" + mUtil.getMLang(mlangCntn, "LB00000508") + "</p>\r\n";
			msdsTable += "<table class=\"txc-table\" width=\"846\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"border:none;border-collapse:collapse;word-break:break-all;font-family:&quot;Malgun Gothic&quot;, &quot;맑은 고딕&quot;;font-size:13.3333px\">\r\n";
			msdsTable += "    <tbody>\r\n" + 
					"        <tr>\r\n" + 
					"            <td style=\"width: 112px; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"                <p style=\"text-align: center;\"><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000275") + "</span><br></p>\r\n" + 
					"            </td>\r\n" + 
					"            <td style=\"width: 83px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"                <p style=\"text-align: center; font-weight: bold;\"><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000021") + "</span></p>\r\n" + 
					"            </td>\r\n" + 
					"            <td style=\"width: 92px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"                <p style=\"text-align: center; font-weight: bold;\"><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000024") + "</span></p>\r\n" + 
					"            </td>\r\n" + 
					"            <td style=\"width: 259px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"                <p style=\"text-align: center; font-weight: bold;\"><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000025") + "</span></p>\r\n" + 
					"            </td>\r\n" + 
					"            <td style=\"width: 110px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"                <p style=\"text-align: center; font-weight: bold;\"><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000512") + "</span></p>\r\n" + 
					"            </td>\r\n" + 
					"            <td style=\"width: 93px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"                <p style=\"text-align: center; font-weight: bold;\"><span style=\"font-size: 13.3333px; text-align: center; font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000143") + "</span><br></p>\r\n" + 
					"            </td>\r\n" + 
					"            <td style=\"width: 88px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"                <p style=\"text-align: center; font-weight: bold;\"><span style=\"font-size: 13.3333px; text-align: center; font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000449") + "</span><br></p>\r\n" + 
					"            </td>\r\n" + 
					"        </tr>\r\n";
			for(int j = 0; j < msdsList.size(); j++) {
				msdsTable += "        <tr>\r\n" + 
						"            <td style=\"width: 112px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
						"                <p style=\"text-align: center;\">" + msdsList.get(j).get("msds_rev_no") + "<br></p>\r\n" + 
						"            </td>\r\n" + 
						"            <td style=\"width: 83px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
						"                <p style=\"text-align: left;\">" + msdsList.get(j).get("wkpl_nm") + "<br></p>\r\n" + 
						"            </td>\r\n" + 
						"            <td style=\"width: 92px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
						"                <p style=\"text-align: center;\">" + msdsList.get(j).get("mtrl_no") + "<br></p>\r\n" + 
						"            </td>\r\n" + 
						"            <td style=\"width: 259px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
						"                <p>" + msdsList.get(j).get("mtrl_nm") + "<br></p>\r\n" + 
						"            </td>\r\n" + 
						"            <td style=\"width: 110px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
						"                <p style=\"text-align: center;\">" + msdsList.get(j).get("req_da") + "<br></p>\r\n" + 
						"            </td>\r\n" + 
						"            <td style=\"width: 93px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
						"                <p style=\"text-align: center;\">" + msdsList.get(j).get("rev_prog_stt_nm") + "<br></p>\r\n" + 
						"            </td>\r\n" + 
						"            <td style=\"width: 88px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
						"                <p style=\"text-align: center;\"><a style=\"font-weight: bold;\" href=\"" + frontUrl + "" + msdsList.get(j).get("url") + "\">" + mUtil.getMLang(mlangCntn, "LB00000449") + "</a><br></p>\r\n" + 
						"            </td>\r\n" + 
						"        </tr>\r\n" ;
			}
			msdsTable += "    </tbody>\r\n" + 
					"</table>\r\n";
		}
		String msdsTable2 = "";
		if(msdsList2.size() > 0) {
			msdsTable2 += "<p style=\"\">" + mUtil.getMLang(mlangCntn, "LB00000622") + "</p>\r\n";
			msdsTable2 += "<table class=\"txc-table\" width=\"846\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"border:none;border-collapse:collapse;word-break:break-all;font-family:&quot;Malgun Gothic&quot;, &quot;맑은 고딕&quot;;font-size:13.3333px\">\r\n";
			msdsTable2 += "    <tbody>\r\n" + 
					"        <tr>\r\n" + 
					"            <td style=\"width: 112px; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"                <p style=\"text-align: center;\"><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000275") + "</span><br></p>\r\n" + 
					"            </td>\r\n" + 
					"            <td style=\"width: 83px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"                <p style=\"text-align: center; font-weight: bold;\"><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000021") + "</span></p>\r\n" + 
					"            </td>\r\n" + 
					"            <td style=\"width: 92px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"                <p style=\"text-align: center; font-weight: bold;\"><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000024") + "</span></p>\r\n" + 
					"            </td>\r\n" + 
					"            <td style=\"width: 259px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"                <p style=\"text-align: center; font-weight: bold;\"><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000025") + "</span></p>\r\n" + 
					"            </td>\r\n" + 
					"            <td style=\"width: 110px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"                <p style=\"text-align: center; font-weight: bold;\"><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000512") + "</span></p>\r\n" + 
					"            </td>\r\n" + 
					"            <td style=\"width: 93px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"                <p style=\"text-align: center; font-weight: bold;\"><span style=\"font-size: 13.3333px; text-align: center; font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000143") + "</span><br></p>\r\n" + 
					"            </td>\r\n" + 
					"            <td style=\"width: 88px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"                <p style=\"text-align: center; font-weight: bold;\"><span style=\"font-size: 13.3333px; text-align: center; font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000449") + "</span><br></p>\r\n" + 
					"            </td>\r\n" + 
					"        </tr>\r\n";
			for(int j = 0; j < msdsList2.size(); j++) {
				msdsTable2 += "        <tr>\r\n" + 
						"            <td style=\"width: 112px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
						"                <p style=\"text-align: center;\">" + msdsList2.get(j).get("msds_rev_no") + "<br></p>\r\n" + 
						"            </td>\r\n" + 
						"            <td style=\"width: 83px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
						"                <p style=\"text-align: left;\">" + msdsList2.get(j).get("wkpl_nm") + "<br></p>\r\n" + 
						"            </td>\r\n" + 
						"            <td style=\"width: 92px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
						"                <p style=\"text-align: center;\">" + msdsList2.get(j).get("mtrl_no") + "<br></p>\r\n" + 
						"            </td>\r\n" + 
						"            <td style=\"width: 259px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
						"                <p>" + msdsList2.get(j).get("mtrl_nm") + "<br></p>\r\n" + 
						"            </td>\r\n" + 
						"            <td style=\"width: 110px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
						"                <p style=\"text-align: center;\">" + msdsList2.get(j).get("req_da") + "<br></p>\r\n" + 
						"            </td>\r\n" + 
						"            <td style=\"width: 93px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
						"                <p style=\"text-align: center;\">" + msdsList2.get(j).get("rev_prog_stt_nm") + "<br></p>\r\n" + 
						"            </td>\r\n" + 
						"            <td style=\"width: 88px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
						"                <p style=\"text-align: center;\"><a style=\"font-weight: bold;\" href=\"" + frontUrl + "" + msdsList2.get(j).get("url") + "\">" + mUtil.getMLang(mlangCntn, "LB00000449") + "</a><br></p>\r\n" + 
						"            </td>\r\n" + 
						"        </tr>\r\n" ;
			}
			msdsTable2 += "    </tbody>\r\n" + 
					"</table>\r\n";
		}
		// 메일 내용
		String content = "<p><br></p>\r\n" + 
				rgltTable +
				"<p><br></p>\r\n" + 
				rgltTable2 +
				"<p><br></p>\r\n" + 
				msdsTable +
				"<p><br></p>\r\n" + 
				msdsTable2;
		// 메일 송신
		mailResult = util.sendMail(bizCd, "AC928960", subject, content, to);
		// 메일 결과에 따라 메일 전송 여부 Y로 체크
		if(mailResult) {
			// 전송 성공시
			mapper.updateRgltReqBatStt(temp);
		} else {
			throw new Exception("Send Mail Error");
		}
		
		return true; 
	}
}
