package com.kist.portal.common.batch;

import java.lang.reflect.Method;
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

import com.kist.portal.common.aprv.rest.AprvController;
import com.kist.portal.common.batch.mapper.BatchMapper;
import com.kist.portal.common.utils.TransactionHandler;

@Component
public class AprvRevJob implements Job {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	BatchMapper mapper;
	
	@Autowired
	AprvController aprv;
	
	@Autowired
	TransactionHandler transactionHandler;
	
	private String content = "";
	private String resultCd = "";
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("AprvRevJob start, jobKey: " + context.getJobDetail().getKey().getName());
        
        Map<String, String> param = new HashMap<>();
        param.put("batCd", context.getJobDetail().getKey().getName());
        mapper.insertBatch(param); // start
        logger.info("AprvRevJob batNo : " + param.get("batNo"));
        
        // 배치 실행 중 상태 코드 업데이트
        param.put("resultCd", "EX");
        param.put("content", "");
        mapper.updateBatch(param);
        
        // 실제 배치 실행 후 결과 코드 
        content = "";
        resultCd = "ER";
        transactionHandler.runInTransaction(() -> {
	        try {
	        	//1. 인터페이스 결제 승인 apon 정보 가져오기
	        	List<Map<String, Object>> aponList = mapper.selectAponAprvList(param);
	        	for(int i = 0; i < aponList.size(); i++) {
	        		Map<String, Object> temp = new HashMap<>();
	        		//2-1. 가져온 데이터에 해당하는 aprv 테이블의 후행 메서드 실행(상태코드가 N이 아닌 것)        		
	        		temp.put("aprvseq", aponList.get(i).get("aprvseq"));
	        		temp.put("aprv_no", aponList.get(i).get("reqno"));
	        		temp.put("docst", aponList.get(i).get("docst"));
	        		String methodName = mapper.selectAprvMethod(temp);
	        		if(methodName == null || methodName.isEmpty()) {
	        			// 후행 메서드가 존재하지 않을경우 익셉션
	        			resultCd = "ER";
	    	        	content = "잘못된 요청입니다.";
	        			return;
	        		}
	        		
	        		Method method = aprv.getClass().getMethod(methodName, String.class, String.class);
	        		method.invoke(aprv, aponList.get(i).get("reqno"), aponList.get(i).get("docst"));
	        		
	        		//2-2. 후행 메서드 실행 후 각 테이블 상태값 업데이트
	        		mapper.updateAprvStt(temp);
	        		mapper.updateAprvRcvStt(temp);
	        	}
	        	//3. 30분 지난 T 상태의 aprv 데이터 사용여부 N으로 전환
	        	List<Map<String, String>> delList = mapper.selectAponDelList(param);
	        	for(int j = 0; j < delList.size(); j++) {
	        		Map<String, Object> temp = new HashMap<>();
	        		temp.put("aprv_no", delList.get(j).get("aprv_no"));
	        		param.put("aprv_no", delList.get(j).get("aprv_no"));
	        		String methodName = mapper.selectAprvMethod(temp);
	        		if(methodName == null || methodName.isEmpty()) {
	        			resultCd = "ER";
	    	        	content = "잘못된 요청입니다.";
	        			return;
	        		}
	        		Method method = aprv.getClass().getMethod(methodName, String.class, String.class);
	        		// 후행 메서드에 C 넘겨줌
	        		method.invoke(aprv, delList.get(j).get("aprv_no"), "C");
	        		//4. 해당 결제 요청 삭제
	        		mapper.deleteAprvData(param);
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
        logger.info("AprvRevJob start, jobKey: " + "BA001");
        
        Map<String, String> param = new HashMap<>();
        param.put("batCd", "BA001");
        mapper.insertBatch(param); // start
        logger.info("AprvRevJob batNo : " + param.get("batNo"));
        
        // 배치 실행 중 상태 코드 업데이트
        param.put("resultCd", "EX");
        param.put("content", "");
        mapper.updateBatch(param);
        
        // 실제 배치 실행 후 결과 코드 
        content = "";
        resultCd = "ER";
        transactionHandler.runInTransaction(() -> {
	        try {
	        	//1. 인터페이스 결제 승인 apon 정보 가져오기
	        	List<Map<String, Object>> aponList = mapper.selectAponAprvList(param);
	        	for(int i = 0; i < aponList.size(); i++) {
	        		Map<String, Object> temp = new HashMap<>();
	        		//2-1. 가져온 데이터에 해당하는 aprv 테이블의 후행 메서드 실행(상태코드가 N이 아닌 것)        		
	        		temp.put("aprvseq", aponList.get(i).get("aprvseq"));
	        		temp.put("aprv_no", aponList.get(i).get("reqno"));
	        		temp.put("docst", aponList.get(i).get("docst"));
	        		String methodName = mapper.selectAprvMethod(temp);
	        		if(methodName == null || methodName.isEmpty()) {
	        			// 후행 메서드가 존재하지 않을경우 익셉션
	        			resultCd = "ER";
	    	        	content = "잘못된 요청입니다.";
	        			return;
	        		}
	        		
	        		Method method = aprv.getClass().getMethod(methodName, String.class, String.class);
	        		method.invoke(aprv, aponList.get(i).get("reqno"), aponList.get(i).get("docst"));
	        		
	        		//2-2. 후행 메서드 실행 후 각 테이블 상태값 업데이트
	        		mapper.updateAprvStt(temp);
	        		mapper.updateAprvRcvStt(temp);
	        	}
	        	//3. 30분 지난 T 상태의 aprv 데이터 사용여부 N으로 전환
	        	List<Map<String, String>> delList = mapper.selectAponDelList(param);
	        	for(int j = 0; j < delList.size(); j++) {
	        		Map<String, Object> temp = new HashMap<>();
	        		temp.put("aprv_no", delList.get(j).get("aprv_no"));
	        		param.put("aprv_no", delList.get(j).get("aprv_no"));
	        		String methodName = mapper.selectAprvMethod(temp);
	        		if(methodName == null || methodName.isEmpty()) {
	        			resultCd = "ER";
	    	        	content = "잘못된 요청입니다.";
	        			return;
	        		}
//	        		Method method = aprv.getClass().getMethod(methodName, String.class, String.class);
//	        		// 후행 메서드에 C 넘겨줌
//	        		method.invoke(aprv, delList.get(j).get("aprv_no"), "C");
	        		//4. 해당 결제 요청 삭제
	        		mapper.deleteAprvData(param);
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
