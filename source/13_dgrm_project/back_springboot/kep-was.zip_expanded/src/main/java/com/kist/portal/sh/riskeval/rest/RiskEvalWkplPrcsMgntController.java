package com.kist.portal.sh.riskeval.rest;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kist.portal.common.login.model.UserInfo;
import com.kist.portal.common.utils.Constant;
import com.kist.portal.sh.riskeval.service.RiskEvalWkplPrcsMgntService;
import com.kist.portal.sysconf.manager.SessionManager;

@RestController
@RequestMapping("/api/v1/she/riskeval")
public class RiskEvalWkplPrcsMgntController {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SessionManager sessionManager;
		
	@Autowired
	private RiskEvalWkplPrcsMgntService service;

	@GetMapping("/wkpl-dngp-list")
	public ResponseEntity<?> selectRiskEvalWkplDngpList(@RequestParam Map<String, String> param, HttpSession session) {

		List<Map<String, ?>> list = service.selectRiskEvalWkplDngpList(param);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@GetMapping("/wkplprcsmgnt")
	public ResponseEntity<?> selectRiskEvalWkplPrcsMgntList(@RequestParam Map<String, String> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());

		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_DEPT", userInfo.getDeptCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());

		List<Map<String, ?>> list = service.selectRiskEvalWkplPrcsMgntList(param);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@GetMapping("/wkplprcsmgnt/subcategory")
	public ResponseEntity<?> selectRiskEvalWkplPrcsMgnt(@RequestParam Map<String, String> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		
		param.put("SESS_LANG", userInfo.getLangCd());
		
		List<Map<String, ?>> list = service.selectRiskEvalWkplPrcsMgnt(param);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@PutMapping("/wkplprcsmgnt/subcategory")
	@Transactional
	public ResponseEntity<?> insertRiskEvalWkplPrcsMgntSubcategory(@RequestBody Map<String, String> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());

		param.put("SESS_USR_ID", userInfo.getUsrId());
		param.put("upp_dngp_id", param.get("mdcl_dngp_id"));
		param.put("dngp_nm", param.get("new_dngp_nm"));
		int n = 0;
		n *= service.insertRiskEvalWkplPrcsMgnt(param);

		String result = n > -1? Constant.SUCCESS : Constant.FAIL;
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@GetMapping("/wkplprcsmgnt/{id}")
	public ResponseEntity<?> selectDngpId(@PathVariable String id, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		Map<String, String> param = new HashMap<>();
		param.put("SESS_LANG", userInfo.getLangCd());
		
		String dngpId = service.selectDngpId(param);
		
		return new ResponseEntity<>(dngpId, HttpStatus.OK);
	}
	
	@PutMapping("/wkplprcsmgnt")
	@SuppressWarnings("unchecked")
	@Transactional
	public ResponseEntity<?> insertRiskEvalWkplPrcsMgnt(@RequestBody Map<String, Object> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());		
		param.put("SESS_USR_ID", userInfo.getUsrId());
		
		List<Map<String, String>> insertList = (List<Map<String, String>>)param.get("insertList");
		List<Map<String, String>> updateList = (List<Map<String, String>>)param.get("updateList");
		List<Map<String, String>> deleteList = (List<Map<String, String>>)param.get("deleteList");
		
		int n = 0;
		
		for(Map<String, String> map : insertList) {
			map.put("SESS_USR_ID", (String) param.get("SESS_USR_ID"));
			n *= service.insertRiskEvalWkplPrcsMgnt(map);
		}
		
		for(Map<String, String> map : updateList) {
			map.put("SESS_USR_ID", (String) param.get("SESS_USR_ID"));
			n *= service.updateRiskEvalWkplPrcsMgnt(map);
		}
		
		for(Map<String, String> map : deleteList) {
			map.put("SESS_USR_ID", (String) param.get("SESS_USR_ID"));
			n *= service.deleteRiskEvalWkplPrcsMgnt(map);
		}
		
		String result = n > -1? Constant.SUCCESS : Constant.FAIL;
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

}
