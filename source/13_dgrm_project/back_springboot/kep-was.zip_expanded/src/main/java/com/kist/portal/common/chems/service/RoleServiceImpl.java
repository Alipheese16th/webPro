package com.kist.portal.common.chems.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.kist.portal.common.chems.dto.RoleDto;
import com.kist.portal.common.chems.mapper.RoleMapper;
import com.kist.portal.common.comm.dto.ApiDto;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

	@NonNull
	private final RoleMapper mapper;

	@Override
	public List<RoleDto> selectRoleList(RoleDto param) {
		return mapper.selectRoleList(param);
	}

	@Override
	public String selectRolekey() {
		return mapper.selectRolekey();
	}

	@Override
	public int insertRoleList(RoleDto param) {
		return mapper.insertRoleList(param);
	}

	@Override
	public int updateRoleList(RoleDto param) {
		return mapper.updateRoleList(param);
	}

	@Override
	public int deleteRoleList(RoleDto param) {
		return mapper.deleteRoleList(param);
	}
	
	@Override
	public List<RoleDto> selectUserRoleList(RoleDto param) {
		return mapper.selectUserRoleList(param);
	}

	@Override
	public List<RoleDto> selectRoleListMeunRole(RoleDto param) {
		return mapper.selectRoleListMeunRole(param);
	}

	@Override
	public List<RoleDto> selectMenuListMeunRole(RoleDto param) {
		return mapper.selectMenuListMeunRole(param);
	}

	@Override
	public int insertMeunRoleList(RoleDto param) {
		return mapper.insertMeunRoleList(param);
	}

	@Override
	public int updateMeunRoleList(RoleDto param) {
		return mapper.updateMeunRoleList(param);
	}

	@Override
	public int deleteMeunRoleList(Map<String, String> param) {
		return mapper.deleteMeunRoleList(param);
	}

	@Override
	public List<ApiDto> selectApiListApiRole(ApiDto param) {
		return mapper.selectApiListApiRole(param);
	}

	@Override
	public int insertApiRoleList(RoleDto param) {
		return mapper.insertApiRoleList(param);
	}

	@Override
	public void modifyUser(RoleDto param) {
		/**
		 * 1. 파라미터로 role_no, usr_id[] 를 받는다.
		 * 2. 권한 ~ 사용자 매핑 테이블에서 해당 권한의 usr_id [] 를 모두 가져온다.
		 * 
		 * 3. 파라미터 usr_id[] 에 기존 usr_id 가 존재하지 않음: 기존 usr_id 를 기반으로 매핑정보 delete
		 * 4. 기존 usr_id[] 에 파라미터 usr_id 가 이미 존재함: 아무것도 하지 않음
		 * 5. 기존 usr_id[] 에 파라미터 usr_id 가 존재하지 않음: 파라미터 usr_id 를 기반으로 insert
		 */

		// 1~2. 해당 권한에 엮인 usr_id [] 전부 가져오기
		final List<String> newUserIds = param.getUserIds();
		final List<String> currentUserIds = mapper.selectUserIdsByRoleNo(param.getRoleNo());

		// 3. 파라미터로 온 새로 매핑할 유저정보[] 에 존재하지 않는 기존 usr_id 들은 delete 처리
		final RoleDto removeParam = new RoleDto();
		removeParam.setRoleNo(param.getRoleNo());
		removeParam.setUserIds(
			currentUserIds.stream()
				.filter((currentUser) -> !newUserIds.contains(currentUser))
				.collect(Collectors.toList())
		);
		if(removeParam.getUserIds().size() > 0){
			mapper.removeRoleUser(removeParam);
		} else;

		// 4~5. 기존 usr_id[] 에 파라미터로 온 새로 매핑할 사용자 정보가 존재하지 않음: 파라미터 usr_id 를 기반으로 insert
		final RoleDto insertParam = new RoleDto();
		insertParam.setCreateUserId(param.getCreateUserId());
		insertParam.setRoleNo(param.getRoleNo());
		newUserIds.stream()
			.filter((newUser) -> !currentUserIds.contains(newUser))
			.collect(Collectors.toList())
			.forEach((newUserId) -> {
				insertParam.setUserId(newUserId);
				mapper.createRoleUser(insertParam);
			});
	}

	@Override
	public int countUserOfRole(List<String> roleNos) {
		return mapper.countUserOfRole(roleNos);
	}
}
