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
import org.springframework.stereotype.Component;

import com.kist.portal.common.batch.mapper.BatchMapper;
import com.kist.portal.common.utils.TransactionHandler;

@Component
public class MtrlWhJob implements Job {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	BatchMapper mapper;
	
	@Autowired
	TransactionHandler transactionHandler;
	
	private String content = "";
	private String resultCd = "";
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("MtrlWhJob start, jobKey: " + context.getJobDetail().getKey().getName());
        
        Map<String, String> param = new HashMap<>();
        param.put("batCd", context.getJobDetail().getKey().getName());
        mapper.insertBatch(param); // start
        logger.info("MtrlWhJob batNo : " + param.get("batNo"));
        
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
            	// 1. 작업장 그룹 코드 추가
            	mapper.insertWhWrkAreaCdGrp(temp);
            	// 2. 작업장 그룹 코드명 다국어 추가
            	mapper.insertWhWrkAreaCdGrpMlang(temp);
            	// 3. 작업장 코드 추가
            	mapper.insertWhWrkAreaCd(temp);
            	// 4. 작업장 코드명 다국어 추가
            	mapper.insertWhWrkAreaCdMlang(temp);
            	// 5. 시설 코드 추가
            	mapper.insertWhEqupCd(temp);
            	// 6. 시설 코드명 다국어 추가
            	mapper.insertWhEqupCdMlang(temp);
            	// 7. 시설 코드 작업장 매핑 등록
            	mapper.insertWhEqupWrkAreaRel(temp);
            	// 8. 시설 코드 작업장 매핑 등록 2
            	mapper.insertWhEqupWrkAreaRelT(temp);
            	// 업데이트 리스트 여부 확인
            	List<Map<String, Object>> list = mapper.selectMtrlRohWh(temp);
            	List<Map<String, Object>> list2 = mapper.selectMtrlHalWh(temp);
            	// mro 자재 입고 처리
            	List<Map<String, Object>> mroList = mapper.selectMroMtrlWhList(temp);
            	if(list.size() > 0 || list2.size() > 0 || mroList.size() > 0) {
            		// 자재 입고 테이블 insert
                	mapper.insertMtrlWhDl(temp);
                	// 자재 입고 테이블 insert(Mro)
                	mapper.insertMroWhDl(temp);
                	// 9. 집계 대상 사업장 테이블 insert(생산 출고 및 취소)
                	// mapper.insertWhDdRohData(temp);
                	// Roh 데이터 존재 여부 확인
                	if(list.size() > 0) {
                		Map<String, Object> rohTemp = new HashMap<String, Object>();
                		for(int i = 0; i < list.size(); i++) {
                			rohTemp = list.get(i);
                			String chkFlag = String.valueOf(rohTemp.get("cud_ci"));
                			// 가져온 데이터의 등록 구분에 따라 로직 처리
                			if("C".equalsIgnoreCase(chkFlag)) { // 처음 들어온 데이터
            					// tran_type이 101일 경우 +, tran_type이 102일 경우 - upsert 처리
            					mapper.updateCreateWhDd(rohTemp);
            					// 해당 월 데이터 upsert 처리(101이면 + 102이면 -)
            					mapper.updateCreateWhMm(rohTemp);
            					// 해당 자재에 대한 물질 정보 select
            					List<Map<String, Object>> sbstList = mapper.selectWhRohSbstList(rohTemp);
            					if(sbstList.size() > 0) {
            						for(int j = 0; j < sbstList.size(); j++) {
            							rohTemp = sbstList.get(j);
            							// 물질 데이터 upsert 처리 (101이면 + 102이면 -)
            							mapper.updateCreateWhSbDd(rohTemp);
            							// 해당 물질 데이터 월별 upsert 처리(101이면 + 102이면 -)
            							mapper.updateCreateWhSbMm(rohTemp);
            						}
            					}
                			}else if("U".equalsIgnoreCase(chkFlag)) { // 기존 데이터를 수정
                				// 해당 데이터를 기본키를 기준으로 tb_ch_mtrl_wh 테이블을 조회하여 수정하고자 하는 데이터를 조회(C또는 U인 데이터)
                				List<Map<String, Object>> trgtDt = mapper.selectWhUpdateList(rohTemp);
                				if(trgtDt.size() > 0) {
                					// 수정하고자 하는 데이터를 찾았다면 해당 데이터의 키에 해당하는 일일 합산 테이블에 tran_type의 반대로 값을 빼고 Update 데이터의 값을 tran_type에 따라 처리해준다.
                					rohTemp.put("bf_qty", trgtDt.get(0).get("wh_qty"));
                					rohTemp.put("bf_type_cd", trgtDt.get(0).get("move_type_cd"));
                					mapper.updateUpdateWhDd(rohTemp);
                					// 해당 일자의 월별 데이터 update 처리
                					mapper.updateUpdateWhMm(rohTemp);
                					// 해당 자재에 대한 물질 정보 select
                					List<Map<String, Object>> sbstList = mapper.selectWhRohSbstList(rohTemp);
                					if(sbstList.size() > 0) {
                						for(int j = 0; j < sbstList.size(); j++) {
                							rohTemp = sbstList.get(j);
                							rohTemp.put("bf_type_cd", trgtDt.get(0).get("move_type_cd"));
                							// 물질 데이터 update 처리 (101이면 + 102이면 -)
                							mapper.updateUpdateWhSbDd(rohTemp);
                							// 해당 물질 데이터 월별 update 처리(101이면 + 102이면 -)
                							mapper.updateUpdateWhSbMm(rohTemp);
                						}
                					}
                				}else { // 타겟 데이터가 존재하지 않을 경우 Create를 따라감
                					// tran_type이 101일 경우 +, tran_type이 102일 경우 - upsert 처리
                					mapper.updateCreateWhDd(rohTemp);
                					// 해당 월 데이터 upsert 처리(101이면 + 102이면 -)
                					mapper.updateCreateWhMm(rohTemp);
                					// 해당 자재에 대한 물질 정보 select
                					List<Map<String, Object>> sbstList = mapper.selectWhRohSbstList(rohTemp);
                					if(sbstList.size() > 0) {
                						for(int j = 0; j < sbstList.size(); j++) {
                							rohTemp = sbstList.get(j);
                							// 물질 데이터 upsert 처리 (101이면 + 102이면 -)
                							mapper.updateCreateWhSbDd(rohTemp);
                							// 해당 물질 데이터 월별 upsert 처리(101이면 + 102이면 -)
                							mapper.updateCreateWhSbMm(rohTemp);
                						}
                					}
                				}
                			}else if("D".equalsIgnoreCase(chkFlag)) { // 기존 데이터를 삭제
                				// 해당 데이터를 기본키를 기준으로 tb_ch_mtrl_wh 테이블을 조회하여 삭제하고자 하는 데이터를 조회(C또는 U인 데이터)
                				List<Map<String, Object>> trgtDt = mapper.selectWhUpdateList(rohTemp);
                				if(trgtDt.size() > 0) {
                					// 수정하고자 하는 데이터를 찾았다면 해당 데이터의 키에 해당하는 값을 반대로 처리한다.
                					rohTemp.put("bf_qty", trgtDt.get(0).get("wh_qty"));
                					rohTemp.put("bf_type_cd", trgtDt.get(0).get("move_type_cd"));
                					mapper.updateDeleteWhDd(rohTemp);
                					// 해당 일자의 월별 데이터 update 처리
                					mapper.updateDeleteWhMm(rohTemp);
                					// 해당 자재에 대한 물질 정보 select
                					List<Map<String, Object>> sbstList = mapper.selectWhRohSbstList(rohTemp);
                					if(sbstList.size() > 0) {
                						for(int j = 0; j < sbstList.size(); j++) {
                							rohTemp = sbstList.get(j);
                							rohTemp.put("bf_type_cd", trgtDt.get(0).get("move_type_cd"));
                							// 물질 데이터 update 처리 (삭제이므로 반대로 처리)
                							mapper.updateDeleteWhSbDd(rohTemp);
                							// 해당 물질 데이터 월별 update 처리(삭제이므로 반대로 처리)
                							mapper.updateDeleteWhSbMm(rohTemp);
                						}
                					}
                				} 
                				// 기존 데이터 없을 경우 처리 없음
                			}
                		}
                	}
                	// mapper.insertWhDdHalData(temp);
                	if(list2.size() > 0) {
                		Map<String, Object> halTemp = new HashMap<String, Object>();
                		for(int i = 0; i < list2.size(); i++) {
                			halTemp = list2.get(i);
                			String chkFlag = String.valueOf(halTemp.get("cud_ci"));
                			// 가져온 데이터의 등록 구분에 따라 로직 처리
                			if("C".equalsIgnoreCase(chkFlag)) { // 처음 들어온 데이터
            					// tran_type이 101일 경우 +, tran_type이 102일 경우 - upsert 처리
            					mapper.updateCreateWhDd(halTemp);
            					// 해당 월 데이터 upsert 처리(101이면 + 102이면 -)
            					mapper.updateCreateWhMm(halTemp);
            					// 해당 자재에 대한 물질 정보 select
            					List<Map<String, Object>> sbstList = mapper.selectWhHalSbstList(halTemp);
            					if(sbstList.size() > 0) {
            						for(int j = 0; j < sbstList.size(); j++) {
            							halTemp = sbstList.get(j);
            							// 물질 데이터 upsert 처리 (101이면 + 102이면 -)
            							mapper.updateCreateWhSbDd(halTemp);
            							// 해당 물질 데이터 월별 upsert 처리(101이면 + 102이면 -)
            							mapper.updateCreateWhSbMm(halTemp);
            						}
            					}
                			}else if("U".equalsIgnoreCase(chkFlag)) { // 기존 데이터를 수정
                				// 해당 데이터를 기본키를 기준으로 tb_ch_mtrl_wh 테이블을 조회하여 수정하고자 하는 데이터를 조회(C또는 U인 데이터)
                				List<Map<String, Object>> trgtDt = mapper.selectWhUpdateList(halTemp);
                				if(trgtDt.size() > 0) {
                					// 수정하고자 하는 데이터를 찾았다면 해당 데이터의 키에 해당하는 일일 합산 테이블에 tran_type의 반대로 값을 빼고 Update 데이터의 값을 tran_type에 따라 처리해준다.
                					halTemp.put("bf_qty", trgtDt.get(0).get("wh_qty"));
                					halTemp.put("bf_type_cd", trgtDt.get(0).get("move_type_cd"));
                					mapper.updateUpdateWhDd(halTemp);
                					// 해당 일자의 월별 데이터 update 처리
                					mapper.updateUpdateWhMm(halTemp);
                					// 해당 자재에 대한 물질 정보 select
                					List<Map<String, Object>> sbstList = mapper.selectWhHalSbstList(halTemp);
                					if(sbstList.size() > 0) {
                						for(int j = 0; j < sbstList.size(); j++) {
                							halTemp = sbstList.get(j);
                							halTemp.put("bf_type_cd", trgtDt.get(0).get("move_type_cd"));
                							// 물질 데이터 update 처리 (101이면 + 102이면 -)
                							mapper.updateUpdateWhSbDd(halTemp);
                							// 해당 물질 데이터 월별 update 처리(101이면 + 102이면 -)
                							mapper.updateUpdateWhSbMm(halTemp);
                						}
                					}
                				}else { // 타겟 데이터가 존재하지 않을 경우 Create를 따라감
                					// tran_type이 101일 경우 +, tran_type이 102일 경우 - upsert 처리
                					mapper.updateCreateWhDd(halTemp);
                					// 해당 월 데이터 upsert 처리(101이면 + 102이면 -)
                					mapper.updateCreateWhMm(halTemp);
                					// 해당 자재에 대한 물질 정보 select
                					List<Map<String, Object>> sbstList = mapper.selectWhHalSbstList(halTemp);
                					if(sbstList.size() > 0) {
                						for(int j = 0; j < sbstList.size(); j++) {
                							halTemp = sbstList.get(j);
                							// 물질 데이터 upsert 처리 (101이면 + 102이면 -)
                							mapper.updateCreateWhSbDd(halTemp);
                							// 해당 물질 데이터 월별 upsert 처리(101이면 + 102이면 -)
                							mapper.updateCreateWhSbMm(halTemp);
                						}
                					}
                				}
                			}else if("D".equalsIgnoreCase(chkFlag)) { // 기존 데이터를 삭제
                				// 해당 데이터를 기본키를 기준으로 tb_ch_mtrl_wh 테이블을 조회하여 삭제하고자 하는 데이터를 조회(C또는 U인 데이터)
                				List<Map<String, Object>> trgtDt = mapper.selectWhUpdateList(halTemp);
                				if(trgtDt.size() > 0) {
                					// 수정하고자 하는 데이터를 찾았다면 해당 데이터의 키에 해당하는 값을 반대로 처리한다.
                					halTemp.put("bf_qty", trgtDt.get(0).get("wh_qty"));
                					halTemp.put("bf_type_cd", trgtDt.get(0).get("move_type_cd"));
                					mapper.updateDeleteWhDd(halTemp);
                					// 해당 일자의 월별 데이터 update 처리
                					mapper.updateDeleteWhMm(halTemp);
                					// 해당 자재에 대한 물질 정보 select
                					List<Map<String, Object>> sbstList = mapper.selectWhHalSbstList(halTemp);
                					if(sbstList.size() > 0) {
                						for(int j = 0; j < sbstList.size(); j++) {
                							halTemp = sbstList.get(j);
                							halTemp.put("bf_type_cd", trgtDt.get(0).get("move_type_cd"));
                							// 물질 데이터 update 처리 (삭제이므로 반대로 처리)
                							mapper.updateDeleteWhSbDd(halTemp);
                							// 해당 물질 데이터 월별 update 처리(삭제이므로 반대로 처리)
                							mapper.updateDeleteWhSbMm(halTemp);
                						}
                					}
                				} 
                				// 기존 데이터 없을 경우 처리 없음
                			}
                		}
                	}
                	// 집계 대상 사업장 테이블 insert(mro)
                	// mapper.insertWhDdMroData(temp);
                	if(mroList.size() > 0) { // mro는 들어오는대로 모두 소모된 것으로 치며 'C'만 존재
                		Map<String, Object> mroTemp = new HashMap<String, Object>();
                		for(int i = 0; i < mroList.size(); i++) {
                			mroTemp = mroList.get(i);
        					// 모두 + upsert 처리
        					mapper.updateCreateWhMroDd(mroTemp);
        					// 해당 월 데이터 upsert 처리(모두 +)
        					mapper.updateCreateWhMroMm(mroTemp);
        					// 해당 자재에 대한 물질 정보 select
        					List<Map<String, Object>> sbstList = mapper.selectWhMroSbstList(mroTemp);
        					if(sbstList.size() > 0) {
        						for(int j = 0; j < sbstList.size(); j++) {
        							mroTemp = sbstList.get(j);
        							// 물질 데이터 upsert 처리 (모두 +)
        							mapper.updateCreateWhSbMroDd(mroTemp);
        							// 해당 물질 데이터 월별 upsert 처리(모두 +)
        							mapper.updateCreateWhSbMroMm(mroTemp);
        						}
        					}
        					// Mro 인터페이스 테이블 trn_yn 여부 Y 변경
                        	mapper.updateWhMroTrn(mroTemp);
                		}
                	}
                	// 13. 인터페이스 테이블 trn_yn 여부 Y 변경
                	mapper.updateWhRohMaTrn(temp);
                	mapper.updateWhHalMaTrn(temp);
            	}
            	// imk 자재 입고 처리
            	List<Map<String, Object>> imkList = mapper.selectImkMtrlWhList(temp);
            	for(int i = 0; i < imkList.size(); i++) {
            		Map<String, Object> imkTemp = imkList.get(i);
            		Map<String, Object> uptTemp = imkList.get(i);
            		String gb = String.valueOf(imkTemp.get("if_gb"));
            		// 자재 입고 마스터 테이블에 데이터 추가
            		mapper.insertImkMstData(imkTemp);
        			// 존재할 경우 자재 입고 update 처리
        			imkTemp.put("if_gb", gb);
        			// upsert 처리
        			mapper.updateImkWhDdData(imkTemp);
        			// 해당 달 데이터 upsert 처리
        			mapper.updateImkWhMmData(imkTemp);
        			// 물질 입고 update 처리
        			// 업데이트 필요한 물질 검색
        			List<Map<String, Object>> sbstList = mapper.selectImkSbstList(imkTemp);
        			for(int j = 0; j < sbstList.size(); j++) {
        				imkTemp = sbstList.get(j);
        				imkTemp.put("if_gb", gb);
        				// 물질 리스트 만큼 기존 테이블 upsert
        				mapper.updateImkSbstDlData(imkTemp);
        				// 물질 리스트 해당 달 데이터 upsert
        				mapper.updateImkSbstMmData(imkTemp);
        			}
            		// wh_trn_yn 값 업데이트
            		mapper.updateImkMtrlTrnYn(uptTemp);
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
        logger.info("MtrlWhJob start, jobKey: " + "BQ001");
        
        Map<String, String> param = new HashMap<>();
        param.put("batCd", "BQ001");
        mapper.insertBatch(param); // start
        logger.info("MtrlWhJob batNo : " + param.get("batNo"));
        
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
            	// 1. 작업장 그룹 코드 추가
            	mapper.insertWhWrkAreaCdGrp(temp);
            	// 2. 작업장 그룹 코드명 다국어 추가
            	mapper.insertWhWrkAreaCdGrpMlang(temp);
            	// 3. 작업장 코드 추가
            	mapper.insertWhWrkAreaCd(temp);
            	// 4. 작업장 코드명 다국어 추가
            	mapper.insertWhWrkAreaCdMlang(temp);
            	// 5. 시설 코드 추가
            	mapper.insertWhEqupCd(temp);
            	// 6. 시설 코드명 다국어 추가
            	mapper.insertWhEqupCdMlang(temp);
            	// 7. 시설 코드 작업장 매핑 등록
            	mapper.insertWhEqupWrkAreaRel(temp);
            	// 8. 시설 코드 작업장 매핑 등록 2
            	mapper.insertWhEqupWrkAreaRelT(temp);
            	// 업데이트 리스트 여부 확인
            	List<Map<String, Object>> list = mapper.selectMtrlRohWh(temp);
            	List<Map<String, Object>> list2 = mapper.selectMtrlHalWh(temp);
            	// mro 자재 입고 처리
            	List<Map<String, Object>> mroList = mapper.selectMroMtrlWhList(temp);
            	if(list.size() > 0 || list2.size() > 0 || mroList.size() > 0) {
            		// 자재 입고 테이블 insert
                	mapper.insertMtrlWhDl(temp);
                	// 자재 입고 테이블 insert(Mro)
                	mapper.insertMroWhDl(temp);
                	// 9. 집계 대상 사업장 테이블 insert(생산 출고 및 취소)
                	// mapper.insertWhDdRohData(temp);
                	// Roh 데이터 존재 여부 확인
                	if(list.size() > 0) {
                		Map<String, Object> rohTemp = new HashMap<String, Object>();
                		for(int i = 0; i < list.size(); i++) {
                			rohTemp = list.get(i);
                			String chkFlag = String.valueOf(rohTemp.get("cud_ci"));
                			// 가져온 데이터의 등록 구분에 따라 로직 처리
                			if("C".equalsIgnoreCase(chkFlag)) { // 처음 들어온 데이터
            					// tran_type이 101일 경우 +, tran_type이 102일 경우 - upsert 처리
            					mapper.updateCreateWhDd(rohTemp);
            					// 해당 월 데이터 upsert 처리(101이면 + 102이면 -)
            					mapper.updateCreateWhMm(rohTemp);
            					// 해당 자재에 대한 물질 정보 select
            					List<Map<String, Object>> sbstList = mapper.selectWhRohSbstList(rohTemp);
            					if(sbstList.size() > 0) {
            						for(int j = 0; j < sbstList.size(); j++) {
            							rohTemp = sbstList.get(j);
            							// 물질 데이터 upsert 처리 (101이면 + 102이면 -)
            							mapper.updateCreateWhSbDd(rohTemp);
            							// 해당 물질 데이터 월별 upsert 처리(101이면 + 102이면 -)
            							mapper.updateCreateWhSbMm(rohTemp);
            						}
            					}
                			}else if("U".equalsIgnoreCase(chkFlag)) { // 기존 데이터를 수정
                				// 해당 데이터를 기본키를 기준으로 tb_ch_mtrl_wh 테이블을 조회하여 수정하고자 하는 데이터를 조회(C또는 U인 데이터)
                				List<Map<String, Object>> trgtDt = mapper.selectWhUpdateList(rohTemp);
                				if(trgtDt.size() > 0) {
                					// 수정하고자 하는 데이터를 찾았다면 해당 데이터의 키에 해당하는 일일 합산 테이블에 tran_type의 반대로 값을 빼고 Update 데이터의 값을 tran_type에 따라 처리해준다.
                					rohTemp.put("bf_qty", trgtDt.get(0).get("wh_qty"));
                					rohTemp.put("bf_type_cd", trgtDt.get(0).get("move_type_cd"));
                					mapper.updateUpdateWhDd(rohTemp);
                					// 해당 일자의 월별 데이터 update 처리
                					mapper.updateUpdateWhMm(rohTemp);
                					// 해당 자재에 대한 물질 정보 select
                					List<Map<String, Object>> sbstList = mapper.selectWhRohSbstList(rohTemp);
                					if(sbstList.size() > 0) {
                						for(int j = 0; j < sbstList.size(); j++) {
                							rohTemp = sbstList.get(j);
                							rohTemp.put("bf_type_cd", trgtDt.get(0).get("move_type_cd"));
                							// 물질 데이터 update 처리 (101이면 + 102이면 -)
                							mapper.updateUpdateWhSbDd(rohTemp);
                							// 해당 물질 데이터 월별 update 처리(101이면 + 102이면 -)
                							mapper.updateUpdateWhSbMm(rohTemp);
                						}
                					}
                				}else { // 타겟 데이터가 존재하지 않을 경우 Create를 따라감
                					// tran_type이 101일 경우 +, tran_type이 102일 경우 - upsert 처리
                					mapper.updateCreateWhDd(rohTemp);
                					// 해당 월 데이터 upsert 처리(101이면 + 102이면 -)
                					mapper.updateCreateWhMm(rohTemp);
                					// 해당 자재에 대한 물질 정보 select
                					List<Map<String, Object>> sbstList = mapper.selectWhRohSbstList(rohTemp);
                					if(sbstList.size() > 0) {
                						for(int j = 0; j < sbstList.size(); j++) {
                							rohTemp = sbstList.get(j);
                							// 물질 데이터 upsert 처리 (101이면 + 102이면 -)
                							mapper.updateCreateWhSbDd(rohTemp);
                							// 해당 물질 데이터 월별 upsert 처리(101이면 + 102이면 -)
                							mapper.updateCreateWhSbMm(rohTemp);
                						}
                					}
                				}
                			}else if("D".equalsIgnoreCase(chkFlag)) { // 기존 데이터를 삭제
                				// 해당 데이터를 기본키를 기준으로 tb_ch_mtrl_wh 테이블을 조회하여 삭제하고자 하는 데이터를 조회(C또는 U인 데이터)
                				List<Map<String, Object>> trgtDt = mapper.selectWhUpdateList(rohTemp);
                				if(trgtDt.size() > 0) {
                					// 수정하고자 하는 데이터를 찾았다면 해당 데이터의 키에 해당하는 값을 반대로 처리한다.
                					rohTemp.put("bf_qty", trgtDt.get(0).get("wh_qty"));
                					rohTemp.put("bf_type_cd", trgtDt.get(0).get("move_type_cd"));
                					mapper.updateDeleteWhDd(rohTemp);
                					// 해당 일자의 월별 데이터 update 처리
                					mapper.updateDeleteWhMm(rohTemp);
                					// 해당 자재에 대한 물질 정보 select
                					List<Map<String, Object>> sbstList = mapper.selectWhRohSbstList(rohTemp);
                					if(sbstList.size() > 0) {
                						for(int j = 0; j < sbstList.size(); j++) {
                							rohTemp = sbstList.get(j);
                							rohTemp.put("bf_type_cd", trgtDt.get(0).get("move_type_cd"));
                							// 물질 데이터 update 처리 (삭제이므로 반대로 처리)
                							mapper.updateDeleteWhSbDd(rohTemp);
                							// 해당 물질 데이터 월별 update 처리(삭제이므로 반대로 처리)
                							mapper.updateDeleteWhSbMm(rohTemp);
                						}
                					}
                				} 
                				// 기존 데이터 없을 경우 처리 없음
                			}
                		}
                	}
                	// mapper.insertWhDdHalData(temp);
                	if(list2.size() > 0) {
                		Map<String, Object> halTemp = new HashMap<String, Object>();
                		for(int i = 0; i < list2.size(); i++) {
                			halTemp = list2.get(i);
                			String chkFlag = String.valueOf(halTemp.get("cud_ci"));
                			// 가져온 데이터의 등록 구분에 따라 로직 처리
                			if("C".equalsIgnoreCase(chkFlag)) { // 처음 들어온 데이터
            					// tran_type이 101일 경우 +, tran_type이 102일 경우 - upsert 처리
            					mapper.updateCreateWhDd(halTemp);
            					// 해당 월 데이터 upsert 처리(101이면 + 102이면 -)
            					mapper.updateCreateWhMm(halTemp);
            					// 해당 자재에 대한 물질 정보 select
            					List<Map<String, Object>> sbstList = mapper.selectWhHalSbstList(halTemp);
            					if(sbstList.size() > 0) {
            						for(int j = 0; j < sbstList.size(); j++) {
            							halTemp = sbstList.get(j);
            							// 물질 데이터 upsert 처리 (101이면 + 102이면 -)
            							mapper.updateCreateWhSbDd(halTemp);
            							// 해당 물질 데이터 월별 upsert 처리(101이면 + 102이면 -)
            							mapper.updateCreateWhSbMm(halTemp);
            						}
            					}
                			}else if("U".equalsIgnoreCase(chkFlag)) { // 기존 데이터를 수정
                				// 해당 데이터를 기본키를 기준으로 tb_ch_mtrl_wh 테이블을 조회하여 수정하고자 하는 데이터를 조회(C또는 U인 데이터)
                				List<Map<String, Object>> trgtDt = mapper.selectWhUpdateList(halTemp);
                				if(trgtDt.size() > 0) {
                					// 수정하고자 하는 데이터를 찾았다면 해당 데이터의 키에 해당하는 일일 합산 테이블에 tran_type의 반대로 값을 빼고 Update 데이터의 값을 tran_type에 따라 처리해준다.
                					halTemp.put("bf_qty", trgtDt.get(0).get("wh_qty"));
                					halTemp.put("bf_type_cd", trgtDt.get(0).get("move_type_cd"));
                					mapper.updateUpdateWhDd(halTemp);
                					// 해당 일자의 월별 데이터 update 처리
                					mapper.updateUpdateWhMm(halTemp);
                					// 해당 자재에 대한 물질 정보 select
                					List<Map<String, Object>> sbstList = mapper.selectWhHalSbstList(halTemp);
                					if(sbstList.size() > 0) {
                						for(int j = 0; j < sbstList.size(); j++) {
                							halTemp = sbstList.get(j);
                							halTemp.put("bf_type_cd", trgtDt.get(0).get("move_type_cd"));
                							// 물질 데이터 update 처리 (101이면 + 102이면 -)
                							mapper.updateUpdateWhSbDd(halTemp);
                							// 해당 물질 데이터 월별 update 처리(101이면 + 102이면 -)
                							mapper.updateUpdateWhSbMm(halTemp);
                						}
                					}
                				}else { // 타겟 데이터가 존재하지 않을 경우 Create를 따라감
                					// tran_type이 101일 경우 +, tran_type이 102일 경우 - upsert 처리
                					mapper.updateCreateWhDd(halTemp);
                					// 해당 월 데이터 upsert 처리(101이면 + 102이면 -)
                					mapper.updateCreateWhMm(halTemp);
                					// 해당 자재에 대한 물질 정보 select
                					List<Map<String, Object>> sbstList = mapper.selectWhHalSbstList(halTemp);
                					if(sbstList.size() > 0) {
                						for(int j = 0; j < sbstList.size(); j++) {
                							halTemp = sbstList.get(j);
                							// 물질 데이터 upsert 처리 (101이면 + 102이면 -)
                							mapper.updateCreateWhSbDd(halTemp);
                							// 해당 물질 데이터 월별 upsert 처리(101이면 + 102이면 -)
                							mapper.updateCreateWhSbMm(halTemp);
                						}
                					}
                				}
                			}else if("D".equalsIgnoreCase(chkFlag)) { // 기존 데이터를 삭제
                				// 해당 데이터를 기본키를 기준으로 tb_ch_mtrl_wh 테이블을 조회하여 삭제하고자 하는 데이터를 조회(C또는 U인 데이터)
                				List<Map<String, Object>> trgtDt = mapper.selectWhUpdateList(halTemp);
                				if(trgtDt.size() > 0) {
                					// 수정하고자 하는 데이터를 찾았다면 해당 데이터의 키에 해당하는 값을 반대로 처리한다.
                					halTemp.put("bf_qty", trgtDt.get(0).get("wh_qty"));
                					halTemp.put("bf_type_cd", trgtDt.get(0).get("move_type_cd"));
                					mapper.updateDeleteWhDd(halTemp);
                					// 해당 일자의 월별 데이터 update 처리
                					mapper.updateDeleteWhMm(halTemp);
                					// 해당 자재에 대한 물질 정보 select
                					List<Map<String, Object>> sbstList = mapper.selectWhHalSbstList(halTemp);
                					if(sbstList.size() > 0) {
                						for(int j = 0; j < sbstList.size(); j++) {
                							halTemp = sbstList.get(j);
                							halTemp.put("bf_type_cd", trgtDt.get(0).get("move_type_cd"));
                							// 물질 데이터 update 처리 (삭제이므로 반대로 처리)
                							mapper.updateDeleteWhSbDd(halTemp);
                							// 해당 물질 데이터 월별 update 처리(삭제이므로 반대로 처리)
                							mapper.updateDeleteWhSbMm(halTemp);
                						}
                					}
                				} 
                				// 기존 데이터 없을 경우 처리 없음
                			}
                		}
                	}
                	// 집계 대상 사업장 테이블 insert(mro)
                	// mapper.insertWhDdMroData(temp);
                	if(mroList.size() > 0) { // mro는 들어오는대로 모두 소모된 것으로 치며 'C'만 존재
                		Map<String, Object> mroTemp = new HashMap<String, Object>();
                		for(int i = 0; i < mroList.size(); i++) {
                			mroTemp = mroList.get(i);
        					// 모두 + upsert 처리
        					mapper.updateCreateWhMroDd(mroTemp);
        					// 해당 월 데이터 upsert 처리(모두 +)
        					mapper.updateCreateWhMroMm(mroTemp);
        					// 해당 자재에 대한 물질 정보 select
        					List<Map<String, Object>> sbstList = mapper.selectWhMroSbstList(mroTemp);
        					if(sbstList.size() > 0) {
        						for(int j = 0; j < sbstList.size(); j++) {
        							mroTemp = sbstList.get(j);
        							// 물질 데이터 upsert 처리 (모두 +)
        							mapper.updateCreateWhSbMroDd(mroTemp);
        							// 해당 물질 데이터 월별 upsert 처리(모두 +)
        							mapper.updateCreateWhSbMroMm(mroTemp);
        						}
        					}
        					// Mro 인터페이스 테이블 trn_yn 여부 Y 변경
                        	mapper.updateWhMroTrn(mroTemp);
                		}
                	}
                	// 13. 인터페이스 테이블 trn_yn 여부 Y 변경
                	mapper.updateWhRohMaTrn(temp);
                	mapper.updateWhHalMaTrn(temp);
            	}
            	// imk 자재 입고 처리
            	List<Map<String, Object>> imkList = mapper.selectImkMtrlWhList(temp);
            	for(int i = 0; i < imkList.size(); i++) {
            		Map<String, Object> imkTemp = imkList.get(i);
            		Map<String, Object> uptTemp = imkList.get(i);
            		String gb = String.valueOf(imkTemp.get("if_gb"));
            		// 자재 입고 마스터 테이블에 데이터 추가
            		mapper.insertImkMstData(imkTemp);
        			// 존재할 경우 자재 입고 update 처리
        			imkTemp.put("if_gb", gb);
        			// upsert 처리
        			mapper.updateImkWhDdData(imkTemp);
        			// 해당 달 데이터 upsert 처리
        			mapper.updateImkWhMmData(imkTemp);
        			// 물질 입고 update 처리
        			// 업데이트 필요한 물질 검색
        			List<Map<String, Object>> sbstList = mapper.selectImkSbstList(imkTemp);
        			for(int j = 0; j < sbstList.size(); j++) {
        				imkTemp = sbstList.get(j);
        				imkTemp.put("if_gb", gb);
        				// 물질 리스트 만큼 기존 테이블 upsert
        				mapper.updateImkSbstDlData(imkTemp);
        				// 물질 리스트 해당 달 데이터 upsert
        				mapper.updateImkSbstMmData(imkTemp);
        			}
            		// wh_trn_yn 값 업데이트
            		mapper.updateImkMtrlTrnYn(uptTemp);
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
}
