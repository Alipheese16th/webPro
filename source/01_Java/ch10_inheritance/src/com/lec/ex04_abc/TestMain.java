package com.lec.ex04_abc;

public class TestMain {

	public static void main(String[] args) {
		S sObj = new S(); //생성자 함수1개 실행
//		A aObj = new A();
		S aObj = new A(); //생성자 함수2개 실행(부모+자식)
//		B bObj = new B();
		S bObj = new B(); //2개 실행
		C cObj = new C(); //2개 실행
//		S cObj = new C();
//		S[] arr = {new S(), new A(), new B(), new C()};
		S[] arr = {sObj,aObj,bObj,cObj};
		
		for(S a :arr) {
			System.out.println("s = "+a.s);
		}
		
	}
}
