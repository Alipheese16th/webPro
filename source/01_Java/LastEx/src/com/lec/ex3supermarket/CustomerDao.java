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
	
	//1.ȸ�����Խ�	��ȭ��ȣ, �̸� �Է� 	��DTO(��ȭ��ȣ,�̸�) return int 1 , 0
	public int insertCustomer() {
		return 1;
	}

	//2.��4�ڸ��Է¹޾� SELECT ������ ���	��DTO(ID,��ȭ,�̸�,����Ʈ,���Ŵ�����,��޸�,�������ݾ�)
	
	//3.��ǰ���� �����̵�� ��ǰ������ �Է¹޾� ó�� /update�����ݾ�,����Ʈ,WHERE���Ƶ�/�������������� ����Ȯ��
		
	//4.��޺� ���	/��� ��޸� ��º����ְ�/ ��� �Է¹����� ������¡�DTO(ID,��ȭ,�̸�,����Ʈ,���Ŵ�����,��޸�,�������� ���� �߰��ݾ�)
	
	//5.����� ���	��DTO(ID,��ȭ,�̸�,����Ʈ,���Ŵ�����,��޸�,�������� ���� �߰��ݾ�)
	
	//6.ȸ��Ż��	��ȭ��ȣ �Է¹޾� 	DELETE WHERE ��ȭ��ȣ

	
	
	
	
	
	
	
}
