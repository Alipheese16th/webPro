package com.kist.portal.common.comm.rest;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kist.portal.common.login.model.UserInfo;
import com.kist.portal.sh.acdtmgnt.service.AcdtMgntService;
import com.kist.portal.sh.riskeval.service.RiskEvalService;
import com.kist.portal.sysconf.manager.SessionManager;

@RestController
@RequestMapping("/api/v1/comm")
public class ReportController {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Value("${spring.ozreport.viewer}")
	private String reportviewer;
	
	@Value("${spring.ozreport.server}")
	private String reportserver;
	
	@Value("${dgrm.front-url}")
	private String frontUrl;
	
	@Value("${dgrm.server-url}")
	private String serverUrl;
	
	@Autowired
	private AcdtMgntService acdtMgntService;

	@Autowired
	private RiskEvalService basicService; 
	
	@Autowired
	private SessionManager sessionManager;
	
	@SuppressWarnings("unchecked")
	@PostMapping("/report/gwsgn")
	public ResponseEntity<?> selectOzReport(@RequestBody Map<String, Object> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		//mtrlNoList 
		List<Object> mtrlNoList = (List<Object>)param.get("mtrlNoList");
		String mtrl_no = "";
		for(int i=0 ; i<mtrlNoList.size();i++) {
			if(i == 0) {
				mtrl_no += ("\'"+mtrlNoList.get(i)+"\'") ;
			}else {
				mtrl_no += (",\'"+mtrlNoList.get(i)+"\'") ;
			}
		}
		String oz = "";
		oz = "<!DOCTYPE html>" + 
				"<html style=\"height:100%\">" + 
				"<head>" + 
				"<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"/>" + 
				"<script src=\""+reportviewer+"/jquery/jquery-2.0.3.min.js\"></script>" + 
				"<link rel=\"stylesheet\" href=\""+reportviewer+"/jquery/jquery-ui.css\" type=\"text/css\"/>" + 
				"<script src=\""+reportviewer+"/jquery/jquery-ui.min.js\"></script>" + 
				"<link rel=\"stylesheet\" href=\""+reportviewer+"/ui.dynatree.css\" type=\"text/css\"/>" + 
				"<script type=\"text/javascript\" src=\""+reportviewer+"/jquery.dynatree.js\" charset=\"utf-8\"></script>" + 
				"<script type=\"text/javascript\" src=\""+reportviewer+"/OZJSViewer.js\" charset=\"utf-8\"></script>" + 
				"</head>" + 
				"<body style=\"width:98%;height:98%\">" + 
				"<div id=\"OZViewer\" style=\"width:98%;height:98%\"></div>" + 
				"<script type=\"text/javascript\" >" + 
				"	function SetOZParamters_OZViewer(){" + 
				"	  var oz;" + 
				"		 oz = document.getElementById(\"OZViewer\");" + 
				"		 oz.sendToActionScript(\"connection.displayname\",\"report\");" + 
				"		 oz.sendToActionScript(\"connection.servlet\",\""+reportserver+"\");" + 
				"		 oz.sendToActionScript(\"connection.reportname\",\"/"+param.get("reportForm")+".ozr\");"+
				"        oz.sendToActionScript(\"odi.odinames\",\"gwsgn\");" + 
				"        oz.sendToActionScript(\"odi.gwsgn.pcount\",\"2\");" + 
				"        oz.sendToActionScript(\"odi.gwsgn.args1\",\"mtrl_no="+mtrl_no+"\");" +
				"        oz.sendToActionScript(\"odi.gwsgn.args2\",\"SESS_LANG="+userInfo.getLangCd()+"\");" +
				"		 oz.sendToActionScript(\"viewer.showthumbnail\",\"true\");"	+
				"        oz.sendToActionScript(\"viewer.pagedisplay\",\"singlepagecontinuous\");" + 
				"        oz.sendToActionScript(\"viewer.viewmode\",\"fittoframe\");" + 
				"		 oz.sendToActionScript(\"export.filename\", "+"report"+");";

			oz += 
				"		return true;" + 
				"	}" + 
				"	start_ozjs(\"OZViewer\",\""+reportviewer+"/\");" + 
				"</script>" + 
				"</body>" + 
				"</html>" + 
				"";
		
		return new ResponseEntity<>(oz, HttpStatus.OK);
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/report/cstprms")
	public ResponseEntity<?> selectOzReportCstprms(@RequestBody Map<String, Object> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		String oz = "";
		oz = "<!DOCTYPE html>" + 
				"<html style=\"height:100%\">" + 
				"<head>" + 
				"<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"/>" +
				"<script src=\""+reportviewer+"/jquery/jquery-2.0.3.min.js\"></script>" + 
				"<link rel=\"stylesheet\" href=\""+reportviewer+"/jquery/jquery-ui.css\" type=\"text/css\"/>" + 
				"<script src=\""+reportviewer+"/jquery/jquery-ui.min.js\"></script>" + 
				"<link rel=\"stylesheet\" href=\""+reportviewer+"/ui.dynatree.css\" type=\"text/css\"/>" + 
				"<script type=\"text/javascript\" src=\""+reportviewer+"/jquery.dynatree.js\" charset=\"utf-8\"></script>" + 
				"<script type=\"text/javascript\" src=\""+reportviewer+"/OZJSViewer.js\" charset=\"utf-8\"></script>" + 
				"<script language=\"JavaScript\">" +
				"function OZPrintCommand_OZViewer(msg, code, reportname, printername, printcopy, printpages, printrange, username, printerdrivername, printpagesrange) {" +
				"  if (code == \"0\")" +
				"  {" +
				"     var saveParam = '{\"con_id\" : \"" + param.get("con_id") + "\", \"con_prms_id\" : \"" + param.get("con_prms_id") + "\", \"wkpl_id\" : \"" + param.get("wkpl_id") + "\", \"usr_id\" : \"" + param.get("usr_id") + "\"}';" +
				"     console.log(saveParam);" +
				"     $.ajax({" +
				"      type: 'PUT'," +
				"      url:  '" + serverUrl + "/api/v1/she/cstprms/con-oprn-prms-dtl-print'," +
				"      contentType: 'application/json; charset=utf-8'," +
				"      data: saveParam," +
				// "      dataType: 'json'," +
				"      async: true," +
				"      success: function(result) {" +
				"      }," +
				"      error: function(jqXHR, textStatus, errorThrown) {" +
				"        console.log(jqXHR.status + ' ' + jqXHR.responseText);" +
				"      }," +
				"     });" +
				"  } else {" +
				"     alert(msg);" +
				"  }" +
				"}" +
			    "</script>" +
			    "<script language=\"JavaScript\">" + 
			    "function OZExportCommand_OZViewer(code, path, filename, pagecount, filepaths) {" + 
			    "  if (code == \"1\")" +
				"  {" +
				"     var saveParam = '{\"con_id\" : \"" + param.get("con_id") + "\", \"con_prms_id\" : \"" + param.get("con_prms_id") + "\", \"wkpl_id\" : \"" + param.get("wkpl_id") + "\", \"usr_id\" : \"" + param.get("usr_id") + "\"}';" +
				"     console.log(saveParam);" +
				"     $.ajax({" +
				"      type: 'PUT'," +
				"      url:  '" + serverUrl + "/api/v1/she/cstprms/con-oprn-prms-dtl-print'," +
				"      contentType: 'application/json; charset=utf-8'," +
				"      data: saveParam," +
				// "      dataType: 'json'," +
				"      async: true," +
				"      success: function(result) {" +
				"      }," +
				"      error: function(jqXHR, textStatus, errorThrown) {" +
				"        console.log(jqXHR.status + ' ' + jqXHR.responseText);" +
				"      }," +
				"     });" +
				"  } else {" +
				"     alert('실패');" +
				"  }" +
				"}" + 
				"</script>" + 
				"</head>" + 
				"<body style=\"width:98%;height:98%\">" + 
				"<div id=\"OZViewer\" style=\"width:98%;height:98%\"></div>" + 
				"<script type=\"text/javascript\" >" + 
				"	function SetOZParamters_OZViewer(){" + 
				"	  var oz;" + 
				"		 oz = document.getElementById(\"OZViewer\");" + 
				"		 oz.sendToActionScript(\"connection.displayname\",\"report\");" + 
				"		 oz.sendToActionScript(\"connection.servlet\",\""+reportserver+"\");" + 
				"		 oz.sendToActionScript(\"connection.reportname\",\"/"+param.get("reportForm")+".ozr\");"+
				"        oz.sendToActionScript(\"odi.odinames\",\"conoprnprms\");" + 
				"        oz.sendToActionScript(\"odi.conoprnprms.pcount\",\"4\");" + 
				"        oz.sendToActionScript(\"odi.conoprnprms.args1\",\"SESS_LANG="+userInfo.getLangCd()+"\");" +
				"        oz.sendToActionScript(\"odi.conoprnprms.args2\",\"con_id="+param.get("con_id")+"\");" +
				"        oz.sendToActionScript(\"odi.conoprnprms.args3\",\"con_prms_id="+param.get("con_prms_id")+"\");" +
				"        oz.sendToActionScript(\"odi.conoprnprms.args4\",\"wkpl_id="+param.get("wkpl_id")+"\");" +
				"        oz.sendToActionScript(\"viewer.exportcommand\",\"true\");" +
				"        oz.sendToActionScript(\"viewer.printcommand\",\"true\");" +
				"		 oz.sendToActionScript(\"viewer.showthumbnail\",\"true\");"	+
				"        oz.sendToActionScript(\"viewer.pagedisplay\",\"singlepagecontinuous\");" + 
				"        oz.sendToActionScript(\"viewer.viewmode\",\"fittoframe\");" + 
				"		 oz.sendToActionScript(\"export.filename\", "+"report"+");" +
				"		return true;" + 
				"	}" + 
				"	start_ozjs(\"OZViewer\",\""+reportviewer+"/\");" + 
				"</script>" + 
				"</body>" + 
				"</html>" + 
				"";
		
		return new ResponseEntity<>(oz, HttpStatus.OK);
	}
	
	// 사업장 선임현황 출력
	@SuppressWarnings("unchecked")
	@PostMapping("/report/wkplElcRpt")
	public ResponseEntity<?> selectOzReportWkplElc(@RequestBody Map<String, String> param, HttpSession session) {

		UserInfo userInfo = sessionManager.get(session.getId());
		
		String oz = "";
		oz = "<!DOCTYPE html>" + 
				"<html style=\"height:100%\">" + 
				"<head>" + 
				"<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"/>" +
				"<script src=\""+reportviewer+"/jquery/jquery-2.0.3.min.js\"></script>" + 
				"<link rel=\"stylesheet\" href=\""+reportviewer+"/jquery/jquery-ui.css\" type=\"text/css\"/>" + 
				"<script src=\""+reportviewer+"/jquery/jquery-ui.min.js\"></script>" + 
				"<link rel=\"stylesheet\" href=\""+reportviewer+"/ui.dynatree.css\" type=\"text/css\"/>" + 
				"<script type=\"text/javascript\" src=\""+reportviewer+"/jquery.dynatree.js\" charset=\"utf-8\"></script>" + 
				"<script type=\"text/javascript\" src=\""+reportviewer+"/OZJSViewer.js\" charset=\"utf-8\"></script>" +
				"</head>" + 
				
				
				"<body style=\"width:98%;height:98%\">" + 
				"<div id=\"OZViewer\" style=\"width:98%;height:98%\"></div>" + 
				"<script type=\"text/javascript\" >" + 
				"	function SetOZParamters_OZViewer(){" + 
				"	  var oz;" + 
				"		 oz = document.getElementById(\"OZViewer\");" + 
				"		 oz.sendToActionScript(\"connection.displayname\",\"report\");" + 
				"		 oz.sendToActionScript(\"connection.servlet\",\""+reportserver+"\");" + 
				"		 oz.sendToActionScript(\"connection.reportname\",\"/"+param.get("reportForm")+".ozr\");"+
				"        oz.sendToActionScript(\"odi.odinames\",\"wkplElcMgntDtledt\");" + 
				"        oz.sendToActionScript(\"odi.wkplElcMgntDtledt.pcount\",\"10\");" + 
				"        oz.sendToActionScript(\"odi.wkplElcMgntDtledt.args1\",\"SESS_LANG="+userInfo.getLangCd()+"\");" +
				"        oz.sendToActionScript(\"odi.wkplElcMgntDtledt.args2\",\"cmpy_cd="+param.get("cmpy_cd")+"\");" +
				"        oz.sendToActionScript(\"odi.wkplElcMgntDtledt.args3\",\"wkpl_id="+param.get("wkpl_id")+"\");" +
				"        oz.sendToActionScript(\"odi.wkplElcMgntDtledt.args4\",\"elc_cl_cd="+param.get("elc_cl_cd")+"\");" +
				"        oz.sendToActionScript(\"odi.wkplElcMgntDtledt.args5\",\"ag_yn="+param.get("ag_yn")+"\");" +
				"        oz.sendToActionScript(\"odi.wkplElcMgntDtledt.args6\",\"elc_pric_nm="+param.get("elc_pric_nm")+"\");" +
				"        oz.sendToActionScript(\"odi.wkplElcMgntDtledt.args7\",\"mand_tlgl_trn_yn="+param.get("mand_tlgl_trn_yn")+"\");" +
				"        oz.sendToActionScript(\"odi.wkplElcMgntDtledt.args8\",\"trn_fcst_cert_reg_yn="+param.get("trn_fcst_cert_reg_yn")+"\");" +
				"        oz.sendToActionScript(\"odi.wkplElcMgntDtledt.args9\",\"sDate="+param.get("sDate")+"\");" +
				"        oz.sendToActionScript(\"odi.wkplElcMgntDtledt.args10\",\"eDate="+param.get("eDate")+"\");" +
				"        oz.sendToActionScript(\"viewer.exportcommand\",\"true\");" +
				"        oz.sendToActionScript(\"viewer.printcommand\",\"true\");" +
				"		 oz.sendToActionScript(\"viewer.showthumbnail\",\"true\");"	+
				"        oz.sendToActionScript(\"viewer.pagedisplay\",\"singlepagecontinuous\");" + 
				"        oz.sendToActionScript(\"viewer.viewmode\",\"fittoframe\");" + 
				"		 oz.sendToActionScript(\"export.filename\", "+"report"+");" +
				"		return true;" + 
				"	}" + 
				"	start_ozjs(\"OZViewer\",\""+reportviewer+"/\");" + 
				"</script>" + 
				"</body>" + 
				"</html>" + 
				"";
		
		return new ResponseEntity<>(oz, HttpStatus.OK);
	}
	 
	@SuppressWarnings("unchecked")
	@PostMapping("/report/acdtNewsRpt")
	public ResponseEntity<?> selectOzAcdtNewsRpt(@RequestBody Map<String, Object> param, HttpSession session) {
		
		UserInfo userInfo = sessionManager.get(session.getId());
		
		Map<String, ?> content = acdtMgntService.selectAcdtMgnt(param);	// 사고보고조사 상세 조회
		List<Map<String, ?>> occur_atfl_list = (List<Map<String, ?>>) content.get("occur_atfl_list");	//사고발생현장 사진 조회
		List<Map<String, ?>> reenact_atfl_list = (List<Map<String, ?>>) content.get("reenact_atfl_list");	//사고재현 사진 조회
		
		Map<String, ?> tempOccurReq = new HashMap<>();
		Map<String, ?> tempReenactReq = new HashMap<>();
			
		// 사고발생현장 사진
		if(occur_atfl_list.size() > 0) {
			tempOccurReq = occur_atfl_list.get(0);
		}
		
		// 재현 사진
		if(reenact_atfl_list.size() > 0) {
			tempReenactReq = reenact_atfl_list.get(0);
		}		
				
		String oz = "";
		oz = "<!DOCTYPE html>" + 
				"<html style=\"height:100%\">" + 
				"<head>" + 
				"<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"/>" +
				"<script src=\""+reportviewer+"/jquery/jquery-2.0.3.min.js\"></script>" + 
				"<link rel=\"stylesheet\" href=\""+reportviewer+"/jquery/jquery-ui.css\" type=\"text/css\"/>" + 
				"<script src=\""+reportviewer+"/jquery/jquery-ui.min.js\"></script>" + 
				"<link rel=\"stylesheet\" href=\""+reportviewer+"/ui.dynatree.css\" type=\"text/css\"/>" + 
				"<script type=\"text/javascript\" src=\""+reportviewer+"/jquery.dynatree.js\" charset=\"utf-8\"></script>" + 
				"<script type=\"text/javascript\" src=\""+reportviewer+"/OZJSViewer.js\" charset=\"utf-8\"></script>" +
				"</head>" + 
				
				
				"<body style=\"width:98%;height:98%\">" + 
				"<div id=\"OZViewer\" style=\"width:98%;height:98%\"></div>" + 
				"<script type=\"text/javascript\" >" + 
				"	function SetOZParamters_OZViewer(){" + 
				"	  var oz;" + 
				"		 oz = document.getElementById(\"OZViewer\");" + 
				"		 oz.sendToActionScript(\"connection.displayname\",\"report\");" + 
				"		 oz.sendToActionScript(\"connection.servlet\",\""+reportserver+"\");" + 
				"		 oz.sendToActionScript(\"connection.reportname\",\"/"+param.get("reportForm")+".ozr\");"+
				"        oz.sendToActionScript(\"odi.odinames\",\"acdtNewsRpt\");" + 
				"        oz.sendToActionScript(\"odi.acdtNewsRpt.pcount\",\"5\");" + 
				"        oz.sendToActionScript(\"odi.acdtNewsRpt.args1\",\"SESS_LANG="+userInfo.getLangCd()+"\");" +
				"        oz.sendToActionScript(\"odi.acdtNewsRpt.args2\",\"acdt_id="+ param.get("acdt_id")+"\");" +
				"        oz.sendToActionScript(\"odi.acdtNewsRpt.args3\",\"dfdt_sn="+ param.get("dfdt_sn")+"\");" +
				"        oz.sendToActionScript(\"odi.acdtNewsRpt.args4\",\"acdtOccurImgUrl="+ serverUrl + "/api/v1/public/download/" + tempOccurReq.get("acdt_atfl_no") +"\");" +
				"        oz.sendToActionScript(\"odi.acdtNewsRpt.args5\",\"acdtReenactImgUrl="+ serverUrl + "/api/v1/public/download/" + tempReenactReq.get("acdt_atfl_no") +"\");" +
				"        oz.sendToActionScript(\"viewer.exportcommand\",\"true\");" +
				"        oz.sendToActionScript(\"viewer.printcommand\",\"true\");" +
				"		 oz.sendToActionScript(\"viewer.showthumbnail\",\"true\");"	+
				"        oz.sendToActionScript(\"viewer.pagedisplay\",\"singlepagecontinuous\");" + 
				"        oz.sendToActionScript(\"viewer.viewmode\",\"fittoframe\");" + 
				"		 oz.sendToActionScript(\"export.filename\", "+"report"+");" +
				"		return true;" + 
				"	}" + 
				"	start_ozjs(\"OZViewer\",\""+reportviewer+"/\");" + 
				"</script>" + 
				"</body>" + 
				"</html>" + 
				"";
		
		return new ResponseEntity<>(oz, HttpStatus.OK);
	}
	
	@SuppressWarnings("unchecked")
	@PostMapping("/report/safechk")
	public ResponseEntity<?> selectOzReportSafeChk(@RequestBody Map<String, Object> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		String oz = "";
		oz = "<!DOCTYPE html>" + 
				"<html style=\"height:100%\">" + 
				"<head>" + 
				"<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"/>" + 
				"<script src=\""+reportviewer+"/jquery/jquery-2.0.3.min.js\"></script>" + 
				"<link rel=\"stylesheet\" href=\""+reportviewer+"/jquery/jquery-ui.css\" type=\"text/css\"/>" + 
				"<script src=\""+reportviewer+"/jquery/jquery-ui.min.js\"></script>" + 
				"<link rel=\"stylesheet\" href=\""+reportviewer+"/ui.dynatree.css\" type=\"text/css\"/>" + 
				"<script type=\"text/javascript\" src=\""+reportviewer+"/jquery.dynatree.js\" charset=\"utf-8\"></script>" + 
				"<script type=\"text/javascript\" src=\""+reportviewer+"/OZJSViewer.js\" charset=\"utf-8\"></script>" + 
				"</head>" + 
				"<body style=\"width:98%;height:98%\">" + 
				"<div id=\"OZViewer\" style=\"width:98%;height:98%\"></div>" + 
				"<script type=\"text/javascript\" >" + 
				"	function SetOZParamters_OZViewer(){" + 
				"	  var oz;" + 
				"		 oz = document.getElementById(\"OZViewer\");" + 
				"		 oz.sendToActionScript(\"connection.displayname\",\"report\");" + 
				"		 oz.sendToActionScript(\"connection.servlet\",\""+reportserver+"\");" + 
				"		 oz.sendToActionScript(\"connection.reportname\",\"/"+param.get("reportForm")+".ozr\");"+
				"        oz.sendToActionScript(\"odi.odinames\",\"safechk\");" + 
				"        oz.sendToActionScript(\"odi.safechk.pcount\",\"2\");" + 
				"        oz.sendToActionScript(\"odi.safechk.args1\",\"SESS_LANG="+userInfo.getLangCd()+"\");" +
				"        oz.sendToActionScript(\"odi.safechk.args2\",\"tgt_id="+param.get("tgt_id")+"\");" +
				"        oz.sendToActionScript(\"viewer.exportcommand\",\"true\");" +
				"        oz.sendToActionScript(\"viewer.printcommand\",\"true\");" +
				"		 oz.sendToActionScript(\"viewer.showthumbnail\",\"true\");"	+
				"        oz.sendToActionScript(\"viewer.pagedisplay\",\"singlepagecontinuous\");" + 
				"        oz.sendToActionScript(\"viewer.viewmode\",\"fittoframe\");" + 
				"		 oz.sendToActionScript(\"export.filename\", "+"report"+");";

			oz += 
				"		return true;" + 
				"	}" + 
				"	start_ozjs(\"OZViewer\",\""+reportviewer+"/\");" + 
				"</script>" + 
				"</body>" + 
				"</html>" + 
				"";
		
		return new ResponseEntity<>(oz, HttpStatus.OK);
	}

	@PostMapping("/report/riskEvalList")
	public ResponseEntity<?> selectOzRiskEval(@RequestBody Map<String, Object> param, HttpSession session) {
		String oz = "";
		
		UserInfo userInfo = sessionManager.get(session.getId());
		oz = "<!DOCTYPE html>" + 
				"<html style=\"height:100%\">" + 
				"<head>" + 
				"<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"/>" +
				"<script src=\""+reportviewer+"/jquery/jquery-2.0.3.min.js\"></script>" + 
				"<link rel=\"stylesheet\" href=\""+reportviewer+"/jquery/jquery-ui.css\" type=\"text/css\"/>" + 
				"<script src=\""+reportviewer+"/jquery/jquery-ui.min.js\"></script>" + 
				"<link rel=\"stylesheet\" href=\""+reportviewer+"/ui.dynatree.css\" type=\"text/css\"/>" + 
				"<script type=\"text/javascript\" src=\""+reportviewer+"/jquery.dynatree.js\" charset=\"utf-8\"></script>" + 
				"<script type=\"text/javascript\" src=\""+reportviewer+"/OZJSViewer.js\" charset=\"utf-8\"></script>" +
				"</head>" + 
				
				
				"<body style=\"width:98%;height:98%\">" + 
				"<div id=\"OZViewer\" style=\"width:98%;height:98%\"></div>" + 
				"<script type=\"text/javascript\" >" + 
				"	function SetOZParamters_OZViewer(){" + 
				"	  var oz;" + 
				"		 oz = document.getElementById(\"OZViewer\");" + 
				"		 oz.sendToActionScript(\"connection.displayname\",\"report\");" + 
				"		 oz.sendToActionScript(\"connection.servlet\",\""+reportserver+"\");" + 
				"		 oz.sendToActionScript(\"connection.reportname\",\"/"+param.get("reportForm")+".ozr\");"+
				"        oz.sendToActionScript(\"odi.odinames\",\"riskEvalList\");" + 
				"        oz.sendToActionScript(\"odi.riskEvalList.pcount\",\"5\");" + 
				"        oz.sendToActionScript(\"odi.riskEvalList.args1\",\"SESS_LANG="+userInfo.getLangCd()+"\");" +
				"        oz.sendToActionScript(\"odi.riskEvalList.args2\",\"dgsn_apal_id="+ param.get("dgsn_apal_id")+"\");" +
				"        oz.sendToActionScript(\"odi.riskEvalList.args3\",\"acdtOccurImgUrl="+ serverUrl + "/api/v1/public/download/" + 1 +"\");" +
				"        oz.sendToActionScript(\"odi.riskEvalList.args4\",\"acdtReenactImgUrl="+ serverUrl + "/api/v1/public/download/" + 11 +"\");" +
				"        oz.sendToActionScript(\"viewer.exportcommand\",\"true\");" +
				"        oz.sendToActionScript(\"viewer.printcommand\",\"true\");" +
				"		 oz.sendToActionScript(\"viewer.showthumbnail\",\"true\");"	+
				"        oz.sendToActionScript(\"viewer.pagedisplay\",\"singlepagecontinuous\");" + 
				"        oz.sendToActionScript(\"viewer.viewmode\",\"fittoframe\");" + 
				"		 oz.sendToActionScript(\"export.filename\", "+"report"+");" +
				"		return true;" + 
				"	}" + 
				"	start_ozjs(\"OZViewer\",\""+reportviewer+"/\");" + 
				"</script>" + 
				"</body>" + 
				"</html>" + 
				"";
		
		return new ResponseEntity<>(oz, HttpStatus.OK);
	}
	
}
