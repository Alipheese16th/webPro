package com.kist.portal.common.batch;

import java.util.ArrayList;
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
public class WkplTrnJob implements Job {
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
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("WkplTrnJob start, jobKey: " + context.getJobDetail().getKey().getName());
        
        Map<String, String> param = new HashMap<>();
        param.put("batCd", context.getJobDetail().getKey().getName());
        mapper.insertBatch(param); // start
        logger.info("WkplTrnJob batNo : " + param.get("batNo"));
        
        // 배치 실행 중 상태 코드 업데이트
        param.put("resultCd", "EX");
        param.put("content", "");
        mapper.updateBatch(param);
        
        // 실제 배치 실행 후 결과 코드 
        content = "";
        resultCd = "ER";
        transactionHandler.runInTransaction(() -> {
	        try {
	        	// 1. 법정교육여부가 Y이고 오늘날짜 기준으로 만료일이 지난 데이터를 교육이수증 등록여부 N으로 업데이트
	        	mapper.updateWkplTrn();
	        	// 2. 교육이수증 등록여부가 N인 데이터데 대해 교육 이수 안내, 확인서류 등록 안내 메일 발송
	        	boolean result = sendWkplTrnExprMail();
	        	
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
        logger.info("WkplTrnJob start, jobKey: " + "BS008");
        
        Map<String, String> param = new HashMap<>();
        param.put("batCd", "BS008");
        mapper.insertBatch(param); // start
        logger.info("WkplTrnJob batNo : " + param.get("batNo"));
        
        // 배치 실행 중 상태 코드 업데이트
        param.put("resultCd", "EX");
        param.put("content", "");
        mapper.updateBatch(param);
        
        // 실제 배치 실행 후 결과 코드 
        content = "";
        resultCd = "ER";
        transactionHandler.runInTransaction(() -> {
	        try {
	        	
	        	// 1. 법정교육여부가 Y이고 오늘날짜 기준으로 만료일이 지난 데이터를 교육이수증 등록여부 N으로 업데이트
	        	mapper.updateWkplTrn();
	        	// 2. 교육이수증 등록여부가 N인 데이터데 대해 교육 이수 안내, 확인서류 등록 안내 메일 발송
	        	boolean result = sendWkplTrnExprMail();
	        	
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
	public boolean sendWkplTrnExprMail() throws Exception {	
		List<Map<String, Object>> ccList = new ArrayList<>();
		Map<String, String> param = new HashMap<>();
		param.put("SESS_LANG", "KO");
		
		// 오늘날짜 기준으로 만료일이 지난거 
		List<Map<String, Object>> exprList = mapper.selectWkplElcTrnExpire(param);
		boolean result = true;
		Boolean mailResult = true;
		
		for(int i = 0; i < exprList.size(); i++) {
			Map<String, Object> exprInfo = exprList.get(i);
			String cyccd = String.valueOf(exprInfo.get("elc_trn_cyc_cd"));
			String exprdt = String.valueOf(exprInfo.get("expr_dt"));
			String empid = String.valueOf(exprInfo.get("elc_rspn_usr_id"));
			
			///// 1. 법정 교육 이수 안내 메일 발송
			if(cyccd.equals("3") && (exprdt.equals("30") || exprdt.equals("7"))) {
				// 교육주기(cyccd)가 분기(3)인 데이터를 만료일(exprdt) 30일전, 7일전 전에 발송
				String[] to = new String[1];
				to[0] = empid;
				ccList = mapper.selectWkplElcTrnMng(exprInfo);
				String[] cc = new String[ccList.size()+1];
				for(int j = 0; j < ccList.size(); j++) {
					cc[j+1] = String.valueOf(ccList.get(j).get("elc_rspn_usr_id"));
				}
				cc[0] = String.valueOf(exprInfo.get("dept_head_id"));
				boolean result1 = sendMailTrnGuide(exprInfo, to, cc);
			} else if(!cyccd.equals("3") && (exprdt.equals("60") || exprdt.equals("30")) || exprdt.equals("7")) {
				// 교육주기(cyccd)가 분기가 아닌(!= 3) 데이터를 만료일(exprdt) 60일전, 30일전, 7일전에 발송
				String[] to = new String[1];
				to[0] = empid;
				ccList = mapper.selectWkplElcTrnMng(exprInfo);
				String[] cc = new String[ccList.size()+1];
				for(int j = 0; j < ccList.size(); j++) {
					cc[j+1] = String.valueOf(ccList.get(j).get("elc_rspn_usr_id"));
				}
				cc[0] = String.valueOf(exprInfo.get("dept_head_id"));
				boolean result2 = sendMailTrnGuide(exprInfo, to, cc);
			}
			
			///// 2. 법정 교육 이수 확인 서류 등록 안내 메일 발송
			if(exprdt.equals("0") || exprdt.equals("3") || Integer.valueOf(exprdt) <= -7) {
				// 만료일(exprdt) 3일전, 당일, 7일 초과 시 발송
				String[] to2 = new String[1];
				String[] cc2 = new String[1];
				to2[0] = empid;
				cc2[0] = String.valueOf(exprInfo.get("dept_head_id"));
				boolean result1 = sendMailTrnCert(exprInfo, to2, cc2);
			}
		}
		return result;
	}

	// 법정 교육 이수 안내 메일 발송
	public boolean sendMailTrnGuide(Map<String, Object> exprInfo, String[] to, String[] cc) {
		String[] dt = String.valueOf(exprInfo.get("trn_expr_dt")).split("-");
		String elcclnm[] = String.valueOf(exprInfo.get("elc_cl_nm")).split("[.]");
		String elc_cl_nm = elcclnm[1].substring(1, elcclnm[1].length());
		String shePortalLink = frontUrl + "/stndinfr/wkplelcmgntdtledt/";
		
		// 메일 업무 코드(정의 필요)
		String bizCd = "60";
		// 메일 제목(정의 필요)
		String title = "[SHE Portal] '" + elc_cl_nm + "' 법정 교육 이수 안내 메일";
		// 메일 내용(정의 필요)
		String content = 
				util.getmailCss() +
				"<div id=\"WkplMgnt\" class=\"type1_div\" style=\"width:820px; display:table; position:relative;\">\r\n" +
				"	<table cellspacing=\"0\" cellpadding=\"0\" style=\"width:100%; \"\">\r\n" + 
				"		<thead>\r\n" +
				"			<tr style=\"background-color: #062c5e;\">\r\n" +	
				"				<td style=\"border: 0;\">\r\n" +
				"					<p style=\"height: 60px; padding-left: 15px;\">\r\n" +
				" 						<img src=\"http://localhost:8080/_nuxt/img/logo.d0672ff.png" + "\" style=\"height: 20px; margin-top: 20px;\">&nbsp;&nbsp;\r\n" +
				" 						<span style=\"color: white; font-size: 20px;\">SHE PORTAL '" + elc_cl_nm + "' 법정 교육 이수 안내</span>\r\n" +
				"					</p>\r\n" +
				"				</td>\r\n" +	
				"			</tr>\r\n" +	
				"		</thead>\r\n" +
				"		<tbody style=\"display:table; width:100%; border-left:1px solid #ddd; border-right:1px solid #ddd;\">\r\n" +
				"			<tr>\n" +
				"				<td style=\"font-size: 14px; line-height:1.5; padding:30px 30px 130px 30px;\">\r\n" +
				"					<p style=\"font-size: 14px; color:rgb(0,0,0);\">\r\n" +
				"						SHE Portal 에서 '" +elcclnm[1] + "' 법정 교육 이수 안내 메일 드립니다.<br>\r\n" + 
										String.valueOf(exprInfo.get("wkpl_nm")) + ", '" +elcclnm[1] + "' " + String.valueOf(exprInfo.get("elc_pric_nm")) + "님 께서는 " +  String.valueOf(dt[1]) + "월 " + String.valueOf(dt[2]) + "일까지 정기 교육을 이수하여야 합니다.<br><br>\r\n" +
				"						ㆍ교육명: '" +elcclnm[1] + "' 법정 교육" +"<br>\r\n" + 
				"						ㆍ법정교육 만료일: " + String.valueOf(dt[0]) + "-" + String.valueOf(dt[1]) + "-" + String.valueOf(dt[2]) + "<br><br>\r\n" +
				"						<p style=\"font-size: 14px; color:rgb(6,44,94)\">*각 사업장에서는 법규 준수를 위하여 교육 대상자 업무 조정 및 배려 부탁드립니다.\n<br>*이미 교육을 이수하신 경우, 이수 확인 서류를 SHE Portal 에 등록 바랍니다.<br>\r\n" +
				"						<p style=\"margin-left:10px;\">(미등록시 향후 안내 메일이 여러 번 발송 될 수 있습니다.)</p>\r\n" +
				"						<p style=\"margin-top:10px;\">\r\n" +					
				"							<a target=\"_blank\" href=\"" + shePortalLink + "\"style=\"color: rgb(0,0,255); text-decoration: underline;\" \">" + "SHE Portal Link" + "</a>\r\n" +
				"						</p>\r\n" +	
				"					</p>\r\n" +
				"				</td>\r\n" +
				"            </tr>\r\n" +
				"			<tr class=\"inquiry_info\" style=\"position:absolute; bottom:90px; display:table; right:10px; z-index:10;\">\r\n" +
				"				<td style=\"border:0; width:100%; display:block; line-height:1.6;\">\r\n" +
				"					<p style=\"text-align:left;\">시스템 문의</p>\r\n" +
				" 					<p style=\"text-align:left;\">안전보건시스템 dehs (dehs@donggramisoft.co.kr)</p>\r\n" +
				"				</td>\r\n" +
				"			</tr>\r\n" +
				"		</tbody>\r\n" + 
				"		<tfoot>\r\n" +
				"			<tr style=\"height: 60px; background-color: #062c5e;\">\r\n" +	
				"				<td>\r\n" +
				"				</td>\r\n" +	
				"			</tr>\r\n" +	
				"		</tfoot>\r\n" +
				"	</table>" +
				"	<img src=\"" + frontUrl + "/images/ap_background.png\" alt=\"\" style=\"width: 250px; position:absolute; bottom:1px; right:1px;\"\">\r\n" +
				"</div>";
		
		// 메일 송신
		Boolean mailResult = util.sendMail(bizCd, "AC928960", title, content, to, cc);
		if(mailResult) {
			return true;
		} else {
			return false;
		}
	}

	// 법정 교육 이수 안내 메일 발송
	public boolean sendMailTrnCert(Map<String, Object> exprInfo, String[] to, String[] cc) {
		String[] dt = String.valueOf(exprInfo.get("trn_expr_dt")).split("-");
		String elcclnm[] = String.valueOf(exprInfo.get("elc_cl_nm")).split("[.]");
		String elc_cl_nm = elcclnm[1].substring(1, elcclnm[1].length());
		String shePortalLink = frontUrl + "/stndinfr/wkplelcmgntdtledt/";
		
		
		String bizCd2 = "60";
		// 메일 제목(정의 필요)
		String title2 = "[SHE PORTAL] '" + elc_cl_nm + "' 법정 교육 이수 확인 서류 등록 안내";
		// 메일 내용(정의 필요)
		String content2 = 
				util.getmailCss() +
				"<div id=\"WkplMgnt\" class=\"type1_div\" style=\"width:820px; display:table; position:relative;\">\r\n" +
				"	<table cellspacing=\"0\" cellpadding=\"0\" style=\"width:100%; \"\">\r\n" + 
				"		<thead>\r\n" +
				"			<tr style=\"background-color: #062c5e;\">\r\n" +	
				"				<td style=\"border: 0;\">\r\n" +
				"					<p style=\"height: 60px; padding-left: 15px;\">\r\n" +
				" 						<img src=\"http://localhost:8080/_nuxt/img/logo.d0672ff.png" + "\" style=\"height: 20px; margin-top: 20px;\">&nbsp;&nbsp;\r\n" +
				" 						<span style=\"color: white; font-size: 20px;\">SHE PORTAL '" + elc_cl_nm + "' 법정 교육 이수 확인 서류 등록</span>\r\n" +
				"					</p>\r\n" +
				"				</td>\r\n" +	
				"			</tr>\r\n" +	
				"		</thead>\r\n" +
				"		<tbody style=\"display:table; width:100%; border-left:1px solid #ddd; border-right:1px solid #ddd;\">\r\n" +
				"			<tr>\n" +
				"				<td style=\"font-size: 14px; line-height:1.5; padding:30px 30px 130px 30px;\">\r\n" +
				"					<p style=\"font-size: 14px; color:rgb(0,0,0);\">" +
				"						SHE Portal 에서 '" + elc_cl_nm + "' 법정 교육 이수 안내 확인 서류 등록 안내 드립니다.<br>" +
										String.valueOf(exprInfo.get("wkpl_nm")) + ", '" + elc_cl_nm + "' " + String.valueOf(exprInfo.get("elc_pric_nm")) + "님 께서는 " +  String.valueOf(dt[1]) + "월 " + String.valueOf(dt[2]) + "일까지 정기 교육을 이수하여야 합니다.<br>" +
				"						교육 이수 후 교육 이수를 증명 할 수 있는 공식 서류/자료 등 시스템 등록 바랍니다.<br><br>" +
				"						ㆍ교육구분: '" + elc_cl_nm + "' 법정 교육<br>" +
				"						ㆍ법정교육 만료일: " + String.valueOf(dt[0]) + "-" + String.valueOf(dt[1]) + "-" + String.valueOf(dt[2]) + "<br>" +
				"						<p style=\"margin-top:10px;\">\r\n" +					
				"							<a target=\"_blank\" href=\"" + shePortalLink + "\"style=\"color: rgb(0,0,255); text-decoration: underline;\" \">" + "SHE Portal Link" + "</a>\r\n" +
				"						</p>\r\n" +	
				"						<p style=\"margin-top:20px; font-size: 14px; color:rgb(6,44,94);\">* 본 메일은 법정 교육 이수 확인 서류 등록시 까지 매주 발송됩니다.</p>" +
				"					</p>\n" +
				"				</td>\n" +
				"			</tr>\n" +
				"			<tr class=\"inquiry_info\" style=\"position:absolute; bottom:90px; display:table; right:10px; z-index:10;\">\r\n" +
				"				<td style=\"border:0; width:100%; display:block; line-height:1.6;\">\r\n" +
				"					<p style=\"text-align:left;\">시스템 문의</p>\r\n" +
				" 					<p style=\"text-align:left;\">안전보건시스템 dehs (dehs@donggramisoft.co.kr)</p>\r\n" +
				"				</td>\r\n" +
				"			</tr>\r\n" +
				"		</tbody>\r\n" + 
				"		<tfoot>\r\n" +
				"			<tr style=\"height: 60px; background-color: #062c5e;\">\r\n" +	
				"				<td>\r\n" +
				"				</td>\r\n" +	
				"			</tr>\r\n" +	
				"		</tfoot>\r\n" +
				"	</table>" +
				"	<img src=\"" + frontUrl + "/images/ap_background.png\" alt=\"\" style=\"width: 250px; position:absolute; bottom:1px; right:1px;\"\">\r\n" +
				"</div>";
		// 메일 송신
		Boolean mailResult = util.sendMail(bizCd2, "AC928960", title2, content2, to, cc);
		if(mailResult) {
			return true;
		} else {
			return false;
		}
	}
}