"use strict";
let memberArray = ['egoing','graphittie','leezhce'];
var copyArray = [...memberArray];  // 객체의 깊은 복사
var copyArray = Array.from(memberArray); // Array 메소드 이용 복사
copyArray[0] = '홍길동';
copyArray[1] = '바꾼자';
copyArray[2] = '카피함';

console.group('memberArray for-loop');
for(let i=0; i<memberArray.length; i++){
    console.log(i+'번째 : ',memberArray[i]);
}
console.groupEnd('memberArray for-loop');

console.group('copyArray for-loop');
copyArray.forEach(function(data,idx){
    console.log(idx,'번째 : ',copyArray[idx]);
});
console.groupEnd('copyArray for-loop');
////////////////////////////////////////////////////

var memberObject = {
    name : '홍길동',
    age : 20,
};
// var copyObject = {...memberObject}; // 객체의 깊은 복사 
// var copyObject = Object.assign({},memberObject);  // Object 메소드이용 복사
var copyObject = Object.assign({id:1},memberObject);

copyObject['name'] = '바꾼자';

console.group('memberObject for-loop');
for(var key in memberObject){
    console.log(key,' :',memberObject[key]);
}
console.groupEnd('memberObject for-loop');

console.group('copyObject for-loop');
for(var key in copyObject){
    console.log(key,' :',copyObject[key]);
}
console.groupEnd('copyObject for-loop');

