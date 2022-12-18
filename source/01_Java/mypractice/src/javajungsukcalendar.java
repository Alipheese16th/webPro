import java.util.Calendar;
import java.util.Scanner;

public class javajungsukcalendar {

	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		String[] arr;
		
		do {
		System.out.println("년 월 (정수)입력");
		String a1 = scanner.nextLine();
		arr = a1.split(" ");
		}while(arr.length != 2);
		
		int year = Integer.parseInt(arr[0]);
		int month = Integer.parseInt(arr[1]);
		int dayOfWeek;
		int endDay;
		
		
		Calendar sDay = Calendar.getInstance();
		Calendar eDay = Calendar.getInstance();
		
		sDay.set(year, month-1,1);	//시작날짜 : 해당 월의 1일
		eDay.set(year, month,1);
		eDay.add(Calendar.DATE, -1);//끝날짜 : 해당 월의 마지막날
		
		dayOfWeek = sDay.get(Calendar.DAY_OF_WEEK);	//시작날짜의 요일 : 해당 월의 1일의 요일
		endDay = eDay.get(Calendar.DATE);	//해당월의 마지막날의 정수
		
		System.out.println("\t\t\t     "+year+"년 "+month+"월");
		System.out.println("\tSU\tMO\tTU\tWE\tTH\tFR\tSA");
		
		for(int i=1; i < dayOfWeek; i++) {
			System.out.print("\t");
		}
		
		for(int i=1, n=dayOfWeek; i<=endDay; i++, n++) {
			System.out.print((i<10)?"\t0"+i:"\t"+i);
			if(n%7==0)System.out.println();
		}
		
		
		
		
		
		
		
	}
}
