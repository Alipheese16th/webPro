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
public class UserDeptRoleJob implements Job {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	BatchMapper mapper;
	
	@Autowired
	TransactionHandler transactionHandler;
	
	private String content = "";
	private String resultCd = "";
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("UserDeptRoleJob start, jobKey: " + context.getJobDetail().getKey().getName());
        
        Map<String, String> param = new HashMap<>();
        param.put("batCd", context.getJobDetail().getKey().getName());
        mapper.insertBatch(param); // start
        logger.info("UserDeptRoleJob batNo : " + param.get("batNo"));
        
        // 배치 실행 중 상태 코드 업데이트
        param.put("resultCd", "EX");
        param.put("content", "");
        mapper.updateBatch(param);
        
        // 실제 배치 실행 후 결과 코드 
        content = "";
        resultCd = "ER";
        transactionHandler.runInTransaction(() -> {
	        try {
	        	// 1. 수신 부서 목록 조회
		        List<Map<String, ?>> deptList = mapper.selectUdrDeptList(param);
		        
		        for(int i = 0; i < deptList.size(); i++) {
		        	// 2. 부서 MERGE
		        	mapper.mergeUdrDeptList(deptList.get(i));
		        	// 3. 수신 테이블 처리 결과 update
		        	mapper.updateUdrDeptRsltList(deptList.get(i));
		        }
	        	
		        // 1. 수신 사용자 목록 조회
		        List<Map<String, ?>> userList = mapper.selectUdrusrList(param);
		        
		        for(int i = 0; i < userList.size(); i++) {
		        	// 2. 사용자 MERGE
		        	mapper.mergeUdrusrList(userList.get(i));
		        	// 3. 일반사용자권한 추가 (todo : 정직원만 권한 부여 조건 추가 필요)
		        	mapper.insertUdrauthList(userList.get(i));
		        	mapper.insertUdrauthListAC(userList.get(i));
		        	// 4. 수신 테이블 처리 결과 update
		        	mapper.updateUdrrsltList(userList.get(i));
		        }
		        
		        // 1. 수신 사용자 권한 목록 조회
		        List<Map<String, ?>> authList = mapper.selectUdrAuthList(param);
		        
		        for(int i = 0; i < authList.size(); i++) {
		        	// 2. 사용자 권한 MERGE
		        	mapper.mergeUdrAuthList(authList.get(i));
		        	// 3. 수신 테이블 처리 결과 update
		        	mapper.updateUdrAuthRsltList(authList.get(i));
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
        logger.info("UserDeptRoleJob start, jobKey: " + "BC001");
        
        Map<String, String> param = new HashMap<>();
        param.put("batCd", "BC001");
        mapper.insertBatch(param); // start
        logger.info("UserDeptRoleJob batNo : " + param.get("batNo"));
        
        // 배치 실행 중 상태 코드 업데이트
        param.put("resultCd", "EX");
        param.put("content", "");
        mapper.updateBatch(param);
        
        // 실제 배치 실행 후 결과 코드 
        content = "";
        resultCd = "ER";
        transactionHandler.runInTransaction(() -> {
	        try {
	        	// 1. 수신 부서 목록 조회
		        List<Map<String, ?>> deptList = mapper.selectUdrDeptList(param);
		        
		        for(int i = 0; i < deptList.size(); i++) {
		        	// 2. 부서 MERGE
		        	mapper.mergeUdrDeptList(deptList.get(i));
		        	// 3. 수신 테이블 처리 결과 update
		        	mapper.updateUdrDeptRsltList(deptList.get(i));
		        }
	        	
		        // 1. 수신 사용자 목록 조회
		        List<Map<String, ?>> userList = mapper.selectUdrusrList(param);
		        
		        for(int i = 0; i < userList.size(); i++) {
		        	// 2. 사용자 MERGE
		        	mapper.mergeUdrusrList(userList.get(i));
		        	// 3. 일반사용자권한 추가 (todo : 정직원만 권한 부여 조건 추가 필요)
		        	mapper.insertUdrauthList(userList.get(i));
		        	mapper.insertUdrauthListAC(userList.get(i));
		        	// 4. 수신 테이블 처리 결과 update
		        	mapper.updateUdrrsltList(userList.get(i));
		        }
		        
		        // 1. 수신 사용자 권한 목록 조회
		        List<Map<String, ?>> authList = mapper.selectUdrAuthList(param);
		        
		        for(int i = 0; i < authList.size(); i++) {
		        	// 2. 사용자 권한 MERGE
		        	mapper.mergeUdrAuthList(authList.get(i));
		        	// 3. 수신 테이블 처리 결과 update
		        	mapper.updateUdrAuthRsltList(authList.get(i));
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
