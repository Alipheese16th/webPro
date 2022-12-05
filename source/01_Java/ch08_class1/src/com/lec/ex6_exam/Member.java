package com.lec.ex6_exam;

public class Member {

	private String id,pw,name,email,address,birth;
	private char gender;

	public Member() {}

	public Member(String id, String pw, String name, String email,
			String address, String birth, char gender) {
		this.id = id;
		this.pw = pw;
		this.name = name;
		this.email = email;
		this.address = address;
		this.birth = birth;
		this.gender = gender;
	}

	public String infoString() {
		return "id : "+id+"\n�̸� : "+name+"\n�̸��� : "+email+"\n�ּ� : "+address+
				"\n���� : "+birth+"\n���� : "
+(gender=='M'||gender=='m'?"����":gender=='F'||gender=='f'?"����":"M,F �� �ϳ��� �Է��ϼ���");
	}

	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getBirth() {
		return birth;
	}

	public char getGender() {
		return gender;
	}
	
}
