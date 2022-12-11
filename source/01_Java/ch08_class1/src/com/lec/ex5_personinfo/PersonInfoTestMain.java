package com.lec.ex5_personinfo;

public class PersonInfoTestMain {

	public static void main(String[] args) {
		
		int[] arr = {1,2,3};
		int[] arr2 = new int[3];
		arr[0]=1;arr[1]=2;arr[2]=3;
		
		for (int i = 0; i < arr.length; i++) { //일반for문
			System.out.print(arr[i]);
		}
		for(int a : arr2) {		//확장for문
			System.out.println(a);
		}
		
		PersonInfo p1 = new PersonInfo("홍",32,'f');
		
		PersonInfo[] person = { new PersonInfo("홍길동",25,'m'),
								new PersonInfo("홍길순",24,'f'), };
		
		for (int i = 0; i < person.length; i++) {
			System.out.println(person[i].infoString());
			person[i].print();
		}
		
		for(PersonInfo p : person) {
			System.out.println(p.infoString());
		}
		
		PersonInfo[] person2 = new PersonInfo[2];
		person2[0] = new PersonInfo("홍덕순",17,'f');
		person2[1] = p1;   //이제 p1이 바뀌면 person2[1]도 바뀐다. 같은 주소값을 가리키고 있음
		
		
	}
}
