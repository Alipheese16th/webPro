package com.kist.portal.sh.hmex.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kist.portal.sh.hmex.mapper.HmexRsltMapper;

@Service
public class HmexRsltServiceImpl implements HmexRsltService {

	@Autowired(required=true)
	HmexRsltMapper mapper;

	@Override
	public List<Map<String, ?>> selectRsltList(Map<String, String> param) {	
		return mapper.selectRsltList(param);
	}
	
	@Override
	public Map<String, ?> selectRslt(Map<String, String> param) {
		Map<String, Object> map = mapper.selectUser(param);
		map.put("rslt_list", mapper.selectHmexRslt(param));
		
		return map;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int insertHmexRslt(Map<String, Object> param) {
		int n = 0;
		
		List<Map<String, String>> rsltList = (List<Map<String, String>>) param.get("rslt_list");
		
		for(Map<String, String> rslt : rsltList) {
			rslt.put("SESS_USR_ID", (String) param.get("SESS_USR_ID"));
			
			// 특수/일반 검진 대상자 관리에서 확정한 경우 
			if ("Y".equals(param.get("hmex_confirm"))) {
				n = mapper.insertHmexRslt2(rslt);		// 건강검진 결과 등록
			} else {
				n += mapper.insertHmexRslt(rslt);		// 건강검진 결과 등록/수정
				
				// 검진구분 :	일반검진 : CG01
				//			배치전 검진 : CG02, 배치후 검진 : CG03, 특수 1차: CG04, 특수 2차: CG05
				if ("CG01".equals(rslt.get("hmex_cl_cd"))) {
					n += mapper.insertNewHmexMgnt(rslt);	// 건강검진 결과 - 신규 일반대상자 등록
				} else {
					n += mapper.insertNewHmexMgnt2(rslt);	// 건강검진 결과 - 신규 특수대상자 등록
				}
			}
		}
		return n;
	}
	
	@Override
	public int deleteHmexRslt(Map<String, String> param) {
		return mapper.deleteHmexRslt(param);
	}
}
