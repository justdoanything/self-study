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

const v4 = c1 || c2;        //
const v5 = '' || c2;        //

const v6 = !!(c1 && 0 && c2); //
const v7 = !!(c1 || c2);      //

```