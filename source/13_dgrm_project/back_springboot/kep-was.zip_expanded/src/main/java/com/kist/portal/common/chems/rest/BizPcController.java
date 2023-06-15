package com.kist.portal.common.chems.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kist.portal.common.chems.service.BizPcService;
import com.kist.portal.common.login.model.UserInfo;
import com.kist.portal.common.utils.Constant;
import com.kist.portal.sysconf.manager.SessionManager;

@RestController
@RequestMapping("/api/v1/common/chems")
public class BizPcController {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SessionManager sessionManager;
	
	
	@Autowired
	private BizPcService bizPcservice;
	
	@GetMapping("/bizpc")
	public ResponseEntity<?> selectBizPcGrpList(@RequestParam Map<String, String> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());

		param.put("SESS_LANG", userInfo.getLangCd());
		List<Map<String, ?>> list = bizPcservice.selectBizPcGrpList(param);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@GetMapping("/bizpc/{biz_pc_grp_cd}")
	public ResponseEntity<?> selectBizPcDtlList(@PathVariable String biz_pc_grp_cd, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		Map<String, String> searchData = new HashMap<>();

		searchData.put("SESS_LANG", userInfo.getLangCd());
		searchData.put("SESS_TIME_ZONE", userInfo.getTimeZone());

		searchData.put("biz_pc_grp_cd", biz_pc_grp_cd);

		List<Map<String, ?>> list = bizPcservice.selectBizPcDtlList(searchData);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@PostMapping("/bizpc")
	@Transactional
	public ResponseEntity<?> saveMlangList(@RequestBody Map<String, List<List<Map<String, String>>>> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());

		List<List<Map<String, String>>> grpsavedatas = param.get("saveGrpdatas");
		List<List<Map<String, String>>> bizsavedatas = param.get("saveBizdatas");
		// Biz키 채번
		String key = "";

		for(int i = 0 ; i< grpsavedatas.size();i++) 
		{
			Map<String, String> savedata = new HashMap<>();
			savedata = (Map<String, String>)grpsavedatas.get(i).get(0);
			savedata.put("SESS_USR_ID", userInfo.getUsrId());
			if("C".equals(savedata.get("row_status"))) 
			{
//				key = bizPcservice.selectBizPckey(savedata);
//				savedata.put("biz_pc_grp_cd", key);
				bizPcservice.saveBizPcGrp(savedata);
			}
			if("U".equals(savedata.get("row_status"))) 
			{
				bizPcservice.saveBizPcGrp(savedata);
			}
			if("D".equals(savedata.get("row_status"))) 
			{
				savedata.put("del_yn", "Y");
				bizPcservice.deleteBizPcGrp(savedata);

			}
		}
		
		for(int i = 0 ; i< bizsavedatas.size();i++) 
		{
			Map<String, String> savedata = new HashMap<>();
			savedata = (Map<String, String>)bizsavedatas.get(i).get(0);
			savedata.put("SESS_USR_ID", userInfo.getUsrId());
			if("C".equals(savedata.get("row_status"))) 
			{
//				if(savedata.get("biz_pc_grp_cd") == "") {
//					savedata.put("biz_pc_grp_cd", key);
//				}
				bizPcservice.saveBizPc(savedata);
			}
			if("U".equals(savedata.get("row_status"))) 
			{
				bizPcservice.saveBizPc(savedata);
			}
			if("D".equals(savedata.get("row_status"))) 
			{
				savedata.put("del_yn", "Y");
				bizPcservice.deleteBizPc(savedata);
			}
		}
		
		return new ResponseEntity<>(Constant.SUCCESS, HttpStatus.OK);
	}
	
}
