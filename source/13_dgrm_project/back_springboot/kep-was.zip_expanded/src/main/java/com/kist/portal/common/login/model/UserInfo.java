package com.kist.portal.common.login.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.kist.portal.common.login.dto.UserDetailDto;

public class UserInfo implements Serializable {

	private static final long serialVersionUID = -5815072092278883977L;
	private String usrId;
	private String usrNm;
	private String deptCd;
	private String deptNm;
	private String cmpyNm;
	private String cmpyCd;
	private String wkplId;
	private String wkplNm;
	private String langCd;
	private String timeZone;
	private String timeZoneCd;
	private String workspaceCd;
	private String roles;
	private String roleTypeCds;
	private String empEmail;
	private String empCphoNo;
	private String dpthUsrId;
	private String lastLoginDt;
	private String lastIpAddr;
	private Map<String, String> apiInfo;

	/**
	 * UserInfo to UserDto Contructor
	 * 
	 * @param dto
	 */
	public UserInfo(UserDetailDto dto) {
		this.usrId = dto.getUserId();
		this.usrNm = dto.getUsername();
		this.deptCd = dto.getDepartmentCode();
		this.deptNm = dto.getDepartmentName();
		this.cmpyCd = dto.getCompanyCode();
		this.wkplId = dto.getWkplId();
		this.wkplNm = dto.getWkplNm();
		this.langCd = dto.getLanguageCode();
		this.timeZone = dto.getTimeZone();
		this.timeZoneCd = dto.getTimeZoneCode();
		this.workspaceCd = dto.getWorkspaceCode();
		this.roles = dto.getRoles();
		this.roleTypeCds = dto.getRoleTypeCodes();
		this.empEmail = dto.getUserEmail();
		this.empCphoNo = dto.getUserCellPhoneNo();
		this.dpthUsrId = dto.getDepartmentHeadUserId();
		this.lastLoginDt = dto.getLastLoginDateTime();
		this.lastIpAddr = dto.getLastIpAddress();
		this.apiInfo = dto.getApiInfo();
		
	}

	/**
	 * UserDto 를 UserInfo로 변환
	 * 생성자 초기화 방식으로 활용.
	 * 
	 * @param dto
	 * @return
	 */
	public static UserInfo clone(UserDetailDto dto) {
		return new UserInfo(dto);
	}

	public String getUsrId() {
		return usrId;
	}

	public void setUsrId(String usrId) {
		this.usrId = usrId;
	}

	public String getUsrNm() {
		return usrNm;
	}

	public void setUsrNm(String usrNm) {
		this.usrNm = usrNm;
	}

	public String getCmpyNm() {
		return cmpyNm;
	}

	public String getCmpyCd() {
		return cmpyCd;
	}

	public void setCmpyNm(String cmpyNm) {
		this.cmpyNm = cmpyNm;
	}

	public String getDeptCd() {
		return deptCd;
	}

	public void setDeptCd(String deptCd) {
		this.deptCd = deptCd;
	}

	public String getDeptNm() {
		return deptNm;
	}

	public void setDeptNm(String deptNm) {
		this.deptNm = deptNm;
	}

	public String getWkplId() {
		return wkplId;
	}

	public void setWkplId(String wkplId) {
		this.wkplId = wkplId;
	}

	public String getWkplNm() {
		return wkplNm;
	}

	public void setWkplNm(String wkplNm) {
		this.wkplNm = wkplNm;
	}

	public String getLangCd() {
		return langCd;
	}

	public void setLangCd(String langCd) {
		this.langCd = langCd;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	public String getTimeZoneCd() {
		return timeZoneCd;
	}

	public void setTimeZoneCd(String timeZoneCd) {
		this.timeZoneCd = timeZoneCd;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public String getRoleTypeCds() {
		return roleTypeCds;
	}

	public void setRoleTypeCds(String roleTypeCds) {
		this.roleTypeCds = roleTypeCds;
	}

	public String getUsrEmail() {
		return empEmail;
	}

	public void setUsrEmail(String empEmail) {
		this.empEmail = empEmail;
	}

	public String getUsrCphoNo() {
		return empCphoNo;
	}

	public void setUsrCphoNo(String empCphoNo) {
		this.empCphoNo = empCphoNo;
	}

	public String getDpthUsrId() {
		return dpthUsrId;
	}

	public void setDpthUsrId(String dpthUsrId) {
		this.dpthUsrId = dpthUsrId;
	}

	public String getWorkspaceCd() {
		return workspaceCd;
	}

	public void setWorkspaceCd(String workspaceCd) {
		this.workspaceCd = workspaceCd;
	}

	public List<String> getRoleList() {
		if (this.roles.length() > 0) {
			return Arrays.asList(this.roles.split(","));
		}

		return new ArrayList<>();
	}

	public List<String> getRoleTypeCdsList() {
		if (this.roleTypeCds.length() > 0) {
			return Arrays.asList(this.roleTypeCds.split(","));
		}

		return new ArrayList<>();
	}

	public String getLastLoginDt() {
		return lastLoginDt;
	}

	public void setLastLoginDt(String lastLoginDt) {
		this.lastLoginDt = lastLoginDt;
	}

	public String getLastIpAddr() {
		return lastIpAddr;
	}

	public void setLastIpAddr(String lastIpAddr) {
		this.lastIpAddr = lastIpAddr;
	}

	public Map<String, String> getApiInfo() {
		return apiInfo;
	}

	public void setApiInfo(Map<String, String> apiInfo) {
		this.apiInfo = apiInfo;
	}

}
