import java.util.Scanner;

public class Gaibaibo {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int com, youi = 10;
		String yous,coms = null;
		
		set:while(true) {
			do {
				System.out.print("���������� ��ɾ� \n 1.����  2.����  3.��  4.�� or ���� : ");
				yous = scanner.next();
				
				com = (int)(Math.random()*3);
			
				switch(yous) {
					case "����": youi = 0; break;
					case "����": youi = 1; break;
					case "��": youi = 2; break;
					case "��": case "����": break set;
					default : System.out.print("% �߸� �Է��ϼ̽��ϴ� %\n");
				}
			}while(!(0<=youi&&youi<=2));
			switch(com) {
			case 0: coms = "����"; break;
			case 1: coms = "����"; break;
			case 2: coms = "��"; break;
		}
			
			if((youi+2)%3==com) {
				System.out.printf("���(%s) ��ǻ��(%s) �̰���ϴ�. �����մϴ�.\n",yous,coms);
			}else if((youi+1)%3==com){
				System.out.printf("���(%s) ��ǻ��(%s) �����ϴ�. �ƽ��׿�\n",yous,coms);
			}else {
				System.out.printf("���(%s) ��ǻ��(%s) �����ϴ�.\n",yous,coms);
			}youi = 10;
	
		}
		
		scanner.close();
	}

}
