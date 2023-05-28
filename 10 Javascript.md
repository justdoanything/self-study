# Javascript

###### Good to note with below articles
- https://github.com/justdoanything/self-study/tree/main/WIS/ES5
- https://github.com/justdoanything/self-study/blob/main/WIS/10%20Javascript.md
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

## 함수 정의 방식
```javascript
// 기본값 설정
function print(a = 1) {
    console.log( { a } );
}
function print(a = printDefault()){
    console.log( { a } );
}
// 필수값 설정
function required() {
    throw new Error("There are no required parameters");
}
function print(a = required()){
    console.log( { a } );
}

// Rest Parameter
function print(a, ...rest) {
    console.log( { a, rest } );
}
print(1, 2, 3); // a = 1, rest = [2, 3]

// Named Parameter
function getValue( { numbers, greaterThan = 0, lessThan = Number.MAX_VALUE }){
    return numbers.filter(item => greaterThan < item && item < lessThan);
}
console.log(getValue( { numbers, greaterThan: 5, lessThan: 25 } ));

// Arrow Function
const add = (a, b) => a + b;
const add = a => a + 5;
const add = (a, b) => ({ result: a + b});
const add = (a, b) => {
    return a + b;
}
```

## Arrow Function vs Normal Function
  - this
    - Arrow Function : 함수를 선언할 때 this에 바인딩할 객체가 정적으로 결정된다. 화살표 함수의 this는 언제나 상위 scope의 this를 가르킨다. (Lexical this) call, apply, bind 메소드를 사용해서 this를 변경할 수 없다.
    - Normal Function : 모든 함수는 실행될 때마다 함수 내부에 this라는 객체가 추가된다. 함수를 선언할 때 this에 바인딩할 객체가 정적으로 결정되는 것이 아니고 함수를 호출할 때 함수가 어떻게 호출되었는지에 따라 this에 바인딩할 객체가 동적으로 결정된다. 일반 함수에서 this가 바인딩되는 상황은 아래와 같다.
      - 함수 실행시에는 전역(window) 객체를 가리킨다.
      - 메소드 실행시에는 메소드를 소유하고 있는 객체를 가리킨다.
      - 생성자 실행시에는 새롭게 만들어진 객체를 가리킨다.
	```js
	function sample() {
		this.name = "hi";
		return {
			name: "bye",
			speak: function() {
				console.log(this.name);
			}
		}
	}

	function arrowFunction() {
		this.name = "hi";
		return {
			name: "bye",
			speak: () => {
				console.log(this.name);
			}
		}
	}

	sample.speak(); // bye
	arrowFunction.speak() // hi
	```
	- 생성자 함수로 사용 가능 여부
	  - 일반 함수는 생성자 함수로 사용할 수 있다.
	  - 화살표 함수는 prototype property를 갖고 있지 않기 때문에 생성자 함수로 사용할 수 없다. 
	```js
	function sample() {
		this.name = 'yongwoo';
	}

	const arrowFunction = () => {
		this.name = 'yongwoo';
	}

	const sample = new sample();
	const arrowFunction = new arrowFunction(); // 에러 발생
	```
  - Argument 사용 가능 여부
	  - 일반 함수에서는 함수가 실행될 때 암묵적으로 arguments 변수가 전달되어 사용할 수 있다.
	  - 화살표 함수에서는 arguments가 변수가 전달되지 않는다.
    ```js
    function sample() {
        console.log(arguments); // Arguments(3) [1, 2, 3]
    }

    cont arrowFunction = () => {
        console.log(arguments); // not defined 에러 발생
    }
    ```
	
      

## Promise
```js
callApi1()
    .then(data => {
        console.log(data);
        return callApi2();
    })
    .then(data => {
        console.log(data);
    });

const pendingPromise = new Promise((resolve, reject) => {});
const rejectedPromise = Promise.reject('error message');
const fulfilledPromise = Promise.resolve(param);

// pending : 대기중
// settled
//  fulfilled : 성공, resolve 호출 후 
//  rejected : 실패, reject 호출 후 

// then은 항상 Promise를 반환함
// 첫번째 인자는 fullfilled 일 때 빠지는 함수
// 두번째 인자는 rejected 일 때 빠지는 함수

Promise.resolve(10)
    .then(data => {
        return data + 1;    // Promise.resolve(11) 반환
    })
    .then(data => {
        throw new Error('error');   // Promise.reject('error') 반환
    })
    .then(null, error => {
        console.log(error); // Promise.resolve() 반환
    })
    .then(data => {
        console.log(data); // 위에서 Promise.resolve()를 반환했기 때문에 fulfilled 상태가 되고 이 행은 출력된다.
    })

//4, 5만 출력 됨
Promise.reject('err')
    .then(()=>console.log('1'))
    .then(()=>console.log('2'))
    .then(
        () => console.log('3'),
        () => console.log('4'),
    )
    .then(
        () => console.log('5'),
        () => console.log('6'),
    )

// then, catch, finally
// finally에는 데이터가 안넘어오고 이 전의 Promise 객체를 그대로 반환한다.
Promise.resolve(10)
    .then(data => {
        console.log("In resolve");
        return data + 10;
    })
    .catch(error => {
        console.log("In catch");
        return data - 10;
    })
    .finally(() => {
        console.log("In finally");
    })
    .then(data => {
        console.log(data); // 20
    })

Promise.reject(10)
    .then(data => {
        console.log("In resolve");
        return data + 10;
    })
    .catch(data => { 
        console.log("In catch");
        return data - 10;
    })
    .finally(() => {
        console.log("In finally");
    })
    .then(data => {
        console.log(data); // 0
    })

Promise.reject(10)
    .then(data => {
        console.log("In resolve");
        return data + 10;
    }, data => { 
        console.log("In catch");
        return data - 10;
    })
    .finally(() => {
        console.log("In finally");
    })
    .then(data => {
        console.log(data); // 0
    })

function requestData() {
    return fetch()
        .catch(error => {
            ...
        })
        .finally(() => {
            ...
        });
}
requestData().then(data => console.log(data));

// then을 병렬로 처리하기
requestData1().then(data => console.log(data));
requestData2().then(data => console.log(data));
Promise.all([requestData1(), requestData2()]).then(([data1, data2]) => {
    console.log(data1, data2);
});

// 가장 먼저 settled 상태가 된 Promise 반환
Promise.race([
    requestData(),
    new Promise((_, reject) => setTimeout(reject, 3000))
])
    .then(data => console.log('fulfilled', data))
    .catch(error => console.log('rejected'));
```

## async-await
```js
async function getData() {
    new Promise.resolve(10);
}

function getData() {
    return asyncFunc1()
        .then(data1 => Promise.all([data1, asyncFunc2(data2)]))
        ,then(([data1, data2]) => {
            return asyncFunc3(data1, data2);
        });
}
// 직렬 처리
async function getData() {
    const data1 = await asyncFunc1();
    const data2 = await asyncFUnc2(data);
    return asyncFunc3(data, data2);
}
//병렬 처리
async function getData() {
    const p1 = asyncFunc();
    const p2 = asyncFunc2();
    const data1 = await p1;
    const data2 = await p2;
    console.log( { data1, data2 });
}
async function getData() {
    const [data1, data2] = await Promise.all([asyncFunc1(), asyncFunc2()]);
}
// 예외 처리
async function getData() {
    try {
        await doAsync();
        return doSync();
    }catch(error) {
        // doAsync, doSync 함수에 대한 예외가 모두 여기에서 처리됨
        return Promise.reject(error);
    }
}
```

## Prototype
- 함수의 [[Prototype]]과 prototype 속성은 다르다. [[Prototype]]은 모든 객체가 갖고 있는 값이고 prototype은 해당 함수가 갖고 있는 특별한 속성이다.
  ```js
  const person = new Person();
  Object.getPrototypeOf(Person) !== Person.prototype;
  
  Object.getPrototypeOf(person) === Person.prototype;
  Object.getPrototypeOf(Person.prototype) === Object.prototype
  Object.getPrototypeOf(Person) === Function.prototype
  
  Object.getPrototypeOf(Object) === Function.prototype
  Object.getPrototypeOf(Function.prototype) === Object.prototype
  Object.getPrototypeOf(Object.prototype) === null
  ```
- 예제코드
  ```js
  const person = {
      name: 'mike'
  }
  const programmer = {
      language: 'javascript'
  }
  const prototype = person.getPrototypeOf(person); // 안전한 방법
  console.log(typeof prototype);  // object
  console.log(person.__proto__ === prototype);

  Object.setPrototypeOf(programmer, person);
  console.log(Object.getPrototype(programmer) === person);    // true
  console.log(programmer.name); // mike, 속성이 없으면 prototype에서 찾는다.

  for(const prop in programmer) {
      console.log(prop);  // name, language
      if(programmer.hasOwnProperty(prop)){
      // Object.keys(programmer)
          console.log(prop);  // language
      }
  }

  // 생성자 함수에서 함수를 정의하면 객체가 생성될때마다 함수가 생성되기 때문에 prototype를 사용하면 1번만 생성해서 사용할 수 있다.
  function Person(name) {
    this.name = name;
    this._salary = 0;
  }
  Person.prototype = {
    setSalary(salary) = {
      this._salary = Math.max(0, Math.min(1000, salary));
    },
    getSalary() {
      return this._salary;
    }
  }
  ```

## Class
```js
class Person {
  constructor(name) {
    this._name = name;
  }
  // getter 만 선언하면 read-only 처럼 사용할 수 있다.
  get name() {
    return this._name;
  }

  sayHello() {
    console.log("hello! " + _name);
  }
}

// 상속
class Programmer extends Person {
  constructor(name, language){
    super(name);  // 반드시 호출해야함
    this.language = language;
  }

  // 화살표 함수로 정의하면 객체로 인식한다.
  printName = () => {
    console.log(this.name);
  }

  printNameFunc = function() {
    console.log(this.name);
  }

  // 부모 클래스에서 클래스 필드로 정의하면 자식에서 super로 호출할 수 없다.
  // super는 prototype 기반으로 동작하는데 클래스 필드로 정의하면 객체에 추가되기 때문이다.
  // this 키워드를 사용해서 객체에서 먼저 찾고 없으면 prototype에서 찾기 때문에 부모 함수가 호출된다.
  // 이런 혼동을 방지하기 위해서 자식에서도 클래스 필드로 정의해야 한다. sayHello = () => { ... } 
  sayHello() {
    super.sayHello(); // 에러
    this.sayHello(); // 부모의 함수가 호출됨.
  }
}

setTimeout(programmer.printName, 100);    // 정상 출력
setTimeout(programmer.printNameFunc, 100);  // undefined 출력
```

## ESM
```js
// CommonJS 형식 (과거)
exports.sayHello = function(name) { consoel.log(name); }
//--------
const moduleB = require('./b.js');
moduleB.sayHello('yongwoo');

// ESM 형식
export function sayHello(name) { consoel.log(name); }
export function sayHelloDefault(name) { consoel.log("default : " + name); }
//--------
import sayHelloDefault, {sayHello as ailasSayHello} from './b.js';

sayHelloDefault('yongwoo');
ailasSayHello('yongwoo');

// 1개의 js 파일(main)에서 여러개의 js(sub)를 import 받고 다른 파일에선 main js만 import 받아서 sub js를 사용할 수 있다.

// import 비동기 처리
// Promise를 반환한다.
if(name === 'yongwoo'){
  import('./b.js').then(({b}) => [
    console.log(b);
  ]);
}else {
  // import는 Promise를 사용하기 때문에 async-await 사용 가능
  const { c } = await import('./c.js');
  console.log(c);
}

// 순환참조 문제
// 하나의 파일에서 모든 import를 받고 실행 순서를 정해주면 된다.
// a가 b에 종속적일서 b를 반드시 먼저 호출해야하는 경우

// util.js
export * from './b.js';
export * from './a.js';

// 다른 파일에선 util.js를 import 받아서 사용해야 한다.
```

## webpack
- 모듈 방식으로 작성된 코드를 배포하기 좋은 형태로 변환해준다.
- ESM 방식으로 작성된 코드를 변환해주기 때문에 ESM을 지원하지 않는 오래된 브라우저에서도 실행할 수 있게 해준다.

## babel
- 코드를 압축하거나 자바스크립트 표준 문법으로 변환해준다.
- 오래된 브라우저에서도 실행할 수 있는 코드가 된다.

## polyfill
- babel과 마찬가지로 오래된 브라우저를 지원하는 용도로 사용된다.
- babel은 컴파일 타임에서 실행, polyfill은 런타임에서 실행된다.
- babel은 사용자 환경을 모른채 무조건 컴파일 타임에서 변환해주고 polyfill은 런타임에 사용자 환경을 보고 필요할 때만 기능을 주입한다.

# Reference
- https://www.inflearn.com/course/%EC%8B%A4%EC%A0%84-%EC%9E%90%EB%B0%94%EC%8A%A4%ED%81%AC%EB%A6%BD%ED%8A%B8
- https://hhyemi.github.io/2021/06/09/arrow.html