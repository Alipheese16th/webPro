package com.lec.ex;

//return ���� ���� �޼ҵ�
public class Ex04_voidMethod {
	public static void main(String[] args) {
		printLine();
		System.out.println("Hello,World!");
		printLine('~');
		System.out.println("Hello,Java");
		printLine(15);
		System.out.println("Hello,HTML");
		Ex04_voidMethod ex = new Ex04_voidMethod();// static�� ������ Ŭ������ ������ �������Ѵ�
		ex.printLine(21, '#');
	}

	private void printLine(int cnt, char ch) {
		for (int i = 0; i < cnt; i++) {
			System.out.print(ch);
		}
		System.out.println();
	}

	private static void printLine(int cnt) {
		for (int i = 0; i < cnt; i++) {
			System.out.print('-');
		}
		System.out.println();
	}

	private static void printLine(char ch) {
		for (int i = 0; i < 20; i++) {
			System.out.print(ch);
		}
		System.out.println();
	}

	private static void printLine() {
		for (int i = 0; i < 20; i++) {
			System.out.print('-');
		}
		System.out.println();// ����
	}

}