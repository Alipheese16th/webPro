package com.kist.portal.common.chems.rest;

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

import com.kist.portal.common.chems.service.CdService;
import com.kist.portal.common.login.model.UserInfo;
import com.kist.portal.common.utils.Constant;
import com.kist.portal.sysconf.manager.SessionManager;

@RestController
@RequestMapping("/api/v1/common/chems")
public class CdController {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CdService service;
	
	@Autowired
	private SessionManager sessionManager;
	
	@GetMapping("/cd-grp")
	public ResponseEntity<?> selectCdGrpList(@RequestParam Map<String, String> param, HttpSession session) {
		// 언어 셋 지정
		UserInfo userInfo = sessionManager.get(session.getId());
		
		param.put("SESS_LANG", userInfo.getLangCd());
		List<Map<String, ?>> list = service.selectCdGrpList(param);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@GetMapping("/cd")
	public ResponseEntity<?> selectCdList(@RequestParam Map<String, String> param) {

		List<Map<String, ?>> list = service.selectCdList(param);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@PostMapping("/cd")
	@SuppressWarnings("unchecked")
	@Transactional
	public ResponseEntity<?> insertCdList(@RequestBody Map<String, Object> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
	
		//insert, update, delete 데이터 초기화
		List<Map<String, String>> insertGrpList = (List<Map<String, String>>)param.get("insertGrpRow");
		List<Map<String, String>> updateGrpList = (List<Map<String, String>>)param.get("updateGrpRow");
		List<Map<String, String>> deleteGrpList = (List<Map<String, String>>)param.get("deleteGrpRow");
		
		//insert, update, delete 데이터 초기화
		List<Map<String, String>> insertList = (List<Map<String, String>>)param.get("insertRow");
		List<Map<String, String>> updateList = (List<Map<String, String>>)param.get("updateRow");
		List<Map<String, String>> deleteList = (List<Map<String, String>>)param.get("deleteRow");
		
		// 인서트 처리
		System.out.println("인서트");
		String key = "";
		int m = 0;
		int n = 0;
		for(int i = 0; i < insertGrpList.size(); i++) {
			Map<String, String> temp = insertGrpList.get(i);
			temp.put("username", userInfo.getUsrId());
			System.out.println(temp);
			// 키 채번
			key = service.selectBizCdKey(temp);
			temp.put("cd_grp_no", key);
			// 인서트 쿼리
			m = service.insertCdGrpList(temp);
		}
		// 업데이트 처리
		System.out.println("업데이트");
		for(int i = 0; i < updateGrpList.size(); i++) {
			Map<String, String> temp = updateGrpList.get(i);
			temp.put("username", userInfo.getUsrId());
			System.out.println(temp);
			m = service.updateCdGrpList(temp);
		}
		// 삭제와 동시에 권한, 다국어 테이블도 함께 삭제 처리
		System.out.println("딜리트");
		for(int i = 0; i < deleteGrpList.size(); i++) {
			Map<String, String> temp = deleteGrpList.get(i);
			temp.put("username", userInfo.getUsrId());
			System.out.println(temp);
			// 코드 그룹 테이블에서 삭제
			service.deleteCdGrpList(temp);
			// 코드 테이블에서 삭제
			service.deleteCdList(temp);
			// 다국어 테이블에서 삭제
			m = service.deleteMlangAllCdList(temp);
		}
			
		// 인서트 처리
		System.out.println("인서트");
		for(int i = 0; i < insertList.size(); i++) {
			Map<String, String> temp = insertList.get(i);
			// 유효성 검사(이미 해당 메뉴 키를 가진 사용중인 메뉴가 있는지)
			// 해당 키를 가진 메뉴가 존재하지 않을 경우만
			// 다국어 키 조회
			String langKey = service.selectMlangCdGrpKey(temp);
			temp.put("username", userInfo.getUsrId());
			if(param.get("cd_grp_no") == null || "".equalsIgnoreCase(param.get("cd_grp_no").toString())){
				temp.put("cd_grp_no", key);
			}else {
				temp.put("cd_grp_no", param.get("cd_grp_no").toString());
			}
			// 다국어 기준이 될 언어 파라미터 설정
			temp.put("lang_cd", param.get("lang_cd").toString());
			// 채번해온 다국어 키 설정
			temp.put("mlang_key", langKey);
			// 코드 그룹의 업무 코드 설정
			temp.put("biz_cd", param.get("set_biz_cd").toString());
			System.out.println(temp);
			Map<String, ?> cdData = service.selectCdDetail(temp);
			if(cdData == null || cdData.isEmpty()) {
				// 코드 테이블 인서트
				service.insertCdList(temp);
				// 다국어 테이블 인서트
				n = service.insertCdMlangList(temp);
			} else {
				return new ResponseEntity<>("OverlapFail", HttpStatus.OK);
			}
		}
		// 업데이트 처리
		System.out.println("업데이트");
		for(int i = 0; i < updateList.size(); i++) {
			Map<String, String> temp = updateList.get(i);
			temp.put("username", userInfo.getUsrId());
			System.out.println(temp);
			temp.put("cd_grp_no", param.get("cd_grp_no").toString());
			// 다국어 기준이 될 언어 파라미터 설정
			temp.put("lang_cd", param.get("lang_cd").toString());
			// 코드 테이블 업데이트
			service.updateCdList(temp);
			// 다국어 테이블 업데이트
			n = service.updateCdMlangList(temp);
		}
		// 삭제와 동시에 권한도 함께 삭제 처리
		System.out.println("딜리트");
		for(int i = 0; i < deleteList.size(); i++) {
			Map<String, String> temp = deleteList.get(i);
			temp.put("username", userInfo.getUsrId());
			System.out.println(temp);
			temp.put("cd_grp_no", param.get("cd_grp_no").toString());
			// 코드 테이블 삭제
			service.deleteCd(temp);
			// 다국어 테이블 삭제
			n = service.deleteCdMlangList(temp);
		}
		
		String result = n > -1? (m > -1 ? Constant.SUCCESS : Constant.FAIL) : Constant.FAIL;
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
