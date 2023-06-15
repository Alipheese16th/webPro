package com.kist.portal.ch.mro.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kist.portal.ch.mro.mapper.mroPchsMapper;

@Service
public class mroPchsServiceImpl implements mroPchsService {
	
	@Autowired
	mroPchsMapper mapper;

	@Override
	public List<Map<String, ?>> selectMroList(Map<String, String> param) {
		return mapper.selectMroList(param);
	}

	@Override
	public List<Map<String, ?>> selectMroPkgList(Map<String, String> param) {
		return mapper.selectMroPkgList(param);
	}

	@Override
	public String selectChkDeptYnMro(Map<String, String> param) {
		return mapper.selectChkDeptYnMro(param);
	}

	@Override
	public String selectMroPchskey(Map<String, String> param) {
		return mapper.selectMroPchskey(param);
	}

	@Override
	public int insertMroPchs(Map<String, String> param) {
		return mapper.insertMroPchs(param);
	}

	@Override
	public int insertMroPchsMtrl(Map<String, String> param) {
		return mapper.insertMroPchsMtrl(param);
	}

	@Override
	public int insertMroPchsMtrlSbst(Map<String, String> param) {
		return mapper.insertMroPchsMtrlSbst(param);
	}

	@Override
	public int insertMroPchsGwsgn(Map<String, String> param) {
		return mapper.insertMroPchsGwsgn(param);
	}
	
	@Override
	public int deleteMroPic(Map<String, String> param) {
		return mapper.deleteMroPic(param);
	}
	
	@Override
	public int insertMroPic(Map<String, String> param) {
		return mapper.insertMroPic(param);
	}

	@Override
	public Map<String, String> selectMroPchsDtl(Map<String, String> param) {
		return mapper.selectMroPchsDtl(param);
	}

	@Override
	public List<Map<String, ?>> selectMroPchsMtrlList(Map<String, String> param) {
		return mapper.selectMroPchsMtrlList(param);
	}

	@Override
	public Map<String, String> selectMroPchsMtrlDtl(Map<String, String> param) {
		return mapper.selectMroPchsMtrlDtl(param);

	}

	@Override
	public List<Map<String, String>> selectMroPchsMtrlSbstDtlList(Map<String, String> param) {
		return mapper.selectMroPchsMtrlSbstDtlList(param);

	}

	@Override
	public Map<String, String> selectMroPchsGwsgnDtl(Map<String, String> param) {
		return mapper.selectMroPchsGwsgnDtl(param);

	}

	@Override
	public List<Map<String, String>> selectMroPicDtlList(Map<String, String> param) {
		return mapper.selectMroPicDtlList(param);
	}

	@Override
	public int deleteMroPchs(Map<String, String> param) {
		return mapper.deleteMroPchs(param);
	}

	@Override
	public int deleteMroPchsMtrl(Map<String, String> param) {
		return mapper.deleteMroPchsMtrl(param);
	}

	@Override
	public int deleteMroPchsMtrlSbst(Map<String, String> param) {
		return mapper.deleteMroPchsMtrlSbst(param);
	}

	@Override
	public int deleteMroPchsGwsgn(Map<String, String> param) {
		return mapper.deleteMroPchsGwsgn(param);
	}

	@Override
	public int updateMroPchsMtrl(Map<String, String> param) {
		return mapper.updateMroPchsMtrl(param);
	}

	@Override
	public int insertMroPchsMultiMtrl(Map<String, String> param) {
		return mapper.insertMroPchsMultiMtrl(param);

	}

	@Override
	public int deleteMroPchsMultiMtrl(Map<String, String> param) {
		return mapper.deleteMroPchsMultiMtrl(param);

	}

	@Override
	public List<Map<String, String>> selectMroPchsMultiMtrlList(Map<String, String> param) {
		return mapper.selectMroPchsMultiMtrlList(param);

	}
	
	@Override
	public int MroPchsAprvIng(Map<String, Object> param) {
		return mapper.MroPchsAprvIng(param);
	}
	
	//결제 후처리 
	@Override
	public Map<String, String> selectMroPchsAprvNo(Map<String, String> param) {
		return mapper.selectMroPchsAprvNo(param);
	}

	//결제 후처리(일괄)
	@Override
	public List<Map<String, String>> selectMroPchsAprvNoList(Map<String, String> param) {
		return mapper.selectMroPchsAprvNoList(param);
	}
	
	@Override
	public Map<String, String> selectMroMtrlNo(Map<String, String> param) {
		return mapper.selectMroMtrlNo(param);
	}
	
	@Override
	public int MroPchsAprvSucs(Map<String, String> param) {
		return mapper.MroPchsAprvSucs(param);
	}

	@Override
	public int MroPchsAprvFail(Map<String, String> param) {
		return mapper.MroPchsAprvFail(param);
	}
	
	@Override
	public int MroPchsAprvCancel(Map<String, String> param) {
		return mapper.MroPchsAprvCancel(param);
	}

	@Override
	public Map<String, String> selectRgltChkNo(Map<String, String> param) {
		return mapper.selectRgltChkNo(param);
	}

	@Override
	public int MrpPchsAprvSucsInsertMtrl(Map<String, String> param) {
		return mapper.MrpPchsAprvSucsInsertMtrl(param);
	}
	
	@Override
	public int MrpPchsAprvSucsInsertRgltChk(Map<String, String> param) {
		return mapper.MrpPchsAprvSucsInsertRgltChk(param);
	}

	@Override
	public int MrpPchsAprvSucsInsertRgltChkMtrl(Map<String, String> param) {
		return mapper.MrpPchsAprvSucsInsertRgltChkMtrl(param);
	}

	@Override
	public int MrpPchsAprvSucsInsertRgltChkMsds(Map<String, String> param) {
		return mapper.MrpPchsAprvSucsInsertRgltChkMsds(param);
	}

	@Override
	public int MrpPchsAprvSucsInsertRgltChkSbst(Map<String, String> param) {
		return mapper.MrpPchsAprvSucsInsertRgltChkSbst(param);
	}
	
	@Override
	public int MroPchsAprvSucsInsertRgltChkRglt(Map<String, String> param) {
		return mapper.MroPchsAprvSucsInsertRgltChkRglt(param);
	}

	@Override
	public int MrpPchsAprvSucsInsertRgltChkGwsgn(Map<String, String> param) {
		return mapper.MrpPchsAprvSucsInsertRgltChkGwsgn(param);
	}

	@Override
	public int MrpPchsAprvSucsInsertRgltChkGwsgnPicg(Map<String, String> param) {
		return mapper.MrpPchsAprvSucsInsertRgltChkGwsgnPicg(param);
	}
	
	@Override
	public int MrpPchsAprvSucsInsertRgltChkMtrlSite(Map<String, String> param) {
		return mapper.MrpPchsAprvSucsInsertRgltChkMtrlSite(param);
	}

	@Override
	public int MrpPchsAprvSucsUpdateRgltChkPchsMtrl(Map<String, String> param) {
		return mapper.MrpPchsAprvSucsUpdateRgltChkPchsMtrl(param);
	}
	
}
	