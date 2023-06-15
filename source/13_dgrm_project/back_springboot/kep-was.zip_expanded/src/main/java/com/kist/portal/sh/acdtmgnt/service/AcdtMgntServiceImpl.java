package com.kist.portal.sh.acdtmgnt.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kist.portal.sh.acdtmgnt.mapper.AcdtMgntMapper;

@Service
public class AcdtMgntServiceImpl implements AcdtMgntService {
	
	@Autowired(required=true)
	AcdtMgntMapper mapper;

	@Override
	public List<Map<String, ?>> selectAcdtMgntList(Map<String, String> param) {
		return mapper.selectAcdtMgntList(param);
	}
	
	@Override
	public Map<String, ?> selectAcdtMgnt(Map<String, Object> param) {
		Map<String, Object> map = mapper.selectAcdtMgnt(param);	// 사고 기본정보 조회
		map.put("oprn_list", mapper.selectAcdtOprn(param));	// SHAMSD_사고작업내용
		map.put("usfttxt_list", mapper.selectAcdtUsftTxt(param));	// SHAMSD_사고불안전내용
		map.put("usftst_list", mapper.selectAcdtUsftSt(param));	// SHAMSD_사고불안전상태
		map.put("dfdt_list", mapper.selectAcdtDfdt(param));	// SHAMSM_사고자
		map.put("ivst_list", mapper.selectAcdtIvstRslt(param));	// SHAMSD_사고조사결과
		map.put("actn_list", mapper.selectAcdtActnPlan(param));	// SHAMSD_사고조치계획
		map.put("atfl_list1", mapper.selectAcdtAtfl1(param));	// 첨부파일정보
		map.put("atfl_list2", mapper.selectAcdtAtfl2(param));	// 개선결과 첨부파일정보
		map.put("occur_atfl_list", mapper.selectAcdtOccurAtfl(param));	//사고발생현장사진 조회
		map.put("reenact_atfl_list", mapper.selectAcdtReenactAtfl(param));	//사고재현사진 조회
		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int insertAcdtMgnt(Map<String, Object> param) {
		int n = mapper.insertAcdtMgnt(param);
		
		List<Map<String, String>> subList = (List<Map<String, String>>) param.get("oprn_list");
		for(Map<String, String> sub : subList) {
			sub.put("acdt_id", (String) param.get("acdt_id"));
			sub.put("SESS_USR_ID", (String) param.get("SESS_USR_ID"));
			mapper.insertAcdtOprn(sub);
		}
		
		subList = (List<Map<String, String>>) param.get("usfttxt_list");
		for(Map<String, String> sub : subList) {
			sub.put("acdt_id", (String) param.get("acdt_id"));
			sub.put("SESS_USR_ID", (String) param.get("SESS_USR_ID"));
			mapper.insertAcdtUsftTxt(sub);
		}
		
		subList = (List<Map<String, String>>) param.get("usftst_list");
		for(Map<String, String> sub : subList) {
			sub.put("acdt_id", (String) param.get("acdt_id"));
			sub.put("SESS_USR_ID", (String) param.get("SESS_USR_ID"));
			mapper.insertAcdtUsftSt(sub);
		}
		
		int i = 1;
		subList = (List<Map<String, String>>) param.get("dfdt_list");
		for(Map<String, String> sub : subList) {
			sub.put("acdt_id", (String) param.get("acdt_id"));
			sub.put("dfdt_sn", String.valueOf(i++));
			sub.put("SESS_USR_ID", (String) param.get("SESS_USR_ID"));
			mapper.insertAcdtDfdt(sub);
		}
		
		i = 1;
		subList = (List<Map<String, String>>) param.get("ivst_list");
		for(Map<String, String> sub : subList) {
			sub.put("acdt_id", (String) param.get("acdt_id"));
			sub.put("acdt_ivst_sn", String.valueOf(i++));
			sub.put("SESS_USR_ID", (String) param.get("SESS_USR_ID"));
			mapper.insertAcdtIvstRslt(sub);
		}
		// SHAMSD_사고조치계획
		i = 1;
		subList = (List<Map<String, String>>) param.get("actn_list");
		for(Map<String, String> sub : subList) {
			sub.put("acdt_id", (String) param.get("acdt_id"));
			sub.put("acdt_actn_plan_sn", String.valueOf(i++));
			sub.put("SESS_USR_ID", (String) param.get("SESS_USR_ID"));
			mapper.insertAcdtActnPlan(sub);
		}
		
		subList = (List<Map<String, String>>) param.get("atfl_list1");
		for(Map<String, String> sub : subList) {
			sub.put("acdt_id", (String) param.get("acdt_id"));
			sub.put("SESS_USR_ID", (String) param.get("SESS_USR_ID"));
			mapper.insertAcdtAtfl1(sub);
		}
		
		subList = (List<Map<String, String>>) param.get("atfl_list2");
		for(Map<String, String> sub : subList) {
			sub.put("acdt_id", (String) param.get("acdt_id"));
			sub.put("SESS_USR_ID", (String) param.get("SESS_USR_ID"));
			mapper.insertAcdtAtfl2(sub);
		}
		
		//사고발생현장 사진
		subList = (List<Map<String, String>>) param.get("occur_atfl_list");
		for(Map<String, String> sub : subList) {
			
			sub.put("acdt_id", (String) param.get("acdt_id"));
			sub.put("SESS_USR_ID", (String) param.get("SESS_USR_ID"));		
			
			System.out.println("-------->" + sub);
			mapper.insertAcdtPhtscOrRppht(sub);	
		}
		
		//사고재현 사진
		subList = (List<Map<String, String>>) param.get("reenact_atfl_list");
		for(Map<String, String> sub : subList) {
			
			sub.put("acdt_id", (String) param.get("acdt_id"));
			sub.put("SESS_USR_ID", (String) param.get("SESS_USR_ID"));		
			
			System.out.println("-------->" + sub);
			mapper.insertAcdtPhtscOrRppht(sub);	
		}
		
		return n;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int updateAcdtMgnt(Map<String, Object> param) {
		int n = mapper.updateAcdtMgnt(param);
		
		mapper.deleteAcdtOprn(param);
		mapper.deleteAcdtUsftTxt(param);
		mapper.deleteAcdtUsftSt(param);
		mapper.deleteAcdtDfdt(param);
		mapper.deleteAcdtIvstRslt(param);
		mapper.deleteAcdtActnPlan(param);
		mapper.deleteAcdtAtfl1(param);
		mapper.deleteAcdtAtfl2(param);
		mapper.deleteAcdtPhtscOrRppht(param);	//사고발생현장사진/사고재현사진 삭제
		
		List<Map<String, String>> subList = (List<Map<String, String>>) param.get("oprn_list");
		for(Map<String, String> sub : subList) {
			sub.put("acdt_id", (String) param.get("acdt_id"));
			sub.put("SESS_USR_ID", (String) param.get("SESS_USR_ID"));
			mapper.insertAcdtOprn(sub);
		}
		
		subList = (List<Map<String, String>>) param.get("usfttxt_list");
		for(Map<String, String> sub : subList) {
			sub.put("acdt_id", (String) param.get("acdt_id"));
			sub.put("SESS_USR_ID", (String) param.get("SESS_USR_ID"));
			mapper.insertAcdtUsftTxt(sub);
		}
		
		subList = (List<Map<String, String>>) param.get("usftst_list");
		for(Map<String, String> sub : subList) {
			sub.put("acdt_id", (String) param.get("acdt_id"));
			sub.put("SESS_USR_ID", (String) param.get("SESS_USR_ID"));
			mapper.insertAcdtUsftSt(sub);
		}
		
		int i = 1;
		subList = (List<Map<String, String>>) param.get("dfdt_list");
		for(Map<String, String> sub : subList) {
			sub.put("acdt_id", (String) param.get("acdt_id"));
			sub.put("dfdt_sn", String.valueOf(i++));
			sub.put("SESS_USR_ID", (String) param.get("SESS_USR_ID"));
			mapper.insertAcdtDfdt(sub);
		}
		
		i = 1;
		subList = (List<Map<String, String>>) param.get("ivst_list");
		for(Map<String, String> sub : subList) {
			sub.put("acdt_id", (String) param.get("acdt_id"));
			sub.put("acdt_ivst_sn", String.valueOf(i++));
			sub.put("SESS_USR_ID", (String) param.get("SESS_USR_ID"));
			mapper.insertAcdtIvstRslt(sub);
		}		
		// 사고 조치 계획
		i = 1;
		subList = (List<Map<String, String>>) param.get("actn_list");
		for(Map<String, String> sub : subList) {			
			sub.put("acdt_id", (String) param.get("acdt_id"));			
			sub.put("acdt_actn_plan_sn", String.valueOf(i++));
			sub.put("SESS_USR_ID", (String) param.get("SESS_USR_ID"));
			mapper.insertAcdtActnPlan(sub);
		}
		
		// 재발방지대책 이행결과
		i = 1;
		subList = (List<Map<String, String>>) param.get("actn_list2");
		for(Map<String, String> sub : subList) {
			sub.put("acdt_id", (String) param.get("acdt_id"));
			sub.put("acdt_actn_plan_sn", String.valueOf(i++));
			sub.put("SESS_USR_ID", (String) param.get("SESS_USR_ID"));
			//mapper.insertAcdtActnPlan(sub);
			mapper.updateAcdtActnPlan(sub);
		}
		
		subList = (List<Map<String, String>>) param.get("atfl_list1");
		for(Map<String, String> sub : subList) {
			sub.put("acdt_id", (String) param.get("acdt_id"));
			sub.put("SESS_USR_ID", (String) param.get("SESS_USR_ID"));
			mapper.insertAcdtAtfl1(sub);
		}
		
		subList = (List<Map<String, String>>) param.get("atfl_list2");
		for(Map<String, String> sub : subList) {
			sub.put("acdt_id", (String) param.get("acdt_id"));
			sub.put("SESS_USR_ID", (String) param.get("SESS_USR_ID"));
			mapper.insertAcdtAtfl2(sub);
		}
		
		//사고발생현장 사진
		subList = (List<Map<String, String>>) param.get("occur_atfl_list");
		for(Map<String, String> sub : subList) {
			
			sub.put("acdt_id", (String) param.get("acdt_id"));
			sub.put("SESS_USR_ID", (String) param.get("SESS_USR_ID"));		
			
			mapper.insertAcdtPhtscOrRppht(sub);	
		}
		
		//사고재현 사진
		subList = (List<Map<String, String>>) param.get("reenact_atfl_list");
		for(Map<String, String> sub : subList) {
			
			sub.put("acdt_id", (String) param.get("acdt_id"));
			sub.put("SESS_USR_ID", (String) param.get("SESS_USR_ID"));		
			
			mapper.insertAcdtPhtscOrRppht(sub);	
		}
		
		return n;
	}
		
	@Override
	public int updateAppr(Map<String, Object> param) {
		return mapper.updateAppr(param);
	}	
	
	@Override
	public int deleteAcdtMgnt(Map<String, Object> param) {
		int n = mapper.deleteAcdtMgnt(param);
		mapper.deleteAcdtOprn(param);
		mapper.deleteAcdtUsftTxt(param);
		mapper.deleteAcdtUsftSt(param);
		mapper.deleteAcdtDfdt(param);
		mapper.deleteAcdtIvstRslt(param);
		mapper.deleteAcdtActnPlan(param);
		mapper.deleteAcdtAtfl1(param);
		mapper.deleteAcdtAtfl2(param);
		mapper.deleteAcdtPhtscOrRppht(param);	//사고발생현장사진/사고재현사진
		return n;
	}

	@Override
	public String selectAcdtMgntId(Map<String, String> param) {
		return mapper.selectAcdtMgntId(param);
	}

	@Override
	public List<Map<String, ?>> selectAcdtMgntAprvDtl(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return mapper.selectAcdtMgntAprvDtl(param);
	}

	@Override
	public int updateAcdtMgntAprvAf(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return mapper.updateAcdtMgntAprvAf(param);
	}

	@Override
	public int delAcdt(Map<String, String> param) {
		return mapper.delAcdt(param);
	}

	@Override
	public List<Map<String, ?>> selectAcdtReport(Map<String, Object> param) {
		return mapper.selectAcdtReport(param);
	}

	@Override
	public List<Map<String, ?>> selectAcdtRpUnBhc(Map<String, Object> param) {
		return mapper.selectAcdtRpUnBhc(param);
	}

	@Override
	public List<Map<String, ?>> selectAcdtRpUnStCn(Map<String, Object> param) {
		return mapper.selectAcdtRpUnStCn(param);
	}

	@Override
	public int updateAcdtMailSendingStatus(Map<String, Object> param) {
		return mapper.updateAcdtMailSendingStatus(param);
	}

	@Override
	public List<Map<String, ?>> selectAcdtRecvList(Map<String, Object> param) {
		return mapper.selectAcdtRecvList(param);
	}

	@Override
	public List<Map<String, ?>> selectDeptMental(Map<String, String> param) {
		return mapper.selectDeptMental(param);
	}
}
