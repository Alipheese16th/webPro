package com.kist.portal.sh.hmex.rest;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kist.portal.common.comm.service.CommService;
import com.kist.portal.common.login.model.UserInfo;
import com.kist.portal.common.utils.Constant;
import com.kist.portal.sh.hmex.service.HmexRsltService;
import com.kist.portal.sysconf.manager.SessionManager;

@RestController
@RequestMapping("/api/v1/she/hlth")
public class HmexRsltController {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SessionManager sessionManager;
	
	@Autowired
	private HmexRsltService service;
	
	@Autowired
	private CommService commService;
	
	@GetMapping("/hmexrslt")
	public ResponseEntity<?> selectHmexRsltList(@RequestParam Map<String, String> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());

		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_DEPT", userInfo.getDeptCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());
		
		List<Map<String, ?>> list = service.selectRsltList(param);
		
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
				temp.put("hmex_tgt_usr_id", String.valueOf(list.get(i).get("hmex_tgt_usr_id")));
				// 성명
				temp.put("full_hmex_trgp_nm", String.valueOf(list.get(i).get("full_hmex_trgp_nm")));
				// 부서
				temp.put("org_nm", String.valueOf(list.get(i).get("org_nm")));
				// 질병정보(판정1/소견1 등)
				if (list.get(i).get("hmex_ovr_opin_nm1") != null) {
					temp.put("hmex_ovr_opin_nm1", "질병정보1");
				}
				// 질병정보(판정1/소견1 등)
				if (list.get(i).get("hmex_ovr_opin_nm2") != null) {
					temp.put("hmex_ovr_opin_nm2", "질병정보2");
				}
				// 질병정보(판정1/소견1 등)
				if (list.get(i).get("hmex_ovr_opin_nm3") != null) {
					temp.put("hmex_ovr_opin_nm3", "질병정보3");
				}
				// 질병정보(판정1/소견1 등)
				if (list.get(i).get("hmex_ovr_opin_nm4") != null) {
					temp.put("hmex_ovr_opin_nm4", "질병정보4");
				}
				// 질병정보(판정1/소견1 등)
				if (list.get(i).get("hmex_ovr_opin_nm5") != null) {
					temp.put("hmex_ovr_opin_nm5", "질병정보5");
				}
				logInfo.put("result_item", String.valueOf(temp));
				
				commService.insertPrivacyLog(logInfo);
			}
		}
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@GetMapping("/hmexrslt/{id}")
	public ResponseEntity<?> selectHmexRslt(@PathVariable String id, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());

		Map<String, String> param = new HashMap<>();
		String[] arr = id.split("@");
		param.put("hmex_tgt_usr_id", arr[0]);
		if(arr.length > 1) {
			param.put("hmex_year", arr[1]);
		}
		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());

		Map<String, ?> map = service.selectRslt(param);
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	
	@PostMapping("/hmexrslt")
	@Transactional
	public ResponseEntity<?> insertHmexRslt(@RequestBody Map<String, Object> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		
		param.put("SESS_USR_ID", userInfo.getUsrId());
		int n = service.insertHmexRslt(param);
		
		String result = n > -1? Constant.SUCCESS : Constant.FAIL;
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PutMapping("/hmexrslt")
	@Transactional
	public ResponseEntity<?> deleteHmexRslt(@RequestBody Map<String, Object> param , HttpSession session) {
		Map<String, String> hmexrslt = new HashMap<>();
		List<Map<String, String>> deleteList = (List<Map<String, String>>)param.get("delRow");
		int n = 0;
		int m = 0;
		for(int i=0; i < deleteList.size(); i ++) {
			hmexrslt.put("hmex_year", deleteList.get(i).get("hmex_year"));
			hmexrslt.put("hmex_tgt_usr_id", deleteList.get(i).get("hmex_tgt_usr_id"));
			hmexrslt.put("hmex_sn", deleteList.get(i).get("hmex_sn"));
			n = service.deleteHmexRslt(hmexrslt);
			if (n == 1) {
				m++;
			}
		}
		
		
		String result = (m == deleteList.size()?  Constant.SUCCESS : Constant.FAIL);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

}
