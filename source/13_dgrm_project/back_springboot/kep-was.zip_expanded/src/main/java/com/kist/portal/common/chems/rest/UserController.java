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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kist.portal.common.chems.dto.UserDto;
import com.kist.portal.common.chems.service.UserService;
import com.kist.portal.common.login.model.UserInfo;
import com.kist.portal.common.utils.Constant;
import com.kist.portal.sysconf.manager.SessionManager;

@RestController
@RequestMapping("/api/v1/common/chems")
public class UserController {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SessionManager sessionManager;

	@Autowired
	private UserService userservice;

	/**
	 * 로그인 사용자가 속한 회사의 부서 리스트 조회
	 * (재귀 조회)
	 * @param param
	 * @return
	 */
	@GetMapping("/user/dept-tree-new")
	public ResponseEntity<List<UserDto>> selectDeptTreeListNew(@ModelAttribute UserDto param) {
		return new ResponseEntity<>(userservice.selectDeptTreeListNew(param), HttpStatus.OK);
	}
	@GetMapping("/user/dept-tree")
	public ResponseEntity<?> selectDeptTreeList(@RequestParam Map<String, String> param) {
		UserInfo userInfo = sessionManager.get();

		param.put("SESS_CMPN", userInfo.getCmpyCd());
		param.put("SESS_LANG", userInfo.getLangCd());
		List<Map<String, ?>> list = userservice.selectDeptTreeList(param);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	/**
	 * 로그인 사용자가 속한 회사의 부서 리스트 조회
	 * @param param
	 * @return
	 */
	@GetMapping("/user/dept-search-new")
	public ResponseEntity<List<UserDto>> selectDeptSearchListNew(@ModelAttribute UserDto param) {
		return new ResponseEntity<>(userservice.selectDeptSearchListNew(param), HttpStatus.OK);
	}
	@GetMapping("/user/dept-search")
	public ResponseEntity<?> selectDeptSearchList(@RequestParam(required=false) Map<String, String> param) {
		UserInfo userInfo = sessionManager.get();

		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_CMPY", userInfo.getCmpyCd());
		List<Map<String, ?>> list = userservice.selectDeptSearchList(param);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@GetMapping("/user/pop")
	public ResponseEntity<?> selectUserPopList(@RequestParam Map<String, String> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());

		param.put("SESS_LANG", userInfo.getLangCd());
		// 2022-03-17 김기정님 요청으로 회사코드 조건제거
		// param.put("SESS_CMPY", userInfo.getCmpyCd());
		List<Map<String, ?>> list = userservice.selectUserPopList(param);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@GetMapping("/user")
	public ResponseEntity<?> selectUserList(UserDto param) {

		List<UserDto> list = userservice.selectUserList(param);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@PostMapping("/user")
	@Transactional
	public ResponseEntity<?> updateUserList(@RequestBody Map<String, List<List<Map<String, String>>>> param,
			HttpSession session) {
		int n = 0;
		List<List<Map<String, String>>> savedatas = param.get("savedatas");
		for (int i = 0; i < savedatas.size(); i++) {
			Map<String, String> savedata = new HashMap<>();
			savedata = (Map<String, String>) savedatas.get(i).get(0);
			// update
			n = userservice.updateUserList(savedata);
		}

		Map<String, String> resultMap = new HashMap<>();
		String result;
		result = (n > -1 ? Constant.SUCCESS : Constant.FAIL);
		resultMap.put("result", result);

		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}

	@PostMapping("user/create")
	public void createUser(@RequestBody UserDto param) {
		userservice.createUser(param);
	}

	@PatchMapping("user/update")
	public void updateUser(@RequestBody UserDto param) {

		userservice.updateUser(param);
	}

	@PatchMapping("user/remove/{usr_id}")
	public void deleteUser(@PathVariable("usr_id") String usr_id) {
		userservice.deleteUser(usr_id);
	}

	/**
	 * [시스템관리/사용자조회 액션]
	 * 사용자에게 권한 다건 매핑하기
	 * @param param usr_id, role_no[]
	 * @author miri
	 * @version 2023-05-30 미구현 => 구현완료.
	 */
	@PostMapping("user/modify-role")
	public void modifyRole(@RequestBody UserDto param) {
		param.setCreateUserId(sessionManager.get().getUsrId());
		userservice.modifyRole(param);
	}

}
