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
public class MtrlVndrJob implements Job {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	BatchMapper mapper;
	
	@Autowired
	TransactionHandler transactionHandler;
	
	private String content = "";
	private String resultCd = "";
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("MtrlVndrJob start, jobKey: " + context.getJobDetail().getKey().getName());
        
        Map<String, String> param = new HashMap<>();
        param.put("batCd", context.getJobDetail().getKey().getName());
        mapper.insertBatch(param); // start
        logger.info("MtrlVndrJob batNo : " + param.get("batNo"));
        
        // 배치 실행 중 상태 코드 업데이트
        param.put("resultCd", "EX");
        param.put("content", "");
        mapper.updateBatch(param);
        
        // 실제 배치 실행 후 결과 코드 
        content = "";
        resultCd = "ER";
        transactionHandler.runInTransaction(() -> {
	        try {
		        //1. 공급 업체 데이터 조회
	        	List<Map<String, Object>> vndrList = mapper.selectVndrSapList(param);
	        	for(int i = 0; i < vndrList.size(); i++) {
	        		//2. 해당 데이터 merge 처리
	        		mapper.upsertVndrList(vndrList.get(i));
	        		//3. 해당 데이터 trn_yn 처리
	        		mapper.updateVndrStt(vndrList.get(i));
	        	}
	        	
	        	//3. 자재별 공급업체 리스트 조회
	        	List<Map<String, Object>> mtrlVndrList = mapper.selectMtrlVndrSapList(param);
	        	//4. 자재별 공급업체 merge
	        	for(int i = 0; i < mtrlVndrList.size(); i++) {
	        		//2. 해당 데이터 merge 처리
	        		mapper.upsertMtrlVndrList(mtrlVndrList.get(i));
	        		//3. 해당 데이터 trn_yn 처리
	        		mapper.updateMtrlVndrStt(mtrlVndrList.get(i));
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
        logger.info("MtrlVndrJob start, jobKey: " + "BV001");
        
        Map<String, String> param = new HashMap<>();
        param.put("batCd", "BV001");
        mapper.insertBatch(param); // start
        logger.info("MtrlVndrJob batNo : " + param.get("batNo"));
        
        // 배치 실행 중 상태 코드 업데이트
        param.put("resultCd", "EX");
        param.put("content", "");
        mapper.updateBatch(param);
        
        // 실제 배치 실행 후 결과 코드 
        content = "";
        resultCd = "ER";
        transactionHandler.runInTransaction(() -> {
	        try {
		        //1. 공급 업체 데이터 조회
	        	List<Map<String, Object>> vndrList = mapper.selectVndrSapList(param);
	        	for(int i = 0; i < vndrList.size(); i++) {
	        		//2. 해당 데이터 merge 처리
	        		mapper.upsertVndrList(vndrList.get(i));
	        		//3. 해당 데이터 trn_yn 처리
	        		mapper.updateVndrStt(vndrList.get(i));
	        	}
	        	
	        	//3. 자재별 공급업체 리스트 조회
	        	List<Map<String, Object>> mtrlVndrList = mapper.selectMtrlVndrSapList(param);
	        	//4. 자재별 공급업체 merge
	        	for(int i = 0; i < mtrlVndrList.size(); i++) {
	        		//2. 해당 데이터 merge 처리
	        		mapper.upsertMtrlVndrList(mtrlVndrList.get(i));
	        		//3. 해당 데이터 trn_yn 처리
	        		mapper.updateMtrlVndrStt(mtrlVndrList.get(i));
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
