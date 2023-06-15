package com.kist.portal.common.aprv.rest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kist.portal.ch.mro.service.mroPchsService;
import com.kist.portal.ch.rgltchk.service.RgltChkService;
import com.kist.portal.common.email.rest.SendEmailUtil;
import com.kist.portal.common.utils.Constant;
import com.kist.portal.common.utils.MultiLanguageUtil;
import com.kist.portal.sh.acdtmgnt.service.AcdtMgntService;
import com.kist.portal.sh.cstprms.service.CstprmsService;
import com.kist.portal.sh.mtng.service.MtngService;
import com.kist.portal.sh.opem.service.OpemActnService;
import com.kist.portal.sh.opem.service.OpemMgntService;
import com.kist.portal.sh.rgltvltn.service.RgltVltnService;
import com.kist.portal.sh.riskeval.service.RiskEvalAftActService;
import com.kist.portal.sh.riskeval.service.RiskEvalService;
import com.kist.portal.sh.sftchk.service.SftChkService;
import com.kist.portal.sh.trnmgnt.service.TrnMgntService;
import com.kist.portal.st.stndinfr.service.InvsMgntService;

@RestController
@RequestMapping("/api/v1/common/aprv")
public class AprvController {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Value("${dgrm.front-url}")
	private String frontUrl;
	
	@Value("${dgrm.server-url}")
	private String serverUrl;
	
	@Autowired
	private mroPchsService mroService;
	
	@Autowired
	private RgltChkService rgltChkService;
	
	@Autowired
	private CstprmsService cstprmsService;
	
	@Autowired
	private TrnMgntService trnMgntservice;
	
	@Autowired
	private InvsMgntService invsMgntservice;
	
	@Autowired
	private SftChkService sftChkService;

	@Autowired
	private MtngService mtngService;

	@Autowired
	private RgltVltnService rgltvltnservice;
	
	@Autowired
	private OpemMgntService opemMgntService;
	
	@Autowired
	private OpemActnService opemActnService;

	@Autowired
	private AcdtMgntService acdtMgntService;
	
	@Autowired
	private RiskEvalService riskEvalService;
	
	@Autowired
	private RiskEvalAftActService riskEvalAftActService;
	
	@Autowired
	private SendEmailUtil util;
	
	@Autowired
	private MultiLanguageUtil mUtil;

//	0	취소
//	1	결재상신
//	2	결재진행
//	3	결재완료
//	9	반려

	//mro 구매 단건 구매 결재 후처리
	@Transactional
	public void aprvRevMropchs(String aprv_no, String aprv_stt_cd) throws Exception {
		//결제 번호로 mro구매번호 찾아서 담기 
		Map<String, String> param = new HashMap<>();
		param.put("aprv_no", aprv_no);
		param.put("SESS_LANG", "KO");

		//결제번호로 조회한 mro데이터
		Map<String, String> aprv_data = mroService.selectMroPchsAprvNo(param);
		
		if(aprv_data != null && !aprv_data.isEmpty())
		{
			param.put("mro_pchs_no", aprv_data.get("mro_pchs_no"));
			String result = "";
			
			if("3".equals(aprv_stt_cd)) {
				// MTRL_NO 채번
				Map<String, String> mtrl_data = mroService.selectMroMtrlNo(param);
				param.put("mtrl_no", mtrl_data.get("mtrl_no"));
				
				//결제 결과 승인일시 
				//mro 기본 데이터
				Map<String, String> mroSaveData = mroService.selectMroPchsDtl(param);
				//mro 자재 데이터
				Map<String, String> mtrlSaveData = mroService.selectMroPchsMtrlDtl(param);
				//mro 자재 물질 데이터
				List<Map<String, String>> sbstlist = mroService.selectMroPchsMtrlSbstDtlList(param);
				//mro 구매 표지 데이터
				Map<String, String> gwsgnSaveData = mroService.selectMroPchsGwsgnDtl(param);
				//mro 구매 그림문자
				List<Map<String, String>> picList = mroService.selectMroPicDtlList(param);
				
				mtrlSaveData.put("rglt_chk_req_usr_id", mroSaveData.get("pchs_usr_id"));
				mtrlSaveData.put("rglt_chk_wkpl_id", mroSaveData.get("wkpl_id"));
				
				// 자재마스터 등록 
				mroService.MrpPchsAprvSucsInsertMtrl(mtrlSaveData);
				mroService.MrpPchsAprvSucsInsertRgltChkMtrlSite(mtrlSaveData);
				
				// 구매처 직접입력 - 공급업체 테이블 등록
				if("Y".equals(mtrlSaveData.get("vndr_kyin_yn"))) {
					String key = rgltChkService.selectRgltChkVndrKey(mtrlSaveData);
					mtrlSaveData.put("rglt_chk_usr_id", mroSaveData.get("pchs_usr_id"));
					mtrlSaveData.put("vndr_no", key);
					mtrlSaveData.put("vndr_clsf_cd", "2");	// 공급업체_구분_코드(APCHEMS)
					mtrlSaveData.put("vndr_type_cd", "2");	// 공급업체_유형_코드(소모성자재)
					// 공급업체 등록
					rgltChkService.insertRgltChkVndrMstData(mtrlSaveData);
					
					// mro구매처 번호 업데이트[구매처코드 추가]
					param.put("vndr_no", key);
				}
				
				// MRO 구매 자재 - 자재_번호 업데이트[구매처코드 업데이트 추가]
				mroService.MrpPchsAprvSucsUpdateRgltChkPchsMtrl(param);
				
				String rglt_chk_no = mroService.selectRgltChkNo(mroSaveData).get("rglt_chk_no");
				mroSaveData.put("rglt_chk_no", rglt_chk_no);								// 규제검토번호
				mroSaveData.put("rglt_chk_req_usr_id", mroSaveData.get("pchs_usr_id"));		// 구매 사원코드
				
				mroService.MrpPchsAprvSucsInsertRgltChk(mroSaveData);
				
				mtrlSaveData.put("rglt_chk_no", rglt_chk_no);								// 규제검토번호
				mtrlSaveData.put("username", mroSaveData.get("pchs_usr_id"));				// 구매 사원코드
				mtrlSaveData.put("chk_req_clsf_cd", "2");									// 검토요청 구분 - 규제검토
				mroService.MrpPchsAprvSucsInsertRgltChkMtrl(mtrlSaveData);
				mroService.MrpPchsAprvSucsInsertRgltChkMsds(mtrlSaveData);
				
				for(int i=0 ; i<sbstlist.size(); i++) {
					Map<String, String> sbstsavedata = new HashMap<>();
					sbstsavedata = (Map<String, String>)sbstlist.get(i);
					sbstsavedata.put("rglt_chk_no", rglt_chk_no);
					sbstsavedata.put("rglt_chk_req_usr_id", mroSaveData.get("pchs_usr_id"));
					mroService.MrpPchsAprvSucsInsertRgltChkSbst(sbstsavedata);
				}
				// 예상규제 저장
				mroService.MroPchsAprvSucsInsertRgltChkRglt(mtrlSaveData); // to-do 규제검토 대상 법규 필터 조건 넣어야함
				rgltChkService.insertRgltChkReqRgltSbst(mtrlSaveData);	   // 예상 규제별 대상물질 저장
				rgltChkService.updateReqRgltChkRgltSave(mtrlSaveData);	   // 규제검토 목록 생성 후 규제대상 여부 업데이트
				
				gwsgnSaveData.put("rglt_chk_no", rglt_chk_no);
				gwsgnSaveData.put("rglt_chk_req_usr_id", mroSaveData.get("pchs_usr_id"));
				mroService.MrpPchsAprvSucsInsertRgltChkGwsgn(gwsgnSaveData);
				
				for(int i=0 ; i<picList.size(); i++) {
					Map<String, String> sbstsavedata = new HashMap<>();
					sbstsavedata = (Map<String, String>)picList.get(i);
					sbstsavedata.put("rglt_chk_no", rglt_chk_no);
					sbstsavedata.put("rglt_chk_req_usr_id", mroSaveData.get("pchs_usr_id"));
					mroService.MrpPchsAprvSucsInsertRgltChkGwsgnPicg(sbstsavedata);
				}
				// To-DO 규재검토 요청 메일발송
				sendRgltChkReqMroMail(rglt_chk_no);
				mroService.MroPchsAprvSucs(param);
				
			} else if("9".equals(aprv_stt_cd)) {
				//결제 결과 반려일시
				mroService.MroPchsAprvFail(param);
			} else if("0".equals(aprv_stt_cd) || "C".equals(aprv_stt_cd)) {
				//결제 결과 취소일시/결제창 닫기, 진행상태 작성중
				mroService.MroPchsAprvCancel(param);
			}
		}
	}
	
	//mro 구매 다건 구매 결재 후처리
	@Transactional
	public void aprvRevMropchsMulti(String aprv_no, String aprv_stt_cd) throws Exception {
		Map<String, String> param = new HashMap<>();
		param.put("aprv_no", aprv_no);
		param.put("SESS_LANG", "KO");

		//결제번호로 조회한 mro데이터
		Map<String, String> aprv_data = mroService.selectMroPchsAprvNo(param);
		
		if(aprv_data != null && !aprv_data.isEmpty())
		{
			param.put("mro_pchs_no", aprv_data.get("mro_pchs_no"));
			
			if("3".equals(aprv_stt_cd)) {
				//결제 결과 승인일시 
				mroService.MroPchsAprvSucs(param);
			} else if("9".equals(aprv_stt_cd)) {
				//결제 결과 반려일시
				mroService.MroPchsAprvFail(param);
			} else if("0".equals(aprv_stt_cd)) {
				//결제 결과 취소일시
				mroService.MroPchsAprvCancel(param);
			}
		}
	}
	
	//mro 일괄 결재 후처리
	@Transactional
	public void aprvRevMropchsPkg(String aprv_no, String aprv_stt_cd) throws Exception {
		Map<String, String> param = new HashMap<>();
		param.put("aprv_no", aprv_no);
		param.put("SESS_LANG", "KO");

		//결제번호로 조회한 mro데이터
		List<Map<String, String>> aprv_data_list = mroService.selectMroPchsAprvNoList(param);
		
		//if(aprv_data != null && !aprv_data.isEmpty())
		for(int i=0 ; i<aprv_data_list.size(); i++)
		{
			Map<String, String> aprv_data = aprv_data_list.get(i);
			param.put("mro_pchs_no", aprv_data.get("mro_pchs_no"));
			
			// 결재 상태에 따라 처리..
			if("3".equals(aprv_stt_cd)) {
				// 최초 구매일 경우
				if("N".equals(aprv_data.get("multi_yn"))) {
					// MTRL_NO 채번
					Map<String, String> mtrl_data = mroService.selectMroMtrlNo(param);
					param.put("mtrl_no", mtrl_data.get("mtrl_no"));
					
					//결제 결과 승인일시 
					//mro 기본 데이터
					Map<String, String> mroSaveData = mroService.selectMroPchsDtl(param);
					//mro 자재 데이터
					Map<String, String> mtrlSaveData = mroService.selectMroPchsMtrlDtl(param);
					//mro 자재 물질 데이터
					List<Map<String, String>> sbstlist = mroService.selectMroPchsMtrlSbstDtlList(param);
					//mro 구매 표지 데이터
					Map<String, String> gwsgnSaveData = mroService.selectMroPchsGwsgnDtl(param);
					//mro 구매 그림문자
					List<Map<String, String>> picList = mroService.selectMroPicDtlList(param);
					
					mtrlSaveData.put("rglt_chk_req_usr_id", mroSaveData.get("pchs_usr_id"));
					mtrlSaveData.put("rglt_chk_wkpl_id", mroSaveData.get("wkpl_id"));
					
					// 자재마스터 등록 
					mroService.MrpPchsAprvSucsInsertMtrl(mtrlSaveData);
					mroService.MrpPchsAprvSucsInsertRgltChkMtrlSite(mtrlSaveData);
					
					// 구매처 직접입력 - 공급업체 테이블 등록
					if("Y".equals(mtrlSaveData.get("vndr_kyin_yn"))) {
						String key = rgltChkService.selectRgltChkVndrKey(mtrlSaveData);
						mtrlSaveData.put("rglt_chk_usr_id", mroSaveData.get("pchs_usr_id"));
						mtrlSaveData.put("vndr_no", key);
						mtrlSaveData.put("vndr_clsf_cd", "2");	// 공급업체_구분_코드(APCHEMS)
						mtrlSaveData.put("vndr_type_cd", "2");	// 공급업체_유형_코드(소모성자재)
						// 공급업체 등록
						rgltChkService.insertRgltChkVndrMstData(mtrlSaveData);
						
						// mro구매처 번호 업데이트[구매처코드 추가]
						param.put("vndr_no", key);
					}
					
					// MRO 구매 자재 - 자재_번호 업데이트[구매처코드 업데이트 추가]
					mroService.MrpPchsAprvSucsUpdateRgltChkPchsMtrl(param);
					
					String rglt_chk_no = mroService.selectRgltChkNo(mroSaveData).get("rglt_chk_no");
					mroSaveData.put("rglt_chk_no", rglt_chk_no);								// 규제검토번호
					mroSaveData.put("rglt_chk_req_usr_id", mroSaveData.get("pchs_usr_id"));		// 구매 사원코드
					
					mroService.MrpPchsAprvSucsInsertRgltChk(mroSaveData);
					
					mtrlSaveData.put("rglt_chk_no", rglt_chk_no);								// 규제검토번호
					mtrlSaveData.put("username", mroSaveData.get("pchs_usr_id"));				// 구매 사원코드
					mtrlSaveData.put("chk_req_clsf_cd", "2");									// 검토요청 구분 - 규제검토
					mroService.MrpPchsAprvSucsInsertRgltChkMtrl(mtrlSaveData);
					mroService.MrpPchsAprvSucsInsertRgltChkMsds(mtrlSaveData);
					
					for(int j=0 ; j<sbstlist.size(); j++) {
						Map<String, String> sbstsavedata = new HashMap<>();
						sbstsavedata = (Map<String, String>)sbstlist.get(j);
						sbstsavedata.put("rglt_chk_no", rglt_chk_no);
						sbstsavedata.put("rglt_chk_req_usr_id", mroSaveData.get("pchs_usr_id"));
						mroService.MrpPchsAprvSucsInsertRgltChkSbst(sbstsavedata);
					}
					// 예상규제 저장
					mroService.MroPchsAprvSucsInsertRgltChkRglt(mtrlSaveData); // to-do 규제검토 대상 법규 필터 조건 넣어야함
					rgltChkService.insertRgltChkReqRgltSbst(mtrlSaveData);	   // 예상 규제별 대상물질 저장
					rgltChkService.updateReqRgltChkRgltSave(mtrlSaveData);	   // 규제검토 목록 생성 후 규제대상 여부 업데이트
					
					gwsgnSaveData.put("rglt_chk_no", rglt_chk_no);
					gwsgnSaveData.put("rglt_chk_req_usr_id", mroSaveData.get("pchs_usr_id"));
					mroService.MrpPchsAprvSucsInsertRgltChkGwsgn(gwsgnSaveData);
					
					for(int j=0 ; j<picList.size(); j++) {
						Map<String, String> sbstsavedata = new HashMap<>();
						sbstsavedata = (Map<String, String>)picList.get(j);
						sbstsavedata.put("rglt_chk_no", rglt_chk_no);
						sbstsavedata.put("rglt_chk_req_usr_id", mroSaveData.get("pchs_usr_id"));
						mroService.MrpPchsAprvSucsInsertRgltChkGwsgnPicg(sbstsavedata);
					}
					// To-DO 규재검토 요청 메일발송
					sendRgltChkReqMroMail(rglt_chk_no);
					mroService.MroPchsAprvSucs(param);
				} else {
					//결제 결과 승인일시 
					mroService.MroPchsAprvSucs(param);
				}
			} else if("9".equals(aprv_stt_cd)) {
				//결제 결과 반려일시
				mroService.MroPchsAprvFail(param);
			} else if("0".equals(aprv_stt_cd)) {
				//결제 결과 취소일시
				mroService.MroPchsAprvCancel(param);
			}
		}
	}

	// 규제검토 결재 후처리
	@Transactional
	public void updateRgltChkAprv(String aprv_no, String aprv_stt_cd) throws Exception {
		// 결제번호 저장
		Map<String, String> param = new HashMap<>();
		param.put("aprv_no", aprv_no);

		// 규제검토 결재정보 조회
		List<Map<String, ?>> aprvInfo = rgltChkService.selectRgltChkAprvBasicInfo(param);
		
		if(aprvInfo != null && !aprvInfo.isEmpty()) {
			Map<String, String> tempInfo = (Map<String, String>) aprvInfo.get(0);
			
			// 1 : 결재상신 - 결재상신 즉시 결재상신 상태임으로 처리내용 없음 
			// 2 : 결재중   - 결재중 처리내용 없음		
			if ("3".equals(aprv_stt_cd)) {
				// 규제검토 경고표지 공급업체 정보 조회
				List<Map<String, ?>> gwsgnData = rgltChkService.selectRgltChkReqGwsgnList(tempInfo);
				
				if(gwsgnData != null && !gwsgnData.isEmpty()) {
					Map<String, String> gwsgnInfo = (Map<String, String>) gwsgnData.get(0);
					
					// 결재 완료
					rgltChkService.deleteRgltChkAprvSbstAll(tempInfo);		// 물질 삭제
					rgltChkService.deleteRgltChkAprvRgltAll(tempInfo);		// 규제정보 삭제
					rgltChkService.deleteRgltChkAprvRgltSbstAll(tempInfo);	// 규제물질 삭제
					rgltChkService.deleteRgltChkAprvGwsgnAll(tempInfo);		// 경고표지 삭제
					rgltChkService.deleteRgltChkAprvPicgAll(tempInfo);		// 그림문자 삭제
					rgltChkService.deleteRgltChkAprvMsdsAll(tempInfo);		// MSDS 삭제
					
					rgltChkService.updateRgltChkAprvMtrlInfo(tempInfo);		// 자재정보 수정
					
					// 진행상태 완료
					tempInfo.put("rglt_chk_prog_stt_cd", "80");
					rgltChkService.updateRgltChkAprvChkInfo(tempInfo);		// 규제검토 진행상태 수정
					
					rgltChkService.insertRgltChkAprvSbst(tempInfo);			// 물질 추가
					rgltChkService.insertRgltChkAprvRgltList(tempInfo);		// 규제정보 추가
					rgltChkService.insertRgltChkAprvRgltSbst(tempInfo);		// 규제물질 추가
					rgltChkService.insertRgltChkAprvGwsgn(tempInfo);		// 경고표지 추가
					rgltChkService.insertRgltChkAprvPicg(tempInfo);			// 그림문자 추가
					rgltChkService.insertRgltChkAprvMsds(tempInfo); 		// MSDS 추가
					
					// 공급업체 직접입력 - 공급업체 테이블 등록
					if("Y".equals(gwsgnInfo.get("vndr_kyin_yn"))) {
						// 공급업체 번호 생성
						String vndr_no = rgltChkService.selectRgltChkVndrKey(tempInfo);
						gwsgnInfo.put("rglt_chk_usr_id", tempInfo.get("rglt_chk_usr_id"));
						gwsgnInfo.put("vndr_no", vndr_no);									// 공급업체 번호
						gwsgnInfo.put("vndr_clsf_cd", tempInfo.get("mtrl_clsf_cd"));		// 자재_구분_코드 = 공급업체_구분_코드
						// 공급업체 등록
						rgltChkService.insertRgltChkVndrMstData(gwsgnInfo);
						
						// 자재 공급업체 추가 데이터 생성
						gwsgnInfo.put("vndr_no", vndr_no);					// 공급업체 번호
						tempInfo.put("vndr_no", vndr_no);
						// 공급업체 정보 업데이트
						rgltChkService.updateRgltChkAprvGwsgn(tempInfo);
					}
					
					// 자재 공급업체 추가
					rgltChkService.insertRgltChkMtrlVndrData(gwsgnInfo); 
					
					// 위험물 확인
					tempInfo.put("rglt_no", "RGLT000020");	// 규제-위험물
					tempInfo.put("law_cd", "L04");			// 위험물안전관리법
					tempInfo.put("SESS_LANG", "KO");
					List<Map<String, ?>> rgltSbstCnt = rgltChkService.selectRgltChkRgltSbstCnt(tempInfo);
					if(rgltSbstCnt != null && !rgltSbstCnt.isEmpty()) {
						Map<String, String> rgltSbstCntInfo = (Map<String, String>) rgltSbstCnt.get(0);
					
						if(Integer.parseInt(String.valueOf(rgltSbstCntInfo.get("cnt"))) > 0) {
							//위험물인경우 MES 전송 테이블에 데이터 추가
							rgltChkService.insertDngrMtrlMesSend(tempInfo);
						}
					}
					// 경고표지 MES 전송 테이블에 데이터 추가
					rgltChkService.insertMtrlGwsgnMesSend(tempInfo);
					
				}
			} else if("9".equals(aprv_stt_cd)) {
				// 결제결과 반려, 진행상태 결재반려
				tempInfo.put("rglt_chk_prog_stt_cd", "70");
				rgltChkService.updateRgltChkAprvChkInfo(tempInfo);		// 규제검토 진행상태 수정	
			} else if("0".equals(aprv_stt_cd) || "C".equals(aprv_stt_cd)) { 
				// 결제결과 취소/결제창 닫기, 진행상태 작성중
				tempInfo.put("rglt_chk_prog_stt_cd", "50");
				rgltChkService.updateRgltChkAprvChkInfo(tempInfo);		// 규제검토 진행상태 수정	
			}
		}
	}
	
	// 검토요청 MRO 메일
	@Transactional
	@SuppressWarnings("unchecked")
	public boolean sendRgltChkReqMroMail(String rgltChkNo) {
		// 언어, 시간 지정
		Map<String, String> param = new HashMap<>();
		param.put("rglt_chk_no", rgltChkNo);
		param.put("chk_req_clsf_cd", "2");
		param.put("SESS_LANG", "KO");
		param.put("SESS_TIME_ZONE", "Asia/Seoul");

		String langCd = "KO";
		
		// 규제검토상세, 자재 정보
		List<Map<String, ?>> basicList = rgltChkService.selectRgltChkMstDtl(param);
		// 구성물질 리스트
		List<Map<String, ?>> sbstList = rgltChkService.selectRgltChkReqSbstList(param);
		// 규제검토 정보
		List<Map<String, ?>> rgltList = rgltChkService.selectRgltChkReqRgltList(param);
		// 경고 표지 정보
		List<Map<String, ?>> alertList = rgltChkService.selectRgltChkReqGwsgnList(param);
		// 그림문자 정보
		List<Map<String, ?>> picList = rgltChkService.selectRgltChkReqGwsgnPicList(param);
		
		// MRO-IMK 페이지
		String pageUrl = "rgltchkimkedt";
		
		// 다국어 처리
		String[] mlangNo = {"LB00000337", "LB00000198", "LB00000420",
				"LB00000151", "LB00000283", "LB00000284", "LB00000416",
				"LB00000150", "LB00000149", "LB00000405", "LB00000024",
				"LB00000025", "LB00000177", "LB00000022", "LB00000023",
				"LB00000176", "LB00000404", "LB00000403", "LB00000402",
				"LB00000355", "LB00000354", "LB00000353", "LB00000401",
				"LB00000407", "LB00000196", "LB00000195", "LB00000400",
				"LB00000270", "LB00000394", "LB00000196", "LB00000195",
				"LB00000268", "LB00000322", "LB00000028", "LB00000212",
				"LB00000408", "LB00000095", "LB00000038", "LB00000025",
				"LB00000173", "LB00000172", "LB00000193", "LB00000192",
				"LB00000191", "LB00000190", "LB00000045", "LB00000189",
				"LB00000130", "LB00000186", "LB00000185", "LB00000184",
				"LB00000183", "LB00000182", "LB00000528"};
		List<Map<String, String>> mlangCntn = mUtil.getMLangList(Arrays.asList(mlangNo), langCd);
		
		// 그림문자 html 태그
		String subTag = "";
		for(int i=0; i<picList.size(); i++) {
			if(picList.get(i).get("picg_cd").toString().equalsIgnoreCase("1")) {
				subTag += "<p><img src=\"" + frontUrl + "/images/P01.png\" alt=\"\" class=\"\" style=\"background-repeat: no-repeat; box-sizing: inherit; padding: 0px; margin: 0px; float: left; border-style: none; width: 120px; color: rgb(68, 68, 68); font-family: arita-dotum; font-size: 13px; text-align: center; transition: none 0s ease 0s !important;\"></p>";
			} else if(picList.get(i).get("picg_cd").toString().equalsIgnoreCase("2")) {
				subTag += "<p><img src=\"" + frontUrl + "/images/P02.png\" alt=\"\" class=\"\" style=\"background-repeat: no-repeat; box-sizing: inherit; padding: 0px; margin: 0px; float: left; border-style: none; width: 120px; color: rgb(68, 68, 68); font-family: arita-dotum; font-size: 13px; text-align: center; transition: none 0s ease 0s !important;\"></p>";
			} else if(picList.get(i).get("picg_cd").toString().equalsIgnoreCase("3")) {
				subTag += "<p><img src=\"" + frontUrl + "/images/P03.png\" alt=\"\" class=\"\" style=\"background-repeat: no-repeat; box-sizing: inherit; padding: 0px; margin: 0px; float: left; border-style: none; width: 120px; color: rgb(68, 68, 68); font-family: arita-dotum; font-size: 13px; text-align: center; transition: none 0s ease 0s !important;\"></p>";
			} else if(picList.get(i).get("picg_cd").toString().equalsIgnoreCase("4")) {
				subTag += "<p><img src=\"" + frontUrl + "/images/P04.png\" alt=\"\" class=\"\" style=\"background-repeat: no-repeat; box-sizing: inherit; padding: 0px; margin: 0px; float: left; border-style: none; width: 120px; color: rgb(68, 68, 68); font-family: arita-dotum; font-size: 13px; text-align: center; transition: none 0s ease 0s !important;\"></p>";
			} else if(picList.get(i).get("picg_cd").toString().equalsIgnoreCase("5")) {
				subTag += "<p><img src=\"" + frontUrl + "/images/P05.png\" alt=\"\" class=\"\" style=\"background-repeat: no-repeat; box-sizing: inherit; padding: 0px; margin: 0px; float: left; border-style: none; width: 120px; color: rgb(68, 68, 68); font-family: arita-dotum; font-size: 13px; text-align: center; transition: none 0s ease 0s !important;\"></p>";
			} else if(picList.get(i).get("picg_cd").toString().equalsIgnoreCase("6")) {
				subTag += "<p><img src=\"" + frontUrl + "/images/P06.png\" alt=\"\" class=\"\" style=\"background-repeat: no-repeat; box-sizing: inherit; padding: 0px; margin: 0px; float: left; border-style: none; width: 120px; color: rgb(68, 68, 68); font-family: arita-dotum; font-size: 13px; text-align: center; transition: none 0s ease 0s !important;\"></p>";
			} else if(picList.get(i).get("picg_cd").toString().equalsIgnoreCase("7")) {
				subTag += "<p><img src=\"" + frontUrl + "/images/P07.png\" alt=\"\" class=\"\" style=\"background-repeat: no-repeat; box-sizing: inherit; padding: 0px; margin: 0px; float: left; border-style: none; width: 120px; color: rgb(68, 68, 68); font-family: arita-dotum; font-size: 13px; text-align: center; transition: none 0s ease 0s !important;\"></p>";
			} else if(picList.get(i).get("picg_cd").toString().equalsIgnoreCase("8")) {
				subTag += "<p><img src=\"" + frontUrl + "/images/P08.png\" alt=\"\" class=\"\" style=\"background-repeat: no-repeat; box-sizing: inherit; padding: 0px; margin: 0px; float: left; border-style: none; width: 120px; color: rgb(68, 68, 68); font-family: arita-dotum; font-size: 13px; text-align: center; transition: none 0s ease 0s !important;\"></p>";
			} else if(picList.get(i).get("picg_cd").toString().equalsIgnoreCase("9")) {
				subTag += "<p><img src=\"" + frontUrl + "/images/P09.png\" alt=\"\" class=\"\" style=\"background-repeat: no-repeat; box-sizing: inherit; padding: 0px; margin: 0px; float: left; border-style: none; width: 120px; color: rgb(68, 68, 68); font-family: arita-dotum; font-size: 13px; text-align: center; transition: none 0s ease 0s !important;\"></p>";
			}
		}
		
		// 구성물질 html 태그
		String subTagSbst = "";
		for(int i=0; i<sbstList.size(); i++) {
			subTagSbst += "            <tr>\r\n" + 
					      "                <td style=\"width: 3%; height: 24px; border-left: 1px solid rgb(204, 204, 204); border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					      "                    <p>" + (i+1) + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "                <td style=\"width: 15%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					      "                    <p>" + sbstList.get(i).get("sbst_type_nm") + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "                <td style=\"width: 15%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					      "                    <p>" + sbstList.get(i).get("cas_no") + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "                <td style=\"width: 42%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
					      "                    <p>" + sbstList.get(i).get("sbst_nm") + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "                <td style=\"width: 10%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					      "                    <p>" + sbstList.get(i).get("sbst_rati") + "<br></p>\r\n" + 
					      "                </td>\r\n" +
					      "            </tr>\r\n";
		}
		
		// 규제정보 html 태그
		String subTagRglt = "";
		for(int i=0; i<rgltList.size(); i++) {
			subTagRglt += "            <tr>\r\n" + 
					      "                <td style=\"width: 3%; height: 24px; border-left: 1px solid rgb(204, 204, 204); border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					      "                    <p>" + (i+1) + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "                <td style=\"width: 10%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					      "                    <p>" + rgltList.get(i).get("law_nm") + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "                <td style=\"width: 25%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
					      "                    <p>" + rgltList.get(i).get("rglt_nm") + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "                <td style=\"width: 10%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					      "                    <p>" + rgltList.get(i).get("org_tgt_yn") + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "                <td style=\"width: 25%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
					      "                    <p>" + rgltList.get(i).get("cas_no") + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "                <td style=\"width: 21%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					      "                    <p>" + rgltList.get(i).get("rglt_note") + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "            </tr>\r\n";
		}
		
		// 경고표지 html 태그
		String subTagAlert = "";
		for(int i=0; i<alertList.size(); i++) {
			subTagAlert += "    <p style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\"><br></p>\r\n" + 
					       "    <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + mUtil.getMLang(mlangCntn, "LB00000038") + "</p>\r\n" + 
					       "    <table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;\">\r\n" + 
					       "        <tbody>\r\n" + 
					       "            <tr>\r\n" + 
					       "                <td colspan=\"2\" style=\"width: 20%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					       "                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000025") + "</p>\r\n" + 
					       "                </td>\r\n" + 
					       "                <td colspan=\"3\" style=\"width: 80%; height: 25px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 3px;\">\r\n" + 
					       "                    <p>" + basicList.get(0).get("mtrl_nm") + "<br></p>\r\n" + 
					       "                </td>\r\n" +
					       "            </tr>\r\n" + 
					       "            <tr>\r\n" + 
					       "                <td colspan=\"2\" style=\"width: 20%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					       "                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000173") + "</p>\r\n" + 
					       "                </td>\r\n" + 
					       "                <td colspan=\"3\" style=\"width: 80%; height: 25px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 3px;\">\r\n" + 
					       "                    <p>" + alertList.get(0).get("sgw_nm") + "<br></p>\r\n" + 
					       "                </td>\r\n" + 
					       "            </tr>\r\n" + 
					       "            <tr>\r\n" + 
					       "                <td colspan=\"2\" style=\"width: 20%; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					       "                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000172") + "</p>\r\n" + 
					       "                </td>\r\n" + 
					       "                <td colspan=\"3\" style=\"width: 80%; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 3px;\">\r\n" + 
					       subTag + 
					       "                </td>\r\n" + 
					       "            </tr>\r\n" + 
					       "            <tr>\r\n" + 
					       "                <td colspan=\"2\" style=\"width: 20%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					       "                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000193") + "</p>\r\n" + 
					       "                </td>\r\n" + 
					       "                <td colspan=\"3\" style=\"width: 80%; height: 25px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 3px;\">\r\n" + 
					       "                    <p>" + alertList.get(0).get("hars_cntn").toString().replaceAll("(\r\n|\r|\n|\n\r)", "<br/>") + "</p>\r\n" + 
					       "                </td>\r\n" + 
					       "            </tr>\r\n" + 
					       "            <tr>\r\n" + 
					       "                <td rowspan=\"4\" style=\"width: 13%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					       "                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000192") + "</p>\r\n" + 
					       "                </td>\r\n" + 
					       "                <td style=\"width: 7%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					       "                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000191") + "</p>\r\n" + 
					       "                </td>\r\n" + 
					       "                <td colspan=\"3\" style=\"margin: 3px 3px 3px 3px; width: 80%; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 3px;\">\r\n" + 
					       "                    <p>" + alertList.get(0).get("prv_cntn").toString().replaceAll("(\r\n|\r|\n|\n\r)", "<br/>") + "</p>\r\n" + 
					       "                </td>\r\n" + 
					       "            </tr>\r\n" + 
					       "            <tr>\r\n" + 
					       "                <td style=\"width: 119px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					       "                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000190") + "</p>\r\n" + 
					       "                </td>\r\n" + 
					       "                <td colspan=\"3\" style=\"margin: 3px 3px 3px 3px; width: 80%; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 3px;\">\r\n" + 
					       "                    <p>" + alertList.get(0).get("act_cntn").toString().replaceAll("(\r\n|\r|\n|\n\r)", "<br/>") + "</p>\r\n" + 
					       "                </td>\r\n" + 
					       "            </tr>\r\n" + 
					       "            <tr>\r\n" + 
					       "                <td style=\"width: 119px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					       "                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000045") + "</p>\r\n" + 
					       "                </td>\r\n" + 
					       "                <td colspan=\"3\" style=\"margin: 3px 3px 3px 3px; width: 80%; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 3px;\">\r\n" + 
					       "					 <p>" + alertList.get(0).get("str_cntn").toString().replaceAll("(\r\n|\r|\n|\n\r)", "<br/>") + "</p>\r\n" +
					       "                </td>\r\n" + 
					       "            </tr>\r\n" + 
					       "            <tr>\r\n" + 
					       "                <td style=\"width: 119px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					       "                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000189") + "</p>\r\n" + 
					       "                </td>\r\n" + 
					       "                <td colspan=\"3\" style=\"margin: 3px 3px 3px 3px; width: 80%; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 3px;\">\r\n" + 
					       "					 <p>" + alertList.get(0).get("scr_cntn").toString().replaceAll("(\r\n|\r|\n|\n\r)", "<br/>") + "</p>\r\n" +
					       "                </td>\r\n" + 
					       "            </tr>\r\n" + 
					       "            <tr>\r\n" + 
					       "                <td colspan=\"2\" style=\"width: 25%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					       "                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000130") + "</p>\r\n" + 
					       "                </td>\r\n" + 
					       "                <td style=\"width: 25%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 3px;\">\r\n" + 
					       "                    <p>" + alertList.get(0).get("vndr_nm") + "<br></p>\r\n" + 
					       "                </td>\r\n" + 
					       "                <td style=\"width: 20%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					       "                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000186") + "</p>\r\n" + 
					       "                </td>\r\n" + 
					       "                <td style=\"width: 30%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 3px;\">\r\n" + 
					       "                    <p>" + alertList.get(0).get("vndr_addr") + "<br></p>\r\n" + 
					       "                </td>\r\n" + 
					       "            </tr>\r\n" + 
					       "            <tr>\r\n" + 
					       "                <td colspan=\"2\" style=\"width: 119px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					       "                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000185") + "</p>\r\n" + 
					       "                </td>\r\n" + 
					       "                <td style=\"width: 302px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 3px;\">\r\n" + 
					       "                    <p>" + alertList.get(0).get("nat_nm") + "<br></p>\r\n" + 
					       "                </td>\r\n" + 
					       "                <td style=\"width: 211px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					       "                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000184") + "</p>\r\n" + 
					       "                </td>\r\n" + 
					       "                <td style=\"width: 212px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 3px;\">\r\n" + 
					       "                    <p>" + alertList.get(0).get("vndr_phon_no") + "<br></p>\r\n" + 
					       "                </td>\r\n" + 
					       "            </tr>\r\n" + 
					       "            <tr>\r\n" + 
					       "                <td colspan=\"2\" style=\"width: 119px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					       "                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000183") + "</p>\r\n" + 
					       "                </td>\r\n" + 
					       "                <td style=\"width: 302px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
					       "                    <p>" + alertList.get(0).get("vndr_rprs_nm") + "<br></p>\r\n" + 
					       "                </td>\r\n" + 
					       "                <td style=\"width: 211px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					       "                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000182") + "</p>\r\n" + 
					       "                </td>\r\n" + 
					       "                <td style=\"width: 212px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
					       "                    <p>" + alertList.get(0).get("vndr_coreg_no") + "<br></p>\r\n" + 
					       "                </td>\r\n" + 
					       "            </tr>\r\n" + 
					       "        </tbody>\r\n" + 
					       "    </table>\r\n";
		}
		
		// 메일 업무 코드
		String bizCd = "10";
		// 메일 제목
		String subject = mUtil.getMLang(mlangCntn, "LB00000337") + "(" + basicList.get(0).get("mtrl_nm") + ")";
		// 메일 내용
		String content = "<div>\r\n" + 
				"    <p>\r\n" + 
				"        <spam style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + mUtil.getMLang("LB00000198", langCd) + " :&nbsp;</spam><a href=\"" + frontUrl + "/rgltchk/" + pageUrl + "/" + basicList.get(0).get("rglt_chk_no") + "\" style=\"font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + frontUrl + "/rgltchk/" + pageUrl + "/" + basicList.get(0).get("rglt_chk_no") + "</a>\r\n" + 
				"    </p>\r\n" + 
				"    <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + mUtil.getMLang("LB00000420", langCd) + "</p>\r\n" + 
				"    <table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;\">\r\n" + 
				"        <tbody>\r\n" + 
				"            <tr>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000151") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("rglt_chk_no") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000150") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 28%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("rglt_chk_req_usr_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000149") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("rglt_chk_req_dt") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"        </tbody>\r\n" + 
				"    </table>\r\n" + 
				"    <p style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\"><br></p>\r\n" + 
				"    <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + mUtil.getMLang(mlangCntn, "LB00000405") + "</p>\r\n" + 
				"    <table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;\">\r\n" + 
				"        <tbody>\r\n" + 
				"            <tr>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000024") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("mtrl_no") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000025") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 28%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("mtrl_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000177") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + "<a href=\"" + serverUrl + "/api/v1/public/download/" + basicList.get(0).get("ko_atfl_no") + "\">" + basicList.get(0).get("ko_atfl_nm") + "</a>" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" +
				"            <tr>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000022") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("mtrl_clsf_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000023") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 28%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("macl_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000176") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n";

				if(!"".equals(basicList.get(0).get("en_atfl_no")) && null != (basicList.get(0).get("en_atfl_no"))) {
					content += "                    <p>" + "<a href=\"" + serverUrl + "/api/v1/public/download/" + basicList.get(0).get("en_atfl_no") + "\">" + basicList.get(0).get("en_atfl_nm") + "</a>" + "<br></p>\r\n";
				}
		
				content +=		
				"                </td>\r\n" + 
				"            </tr>\r\n" +
				"            <tr>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000404") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("mtrl_reg_dt") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000403") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 28%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("mtrl_reg_usr_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000402") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("mtrl_site") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" +
				"            <tr>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000355") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("phase_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000354") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 28%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("mtrl_puse_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000353") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("mtrl_puse_cntn") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n";
		// 자재구분 MRO인경우 단위중량 조회
		if ("2".equals(basicList.get(0).get("mtrl_clsf_cd"))) {
			content += 
		   		"            <tr>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000528") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"5\" style=\"width: 88%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("pchs_unit_weght") + "&nbsp;" + basicList.get(0).get("pchs_unit_nm") + "<br></p>\r\n" +  
				"                </td>\r\n" +  
				"            </tr>\r\n";
		}				
			content +=		
				"        </tbody>\r\n" + 
				"    </table>\r\n" +  
				"    <p style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\"><br></p>\r\n" + 
				"    <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + mUtil.getMLang(mlangCntn, "LB00000400") + "</p>\r\n" + 
				"    <table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px; text-align: center;\">\r\n" + 
				"        <tbody>\r\n" + 
				"            <tr>\r\n" + 
				"                <td style=\"width: 3%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "No." + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 15%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000270") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 15%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "CAS No." + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 42%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000196") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 10%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000195") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				subTagSbst + 
				"        </tbody>\r\n" + 
				"    </table>\r\n" +
				"    <p style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\"><br></p>\r\n" + 
				"    <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + mUtil.getMLang(mlangCntn, "LB00000268") + "</p>\r\n" + 
				"    <table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;\">\r\n" + 
				"        <tbody>\r\n" + 
				"            <tr>\r\n" + 
				"                <td style=\"width: 3%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "No." + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 10%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000322") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 25%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000028") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 10%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000212") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 25%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000408") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 21%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000095") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				subTagRglt + 
				"        </tbody>\r\n" + 
				"    </table>\r\n" +
				subTagAlert +
				"</div>";
		
		Map<String, String> map = new HashMap();
		map.put("wkpl_id", basicList.get(0).get("wkpl_id").toString());
		map.put("SESS_LANG", langCd);
		// 메일 수신자
		List<Map<String, ?>> mailUser = rgltChkService.selectRgltChkReqMailReceiver(map);
		String[] to = new String[mailUser.size()];
		for(int i=0; i<mailUser.size(); i++) {
			to[i] = mailUser.get(i).get("usr_id").toString();
		}
		// 메일 송신
		Boolean mailResult = util.sendMail(bizCd, basicList.get(0).get("rglt_chk_req_usr_id").toString(), subject, content, to);
		
		return mailResult; 
	}
	
//	0	취소
//	1	결재상신
//	2	결재진행
//	3	결재완료
//	9	반려
	// 작업허가 결재 후 처리
	@SuppressWarnings("unchecked")
	@Transactional
	public ResponseEntity<?> aprvConOprnPrmsCnfm(String aprv_no, String aprv_stt_cd) {
	
		int a = 0;
		
		Map<String, String> info = new HashMap<>();
		info.put("con_req_aprv_no", aprv_no);
		
		// 결재정보 조회
		List<Map<String, ?>> aprvInfo = cstprmsService.selectConOprnPrmsAprvInfo(info);

		if(aprvInfo != null && !aprvInfo.isEmpty()) {
			Map<String, String> tempInfo = (Map<String, String>) aprvInfo.get(0);
			tempInfo.put("username", "APPR_BATCH");
			
			// 결재 완료
			if ("3".equals(aprv_stt_cd)) {
				tempInfo.put("con_req_prst_cd", "S03");
				
				a = cstprmsService.updateConOprnPrmsMainStatAprv(tempInfo);

				// 모바일 데이터 생성
				tempInfo.put("wkpl_chec_cl_cd", "WC02");
				tempInfo.put("wkpl_chec_sn", "1");
				tempInfo.put("con_chk_txt", "");
				tempInfo.put("con_chk_prst_cd", "40");
				// 재상신일 경우가 있어 존재여부 체크
				String mobileDataExistYn = "N";
				mobileDataExistYn = cstprmsService.selectConOprnPrmsmobileDataExistYn(tempInfo);
				logger.info("==> selectConOprnPrmsmobileDataExistYn mobileDataExistYn :" + mobileDataExistYn);
				// 점검 데이터가 존재하지 않다면
				if(!"Y".equals(mobileDataExistYn)) {
					logger.info("==> mobile Data Exist : Y -> insert");
					cstprmsService.insertConOprnPrmsChkNextDataMobile(tempInfo);
					cstprmsService.insertConOprnPrmsChkListNextDataMobile(tempInfo);
				}
			} else {
				tempInfo.put("con_req_prst_cd", "S01");
				
				a = cstprmsService.updateConOprnPrmsMainStatAprv(tempInfo);
			}
		}
		
		String result;
		result = (a > -1? Constant.SUCCESS : Constant.FAIL);
		
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	// 작업완료 결재 후 처리
	@SuppressWarnings("unchecked")
	@Transactional
	public ResponseEntity<?> aprvConOprnPrmsCmplCnfm(String aprv_no, String aprv_stt_cd) {
	
		int a = 0;
		int b = 0;
		
		Map<String, String> info = new HashMap<>();
		info.put("con_oprn_cmpy_aprv_no", aprv_no);
		
		// 결재정보 조회
		List<Map<String, ?>> aprvInfo = cstprmsService.selectConOprnPrmsCmplAprvInfo(info);

		if(aprvInfo != null && !aprvInfo.isEmpty())
		{
			Map<String, String> tempInfo = (Map<String, String>) aprvInfo.get(0);
			tempInfo.put("username", "APPR_BATCH");
			
			// 결재 완료
			if ("3".equals(aprv_stt_cd)) {
				// 안전작업허가 작업완료 처리
				tempInfo.put("con_req_prst_cd", "S08");
				a = cstprmsService.updateConOprnPrmsMainStatAprv(tempInfo);
				
				// 안전작업허가 종료보고완료 처리
				tempInfo.put("con_cmpy_prst_cd", "S03");
				b = cstprmsService.updateConOprnPrmsCmplAprvAf(tempInfo);
			} else {
				//tempInfo.put("con_req_prst_cd", "S01");
				//a = cstprmsService.updateConOprnPrmsMainStatAprv(tempInfo);
				
				// 안전작업허가 종료보고 작성중 처리
				tempInfo.put("con_cmpy_prst_cd", "S01");
				b = cstprmsService.updateConOprnPrmsCmplAprvAf(tempInfo);
			}
		}
		
		String result;
		result = (a > -1? (b > -1? Constant.SUCCESS : Constant.FAIL) : Constant.FAIL);
		
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	// 작업완료 멀티결재 후 처리
	@SuppressWarnings("unchecked")
	@Transactional
	public ResponseEntity<?> aprvConOprnPrmsCmplMultiCnfm(String aprv_no, String aprv_stt_cd) {
	
		int a = 0;
		int b = 0;
		
		Map<String, String> info = new HashMap<>();
		info.put("con_oprn_cmpy_aprv_no", aprv_no);
		
		// 결재정보 조회
		List<Map<String, ?>> aprvInfo = cstprmsService.selectConOprnPrmsCmplAprvInfo(info);
		
		for (int i = 0; i < aprvInfo.size(); i++) {
			Map<String, String> tempInfo = (Map<String, String>) aprvInfo.get(i);
			tempInfo.put("username", "APPR_BATCH");
			
			// 결재 완료
			if ("3".equals(aprv_stt_cd)) {
				// 안전작업허가 작업완료 처리
				tempInfo.put("con_req_prst_cd", "S08");
				a = cstprmsService.updateConOprnPrmsMainStatAprv(tempInfo);

				// 안전작업허가 종료보고완료 처리
				tempInfo.put("con_cmpy_prst_cd", "S03");
				b = cstprmsService.updateConOprnPrmsCmplAprvAf(tempInfo);
			} else {
				//tempInfo.put("con_req_prst_cd", "S01");
				//a = cstprmsService.updateConOprnPrmsMainStatAprv(tempInfo);
				
				// 안전작업허가 종료보고 작성중 처리
				tempInfo.put("con_cmpy_prst_cd", "S01");
				b = cstprmsService.updateConOprnPrmsCmplAprvAf(tempInfo);
			}
		}
		
		String result;
		result = (a > -1? (b > -1? Constant.SUCCESS : Constant.FAIL) : Constant.FAIL);
		
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	// 교육관리 결재 후 처리
	@SuppressWarnings("unchecked")
	@Transactional
	public ResponseEntity<?> aprvTrnApprAf(String aprv_no, String aprv_stt_cd) {
	
		int a = 0;
		
		Map<String, String> info = new HashMap<>();
		info.put("trn_aprv_no", aprv_no);
		
		// 결재정보 조회
		List<Map<String, ?>> aprvInfo = trnMgntservice.selectTrnAprvInfo(info);

		if(aprvInfo != null && !aprvInfo.isEmpty())
		{
			Map<String, String> tempInfo = (Map<String, String>) aprvInfo.get(0);
			
			// 결재 완료
			if ("3".equals(aprv_stt_cd)) {
				tempInfo.put("trn_prst_cd", "S03");
				
				a = trnMgntservice.updateTrnApprAf(tempInfo);
			} else {
				tempInfo.put("trn_prst_cd", "S01");
				
				a = trnMgntservice.updateTrnApprAf(tempInfo);
			}
		}
		
		String result;
		result = (a > -1? Constant.SUCCESS : Constant.FAIL);
		
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	// 사업장별 투자 관리 결재 후 처리
	@SuppressWarnings("unchecked")
	@Transactional
	public ResponseEntity<?> aprvInvsMgntApprAf(String aprv_no, String aprv_stt_cd) {
	
		int a = 0;
		
		Map<String, String> info = new HashMap<>();
		info.put("invs_aprv_no", aprv_no);
		
		// 결재정보 조회
		List<Map<String, ?>> aprvInfo = invsMgntservice.selectInvsMgntAprvDtl(info);

		if(aprvInfo != null && !aprvInfo.isEmpty())
		{
			Map<String, String> tempInfo = (Map<String, String>) aprvInfo.get(0);
			
			// 결재 완료
			if ("3".equals(aprv_stt_cd)) {
				tempInfo.put("invs_prst_cd", "S09");
				
				a = invsMgntservice.updateInvsMgntApprAf(tempInfo);
			} else {
				tempInfo.put("invs_prst_cd", "S03");
				
				a = invsMgntservice.updateInvsMgntApprAf(tempInfo);
			}
		}
		
		String result;
		result = (a > -1? Constant.SUCCESS : Constant.FAIL);
		
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	// 합동점검 결재 후 처리
	@SuppressWarnings("unchecked")
	@Transactional
	public ResponseEntity<?> aprvCoprApprAf(String aprv_no, String aprv_stt_cd) {
	
		int a = 0;
		
		Map<String, Object> info = new HashMap<>();
		info.put("jnt_aprv_no", aprv_no);
		
		// 결재정보 조회(aprv_no를 통해 정보 조회)
		List<Map<String, ?>> aprvInfo = sftChkService.selectCoprChkAprvDtl(info);

		if(aprvInfo != null && !aprvInfo.isEmpty())
		{
			Map<String, Object> tempInfo = (Map<String, Object>) aprvInfo.get(0);
			
			// 결재 완료
			if ("3".equals(aprv_stt_cd)) {
				tempInfo.put("jnt_chk_prst_cd", "60"); // 결재 완료
				
				a = sftChkService.updateCoprChkAprvAf(tempInfo);
			} else {
				tempInfo.put("jnt_chk_prst_cd", "40"); // 작성 중
				
				a = sftChkService.updateCoprChkAprvAf(tempInfo);
			}
		}
		
		String result;
		result = (a > -1? Constant.SUCCESS : Constant.FAIL);
		
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	// 순회점검 결재 후 처리
	@SuppressWarnings("unchecked")
	@Transactional
	public ResponseEntity<?> aprvTourApprAf(String aprv_no, String aprv_stt_cd) {
	
		int a = 0;
		
		Map<String, Object> info = new HashMap<>();
		info.put("circ_aprv_no", aprv_no);
		
		// 결재정보 조회(aprv_no를 통해 정보 조회)
		List<Map<String, ?>> aprvInfo = sftChkService.selectTourChkAprvDtl(info);

		if(aprvInfo != null && !aprvInfo.isEmpty())
		{
			Map<String, Object> tempInfo = (Map<String, Object>) aprvInfo.get(0);
			
			// 결재 완료
			if ("3".equals(aprv_stt_cd)) {
				tempInfo.put("circ_chk_prst_cd", "60"); // 결재 완료
				
				a = sftChkService.updateTourChkAprvAf(tempInfo);
			} else {
				tempInfo.put("circ_chk_prst_cd", "40"); // 점검 완료
				
				a = sftChkService.updateTourChkAprvAf(tempInfo);
			}
		}
		
		String result;
		result = (a > -1? Constant.SUCCESS : Constant.FAIL);
		
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	// 회의체 결재 후 처리
	@SuppressWarnings("unchecked")
	@Transactional
	public ResponseEntity<?> aprvMtngApprAf(String aprv_no, String aprv_stt_cd) {
	
		int a = 0;
		
		Map<String, Object> info = new HashMap<>();
		info.put("mtng_rslt_aprv_no", aprv_no);
		
		// 결재정보 조회(aprv_no를 통해 정보 조회)
		List<Map<String, ?>> aprvInfo = mtngService.selectMtngAprvDtl(info);

		if(aprvInfo != null && !aprvInfo.isEmpty())
		{
			Map<String, Object> tempInfo = (Map<String, Object>) aprvInfo.get(0);
			
			// 결재 완료
			if ("3".equals(aprv_stt_cd)) {
				tempInfo.put("mtng_rslt_prst_cd", "30"); // 결재 완료
				
				a = mtngService.updateMtngAprvAf(tempInfo);
			} else {
				tempInfo.put("mtng_rslt_prst_cd", "10"); // 작성중
				
				a = mtngService.updateMtngAprvAf(tempInfo);
			}
		}
		
		String result;
		result = (a > -1? Constant.SUCCESS : Constant.FAIL);
		
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	//  대외점검 관리 결재 후 처리
	@SuppressWarnings("unchecked")
	@Transactional
	public ResponseEntity<?> aprvRgltVltnApprAf(String aprv_no, String aprv_stt_cd) {
	
		int a = 0;
		
		Map<String, Object> info = new HashMap<>();
		info.put("rglt_vltn_aprv_no", aprv_no);
		
		// 결재정보 조회(aprv_no를 통해 정보 조회)
		List<Map<String, ?>> aprvInfo = rgltvltnservice.selectRgltVltnAprvDtl(info);

		if(aprvInfo != null && !aprvInfo.isEmpty())
		{
			Map<String, Object> tempInfo = (Map<String, Object>) aprvInfo.get(0);
			
			// 결재 완료
			if ("3".equals(aprv_stt_cd)) {
				tempInfo.put("rglt_vltn_prst_cd", "30"); // 결재 완료
				
				a = rgltvltnservice.updateRgltVltnAprvAf(tempInfo);
			} else {
				tempInfo.put("rglt_vltn_prst_cd", "10"); // 작성중
				
				a = rgltvltnservice.updateRgltVltnAprvAf(tempInfo);
			}
		}
		
		String result;
		result = (a > -1? Constant.SUCCESS : Constant.FAIL);
		
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	// 작업환경측정 결재 후 처리
	@SuppressWarnings("unchecked")
	@Transactional
	public ResponseEntity<?> aprvOpemMgntApprAf(String aprv_no, String aprv_stt_cd) {
	
		int a = 0;
		
		Map<String, Object> info = new HashMap<>();
		info.put("wem_aprv_no", aprv_no);
		
		// 결재정보 조회(aprv_no를 통해 정보 조회)
		List<Map<String, ?>> aprvInfo = opemMgntService.selectOpemMgntAprvDtl(info);

		if(aprvInfo != null && !aprvInfo.isEmpty())
		{
			Map<String, Object> tempInfo = (Map<String, Object>) aprvInfo.get(0);
			
			// 결재 완료
			if ("3".equals(aprv_stt_cd)) {
				tempInfo.put("wem_prst_cd", "30"); // 결재 완료
				
				a = opemMgntService.updateOpemMgntAprvAf(tempInfo);
			} else {
				tempInfo.put("wem_prst_cd", "10"); // 작성중
				
				a = opemMgntService.updateOpemMgntAprvAf(tempInfo);
			}
		}
		
		String result;
		result = (a > -1? Constant.SUCCESS : Constant.FAIL);
		
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	// 작업환경측정 조치 결재 후 처리
	@SuppressWarnings("unchecked")
	@Transactional
	public ResponseEntity<?> aprvOpemActnApprAf(String aprv_no, String aprv_stt_cd) {
	
		int a = 0;
		
		Map<String, Object> info = new HashMap<>();
		info.put("acco_aprv_no", aprv_no);
		
		// 결재정보 조회(aprv_no를 통해 정보 조회)
		List<Map<String, ?>> aprvInfo = opemActnService.selectOpemActnAprvDtl(info);

		if(aprvInfo != null && !aprvInfo.isEmpty())
		{
			Map<String, Object> tempInfo = (Map<String, Object>) aprvInfo.get(0);
			
			// 결재 완료
			if ("3".equals(aprv_stt_cd)) {
				tempInfo.put("acco_prst_cd", "40"); // 결재 완료
				
				a = opemActnService.updateOpemActnAprvAf(tempInfo);
			} else {
				tempInfo.put("acco_prst_cd", "20"); // 작성중
				
				a = opemActnService.updateOpemActnAprvAf(tempInfo);
			}
		}
		
		String result;
		result = (a > -1? Constant.SUCCESS : Constant.FAIL);
		
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	// 사고관리 결재 후 처리
	@SuppressWarnings("unchecked")
	@Transactional
	public ResponseEntity<?> aprvAcdtMgntApprAf(String aprv_no, String aprv_stt_cd) {
	
		int a = 0;
		
		Map<String, Object> info = new HashMap<>();
		info.put("acdt_brie_aprv_no", aprv_no);
		
		// 결재정보 조회(aprv_no를 통해 정보 조회)
		List<Map<String, ?>> aprvInfo = acdtMgntService.selectAcdtMgntAprvDtl(info);

		if(aprvInfo != null && !aprvInfo.isEmpty())
		{
			Map<String, Object> tempInfo = (Map<String, Object>) aprvInfo.get(0);
			
			// 결재 완료
			if ("3".equals(aprv_stt_cd)) {
				tempInfo.put("acdt_prst_cd", "4"); // 결재완료
				
				a = acdtMgntService.updateAcdtMgntAprvAf(tempInfo);
			} else {
				tempInfo.put("acdt_prst_cd", "2"); // 조치완료
				
				a = acdtMgntService.updateAcdtMgntAprvAf(tempInfo);
			}
		}
		
		String result;
		result = (a > -1? Constant.SUCCESS : Constant.FAIL);
		
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	// 위험성평가 검토/실시보고 후 처리
	@SuppressWarnings("unchecked")
	@Transactional
	public ResponseEntity<?> aprvRiskEvalBasicApprAf(String aprv_no, String aprv_stt_cd) {
	
		int a = 0;
		
		Map<String, String> info = new HashMap<>();
		info.put("dgsn_apal_aprv_no", aprv_no);
		
		// 결재정보 조회(aprv_no를 통해 정보 조회)
		List<Map<String, ?>> aprvInfo = riskEvalService.selectRiskEvalBasicAppr(info);

		if(aprvInfo != null && !aprvInfo.isEmpty()) {
			Map<String, String> tempInfo = (Map<String, String>) aprvInfo.get(0);

			// 1 : 결재상신, 3 : 결재완료, 9 : 결재반려, 0 : 결재취소
			if ("1".equals(aprv_stt_cd)) {
				tempInfo.put("dgsn_apal_prst_cd", "E02"); // 결재중
				tempInfo.put("dgsn_apal_drup_stp_cd", "4");
				a = riskEvalService.updateRiskEvalBasicAprvAf(tempInfo);
			}else if ("3".equals(aprv_stt_cd)) {
				tempInfo.put("dgsn_apal_prst_cd", "E03"); // 결재 완료
				tempInfo.put("dgsn_apal_drup_stp_cd", "5");
				a = riskEvalService.updateRiskEvalBasicAprvAf(tempInfo);
			}else if ("9".equals(aprv_stt_cd)) {
				tempInfo.put("dgsn_apal_prst_cd", "E01"); // 작성중
				tempInfo.put("dgsn_apal_drup_stp_cd", "4");
				a = riskEvalService.updateRiskEvalBasicAprvAf(tempInfo);
			}else if ("0".equals(aprv_stt_cd)) {
				tempInfo.put("dgsn_apal_prst_cd", "E01"); // 작성중
				tempInfo.put("dgsn_apal_drup_stp_cd", "4");
				a = riskEvalService.updateRiskEvalBasicAprvAf(tempInfo);
			}
		}
		
		String result = (a > -1? Constant.SUCCESS : Constant.FAIL);
		
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	// 위험성평가 조치보고 후 처리
	@SuppressWarnings("unchecked")
	@Transactional
	public ResponseEntity<?> aprvRiskEvalAftActApprAf(String aprv_no, String aprv_stt_cd) {
	
		int a = 0;
		
		Map<String, String> info = new HashMap<>();
		info.put("dgsn_btt_aprv_no", aprv_no);
		
		// 결재정보 조회(aprv_no를 통해 정보 조회)
		List<Map<String, ?>> aprvInfo = riskEvalAftActService.selectRiskEvalAftActAppr(info);

		if(aprvInfo != null && !aprvInfo.isEmpty()) {
			Map<String, String> tempInfo = (Map<String, String>) aprvInfo.get(0);
			 
			// 결재 완료
			if ("3".equals(aprv_stt_cd)) {
				tempInfo.put("dgsn_btt_prst_cd", "EA04"); // 결재 완료
				a = riskEvalAftActService.updateRiskEvalAftActAprvAf(tempInfo);
			} else {
				tempInfo.put("dgsn_btt_prst_cd", "EA03"); // 결재중
				a = riskEvalAftActService.updateRiskEvalAftActAprvAf(tempInfo);
			}
		}
		
		String result = (a > -1? Constant.SUCCESS : Constant.FAIL);
		
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	// 사업장관리 결재 후 처리
	@SuppressWarnings("unchecked")
	@Transactional
	public ResponseEntity<?> aprvElcTrnApprAf(String aprv_no, String aprv_stt_cd) {
	
		
		String result = "SUCCESS";
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	// 위험성평가 최종결재 후 처리
	@SuppressWarnings("unchecked")
	@Transactional
	public ResponseEntity<?> aprvRiskEvalAftActFinalApprAf(String aprv_no, String aprv_stt_cd) {
	
		String result = "SUCCESS";
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
