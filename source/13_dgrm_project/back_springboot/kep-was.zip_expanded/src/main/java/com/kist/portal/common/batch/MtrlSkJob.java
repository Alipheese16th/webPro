package com.kist.portal.common.batch;

import java.util.HashMap;
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
public class MtrlSkJob implements Job {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	BatchMapper mapper;
	
	@Autowired
	TransactionHandler transactionHandler;
	
	private String content = "";
	private String resultCd = "";
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("MtrlSkJob start, jobKey: " + context.getJobDetail().getKey().getName());
        
        Map<String, String> param = new HashMap<>();
        param.put("batCd", context.getJobDetail().getKey().getName());
        mapper.insertBatch(param); // start
        logger.info("MtrlSkJob batNo : " + param.get("batNo"));
        
        // 실제 배치 실행 후 결과 코드 
        content = "";
        resultCd = "ER";
        transactionHandler.runInTransaction(() -> {
        	try {
        		Map<String, Object> temp = new HashMap<>();
            	// 1. 자재 재고 insert
        		mapper.insertMtrlSk(temp);
        		// 2. 자재 물질 insert
        		mapper.insertSbstSk(temp);
        		// 3. 자재 재고 인터페이스 trn 업데이트
        		mapper.updateMtrlTrnStt(temp);
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
        logger.info("MtrlSkJob start, jobKey: " + "BQ003");
        
        Map<String, String> param = new HashMap<>();
        param.put("batCd", "BQ003");
        mapper.insertBatch(param); // start
        logger.info("MtrlSkJob batNo : " + param.get("batNo"));
        
        // 실제 배치 실행 후 결과 코드 
        content = "";
        resultCd = "ER";
        transactionHandler.runInTransaction(() -> {
        	try {
        		Map<String, Object> temp = new HashMap<>();
            	// 1. 자재 재고 insert
        		mapper.insertMtrlSk(temp);
        		// 2. 자재 물질 insert
        		mapper.insertSbstSk(temp);
        		// 3. 자재 재고 인터페이스 trn 업데이트
        		mapper.updateMtrlTrnStt(temp);
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
