package com.lec.ex3supermarket;

public class CustomerDao {
	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url    = "jdbc:oracle:thin:@127.0.0.1:1521:xe";
	
	public final static int SUCCESS = 1;
	public final static int FAIL 	= 0;
	private static CustomerDao INSTANCE;
	public static CustomerDao getInstance() {
		if(INSTANCE==null) {
			INSTANCE = new CustomerDao();
		}
		return INSTANCE;
	}
	
	private CustomerDao() {
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}
	
	//1.회원가입시	전화번호, 이름 입력 	★DTO(전화번호,이름) return int 1 , 0
	public int insertCustomer() {
		return 1;
	}

	//2.폰4자리입력받아 SELECT 고객정보 출력	★DTO(ID,전화,이름,포인트,구매누적액,등급명,레벨업금액)
	
	//3.물품구입 고객아이디와 물품가격을 입력받아 처리 /update누적금액,포인트,WHERE고객아디/레벨오를지말지 조건확인
		
	//4.등급별 출력	/모든 등급명 출력보여주고/ 등급 입력받으면 정보출력★DTO(ID,전화,이름,포인트,구매누적액,등급명,레벨업을 위한 추가금액)
	
	//5.모든멤버 출력	★DTO(ID,전화,이름,포인트,구매누적액,등급명,레벨업을 위한 추가금액)
	
	//6.회원탈퇴	전화번호 입력받아 	DELETE WHERE 전화번호

	
	
	
	
	
	
	
}
