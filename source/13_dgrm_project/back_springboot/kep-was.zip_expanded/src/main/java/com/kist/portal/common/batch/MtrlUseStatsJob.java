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

@Component
public class MtrlUseStatsJob implements Job {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	BatchMapper mapper;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("MtrlUseStatsJob start, jobKey: " + context.getJobDetail().getKey().getName());
        
        Map<String, String> param = new HashMap<>();
        param.put("batCd", context.getJobDetail().getKey().getName());
        mapper.insertBatch(param); // start
        logger.info("MtrlUseStatsJob batNo : " + param.get("batNo"));
        
        // 배치 실행 중 상태 코드 업데이트
        param.put("resultCd", "EX");
        param.put("content", "");
        mapper.updateBatch(param);
        
        // 실제 배치 실행 후 결과 코드 
//        String content = "";
//        List<Map<String, String>> result = mapper.selectMtrlUseStatsJob(param);
//        String resultCd = result.get(0).get("v_result");
//        if(resultCd.equalsIgnoreCase("SU")) {
//        	content = "";
//        } else {
//        	content = result.get(0).get("v_error");
//        }
//        
//        param.put("resultCd", resultCd);
//        param.put("content", content);
//        mapper.updateBatch(param); // end
    }
}
