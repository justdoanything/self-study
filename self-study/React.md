React
===
## 기존 방식과 React 방식의 차이점
- 기존 방식 : JS에서 HTML에 있는 요소를 가져오고 → JS에서 값을 변경하고 → 다시 HTML을 업데이트 해주는 방식 (시작이 HTML)
- React 방식 : JS에서 HTML 요소를 선택해서 바로 업데이트 해주는 방식 (시작이 JS)
- babel : jsx를 html 코드로 변환
####jsx
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
####html
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
####Container
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
####Re-rendering (useState)
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


---
### Practice Code
[Repositroy : self-study/self-study/React_Practice.html](https://github.com/justdoanything/self-study/blob/main/self-study/React_Practice.html)


---
### Reference
[Nomadcoder's ReactJS로 영화 웹 서비스 만들기](https://nomadcoders.co/react-for-beginners/lobby)