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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kist.portal.common.chems.service.MenuService;
import com.kist.portal.common.login.model.UserInfo;
import com.kist.portal.common.utils.Constant;
import com.kist.portal.sysconf.manager.SessionManager;

@RestController
@RequestMapping("/api/v1/common/chems")
public class MenuController {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SessionManager sessionManager;
	
	@Autowired
	private MenuService service;
	
	@GetMapping("/menu")
	public ResponseEntity<?> selectMenuList(@RequestParam Map<String, String> param, HttpSession session) {
		// 언어 셋 지정
		UserInfo userInfo = sessionManager.get(session.getId());
		
		param.put("SESS_LANG", userInfo.getLangCd());
		List<Map<String, ?>> list = service.selectMenuList(param);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@PostMapping("/menu")
	@Transactional
	public ResponseEntity<?> insertMenu(@RequestBody Map<String, String> param, HttpSession session) {
		// 언어 셋 지정
		UserInfo userInfo = sessionManager.get(session.getId());
		String lang = userInfo.getLangCd();
		param.put("SESS_LANG", lang);
		
		String result;
		
		// 유효성 검사(이미 해당 메뉴 키를 가진 사용중인 메뉴가 있는지)
		Map<String, ?> menu = service.selectMenuDetail(param);
		// 해당 키를 가진 메뉴가 존재하지 않을 경우만
		if(menu == null || menu.isEmpty()) {
			// 다국어 키 채번(삭제된 메뉴가 있을 경우 해당 메뉴의 키를 들고옵니다.)
			String key = service.selectMlangMenuKey(param);
			param.put("mlang_key", key);
			
			// 메뉴 정보 insert(삭제된 메뉴가 있을경우 del_yn을 'N'으로 바꾸고 업데이트 처리해준다.)
			int n = service.insertMenu(param);
			// 메뉴명 다국어 insert(삭제된 다국어 정보가 있을경우 del_yn을 'N'으로 바꾸고 업데이트 처리해준다.)
			int m = service.insertMlangMenu(param);
			
			result = n > -1? (m > -1 ? Constant.SUCCESS : Constant.FAIL) : Constant.FAIL;
		} else {
			result = "OverlapFail";
		}
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PutMapping("/menu")
	@Transactional
	public ResponseEntity<?> updateMenu(@RequestBody Map<String, String> param, HttpSession session) {
		// 언어 셋 지정
		UserInfo userInfo = sessionManager.get(session.getId());
		
		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("username", userInfo.getUsrId());
		
		// 메뉴 정보 update
		int n = service.updateMenu(param);
		// 메뉴명 다국어 update
		int m = service.updateMlangMenu(param);
		
		String result = n > -1? (m > -1 ? Constant.SUCCESS : Constant.FAIL) : Constant.FAIL;
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@DeleteMapping("/menu/{menuNo}")
	@Transactional
	public ResponseEntity<?> deleteMenu(@PathVariable String menuNo, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		Map<String, String> param = new HashMap<>();
		param.put("menuNo", menuNo);
		param.put("username", userInfo.getUsrId());
		
		// 메뉴 테이블에서 삭제처리
		int n = service.deleteMenu(param);
		// 다국어 테이블에서 삭제처리
		int m = service.deleteMlangMenu(param);
		// 메뉴 권한 테이블에서 삭제함
		int o = service.deleteMenuRole(param);
		
		String result = n > -1? (m > -1 ? (o > -1 ? Constant.SUCCESS : Constant.FAIL) : Constant.FAIL) : Constant.FAIL;
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("/menu-role")
	public ResponseEntity<?> selectMenuRoleList(@RequestParam Map<String, String> param, HttpSession session) {
		// 언어 셋 지정
		UserInfo userInfo = sessionManager.get(session.getId());
		
		param.put("SESS_LANG", userInfo.getLangCd());
		List<Map<String, ?>> list = service.selectMenuRoleList(param);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@PostMapping("/menu-role")
	@SuppressWarnings("unchecked")
	@Transactional
	public ResponseEntity<?> insertMenuRole(@RequestBody Map<String, Object> param, HttpSession session) {
		// 언어 셋 지정
		UserInfo userInfo = sessionManager.get(session.getId());
		
		List<String> chkValues = (List<String>)param.get("chkValue");
		List<String> unChkValues = (List<String>)param.get("unChkValue");
	
		String temp;
		Map<String, String> parameter;
		
		int n = 0;
		int m = 0;
		
		for(int i = 0; i < chkValues.size(); i++) {
			temp = chkValues.get(i);
			parameter = new HashMap<String, String>();
			parameter.put("menuNo", param.get("menuNo").toString());
			parameter.put("roleNo", temp);
			parameter.put("username", userInfo.getUsrId());
			n = service.insertMenuRole(parameter); 
		}

		
		for(int i = 0; i < unChkValues.size(); i++) {
			temp = unChkValues.get(i);
			parameter = new HashMap<String, String>();
			parameter.put("menuNo", param.get("menuNo").toString());
			parameter.put("roleNo", temp);
			parameter.put("username", userInfo.getUsrId());
			m = service.updateMenuRole(parameter); 
		}
		
		String result = n > -1? (m > -1 ? Constant.SUCCESS : Constant.FAIL) : Constant.FAIL;
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
