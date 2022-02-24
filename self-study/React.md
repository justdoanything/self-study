React
===

<details>

  <summary>click to collapse</summary>

  this one starts expanded because of the "open"

</details>

## 함수형 프로그래밍 (ES5)
  - Move to https://github.com/justdoanything/self-study/blob/main/self-study/React-ES5.html

<details>
 <summary>go</summary>

```js
function _go(arg){
    var fns = _rest(arguments);
    return _pipe.apply(null, fns)(arg);
}
_go(1, 
    function(a) { return a + 1; },
    function(a) { return a * 2; },
    function(a) { return a * a; },
    console.log
);
_go(users,
    function(users) {
        return _filter(users, function(user) {
            return user.age >= 30;
        });
    },
    function(users) {
        return _map(users, _getr('name'));
    },
    console.log
);
var _mapr = _curryr(_mapr),
_filterr = _curryr(_filter);
_go(users,
    _filterr(function(user) { return user.age >= 30;}),
    _mapr(_get('name')),
    console.log
);
_go(users,
    _filterr(user => user.age >= 30),
    _mapr(_get('name')),
    console.log
);

// 화살표 함수
var a = function(user) { return user.age >= 30; };
var a = user => user.age >= 30;
var add = function(a, b) {return a + b; };
var add = (a, b) => {
    a + b
};
// 함수가 아니라 객체로 반환하고 싶으면  ( ) 으로 감싸라    
var add = (a, b) => (
    { val: a + b }
);
```
</details>

<defails>
  <summary>다형성</summary>

```js
// 6. _each의 외부 다형성 높이기
// 1. _each에 null 넣어도 에러 안나게
_each(null, console.log);
console.log( _filter(null, function(v) { return v; }) );

// 2. _keys 만들기
// 3. _keys에서도 _is_object인지 검사하여 null 에러 안나게
console.log( _keys({ name: 'ID', age: 33 }) );
console.log( _keys([1, 2, 3, 4]) );
console.log( _keys(10) );
console.log( _keys(null) );

// 4. _each 외부 다형성 높이기
_each({
  13: 'ID',
  19: 'HD',
  29: 'YD'
}, function(name) {
  console.log(name);
});
console.log( _map({
  13: 'ID',
  19: 'HD',
  29: 'YD'
}, function(name) {
  return name.toLowerCase();
}) );
_go({
    1: users[0],
    3: users[2],
    5: users[4]
  },
  _map(function(user) {
    return user.name.toLowerCase();
  }),
  console.log);
```
</details>



## 기존 방식과 React 방식의 차이점
- 기존 방식 : JS에서 HTML에 있는 요소를 가져오고 → JS에서 값을 변경하고 → 다시 HTML을 업데이트 해주는 방식 (시작이 HTML)
- React 방식 : JS에서 HTML 요소를 선택해서 바로 업데이트 해주는 방식 (시작이 JS)
- babel : jsx를 html 코드로 변환
#### jsx
```html
<!-- React -->
<script src="https://unpkg.com/react@17.0.2/umd/react.production.min.js"></script>
<script src="https://unpkg.com/react-dom@17.0.2/umd/react-dom.production.min.js"></script>
<script src="https://unpkg.com/@bable/standalone/babel.min.js"></script>
<script type="text/babel">
    const sample = (
        <h3 id="title" onMouseEnter={()=>console.log("mouse enter")}>
            Hello! World!
        </h3>
    )
</script>
```
#### html
```html
<!-- babel converts jsx to html -->
<script>
    const sample = React.createElement(
        "h3",
        {
            id:"title"
            , onMouseEnter: () => console.log("mouse enter")
        },
        "Hello! World"
    );
</script>
```
#### Container
```html
<!-- Render Container Type -->
<script>
    const Sample = () => (
        <h3 id="title" onMouseEnter={()=>console.log("mouse enter")}>
            Hello! World!
        </h3>
    );

    /*
    function Sample() {
        return (
            ...
        );
    }
    */

    const Container = <div><Sample /></div>;
    ReactDOM.render(Container, root);
</script>
```
#### Add Event Listner
```html
<!-- Event Listener-->
<script>
    let count = 0;
    function countUp(){
        count = count + 1;
        ReactDOM.render(<Container />, root);   // 함수가 호출됐을 때 다시 render
    }
    const Container = () => (
        <div>
            <h3> Count : {count} </h3>
            <button onClick={countUp}>Click Button</button>
        </div>
    );
    ReactDOM.render(<Container />, root);   // 페이지가 Load될 때 render

    /* more fency code */
    function render() {
        ReactDOM.render(<Container />, root);
    }
    function countUp(){
        count = count + 1;
        render();
    }
    const Container = () => (
        <div>
            <h3> Count : {count} </h3>
            <button onClick={countUp}>Click Button</button>
        </div>
    );
    render();
</script>
```
#### Re-rendering (useState)
```html
<!-- useState에 modify 함수를 사용하면 해당 Component는 자동으로 re-rendering 된다. -->
<script>
    function Sample() {
        const [count, countUp] = React.useState(0);
        const onClick = () => {
            // count를 count + 1로 바꿔주고 re-render까지 자동으로 해준다.
            countUp(count + 1);  
            countUp((current) => current + 1);
        };
        return (
            <div>
                <h3>Count : {count}</h3>
                <button onClick={onClick}>Click</button>
            </div>
        );
    }
    ReactDOM.render(<Sample />, root); 
</script>
```
#### Prop
``` html
<script>
    //function Btn({text, onClickEvent, fontSize = 12}) {
    function Btn(props) {
        return (
            <button
                style={{
                    backgroudColor: "red",
                    color: "white",
                    padding: "10px 20px",
                    border: 0,
                    borderRadius: 10
                }}
                onClick = {props.onClickEvent}
            >
            {props.text}
            </button>
        );
    }
    // Checking PropType
    Btn.propTypes = {
        text : PropTypes.string,
        fontSize: PropTypes.number.isRequired
    }

    function App(){
        const [value, setValue] = React.useState("Save Changes");
        const changeValue = () => setValue("Revert Changes");
        return (
            <div>
                <Btn text = {value} onClickEvent = {changeValue} fontSize = {10}/>
                <Btn text = {value} onClickEvent = {changeValue} fontSize = {12} />
            </div>
        );
    }

</script>
```
---
# useState & useEffect
- useState[state, setState]
  - document : https://ko.reactjs.org/docs/hooks-reference.html#usestate
  - `function useState<S>(initialState: S | (() => S)): [S, Dispatch<SetStateAction<S>>] import useState`
  - `state`를 변경하기 위해선 `setState`를 호출해야하며 `setState`가 호출될 때마다 해당하는 component는 re-rendering 된다.
  - component가 전체 re-rendering 되기 때문에 필요에 따라 `useEffect`를 사용해준다.
- useEffect
  - document : https://ko.reactjs.org/docs/hooks-reference.html#useeffect
  - `function useEffect(effect: EffectCallback, deps?: DependencyList): void import useEffect`
  - object가 변경됐을 때만 function을 수행한다.
  - 예시
    ```js
    useEffect( () => {
        if(keyword.length > 5)
            console.log("keyword is changes");
    }, [keyword]);
    ```
---
# Cleanup
- Component가 삭제됐을 때 실행되는 함수
    ```js
    function Hello() {
        
        // Way 1 : better way
        useEffect(()=>{
            console.log("created :)");
            return () => console.log("destroyed :(");   // Cleanup function
        }, []);

        // Way 2
        function effectCreated() {
            console.log("created :)");
            return effectDestroyed;     // Cleanup function
        }
        function effectDestroyed() {
            console.log("destroyed :(");
        }
        useEffect(effectCreated, []);

        return <h1>Hello</h1>;
    }
    ```
---
# Array of useState
- Array에 Array를 더하는 방법
  ```js
  const arraySample = [1,2,3];
  const useState[value, setValue] = useState([]);
  setValue((currentArray) => [value, ...currentArray]);
  ```
---

# fetch
```js
fetch("https://url", {
    method: 'POST',
    headers: {
        'Content-Type': 'application/json',
        'Access-Control-Allow-Origin': '*'
    },
    body: JSON.stringify({
        'fromEmail': 'yongwoo.leee@lgcns.com',
        'toEmail': ['yongwoo.leee@lgcns.com'],
        'mailSubject': 'Test Mailing - Beaver',
        'mailContent': 'Test Mailing - Beaver'
    })
}).then((response) => {
    console.log(response.status);
    if (response.status === 200) {
        alert("메일 전송 성공");
    } else {
        alert("메일 전송 실패 : " + response.status);
    }
        
    response.json().then((json) => {
        console.log(json);
    });
});     
```





### Practice Code
- [Repository : self-study/self-study/do-react](https://github.com/justdoanything/self-study/tree/main/self-study/do-react)

---
### Reference
- [Inflearn - 자바스크립트로 알아보는 함수형 프로그래밍 (ES5)](https://www.inflearn.com/course/%ED%95%A8%EC%88%98%ED%98%95-%ED%94%84%EB%A1%9C%EA%B7%B8%EB%9E%98%EB%B0%8D/lecture/6776?tab=curriculum&volume=1.00&speed=1.5)
- [Nomadcoder's ReactJS로 영화 웹 서비스 만들기](https://nomadcoders.co/react-for-beginners/lobby)
