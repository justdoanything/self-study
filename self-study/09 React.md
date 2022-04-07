# React

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
    useEffect(() => {
      if (keyword.length > 5) console.log("keyword is changes");
    }, [keyword]);
    ```

---

# Cleanup

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

# i18n 언어팩 적용

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

### Practice Code

- [Repository : self-study/self-study/do-react](https://github.com/justdoanything/self-study/tree/main/self-study/do-react)

---

### Reference

- [Nomadcoder's ReactJS로 영화 웹 서비스 만들기](https://nomadcoders.co/react-for-beginners/lobby)
