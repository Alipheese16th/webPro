package com.kist.portal.ch.mro.rest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

import com.kist.portal.ch.mro.service.mroPchsService;
import com.kist.portal.common.aprv.service.AprvService;
import com.kist.portal.common.login.model.UserInfo;
import com.kist.portal.common.utils.Constant;
import com.kist.portal.common.utils.MultiLanguageUtil;
import com.kist.portal.sysconf.manager.SessionManager;

@RestController
@RequestMapping("/api/v1/ch/mro")
public class mroPchsController {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SessionManager sessionManager;
	
	@Value("${dgrm.front-url}")
	private String frontUrl;
	
	@Value("${dgrm.server-url}")
	private String serverUrl;
	
	@Autowired
	private mroPchsService service;
	
	@Autowired
	private AprvService aprvService;
	
	@Autowired
	private MultiLanguageUtil mUtil;
	
	@GetMapping("/mropchs-list")
	public ResponseEntity<?> selectMroList(@RequestParam Map<String, String> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());

		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());

		List<Map<String, ?>> list = service.selectMroList(param);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@GetMapping("/mropchs-pkg-list")
	public ResponseEntity<?> selectMroPkgList(@RequestParam Map<String, String> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());

		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());

		List<Map<String, ?>> list = service.selectMroPkgList(param);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@Transactional
	@SuppressWarnings("unchecked")
	@PostMapping("/mropchs")
	public ResponseEntity<?> insertmropchs(@RequestBody Map<String, Object> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		param.put("SESS_USR_ID", userInfo.getUsrId());
		param.put("SESS_USR_ID", userInfo.getUsrId());
		param.put("dept_cd", userInfo.getDeptCd());
		
		String chk = "SUCCESS";
		
		//mro 기본 데이터
		Map<String, String> mroSaveData = (Map<String, String>)param.get("mroSaveData");
		mroSaveData.put("dept_cd", userInfo.getDeptCd());
		
		//mro 자재 데이터
		Map<String, String> mtrlSaveData = (Map<String, String>)param.get("mtrlSaveData");
		
		//mro 자재 물질 데이터
		List<List<Map<String, String>>> sbstlist = (List<List<Map<String, String>>>)param.get("sbstlist");
		
		//mro 구매 표지 데이터
		Map<String, String> gwsgnSaveData = (Map<String, String>)param.get("gwsgnSaveData");
		
		//mro 구매 그림문자
		List<Map<String, String>> picList = (List<Map<String, String>>)param.get("pic");
		
		//저장 전에 유효성 검사한다.(진행상태가 20, 30이거나 저장 유저의 dept_cd 체크헤준다.)
		//최초 저장 시에는 체크 안함
		if("".equals(mroSaveData.get("mro_pchs_no")) || null == (mroSaveData.get("mro_pchs_no"))){
			chk = "SUCCESS";
		}else {
			if(param.get("dept_cd") == null) {
				chk = "FAIL";
			} else {
				chk = service.selectChkDeptYnMro(mroSaveData);
				if(chk == "READ") {
					chk = "FAIL";
				}
				else {
					chk = "SUCCESS";
				}
			}
		}
		int n = 0;
		int m = 0;
		int j = 0;
		int k = 0;
		int z = 0;
		String result = "Fail";
		
		//유효성 검사 통과 시에만 insert 처리
		if(chk != "FAIL") {
			//최초 저장 insert
			if("".equals(mroSaveData.get("mro_pchs_no")) || null == (mroSaveData.get("mro_pchs_no"))){
				//MRO구매번호 채번
				String key = "";
				key = service.selectMroPchskey(mroSaveData);
				mroSaveData.put("mro_pchs_no", key);
				mroSaveData.put("SESS_USR_ID", userInfo.getUsrId());
				param.put("SESS_USR_ID", userInfo.getUsrId());
				// mro 기본 정보 insert
				n = service.insertMroPchs(mroSaveData);
				
				
				mtrlSaveData.put("mro_pchs_no", key);
				mtrlSaveData.put("SESS_USR_ID", userInfo.getUsrId());
				mtrlSaveData.put("mtrl_no", "Z");
				mtrlSaveData.put("vndr_type_cd", "2");
				// mro 구매 자재 정보 insert
				m = service.insertMroPchsMtrl(mtrlSaveData);
				
				// 구매자재 물질 insert
				for(int i=0;i<sbstlist.size();i++) {
					Map<String, String> savedata = new HashMap<>();
					savedata = (Map<String, String>)sbstlist.get(i).get(0);
					savedata.put("SESS_USR_ID", userInfo.getUsrId());
					savedata.put("mro_pchs_no", key);
					savedata.put("mtrl_no", "Z");
					j = service.insertMroPchsMtrlSbst(savedata);
				}
				
				
				
				gwsgnSaveData.put("SESS_USR_ID", userInfo.getUsrId());
				gwsgnSaveData.put("mro_pchs_no", key);
				gwsgnSaveData.put("mtrl_no", "Z");
				gwsgnSaveData.put("vndr_type_cd", "2");

				// 경고 표지 update
				k = service.insertMroPchsGwsgn(gwsgnSaveData);
				
				
				// 그림 문자 초기화
				service.deleteMroPic(gwsgnSaveData);
				// 그림 문자
				for(int i = 0; i < picList.size(); i++) {
					Map<String, String> savedata = new HashMap<>();
					savedata = (Map<String, String>)picList.get(i);
					savedata.put("SESS_USR_ID", userInfo.getUsrId());
					savedata.put("mro_pchs_no", key);
					savedata.put("mtrl_no", "Z");
					z = service.insertMroPic(savedata);
				}
				
				result = (n > -1? (m > -1? (j > -1? (k > -1? (z > -1? (mroSaveData.get("mro_pchs_no").toString()) : Constant.FAIL) : Constant.FAIL) : Constant.FAIL) : Constant.FAIL) : Constant.FAIL);
			}else { //업데이트
				//MRO구매번호 채번
				mroSaveData.put("SESS_USR_ID", userInfo.getUsrId());
				// mro 기본 정보 insert
				n = service.insertMroPchs(mroSaveData);
				
				
				mtrlSaveData.put("mro_pchs_no", mroSaveData.get("mro_pchs_no"));
				mtrlSaveData.put("SESS_USR_ID", userInfo.getUsrId());
				mtrlSaveData.put("vndr_type_cd", "2");
				mtrlSaveData.put("mtrl_no", "Z");
				// mro 구매 자재 정보 update
				m = service.updateMroPchsMtrl(mtrlSaveData);
				
				// 구매자재 물질 insert
				for(int i=0;i<sbstlist.size();i++) {
					Map<String, String> savedata = new HashMap<>();
					savedata = (Map<String, String>)sbstlist.get(i).get(0);
					savedata.put("SESS_USR_ID", userInfo.getUsrId());
					savedata.put("mro_pchs_no", mroSaveData.get("mro_pchs_no"));
					savedata.put("mtrl_no", "Z");
					j = service.insertMroPchsMtrlSbst(savedata);
				}
				
				
				
				gwsgnSaveData.put("SESS_USR_ID", userInfo.getUsrId());
				gwsgnSaveData.put("mro_pchs_no", mroSaveData.get("mro_pchs_no"));
				gwsgnSaveData.put("mtrl_no", "Z");
				gwsgnSaveData.put("vndr_type_cd", "2");
				// 경고 표지 update
				k = service.insertMroPchsGwsgn(gwsgnSaveData);
				
				
				// 그림 문자 초기화
				service.deleteMroPic(gwsgnSaveData);
				// 그림 문자
				for(int i = 0; i < picList.size(); i++) {
					Map<String, String> savedata = new HashMap<>();
					savedata = (Map<String, String>)picList.get(i);
					savedata.put("SESS_USR_ID", userInfo.getUsrId());
					savedata.put("mro_pchs_no", mroSaveData.get("mro_pchs_no"));
					savedata.put("mtrl_no", "Z");
					z = service.insertMroPic(savedata);
				}
				result = (n > -1? (m > -1? (j > -1? (k > -1? (z > -1? (mroSaveData.get("mro_pchs_no")) : Constant.FAIL) : Constant.FAIL) : Constant.FAIL) : Constant.FAIL) : Constant.FAIL);
			}
		}else {
			result = "READ";
		}
		return new ResponseEntity<>(result, HttpStatus.OK);	
	}
	
	@GetMapping("/mropchs/{mropchsno}")
	public ResponseEntity<?> selectMropchsDtl(@PathVariable String mropchsno , HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());

		
		// 언어 셋 지정
		Map<String, String> param = new HashMap<>();
		param.put("mro_pchs_no", mropchsno);
		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_USR_ID", userInfo.getUsrId());
				
		//mro 기본 데이터
		Map<String, String> mroSaveData = service.selectMroPchsDtl(param);
		//mro 자재 데이터
		Map<String, String> mtrlSaveData = service.selectMroPchsMtrlDtl(param);
		//mro 자재 물질 데이터
		List<Map<String, String>> sbstlist = service.selectMroPchsMtrlSbstDtlList(param);
		//mro 구매 표지 데이터
		Map<String, String> gwsgnSaveData = service.selectMroPchsGwsgnDtl(param);
		//mro 구매 그림문자
		List<Map<String, String>> picList = service.selectMroPicDtlList(param);
		
		Map<String, Object> data = new HashMap<>();
		data.put("mroSaveData", mroSaveData);
		data.put("mtrlSaveData", mtrlSaveData);
		data.put("sbstlist", sbstlist);
		data.put("gwsgnSaveData", gwsgnSaveData);
		data.put("pic", picList);
		
		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	@GetMapping("/mropchs-mtrl-list")
	public ResponseEntity<?> selectMropchsMtrlList(@RequestParam Map<String, String> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());

		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_USR_ID", userInfo.getUsrId());
		
		//mro 기본 데이터
		List<Map<String, ?>> list = service.selectMroPchsMtrlList(param);

		Map<String, Object> data = new HashMap<>();
		data.put("mroMtrlList", list);
		
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	@DeleteMapping("/mropchs/{mropchsno}")
	public ResponseEntity<?> deleteMropchs(@PathVariable String mropchsno , HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());

		Map<String, String> param = new HashMap<>();
		param.put("mro_pchs_no", mropchsno);
		param.put("SESS_USR_ID", userInfo.getUsrId());
		
		int n = 0;
		int m = 0;
		int j = 0;
		int k = 0;
		int z = 0;

		// mro 기본 데이터 데이터 삭제
		n = service.deleteMroPchs(param);
		// mro 자재 데이터 삭제
		m = service.deleteMroPchsMtrl(param);
		// mro 자재 물질 데이터 삭제
		j = service.deleteMroPchsMtrlSbst(param);
		// mro 구매 표지 데이터 삭제
		k = service.deleteMroPchsGwsgn(param);
		// mro 구매 그림문자 삭제
		z = service.deleteMroPic(param);
		
		String result = (n > -1? (m > -1? (j > -1? (k > -1? (z > -1? Constant.SUCCESS : Constant.FAIL) : Constant.FAIL) : Constant.FAIL) : Constant.FAIL) : Constant.FAIL);

		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	@Transactional
	@SuppressWarnings("unchecked")
	@PostMapping("/mropchs-multi")
	public ResponseEntity<?> insertmropchsMulti(@RequestBody Map<String, Object> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		param.put("SESS_USR_ID", userInfo.getUsrId());
		param.put("dept_cd", userInfo.getDeptCd());
		
		String chk = "SUCCESS";
		
		//mro 기본 데이터
		Map<String, String> mroSaveData = (Map<String, String>)param.get("mroSaveData");
		mroSaveData.put("dept_cd", userInfo.getDeptCd());
		
		//mro 자재 데이터
		List<List<Map<String, String>>> mtrltlist = (List<List<Map<String, String>>>)param.get("savedatas");
		
		//저장 전에 유효성 검사한다.(진행상태가 20, 30이거나 저장 유저의 dept_cd 체크헤준다.)
		//최초 저장 시에는 체크 안함
		if("".equals(mroSaveData.get("mro_pchs_no")) || null == (mroSaveData.get("mro_pchs_no"))){
			chk = "SUCCESS";
		}else {
			if(param.get("dept_cd") == null) {
				chk = "FAIL";
			} else {
				chk = service.selectChkDeptYnMro(mroSaveData);
				if(chk == "READ") {
					chk = "FAIL";
				}
				else {
					chk = "SUCCESS";
				}
			}
		}
		int n = 0;
		int m = 0;
		String result = "Fail";
		
		//유효성 검사 통과 시에만 insert,update 처리
		if(chk != "FAIL") {
			//최초 저장 insert
			if("".equals(mroSaveData.get("mro_pchs_no")) || null == (mroSaveData.get("mro_pchs_no"))){
				//MRO구매번호 채번
				String key = "";
				key = service.selectMroPchskey(mroSaveData);
				mroSaveData.put("mro_pchs_no", key);
				mroSaveData.put("SESS_USR_ID", userInfo.getUsrId());
				// mro 기본 정보 insert
				n = service.insertMroPchs(mroSaveData);
				
				// 구매자재 물질 insert
				for(int i=0;i<mtrltlist.size();i++) {
					Map<String, String> savedata = new HashMap<>();
					savedata = (Map<String, String>)mtrltlist.get(i).get(0);
					savedata.put("SESS_USR_ID", userInfo.getUsrId());
					savedata.put("mro_pchs_no", key);
					m = service.insertMroPchsMultiMtrl(savedata);
				}
			}else { // update
				mroSaveData.put("SESS_USR_ID", userInfo.getUsrId());
				// mro 기본 정보 update
				n = service.insertMroPchs(mroSaveData);
				
				// 구매자재 물질 update
				for(int i=0;i<mtrltlist.size();i++) {
					Map<String, String> savedata = new HashMap<>();
					savedata = (Map<String, String>)mtrltlist.get(i).get(0);
					savedata.put("SESS_USR_ID", userInfo.getUsrId());

					if("C".equals(savedata.get("row_status"))) 
					{
						savedata.put("del_yn", "N");
						m = service.insertMroPchsMultiMtrl(savedata);
					}
					if("U".equals(savedata.get("row_status"))) 
					{
						savedata.put("del_yn", "N");
						m = service.insertMroPchsMultiMtrl(savedata);
					}
					if("D".equals(savedata.get("row_status"))) 
					{
						savedata.put("del_yn", "Y");
						m = service.deleteMroPchsMultiMtrl(savedata);
					}
				}
			}
		}
		
		result = (n > -1? (m > -1? (mroSaveData.get("mro_pchs_no")) : Constant.FAIL) : Constant.FAIL);

		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("/mropchs-multi/{mropchsno}")
	public ResponseEntity<?> selectMropchsMultiDtl(@PathVariable String mropchsno , HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());

		
		// 언어 셋 지정
		Map<String, String> param = new HashMap<>();
		param.put("mro_pchs_no", mropchsno);
		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_USR_ID", userInfo.getUsrId());
				
		//mro 기본 데이터
		Map<String, String> mroSaveData = service.selectMroPchsDtl(param);
		//mro 자재 데이터
		List<Map<String, String>> mtrlSaveList = service.selectMroPchsMultiMtrlList(param);
		
		Map<String, Object> data = new HashMap<>();
		data.put("mroSaveData", mroSaveData);
		data.put("mtrlSaveData", mtrlSaveList);
		
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	@DeleteMapping("/mropchs-multi/{mropchsno}")
	public ResponseEntity<?> deleteMropchsMulti(@PathVariable String mropchsno , HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());

		Map<String, String> param = new HashMap<>();
		param.put("mro_pchs_no", mropchsno);
		param.put("SESS_USR_ID", userInfo.getUsrId());
		
		int n = 0;
		int m = 0; 

		// mro 기본 데이터 데이터 삭제
		n = service.deleteMroPchs(param);
		// mro 자재 데이터 삭제
		m = service.deleteMroPchsMtrl(param);
		
		String result = (n > -1? (m > -1?  Constant.SUCCESS : Constant.FAIL) : Constant.FAIL) ;

		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@Transactional
	@SuppressWarnings("unchecked")
	@PostMapping("/mropchs-aprv")
	public ResponseEntity<?> aprvMropchs(@RequestBody Map<String, Object> saveParam, HttpSession session) {
		Map<String, String> mroSaveParam = (Map<String, String>)saveParam.get("mroSaveData");
		UserInfo userInfo = sessionManager.get(session.getId());
		
		Map<String, String> Searchparam = new HashMap<>();
		Searchparam.put("SESS_USR_ID", userInfo.getUsrId());
		Searchparam.put("SESS_LANG", userInfo.getLangCd());
		Searchparam.put("mro_pchs_no", mroSaveParam.get("mro_pchs_no"));

		//mro 기본 데이터
		Map<String, String> mroSaveData = service.selectMroPchsDtl(Searchparam);
		//mro 자재 데이터
		Map<String, String> mtrlSaveData = service.selectMroPchsMtrlDtl(Searchparam);
		//mro 자재 물질 데이터
		List<Map<String, String>> sbstlist = service.selectMroPchsMtrlSbstDtlList(Searchparam);
		//mro 구매 표지 데이터
		Map<String, String> gwsgnSaveData = service.selectMroPchsGwsgnDtl(Searchparam);
		//mro 구매 그림문자
		List<Map<String, String>> picList = service.selectMroPicDtlList(Searchparam);
		
		
		Map<String, Object> param = new HashMap<>();
//		결재 키 
//		String aprv_no = aprvService.selectAprvkey();
		String aprv_no = UUID.randomUUID().toString().replace("-", "");
		
		param.put("aprv_no",aprv_no);
//		결재 코드
		param.put("arpv_cd","20");
//		업무 번호
		param.put("biz_no",mroSaveData.get("mro_pchs_no"));
//		상신자
		param.put("aprv_usr_id",userInfo.getUsrId());
//		결재 상태 코드
		param.put("aprv_stt_cd","1");
//		후행메서드	
		param.put("aprv_mthd_nm","aprvRevMropchs");
//		결재 비고	
		param.put("aprv_note","");
//		등록자
		param.put("SESS_USR_ID", userInfo.getUsrId());
		
		// 다국어 처리
		String[] mlangNo = {"LB00000153", "LB00000151", "LB00000150",
				"LB00000149", "LB00000021", "LB00000159",
				"LB00000156", "LB00000169", "LB00000351",
				"LB00000507", "LB00000154", "LB00000023",
				"LB00000157", "LB00000355", "LB00000354",
				"LB00000353", "LB00000166", "LB00000158",
				"LB00000164", "LB00000163", "LB00000162",
				"LB00000161", "LB00000182", "LB00000181",
				"LB00000180", "LB00000179", "LB00000202",
				"LB00000203", "LB00000035", "LB00000177",
				"LB00000176", "LB00000026", "LB00000039",
				"LB00000040", "LB00000194", "LB00000196",
				 "LB00000195", "LB00000095", "LB00000025",
				 "LB00000173", "LB00000172", "LB00000193",
				 "LB00000192", "LB00000191", "LB00000190",
				 "LB00000045", "LB00000189", "LB00000188",
				 "LB00000165", "LB00000187", "LB00000130",
				 "LB00000186", "LB00000185", "LB00000184",
				 "LB00000183", "LB00000182", "LB00000181",
				 "LB00000180", "LB00000147", "LB00000175",
				 "LB00000038", "LB00000528", "LB00000527"};
		
		String langCd = userInfo.getLangCd();
		List<Map<String, String>> mlangCntn = mUtil.getMLangList(Arrays.asList(mlangNo), langCd);
		
		// 그림문자 html 태그
		String subTag = "";
		for(int i=0; i<picList.size(); i++) {
			if(picList.get(i).get("picg_cd").equalsIgnoreCase("1")) {
				subTag += "<p><img src=\"" + frontUrl + "/images/P01.png\" alt=\"\" class=\"\" style=\"background-repeat: no-repeat; box-sizing: inherit; padding: 0px; margin: 0px; float: left; border-style: none; width: 120px; color: rgb(68, 68, 68); font-family: arita-dotum; font-size: 13px; text-align: center; transition: none 0s ease 0s !important;\"></p>";
			} else if(picList.get(i).get("picg_cd").equalsIgnoreCase("2")) {
				subTag += "<p><img src=\"" + frontUrl + "/images/P02.png\" alt=\"\" class=\"\" style=\"background-repeat: no-repeat; box-sizing: inherit; padding: 0px; margin: 0px; float: left; border-style: none; width: 120px; color: rgb(68, 68, 68); font-family: arita-dotum; font-size: 13px; text-align: center; transition: none 0s ease 0s !important;\"></p>";
			} else if(picList.get(i).get("picg_cd").equalsIgnoreCase("3")) {
				subTag += "<p><img src=\"" + frontUrl + "/images/P03.png\" alt=\"\" class=\"\" style=\"background-repeat: no-repeat; box-sizing: inherit; padding: 0px; margin: 0px; float: left; border-style: none; width: 120px; color: rgb(68, 68, 68); font-family: arita-dotum; font-size: 13px; text-align: center; transition: none 0s ease 0s !important;\"></p>";
			} else if(picList.get(i).get("picg_cd").equalsIgnoreCase("4")) {
				subTag += "<p><img src=\"" + frontUrl + "/images/P04.png\" alt=\"\" class=\"\" style=\"background-repeat: no-repeat; box-sizing: inherit; padding: 0px; margin: 0px; float: left; border-style: none; width: 120px; color: rgb(68, 68, 68); font-family: arita-dotum; font-size: 13px; text-align: center; transition: none 0s ease 0s !important;\"></p>";
			} else if(picList.get(i).get("picg_cd").equalsIgnoreCase("5")) {
				subTag += "<p><img src=\"" + frontUrl + "/images/P05.png\" alt=\"\" class=\"\" style=\"background-repeat: no-repeat; box-sizing: inherit; padding: 0px; margin: 0px; float: left; border-style: none; width: 120px; color: rgb(68, 68, 68); font-family: arita-dotum; font-size: 13px; text-align: center; transition: none 0s ease 0s !important;\"></p>";
			} else if(picList.get(i).get("picg_cd").equalsIgnoreCase("6")) {
				subTag += "<p><img src=\"" + frontUrl + "/images/P06.png\" alt=\"\" class=\"\" style=\"background-repeat: no-repeat; box-sizing: inherit; padding: 0px; margin: 0px; float: left; border-style: none; width: 120px; color: rgb(68, 68, 68); font-family: arita-dotum; font-size: 13px; text-align: center; transition: none 0s ease 0s !important;\"></p>";
			} else if(picList.get(i).get("picg_cd").equalsIgnoreCase("7")) {
				subTag += "<p><img src=\"" + frontUrl + "/images/P07.png\" alt=\"\" class=\"\" style=\"background-repeat: no-repeat; box-sizing: inherit; padding: 0px; margin: 0px; float: left; border-style: none; width: 120px; color: rgb(68, 68, 68); font-family: arita-dotum; font-size: 13px; text-align: center; transition: none 0s ease 0s !important;\"></p>";
			} else if(picList.get(i).get("picg_cd").equalsIgnoreCase("8")) {
				subTag += "<p><img src=\"" + frontUrl + "/images/P08.png\" alt=\"\" class=\"\" style=\"background-repeat: no-repeat; box-sizing: inherit; padding: 0px; margin: 0px; float: left; border-style: none; width: 120px; color: rgb(68, 68, 68); font-family: arita-dotum; font-size: 13px; text-align: center; transition: none 0s ease 0s !important;\"></p>";
			} else if(picList.get(i).get("picg_cd").equalsIgnoreCase("9")) {
				subTag += "<p><img src=\"" + frontUrl + "/images/P09.png\" alt=\"\" class=\"\" style=\"background-repeat: no-repeat; box-sizing: inherit; padding: 0px; margin: 0px; float: left; border-style: none; width: 120px; color: rgb(68, 68, 68); font-family: arita-dotum; font-size: 13px; text-align: center; transition: none 0s ease 0s !important;\"></p>";
			}
		}
//		제목
		param.put("aprv_ttl",mUtil.getMLang(mlangCntn, "LB00000153"));
//		본문 html 소스
		String aprv_cntn = "<div>\r\n" + 
				"	<p style=\"font-weight: bold; font-size: 14pt;\">\r\n" + 
				"		<span style=\"font-weight: bold; font-size: 12pt;\">"+mUtil.getMLang(mlangCntn, "LB00000153")+"</span>\r\n" + 
				"	</p>\r\n" + 
				"	<table class=\"txc-table\" width=\"1551\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"border:none;border-collapse:collapse;word-break:break-all;font-family:&quot;Malgun Gothic&quot;, &quot;맑은 고딕&quot;;font-size:13.3333px\">\r\n" + 
				"	<tbody>\r\n" + 
				"	<tr>\r\n" + 
				"		<td style=\"width: 193px; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"			<p>\r\n" + 
				"				<span style=\"font-weight: bold;\">"+mUtil.getMLang(mlangCntn, "LB00000151")+"</span><br>\r\n" + 
				"			</p>\r\n" + 
				"		</td>\r\n" + 
				"		<td style=\"width: 281px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"			<p>\r\n" + 
				"				 "+mroSaveData.get("mro_pchs_no")+"\r\n" + 
				"			</p>\r\n" + 
				"		</td>\r\n" + 
				"		<td style=\"width: 172px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"			<p style=\"font-weight: bold;\">\r\n" + 
				"				<span style=\"font-weight: bold;\">"+mUtil.getMLang(mlangCntn, "LB00000150")+"</span>\r\n" + 
				"			</p>\r\n" + 
				"		</td>\r\n" + 
				"		<td style=\"width: 296px; height: 25px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\" colspan=\"3\">\r\n" + 
				"			<p>\r\n" + 
				"				<span style=\"font-size: 13.3333px;\">"+mroSaveData.get("upt_usr_nm")+"</span><br>\r\n" + 
				"			</p>\r\n" + 
				"		</td>\r\n" + 
				"		<td style=\"width: 203px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"			<p>\r\n" + 
				"				<span style=\"font-size: 13.3333px; font-weight: bold;\">"+mUtil.getMLang(mlangCntn, "LB00000149")+"</span><br>\r\n" + 
				"			</p>\r\n" + 
				"		</td>\r\n" + 
				"		<td style=\"width: 391px; height: 26px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\" colspan=\"2\">\r\n" + 
				"			<p>\r\n" + 
				"				<span style=\"font-size: 13.3333px;\">"+mroSaveData.get("upt_dt")+"</span><br>\r\n" + 
				"			</p>\r\n" + 
				"		</td>\r\n" + 
				"	</tr>\r\n" + 
				"	<tr>\r\n" + 
				"		<td style=\"width: 193px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"			<p style=\"font-weight: bold;\">\r\n" + 
				"				<span style=\"font-weight: bold;\">"+mUtil.getMLang(mlangCntn, "LB00000021")+"</span>\r\n" + 
				"			</p>\r\n" + 
				"		</td>\r\n" + 
				"		<td style=\"width: 281px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"			<p>\r\n" + 
				"				<span style=\"font-size: 13.3333px;\">"+mroSaveData.get("wkpl_id_nm")+"</span><br>\r\n" + 
				"			</p>\r\n" + 
				"		</td>\r\n" + 
				"		<td style=\"width: 172px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"			<p style=\"font-weight: bold;\">\r\n" + 
				"				<span style=\"font-weight: bold;\">"+mUtil.getMLang(mlangCntn, "LB00000159")+"</span>\r\n" + 
				"			</p>\r\n" + 
				"		</td>\r\n" + 
				"		<td style=\"width: 890px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\" colspan=\"6\">\r\n" + 
				"			<p>\r\n" + 
				"				<span style=\"font-size: 13.3333px;\">"+mtrlSaveData.get("mtrl_ko_nm")+"</span><br>\r\n" + 
				"			</p>\r\n" + 
				"		</td>\r\n" + 
				"	</tr>\r\n" + 
				"	<tr>\r\n" +
				"		<td style=\"width: 193px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"			<p style=\"font-weight: bold;\">\r\n" +
				"				<span style=\"font-weight: bold;\">"+mUtil.getMLang(mlangCntn, "LB00000527")+"</span>\r\n" + 
				"			</p>\r\n" + 
				"		</td>\r\n" + 
				"		<td style=\"width: 281px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"			<p>\r\n" + 
				"				<span style=\"font-size: 13.3333px;\">"+String.valueOf(mtrlSaveData.get("pchs_qty"))+"</span><br>\r\n" + 
				"			</p>\r\n" + 
				"		</td>\r\n" +
				"		<td style=\"width: 172px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"			<p style=\"font-weight: bold;\">\r\n" + 
				"				<span style=\"font-weight: bold;\">"+mUtil.getMLang(mlangCntn, "LB00000528")+"</span>\r\n" + 
				"			</p>\r\n" + 
				"		</td>\r\n" + 
				"		<td style=\"width: 890px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\" colspan=\"6\">\r\n" + 
				"			<p>\r\n" + 
				"				<span style=\"font-size: 13.3333px;\">"+ mtrlSaveData.get("pchs_unit_weght") + "&nbsp;" + mtrlSaveData.get("pchs_unit_nm")+"</span><br>\r\n" + 
				"			</p>\r\n" + 
				"		</td>\r\n" + 
				"	</tr>\r\n" + 
				"	<tr>\r\n" + 
				"		<td style=\"width: 193px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"			<p style=\"font-weight: bold;\">\r\n" + 
				"				<span style=\"font-weight: bold;\">"+mUtil.getMLang(mlangCntn, "LB00000023")+"</span>\r\n" + 
				"			</p>\r\n" + 
				"		</td>\r\n" + 
				"		<td style=\"width: 281px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"			<p>\r\n" + 
				"				<span style=\"font-size: 13.3333px;\">"+mtrlSaveData.get("macl_cd_nm")+"</span><br>\r\n" + 
				"			</p>\r\n" + 
				"		</td>\r\n" + 
				"		<td style=\"width: 172px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"			<p style=\"font-weight: bold;\">\r\n" + 
				"				<span style=\"font-weight: bold;\">"+mUtil.getMLang(mlangCntn, "LB00000157")+"</span>\r\n" + 
				"			</p>\r\n" + 
				"		</td>\r\n" + 
				"		<td style=\"width: 890px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\" colspan=\"6\">\r\n" + 
				"			<p style=\"font-weight: \">\r\n" + 
				"				<span style=\"font-size: 13.3333px;\">"+mtrlSaveData.get("impt_yn")+"</span><br>\r\n" + 
				"			</p>\r\n" + 
				"		</td>\r\n" + 
				"	</tr>\r\n" + 
				"	<tr>\r\n" + 
				"		<td style=\"width: 193px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"			<p style=\"font-weight: bold;\">\r\n" + 
				"				<span style=\"font-weight: bold;\">"+mUtil.getMLang(mlangCntn, "LB00000355")+"</span>\r\n" + 
				"			</p>\r\n" + 
				"		</td>\r\n" + 
				"		<td style=\"width: 281px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"			<p>\r\n" + 
				"				<span style=\"font-size: 13.3333px;\">"+mtrlSaveData.get("phase_cd_nm")+"</span><br>\r\n" + 
				"			</p>\r\n" + 
				"		</td>\r\n" + 
				"		<td style=\"width: 172px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"			<p>\r\n" + 
				"				<span style=\"font-weight: bold;\">"+mUtil.getMLang(mlangCntn, "LB00000354")+"</span>\r\n" + 
				"			</p>\r\n" + 
				"		</td>\r\n" + 
				"		<td style=\"width: 296px; height: 25px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\" colspan=\"3\">\r\n" + 
				"			<p>\r\n" + 
				"				<span style=\"font-size: 13.3333px;\">"+mtrlSaveData.get("mtrl_puse_cd_nm")+"</span><br>\r\n" + 
				"			</p>\r\n" + 
				"		</td>\r\n" + 
				"		<td style=\"width: 203px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"			<p style=\"font-weight: bold;\">\r\n" + 
				"				<span style=\"font-weight: bold;\">"+mUtil.getMLang(mlangCntn, "LB00000353")+"</span>\r\n" + 
				"			</p>\r\n" + 
				"		</td>\r\n" + 
				"		<td style=\"width: 391px; height: 26px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\" colspan=\"2\">\r\n" + 
				"			<p style=\"font-weight: \">\r\n" + 
				"				<span style=\"font-size: 13.3333px;\">"+mtrlSaveData.get("mtrl_puse_cntn")+"</span><br>\r\n" + 
				"			</p>\r\n" + 
				"		</td>\r\n" + 
				"	</tr>\r\n" + 
				"	<tr>\r\n" + 
				"		<td style=\"width: 193px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"			<p style=\"font-weight: bold;\">\r\n" + 
				"				<span style=\"font-weight: bold;\">"+mUtil.getMLang(mlangCntn, "LB00000166")+"</span>\r\n" + 
				"			</p>\r\n" + 
				"		</td>\r\n" + 
				"		<td style=\"width: 1357px; height: 25px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\" colspan=\"8\">\r\n" + 
				"			<p>\r\n" + 
				"				<span style=\"font-size: 13.3333px;\">";if(mtrlSaveData.get("vndr_kyin_yn").equals("Y")) {aprv_cntn += mUtil.getMLang(mlangCntn, "LB00000165");}else{aprv_cntn += mtrlSaveData.get("vndr_nm");} aprv_cntn+="</span><br>\r\n" + 
				"			</p>\r\n" + 
				"		</td>\r\n" + 
				"	</tr>\r\n";
				if(mtrlSaveData.get("vndr_kyin_yn").equals("Y")) {
					aprv_cntn +=
					"	<tr>\r\n" + 
					"		<td style=\"width: 193px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"			<p style=\"font-weight: bold;\">\r\n" + 
					"				<span style=\"font-weight: bold;\">"+mUtil.getMLang(mlangCntn, "LB00000158")+"</span>\r\n" + 
					"			</p>\r\n" + 
					"		</td>\r\n" + 
					"		<td style=\"width: 527px; height: 25px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\" colspan=\"3\">\r\n" + 
					"			<p>\r\n" + 
					"				<span style=\"font-size: 13.3333px;\">"+mtrlSaveData.get("vndr_nm")+"</span><br>\r\n" + 
					"			</p>\r\n" + 
					"		</td>\r\n" + 
					"		<td style=\"width: 214px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\" colspan=\"1\">\r\n" + 
					"			<p style=\"font-weight: bold;\">\r\n" + 
					"				<span style=\"font-weight: bold;\">"+mUtil.getMLang(mlangCntn, "LB00000164")+"</span>\r\n" + 
					"			</p>\r\n" + 
					"		</td>\r\n" + 
					"		<td style=\"width: 602px; height: 26px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\" colspan=\"4\">\r\n" + 
					"			<p>\r\n" + 
					"				<span style=\"font-size: 13.3333px;\">"+mtrlSaveData.get("vndr_addr")+"</span><br>\r\n" + 
					"			</p>\r\n" + 
					"		</td>\r\n" + 
					"	</tr>\r\n" + 
					"	<tr>\r\n" + 
					"		<td style=\"width: 193px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"			<p style=\"font-weight: bold;\">\r\n" + 
					"				<span style=\"font-weight: bold;\">"+mUtil.getMLang(mlangCntn, "LB00000163")+"</span>\r\n" + 
					"			</p>\r\n" + 
					"		</td>\r\n" + 
					"		<td style=\"width: 527px; height: 25px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\" colspan=\"3\">\r\n" + 
					"			<p>\r\n" + 
					"				<span style=\"font-size: 13.3333px;\">"+mtrlSaveData.get("nat_cd_nm")+"</span><br>\r\n" + 
					"			</p>\r\n" + 
					"		</td>\r\n" + 
					"		<td style=\"width: 214px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\" colspan=\"1\">\r\n" + 
					"			<p style=\"font-weight: bold;\">\r\n" + 
					"				<span style=\"font-weight: bold;\">"+mUtil.getMLang(mlangCntn, "LB00000162")+"</span>\r\n" + 
					"			</p>\r\n" + 
					"		</td>\r\n" + 
					"		<td style=\"width: 602px; height: 26px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\" colspan=\"4\">\r\n" + 
					"			<p>\r\n" + 
					"				<span style=\"font-size: 13.3333px;\">"+mtrlSaveData.get("vndr_phon_no")+"</span><br>\r\n" + 
					"			</p>\r\n" + 
					"		</td>\r\n" + 
					"	</tr>\r\n" + 
					"	<tr>\r\n" + 
					"		<td style=\"width: 193px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"			<p style=\"font-weight: bold;\">\r\n" + 
					"				<span style=\"font-weight: bold;\">"+mUtil.getMLang(mlangCntn, "LB00000161")+"</span>\r\n" + 
					"			</p>\r\n" + 
					"		</td>\r\n" + 
					"		<td style=\"width: 527px; height: 25px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\" colspan=\"3\">\r\n" + 
					"			<p>\r\n" + 
					"				<span style=\"font-size: 13.3333px;\">"+mtrlSaveData.get("vndr_rprs_nm")+"</span><br>\r\n" + 
					"			</p>\r\n" + 
					"		</td>\r\n" + 
					"		<td style=\"width: 214px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\" colspan=\"1\">\r\n" + 
					"			<p style=\"font-weight: bold;\">\r\n" + 
					"				<span style=\"font-weight: bold;\">"+mUtil.getMLang(mlangCntn, "LB00000182")+"</span>\r\n" + 
					"			</p>\r\n" + 
					"		</td>\r\n" + 
					"		<td style=\"width: 602px; height: 26px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\" colspan=\"4\">\r\n" + 
					"			<p>\r\n" + 
					"				<span style=\"font-size: 13.3333px;\">"+mtrlSaveData.get("vndr_coreg_no")+"</span><br>\r\n" + 
					"			</p>\r\n" + 
					"		</td>\r\n" + 
					"	</tr>\r\n" + 
					"	<tr>\r\n" + 
					"		<td style=\"width: 193px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\" rowspan=\"1\">\r\n" + 
					"			<p>\r\n" + 
					"				<span style=\"font-weight: bold;\">"+mUtil.getMLang(mlangCntn, "LB00000181")+"</span>\r\n" + 
					"			</p>\r\n" + 
					"		</td>\r\n" + 
					"		<td style=\"width: 527px; height: 25px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\" colspan=\"3\">\r\n" + 
					"			<p>\r\n" + 
					"				<span style=\"font-size: 13.3333px;\">"+mtrlSaveData.get("vndr_url")+"</span><br>\r\n" + 
					"			</p>\r\n" + 
					"		</td>\r\n" + 
					"		<td style=\"width: 214px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\" rowspan=\"1\" colspan=\"1\">\r\n" + 
					"			<p>\r\n" + 
					"				<span style=\"font-weight: bold;\">"+mUtil.getMLang(mlangCntn, "LB00000180")+"</span>\r\n" + 
					"			</p>\r\n" + 
					"		</td>\r\n" + 
					"		<td style=\"width: 602px; height: 26px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\" colspan=\"4\">\r\n" + 
					"			<p>\r\n" + 
					"				<span style=\"font-size: 13.3333px;\">"+mtrlSaveData.get("vndr_email")+"</span><br>\r\n" + 
					"			</p>\r\n" + 
					"		</td>\r\n" + 
					"	</tr>\r\n" ;
				}
				aprv_cntn +=
				"	</tbody>\r\n" + 
				"	</table>\r\n" + 
				"	<p>\r\n" + 
				"		<br>\r\n" + 
				"	</p>\r\n" + 
				"	<p style=\"font-size: 12pt; font-weight: bold;\">\r\n" + 
				"		<span style=\"font-size: 12pt; font-weight: bold;\">MSDS</span>\r\n" + 
				"	</p>\r\n" + 
				"	<table class=\"txc-table\" width=\"1551\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"border:none;border-collapse:collapse;word-break:break-all;font-family:&quot;Malgun Gothic&quot;, &quot;맑은 고딕&quot;;font-size:13.3333px\">\r\n" + 
				"	<tbody>\r\n" + 
				"	<tr>\r\n" + 
				"		<td style=\"width: 195px; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"			<p>\r\n" + 
				"				<span style=\"font-weight: bold;\">"+mUtil.getMLang(mlangCntn, "LB00000202")+"</span>\r\n" + 
				"			</p>\r\n" + 
				"		</td>\r\n" + 
				"		<td style=\"width: 536px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"			<p>\r\n" + 
				"				<span style=\"font-size: 13.3333px;\">"+mtrlSaveData.get("enact_da")+"</span><br>\r\n" + 
				"			</p>\r\n" + 
				"		</td>\r\n" + 
				"		<td style=\"width: 217px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"			<p style=\"font-weight: bold;\">\r\n" + 
				"				<span style=\"font-weight: bold;\">"+mUtil.getMLang(mlangCntn, "LB00000203")+"</span>\r\n" + 
				"			</p>\r\n" + 
				"		</td>\r\n" + 
				"		<td style=\"width: 602px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"			<p>\r\n" + 
				"				<span style=\"font-size: 13.3333px;\">"+mtrlSaveData.get("rev_da")+"</span><br>\r\n" + 
				"			</p>\r\n" + 
				"		</td>\r\n" + 
				"	</tr>\r\n" + 
				"	<tr>\r\n" + 
				"		<td style=\"width: 195px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"			<p style=\"font-weight: bold;\">\r\n" + 
				"				<span style=\"font-weight: bold;\">"+mUtil.getMLang(mlangCntn, "LB00000035")+"</span>\r\n" + 
				"			</p>\r\n" + 
				"		</td>\r\n" + 
				"		<td style=\"width: 1352px; height: 25px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\" colspan=\"3\">\r\n" + 
				"			<p>\r\n" + 
				"				<span style=\"font-size: 13.3333px;\">"+mtrlSaveData.get("msds_ver")+"</span><br>\r\n" + 
				"			</p>\r\n" + 
				"		</td>\r\n" + 
				"	</tr>\r\n" + 
				"	<tr>\r\n" + 
				"		<td style=\"width: 195px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"			<p>\r\n" + 
				"				<span style=\"font-weight: bold;\">"+mUtil.getMLang(mlangCntn, "LB00000177")+"</span>\r\n" + 
				"			</p>\r\n" + 
				"		</td>\r\n" + 
				"		<td style=\"width: 536px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"			<p>\r\n" + 
				"				<span style=\"font-size: 13.3333px;\">"+ "<a href=\"" + serverUrl + "/api/v1/public/download/" + mtrlSaveData.get("ko_atfl_no") + "\">" + mtrlSaveData.get("ko_atfl_nm") + "</a>"+"</span><br>\r\n" + 
				"			</p>\r\n" + 
				"		</td>\r\n" + 
				"		<td style=\"width: 217px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"			<p style=\"font-weight: bold;\">\r\n" +
				"				<span style=\"font-weight: bold;\">"+mUtil.getMLang(mlangCntn, "LB00000176")+"</span>\r\n" + 
				"			</p>\r\n" + 
				"		</td>\r\n" + 
				"		<td style=\"width: 602px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"			<p style=\"\">\r\n";
				
				if(!"".equals(mtrlSaveData.get("en_atfl_no")) || null != (mtrlSaveData.get("en_atfl_no"))) {
					aprv_cntn += " <span style=\"font-size: 13.3333px;\">"+ "<a href=\"" + serverUrl + "/api/v1/public/download/" + mtrlSaveData.get("en_atfl_no") + "\">" + mtrlSaveData.get("en_atfl_nm") + "</a>"+"</span><br>\r\n";
				}
				
				aprv_cntn +=
				"			</p>\r\n" + 
				"		</td>\r\n" + 
				"	</tr>\r\n" + 
				"	</tbody>\r\n" + 
				"	</table>\r\n" + 
				"	<p>\r\n" + 
				"		<br>\r\n" + 
				"	</p>\r\n" + 
				"	<p style=\"font-size: 12pt; font-weight: bold;\">\r\n" + 
				"		<span style=\"font-size: 12pt; font-weight: bold;\">"+mUtil.getMLang(mlangCntn, "LB00000175")+"</span>\r\n" + 
				"	</p>\r\n" + 
				"	<p>\r\n" + 
				"		 "+mUtil.getMLang(mlangCntn, "LB00000039")+"&nbsp;<span style=\"font-size: 13.3333px;\">"+sbstlist.size()+mUtil.getMLang(mlangCntn, "LB00000040")+"</span>\r\n" + 
				"	</p>\r\n" + 
				"	<table class=\"txc-table\" width=\"1551\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"border:none;border-collapse:collapse;word-break:break-all;font-family:&quot;Malgun Gothic&quot;, &quot;맑은 고딕&quot;;font-size:13.3333px\">\r\n" + 
				"	<tbody>\r\n" + 
				"	<tr>\r\n" + 
				"		<td style=\"width: 79px; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"			<p>\r\n" + 
				"				<span style=\"font-weight: bold;\">No.</span><br>\r\n" + 
				"			</p>\r\n" + 
				"		</td>\r\n" + 
				"		<td style=\"width: 234px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"			<p style=\"font-weight: bold;\">\r\n" + 
				"				<span style=\"font-weight: bold;\">"+mUtil.getMLang(mlangCntn, "LB00000194")+"</span>\r\n" + 
				"			</p>\r\n" + 
				"		</td>\r\n" + 
				"		<td style=\"width: 279px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"			<p style=\"font-weight: bold;\">\r\n" + 
				"				<span style=\"font-weight: bold;\">Cas No.</span>\r\n" + 
				"			</p>\r\n" + 
				"		</td>\r\n" + 
				"		<td style=\"width: 454px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"			<p style=\"font-weight: bold;\">\r\n" + 
				"				<span style=\"font-weight: bold;\">"+mUtil.getMLang(mlangCntn, "LB00000196")+"</span>\r\n" + 
				"			</p>\r\n" + 
				"		</td>\r\n" + 
				"		<td style=\"width: 211px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"			<p>\r\n" + 
				"				<span style=\"font-weight: bold;\">"+mUtil.getMLang(mlangCntn, "LB00000195")+"</span>\r\n" + 
				"			</p>\r\n" + 
				"		</td>\r\n" + 
				"		<td style=\"width: 355px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\" class=\"\" colspan=\"1\">\r\n" + 
				"			<p style=\"font-weight: bold;\">\r\n" + 
				"				<span style=\"font-weight: bold;\">"+mUtil.getMLang(mlangCntn, "LB00000095")+"</span>\r\n" + 
				"			</p>\r\n" + 
				"		</td>\r\n" + 
				"	</tr>\r\n";
				
				
				for(int i=0 ;i <sbstlist.size();i++) {
					aprv_cntn +=
						"	<tr>\r\n" + 
						"		<td style=\"width: 79px; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px;\" rowspan=\"1\">\r\n" + 
						"			<p>\r\n" + 
						"				<span style=\"font-size: 13.3333px;\">"+(i+1)+"</span><br>\r\n" + 
						"			</p>\r\n" + 
						"		</td>\r\n" + 
						"		<td style=\"width: 234px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\" rowspan=\"1\">\r\n" + 
						"			<p>\r\n" + 
						"				<span style=\"font-size: 13.3333px;\">"+sbstlist.get(i).get("sbst_type_nm")+"</span><br>\r\n" + 
						"			</p>\r\n" + 
						"		</td>\r\n" + 
						"		<td style=\"width: 279px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\" rowspan=\"1\">\r\n" + 
						"			<p>\r\n" + 
						"				<span style=\"font-size: 13.3333px;\">"+sbstlist.get(i).get("cas_no")+"</span><br>\r\n" + 
						"			</p>\r\n" + 
						"		</td>\r\n" + 
						"		<td style=\"width: 454px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\" rowspan=\"1\">\r\n" + 
						"			<p>\r\n" + 
						"				<span style=\"font-size: 13.3333px;\">"+sbstlist.get(i).get("sbst_ko_nm")+"</span><br>\r\n" + 
						"			</p>\r\n" + 
						"		</td>\r\n" + 
						"		<td style=\"width: 211px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\" rowspan=\"1\">\r\n" + 
						"			<p>\r\n" + 
						"				<span style=\"font-size: 13.3333px;\">"+String.valueOf(sbstlist.get(i).get("sbst_rati"))+"</span><br>\r\n" + 
						"			</p>\r\n" + 
						"		</td>\r\n" + 
						"		<td style=\"width: 355px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\" rowspan=\"1\" class=\"\" colspan=\"1\">\r\n" + 
						"			<p>\r\n" + 
						"				<span style=\"font-size: 13.3333px;\">"+sbstlist.get(i).get("sbst_note")+"</span><br>\r\n" + 
						"			</p>\r\n" + 
						"		</td>\r\n" + 
						"	</tr>\r\n" ;
				}
				aprv_cntn +=		
				"	</tbody>\r\n" + 
				"	</table>\r\n" +
				"	<p>\r\n" + 
				"		<br>\r\n" + 
				"	</p>\r\n" +  
				"	<p style=\"font-size: 12pt; font-weight: bold;\">\r\n" + 
				"		<span style=\"font-size: 12pt; font-weight: bold;\">"+mUtil.getMLang(mlangCntn, "LB00000038")+"</span>\r\n" + 
				"	</p>\r\n" + 
				"	<table class=\"txc-table\" width=\"1551\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"border:none;border-collapse:collapse;word-break:break-all;font-family:&quot;Malgun Gothic&quot;, &quot;맑은 고딕&quot;;font-size:13.3333px\">\r\n" + 
				"	<tbody>\r\n" + 
				"	<tr>\r\n" + 
				"		<td style=\"width: 194px; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"			<p>\r\n" + 
				"				<span style=\"font-weight: bold;\">"+mUtil.getMLang(mlangCntn, "LB00000025")+"</span><br>\r\n" + 
				"			</p>\r\n" + 
				"		</td>\r\n" + 
				"		<td style=\"width: 1356px; height: 25px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\" colspan=\"4\">\r\n" + 
				"			<p>\r\n" + 
				"				<span style=\"font-size: 13.3333px;\">"+mtrlSaveData.get("mtrl_ko_nm")+"</span><br>\r\n" + 
				"			</p>\r\n" + 
				"		</td>\r\n" + 
				"	</tr>\r\n" + 
				"	<tr>\r\n" + 
				"		<td style=\"width: 194px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"			<p style=\"font-weight: bold;\">\r\n" + 
				"				<span style=\"font-weight: bold;\">"+mUtil.getMLang(mlangCntn, "LB00000173")+"</span><br>\r\n" + 
				"			</p>\r\n" + 
				"		</td>\r\n" + 
				"		<td style=\"width: 1356px; height: 25px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\" colspan=\"4\">\r\n" + 
				"			<p>\r\n" + 
				"				<span style=\"font-size: 13.3333px;\">"+gwsgnSaveData.get("sgw_nm")+"</span><br>\r\n" + 
				"			</p>\r\n" + 
				"		</td>\r\n" + 
				"	</tr>\r\n" + 
				"	<tr>\r\n" + 
				"		<td style=\"width: 194px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"			<p style=\"font-weight: bold;\">\r\n" + 
				"				<span style=\"font-weight: bold;\">"+mUtil.getMLang(mlangCntn, "LB00000172")+"</span><br>\r\n" + 
				"			</p>\r\n" + 
				"		</td>\r\n" + 
				"		<td style=\"width: 1356px; height: 25px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\" colspan=\"4\">\r\n" + 
				subTag + 
				"		</td>\r\n" + 
				"	</tr>\r\n" + 
				"	<tr>\r\n" + 
				"		<td style=\"width: 194px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"			<p style=\"font-weight: bold;\">\r\n" + 
				"				<span style=\"font-weight: bold;\">"+mUtil.getMLang(mlangCntn, "LB00000193")+"</span><br>\r\n" + 
				"			</p>\r\n" + 
				"		</td>\r\n" + 
				"		<td style=\"width: 1356px; height: 25px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\" colspan=\"4\">\r\n" + 
				"			<p>\r\n" + 
				"				<span style=\"font-size: 13.3333px;\">"+gwsgnSaveData.get("hars_cntn")+"</span><br>\r\n" + 
				"			</p>\r\n" + 
				"		</td>\r\n" + 
				"	</tr>\r\n" + 
				"	<tr>\r\n" + 
				"		<td style=\"width: 188px; height: 107px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\" rowspan=\"4\">\r\n" + 
				"			<p style=\"font-weight: bold;\">\r\n" + 
				"				<span style=\"font-weight: bold;\">"+mUtil.getMLang(mlangCntn, "LB00000192")+"</span><br>\r\n" + 
				"			</p>\r\n" + 
				"		</td>\r\n" + 
				"		<td style=\"width: 136px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"			<p style=\"font-weight: bold;\">\r\n" + 
				"				<span style=\"font-weight: bold;\">"+mUtil.getMLang(mlangCntn, "LB00000191")+"</span><br>\r\n" + 
				"			</p>\r\n" + 
				"		</td>\r\n" + 
				"		<td style=\"width: 1226px; height: 26px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\" colspan=\"3\">\r\n" + 
				"			<p>\r\n" + 
				"				<span style=\"font-size: 13.3333px;\">"+gwsgnSaveData.get("prv_cntn")+"</span><br>\r\n" + 
				"			</p>\r\n" + 
				"		</td>\r\n" + 
				"	</tr>\r\n" + 
				"	<tr>\r\n" + 
				"		<td style=\"width: 136px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"			<p style=\"font-weight: bold;\">\r\n" + 
				"				<span style=\"font-weight: bold;\">"+mUtil.getMLang(mlangCntn, "LB00000190")+"</span><br>\r\n" + 
				"			</p>\r\n" + 
				"		</td>\r\n" + 
				"		<td style=\"width: 1226px; height: 26px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\" colspan=\"3\">\r\n" + 
				"			<p>\r\n" + 
				"				<span style=\"font-size: 13.3333px;\">"+gwsgnSaveData.get("act_cntn")+"</span><br>\r\n" + 
				"			</p>\r\n" + 
				"		</td>\r\n" + 
				"	</tr>\r\n" + 
				"	<tr>\r\n" + 
				"		<td style=\"width: 136px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"			<p style=\"font-weight: bold;\">\r\n" + 
				"				<span style=\"font-weight: bold;\">"+mUtil.getMLang(mlangCntn, "LB00000045")+"</span><br>\r\n" + 
				"			</p>\r\n" + 
				"		</td>\r\n" + 
				"		<td style=\"width: 1226px; height: 26px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\" colspan=\"3\">\r\n" + 
				"			<p>\r\n" + 
				"				<span style=\"font-size: 13.3333px;\">"+gwsgnSaveData.get("str_cntn")+"</span><br>\r\n" + 
				"			</p>\r\n" + 
				"		</td>\r\n" + 
				"	</tr>\r\n" + 
				"	<tr>\r\n" + 
				"		<td style=\"width: 136px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"			<p>\r\n" + 
				"				<span style=\"font-weight: bold;\">"+mUtil.getMLang(mlangCntn, "LB00000189")+"</span><br>\r\n" + 
				"			</p>\r\n" + 
				"		</td>\r\n" + 
				"		<td style=\"width: 1226px; height: 25px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\" colspan=\"3\">\r\n" + 
				"			<p>\r\n" + 
				"				<span style=\"font-size: 13.3333px;\">"+gwsgnSaveData.get("scr_cntn")+"</span><br>\r\n" + 
				"			</p>\r\n" + 
				"		</td>\r\n" + 
				"	</tr>\r\n" + 
				"	<tr>\r\n" + 
				"		<td style=\"width: 194px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"			<p style=\"font-weight: bold;\">\r\n" + 
				"				<span style=\"font-weight: bold;\">"+mUtil.getMLang(mlangCntn, "LB00000188")+"</span><br>\r\n" + 
				"			</p>\r\n" + 
				"		</td>\r\n" + 
				"		<td style=\"width: 136px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"			<p>\r\n"; 
				if(gwsgnSaveData.get("vndr_kyin_yn").equals("Y")) {aprv_cntn += mUtil.getMLang(mlangCntn, "LB00000165");}else{aprv_cntn += gwsgnSaveData.get("vndr_nm");} aprv_cntn+=
				"			</p>\r\n" + 
				"		</td>\r\n" + 
				"		<td style=\"width: 1223px; height: 26px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\" colspan=\"3\">\r\n" + 
				"			<p>\r\n" ;
				if(gwsgnSaveData.get("vndr_same_yn").equals("Y")) {aprv_cntn += mUtil.getMLang(mlangCntn, "LB00000187") + " Y";}else{aprv_cntn += mUtil.getMLang(mlangCntn, "LB00000187") + " N";} aprv_cntn+=
				"			</p>\r\n" + 
				"		</td>\r\n" + 
				"	</tr>\r\n" + 
				"	<tr>\r\n" ;
				if(mtrlSaveData.get("vndr_kyin_yn").equals("Y")) {
					aprv_cntn +=
					"		<td style=\"width: 194px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"			<p style=\"font-weight: bold;\">\r\n" + 
					"				<span style=\"font-weight: bold;\">"+mUtil.getMLang(mlangCntn, "LB00000130")+"</span><br>\r\n" + 
					"			</p>\r\n" + 
					"		</td>\r\n" + 
					"		<td style=\"width: 523px; height: 25px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\" colspan=\"2\">\r\n" + 
					"			<p>\r\n" + 
					"				<span style=\"font-size: 13.3333px;\">"+gwsgnSaveData.get("vndr_nm")+"</span><br>\r\n" + 
					"			</p>\r\n" + 
					"		</td>\r\n" + 
					"		<td style=\"width: 216px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"			<p style=\"font-weight: bold;\">\r\n" + 
					"				<span style=\"font-weight: bold;\">"+mUtil.getMLang(mlangCntn, "LB00000186")+"</span><br>\r\n" + 
					"			</p>\r\n" + 
					"		</td>\r\n" + 
					"		<td style=\"width: 603px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
					"			<p>\r\n" + 
					"				<span style=\"font-size: 13.3333px;\">"+gwsgnSaveData.get("vndr_addr")+"</span><br>\r\n" + 
					"			</p>\r\n" + 
					"		</td>\r\n" + 
					"	</tr>\r\n" + 
					"	<tr>\r\n" + 
					"		<td style=\"width: 194px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"			<p style=\"font-weight: bold;\">\r\n" + 
					"				<span style=\"font-weight: bold;\">"+mUtil.getMLang(mlangCntn, "LB00000185")+"</span><br>\r\n" + 
					"			</p>\r\n" + 
					"		</td>\r\n" + 
					"		<td style=\"width: 523px; height: 25px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\" colspan=\"2\">\r\n" + 
					"			<p>\r\n" + 
					"				<span style=\"font-size: 13.3333px;\">"+gwsgnSaveData.get("nat_cd_nm")+"</span><br>\r\n" + 
					"			</p>\r\n" + 
					"		</td>\r\n" + 
					"		<td style=\"width: 216px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"			<p style=\"font-weight: bold;\">\r\n" + 
					"				<span style=\"font-weight: bold;\">"+mUtil.getMLang(mlangCntn, "LB00000184")+"</span><br>\r\n" + 
					"			</p>\r\n" + 
					"		</td>\r\n" + 
					"		<td style=\"width: 603px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
					"			<p>\r\n" + 
					"				<span style=\"font-size: 13.3333px;\">"+gwsgnSaveData.get("vndr_phon_no")+"</span><br>\r\n" + 
					"			</p>\r\n" + 
					"		</td>\r\n" + 
					"	</tr>\r\n" + 
					"	<tr>\r\n" + 
					"		<td style=\"width: 194px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"			<p style=\"font-weight: bold;\">\r\n" + 
					"				<span style=\"font-weight: bold;\">"+mUtil.getMLang(mlangCntn, "LB00000183")+"</span><br>\r\n" + 
					"			</p>\r\n" + 
					"		</td>\r\n" + 
					"		<td style=\"width: 523px; height: 25px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\" colspan=\"2\">\r\n" + 
					"			<p>\r\n" + 
					"				<span style=\"font-size: 13.3333px;\">"+gwsgnSaveData.get("vndr_rprs_nm")+"</span><br>\r\n" + 
					"			</p>\r\n" + 
					"		</td>\r\n" + 
					"		<td style=\"width: 216px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"			<p style=\"font-weight: bold;\">\r\n" + 
					"				<span style=\"font-weight: bold;\">"+mUtil.getMLang(mlangCntn, "LB00000182")+"</span><br>\r\n" + 
					"			</p>\r\n" + 
					"		</td>\r\n" + 
					"		<td style=\"width: 603px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
					"			<p>\r\n" + 
					"				<span style=\"font-size: 13.3333px;\">"+gwsgnSaveData.get("vndr_coreg_no")+"</span><br>\r\n" + 
					"			</p>\r\n" + 
					"		</td>\r\n" + 
					"	</tr>\r\n" + 
					"	<tr>\r\n" + 
					"		<td style=\"width: 194px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"			<p>\r\n" + 
					"				<span style=\"font-weight: bold;\">"+mUtil.getMLang(mlangCntn, "LB00000181")+"</span><br>\r\n" + 
					"			</p>\r\n" + 
					"		</td>\r\n" + 
					"		<td style=\"width: 523px; height: 25px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\" colspan=\"2\">\r\n" + 
					"			<p>\r\n" + 
					"				<span style=\"font-size: 13.3333px;\">"+gwsgnSaveData.get("vndr_url")+"</span><br>\r\n" + 
					"			</p>\r\n" + 
					"		</td>\r\n" + 
					"		<td style=\"width: 216px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					"			<p>\r\n" + 
					"				<span style=\"font-weight: bold;\">"+mUtil.getMLang(mlangCntn, "LB00000180")+"</span><br>\r\n" + 
					"			</p>\r\n" + 
					"		</td>\r\n" + 
					"		<td style=\"width: 603px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
					"			<p>\r\n" + 
					"				<span style=\"font-size: 13.3333px;\">"+gwsgnSaveData.get("vndr_email")+"</span><br>\r\n" + 
					"			</p>\r\n" + 
					"		</td>\r\n" ; 
				}
				aprv_cntn +=
				"	</tr>\r\n" + 
				"	</tbody>\r\n" + 
				"	</table>\r\n" + 
				"	<p>\r\n" + 
				"		<span style=\"font-size: 13.3333px;\"><br>\r\n" + 
				"		</span>\r\n" + 
				"	</p>\r\n" + 
				"	<p>\r\n" + 
				"		<span style=\"font-size: 13.3333px;\"><br>\r\n" + 
				"		</span>\r\n" + 
				"	</p>\r\n" + 
				"</div>";
		param.put("aprv_cntn",aprv_cntn);
		// 결제 정보 table insert 
		int n = aprvService.insertAprv(param);
		
		
		// mro 구매건에 결재번호,생성일시 insert , 진행상태 변경
		Map<String, Object> data = new HashMap<>();
		if(n > -1) {
			param.put("mro_pchs_no",mroSaveData.get("mro_pchs_no"));
			n = service.MroPchsAprvIng(param);
			
			data.put("Success", "Success");
			data.put("aprvNo", param.get("aprv_no"));
			data.put("usrId", userInfo.getUsrId());
		}
		
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	@Transactional
	@SuppressWarnings("unchecked")
	@PostMapping("/mropchs-multi-aprv")
	public ResponseEntity<?> aprvMropchsMulti(@RequestBody Map<String, Object> saveParam, HttpSession session) {
		Map<String, String> mroSaveParam = (Map<String, String>)saveParam.get("mroSaveData");
		UserInfo userInfo = sessionManager.get(session.getId());
		
		// 언어 셋 지정
		Map<String, String> Searchparam = new HashMap<>();
		Searchparam.put("mro_pchs_no", mroSaveParam.get("mro_pchs_no"));
		Searchparam.put("SESS_LANG", userInfo.getLangCd());
		Searchparam.put("SESS_USR_ID", userInfo.getUsrId());
				
		//mro 기본 데이터
		Map<String, String> mroSaveData = service.selectMroPchsDtl(Searchparam);
		//mro 자재 데이터
		List<Map<String, String>> mtrlSaveList = service.selectMroPchsMultiMtrlList(Searchparam);
		
		
		Map<String, Object> param = new HashMap<>();
//		결재 키 
//		String aprv_no = aprvService.selectAprvkey(param);
		String aprv_no = UUID.randomUUID().toString().replace("-", "");

		param.put("aprv_no",aprv_no);
//		결재 코드
		param.put("arpv_cd","20");
//		업무 번호
		param.put("biz_no",mroSaveData.get("mro_pchs_no"));
//		상신자
		param.put("aprv_usr_id",userInfo.getUsrId());
//		결재 상태 코드
		param.put("aprv_stt_cd","1");
//		후행메서드	
		param.put("aprv_mthd_nm","aprvRevMropchsMulti");
//		결재 비고	
		param.put("aprv_note","");
//      유저 정보
		param.put("SESS_USR_ID", userInfo.getUsrId());
		
		
		// 다국어 처리
		String[] mlangNo = {"LB00000153", "LB00000151", "LB00000150",
				 "LB00000160", "LB00000159", "LB00000158",
				 "LB00000157", "LB00000156", "LB00000155",
				 "LB00000154", "LB00000147", "LB00000149",
				 "LB00000021", "LB00000148", "LB00000039",
				 "LB00000040", "LB00000528", "LB00000600"};
		
		String langCd = userInfo.getLangCd();
		List<Map<String, String>> mlangCntn = mUtil.getMLangList(Arrays.asList(mlangNo), langCd);
		
		
//		제목
		param.put("aprv_ttl",mUtil.getMLang(mlangCntn, "LB00000153"));
//		본문 html 소스
		String aprv_cntn = "<div>\r\n" + 
				"	<p style=\"font-size: 12pt; font-weight: bold;\">\r\n" + 
				"		<span style=\"font-size: 12pt; font-weight: bold;\">"+mUtil.getMLang(mlangCntn, "LB00000153")+"</span>\r\n" + 
				"	</p>\r\n" + 
				"	<table class=\"txc-table\" width=\"1551\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"border:none;border-collapse:collapse;word-break:break-all;font-family:&quot;Malgun Gothic&quot;, &quot;맑은 고딕&quot;;font-size:13.3333px\">\r\n" + 
				"	<tbody>\r\n" + 
				"	<tr>\r\n" + 
				"		<td style=\"width: 176px; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"			<p style=\"font-weight: bold;\">\r\n" + 
				"				<span style=\"font-weight: bold;\">"+mUtil.getMLang(mlangCntn, "LB00000151")+"</span>\r\n" + 
				"			</p>\r\n" + 
				"			<p style=\"font-weight: bold;\">\r\n" + 
				"			</p>\r\n" + 
				"		</td>\r\n" + 
				"		<td style=\"width: 338px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"			<p>\r\n" + 
				"				 "+mroSaveData.get("mro_pchs_no")+"\r\n" + 
				"			</p>\r\n" + 
				"		</td>\r\n" + 
				"		<td style=\"width: 182px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"			<span style=\"font-weight: bold;\">"+mUtil.getMLang(mlangCntn, "LB00000150")+"</span>\r\n" + 
				"			<p style=\"font-family: &quot;Malgun Gothic&quot;, &quot;맑은 고딕&quot;;\">\r\n" + 
				"			</p>\r\n" + 
				"		</td>\r\n" + 
				"		<td style=\"width: 335px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"			<p style=\"font-family: &quot;Malgun Gothic&quot;, &quot;맑은 고딕&quot;;\">\r\n" + 
				"				<span style=\"font-size: 13.3333px;\">"+mroSaveData.get("upt_usr_nm")+"</span><br>\r\n" + 
				"			</p>\r\n" + 
				"		</td>\r\n" + 
				"		<td style=\"width: 166px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"			<span style=\"font-weight: bold;\">"+mUtil.getMLang(mlangCntn, "LB00000149")+"</span>\r\n" + 
				"			<p style=\"font-family: &quot;Malgun Gothic&quot;, &quot;맑은 고딕&quot;;\">\r\n" + 
				"			</p>\r\n" + 
				"		</td>\r\n" + 
				"		<td style=\"width: 352px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"			<p style=\"font-family: &quot;Malgun Gothic&quot;, &quot;맑은 고딕&quot;;\">\r\n" + 
				"				<span style=\"font-size: 13.3333px;\">"+mroSaveData.get("upt_dt")+"</span><br>\r\n" + 
				"			</p>\r\n" + 
				"		</td>\r\n" + 
				"	</tr>\r\n" + 
				"	<tr>\r\n" + 
				"		<td style=\"width: 176px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"			<span style=\"font-weight: bold;\">"+mUtil.getMLang(mlangCntn, "LB00000021")+"</span>\r\n" + 
				"			<p style=\"font-family: &quot;Malgun Gothic&quot;, &quot;맑은 고딕&quot;; font-weight: bold;\">\r\n" + 
				"			</p>\r\n" + 
				"		</td>\r\n" + 
				"		<td style=\"width: 1372px; height: 25px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\" colspan=\"5\">\r\n" + 
				"			<p>\r\n" + 
				"				<span style=\"font-size: 13.3333px;\">"+mroSaveData.get("wkpl_id_nm")+"</span><br>\r\n" + 
				"			</p>\r\n" + 
				"		</td>\r\n" + 
				"	</tr>\r\n" + 
				"	</tbody>\r\n" + 
				"	</table>\r\n" + 
				"	<p>\r\n" + 
				"		<br>\r\n" + 
				"	</p>\r\n" + 
				"	<p style=\"font-size: 12pt; font-weight: bold;\">\r\n" + 
				"		<span style=\"font-size: 12pt; font-weight: bold;\">"+mUtil.getMLang(mlangCntn, "LB00000148")+"</span>\r\n" + 
				"	</p>\r\n" + 
				"	<p>\r\n" + 
				"		 "+mUtil.getMLang(mlangCntn, "LB00000039")+"&nbsp;<span style=\"font-size: 13.3333px;\">"+mtrlSaveList.size()+mUtil.getMLang(mlangCntn, "LB00000040")+"</span>\r\n" + 
				"	</p>\r\n" + 
				"	<table class=\"txc-table\" width=\"1551\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"border:none;border-collapse:collapse;word-break:break-all;font-family:&quot;Malgun Gothic&quot;, &quot;맑은 고딕&quot;;font-size:13.3333px\">\r\n" + 
				"	<tbody>\r\n" + 
				"	<tr>\r\n" + 
				"		<td style=\"width: 101px; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"			<p>\r\n" + 
				"				<span style=\"font-weight: bold;\">No.</span><br>\r\n" + 
				"			</p>\r\n" + 
				"		</td>\r\n" + 
				"		<td style=\"width: 183px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"			<p style=\"font-weight: bold;\">\r\n" + 
				"				<span style=\"font-weight: bold;\">"+mUtil.getMLang(mlangCntn, "LB00000160")+"</span>\r\n" + 
				"			</p>\r\n" + 
				"		</td>\r\n" + 
				"		<td style=\"width: 400px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"			<p style=\"font-weight: bold;\">\r\n" + 
				"				<span style=\"font-weight: bold;\">"+mUtil.getMLang(mlangCntn, "LB00000159")+"</span>\r\n" + 
				"			</p>\r\n" + 
				"		</td>\r\n" + 
				"		<td style=\"width: 300px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"			<p style=\"font-weight: bold;\">\r\n" + 
				"				<span style=\"font-weight: bold;\">"+mUtil.getMLang(mlangCntn, "LB00000158")+"</span>\r\n" + 
				"			</p>\r\n" + 
				"		</td>\r\n" + 
				"		<td style=\"width: 110px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"			<p style=\"font-weight: bold;\">\r\n" + 
				"				<span style=\"font-weight: bold;\">"+mUtil.getMLang(mlangCntn, "LB00000157")+"</span>\r\n" + 
				"			</p>\r\n" + 
				"		</td>\r\n" + 
				"		<td style=\"width: 181px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"			<p style=\"font-weight: bold;\">\r\n" + 
				"				<span style=\"font-weight: bold;\">"+mUtil.getMLang(mlangCntn, "LB00000528")+"</span>\r\n" + 
				"			</p>\r\n" + 
				"		</td>\r\n" + 
				"		<td style=\"width: 126px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"			<p>\r\n" + 
				"				<span style=\"font-weight: bold;\">"+mUtil.getMLang(mlangCntn, "LB00000154")+"</span>\r\n" + 
				"			</p>\r\n" + 
				"		</td>\r\n" + 
				"		<td style=\"width: 126px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"			<p>\r\n" + 
				"				<span style=\"font-weight: bold;\">"+mUtil.getMLang(mlangCntn, "LB00000600")+"</span>\r\n" + 
				"			</p>\r\n" + 
				"		</td>\r\n" + 
				"	</tr>\r\n" ; 
				for(int i=0 ;i <mtrlSaveList.size();i++) {
					aprv_cntn +=
				"	<tr>\r\n" + 
				"		<td style=\"width: 101px; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px;\" rowspan=\"1\">\r\n" + 
				"			<p>\r\n" + 
				"				<span style=\"font-size: 13.3333px;\">"+(i+1)+"</span><br>\r\n" + 
				"			</p>\r\n" + 
				"		</td>\r\n" + 
				"		<td style=\"width: 183px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\" rowspan=\"1\">\r\n" + 
				"			<p>\r\n" + 
				"				<span style=\"font-size: 13.3333px;\">"+mtrlSaveList.get(i).get("mtrl_no")+"</span><br>\r\n" + 
				"			</p>\r\n" + 
				"		</td>\r\n" + 
				"		<td style=\"width: 400px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\" rowspan=\"1\">\r\n" + 
				"			<p>\r\n" + 
				"				<span style=\"font-size: 13.3333px;\">"+mtrlSaveList.get(i).get("mtrl_nm")+"</span><br>\r\n" + 
				"			</p>\r\n" + 
				"		</td>\r\n" + 
				"		<td style=\"width: 334px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\" rowspan=\"1\">\r\n" + 
				"			<p>\r\n" + 
				"				<span style=\"font-size: 13.3333px;\">"+mtrlSaveList.get(i).get("vndr_nm")+"</span><br>\r\n" + 
				"			</p>\r\n" + 
				"		</td>\r\n" + 
				"		<td style=\"width: 76px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\" rowspan=\"1\">\r\n" + 
				"			<p>\r\n" + 
				"				<span style=\"font-size: 13.3333px;\">"+mtrlSaveList.get(i).get("impt_yn")+"</span><br>\r\n" + 
				"			</p>\r\n" + 
				"		</td>\r\n" + 
				"		<td style=\"width: 181px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\" rowspan=\"1\">\r\n" + 
				"			<p>\r\n" + 
				"				<span style=\"font-size: 13.3333px;\">"+String.valueOf(mtrlSaveList.get(i).get("pchs_req_unit_weght"))+mtrlSaveList.get(i).get("pchs_req_unit_nm")+"</span><br>\r\n" + 
				"			</p>\r\n" + 
				"		</td>\r\n" + 
				"		<td style=\"width: 126px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\" rowspan=\"1\">\r\n" + 
				"			<p>\r\n" + 
				"				<span style=\"font-size: 13.3333px;\">"+String.valueOf(mtrlSaveList.get(i).get("pchs_qty"))+"</span><br>\r\n" + 
				"			</p>\r\n" + 
				"		</td>\r\n" + 
				"		<td style=\"width: 126px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\" rowspan=\"1\">\r\n" + 
				"			<p>\r\n" + 
				"				<span style=\"font-size: 13.3333px;\">"+Float.parseFloat(String.valueOf(mtrlSaveList.get(i).get("pchs_req_unit_weght")))*Integer.parseInt(String.valueOf(mtrlSaveList.get(i).get("pchs_qty")))+mtrlSaveList.get(i).get("pchs_req_unit_nm")+"</span><br>\r\n" + 
				"			</p>\r\n" + 
				"		</td>\r\n" + 
				"	</tr>\r\n";
				}
				aprv_cntn += 
				"	</tbody>\r\n" + 
				"	</table>\r\n" + 
				"	<p>\r\n" + 
				"		<br>\r\n" + 
				"	</p>\r\n" + 
				"</div>";
		param.put("aprv_cntn",aprv_cntn);
		// 결제 정보 table insert 
		int n = aprvService.insertAprv(param);
		
		// mro 구매건에 결재번호,생성일시 insert , 진행상태 변경
		Map<String, Object> data = new HashMap<>();
		if(n > -1) {
			param.put("mro_pchs_no",mroSaveData.get("mro_pchs_no"));
			n = service.MroPchsAprvIng(param);
			
			data.put("Success", "Success");
			data.put("aprvNo", param.get("aprv_no"));
			data.put("usrId", userInfo.getUsrId());
		}
				
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	@Transactional
	@SuppressWarnings("unchecked")
	@PostMapping("/mropchs-pkg-aprv")
	public ResponseEntity<?> aprvMropchsPkg(@RequestBody Map<String, Object> saveParam, HttpSession session) {
		//Map<String, String> mroSaveParam = (Map<String, String>)saveParam.get("mroSaveData");
		UserInfo userInfo = sessionManager.get(session.getId());
		
		Map<String, String> Searchparam = new HashMap<>();
		Searchparam.put("SESS_USR_ID", userInfo.getUsrId());
		Searchparam.put("SESS_LANG", userInfo.getLangCd());

		//insert, update, delete 데이터 초기화
		List<Map<String, String>> selectList = (List<Map<String, String>>)saveParam.get("mroSaveData");
		
		//mro 자재 물질 데이터
		//List<Map<String, String>> sbstlist = service.selectMroPchsMtrlSbstDtlList(Searchparam);
		
		
		Map<String, Object> param = new HashMap<>();
//		결재 키 
//		String aprv_no = aprvService.selectAprvkey();
		String aprv_no = UUID.randomUUID().toString().replace("-", "");
		
		param.put("aprv_no",aprv_no);
//		결재 코드
		param.put("arpv_cd","20");
//		업무 번호
		param.put("biz_no",null);
//		상신자
		param.put("aprv_usr_id",userInfo.getUsrId());
//		결재 상태 코드
		param.put("aprv_stt_cd","1");
//		후행메서드	
		param.put("aprv_mthd_nm","aprvRevMropchsPkg");
//		결재 비고	
		param.put("aprv_note","");
//		등록자
		param.put("SESS_USR_ID", userInfo.getUsrId());
		
		// 다국어 처리
		String[] mlangNo = {"LB00000604", "LB00000137", "LB00000021",
				"LB00000023", "LB00000160", "LB00000159", "LB00000600",
				"LB00000158", "LB00000157", "LB00000528", "LB00000154", "LB00000449"};
		
		String langCd = userInfo.getLangCd();
		List<Map<String, String>> mlangCntn = mUtil.getMLangList(Arrays.asList(mlangNo), langCd);
		
//		제목
		param.put("aprv_ttl",mUtil.getMLang(mlangCntn, "LB00000604"));
//		본문 html 소스
		String aprv_cntn = "<div>\r\n" + 
				"	<p style=\"font-weight: bold; font-size: 14pt;\">\r\n" + 
				"		<span style=\"font-weight: bold; font-size: 12pt;\">"+mUtil.getMLang(mlangCntn, "LB00000604")+"</span>\r\n" + 
				"	</p>\r\n" + 
				"	<table class=\"txc-table\" width=\"1551\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"border:none;border-collapse:collapse;word-break:break-all;font-family:&quot;Malgun Gothic&quot;, &quot;맑은 고딕&quot;;font-size:13.3333px\">\r\n" + 
				"	<tbody>\r\n" + 
				"	<tr>\r\n" + 
				"		<td style=\"width: 79px; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"			<p>\r\n" + 
				"				<span style=\"font-weight: bold;\">No.</span><br>\r\n" + 
				"			</p>\r\n" + 
				"		</td>\r\n" + 
				"		<td style=\"width: 200px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"			<p style=\"font-weight: bold;\" style=\"text-align: center;\">\r\n" + 
				"				<span style=\"font-weight: bold;\">"+mUtil.getMLang(mlangCntn, "LB00000160")+"</span>\r\n" + 
				"			</p>\r\n" + 
				"		</td>\r\n" + 
				"		<td style=\"width: 500px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\" class=\"\" colspan=\"1\">\r\n" + 
				"			<p style=\"font-weight: bold;\" style=\"text-align: center;\">\r\n" + 
				"				<span style=\"font-weight: bold;\">"+mUtil.getMLang(mlangCntn, "LB00000159")+"</span>\r\n" + 
				"			</p>\r\n" + 
				"		</td>\r\n" + 
				"		<td style=\"width: 250px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\" class=\"\" colspan=\"1\">\r\n" + 
				"			<p style=\"font-weight: bold;\" style=\"text-align: center;\">\r\n" + 
				"				<span style=\"font-weight: bold;\">"+mUtil.getMLang(mlangCntn, "LB00000158")+"</span>\r\n" + 
				"			</p>\r\n" + 
				"		</td>\r\n" + 
				"		<td style=\"width: 100px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\" class=\"\" colspan=\"1\">\r\n" + 
				"			<p style=\"font-weight: bold;\" style=\"text-align: center;\">\r\n" + 
				"				<span style=\"font-weight: bold;\">"+mUtil.getMLang(mlangCntn, "LB00000157")+"</span>\r\n" + 
				"			</p>\r\n" + 
				"		</td>\r\n" + 
				"		<td style=\"width: 200px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\" class=\"\" colspan=\"1\">\r\n" + 
				"			<p style=\"font-weight: bold;\" style=\"text-align: center;\">\r\n" + 
				"				<span style=\"font-weight: bold;\">"+mUtil.getMLang(mlangCntn, "LB00000528")+"</span>\r\n" + 
				"			</p>\r\n" + 
				"		</td>\r\n" + 
				"		<td style=\"width: 200px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\" class=\"\" colspan=\"1\">\r\n" + 
				"			<p style=\"font-weight: bold;\" style=\"text-align: center;\">\r\n" + 
				"				<span style=\"font-weight: bold;\">"+mUtil.getMLang(mlangCntn, "LB00000154")+"</span>\r\n" + 
				"			</p>\r\n" + 
				"		</td>\r\n" + 
				"		<td style=\"width: 200px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\" class=\"\" colspan=\"1\">\r\n" + 
				"			<p style=\"font-weight: bold;\" style=\"text-align: center;\">\r\n" + 
				"				<span style=\"font-weight: bold;\">"+mUtil.getMLang(mlangCntn, "LB00000600")+"</span>\r\n" + 
				"			</p>\r\n" + 
				"		</td>\r\n" + 
				"		<td style=\"width: 200px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\" class=\"\" colspan=\"1\">\r\n" + 
				"			<p style=\"font-weight: bold;\" style=\"text-align: center;\">\r\n" + 
				"				<span style=\"font-weight: bold;\">"+mUtil.getMLang(mlangCntn, "LB00000449")+"</span>\r\n" + 
				"			</p>\r\n" + 
				"		</td>\r\n" + 
				"	</tr>\r\n";
				for(int i=0 ;i <selectList.size();i++) {
					aprv_cntn +=
						"	<tr>\r\n" + 
						"		<td style=\"width: 79px; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px;\" rowspan=\"1\">\r\n" + 
						"			<p>\r\n" + 
						"				<span style=\"font-size: 13.3333px;\">"+(i+1)+"</span><br>\r\n" + 
						"			</p>\r\n" + 
						"		</td>\r\n" + 
						"		<td style=\"width: 200px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\" rowspan=\"1\">\r\n" + 
						"			<p>\r\n" + 
						"				<span style=\"font-size: 13.3333px;\">"+selectList.get(i).get("mtrl_no").toString()+"</span><br>\r\n" + 
						"			</p>\r\n" + 
						"		</td>\r\n" + 
						"		<td style=\"width: 500px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\" rowspan=\"1\">\r\n" + 
						"			<p>\r\n" + 
						"				<span style=\"font-size: 13.3333px;\">"+selectList.get(i).get("mtrl_nm").toString()+"</span><br>\r\n" + 
						"			</p>\r\n" + 
						"		</td>\r\n" + 
						"		<td style=\"width: 250px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\" rowspan=\"1\" class=\"\" colspan=\"1\">\r\n" + 
						"			<p>\r\n" + 
						"				<span style=\"font-size: 13.3333px;\">"+selectList.get(i).get("vndr_nm").toString()+"</span><br>\r\n" + 
						"			</p>\r\n" + 
						"		</td>\r\n" + 
						"		<td style=\"width: 100px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\" rowspan=\"1\">\r\n" + 
						"			<p>\r\n" + 
						"				<span style=\"font-size: 13.3333px;\">"+selectList.get(i).get("impt_yn").toString()+"</span><br>\r\n" + 
						"			</p>\r\n" + 
						"		</td>\r\n" + 
						"		<td style=\"width: 200px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\" rowspan=\"1\" class=\"\" colspan=\"1\">\r\n" + 
						"			<p>\r\n" + 
						"				<span style=\"font-size: 13.3333px;\">"+selectList.get(i).get("pchs_req_unit_weght_nm").toString()+"</span><br>\r\n" + 
						"			</p>\r\n" + 
						"		</td>\r\n" + 
						"		<td style=\"width: 200px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\" rowspan=\"1\" class=\"\" colspan=\"1\">\r\n" + 
						"			<p>\r\n" + 
						"				<span style=\"font-size: 13.3333px;\">"+selectList.get(i).get("pchs_qty").toString()+"</span><br>\r\n" + 
						"			</p>\r\n" + 
						"		</td>\r\n" + 
						"		<td style=\"width: 200px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\" rowspan=\"1\" class=\"\" colspan=\"1\">\r\n" + 
						"			<p>\r\n" + 
						"				<span style=\"font-size: 13.3333px;\">"+selectList.get(i).get("pchs_req_total_weght").toString()+"</span><br>\r\n" + 
						"			</p>\r\n" + 
						"		</td>\r\n";
					if ("Y".equals(selectList.get(i).get("multi_yn").toString())) {
		                aprv_cntn +="<td style=\"width: 200px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\" rowspan=\"1\" class=\"\" colspan=\"1\">\r\n" + 
							"        	 <p><a style=\"font-weight: bold;\" href=\"" + frontUrl + "/mro/mropchsmultilup/" + selectList.get(i).get("mro_pchs_no").toString() + "\">" + mUtil.getMLang(mlangCntn, "LB00000449") + "</a></p>\r\n" + 
							"        </td>\r\n";
					} else {
				        aprv_cntn +="<td style=\"width: 200px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\" rowspan=\"1\" class=\"\" colspan=\"1\">\r\n" + 
							"            <p><a style=\"font-weight: bold;\" href=\"" + frontUrl + "/mro/mropchslup/" + selectList.get(i).get("mro_pchs_no").toString() + "\">" + mUtil.getMLang(mlangCntn, "LB00000449") + "</a></p>\r\n" + 
							"        </td>\r\n";
					}
					aprv_cntn +="</tr>\r\n" ;
				}
				aprv_cntn +=		
				"	</tbody>\r\n" + 
				"	</table>\r\n" + 
				"	<p>\r\n" + 
				"		<span style=\"font-size: 13.3333px;\"><br>\r\n" + 
				"		</span>\r\n" + 
				"	</p>\r\n" + 
				"	<p>\r\n" + 
				"		<span style=\"font-size: 13.3333px;\"><br>\r\n" + 
				"		</span>\r\n" + 
				"	</p>\r\n" + 
				"</div>";
		param.put("aprv_cntn",aprv_cntn);
		// 결제 정보 table insert 
		int n = aprvService.insertAprv(param);
		
		
		// mro 구매건에 결재번호,생성일시 insert , 진행상태 변경
		Map<String, Object> data = new HashMap<>();
		if(n > -1) {
			for(int i=0 ;i <selectList.size();i++) {
				param.put("mro_pchs_no",selectList.get(i).get("mro_pchs_no").toString());
				n = service.MroPchsAprvIng(param);
			}
			data.put("Success", "Success");
			data.put("aprvNo", param.get("aprv_no"));
			data.put("usrId", userInfo.getUsrId());
		}
		
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	
//	
////결제 후처리
//	// 단건 후처리
//		//	tb_ch_rglt_chk_req : 규제 검토 요청              
//		//	tb_ch_rglt_chk_mtrl : 규제 검토 대상 자재     	<-> tb_ch_mro_pchs_mtrl
//		//	tb_ch_rglt_chk_msds : MSDS              <-> tb_ch_mro_pchs_mtrl
//		//	tb_ch_rglt_chk_sbst : 성분                          <-> tb_ch_mro_pchs_mtrl_sbst
//		//	tb_ch_rglt_chk_gwsgn : 자재 경고표지 내용    	<-> tb_ch_mro_pchs_gwsgn
//		//	tb_ch_rglt_chk_gwsgn_picg : 그림문자          	<-> tb_ch_mro_pchs_gwsgn_picg
//	public void mropchsAprv(String aprvno, HttpSession session) {
//		UserInfo userInfo = sessionManager.get(session.getId());
//
//		//결제 번호로 mro구매번호 찾아서 담기 
//		Map<String, String> param = new HashMap<>();
//		param.put("aprv_no", aprvno);
//		
//		//결제번호로 조회한 mro데이터
//		Map<String, String> aprv_data = service.selectMroPchsDtl(param);
//		param.put("mro_pchs_no", aprv_data.get("mro_pchs_no"));
//		param.put("SESS_LANG", userInfo.getLangCd());
//		param.put("SESS_USR_ID", userInfo.getUsrId());
//				
//		
//		int n = 0;
//		int m = 0;
//		int j = 0;
//		int k = 0;
//		int h = 0;
//		int z = 0;
//		String result = "";
//		if("승인".equals("승인")) {
//			//결제 결과 승인일시 
//			//mro 기본 데이터
//			Map<String, String> mroSaveData = service.selectMroPchsDtl(param);
//			//mro 자재 데이터
//			Map<String, String> mtrlSaveData = service.selectMroPchsMtrlDtl(param);
//			//mro 자재 물질 데이터
//			List<Map<String, String>> sbstlist = service.selectMroPchsMtrlSbstDtlList(param);
//			//mro 구매 표지 데이터
//			Map<String, String> gwsgnSaveData = service.selectMroPchsGwsgnDtl(param);
//			//mro 구매 그림문자
//			List<Map<String, String>> picList = service.selectMroPicDtlList(param);
//			
//			String rglt_chk_no = service.selectRgltChkNo(mroSaveData).get("rglt_chk_no");
//			mroSaveData.put("rglt_chk_no", rglt_chk_no);
//			mroSaveData.put("rglt_chk_req_usr_id", mroSaveData.get("pchs_usr_id"));
//			n = service.MrpPchsAprvSucsInsertRgltChkReq(mroSaveData);
//			
//			mtrlSaveData.put("rglt_chk_no", rglt_chk_no);
//			mtrlSaveData.put("rglt_chk_req_usr_id", mroSaveData.get("pchs_usr_id"));
//			m = service.MrpPchsAprvSucsInsertRgltChkMtrl(mtrlSaveData);
//			j = service.MrpPchsAprvSucsInsertRgltChkMsds(mtrlSaveData);
//			
//			for(int i=0 ; i<sbstlist.size(); i++) {
//				Map<String, String> sbstsavedata = new HashMap<>();
//				sbstsavedata = (Map<String, String>)sbstlist.get(i);
//				sbstsavedata.put("rglt_chk_no", rglt_chk_no);
//				sbstsavedata.put("rglt_chk_req_usr_id", mroSaveData.get("pchs_usr_id"));
//				k = service.MrpPchsAprvSucsInsertRgltChkSbst(sbstsavedata);
//			}
//
//			gwsgnSaveData.put("rglt_chk_no", rglt_chk_no);
//			gwsgnSaveData.put("rglt_chk_req_usr_id", mroSaveData.get("pchs_usr_id"));
//			h = service.MrpPchsAprvSucsInsertRgltChkGwsgn(gwsgnSaveData);
//			
//			for(int i=0 ; i<picList.size(); i++) {
//				Map<String, String> sbstsavedata = new HashMap<>();
//				sbstsavedata = (Map<String, String>)picList.get(i);
//				sbstsavedata.put("rglt_chk_no", rglt_chk_no);
//				sbstsavedata.put("rglt_chk_req_usr_id", mroSaveData.get("pchs_usr_id"));
//				z = service.MrpPchsAprvSucsInsertRgltChkGwsgnPicg(sbstsavedata);
//			}
//			result = (n > -1? (m > -1? (j > -1? (k > -1? (h > -1? (z > -1? Constant.SUCCESS : Constant.FAIL) : Constant.FAIL) : Constant.FAIL) : Constant.FAIL) : Constant.FAIL) : Constant.FAIL);
//		} else {
//			//결제 결과 반려일시
//			n = service.MroPchsAprvFail(param);
//			result = (n > -1?  Constant.SUCCESS : Constant.FAIL);
//		}
//		
//	}
//	// 다건 후처리  - 다건 구매 상태값만 변경
//	public void mropchsMultiAprv(String aprvno) {
//		
//		Map<String, String> param = new HashMap<>();
//		param.put("aprv_no", aprvno);
//		
//		//결제번호로 조회한 mro데이터
//		Map<String, String> aprv_data = service.selectMroPchsDtl(param);
//		param.put("mro_pchs_no", aprv_data.get("mro_pchs_no"));
//		
//		int n = 0;
//		String result = "";
//		if("승인".equals("승인")) {
//			//결제 결과 승인일시 
//			n = service.MroPchsAprvSucs(param);
//		} else {
//			//결제 결과 반려일시
//			n = service.MroPchsAprvFail(param);
//		}
//		result = (n > -1?  Constant.SUCCESS : Constant.FAIL);
//	}
	
}
