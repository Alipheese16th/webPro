package com.kist.portal.sh.htcrrm.rest;

import java.text.SimpleDateFormat;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kist.portal.common.comm.service.CommService;
import com.kist.portal.common.login.model.UserInfo;
import com.kist.portal.common.utils.Constant;
import com.kist.portal.sh.htcrrm.service.HtcrRmMgntService;
import com.kist.portal.sysconf.manager.SessionManager;

@RestController
@RequestMapping("/api/v1/she/hlth")
public class HtcrRmMgntController {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SessionManager sessionManager;
	
	@Autowired
	private HtcrRmMgntService service;
	
	@Autowired
	private CommService commService;
	
	@GetMapping("/htcrrm")
	public ResponseEntity<?> selectHtcrRmList(@RequestParam Map<String, String> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());

		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_DEPT", userInfo.getDeptCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());
		
		List<Map<String, ?>> list = service.selectHtcrRmList(param);
		
		if(list.size() > 0) {
			Map<String, String> logInfo = userInfo.getApiInfo();
			logInfo.put("result_cnt", String.valueOf(list.size()));
			logInfo.put("result_item", String.valueOf(list));
			
			SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
			Date dt = new Date();
			
			String dtT = formatter.format(dt);
			
			for(int i = 0; i < list.size(); i++) {
				logInfo.put("exe_dt", dtT);
				logInfo.put("result_cnt", String.valueOf(list.size()));
				Map<String, String> temp = new HashMap<String, String>();
				// 사번
				temp.put("hlth_maof_user_id", String.valueOf(list.get(i).get("hlth_maof_user_id")));
				// 성명
				temp.put("hlth_maof_user_nm", String.valueOf(list.get(i).get("hlth_maof_user_nm")));
				// 부서
				temp.put("org_nm", String.valueOf(list.get(i).get("org_nm")));
				logInfo.put("result_item", String.valueOf(temp));
				
				commService.insertPrivacyLog(logInfo);
			}
		}
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@PostMapping("/htcrrm")
	@Transactional
	public ResponseEntity<?> insertHtcrRm(@RequestBody Map<String, Object> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		
		param.put("SESS_USR_ID", userInfo.getUsrId());
		int n = service.insertHtcrRm(param);
		
		String result = n > -1? Constant.SUCCESS : Constant.FAIL;
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@DeleteMapping("/htcrrm/{id}")
	public ResponseEntity<?> deleteHtcrRm(@PathVariable String id , HttpSession session) {
		Map<String, String> param = new HashMap<>();
		param.put("hlth_maof_usg_no", id);
		
		int n = service.deleteHtcrRm(param);
		
		String result = (n > -1?  Constant.SUCCESS : Constant.FAIL);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

}
