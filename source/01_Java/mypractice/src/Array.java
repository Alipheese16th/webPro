//{76,45,34,89,100,50,90,93} 8���� ���� 1���� �迭�� �ʱ�ȭ �ϰ� ���� �հ��  ��� �׸��� �ִ밪�� �ּҰ��� ���ϴ� ���α׷��� �ۼ� �Ͻÿ�
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
		System.out.printf("�迭�� �հ�� %d�̰� ����� %.2f�̴�. �ִ밪�� %d �ּҰ��� %d�̴�.",total,average,max,min);
	}

}
