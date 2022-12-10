// 5! = 5*4*3*2*1
// 5! = 5*4!
// f! = f*(f-1)!
public class Factorial {
	public static void main(String[] args) {
		
		System.out.println(factorial(10));
		
	}
	
//	public static int factorial(int a) {
//		int fact = 1;
//				for(int i=a;i>0;i--) {
//					fact = fact*i;
//				}
//				return fact;
//	}
	
	public static int factorial(int a) {
		if(a<=1) {
			return 1;
		}else {
			return a*factorial(a-1);
		}
	}
	
	
	
	
	
}
