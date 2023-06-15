package com.kist.portal.common.comm.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.kist.portal.common.login.model.UserInfo;

public interface FileService {
	
	public List<Map<String, ?>> selectFileList(Map<String, String> param) throws Exception;
	
	public String insertFile(MultipartFile[] uploadFile, UserInfo userInfo, String isMsds) throws Exception;
	
	public String updateFile(MultipartFile[] uploadFile, UserInfo userInfo, String updateKey, String isMsds) throws Exception;
	
	public int deleteFile(String id) throws Exception;
	
	public Map<String, Object> downloadFile(Map<String, String> param, String fileKey) throws Exception;

	public Map<String, Object> downloadUrlFile(Map<String, String> param) throws Exception;

	public Map<String, Object> toByteArrayFromUrlFile(String fileKey) throws Exception;
	
	public List<Map<String,String>> selectExtYn(Map<String, String> param) throws Exception;
	
	public String selectFileKey(Map<String, String> param) throws Exception;
	
	public Map<String, Object> downloadImage(Map<String, String> param, String fileKey) throws Exception;
	
	public String selectDocFileNo(Map<String, Object> param);
	
	public List<Map<String,String>> selectPubYn(Map<String, String> param) throws Exception;
	
}
