
public class LottoPractice {
//�ߺ��� ���� �ζǻ����⸦ �ٽ� �ѹ� ������   1~45
	public static void main(String[] args) {
		int[] lotto = new int[6];
		int i=0,j=0,temp;
		
		for(i=0;i<lotto.length;i++) {
				do {
					temp = (int)(Math.random()*45+1);  //���� ����
							for(j=0;j<i;j++) {  //j�� i���� ���� (�� ���ݺ��� ���� ���� �ζ� ���� ���� Ȯ���ϴ� ) ��
										if(lotto[j] == temp) {  //j�� 0~i-1 ���� �ݺ��ϸ鼭 ���� �ߺ� ���� �ִٸ�
											System.out.println((i+1)+"ȸ��"+(j+1)+"��° "+temp+" �ߺ�");
											break;  					//�극��ũ -> do while������ ����.
										}
							}
				}while(i!=j);  //i�� j�� ���� �ʴٸ� ���ѹݺ�. i�� j�� ���ٴ� ������ ���������� �ߺ�üũ�� �ϼ�����������.
			
			lotto[i] = temp; //�ߺ��� ���ŵ� ���� �Է�
		}//main for
		
		for(i=0;i<lotto.length-1;i++) {
			for(j=i+1;j<lotto.length;j++) {
				if(lotto[i]>lotto[j]) {
					temp = lotto[i];
					lotto[i] = lotto[j];
					lotto[j] = temp;
				}
			}
		}
		for(i=0;i<lotto.length;i++) {
			System.out.print(lotto[i]+" ");
		}
		
	}//main
}//class
