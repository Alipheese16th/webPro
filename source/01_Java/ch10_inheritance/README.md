 # 상속  
  우리가 생각하는 상속이라는 단어가 맞다. 
현실 세계에서 부모님이 자식들에게 재산을 물려주는 것과 비슷하다. 
차이라고 하면 자식(클래스)이 상속받고 싶은 부모(클래스)를 선택해서 
물려받는다. 이때 상속받는 클래스를 자식 클래스, 하위 클래스 또는 
서브 클래스라고 부른다. 상속을 해주는 클래스를 부모 클래스, 
상위 클래스 또는 슈퍼 클래스라고 한다.  

상속의 대상
 자식 클래스가 부모 클래스로부터 상속을 받게 되면 부모 클래스의 필드와
 메서드를 물려받게 된다. 단, 접근제어자가 private을 갖는 필드나 메소드는
 상속이 불가하고, 패키지가 다를 경우 접근제어자가 default인 경우도 
상속이 불가하다.  

상속의 장점
 상속의 장점은 처음에 설명했던 것과 큰 차이가 없다. 
중복된 코드를 줄일 수 있고, 유지 보수가 편리하며, 통일성이 있고 
다형성을 구현할 수 있다.  

 상속을 받는 방법은 간단하다. 상속받을 자식 클래스 뒤에 
extends 키워드를 사용하고 부모 클래스를 적어주면 된다.  


<<<<상속 문법 이미지  (클래스 이름 extends 상위클래스 )>>>>   

 ### 상속의 장점  
 - 모든 개발을 처음부터 하얀 도화지에 할 필요는 없다. 즉 개발시간을 단축할수있다. 
기존에 만들어진 클래스는 검증이 된 클래스이므로 내가 테스트 할 필요가 없다.  

 - 다양한 객체(타입)을 상속을 통해서 하나의 객체(타입)으로 묶을 수 있다.
추상화의 개념이 필요한데, 아직은 배우지 않았으니 우선은 다양한 객체를 통일 시킬수 
있다고만 알고 있으면 된다.  

 ###   같은 타입의 배열 
하나의 클래스를 상속받은 여러 자식클래스는 
같은 부모클래스타입을 갖고 있기때문에 같은 타입이라고 할수있다.
같은 타입이라면 참조타입 **배열**로 만들수 있다.  
ex) 개구리, 원숭이 , 독수리는 다같은 동물이다 . 동물 배열. 
동물 배열 안에서 동물[i]의 메소드를 사용한다고하면 동물객체의 메소드만 쓸수 있다. 
개구리의 메소드를 쓰고싶다면 거기서 다시 개구리로 형변환을 시켜줘야한다.



  
### 생성자  
자식 클래스의 객체를 생성할때 생성자를 호출하면 
그 부모 클래스의 생성자부터 호출을 한다. 메모리 생성 순서가  
제일 상위 클래스 데이터, 생성자 -> 그다음 클래스 -> 하위클래스 데이터, 생성자   
  
만약 부모클래스의 디폴트생성자가 없다면 자식클래스에서 상속을 못받는다.
반드시 생성자로 호출을 해야 쓸수있기때문.
   
super() 호출  
안써도 내 생성자 함수 안에 
자동으로 매개변수없는 슈퍼생성자가 첫번째 줄에 호출이된다.
그동안 안써왔지만 Object클래스의 생성자를 호출하고 있었던 것.  
 
this() 생성자 호출  
 생성자끼리도 호출이 가능하다. 단 생성자 내용중 반드시 **첫번째 줄** 
에 this()를 적어야한다. 첫번째 라인이 아니라면 에러가 난다.
호출해서 불러오고 다음줄 명령을 순차적으로 실행한다.   

  
 ### 오버로딩과 오버라이딩  
 - 오버로딩이란  
 **함수의 중복 정의**

 - 오버라이딩이란  
 **함수의 재정의**
  
상속에 있어서 오버라이딩은 필수적이다. 
부모클래스의 메소드를 어느정도 고칠 필요가 있으면 
자식 클래스에서 고쳐쓸수있다.  
사용할때는 첫문장에 @Override를 붙여야한다. 
컴파일러 단계에서 확인하는 문구
 

객체는 형변환을 할수 있다. 객체의 명시적인 형변환
그러나 함부로 사용하면 위험하다. 
타입이 맞는지 확인하고 형변환을 해줘야하는데
IF문으로 A instanceof B 를 사용해서 트루라면 형변환을 해주자  


  


 ##### 여러가지  
  \t 는 항상 일정하게 띄어지는 공백이 아니라 정해진 열까지 떨어진다.
1번째 - 9번째 -17 번째 열마다 탭이 떨어진다.

