package com.kist.portal.common.comm.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FileMapper {
	
	public String selectFileId();
	
	public String selectSeq(String id);
	
	public List<Map<String, ?>> selectFileList(Map<String, String> param);
	
	public int deleteFile(Map<String, String> param);
	
	public Map<String, ?> selectFile(Map<String, String> param);
	
	public int insertFile(Map<String, String> param);
	
	public List<Map<String,String>> selectExtYn(Map<String, String> param);
	
	public String selectFileKey(Map<String, String> param);
	
	public String selectDocFileNo(Map<String, Object> param);
	
	public List<Map<String,String>> selectPubYn(Map<String, String> param) throws Exception;
	
}
