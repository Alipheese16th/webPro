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
		(" 아이디 = %s\n이름 = %s\n이메일 = %s\n주소 = %s\n생일 = %s\n성별 = %s\n",
				id,pw,name,email,address,birth,gender=='m'||gender=='M'?"남자":gender=='f'||gender=='F'?"여자":"무성이신가요");
	}
	
	
	
}
