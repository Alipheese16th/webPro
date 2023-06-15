package com.kist.portal.common.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kist.portal.common.chems.service.MlangService;

@Component
public class MultiLanguageUtil {
	
	@Autowired
	private MlangService service;
	
	public String getMLang(String mlangNo, String langCd) {
		Map<String, String> param = new HashMap<>();
		param.put("mlang_no", mlangNo);
		param.put("lang_cd", langCd);
		
		String cntn = service.selectMlangInfo(param);
		
		return cntn;
	}
	
	public List<Map<String, String>> getMLangList(List<String> mlangNo, String langCd) {
		Map<String, Object> param = new HashMap<>();
		param.put("mlang_no", mlangNo);
		param.put("lang_cd", langCd);
		
		List<Map<String, String>> mlangList = service.selectMlangListInfo(param);
		
		return mlangList;
	}
	
	public String getMLang(List<Map<String, String>> list, String cd) {
		
		return list.stream().filter(m -> m.get("mlang_no").equals(cd)).findAny().get().get("mlang_cntn");
	}
	
}
