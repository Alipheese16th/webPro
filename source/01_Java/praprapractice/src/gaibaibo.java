import java.util.Random;
import java.util.Scanner;
// you가 질 때까지 반복
public class gaibaibo {
	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		Random random = new Random();
		String you;
		int computer;
		int youInt;
		
		do{
			System.out.println("가위,바위,보 중 하나를 선택하세요");
			you = scanner.next().trim();
			youInt = you.equals("가위")? 0 : you.equals("바위")? 1 : you.equals("보")? 2 : -1 ;
			
			computer = random.nextInt(3);
			String computerStr = (computer==0) ? "가위" : (computer==1)?"바위":"보자기";
			
			if(youInt<0 || youInt>2) {
				System.out.print("잘못 입력하셨습니다  ");
			}else {
				if( (youInt+2)%3 == computer ) {
					System.out.printf("당신은 %s, 컴퓨터는 %s. 당신이 이겼어요 ^.^\n", you, computerStr);
				}else if((youInt+1)%3 == computer){
					System.out.printf("당신은 %s, 컴퓨터는 %s. 컴퓨터가 이겼어요 ㅠ.ㅠ\n", you, computerStr);
				}else {
					System.out.printf("당신은 %s, 컴퓨터는 %s. 비겼어요\n",  you, computerStr);
				}
			}
		}while(youInt==-1 || !((youInt+2)%3 == computer));
		
		scanner.close();
		System.out.println("You Win! BYE");
	}//main
}//class
