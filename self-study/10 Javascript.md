# Javascript

## var
- var 변수의 scope = function
- var 키워드를 사용하지 않고 변수를 선언하면 전역 변수가 된다. 이를 방지하기 위해선 'use strict'를 명시해줄 수 있다.
```js
'use strict'

exam1() {
    i = 10;
}
exam2() [
    console.log(i);
]

exam1();
exam2();
```
- 반복문 등에서 var를 사용하면 반복문 밖에서도 var 변수를 사용할 수 있다. 이를 방지하기 위해선 function 으로 묶어줄 수 있다.
```js
(function() {
    for(var i=0; i<10; i++)
        console.log(i);
})();

console.log(i);
```
- var 변수 선언을 코드 최상단으로 땡겨진다. 하지만 초기값은 undefined로 설정된다. => hoisting
```js
var myVar = undefined;
console.log(myVar);
myVar = 1;
```

## const, let
- const, let 변수의 scope = block
- const, let도 hoisting이 되지만 var와의 차이점은 var는 undefined 가 할당되지만 const, let은 아무 값도 할당되지 않기 때문에 참조 에러가 발생한다.
- const : 재할당 불가능, 하지만 객체의 내부 속성값은 수정이 가능함. 이를 막기 위해선 immer package(기존 객체를 수정하지 않고 새로운 객체를 생성) 또는 Object.freeze, Object.preventExtensions, Object.seal, Object.freeze와 같은 Javascript 내장함수를 사용하면 수정을 막을 수 있다.
- let : 재할당 가능

## 8가지 기본타입
기본 타입 | 값
---|---
number | var a = 12;
bigint | var a = 1234567890n;
string | var a = 'abcd';
boolean | var a = true;
object | var a = { name: 'abc' };
symbol | var a = Symbol('abc');
undefined | var a = undefined;
null | var a = null;
- type 확인 : typeof a;
- null 체크 : Object.prototype.toString.call(null); // object Null
- Symbol은 key 중복 문제를 해결해준다.
  ```js
  const idSymbol = Symbol('id');
  const obj = {id: 123};
  obj[idSymbol] = 456;
  // { id : 123, [Symbol(id)] : 456}
  ```
- Boolean('') : false\
  Boolean('abc') : true

## 타입 변환
타입 변환 | 결과
---|---
String(123) | '123'
Number('123'), | 123
BigInt('123') | 123
Boolean('123') | true
Number.parseInt('123') | 123
Number.parseInt('123abc') | 123
Number.parseFloat('123.456abc') | 123.456
Number.parseInt('abc') | NaN `(Not a number)`
Number.isNaN(Number.parseInt('abc')) | true
Number.isFinite(Infinity) | false

## 큰 숫자 처리
- 큰 숫자를 처리할 때 bingint로 처리해야 한다.
```js
const a = 9007199254740995;
const b = 10;

Number.isSafeInteger(a); // false
Number.isSafeInteger(b); // true

const wrong = a - b; // 틀린값
const correct = 9007199254740995n - 10n; // 9007199254740985n
``` 

## Math
```js
Math.random();
Math.getRandomInt(0, 15); 
Math.max(30, 10, 55);
Math.pow(5, 3); // 5^3 = 125
Math.floor(10.55); // 10
```

## String
```js
const name = '', "", ``;
const text1 = 'name : ' + name + ', age : ' + age;
const text2 = `name : ${name}, age : ${age}`; 

text1 = '첫번째줄\n두번째줄';
text2 = `첫번째줄
두번째줄`;

// string은 불변 변수이다. (immutable)
string.replace();
string.replaceAll();
string.include('car', 10);
string.startWith('car');
string.endWith('car');
string.substr(0, 10);
string.indexOf('c');
string.slice(5, 10);  // 시작위치, 인덱스
string.split(' ');
arr.map(item => item.trim());
string.split(' ').join();
string.padStart(5, '0');
string.padEnd(5, '0');
string.match(정규식);

// tagged template literals
function taggedFunction(strings, ...expressions) {
    console.log({strings, expressions}); // { strings : ['a-', '-b-', ''], expressions [ 10, 20 ] }
    return 123;
}
const v1 = 10;
const v2 = 20;
const result = taggedFunction`a-${v1}-b-${v2}`;
console.log({ result });    // 123

taggedFunction`a-${v1}-b-${v2}-c`; // { strings : ['a-', '-b-', '-c'], expressions [ 10, 20 ] }
taggedFunction`a-${v1}-b-${v2}`; // { strings : ['a-', '-b-', ''], expressions [ 10, 20 ] }
taggedFunction`${v1}-b-${v2}`; // { strings : ['', '-b-', ''], expressions [ 10, 20 ] }  

```

## boolean
```js
const c1 = 123;
const c2 = 'abc';

const v1 = c1 && c2;        // 'abc'
const v2 = c1 && c2 && 0;   // 0
const v3 = c1 && 0 && c2;   // 0

const v4 = c1 || c2;        // 123
const v5 = '' || c2;        // abc

const v6 = !!(c1 && 0 && c2); // false
const v7 = !!(c1 || c2);      // true
```

## ??, ?.
- Nullish Coalescing : `??` 키워드로 null, undefined를 체크
- Optional Chaining : `?.` 키워드로 null, undefined를 체크
```js
// Nullish Coalescing
const name = person.name ?? 'unknown';  // person.name === null || person.name === undefined ? 'unknown' : person.name;

// 빈 문자열('')을 값으로 인정한다면 nullish coalescing을 사용
// 빈 문자열('')을 값으로 인정안하면 || 연산자 사용
const person = { name : '' };
const name = person.name ?? 'unknown'; // ''
const name = person.name || 'unknown'; // 'unknown'

// Optional Chaining
const name = person?.name;  // person === null || person === undefined ? undefined : person.name;
const name = person.getName?.();
const name = person.friend?.[0];
const name = person?.friends?.[0]?.mother?.name ?? 'defalt name';

```

## Object
```js
//const obj = new Object({
const obj = { 
    age: 21,
    name: 'yong'
}

// Object 관련 주요 함수들
// 변수명에 서브함수를 사용하지 않고 Object 객체를 활용한다.
Object.keys(obj);       // [ 'age', 'name' ]
Object.values(obj);     // [ 21, 'yong' ]
Object.entries(obj);    // [ [ 'age', 21 ], [ 'name', 'yong' ] ]

for(const [key, value] of Object.entries(obj)){
    console.log(key, value);
}

// 객체 값 추가 / 삭제
obj.city = 'seoul';
obj.age = 30;
delete obj.city;


// Array Object
const array = [1, 2, 3];
Object.values(array); // [ 1, 2, 3 ]

array.map(item => item + 1);    // [ 2, 3, 4 ] 
array.filter(item => item >= 2);    // [ 2, 3 ]
array.reduce((acc, item) => acc + item, 0); // 6, 0으로 시작해서 연산을 누적으로 해준다.
array.some(item => item === 2);     // true, 하나라도 만족하면 true
array.every(item => item === 2);    // false, 모두 만족해야 true
array.includes(2);  // true, 2를 포함하는지
array.find(item => item % 2 === 1); // 1, 조건에 부합하는 첫번째 값
array.findIndex(item => item % 2 === 1); // 0, 조건에 부합하는 첫번째 값의 인덱스

// 반복문
array.forEach(item => console.log(item));
for(const item of array){
    console.log(item);
}

// 배열의 값 추가 / 삭제
array.push(4);
array.pop();

array.splice(1, 1);             // [ 1, 3 ], 1번 인덱스에서 1개 삭제
array.splice(1, 0, 10, 20, 30); // [ 1, 10, 20, 30, 3 ],  1번 인덱스에서 0개를 삭제하고 뒤에 있는 아이템 추가
array.splice(1, 3, 40, 50);     // [ 1, 40, 50, 3 ]

array.sort();   // 오름차순, [ 1, 3, 40, 50 ]
array.sort((a, b) => ( a % 10) - (b % 10)); // [ 40, 50, 1, 3 ], 함수에서 음수가 나오면 순서 변경 안함, 양수면 순서 변경
```

## 객체 속성을 간결하게 다루기 위한 함수
- shorthand property names (단축 속성명): 객체 리터널 코드를 간편하게 작성
- computed property names (계산된 속성명): 객체의 속성명을 동적으로 결정
- spread operator : ... 키워드로 객체를 복사해서 사용할 수 있다.
```js

/*
 * shorthand property names
 */
const obj = {
    age: 21,
    name,
    // 단축 속성명 없이 적용할 때, 
    // getName : function getName() { return this.name; }
    getName() { return this.name; }
};

// 단축속성명 없이 적용할 때, 
// function person(age, name) { return { age: age, name: name }};
function person(age, name) { return { age, name }};


/*
 * computed property names
 */
// 계산된 속성명 적용 안한 코드
function person(key, value) {
    const obj = {}};
    obj[key] = value;
    return obj;
}
function person(key, value) { 
    return { [key] : value };
}

/*
 * spread operator
 */
const numbers = [1, 3, 7, 9];
Max.max(...numbers);

const array2 = [...array];
const obj3 = {...obj1, ...obj2};
const date = new Date(...[2018, 11, 24]);
const obj4 = {...obj1, name: 'yong2'};
```


