package com.kist.portal.common.aprv.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kist.portal.common.aprv.mapper.AprvMapper;

@Service
public class AprvServiceImpl implements AprvService {
	
	@Autowired
	AprvMapper mapper;
	
	@Override
	public int insertAprv(Map<String, Object> param) {
		return mapper.insertAprv(param);
	}

	@Override
	public String selectAprvkey() {
		return mapper.selectAprvkey();

	}
	
	@Override
	public String getaprvCSS() {
		String aprvClass = 
				"<style>\r\n" +
				/*공통 시작*/
				"	.aprv_div {margin-bottom:50px;}\r\n" +
				"	.aprv_div .title {font-weight: bold; color: rgb(0, 0, 0); font-family: Malgun Gothic; font-size: medium;}\r\n" +
				"	.aprv_div .txc-table {width: 100%; table-layout: fixed; text-align:center; border-color: rgb(204, 204, 204); border-collapse: collapse; word-break: break-all; font-size: 13.3333px;}\r\n" +
				"	.aprv_div .txc-table p {margin:0;}\r\n" +
				"	.aprv_div .txc-table thead {background:rgb(246, 246, 246);}\r\n" +
				"	.aprv_div .txc-table tbody tr {height:60px;}\r\n" +
				"	.aprv_div .txc-table tbody tr td {box-sizing:border-box; padding:5px;}\r\n" +
				"	.aprv_div .text_left {text-align:left;}\r\n" +
				"</style>\r\n";
		
		return aprvClass;
	}

}
