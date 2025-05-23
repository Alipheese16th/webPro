// cf. 가변인자함수 : 매개변수의 갯수에 따라 변하는 함수. 화살표함수에서는 불가능
// 내장함수 Array()를 이용해서 가변인자 함수 array()를 생성

var sum = (x,y) =>{ // 화살표 함수
    return x+y;
};

// var sum = function(){ // 익명 함수
//     return x+y;
// };

// function sum(x,y){ // 선언적 함수
//     return x+y;
// }

var arr1 = [273,2,3,'Hello'];
var arr2 = Array(273,2,3,'Hello');
var arr3 = [ , , , ];  // 방 3개짜리 빈 배열 (배열에서 마지막 ,는 무시된다 함수형언어특성)
var arr4 = Array(3);   // 방 3개짜리 빈 배열 (매개변수가 하나면 방갯수를 나타냄)
var arr5 = [];      // 방의 갯수가 0인 배열
var arr6 = Array(); // 방의 갯수가 0인 배열

console.log('arr1=',arr1,'- 방의 갯수=',arr1.length);
console.log('arr2=',arr2,'- 방의 갯수=',arr2.length);
console.log('arr3=',arr3,'- 방의 갯수=',arr3.length);
console.log('arr4=',arr4,'- 방의 갯수=',arr4.length);
console.log('arr5=',arr5,'- 방의 갯수=',arr5.length);
console.log('arr6=',arr6,'- 방의 갯수=',arr6.length);


/* 내장함수 중 Array 함수 : 
     *  매개변수가 0개 : length가 0인 배열생성
     *  매개변수가 1개일 때 : 매개변수만큼의 크기를 가지는 배열생성
     *  매개변수가 2개이상일때는 매개변수로 배열을 만든다.  */ 
