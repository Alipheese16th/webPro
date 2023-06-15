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
public class UnCheckedMailJob implements Job {
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
        logger.info("UnCheckedMailJob start, jobKey: " + context.getJobDetail().getKey().getName());
        
        Map<String, String> param = new HashMap<>();
        param.put("batCd", context.getJobDetail().getKey().getName());
        mapper.insertBatch(param); // start
        logger.info("UnCheckedMailJob batNo : " + param.get("batNo"));
        
        // 배치 실행 중 상태 코드 업데이트
        param.put("resultCd", "EX");
        param.put("content", "");
        mapper.updateBatch(param);
        
        // 실제 배치 실행 후 결과 코드 
        content = "";
        resultCd = "ER";
        transactionHandler.runInTransaction(() -> {
	        try {
	        	// 알림 메일 전송
        		Boolean result = sendCircChkNotiMail(); // 해당 점검 담당자에게 메일 전송
        		
        		if(!result) {
        			throw new Exception("Send Mail Error");
        		}
	        	
	        	// 알림 메일 전송
        		Boolean result2 = sendJntChkNotiMail(); // 해당 점검 담당자에게 메일 전송
        		
        		if(!result2) {
        			throw new Exception("Send Mail Error");
        		}
	        	
        		Boolean result3 = false;
	        	// 시정 지시서 일자 초과 정보 확인
        		result3 = sendCrinChkNotiMail();
        		if(!result3) {
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
        logger.info("UnCheckedMailJob start, jobKey: " + "BS002");
        
        Map<String, String> param = new HashMap<>();
        param.put("batCd", "BS002");
        mapper.insertBatch(param); // start
        logger.info("UnCheckedMailJob batNo : " + param.get("batNo"));
        
        // 배치 실행 중 상태 코드 업데이트
        param.put("resultCd", "EX");
        param.put("content", "");
        mapper.updateBatch(param);
        
        // 실제 배치 실행 후 결과 코드 
        content = "";
        resultCd = "ER";
        transactionHandler.runInTransaction(() -> {
	        try {
	        	// 알림 메일 전송
        		Boolean result = sendCircChkNotiMail(); // 해당 점검 담당자에게 메일 전송
        		
        		if(!result) {
        			throw new Exception("Send Mail Error");
        		}
	        	
	        	// 알림 메일 전송
        		Boolean result2 = sendJntChkNotiMail(); // 해당 점검 담당자에게 메일 전송
        		
        		if(!result2) {
        			throw new Exception("Send Mail Error");
        		}
	        	
        		Boolean result3 = false;
	        	// 시정 지시서 일자 초과 정보 확인
        		result3 = sendCrinChkNotiMail();
        		if(!result3) {
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
	public boolean sendCircChkNotiMail() throws Exception {		
		Map<String, String> temp = new HashMap<>();		
		
		// 미점검 점검일 초과 유저 조회
		List<Map<String, Object>> userList = mapper.selectCircOverDateList(temp);
		
		Boolean mailResult = true;
		
		for(int i = 0; i < userList.size(); i++) {
			temp.put("chkList", "Y");
			String sendUser = String.valueOf(userList.get(i).get("circ_chk_supe_usr_id"));
			
			temp.put("circ_chk_supe_usr_id", sendUser);
			temp.put("lang_cd", String.valueOf(userList.get(i).get("lang_cd")));
			// 수신자 학인
			String[] to = new String[1];
			// 수신자 저장
			to[0] = String.valueOf(sendUser);
			// 수신자 저장(임시)
			// to[0] = "AC926550";
			
			// 순회 점검 리스트 조회
			List<Map<String, Object>> circList = mapper.selectCircOverDateList(temp);
			
			// 다국어 처리
			String[] mlangNo = {"LB00000508", "LB00000509", "LB00000510"
								, "LB00000511", "LB00000021", "LB00000024"
								, "LB00000025", "LB00000512", "LB00000143"
								, "LB00000449", "LB00000275", "LB00000640"
								, "LB00000739" ,"LB00000681", "LB00000679"
								, "LB00000150"};
			List<Map<String, String>> mlangCntn = mUtil.getMLangList(Arrays.asList(mlangNo), String.valueOf(circList.get(0).get("lang_cd")));
			// 메일 업무 코드(정의 필요)
			String bizCd = "30";
			// 메일 제목(정의 필요)
			String title = "순회 점검 미진행 목록 확인 메일(" + circList.size() + " 건)";
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
	public boolean sendJntChkNotiMail() throws Exception {		
		Map<String, String> temp = new HashMap<>();
		
		// 미점검 점검일 초과 유저 조회
		List<Map<String, Object>> userList = mapper.selectJntOverDateList(temp);
		
		Boolean mailResult = true;
		
		for(int i = 0; i < userList.size(); i++) {
			temp.put("chkList", "Y");
			String sendUser = String.valueOf(userList.get(i).get("jnt_chk_supe_usr_id"));
			
			temp.put("jnt_chk_supe_usr_id", sendUser);
			temp.put("lang_cd", String.valueOf(userList.get(i).get("lang_cd")));
			// 수신자 학인
			String[] to = new String[1];
			// 수신자 저장
			to[0] = String.valueOf(sendUser);
			// 수신자 저장(임시)
			// to[0] = "AC926550";
			
			// 순회 점검 리스트 조회
			List<Map<String, Object>> jntList = mapper.selectJntOverDateList(temp);
			
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
			String title = "합동 점검 미진행 목록 확인 메일(" + jntList.size() + " 건)";
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
	
	@Transactional
	public boolean sendCrinChkNotiMail() throws Exception {		
		Map<String, String> temp = new HashMap<>();
		
		// 미점검 점검일 초과 유저 조회
		List<Map<String, Object>> userList = mapper.selectCrinOverDateList(temp);
		
		Boolean mailResult = true;
		
		for(int i = 0; i < userList.size(); i++) {
			temp.put("chkList", "Y");
			String sendUser = String.valueOf(userList.get(i).get("crin_actn_schd_usr_id"));
			
			temp.put("crin_actn_schd_usr_id", sendUser);
			temp.put("lang_cd", String.valueOf(userList.get(i).get("lang_cd")));
			// 수신자 학인
			String[] to = new String[1];
			// 수신자 저장
			to[0] = String.valueOf(sendUser);
			// 수신자 저장(임시)
			// to[0] = "AC926550";
			
			// 순회 점검 리스트 조회
			List<Map<String, Object>> crinList = mapper.selectCrinOverDateList(temp);
			
			// 다국어 처리
			String[] mlangNo = {"LB00000508", "LB00000509", "LB00000510"
								, "LB00000511", "LB00000021", "LB00000024"
								, "LB00000025", "LB00000512", "LB00000143"
								, "LB00000449", "LB00000275", "LB00000669"
								, "LB00000640", "LB00000739", "LB00000681"
								, "LB00000680", "LB00000679", "LB00000673"};
			List<Map<String, String>> mlangCntn = mUtil.getMLangList(Arrays.asList(mlangNo), String.valueOf(crinList.get(0).get("lang_cd")));
			// 메일 업무 코드(정의 필요)
			String bizCd = "32";
			// 메일 제목(정의 필요)
			String title = "시정지시서 미조치 목록 확인 메일(" + crinList.size() + " 건)";
			// 메일 내용(정의 필요)
			String content = "<table class=\"txc-table\" width=\"846\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"border:none;border-collapse:collapse;word-break:break-all;font-family:&quot;Malgun Gothic&quot;, &quot;맑은 고딕&quot;;font-size:13.3333px\">\r\n" + 
					"    <tbody>\r\n" + 
					"        <tr>\r\n" + 
					"            <td style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(234, 234, 234);\">\r\n" + 
					"                <p style=\"text-align: center;\"><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000021") + "</span><br></p>\r\n" + 
					"            </td>\r\n" + 
					"            <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(234, 234, 234);\">\r\n" + 
					"                <p style=\"font-weight: bold; text-align: center;\"><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000669") + "</span></p>\r\n" + 
					"            </td>\r\n" +
					"            <td style=\"width: 14%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(234, 234, 234);\">\r\n" + 
					"                <p style=\"font-weight: bold; text-align: center;\"><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000640") + "</span></p>\r\n" + 
					"            </td>\r\n" + 
					"            <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(234, 234, 234);\">\r\n" + 
					"                <p style=\"font-weight: bold; text-align: center;\"><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000739") + "</span></p>\r\n" + 
					"            </td>\r\n" + 
					"            <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(234, 234, 234);\">\r\n" + 
					"                <p style=\"font-weight: bold; text-align: center;\"><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000681") + "</span></p>\r\n" + 
					"            </td>\r\n" + 
					"            <td style=\"width: 14%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(234, 234, 234);\">\r\n" + 
					"                <p style=\"font-weight: bold; text-align: center;\"><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000680") + "</span></p>\r\n" + 
					"            </td>\r\n" +
					"            <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(234, 234, 234);\">\r\n" + 
					"                <p style=\"text-align: center;\"><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000679") + "</span></p>\r\n" + 
					"            </td>\r\n" + 
					"            <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(234, 234, 234);\">\r\n" + 
					"                <p style=\"text-align: center;\"><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000673") + "</span></p>\r\n" + 
					"            </td>\r\n" +
					"        </tr>";
			for(int j = 0; j < crinList.size(); j++) {
				Map<String, Object> tempData = crinList.get(j);
				content += "<tr>\r\n" + 
						"            <td style=\"width:12%;height:24px;border-bottom:1px solid #ccc;border-right:1px solid #ccc;border-left:1px solid #ccc;padding:0px 3px 0px 3px;;\">\r\n" + 
						"                <p>" + String.valueOf(tempData.get("wkpl_nm")) + "</p>\r\n" + 
						"            </td>\r\n" + 
						"            <td style=\"width:12%;height:24px;border-bottom:1px solid #ccc;border-right:1px solid #ccc;padding:0px 3px 0px 3px;;\">\r\n" + 
						"                <p>" + String.valueOf(tempData.get("crin_cl_cd_nm")) + "</p>\r\n" + 
						"            </td>\r\n" + 
						"            <td style=\"width:14%;height:24px;border-bottom:1px solid #ccc;border-right:1px solid #ccc;padding:0px 3px 0px 3px;;\">\r\n" + 
						"                <p style=\"text-decoration: underline;\"><a href=\"" + frontUrl + "/sftchk/crrtdrtmgntdtledt/" + String.valueOf(tempData.get("crin_id")) + "\"" + ">" + String.valueOf(tempData.get("crin_chk_tgt_nm")) + "</a></p>\r\n" + 
						"            </td>\r\n" + 
						"            <td style=\"width:12%;height:24px;border-bottom:1px solid #ccc;border-right:1px solid #ccc;padding:0px 3px 0px 3px;;\">\r\n" + 
						"                <p>" + String.valueOf(tempData.get("crin_chk_wkpl_loc_nm")) + "</p>\r\n" + 
						"            </td>\r\n" + 
						"            <td style=\"width:12%;height:24px;border-bottom:1px solid #ccc;border-right:1px solid #ccc;padding:0px 3px 0px 3px;;\">\r\n" + 
						"                <p>" + String.valueOf(tempData.get("crin_chk_dtl_loc_nm")) + "</p>\r\n" + 
						"            </td>\r\n" + 
						"            <td style=\"width:14%;height:24px;border-bottom:1px solid #ccc;border-right:1px solid #ccc;padding:0px 3px 0px 3px;;\">\r\n" + 
						"                <p>" + String.valueOf(tempData.get("crin_chk_dttm")) + "</p>\r\n" + 
						"            </td>\r\n" + 
						"            <td style=\"width:12%;height:24px;border-bottom:1px solid #ccc;border-right:1px solid #ccc;padding:0px 3px 0px 3px;;\">\r\n" + 
						"                <p>" + String.valueOf(tempData.get("crin_chk_supe_usr_nm")) + "</p>\r\n" + 
						"            </td>\r\n" + 
						"            <td style=\"width:12%;height:24px;border-bottom:1px solid #ccc;border-right:1px solid #ccc;padding:0px 3px 0px 3px;;\">\r\n" + 
						"                <p>" + String.valueOf(tempData.get("crin_actn_schd_usr_nm")) + "</p>\r\n" + 
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
