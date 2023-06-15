package com.kist.portal.sh.acdtmgnt.service;

import java.util.List;
import java.util.Map;

public interface AcdtMgntService {
		
	public List<Map<String, ?>> selectAcdtMgntList(Map<String, String> param);
	
	public Map<String, ?> selectAcdtMgnt(Map<String, Object> param);
	
	public List<Map<String, ?>> selectAcdtReport(Map<String, Object> param);	//사고속보리포트조회

	public List<Map<String, ?>> selectAcdtRpUnBhc(Map<String, Object> param);	//사고속보리포트 3. 사고발생 원인(SHAMSD_사고불안전한행동내용 상위 4개 조회)
	
	public List<Map<String, ?>> selectAcdtRpUnStCn(Map<String, Object> param);	//사고속보리포트 3. 사고발생 원인(SHAMSD_사고불안전상태 상위 4개 조회)
	
	public int insertAcdtMgnt(Map<String, Object> param);
	
	public int updateAcdtMgnt(Map<String, Object> param);
	
	public int updateAppr(Map<String, Object> param);
	
	public int deleteAcdtMgnt(Map<String, Object> param);

	public String selectAcdtMgntId(Map<String, String> param);

	public List<Map<String, ?>> selectAcdtMgntAprvDtl(Map<String, Object> param);

	public int updateAcdtMgntAprvAf(Map<String, Object> param);

	public int delAcdt(Map<String, String> param);
	
	// 사고속보 수신처 조회
	public List<Map<String, ?>> selectAcdtRecvList(Map<String, Object> param);

	public List<Map<String, ?>> selectDeptMental(Map<String, String> param);	// 메일 부서 전체 조회
	
	public int updateAcdtMailSendingStatus(Map<String, Object> param);	// 사고이메일발송수정

}
	
