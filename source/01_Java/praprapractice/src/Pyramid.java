
public class Pyramid {

	public static void main(String[] args) {
		
		//int num = 10;
		//�ﰢ
//		for (int i = 0; i < num; i++) {
//			for (int j = 0; j <= i  ; j++) {
//				System.out.print('*');
//			}
//			System.out.println();
//		}
		//���ﰢ
//		for (int i = 0; i < num ; i++) {
//			for (int j = 0 ; j < num - i ; j++) {
//				System.out.print('*');
//			}
//			System.out.println();
//		}
		//�Ƕ�̵�
//		for (int i = 0; i < num; i++) {	//0 1 2 3 4
//			for (int j = 1; j < num - i ; j++) {	//���� 4 3 2 1 0
//				System.out.print(" ");
//			}
//			for (int k = 0; k < i*2+1 ; k++) {
//				System.out.print('*');
//			}
//			System.out.println();
//		}
		//���Ƕ�̵�
//		for (int i = 0; i < num ; i++) {	// 0 1 2 3 4 5 ...
//			for (int j = 0; j < i ; j++) {	// 0 1 2 3 4 5  
//				System.out.print(" ");
//			}
//			for (int k = 0 ; k < 2*num-(i*2+1) ; k++) { // i�� 0�϶� �ִ��  i�� 9�϶� 1�̾����
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
		//���̾Ƹ��
//		for (int i = 0; i < num; i++) {	// i : 0 ~ 9
//			for (int j = 0; j < num-i; j++) {	//����  �ݺ�Ƚ�� 10 , 9 , 8 , 7 ... , 1
//				System.out.print(" ");
//			}
//			for (int k = 0; k < i*2+1; k++) {  //�� �ݺ�Ƚ�� 1 , 3, 5, 7, ... 19
//				System.out.print("*");
//			}
//			System.out.println();
//		}
//		for (int i = num; i > 0; i--) { //i :  10 ~ 1
//			for (int j = num-i+2; j > 0; j--) {	//����  
//				System.out.print(" ");
//			}
//			for (int k = (i-1)*2-1; k > 0 ; k--) {	//��  
//				System.out.print("*");
//			}
//			System.out.println();
//		}
		
		
		
        for(int i=1; i<=5; i++) {   	//i�� ���� �þ��  1 2 3 4 5
            for (int j=1; j<=5-i; j++) {
                System.out.print("  "); //������ ���� �پ��� 
            }
            for (int j=1; j<=i*2-1; j++) { //���� ���� �þ�� 
                System.out.print("* "); 
            }
            System.out.println();
        }
        //�ϴ�.
        for(int i=4; i>0; i--) {    // i�� ���� �پ���  4 3 2 1
            for (int j=4; j>=i; j--) {
                System.out.print("  "); //������ ���� �þ�� 
            }
            for (int j=1; j<=i*2-1; j++) {// ���� ���� �پ��� 
                System.out.print("* "); 
            }
            
            System.out.println();
        }
		
		
		
		
		
		
		
		
		
	}//main

}
