package com.lec.ex4_object;
// new Card('��',6);
public class Card {
	
	private char kind;	// ��, ��, ��, ��
	private int num;
	
	
	public Card() {}
	public Card(char kind, int num) {
		this.kind = kind;
		this.num = num;
	}
	
	@Override
	public String toString() {
		return "ī�� : " + kind + " " + num;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj != null && getClass() == obj.getClass()) {
			Card card = (Card)obj;
			boolean kindcheck = kind == card.kind; //kind�� String�̶�� equals���
			boolean numcheck = num == card.num;
			return kindcheck && numcheck;
		}else {
			return false;
		}
	}
	
}
