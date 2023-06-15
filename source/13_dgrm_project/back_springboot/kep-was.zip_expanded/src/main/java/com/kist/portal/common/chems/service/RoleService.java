package com.kist.portal.common.chems.service;

import java.util.List;
import java.util.Map;

import com.kist.portal.common.chems.dto.RoleDto;
import com.kist.portal.common.comm.dto.ApiDto;

public interface RoleService {
	
	/**
	 * 권한 리스트 조회
	 * @param param
	 * @return
	 */
	public List<RoleDto> selectRoleList(RoleDto param);
	
	/**
	 * 권한 키 채번
	 * @return
	 */
	public String selectRolekey();
	
	/**
	 * 권한 등록
	 * @param param
	 * @return
	 */
	public int insertRoleList(RoleDto param);
	
	/**
	 * 권한 수정
	 * @param param
	 * @return
	 */
	public int updateRoleList(RoleDto param);
	
	/**
	 * 권한 삭제
	 * @param param
	 * @return
	 */
	public int deleteRoleList(RoleDto param);
	
	/**
	 * 사용자 ~ 권한 매핑 리스트 조회
	 * @param param
	 * @return
	 */
	public List<RoleDto> selectUserRoleList(RoleDto param);
	
	/**
	 * 권한 ~ 메뉴 매핑 리스트 조회
	 * @param param
	 * @return
	 */
	public List<RoleDto> selectRoleListMeunRole(RoleDto param);
	
	/**
	 * 한 권한에게 매핑된 메뉴 리스트 조회
	 * @param param
	 * @return
	 */
	public List<RoleDto> selectMenuListMeunRole(RoleDto param);
	
	/**
	 * 한 권한에게 매핑된 메뉴 저장
	 * (체크박스 활성화된 경우)
	 * @param param
	 * @return
	 */
	public int insertMeunRoleList(RoleDto param);
	
	/**
	 * 한 권한에게 매핑된 메뉴 저장
	 * (체크박스 비활성화된 경우)
	 * @param param
	 * @return
	 */
	public int updateMeunRoleList(RoleDto param);
	
	public int deleteMeunRoleList(Map<String, String> param);
	
	/**
	 * 권한 ~ API 매핑정보 리스트 조회
	 * @param param
	 * @return
	 */
	public List<ApiDto> selectApiListApiRole(ApiDto param);
	
	/**
	 * 권한 ~ API 매핑정보 저장
	 * @param param
	 * @return
	 */
	public int insertApiRoleList(RoleDto param);

	/**
	 * [시스템관리/권한관리 액션]
	 * 권한에게 사용자 다건 매핑하기
	 * @author miri
	 * @version 2023-05-30
	 * @param param
	 */
    public void modifyUser(RoleDto param);

	/**
	 * 해당 권한들에 물린 사용자 카운트 전부 합하여 반환
	 * (정합성 체크용)
	 * @param roleNos
	 * @return
	 */
    public int countUserOfRole(List<String> roleNos);

}
