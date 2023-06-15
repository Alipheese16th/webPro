package com.kist.portal.common.chems.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MenuMapper {

	public List<Map<String, ?>> selectMenuList(Map<String, String> param);
	
	public List<Map<String, ?>> selectMenuRoleList(Map<String, String> param);
	
	public String selectMlangMenuKey(Map<String, String> param);
	
	public int insertMenuRole(Map<String, String> param);
	
	public int updateMenuRole(Map<String, String> param);
	
	public int insertMenu(Map<String, String> param);
	
	public int insertMlangMenu(Map<String, String> param);
	
	public int updateMenu(Map<String, String> param);
	
	public int updateMlangMenu(Map<String, String> param);
	
	public Map<String, ?> selectMenuDetail(Map<String, String> param);
	
	public int deleteMenu(Map<String, String> param);
	
	public int deleteMlangMenu(Map<String, String> param);
	
	public int deleteMenuRole(Map<String, String> param);
}
