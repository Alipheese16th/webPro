package com.lec.loopQuiz;
//����2 : 1~10���� ���� �� ¦�� �Ǵ� Ȧ���� ���� ���غ���
public class Quiz2 {
	public static void main(String[] args) {
		
		int jjacksu = 0;
		int holsu = 0;
		
		for(int i=1;i<11;i++) {
			 if(i%2==0) {
				 jjacksu += i;
			 }else {
				 holsu += i;
			 }
		}
		
		System.out.println("1~10������ ¦���� ���� "+jjacksu);
		System.out.println("1~10������ Ȧ���� ���� "+holsu);
		
	}
}
