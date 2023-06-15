package com.kist.portal.common.chems.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * 사용자 DTO.
 */
@Getter
@Setter
public class UserDto {

  // ----------- start 사용자 ----------- //
  // 사원번호
  private String userId;
  // 사원명 한글
  private String userKoreanName;
  // 사원명 영어
  private String userEnglishName;
  // 사원명 독일어
  private String userGermanyName;
  // 직위 코드
  private String jkwCode;
  // 직위 명
  private String jkwName;
  // 직무 코드
  private String jkgCode;
  // 직무 명
  private String jkgName;
  // 직책 코드
  private String jkcCode;
  // 직책 명
  private String jkcName;
  // 생년월일
  private String userBirthDate;
  // 입사일자
  private String joinDate;
  // 재직 구분 코드
  private String offiClsfCode;
  // 발령 유형 코드
  private String appointmentTypeCode;
  // 파견 회사 코드
  private String dispatchCompanyCode;
  // 파션 부서 코드
  private String dispatchDepartmentCode;
  // 이메일
  private String userEmail;
  // ipt전화번호
  private String iptPhoneNo;
  // 회사 전화번호
  private String companyPhoneNo;
  // 사원 휴대전화 번호
  private String userCellPhoneNo;
  // 팩스번호
  private String faxNo;
  // 협력업체 여부
  private String partnersYn;
  // 언어 코드
  private String languageCode;
  // 퇴직일자
  private String retirementDate;
  // 사용여부
  private String useYn;
  // 삭제여부
  private String delYn;
  // 패스워드
  private String userPassword;
  // 등록자아이디
  private String createUserId;
  // 수정자아이디
  private String updateUserId;
  // 최근 로그인 일시
  private String lastLoginDateTime;
  // 최근 로그인 IP 주소
  private String lastLoginIpAddress;
  // 권한코드
  private String roleCode;
  // 권한명
  private String roleName;
  // ??
  private String wkplName;
  private String wkplId;
  // ----------- end 사용자 ----------- //

  // ----------- start Parameter ----------- //
  // 언어코드
  private String SESS_LANG;
  private String SESS_CMPY;
  // TODO: 확인 필요
  private String wkpl_id;
  private String userName;
  private String userNo;
  // ----------- end Parameter ----------- //

  // ----------- start 권한 ----------- //
  private String roleNo;
  private List<String> roleNos = new ArrayList<>();
  // ----------- end 권한 ----------- //

  // ----------- start 부서 ----------- //
  /** 부서코드 (dept_cd) */
  private String departmentCode;
  /** 상위부서코드 (up_dept_cd) */
  private String upDepartmentCode;
  /** 회사코드 (cmpy_cd) */
  private String companyCode;
  /** 부서명 (dept_nm) */
  private String departmentName;
  /** 부서장사원번호 (dpth_usr_id) */
  private String departmentUserId;
  /* 사용여부 (dpth_usg_yn) */
  private char departmentUsageYn = 'Y';
  /* 삭제여부 (dpth_del_yn) */
  private char departmentDeleteYn = 'N';
  /* 생성사원번호 (dpth_crt_usr_id) */
  private String departmentCreateUserId;
  /* 생성일시 (dpth_crt_dt) */
  private String departmentCreateDateTime;
  /* 수정사원번호 (dpth_upt_usr) */
  private String departmentUpdateUserId;
  /* 수정일시 (dpth_upt_dt) */
  private String departmentUpdateDateTime;
  /** 부서트리 (dept_tree) */
  private String departmentTree;
  /** 부서깊이 (dept_depth) */
  private String departmentDepth;
  // ----------- end 부서 ----------- //

}
