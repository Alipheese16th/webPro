package com.kist.portal.common.comm.rest;

import java.io.FileNotFoundException;
import java.nio.file.NoSuchFileException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.kist.portal.common.comm.service.FileService;
import com.kist.portal.common.login.model.UserInfo;
import com.kist.portal.common.utils.Constant;
import com.kist.portal.sysconf.manager.SessionManager;

@RestController
@RequestMapping("/api/v1/common")
public class FileController {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SessionManager sessionManager;
	
	@Autowired
	private FileService service;
	
	@Value("${spring.profiles.active}")
	private String activeProfile;
	
	@GetMapping("/file/{id}")
    public ResponseEntity<?> selectFileList(@PathVariable String id) throws Exception {
		String[] fileKey = id.split("-");
		Map<String, String> param = new HashMap<>();
		param.put("id", fileKey[0]);
		List<Map<String, ?>> list = service.selectFileList(param);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
    }
	
	@PostMapping("/file")
	public ResponseEntity<?> insertFile(@RequestParam("file") MultipartFile[] uploadFiles, @RequestParam("updateKey") String updateKey, @RequestParam("isMsds") String isMsds, HttpSession session) throws Exception {
		UserInfo userInfo = sessionManager.get(session.getId());
		String fileId = "";
		
		if("0".equals(updateKey)) {
			fileId = service.insertFile(uploadFiles, userInfo, isMsds);
		} else {
			fileId = service.updateFile(uploadFiles, userInfo, updateKey, isMsds);
		}
		
		logger.info("############### uploadFile cnt : {}, updateKey is {}", uploadFiles.length, updateKey);
		return new ResponseEntity<>(fileId, HttpStatus.OK);
	}
	
	@DeleteMapping("/file/{id}")
    public ResponseEntity<?> deleteFile(@PathVariable String id) throws Exception {
		int n = service.deleteFile(id);
		String result = n > -1? Constant.SUCCESS : Constant.FAIL;
		return new ResponseEntity<>(result, HttpStatus.OK);
    }
	
	@GetMapping(value= "/file-down/{id}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String id) throws Exception {
		String[] fileKey = id.split("-");
		Map<String, String> param = new HashMap<>();
		param.put("id", fileKey[0]);
		param.put("seq", fileKey[1]);
		
		// 해당 파일 ext 여부 확인
		List<Map<String,String>> yn = service.selectExtYn(param);
		Map<String, Object> result = null;
		if(yn != null && "Y".equalsIgnoreCase(yn.get(0).get("ext_yn"))) {
			param.put("atfl_url", yn.get(0).get("atfl_url"));
			result = service.downloadUrlFile(param);
		} else {
			if("Y".equalsIgnoreCase(yn.get(0).get("equal_yn"))){
				result = service.downloadFile(param, yn.get(0).get("id"));
			} else {
				result = service.downloadFile(param, id);
			}
		}
		byte[] data = (byte[]) result.get("content");
		String fileName = (String) result.get("fileName");
		ByteArrayResource resource = new ByteArrayResource(data);
        return ResponseEntity
                .ok()
                .contentLength(data.length)
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; filename=\"" + fileName + "\"")
                .body(resource);
    }
	
	@PostMapping("/file-check")
	public ResponseEntity<?> checkFile(@RequestParam("file") MultipartFile[] uploadFiles, HttpSession session) throws Exception {
		String[] exts = {"docx", "doc", "xlsx", "xls", "pptx", "ppt", "hwp", "pdf", "mp4", "txt", "jpg", "jpeg", "png", "gif", "bmp", "tiff", "zip", "alz"};
		for(int i=0; i < uploadFiles.length; i++) {
			System.out.println(uploadFiles[i].getOriginalFilename());
			int pos = uploadFiles[i].getOriginalFilename().lastIndexOf(".");
			String ext = uploadFiles[i].getOriginalFilename().substring(pos+1);
			if(!Arrays.asList(exts).contains(ext.toLowerCase())) {
				return new ResponseEntity<>("EXTFAIL", HttpStatus.OK);
			}
		}
		return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
	}
	
	@GetMapping("/image/{id}")
    public ResponseEntity<ByteArrayResource> selectImageList(@PathVariable String id) throws Exception {
		String[] fileKey = id.split("-");
		Map<String, String> param = new HashMap<>();
		param.put("id", fileKey[0]);
		param.put("seq", fileKey[1]);
		Map<String, Object> result = service.downloadImage(param, id);
		byte[] data = (byte[]) result.get("content");
		String fileName = (String) result.get("fileName");
		ByteArrayResource resource = new ByteArrayResource(data);
		
		return ResponseEntity
                .ok()
                .contentLength(data.length)
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "inline; filename=\"" + fileName + "\"")
                .body(resource);
    }
	
	@GetMapping("/arch-file")
    public ResponseEntity<?> selectDocFileList(@RequestParam Map<String, Object> param, HttpSession session) throws Exception {
		
		UserInfo userInfo = sessionManager.get(session.getId());

		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());
		
		String atFileNo = service.selectDocFileNo(param);
		
		return new ResponseEntity<>(atFileNo, HttpStatus.OK);
    }

}
