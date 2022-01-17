# Memo.md
###### React를 공부하면서 궁금한 점이나 알아두면 좋을 점을 메모하고 trouble shooting에 대한 history

---
## React의 시작지점

- 프로젝트를 처음 install 후 무작정 실행해보면
  1️⃣ public/index.html 
  　- There is `<div id="root"></div>`
  2️⃣ src/index.js
  　- `import './index.css';`
  　- Rendering `<App />`
  3️⃣ src/App.js 
  　- `import './App.css';`
  　- return `<div>` code
  　- it should be shown

- 시작 파일이 index.html 이라는건 어디에 명시되있을까? 이 파일 이름을 바꾸고 싶으면 어떻게 해야할까?
  - index.html 파일 이름을 변경
    - public/index.html 파일을 찾을 수 없다는 에러 발생
      > Error: Child compilation failed:\
  Module not found: Error: Can't resolve 'D:\Dev\Yongwoo\self-study\self-study\do-react\public\index.html' in 'D:\Dev\Yongwoo\self-study\self-study\do-react'\
  ModuleNotFoundError: Module not found: Error: Can't resolve 'D:\Dev\Yongwoo\self-study\self-study\do-react\public\index.html' in 'D:\Dev\Yongwoo\self-study\self-study\do-react'\
      at D:\Dev\Yongwoo\self-study\self-study\do-react\node_modules\webpack\lib\Compilation.js:2011:28\
      at D:\Dev\Yongwoo\self-study\self-study\do-react\node_modules\webpack\lib\NormalModuleFactory.js:795:13\
      at eval (eval at create (D:\Dev\Yongwoo\self-study\self-study\do-react\node_modules\tapable\lib\HookCodeFactory.js:33:10), <anonymous>:10:1)\
      at D:\Dev\Yongwoo\self-study\self-study\do-react\node_modules\webpack\lib\NormalModuleFactory.js:275:22\
      at eval (eval at create (D:\Dev\Yongwoo\self-study\self-study\do-react\node_modules\tapable\lib\HookCodeFactory.js:33:10), <anonymous>:9:1)\
      at D:\Dev\Yongwoo\self-study\self-study\do-react\node_modules\webpack\lib\NormalModuleFactory.js:431:22\
      at D:\Dev\Yongwoo\self-study\self-study\do-react\node_modules\webpack\lib\NormalModuleFactory.js:124:11\
      at D:\Dev\Yongwoo\self-study\self-study\do-react\node_modules\webpack\lib\NormalModuleFactory.js:667:25\
      at D:\Dev\Yongwoo\self-study\self-study\do-react\node_modules\webpack\lib\NormalModuleFactory.js:852:8\
      at D:\Dev\Yongwoo\self-study\self-study\do-react\node_modules\webpack\lib\NormalModuleFactory.js:972:5
    - node_modules\webpack\lib\NormalModuleFactory.js 에 대한 정보 검색
      - https://webpack.js.org/api/normalmodulefactory-hooks/
      - Err line을 추적하다보면 `if (err) return callback(err);` 함수로 들어가서 에러는 반환하고 있음.
      - node_modules\tapable\lib\HookCodeFactory.js
        - `create(options)` 함수에 `switch (this.options.type) { case "async"` 로 연결
      - `this.hooks.factorize.callAsync(resolveData, (err, module) => {`
      - node_modules\webpack\lib\Compilation.js
        - `_factorizeModule(` → `factory.create(` → `if (err) { const notFoundError = new ModuleNotFoundError( return callback(notFoundError, factoryResult ? result : undefined);`
      - ###### NormalModuleFactory Hooks
        >_The NormalModuleFactory module is used by the Compiler to generate modules. Starting with entry points, it resolves each request, parses the content to find further requests, and keeps crawling through files by resolving all and parsing any new files. At last stage, each dependency becomes a Module instance._\
        \
        _factorize
      　AsyncSeriesBailHook\
      　Called before initiating resolve. It should return undefined to proceed.\
      　Callback Parameters: resolveData
      - ###### Compiler Hooks
        >_The Compiler module is the main engine that creates a compilation instance with all the options passed through the CLI or Node API. It extends the Tapable class in order to register and call plugins. Most user-facing plugins are first registered on the Compiler. When developing a plugin for webpack, you might want to know where each hook is called. To learn this, search for hooks.<hook name>.call across the webpack source._
      - ### 최종결론
        > NormalModuleFactory.js 에서 context 기준으로 Resource를 가져오고 factory를 create 하는데 index.html 이라는 파일이 정해진 경로에 없어서 callback 함수에서 notFoundError 에러를 반환하면서 해당 에러가 발생했다.\
        \
        context에 public/index.html은 고정적으로 들어가있는 것으로 보이며 가시적으로 어떤 index.html 이라는 값을 확인할 수 없었고 변경도 어려운 것으로 보인다.
        
---

### Reference
[Nomadcoder's ReactJS로 영화 웹 서비스 만들기](https://nomadcoders.co/react-for-beginners/lobby)