package com.lec.quiz;

public class Example {
	public static void main(String[] args) {
		
		// ����, ����, ���� ���� �Ҵ�
		// ����, ���
		// ������ ��� 
		int lan_score = 96, eng_score = 82, mat_score = 73;
		int	total_score = lan_score + eng_score + mat_score;
		double	Average_score = (lan_score + eng_score + mat_score)/3.0;

		System.out.println("Language score is = " + lan_score);
		System.out.println("English score is = " + eng_score);
		System.out.println("Math score is = " + mat_score);
		System.out.println("Total score is = " + (lan_score + eng_score + mat_score));
		System.out.printf("Total score is = %d%n",total_score);
		System.out.println("Average score is = " + ((lan_score + eng_score + mat_score) / 3.0));
		System.out.printf("Average score is = %.2f%n",Average_score);
	}
}
