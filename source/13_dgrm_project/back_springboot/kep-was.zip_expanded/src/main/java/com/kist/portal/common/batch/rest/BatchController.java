package com.kist.portal.common.batch.rest;

import java.util.Map;

import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kist.portal.common.aprv.rest.AprvController;
import com.kist.portal.common.batch.AcdtJob;
import com.kist.portal.common.batch.AprvRevJob;
import com.kist.portal.common.batch.ConOprnPrmsAlrmJob;
import com.kist.portal.common.batch.ExeHistJob;
import com.kist.portal.common.batch.HmflAlrmJob;
import com.kist.portal.common.batch.MsdsRevCrtJob;
import com.kist.portal.common.batch.MtrlSkJob;
import com.kist.portal.common.batch.MtrlVndrJob;
import com.kist.portal.common.batch.MtrlWhJob;
import com.kist.portal.common.batch.OprbBgnChkJob;
import com.kist.portal.common.batch.RgltAlrmJob;
import com.kist.portal.common.batch.RgltChkCrtJob;
import com.kist.portal.common.batch.RgltChkNotiJob;
import com.kist.portal.common.batch.RgltDbJob;
import com.kist.portal.common.batch.ShcosmHolyJob;
import com.kist.portal.common.batch.TestJob;
import com.kist.portal.common.batch.UnCheckedMailJob;
import com.kist.portal.common.batch.UserDeptRoleJob;
import com.kist.portal.common.batch.WkplTrnJob;
import com.kist.portal.common.batch.mapper.BatchMapper;
import com.kist.portal.common.utils.Constant;

@RestController
@RequestMapping("/api/v1/batch")
public class BatchController {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private RgltChkCrtJob rccj;
	
	@Autowired
	private RgltDbJob rdj;
	
	@Autowired
	private RgltChkNotiJob rcnj;
	
	@Autowired
	private RgltAlrmJob raj;
	
	@Autowired
	private MtrlVndrJob mvj;
	
	@Autowired
	private MsdsRevCrtJob mrcj;
		
	@Autowired
	private MtrlWhJob mwj;
	
	@Autowired
	private MtrlSkJob msj;
	
	@Autowired
	private AprvRevJob arj;
	
	@Autowired
	private UserDeptRoleJob udrj;
	
	@Autowired
	private OprbBgnChkJob obcj;
	
	@Autowired
	private TestJob tj;
	
	@Autowired
	private HmflAlrmJob haj;
	
	@Autowired
	private ConOprnPrmsAlrmJob copaj;
	
	@Autowired
	private UnCheckedMailJob ucmj;
	
	@Autowired
	private WkplTrnJob wkplJob; // 교육 이수자 알림메일 발송
	
	
	@Autowired
	private ExeHistJob exej;

	@Autowired
	private ShcosmHolyJob shho;
	
	@Autowired
	private AcdtJob acdtJob;	// 사고속보 조사보고완료 요청	
	
	@Autowired
	private BatchMapper mapper;
	
	@Autowired
	AprvController aprv;

	@GetMapping("/exec/{id}")
	public ResponseEntity<?> executeBatch(@PathVariable String id) throws Exception{
		
		System.out.println("================================================");
		System.out.println("================================================");
		System.out.println(id);
		System.out.println("================================================");
		System.out.println("================================================");
		try {
			if("BC001".equalsIgnoreCase(id)) {
				udrj.executeManual();	// IAM 사용자 부서 권한
				logger.debug("==> IAM 사용자 부서 권한 : " + "IAM 사용자 부서 권한" );
			} else if("BR001".equalsIgnoreCase(id)) {
				rccj.executeManual();	// 규제검토 생성
				logger.debug("==> 규제검토 생성 : " + "규제검토 생성" );
			} else if("BR002".equalsIgnoreCase(id)) {
				mrcj.executeManual();	// MSDS 개정 생성
				logger.debug("==> MSDS 개정 생성 : " + "MSDS 개정 생성" );
			} else if("BQ001".equalsIgnoreCase(id)) {
				mwj.executeManual();	// 자재 입고
				logger.debug("==> 자재 입고 : " + "자재 입고" );
			} else if("BQ003".equalsIgnoreCase(id)) {
				msj.executeManual();	// 자재 재고
				logger.debug("==> 자재 재고 : " + "자재 재고" );
			} else if("BV001".equalsIgnoreCase(id)) {
				mvj.executeManual();	// 공급업체
				logger.debug("==> 공급업체 : " + "공급업체" );
			} else if("BR003".equalsIgnoreCase(id)) {
				rdj.executeManual();	// 규제DB
				logger.debug("==> 규제DB : " + "규제DB" );
			} else if("BR004".equalsIgnoreCase(id)) {
				rcnj.executeManual();	// 규제검토공지
				logger.debug("==> 규제검토공지 : " + "규제검토공지" );
			} else if("BR005".equalsIgnoreCase(id)) {
				raj.executeManual();	// 규제알림
				logger.debug("==> 규제알림 : " + "규제알림" );
			} else if("BA001".equalsIgnoreCase(id)) {
				arj.executeManual();	// 결재
				logger.debug("==> 결재 : " + "결재" );
			} else if("BS001".equalsIgnoreCase(id)) {
				obcj.executeManual();	// 안전관리
				logger.debug("==> 안전관리 : " + "안전관리" );
			} else if("BS005".equalsIgnoreCase(id)) {
				haj.executeManual();	// 유해화학물질 점검주기 3개월
				logger.debug("==> 유해화학물질 점검주기 3개월 : " + "유해화학물질 점검주기 3개월" );
			} else if("BS003".equalsIgnoreCase(id)) {
				copaj.executeManual();	// 허가서
				logger.debug("==> 허가서 : " + "허가서" );
			} else if("BS002".equalsIgnoreCase(id)) {
				ucmj.executeManual();	// 미점검 알림 메일
				logger.debug("==> 미점검 알림 메일 : " + "미점검 알림 메일" );
			} else if("BS004".equalsIgnoreCase(id)) {
				exej.executeManual();	// 개인정보이력
				logger.debug("==> 개인정보이력 : " + "개인정보이력" );
			} else if("BS006".equalsIgnoreCase(id)) {
				shho.executeManual();	// 공휴일 DB 입력
				logger.debug("==> SAP 공휴일 정보 업데이트: " + "SAP 공휴일 정보 업데이트" );
			} else if("BS007".equalsIgnoreCase(id)) {
				acdtJob.executeManual();	// 사고속보 조사보고완료 요청 배치
				logger.debug("==> 사고속보 조사보고완료 요청 정보 업데이트: " + "사고속보 조사보고완료 요청 정보 업데이트" );
			} else if("BS008".equalsIgnoreCase(id)) {
				wkplJob.executeManual();	// 사업장 법정교육 이수안내 메일 발송
				logger.debug("==> 사업장 법정교육 이수안내 메일 발송: " + "사업장 법정교육 이수안내 메일 발송" );
			}			
		} catch(Exception e) {
			return new ResponseEntity<>(Constant.FAIL, HttpStatus.OK);
		}
		
		return new ResponseEntity<>(Constant.SUCCESS, HttpStatus.OK);
	}
	
	public JobDetail buildJobDetail(Class<? extends Job> job, String name, String desc, Map<String, Object> params) {
		JobDataMap jobDataMap = new JobDataMap();
		jobDataMap.putAll(params);
		return JobBuilder
		    .newJob(job)
		    .withIdentity(name)
		    .withDescription(desc)
		    .usingJobData(jobDataMap)
		    .build();
	}
	
}
