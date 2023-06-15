package com.kist.portal.common.batch;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

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
import com.kist.portal.common.utils.TransactionHandler;

@Component
public class ExeHistJob implements Job {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	BatchMapper mapper;
	
	@Value("${dgrm.front-url}")
	private String frontUrl;
	
	@Autowired
	TransactionHandler transactionHandler;
	
	private String content = "";
	private String resultCd = "";
	
	@Value("${spring.profiles.active}")
	private String activeProfile;
	
	@PostConstruct
	public void init() {
		//s3Client = AmazonS3ClientBuilder.standard().withRegion(Regions.AP_NORTHEAST_2).build();
	}
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("ExeHistJob start, jobKey: " + context.getJobDetail().getKey().getName());
        
        Map<String, String> param = new HashMap<>();
        param.put("batCd", context.getJobDetail().getKey().getName());
        mapper.insertBatch(param); // start
        logger.info("ExeHistJob batNo : " + param.get("batNo"));
        
        // 배치 실행 중 상태 코드 업데이트
        param.put("resultCd", "EX");
        param.put("content", "");
        mapper.updateBatch(param);
        
        // 실제 배치 실행 후 결과 코드 
        content = "";
        resultCd = "ER";
        transactionHandler.runInTransaction(() -> {
	        try {
	        	saveExeHist();
	        	// delUserInfo();
	        	
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
        logger.info("ExeHistJob start, jobKey: " + "BS004");
        
        Map<String, String> param = new HashMap<>();
        param.put("batCd", "BS004");
        mapper.insertBatch(param); // start
        logger.info("ExeHistJob batNo : " + param.get("batNo"));
        
        // 배치 실행 중 상태 코드 업데이트
        param.put("resultCd", "EX");
        param.put("content", "");
        mapper.updateBatch(param);
        
        // 실제 배치 실행 후 결과 코드 
        content = "";
        resultCd = "ER";
        transactionHandler.runInTransaction(() -> {
	        try {
	        	saveExeHist();
	        	// delUserInfo();
	        	
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
	public void delUserInfo() throws Exception {	
		try {
			Map<String, String> param = new HashMap<>();
			// 사업장구분이 SCM,생산자회사,연구소인 데이터 삭제(건강검진년도 30년초과)
			mapper.deleteHmexRsltDataT(param);
			mapper.deleteHmexTrgpDataT(param);
			// 사업장구분이 SCM,생산자회사,연구사가 아닌 데이터 삭제(건강검진년도 5년초과)
			mapper.deleteHmexRsltDataF(param);
			mapper.deleteHmexTrgpDataF(param);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Transactional
	public boolean saveExeHist() throws Exception {	
		int resultCount = 0;
		File file = null;
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
			Date dt = new Date();
			String dtT = formatter.format(dt);

			String filepath = "/data/was/logs";
			if ("local".equals(activeProfile)) { // 로컬환경에서
				filepath = "C:/DEHS";
			}
			
			String title = "exeinfo-log" + dtT;
			String makePath = filepath + File.separator + title;
			
			file = new File(makePath + ".csv");
			
			BufferedWriter fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "MS949"));
			
			Map<String, Object> temp = new HashMap<>();
			List<Map<String, String>> list = mapper.selectExeHistBatch(temp);
			fw.write("Client IP,Server IP,User ID,Exe Date,Service Name,URL Info,Return URL,Method Type,Input Param,Result Count,Privacy Item,Result Item");
			fw.newLine();
			for (int i = 0; i < list.size(); i++) {
				Map<String, String> listMap = list.get(i);
				String printVal = "";
				printVal = listMap.get("ip_addr") + "," + listMap.get("server_ip_addr") + "," + listMap.get("usr_id") + "," + listMap.get("exe_dt") + "," + listMap.get("api_expl") + "," + listMap.get("api_url") + ","
						 + listMap.get("return_url") + "," + listMap.get("mthd_type_nm") + "," + listMap.get("input_param") + "," + listMap.get("result_cnt") + "," + listMap.get("privacy_item") + "," + listMap.get("result_item");
				fw.write(printVal);
				fw.newLine();
				resultCount++;
			}
			
			fw.flush();
			fw.close();
			
//			FileItem fileItem = new DiskFileItem("originFile", Files.probeContentType(file.toPath()), false, file.getName(), (int) file.length() , file.getParentFile());
//			InputStream input = new FileInputStream(file);
//			OutputStream os = fileItem.getOutputStream();
//			IOUtils.copy(input, os);
//			MultipartFile multipartFile = new CommonsMultipartFile(fileItem);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			// 파일 삭제 로직 확인필요
//			if (file.exists()) {
//				file.delete();
//			}
		}
		boolean chk = false;
		if (resultCount > 0) {
			chk = true;
		}
		return chk; 
	}
}
