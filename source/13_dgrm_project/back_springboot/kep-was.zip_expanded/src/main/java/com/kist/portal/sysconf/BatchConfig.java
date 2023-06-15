package com.kist.portal.sysconf;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.quartz.CronScheduleBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

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
import com.kist.portal.common.batch.UnCheckedMailJob;
import com.kist.portal.common.batch.UserDeptRoleJob;
import com.kist.portal.common.batch.WkplTrnJob;

@Configuration
@ConditionalOnProperty(prefix = "spring.batch.job", name = "enabled", havingValue = "true", matchIfMissing = false)
public class BatchConfig {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private Scheduler scheduler;
	
	@PostConstruct
	public void start() {
		logger.info("===== JobController start invoked =====");
	    try {
	    	// 화학물질
	    	JobDetail userDeptRoleJob = buildJobDetail(UserDeptRoleJob.class, "BC001", "BC001", new HashMap<String, Object>());
	    	JobDetail rgltChkCrtJob = buildJobDetail(RgltChkCrtJob.class, "BR001", "BR001", new HashMap<String, Object>());
	    	JobDetail msdsRevCrtJob = buildJobDetail(MsdsRevCrtJob.class, "BR002", "BR002", new HashMap<String, Object>());
	    	JobDetail mtrlWhJob = buildJobDetail(MtrlWhJob.class, "BQ001", "BQ001", new HashMap<String, Object>());
	    	JobDetail mtrlSkJob = buildJobDetail(MtrlSkJob.class, "BQ003", "BQ003", new HashMap<String, Object>());
	    	JobDetail rgltAlrmJob = buildJobDetail(RgltAlrmJob.class, "BR005", "BR005", new HashMap<String, Object>());    	
	    	JobDetail rgltChkNotiJob = buildJobDetail(RgltChkNotiJob.class, "BR004", "BR004", new HashMap<String, Object>());
	    	JobDetail rgltDbJob = buildJobDetail(RgltDbJob.class, "BR003", "BR003", new HashMap<String, Object>());
	    	JobDetail aprvRevJob = buildJobDetail(AprvRevJob.class, "BA001", "BA001", new HashMap<String, Object>());
	    	JobDetail mtrlVndrJob = buildJobDetail(MtrlVndrJob.class, "BV001", "BV001", new HashMap<String, Object>());
	    	
	    	// 안전관리
	    	JobDetail oprbBgnChkJob = buildJobDetail(OprbBgnChkJob.class, "BS001", "BS001", new HashMap<String, Object>());
	    	JobDetail unCheckedMailJob = buildJobDetail(UnCheckedMailJob.class, "BS002", "BS002", new HashMap<String, Object>());
	    	JobDetail conOprnPrmsAlrmJob = buildJobDetail(ConOprnPrmsAlrmJob.class, "BS003", "BS003", new HashMap<String, Object>());
	    	JobDetail exeHistJob = buildJobDetail(ExeHistJob.class, "BS004", "BS004", new HashMap<String, Object>());
	    	JobDetail hmflAlrmJob = buildJobDetail(HmflAlrmJob.class, "BS005", "BS005", new HashMap<String, Object>());
	    	JobDetail ShcosmHolyJob = buildJobDetail(ShcosmHolyJob.class, "BS006", "BS006", new HashMap<String, Object>());	
	    	JobDetail acdtJob = buildJobDetail(AcdtJob.class, "BS007", "BS007", new HashMap<String, Object>());
	    	JobDetail wkplTrnJob = buildJobDetail(WkplTrnJob.class, "BS008", "BS008", new HashMap<String, Object>());
	    	
	    	// 화학물질
	    	scheduler.scheduleJob(userDeptRoleJob, buildCronJobTrigger("0 30 * * * ?")); // 유저 권한 매 30분 배치
	    	scheduler.scheduleJob(rgltChkCrtJob, buildCronJobTrigger("0 20 * * * ?")); // 규제 검토 생성 매  20분
	    	scheduler.scheduleJob(msdsRevCrtJob, buildCronJobTrigger("0 30 * * * ?")); // msds 개정 생성 매 30분
	    	scheduler.scheduleJob(mtrlWhJob, buildCronJobTrigger("0 0 3 * * ?")); //매 3시
	    	scheduler.scheduleJob(mtrlSkJob, buildCronJobTrigger("0 20 3 * * ?")); //매 3시 20분
	    	scheduler.scheduleJob(rgltChkNotiJob, buildCronJobTrigger("0 0 8 * * ?")); // 규제 검토 알림 8:00
	    	scheduler.scheduleJob(rgltAlrmJob, buildCronJobTrigger("0 10 8 * * ?")); // 규제 알림 8:10
	    	scheduler.scheduleJob(rgltDbJob, buildCronJobTrigger("0 0 1 * * ?")); // 규제 DB 1:00
	    	scheduler.scheduleJob(aprvRevJob, buildCronJobTrigger("0 0/5 * * * ?")); // 결재 5분
	    	scheduler.scheduleJob(mtrlVndrJob, buildCronJobTrigger("0 10 * * * ?")); // 공급업체 10분 
	    	
	    	// 안전관리
	    	scheduler.scheduleJob(oprbBgnChkJob, buildCronJobTrigger("0 0 * * * ?")); // 매 0분 
	    	scheduler.scheduleJob(unCheckedMailJob, buildCronJobTrigger("0 0 5 * * ?")); // 매 5시
	    	scheduler.scheduleJob(conOprnPrmsAlrmJob, buildCronJobTrigger("0 0 6 * * ?")); // 매 6시
	    	scheduler.scheduleJob(exeHistJob, buildCronJobTrigger("0 0 1 * * ?")); // 매 1시
	    	scheduler.scheduleJob(hmflAlrmJob, buildCronJobTrigger("0 0 2 * * ?")); // 매 2시 
	    	scheduler.scheduleJob(ShcosmHolyJob, buildCronJobTrigger("0 0 1 * * ?")); // 매 1시
	    	scheduler.scheduleJob(acdtJob, buildCronJobTrigger("0 0 10 * * ?")); // 매일 10:00 사고보고 및 조사 임시저장 자동삭제, 메일발송, 조치지연 상태 업데이트 처리
	    	scheduler.scheduleJob(wkplTrnJob, buildCronJobTrigger("0 0 10 * * ?")); // 매일 10:00 법적선임자관리 교육이수증 등록여부 업데이트, 교육이수증 등록 안내 메일 발송 처리
	    	
	    } catch (SchedulerException e) {
	        logger.error(e.getMessage());
	    }
	}
	
	//* * * * * * *
	//초  분  시  일  월  요일  년도(생략가능)
	public Trigger buildCronJobTrigger(String scheduleExp) {
	    return TriggerBuilder.newTrigger()
	    		.withSchedule(CronScheduleBuilder.cronSchedule(scheduleExp))
	    		.build();
	}
	
	// 매개변수로 입력받은 시간단위로 실행 
	public Trigger buildSimpleJobTrigger(Integer hour) {
		return TriggerBuilder.newTrigger()
				.withSchedule(SimpleScheduleBuilder
				.simpleSchedule()
				.repeatForever()
				.withIntervalInHours(hour))
				.build();
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