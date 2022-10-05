Test-Automation
===
- [TDD vs BDD vs ADD vs ATDD](#tdd-bdd-ddd-atdd)
  - [TDD](#tdd-test-driven-development)
  - [BDD](#bdd-behavior-driven-development)
  - [TDD vs BDD](#tdd-vs-bdd)
  - [DDD](#ddd-domain-driven-development)
  - [ATDD](#atdd-acceptance-test-driven-development)
- [Test 종류](#test-종류)
  - [Unit Test](#unit-test)
  - [Unit Test Code Coverage](#unit-test-code-coverage)
  - [Module Test](#module-test)
  - [Health Check](#health-check)
  - [Smoke Test](#smoke-test)
- [Code Linting](#code-linting)
- [Spring Test](#spring-test)
  - [@Mock](#mock)
---
- # TDD, BDD, DDD, ATDD
  - ###### TDD (Test-Driven Development)
    - 짧은 개발 사이클을 반복하는 SW 개발 프로세스 중에서 요구사항을 검증하는 자동화된 테스트 케이스를 먼저 작성합니다. 테스트 케이스를 통과하기 위한 최소한의 코드를 생성하고 코드에 대한 리팩토링을 진행합니다. 
    - TDD 과정
      1. Test case 작성 (Bad case 위주로 작성하는게 좋음)
      2. Test case에 대한 Main code 작성
      3. Test case 수행
      4. Test code 및 Main code 리팩토링\
         `새로운 Test case가 추가되면 a~d 반복`
    - TDD 장점
      - 개발과 테스트를 위한 주기를 단축시켜 주고 유지보수 포인트를 줄여줍니다.
      - 기존 기능을 아무것도 깨지 않고 리팩터링이나 코드를 수정할 수 있게 합니다.
      - Test case 수행 결과가 산출물이 되기도 합니다.
  - ###### BDD (Behavior-Driven Development)
    - Test case가 아니라 Application Behavior에 대한 부분을 작성합니다.
    - TDD는 구현 세부 사항을 확인하기 위한 단위 테스트에 의존하지만 BDD는 동작을 확인하기 위해 실행 가능한 시나리오에 중점을 둡니다.
    - BDD는 프로그래밍 언어로 작성하는 것이 아닌 비개발자도 이해할 수 있는 공통적인 언어로 작성해야 합니다.
    - BDD 시나리오의 3가지 중요 포인트
      1. Given : 동작이 트리거되기 전의 환경 (전제 조건) 상태를 설명합니다.
      2. When : 행동을 유발하는 행동을 설명합니다.
      3. Then : 행동의 예상 결과를 설명합니다.
    - TDD는 일반적으로 단위 수준의 테스트이고 BDD는 시스템 수준의 테스트입니다.
    - BDD 과정
      1. Application Behavior 작성
      2. Automation Script 작성 (프로그래밍 언어가 아니라 자연어(영어 등)로 작성)
      3. Main code 작성
      4. Behavior 수행
      5. Behavior 및 Code 리팩토링\
        `새로운 Test case가 추가되면 a~e 반복`
    - ###### TDD vs BDD
      TDD | BDD
      ---|:---
      유형(기준) 없음 / 배치 불가능 | 사용자 작업에 의해 주도되는 프로젝트에 대한 더 나은 접근 방식 일 수 있습니다. 예 : 전자 상거래 웹 사이트, 애플리케이션 시스템 등
      테스트 주도 개발을 의미합니다. | 행동 중심 개발을 의미합니다.
      프로세스는 테스트 케이스를 작성하는 것으로 시작됩니다. | 프로세스는 예상되는 동작에 따라 시나리오를 작성하는 것으로 시작됩니다.
      TDD는 기능이 구현되는 방법에 중점을 둡니다.	| BDD는 최종 사용자를위한 애플리케이션의 동작에 중점을 둡니다.
      테스트 케이스는 프로그래밍 언어로 작성됩니다.	| 시나리오는 간단한 영어 형식으로 작성되므로 TDD와 비교할 때 더 읽기 쉽습니다.
      애플리케이션 기능이 TDD의 테스트 케이스에 많은 영향을 미치는 방식 변경.	| BDD 시나리오는 기능 변경의 영향을 많이받지 않습니다.
      개발자 간의 협업 만 필요합니다.	| 모든 이해 관계자들 간의 협력이 필요합니다.
      TDD를 지원하는 일부 도구는 JUnit, TestNG, NUnit 등입니다.	| BDD를 지원하는 도구로는 SpecFlow, Cucumber, MSpec 등이 있습니다.
      TDD의 테스트는 프로그래밍 지식이있는 사람 만 이해할 수 있습니다. | BDD의 테스트는 프로그래밍 지식이없는 테스트를 포함한 모든 사람이 이해할 수 있습니다.
      TDD는 테스트에 버그가있을 가능성을 줄입니다. | 테스트의 버그는 TDD와 비교할 때 추적하기 어렵습니다.
  - ###### DDD (Domain-Driven Development)
    - 각 비니지스 도메인을 중심으로 되어 진행하는 개발 방식
    - 데이터 중심이 아니라 도메인 모델과 로직에 집중하는 것을 말합니다.
    - 도메인 전문가와 개발자 간에 커뮤니케이션을 위해 보편적인 언어로 정의합니다.
    - 분석, 설계, 구현까지 통일된 방식으로 커뮤니케이션 가능하도록 합니다.
    - DDD의 핵심 목표는 낮은 의존성과 높은 응집성입니다.
    - 기술보다 도메인이 더 높은 우선순위를 가져야하고 분석 모델링부터 코드까지 항상 같이 움직이는 모델 구조를 지향
    
  - ###### ATDD (Acceptance Test-Driven Development)
    - 인수 테스트 케이스를 중심으로 개발하는 방식이고 인수 테스트는 사용자 스토리를 기반으로 도출합니다.
    - TDD는 개발자 중심이지만 BDD, ATDD는 고객 중심입니다.
    - ATDD는 단순히 인수 테스트를 중점에 두는 것은 아니고 `사용자(고객)-개발자-테스터`간의 커뮤니케이션을 기반한 개발 방법이라고 보면 됩니다. 인수 테스트는 이 커뮤니케이션을 위한 도구일 뿐입니다.
    - `사용자(고객)`들에게 요구사항을 명확히 하는데 도움을 주고, `개발자`에게는 코드 구현의 방향성과 목적을 가지는데 도움을 주고, `테스터`에게는 단순히 기능적 테스트를 하는 것 보다 좀 더 나은 계획을 세울 수 있게 도와줍니다. 결국 ATDD는 `팀원간에 요구사항을 같은 수준으로 이해`하는데 큰 도움을 줍니다.
    - ATDD의 `4가지의 행위`와 `4가지의 산출물` 그리고 `하나의 결과`이다.
      - 행위 : 토론(Discuss), 추출(Distill), 개발(Develop), 시연(Demo)
      - 산출물 : 사용자 스토리(User Story), 인수 기준(Acceptance Criteria), 테스트(Test), Working Software
      - 결과 : Business Value

---

- ## Test 종류
  종류 | Unit Test | Module Test | Health Check | Smoke Test
  :---:|---|---|---|---
  설명 | Method 테스트 | Test Case 기준으로 테스트(Acceptance Criteria) | Service가 정상구동 중인지 상태를 확인 | 주요 기능을 중심으로 Dependency를 갖는 모든 Service, Module 연결 확인
  목적 | Method가 정상 구현되었는지 확인 | 화면 Module, API에 대한 정상 동작 여부 확인 (외부 Dependency가 있으면 Mocking 처리) | 해당 Service 상태 체크, 연동이 필요한 관련 Service와의 연동 상태가 정상인지 확인 | 주요 기능을 중심으로 Service, Module 간 연동 기능이 정상 동작하는지 확인
  범위 | 로직을 갖고 있는 함수 | FE UI 상의 모든 기능, BE의 모든 API | Service 실행에 필요한 Micro Service, Resource 등 | 주요 핵심 기능
  - 실행 구분
    - 배포 하기 전 : Unit Test, Module Test
      - Local 개발할 때 마다
      - Feature Branch Push 할 때 마다
      - Pull Request 할 때 마다
    - 배포한 후 : Health Check, Smoke Test
      - Dev 환경에 배포될 때
      - Staging 환경에 배포될 때
      - Production 환경에 배포될 때
  - ### Unit Test
    - Class, Function 단위로 내부 로직을 검증하는 테스트 단계이다.
    - 개발자가 개발 단계에서 Test Code를 만들어서 수행해야 한다.
    - 반복적인 검증을 통해 기능 완성도를 확보하고 예외사항을 사전에 검증할 수 있다.
    - Testing Tools
      - React : React Testing Library
      - Javascript/Typescript : Jestjs, Mochajs, Sinonjs
      - Java : Spring Boot Test
      - Python : unittest, behave
      
  - ### Unit Test Code Coverage
    - Unit Test를 실행하고 테스트 된 기능 코드의 범위를 알 수 있다.
    - 개발자가 개발 단계에서 Unit Test 결과와 함께 Coverage를 확인한다.
    - 테스트한 기능과 테스트가 안된 기능 코드를 식별할 수 있다.
    - Coverage Tools
      - Javascript/Typescript : Jest
      - Java : JaCoCo
      - Python : pytest, coverage

  - ### Module Test
    - Application에서 기능을 수행하는 단위 중에서 Function, Method 레벨의 상위 단계인 단독으로 기능을 수행할 수 있는 단위를 Module, Component 라고 한다.
    - Module Test는 단일 Module의 기능이 정상 동작하는지 확인하는 것으로 기능성, 정합성, 경함여부 등을 테스트한다.
    - Front-End의 Module Test : UI 형태의 테스트
    - Back-End의 Module Test : API 형태의 테스트
    - 개발 단계에서 수행해야 하며 Mocking을 사용하여 타 Module과의 연동을 제외합니다. 
    - 프로그램의 각 부분을 Module로 나누고 각각의 부분이 정확하게 동작하는지 확인할 수 있다.
    - Test Code를 자동화하며 반복적으로 수행하며 안정성을 확보한다.
    - Module Testing Tools
      - Front-End : Cypress, Playwright
      - Back-end : Postman
  - ### Health Check
    - 대상 Service를 호출하며 상대 응답이 정상적으로 오는지 확인애햐 한다.
    - 간단하고 빠른 속도로 진행되고 서비스 간의 연결 상태가 정상인지 확인하는 것이 주목적이다.
    - 배포할 때 연관된 서비스와의 연결 상태를 확인해야 한다.

  - ### Smoke Test
    - BVT (Build Verification Testing) : 주요 기능을 중심으로 내외부 연동 기능의 정상을 확인한다.
    - 프로그램의 핵심 기능을 기반으로 외부 연동을 포함하여 설계와 테스트한다.
    - Front-End Smoke Test는 Back-End를 포함할 수 있기 때문에 테스트 대상별로 Test Case를 잘 짜야한다.

---

- ## Code Linting
  - 정적 분석의 한 종류로 어떤 Coding Style 가이드라인을 따르지 않은 패턴으 찾아서 사후에 발생할 수 있는 잠재적 문제점을 사전에 발견할 수 있도록 하기 위해 사용된다.
  - 대부분의 프로그래밍 언어에서 지원하고 컴파일러가 linter를 포함해 컴파일 과정에 lint를 수행하는 경우도 있다.
  - Javascript, Typescript 등 동적이고 느슨한 타입을 가진 언어는 상대적으로 다른 언어에 비해 개발자가 의도하지 않은 에러를 발생시킬 수 있다.
  - linting 도구를 활용하면 코드 실행 전에 발생할 수 있는 여러 문제점을 사전에 찾아 Code 품질을 높일 수 있다.
  - Lint Tool
    - Javascript, Typescript : ESLint
      <details>
        <summary> 📑 ESLint 설정</summary>

        - ESLint는 Node 기반의 프로젝트에서 구동한다.
        - 설치하기
          - `npm install eslint --save-dev`
          - `yarn add eslint --dev`
        - 구성하기
          - 프로젝트 최상위 경로에 `.eslintrc.json` 파일을 만들고 아래처럼 작성한다.
            ```json
            {
              "env": {
                "browser": true,
                "node": true
              },
              "extends": "eslint:recommended",
              "rules": {
                "semi": ["error", "always"],
                "quotes": ["error", "double"]
              }
            }
            ```
          - package.json에 script 추가
            ```json
            "script": {
              "lint": "eslint --config .eslintrc.json --ignore-pattern '**/*.spec.*' --fix './src/**/*.{js,jsx,ts,tsx}'"
            }
            ```
          - 실행허기
            - `npm run lint`
            - `yarn lint`

      </details>
    - Java : SonarQube  
      <details>
        <summary> 📑 SonarQube 설정</summary>

        - SonarQube 설치
          - `docker run -d --name SonarQube -p 9000:9000 -p 9092:9092 sonarqube`
          - http://localhost:9000 접속
          - 초기 계정 정보 : admin/admin
        - SonarQube Module 설치
          - `npm install sonarqube-scanner --save-dev`
        - 사용하기
          - 프로젝트 최상위 경로에 `sonar-project.js` 파일 작성
            ```js
            const sonarqubeScanner = require('sonarqube-scanner');
            sonarqubeScanner({
              serverUrl: 'http://localhost:9000',
              options : {
              'sonar.sources': '.',
              'sonar.inclusions' : 'src/**' // Entry point of your code
              }
            }, () => {});
            ```
          - package.json에 script 추가
            ```json
            "script": {
              "sonar": "node sonar-project.js"
            }
            ```
        - 실행하기
          - `npm run sonar`
          - http://localhost:9000 에 접속해서 결과 확인
        
      </details>
- Reference
  - https://ko.myservername.com/top-10-visual-resume-tools
  - https://boorownie.github.io/1

---
# Spring Test
  - ### @Mock
