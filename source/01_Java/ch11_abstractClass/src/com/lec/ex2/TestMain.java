package com.lec.ex2;

public class TestMain {
	public static void main(String[] args) {
		SuperClass obj = new ChildClass();
		obj.method1();
		obj.method2();
		obj.method3();
		
				SuperClass sObj = new SuperClass() {//이건 SuperClass가 아니다.
						@Override	//이름없는 새로운 클래스다. 디렉토리 가보면 이상한 클래스파일이 있다.
						public void method1() {
							System.out.println("익명클래스의 method1");
							}
						};//웹개발에서 쓰진않고 안드로이드 에서나 쓸법한 문법
		sObj.method1();
		sObj.method2();
		sObj.method3();
	}
}
