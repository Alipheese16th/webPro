
### arguments

arguments 객체는 함수에 전달된 인수(Argument)에 해당되는 배열 형태의 객체입니다.
화살표함수를 제외한 모든 함수내에서 사용가능한 지역변수입니다.
arguments 객체를 이용하여 함수 내에서 모든 인수를 참조할수 있으며, 각각 인수에 대한 항목을 갖고 있습니다. 항목의 인덱스는 0부터 시작합니다.

예를들어 함수가 세 개의 인수를 받은 경우 다음과 같이 접근할 수 있습니다.
```
arguments[0]
arguments[1]
arguments[2]
```
 - 각 인수를 설정하거나 재할당할 수도 있습니다.

 - arguments 객체는 Array가 아닙니다. Array와 비슷하지만, length 빼고는 pop()과 같은 어떤 Array 속성도 없습니다.



### 나머지 매개변수((rest parameters)

나머지 매개변수 구문을 사용하면 함수가 정해지지 않은 수의 매개변수를 배열로 받을 수 있습니다.
```
function fn(a,b,...args) {
   console.log(args);
}

fn(1, 2, 3, "A", "B", "C");

// 실행 결과 [3,'A','B','C']
```
함수의 마지막 매개변수 앞에 "..."을 붙이면 모든 후속 매개변수를 배열에 넣도록 지정합니다. 마지막 매개변수만 나머지 매개변수로 설정할 수 있고, 하나만 존재할수 있습니다.

arguments 객체는 실제 배열이 아닙니다. 그러나 나머지 매개변수는 Array 인스턴스이므로 sort, map, forEach, pop 등의 메서드를 직접 적용할 수 있습니다.

함수의 인자 개수가 틀려 나머지 매개변수가 매핑이 안된 경우에는 빈 배열이 됩니다. 




추가 작성중 
https://developer.mozilla.org/ko/docs/Web/JavaScript/Reference/Functions/rest_parameters


스프레드 문법 
https://ko.javascript.info/rest-parameters-spread