package com.kist.portal.common.comm.service;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.kist.portal.common.comm.mapper.FileMapper;
import com.kist.portal.common.exception.BizException;
import com.kist.portal.common.login.model.UserInfo;

@Service
public class FileServiceImpl implements FileService {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	FileMapper mapper;
	
	private AmazonS3 s3Client;

	@Value("${spring.profiles.active}")
	private String activeProfile;
	
	@Value("${dgrm.aws-s3-bucket:apne2-dspdev-chems-data}")
    private String bucketName;
	
	@Value("${dgrm.fileDir}")
    private String fileDir;
	
	@Value("${dgrm.fileDirDeleted}")
    private String fileDirDeleted;
	
	@Value("${dgrm.fileUploadType}")
    private String fileUploadType;
	
	@PostConstruct
	public void init() {
		s3Client = AmazonS3ClientBuilder.standard().withRegion(Regions.AP_NORTHEAST_2).build();
	}
	
	@Override
	public List<Map<String, ?>> selectFileList(Map<String, String> param) throws Exception {
		return mapper.selectFileList(param);
	}
	
	@Override
	public String insertFile(MultipartFile[] uploadFiles, UserInfo userInfo, String isMsds) throws Exception {
		// 채번
		String fileId = mapper.selectFileId();
		int seq = 1;
		logger.info("Upload File Cnt : " + uploadFiles.length);
		for(MultipartFile multipartFile : uploadFiles) {
			Map<String, String> param = new HashMap<>();
			logger.info("---------------------------------");
			logger.info("Upload File Name :" + multipartFile.getOriginalFilename());
			logger.info("Upload File Size : " + multipartFile.getSize());
			
			String s3Key = fileId + "-" + seq;
			param.put("id", fileId);
			param.put("seq", String.valueOf(seq));
			String originalFileName = multipartFile.getOriginalFilename();
			originalFileName = originalFileName.substring(originalFileName.lastIndexOf("\\") + 1);
			param.put("file_name", originalFileName);
			param.put("file_size", String.valueOf(multipartFile.getSize()));
			param.put("file_url", uploadFile(multipartFile, fileId, seq));
			param.put("pub_yn", isMsds);
			param.put("username", userInfo.getUsrId());
			
			mapper.insertFile(param);
			seq++;
		}
		
		return fileId;
	}
	
	@Override
	public String updateFile(MultipartFile[] uploadFiles, UserInfo userInfo, String updateKey, String isMsds) throws Exception {
		String fileId = updateKey;
		int seq = Integer.parseInt(mapper.selectSeq(fileId));
		logger.info("Upload File Cnt : " + uploadFiles.length);
		for(MultipartFile multipartFile : uploadFiles) {
			
			logger.info("---------------------------------");
			logger.info("Upload File Name :" + multipartFile.getOriginalFilename());
			logger.info("Upload File Size : " + multipartFile.getSize());
			
			String s3Key = fileId + "-" + seq;
			String originalFileName = multipartFile.getOriginalFilename();
			originalFileName = originalFileName.substring(originalFileName.lastIndexOf("\\") + 1);
			
			Map<String, String> param = new HashMap<>();
			param.put("id", fileId);
			param.put("seq", String.valueOf(seq));
			param.put("file_name", originalFileName);
			param.put("file_size", String.valueOf(multipartFile.getSize()));
			param.put("file_url", uploadFile(multipartFile, fileId, seq));
			param.put("pub_yn", isMsds);
			param.put("username", userInfo.getUsrId());
			
			mapper.insertFile(param);
			seq++;
		}
		
		return fileId;
	}
	
	private String uploadFile(MultipartFile multipartFile, String fileId, int seq) throws Exception {
		String path = "";
		
		// fileUploadType : local, s3 (application.yml 에서 정의)
		// local : 파일 서버 등 저장장치 사용, 저장 경로 정보는 fileDir (application.yml 에서 정의)
		// s3 : AWS S3 버킷 사용
		if ("local".equals(fileUploadType)) {
			File folder = new File(fileDir + "/" + fileId);
			path = folder + "\\" + UUID.randomUUID().toString();
			if (!folder.exists()) {
				try {
					folder.mkdirs();
				} catch (Exception e) {
					e.getStackTrace();
				}
			}
			multipartFile.transferTo(new File(path));
		} else if ("s3".equals(fileUploadType)) {
			String s3Key = fileId + "-" + seq;
			String fileName = multipartFile.getOriginalFilename();
			logger.info("S3 bucket name : " + this.bucketName);
			logger.info("S3 Upload Key :" + s3Key);
			logger.info("S3 Upload File Name :" + fileName);
			
			byte[] bytes = IOUtils.toByteArray(multipartFile.getInputStream());
			ObjectMetadata objectMetadata = new ObjectMetadata();
			objectMetadata.setContentLength(bytes.length);
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
			PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, s3Key, byteArrayInputStream, objectMetadata);
	        s3Client.putObject(putObjectRequest);
	        
	        path = s3Client.getUrl(bucketName, s3Key).toString();
		}
		
        return path;
	}
	
	@Override
	public int deleteFile(String id)  throws Exception{
		String[] fileKey = id.split("-");
		
		Map<String, String> param = new HashMap<>();
		param.put("id", fileKey[0]);
		param.put("seq", fileKey[1]);
		
		// fileUploadType : local, s3 (application.yml 에서 정의)
		// local : 파일 서버 등 저장장치 사용, 저장 경로 정보는 fileDir (application.yml 에서 정의)
		// s3 : AWS S3 버킷 사용
		if ("local".equals(fileUploadType)) {
			// 실제 파일을 삭제하는게 맞는지 모르겠음. Deleted 폴더로 이동
			Map<String, ?> map = mapper.selectFile(param);
			
			// 삭제한 사용자 및 삭제 일시 정보
			String deletedInfo = new SimpleDateFormat("yyyyMMddHHmmss").format(Calendar.getInstance().getTime()) + "-";
			
			// 삭제 폴더 정보 : fileDirDeleted/yyymmdd/atfl_no
			// C:/DEHS/files/deleted/20230118/999
			File deletedFolder = new File(fileDirDeleted + "/" + new SimpleDateFormat("yyyyMMdd").format(Calendar.getInstance().getTime()) + "/" +map.get("atfl_no"));
			String path = deletedFolder + "\\" + deletedInfo + UUID.randomUUID().toString();
			
			// 폴더 생성
			if (!deletedFolder.exists()) {
				try {
					deletedFolder.mkdirs();
				} catch (Exception e) {
					e.getStackTrace();
				}
			}
			
			Path deletedFile = Paths.get((String) map.get("atfl_url"));
			Path newFile = Paths.get(path);
			Files.copy(deletedFile, newFile);
			
			param.put("atfl_url", path);
		} else if ("s3".equals(fileUploadType)) {
			// 실제 파일을 삭제하는게 맞는지 모르겠음.
			s3Client.deleteObject(bucketName, id);
		}
		return mapper.deleteFile(param);
	}
	
	@Override
	public Map<String, Object> downloadFile(Map<String, String> param, String fileKey) throws Exception {
		
		Map<String, Object> result = new HashMap<>();
        byte[] content = null;
        
		// fileUploadType : local, s3 (application.yml 에서 정의)
		// local : 파일 서버 등 저장장치 사용, 저장 경로 정보는 fileDir (application.yml 에서 정의)
		// s3 : AWS S3 버킷 사용
		if ("local".equals(fileUploadType)) {
	        File atfl = new File(param.get("atfl_url"));
	        try {
	        	content = IOUtils.toByteArray(new FileInputStream(atfl));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} 
		} else {
	        S3Object s3Object = s3Client.getObject(bucketName, fileKey);
	        S3ObjectInputStream stream = s3Object.getObjectContent();
	        try {
	            content = IOUtils.toByteArray(stream);
	            s3Object.close();
	        } catch(final IOException ex) {
	        	throw new BizException("IO Error Message : " + ex.getMessage());
	        }
		}
		
        Map<String, ?> file = mapper.selectFile(param);
        result.put("content", content);
        result.put("fileName", file.get("atfl_nm"));
        
        return result;
    }
	
	@Override
	public Map<String, Object> downloadUrlFile(Map<String, String> param) throws Exception {
		Map<String, Object> result = new HashMap<>();
        byte[] content = null;
        
		// fileUploadType : local, s3 (application.yml 에서 정의)
		// local : 파일 서버 등 저장장치 사용, 저장 경로 정보는 fileDir (application.yml 에서 정의)
		// s3 : AWS S3 버킷 사용
		if ("local".equals(fileUploadType)) {
	        File atfl = new File(param.get("atfl_url"));
	        try {
	        	content = IOUtils.toByteArray(new FileInputStream(atfl));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if ("s3".equals(fileUploadType)) {
	        URL url = new URL(param.get("atfl_url"));
	        logger.debug("===========================");
	        logger.debug(param.get("atfl_url"));
	        logger.debug("===========================");
	        
	        
	        try {
	        InputStream is = url.openStream();
	        content = IOUtils.toByteArray(is);
	        } catch(Exception e) {
	        	e.printStackTrace();
	        }
		}
		
        Map<String, ?> file = mapper.selectFile(param);
        result.put("content", content);
        result.put("fileName", file.get("atfl_nm"));
        
        return result;
    }

	@Override
	public Map<String, Object> toByteArrayFromUrlFile(String fileKey) throws Exception {
		
		// 파일 key 분리 : id / seq
		String[] arrFileKey = fileKey.split("-");
		
		Map<String, String> param = new HashMap<>();
		param.put("id", arrFileKey[0]);
		param.put("seq", arrFileKey[1]);
		
		// 파일정보 조회
		byte[] content = null;
		Map<String, ?> fileInfo = mapper.selectFile(param);
		
		String fileName = (String) fileInfo.get("atfl_nm");	// 파일명
		String fileExt = (String) fileInfo.get("atfl_ext");	// 파일 확장자
		String fileUrl = (String) fileInfo.get("atfl_url");	// 파일이 존재하는 URL
		String fileExtYn = (String) fileInfo.get("ext_yn");		// 파일이 존재하는 URL 구분
		
		// 파일의 ext_yn 여부 확인
		if(fileExtYn != null && "Y".equalsIgnoreCase(fileExtYn)) {
	        logger.info("==> fileInfo atfl_url : " + fileUrl);
			// 기타
	        InputStream is = null;
	        try {
	        	URL url = new URL(fileUrl);
		        
		        is = url.openStream();
		        content = IOUtils.toByteArray(is);
		        
	        } catch(Exception ex) {
	        	throw new BizException("IO Error Message : " + ex.getMessage());
	        } finally {
	        	if (is != null) {
	        		is.close();	        		
	        	}
	        }
	        
		} else {
			// AWS S3
        	if("local".equals(activeProfile)) { // 로컬환경에서는 S3를 사용할 수 없음.
    	        logger.info("==> AWS S3 local test용 dummy file 생성");
        		content = "local test".getBytes();
        		fileName = "test";
        		fileExt = "txt";
        	} else {
				S3Object s3Object = null;
		        S3ObjectInputStream s3Stream = null;
        		try {
					s3Object = s3Client.getObject(bucketName, fileKey);
			        s3Stream = s3Object.getObjectContent();
			        
		            content = IOUtils.toByteArray(s3Stream);
		            
				} catch(final IOException ex) {
		        	throw new BizException("IO Error Message : " + ex.getMessage());
		        } finally {
		        	if (s3Object != null) {
		        		s3Object.close();	        		
		        	}
		        	if (s3Stream != null) {
		        		s3Stream.close();	        		
		        	}
		        }
        	}
		}
		
		Map<String, Object> result = new HashMap<String, Object>();
        result.put("fileContent", content);
        result.put("fileName", fileName);
        result.put("fileExt", fileExt);
        result.put("fileUrl", fileUrl);
        
        return result;
    }
	
	@Override
	public List<Map<String,String>> selectExtYn(Map<String, String> param) throws Exception {
		return mapper.selectExtYn(param);
	}
	
	@Override
	public String selectFileKey(Map<String, String> param) throws Exception {
		return mapper.selectFileKey(param);
	}
	
	@Override
	public Map<String, Object> downloadImage(Map<String, String> param, String fileKey) throws Exception {
		byte[] content = null;
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		
		if ("local".equals(fileUploadType)) {
			Map<String, ?> fileInfo = mapper.selectFile(param);
	        File atfl = new File((String)fileInfo.get("atfl_url"));
	        try {
	        	 // content = IOUtils.toByteArray(stream);
	        	BufferedImage srcImg = ImageIO.read(new FileInputStream(atfl));
	        	BufferedImage destImg = new BufferedImage(srcImg.getWidth(), srcImg.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
	        	Graphics2D g = destImg.createGraphics();
	            g.drawImage(srcImg, 0, 0, srcImg.getWidth(), srcImg.getHeight(), null);
	            
	            ImageIO.write(destImg, "jpeg", outputStream);
	            
	            content = outputStream.toByteArray();
	            
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else if ("s3".equals(fileUploadType)) {
	        S3Object s3Object = s3Client.getObject(bucketName, fileKey);
	        S3ObjectInputStream stream = s3Object.getObjectContent();
	        try {
	            // content = IOUtils.toByteArray(stream);
	        	BufferedImage srcImg = ImageIO.read(stream);
	        	
	        	BufferedImage destImg = new BufferedImage(srcImg.getWidth(), srcImg.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
	        	Graphics2D g = destImg.createGraphics();
	    		g.drawImage(srcImg, 0, 0, srcImg.getWidth(), srcImg.getHeight(), null);
	            
	            ImageIO.write(destImg, "jpeg", outputStream);
	            
	            content = outputStream.toByteArray();
	            
	            s3Object.close();
	        } catch(final IOException ex) {
	        	throw new BizException("IO Error Message : " + ex.getMessage());
	        }
		}
        
        Map<String, ?> file = mapper.selectFile(param);
        Map<String, Object> result = new HashMap<>();
        result.put("content", content);
        result.put("fileName", file.get("atfl_nm"));
        
        return result;
    }

	@Override
	public String selectDocFileNo(Map<String, Object> param) {
		return mapper.selectDocFileNo(param);
	}

	@Override
	public List<Map<String,String>> selectPubYn(Map<String, String> param) throws Exception {
		return mapper.selectPubYn(param);
	}

}
