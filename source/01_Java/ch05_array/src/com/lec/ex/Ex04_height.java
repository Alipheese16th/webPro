package com.lec.ex;

import java.util.Scanner;

//배열을 이용하여 학생들(영희, 철수, 길동, 영수, 말자)의 키를 입력받고, 평균치를 구해보자
//그리고 가장 큰 학생과 가장 작은 학생을 구분해 보자
public class Ex04_height {

	public static void main(String[] args) {
		String[] arrName = { "영희", "철수", "길동", "영수", "말자" };
		int[] arrHeight = new int[arrName.length];
		Scanner scanner = new Scanner(System.in);
		int totHeight = 0;
		// 키 입력받기 -> totHeight에 누적
		for (int idx = 0; idx < arrName.length; idx++) {
			System.out.print(arrName[idx] + "의 키는? : ");
			arrHeight[idx] = scanner.nextInt();
			totHeight += arrHeight[idx];
		}
		for (int idx = 0; idx < arrName.length; idx++) {
			System.out.printf("%d번째 %s의 키는 %d\n", idx, arrName[idx], arrHeight[idx]);
		}
		System.out.println("그래서 평균 키는 " + (double) totHeight / arrHeight.length);
		scanner.close();
		
		// 가장 큰 학생과 가장 작은학생
		int maxIdx = 0, maxHeight = arrHeight[0]; // 최대값을 구하기 위한 변수
		int minIdx = 0, minHeight = arrHeight[0];// 최소값을 구하기 위한 변수
		for (int idx = 1; idx < arrName.length; idx++) {
			if (arrHeight[idx] > maxHeight) {
				maxHeight = arrHeight[idx];
				maxIdx = idx;
			} // if최대값
			if (arrHeight[idx] < minHeight) {
				minHeight = arrHeight[idx];
				minIdx = idx;
			} // if최소값
		} // for
		System.out.printf("가장 큰 학생 %s의 키는 : %d\n", arrName[maxIdx], maxHeight);
		System.out.printf("가장 작은 학생 %s의 키는 : %d\n", arrName[minIdx], minHeight);

	}
}
