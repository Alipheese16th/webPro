
public class Pyramid {

	public static void main(String[] args) {
		
		//int num = 10;
		//삼각
//		for (int i = 0; i < num; i++) {
//			for (int j = 0; j <= i  ; j++) {
//				System.out.print('*');
//			}
//			System.out.println();
//		}
		//역삼각
//		for (int i = 0; i < num ; i++) {
//			for (int j = 0 ; j < num - i ; j++) {
//				System.out.print('*');
//			}
//			System.out.println();
//		}
		//피라미드
//		for (int i = 0; i < num; i++) {	//0 1 2 3 4
//			for (int j = 1; j < num - i ; j++) {	//공백 4 3 2 1 0
//				System.out.print(" ");
//			}
//			for (int k = 0; k < i*2+1 ; k++) {
//				System.out.print('*');
//			}
//			System.out.println();
//		}
		//역피라미드
//		for (int i = 0; i < num ; i++) {	// 0 1 2 3 4 5 ...
//			for (int j = 0; j < i ; j++) {	// 0 1 2 3 4 5  
//				System.out.print(" ");
//			}
//			for (int k = 0 ; k < 2*num-(i*2+1) ; k++) { // i가 0일때 최대고  i가 9일때 1이어야함
//				System.out.print('*');
//			}
//			System.out.println();
//		}
//		for (int i = num ; i > 0; i--) {
//			for (int j = num - i; j > 0 ; j--) {
//				System.out.print(" ");
//			}
//			for (int k = i * 2 - 1 ; k > 0; k--) {
//				System.out.print("*");
//			}
//			System.out.println();
//		}
		//다이아몬드
//		for (int i = 0; i < num; i++) {	// i : 0 ~ 9
//			for (int j = 0; j < num-i; j++) {	//공백  반복횟수 10 , 9 , 8 , 7 ... , 1
//				System.out.print(" ");
//			}
//			for (int k = 0; k < i*2+1; k++) {  //별 반복횟수 1 , 3, 5, 7, ... 19
//				System.out.print("*");
//			}
//			System.out.println();
//		}
//		for (int i = num; i > 0; i--) { //i :  10 ~ 1
//			for (int j = num-i+2; j > 0; j--) {	//공백  
//				System.out.print(" ");
//			}
//			for (int k = (i-1)*2-1; k > 0 ; k--) {	//별  
//				System.out.print("*");
//			}
//			System.out.println();
//		}
		
		
		
        for(int i=1; i<=5; i++) {   	//i가 점점 늘어난다  1 2 3 4 5
            for (int j=1; j<=5-i; j++) {
                System.out.print("  "); //공백이 점점 줄어든다 
            }
            for (int j=1; j<=i*2-1; j++) { //별이 점점 늘어난다 
                System.out.print("* "); 
            }
            System.out.println();
        }
        //하단.
        for(int i=4; i>0; i--) {    // i가 점점 줄어든다  4 3 2 1
            for (int j=4; j>=i; j--) {
                System.out.print("  "); //공백이 점점 늘어난다 
            }
            for (int j=1; j<=i*2-1; j++) {// 별이 점점 줄어든다 
                System.out.print("* "); 
            }
            
            System.out.println();
        }
		
		
		
		
		
		
		
		
		
	}//main

}
