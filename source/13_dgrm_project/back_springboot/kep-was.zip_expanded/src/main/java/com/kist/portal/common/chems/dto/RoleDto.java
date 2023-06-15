package com.kist.portal.common.chems.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * 권한정보 DTO
 * (tb_co_role)
 * @author miri
 * @version 2023-05-30
 */
@Getter
@Setter
public class RoleDto {

    // ==== [start 세션]
    /** 세션 유저 id */
    private String SESS_USR_ID;
    /** 세션 언어코드 */
    private String SESS_LANG;
    /** 세션 타임존 */
    private String SESS_TIME_ZONE;
    // ==== [end 세션]

    // ==== [start 권한테이블]

    /** 권한 아이디 (role_no) */
    private String roleNo;
    /** 권한 유형 코드 (role_type_cd) */
    private String roleTypeCode;
    /** 권한 유형 코드 이름 (role_type_nm) */
    private String roleTypeCodeName;
    /** 업무코드 (biz_cd) */
    private String businessCode;
    /** 업무코드명 (biz_cd_nm) */
    private String businessCodeName;
    /** 정렬순번 (sort_seq) */
    private int sortSeq;
    /** 권한설명 (role_expl) */
    private String roleExplain;
    private String roleExplainName;
    /** 사용여부 (usg_yn) */
    private char usageYn = 'N';
    /** 삭제여부 (del_yn) */
    private char deleteYn = 'N';
    /** 생성사원번호 (crt_usr_id) */
    private String createUserId;
    /** 생성일시 (crt_dt) */
    private String createDateTime;
    /** 수정사원번호 (upt_usr_id) */
    private String updateUserId;
    /** 수정일시 (upt_dt) */
    private String updateDateTime;
    private String updateTime;

    // ==== [end 권한테이블]

    // ==== [start 사용자 서브테이블]
    /** 사용자 수 카운트 (user_cnt) */
    private int userCount;
    /** 사용자 직위명 (jkw_nm) */
    private String jkwName;
    /** 사용자 부서코드 (dept_cd) */
    private String departmentCode;
    /** 사용자 이매일 (usr_email) */
    private String userEmail;
    /** (파라미터전용) 권한에 매핑할 모든 사용자 아이디[] (usr_ids) */
    private List<String> userIds = new ArrayList<>();
    /** (쿼리반환전용) 권한에 매핑할 모든 사용자 아이디. 구분자 "," (usr_ids) */
    private String userIdsString;
    /** 권한에 매핑될 단건 사용자 아이디 (usr_id) */
    private String userId;
    /** 권한에 매핑될 사용자 이름 (usr_nm) */
    private String userName;
    /** 권한에 매핑될 사용자 부서명 (dept_nm) */
    private String deptName;
    // ==== [end 사용자 서브테이블]

    // ==== [start 메뉴 서브테이블]
    /** 메뉴 번호 (menu_no) */
    private String menuNo;
    /** 상위 메뉴 번호 (up_menu_no) */
    private String upMenuNo;
    /** 메뉴명 (menu_nm) */
    private String menuName;
    /** 메뉴 계층 (menu_lv) */
    private String menuLevel;
    /** 메뉴가 해당 권한에 속해있는지/없는지 1/0 으로 표기 (rolechk) */
    private String roleCheck;
    // ==== [end 메뉴 서브테이블]

    // === [start API 서브테이블]
    /** API 번호 (api_no) */
    private String apiNo;
    /** get메서드 여부 0/1 or YN (gemthd_yn) */
    private String getMethodYn;
    /** post메서드 여부 0/1 or YN (pomthd_yn) */
    private String postMethodYn;
    /** put메서드 여부 0/1 or YN (pumthd_yn) */
    private String putMethodYn;
    /** delete메서드 여부 0/1 or YN (demthd_yn) */
    private String deleteMethodYn;
    // === [end API 서브테이블]


    // ==== [start 저장/수정]
    /** 저장/수정용 데이터 */
    private List<RoleDto> savedatas = new ArrayList<>();
    /** 그리드 status */
    private RowStatusType rowStatus;
    // ==== [end 저장/수정]

    // === [start 다국어 서브테이블]
    /** 다국어 번호 (mlang_no) */
    private String mlanguageNo;
    // === [end 다국어 서브테이블]

    

}
