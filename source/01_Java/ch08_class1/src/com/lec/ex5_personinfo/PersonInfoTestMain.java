package com.lec.ex5_personinfo;

public class PersonInfoTestMain {

	public static void main(String[] args) {
		
		int[] arr = {1,2,3};
		int[] arr2 = new int[3];
		arr[0]=1;arr[1]=2;arr[2]=3;
		
		for (int i = 0; i < arr.length; i++) { //�Ϲ�for��
			System.out.print(arr[i]);
		}
		for(int a : arr2) {		//Ȯ��for��
			System.out.println(a);
		}
		
		PersonInfo p1 = new PersonInfo("ȫ",32,'f');
		
		PersonInfo[] person = { new PersonInfo("ȫ�浿",25,'m'),
								new PersonInfo("ȫ���",24,'f'), };
		
		for (int i = 0; i < person.length; i++) {
			System.out.println(person[i].infoString());
			person[i].print();
		}
		
		for(PersonInfo p : person) {
			System.out.println(p.infoString());
		}
		
		PersonInfo[] person2 = new PersonInfo[2];
		person2[0] = new PersonInfo("ȫ����",17,'f');
		person2[1] = p1;   //���� p1�� �ٲ�� person2[1]�� �ٲ��. ���� �ּҰ��� ����Ű�� ����
		
		
	}
}
