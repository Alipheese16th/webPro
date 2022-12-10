import java.util.Scanner;

public class Gaibaibo {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int com, youi = 10;
		String yous,coms = null;
		
		set:while(true) {
			do {
				System.out.print("가위바위보 명령어 \n 1.가위  2.바위  3.보  4.끝 or 종료 : ");
				yous = scanner.next();
				
				com = (int)(Math.random()*3);
			
				switch(yous) {
					case "가위": youi = 0; break;
					case "바위": youi = 1; break;
					case "보": youi = 2; break;
					case "끝": case "종료": break set;
					default : System.out.print("% 잘못 입력하셨습니다 %\n");
				}
			}while(!(0<=youi&&youi<=2));
			switch(com) {
			case 0: coms = "가위"; break;
			case 1: coms = "바위"; break;
			case 2: coms = "보"; break;
		}
			
			if((youi+2)%3==com) {
				System.out.printf("당신(%s) 컴퓨터(%s) 이겼습니다. 축하합니다.\n",yous,coms);
			}else if((youi+1)%3==com){
				System.out.printf("당신(%s) 컴퓨터(%s) 졌습니다. 아쉽네요\n",yous,coms);
			}else {
				System.out.printf("당신(%s) 컴퓨터(%s) 비겼습니다.\n",yous,coms);
			}youi = 10;
	
		}
		
		scanner.close();
	}

}
