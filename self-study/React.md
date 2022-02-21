React
===

## 함수형 프로그래밍 (ES5)
- 순수함수
  - 동일한 인자를 줬을 때 동일한 값을 주고 부수효과`(결과를 만드는 것 외에 외부 상태에 영향을 미치는 것)`가 없는 함수
    ```js
    // 동일한 인자에 동일한 결과를 만드는 함수
    function add(a, b){
        return a + b;
    }

    // 인자 값을 상태를 변경하지 않고 동일한 경과를 만드는 함수
    var obj = { val: 10};
    function add5(obj, b){
        return { val: obj.val + b };
    }
    ```
  - 순수 함수가 아닌 경우
    ```js
    // 동일한 인자를 줘도 외부 값에 따라 결과가 달라지는 경우
    var c = 10;
    function add2(a, b){
        return a + b + c;
    }
    
    // 부수효과(외부 상태에 영향을 끼치는)가 있는 경우
    function add3(a, b){
        c = b;
        return a + b;
    }

    // 결과 값이 없고 인자값의 상태를 직접 변경하는 경우
    var obj = { val: 10};
    function add4(obj, b){
        obj.val += b;
    }
    ```
- 일급함수
  - 함수를 값으로 다룰 수 있는 함수. 함수를 변수에 담을 수 있고 변수에 담은 함수가 값으로 다뤄질 수 있어서 인자로 사용될 수 있다.
    ```js
    var f1 = function(a) { return a * a; };
    var f2 = add;
    function f3(f) {
        return f();
    }
    f3(function() { return 10; });

    function add_maker(a){
        return function(b) {
            return a + b;
        }
    }
    var add10 = add_maker(10);
    var add15 = add_maker(15);
    add10(20);  // 30
    add15(20);  // 35

    function f4(f1, f2, f3){
        return f3(f1() + f2());
    }

    f4(
        function() { return 2; },
        function() { return 1; },
        function(a) { return a * a}
    );  // 9 
    ```
- Map, Filter
    ```js
    var users = [
        { id: 1, name: 'ID', age: 36},
        { id: 2, name: 'BJ', age: 32},
        { id: 3, name: 'JM', age: 32},
        { id: 4, name: 'PJ', age: 27},
        { id: 5, name: 'HA', age: 25},
        { id: 6, name: 'JE', age: 26},
        { id: 7, name: 'JI', age: 31},
        { id: 8, name: 'MP', age: 23}
    ]

    // 명령형 코드
    // 30세 이상인 users
    var temp_users = [];
    for(var i = 0; i < users.length; i++) {
        if(users[i].age >= 30) {
            temp_users.push(users[i]);
        }
    }
    // 30세 이상인 users의 name
    var names = [];
    for(var i = 0; i < temp_users.length; i++){
        names.push(temp_users[i].name);
    }

    // 30세 미만인 users
    var temp_users = [];
    for(var i = 0; i < users.length; i++) {
        if(users[i].age < 30) {
            temp_users.push(users[i]);
        }
    }

    // 30세 미만 users의 age
    var ages = [];
    for(var i = 0; i < temp_users.length; i++){
        ages.push(temp_users[i].age);
    }
    
    /***************************************************/
    
    // 함수형으로 변환
    function _filter(users, predi) {
        var new_list = [];
        for(var i=0; i < users.length; i++) {
            if(predi(users[i])){
                new_list.push(users[i]);
            }
        }
        return new_list;
    }
    _filter(users, function(user) { return user.age >= 30; });
    _filter(users, function(user) { return user.age < 30; });

    _filter([1, 2, 3, 4], function(num) { return num % 2; });

    function _map(list, mapper){
        var new_list = [];
        for(var i = 0; i < list.length; i++) {
            new_list.push(mapper(list[i]));
        }
        return new_list;
    }

    var over_30 = _filter(users, function(user) { return user.age >= 30 });
    _map(over_30, function(user) { return user.name });

    _map([1, 2, 3], function(num) { return num * 2; });

    // 통합
    _map(
        _filter(users, function(user) { return user.age >= 30; }),
        function(user) { return user.name; }
    );
    ```

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
- [Nomadcoder's ReactJS로 영화 웹 서비스 만들기](https://nomadcoders.co/react-for-beginners/lobby)
- [Inflearn - 자바스크립트로 알아보는 함수형 프로그래밍 (ES5)](https://www.inflearn.com/course/%ED%95%A8%EC%88%98%ED%98%95-%ED%94%84%EB%A1%9C%EA%B7%B8%EB%9E%98%EB%B0%8D/lecture/6776?tab=curriculum&volume=1.00&speed=1.5)
