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
public class ShcosmHolyJob implements Job {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	BatchMapper mapper;

	@Autowired
	TransactionHandler transactionHandler;

	private String content = "";
	private String resultCd = "";

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		logger.info("ShcosmHolyJob start, jobKey: " + context.getJobDetail().getKey().getName());

		Map<String, String> param = new HashMap<>();
		param.put("batCd", context.getJobDetail().getKey().getName());
		mapper.insertBatch(param); // start
		logger.info("ShcosmHolyJob batNo : " + param.get("batNo"));

		// 배치 실행 중 상태 코드 업데이트
		param.put("resultCd", "EX");
		param.put("content", "");
		mapper.updateBatch(param);

		// 실제 배치 실행 후 결과 코드
		content = "";
		resultCd = "ER";
		transactionHandler.runInTransaction(() -> {
			try {
	        	// 1. 휴일 목록 조회
		        List<Map<String, ?>> holyList = mapper.selectHolyList(param);
		        
		        for(int i = 0; i < holyList.size(); i++) {
		        	// 2. 휴일 데이터 MERGE
		        	mapper.updateHolyList(holyList.get(i));
		        	// 3. 휴일 테이블 처리 결과 update
		        	mapper.mergeHolyList(holyList.get(i));
		        }
		        resultCd = "SU";
			} catch (Exception e) {
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
        logger.info("ShcosmHolyJob start, jobKey: " + "BS006");
        
        Map<String, String> param = new HashMap<>();
        param.put("batCd", "BS006");
        mapper.insertBatch(param); // start
        logger.info("ShcosmHolyJob batNo : " + param.get("batNo"));
        
        // 배치 실행 중 상태 코드 업데이트
        param.put("resultCd", "EX");
        param.put("content", "");
        mapper.updateBatch(param);
        
        // 실제 배치 실행 후 결과 코드 
        content = "";
        resultCd = "ER";
        transactionHandler.runInTransaction(() -> {
	        try {
	        	// 1. 휴일 목록 조회
		        List<Map<String, ?>> holyList = mapper.selectHolyList(param);
		        
		        for(int i = 0; i < holyList.size(); i++) {
		        	// 2. 휴일 데이터 MERGE
		        	mapper.updateHolyList(holyList.get(i));
		        	// 3. 휴일 테이블 처리 결과 update
		        	mapper.mergeHolyList(holyList.get(i));
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
