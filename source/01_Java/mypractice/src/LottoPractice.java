
public class LottoPractice {
//중복값 없는 로또생성기를 다시 한번 만들어보자   1~45
	public static void main(String[] args) {
		int[] lotto = new int[6];
		int i=0,j=0,temp;
		
		for(i=0;i<lotto.length;i++) {
				do {
					temp = (int)(Math.random()*45+1);  //난수 생성
							for(j=0;j<i;j++) {  //j는 i보다 작은 (즉 지금보다 전에 만든 로또 수를 재차 확인하는 ) 수
										if(lotto[j] == temp) {  //j가 0~i-1 까지 반복하면서 만약 중복 값이 있다면
											System.out.println((i+1)+"회차"+(j+1)+"번째 "+temp+" 중복");
											break;  					//브레이크 -> do while문으로 빠짐.
										}
							}
				}while(i!=j);  //i랑 j가 같지 않다면 무한반복. i랑 j가 같다는 조건은 정상적으로 중복체크를 완수했을때만임.
			
			lotto[i] = temp; //중복이 제거된 값을 입력
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
