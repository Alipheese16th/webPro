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
public class MsdsRevCrtJob implements Job {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	BatchMapper mapper;
	
	@Value("${dgrm.front-url}")
	private String frontUrl;
	
	@Value("${dgrm.server-url}")
	private String serverUrl;
	
	@Autowired
	private SendEmailUtil util;
	
	@Autowired
	private MultiLanguageUtil mUtil;
	
	@Autowired
	TransactionHandler transactionHandler;
	
	private String content = "";
	private String resultCd = "";
	
	@SuppressWarnings("unchecked")
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("MsdsRevCrtJob start, jobKey: " + context.getJobDetail().getKey().getName());
        
        Map<String, String> param = new HashMap<>();
        param.put("batCd", context.getJobDetail().getKey().getName());
        mapper.insertBatch(param); // start
        logger.info("MsdsRevCrtJob batNo : " + param.get("batNo"));
        
        // 배치 실행 중 상태 코드 업데이트
        param.put("resultCd", "EX");
        param.put("content", "");
        mapper.updateBatch(param);
        
        // 실제 배치 실행 후 결과 코드 
        content = "";
        resultCd = "ER";
        transactionHandler.runInTransaction(() -> {
	        try {
	        	// 1. 신규자재 MSDS 목록 조회(todo : 티움넷 신규 자재인지 확인하는 로직 체크 필요있음)
		        List<Map<String, ?>> mtrlList = mapper.selectNewMsdsMtrlList(param);
		        // 1-1. 규제검토 생성(1. MSDS 목록 조회 쿼리에서 rglt_chk_yn 값이 N 이면 실행)
		        for(int i = 0; i < mtrlList.size(); i++) {
		        	Map<String, Object> temp = (Map<String, Object>) mtrlList.get(i);
		        	temp.put("rev_usr_id", String.valueOf(mtrlList.get(i).get("req_usr_id")));
	        		// if #{msds_file_ko_url} is not null 이면 실행성
	        		if(mtrlList.get(i).get("msds_file_ko_nm") != null && !String.valueOf(mtrlList.get(i).get("msds_file_ko_nm")).equalsIgnoreCase("null")){
	        			// 파일 키 채번
	        			String key = mapper.selectAtfileKey(temp);
	        			temp.put("ko_atfl_no", key);
	        			// 파일 메타정보 생성
	        			mapper.insertAtfileKoMetaData(temp);
	        		}
	        		// if #{msds_file_en_url} is not null 이면 실행성
	        		if(mtrlList.get(i).get("msds_file_en_nm") != null && !String.valueOf(mtrlList.get(i).get("msds_file_en_nm")).equalsIgnoreCase("null")){
	        			// 파일 키 채번
	        			String key = mapper.selectAtfileKey(temp);
	        			temp.put("en_atfl_no", key);
	        			// 파일 메타정보 생성
	        			mapper.insertAtfileEnMetaData(temp);
	        		}
	        		// 규제 검토 MSDS 등록을 위한 select
	        		List<Map<String, Object>> data = mapper.selectMsdstiumData(temp);
	        		// 자재마스터 업데이트
	        		mapper.updateMtrlMstMsds(temp);
	        		// 규제 검토 MSDS 등록
	        		mapper.insertRgltChkMsds(data.get(0));
	        		// 배치 처리 여부 업데이트
	        		mapper.updateRgltChkSucc(temp);
		        }
		        
		        // 2. MSDS 개정 요청 대상 조회
		        List<Map<String, ?>> msdsList = mapper.selectMsdsReqList(param);
		        // 1-1. 규제검토 생성(1. MSDS 목록 조회 쿼리에서 rglt_chk_yn 값이 N 이면 실행)
		        for(int i = 0; i < msdsList.size(); i++) {
		        	Map<String, Object> temp = (Map<String, Object>) msdsList.get(i);
	        		// if #{msds_file_ko_url} is not null 이면 실행성
	        		if(msdsList.get(i).get("msds_file_ko_nm") != null && !String.valueOf(msdsList.get(i).get("msds_file_ko_nm")).equalsIgnoreCase("null")){
	        			// 파일 키 채번
	        			String key = mapper.selectAtfileKey(temp);
	        			temp.put("ko_atfl_no", key);
	        			// 파일 메타정보 생성
	        			mapper.insertAtfileKoMetaData(temp);
	        		}
	        		// if #{msds_file_en_url} is not null 이면 실행성
	        		if(msdsList.get(i).get("msds_file_en_nm") != null && !String.valueOf(msdsList.get(i).get("msds_file_en_nm")).equalsIgnoreCase("null")){
	        			// 파일 키 채번
	        			String key = mapper.selectAtfileKey(temp);
	        			temp.put("en_atfl_no", key);
	        			// 파일 메타정보 생성
	        			mapper.insertAtfileEnMetaData(temp);
	        		}
	        		// 개정 요청 정보 생성
	        		// 키 채번
	        		String key = mapper.selectMsdsReqKey(temp);
	        		temp.put("msds_rev_no", key);
	        		// 자재마스터 업데이트
	        		mapper.updateMtrlMstMsds(temp);
	        		// msds 개정 요청
	        		mapper.insertRgltMsdsReq(temp);	// toad:적용시 상태코드 10 =>20으로 변경 필요
	        		// 개정 요청 경고표지/그림문자 정보 생성
	        		mapper.insertAlertMsds(temp);
	        		// 그림 문자
	        		mapper.insertPicMsds(temp);

	        		
	        		// MSDS 개정검토
	        		// 개정요청 데이터 기반으로 개정검토 단계로 보낸다.
	        		// 보건 담당자에게 개정검토 메일을 보내다.
	        		mapper.insertRgltMsdsReqTwo(temp);
	        		// 개정 요청 경고표지/그림문자 정보 생성
	        		mapper.insertAlertMsdsTwo(temp);
	        		// 그림 문자
	        		mapper.insertPicMsdsTwo(temp);
	        		

	        		// 1-3. 알림 메일 전송[20210623 : 함종호님 요청으로 발송 차단] => 변경 : 자재별 규제 검토 사업장 보건담당자에 발송
	        		sendMsdsMail(temp);
		        	
	        		
	        		// 배치 처리 여부 업데이트
	        		mapper.updateRgltChkSucc(temp);
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
	
	@SuppressWarnings("unchecked")
	@Transactional
	public void executeManual() throws JobExecutionException {
        logger.info("MsdsRevCrtJob start, jobKey: " + "BR002");
        
        Map<String, String> param = new HashMap<>();
        param.put("batCd", "BR002");
        mapper.insertBatch(param); // start
        logger.info("MsdsRevCrtJob batNo : " + param.get("batNo"));
        
        // 배치 실행 중 상태 코드 업데이트
        param.put("resultCd", "EX");
        param.put("content", "");
        mapper.updateBatch(param);
        
        // 실제 배치 실행 후 결과 코드 
        content = "";
        resultCd = "ER";
        transactionHandler.runInTransaction(() -> {
	        try {
	        	// 1. 신규자재 MSDS 목록 조회(todo : 티움넷 신규 자재인지 확인하는 로직 체크 필요있음)
		        List<Map<String, ?>> mtrlList = mapper.selectNewMsdsMtrlList(param);
		        // 1-1. 규제검토 생성(1. MSDS 목록 조회 쿼리에서 rglt_chk_yn 값이 N 이면 실행)
		        for(int i = 0; i < mtrlList.size(); i++) {
		        	Map<String, Object> temp = (Map<String, Object>) mtrlList.get(i);
		        	temp.put("rev_usr_id", String.valueOf(mtrlList.get(i).get("req_usr_id")));
	        		// if #{msds_file_ko_url} is not null 이면 실행성
	        		if(mtrlList.get(i).get("msds_file_ko_nm") != null && !String.valueOf(mtrlList.get(i).get("msds_file_ko_nm")).equalsIgnoreCase("null")){
	        			// 파일 키 채번
	        			String key = mapper.selectAtfileKey(temp);
	        			temp.put("ko_atfl_no", key);
	        			// 파일 메타정보 생성
	        			mapper.insertAtfileKoMetaData(temp);
	        		}
	        		// if #{msds_file_en_url} is not null 이면 실행성
	        		if(mtrlList.get(i).get("msds_file_en_nm") != null && !String.valueOf(mtrlList.get(i).get("msds_file_en_nm")).equalsIgnoreCase("null")){
	        			// 파일 키 채번
	        			String key = mapper.selectAtfileKey(temp);
	        			temp.put("en_atfl_no", key);
	        			// 파일 메타정보 생성
	        			mapper.insertAtfileEnMetaData(temp);
	        		}
	        		// 규제 검토 MSDS 등록을 위한 select
	        		List<Map<String, Object>> data = mapper.selectMsdstiumData(temp);
	        		// 규제 검토 MSDS 등록
	        		mapper.insertRgltChkMsds(data.get(0));
	        		// 배치 처리 여부 업데이트
	        		mapper.updateRgltChkSucc(temp);
		        }
		        
		        // 2. MSDS 개정 요청 대상 조회
		        List<Map<String, ?>> msdsList = mapper.selectMsdsReqList(param);
		        // 1-1. 규제검토 생성(1. MSDS 목록 조회 쿼리에서 rglt_chk_yn 값이 N 이면 실행)
		        for(int i = 0; i < msdsList.size(); i++) {
		        	Map<String, Object> temp = (Map<String, Object>) msdsList.get(i);
	        		// if #{msds_file_ko_url} is not null 이면 실행성
	        		if(msdsList.get(i).get("msds_file_ko_nm") != null && !String.valueOf(msdsList.get(i).get("msds_file_ko_nm")).equalsIgnoreCase("null")){
	        			// 파일 키 채번
	        			String key = mapper.selectAtfileKey(temp);
	        			temp.put("ko_atfl_no", key);
	        			// 파일 메타정보 생성
	        			mapper.insertAtfileKoMetaData(temp);
	        		}
	        		// if #{msds_file_en_url} is not null 이면 실행성
	        		if(msdsList.get(i).get("msds_file_en_nm") != null && !String.valueOf(msdsList.get(i).get("msds_file_en_nm")).equalsIgnoreCase("null")){
	        			// 파일 키 채번
	        			String key = mapper.selectAtfileKey(temp);
	        			temp.put("en_atfl_no", key);
	        			// 파일 메타정보 생성
	        			mapper.insertAtfileEnMetaData(temp);
	        		}
	        		// 개정 요청 정보 생성
	        		// 키 채번
	        		String key = mapper.selectMsdsReqKey(temp);
	        		temp.put("msds_rev_no", key);
	        		mapper.insertRgltMsdsReq(temp);	// toad:적용시 상태코드 10 =>20으로 변경 필요
	        		// 개정 요청 경고표지/그림문자 정보 생성
	        		mapper.insertAlertMsds(temp);
	        		// 그림 문자
	        		mapper.insertPicMsds(temp);

	        		
	        		// MSDS 개정검토
	        		// 개정요청 데이터 기반으로 개정검토 단계로 보낸다.
	        		// 보건 담당자에게 개정검토 메일을 보내다.
	        		mapper.insertRgltMsdsReqTwo(temp);
	        		// 개정 요청 경고표지/그림문자 정보 생성
	        		mapper.insertAlertMsdsTwo(temp);
	        		// 그림 문자
	        		mapper.insertPicMsdsTwo(temp);
	        		

	        		// 1-3. 알림 메일 전송[20210623 : 함종호님 요청으로 발송 차단] => 변경 : 자재별 규제 검토 사업장 보건담당자에 발송
	        		sendMsdsMail(temp);
	        		
	        		
	        		// 배치 처리 여부 업데이트
	        		mapper.updateRgltChkSucc(temp);
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
	public boolean sendMsdsMail(Map<String, Object> temp) throws Exception {		
		
		// 수신자 학인
		//String[] to = new String[1];	// 기존 수신자
		
		Boolean mailResult = true;
		// 수신자 저장
		//to[0] = altValueOf(temp.get("rev_usr_id"), "");	// 기존 수신자
		List<Map<String, Object>> msdsChkAuthMem = mapper.selectMsdsChkUsrList(temp);
		String[] to = new String[msdsChkAuthMem.size()];
		for(int i=0; i<msdsChkAuthMem.size(); i++) {
			to[i] = (String) msdsChkAuthMem.get(i).get("usr_id");
		}
		
		// 다국어 처리
		String[] mlangNo = {"MS00000227", "LB00000198", "CD00000801"
				           ,"LB00000151", "LB00000150", "LB00000199"
				           ,"LB00000025", "LB00000207", "LB00000208"
				           ,"LB00000209", "LB00000210", "LB00000032"};
		String langCd = altValueOf(temp.get("lang_cd"), "KO");
		List<Map<String, String>> mlangCntn = mUtil.getMLangList(Arrays.asList(mlangNo),langCd);
		// 메일 업무 코드
		String bizCd = "13";
		// 메일 제목
		String subject = "";
		String mtrlNm = "";
		if(langCd.equalsIgnoreCase("KO")) {
			mtrlNm = temp.get("mtrl_ko_nm").toString();
			subject = mUtil.getMLang(mlangCntn, "MS00000227") + "(" + temp.get("mtrl_no") + " : " + mtrlNm + ")";
		}else if(langCd.equalsIgnoreCase("EN")) {
			mtrlNm = temp.get("mtrl_en_nm").toString();
			subject = mUtil.getMLang(mlangCntn, "MS00000227") + "(" + temp.get("mtrl_no") + " : " + mtrlNm + ")";
		}else {
			mtrlNm = temp.get("mtrl_zh_nm").toString();
			subject = mUtil.getMLang(mlangCntn, "MS00000227") + "(" + temp.get("mtrl_no") + " : " + mtrlNm + ")";
		}

		// 메일 내용
		String content = "<div>\r\n" + 
				"    <p>\r\n" + 
				"        <spam style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + mUtil.getMLang("LB00000032", langCd) + " :&nbsp;</spam><a href=\"" + frontUrl + "/msds/msdsrevchkedt/" + altValueOf(temp.get("msds_rev_no"), "") + "\" style=\"font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + frontUrl + "/msds/msdsrevreqedt/" + altValueOf(temp.get("msds_rev_no"), "") + "</a>\r\n" + 
				"    </p>\r\n" + 
				"    <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + mUtil.getMLang("CD00000801", langCd) + "</p>\r\n" + 
				"    <table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;\">\r\n" + 
				"        <tbody>\r\n" + 
				"            <tr>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000151") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 38%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + altValueOf(temp.get("msds_rev_no"), "") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000150") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 38%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + altValueOf(temp.get("rev_usr_id"), "") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"            <tr>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000199") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 38%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + altValueOf(temp.get("mtrl_no"), "") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000025") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 38%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + mtrlNm + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"        </tbody>\r\n" + 
				"    </table>\r\n" + 
				"    <p style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\"><br></p>\r\n" + 
				"    <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">MSDS</p>\r\n" + 
				"    <table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;\">\r\n" + 
				"        <tbody>\r\n" + 
				"            <tr>\r\n" + 
				"                <td style=\"width: 137px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000207") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 284px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + "<a href=\"" + serverUrl + "/api/v1/public/download/" + String.valueOf(temp.get("rev_bf_ko_atfl_no")) + "\">" + altValueOf(temp.get("rev_bf_ko_atfl_nm"), "") + "</a>" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 141px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000208") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 284px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + "<a href=\"" + serverUrl + "/api/v1/public/download/" + String.valueOf(temp.get("ko_atfl_no")) + "\">" + altValueOf(temp.get("msds_file_ko_nm"), "") + "</a>" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"            <tr>\r\n" + 
				"                <td style=\"width: 137px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000209") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 284px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + "<a href=\"" + serverUrl + "/api/v1/public/download/" + String.valueOf(temp.get("rev_bf_en_atfl_no")) + "\">" + altValueOf(temp.get("rev_bf_en_atfl_nm"), "") + "</a>" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 141px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000210") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 284px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + "<a href=\"" + serverUrl + "/api/v1/public/download/" + String.valueOf(temp.get("en_atfl_no")) + "\">" + altValueOf(temp.get("msds_file_en_nm"), "") + "</a>" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"        </tbody>\r\n" + 
				"    </table>\r\n" +
				"</div>";
		// 메일 송신
		mailResult = util.sendMail(bizCd, "AC928960", subject, content, to);
		// 메일 결과에 따라 메일 전송 여부 Y로 체크
		if(!mailResult) {
			throw new Exception("Send Mail Error");
		}
		
		return mailResult; 
	}
	
	public static String altValueOf(Object value, String alt) {
	  if(value == null) return alt;
	  return String.valueOf(value);
	}
}
