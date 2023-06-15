package com.kist.portal.common.chems.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ApprMapper {

	public List<Map<String, ?>> selectApprList(Map<String, String> param);

	public List<Map<String, ?>> selectApprDtl(Map<String, String> param);

	public List<Map<String, ?>> selectApprDtlRecv(Map<String, String> param);

	public int insertApprDtlRecv(Map<String, String> param);

	public int updateApprDtl(Map<String, String> param);

	public int updateApprRecvDtl(Map<String, String> param);
}
