//{76,45,34,89,100,50,90,93} 8개의 값을 1차원 배열로 초기화 하고 값에 합계와  평균 그리고 최대값과 최소값을 구하는 프로그램을 작성 하시요
public class Array {

	public static void main(String[] args) {
		int array[]={76,45,34,89,100,50,90,93};
		int total = 0,min=array[0],max=array[0];
		double average;
		for(int i=0;i<array.length;i++) {
			total += array[i];
		} average = (double)total/array.length;
		
		for(int i=1;i<array.length;i++) {
			if(min>array[i]) {
				min = array[i];
			}else if(max<array[i]){
				max = array[i];
			}
		}
		System.out.printf("배열의 합계는 %d이고 평균은 %.2f이다. 최대값은 %d 최소값은 %d이다.",total,average,max,min);
	}

}
