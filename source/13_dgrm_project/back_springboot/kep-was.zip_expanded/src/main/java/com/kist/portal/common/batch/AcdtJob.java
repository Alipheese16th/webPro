package com.kist.portal.common.batch;
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
import com.kist.portal.common.utils.TransactionHandler;

@Component
public class AcdtJob implements Job {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	BatchMapper mapper;
	
	@Autowired
	private SendEmailUtil util;
	
	@Value("${dgrm.front-url}")
	private String frontUrl;
	
	@Autowired
	TransactionHandler transactionHandler;
	
	private String content = "";
	private String resultCd = "";
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		
		logger.info("AcdtJob start, jobKey: " + "BS007");
		
		Map<String, String> param = new HashMap<>();
        param.put("batCd", context.getJobDetail().getKey().getName());
        mapper.insertBatch(param); // start
        logger.info("AcdtJob batNo : " + param.get("batNo"));
        
        // 배치 실행 중 상태 코드 업데이트
        param.put("resultCd", "EX");
        param.put("content", "");
        mapper.updateBatch(param);
		
        // 실제 배치 실행 후 결과 코드 
        content = "";
        resultCd = "ER";  
        transactionHandler.runInTransaction(() -> {
	        try {	        	
	        	// 1. 임시저장 데이터 정보 가져오기
	        	List<Map<String, Object>> tempSaveList = mapper.selectAcdtTempSave(param);
	        	for(int i = 0; i < tempSaveList.size(); i++) {	        		
	        		Map<String, Object> temp = new HashMap<>();
	        		temp.put("acdt_id", tempSaveList.get(i).get("acdt_id"));
	        		// 1-2. 임시저장된 데이터 중 7일 지난 데이터 del_yn -> Y으로...
	        		mapper.updateAcdtTempSave(temp);
	        	}
	        	
	        	// 2. 조사보고완료 요청 메일 발송	      
	        	// 조사보고완료 요청 메일데이터 정보 가져오기(14일 이상 등록중인 상태)
	        	List<Map<String, Object>> investReportCompleList = mapper.selectAcdtInvestReportComple(param);
	        	if(investReportCompleList.size() > 0) {
	        	    try {
	        	    	sendMail(investReportCompleList);
	        	    } catch (Exception e) {
	        	        logger.error(e.getMessage());
					}
	        	}  
	        	
	        	// 3. 재발방지대책 이행결과 - 조치지연 처리
	        	List<Map<String, Object>> delayInAction = mapper.selectAcdtDelayList(param);
	        	for(int i = 0; i < delayInAction.size(); i++) {	        		
	        		Map<String, Object> temp = new HashMap<>();
	        		temp.put("acdt_id", delayInAction.get(i).get("acdt_id"));
	        		// 조치기한이 한개라도 지난 건들을 조치지연상태로 처리
	        		mapper.updateAcdtDelayList(temp);
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
		logger.info("AcdtJob start, jobKey: " + "BS007");
		
		Map<String, String> param = new HashMap<>();
        param.put("batCd", "BS007");
        mapper.insertBatch(param); // start
        logger.info("AcdtJob batNo : " + param.get("batNo"));
        
        // 배치 실행 중 상태 코드 업데이트
        param.put("resultCd", "EX");
        param.put("content", "");
        mapper.updateBatch(param);
		
        // 실제 배치 실행 후 결과 코드 
        content = "";
        resultCd = "ER";
        transactionHandler.runInTransaction(() -> {
	        try {
	        	
	        	//1. 임시저장 데이터 정보 가져오기
	        	List<Map<String, Object>> tempSaveList = mapper.selectAcdtTempSave(param);
	        	for(int i = 0; i < tempSaveList.size(); i++) {	        		
	        		Map<String, Object> temp = new HashMap<>();
	        		temp.put("acdt_id", tempSaveList.get(i).get("acdt_id"));
	        		// 1-2. 임시저장된 데이터 중 7일 지난 데이터 del_yn -> Y으로...
	        		mapper.updateAcdtTempSave(temp);
	        	}
	        	
	        	// 2. 조사보고완료 요청 메일 발송	      
	        	// 조사보고완료 요청 메일데이터 정보 가져오기(14일 이상 등록중인 상태)
	        	List<Map<String, Object>> investReportCompleList = mapper.selectAcdtInvestReportComple(param);
	        	if(investReportCompleList.size() > 0) {
	        	    try {
	        	    	sendMail(investReportCompleList);
	        	    } catch (Exception e) {
	        	        logger.error(e.getMessage());
					}
	        	}
	        	
	        	// 3. 재발방지대책 이행결과 - 조치지연 처리
	        	List<Map<String, Object>> delayInAction = mapper.selectAcdtDelayList(param);
	        	for(int i = 0; i < delayInAction.size(); i++) {	        		
	        		Map<String, Object> temp = new HashMap<>();
	        		temp.put("acdt_id", delayInAction.get(i).get("acdt_id"));
	        		// 조치기한이 한개라도 지난 건들을 조치지연상태로 처리
	        		mapper.updateAcdtDelayList(temp);
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
	public boolean sendMail(List<Map<String, Object>> list) throws Exception {		
		
		// 수신
		String[] to = new String[1];
		
		// 참조
		String[] cc = new String[1];
		
		// 메일 발송 성공여부
		Boolean result = false;
		
		// 메일 형태
		String callType = "";
		
		for(int i=0; i < list.size(); i++) {
			
			Map<String, Object> temp = list.get(i);
			temp.put("SESS_LANG", "KO");
			
			// 수신자
			String userId = (String) temp.get("upt_usr_id");
			
			// 사고번호
			String sheAcdtId = (String) temp.get("acdt_id");
						
			// 사고일시 일자
			String acdtOccrTspDt = (String) temp.get("acdt_occr_tsp_dt").toString();

			String shePortalLink = frontUrl + "/acdtmgnt/acdtmgntdtledt/" + sheAcdtId;
			
			if(Math.round((Double) temp.get("dayinterval")) > 17) {
				//17일 초과한 데이터
				callType = "excess";
			} else if (Math.round((Double) temp.get("dayinterval")) == 17) {
				//17일째 인 데이터
				callType = "relevant";
			} else if (Math.round((Double) temp.get("dayinterval")) == 14) {
				//14일째 인 데이터
				callType = "elapsed";
			}else {
				continue;
			}
			
			// 수신자 저장
			to[0] = userId;
			
			String bizCd = "41";
			
			// 메일 제목
			String title = "";
			
			// 메일 내용			
			String content = "";
			content +=
					util.getmailCss() +
					"<div id=\"acdtJob\" style=\"width:720px; display:table; position:relative;\">\r\n" +
					"<table cellspacing=\"0\" cellpadding=\"0\" style=\"width:100%; \"\">\r\n" + 
					"	<thead>\r\n" +
					"		<tr style=\"background-color: #062c5e;\">\r\n" +	
					"			<td style=\"border: 0;\">\r\n" +
					"				<p style=\"height: 60px; padding-left: 15px;\">\r\n" +
					" 					<img src=\"http://localhost:8080/_nuxt/img/logo.d0672ff.png" + "\" style=\"height: 20px; margin-top: 20px;\">&nbsp;&nbsp;\r\n" +
					" 					<span style=\"color: white; font-size: 20px;\">사고 조사보고완료 요청 메일</span>\r\n" +
					"				</p>\r\n" +
					"			</td>\r\n" +	
					"		</tr>\r\n" +	
					"	</thead>\r\n" +
					"	<tbody style=\"display:table; width:100%; border-left:1px solid #ddd; border-right:1px solid #ddd;\">\r\n" +
					"	<tr>\r\n" +	
					"		<td style=\"font-size: 14px; line-height:1.5; padding:30px 30px 130px 30px;\">\r\n" +	
					" 			<p>SHE Portal 에서 사고 조사보고완료 요청 드립니다.</p>\r\n";
			
			// 17일 초과
			if("excess".equals(callType)) {
				title = "[SHE Portal] 사고 조사보고완료 안내 D+" + (Math.round((Double) temp.get("dayinterval")) - 17) + "(사고번호" + sheAcdtId +")";
				
				content +=						
						" 	<p>해당 사고의 조사보고완료 마감이 "+ (Math.round((Double) temp.get("dayinterval")) - 17) +"일 초과 되었습니다.</p>\r\n" +
						" 	<br/>\r\n" +
						" 	<p>ㆍ 사고번호: "+ sheAcdtId +"</p>\r\n" +
						" 	<p>ㆍ 사고일시: "+ acdtOccrTspDt +"</p>\r\n";
								
				cc[0] = (String) temp.get("fscr_dpth_id");
			} else if("relevant".equals(callType)) {
				title = "[SHE Portal] 사고 조사보고완료 안내 D-0 (사고번호" + sheAcdtId +")";
				
				// 17일째
				content +=						
						" 	<p>해당 사고의 조사보고완료 마감일 입니다.</p>\r\n" +
						" 	<br/>\r\n" +
						" 	<p>ㆍ 사고번호: "+ sheAcdtId +"</p>\r\n" +
						" 	<p>ㆍ 사고일시: "+ acdtOccrTspDt +"</p>\r\n";		
				
				cc[0] = (String) temp.get("fscr_dpth_id");
			} else if("elapsed".equals(callType)) {
				title = "[SHE Portal] 사고 조사보고완료 안내 D-3" +"(사고번호" + sheAcdtId +")";
				
				// 14일째
				content +=
						" 	<p>해당 사고의 조사보고완료 마감 3일 전 입니다.</p>\r\n" +
						" 	<br/>\r\n" +
						" 	<p>ㆍ 사고번호: "+ sheAcdtId +"</p>\r\n" +
						" 	<p>ㆍ 사고일시: "+ acdtOccrTspDt +"</p>\r\n";	
			}
			
			content +=
				"			<p style=\"margin-left:20px; margin-top:10px;\">\r\n" +					
				"				<a target=\"_blank\" href=\"" + shePortalLink + "\"style=\"color: rgb(0,0,255); text-decoration: underline;\" \">" + "SHE Portal Link" + "</a>\r\n" +
				"			</p>\r\n" +	
				" 			<br/>\r\n" +
				" 			<p>*AP ‘중대재해 예방 및 대응을 위한 사고조사 및 재발방지 대책 수립 지침’에 의거 </p>\r\n" +
				" 			<p style=\"margin-left:3px;\">사고보고완료는 사고일 기준 17일 이내 완료 하여야 합니다.</p>\r\n" +
				"		</td>\r\n" +
				"	</tr>\r\n" +
				"	<tr class=\"inquiry_info\" style=\"position:absolute; bottom:90px; display:table; right:10px; z-index:10;\">\r\n" +
				"		<td style=\"border:0; width:100%; display:block; line-height:1.6;\">\r\n" +
				"			<p style=\"text-align:left;\">시스템 문의</p>\r\n" +
				" 			<p style=\"text-align:left;\">안전보건시스템 dehs (dehs@donggramisoft.co.kr)</p>\r\n" +
				"		</td>\r\n" +
				"	</tr>\r\n" +
				"	</tbody>\r\n" +
				"	<tfoot>\r\n" +
				"		<tr style=\"height: 60px; background-color: #062c5e;\">\r\n" +	
				"			<td>\r\n" +
				"			</td>\r\n" +	
				"		</tr>\r\n" +	
				"	</tfoot>\r\n" +
				"</table>\r\n" +
				"<img src=\"" + frontUrl + "/images/ap_background.png\" alt=\"\" style=\"width: 250px; position:absolute; bottom:1px; right:1px;\"\">\r\n" +
				"</div>\r\n";
			
			// 메일 송신 
			Boolean mailResult = false;
			
			//AC928960 안전환경포털시스템
			if(cc[0] != null) {
				// 참조자가 있을 경우
				mailResult = util.sendMail(bizCd, "AC928960", title, content, to, cc);	
			} else {
				// 참조자가 없는 경우
				mailResult = util.sendMail(bizCd, "AC928960", title, content, to);
			}
			result = (mailResult ? true : false);
		}
		
		return result; 
	}

}
