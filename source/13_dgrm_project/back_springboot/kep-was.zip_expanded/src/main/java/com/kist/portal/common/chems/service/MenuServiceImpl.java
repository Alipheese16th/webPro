package com.kist.portal.common.chems.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kist.portal.common.chems.mapper.MenuMapper;

@Service
public class MenuServiceImpl implements MenuService {

	@Autowired
	MenuMapper mapper;

	@Override
	public List<Map<String, ?>> selectMenuList(Map<String, String> param) {
		return mapper.selectMenuList(param);
	}
	
	@Override
	public List<Map<String, ?>> selectMenuRoleList(Map<String, String> param) {
		return mapper.selectMenuRoleList(param);
	}
	
	@Override
	public String selectMlangMenuKey(Map<String, String> param) {
		return mapper.selectMlangMenuKey(param);
	}
	
	@Override
	public int insertMenuRole(Map<String, String> param) {
		return mapper.insertMenuRole(param);
	}
	
	@Override
	public int updateMenuRole(Map<String, String> param) {
		return mapper.updateMenuRole(param);
	}
	
	@Override
	public int insertMenu(Map<String, String> param) {
		return mapper.insertMenu(param);
	}
	
	@Override
	public int insertMlangMenu(Map<String, String> param) {
		return mapper.insertMlangMenu(param);
	}
	
	@Override
	public int updateMenu(Map<String, String> param) {
		return mapper.updateMenu(param);
	}
	
	@Override
	public int updateMlangMenu(Map<String, String> param) {
		return mapper.updateMlangMenu(param);
	}
	
	@Override
	public Map<String, ?> selectMenuDetail(Map<String, String> param) {
		return mapper.selectMenuDetail(param);
	}
	
	@Override
	public int deleteMenu(Map<String, String> param) {
		return mapper.deleteMenu(param);
	}
	
	@Override
	public int deleteMlangMenu(Map<String, String> param) {
		return mapper.deleteMlangMenu(param);
	}
	
	@Override
	public int deleteMenuRole(Map<String, String> param) {
		return mapper.deleteMenuRole(param);
	}
}
