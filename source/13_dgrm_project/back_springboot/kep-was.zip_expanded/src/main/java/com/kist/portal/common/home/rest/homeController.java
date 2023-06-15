package com.kist.portal.common.home.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kist.portal.common.home.service.homeService;
import com.kist.portal.common.login.model.UserInfo;
import com.kist.portal.sysconf.manager.SessionManager;

@RestController
@RequestMapping("/api/v1/common/home/")
public class homeController {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private homeService service;
	
	@Autowired
	private SessionManager sessionManager;
	
	@GetMapping("/main-list")
	public ResponseEntity<?> selectMainList(@RequestParam Map<String, String> param, HttpSession session) {
		// 언어 셋 지정
		UserInfo userInfo = sessionManager.get(session.getId());
		
		param.put("SESS_LANG", userInfo.getLangCd());
		
		// 공지사항 리스트(Top 3)
		List<Map<String, ?>> notiList = service.selectNotiList(param);
		// 규제 알림 리스트(Top 3)
		List<Map<String, ?>> alrmList = service.selectRgltAlrmList(param);
		// 대시보드 자재 갯수 조회
		List<Map<String, ?>> countList = service.selectMtrlCount(param);
		// 대시보드 순위 조회
		List<Map<String, ?>> rgltList = service.selectRgltCount(param);
		// 이용 문의 담당자 조회
		List<Map<String, ?>> useBizList = service.selectUseBizList(param);
		// 전월입고량, 연간누적입고량 조회
		List<Map<String, ?>> whList = service.selectWhList(param);
		// 규제물질 현황 및 비율 조회
		List<Map<String, ?>> rgltSbstList = service.selectRgltSbstList(param);
		// 이용 문의 담당자 조회(안전/보건)
		List<Map<String, ?>> useSafeList = service.selectUseSafeList(param);
		
		Map<String, List<Map<String, ?>>> data = new HashMap<>();
		data.put("notiList", notiList);
		data.put("alrmList", alrmList);
		data.put("countList", countList);
		data.put("rgltList", rgltList);
		data.put("useBizList", useBizList);
		data.put("whList", whList);
		data.put("rgltSbstList", rgltSbstList);
		data.put("useSafeList", useSafeList);
		
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
}
