package ch08_quiz;

public class Member {
	public String id,pw,name,email,address,birth;
	public char gender;
	
	public Member(String id, String pw, String name, String email, String address, String birth, char gender) {
		this.id = id;
		this.pw = pw;
		this.name = name;
		this.email = email;
		this.address = address;
		this.birth = birth;
		this.gender = gender;
	}
	
	public String infoString() {
		return String.format
		(" ���̵� = %s\n�̸� = %s\n�̸��� = %s\n�ּ� = %s\n���� = %s\n���� = %s\n",
				id,pw,name,email,address,birth,gender=='m'||gender=='M'?"����":gender=='f'||gender=='F'?"����":"�����̽Ű���");
	}
	
	
	
}
