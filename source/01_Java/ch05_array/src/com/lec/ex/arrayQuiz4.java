package com.lec.ex;

//76,45,34,89,100,50,90,92 8���� ���� 1���� �迭�� �ʱ�ȭ �ϰ� 
// �̵� ������ ũ�� ������ ��Ÿ���� ���α׷��� �ۼ��Ͻÿ�
public class arrayQuiz4 {
	public static void main(String[] args) {
		
		int[] array = { 76, 45, 34, 89, 100, 50, 90, 92 };
		int i, j, temp;
		
		for (i = 0; i < array.length - 1; i++) {
			for (j = i + 1; j < array.length; j++) {
				if (array[i] > array[j]) {
					temp = array[i];
					array[i] = array[j];
					array[j] = temp;
				}
			}
		}
		for (i = 0; i < array.length; i++) {
			System.out.print(array[i]);
			if (i != array.length - 1) {
				System.out.print(",");
			}
		}
	}
}
