React
===

# 목차
<!-- TOC -->
* [React](#react)
* [목차](#목차)
* [실제 프로젝트에서 경험했던 기술과 고민](#실제-프로젝트에서-경험했던-기술과-고민)
  * [formik](#formik)
  * [yup](#yup)
  * [Store 단점](#store-단점)
  * [React로 구현하다가 부딪힌 점](#react로-구현하다가-부딪힌-점)
  * [Nextjs에서 getServerSideProps의 로그인 처리](#nextjs에서-getserversideprops의-로그인-처리)
* [React](#react-1)
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
    * [Array of useState](#array-of-usestate)
    * [fetch](#fetch)
    * [useState & useEffect & useMemo](#usestate--useeffect--usememo)
  * [Axios](#axios)
    * [async 함수 정의](#async-함수-정의)
    * [Request/Response 정의](#requestresponse-정의)
    * [API 정의](#api-정의)
    * [API 사용](#api-사용)
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
* [상태관리](#상태관리)
  * [Recoil](#recoil)
* [사용했던 Library와 Trouble Shooting](#사용했던-library와-trouble-shooting)
  * [GroupComponent 다루기 - BaseSelect](#groupcomponent-다루기---baseselect)
  * [Notion 관련 Library](#notion-관련-library)
    * [react-notion-x](#react-notion-x)
    * [react-notion](#react-notion)
  * [i18n 언어팩 적용](#i18n-언어팩-적용)
  * [Sub Component 제어](#sub-component-제어)
  * [Autocomplete](#autocomplete)
  * [react-mentions](#react-mentions)
  * [infinite scroll](#infinite-scroll)
  * [간단한 문법 메모](#간단한-문법-메모)
<!-- TOC -->

---

# 실제 프로젝트에서 경험했던 기술과 고민
- React 상태 관리 : Redux, Mobx, Recoil, zustand
- Store를 사용하지 않고 Props로만 관리하는 것의 차이
	- React 컴포넌트에서 받아야 하는 Props가 너무 많아짐.
	- Store(상태 관리하는 구조체)를 쓰는 이유는 Store 안에 큰 컴포넌트 단위로 각각의 데이터 구조를 다 갖고 있음.

## formik
- 여러 form을 처리해주기 쉽게 만드는거.
  - focus = touch로 (form 이 터치가 됐느냐)
  - error = error로 잡아줌 (form에 값이 쓰여져있느냐)
- 기본 사용법 (https://krpeppermint100.medium.com/ts-formik-사용법-4f526888c81a)
  ```js
  return (
    <Formik 
      initialValues={{ username: "beaver", password: "beaver" }} 
      onSubmit={(data, { setSubmitting }) => {
        setSubmitting(true)
      }}>
      {
        ({ values, handleChange, handleBlur, handleSubmit, isSubmitting }) => (
          <form onSubmit={handleSubmit}>
            <div>
              <TextField name="username" value={values.username} onChange={handleChange} />
            </div>
            <div>
              <TextField name="password" value={values.password} onChange={handleChange} />
            </div>
            <Button type="submit" disabled={isSubmitting}>Submit</Button>
          </form>
        )
      }
    </Formik>
  );
  ```
## yup
- form에 대한 에러를 잡는 기준을 정해주는것.
- formik 안에 yup를 넣어서 yup이 에러 기준을 잡고 에러가 발생하면 formik이 감지함.

## Store 단점
- 폼이 너무 많으면 폼을 쪼개기가 어렵다.
- 에러, 검증 값을 처리할때 보일러 플레이트 코드가 많다.

## React로 구현하다가 부딪힌 점 
- 요구사항
  - 한 화면에서 기본정보, 보유 자산 종류, 관심 상품 정보 등 여러 form으로 나눠져있고 사용자는 원하는 값을 입력한다. 
  - 사용자의 정보가 이미 있는 경우 기존에 저장되있던 데이터를 화면에 뿌려준다.
  - 각 form에는 + 버튼이 있어서 입력 받는 값은 동적으로 늘어나고 줄어들 수 있다. 
  - 여러 값들을 입력한 후 저장 버튼을 누르면 화면에 나와있는 데이터를 모두 저장해야 한다.
- 구현
  - 상태관리로는 Redux를 사용했고 formik과 yup를 사용해서 여러 form에 있는 모든 필드에 데이터 검증을 하고 저장 버튼을 눌렀을 때 데이터를 가져오도록 했다.
  - 데이터를 저장할 때 바뀐 필드만 추려서 저장할지, 전체를 저장할지 정해야 했는데 바뀐 필드만 저장하는 방식을 채택했다. (전체를 저장하는게 더 쉬웠을 것 같다....)
  - 기본 값과 바뀐 값 전체를 1:1로 비교해서 바뀐 값만 저장을 했어야 했는데 여기에서 깊은 복사와 얕은 복사의 문제점이 발생했다.
- 문제
  - Redux는 상태 변화를 옵저버로 감지하는데 기존 값과 변화된 값을 비교하기 위해서 기존 값 A를 복사하면 A-1 까지는 깊은 복사가 잘된다. (새로운 주소가 생성된다.)
  - 화면이 여러 form으로 되어 있어서 값들을 props로 던져야했는데 A-1을 props로 넘기고 A-1의 값을 변경했을 때 옵저버가 A-1의 변화를 감지하고 initial value를 변경시켜 버렸다.
  - 그 이유는 A-1의 주소는 새로 따졌지만 proxy로 감싸져 있기 때문에 옵저버는 A-1의 변화를 감지하고 수정된 값을 initial value에 덮어써버린다.
  - 최종적으로 A에 대한 수정된 내용을 비교할 때 initial value는 이미 변경됐기 때문에 form에서 변경된 값으로 인지를 못했다. (변경된 값이 initial value가 됐기 때문에 Redux는 A가 변하지 않은 값으로 판단한다.)
  - SPA에선 변경된 값을 감지하는게 중요한대 (그래야 부분적으로 렌더링을 할 수 있으니까) props로 값들을 넘길때 얕은 복사가 이뤄지면서 데이터의 보존이 어려워졌다.

## Nextjs에서 getServerSideProps의 로그인 처리
  - Nestjs + SpringBoot로 구성되어 있는 어플리케이션에서 SEO를 위해서 한 페이지의 데이터를 SSR로 가져와야 했다.
  - 새로고침이 필요한 데이터라서 `getServerSideProps`를 사용해서 데이터를 가져왔고 로그인 구분 없이 보여주는 컨텐츠와 반응, 댓글 등 로그인 사용자의 정보를 보여줘야하는 컨텐츠가 섞여 있었다.
  - Session에 로그인 된 사용자가 있으면 BE API를 호출할 때 로그인 정보를 담아서 호출하고 BE는 Session을 체크해서 로그인 사용자의 대한 정보를 추가로 응답했다.
  - 여기서 발생했던 문제는 token 등 로그인 정보는 session과 localStorage에 있었는데 `getServerSideProps`에선 session, localStorage에 접근할 수 없었기 때문에 로그인 정보를 담아서 API를 호출할 수 없었다.
  - 이를 해결하기 위해서 해당 페이지의 각 데이터 영역을 공통 컴포넌트 등으로 세분화하고 최초 로딩 시 SSR을 통해서 가져온 데이터로 화면을 렌더링하고 로그인 session이 존재하면 API를 다시 호출해서 로그인 사용자의 정보를 가져오고 useEffect, useMemo를 사용해서 로그인 사용자의 정보가 필요한 컴포넌트만 재랜더링하는 방식으로 구현했다.

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

### Array of useState
- Array에 Array를 더하는 방법
  ```js
  const arraySample = [1,2,3];
  const useState[value, setValue] = useState([]);
  setValue((currentArray) => [value, ...currentArray]);
  ```
### fetch
```js
fetch("https://url", {
  method: "POST",
  headers: {
    "Content-Type": "application/json",
    "Access-Control-Allow-Origin": "*",
  },
  body: JSON.stringify({
    fromEmail: "test@email.com",
    toEmail: ["test@email.com"],
    mailSubject: "Test Mailing - Beaver",
    mailContent: "Test Mailing - Beaver",
  }),
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

### useState & useEffect & useMemo
- #### useState[state, setState]
  - document : https://ko.reactjs.org/docs/hooks-reference.html#usestate
  - 기본정의 : `function useState<S>(initialState: S | (() => S)): [S, Dispatch<SetStateAction<S>>] import useState`
  - `state`를 변경하기 위해선 `setState`를 호출해야하며 `setState`가 호출될 때마다 해당하는 component는 re-rendering 된다.
  - component가 전체 re-rendering 되기 때문에 필요에 따라 `useEffect`를 사용해준다.
- #### useEffect
  - document : https://ko.reactjs.org/docs/hooks-reference.html#useeffect
  - 기본 정의 : `function useEffect(effect: EffectCallback, deps?: DependencyList): void import useEffect`
  - object가 변경됐을 때만 function을 수행한다.
  - 예시
    ```js
    useEffect(() => {
      if (keyword.length > 5) console.log("keyword is changes");
    }, [keyword]);
    ```
- #### useMemo
  - `const memoizedValue = useMemo(() => computeExpensiveValue(a, b), [a, b]);`

- ### enum
  ```js
  export enum Method {
    GET = 'GET',
    POST = 'POST',
    PUT = 'PUT',
    DELETE = 'DELETE',
    PATCH = 'PATCH',
  }

  Method.GET
  ```

- ### useEffect - async
  ```js
  useEffect(() => {
    const fetchData = async () => {
      const data = await (await fetch(url)).json();
      setData(data);
    };

    fetchData();
  }, [data]);
  ```

- ### useEffect와 router.query
  ```js
  const router = useRouter();
  const { id } = router.query;

  useEffect(() => {
    if (!router.isReady) return;
    console.log(id);
  }, [router.isReady]);
  ```

## Axios
### async 함수 정의
- API 사용 공통 함수 만들기
  ```js
  // utils/ApiUtil.ts
  export const commonCallApi = async (commonApiRequest: CommonApiRequest): Promise<CommonApiResponse> => {
    const url: string = commonApiRequest.url + getQueryStringFormat(commonApiRequest.params?.queryParams);
    const isLoading = commonApiRequest.config?.isLoading || false;
    let response: CommonApiResponse = {
      successOrNot: 'N',
      statusCode: StatusCode.UNKNOWN_ERROR,
      data: {},
    };

    switch (commonApiRequest.method) {
      case Method.GET:
        response = await getInstance(commonApiRequest.service, isLoading).get(url);
        break;
      case Method.POST:
        response = await getInstance(commonApiRequest.service, isLoading).post(url, commonApiRequest.params?.bodyParams);
        break;
      case Method.PUT:
        response = await getInstance(commonApiRequest.service, isLoading).put(url, commonApiRequest.params?.bodyParams);
        break;
      case Method.DELETE:
        response = await getInstance(commonApiRequest.service, isLoading).delete(url);
        break;
      case Method.PATCH:
        response = await getInstance(commonApiRequest.service, isLoading).patch(url, commonApiRequest.params?.bodyParams);
        break;
      default:
        break;
    }
    return response;
  };
  ```
  ```js
  const getInstance = (serviceName: string, isLoading: boolean, params?: any): AxiosInstance => {
    if (isLoading) {
      // @ts-ignore
      // eslint-disable-next-line
      window.loadingSpinner.setChange(true);

    }

    axios.defaults.headers.post['Content-Type'] = 'application/json';

    let baseURL = process.env.NEXT_PUBLIC_API_BASE_URL ;
    const sessionUtil = new SessionUtil();

    if (process.env.NODE_ENV === 'development') {
      switch (serviceName) {
      case Service.MZP_BE:
        baseURL += ':' + ServicePort.MZP_BE.toString();
        break;
      default:
        break;
      }
    }

    const instance = axios.create({
      baseURL: baseURL,
      params: params || {},
    });

    // 공통 요청 처리
    instance.interceptors.request.use(
      (config: AxiosRequestConfig): AxiosRequestConfig => {
        if (config?.headers) {
          config.headers['x-correlation-id'] =
            window.location.pathname === '/'
              ? 'root'.concat('_').concat(uuidv4())
              : window.location.pathname?.concat('_').concat(uuidv4()) || '';
          if (sessionUtil.getSessionInfo().sessionId) {
            config.headers['x-session-id'] = sessionUtil.getSessionInfo().sessionId || '';
          }
        }
        return config;
      },
      (error: any): Promise<any> => {
        return Promise.reject(error);
      },
    );

    // success / error 공통 처리
    instance.interceptors.response.use(
      (response: any): any => {
        if (isLoading) {
          // @ts-ignore
          // eslint-disable-next-line
          window.loadingSpinner.setChange(false);
        }

        const commonResponse: CommonResponse = response.data as CommonResponse;
        if (commonResponse.statusCode && commonResponse.statusCode === StatusCode.SESSION_EXPIRE) {
          sessionUtil.deleteSessionInfo();
          window.location.assign('/login');
        }
        return commonResponse;
      },

      (error: any): any => {
        if (isLoading) {
          // @ts-ignore
          // eslint-disable-next-line
          window.loadingSpinner.setChange(false);
        }

        const unknownError: CommonResponse = {
          successOrNot: 'N',
          statusCode: StatusCode.UNKNOWN_ERROR,
          data: {},
        };

        // eslint-disable-next-line
        if (error.response && error.response.status.toString().indexOf('40') === 0) {
          //TODO: 400대 에러 공통처리
        }
        return unknownError;
      },
    );

    return instance;
  };
  ```
  ```js
  const getQueryStringFormat = (queryParams?: QueryParams): string => {
    if (!queryParams) return '';
    const keys = Object.keys(queryParams);
    const queryString = keys
      .map((key) => `${key}=${encodeURIComponent(queryParams[key] as string)}`) // eslint-disable-line
      .join('&');
    return queryString ? `?${queryString}` : '';
  };
  ```

### Request/Response 정의
  ```js
  export interface CommonApiRequest {
    service: Service;
    url: string;
    method: Method;
    params?: ParamObject;
    config?: Config;
  }

  export interface CommonApiResponse<T = any> {
    result: string;
    statusCode: string;
    data?: T;
  }
  ```

### API 정의
  ```js
  export interface SampleRequest {
    type: string;
    id: string;
    email: string;
    memberName: string;
    nickname?: string;
  }
  
  export const sampleApi = async(sampleRequest: SampleRequest, isLoading = true): Promise<CommonApiResponse> => {
    return commonCallApi({
      service: Service.BackEnd,
      url: '/health',
      method: Method.POST,
      params: {
        bodyParams: sampleRequest
      },
      config: {
        isLoading: isLoading
      }
    });
  }
  ```

### API 사용
  ```js
  const [data, setData] = useState<SampleResponse>();
  useEffect( () => {
    (async () => {
      const response = await sample.sampleApi(sampleRequest);
    setData(response);
    })();
  }, []);
  ```
  ```js
  const sampleCallApi = async () => {
    const sampleRequest = {
      type: info.type,
      id: info.id,
      email,
      memberName,
      nickname,
    } as SampleRequest;
    const response = await sampleApi(sampleRequest);
    if (response.result === SuccessOrNot.Y) {
      // input your logic
    }
  };
  ```

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
- SWRConfig
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
- mutate
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
- Revalidate
  - 자동 갱신 옵션 : https://swr.vercel.app/ko/docs/revalidation
- Pre-fetching
  - <link rel="preload" href="/api/data" as="fetch" crossorigin="anonymous">
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

- Reference
  - basic : https://recoiljs.org/ko/docs/basic-tutorial/atoms
  - atom, atomFamily, selector, selectorFamily : https://kelly-kh-woo.medium.com/번역-recoil-새로운-리액트-상태-관리-라이브러리-95b5bea91b0

---

# 사용했던 Library와 Trouble Shooting
## GroupComponent 다루기 - BaseSelect 
- BaseSelect에서 multi check 옵션을 썼을 때 완료를 누르지 않을 땐 처음 진입했을 때의 checked list 값을 유지해야했는데 잘 되지 않았다.
- 해당 컴포넌트 진입(화면에 노출될 때)할 떄 기본적으로 체크된 값은 `defaultChecked` 를 사용해야한다. `checked` 값을 사용하려면 true/false 값을 각 항목마다 state로 관리해야 하는데 번거로움이 있다.
- 한 항목을 체크하고 체크를 해제했을 때 실행되는 `handleMultipleChecked` 함수는 체크를 하고 해제할 때마다 해당 함수만 나가는게 아니라 컴포넌트 전체를 나가는 문제 떄문에 어려웠었다.
- 따라서 체크를 할 때마다 `onChange` 함수를 통해 바깥쪽 값(state)를 변화시켜줘야 하고 해당 컴포넌트를 `onClose` 하거나 그냥 나갔을 때 기존 값을 유지해주기 위해서 기존 값을 해당 컴포넌트 밖에서 관리해줘야 했다.
- `value`와 `originValue`를 관리해서 완료 버튼을 눌렀을 땐, `onComplete` 를 실행해서 value와 originValue를 없데이트하고 해당 컴포넌트가 닫힐 때 발생하는 `handleCloseModal`에선 외부 변수를 기존 값으로 돌려준다.
- `handleMultipleChecked`에선 체크/해제가 될 때마다 외부 값에 추가 또는 삭제해줘야 한다.
```js
interface DefaultProps {
  title: string;
  value: string | string[];
  originValue?: string;
  optionList: string[];
  multiple: boolean;
  onChange: (param: string) => void;
  onComplete?: (param: string) => void;
}
const BaseSelect = ({
  title,
  value,
  originValue,
  optionList,
  multiple,
  onChange,
  onComplte,
}: DefaultProps) => {
  const [isModalVisible, setIsModalVisible] = useState(false);
  const [btnDisabled, setBtnDisabled] = useState(true);

  const handleMultipleChecked = (checked: boolean, checkedValue: any) => {
    let newCheckValue: string[] = value ? [...value] : [];
    if (checked) {
      if (!newCheckValue.includes(checkedValue)) {
        newCheckValue = [...newCheckValue, checkedValue];
      }
    } else {
      newCheckValue = [...newCheckValue.filter((item: any) => item !== checkedValue)];
    }
    onChange(newCheckValue);
    setBtnDisabled(newCheckValue?.length === 0);
  };
  
  const handleShowModal = (visible: boolean) => () => {
    setBtnDisabled(value?.length === 0);
    setIsModalVisible(visible);
  };
  
  const handleCloseModal = () => {
    if (multiple) {
      onChange(originValue);
    }
    setIsModalVisible(false);
  };
  
  const handleMultiComplete = () => {
    if (onComplete) onComplete(value);
    setIsModalVisible(false);
  };
  
  const handleOptionClick = (optionValue: string) => {
    onChange(optionValue);
    setIsModalVisible(false);
  };
  
  const setOptionList = () => {
    return (
      <List>
        {list.map((item: any) => (
          <ListItem key={item.value}>
            {!multiple && <ListItemButton onClick={() => handleOptionClick(item.value)}>{item.label}</ListItemButton>}
            {multiple && (
              <FormControlLabel
                control={
                  <Checkbox
                    onChange={(event, checked) => handleMultipleChecked(checked, item.value)}
                    defaultChecked={value.includes(item.value)}
                  />
                }
                label={item.label}
              />
            )}
          </ListItem>
        ))}
      </List>
    );
  };
  
  return (
    <RootStyled>
      <div>
        <Input value={value} readOnly />
        <button type="button" onClick={handleShowModal(true)}>
          {(!multiple && list.filter((item: any) => item.value === value)[0]?.label) ?? title}
          {multiple &&
            (value.length === 0
              ? title
              : value.length === 1
              ? list.filter((item: any) => item.value === value[0])[0]?.label
              : `${list.filter((item: any) => item.value === value[0])[0]?.label} 외 ${value.length - 1}개`)}
        </button>
        <DrawerStyled anchor="bottom" open={isModalVisible} onClose={handleCloseModal}>
          <Box sx={{ display: 'flex', alignItems: 'left', padding: '24px 16px 16px' }}>
            {title && <p>{title}</p>}
            <IconButtonStyled sx={{ marginLeft: 'auto' }} onClick={handleCloseModal}>
              <IconClose />
            </IconButtonStyled>
          </Box>
          <Box sx={{ padding: '0 16px 16px', alignItems: 'left', overflowY: 'auto' }}>{setOptionList()}</Box>
          {multiple && (
            <Box>
              <BaseButton variant="boxPurple" onClick={handleMultiComplete} disabled={btnDisabled} fullWidth>
                완료
              </BaseButton>
            </Box>
          )}
        </DrawerStyled>
      </div>
    </RootStyled>
  );
};
export default BaseSelectWithMulti;
```
```js
// 단일 선택 BaseSelect
<FormControl variant="standard">
  <FormLabel htmlFor="contentId">단일선택</FormLabel>
  <BaseSelectWithMulti
    value={selectOne}
    title="지역 선택"
    list={selectOntList}
    onChange={handleSelectOneChange}
  />
</FormControl>

// 멀티 선택 BaseSelect
<FormControl variant="standard">
  <FormLabel htmlFor="contentId">다중선택</FormLabel>
  <BaseSelectWithMulti
    value={selectMulti}
    originValue={originSelectMulti}
    title="근무 업종 선택"
    list={selectMultiList}
    onComplete={handleSelectMultiComplete}
    onChange={handleSelectMultiChange}
    multiple={true}
  />
</FormControl>
```

## Notion 관련 Library
### react-notion-x
- 필요한 package
  - react-notion-x (https://github.com/NotionX/react-notion-x)
  - notion-client
- 유의사항
  - notion-client를 사용하기 때문에 SSR로 데이터를 호출해야 합니다.
  - notion page가 public page로 open되어 있어야 하며 private page은 notion-client에 인증키를 세팅해서 호출해야 합니다.
  - notion page의 style을 사용하기 위해서 반드시 styles.css를 import 해줘야 합니다.\
  `import 'react-notion-x/src/styles.css';`
  - notion page 내 기능이나 link 가 동작하지 않기 때문에 정적인 컨텐츠를 보여줄 때 사용합니다.
  - notion page 기능을 사용하려면 notion page를 popup으로 보여주는게 좋습니다.
- 샘플 코드
  ```js
  import 'react-notion-x/src/styles.css';

  // SSR 방식으로 데이터 호출
  export const getServerSideProps: GetServerSideProps = async () => {
    const notionApi = new NotionAPI();
    const recordMap = await notionApi.getPage(pageId || '');

    return { props: { recordMap } };
  };

  const ClubMySet = ({ recordMap }: InferGetServerSidePropsType<typeof getServerSideProps>) => {
    ...
    return (
      ...
      <div>
        // 데이터 렌더링
        <NotionRenderer recordMap={recordMap} fullPage={false} darkMode={false} />
      </div>
      ...
    )
  }
  ```
### react-notion
- 추가 : `react-notion` 보다 `react-notion-x`가 더 활용성이 좋다.
- 사용하는 Package
  - react-notion (https://github.com/splitbee/react-notion)
  - npm 버전에 따라 build가 되지 않을 수 있으니 node/npm 버전을 확인해야 한다.
  - notion page의 style을 제대로 적용하기 위해서 아래 styles.css를 import 해야 한다.\
  `import "react-notion/src/styles.css";`
- 예제
  - 새로운 창으로 띄우기 : `window.open(link, '', '_blank');`
    ```js
    <Link href="/notion/page">
      <Button variant="outlined"></Button>
    </Link>

    //-------------------------------------------------------------//
    import 'react-notion/src/styles.css';
    import 'prismjs/themes/prism-tomorrow.css';
    import React, { useState, ReactElement, useEffect } from 'react';

    import { NotionRenderer } from 'react-notion';

    const NoticePage = (): ReactElement => {
      const [notionData, setNotionData] = useState({});

      useEffect(() => {
        fetch('https://notion-api.splitbee.io/v1/page/${NOTION_PAGE_ID}')
          .then((response) => response.json())
          .then((json) => {
            setNotionData(json);
          });
      }, []);

      return (
        <div className="noticePage">
          {Object.keys(notionData).length ? (
            <NotionRenderer
              blockMap={notionData}
              fullPage={true}
              hideHeader={true}
            />
          ) : (
            <div>static page</div>
          )}
        </div>
      );
    };
    export default NoticePage;
    ```

## i18n 언어팩 적용
- `package.json` 에 i18next 관련 dependency 추가
  ```json
  {
    ...
    "i18next": "^21.6.14",
    "i18next-browser-languagedetector": "^6.1.4",
    "react-i18next": "^11.16.2",
    ...
  }
  ```
- `i18n.ts` 파일 생성해서 초기화
  ```js
  import i18n from "i18next";
  import LanguageDetector from "i18next-browser-languagedetector";
  import { initReactI18next } from "react-i18next";

  // transfer file for each languange
  import translationko from "./locales/ko/translation.json";
  import translationen from "./locales/en/translation.json";
  // Add this line to your app entrypoint. Usually it is src/index.js
  // import './i18n';

  // https://react.i18next.com/latest/i18next-instance
  // https://react.i18next.com/latest/using-with-hooks#using-the-withtranslation-hoc
  i18n
    // load translation using xhr -> see /public/locales
    // learn more: https://github.com/i18next/i18next-xhr-backend
    // .use(Backend)
    // detect user language
    // learn more: https://github.com/i18next/i18next-browser-languageDetector
    .use(LanguageDetector)
    // pass the i18n instance to react-i18next.
    .use(initReactI18next)
    // init i18next
    // for all options read: https://www.i18next.com/overview/configuration-options
    .init({
      detection: {
        lookupQuerystring: "locale",
        lookupCookie: "lang",
        lookupLocalStorage: "lang",
      },
      resources: {
        ko: { translation: translationko }, // Korean
        en: { translation: translationen }, // English
      },
      fallbackLng: "ko",
      debug: process.env.REACT_APP_I18N_DEBUG === "true",
      react: {
        useSuspense: false,
      },
    });

  export default i18n;
  ```

- `index.tsx`에 i18n 초기화
  ```js
  import "react-app-polyfill/ie9";
  import "react-app-polyfill/ie11";
  import "react-app-polyfill/stable";

  import "./i18n";

  import React from "react";
  import ReactDOM from "react-dom";
  import "./index.scss";
  import App from "./App";
  import reportWebVitals from "./reportWebVitals";

  ReactDOM.render(
    <React.StrictMode>
      <App />
    </React.StrictMode>,
    document.getElementById("root")
  );

  // If you want to start measuring performance in your app, pass a function
  // to log results (for example: reportWebVitals(console.log))
  // or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
  reportWebVitals();
  ```

- 언어 변경 이벤트 적용 (MUI 사용)
  ```js
  import MenuItem from "@mui/material/MenuItem";
  import FormControl from "@mui/material/FormControl";
  import Select, { SelectChangeEvent } from "@mui/material/Select";

  import { useTranslation } from "react-i18next";

  function navBar {
    const { t, i18n } = useTranslation();
    const [lang, setLang] = React.useState(i18n.language);
    const changeLaunage = (event: SelectChangeEvent) => {
      i18n.changeLanguage(event.target.value);
      setLang(event.target.value);
    };

    return (
      <div className="langBtn">
        <FormControl sx={{ m: 1, minWidth: 50 }}>
          <Select
            value={lang}
            onChange={changeLaunage}
            inputProps={{ "aria-label": "Without label" }}
          >
            <MenuItem value="ko">KOR</MenuItem>
            <MenuItem value="en">ENG</MenuItem>
          </Select>
        </FormControl>
      </div>;
    )
  }

  ```

- 언어팩을 적용할 Component에 `useTranslation` 추가
  ```js
  ...
  import { useTranslation } from 'react-i18next';
  ...

  const Sample = () => {

    const { t, i18n } = useTranslation();

    return (
      ...
      <div><h1>{t('test.title.main')}</h1></div>
    );
  };

  export default Sample;
  ```

- 각 언어에 맞는 번역 json 추가
  ```json
  // ./locales/ko/translation.json
  {
    "test": {
      "title": {
        "main": "한국어 메인입니다!"
      }
    }
  }
  ```

  ```json
  // ./locales/en/translation.json
  {
    "test": {
      "title": {
        "main": "This is English Main!"
      }
    }
  }
  ```


## Sub Component 제어
- Modal을 호출하는 Main Component에서 데이터값 뿐만 아니라 state 함수를 같이 넘겨서 사용한다.
- Modal은 부모의 state를 사용해서 부모에서 선언해놓은 snackbar component를 제어하고 사용한다.
```js
import RequestModal from './RequestModal';

return(
  ...
  <RequestModal 
    isModelOpen={isModelOpen}
    handleModelClose={handleModelClose}
    data={data}
    setSnackBarMessage={setSnackBarMessage}
  />
  ...
)
```
```js
interface RequestModalProps {
  isModelOpen: boolean;
  handleModelClose: () => void;
  data: string[];
  setSnackBarMessage: (isSuccess: boolean, message: string) => void;
}

export default function RequestModal({
  isModelOpen,
  handleModelClose,
  data,
  setSnackBarMessage
}: RequestModalProps): React.ReactElement {
  const style = {
    position: 'absolute' as const,
    overflow: 'scroll',
    top: '50%',
    left: '50%',
    transform: 'translate(-50%, -50%)',
    width: '80%',
    maxWidth: 500,
    bgcolor: 'background.paper',
    boxShadow: 24,
    p: 4,
  };

  const [userInput, setUserInput] = userState<string>('');
  ...

  userEffect(()=>{
    setUserInput(data[0]);
  }, [data]);

  return (
    ...
    <TextField
      variant="outlined"
      id="description"
      margin="normal"
      error={!isVaild}
      helperText={isValid ? '' : errorMessage}
      fullWidth
      autoFocus
      value={userInput}
      onChange={(e) => {
        setUserInput(e.target.value);
        checkUserInput(e.target.value);
      }}
    />
    ...
  )

}
```

## Autocomplete
```js
...
const filter = createFilterOptions<Team>();

return (
  ...
  <Autocomplete
    id="combobox"
    options={data.team}
    getOptionLabel={(option)=>option.name || ''}
    value={data.team.filter((team) => team === member.team?.name) || ''}
    renderInput={(params) => <TextField { ...params} label="팀" />}
    sx={{width: 300}}
    disabled={isTeam ? true : false}
    filterOptions={(options, params) => {
      const filtered = filter(options, params);

      if(params.inputValue !== '' && !data.team.map((team) => team.name).includes(params.inputValue)){
        filtered.push({
          // new Team props
          name: `Add "${params.inputValue}"`,
          avtivated: true,
        });
      }
      return filtered;
    }}
  />

  ...
)
```

## react-mentions
- Library 추가
  - yarn add @types/react-mentions
  - yarn add react-mentions
  
- 예제
  ```js
  <MentionsInput
    value={mentionValue}
    onChange={(e) => setMentionValue(e.target.value)}
    onKeyDown={function(event)}
    singleLine={true|false}
    onBlur={function(event, clickedSuggestion)}
    allowSpaceInQuery={true|false}
    suggestionsPortalHost={DOM Element}
    inputRef={React ref}
    allowSuggestionsAboveCursor={true|false}
    forceSuggestionsAboveCursor={true|false}
    a11ySuggestionsListLabel={string}
    customSuggestionsContainer={function(children)}
  >
    <Mention
      trigger="$"
      data={users}
      renderSuggestion={function (entry, search, highlightedDisplay, index, focused)}
      /* renderSuggestion={(suggestion, search, highlightedDisplay) => (
          <div className="user">{highlightedDisplay}</div>
        )} */
      markup="@__display__"
      /* 여러개의 Mention에 displayTransform, style을 적용하려면 markup 값이 달라야 한다.*/
      displayTransform={function (id, display)}
      /* displayTransform={(display) => `${"$" + display}`} */
      regex={RegExp}
      onAdd={function (id, display, startPos, endPos)}
      appendSpaceOnAdd={true|false}
    />
  </MentionsInput>
  ```
- git repo : https://github.com/signavio/react-mentions

## infinite scroll
- 사용하는 Package
  - react-infinite-scroll-component
- Sample Code
  - InfiniteScrollModule.tsx
    ```js
    import React from 'react';

    import InfiniteScroll from 'react-infinite-scroll-component';

    interface Props {
      dataList: any;
      fetchMoreData: () => void;
      showListItem: (props: any) => React.ReactNode;
      scrollThreshold?: number;
      repeatCss?: any;
    }

    const InfiniteScrollModule: React.FC<Props> = ({
      dataList,
      fetchMoreData,
      showListItem,
      scrollThreshold,
      repeatCss,
    }: Props) => {
      return (
        <React.Fragment>
          {dataList && (
            <InfiniteScroll
              dataLength={dataList.length}
              next={fetchMoreData}
              hasMore={true}
              loader={<></>}
              scrollThreshold={scrollThreshold || 1}
              scrollableTarget="infiniteScrollDiv"
            >
              {dataList.map((item: any, idx: number) => {
                return (
                  <React.Fragment key={idx}>
                    <div style={repeatCss}>{showListItem(item)}</div>
                  </React.Fragment>
                );
              })}
            </InfiniteScroll>
          )}
        </React.Fragment>
      );
    };

    export default React.memo(InfiniteScrollModule);
    ```
  - Module 사용
    ```js
    <InfiniteScrollModule
                    dataList={feedList}
                    fetchMoreData={() => handleScrollEnd()}
                    showListItem={showListItem}
                    scrollThreshold={0.75}
                    repeatCss={{ marginTop: '12px' }}
                  />
    ```

## 간단한 문법 메모
```js
/*
* javascript의 map은 java stream의 map과는 다르다.
* 객체의 속성값을 변경하거나 일괄 처리를 하고 싶을 때 아래와 같이 사용한다.  
*/
const data = File[];
data.files
  .filter((item) => item.fileUrl.startsWith('/IMAGE'))
  .map((item) => {
    return { ...item, fileUrl: bucketBaseUrl + item.fileUrl };
  })
```
```js
/*
* .map과 await 같이 쓰기 (Notion & SSR)
*/
interface NotionPageContents {
  title: string;
  contents: ExtendedRecordMap;
}

export const getServerSideProps: GetServerSideProps = async () => {
  const notionApi = new NotionAPI();
  const notionPageList: NotionPageContents[] = await backApi.getContents(ContentsTypeCode.FEED).then((response) => {
    if (response) {
      return Promise.all(
        response
          .filter((item) => item.notionPageUrl)
          .map(async (item) => {
            const notionPageContents = item.notionPageUrl ? await notionApi.getPage(item.notionPageUrl);
            return {
              title: item.title,
              contents: notionPageContents,
            } as NotionPageContents;
          })
      );
    } else {
      return [];
    }
  });
  return { props: { notionPageList } };
}
```
```js
interface NotionPageContents {
  title: string;
  contents: ExtendedRecordMap;
}

export const getServerSideProps: GetServerSideProps = async () => {
  if (!context.query.contents) {
    return {
      redirect: {
        destination: '/login',
        permanent: false,
      },
    };
  }
  const notionApi = new NotionAPI();
  const contentsList: Contents[] = JSON.parse(context.query.contents as string);
  const notionPageList: NotionPageContents[] = await Promise.all(
    contentsList.map(async (item) => {
      const recordMap = item.notionPageUrl ? await notionApi.getPage(item.notionPageUrl) : '';
      return {
        title: item.title,
        contents: notionPageContents,
      } as NotionPageContents;
    })
  );
  return { props: { notionPageList } };
}
```

---
