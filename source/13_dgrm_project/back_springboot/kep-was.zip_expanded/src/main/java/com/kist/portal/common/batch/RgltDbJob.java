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
import com.kist.portal.common.email.rest.SendEmailUtil;
import com.kist.portal.common.utils.MultiLanguageUtil;
import com.kist.portal.common.utils.TransactionHandler;

@Component
public class RgltDbJob implements Job {
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
        logger.info("RgltDbJob start, jobKey: " + context.getJobDetail().getKey().getName());
        
        Map<String, String> param = new HashMap<>();
        param.put("batCd", context.getJobDetail().getKey().getName());
        mapper.insertBatch(param); // start
        logger.info("RgltDbJob batNo : " + param.get("batNo"));
        
        // 배치 실행 중 상태 코드 업데이트
        param.put("resultCd", "EX");
        param.put("content", "");
        mapper.updateBatch(param);
        
        // 실제 배치 실행 후 결과 코드 
        content = "";
        resultCd = "ER";
        transactionHandler.runInTransaction(() -> {
	        try {
		        // 1. merge 대상 물질 조회
	        	List<Map<String, Object>> dbList = mapper.selectRgltDbList(param);
	        	for(int i = 0; i < dbList.size(); i++) {
	        		Map<String, Object> tempData = dbList.get(i);
	        		// 1-1. 물질 update/insert
	        		mapper.upsertRgltDbList(tempData);
	        		// 1-2. 물질 변경 이력 insert
	        		mapper.insertRgltDbHist(tempData);
	        	}
	        	// 2. 규제-물질 변경 대상 목록 조회
	        	List<Map<String, String>> mtrlDataList = new ArrayList<>();
	        	List<Map<String, Object>> chList = mapper.selectRgltChList(param);
	        	for(int j = 0; j < chList.size(); j++) {
	        		Map<String, Object> tempData = chList.get(j);
	        		// 2-1. 규제-물질 변경
	        		mapper.upsertRgltChList(tempData);
	        		// 2-2. 규제-물질 변경 history 저장
	        		mapper.insertRgltChHist(tempData);
	        
	        		// 2-3. 하나의 규제변경에 대한 CUD 자재 선택.
	        		if("C".equalsIgnoreCase(chList.get(j).get("chg_type_cd").toString())) {
	        			// 해당 물질을 포함하는 tb_ch_mtrl_sbst 테이블에서 물질 함량비가 규정된 규제 정보에 초과되는 여부를 가지는 자재를 가져온다.
	        			List<Map<String, String>> cMtrlList = mapper.selectUpsertMtrlList(chList.get(j));
	        			for(int c = 0; c < cMtrlList.size(); c++) {
	        				Map<String, String> data = new HashMap<>();
	        				String re_chk_cau_cntn = cMtrlList.get(c).get("re_chk_cau_cntn");
	        				for(int i = 0; i < mtrlDataList.size(); i++) {
	        					// 내부에 동일한 자재번호가 존재할 경우 기존 내용에 추가로 더해준다.
	        					if(mtrlDataList.get(i).get("mtrl_no").equalsIgnoreCase(cMtrlList.get(c).get("mtrl_no"))) {
	        						re_chk_cau_cntn = mtrlDataList.get(i).get("re_chk_cau_cntn") + " " + re_chk_cau_cntn;
	        						// 기존 데이터 삭제
	        						mtrlDataList.remove(i);
	        						break;
	        					}
	        				}
	        				data.put("mtrl_no", cMtrlList.get(c).get("mtrl_no"));
	        				data.put("re_chk_cau_cntn", cMtrlList.get(c).get("re_chk_cau_cntn"));
	        				
	        				mtrlDataList.add(data);
	        			}
	        		} else if("U".equalsIgnoreCase(chList.get(j).get("chg_type_cd").toString())) {
	        			// 기존 tb_ch_mtrl_sbst 테이블에서 물질 함량비가 조회해온 변경된 규제 정보에 초과되는지 확인된 자재를 가져온다.
	        			List<Map<String, String>> uMtrlList = mapper.selectUpdateMtrlList(chList.get(j));
	        			for(int u = 0; u < uMtrlList.size(); u++) {
	        				Map<String, String> data = new HashMap<>();
	        				String re_chk_cau_cntn = uMtrlList.get(u).get("re_chk_cau_cntn");
	        				for(int i = 0; i < mtrlDataList.size(); i++) {
	        					// 내부에 동일한 자재번호가 존재할 경우 기존 내용에 추가로 더해준다.
	        					if(mtrlDataList.get(i).get("mtrl_no").equalsIgnoreCase(uMtrlList.get(u).get("mtrl_no"))) {
	        						re_chk_cau_cntn = mtrlDataList.get(i).get("re_chk_cau_cntn") + " " + re_chk_cau_cntn;
	        						// 기존 데이터 삭제
	        						mtrlDataList.remove(i);
	        						break;
	        					}
	        				}
	        				data.put("mtrl_no", uMtrlList.get(u).get("mtrl_no"));
	        				data.put("re_chk_cau_cntn", re_chk_cau_cntn);
	        				
	        				mtrlDataList.add(data);
	        			}
	        		} else if("D".equalsIgnoreCase(chList.get(j).get("chg_type_cd").toString())) {
	        			// 해당 삭제된 규제 및 물질에 대한 자재 번호를(tb_ch_mtrl_rglt_sbst) 가져와서 해당 자재번호를 규제 재검토 한다.
	        			List<Map<String, String>> dMtrlList = mapper.selectDeleteMtrlList(chList.get(j));
	        			for(int d = 0; d < dMtrlList.size(); d++) {
	        				Map<String, String> data = new HashMap<>();
	        				String re_chk_cau_cntn = dMtrlList.get(d).get("re_chk_cau_cntn");
	        				for(int i = 0; i < mtrlDataList.size(); i++) {
	        					// 내부에 동일한 자재번호가 존재할 경우 기존 내용에 추가로 더해준다.
	        					if(mtrlDataList.get(i).get("mtrl_no").equalsIgnoreCase(dMtrlList.get(d).get("mtrl_no"))) {
	        						re_chk_cau_cntn = mtrlDataList.get(i).get("re_chk_cau_cntn") + " " + re_chk_cau_cntn;
	        						// 기존 데이터 삭제
	        						mtrlDataList.remove(i);
	        						break;
	        					}
	        				}
	        				data.put("mtrl_no", dMtrlList.get(d).get("mtrl_no"));
	        				data.put("re_chk_cau_cntn", re_chk_cau_cntn);
	        				
	        				mtrlDataList.add(data);
	        			}
	        		}
	        	}
	        	// 모든 처리가 끝난 이후 중복이 제거된 mtrl_no 데이터와 내용을 가진 정보를 가진 리스트를 사용해 insert 처리해준다.
	        	for(int i = 0; i < mtrlDataList.size(); i++) {
	    			Map<String, String> temp = new HashMap<>();
	    			temp.put("mtrl_no", mtrlDataList.get(i).get("mtrl_no"));
	    			temp.put("re_chk_cau_cntn", mtrlDataList.get(i).get("re_chk_cau_cntn"));
	    			// 규제 검토 키 생성
	    			String key = mapper.selectMtrlChkKey(temp);
	    			temp.put("rglt_chk_no", key);
	    			mtrlDataList.set(i, temp);
	    			// 규제 검토 자재 테이블 insert
	    			mapper.insertRgltMtrlRechk(temp);
	    			// 규제 검토 규제 테이블 insert
	    			mapper.insertRgltChkRgltRechk(temp);
	    			// 규제 검토 규제 물질 테이블 insert
	    			mapper.insertRgltChkRgltSbstRechk(temp);
	    			// 규제 검토 물질 테이블 insert
	    			mapper.insertRgltChkSbstRechk(temp);
	    			// MSDS insert
	    			mapper.insertRgltMtrlMsdsRechk(temp);
	    			// 규제 검토 경고 표지 insert
	    			mapper.insertRgltChkGwsgnRechk(temp);
	    			// 규제 그림 표지 insert
	    			mapper.insertRgltChkGwsgnPicg(temp);
	    			// 규제 검토 테이블 insert(검토 진행 중 자제를 제외하기 위해 맨 마지막에 생성해야함)
	    			mapper.insertRgltChkMtrlRechk(temp);
	    		}
	        	// 재검토 메일 발송(올릴 자재가 존재할 경우만)
	        	boolean result = true;
	        	if(mtrlDataList.size() > 0) {
	        		result = sendRgltChkMail(mtrlDataList);
	        	}
				if(!result) {
					resultCd = "ER";
				} else {
					resultCd = "SU";
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
        logger.info("RgltDbJob start, jobKey: " + "BR003");
        
        Map<String, String> param = new HashMap<>();
        param.put("batCd", "BR003");
        mapper.insertBatch(param); // start
        logger.info("RgltDbJob batNo : " + param.get("batNo"));
        
        // 배치 실행 중 상태 코드 업데이트
        param.put("resultCd", "EX");
        param.put("content", "");
        mapper.updateBatch(param);
        
        // 실제 배치 실행 후 결과 코드 
        content = "";
        resultCd = "ER";
        transactionHandler.runInTransaction(() -> {
	        try {
		        // 1. merge 대상 물질 조회
	        	List<Map<String, Object>> dbList = mapper.selectRgltDbList(param);
	        	for(int i = 0; i < dbList.size(); i++) {
	        		Map<String, Object> tempData = dbList.get(i);
	        		// 1-1. 물질 update/insert
	        		mapper.upsertRgltDbList(tempData);
	        		// 1-2. 물질 변경 이력 insert
	        		mapper.insertRgltDbHist(tempData);
	        	}
	        	// 2. 규제-물질 변경 대상 목록 조회
	        	List<Map<String, String>> mtrlDataList = new ArrayList<>();
	        	List<Map<String, Object>> chList = mapper.selectRgltChList(param);
	        	for(int j = 0; j < chList.size(); j++) {
	        		Map<String, Object> tempData = chList.get(j);
	        		// 2-1. 규제-물질 변경
	        		mapper.upsertRgltChList(tempData);
	        		// 2-2. 규제-물질 변경 history 저장
	        		mapper.insertRgltChHist(tempData);
	        
	        		// 2-3. 하나의 규제변경에 대한 CUD 자재 선택.
	        		if("C".equalsIgnoreCase(chList.get(j).get("chg_type_cd").toString())) {
	        			// 해당 물질을 포함하는 tb_ch_mtrl_sbst 테이블에서 물질 함량비가 규정된 규제 정보에 초과되는 여부를 가지는 자재를 가져온다.
	        			List<Map<String, String>> cMtrlList = mapper.selectUpsertMtrlList(chList.get(j));
	        			for(int c = 0; c < cMtrlList.size(); c++) {
	        				Map<String, String> data = new HashMap<>();
	        				String re_chk_cau_cntn = cMtrlList.get(c).get("re_chk_cau_cntn");
	        				for(int i = 0; i < mtrlDataList.size(); i++) {
	        					// 내부에 동일한 자재번호가 존재할 경우 기존 내용에 추가로 더해준다.
	        					if(mtrlDataList.get(i).get("mtrl_no").equalsIgnoreCase(cMtrlList.get(c).get("mtrl_no"))) {
	        						re_chk_cau_cntn = mtrlDataList.get(i).get("re_chk_cau_cntn") + " " + re_chk_cau_cntn;
	        						// 기존 데이터 삭제
	        						mtrlDataList.remove(i);
	        						break;
	        					}
	        				}
	        				data.put("mtrl_no", cMtrlList.get(c).get("mtrl_no"));
	        				data.put("re_chk_cau_cntn", cMtrlList.get(c).get("re_chk_cau_cntn"));
	        				
	        				mtrlDataList.add(data);
	        			}
	        		} else if("U".equalsIgnoreCase(chList.get(j).get("chg_type_cd").toString())) {
	        			// 기존 tb_ch_mtrl_sbst 테이블에서 물질 함량비가 조회해온 변경된 규제 정보에 초과되는지 확인된 자재를 가져온다.
	        			List<Map<String, String>> uMtrlList = mapper.selectUpdateMtrlList(chList.get(j));
	        			for(int u = 0; u < uMtrlList.size(); u++) {
	        				Map<String, String> data = new HashMap<>();
	        				String re_chk_cau_cntn = uMtrlList.get(u).get("re_chk_cau_cntn");
	        				for(int i = 0; i < mtrlDataList.size(); i++) {
	        					// 내부에 동일한 자재번호가 존재할 경우 기존 내용에 추가로 더해준다.
	        					if(mtrlDataList.get(i).get("mtrl_no").equalsIgnoreCase(uMtrlList.get(u).get("mtrl_no"))) {
	        						re_chk_cau_cntn = mtrlDataList.get(i).get("re_chk_cau_cntn") + " " + re_chk_cau_cntn;
	        						// 기존 데이터 삭제
	        						mtrlDataList.remove(i);
	        						break;
	        					}
	        				}
	        				data.put("mtrl_no", uMtrlList.get(u).get("mtrl_no"));
	        				data.put("re_chk_cau_cntn", re_chk_cau_cntn);
	        				
	        				mtrlDataList.add(data);
	        			}
	        		} else if("D".equalsIgnoreCase(chList.get(j).get("chg_type_cd").toString())) {
	        			// 해당 삭제된 규제 및 물질에 대한 자재 번호를(tb_ch_mtrl_rglt_sbst) 가져와서 해당 자재번호를 규제 재검토 한다.
	        			List<Map<String, String>> dMtrlList = mapper.selectDeleteMtrlList(chList.get(j));
	        			for(int d = 0; d < dMtrlList.size(); d++) {
	        				Map<String, String> data = new HashMap<>();
	        				String re_chk_cau_cntn = dMtrlList.get(d).get("re_chk_cau_cntn");
	        				for(int i = 0; i < mtrlDataList.size(); i++) {
	        					// 내부에 동일한 자재번호가 존재할 경우 기존 내용에 추가로 더해준다.
	        					if(mtrlDataList.get(i).get("mtrl_no").equalsIgnoreCase(dMtrlList.get(d).get("mtrl_no"))) {
	        						re_chk_cau_cntn = mtrlDataList.get(i).get("re_chk_cau_cntn") + " " + re_chk_cau_cntn;
	        						// 기존 데이터 삭제
	        						mtrlDataList.remove(i);
	        						break;
	        					}
	        				}
	        				data.put("mtrl_no", dMtrlList.get(d).get("mtrl_no"));
	        				data.put("re_chk_cau_cntn", re_chk_cau_cntn);
	        				
	        				mtrlDataList.add(data);
	        			}
	        		}
	        	}
	        	// 모든 처리가 끝난 이후 중복이 제거된 mtrl_no 데이터와 내용을 가진 정보를 가진 리스트를 사용해 insert 처리해준다.
	        	for(int i = 0; i < mtrlDataList.size(); i++) {
	    			Map<String, String> temp = new HashMap<>();
	    			temp.put("mtrl_no", mtrlDataList.get(i).get("mtrl_no"));
	    			temp.put("re_chk_cau_cntn", mtrlDataList.get(i).get("re_chk_cau_cntn"));
	    			// 규제 검토 키 생성
	    			String key = mapper.selectMtrlChkKey(temp);
	    			temp.put("rglt_chk_no", key);
	    			mtrlDataList.set(i, temp);
	    			// 규제 검토 자재 테이블 insert
	    			mapper.insertRgltMtrlRechk(temp);
	    			// 규제 검토 규제 테이블 insert
	    			mapper.insertRgltChkRgltRechk(temp);
	    			// 규제 검토 규제 물질 테이블 insert
	    			mapper.insertRgltChkRgltSbstRechk(temp);
	    			// 규제 검토 물질 테이블 insert
	    			mapper.insertRgltChkSbstRechk(temp);
	    			// MSDS insert
	    			mapper.insertRgltMtrlMsdsRechk(temp);
	    			// 규제 검토 경고 표지 insert
	    			mapper.insertRgltChkGwsgnRechk(temp);
	    			// 규제 그림 표지 insert
	    			mapper.insertRgltChkGwsgnPicg(temp);
	    			// 규제 검토 테이블 insert(검토 진행 중 자제를 제외하기 위해 맨 마지막에 생성해야함)
	    			mapper.insertRgltChkMtrlRechk(temp);
	    		}
	        	// 재검토 메일 발송(올릴 자재가 존재할 경우만)
	        	boolean result = true;
	        	if(mtrlDataList.size() > 0) {
	        		result = sendRgltChkMail(mtrlDataList);
	        	}
				if(!result) {
					resultCd = "ER";
				} else {
					resultCd = "SU";
				}
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
	public boolean sendRgltChkMail(List<Map<String, String>> mtrlDataList) throws Exception {		
		Map<String, Object> temp = new HashMap<>();
		// mtrl_no 정보를 가지고 있는 list
		List<String> keyList = new ArrayList<>();
		for(int i = 0; i < mtrlDataList.size(); i++) {
			System.out.println("===========================================");
			System.out.println(mtrlDataList.get(i));
			System.out.println("===========================================");
			keyList.add(mtrlDataList.get(i).get("rglt_chk_no"));
		}
		temp.put("rgltNoList", keyList);
		
		// 수신자 학인
		List<Map<String, String>> rgltUserList = mapper.selectRgltChkUserList(temp);
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
			System.out.println("==========================================");
			System.out.println(temp);
			System.out.println("==========================================");
			// 자재 정보 조회
			List<Map<String, String>> rgltDataList = mapper.selectMtrlDataList(temp);
			// 다국어 처리
			String[] mlangNo = {"MS00000224", "MS00000200", "LB00000199"
								,"LB00000025", "LB00000404", "LB00000449"
								,"LB00000516"};
			List<Map<String, String>> mlangCntn = mUtil.getMLangList(Arrays.asList(mlangNo), langCd);
			
			// 메일 업무 코드
			String bizCd = "24";
			// 메일 제목
			String subject = mUtil.getMLang(mlangCntn, "MS00000224"); // 규제 재검토 알림
			// 메일 제목 규제 검토 번호 append
			subject+= "(";
			for(int l = 0; l < rgltDataList.size(); l++) {
				if(l > 0) {
					subject+=" 외 " + (rgltDataList.size()-1) + "건";
					break;
				}
				subject+= rgltDataList.get(l).get("mtrl_no");
			}
			subject+= ")";
			// 테이블 동적 생성
			String table = "";
			for(int j = 0; j < rgltDataList.size(); j++) {
				String mtrl_no = rgltDataList.get(j).get("mtrl_no");
				if(rgltDataList.get(j).get("mtrl_clsf_cd").equalsIgnoreCase("1")) {
					table += "        <tr>\r\n" + 
							"            <td style=\"width:20%;height:24px;border-bottom:1px solid #ccc;border-right:1px solid #ccc;border-left:1px solid #ccc;padding:0px 3px 0px 3px;;\">\r\n" + 
							"                <p style=\"text-align: center;\">" + rgltDataList.get(j).get("mtrl_no") + "</p>\r\n" + 
							"            </td>\r\n" + 
							"            <td style=\"width:20%;height:24px;border-bottom:1px solid #ccc;border-right:1px solid #ccc;padding:0px 3px 0px 3px;;\">\r\n" + 
							"                <p>" + rgltDataList.get(j).get("mtrl_nm") + "</p>\r\n" + 
							"            </td>\r\n" + 
							"            <td style=\"width:20%;height:24px;border-bottom:1px solid #ccc;border-right:1px solid #ccc;padding:0px 3px 0px 3px;;\">\r\n" + 
							"                <p style=\"text-align: center;\">" + rgltDataList.get(j).get("mtrl_reg_da") + "</p>\r\n" + 
							"            </td>\r\n" + 
							"            <td style=\"width:20%;height:24px;border-bottom:1px solid #ccc;border-right:1px solid #ccc;padding:0px 3px 0px 3px;;\">\r\n" + 
							"                <p style=\"text-align: center;\">" + mtrlDataList.stream().filter((v)->v.get("mtrl_no").equalsIgnoreCase(mtrl_no)).findAny().get().get("re_chk_cau_cntn") + "</p>\r\n" + 
							"            </td>\r\n" + 
							"            <td style=\"width:20%;height:24px;border-bottom:1px solid #ccc;border-right:1px solid #ccc;padding:0px 3px 0px 3px;;\">\r\n" + 
							"                <p style=\"font-family: &quot;Malgun Gothic&quot;; text-align: center; font-size: medium;\"><a style=\"font-weight: bold;\" href=\"" + frontUrl + "/rgltchk/rgltchkmtrledt/" + rgltDataList.get(j).get("rglt_chk_no") + "\">" + mUtil.getMLang(mlangCntn, "LB00000449") + "</a></p>\r\n" + 
							"            </td>\r\n" + 
							"        </tr>\r\n";
				}else {
					table += "        <tr>\r\n" + 
							"            <td style=\"width:20%;height:24px;border-bottom:1px solid #ccc;border-right:1px solid #ccc;border-left:1px solid #ccc;padding:0px 3px 0px 3px;;\">\r\n" + 
							"                <p style=\"text-align: center;\">" + rgltDataList.get(j).get("mtrl_no") + "</p>\r\n" + 
							"            </td>\r\n" + 
							"            <td style=\"width:20%;height:24px;border-bottom:1px solid #ccc;border-right:1px solid #ccc;padding:0px 3px 0px 3px;;\">\r\n" + 
							"                <p>" + rgltDataList.get(j).get("mtrl_nm") + "</p>\r\n" + 
							"            </td>\r\n" + 
							"            <td style=\"width:20%;height:24px;border-bottom:1px solid #ccc;border-right:1px solid #ccc;padding:0px 3px 0px 3px;;\">\r\n" + 
							"                <p style=\"text-align: center;\">" + rgltDataList.get(j).get("mtrl_reg_da") + "</p>\r\n" + 
							"            </td>\r\n" + 
							"            <td style=\"width:20%;height:24px;border-bottom:1px solid #ccc;border-right:1px solid #ccc;padding:0px 3px 0px 3px;;\">\r\n" + 
							"                <p style=\"text-align: center;\">" + mtrlDataList.stream().filter((v)->v.get("mtrl_no").equalsIgnoreCase(mtrl_no)).findAny().get().get("re_chk_cau_cntn") + "</p>\r\n" + 
							"            </td>\r\n" + 
							"            <td style=\"width:20%;height:24px;border-bottom:1px solid #ccc;border-right:1px solid #ccc;padding:0px 3px 0px 3px;;\">\r\n" + 
							"                <p style=\"font-family: &quot;Malgun Gothic&quot;; text-align: center; font-size: medium;\"><a style=\"font-weight: bold;\" href=\"" + frontUrl + "/rgltchk/rgltchkimkedt/" + rgltDataList.get(j).get("rglt_chk_no") + "\">" + mUtil.getMLang(mlangCntn, "LB00000449") + "</a></p>\r\n" + 
							"            </td>\r\n" + 
							"        </tr>\r\n";
				}
				
			}
			// 메일 내용
			String content = "<p>" + mUtil.getMLang(mlangCntn, "MS00000200") + "</p>\r\n" + 
					"<table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"border:none;border-collapse:collapse;word-break:break-all;font-family:&quot;Malgun Gothic&quot;, &quot;맑은 고딕&quot;;font-size:13.3333px\">\r\n" + 
					"    <tbody>\r\n" + 
					"        <tr>\r\n" + 
					"            <td style=\"width: 20%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"                <p style=\"text-align: center;\"><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000199") + "</span><br></p>\r\n" + 
					"            </td>\r\n" + 
					"            <td style=\"width: 20%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"                <p style=\"font-weight: bold; text-align: center;\"><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000025") + "</span></p>\r\n" + 
					"            </td>\r\n" + 
					"            <td style=\"width: 20%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"                <p style=\"font-weight: bold; text-align: center;\"><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000404") + "</span></p>\r\n" + 
					"            </td>\r\n" + 
					"            <td style=\"width: 20%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"                <p style=\"font-weight: bold; text-align: center;\"><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000516") + "</span></p>\r\n" + 
					"            </td>\r\n" + 
					"            <td style=\"width: 20%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"                <p style=\"text-align: center;\"><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000449") + "</span></p>\r\n" + 
					"            </td>\r\n" + 
					"        </tr>\r\n" + 
					table +
					"    </tbody>\r\n" + 
					"</table>";
			
			// 메일 송신
			mailResult = util.sendMail(bizCd, "AC928960", subject, content, to);
		}
		
		return mailResult; 
	}
}
