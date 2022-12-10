package com.lec.ex4_product;

public class ProductMain {

	public static void main(String[] args) {
		System.out.println(Product.count);
		Product.staticMethod();
		Product p1 = new Product();
		p1.infoPrint();
//		System.out.println(p1.count);
		System.out.println(Product.count);
		Product p2 = new Product();
		p2.infoPrint();
		System.out.println("static º¯¼ö : "+Product.count);
	}

}
