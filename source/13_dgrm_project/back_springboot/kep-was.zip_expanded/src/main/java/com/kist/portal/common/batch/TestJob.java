package com.kist.portal.common.batch;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.kist.portal.common.batch.mapper.BatchMapper;

@Component
public class TestJob implements Job {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Value("${dgrm.sap-cnst}")
	private String sapCnst;
	
	@Autowired
	BatchMapper mapper;

    @Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("TestJob start, jobKey: " + context.getJobDetail().getKey().getName());
        
        Map<String, String> param = new HashMap<>();
        param.put("batCd", context.getJobDetail().getKey().getName());
        mapper.insertBatch(param); // start
        logger.info("TestJob batNo : " + param.get("batNo"));
        
        
        
        // do something...
        String resultCd = "배치 결과 코드";
        String content = "배치 내용";
        
        param.put("resultCd", resultCd);
        param.put("content", content);
        mapper.updateBatch(param); // end
    }
    
    @Transactional
	public void executeManual(){
		
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String user = "system";
		String pw = "1q2w3e4r5t";
		
		// DB 접속
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			
			con.setAutoCommit(false);
			// 인터페이스 테이블에서 데이터 로드
			String query = " SELECT * FROM IMKFIO_REQ_MT WHERE SND_YN = 'N' ";
			stmt = con.prepareStatement(query);
			// select 쿼리 실행
			rs = stmt.executeQuery();
			while(rs.next()) {
				// 한 줄씩 처리
				Map<String, Object> tempData = new HashMap<>();
				String ifYmd = rs.getString("IF_YMD");
				int ifSeq = rs.getInt("IF_SEQ");
				tempData.put("if_ymd", ifYmd);
				tempData.put("if_seq", ifSeq);
				tempData.put("if_gb", rs.getString("IF_GB"));
				tempData.put("if_gijun", rs.getString("IF_GIJUN"));
				tempData.put("ord_ymd", rs.getString("ORD_YMD"));
				tempData.put("po_no", rs.getString("PO_NO"));
				tempData.put("po_seq", rs.getString("PO_SEQ"));
				tempData.put("itm_cd", rs.getString("ITM_CD"));
				tempData.put("sal_cur_cd", rs.getString("SAL_CUR_CD"));
				tempData.put("ord_qty", rs.getInt("ORD_QTY"));
				tempData.put("biz_no", rs.getString("BIZ_NO"));
				tempData.put("bdgt_org_cd", rs.getString("BDGT_ORG_CD"));
				tempData.put("bp_cust_cd", rs.getString("BP_CUST_CD"));
				tempData.put("div_cust_cd", rs.getString("DIV_CUST_CD"));
				tempData.put("acc_cd", rs.getString("ACC_CD"));
				tempData.put("itm_nm", rs.getString("ITM_NM"));
				tempData.put("itm_spec", rs.getString("ITM_SPEC"));
				tempData.put("ord_ps_usr_id", rs.getString("ORD_PS_USR_ID"));
				tempData.put("ord_nm", rs.getString("ORD_NM"));
				tempData.put("tov_ps_usr_id", rs.getString("TOV_PS_USR_ID"));
				tempData.put("tov_nm", rs.getString("TOV_NM"));
				tempData.put("spcp_nm", rs.getString("SPCP_NM"));
				tempData.put("cp_cust_cd", rs.getString("CP_CUST_CD"));
				tempData.put("cp_cust_nm", rs.getString("CP_CUST_NM"));
				tempData.put("org_cd", rs.getString("ORG_CD"));
				tempData.put("org_nm", rs.getString("ORG_NM"));
				tempData.put("spcp_cd", rs.getString("SPCP_CD"));
				tempData.put("bdgt_org_nm", rs.getString("BDGT_ORG_NM"));
				tempData.put("bp_cust_nm", rs.getString("BP_CUST_NM"));
				tempData.put("div_cust_nm", rs.getString("DIV_CUST_NM"));
				tempData.put("itm_clsf_nm", rs.getString("ITM_CLSF_NM"));
				tempData.put("chm_mtr_yn", rs.getString("CHM_MTR_YN"));
				tempData.put("tov_zip_cd", rs.getString("TOV_ZIP_CD"));
				tempData.put("tov_addr1", rs.getString("TOV_ADDR1"));
				tempData.put("tov_addr2", rs.getString("TOV_ADDR2"));
				tempData.put("msds_fil_url", rs.getString("MSDS_FIL_URL"));
				System.out.println("=============================================");
				System.out.println(tempData);
				// 화학물질관리시스템 인터페이스 수신 테이블에 insert
				mapper.insertImkRecvData(tempData);
				// IMK 인터페이스 테이블 SND_YN 값 Y로 업데이트
				query = " UPDATE IMKFIO_REQ_MT SET SND_YN = 'Y' WHERE IF_YMD = ? AND IF_SEQ = ? ";
				stmt = con.prepareStatement(query);
				stmt.setString(1, ifYmd);
				stmt.setInt(2, ifSeq);
				
				int r = stmt.executeUpdate();
				System.out.println("row : " + r);
				stmt.close();
			}
			System.out.println("========================================");
			System.out.println("종료");
			
			con.commit();
		}catch(Exception e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally {
			if(rs != null) {
				try {
					rs.close();
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
			if(stmt != null) {
				try {
					stmt.close();
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
			if(con != null) {
				try {
					con.close();
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
		}
    }
}
