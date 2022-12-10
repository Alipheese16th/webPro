
public class lotto {

	public static void main(String[] args) {
		int lotto[] = new int[6];
		int result = 0;
		int j,i;
		for (i = 0; i < lotto.length; i++) {
					do { result = (int)(Math.random()*45+1);
								for(j=0;j<i;j++) {
										if(lotto[j]==result) {
												break;
										}
								}
					}while(i!=j);
			lotto[i] = result;
		}
		
		for (int k = 0; k < lotto.length-1; k++) {
					for (int k2 = k+1; k2 < lotto.length; k2++) {
							if(lotto[k]>lotto[k2]) {
								int temp;
								temp = lotto[k];
								lotto[k] = lotto[k2];
								lotto[k2] = temp;
							}
					}
		}
		
		System.out.println("로또번호는 ");
		for (int lot : lotto) {
			System.out.print("   "+lot);
		}
		
	}

}
