package com.lec.ex11_store;
// 3ȣ�� : ��ġ�-6,000  �δ��-7,000  ����� 7,000 ��ū���뱹-6,000
public class Store3 extends HeadQuarterStore {

	public Store3(String storeName) {
		super(storeName);
	}
	
	@Override
	public void kimchi() {
		System.out.println("��ġ� : 6,000��");
	}
	
	@Override
	public void bude() {
		System.out.println("�δ�� : 7,000��");
	}
	
	@Override
	public void bibim() {
		System.out.println("����� : 7,000��");
	}
	
	@Override
	public void sunde() {
		System.out.println("��ū���뱹 : 6,000��");
	}

}
