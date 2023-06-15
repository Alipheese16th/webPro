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
public class RgltChkCrtJob implements Job {
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
        logger.info("RgltChkCrtJob start, jobKey: " + context.getJobDetail().getKey().getName());
        
        Map<String, String> param = new HashMap<>();
        param.put("batCd", context.getJobDetail().getKey().getName());
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
	        	// 1. 규제 정보 조회
		        List<Map<String, String>> rgltList = mapper.selectRgltMtrlList(param);
		        
		        for(int i = 0; i < rgltList.size(); i++) {
	        		Map<String, String> temp = rgltList.get(i);
	    			String key = mapper.selectMtrlChkKey(temp);
	    			
	    			List<Map<String, Object>> msdsList = null;
	    			// msds 파일 존재 여부 확인(recv dt descending 으로 limit 1)
	    			if("2".equalsIgnoreCase(rgltList.get(i).get("mtrl_kind_cd").toString()) || "1".equalsIgnoreCase(rgltList.get(i).get("mtrl_kind_cd").toString())){
	    				// msds 인터페이스 데이터 조회
	    				msdsList = mapper.selectMsdsTiumList(temp);
		        		if(msdsList.size() > 0) {
		        			temp.put("usr_id", msdsList.get(0).get("req_usr_id").toString());
			        		temp.put("recv_dt", msdsList.get(0).get("recv_dt").toString());
			        		temp.put("recv_seq", msdsList.get(0).get("recv_seq").toString());	// 인터페이스 테이블에 처리여부 업데이트할때 필요
		        		}
	    			}else {
	    				temp.put("usr_id", temp.get("mtrl_reg_usr_id").toString());
		        		temp.put("recv_dt", temp.get("mtrl_reg_da").toString());
	    			}
	        		
	    			temp.put("rglt_chk_no", key);
	        		// 2. (#{reg_cd} = 'C' and (#{zzitem_code} = 'ICS' or #{zzitem_code} = 'IIS')) 인 경우(자재 신규이고 향인 경우) 규제 검토 요청 데이터 생성
	        		if("2".equalsIgnoreCase(rgltList.get(i).get("mtrl_kind_cd").toString())){
	        			temp.put("chk_req_clsf_cd", "1");
	        			// 2-1. 규제 검토 자재 테이블 insert
	        			mapper.insertRgltMtrl(temp);
	        			// 2-2. 규제 검토 요청 테이블 insert
	        			mapper.insertRgltReqMtrl(temp);
	        		}
	        		// 3. (#{reg_cd} = 'C' and (#{zzitem_code} != 'ICS' and #{zzitem_code} != 'IIS')) 인 경우 규제 검토 데이터 생성
	        		if("1".equalsIgnoreCase(rgltList.get(i).get("mtrl_kind_cd").toString())){
	        			temp.put("chk_req_clsf_cd", "2");
	        			// 3-1. 규제 검토 자재 테이블 insert
	        			mapper.insertRgltMtrl(temp);
	        			// 3-2. 규제 검토 테이블 insert
	        			mapper.insertRgltChkMtrl(temp);
	        		}
	        		// IMK 데이터가 들어올 경우 mtrl_kind_cd = 4 규제 검토 요청 생성
	        		if("4".equalsIgnoreCase(rgltList.get(i).get("mtrl_kind_cd").toString())){
	        			temp.put("chk_req_clsf_cd", "1");
	        			// 규제 검토 자재 테이블 insert
	        			mapper.insertRgltMtrl(temp);
	        			// 규제 검토 요청 테이블 insert
	        			mapper.insertRgltReqMtrl(temp);
	        			// 첨부파일 번호 가져오기
	        			List<Map<String, String>> data = mapper.selectImkAtfileNo(temp);
	        			if(data.size() > 0) {
	        				temp.put("imk_atfl_no", String.valueOf(data.get(0).get("imk_atfl_no")));
		        			// 첨부파일 seq 값이 여러개일 경우 ko_atfl_no에 imk_atfl_no 집어넣는다.
		        			List<Map<String, String>> count = mapper.selectimkAtfileNoCount(temp);
		        			if(count.size() <= 1) {
		        				temp.put("imk_atfl_no", "");
		        				temp.put("ko_atfl_no", String.valueOf(data.get(0).get("imk_atfl_no")));
		        			}
	        			}
	        		}
	        		
	        		String ko_file_key = "";
	        		String en_file_key = "";
	        		if("2".equalsIgnoreCase(rgltList.get(i).get("mtrl_kind_cd").toString()) || "1".equalsIgnoreCase(rgltList.get(i).get("mtrl_kind_cd").toString())){
		        		if(msdsList.size() > 0) {
			        		if(msdsList.get(0).get("msds_file_ko_nm") != null) {
			        			if(!msdsList.get(0).get("msds_file_ko_nm").toString().isEmpty()) {
				        			// msds 파일 키 채번
				        			ko_file_key = mapper.selectMsdsTiumKoFileKey(temp);
				        			temp.put("ko_atfl_no", ko_file_key);
				        			temp.put("atfl_no", ko_file_key);
				        			temp.put("file_lang", "ko");
				        			// 파일 메타 정보 등록
				        			mapper.insertMsdsTiumfileMeta(temp);
			        			}
			        		}
			        		if(msdsList.get(0).get("msds_file_en_nm") != null) {
			        			if(!msdsList.get(0).get("msds_file_en_nm").toString().isEmpty()) {
				        			// msds 파일 키 채번
				        			en_file_key = mapper.selectMsdsTiumEnFileKey(temp);
				        			temp.put("en_atfl_no", en_file_key);
				        			temp.put("atfl_no", en_file_key);
				        			temp.put("file_lang", "en");
				        			// 파일 메타 정보 등록
				        			mapper.insertMsdsTiumfileMeta(temp);
			        			}
			        		}
		        		}
	        		}
	        		if(String.valueOf(temp.get("imk_atfl_no")).equalsIgnoreCase("null") || String.valueOf(temp.get("imk_atfl_no")).equalsIgnoreCase("")){
        				temp.put("imk_atfl_no", "");
        			}
	        		if("2".equalsIgnoreCase(rgltList.get(i).get("mtrl_kind_cd").toString()) || "1".equalsIgnoreCase(rgltList.get(i).get("mtrl_kind_cd").toString())){
		        		// 3-3. todo : msds record 생성(티움넷)
		    			mapper.insertRgltReqMsds(temp);
		    			// trn 처리
		    			mapper.updateMsdsTiumStt(temp);
	        		} else {
	        			// 3-3. todo : msds record 생성(IMK)
	        			mapper.insertImkRgltReqMsds(temp);
	        		}
	        		// todo : SAP 성분정보 내려받아서 구성물질정보 생성 추가 필요
	        		mapper.insertRgltChkCnst(temp);
	    			// todo : 규제 검토 물질 테이블에 insert 필요 tb_ch_sbst cas_no 일치하는 데이터만 집어넣기
	    			mapper.insertRgltChkSbst(temp);
	        		// -3. 처리 완료 
	    			mapper.updateRgltSucc(temp);
		        }
		        
		        
		        // 규제 검토 요청 메일 발송
		        Boolean result = sendRgltReqMail();
		        // 규제 검토 메일 발송
		        Boolean result2 = sendRgltChkMail();
		        if(result && result2) { 
		        	resultCd = "SU";
		        } else {
		        	resultCd = "ER";
		        	content = "Send Email Error";
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
        logger.info("RgltChkCrtJob start, jobKey: " + "BR001");
        
        Map<String, String> param = new HashMap<>();
        param.put("batCd", "BR001");
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
	        	// 1. 규제 정보 조회
		        List<Map<String, String>> rgltList = mapper.selectRgltMtrlList(param);
		        
		        for(int i = 0; i < rgltList.size(); i++) {
	        		Map<String, String> temp = rgltList.get(i);
	    			String key = mapper.selectMtrlChkKey(temp);
	    			List<Map<String, Object>> msdsList = null;
	    			// msds 파일 존재 여부 확인(recv dt descending 으로 limit 1)
	    			if("2".equalsIgnoreCase(rgltList.get(i).get("mtrl_kind_cd").toString()) || "1".equalsIgnoreCase(rgltList.get(i).get("mtrl_kind_cd").toString())){
	    				// msds 인터페이스 데이터 조회
	    				msdsList = mapper.selectMsdsTiumList(temp);
		        		if(msdsList.size() > 0) {
		        			temp.put("usr_id", msdsList.get(0).get("req_usr_id").toString());
			        		temp.put("recv_dt", msdsList.get(0).get("recv_dt").toString());
			        		temp.put("recv_seq", msdsList.get(0).get("recv_seq").toString());	// 인터페이스 테이블에 처리여부 업데이트할때 필요
		        		}
	    			}else {
	    				temp.put("usr_id", temp.get("mtrl_reg_usr_id").toString());
		        		temp.put("recv_dt", temp.get("mtrl_reg_da").toString());
	    			}
	        		
	    			temp.put("rglt_chk_no", key);
	        		// 2. (#{reg_cd} = 'C' and (#{zzitem_code} = 'ICS' or #{zzitem_code} = 'IIS')) 인 경우(자재 신규이고 향인 경우) 규제 검토 요청 데이터 생성
	        		if("2".equalsIgnoreCase(rgltList.get(i).get("mtrl_kind_cd").toString())){
	        			temp.put("chk_req_clsf_cd", "1");
	        			// 2-1. 규제 검토 자재 테이블 insert
	        			mapper.insertRgltMtrl(temp);
	        			// 2-2. 규제 검토 요청 테이블 insert
	        			mapper.insertRgltReqMtrl(temp);
	        		}
	        		// 3. (#{reg_cd} = 'C' and (#{zzitem_code} != 'ICS' and #{zzitem_code} != 'IIS')) 인 경우 규제 검토 데이터 생성
	        		if("1".equalsIgnoreCase(rgltList.get(i).get("mtrl_kind_cd").toString())){
	        			temp.put("chk_req_clsf_cd", "2");
	        			// 3-1. 규제 검토 자재 테이블 insert
	        			mapper.insertRgltMtrl(temp);
	        			// 3-2. 규제 검토 테이블 insert
	        			mapper.insertRgltChkMtrl(temp);
	        		}
	        		// IMK 데이터가 들어올 경우 mtrl_kind_cd = 4 규제 검토 요청 생성
	        		if("4".equalsIgnoreCase(rgltList.get(i).get("mtrl_kind_cd").toString())){
	        			temp.put("chk_req_clsf_cd", "1");
	        			// 규제 검토 자재 테이블 insert
	        			mapper.insertRgltMtrl(temp);
	        			// 규제 검토 요청 테이블 insert
	        			mapper.insertRgltReqMtrl(temp);
	        			// 첨부파일 번호 가져오기
	        			List<Map<String, String>> data = mapper.selectImkAtfileNo(temp);
	        			if(data.size() > 0) {
	        				temp.put("imk_atfl_no", String.valueOf(data.get(0).get("imk_atfl_no")));
		        			// 첨부파일 seq 값이 여러개일 경우 ko_atfl_no에 imk_atfl_no 집어넣는다.
		        			List<Map<String, String>> count = mapper.selectimkAtfileNoCount(temp);
		        			if(count.size() <= 1) {
		        				temp.put("imk_atfl_no", "");
		        				temp.put("ko_atfl_no", String.valueOf(data.get(0).get("imk_atfl_no")));
		        			}
	        			}
	        		}
	        		
	        		String ko_file_key = "";
	        		String en_file_key = "";
	        		if("2".equalsIgnoreCase(rgltList.get(i).get("mtrl_kind_cd").toString()) || "1".equalsIgnoreCase(rgltList.get(i).get("mtrl_kind_cd").toString())){
		        		if(msdsList.size() > 0) {
			        		if(msdsList.get(0).get("msds_file_ko_nm") != null) {
			        			if(!msdsList.get(0).get("msds_file_ko_nm").toString().isEmpty()) {
				        			// msds 파일 키 채번
				        			ko_file_key = mapper.selectMsdsTiumKoFileKey(temp);
				        			temp.put("ko_atfl_no", ko_file_key);
				        			temp.put("atfl_no", ko_file_key);
				        			temp.put("file_lang", "ko");
				        			// 파일 메타 정보 등록
				        			mapper.insertMsdsTiumfileMeta(temp);
			        			}
			        		}
			        		if(msdsList.get(0).get("msds_file_en_nm") != null) {
			        			if(!msdsList.get(0).get("msds_file_en_nm").toString().isEmpty()) {
				        			// msds 파일 키 채번
				        			en_file_key = mapper.selectMsdsTiumEnFileKey(temp);
				        			temp.put("en_atfl_no", en_file_key);
				        			temp.put("atfl_no", en_file_key);
				        			temp.put("file_lang", "en");
				        			// 파일 메타 정보 등록
				        			mapper.insertMsdsTiumfileMeta(temp);
			        			}
			        		}
		        		}
	        		}
	        		if(String.valueOf(temp.get("imk_atfl_no")).equalsIgnoreCase("null") || String.valueOf(temp.get("imk_atfl_no")).equalsIgnoreCase("")){
        				temp.put("imk_atfl_no", "");
        			}
	        		if("2".equalsIgnoreCase(rgltList.get(i).get("mtrl_kind_cd").toString()) || "1".equalsIgnoreCase(rgltList.get(i).get("mtrl_kind_cd").toString())){
		        		// 3-3. todo : msds record 생성(티움넷)
		    			mapper.insertRgltReqMsds(temp);
		    			// trn 처리
		    			mapper.updateMsdsTiumStt(temp);
	        		} else {
	        			// 3-3. todo : msds record 생성(IMK)
	        			mapper.insertImkRgltReqMsds(temp);
	        		}
	        		// todo : SAP 성분정보 내려받아서 구성물질정보 생성 추가 필요
	        		mapper.insertRgltChkCnst(temp);
	    			// todo : 규제 검토 물질 테이블에 insert 필요 tb_ch_sbst cas_no 일치하는 데이터만 집어넣기
	    			mapper.insertRgltChkSbst(temp);
	        		// -3. 처리 완료 
	    			mapper.updateRgltSucc(temp);
		        }
		        
		        
		        // 규제 검토 요청 메일 발송
		        Boolean result = sendRgltReqMail();
		        // 규제 검토 메일 발송
		        Boolean result2 = sendRgltChkMail();
		        if(result && result2) { 
		        	resultCd = "SU";
		        } else {
		        	resultCd = "ER";
		        	content = "Send Email Error";
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
	public boolean sendRgltReqMail() throws Exception {		
		Map<String, String> temp = new HashMap<>();
		
		// 수신자 학인
		List<Map<String, String>> rgltUserList = mapper.selectRgltReqUserList(temp);
		String[] to = new String[1];
		
		Boolean mailResult = true;
		for(int i=0; i<rgltUserList.size(); i++) {
			// 수신자 저장
			to[0] = rgltUserList.get(i).get("rglt_chk_req_usr_id");
			// 수신자 저장(임시)
			//to[0] = "AC926550";
			// 언어 설정
			String langCd = rgltUserList.get(i).get("lang_cd");
			if(langCd == null || langCd.isEmpty()) {
				langCd = "KO";
			}
			temp.put("SESS_LANG", langCd);
			temp.put("usr_id", rgltUserList.get(i).get("rglt_chk_req_usr_id"));
			// 수신자의 신규 자재 규제 검토 정보 조회
			List<Map<String, String>> rgltDataList = mapper.selectRgltDataList(temp);
			// 다국어 처리
			String[] mlangNo = {"MS00000196", "MS00000197", "LB00000199"
								,"LB00000025", "LB00000404", "LB00000449"
								,"LB00000365", "LB00000601"};
			List<Map<String, String>> mlangCntn = mUtil.getMLangList(Arrays.asList(mlangNo), langCd);
			
			// 메일 업무 코드
			String bizCd = "20";
			// 메일 제목
			String subject = mUtil.getMLang(mlangCntn, "MS00000196");
			// 메일 제목 규제 검토 번호 append
			subject+= "(";
//			for(int l = 0; l < rgltDataList.size(); l++) {
//				if(l > 0) {
//					subject+=", ";
//				}
//				subject+= rgltDataList.get(l).get("mtrl_no");
//			}
			if(rgltDataList.size() > 1) {
				subject+= rgltDataList.get(0).get("mtrl_no") + mUtil.getMLang(mlangCntn, "LB00000601") + " " + (rgltDataList.size() - 1) + mUtil.getMLang(mlangCntn, "LB00000365");
			} else if(rgltDataList.size() == 1) {
				subject+= rgltDataList.get(0).get("mtrl_no");
			}
			subject+= ")";
			// 테이블 동적 생성
			String table = "";
			for(int j = 0; j < rgltDataList.size(); j++) {
				// mtrl_kind_cd가 4일 경우 imkedt으로 이동해준다.
				if(String.valueOf(rgltDataList.get(j).get("mtrl_kind_cd")).equalsIgnoreCase("4")) {
					table += "        <tr>\r\n" + 
							"            <td style=\"width:25%;height:24px;border-bottom:1px solid #ccc;border-right:1px solid #ccc;border-left:1px solid #ccc;padding:0px 3px 0px 3px;;\">\r\n" + 
							"                <p style=\"text-align: center;\">" + rgltDataList.get(j).get("mtrl_no") + "</p>\r\n" + 
							"            </td>\r\n" + 
							"            <td style=\"width:25%;height:24px;border-bottom:1px solid #ccc;border-right:1px solid #ccc;padding:0px 3px 0px 3px;;\">\r\n" + 
							"                <p>" + rgltDataList.get(j).get("mtrl_nm") + "</p>\r\n" + 
							"            </td>\r\n" + 
							"            <td style=\"width:25%;height:24px;border-bottom:1px solid #ccc;border-right:1px solid #ccc;padding:0px 3px 0px 3px;;\">\r\n" + 
							"                <p style=\"text-align: center;\">" + rgltDataList.get(j).get("mtrl_reg_da") + "</p>\r\n" + 
							"            </td>\r\n" + 
							"            <td style=\"width:25%;height:24px;border-bottom:1px solid #ccc;border-right:1px solid #ccc;padding:0px 3px 0px 3px;;\">\r\n" + 
							"                <p style=\"font-family: &quot;Malgun Gothic&quot;; text-align: center; font-size: medium;\"><a style=\"font-weight: bold;\" href=\"" + frontUrl + "/rgltchk/rgltchkreqimkedt/" + rgltDataList.get(j).get("rglt_chk_no") + "\">" + mUtil.getMLang(mlangCntn, "LB00000449") + "</a></p>\r\n" + 
							"            </td>\r\n" + 
							"        </tr>\r\n";
				}else {
					table += "        <tr>\r\n" + 
							"            <td style=\"width:25%;height:24px;border-bottom:1px solid #ccc;border-right:1px solid #ccc;border-left:1px solid #ccc;padding:0px 3px 0px 3px;;\">\r\n" + 
							"                <p style=\"text-align: center;\">" + rgltDataList.get(j).get("mtrl_no") + "</p>\r\n" + 
							"            </td>\r\n" + 
							"            <td style=\"width:25%;height:24px;border-bottom:1px solid #ccc;border-right:1px solid #ccc;padding:0px 3px 0px 3px;;\">\r\n" + 
							"                <p>" + rgltDataList.get(j).get("mtrl_nm") + "</p>\r\n" + 
							"            </td>\r\n" + 
							"            <td style=\"width:25%;height:24px;border-bottom:1px solid #ccc;border-right:1px solid #ccc;padding:0px 3px 0px 3px;;\">\r\n" + 
							"                <p style=\"text-align: center;\">" + rgltDataList.get(j).get("mtrl_reg_da") + "</p>\r\n" + 
							"            </td>\r\n" + 
							"            <td style=\"width:25%;height:24px;border-bottom:1px solid #ccc;border-right:1px solid #ccc;padding:0px 3px 0px 3px;;\">\r\n" + 
							"                <p style=\"font-family: &quot;Malgun Gothic&quot;; text-align: center; font-size: medium;\"><a style=\"font-weight: bold;\" href=\"" + frontUrl + "/rgltchk/rgltchkreqmtrledt/" + rgltDataList.get(j).get("rglt_chk_no") + "\">" + mUtil.getMLang(mlangCntn, "LB00000449") + "</a></p>\r\n" + 
							"            </td>\r\n" + 
							"        </tr>\r\n";
				}
			}
			// 메일 내용
			String content = "<p>" + mUtil.getMLang(mlangCntn, "MS00000197") + "</p>\r\n" + 
					"<table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"border:none;border-collapse:collapse;word-break:break-all;font-family:&quot;Malgun Gothic&quot;, &quot;맑은 고딕&quot;;font-size:13.3333px\">\r\n" + 
					"    <tbody>\r\n" + 
					"        <tr>\r\n" + 
					"            <td style=\"width: 25%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"                <p style=\"text-align: center;\"><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000199") + "</span><br></p>\r\n" + 
					"            </td>\r\n" + 
					"            <td style=\"width: 25%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"                <p style=\"font-weight: bold; text-align: center;\"><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000025") + "</span></p>\r\n" + 
					"            </td>\r\n" + 
					"            <td style=\"width: 25%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"                <p style=\"font-weight: bold; text-align: center;\"><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000404") + "</span></p>\r\n" + 
					"            </td>\r\n" + 
					"            <td style=\"width: 25%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"                <p style=\"text-align: center;\"><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000449") + "</span></p>\r\n" + 
					"            </td>\r\n" + 
					"        </tr>\r\n" + 
					table +
					"    </tbody>\r\n" + 
					"</table>";
			
			// 메일 송신(송신자 임시)
			if (rgltDataList.size() > 0) {
				// AC928960 안전환경포털시스템
				mailResult = util.sendMail(bizCd, "AC928960", subject, content, to);
			} else {
				mailResult = true;
				return mailResult;
			}
			

			// 메일 결과에 따라 메일 전송 여부 Y로 체크
			if(mailResult) {
				// 전송 성공시
				mapper.updateRgltReqBatStt(temp);
			} else {
				//throw new Exception("Send Mail Error");
				logger.error("===> Send Mail Error!!");
			}
		}
		
		return mailResult; 
	}
	
	@Transactional
	public boolean sendRgltChkMail() throws Exception {		
		Map<String, String> temp = new HashMap<>();
		Map<String, Object> temp2 = new HashMap<>();
		
		// 수신자 학인
		List<Map<String, String>> rgltUserList = mapper.selectRgltChkUserList(temp2);
		String[] to = new String[1];
		
		Boolean mailResult = true;
		for(int i=0; i<rgltUserList.size(); i++) {
			// 수신자 저장
			to[0] = rgltUserList.get(i).get("usr_id");
			// 수신자 저장(임시)
			//to[0] = "AC926550";
			// 언어 설정
			String langCd = rgltUserList.get(i).get("lang_cd");
			if(langCd == null || langCd.isEmpty()) {
				langCd = "KO";
			}
			temp.put("SESS_LANG", langCd);
			temp.put("wkpl_id", rgltUserList.get(i).get("wkpl_id"));
			// 수신자의 신규 자재 규제 검토 정보 조회
			List<Map<String, String>> rgltDataList = mapper.selectRgltChkDataList(temp);
			// 다국어 처리
			String[] mlangNo = {"MS00000199", "MS00000200", "LB00000199"
								,"LB00000025", "LB00000404", "LB00000449"
								,"LB00000365", "LB00000601"};
			List<Map<String, String>> mlangCntn = mUtil.getMLangList(Arrays.asList(mlangNo), langCd);
			
			// 메일 업무 코드
			String bizCd = "20";
			// 메일 제목
			String subject = mUtil.getMLang(mlangCntn, "MS00000199"); // 신규 자재 규제 검토 알림
			// 메일 제목 규제 검토 번호 append
			subject+= "(";
//			for(int l = 0; l < rgltDataList.size(); l++) {
//				if(l > 0) {
//					subject+=", ";
//				}
//				subject+= rgltDataList.get(l).get("mtrl_no");
//			}
			if(rgltDataList.size() > 1) {
				subject+= rgltDataList.get(0).get("mtrl_no") + mUtil.getMLang(mlangCntn, "LB00000601") + " " + (rgltDataList.size() - 1) + mUtil.getMLang(mlangCntn, "LB00000365");
			} else if(rgltDataList.size() == 1) {
				subject+= rgltDataList.get(0).get("mtrl_no");
			} 
			subject+= ")";
			// 테이블 동적 생성
			String table = "";
			for(int j = 0; j < rgltDataList.size(); j++) {
				table += "        <tr>\r\n" + 
						"            <td style=\"width:25%;height:24px;border-bottom:1px solid #ccc;border-right:1px solid #ccc;border-left:1px solid #ccc;padding:0px 3px 0px 3px;;\">\r\n" + 
						"                <p style=\"text-align: center;\">" + rgltDataList.get(j).get("mtrl_no") + "</p>\r\n" + 
						"            </td>\r\n" + 
						"            <td style=\"width:25%;height:24px;border-bottom:1px solid #ccc;border-right:1px solid #ccc;padding:0px 3px 0px 3px;;\">\r\n" + 
						"                <p>" + rgltDataList.get(j).get("mtrl_nm") + "</p>\r\n" + 
						"            </td>\r\n" + 
						"            <td style=\"width:25%;height:24px;border-bottom:1px solid #ccc;border-right:1px solid #ccc;padding:0px 3px 0px 3px;;\">\r\n" + 
						"                <p style=\"text-align: center;\">" + rgltDataList.get(j).get("mtrl_reg_da") + "</p>\r\n" + 
						"            </td>\r\n" + 
						"            <td style=\"width:25%;height:24px;border-bottom:1px solid #ccc;border-right:1px solid #ccc;padding:0px 3px 0px 3px;;\">\r\n" + 
						"                <p style=\"font-family: &quot;Malgun Gothic&quot;; text-align: center; font-size: medium;\"><a style=\"font-weight: bold;\" href=\"" + frontUrl + "/rgltchk/rgltchkimkedt/" + rgltDataList.get(j).get("rglt_chk_no") + "\">" + mUtil.getMLang(mlangCntn, "LB00000449") + "</a></p>\r\n" + 
						"            </td>\r\n" + 
						"        </tr>\r\n";
			}
			// 메일 내용
			String content = "<p>" + mUtil.getMLang(mlangCntn, "MS00000200") + "</p>\r\n" + 
					"<table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"border:none;border-collapse:collapse;word-break:break-all;font-family:&quot;Malgun Gothic&quot;, &quot;맑은 고딕&quot;;font-size:13.3333px\">\r\n" + 
					"    <tbody>\r\n" + 
					"        <tr>\r\n" + 
					"            <td style=\"width: 25%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"                <p style=\"text-align: center;\"><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000199") + "</span><br></p>\r\n" + 
					"            </td>\r\n" + 
					"            <td style=\"width: 25%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"                <p style=\"font-weight: bold; text-align: center;\"><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000025") + "</span></p>\r\n" + 
					"            </td>\r\n" + 
					"            <td style=\"width: 25%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"                <p style=\"font-weight: bold; text-align: center;\"><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000404") + "</span></p>\r\n" + 
					"            </td>\r\n" + 
					"            <td style=\"width: 25%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"                <p style=\"text-align: center;\"><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000449") + "</span></p>\r\n" + 
					"            </td>\r\n" + 
					"        </tr>\r\n" + 
					table +
					"    </tbody>\r\n" + 
					"</table>";
			
			// 메일 송신
			if(rgltDataList.size() > 0) {
				mailResult = util.sendMail(bizCd, "AC928960", subject, content, to);
			} else {
				mailResult = true;
				return mailResult;
			}
			
			// 메일 결과에 따라 메일 전송 여부 Y로 체크
			if(mailResult) {
				// 전송 성공시
				mapper.updateRgltChkBatStt(temp);
			} else {
				//throw new Exception("Send Mail Error");
				logger.error("===> Send Mail Error!!");
			}
		}
		
		return mailResult; 
	}
}
