package com.kist.portal.sh.acdtmgnt.service;

import java.util.List;
import java.util.Map;

public interface AcdtRecvMgntService {

	// 기본정보 조회
	public List<Map<String, Object>> selectAcdtRecvMgntBasic(Map<String, String> param);

	// 수신처 부서 목록조회
	public List<Map<String, Object>> selectAcdtRecvMgntRcvDeptList(Map<String, String> param);

	// 수신처 인원 목록조회
	public List<Map<String, Object>> selectAcdtRecvMgntRcvEmpList(Map<String, String> param);

	// 참조처 부서 목록조회
	public List<Map<String, Object>> selectAcdtRecvMgntRefDeptList(Map<String, String> param);

	// 참조처 부서 목록조회
	public List<Map<String, Object>> selectAcdtRecvMgntRefEmpList(Map<String, String> param);

	public int insertAcdtRcv(Map<String, Object> param);

	public int deleteAcdtRcv(Map<String, Object> param);
}
