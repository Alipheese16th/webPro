package com.lec.ex;

// println() : �ܼ�â ��� (��������)
// print() : �ܼ�â ���(���� ������)
// printf() : �ܼ�â ��� (���� ����)
public class VarEx02 {
	public static void main(String[] args) {
		// Ÿ�� ������ �Ҵ翬���� ��;
		int i = 10;
		byte j = 127;
		double h = 10.15;
		i = 100; // ���� ��� �ٲ㼭 ������ �ִ�.
		System.out.println("i=" + i + "\tj=" + j + "\th=" + h);
		System.out.printf("i=%d \t j=%d \t h=%.2f \n", i, j, h);
		char c1 = '��';
		char c2 = '�l';
		// %c:����, %s:���ڿ�, %d:����, %f:�Ǽ�, %b:boolean
		// System.out.printf(Format,args);
		System.out.printf("c1=%c \t c2=%c\n", c1, c2);
		System.out.printf("c1=%d \t c2=%d\n", (int) c1, (int) c2);
		System.out.println("���α׷��� ");

	}
}
// ���� boolean 	1 byte true, false
// ������ char 	2 byte 0~65,535
// ������ byte 	1 byte -128 ~ 127 (-27~ 27-1)
// 		short 	2 byte -32,768 ~ 32,767 (-215~ 215-1)
// 		int 	4 byte -2,147,483,648 ~ 2,147,483,647
// 		long 	8 byte -9,223,372,036,854,775,808 ~ 9,223,372,036,854,775,807
// �Ǽ��� float 	4 byte -3.4E38 ~ +3.4E38
// 		double 	8 byte 1.7E308 ~ + 1.7E308
