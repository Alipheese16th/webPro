package com.lec.test;

//76,45,34,89,100,50,90,93 8���� ���� 1���� �迭�� �ʱ�ȭ �ϰ� 
// ���� �հ�� ��� �׸��� �ִ밪�� �ּҰ��� ���ϴ� ���α׷��� �ۼ�
public class ProgramingLan {
	public static void main(String[] args) {
		int[] array = { 76, 45, 34, 89, 100, 50, 90, 93 };
		int i,tot = 0;
		int max = 0 , min = 999;
		for(i=0;i<array.length;i++) {
			tot += array[i];
		}
		double avg = (double)tot/array.length;
		
		for(i=0;i<array.length;i++) {
			if(array[i]>max) {
				max=array[i];
			}
			if(array[i]<min) {
				min=array[i];
			}
		}
		System.out.printf("���� ���� %d �̰� ����� %.2f �̴�. \n"
				+ "�׸��� �ִ밪�� %d �ּҰ��� %d�̴�.",tot,avg,max,min);
		
	}
}
