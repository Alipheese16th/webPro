package com.lec.ex;
// ������� : + - * / %(������ ������)
public class Ex01 {
	public static void main(String[] args) {
		
		int n1=33, n2=10;
		int result; double resultDouble;
		
		result = n1 + n2;
		System.out.printf("%d %c %d = %d%n",n1,'+',n2,result);
		
		result = n1 / n2;
		System.out.printf("%d %c %d = %d%n",n1,'/',n2,result); //��
		
		result = n1 % n2;
		System.out.printf("%d %c %d = %d%n",n1,'%',n2,result); //������
		// ������ �������� �뵵 : ¦Ȧ�� �Ǻ�, ��� �Ǻ� ....
		
		if(n1%2 == 0) {
			System.out.println("n1�� ¦��");
		}else {
			System.out.println("n1�� Ȧ��");
		}
		
	}
}
//if(n1%2 == 1) �̷��� Ȧ���� ���ϴ°� ���� �ʴ�. ����� Ȧ���� ��������� ������ Ȧ���� ���� ���� -1 �̱⶧����
// Ȧ������ false �� �ȴ�. if(n1%2 == 1 || n1%2 ==-1) �̷��� �ΰ����� ���ϰų�  ¦���� ���ؼ� else�� Ȯ���ϴ°� ����.
