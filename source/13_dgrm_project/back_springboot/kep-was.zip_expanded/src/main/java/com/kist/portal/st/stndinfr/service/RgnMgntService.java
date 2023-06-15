package com.kist.portal.st.stndinfr.service;

import java.util.List;
import java.util.Map;

public interface RgnMgntService {

	public List<Map<String, ?>> selectRgnmgntList(Map<String, Object> param);
	
	public List<Map<String, ?>> selectRgnmgntCdList(Map<String, Object> param);
	
	public List<Map<String, ?>> selectRgnmgntPopList(Map<String, Object> param);
	
	public int insertRgnmgntList(Map<String, Object> param);

	public int updateRgnmgntList(Map<String, Object> param);
	
	public int deleteRgnmgntList(Map<String, Object> param);
	
	public String selectRgnmgntFKey(Map<String, Object> param);
	
	public String selectRgnmgntBKey(Map<String, Object> param);

	public List<Map<String, ?>> selectCatMapgtList(Map<String, Object> param);

	public List<Map<String, ?>> selectRgnmgntLoc(Map<String, Object> param);

	public void updateRgnmgntLoc(Map<String, Object> param);

	public void saveLocPoint(Map<String, Object> param);
}
