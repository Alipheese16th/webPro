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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kist.portal.common.chems.service.MlangService;
import com.kist.portal.common.login.model.UserInfo;
import com.kist.portal.common.utils.Constant;
import com.kist.portal.sysconf.manager.SessionManager;

@RestController
@RequestMapping("/api/v1/common/chems")
public class MlangController {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SessionManager sessionManager;
	
	@Autowired
	private MlangService service;
	
	@GetMapping("/mlang")
	public ResponseEntity<?> selectMlangList(@RequestParam Map<String, String> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());

		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());

		List<Map<String, ?>> list = service.selectMlangList(param);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@PostMapping("/mlang")
	@Transactional
	public ResponseEntity<?> saveMlangList(@RequestBody Map<String, List<List<Map<String, String>>>> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());

		List<List<Map<String, String>>> savedatas = param.get("savedatas");
		for(int i = 0 ; i< savedatas.size();i++) 
		{
			Map<String, String> savedata = new HashMap<>();
			savedata = (Map<String, String>)savedatas.get(i).get(0);
			savedata.put("SESS_USR_ID", userInfo.getUsrId());
			if("C".equals(savedata.get("row_status"))) 
			{
				System.out.println("insert시작>>>");
				System.out.println(savedata);
				// 다국어 키 채번
				String key = "";
				key = service.selectMlangkey(savedata);
				savedata.put("mlang_no", key);
				savedata.put("role_no", "ZZZZZZZZZZZZZZZZZZZZ");
				savedata.put("menu_no", "ZZZZZZZZZZZZZZZZZZZZ");
				savedata.put("cd_grp_no", "ZZZZZZZZZZZZZZZZZZZZ");
				savedata.put("cd", "ZZZZZZZZZZZZZZZZZZZZ");
				// insert
				service.insertMlangList(savedata);
			}
			if("U".equals(savedata.get("row_status"))) 
			{
				System.out.println("update시작>>>");
				System.out.println(savedata);
				// update
				service.updateMlangList(savedata);
				
			}
			if("D".equals(savedata.get("row_status"))) 
			{
				System.out.println("delete시작>>>");
				System.out.println(savedata);
				savedata.put("del_yn", "Y");
				// delete
				service.deleteMlangList(savedata);
			}
		}
		
		return new ResponseEntity<>(Constant.SUCCESS, HttpStatus.OK);
	}
}
