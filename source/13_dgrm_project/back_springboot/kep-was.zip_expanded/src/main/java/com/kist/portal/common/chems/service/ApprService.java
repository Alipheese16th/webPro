package com.kist.portal.common.chems.service;

import java.util.List;
import java.util.Map;

public interface ApprService {

	public List<Map<String, ?>> selectApprList(Map<String, String> param);

	public List<Map<String, ?>> selectApprDtl(Map<String, String> param);

	public List<Map<String, ?>> selectApprDtlRecv(Map<String, String> param);

	int insertApprDtlRecv(Map<String, String> param);

	public int updateApprDtl(Map<String, String> param);

	public int updateApprRecvDtl(Map<String, String> param);
}
