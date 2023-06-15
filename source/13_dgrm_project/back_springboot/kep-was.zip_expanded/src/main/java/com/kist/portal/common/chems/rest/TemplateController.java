package com.kist.portal.common.chems.rest;

import com.kist.portal.common.chems.dto.TemplateDto;
import com.kist.portal.common.chems.service.TemplateService;
import com.kist.portal.common.login.model.UserInfo;
import com.kist.portal.common.utils.Constant;
import com.kist.portal.sysconf.manager.SessionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/common/chems")
public class TemplateController {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SessionManager sessionManager;

	@Autowired
	private TemplateService templateService;

	@GetMapping("/tmpt-list")
	public ResponseEntity<?> selectTemplateList(TemplateDto.param param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		param.setSessionLang(userInfo.getLangCd());

		List<TemplateDto.response> list = templateService.selectTemplateList(param);

		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@PostMapping("/tmpt")
	@Transactional
	public ResponseEntity<?> updateUserList(@RequestBody TemplateDto.insertParam param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());

		int n = 0;

		if (param != null ){
			param.setSessionUserId(userInfo.getUsrId());
			param.setSessionLangCd(userInfo.getLangCd());

			if (param.getTmptId() != null && !"".equals(param.getTmptId())) {
				n += templateService.updateTemplate(param);
			} else {
				n += templateService.insertTemplate(param);
			}
		}

		Map<String, String> resultMap = new HashMap<>();
		String result;
		result = (n > -1? Constant.SUCCESS : Constant.FAIL);
		resultMap.put("result", result);

		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}

	@PostMapping("/tmpt-cntn")
	public ResponseEntity<?> selectTemplateContent(@RequestBody TemplateDto.param param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		param.setSessionLang(userInfo.getLangCd());

		TemplateDto templateContent = templateService.selectTemplateContent(param);

		return new ResponseEntity<>(templateContent, HttpStatus.OK);
	}

}
