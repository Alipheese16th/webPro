package com.kist.portal.common.chems.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.kist.portal.common.chems.dto.RoleDto;
import com.kist.portal.common.comm.dto.ApiDto;

@Mapper
public interface RoleMapper {

	/**
	 * [select]
	 * 권한 리스트 조회
	 * @param param
	 * @return
	 */
	public List<RoleDto> selectRoleList(RoleDto param);
	
	/**
	 * [select]
	 * 권한 키 채번
	 * @return
	 */
	public String selectRolekey();
	
	/**
	 * [insert]
	 * 권한 등록
	 * @param param
	 * @return
	 */
	public int insertRoleList(RoleDto param);
	
	/**
	 * [update]
	 * 권한 수정
	 * @param param
	 * @return
	 */
	public int updateRoleList(RoleDto param);
	
	/**
	 * [delete]
	 * 권한 삭제
	 * @param param
	 * @return
	 */
	public int deleteRoleList(RoleDto param);
	
	/**
	 * [select]
	 * 사용자 ~ 권한 매핑 리스트 조회
	 * @param param
	 * @return
	 */
	public List<RoleDto> selectUserRoleList(RoleDto param);
	
	/**
	 * [select]
	 * 권한 ~ 메뉴 매핑 리스트 조회
	 * @param param
	 * @return
	 */
	public List<RoleDto> selectRoleListMeunRole(RoleDto param);
	
	/**
	 * [select]
	 * 한 권한에게 매핑된 메뉴 리스트 조회
	 * @param param
	 * @return
	 */
	public List<RoleDto> selectMenuListMeunRole(RoleDto param);
	
	/**
	 * [insert]
	 * 한 권한에게 매핑된 메뉴 저장
	 * (체크박스 활성화된 경우)
	 * @param param
	 * @return
	 */
	public int insertMeunRoleList(RoleDto param);
	
	/**
	 * [update]
	 * 한 권한에게 매핑된 메뉴 저장
	 * (체크박스 비활성화된 경우)
	 * @param param
	 * @return
	 */
	public int updateMeunRoleList(RoleDto param);
	
	public int deleteMeunRoleList(Map<String, String> param);
	
	/**
	 * [select]
	 * 권한 ~ API 매핑정보 리스트 조회
	 * @param param
	 * @return
	 */
	public List<ApiDto> selectApiListApiRole(ApiDto param);
	
	/**
	 * [insert]
	 * 권한 ~ API 매핑정보 저장
	 * @param param
	 * @return
	 */
	public int insertApiRoleList(RoleDto param);

	/**
     * [select]
     * 특정 권한에게 매핑된 사용자 id[] 반환
     * @param roleNo
     * @return 해당 권한에 엮인 사용자 id[]
	 * @author miri
 	 * @version 2023-05-30
     */
    public List<String> selectUserIdsByRoleNo(String roleNo);

	/**
     * [remove]
     * 사용자 ~ 권한 매핑 정보 삭제
     * @param param
	 * @author miri
 	 * @version 2023-05-30
     */
    public void removeRoleUser(RoleDto param);

	/**
     * [insert]
     * 권한 ~ 사용자 매핑 정보 추가
     * @param param
	 * @author miri
 	 * @version 2023-05-30
     */
    public void createRoleUser(RoleDto param);

	/**
	 * [select]
	 * 해당 권한들에 물린 사용자 카운트 전부 합하여 반환
	 * (정합성 체크용)
	 * @param roleNos
	 * @return
	 */
    public int countUserOfRole(List<String> roleNos);
}
