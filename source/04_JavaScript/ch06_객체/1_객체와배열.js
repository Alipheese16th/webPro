/*
-- 자바에서의 객체 --
class Person{
    private String name;
    private int age;
    Person(String name, int age){
        this.name = name;
        this.age = age;
    }
}
Person person = new Person("홍길동",20);
*/
const person = {'name':'홍길동','age':20};

const i = 10;
// i = 11;   const는 상수라 변경불가

person.name = '신길동';  // person 객체를 바꾸는게 아니라 안의 내용을 바꾸는것이기 때문에 const로서 문제가 없다. person 객체는 주소를 참조하는것. 그 주소만 바뀌지않으면 된다.

const arr = ['홍길동',20];
// const arr = {0:'홍길동',1:20};

console.log('arr[0] = '+arr[0]+'/ arr[1] = '+arr[1]);
console.log('person[\'name\'] = ',person['name'],"/ person['age'] =",person['age']);
console.log('person[\'name\'] = ',person.name,"/ person['age'] =",person.age);


