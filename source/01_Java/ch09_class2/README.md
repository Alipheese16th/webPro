 # 클래스 - 접근제한의 종류와 static, 패키지   

 ### 접근제한자란?   
멤버들은 객체 자신들만의 속성이자 특징이므로 대외적으로 공개되는것이 결코 좋은 것이 아닙니다.    
그런 이유로 프로그래머가 객체의 멤버들에게 접근 제한을 걸수 있는데 이것이 접근 제한자입니다.   
  
 ### 제한자 종류  
 - public : 모든 접근을 허용  
 - protected : 같은 패키지에 있는 객체와 상속관계의 객체들만 허용  
 - default : 같은 패키지에 있는 객체들만 허용  
 - private : 현재 객체 내에서만 허용  
  
![image](https://user-images.githubusercontent.com/117807553/208233767-fe9ef6b1-8948-49c0-9d93-792e4f602c76.png)

   
 ### static   
 인스턴스 변수는 객체마다의 값이 서로 다른 메모리공간에 저장됩니다.   
static변수는 따로 모여서 다른 메모리공간에 저장됩니다.   
클래스변수(static)를 사용할때는 객체와 상관없이 (클래스이름).(변수)을 해야합니다.   
   
 ### iv와 cv의 차이.    
 인스턴스 변수와 클래스 변수   
객체는 인스턴스변수의 집합체라고 생각하면 편합니다.    
클래스 변수는 객체들이 공통적으로 가지는 속성. 공통속성의 의미를 가집니다.  
 공유변수 = 클래스변수 = static변수  

 <image src="https://user-images.githubusercontent.com/117807553/208233946-61d92f1b-39a4-438e-a601-71d7cd3d11e3.png" width="50%" height="50%"/>
 
 - 프로그램을 돌리기위한영역 heap메모리 (객체 인스턴스들 가비지컬렉션 관리영역) 객체들이 모여있음 (new를 이용해서 생성)

 - 데이터영역 클래스(static이 올라오는곳) 가비지콜렉션이 관리하지 않는 영역. 클래스(설계도)들이 모여있음

 ### 패키지 
패키지는 지금까지 내가 연습을 하면서 무수히 많은 클래스를 만들었듯이 패키지도 많이 만들었었습니다.   
생각해보면 간단한 개념입니다. 평소에 파일이 많으면 여러 폴더에 나뉘어 담는 것처럼   
패키지로 분류해서 수많은 클래스를 관리하는 것입니다.   

 ### API   
API란 자바에서 사용자들을 위해 미리 만들어둔 명령어들의 모음입니다.  
JDK를 설치하면 자동적으로 같이 설치 됩니다. 그러나 양이 엄청 방대한데 api는 java1.8기준으로도 3천개가 넘습니다.   
이걸 일일히 전부 외우고 시작할수가 없습니다.   
계속해서 필요할때마다 찾아볼 만한 설명서가 있는데, 그것이 바로 java document   
틈날때마다 들어가서 확인하고 익혀두는 것이 제 성장을 위한 밑거름이 될 것입니다.  


##### 기타  
 - final이 붙은 상수(변하지 않는 수)는 변수 이름을 대문자로 구성하는 것이 일반적입니다.  

 - 패키지명은 대문자를 거의 안쓰고 소문자로만 쓰는게 일반적입니다.    
 패키지명을 회사 주소로 만드는 경우 순서를 거꾸로 씁니다 ex) com.lec.ex   

 - JAVA 프레임워크인 Spring은 패키지를 만들때 3가지의 디렉토리를 만들어야 클래스가 제대로 구동이 됩니다.   
 그래서 패키지만들때 com.lec.ex  

 - 패키지 안의 클래스를 cmd로 킬때는 bin디렉토리 기준에서 (그 안에서 cmd) > com.lec.ex.클래스 < 이런식으로 쳐야 실행이 됩니다.   
 실제 클래스 이름은 패키지명까지 포함입니다.   
  
 - 하위 디렉토리에 있는 패키지라 하더라도 다른 메소드에서 사용하려면 별개의 패키지라서 import가 필요합니다.   


