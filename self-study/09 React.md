React
===
## 함수형 프로그래밍 (ES5)
  - Move to https://github.com/justdoanything/self-study/blob/main/self-study/React-ES5.html

## 리액트를 다루는 기술
  - ## Why React
    - React는 Virual DOM을 활용하기 때문에 DOM Update가 빠르다.
  - ## var, let, const
    - var : 함수 영역에서 동작
    - let : 블록 단위에서 동작
    - const : 블록 단위에서 동작하며 변하지 않는 상수
    ```js
    function my(){
        var a = 1;
        let b = 1;
        if(true){
            var a = 2;
            let b = 2;
            console.log(a); // 2
            console.log(b); // 2
        }
        console.log(a); // 2
        console.log(b); // a
    }
    ```
  - ## ES6의 화살표 함수
    - 일반 함수는 자신이 종속된 객체를 this로 가리키며 화살표 함수는 자신이 종속된 인스턴스를 가리킨다.
    ```js
    function BlackDog(){
        this.name = '흰둥이';
        return {
            name: '검둥이',
            bark: function() {
                console.log(this.name + ": 멍멍");
            }
        }
    }
    const blackDog = new BlackDog();
    blackDog.bark();    // 검둥이: 멍멍

    function WhiteDog(){
        this.name = '흰둥이';
        return {
            name: '검둥이';
            bark: () => {
                console.log(this.name + ": 멍멍");
            }
        }
    }
    const whiteDog = new WhiteDog();
    whiteDog.bark();    // 흰둥이: 멍멍
    ```
  - ## defaultProps
    ```js
    const MyComponent = props => {
        return <div>내 이름은 {props.name} 입니다.</div>;
    };
    MyComponent.defaultProps = {
        name: '기본'
    }
    export default MyComponent.
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
- 리액트를 다루는 기술(개정판) / 길민준 / 길벗
- [Nomadcoder's ReactJS로 영화 웹 서비스 만들기](https://nomadcoders.co/react-for-beginners/lobby)
