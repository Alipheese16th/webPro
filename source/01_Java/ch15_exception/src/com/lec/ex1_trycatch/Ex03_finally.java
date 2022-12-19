package com.lec.ex1_trycatch;

public class Ex03_finally {
	public static void main(String[] args) {
		
		int[] arr = {0,1,2};
		
		for (int i = 0; i <= arr.length; i++) {
			
			try {
				System.out.println("arr["+i+"] = "+arr[i]);
				
			}catch(ArrayIndexOutOfBoundsException e) {
				System.out.println("예외 메세지 : "+e.getMessage());
				
			}finally {	//파일을 정상적으로 실행하든, 에러가 나서 멈추든, 파일 마무리 작업을 실행하는 단계
				System.out.println("try절 실행후에도, catch절 실행후에도 실행되는 절");
			}
		}
		
	}
}
