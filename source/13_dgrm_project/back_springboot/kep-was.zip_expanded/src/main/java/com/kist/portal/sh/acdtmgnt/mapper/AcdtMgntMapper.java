package com.kist.portal.sh.acdtmgnt.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AcdtMgntMapper {
	
	public List<Map<String, ?>> selectAcdtMgntList(Map<String, String> param);
	
	public Map<String, Object> selectAcdtMgnt(Map<String, Object> param);
	
	public List<Map<String, ?>> selectAcdtReport(Map<String, Object> param);	//사고속보리포트조회
	
	public List<Map<String, ?>> selectAcdtRpUnBhc(Map<String, Object> param);	//사고속보리포트 3. 사고발생 원인(SHAMSD_사고불안전한행동내용 상위 4개 조회)
	
	public List<Map<String, ?>> selectAcdtRpUnStCn(Map<String, Object> param);	//사고속보리포트 3. 사고발생 원인(SHAMSD_사고불안전상태 상위 4개 조회)
	
	public List<Map<String, ?>> selectAcdtOprn(Map<String, Object> param);
	
	public List<Map<String, ?>> selectAcdtUsftTxt(Map<String, Object> param);
	
	public List<Map<String, ?>> selectAcdtUsftSt(Map<String, Object> param);
	
	public List<Map<String, ?>> selectAcdtDfdt(Map<String, Object> param);
	
	public List<Map<String, ?>> selectAcdtIvstRslt(Map<String, Object> param);
	
	public List<Map<String, ?>> selectAcdtActnPlan(Map<String, Object> param);
	
	public List<Map<String, ?>> selectAcdtAtfl1(Map<String, Object> param);

	public List<Map<String, ?>> selectAcdtAtfl2(Map<String, Object> param);
	
	public List<Map<String, ?>> selectAcdtOccurAtfl(Map<String, Object> param);	// 사고발생현장사진 데이터
	
	public List<Map<String, ?>> selectAcdtReenactAtfl(Map<String, Object> param);	// 사고재현사진 데이터
	
	public int insertAcdtMgnt(Map<String, Object> param);
		
	public int insertAcdtOprn(Map<String, String> param);
	
	public int insertAcdtUsftTxt(Map<String, String> param);
	
	public int insertAcdtUsftSt(Map<String, String> param);
	
	public int insertAcdtDfdt(Map<String, String> param);
	
	public int insertAcdtIvstRslt(Map<String, String> param);
	
	public int insertAcdtActnPlan(Map<String, String> param);

	public int updateAcdtActnPlan(Map<String, String> param);
	
	public int insertAcdtAtfl1(Map<String, String> param);
	
	public int insertAcdtAtfl2(Map<String, String> param);
	
	public int insertAcdtPhtscOrRppht(Map<String, String> param);	//사고발생현장사진/사고재현사진 등록
	
	public int updateAcdtMgnt(Map<String, Object> param);
	
	public int updateAppr(Map<String, Object> param);
	
	public int deleteAcdtOprn(Map<String, Object> param);
	
	public int deleteAcdtUsftTxt(Map<String, Object> param);
	
	public int deleteAcdtUsftSt(Map<String, Object> param);
	
	public int deleteAcdtDfdt(Map<String, Object> param);
	
	public int deleteAcdtIvstRslt(Map<String, Object> param);
	
	public int deleteAcdtActnPlan(Map<String, Object> param);
	
	public int deleteAcdtAtfl1(Map<String, Object> param);
	
	public int deleteAcdtAtfl2(Map<String, Object> param);
	
	public int deleteAcdtPhtscOrRppht(Map<String, Object> param);	//사고발생현장사진/사고재현사진
	
	public int deleteAcdtMgnt(Map<String, Object> param);
	
	public String selectAcdtMgntId(Map<String, String> param);

	public List<Map<String, ?>> selectAcdtMgntAprvDtl(Map<String, Object> param);

	public int updateAcdtMgntAprvAf(Map<String, Object> param);

	public int delAcdt(Map<String, String> param);
	
	// 사고속보 메일 수신처 조회
	public List<Map<String, ?>> selectAcdtRecvList(Map<String, Object> param);

	public List<Map<String, ?>> selectDeptMental(Map<String, String> param);	// 메일 부서 전체 조회
	
	public int updateAcdtMailSendingStatus(Map<String, Object> param);	// 사고이메일발송수정

}
