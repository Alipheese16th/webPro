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
public class HmflAlrmJob implements Job {
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
        logger.info("HmflAlrmJob start, jobKey: " + context.getJobDetail().getKey().getName());
        
        Map<String, String> param = new HashMap<>();
        param.put("batCd", context.getJobDetail().getKey().getName());
        mapper.insertBatch(param); // start
        logger.info("HmflAlrmJob batNo : " + param.get("batNo"));
        
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
	        	// 대상 메일 데이터 키 조회
	        	List<Map<String, Object>> hmflList = mapper.selectHmflTgtList(temp);
	        	if(hmflList.size() > 0) {
	        		sendMail(hmflList);
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
        logger.info("HmflAlrmJob start, jobKey: " + "BS005");
        
        Map<String, String> param = new HashMap<>();
        param.put("batCd", "BS005");
        mapper.insertBatch(param); // start
        logger.info("HmflAlrmJob batNo : " + param.get("batNo"));
        
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
	        	// 대상 메일 데이터 키 조회
	        	List<Map<String, Object>> hmflList = mapper.selectHmflTgtList(temp);
	        	if(hmflList.size() > 0) {
	        		sendMail(hmflList);
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
	public boolean sendMail(List<Map<String, Object>> list) throws Exception {		
		String[] to = new String[1];
		
		Boolean mailResult = true;
		for(int i=0; i<list.size(); i++) {
			Map<String, Object> temp = list.get(i);
			temp.put("SESS_LANG", "KO");
			// 수신자 학인
			List<Map<String, String>> userList = mapper.selectHmflTgtEmpList(temp);
			// 메일 내용
			List<Map<String, String>> info = mapper.selectHmflMcnDtlBatch(temp);
			
			for (int j=0; j<userList.size(); j++) {
				
				// 수신자 저장
				to[0] = userList.get(j).get("tgt_usr_id");
				
				// 언어 설정
				String langCd = "KO";
				
				// 메일 정보
				Map<String, String> tempBasic = info.get(0);
				
				// 다국어 처리
				String[] mlangNo = {"MS00000239", "LB00000021", "LB00000024"
						           ,"LB00000025", "LB00000479"};
				List<Map<String, String>> mlangCntn = mUtil.getMLangList(Arrays.asList(mlangNo), langCd);
				
				// 메일 업무 코드
				String bizCd = "25";
				
				// 메일 제목
				String subject = "점검주기 3개월 도래 알림" + "(" + tempBasic.get("hrm_nm") + ")"; // 점검주기 3개월 도래 알림
				
				// 메일 내용
				String content = 
						"<div>\r\n" +  
						"    <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + "작업계획 기본정보" + "</p>\r\n" + 
						"    <table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;\">\r\n" + 
						"        <tbody>\r\n" + 
						"            <tr>\r\n" + 
						"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
						"                    <p style=\"font-weight: bold;\">" + "사업장" + "<br></p>\r\n" + 
						"                </td>\r\n" + 
						"                <td colspan=\"2\" style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
						"                    <p>" + tempBasic.get("wkpl_nm") + "<br></p>\r\n" + 
						"                </td>\r\n" + 
						"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
						"                    <p style=\"font-weight: bold;\">" + "기기종류" + "</p>\r\n" + 
						"                </td>\r\n" + 
						"                <td colspan=\"2\" style=\"width: 28%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
						"                    <p>" + tempBasic.get("hrm_type_nm") + "<br></p>\r\n" + 
						"                </td>\r\n" + 
						"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
						"                    <p style=\"font-weight: bold;\">" + "최종 수정자" + "</p>\r\n" + 
						"                </td>\r\n" + 
						"                <td colspan=\"2\" style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
						"                    <p>" + tempBasic.get("lsch_nm") + "<br></p>\r\n" + 
						"                </td>\r\n" + 
						"            </tr>\r\n" + 				
						"            <tr>\r\n" + 
						"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
						"                    <p style=\"font-weight: bold;\">" + "기기명" + "<br></p>\r\n" + 
						"                </td>\r\n" + 
						"                <td colspan=\"5\" style=\"width: 58%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
						"                    <p>" + tempBasic.get("hrm_nm") + "<br></p>\r\n" + 
						"                </td>\r\n" + 
						"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
						"                    <p style=\"font-weight: bold;\">" + "유해위험기계기구 번호" + "</p>\r\n" + 
						"                </td>\r\n" + 
						"                <td colspan=\"2\" style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
						"                    <p>" + tempBasic.get("hrm_mgnt_no") + "<br></p>\r\n" + 
						"                </td>\r\n" + 
						"            </tr>\r\n" + 
						"            <tr>\r\n" + 
						"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
						"                    <p style=\"font-weight: bold;\">" + "설치일자" + "<br></p>\r\n" + 
						"                </td>\r\n" + 
						"                <td colspan=\"2\" style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
						"                    <p>" + tempBasic.get("hrm_istl_dt") + "<br></p>\r\n" + 
						"                </td>\r\n" + 
						"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
						"                    <p style=\"font-weight: bold;\">" + "제조사" + "</p>\r\n" + 
						"                </td>\r\n" + 
						"                <td colspan=\"2\" style=\"width: 28%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
						"                    <p>" + tempBasic.get("hrm_mnfr_nm") + "<br></p>\r\n" + 
						"                </td>\r\n" + 
						"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
						"                    <p style=\"font-weight: bold;\">" + "자산번호" + "</p>\r\n" + 
						"                </td>\r\n" + 
						"                <td colspan=\"2\" style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
						"                    <p>" + tempBasic.get("hrm_asst_no") + "<br></p>\r\n" + 
						"                </td>\r\n" + 
						"            </tr>\r\n" + 
						"            <tr>\r\n" + 
						"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
						"                    <p style=\"font-weight: bold;\">" + "위치" + "<br></p>\r\n" + 
						"                </td>\r\n" + 
						"                <td colspan=\"2\" style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
						"                    <p>" + tempBasic.get("hrm_wkpl_loc_nm") + "<br></p>\r\n" + 
						"                </td>\r\n" + 
						"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
						"                    <p style=\"font-weight: bold;\">" + "상세위치" + "</p>\r\n" + 
						"                </td>\r\n" + 
						"                <td colspan=\"2\" style=\"width: 28%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
						"                    <p>" + tempBasic.get("hrm_dtl_loc_nm") + "<br></p>\r\n" + 
						"                </td>\r\n" + 
						"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
						"                    <p style=\"font-weight: bold;\">" + "점검주기" + "</p>\r\n" + 
						"                </td>\r\n" + 
						"                <td colspan=\"2\" style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
						"                    <p>" + tempBasic.get("hrm_chk_cyc_nm") + "<br></p>\r\n" + 
						"                </td>\r\n" + 
						"            </tr>\r\n" + 		
						"            <tr>\r\n" + 
						"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
						"                    <p style=\"font-weight: bold;\">" + "담당자(정)" + "<br></p>\r\n" + 
						"                </td>\r\n" + 
						"                <td colspan=\"2\" style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
						"                    <p>" + tempBasic.get("hrm_rspn_usr_nm") + "<br></p>\r\n" + 
						"                </td>\r\n" + 
						"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
						"                    <p style=\"font-weight: bold;\">" + "담당자(부)" + "</p>\r\n" + 
						"                </td>\r\n" + 
						"                <td colspan=\"2\" style=\"width: 28%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
						"                    <p>" + tempBasic.get("hrm_spic_usr_nm") + "<br></p>\r\n" + 
						"                </td>\r\n" + 
						"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
						"                    <p style=\"font-weight: bold;\">" + "사용여부" + "</p>\r\n" + 
						"                </td>\r\n" + 
						"                <td colspan=\"2\" style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
						"                    <p>" + tempBasic.get("hrm_usg_yn") + "<br></p>\r\n" + 
						"                </td>\r\n" + 
						"            </tr>\r\n" + 		
						"            <tr>\r\n" + 
						"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
						"                    <p style=\"font-weight: bold;\">" + "유해위험기계기구 SPEC" + "<br></p>\r\n" + 
						"                </td>\r\n" + 
						"                <td colspan=\"8\" style=\"width: 88%; height: 72px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
						"                    <p>" + tempBasic.get("hrm_spec_txt") + "<br></p>\r\n" + 
						"                </td>\r\n" + 
						"            </tr>\r\n" +
						"        </tbody>\r\n" + 
						"    </table>\r\n" + 
						"    <p>\r\n" + 
						"        <spam style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + "바로가기" + " :&nbsp;</spam><a href=\"" + frontUrl + "/hmflmcn/hmflriskmcnmgntdtllup" + "/" + tempBasic.get("hrm_id") + "\" style=\"font-family: &quot;Malgun Gothic&quot;; font-size: medium;\"target=\"_blank\">" + frontUrl + "/hmflmcn/hmflriskmcnmgntdtllup" + "/" + tempBasic.get("hrm_id") + "</a>\r\n" + 
						"    </p>\r\n" +
						"</div>";
				
				// 메일 송신
				if(tempBasic.size() > 0) {
					mailResult = util.sendMail(bizCd, "AC928960", subject, content, to);
				} else {
					mailResult = true;
					return mailResult;
				}
				
				// 메일 결과 체크
				if(!mailResult) {
					throw new Exception("Send Mail Error");
				}
			}
		}
		
		return mailResult; 
	}
}
