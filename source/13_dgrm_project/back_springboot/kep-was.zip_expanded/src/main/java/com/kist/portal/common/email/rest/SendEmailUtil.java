package com.kist.portal.common.email.rest;

import java.util.ArrayList;
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
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.securitytoken.AWSSecurityTokenService;
import com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClientBuilder;
import com.amazonaws.services.securitytoken.model.AssumeRoleRequest;
import com.amazonaws.services.securitytoken.model.Credentials;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import com.amazonaws.services.simpleemail.model.SendEmailResult;
import com.kist.portal.common.email.EmailProperties;
import com.kist.portal.common.email.service.sendMailService;
import com.kist.portal.common.login.model.UserInfo;

@Component
public class SendEmailUtil {
	Logger logger  = LoggerFactory.getLogger(this.getClass());

	@Value("${spring.profiles.active}")
	private String activeProfile;
	
	@Autowired
	private sendMailService service;
	
	// 발송을 위한 Credentials 정보 설정에 profileName을 받을 수 있도록 추가
	public AWSStaticCredentialsProvider Credentials(final String role_arn, final Regions regions, final String profileName) throws IllegalArgumentException, AmazonClientException {
		return setCredentials(role_arn, regions, profileName);
	}
	
	// 인증 처리
	protected AWSStaticCredentialsProvider setCredentials(final String role_arn, final Regions regions, final String profileName) throws IllegalArgumentException, AmazonClientException {
		
		AWSSecurityTokenServiceClientBuilder builder = AWSSecurityTokenServiceClientBuilder.standard().withRegion(regions);
		
		logger.info("==================================================");
		logger.info(role_arn);
		logger.info(regions.getName());
		logger.info(profileName);
		
		if(StringUtils.isEmpty(profileName)) {
			builder.withCredentials(new ProfileCredentialsProvider(profileName));
		}
		
		AWSSecurityTokenService stsClient = builder.build();
		
		AssumeRoleRequest assume_role = new AssumeRoleRequest().withRoleArn(role_arn).withRoleSessionName("botocore-session-1607578740").withDurationSeconds(900);
		
		Credentials session_creds = stsClient.assumeRole(assume_role).getCredentials();
		
		BasicSessionCredentials credentials = new BasicSessionCredentials(session_creds.getAccessKeyId(), session_creds.getSecretAccessKey(), session_creds.getSessionToken());
		
		return new AWSStaticCredentialsProvider(credentials);
	}
	
	/**
	 * 메일 발송 메서드(수신자)
	 * @param cd 발송 업무 코드
	 * @param from 발송자 사번 or 이메일 주소
	 * @param title 메일 제목
	 * @param content 메일 내용
	 * @param to 수신자 사번 or 이메일 주소
	 * @return true or false 전송 성공 실패 여부
	 */
	@SuppressWarnings("finally")
	public boolean sendMail(String cd, String from, String title, String content, String[] to) {
		if(from == null || from.isEmpty()) {
			logger.error("==> sender not set...");
			return false;
		}
		if(to.length <= 0) {
			logger.error("==> reciever not set...");
			return false;
		}
		
		Map<String, String> param = new HashMap<>();
		param.put("data", from);
		
		List<Map<String, ?>> senderData = service.selectEmailData(param);
		// 보내는 유저가 검색되지 않을 시
		if(senderData.size() == 0) {
			logger.warn("==> no sender data...");
			logger.warn("==> from : " + from);
			return false;
		}
		
		List<Map<String, String>> recieverData = new ArrayList<>();
		String[] reciever = new String[to.length];
		
		// 받는 유저가 검색되지 않을 시 
		for(int i = 0; i < to.length; i++) {
			param.put("data", to[i]);
			List<Map<String, ?>> recvData = service.selectEmailData(param);
			if(recvData.size() == 0) {
				logger.warn("==> no reciever data...");
				logger.warn("==> to : " + to);
				return false;
			}
			
			if(!String.valueOf(recvData.get(0).get("usr_email")).equalsIgnoreCase("null") && !recvData.get(0).get("usr_email").toString().isEmpty()) {
				Map<String, String> temp = new HashMap<>();
				temp.put("recv_usr_id", recvData.get(0).get("usr_id").toString());
				temp.put("recv_email", recvData.get(0).get("usr_email").toString());
				recieverData.add(temp);
				reciever[i] = recvData.get(0).get("usr_email").toString();
			}
		}
		
		String sender = senderData.get(0).get("usr_email").toString();
		
		param.put("send_email", sender);
		param.put("send_usr_id", senderData.get(0).get("usr_id").toString());
		param.put("email_send_biz_cd", cd);
		param.put("email_ttl", title);
		param.put("email_cntn", content);

	    AmazonSimpleEmailService client = null;
		boolean checked = false;

	    try {
	    	// 로컬이 아닐 경우
			if (!"local".equals(activeProfile)) {
			    // AWS Profile 인증
				AWSStaticCredentialsProvider awsStaticCredentialsProvider;
			    if (EmailProperties.AWS_ROLE_ARN != null && EmailProperties.AWS_ROLE_ARN.length() > 0) {
			        awsStaticCredentialsProvider = Credentials(EmailProperties.AWS_ROLE_ARN
			                , Regions.fromName(EmailProperties.REGION),
			                EmailProperties.PROFILE);  //<- 추가된 ProfileName
			    } else {
			        awsStaticCredentialsProvider = null;
			    }
			    
			    //AWS email service 생성
			    if (awsStaticCredentialsProvider != null) {
			        client = AmazonSimpleEmailServiceClientBuilder.standard()
			                .withCredentials(awsStaticCredentialsProvider)
			                .withRegion(Regions.fromName(EmailProperties.REGION))
			                .build();
			    } else {
			        client = AmazonSimpleEmailServiceClientBuilder.standard()
			                .withRegion(Regions.fromName(EmailProperties.REGION))
			                .build();
			    }

			    SendEmailRequest request = new SendEmailRequest()
			            .withDestination(
			                new Destination().withToAddresses(reciever))
			            .withMessage(new Message()
			                .withBody(new Body()
			                    .withHtml(new Content()
			                        .withCharset("UTF-8").withData(content)))
			                .withSubject(new Content()
			                    .withCharset("UTF-8").withData(title)))
			            .withSource(sender);
			    
				SendEmailResult f = client.sendEmail(request);
				
				// 처리결과
		        checked = f.getMessageId().length() > 0;
			}
	    } catch (AmazonServiceException e) {
	        logger.error(e.getErrorMessage());
	    } finally {
	    	int key = service.selectEmailKey(param);
	    	param.put("key", Integer.toString(key));
	    	if(checked) {
	    		param.put("send_yn", "Y");
	    	} else {
	    		param.put("send_yn", "N");
	    	}
	    	int m = 0;
	    	int n = service.insertMailSenderLogList(param);
	    	for(int i=0; i<recieverData.size(); i++) {
	    		Map<String, String> temp = new HashMap<>();
	    		temp.put("email_log_seq", Integer.toString(key));
	    		temp.put("recv_seq", Integer.toString(i+1));
	    		temp.put("recv_type_cd", "TO");
	    		temp.put("recv_usr_id", recieverData.get(i).get("recv_usr_id"));
	    		temp.put("recv_email", recieverData.get(i).get("recv_email"));
	    		m = service.insertMailReciverLogList(temp);
	    	}
	    	Boolean result = n > -1 ? m > -1 ? Boolean.TRUE : Boolean.FALSE : Boolean.FALSE;
	    	
	    	if (client != null) {
	    		client.shutdown();
	    	}
	    	
	    	return result;
	    }
	}
	
	/**
	 * 메일 발송 메서드(수신자, 참조)
	 * @param cd 발송 업무 코드
	 * @param from 발송자 사번 or 이메일 주소
	 * @param title 메일 제목
	 * @param content 메일 내용
	 * @param to 수신자 사번 or 이메일 주소
	 * @param cc 참조자 사번 or 이메일 주소
	 * @return true or false 전송 성공 실패 여부
	 */
	@SuppressWarnings("finally")
	public boolean sendMail(String cd, String from, String title, String content, String[] to, String[] cc) {
		Map<String, String> param = new HashMap<>();
		
		param.put("data", from);
		List<Map<String, ?>> senderData = service.selectEmailData(param);
		// 보내는 유저가 검색되지 않을 시
		if(senderData.size() == 0) {
			logger.warn("==> no sender user...");
			return false;
		}
		if(to.length <= 0) {
			logger.warn("==> no sender user...");
			return false;
		}
		
		List<Map<String, String>> recieverData = new ArrayList<>();
		String[] reciever = new String[to.length];
		String[] recieverCc = new String[cc.length];
		
		// 받는 유저가 검색되지 않을 시 
		for(int i=0; i<to.length; i++) {
			param.put("data", to[i]);
			List<Map<String, ?>> recvData = service.selectEmailData(param);
			if(recvData.size() == 0) {
				logger.warn("==> no reciever user...");
				return false;
			}
			
			if(!String.valueOf(recvData.get(0).get("usr_email")).equalsIgnoreCase("null") || !recvData.get(0).get("usr_email").toString().isEmpty()) {
				Map<String, String> temp = new HashMap<>();
				temp.put("recv_usr_id", recvData.get(0).get("usr_id").toString());
				temp.put("recv_email", recvData.get(0).get("usr_email").toString());
				temp.put("recv_type_cd", "TO");
				recieverData.add(temp);
				reciever[i] = recvData.get(0).get("usr_email").toString();
			}
		}
		
		// 받는 유저가 검색되지 않을 시 
		for(int i=0; i<cc.length; i++) {
			param.put("data", cc[i]);
			List<Map<String, ?>> recvCcData = service.selectEmailData(param);
			if(recvCcData.size() == 0) {
				logger.warn("==> no reciever user...");
				return false;
			}

			if(recvCcData.get(0).get("usr_email").toString() != null || !recvCcData.get(0).get("usr_email").toString().isEmpty()) {
				Map<String, String> temp = new HashMap<>();
				temp.put("recv_usr_id", recvCcData.get(0).get("usr_id").toString());
				temp.put("recv_email", recvCcData.get(0).get("usr_email").toString());
				temp.put("recv_type_cd", "CC");
				recieverData.add(temp);
				recieverCc[i] = recvCcData.get(0).get("usr_email").toString();
			}
		}
		
		String sender = senderData.get(0).get("usr_email").toString();
		
		param.put("send_email", sender);
		param.put("send_usr_id", senderData.get(0).get("usr_id").toString());
		param.put("email_send_biz_cd", cd);
		param.put("email_ttl", title);
		param.put("email_cntn", content);

		boolean checked = false;
		
	    try {
	    	// 로컬이 아닐 경우
			if (!"local".equals(activeProfile)) {
			    // AWS Profile 인증
				AWSStaticCredentialsProvider awsStaticCredentialsProvider;
			    if (EmailProperties.AWS_ROLE_ARN != null
			            && EmailProperties.AWS_ROLE_ARN.length() > 0) {
			        awsStaticCredentialsProvider = Credentials(EmailProperties.AWS_ROLE_ARN
			                , Regions.fromName(EmailProperties.REGION),
			                EmailProperties.PROFILE);  //<- 추가된 ProfileName
			    }else{
			        awsStaticCredentialsProvider = null;
			    }
			    
			    AmazonSimpleEmailService client;
			    if (awsStaticCredentialsProvider != null) {
			        client = AmazonSimpleEmailServiceClientBuilder.standard()
			                .withCredentials(awsStaticCredentialsProvider)
			                .withRegion(Regions.fromName(EmailProperties.REGION))
			                .build();
			    }else{
			        client = AmazonSimpleEmailServiceClientBuilder.standard()
			                .withRegion(Regions.fromName(EmailProperties.REGION))
			                .build();
			    }
			    
			    SendEmailRequest request = new SendEmailRequest()
			            .withDestination(
			                new Destination().withToAddresses(reciever).withCcAddresses(recieverCc))
			            .withMessage(new Message()
			                .withBody(new Body()
			                    .withHtml(new Content()
			                        .withCharset("UTF-8").withData(content)))
			                .withSubject(new Content()
			                    .withCharset("UTF-8").withData(title)))
			            .withSource(sender);
				
		        SendEmailResult f = client.sendEmail(request);
		        
		        checked = f.getMessageId().length() > 0;
			}
	    } catch (AmazonServiceException e) {
	        logger.error(e.getErrorMessage());
	    } finally {
	    	int key = service.selectEmailKey(param);
	    	param.put("key", Integer.toString(key));
	    	if(checked) {
	    		param.put("send_yn", "Y");
	    	} else {
	    		param.put("send_yn", "N");
	    	}
	    	int m = 0;
	    	int n = service.insertMailSenderLogList(param);
	    	for(int i=0; i<recieverData.size(); i++) {
	    		Map<String, String> temp = new HashMap<>();
	    		temp.put("email_log_seq", Integer.toString(key));
	    		temp.put("recv_seq", Integer.toString(i+1));
	    		temp.put("recv_type_cd", recieverData.get(i).get("recv_type_cd"));
	    		temp.put("recv_usr_id", recieverData.get(i).get("recv_usr_id"));
	    		temp.put("recv_email", recieverData.get(i).get("recv_email"));
	    		m = service.insertMailReciverLogList(temp);
	    	}
	    	Boolean result = n > -1 ? m > -1 ? Boolean.TRUE : Boolean.FALSE : Boolean.FALSE;
	    	
	    	return result;
	    }
	}
	
	/**
	 * 메일 발송 메서드(수신자, 참조, 숨은 참조)
	 * @param cd 발송 업무 코드
	 * @param from 발송자 사번 or 이메일 주소
	 * @param title 메일 제목
	 * @param content 메일 내용
	 * @param to 수신자 사번 or 이메일 주소
	 * @param cc 참조자 사번 or 이메일 주소
	 * @param bcc 숨은 참조자 사번 or 이메일 주소
	 * @return true or false 전송 성공 실패 여부
	 */
	@SuppressWarnings("finally")
	public boolean sendMail(String cd, String from, String title, String content, String[] to, String[] cc, String[] bcc) {
		Map<String, String> param = new HashMap<>();
		
		param.put("data", from);
		List<Map<String, ?>> senderData = service.selectEmailData(param);
		// 보내는 유저가 검색되지 않을 시
		if(senderData.size() == 0) {
			logger.warn("==> no sender user...");
			return false;
		}
		if(to.length <= 0) {
			logger.warn("==> no sender user...");
			return false;
		}
		
		List<Map<String, String>> recieverData = new ArrayList<>();
		String[] reciever = new String[to.length];
		String[] recieverCc = new String[cc.length];
		String[] recieverBcc = new String[bcc.length];
		
		// 받는 유저가 검색되지 않을 시 
		for(int i=0; i<to.length; i++) {
			param.put("data", to[i]);
			List<Map<String, ?>> recvData = service.selectEmailData(param);
			if(recvData.size() == 0) {
				logger.warn("==> no reciever user...");
				return false;
			}
			
			if(!String.valueOf(recvData.get(0).get("usr_email")).equalsIgnoreCase("null") || !recvData.get(0).get("usr_email").toString().isEmpty()) {
				Map<String, String> temp = new HashMap<>();
				temp.put("recv_usr_id", recvData.get(0).get("usr_id").toString());
				temp.put("recv_email", recvData.get(0).get("usr_email").toString());
				temp.put("recv_type_cd", "TO");
				recieverData.add(temp);
				reciever[i] = recvData.get(0).get("usr_email").toString();
			}
		}
		
		// 받는 유저가 검색되지 않을 시 
		for(int i=0; i<cc.length; i++) {
			param.put("data", cc[i]);
			List<Map<String, ?>> recvCcData = service.selectEmailData(param);
			if(recvCcData.size() == 0) {
				logger.warn("==> no reciever user...");
				return false;
			}

			if(recvCcData.get(0).get("usr_email").toString() != null || !recvCcData.get(0).get("usr_email").toString().isEmpty()) {
				Map<String, String> temp = new HashMap<>();
				temp.put("recv_usr_id", recvCcData.get(0).get("usr_id").toString());
				temp.put("recv_email", recvCcData.get(0).get("usr_email").toString());
				temp.put("recv_type_cd", "CC");
				recieverData.add(temp);
				recieverCc[i] = recvCcData.get(0).get("usr_email").toString();
			}
		}
		
		// 받는 유저가 검색되지 않을 시 
		for(int i=0; i<bcc.length; i++) {
			param.put("data", bcc[i]);
			List<Map<String, ?>> recvBccData = service.selectEmailData(param);
			if(recvBccData.size() == 0) {
				logger.warn("==> no reciever user...");
				return false;
			}

			if(recvBccData.get(0).get("usr_email").toString() != null || !recvBccData.get(0).get("usr_email").toString().isEmpty()) {
				Map<String, String> temp = new HashMap<>();
				temp.put("recv_usr_id", recvBccData.get(0).get("usr_id").toString());
				temp.put("recv_email", recvBccData.get(0).get("usr_email").toString());
				temp.put("recv_type_cd", "BC");
				recieverData.add(temp);
				recieverBcc[i] = recvBccData.get(0).get("usr_email").toString();
			}
		}
		
		String sender = senderData.get(0).get("usr_email").toString();
		
		param.put("send_email", sender);
		param.put("send_usr_id", senderData.get(0).get("usr_id").toString());
		param.put("email_send_biz_cd", cd);
		param.put("email_ttl", title);
		param.put("email_cntn", content);

		boolean checked = false;
		
	    try {
	    	// 로컬이 아닐 경우
			if (!"local".equals(activeProfile)) {
			    // AWS Profile 인증
				AWSStaticCredentialsProvider awsStaticCredentialsProvider;
			    if (EmailProperties.AWS_ROLE_ARN != null
			            && EmailProperties.AWS_ROLE_ARN.length() > 0) {
			        awsStaticCredentialsProvider = Credentials(EmailProperties.AWS_ROLE_ARN
			                , Regions.fromName(EmailProperties.REGION),
			                EmailProperties.PROFILE);  //<- 추가된 ProfileName
			    }else{
			        awsStaticCredentialsProvider = null;
			    }
			    
			    AmazonSimpleEmailService client;
			    if (awsStaticCredentialsProvider != null) {
			        client = AmazonSimpleEmailServiceClientBuilder.standard()
			                .withCredentials(awsStaticCredentialsProvider)
			                .withRegion(Regions.fromName(EmailProperties.REGION))
			                .build();
			    }else{
			        client = AmazonSimpleEmailServiceClientBuilder.standard()
			                .withRegion(Regions.fromName(EmailProperties.REGION))
			                .build();
			    }
			    
			    SendEmailRequest request = new SendEmailRequest()
			            .withDestination(
			                new Destination().withToAddresses(reciever).withCcAddresses(recieverCc).withBccAddresses(recieverBcc))
			            .withMessage(new Message()
			                .withBody(new Body()
			                    .withHtml(new Content()
			                        .withCharset("UTF-8").withData(content)))
			                .withSubject(new Content()
			                    .withCharset("UTF-8").withData(title)))
			            .withSource(sender);
				
		        SendEmailResult f = client.sendEmail(request);
		        
		        checked = f.getMessageId().length() > 0;
			}
	    } catch (AmazonServiceException e) {
	        logger.error(e.getErrorMessage());
	    } finally {
	    	int key = service.selectEmailKey(param);
	    	param.put("key", Integer.toString(key));
	    	if(checked) {
	    		param.put("send_yn", "Y");
	    	} else {
	    		param.put("send_yn", "N");
	    	}
	    	int m = 0;
	    	int n = service.insertMailSenderLogList(param);
	    	for(int i=0; i<recieverData.size(); i++) {
	    		Map<String, String> temp = new HashMap<>();
	    		temp.put("email_log_seq", Integer.toString(key));
	    		temp.put("recv_seq", Integer.toString(i+1));
	    		temp.put("recv_type_cd", recieverData.get(i).get("recv_type_cd"));
	    		temp.put("recv_usr_id", recieverData.get(i).get("recv_usr_id"));
	    		temp.put("recv_email", recieverData.get(i).get("recv_email"));
	    		m = service.insertMailReciverLogList(temp);	
	    	}
	    	Boolean result = n > -1 ? m > -1 ? Boolean.TRUE : Boolean.FALSE : Boolean.FALSE;
	    	
	    	return result;
	    }
	}
	
	public String getmailCss() {
		String mailClass = 
				"<style>\r\n" +
				/*공통 시작*/
				"	* {font-family:\"아리따-돋움4.0(OTF)\" !important;}\r\n" +
				"	#mail_div {border: 1px solid #ddd; position:relative;}\r\n" + 
				"	#mail_div p {margin:0;}\r\n" + 
				" 	#header_div {margin: 0; background: #062c5e; height: 60px; line-height: 60px; box-sizing: border-box; padding-left: 10px;} \r\n" +
				" 	#header_div .logo {height:20px;} \r\n" +
				" 	#header_div .title {font-size: 20px; color: white; padding-top: 2px; padding-left: 10px;}\r\n" +
				" 	#content_div {font-size:14px; padding: 30px 30px 150px 30px; line-height:1.5;} \r\n" +
				" 	#content_div .txc-table {word-break: break-all; text-align:center; border-collapse: collapse;} \r\n" +
				" 	#content_div .txc-table thead {background:#f6f6f6; font-weight:500;} \r\n" +
				"	#content_div .link_text{text-decoration:underline;}\r\n" +
				"	#inquiry_div {display:table; width:100%; position:absolute; left:0; bottom:90px;z-index:1;}\r\n" +
				"	#inquiry_div > div {display:table; float:right; font-size:12px; line-height:1.6; margin-right:30px;}\r\n" +
				" 	#footer_div {width:100%; height:60px; background:#062c5e;} \r\n" +
				" 	#footer_div .background_img{width: 250px; position:absolute; bottom:1px; right:1px;} \r\n" +
				"	.red_color td{color:#f00;}\r\n" +
				/* 타입1 메일 */
				"	.type1_div {width:100%; min-width:887px; max-width:1200px; box-sizing:border-box;}\r\n" +
				"	.type1_div .table_subject > p {font-weight:600 !important;}\r\n" +
				"	.type1_div p {line-height:1.5; margin:0;}\r\n" +
				"	.type1_div td p {font-weight:400;}\r\n" +
				"	.type1_div .table_tr_title {line-height:1.5;}\r\n" +
				"	.type1_div .inquiry_info {font-size:12px;}\r\n" +
				/* 사고보고 메일 */
				"	#acdtMgnt {width:100%; min-width:887px; max-width:1200px; box-sizing:border-box;}\r\n" +
				"	#acdtMgnt .table_subject > p {font-weight:600 !important;}\r\n" +
				"	#acdtMgnt p {line-height:1.5; margin:0;}\r\n" +
				"	#acdtMgnt td p {font-weight:400;}\r\n" +
				"	#acdtMgnt .table_tr_title {line-height:1.5;}\r\n" +
				"	#acdtJob p {margin:0;}\r\n" +
				"	#acdtJob .inquiry_info{font-size:12px;}\r\n" +
				/* 위험성평가 메일 */
				" 	.riskeval_div .txc-table {font-size:14px;} \r\n" +
				"</style>\r\n";
		
		return mailClass;
	}
}
