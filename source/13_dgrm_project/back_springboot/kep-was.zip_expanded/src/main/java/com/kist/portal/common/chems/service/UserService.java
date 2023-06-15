package com.kist.portal.common.chems.service;

import java.util.List;
import java.util.Map;

import com.kist.portal.common.chems.dto.UserDto;

public interface UserService {

	// ---------------- 기존 API 시작 ---------------- //

	/**
	 * 로그인 사용자가 속한 회사의 부서 리스트 조회
	 * (재귀 조회)
	 * @param param
	 * @return
	 */
	public List<UserDto> selectDeptTreeListNew(UserDto param);
	public List<Map<String, ?>> selectDeptTreeList(Map<String, String> param);

	/**
	 * 로그인 사용자가 속한 회사의 부서 리스트 조회
	 * @param param
	 * @return
	 */
	public List<UserDto> selectDeptSearchListNew(UserDto param);
	public List<Map<String, ?>> selectDeptSearchList(Map<String, String> param);

	public List<Map<String, ?>> selectUserPopList(Map<String, String> param);

	public List<UserDto> selectUserList(UserDto param);

	public int updateUserList(Map<String, String> param);

	// ---------------- 기존 API 종료 ---------------- //

	void createUser(UserDto param);

	void updateUser(UserDto param);

	void deleteUser(String usr_id);

	/**
	 * [시스템관리/사용자조회 액션]
	 * 사용자에게 권한 다건 매핑하기
	 * 
	 * @author miri
	 * @version 2023-05-30
	 * @param param
	 */
	public void modifyRole(UserDto param);
}
