package com.kist.portal.common.batch;

import java.util.ArrayList;
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
import com.kist.portal.common.comm.service.FileService;
import com.kist.portal.common.email.rest.SendEmailUtil;
import com.kist.portal.common.utils.MultiLanguageUtil;
import com.kist.portal.common.utils.TransactionHandler;

@Component
public class ConOprnPrmsAlrmJob implements Job {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	BatchMapper mapper;
	
	@Value("${dgrm.front-url}")
	private String frontUrl;
	
	@Autowired
	private FileService fileService;
	
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
        logger.info("ConOprnPrmsAlrmJob start, jobKey: " + context.getJobDetail().getKey().getName());
        
        Map<String, String> param = new HashMap<>();
        param.put("batCd", context.getJobDetail().getKey().getName());
        mapper.insertBatch(param); // start
        logger.info("ConOprnPrmsAlrmJob batNo : " + param.get("batNo"));
        
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
	        	List<Map<String, Object>> dataList = mapper.selectConOprnPrmsDataBatch(temp);
	        	if(dataList.size() > 0) {
	        		sendMail(dataList);
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
        logger.info("ConOprnPrmsAlrmJob start, jobKey: " + "BS003");
        
        Map<String, String> param = new HashMap<>();
        param.put("batCd", "BS003");
        mapper.insertBatch(param); // start
        logger.info("ConOprnPrmsAlrmJob batNo : " + param.get("batNo"));
        
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
	        	List<Map<String, Object>> dataList = mapper.selectConOprnPrmsDataBatch(temp);
	        	if(dataList.size() > 0) {
	        		sendMail(dataList);
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
			// 메일 내용
			List<Map<String, ?>> basicInfo = mapper.selectConOprnPrmsDtlBatch(temp);
			List<Map<String, ?>> reqInfo = mapper.selectConOprnPrmsReqDtlBatch(temp);
			List<Map<String, ?>> oprnInfo = mapper.selectConOprnPrmsOprnBatch(temp);
			// 작업자 정보
			List<Map<String, ?>> oprrInfo = mapper.selectConOprnPrmsOprrSaveBatch(temp);
			
			Map<String, ?> tempBasic = basicInfo.get(0);
			Map<String, ?> tempReq = reqInfo.get(0);
			Map<String, ?> tempOprn = oprnInfo.get(0);
			
			List<Map<String, ?>> fileList = new ArrayList();
			List<Map<String, ?>> fileReqList = new ArrayList();
			
			if (tempBasic.get("oprn_plan_atfl_no") != null && !"".equals(tempBasic.get("oprn_plan_atfl_no"))) {
				Map<String, String> fileParam = new HashMap<>();
				fileParam.put("id", tempBasic.get("oprn_plan_atfl_no").toString());
				try {
					fileList = fileService.selectFileList(fileParam);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if (tempReq.get("con_req_atfl_no") != null && !"".equals(tempReq.get("con_req_atfl_no"))) {
				Map<String, String> fileParam = new HashMap<>();
				fileParam.put("id", tempReq.get("con_req_atfl_no").toString());
				try {
					fileReqList = fileService.selectFileList(fileParam);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			String conYnRtn = "";
			if ("Y".equals(tempBasic.get("wekd_con_yn").toString())) {
				conYnRtn = conYnRtn + "주중,";
			}
			if ("Y".equals(tempBasic.get("wknd_con_yn").toString())) {
				conYnRtn = conYnRtn + "주말,";
			}
			if ("Y".equals(tempBasic.get("nght_con_yn").toString())) {
				conYnRtn = conYnRtn + "야간,";
			}
			conYnRtn = conYnRtn.substring(0, conYnRtn.length()-1);
			
			String reqConYnRtn = "";
			if ("Y".equals(tempReq.get("wekd_con_yn").toString())) {
				reqConYnRtn = reqConYnRtn + "주중,";
			}
			if ("Y".equals(tempReq.get("wknd_con_yn").toString())) {
				reqConYnRtn = reqConYnRtn + "주말,";
			}
			if ("Y".equals(tempReq.get("nght_con_yn").toString())) {
				reqConYnRtn = reqConYnRtn + "야간,";
			}
			reqConYnRtn = reqConYnRtn.substring(0, reqConYnRtn.length()-1);
			
			String oprnRtn = "";
			if ("Y".equals(tempOprn.get("h_yn").toString())) {
				oprnRtn = oprnRtn + "화기,";
			}
			if ("Y".equals(tempOprn.get("g_yn").toString())) {
				oprnRtn = oprnRtn + "고소,";
			}
			if ("Y".equals(tempOprn.get("m_yn").toString())) {
				oprnRtn = oprnRtn + "밀폐,";
			}
			if ("Y".equals(tempOprn.get("j_yn").toString())) {
				oprnRtn = oprnRtn + "전기차단,";
			}
			if ("Y".equals(tempOprn.get("u_yn").toString())) {
				oprnRtn = oprnRtn + "유해/위험물질,";
			}
			if ("Y".equals(tempOprn.get("v_yn").toString())) {
				oprnRtn = oprnRtn + "중장비사용,";
			}
			if ("Y".equals(tempOprn.get("n_yn").toString())) {
				oprnRtn = oprnRtn + "일반,";
			}
			oprnRtn = oprnRtn.substring(0, oprnRtn.length()-1);
				
			// 수신자 저장
			to[0] = temp.get("con_rq_usr_id").toString();
			
			// 언어 설정
			String langCd = "KO";
			
			
			// 다국어 처리
			String[] mlangNo = {"MS00000239", "LB00000021", "LB00000024"
					           ,"LB00000025", "LB00000479"};
			List<Map<String, String>> mlangCntn = mUtil.getMLangList(Arrays.asList(mlangNo), langCd);
			
			// 메일 업무 코드
			String bizCd = "25";
			
			// 메일 제목
			String subject = "허가서 미출력 알림" + "(" + temp.get("con_nm") + ")"; // 허가서 미출력 알림
			
			// html 태그
			String subTag = "";
			// 대상 html 태그
			for(int j=0; j<oprrInfo.size(); j++) {
				subTag += "            <tr>\r\n" + 
						  "                <td style=\"width: 5%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
						  "                    <p>" + (j+1) + "<br></p>\r\n" + 
						  "                </td>\r\n" + 
						  "                <td style=\"width: 35%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
						  "                    <p>" + oprrInfo.get(j).get("oprr_nm") + "<br></p>\r\n" + 
						  "                </td>\r\n" + 
						  "                <td style=\"width: 40%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
						  "                    <p>" + oprrInfo.get(j).get("oprr_id") + "<br></p>\r\n" + 
						  "                </td>\r\n" + 
						  "                <td style=\"width: 20%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
						  "                    <p>" + oprrInfo.get(j).get("oprn_cl_nm") + "<br></p>\r\n" + 
						  "                </td>\r\n" + 
						  "            </tr>\r\n";
			}
			
			// 대상 html 태그
			String subFile = "";
			if (fileList.size() > 0) {
				for(int j=0; j<fileList.size(); j++) {
					subFile += "            <tr>\r\n" + 
							  "                <td style=\"width: 5%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
							  "                    <p>" + (j+1) + "<br></p>\r\n" + 
							  "                </td>\r\n" + 
							  "                <td style=\"width: 95%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
							  "                    <p>" + "<a href=\"" + frontUrl + "/api/v1/public/downloadSeq/" + fileList.get(j).get("key") + "\">" + fileList.get(j).get("name") + "</a>" + "<br></p>\r\n" + 
							  "                </td>\r\n" + 
							  "            </tr>\r\n";
				}
			}
			// 대상 html 태그
			String subReqFile = "";
			if (fileReqList.size() > 0) {
				for(int j=0; j<fileReqList.size(); j++) {
					subReqFile += "            <tr>\r\n" + 
							  "                <td style=\"width: 5%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
							  "                    <p>" + (j+1) + "<br></p>\r\n" + 
							  "                </td>\r\n" + 
							  "                <td style=\"width: 95%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
							  "                    <p>" + "<a href=\"" + frontUrl + "/api/v1/public/downloadSeq/" + fileReqList.get(j).get("key") + "\">" + fileReqList.get(j).get("name") + "</a>" + "<br></p>\r\n" + 
							  "                </td>\r\n" + 
							  "            </tr>\r\n";
				}
			}
			
			// 메일 내용
			String content = 
					"<div>\r\n" +  
					"    <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + "작업계획 기본정보" + "</p>\r\n" + 
					"    <table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;\">\r\n" + 
					"        <tbody>\r\n" + 
					"            <tr>\r\n" + 
					"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"                    <p style=\"font-weight: bold;\">" + "작업번호" + "<br></p>\r\n" + 
					"                </td>\r\n" + 
					"                <td colspan=\"2\" style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
					"                    <p>" + tempBasic.get("con_id") + "<br></p>\r\n" + 
					"                </td>\r\n" + 
					"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"                    <p style=\"font-weight: bold;\">" + "사업장" + "</p>\r\n" + 
					"                </td>\r\n" + 
					"                <td colspan=\"2\" style=\"width: 28%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
					"                    <p>" + tempBasic.get("con_id") + "<br></p>\r\n" + 
					"                </td>\r\n" + 
					"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"                    <p style=\"font-weight: bold;\">" + "작업 요청자" + "</p>\r\n" + 
					"                </td>\r\n" + 
					"                <td colspan=\"2\" style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
					"                    <p>" + tempBasic.get("con_rq_usr_nm") + "<br></p>\r\n" + 
					"                </td>\r\n" + 
					"            </tr>\r\n" + 				
					"            <tr>\r\n" + 
					"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"                    <p style=\"font-weight: bold;\">" + "작업기간" + "<br></p>\r\n" + 
					"                </td>\r\n" + 
					"                <td colspan=\"5\" style=\"width: 58%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
					"                    <p>" + tempBasic.get("con_bgn_dt") + " ~ " + tempBasic.get("con_end_dt") + " " + conYnRtn + "<br></p>\r\n" + 
					"                </td>\r\n" + 
					"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"                    <p style=\"font-weight: bold;\">" + "작업 주관부서" + "</p>\r\n" + 
					"                </td>\r\n" + 
					"                <td colspan=\"2\" style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
					"                    <p>" + tempBasic.get("con_rspn_org_nm") + "<br></p>\r\n" + 
					"                </td>\r\n" + 
					"            </tr>\r\n" + 
					"            <tr>\r\n" + 
					"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"                    <p style=\"font-weight: bold;\">" + "작업명" + "<br></p>\r\n" + 
					"                </td>\r\n" + 
					"                <td colspan=\"5\" style=\"width: 58%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
					"                    <p>" + tempBasic.get("con_nm") + "<br></p>\r\n" + 
					"                </td>\r\n" + 
					"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"                    <p style=\"font-weight: bold;\">" + "긴급작업 여부" + "</p>\r\n" + 
					"                </td>\r\n" + 
					"                <td colspan=\"2\" style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
					"                    <p>" + tempBasic.get("ugnc_con_yn") + "<br></p>\r\n" + 
					"                </td>\r\n" + 
					"            </tr>\r\n" + 
					"            <tr>\r\n" + 
					"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"                    <p style=\"font-weight: bold;\">" + "작업 업체명" + "<br></p>\r\n" + 
					"                </td>\r\n" + 
					"                <td colspan=\"2\" style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
					"                    <p>" + tempBasic.get("otsr_con_bsen_nm") + "<br></p>\r\n" + 
					"                </td>\r\n" + 
					"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"                    <p style=\"font-weight: bold;\">" + "업체 작업책임자" + "</p>\r\n" + 
					"                </td>\r\n" + 
					"                <td colspan=\"2\" style=\"width: 28%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
					"                    <p>" + tempBasic.get("otsr_con_bsen_pric_nm") + "<br></p>\r\n" + 
					"                </td>\r\n" + 
					"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"                    <p style=\"font-weight: bold;\">" + "업체 담당자 전화번호" + "</p>\r\n" + 
					"                </td>\r\n" + 
					"                <td colspan=\"2\" style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
					"                    <p>" + tempBasic.get("otsr_con_bsen_pric_tphn") + "<br></p>\r\n" + 
					"                </td>\r\n" + 
					"            </tr>\r\n" + 				
					"            <tr>\r\n" + 
					"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"                    <p style=\"font-weight: bold;\">" + "작업내용" + "<br></p>\r\n" + 
					"                </td>\r\n" + 
					"                <td colspan=\"8\" style=\"width: 88%; height: 72px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
					"                    <p>" + tempBasic.get("con_txt") + "<br></p>\r\n" + 
					"                </td>\r\n" + 
					"            </tr>\r\n" + 				
					"        </tbody>\r\n" + 
					"    </table>\r\n" + 

					
					"    <p style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\"><br></p>\r\n" + 
					"    <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + "작업허가정보" + "</p>\r\n" + 
					"    <table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;\">\r\n" + 
					"        <tbody>\r\n" + 
					"            <tr>\r\n" + 
					"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"                    <p style=\"font-weight: bold;\">" + "허가번호" + "<br></p>\r\n" + 
					"                </td>\r\n" + 
					"                <td colspan=\"5\" style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
					"                    <p>" + tempReq.get("con_prms_id") + "<br></p>\r\n" + 
					"                </td>\r\n" + 
					"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"                    <p style=\"font-weight: bold;\">" + "작업일시" + "</p>\r\n" + 
					"                </td>\r\n" + 
					"                <td colspan=\"2\" style=\"width: 28%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
					"                    <p>" + tempReq.get("con_dt") + " " + tempReq.get("con_bgn_hm") + " - " + tempReq.get("con_end_hm") + "  " + reqConYnRtn + "<br></p>\r\n" + 
					"                </td>\r\n" + 
					"            </tr>\r\n" +
					"            <tr>\r\n" + 
					"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"                    <p style=\"font-weight: bold;\">" + "작업종류" + "<br></p>\r\n" + 
					"                </td>\r\n" + 
					"                <td colspan=\"8\" style=\"width: 88%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
					"                    <p>" + oprnRtn + "<br></p>\r\n" + 
					"                </td>\r\n" + 
					"            </tr>\r\n" +
					"            <tr>\r\n" + 
					"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"                    <p style=\"font-weight: bold;\">" + "작업위치" + "<br></p>\r\n" + 
					"                </td>\r\n" + 
					"                <td colspan=\"2\" style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
					"                    <p>" + tempReq.get("con_oprn_wkpl_loc_nm") + "<br></p>\r\n" + 
					"                </td>\r\n" + 
					"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"                    <p style=\"font-weight: bold;\">" + "상세위치" + "</p>\r\n" + 
					"                </td>\r\n" + 
					"                <td colspan=\"2\" style=\"width: 28%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
					"                    <p>" + tempReq.get("con_oprn_dtl_loc_nm") + "<br></p>\r\n" + 
					"                </td>\r\n" + 
					"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"                    <p style=\"font-weight: bold;\">" + "PSM대상" + "</p>\r\n" + 
					"                </td>\r\n" + 
					"                <td colspan=\"2\" style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
					"                    <p>" + tempReq.get("psm_tgt_yn") + "<br></p>\r\n" + 
					"                </td>\r\n" + 
					"            </tr>\r\n" +
					"            <tr>\r\n" + 
					"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"                    <p style=\"font-weight: bold;\">" + "관리감독자" + "<br></p>\r\n" + 
					"                </td>\r\n" + 
					"                <td colspan=\"2\" style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
					"                    <p>" + tempReq.get("con_supe_usr_nm") + "<br></p>\r\n" + 
					"                </td>\r\n" + 
					"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"                    <p style=\"font-weight: bold;\">" + "작업 담당자" + "</p>\r\n" + 
					"                </td>\r\n" + 
					"                <td colspan=\"2\" style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
					"                    <p>" + tempReq.get("con_oprn_supe_usr_nm") + "<br></p>\r\n" + 
					"                </td>\r\n" + 
					"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"                    <p style=\"font-weight: bold;\">" + "안전관리(담당)자" + "</p>\r\n" + 
					"                </td>\r\n" + 
					"                <td colspan=\"2\" style=\"width: 28%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
					"                    <p>" + tempReq.get("con_sft_rspn_usr_nm") + "<br></p>\r\n" + 
					"                </td>\r\n" + 
					"            </tr>\r\n" +
					"            <tr>\r\n" + 
					"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"                    <p style=\"font-weight: bold;\">" + "작업내용" + "<br></p>\r\n" + 
					"                </td>\r\n" + 
					"                <td colspan=\"8\" style=\"width: 88%; height: 72px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
					"                    <p>" + tempReq.get("con_oprn_txt") + "<br></p>\r\n" + 
					"                </td>\r\n" + 
					"            </tr>\r\n" +
					"            <tr>\r\n" + 
					"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"                    <p style=\"font-weight: bold;\">" + "기타 주의사항" + "<br></p>\r\n" + 
					"                </td>\r\n" + 
					"                <td colspan=\"8\" style=\"width: 88%; height: 72px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
					"                    <p>" + tempReq.get("oth_prct_txt") + "<br></p>\r\n" + 
					"                </td>\r\n" + 
					"            </tr>\r\n" +
					"        </tbody>\r\n" + 
					"    </table>\r\n" + 
					
					"    <p style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\"><br></p>\r\n" + 
					"    <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + "작업인원" + "</p>\r\n" + 
					"    <table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;\">\r\n" + 
					"        <tbody>\r\n" + 
					"            <tr>\r\n" + 
					"                <td style=\"width: 5%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"                    <p style=\"font-weight: bold;\">" + "No." + "<br></p>\r\n" + 
					"                </td>\r\n" + 
					"                <td style=\"width: 35%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"                    <p style=\"font-weight: bold;\">" + "성명" + "<br></p>\r\n" + 
					"                </td>\r\n" + 
					"                <td style=\"width: 40%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"                    <p style=\"font-weight: bold;\">" + "작업자 번호" + "<br></p>\r\n" + 
					"                </td>\r\n" + 
					"                <td style=\"width: 20%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"                    <p style=\"font-weight: bold;\">" + "자격구분" + "</p>\r\n" + 
					"                </td>\r\n" + 
					"            </tr>\r\n" +
					subTag + 
					"        </tbody>\r\n" + 
					"    </table>\r\n" + 
					"</div>";
			
			if (fileList.size() > 0) {
				content = content +
						"    <p style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\"><br></p>\r\n" + 
						"    <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + "작업 계획 첨부파일" + "</p>\r\n" + 
						"    <table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;\">\r\n" + 
						"        <tbody>\r\n" + 
						"            <tr>\r\n" + 
						"                <td style=\"width: 5%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
						"                    <p style=\"font-weight: bold;\">" + "No." + "<br></p>\r\n" + 
						"                </td>\r\n" + 
						"                <td style=\"width: 95%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
						"                    <p style=\"font-weight: bold;\">" + "파일명" + "<br></p>\r\n" + 
						"                </td>\r\n" + 
						"            </tr>\r\n" +
						subFile + 
						"        </tbody>\r\n" + 
						"    </table>\r\n";
			}
			
			if (fileReqList.size() > 0) {
				content = content +
						"    <p style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\"><br></p>\r\n" + 
						"    <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + "작업 허가 첨부파일" + "</p>\r\n" + 
						"    <table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;\">\r\n" + 
						"        <tbody>\r\n" + 
						"            <tr>\r\n" + 
						"                <td style=\"width: 5%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
						"                    <p style=\"font-weight: bold;\">" + "No." + "<br></p>\r\n" + 
						"                </td>\r\n" + 
						"                <td style=\"width: 95%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
						"                    <p style=\"font-weight: bold;\">" + "파일명" + "<br></p>\r\n" + 
						"                </td>\r\n" + 
						"            </tr>\r\n" +
						subReqFile + 
						"        </tbody>\r\n" + 
						"    </table>\r\n";
			}
			content = content +
					"    <p>\r\n" + 
					"        <spam style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + "바로가기" + " :&nbsp;</spam><a href=\"" + frontUrl + "/cstprms/cstprmsmgntdtllup" + "/" + tempBasic.get("con_id") + "-" + tempReq.get("con_prms_id") + "\" style=\"font-family: &quot;Malgun Gothic&quot;; font-size: medium;\"target=\"_blank\">" + frontUrl + "/cstprms/cstprmsmgntdtllup" + "/" + tempBasic.get("con_id") + "-" + tempReq.get("con_prms_id") + "</a>\r\n" + 
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
		
		return mailResult; 
	}
}
