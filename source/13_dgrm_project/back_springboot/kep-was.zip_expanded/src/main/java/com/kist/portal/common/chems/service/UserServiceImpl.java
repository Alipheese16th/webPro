package com.kist.portal.common.chems.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kist.portal.common.chems.dto.UserDto;
import com.kist.portal.common.chems.mapper.UserMapper;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	/** 사용자 테이블 매퍼 */
	@NonNull
	private final UserMapper userMapper;

	@NonNull
	private final PasswordEncoder passwordEncoder;

	// ---------------- 기존 API 시작 ---------------- //

	@Override
	public List<UserDto> selectDeptTreeListNew(UserDto param) {
		return userMapper.selectDeptTreeListNew(param);
	}
	@Override
	public List<Map<String, ?>> selectDeptTreeList(Map<String, String> param) {
		return userMapper.selectDeptTreeList(param);
	}

	@Override
	public List<UserDto> selectDeptSearchListNew(UserDto param) {
		return userMapper.selectDeptSearchListNew(param);
	}
	@Override
	public List<Map<String, ?>> selectDeptSearchList(Map<String, String> param) {
		return userMapper.selectDeptSearchList(param);
	}

	@Override
	public List<Map<String, ?>> selectUserPopList(Map<String, String> param) {
		return userMapper.selectUserPopList(param);
	}

	@Override
	public List<UserDto> selectUserList(UserDto param) {
		return userMapper.selectUserList(param);
	}

	@Override
	public int updateUserList(Map<String, String> param) {
		return userMapper.updateUserList(param);
	}

	// ---------------- 기존 API 종료 ---------------- //

	@Override
	public void createUser(UserDto param) {

		// 패스워드 암호화 후 재입력
		param.setUserPassword(passwordEncoder.encode(param.getUserPassword()));

		userMapper.createUser(param);
	}

	@Override
	public void updateUser(UserDto param) {
		// 패스워드 암호화 후 재입력
		param.setUserPassword(passwordEncoder.encode(param.getUserPassword()));

		userMapper.updateUser(param);
	}

	@Override
	public void deleteUser(String usr_id) {
		userMapper.deleteUser(usr_id);
	}

	@Override
	public void modifyRole(UserDto param) {
		/**
		 * 1. 파라미터로 usr_id, role_no[] 를 받는다.
		 * 2. 사용자 ~ 권한 매핑 테이블에서 해당 사용자의 role_no [] 를 모두 가져온다.
		 * 
		 * 3. 파라미터 role_no[] 에 기존 role_no 가 존재하지 않음: 기존 role_no 를 기반으로 delete 처리
		 * 4. 기존 role_no[] 에 파라미터 role_no 가 이미 존재함: 아무것도 하지 않음
		 * 5. 기존 role_no[] 에 파라미터 role_no 가 존재하지 않음: 파라미터 role_no 를 기반으로 insert
		 * 
		 */

		// 1~2. 해당 사용자에 엮인 role_no [] 전부 가져오기
		final List<String> newRoleNos = param.getRoleNos();
		final List<String> currentRoleNos = userMapper.selectRoleNosByUserId(param.getUserId());

		// 3. 파라미터로 온 새로 매핑할 권한정보[] 에 존재하지 않는 기존 role_no 들은 delete 처리
		final UserDto removeParam = new UserDto();
		removeParam.setUserId(param.getUserId());
		removeParam.setRoleNos(
				currentRoleNos.stream()
						.filter((currentRole) -> !newRoleNos.contains(currentRole))
						.collect(Collectors.toList()));
		if (removeParam.getRoleNos().size() > 0) {
			userMapper.removeUserRole(removeParam);
		} else
			;

		// 4~5. 기존 role_no[] 에 파라미터로 온 새로 매핑할 권한정보가 존재하지 않음: 파라미터 role_no 를 기반으로 insert
		final UserDto insertParam = new UserDto();
		insertParam.setCreateUserId(param.getCreateUserId());
		insertParam.setUserId(param.getUserId());
		newRoleNos.stream()
				.filter((newRole) -> !currentRoleNos.contains(newRole))
				.collect(Collectors.toList())
				.forEach((newRoleNo) -> {
					insertParam.setRoleNo(newRoleNo);
					userMapper.createUserRole(insertParam);
				});
	}
}
