package com.kist.portal.common.pub.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kist.portal.ch.msds.service.msdsService;
import com.kist.portal.common.comm.service.CommService;
import com.kist.portal.common.comm.service.FileService;
import com.kist.portal.common.login.model.UserInfo;
import com.kist.portal.common.pub.service.PublicService;
import com.kist.portal.common.utils.Constant;
import com.kist.portal.sysconf.manager.SessionManager;

@RestController
@RequestMapping("/api/v1/public")
public class PublicController {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private FileService service;
	
	@Autowired
	private msdsService msdsService;
	
	@Autowired
	private SessionManager sessionManager;
	
	@Autowired
	private CommService commService;
	
	@Autowired
	private PublicService publicService;
	
	@Value("${spring.profiles.active}")
	private String activeProfile;
	
	@Value("${spring.ozreport.viewer}")
	private String reportviewer;
	
	@Value("${spring.ozreport.server}")
	private String reportserver;
	
	@GetMapping(value= "/msdsdownload/{mtrlno}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String mtrlno) throws Exception {
		if("local".equals(activeProfile)) { // 로컬환경에서는 S3 사용할 수 없음.
			return new ResponseEntity<>(null, HttpStatus.OK);
		}
		
		Map<String, String> param = new HashMap<>();
		param.put("mtrl_no", mtrlno);
		String key = msdsService.selectMtrlFileKey(param);
		
		// fileKey
		String[] fileKey = key.split("-");
		param.put("id", fileKey[0]);
		param.put("seq", fileKey[1]);
		
		// 해당 파일 ext 여부 확인
		List<Map<String,String>> yn = service.selectExtYn(param);
		Map<String, Object> result = null;
		if(yn != null && "Y".equalsIgnoreCase(yn.get(0).get("ext_yn"))) {
			param.put("atfl_url", yn.get(0).get("atfl_url"));
			result = service.downloadUrlFile(param);
		}else {
			result = service.downloadFile(param, key);
		}
		
		// result = service.downloadFile(param, key);
		byte[] data = (byte[]) result.get("content");
		String fileName = (String) result.get("fileName");
		ByteArrayResource resource = new ByteArrayResource(data);
		fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
        return ResponseEntity
                .ok()
                .contentLength(data.length)
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; filename=\"" + fileName + "\"")
                .body(resource);
    }
	
	// 단건
	@SuppressWarnings("unchecked")
	@GetMapping(value= "/download/{atflNo}")
    public ResponseEntity<ByteArrayResource> downloadFileWithKey(@PathVariable String atflNo) throws Exception {
		if("local".equals(activeProfile)) { // 로컬환경에서는 S3 사용할 수 없음.
			return new ResponseEntity<>(null, HttpStatus.OK);
		}
		
		Map<String, String> param = new HashMap<>();
		param.put("atfl_no", atflNo);
		String key = service.selectFileKey(param);
		
		// fileKey
		if(key == null) {
			return (ResponseEntity<ByteArrayResource>) ResponseEntity.badRequest();
		} else {
			String[] fileKey = key.split("-");
			param.put("id", fileKey[0]);
			param.put("seq", fileKey[1]);
			
			// 해당 파일 ext 여부 확인
			List<Map<String,String>> yn = service.selectExtYn(param);
			Map<String, Object> result = null;
			if(yn != null && "Y".equalsIgnoreCase(yn.get(0).get("ext_yn"))) {
				param.put("atfl_url", yn.get(0).get("atfl_url"));
				result = service.downloadUrlFile(param);
			}else {
				result = service.downloadFile(param, key);
			}
			
			// result = service.downloadFile(param, key);
			byte[] data = (byte[]) result.get("content");
			String fileName = (String) result.get("fileName");
			ByteArrayResource resource = new ByteArrayResource(data);
			fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
	        return ResponseEntity
	                .ok()
	                .contentLength(data.length)
	                .header("Content-type", "application/octet-stream")
	                .header("Content-disposition", "attachment; filename=\"" + fileName + "\"")
	                .body(resource);
		}
    }
	// 단건
	@SuppressWarnings("unchecked")
	@GetMapping(value= "/downloadSeq/{atflKey}")
    public ResponseEntity<ByteArrayResource> downloadFileWithSeq(@PathVariable String atflKey) throws Exception {
		if("local".equals(activeProfile)) { // 로컬환경에서는 S3 사용할 수 없음.
			return new ResponseEntity<>(null, HttpStatus.OK);
		}
		Map<String, String> param = new HashMap<>();
		
		// fileKey
		if(atflKey == null) {
			return (ResponseEntity<ByteArrayResource>) ResponseEntity.badRequest();
		} else {
			String[] fileKey = atflKey.split("-");
			param.put("id", fileKey[0]);
			param.put("seq", fileKey[1]);
			
			// 해당 파일 ext 여부 확인
			List<Map<String,String>> yn = service.selectExtYn(param);
			Map<String, Object> result = null;
			if(yn != null && "Y".equalsIgnoreCase(yn.get(0).get("ext_yn"))) {
				param.put("atfl_url", yn.get(0).get("atfl_url"));
				result = service.downloadUrlFile(param);
			}else {
				result = service.downloadFile(param, atflKey);
			}
			
			// result = service.downloadFile(param, key);
			byte[] data = (byte[]) result.get("content");
			String fileName = (String) result.get("fileName");
			ByteArrayResource resource = new ByteArrayResource(data);
			fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
	        return ResponseEntity
	                .ok()
	                .contentLength(data.length)
	                .header("Content-type", "application/octet-stream")
	                .header("Content-disposition", "attachment; filename=\"" + fileName + "\"")
	                .body(resource);
		}
    }
	// 리포트 주소 호출
	@GetMapping("/report/gwsgn/{mtrl_no}")
    public ResponseEntity<?>  selectOzReportUrl(@PathVariable String mtrl_no) throws Exception {
		String userLang = "KO";
		mtrl_no = ("\'"+mtrl_no+"\'") ;
		String oz = "";
		oz = "<!DOCTYPE html>" + 
				"<html style=\"height:100%\">" + 
				"<head>" + 
				"<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"/>" + 
				"<script src=\""+reportviewer+"/jquery/jquery-2.0.3.min.js\"></script>" + 
				"<link rel=\"stylesheet\" href=\""+reportviewer+"/jquery/jquery-ui.css\" type=\"text/css\"/>" + 
				"<script src=\""+reportviewer+"/jquery/jquery-ui.min.js\"></script>" + 
				"<link rel=\"stylesheet\" href=\""+reportviewer+"/ui.dynatree.css\" type=\"text/css\"/>" + 
				"<script type=\"text/javascript\" src=\""+reportviewer+"/jquery.dynatree.js\" charset=\"utf-8\"></script>" + 
				"<script type=\"text/javascript\" src=\""+reportviewer+"/OZJSViewer.js\" charset=\"utf-8\"></script>" + 
				"</head>" + 
				"<body style=\"width:98%;height:98%\">" + 
				"<div id=\"OZViewer\" style=\"width:98%;height:98%\"></div>" + 
				"<script type=\"text/javascript\" >" + 
				"	function SetOZParamters_OZViewer(){" + 
				"	  var oz;" + 
				"	  	 oz = document.getElementById(\"OZViewer\");" + 
				"		 oz.sendToActionScript(\"connection.displayname\",\"report\");" + 
				"		 oz.sendToActionScript(\"connection.servlet\",\""+reportserver+"\");" + 
				"		 oz.sendToActionScript(\"connection.reportname\",\"/"+"200_2"+".ozr\");"+
				"		 oz.sendToActionScript(\"connection.pcount\",\"1\");" + 
				"        oz.sendToActionScript(\"odi.odinames\",\"gwsgn\");" + 
				"        oz.sendToActionScript(\"odi.gwsgn.pcount\",\"2\");" + 
				"        oz.sendToActionScript(\"odi.gwsgn.args1\",\"mtrl_no="+mtrl_no+"\");" +
				"        oz.sendToActionScript(\"odi.gwsgn.args2\",\"SESS_LANG="+userLang+"\");" +
				"		 oz.sendToActionScript(\"viewer.showthumbnail\",\"true\");"	+
				"        oz.sendToActionScript(\"viewer.viewmode\",\"fittoframe\");" + 
				"		 oz.sendToActionScript(\"export.filename\", "+"report"+");" + 
				"		return true;" + 
				"	}" + 
				"	start_ozjs(\"OZViewer\",\""+reportviewer+"/\");" + 
				"</script>" + 
				"</body>" + 
				"</html>" + 
				"";

		return new ResponseEntity<>(oz, HttpStatus.OK);
    }
	
	@GetMapping(value= "/aprvcntn")
	@ResponseBody
    public List<Map<String, Object>> selectAponJsonData(@RequestParam("usrId") String usrId, @RequestParam("aprvNo") String aprvNo) throws Exception {
		
		Map<String, String> param = new HashMap<>();
		param.put("usrId", usrId);
		param.put("aprvNo", aprvNo);
		
		List<Map<String, Object>> result = publicService.selectAponJsonData(param);
		
		if(result.size() < 1) {
			Map<String, Object> temp = new HashMap<>();
			temp.put("aprv_no", aprvNo);
			temp.put("aprv_usr_id", "");
			temp.put("aprv_ttl", "");
			temp.put("aprv_cntn", "");
			temp.put("usg_yn", "N");
			result.add(temp);
		}
		
		return result;
    }
	
	@GetMapping("/healthcheck")
    public ResponseEntity<?>  healthcheck() throws Exception {
		
		return new ResponseEntity<>(Constant.SUCCESS, HttpStatus.OK);
    }
	
	@PutMapping("/config")
	public ResponseEntity<?> updateConfig(@RequestBody Map<String, String> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		
		param.put("usr_id", userInfo.getUsrId());
		// 타임존 코드 존재 여부 확인
		String result = "";
		List<Map<String, ?>> t = commService.selectTzCode(param);
		if(t.size() > 0) {
			int n = commService.updateConfig(param);
			result = n > -1 ? Constant.SUCCESS : Constant.FAIL;
		} else {
			result = "TIMEFAIL";
		}
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("/img/{atflKey}")
	public ResponseEntity<byte[]> showImage(@PathVariable String atflKey) throws Exception {
		if("local".equals(activeProfile)) { // 로컬환경에서는 S3 사용할 수 없음.
			return new ResponseEntity<>(null, HttpStatus.OK);
		}
		Map<String, String> param = new HashMap<>();
		
		// fileKey
		if(atflKey == null) {
			return (ResponseEntity<byte[]>) ResponseEntity.badRequest();
		} else {
			String[] fileKey = atflKey.split("-");
			param.put("id", fileKey[0]);
			param.put("seq", fileKey[1]);
			
			// 해당 파일 pub 여부 확인
			List<Map<String,String>> yn = service.selectPubYn(param);
			byte[] data = null;
			
			Map<String, Object> result = null;
			if(yn.size() > 0) {
				result = service.downloadFile(param, atflKey);
				data = (byte[]) result.get("content");
			}
			
			HttpHeaders headers = new HttpHeaders();
			headers.setCacheControl(CacheControl.noCache().getHeaderValue());
			
			ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(data, headers, HttpStatus.OK);
			
	        return responseEntity;
		}
	}
	
	@GetMapping("/versioncheck")
    public ResponseEntity<?>  versioncheck() throws Exception {
		
		Map<String, String> param = new HashMap<>();
		String version = publicService.selectAppVersion(param);
		
		return new ResponseEntity<>(version, HttpStatus.OK);
    }
}
