# 블로그 검색

---

## 개발환경
- Language
  - Java (Openjdk 11)
  - SpringBoot 2.7.9
- IDE : Intellij
- OS : macOS Monterey

## 사용한 외부 라이브러리
- ### H2
  - http://localhost:8080/h2-console
  - 인메모리 DB로 사용하기 위해서 사용했습니다.
- ### Flyway
  - 데이터베이스의 변경 관리를 위해서 사용했습니다.
  - 초기 테이블 생성 및 데이터 적재를 위해 사용했습니다.
- ### Swagger
  - http://localhost:8080/swagger-ui/index.html
  - API 명세를 작성하고 Controller 레벨의 단위 테스트를 할 때 사용했습니다.
  - 보편적으로 사용하는 Authorization, SESSION_ID 기본값으로 추가했습니다. 
- ### Hystrix
  - 카카오 API가 동작하지 않거나 timeout이 발생했을 때 fallback 처리로 네이버 API를 호출하고 응답하는 부분에서 사용했습니다.
- ### gson, validation, httpclient, lombok
  - 공통 모듈 및 데이터 처리를 위해서 사용했습니다.

## 구현 기능
- ### 기본적으로 `8080` port를 오픈하고 있으며 profile은 따로 지정 안해도 default가 세팅되어 있습니다. 
- ### 카카오 블로그 검색
  - #### URL : `/v1/kakao/blogs`
  - #### Method : `GET`
   #### Request Parameters
 
    | Name | Type | Description | Required |
    |-----| --- | --- | :---: |
    | blogUrl | String | 특정 블로그 URL | |
    | keyword | String | 블로그를 검색하는 기준 키워드 | V |
    | pageSize | int | Page 단위로 조회 시 한 번에 가져올 Page의 크기 (기본값: 10) | |
    | sortType | String | 블로그를 조회하는 정렬 기준 (기본값: ACCURACY) | |
    | start | int | Page 단위로 조회 시 데이터 조회 시작 건 수 (기본값: 1) | |
    
- ### 네이버 블로그 검색
  - #### URL : `/v1/naver/blogs`
  - #### Method : `GET`
   #### Request Parameters
    
    | Name | Type | Description | Required |
    |-----| --- | --- | :---: |
    | keyword | String | 블로그를 검색하는 기준 키워드 | V |
    | pageSize | int | Page 단위로 조회 시 한 번에 가져올 Page의 크기 (기본값: 10) | |
    | sortType | String | 블로그를 조회하는 정렬 기준 (기본값: ACCURACY) | |
    | start | int | Page 단위로 조회 시 데이터 조회 시작 건 수 (기본값: 1) | |
    

- ### 인기 검색어 검색
  - #### URL : `/v1/popular-keyword`
  - #### Method : `GET`
  - #### Request Parameters

    | Name | Type | Description | Required |
    |-----| --- | -- | :---: |
    | period | String | 인기 검색어를 검색하는 기간 (기본값: ALL) | |
    | size | String | 인기 검색어 수 (기본값: 5) | |

## 부가 기능
- 카카오 API/네이버 API에 장애가 있을 경우 네이버 API/카카오 API를 사용하도록 구현했습니다. (추가적으로 네이버 블로그를 검색할 수 있는 API도 별도로 구현했습니다.)
- 실제 환경을 고려해서 profile을 local/dev/stg/prd로 나누고 flyway도 환경마다 적용할 수 있게 구현했습니다.
- 공통처리를 위한 Interceptor Class를 만들어서 처리했고 필요한 로직을 추가할 수 있도록 TODO 표시했습니다.
  - `prj.searching.config.InterceptorConfig.java`
  - `prj.searching.interceptor.AuthInterceptor.java`
- Dockerfile를 사용해서 docker image를 생성해서 사용할 수 있습니다.
- Swagger UI 화면을 통해서 태스트를 할 수 있습니다. (http://localhost:8080/swagger-ui/index.html)

## 빌드 결과물
- Download : https://github.com/justdoanything/modernBackend/blob/main/jar/searching-0.0.1.jar
