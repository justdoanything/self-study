# React
- [실제 프로젝트에서 경험했던 기술과 고민](#실제-프로젝트에서-경험했던-기술과-고민)
- [React 시작지점](#react의-시작지점-파악해보기)
- [React 기존지식](#react-기본지식)
- [My React Library](#my-react-library)
  - [i18n 언어팩 적용](#i18n-언어팩-적용)
  - [Sub Component 제어](#sub-component-제어)
  - [Autocomplete](#autocomplete)
---

# 실제 프로젝트에서 경험했던 기술과 고민
- React 상태 관리 : Redux, Mobx
- Store를 사용하지 않고 Props로만 관리하는 것의 차이
	- React 컴포넌트에서 받아야 하는 Props가 너무 많아짐.
	- Store(상태 관리하는 구조체)를 쓰는 이유는 Store 안에 큰 컴포넌트 단위로 각각의 데이터 구조를 다 갖고 있음.

- ### formik
  - 여러 form을 처리해주기 쉽게 만드는거.
	- focus = touch로 (form 이 터치가 됐느냐)
	- error = error로 잡아줌 (form에 값이 쓰여져있느냐)

- ### yup
  - form에 대한 에러를 잡는 기준을 정해주는것.
  - formik 안에 yup를 넣어서 yup이 에러 기준을 잡고 에러가 발생하면 formik이 감지함.

- ### Store 단점
	- 폼이 너무 많으면 폼을 쪼개기가 어렵다.
	- 에러, 검증 값을 처리할때 보일러 플레이트 코드가 많다.

- ### React로 구현하다가 부딪힌 점 
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

--- 
# React의 시작지점 파악해보기
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

---
# React 기본지식
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
    <h3 id="title" onMouseEnter={() => console.log("mouse enter")}>
      Hello! World!
    </h3>
  );
</script>
```

#### html

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

#### Container

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

#### Add Event Listner

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
#### useState & useEffect

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
    useEffect(() => {
      if (keyword.length > 5) console.log("keyword is changes");
    }, [keyword]);
    ```

#### Cleanup
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

#### Array of useState
- Array에 Array를 더하는 방법
  ```js
  const arraySample = [1,2,3];
  const useState[value, setValue] = useState([]);
  setValue((currentArray) => [value, ...currentArray]);
  ```
#### fetch

```js
fetch("https://url", {
  method: "POST",
  headers: {
    "Content-Type": "application/json",
    "Access-Control-Allow-Origin": "*",
  },
  body: JSON.stringify({
    fromEmail: "yongwoo.leee@lgcns.com",
    toEmail: ["yongwoo.leee@lgcns.com"],
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

---

# My React Library
### i18n 언어팩 적용
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
---
### Sub Component 제어
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

---

### Autocomplete
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
---

### Reference
- [Nomadcoder's ReactJS로 영화 웹 서비스 만들기](https://nomadcoders.co/react-for-beginners/lobby)
