package com.kist.portal.test.user.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.kist.portal.common.utils.Constant;
import com.kist.portal.test.user.dto.TestUserDto;
import com.kist.portal.test.user.service.TestUserService;

@RestController
@RequestMapping("/api/v1/test/user")
public class TestUserController {
	
	@Autowired
	private TestUserService testUserService;
	
	@GetMapping("/list")
	public ResponseEntity<?> selectUserList(TestUserDto param) {
		
		System.out.println(param);
		
		List<TestUserDto> list = testUserService.selectTestUserList(param);
		return new ResponseEntity<>(list, HttpStatus.OK);
		
	}
	
	@PostMapping("/save")
	public ResponseEntity<?> saveUser(@RequestBody Map<String, String> param) {
		
		
		int n = 0;
		if("".equals(param.get("userKey")) || null==param.get("userKey")) {
			// 첫등록시
			
			// userId 중복체크
			int confirmResult = testUserService.idConfirm(param.get("userId"));
			if(confirmResult > 0) {
				return new ResponseEntity<>("Duplication", HttpStatus.OK);
			}else {
				// 중복없을시 등록 진행
				String key = testUserService.selectTestUserkey();
				param.put("userKey", key);
				n = testUserService.insertTestUser(param);
			}
			
		}else {
			// 유저 업데이트시
			n = testUserService.updateTestUser(param);
			
		}
		String result = n > -1? param.get("userKey") : Constant.FAIL;
		return new ResponseEntity<>(result, HttpStatus.OK);
		
	}
	
	@DeleteMapping("/delete/{userKey}")
	public ResponseEntity<?> deleteTestUser(@PathVariable String userKey) {
		
		int n = 0;
		
		TestUserDto dto = new TestUserDto();
		dto.setUserKey(userKey);
		List<TestUserDto> list = testUserService.selectTestUserList(dto);
		
		Map<String, String> param = new HashMap<>();
		param.put("userKey", userKey);
		param.put("userName", list.get(0).getUserName());
		param.put("userEnglishName", list.get(0).getUserEnglishName());
		
		n = testUserService.deleteTestUser(param);
		
		String result = (n > -1?  Constant.SUCCESS : Constant.FAIL);
		
		return new ResponseEntity<>(result, HttpStatus.OK);		
	}
	
	
	
	
	
	
	
}
