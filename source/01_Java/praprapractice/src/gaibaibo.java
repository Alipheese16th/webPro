import java.util.Random;
import java.util.Scanner;
// you�� �� ������ �ݺ�
public class gaibaibo {
	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		Random random = new Random();
		String you;
		int computer;
		int youInt;
		
		do{
			System.out.println("����,����,�� �� �ϳ��� �����ϼ���");
			you = scanner.next().trim();
			youInt = you.equals("����")? 0 : you.equals("����")? 1 : you.equals("��")? 2 : -1 ;
			
			computer = random.nextInt(3);
			String computerStr = (computer==0) ? "����" : (computer==1)?"����":"���ڱ�";
			
			if(youInt<0 || youInt>2) {
				System.out.print("�߸� �Է��ϼ̽��ϴ�  ");
			}else {
				if( (youInt+2)%3 == computer ) {
					System.out.printf("����� %s, ��ǻ�ʹ� %s. ����� �̰��� ^.^\n", you, computerStr);
				}else if((youInt+1)%3 == computer){
					System.out.printf("����� %s, ��ǻ�ʹ� %s. ��ǻ�Ͱ� �̰��� ��.��\n", you, computerStr);
				}else {
					System.out.printf("����� %s, ��ǻ�ʹ� %s. �����\n",  you, computerStr);
				}
			}
		}while(youInt==-1 || !((youInt+2)%3 == computer));
		
		scanner.close();
		System.out.println("You Win! BYE");
	}//main
}//class
