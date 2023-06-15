package com.kist.portal.common.batch;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
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
public class RgltAlrmJob implements Job {
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
        logger.info("RgltAlrmJob start, jobKey: " + context.getJobDetail().getKey().getName());
        
        Map<String, String> param = new HashMap<>();
        param.put("batCd", context.getJobDetail().getKey().getName());
        mapper.insertBatch(param); // start
        logger.info("RgltAlrmJob batNo : " + param.get("batNo"));
        
        // 배치 실행 중 상태 코드 업데이트
        param.put("resultCd", "EX");
        param.put("content", "");
        mapper.updateBatch(param);
        
        // 실제 배치 실행 후 결과 코드 
        content = "";
        resultCd = "ER";
        transactionHandler.runInTransaction(() -> {
	        try {
	        	Map<String, Object> temp = new HashMap<>();
	        	// 1. 신규 화학물질 입고시 알람/확인
	        	List<Map<String, Object>> newSbstList = mapper.selectNewSbstList(temp);
	        	if(newSbstList.size() > 0) {
	        		sendNewSbstMail(newSbstList);
	        	}
	        	// 2. 등록대상, CMR 물질 1통 이전 정해진 비율에 맞춰 알람발송/확인
	        	List<Map<String, Object>> cmrSbstList = mapper.selectCmrSbstList(temp);
	        	if(cmrSbstList.size() > 0) {
	        		sendCmrSbstMail(cmrSbstList);
	        	}
	        	// 3. 유독물, 제한, 금지, 허가, 사고대비, 위험물 입고 알람
	        	List<Map<String, Object>> etcAlrmSbstList = mapper.selectEtcAlrmSbstList(temp);
	        	if(cmrSbstList.size() > 0) {
	        		sendEtcAlrmSbstMail(etcAlrmSbstList);
	        	}
	        	// 4. 기존화학물질 연간 Ton 알람
	        	List<Map<String, Object>> tonAlrmSbstList = mapper.selectTonAlrmSbstList(temp);
	        	if(cmrSbstList.size() > 0) {
	        		sendTonAlrmSbstMail(tonAlrmSbstList);
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
        logger.info("RgltAlrmJob start, jobKey: " + "BR005");
        
        Map<String, String> param = new HashMap<>();
        param.put("batCd", "BR005");
        mapper.insertBatch(param); // start
        logger.info("RgltChkCrtJob batNo : " + param.get("batNo"));
        
        // 배치 실행 중 상태 코드 업데이트
        param.put("resultCd", "EX");
        param.put("content", "");
        mapper.updateBatch(param);
        
        // 실제 배치 실행 후 결과 코드 
        content = "";
        resultCd = "ER";
        transactionHandler.runInTransaction(() -> {
	        try {
	        	Map<String, Object> temp = new HashMap<>();
	        	// 1. 신규 화학물질 입고시 알람/확인
	        	List<Map<String, Object>> newSbstList = mapper.selectNewSbstList(temp);
	        	if(newSbstList.size() > 0) {
	        		sendNewSbstMail(newSbstList);
	        	}
	        	// 2. 등록대상, CMR 물질 1통 이전 정해진 비율에 맞춰 알람발송/확인
	        	List<Map<String, Object>> cmrSbstList = mapper.selectCmrSbstList(temp);
	        	if(cmrSbstList.size() > 0) {
	        		sendCmrSbstMail(cmrSbstList);
	        	}
	        	// 3. 유독물, 제한, 금지, 허가, 사고대비, 위험물 입고 알람
	        	List<Map<String, Object>> etcAlrmSbstList = mapper.selectEtcAlrmSbstList(temp);
	        	if(etcAlrmSbstList.size() > 0) {
	        		sendEtcAlrmSbstMail(etcAlrmSbstList);
	        	}
	        	// 4. 기존화학물질 연간 Ton 알람
	        	List<Map<String, Object>> tonAlrmSbstList = mapper.selectTonAlrmSbstList(temp);
	        	if(tonAlrmSbstList.size() > 0) {
	        		sendTonAlrmSbstMail(tonAlrmSbstList);
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
	public boolean sendNewSbstMail(List<Map<String, Object>> list) throws Exception {		
		Map<String, Object> temp = new HashMap<>();
		
		// 수신자 학인
		List<Map<String, String>> newSbstUserList = mapper.selectNewSbstUserList(temp);
		String[] to = new String[1];
		
		Boolean mailResult = true;
		String alrmKey = mapper.selectRgltAlrmKey(temp);
		temp.put("alrm_key", alrmKey);
		for(int i=0; i<newSbstUserList.size(); i++) {
			// 수신자 저장
			to[0] = newSbstUserList.get(i).get("usr_id");
			// 언어 설정
			String langCd = newSbstUserList.get(i).get("lang_cd");
			if(langCd == null || langCd.isEmpty()) {
				langCd = "KO";
			}
			temp.put("SESS_LANG", langCd);
			temp.put("wkpl_id", newSbstUserList.get(i).get("wkpl_id"));
			// 수신자의 신규 자재 입고 정보 조회
			List<Map<String, Object>> sbstUserDataList = list;
			// 다국어 처리
			String[] mlangNo = {"MS00000239", "LB00000021", "LB00000024"
					           ,"LB00000025", "LB00000479"};
			List<Map<String, String>> mlangCntn = mUtil.getMLangList(Arrays.asList(mlangNo), langCd);
			// 메일 업무 코드
			String bizCd = "25";
			// 메일 제목
			Calendar c1 = new GregorianCalendar();
			c1.add(Calendar.DATE, -1);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
			String subject = mUtil.getMLang(mlangCntn, "MS00000239") + "(" + sdf.format(c1.getTime()) + ")"; // 신규 자재 규제 검토 알림
			// 테이블 동적 생성
			String table = "";
			for(int j = 0; j < sbstUserDataList.size(); j++) {
				String mtrlNm = "";
				if(langCd == "KO") {
					mtrlNm = String.valueOf(sbstUserDataList.get(j).get("mtrl_ko_nm"));
				}else {
					mtrlNm = String.valueOf(sbstUserDataList.get(j).get("mtrl_en_nm"));
				}
				table += "        <tr>\r\n" + 
						"            <td style=\"width:10%;height:24px;border-bottom:1px solid #ccc;border-right:1px solid #ccc;border-left:1px solid #ccc;padding:0px 3px 0px 3px;;\">\r\n" + 
						"                <p style=\"text-align: center;\">" + (j + 1) + "</p>\r\n" + 
						"            </td>\r\n" + 
						"            <td style=\"width:10%;height:24px;border-bottom:1px solid #ccc;border-right:1px solid #ccc;padding:0px 3px 0px 3px;;\">\r\n" + 
						"                <p>" + String.valueOf(sbstUserDataList.get(j).get("wkpl_nm")) + "</p>\r\n" + 
						"            </td>\r\n" + 
						"            <td style=\"width:20%;height:24px;border-bottom:1px solid #ccc;border-right:1px solid #ccc;padding:0px 3px 0px 3px;;\">\r\n" + 
						"                <p style=\"text-align: center;\">" + String.valueOf(sbstUserDataList.get(j).get("mtrl_no")) + "</p>\r\n" + 
						"            </td>\r\n" + 
						"            <td style=\"width:40%;height:24px;border-bottom:1px solid #ccc;border-right:1px solid #ccc;padding:0px 3px 0px 3px;;\">\r\n" + 
						"                <p>" + mtrlNm + "</p>\r\n" + 
						"            </td>\r\n" + 
						"            <td style=\"width:20%;height:24px;border-bottom:1px solid #ccc;border-right:1px solid #ccc;padding:0px 3px 0px 3px;;\">\r\n" + 
						"                <p style=\"text-align: center;\">" + Float.valueOf(String.valueOf(sbstUserDataList.get(j).get("qty"))) + "</p>\r\n" + 
						"            </td>\r\n" + 
						"        </tr>\r\n";
			}
			// 메일 내용
			String content = 
					"<table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"border:none;border-collapse:collapse;word-break:break-all;font-family:&quot;Malgun Gothic&quot;, &quot;맑은 고딕&quot;;font-size:13.3333px\">\r\n" + 
					"    <tbody>\r\n" + 
					"        <tr>\r\n" + 
					"            <td style=\"width: 10%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"                <p style=\"text-align: center;\"><span style=\"font-weight: bold;\">" + "No." + "</span><br></p>\r\n" + 
					"            </td>\r\n" + 
					"            <td style=\"width: 10%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"                <p style=\"font-weight: bold; text-align: center;\"><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000021") + "</span></p>\r\n" + 
					"            </td>\r\n" + 
					"            <td style=\"width: 20%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"                <p style=\"font-weight: bold; text-align: center;\"><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000024") + "</span></p>\r\n" + 
					"            </td>\r\n" + 
					"            <td style=\"width: 40%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"                <p style=\"font-weight: bold; text-align: center;\"><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000025") + "</span></p>\r\n" + 
					"            </td>\r\n" + 
					"            <td style=\"width: 20%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"                <p style=\"text-align: center;\"><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000479") + "</span></p>\r\n" + 
					"            </td>\r\n" + 
					"        </tr>\r\n" + 
					table +
					"    </tbody>\r\n" + 
					"</table>";
			
			// 메일 송신
			if(sbstUserDataList.size() > 0) {
				mailResult = util.sendMail(bizCd, "AC928960", subject, content, to);
			} else {
				mailResult = true;
				return mailResult;
			}
			
			// 메일 결과에 따라 메일 전송 여부 Y로 체크
			if(mailResult) {
				temp.put("subject", subject);
				temp.put("mContent", content);
				temp.put("mTo", to[0]);
				temp.put("mEmail", newSbstUserList.get(i).get("usr_email"));
				mapper.insertRgltActAlrmRecv(temp);
			} else {
				throw new Exception("Send Mail Error");
			}
		}
		temp.put("alrm_type_cd", "AL001");
		mapper.insertRgltActAlrm(temp);
		for(int j = 0; j < list.size(); j++) {
			temp.put("wkpl_id", list.get(j).get("wkpl_id"));
			temp.put("mtrl_no", String.valueOf(list.get(j).get("mtrl_no")));
			mapper.insertRgltActMtrl(temp);
		}
		
		return mailResult; 
	}
	
	@Transactional
	public boolean sendCmrSbstMail(List<Map<String, Object>> list) throws Exception {		
		Map<String, Object> temp = new HashMap<>();
		
		// 수신자 학인
		List<Map<String, String>> newSbstUserList = mapper.selectNewSbstUserList(temp);
		String[] to = new String[1];
		
		Boolean mailResult = true;
		String alrmKey = mapper.selectRgltAlrmKey(temp);
		temp.put("alrm_key", alrmKey);
		for(int i=0; i<newSbstUserList.size(); i++) {
			// 수신자 저장
			to[0] = newSbstUserList.get(i).get("usr_id");
			// 언어 설정
			String langCd = newSbstUserList.get(i).get("lang_cd");
			if(langCd == null || langCd.isEmpty()) {
				langCd = "KO";
			}
			temp.put("SESS_LANG", langCd);
			temp.put("wkpl_id", newSbstUserList.get(i).get("wkpl_id"));
			// 수신자의 신규 자재 입고 정보 조회
			List<Map<String, Object>> sbstUserDataList = list;
			// 다국어 처리
			String[] mlangNo = {"LB00000213", "LB00000024", "LB00000025"
					           ,"LB00000558", "MS00000240"};
			List<Map<String, String>> mlangCntn = mUtil.getMLangList(Arrays.asList(mlangNo), langCd);
			// 메일 업무 코드
			String bizCd = "25";
			// 메일 제목
			Calendar c1 = new GregorianCalendar();
			c1.add(Calendar.DATE, -1);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
			String subject = mUtil.getMLang(mlangCntn, "MS00000240") + "(" + sdf.format(c1.getTime()) + ")"; // 신규 자재 규제 검토 알림
			// 테이블 동적 생성
			String table = "";
			for(int j = 0; j < sbstUserDataList.size(); j++) {
				String mtrlNm = "";
				if(langCd == "KO") {
					mtrlNm = String.valueOf(sbstUserDataList.get(j).get("mtrl_ko_nm"));
				}else {
					mtrlNm = String.valueOf(sbstUserDataList.get(j).get("mtrl_en_nm"));
				}
				table += "        <tr>\r\n" + 
						"            <td style=\"width:10%;height:24px;border-bottom:1px solid #ccc;border-right:1px solid #ccc;border-left:1px solid #ccc;padding:0px 3px 0px 3px;;\">\r\n" + 
						"                <p style=\"text-align: center;\">" + (j + 1) + "</p>\r\n" + 
						"            </td>\r\n" + 
						"            <td style=\"width:20%;height:24px;border-bottom:1px solid #ccc;border-right:1px solid #ccc;padding:0px 3px 0px 3px;;\">\r\n" + 
						"                <p>" + String.valueOf(sbstUserDataList.get(j).get("rglt_nm")) + "</p>\r\n" + 
						"            </td>\r\n" + 
						"            <td style=\"width:10%;height:24px;border-bottom:1px solid #ccc;border-right:1px solid #ccc;padding:0px 3px 0px 3px;;\">\r\n" + 
						"                <p style=\"text-align: center;\">" + String.valueOf(sbstUserDataList.get(j).get("mtrl_no")) + "</p>\r\n" + 
						"            </td>\r\n" + 
						"            <td style=\"width:40%;height:24px;border-bottom:1px solid #ccc;border-right:1px solid #ccc;padding:0px 3px 0px 3px;;\">\r\n" + 
						"                <p>" + mtrlNm + "</p>\r\n" + 
						"            </td>\r\n" + 
						"            <td style=\"width:20%;height:24px;border-bottom:1px solid #ccc;border-right:1px solid #ccc;padding:0px 3px 0px 3px;;\">\r\n" + 
						"                <p style=\"text-align: center;\">" + Float.valueOf(String.valueOf(sbstUserDataList.get(j).get("use_qty"))) + "</p>\r\n" + 
						"            </td>\r\n" + 
						"        </tr>\r\n";
			}
			// 메일 내용
			String content = 
					"<table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"border:none;border-collapse:collapse;word-break:break-all;font-family:&quot;Malgun Gothic&quot;, &quot;맑은 고딕&quot;;font-size:13.3333px\">\r\n" + 
					"    <tbody>\r\n" + 
					"        <tr>\r\n" + 
					"            <td style=\"width: 10%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"                <p style=\"text-align: center;\"><span style=\"font-weight: bold;\">" + "No." + "</span><br></p>\r\n" + 
					"            </td>\r\n" + 
					"            <td style=\"width: 20%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"                <p style=\"font-weight: bold; text-align: center;\"><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000213") + "</span></p>\r\n" + 
					"            </td>\r\n" + 
					"            <td style=\"width: 10%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"                <p style=\"font-weight: bold; text-align: center;\"><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000024") + "</span></p>\r\n" + 
					"            </td>\r\n" + 
					"            <td style=\"width: 40%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"                <p style=\"font-weight: bold; text-align: center;\"><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000025") + "</span></p>\r\n" + 
					"            </td>\r\n" + 
					"            <td style=\"width: 20%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"                <p style=\"text-align: center;\"><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000558") + "</span></p>\r\n" + 
					"            </td>\r\n" + 
					"        </tr>\r\n" + 
					table +
					"    </tbody>\r\n" + 
					"</table>";
			
			// 메일 송신
			if(sbstUserDataList.size() > 0) {
				mailResult = util.sendMail(bizCd, "AC928960", subject, content, to);
			} else {
				mailResult = true;
				return mailResult;
			}
			
			// 메일 결과에 따라 메일 전송 여부 Y로 체크
			if(mailResult) {
				temp.put("subject", subject);
				temp.put("mContent", content);
				temp.put("mTo", to[0]);
				temp.put("mEmail", newSbstUserList.get(i).get("usr_email"));
				mapper.insertRgltActAlrmRecv(temp);
			} else {
				throw new Exception("Send Mail Error");
			}
		}
		temp.put("alrm_type_cd", "AL002");
		mapper.insertRgltActAlrm(temp);
		List<Map<String, Object>> tempList = mapper.selectCmrMtrlNo(temp);
		for(int j = 0; j < tempList.size(); j++) {
			// temp.put("wkpl_id", list.get(j).get("wkpl_id"));
			temp.put("qtt", 800);
			temp.put("mtrl_no", String.valueOf(tempList.get(j).get("mtrl_no")));
			mapper.insertRgltActAlrmMtrl(temp);
		}
		
		return mailResult; 
	}
	
	@Transactional
	public boolean sendEtcAlrmSbstMail(List<Map<String, Object>> list) throws Exception {		
		Map<String, Object> temp = new HashMap<>();
		
		// 수신자 학인
		List<Map<String, String>> newSbstUserList = mapper.selectNewSbstUserList(temp);
		String[] to = new String[1];
		
		Boolean mailResult = true;
		String alrmKey = mapper.selectRgltAlrmKey(temp);
		temp.put("alrm_key", alrmKey);
		for(int i=0; i<newSbstUserList.size(); i++) {
			// 수신자 저장
			to[0] = newSbstUserList.get(i).get("usr_id");
			// 언어 설정
			String langCd = newSbstUserList.get(i).get("lang_cd");
			if(langCd == null || langCd.isEmpty()) {
				langCd = "KO";
			}
			temp.put("SESS_LANG", langCd);
			temp.put("wkpl_id", newSbstUserList.get(i).get("wkpl_id"));
			// 수신자의 신규 자재 입고 정보 조회
			List<Map<String, Object>> sbstUserDataList = list;
			// 다국어 처리
			String[] mlangNo = {"MS00000241", "LB00000021", "LB00000024"
					           ,"LB00000025", "LB00000479"};
			List<Map<String, String>> mlangCntn = mUtil.getMLangList(Arrays.asList(mlangNo), langCd);
			// 메일 업무 코드
			String bizCd = "25";
			// 메일 제목
			Calendar c1 = new GregorianCalendar();
			c1.add(Calendar.DATE, -1);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
			String subject = mUtil.getMLang(mlangCntn, "MS00000241") + "(" + sdf.format(c1.getTime()) + ")"; // 신규 자재 규제 검토 알림
			// 테이블 동적 생성
			String table = "";
			for(int j = 0; j < sbstUserDataList.size(); j++) {
				String mtrlNm = "";
				if(langCd == "KO") {
					mtrlNm = String.valueOf(sbstUserDataList.get(j).get("mtrl_ko_nm"));
				}else {
					mtrlNm = String.valueOf(sbstUserDataList.get(j).get("mtrl_en_nm"));
				}
				table += "        <tr>\r\n" + 
						"            <td style=\"width:10%;height:24px;border-bottom:1px solid #ccc;border-right:1px solid #ccc;border-left:1px solid #ccc;padding:0px 3px 0px 3px;;\">\r\n" + 
						"                <p style=\"text-align: center;\">" + (j + 1) + "</p>\r\n" + 
						"            </td>\r\n" + 
						"            <td style=\"width:10%;height:24px;border-bottom:1px solid #ccc;border-right:1px solid #ccc;padding:0px 3px 0px 3px;;\">\r\n" + 
						"                <p>" + String.valueOf(sbstUserDataList.get(j).get("wkpl_nm")) + "</p>\r\n" + 
						"            </td>\r\n" + 
						"            <td style=\"width:20%;height:24px;border-bottom:1px solid #ccc;border-right:1px solid #ccc;padding:0px 3px 0px 3px;;\">\r\n" + 
						"                <p style=\"text-align: center;\">" + String.valueOf(sbstUserDataList.get(j).get("mtrl_no")) + "</p>\r\n" + 
						"            </td>\r\n" + 
						"            <td style=\"width:40%;height:24px;border-bottom:1px solid #ccc;border-right:1px solid #ccc;padding:0px 3px 0px 3px;;\">\r\n" + 
						"                <p>" + mtrlNm + "</p>\r\n" + 
						"            </td>\r\n" + 
						"            <td style=\"width:20%;height:24px;border-bottom:1px solid #ccc;border-right:1px solid #ccc;padding:0px 3px 0px 3px;;\">\r\n" + 
						"                <p style=\"text-align: center;\">" + Float.valueOf(String.valueOf(sbstUserDataList.get(j).get("qty"))) + "</p>\r\n" + 
						"            </td>\r\n" + 
						"        </tr>\r\n";
			}
			// 메일 내용
			String content = 
					"<table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"border:none;border-collapse:collapse;word-break:break-all;font-family:&quot;Malgun Gothic&quot;, &quot;맑은 고딕&quot;;font-size:13.3333px\">\r\n" + 
					"    <tbody>\r\n" + 
					"        <tr>\r\n" + 
					"            <td style=\"width: 10%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"                <p style=\"text-align: center;\"><span style=\"font-weight: bold;\">" + "No." + "</span><br></p>\r\n" + 
					"            </td>\r\n" + 
					"            <td style=\"width: 10%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"                <p style=\"font-weight: bold; text-align: center;\"><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000021") + "</span></p>\r\n" + 
					"            </td>\r\n" + 
					"            <td style=\"width: 20%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"                <p style=\"font-weight: bold; text-align: center;\"><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000024") + "</span></p>\r\n" + 
					"            </td>\r\n" + 
					"            <td style=\"width: 40%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"                <p style=\"font-weight: bold; text-align: center;\"><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000025") + "</span></p>\r\n" + 
					"            </td>\r\n" + 
					"            <td style=\"width: 20%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"                <p style=\"text-align: center;\"><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000479") + "</span></p>\r\n" + 
					"            </td>\r\n" + 
					"        </tr>\r\n" + 
					table +
					"    </tbody>\r\n" + 
					"</table>";
			
			// 메일 송신
			if(sbstUserDataList.size() > 0) {
				mailResult = util.sendMail(bizCd, "AC928960", subject, content, to);
			} else {
				mailResult = true;
				return mailResult;
			}
			
			// 메일 결과에 따라 메일 전송 여부 Y로 체크
			if(mailResult) {
				temp.put("subject", subject);
				temp.put("mContent", content);
				temp.put("mTo", to[0]);
				temp.put("mEmail", newSbstUserList.get(i).get("usr_email"));
				mapper.insertRgltActAlrmRecv(temp);
			} else {
				throw new Exception("Send Mail Error");
			}
		}
		temp.put("alrm_type_cd", "AL003");
		mapper.insertRgltActAlrm(temp);
		for(int j = 0; j < list.size(); j++) {
			temp.put("wkpl_id", list.get(j).get("wkpl_id"));
			temp.put("mtrl_no", String.valueOf(list.get(j).get("mtrl_no")));
			mapper.insertRgltActMtrl(temp);
		}
		
		return mailResult; 
	}
	
	@Transactional
	public boolean sendTonAlrmSbstMail(List<Map<String, Object>> list) throws Exception {		
		Map<String, Object> temp = new HashMap<>();
		
		// 수신자 학인
		List<Map<String, String>> newSbstUserList = mapper.selectNewSbstUserList(temp);
		String[] to = new String[1];
		
		Boolean mailResult = true;
		String alrmKey = mapper.selectRgltAlrmKey(temp);
		temp.put("alrm_key", alrmKey);
		for(int i=0; i<newSbstUserList.size(); i++) {
			// 수신자 저장
			to[0] = newSbstUserList.get(i).get("usr_id");
			// 언어 설정
			String langCd = newSbstUserList.get(i).get("lang_cd");
			if(langCd == null || langCd.isEmpty()) {
				langCd = "KO";
			}
			temp.put("SESS_LANG", langCd);
			temp.put("wkpl_id", newSbstUserList.get(i).get("wkpl_id"));
			// 수신자의 신규 자재 입고 정보 조회
			List<Map<String, Object>> sbstUserDataList = list;
			// 다국어 처리
			String[] mlangNo = {"MS00000242", "LB00000024", "LB00000025"
					           ,"LB00000558"};
			List<Map<String, String>> mlangCntn = mUtil.getMLangList(Arrays.asList(mlangNo), langCd);
			// 메일 업무 코드
			String bizCd = "25";
			// 메일 제목
			Calendar c1 = new GregorianCalendar();
			c1.add(Calendar.DATE, -1);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
			String subject = mUtil.getMLang(mlangCntn, "MS00000242") + "(" + sdf.format(c1.getTime()) + ")"; // 신규 자재 규제 검토 알림
			// 테이블 동적 생성
			String table = "";
			for(int j = 0; j < sbstUserDataList.size(); j++) {
				String mtrlNm = "";
				if(langCd == "KO") {
					mtrlNm = String.valueOf(sbstUserDataList.get(j).get("mtrl_ko_nm"));
				}else {
					mtrlNm = String.valueOf(sbstUserDataList.get(j).get("mtrl_en_nm"));
				}
				table += "        <tr>\r\n" + 
						"            <td style=\"width:10%;height:24px;border-bottom:1px solid #ccc;border-right:1px solid #ccc;border-left:1px solid #ccc;padding:0px 3px 0px 3px;;\">\r\n" + 
						"                <p style=\"text-align: center;\">" + (j + 1) + "</p>\r\n" + 
						"            </td>\r\n" + 
						"            <td style=\"width:30%;height:24px;border-bottom:1px solid #ccc;border-right:1px solid #ccc;padding:0px 3px 0px 3px;;\">\r\n" + 
						"                <p style=\"text-align: center;\">" + String.valueOf(sbstUserDataList.get(j).get("mtrl_no")) + "</p>\r\n" + 
						"            </td>\r\n" + 
						"            <td style=\"width:30%;height:24px;border-bottom:1px solid #ccc;border-right:1px solid #ccc;padding:0px 3px 0px 3px;;\">\r\n" + 
						"                <p>" + mtrlNm + "</p>\r\n" + 
						"            </td>\r\n" + 
						"            <td style=\"width:30%;height:24px;border-bottom:1px solid #ccc;border-right:1px solid #ccc;padding:0px 3px 0px 3px;;\">\r\n" + 
						"                <p style=\"text-align: center;\">" + Float.valueOf(String.valueOf(sbstUserDataList.get(j).get("use_qty"))) + "</p>\r\n" + 
						"            </td>\r\n" + 
						"        </tr>\r\n";
			}
			// 메일 내용
			String content = 
					"<table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"border:none;border-collapse:collapse;word-break:break-all;font-family:&quot;Malgun Gothic&quot;, &quot;맑은 고딕&quot;;font-size:13.3333px\">\r\n" + 
					"    <tbody>\r\n" + 
					"        <tr>\r\n" + 
					"            <td style=\"width: 10%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"                <p style=\"text-align: center;\"><span style=\"font-weight: bold;\">" + "No." + "</span><br></p>\r\n" + 
					"            </td>\r\n" + 
					"            <td style=\"width: 30%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"                <p style=\"font-weight: bold; text-align: center;\"><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000024") + "</span></p>\r\n" + 
					"            </td>\r\n" + 
					"            <td style=\"width: 30%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"                <p style=\"font-weight: bold; text-align: center;\"><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000025") + "</span></p>\r\n" + 
					"            </td>\r\n" + 
					"            <td style=\"width: 30%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"                <p style=\"text-align: center;\"><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000558") + "</span></p>\r\n" + 
					"            </td>\r\n" + 
					"        </tr>\r\n" + 
					table +
					"    </tbody>\r\n" + 
					"</table>";
			
			// 메일 송신
			if(sbstUserDataList.size() > 0) {
				mailResult = util.sendMail(bizCd, "AC928960", subject, content, to);
			} else {
				mailResult = true;
				return mailResult;
			}
			
			// 메일 결과에 따라 메일 전송 여부 Y로 체크
			if(mailResult) {
				temp.put("subject", subject);
				temp.put("mContent", content);
				temp.put("mTo", to[0]);
				temp.put("mEmail", newSbstUserList.get(i).get("usr_email"));
				mapper.insertRgltActAlrmRecv(temp);
			} else {
				throw new Exception("Send Mail Error");
			}
		}
		temp.put("alrm_type_cd", "AL004");
		mapper.insertRgltActAlrm(temp);
		for(int j = 0; j < list.size(); j++) {
			// temp.put("wkpl_id", list.get(j).get("wkpl_id"));
			temp.put("qtt", list.get(j).get("qtt"));
			temp.put("mtrl_no", String.valueOf(list.get(j).get("mtrl_no")));
			mapper.insertRgltActAlrmMtrl(temp);
		}
		
		return mailResult; 
	}
}
