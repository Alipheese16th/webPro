package com.lec.ex6_exam;

public class MemberTestMain {

	public static void main(String[] args) {
		Member member = new Member("aaa","xxx","홍길동","hong@company.com",
				"서울 강남구","2000-01-01",'M');
		System.out.println(member.infoString());


		Member[] memarray = new Member[6];
		
		for (int i = 0; i < memarray.length; i++) {
			memarray[i] = new Member("id"+i,"pw"+i,"name"+i,"email"+i,
					"address"+i,"birth"+i,'M');
		}
		
		for (Member a : memarray) {
			System.out.println(a.infoString());
		}



	}

}
