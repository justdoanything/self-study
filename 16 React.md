<!-- TOC -->
* [React](#react)
  * [React의 시작지점 파악해보기](#react의-시작지점-파악해보기)
  * [React를 사용하는 이유](#react를-사용하는-이유)
  * [React 기본 문법과 개념](#react-기본-문법과-개념)
    * [jsx](#jsx)
    * [html](#html)
    * [Container](#container)
    * [Add Event Listener](#add-event-listener)
    * [Re-rendering (useState)](#re-rendering--usestate-)
    * [Prop](#prop)
    * [Cleanup](#cleanup)
    * [useState & useEffect & useMemo & useCallback](#usestate--useeffect--usememo--usecallback)
  * [Reference](#reference)
* [Nextjs](#nextjs)
  * [Next.js 의 개념과 주요 기능](#nextjs-의-개념과-주요-기능)
  * [요약해보기](#요약해보기)
  * [Nextjs 기본 문법과 개념](#nextjs-기본-문법과-개념)
    * [getInitialProps](#getinitialprops)
    * [getStaticProps](#getstaticprops)
    * [getStaticPaths](#getstaticpaths)
    * [getServerSideProps](#getserversideprops)
    * [Server Side Life Cycle](#server-side-life-cycle)
    * [SWR (Stale-While-Revalidate)](#swr--stale-while-revalidate-)
    * [Context Object](#context-object)
    * [Shallow Route](#shallow-route)
    * [CssBaseline](#cssbaseline)
    * [react-next-keep-alive](#react-next-keep-alive)
    * [HOC (Higher Order Component)](#hoc--higher-order-component-)
  * [Reference](#reference-1)
* [React Admin](#react-admin)
  * [Fetch Data](#fetch-data)
  * [Form 이동과 제어](#form-이동과-제어)
  * [Form 기본기능](#form-기본기능)
  * [Reference](#reference-2)
* [상태관리](#상태관리)
  * [Recoil](#recoil)
  * [Reference](#reference-3)
<!-- TOC -->

--- 

# React

## React의 시작지점 파악해보기
- 프로젝트를 처음 install 후 무작정 실행해보면 아래 파일들이 실행된다.
  - 1️⃣ public/index.html
    - There is `<div id="root"></div>`
  - 2️⃣ src/index.js
    - `import './index.css';`
    - Rendering `<App />`
  - 3️⃣ src/App.js
    - `import './App.css';`
    - return `<div>` code
    - it should be shown

- 시작 파일이 index.html 이라는건 어디에 명시되있을까? 이 파일 이름을 바꾸고 싶으면 어떻게 해야할까?
  - index.html 파일 이름을 변경하면 public/index.html 파일을 찾을 수 없다는 에러 발생
  > Error: Child compilation failed: Module not found: Error: Can't resolve 'D:\Dev\Yongwoo\self-study\self-study\do-react\public\index.html' in 'D:\Dev\Yongwoo\self-study\self-study\do-react' ModuleNotFoundError: Module not found: Error: Can't resolve 'D:\Dev\Yongwoo\self-study\self-study\do-react\public\index.html' in 'D:\Dev\Yongwoo\self-study\self-study\do-react' at D:\Dev\Yongwoo\self-study\self-study\do-react\node_modules\webpack\lib\Compilation.js:2011:28 at D:\Dev\Yongwoo\self-study\self-study\do-react\node_modules\webpack\lib\NormalModuleFactory.js:795:13 at eval (eval at create (D:\Dev\Yongwoo\self-study\self-study\do-react\node_modules\tapable\lib\HookCodeFactory.js:33:10), <anonymous>:10:1) at D:\Dev\Yongwoo\self-study\self-study\do-react\node_modules\webpack\lib\NormalModuleFactory.js:275:22 at eval (eval at create (D:\Dev\Yongwoo\self-study\self-study\do-react\node_modules\tapable\lib\HookCodeFactory.js:33:10), <anonymous>:9:1) at D:\Dev\Yongwoo\self-study\self-study\do-react\node_modules\webpack\lib\NormalModuleFactory.js:431:22 at D:\Dev\Yongwoo\self-study\self-study\do-react\node_modules\webpack\lib\NormalModuleFactory.js:124:11 at D:\Dev\Yongwoo\self-study\self-study\do-react\node_modules\webpack\lib\NormalModuleFactory.js:667:25 at D:\Dev\Yongwoo\self-study\self-study\do-react\node_modules\webpack\lib\NormalModuleFactory.js:852:8 at D:\Dev\Yongwoo\self-study\self-study\do-react\node_modules\webpack\lib\NormalModuleFactory.js:972:5
  - node_modules\webpack\lib\NormalModuleFactory.js 에 대한 정보 검색
    - https://webpack.js.org/api/normalmodulefactory-hooks/
    - Error line을 추적하다보면 `if (err) return callback(err);` 함수로 들어가서 에러는 반환하고 있음.
    - node_modules\tapable\lib\HookCodeFactory.js
      - `create(options)` 함수에 `switch (this.options.type) { case "async"` 로 연결
      - `this.hooks.factorize.callAsync(resolveData, (err, module) => {...}`
    - node_modules\webpack\lib\Compilation.js
      - `_factorizeModule(` → `factory.create(` → `if (err) { const notFoundError = new ModuleNotFoundError( return callback(notFoundError, factoryResult ? result : undefined);`
  - #### NormalModuleFactory Hooks
    >_The NormalModuleFactory module is used by the Compiler to generate modules. Starting with entry points, it resolves each request, parses the content to find further requests, and keeps crawling through files by resolving all and parsing any new files. At last stage, each dependency becomes a Module instance._\
    \
    _factorize AsyncSeriesBailHook\
    Called before initiating resolve. It should return undefined to proceed.\
    Callback Parameters: resolveData
  - #### Compiler Hooks
    >_The Compiler module is the main engine that creates a compilation instance with all the options passed through the CLI or Node API. It extends the Tapable class in order to register and call plugins. Most user-facing plugins are first registered on the Compiler. When developing a plugin for webpack, you might want to know where each hook is called. To learn this, search for hooks.<hook name>.call across the webpack source._
  - #### 최종결론
    > NormalModuleFactory.js 에서 context 기준으로 Resource를 가져오고 factory를 create 하는데 index.html 이라는 파일이 정해진 경로에 없어서 callback 함수에서 notFoundError 에러를 반환하면서 해당 에러가 발생했다. context에 public/index.html은 고정적으로 들어가있는 것으로 보이며 가시적으로 어떤 index.html 이라는 값을 확인할 수 없었고 변경도 어려운 것으로 보인다.

## React를 사용하는 이유
- 기존 방식 : JS에서 HTML에 있는 요소를 가져오고 → JS에서 값을 변경하고 → 다시 HTML을 업데이트 해주는 방식 (시작이 HTML)
- React 방식 : JS에서 HTML 요소를 선택해서 바로 업데이트 해주는 방식 (시작이 JS)
  - babel : jsx를 html 코드로 변환

## React 기본 문법과 개념
### jsx
```html
<!-- React -->
<script src="https://unpkg.com/react@17.0.2/umd/react.production.min.js"></script>
<script src="https://unpkg.com/react-dom@17.0.2/umd/react-dom.production.min.js"></script>
<script src="https://unpkg.com/@bable/standalone/babel.min.js"></script>
<script type="text/babel">
  const sample = (
    <h3 id="title" onMouseEnter={() => console.log("mouse enter")}>
      Hello! World!
    </h3>
  );
</script>
```

### html
```html
<!-- babel converts jsx to html -->
<script>
  const sample = React.createElement(
    "h3",
    {
      id: "title",
      onMouseEnter: () => console.log("mouse enter"),
    },
    "Hello! World"
  );
</script>
```

### Container
```html
<!-- Render Container Type -->
<script>
  const Sample = () => (
    <h3 id="title" onMouseEnter={() => console.log("mouse enter")}>
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

  const Container = (
    <div>
      <Sample />
    </div>
  );
  ReactDOM.render(Container, root);
</script>
```

### Add Event Listener
```html
<!-- Event Listener-->
<script>
  let count = 0;
  function countUp() {
    count = count + 1;
    ReactDOM.render(<Container />, root); // 함수가 호출됐을 때 다시 render
  }
  const Container = () => (
    <div>
      <h3> Count : {count} </h3>
      <button onClick={countUp}>Click Button</button>
    </div>
  );
  ReactDOM.render(<Container />, root); // 페이지가 Load될 때 render

  /* more fency code */
  function render() {
    ReactDOM.render(<Container />, root);
  }
  function countUp() {
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

### Re-rendering (useState)
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

### Prop
```html
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
          borderRadius: 10,
        }}
        onClick={props.onClickEvent}
      >
        {props.text}
      </button>
    );
  }
  // Checking PropType
  Btn.propTypes = {
    text: PropTypes.string,
    fontSize: PropTypes.number.isRequired,
  };

  function App() {
    const [value, setValue] = React.useState("Save Changes");
    const changeValue = () => setValue("Revert Changes");
    return (
      <div>
        <Btn text={value} onClickEvent={changeValue} fontSize={10} />
        <Btn text={value} onClickEvent={changeValue} fontSize={12} />
      </div>
    );
  }
</script>
```

### Cleanup
- Component가 삭제됐을 때 실행되는 함수

  ```js
  function Hello() {
    // Way 1 : better way
    useEffect(() => {
      console.log("created :)");
      return () => console.log("destroyed :("); // Cleanup function
    }, []);

    // Way 2
    function effectCreated() {
      console.log("created :)");
      return effectDestroyed; // Cleanup function
    }
    function effectDestroyed() {
      console.log("destroyed :(");
    }
    useEffect(effectCreated, []);

    return <h1>Hello</h1>;
  }
  ```

### useState & useEffect & useMemo & useCallback
- #### useState[state, setState]
  - document : https://ko.reactjs.org/docs/hooks-reference.html#usestate
  - 기본정의 : `function useState<S>(initialState: S | (() => S)): [S, Dispatch<SetStateAction<S>>] import useState`
  - `state`를 변경하기 위해선 `setState`를 호출해야하며 `setState`가 호출될 때마다 해당하는 component는 re-rendering 된다.
  - component가 전체 re-rendering 되기 때문에 필요에 따라 `useEffect`를 사용해준다.
- #### useEffect
  - document : https://ko.reactjs.org/docs/hooks-reference.html#useeffect
  - 기본 정의 : `function useEffect(effect: EffectCallback, deps?: DependencyList): void import useEffect`
  - useEffect는 컴포넌트의 렌더링 이후에 실행되는 함수를 정의하는 Hook
  - 주로 데이터 가져오기, 구독 설정, DOM 조작, 타이머 등의 작업을 처리할 때 사용된다.
  - 기본적으로 모든 렌더링 시마다 실행되며, 특정 상태나 prop의 변화를 감지하여 특정 작업을 수행하도록 설정할 수 있다.
  - 마운트, 언마운트, 업데이트 시에 모두 실행된다.
  - 예시
    ```js
    useEffect(() => {
      if (keyword.length > 5) 
        console.log("keyword is changes");
    }, [keyword]);
    ```
- #### useMemo
  - document : https://ko.legacy.reactjs.org/docs/hooks-reference.html#usememo
  - 기본정의 : `const memoizedValue = useMemo(() => computeExpensiveValue(a, b), [a, b]);`
  - 계산 비용이 많은 함수의 결과 값을 기억하는 Hook
  - 이전에 계산된 값이 다음 렌더링에서 필요할 때, 이전 값을 재사용하여 성능을 최적화할 수 있다.
  - 의존성 배열(deps)을 지정하여 해당 의존성이 변경되었을 때에만 값을 다시 계산하도록 설정할 수 있다.
  - 아래 코드에서 버튼을 누르면 useMemo는 2번, useEffect는 1번 호출된다. <br/>
    버튼을 클릭하여 count가 1로 업데이트되면 컴포넌트가 다시 리렌더링되고 doubleCount가 1 * 2로 계산된다. <br/>
    useMemo는 이전에 계산된 결과 0과 다르므로 새로운 값을 반환하게 되기 때문에 2번 호출된다. <br />
    useEffect는 렌더링 이후에 실행되므로 버튼을 클릭하여 count가 변경될 때 1번 호출된다.
    ```javascript
    import React, { useEffect, useMemo, useState } from "react";

    const ExampleComponent = () => {
      const [count, setCount] = useState(0);
  
      const doubleCount = useMemo(() => {
        console.log("useMemo runs only when count changes");
        return count * 2;
      }, [count]);
  
      useEffect(() => {
        console.log("useEffect runs");
      }, [count]);
  
      return (
        <div>
          <p>Count: {count}</p>
          <p>Double Count: {doubleCount}</p>
          <button onClick={() => setCount(count + 1)}>Increment</button>
        </div>
      );
    };

    export default ExampleComponent;
    ```
- #### useCallback
  - document : https://ko.legacy.reactjs.org/docs/hooks-reference.html#usecallback
  - 기본정의 
    ```javascript
    const memoizedCallback = useCallback(
      () => {
        doSomething(a, b);
      },
      [a, b],
    );
    ```
  - useCallback은 함수를 기억하는 Hook
  - 부모 컴포넌트가 렌더링될 때마다 함수가 새로 생성되는 것을 방지하고, 이전에 생성된 함수를 재사용하여 성능을 최적화합니다.
  - 의존성 배열(deps)을 지정하여 해당 의존성이 변경되었을 때에만 새로운 함수를 생성하도록 설정할 수 있습니다.
    ```javascript
    import React, { useCallback, useState } from 'react';

    const ExampleComponent = () => {
      const [count, setCount] = useState(0);
      
      const handleIncrement = useCallback(() => {
        console.log('handleIncrement function is memoized');
          setCount((prevCount) => prevCount + 1);
        }, []);
        
        return (
          <div>
            <p>Count: {count}</p>
            <button onClick={handleIncrement}>Increment</button>
          </div>
      );
    };
    ```
  - `useEffect`: 렌더링 이후에 실행되는 함수를 정의, 부작용 처리에 사용.
  - `useMemo`: 계산 비용이 많은 함수의 결과 값을 기억, 최적화에 사용.
  - `useCallback`: 함수를 기억, 함수 재생성 방지 및 최적화에 사용.
    

## Reference
- [Nomadcoder's ReactJS로 영화 웹 서비스 만들기](https://nomadcoders.co/react-for-beginners/lobby)

---

# Nextjs
## Next.js 의 개념과 주요 기능
- next.js는 React로 만드는 SSR Framework 이다.
- CSR은 모든 js 파일을 loading하고 화면을 표시하는데 많은 시간을 소요하게 된다. 반면에 SSR은 서버에서 js 파일을 loading해서 client에선 js를 loading하는 시간을 절약할 수 있다.
- 검색엔진에 용이하고 SEO를 용이하게 할 수 있다.
- Hot Reloading : 저장되는 코드는 자동 새로고침
- Automatic Routing : pages 폴더 내에 있는 파일은 자동으로 routing
- _document.tsx : meta tag 정의. 서버사이드에 관여하는 로직 또는 static한 로직을 추가하는데 사용한다.
- _app.tsx : 최초 rendering
- Link와 prefetching : 백그라운드에서 페이지를 미리 가져옵니다.
  - `<Link prefetch href="..">`
## 요약해보기
| 종류                   | build 시에만 동작 | 비고                           |
|----------------------|--------------|------------------------------|
| `getInitialProps`    | O            | 9.3 버전부터는 사용을 권장하지 않음        |
| `getStaticProps`     | O            | build 시에만 데이터를 가져오고 속도가 빠름   |
| `getStaticPaths`     | O            | 페이지가 Dynamic Routes 사용할 때 유리 |
| `getServerSideProps` | X            | 페이지 요청 시 마다 데이터를 가져와야할 때 유리  |

## Nextjs 기본 문법과 개념
### getInitialProps
- 각 페이지에서 사전(build될 때)에 불러와야할 데이터가 있을 때 사용한다.
- CSR 방식의 useEffect, created 함수와 비슷하다.
- `getInitialProps`는 _app에서 사용하면 next.js의 page 최적화에 안좋을 수 있기 때문에 각 Component에서 사용하는게 좋습니다.
- `getStaticPropd`, `getStaticPath`는 빌드 후에 고정된 정적 페이지로 사용할 수 있기 때문에 SSR을 하지 않고 CDN 등으로 캐싱할 수 있어서 loading 속도가 좋지만 `getServerSideProps`, `getInitialProps`는 요청이 있을 때마다 SSR을 할 수 있어서 loading 속도가 안좋을 수 있습니다.
- ⭐️ v9 부터는 `getStaticProps`, `getStaticPaths`, `getServerSideProps` 사용을 권장한다.⭐️

### getStaticProps
- build 시 고정되는 값으로 빌드 이후에는 수정이 불가능하다. 가져온 데이터를 static 하게 사용하기 때문에 속도는 굉장히 빠르지만 자주 변하는 데이터에는 적합하지 않다.
- 페이지의 고정 컨텐츠를 외부에서 가져올 때 사용한다.
- `getStaticProps`가 받는 props
  - `params`: dynamic route page 일 때, params에 route parameter 정보를 갖고 있다.
  - `req`: Http Request Object
  - `res`: Http Response Object
  - `query`: Query String
  - `preview`: Preview 모드 여부
  - `previewData`: setPreviewData로 설정되있는 데이터
- `getStaticProps`가 응답하는 props
  - `props`: 해당 컴포넌트로 리턴할 값 (optional)
  - `revalidate`: 페이지 재생성이 발생할 수 있는 시간(초), default = false (다음 build 때까지 페이지가 이 상태로 캐시됨) (optional)
  - `notFound`: boolean, 404 status를 보내는 것을 허용한다. (optional)

  ```js
  // 타입지정을 위해 import
  import { GetStaticProps } from 'next'
  
  interface PostInterface {
    userId: number
    id: number
    title: string
    body: string
  }
  
  //getStaticProps()에서 받은 데이터값을 props로 받음
  function Blog({ posts } : { posts:PostInterface }) {
    return (
      <ul>
        {posts.map((post) => (
          <li>{post.title}</li>
        ))}
      </ul>
    )
  }
  
  // getStaticProps 받는 부분
  export const getStaticProps: GetStaticProps = async (context) => {
    const res = await fetch('https://.../posts')
    const posts = await res.json()
  
    // 데이터가 없으면 notFound를 보낸다
    if (!data) {
      return {
        notFound: true,
      }
    }
    
    //{ props: posts } 빌드타임에 받아서 Blog component로 보낸다
    return {
      props: {
        posts,
      },
    }
  }
  
  export default Blog
  ```

### getStaticPaths
- Dynamic Routes 쉽게 구성할 수 있다. 폴더 구조에서 [id] 와 같이 값을 사용하면 Next.js는 알아서 Routes를 구성합니다.
- 미리 생성된 paths는 캐싱되기 때문에 페이지 로딩 속도가 굉장히 빠릅니다.
- `getStaticPaths`가 응답하는 props
  - `paths`: build 할 때 pre-rendering 할 경로들
  - `fallback`: paths 이외의 경로들에 대해 추후 요청이 들어오면 만들어 줄지 말지 정한다. 정하지 않으면 404를 반환한다.

  ```js
  import { GetStaticProps } from 'next'

  interface PostInterface {
    userId: number
    id: number
    title: string
    body: string
  }
  
  // 이 페이지에서 렌더될 컴포넌트
  function Post({ posts } : { posts:PostInterface }) {
    return (
      <ul>
        {posts.map((post) => (
          <li>{post.title}</li>
        ))}
      </ul>
    )
  }
  
  // 빌드될 때 실행
  export const getStaticPaths = async () => {
    // posts를 받기 위해 fetch
    const res = await fetch('https://.../posts')
    const posts = await res.json()
  
    // pre-render할 Path를 얻음 (posts를 통해서)
    const paths = posts.map((post) => ({
    params: { id: post.id },
    }))
  
    // 우리는 오로지 이 path들만 빌드타임에 프리렌더 함
    // { fallback: false } 는 다른 routes들은 404임을 의미
    // true이면 만들어지지 않은 것도 추후 요청이 들어오면 만들어 줄 거라는 뜻
    return { paths, fallback: false }
  }
  
  // 빌드될 때 실행
  export const getStaticProps = async ({ params }) => {
    // params는 post `id`를 포함하고 있다
    const res = await fetch(`https://.../posts/${params.id}`)
    const post = await res.json()
  
    // 해당 페이지에 props로 보냄
    return { props: { post } }
  }
  
  export default Post
  ```

### getServerSideProps
- build와 상관없이 매 페이지 요청마다 데이터를 서버로부터 가져옵니다.
- `getServerSideProps`는 주로 페이지를 렌더링 하기 전에 필요한 fetch 데이터가 있을 때 사용합니다. 페이지 요청때마다 호출되서 `getStaticProps`보단 느리지만 최신화가 자주 필요한 데이터에 적합합니다.
- `getServerSideProps`가 요청하는 props
  - `params`: dynamic route page 일 때, params에 route parameter 정보를 갖고 있다.
  - `req`: Http Request Object
  - `res`: Http Response Object
  - `query`: Query String
  - `preview`: Preview 모드 여부
  - `previewData`: setPreviewData로 설정되있는 데이터
- `getServerSideProps`가 응답하는 props
  - `props`: 해당 컴포넌트로 리턴할 값 (optional)
  - `redirect` : 값 내부와 외부 리소스 리디렉션 허용한다 (optional)\
    무조건 { destination: string, permanent: boolean } 의 꼴이어야 한다.\
    몇몇 드문 케이스에서 오래된 HTTP Client를 적절히 리디렉션하기 위해 커스텀 status코드가 필요할 수 있는데, 그땐 permanent property 대신에 statusCode property를 이용한다.
  - `notFound`: boolean, 404 status를 보내는 것을 허용한다. (optional)
  ```js
  // 타입 지정을 위해 import
  import { GetServerSideProps } from 'next'
  
  function Page({ data }) {
    console.log(this.props.data)
    //res.json()이 찍힙니다
  }
  
  export const getServerSideProps: GetServerSideProps = async (context) => {
  
    const res = await fetch(`https://.../data`)
    const data = await res.json()
  
    // data 없을 땐 리턴값을 달리함
    if (!data) {
      return {
        redirect: {
          destination: '/',
          permanent: false,
        },
      }
    }

    if(!data) {
      return {
        notFound: true,
      }
    }
    
    //pageProps로 넘길 데이터
    return { props: { data: data } }
  }
  
  export default Page
  ```

### Server Side Life Cycle
- next 서버가 GET 요청을 받는다.
- GET 요청에 맞는 Component를 pages에서 찾는다.
- _app.tsx 의 `getInitialProps`가 있으면 실행한다.
- 해당 Component의 `getInitialProps`가 있으면 실행한다. (props를 받아온다.)
- _document.tsx의 `getInitialProps`가 있으면 실행한다.
- 모든 props를 구성하고 _app.tsx → Component 순으로 Rendering
- 모든 contents를 구성하고 _document.tsx를 실행해서 html 형태로 출력한다.
- `getInitialProps`는 ⭐️ 1번만 ⭐️ 실행된다. _app.tsx → Component으로 실행되기 때문에 _app.tsx에서 `getInitialProps`를 정의했다면 Component에 있는 `getInitialProps`는 실행되지 않는다. Component에서도 사용하려면 _app.tsx에 코드를 추가해야 한다.
- `getInitialProps`는 Server에서 실행되기 때문에 Browser API를 실행하면 안된다.
  ```js
  import "./globals.css";

  function MyApp({ Component, pageProps }) {
    return <Component ponent {...pageProps} />;
  }

  MyApp.getInitialProps = async ({ Component, ctx }) => {
    let pageProps = {};
    // 하위 컴포넌트에 getInitialProps가 있다면 추가 (각 개별 컴포넌트에서 사용할 값 추가)
    if (Component.getInitialProps) {
      pageProps = await Component.getInitialProps(ctx);
    }

    // _app에서 props 추가 (모든 컴포넌트에서 공통적으로 사용할 값 추가)
    pageProps = { ...pageProps, post: { title: 11111, content: 3333 } };

    return { pageProps };
  };

  export default MyApp;
  ```

### SWR (Stale-While-Revalidate)
- 캐시로부터 데이터(stale)을 반환하고 fetch 요청(revalidate)를 보낸 후 최신 데이터를 업데이트합니다.
- 재검증(revalidate)하는 동안에 기존에 캐시된 데이터를 반환하기 때문에 최신 데이터가 아닐 수 있습니다.
- 재검증은 포커싱을 다른 곳으로 옮겼다가 다시 돌아오거나 원하는 순간 혹은 주기로 수행할 수 있습니다.
- SEO가 필요하면 `getServerSideProps`를 사용하고 비공개 페이지 같은 경우엔 `SWR`을 사용합니다.
  ```js
  const { data, error, isValidating, mutate } = useSWR({key}, fetcher, options);
  key : needs unique
  fetcher : Promise
  options : SWR hook 옵션 (https://swr.vercel.app/ko/docs/options)

  data : fetcher 성공 시 반환하는 데이터
  error : fetcher 실패 시 반환하는 에러
  isValidating : 요청이 있거나 로딩 중인 경우 반환 (boolean)
  mutate(data? shouldRevalidate?) : 캐시된 데이터를 mutate 하기 위한 함수

  /******************************************/
  expost const fetcher = async(url) => {
    const response = await fetch(url);
    if(!response.ok){
      const error = new Error("Fail to fetch data");
      error.info = await response.json();
      error.status = response.status;
      throw error;
    }
    return res.json();
  }

  /******************************************/
  import useSWR from "swr";
  import { fetcher } from "./fetch";

  export const fetchData = () => {
    const { data, error } = useSWR(url, fetcher, {
      onErrorRetry: (error, key, config, revalidate, { retryCount }) => {
        if (error.status === 404) return;
        if (retryCount >= 10) return;
        setTimeout(() => revalidate({ retryCount }), 5000);
      },
    });

    if(error) return "Fail to get data";
    if(!data) return "no data";
    return data;
  }
  ```
- SWR은 built in cache, 중복제거를 자동으로 해줘서 불필요한 네트워크 요청을 줄여줍니다. 한 컴포넌트에서 useUser() 라는 SWR이 사용되고 컴포넌트가 4번 사용된다고 해도 네트워크 요청은 1번만 일어나게 됩니다.
- 불필요한 렌더링을 줄이기 위해선 dependency를 조정해야 합니다.
  ```js
  const { data, error, isValidating} = useSWR({key}, fetcher); // 불필요한 rendering 발생함
  const { data } = useSWR(url, fetcher); // data가 변경되었을 때에만 rendering 발생함
  ```
- #### `SWRConfig`
  - SWRConfig의 { value }로 SWR에 적용할 options 값을 넘겨주면서 전역적으로 전파할 수 있습니다.
    ```js
    <SWRConfig
      value = {{
        resfreshInterval: 3000,
        retcher: (resource, init) => fetch(resource, init).then(res => res.json())
      }}>
      <TemplateComponent />
      <FrameComponent />
    </SWRConfig>
    ```
- #### `mutate`
  - useSWRConfig로 얻을 수 있는 mutate는 key를 기준으로 다른 SWR Hook에게 revalidate 메세지를 전역으로 보냅니다.
    ```js
    const { mutate } = useSWRConfig();
    return (
      <div>
        <Button onClick={() => {
          document.cookie = "token=; expires=...";
          mutate('/api/user');  // key 값에 해당하는 모든 SWR이 revalidate 하도록 전파
        }}
        </Button>
      </div>
    )
    ```
- ####  `Revalidate`
  - 자동 갱신 옵션 : https://swr.vercel.app/ko/docs/revalidation
- ####  `Pre-fetching`
  - `<link rel="preload" href="/api/data" as="fetch" crossorigin="anonymous">`
  - prefetch 함수를 따로 정의해서 사용할 수 있다.
    ```js
    const func = () => {
      mutate('/api/data', fetch('/api/data').then(res => res.json())); // Promise가 실행될 때 SWR은 결과를 사용한다.
    }
    ```
  - pre-fill 방법
    ```js
    useSWR('/api/data/', fetcher, {fallbackData: prefetchedData})
    ```
### Context Object
- /profile/about → { id: 'about' }
- /profile/about?name=yongwoo → { name: 'yongwoo' }

### Shallow Route
- 같은 페이지에서 getInitialProps를 굳이 호출하지 않고 URL을 변경하고 싶을 때 사용합니다.
- URL이 변경된 것은 `componentDidUpdate`, `useEffect`를 통해 감지할 수 있습니다.
- 반드시 같은 페이지 내에서 동작해야하고 다른 페이지를 사용하면 새 페이지가 loading 되고 getInitialProps가 실행됩니다.
- 아래 코드를 통해 페이지는 교체되지 않고 URL만 변경할 수 있습니다.
  ```js
  router.push(
    {
      pathname: "/about/profile?name=yongwoo"
      query: { ...values, page: 1 }
    },
    undefiend,
    { shallow: true }
  )
  ```
### CssBaseline
- 모든 브라우저가 일관적으로 보이도록 css룰 전역에서 normaliza 해야 합니다.
  ```js
  import type { AppProps } from "next/app";
  import CssBaseline from "@mui/material/CssBaseline";

  const App = (props: AppProps) => {
    const { Component, pageProps } = props;

    return (
      <>
        <CssBaseline />
        <Component {...pageProps} />
      </>
    );
  };

  export default App;
  ``` 
### react-next-keep-alive
- git repo : https://github.com/AlexSapoznikov/react-next-keep-alive
- 사용 방법
  - _app.tsx에 적용
    ```js
    import { AppProps } from 'next/app';
    import { KeepAliveProvider } from 'react-next-keep-alive';

    function MyApp ({ Component, pageProps }: AppProps) {
      const router = useRouter();

      return (
        // 다른 Component로 감쌀 경우, <Component {...pageProps} /> 바로 위에 위치
        <KeepAliveProvider router={router}>
          <Component {...pageProps} />
        </KeepAliveProvider>
      );
    }

    export default MyApp;
    ```
  - 특정 page에 적용
    ```js
    import React, { useState } from 'react';
    import Link from 'next/link';
    import { withKeepAlive } from 'react-next-keep-alive';

    const samplePage = () => (
      const [count, setCount] = useState(0);
      return (
        <Link href="/page2" as="/page2">
          <a>
            Next page
          </a>
        </Link>

        <br /><br />

        <button type="button" onClick={() => setCount(count + 1)}>
          Count: { count }
        </button>
      )
    );

    export default withKeepAlive(samplePage, 'sample-page');
    ```

### HOC (Higher Order Component)
- 고차 컴포넌트는 컴포넌트를 가져와 새 컴포넌트를 반환합니다.
- Reference
  - https://ko.reactjs.org/docs/higher-order-components.html

## Reference
  - https://nextjs.org
  - https://kyounghwan01.github.io/blog/React/next/basic/
  - https://github.com/AlexSapoznikov/react-next-keep-alive
  - Data Fetching
    - https://nextjs.org/docs/basic-features/data-fetching/get-server-side-props
    - https://nextjs.org/docs/api-reference/data-fetching/get-server-side-props
    - https://velog.io/@devstone/Next.js-100-활용하기-feat.-initialProps-webpack-storybook
  - SWR
    - https://swr.vercel.app/ko/docs/revalidation
    - https://velog.io/@soryeongk/SWRBasic
    - https://velog.io/@soryeongk/ReactSWRTutorial

---

# React Admin
- 공식 Document : https://marmelab.com/react-admin/Readme.html
- 사용한 버전 : "react-admin": "^4.9.0"
## Fetch Data
- React Admin에선 기본적으로 Data를 Fetch하는 hook을 내부적으로 제공하고 있다.
- useGetOne과 같은 hook을 사용하거나 dataProviver Interface를 재정의해서 사용할 수 있다.
- useGetOne, useGetList 등은 dataProvider에 .getOne(), .getList() 함수를 사용한다.
- useGetOne과 같은 hook은 Request와 Response의 기본적인 형식이 정해져있어서 맞춰서 사용해야한다.
- ### useGetList
  - 기본적으로 pagination 형식을 제공한다.
  - 응답 객체에 id는 반드시 있어야 한다.
  ```javascript
  const {
    pagination = { page: 1, perPage: 25 },
    sort = { field: 'id', order: 'DESC' },
    filter = {},
    meta,
  } = params;
  const result = useQuery<
    GetListResult<RecordType>,
    Error,
    GetListResult<RecordType>
  >(
    [resource, 'getList', { pagination, sort, filter, meta }],
    () =>
        dataProvider
            .getList<RecordType>(resource, {
                pagination,
                sort,
                filter,
                meta,
            })
            .then(({ data, total, pageInfo }) => ({
                data,
                total,
                pageInfo,
            })),
        {
            ...options,
            onSuccess: value => {
                // optimistically populate the getOne cache
                value?.data?.forEach(record => {
                    queryClient.setQueryData(
                        [resource, 'getOne', { id: String(record.id), meta }],
                        oldRecord => oldRecord ?? record
                    );
                });
                // execute call-time onSuccess if provided
                if (options?.onSuccess) {
                    options.onSuccess(value);
                }
            },
        }
  );
  ```

- ### useGetOne
  - param에 id가 필수값이다.
  ```javascript
  return useQuery<RecordType, unknown, RecordType>(
     // Sometimes the id comes as a string (e.g. when read from the URL in a Show view).
     // Sometimes the id comes as a number (e.g. when read from a Record in useGetList response).
     // As the react-query cache is type-sensitive, we always stringify the identifier to get a match
     [resource, 'getOne', { id: String(id), meta }],
     () =>
        dataProvider
            .getOne<RecordType>(resource, { id, meta })
            .then(({ data }) => data),
        options
  );
  ```
- ### useGetMany
  ```javascript
  return useQuery<RecordType[], Error, RecordType[]>(
    [
      resource,
      'getMany',
      {
        ids: !ids || ids.length === 0 ? [] : ids.map(id => String(id)),
        meta,
      },
    ],
    () => {
      if (!ids || ids.length === 0) {
        // no need to call the dataProvider
        return Promise.resolve([]);
      }
      return dataProvider
        .getMany<RecordType>(resource, { ids, meta })
        .then(({ data }) => data);
    },
    {
      placeholderData: () => {
        const records =
        !ids || ids.length === 0 ? [] : ids.map(id => {
          const queryHash = hashQueryKey([
            resource,
            'getOne',
            { id: String(id), meta },
          ]);
          return queryCache.get<RecordType>(queryHash)
            ?.state?.data;
        });
        if (records.some(record => record === undefined)) {
          return undefined;
        } else {
          return records as RecordType[];
        }
      },
      onSuccess: data => {
        // optimistically populate the getOne cache
        data.forEach(record => {
          queryClient.setQueryData(
            [resource, 'getOne', { id: String(record.id), meta }],
            oldRecord => oldRecord ?? record
          );
        });
      },
      retry: false,
      ...options,
    }
  );
  ```
- ### useGetManyReference
- ### useUpdate
- ### useUpdateMany
- ### useCreate
- ### useDelete
- ### useDeleteMany
- ### DataProvider
  - `fetchUtils` : react-admin에서 기본으로 제공하는 fetch 관련 util이다. `fetchUtils.fetchJson()`

## Form 이동과 제어
- ### useFormContext
- ### useRecordContext

## Form 기본기능
- ### CreateBase
- ### EditBase

## Reference
- https://marmelab.com/react-admin/Readme.html

---
# 상태관리
## Recoil
- ### atom
  - Recoil 상태 관리의 기본 단위이며 atom이 업데이트되면 구독하고 있는 Component 모두 re-rendering이 된다.
  - key는 식별자로서 반드시 고유한 값을 가져야 한다.
    ```js
    const nameState = atom({
      key: "nameState",
      default: "yongwoo"
    })
    ```
- ### atomFamily
  - atom을 생성할 때 특정 parameter를 받아서 사용할 수 있다.
  - 예를들어, Post를 관리하는 atom을 만든다고 한다면 atom은 Post[]를 가져야 한다.
    ```js
    const postList = atom<Post[]>({
      key: "postList",
      default: []
    })
    ```
  - 이 방식은 Redux와 동일하게 1개의 Post가 수정될때마다 atom에는 매번 새로운 Post[] 배열이 할당되어야 하는 문제와 여러 개의 Post를 렌더링할 때 별도의 memoization이 필요한 문제점이 있다.
  - 그렇다고해서 atom에 Post를 각각 매핑해서 사용할 수는 없다.
    ```js
    type Post = { id: string; title: string; content: string; }
    const post1 = atom<Post>({ key: "post1" });
    const post2 = atom<Post>({ key: "post2" });
    const post3 = atom<Post>({ key: "post3" });
    ```
  - 이런 경우에 atomFamily를 사용하면 된다.
    ```js
    const postFamilyState = atomFamily<
      Post // return data type
      , string // atomFamily 팩토리 함수 호출 파라미터
    >({
      key: "postFamilyState",
      default: (id) => {
        // id를 받아서 새로운 Post 객체를 만들어냄
        return {
          id,
          title: "",
          content: ""
        }
      }
    })
    ```
    ```js
    const [firstPost, setFirstPost] = useRecoilState(postFamilyState("1"));
    const [secondPost, setSecondPost] = useRecoilState(postFamilyState("2"));
    ```
  - 이렇게 생성된 atom들의 id를 관리해줘야 한다. 이는 Recoil의 selector를 통해서 해결할 수 있다.
- ### selector
  - atom 상태에 따른 동적인 데이터를 파생시킨다.
  - `get` 함수를 가져야 한다.

- ### selectorFamily

- ### useRecoilState
  - atom의 값을 구독하고 update하는 hook
- ### useRecoilValue
  - setter를 제외한 atom의 값만 구독한다.
- ### useSetRecoilState
  - setter만 구독한다.
- ### useResetRecoilState
  - atom을 reset 시킨다.

## Reference
  - basic : https://recoiljs.org/ko/docs/basic-tutorial/atoms
  - atom, atomFamily, selector, selectorFamily : https://kelly-kh-woo.medium.com/번역-recoil-새로운-리액트-상태-관리-라이브러리-95b5bea91b0

---