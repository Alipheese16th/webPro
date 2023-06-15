package com.kist.portal.common.comm.dto;

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
public class ApiDto {
    // ==== [start 세션]
    /** 세션 언어코드 */
    private String SESS_LANG;
    /** 세션 타임존 */
    private String SESS_TIME_ZONE;
    // ==== [end 세션]

    // [==== start API 테이블]
    /** api 번호 (api_no) */
    private String apiNo;
    /** 업무코드 (biz_cd) */
    private String businessCode;
    /** 업무코드명 (biz_cd_nm) */
    private String businessCodeName;
    
    /** 상세업무코드 (dtl_biz_cd) */
    private String detailBusinessCode;
    /** 상세업무코드명 (dtl_biz_cd_nm) */
    private String detailBusinessCodeName;

    /** api 설명 (api_expl) */
    private String apiExplain;
    /** api_url (api_url) */
    private String apiUrl;
    /** 사용여부 (usg_yn) */
    private char usageYn = 'Y';
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
    /** 개인정보여부 (privacy_yn) */
    private char privacyYn = 'N';
    /** 개인정보내용 (privacy_txt) */
    private String privacyText;
    // [==== end API 테이블]

    // ==== [start API-권한 매핑 서브테이블]
    /** get메서드 여부 0/1 or YN (gemthd_yn) */
    private String getMethodYn;
    /** post메서드 여부 0/1 or YN (pomthd_yn) */
    private String postMethodYn;
    /** put메서드 여부 0/1 or YN (pumthd_yn) */
    private String putMethodYn;
    /** delete메서드 여부 0/1 or YN (demthd_yn) */
    private String deleteMethodYn;
    // ==== [end API-권한 매핑 서브테이블]

    // [==== start 권한 서브테이블]
    /** 권한 아이디 (role_no) */
    private String roleNo;
    // [==== end 권한 서브테이블]


}
