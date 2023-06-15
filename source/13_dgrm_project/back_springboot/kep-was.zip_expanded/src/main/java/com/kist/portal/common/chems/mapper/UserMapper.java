package com.kist.portal.common.chems.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.kist.portal.common.chems.dto.UserDto;

@Mapper
public interface UserMapper {

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
      * [insert]
      * 사용자 ~ 권한 매핑 정보 추가
      * 
      * @param param
      * @author miri
      * @version 2023-05-30
      */
     public void createUserRole(UserDto param);

     /**
      * [remove]
      * 사용자 ~ 권한 매핑 정보 삭제
      * 
      * @param param
      * @author miri
      * @version 2023-05-30
      */
     public void removeUserRole(UserDto param);

     /**
      * [select]
      * 특정 사용자에게 매핑된 권한 id[] 반환
      * 
      * @return 해당 사용자의 권한 id[]
      * @author miri
      * @version 2023-05-30
      */
     public List<String> selectRoleNosByUserId(String usr_id);
}
