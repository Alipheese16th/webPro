package com.kist.portal.common.dashboard.rest;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kist.portal.common.comm.service.CommService;
import com.kist.portal.common.dashboard.service.dashboardService;
import com.kist.portal.common.login.model.UserInfo;
import com.kist.portal.common.utils.Constant;
import com.kist.portal.sh.acdtmgnt.service.AcdtReportService;
import com.kist.portal.sysconf.manager.SessionManager;

@RestController
@RequestMapping("/api/v1/common/dashboard")
public class dashboardController {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Value("${dgrm.open-api-key}")
	private String serviceKey;
	
	@Autowired
	private CommService commService;
	
	@Autowired
	private dashboardService service;
	
	@Autowired
	private AcdtReportService arService;
	
	@Autowired
	private SessionManager sessionManager;
	
	@SuppressWarnings("unchecked")
	@GetMapping("/dash-chart-info/{region}")
	public ResponseEntity<?> selectOpenApi(@PathVariable String region, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		Map<String, String> searchParam = new HashMap<>();
		searchParam.put("SESS_LANG", userInfo.getLangCd());
		searchParam.put("cd_grp_no", "ST00000023");
		List<Map<String, ?>> regionList = commService.selectCdList(searchParam);
		Map<String,?> regionMap = regionList.stream().filter(m -> region.equals(m.get("cd"))).findFirst().get();
		logger.debug("region : {}", regionMap);
		
		Map<String, Map<String, ?>> data = new HashMap<>();
		
		Map<String, ?> api01 = selectOpenApi01(regionMap, 30);
		String code = ((Map<String, ?>) api01.get("response")) == null? null : 
			((Map<String, ?>) ((Map<String, ?>) api01.get("response")).get("header")) == null? null : 
				(String) ((Map<String, ?>) ((Map<String, ?>) api01.get("response")).get("header")).get("resultCode");
		logger.debug("code1 : {}", code);
		if("03".equals(code)) {
			api01 = selectOpenApi01(regionMap, 60);
			code = ((Map<String, ?>) api01.get("response")) == null? null : 
				((Map<String, ?>) ((Map<String, ?>) api01.get("response")).get("header")) == null? null : 
					(String) ((Map<String, ?>) ((Map<String, ?>) api01.get("response")).get("header")).get("resultCode");
			logger.debug("code2 : {}", code);
			if("03".equals(code)) {
				logger.debug("last try");
				api01 = selectOpenApi01(regionMap, 120);
			}
		}
		
		Map<String, ?> api02 = selectOpenApi02(regionMap);
		Map<String, ?> api03 = selectOpenApi03(regionMap);
		Map<String, ?> api04 = selectOpenApi04(regionMap);
		Map<String, ?> api05 = selectOpenApi05(regionMap);
		Map<String, ?> api06 = selectOpenApi06(regionMap);
		
		data.put("api01", api01);
		data.put("api02", api02);
		data.put("api03", api03);
		data.put("api04", api04);
		data.put("api05", api05);
		data.put("api06", api06);
		
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	// 날씨 예보
	private Map<String, ?> selectOpenApi01(Map<String,?> regionMap, int mm) {
		Map<String, ?> data = new HashMap<>();
		BufferedReader br = null;
        
        try {
        	LocalDateTime dt = LocalDateTime.now().minusMinutes(mm);
    		StringBuilder ub = new StringBuilder("http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtFcst");
            ub.append("?serviceKey=" + serviceKey);
            ub.append("&pageNo=1");
            ub.append("&numOfRows=100");
            ub.append("&dataType=JSON");
            ub.append("&base_date=" + dt.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
            ub.append("&base_time=" + dt.format(DateTimeFormatter.ofPattern("HHmm")));
            ub.append("&nx=" + regionMap.get("buf_1st_cntn"));
            ub.append("&ny=" + regionMap.get("buf_2nd_cntn"));
            logger.debug("OpenApi01 url : {}", ub.toString());
            
	        URL url = new URL(ub.toString());
	        String line = "";
	        String result = "";
	        
	        br = new BufferedReader(new InputStreamReader(url.openStream()));
	        while ((line = br.readLine()) != null) {
	            result = result.concat(line);
	        }
	        
	        ObjectMapper objectMapper = new ObjectMapper();
            data = objectMapper.readValue(result, new TypeReference<Map<String, ?>>(){});
        } catch(Exception e) {
        	logger.error("OpenApi01 Connect Error : {}", e.getMessage());
        } finally {
        	try {
        	if(br != null) br.close();
        	} catch(Exception e) {}
        }
        logger.debug("OpenApi01 : {}", data);
		
		return data;
	}
	
	// 미세먼지 예보
	private Map<String, ?> selectOpenApi02(Map<String,?> regionMap) {
		Map<String, ?> data = new HashMap<>();
		
		String[] region = ((String) regionMap.get("buf_3rd_cntn")).split("/");
		BufferedReader br = null;
        
        try {
        	StringBuilder ub = new StringBuilder("http://apis.data.go.kr/B552584/ArpltnStatsSvc/getCtprvnMesureSidoLIst");
        	ub.append("?serviceKey=" + serviceKey);
        	ub.append("&pageNo=1");
        	ub.append("&numOfRows=100");
        	ub.append("&returnType=JSON");
        	ub.append("&searchCondition=HOUR");
        	ub.append("&sidoName=" + URLEncoder.encode(region[0] , "UTF-8"));
        	logger.debug("OpenApi02 url : {}", ub.toString());
          
	        URL url = new URL(ub.toString());
	        String line = "";
	        String result = "";
	        
	        br = new BufferedReader(new InputStreamReader(url.openStream()));
	        while ((line = br.readLine()) != null) {
	            result = result.concat(line);
	        }
	        
	        ObjectMapper objectMapper = new ObjectMapper();
            data = objectMapper.readValue(result, new TypeReference<Map<String, ?>>(){});
        } catch(Exception e) {
        	logger.error("OpenApi02 Connect Error : {}", e.getMessage());
        } finally {
        	try {
        	if(br != null) br.close();
        	} catch(Exception e) {}
        }
        logger.debug("OpenApi02 : {}", data);
		
		return data;
	}
	
	// 자외선
	private Map<String, ?> selectOpenApi03(Map<String,?> regionMap) {
		Map<String, ?> data = new HashMap<>();
		BufferedReader br = null;
		
        try {
        	LocalDateTime dt = LocalDateTime.now();
        	StringBuilder ub = new StringBuilder("http://apis.data.go.kr/1360000/LivingWthrIdxServiceV3/getUVIdxV3");
        	ub.append("?serviceKey=" + serviceKey);
        	ub.append("&pageNo=1");
        	ub.append("&numOfRows=100");
        	ub.append("&dataType=JSON");
        	ub.append("&time=" + dt.format(DateTimeFormatter.ofPattern("yyyyMMddHH")));
        	ub.append("&areaNo=" + regionMap.get("buf_4th_cntn"));
        	logger.debug("OpenApi03 url : {}", ub.toString());
          
	        URL url = new URL(ub.toString());
	        String line = "";
	        String result = "";
	        
	        br = new BufferedReader(new InputStreamReader(url.openStream()));
	        while ((line = br.readLine()) != null) {
	            result = result.concat(line);
	        }
	        
	        ObjectMapper objectMapper = new ObjectMapper();
            data = objectMapper.readValue(result, new TypeReference<Map<String, ?>>(){});
        } catch(Exception e) {
        	logger.error("OpenApi03 Connect Error : {}", e.getMessage());
        } finally {
        	try {
        	if(br != null) br.close();
        	} catch(Exception e) {}
        }
        logger.debug("OpenApi03 : {}", data);
		
		return data;
	}
	
	// 체감온도 (하절기 5 ~ 9월)
	private Map<String, ?> selectOpenApi04(Map<String,?> regionMap) {
		Map<String, ?> data = new HashMap<>();
		BufferedReader br = null;
		
        try {
        	LocalDateTime dt = LocalDateTime.now();
        	int m = Integer.parseInt(dt.format(DateTimeFormatter.ofPattern("MM")));
        	if(m < 5 || m > 9) {
        		return data;
        	}
        	StringBuilder ub = new StringBuilder("http://apis.data.go.kr/1360000/LivingWthrIdxServiceV3/getSenTaIdxV3");
        	ub.append("?serviceKey=" + serviceKey);
        	ub.append("&pageNo=1");
        	ub.append("&numOfRows=100");
        	ub.append("&dataType=JSON");
        	ub.append("&requestCode=A41");
        	ub.append("&time=" + dt.format(DateTimeFormatter.ofPattern("yyyyMMddHH")));
        	ub.append("&areaNo=" + regionMap.get("buf_4th_cntn"));
        	logger.debug("OpenApi04 url : {}", ub.toString());
          
	        URL url = new URL(ub.toString());
	        String line = "";
	        String result = "";
	        
	        br = new BufferedReader(new InputStreamReader(url.openStream()));
	        while ((line = br.readLine()) != null) {
	            result = result.concat(line);
	        }
	        
	        ObjectMapper objectMapper = new ObjectMapper();
            data = objectMapper.readValue(result, new TypeReference<Map<String, ?>>(){});
        } catch(Exception e) {
        	logger.error("OpenApi04 Connect Error : {}", e.getMessage());
        } finally {
        	try {
        	if(br != null) br.close();
        	} catch(Exception e) {}
        }
        logger.debug("OpenApi04 : {}", data);
		
		return data;
	}
	
	// 동파지수 (동절기 11 ~ 3월)
	private Map<String, ?> selectOpenApi05(Map<String,?> regionMap) {
		Map<String, ?> data = new HashMap<>();
		BufferedReader br = null;
		
        try {
        	LocalDateTime dt = LocalDateTime.now();
        	int m = Integer.parseInt(dt.format(DateTimeFormatter.ofPattern("MM")));
        	if(m > 3 && m < 11) {
        		return data;
        	}
        	StringBuilder ub = new StringBuilder("http://apis.data.go.kr/1360000/LivingWthrIdxServiceV3/getFreezeIdxV3");
        	ub.append("?serviceKey=" + serviceKey);
        	ub.append("&pageNo=1");
        	ub.append("&numOfRows=100");
        	ub.append("&dataType=JSON");
        	ub.append("&requestCode=A41");
        	ub.append("&time=" + dt.format(DateTimeFormatter.ofPattern("yyyyMMddHH")));
        	ub.append("&areaNo=" + regionMap.get("buf_4th_cntn"));
        	logger.debug("OpenApi05 url : {}", ub.toString());
          
	        URL url = new URL(ub.toString());
	        String line = "";
	        String result = "";
	        
	        br = new BufferedReader(new InputStreamReader(url.openStream()));
	        while ((line = br.readLine()) != null) {
	            result = result.concat(line);
	        }
	        
	        ObjectMapper objectMapper = new ObjectMapper();
            data = objectMapper.readValue(result, new TypeReference<Map<String, ?>>(){});
        } catch(Exception e) {
        	logger.error("OpenApi05 Connect Error : {}", e.getMessage());
        } finally {
        	try {
        	if(br != null) br.close();
        	} catch(Exception e) {}
        }
        logger.debug("OpenApi05 : {}", data);
		
		return data;
	}
	
	// 기상특보
	private Map<String, ?> selectOpenApi06(Map<String,?> regionMap) {
		Map<String, ?> data = new HashMap<>();
		BufferedReader br = null;
		
        try {
        	LocalDateTime dt = LocalDateTime.now();
        	StringBuilder ub = new StringBuilder("http://apis.data.go.kr/1360000/WthrWrnInfoService/getWthrWrnList");
        	ub.append("?serviceKey=" + serviceKey);
        	ub.append("&pageNo=1");
        	ub.append("&numOfRows=100");
        	ub.append("&dataType=JSON");
        	ub.append("&fromTmFc=" + dt.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
//        	ub.append("&fromTmFc=20221105");
        	ub.append("&toTmFc=" + dt.format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        	ub.append("&stnId=" + regionMap.get("buf_5th_cntn"));
        	logger.debug("OpenApi06 url : {}", ub.toString());
          
	        URL url = new URL(ub.toString());
	        String line = "";
	        String result = "";
	        
	        br = new BufferedReader(new InputStreamReader(url.openStream()));
	        while ((line = br.readLine()) != null) {
	            result = result.concat(line);
	        }
	        
	        ObjectMapper objectMapper = new ObjectMapper();
            data = objectMapper.readValue(result, new TypeReference<Map<String, ?>>(){});
        } catch(Exception e) {
        	logger.error("OpenApi06 Connect Error : {}", e.getMessage());
        } finally {
        	try {
        	if(br != null) br.close();
        	} catch(Exception e) {}
        }
        logger.debug("OpenApi06 : {}", data);
		
		return data;
	}
	
	@GetMapping("/dash-chart-info")
	public ResponseEntity<?> selectChartList(@RequestParam Map<String, String> param, HttpSession session) {
		// 언어 셋 지정
		UserInfo userInfo = sessionManager.get(session.getId());
		param.put("SESS_LANG", userInfo.getLangCd());
		
		Map<String, Object> data = new HashMap<>();
		String kind = param.get("kind");
		logger.debug("kind is " + kind);
		if("select".equals(kind)) {
			List<Map<String, ?>> apTot = arService.selectApTot(param);
			List<Map<String, ?>> apCat = arService.selectCat(param);
			List<Map<String, ?>> apWkpl = arService.selectWkpl(param);
			List<Map<String, ?>> apCatWkpl = arService.selecCatWkpl(param);
			List<Map<String, ?>> acdtPerQt = arService.selecAcdtPerQt(param);
			List<Map<String, ?>> acdtPerMon = arService.selecAcdtPerMon(param);
						
			data.put("apTot", apTot);
			data.put("apCat", apCat);
			data.put("apWkpl", apWkpl);
			data.put("apCatWkpl", apCatWkpl);
			data.put("acdtPerQt", acdtPerQt);
			data.put("acdtPerMon", acdtPerMon);
			
			// 기상정보 지역
			String rgnCd = service.selectWeatRgn(param);
			// 사업장별 안전작업허가 현황
			List<Map<String, ?>> oprnLocList = service.selectOprnLocList(param);
			List<Map<String, ?>> oprnLocTot = service.selectOprnLocTot(param);
			// 안전작업허가 현황
			List<Map<String, ?>> planRsltlist = service.selectPlanRsltList(param);
			List<Map<String, ?>> planRsltTot = service.selectPlanRsltTot(param);
			// 투자예산 실적
			List<Map<String, ?>> invsData = service.selectInvsPlanRsltList(param);
			List<Map<String, ?>> invsTotData = service.selectInvsPlanRsltTot(param);
			// 안전점검 현황 - 작업 시작 전 점검
			List<Map<String, ?>> oprnList = service.selectDashOprnList(param);
			// 안전점검 현황 - 순회 점검
			List<Map<String, ?>> circList = service.selectDashCircList(param);
			
			data.put("rgnCd", rgnCd);
			data.put("oprnLocList", oprnLocList);
			data.put("oprnLocTot", oprnLocTot);
			data.put("planRsltlist", planRsltlist);
			data.put("planRsltTot", planRsltTot);
			data.put("invsData", invsData);
			data.put("invsTotData", invsTotData);
			data.put("oprnList", oprnList);
			data.put("circList", circList);
		} else if("select1".equals(kind)) {
			List<Map<String, ?>> acdtPerQt = arService.selecAcdtPerQt(param);
			List<Map<String, ?>> acdtPerMon = arService.selecAcdtPerMon(param);
			
			data.put("acdtPerQt", acdtPerQt);
			data.put("acdtPerMon", acdtPerMon);
		} else if("oprnLoc".equals(kind)) {
			// 사업장별 안전작업허가 현황
			List<Map<String, ?>> oprnLocList = service.selectOprnLocList(param);
			List<Map<String, ?>> oprnLocTot = service.selectOprnLocTot(param);
			
			data.put("oprnLocList", oprnLocList);
			data.put("oprnLocTot", oprnLocTot);
		} else if("weatMsg".equals(kind)) {
			// 기상정보 주의사항
			param.put("wtsr_yn", "N");
			List<Map<String, ?>> weatMsgList1 = service.selectWeatMsgList(param);
			// 기상특보 주의사항
			param.put("wtsr_yn", "Y");
			List<Map<String, ?>> weatMsgList2 = service.selectWeatMsgList(param);
			
			data.put("weatMsgList1", weatMsgList1);
			data.put("weatMsgList2", weatMsgList2);
		} else if("chkMsg".equals(kind)) {
			// 기상정보 주의사항
			List<Map<String, ?>> weatMsgChk1 = service.selectWeatMsgChk(param);
			// 기상특보 주의사항
			List<Map<String, ?>> weatMsgChk2 = service.selectWeatMsgChk2(param);
			
			data.put("weatMsgChk1", weatMsgChk1);
			data.put("weatMsgChk2", weatMsgChk2);
		}
		
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	@SuppressWarnings("unchecked")
	@PutMapping("/dash-chart-info")
	@Transactional
	public ResponseEntity<?> insertWeatMsg(@RequestBody Map<String, Object> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		
		int n = 0;
		// 기상정보
		List<Map<String, String>> insertList = (List<Map<String, String>>)param.get("insertList");
		List<Map<String, String>> updateList = (List<Map<String, String>>)param.get("updateList");
		List<Map<String, String>> deleteList = (List<Map<String, String>>)param.get("deleteList");
		
		if (insertList != null) {
			// 인서트
			for(int i = 0; i < insertList.size(); i++) {
				Map<String, String> map = insertList.get(i);
				map.put("username", userInfo.getUsrId());
				map.put("wtsr_yn", "N");
				if("".equals(map.get("weat_elmt_min_vl"))) {
					map.put("weat_elmt_min_vl", null);
				}
				if("".equals(map.get("weat_elmt_max_vl"))) {
					map.put("weat_elmt_max_vl", null);
				}
				n = service.insertWeatMsg(map);
			}
		}
		
		if (updateList != null) {
			// 업데이트
			for (int i = 0; i < updateList.size(); i++) {
				Map<String, String> map = updateList.get(i);
				map.put("username", userInfo.getUsrId());
				map.put("wtsr_yn", "N");
				if("".equals(map.get("weat_elmt_min_vl"))) {
					map.put("weat_elmt_min_vl", null);
				}
				if("".equals(map.get("weat_elmt_max_vl"))) {
					map.put("weat_elmt_max_vl", null);
				}
				n = service.insertWeatMsg(map);
			}
		}
		
		if (deleteList != null) {
			// 삭제
			for(int i = 0; i < deleteList.size(); i++) {
				Map<String, String> map = deleteList.get(i);
				n = service.deleteWeatMsg(map);
			}
		}
		
		// 기상특보
		insertList = (List<Map<String, String>>)param.get("insertList2");
		updateList = (List<Map<String, String>>)param.get("updateList2");
		deleteList = (List<Map<String, String>>)param.get("deleteList2");
		
		if (insertList != null) {
			// 인서트
			for(int i = 0; i < insertList.size(); i++) {
				Map<String, String> map = insertList.get(i);
				map.put("username", userInfo.getUsrId());
				map.put("wtsr_yn", "Y");
				n = service.insertWeatMsg(map);
			}
		}
		
		if (updateList != null) {
			// 업데이트
			for (int i = 0; i < updateList.size(); i++) {
				Map<String, String> map = updateList.get(i);
				map.put("username", userInfo.getUsrId());
				map.put("wtsr_yn", "Y");
				n = service.insertWeatMsg(map);
			}
		}
		
		if (deleteList != null) {
			// 삭제
			for(int i = 0; i < deleteList.size(); i++) {
				Map<String, String> map = deleteList.get(i);
				n = service.deleteWeatMsg(map);
			}
		}
		
		String result = n > -1? Constant.SUCCESS : Constant.FAIL;
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	// 2022.11.3 이전 데시보드에서 사용하던 메소드 백업. 
	public ResponseEntity<?> selectChartList_bakup(@RequestParam Map<String, String> param, HttpSession session) {
		// 언어 셋 지정
		UserInfo userInfo = sessionManager.get(session.getId());
		
		param.put("SESS_LANG", userInfo.getLangCd());
		
		// 안전작업허가 현황
		List<Map<String, ?>> planRsltlist = service.selectPlanRsltList(param);
		List<Map<String, ?>> planRsltTot = service.selectPlanRsltTot(param);
		// 연간/일일 외주작업
		List<Map<String, ?>> yearConOprnlist = service.selectYearConOprnAllList(param);
		// 연간 작업 유형별 구분
		List<Map<String, ?>> conOprnTypelist = service.selectYearConOprnTypeList(param);
		// 연간 점검 건수
		List<Map<String, ?>> conCheckchartData = service.selectYearConCheckList(param);
		// 연간 지적/개선 건수
		List<Map<String, ?>> conCrinchartData = service.selectYearCrinCheckList(param);
		// 연간 지적/개선 건수
		List<Map<String, ?>> conCheckGbchartData = service.selectYearConCheckGbList(param);
		// 연간 지적/개선 건수
		List<Map<String, ?>> conCrinGbchartData = service.selectYearCrinGbCheckList(param);
		// 사고발생건수
		List<Map<String, ?>> acdtData = service.selectAcdtChartList(param);
		List<Map<String, ?>> acdtTotData = service.selectAcdtChartTot(param);
		// 사고발생유형/건수
		List<Map<String, ?>> acdtchartcntData = service.selectAcdtChartcntList(param);
		// 정기안전보건교육 이수율
		List<Map<String, ?>> fc1Data = service.selectTrnFc1AllList(param);
		// 신규채용자교육 이수율
		List<Map<String, ?>> fc2Data = service.selectTrnFc2AllList(param);
		// 특별안전보건교육 이수율
		List<Map<String, ?>> fc3Data = service.selectTrnFc3AllList(param);
		// 관리감독자교육 이수율
		List<Map<String, ?>> fc6Data = service.selectTrnFc6AllList(param);
		// 투자예산 실적
		List<Map<String, ?>> invsData = service.selectInvsPlanRsltList(param);
		List<Map<String, ?>> invsTotData = service.selectInvsPlanRsltTot(param);
		
		// 시정 지시 및 조치현황
		List<Map<String, ?>> crinData = service.selectDashCrinList(param);
		// 안전점검 현황 - 작업 시작 전 점검
		List<Map<String, ?>> oprnList = service.selectDashOprnList(param);
		// 안전점검 현황 - 순회 점검
		List<Map<String, ?>> circList = service.selectDashCircList(param);
		// 안전점검 현황 - 작업 시작 전 점검
		List<Map<String, ?>> jntList = service.selectDashJntList(param);
		// 안전점검 현황 - 연구실 일상점검
		List<Map<String, ?>> labList = service.selectDashLabList(param);
		
		
		Map<String, List<Map<String, ?>>> data = new HashMap<>();
		data.put("planRsltlist", planRsltlist);
		data.put("planRsltTot", planRsltTot);
		data.put("yearConOprnlist", yearConOprnlist);
		data.put("conOprnTypelist", conOprnTypelist);
		data.put("conCheckchartData", conCheckchartData);
		data.put("conCrinchartData", conCrinchartData);
		data.put("conCheckGbchartData", conCheckGbchartData);
		data.put("conCrinGbchartData", conCrinGbchartData);
		data.put("acdtData", acdtData);
		data.put("acdtTotData", acdtTotData);
		data.put("acdtchartcntData", acdtchartcntData);
		data.put("fc1Data", fc1Data);
		data.put("fc2Data", fc2Data);
		data.put("fc3Data", fc3Data);
		data.put("fc6Data", fc6Data);
		data.put("invsData", invsData);
		data.put("invsTotData", invsTotData);
		data.put("crinData", crinData);
		data.put("oprnList", oprnList);
		data.put("circList", circList);
		data.put("jntList", jntList);
		data.put("labList", labList);
		
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
}
