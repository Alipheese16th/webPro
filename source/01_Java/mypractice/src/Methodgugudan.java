import java.util.Scanner;

//������ Quiz : ����ڷκ��� ���ϴ� �ܼ�(2~9) �������� ����ϴ� ���α׷��� �����Ͻÿ�.
//(��, �ܼ��� �Ű������� �޾� �ش� �ܼ��� �������� ����ϴ� �κ��� method�� ó���Ѵ�. ����ڰ� 2~9 ������ ���� �ƴ� ���� �Է��� �� 2~9������ ���� �Է��� ������ ��� �Է� �޴´�.)

public class Methodgugudan {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		int scan;
		System.out.print("������(2~9)�� �Է��Ͻÿ� :");
		scan = scanner.nextInt();
			while(scan<2 || scan>9) {
				System.out.print("2~9�߿� �Է��ϼ��� :");
				scan = scanner.nextInt();
			}
		for(int i=1;i<=9;i++) {
			System.out.printf("%d*%d=%d  ",scan,i,scan*i);
		}
		scanner.close();
	}
}
