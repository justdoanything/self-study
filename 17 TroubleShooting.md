<!-- TOC -->
* [Spring](#spring)
  * [Annotation](#annotation)
  * [Spring Handler VO](#spring-handler-vo)
  * [Bean 간 순환 참조 문제](#bean-간-순환-참조-문제)
  * [GET Parameter에서 한글 깨지는 문제](#get-parameter에서-한글-깨지는-문제)
  * [특정 Class와 필드 이름을 입력 받아서 해당 필드 기준으로 값을 정렬하는 함수](#특정-class와-필드-이름을-입력-받아서-해당-필드-기준으로-값을-정렬하는-함수)
  * [특정 날짜가 시작일, 마감일 사이인지 체크하는 함수](#특정-날짜가-시작일-마감일-사이인지-체크하는-함수)
  * [TCP 통신 - IP 단편화](#tcp-통신---ip-단편화)
* [SQL](#sql)
  * [ORDER BY INTSTR (검색 조건과 가장 많이 일치하는 순으로 정렬하고 싶을 때)](#order-by-intstr--검색-조건과-가장-많이-일치하는-순으로-정렬하고-싶을-때-)
  * [ORDER BY FIELD (특정한 값을 우선적으로 정렬하고 싶을 때)](#order-by-field--특정한-값을-우선적으로-정렬하고-싶을-때-)
  * [ORDER BY CASE (결과값의 첫자리가 숫자 -> 한글 -> 영어 -> 그 외(특수문자)로 정렬하고 싶을 때)](#order-by-case--결과값의-첫자리가-숫자----한글----영어----그-외--특수문자--로-정렬하고-싶을-때-)
  * [IGNORE CASE & INSERT ON DUPLICATATE UPDATE](#ignore-case--insert-on-duplicatate-update)
  * [REGEXP & REPLACE (구분자로 여러 값을 한번에 조회하고 싶을 때)](#regexp--replace--구분자로-여러-값을-한번에-조회하고-싶을-때-)
  * [MySQL Table Lock 해결](#mysql-table-lock-해결)
* [React](#react)
  * [React Component](#react-component)
    * [Function - 핸드폰에 있는 주소록처럼 첫글자로 그룹핑하는 함수](#function---핸드폰에-있는-주소록처럼-첫글자로-그룹핑하는-함수)
    * [MUI - BaseSelect (GroupComponent 다루기)](#mui---baseselect--groupcomponent-다루기-)
    * [MUI - Autocomplete](#mui---autocomplete)
    * [Nextjs - getServerSideProps의 로그인 처리](#nextjs---getserversideprops의-로그인-처리)
  * [무한 스크롤 구현 고려사항](#무한-스크롤-구현-고려사항)
    * [1. 페이지와 컨텐츠의 구성 예시](#1-페이지와-컨텐츠의-구성-예시)
    * [2. 페이징과 정렬 기준](#2-페이징과-정렬-기준)
    * [3. 스크롤 위치 계산을 위해서 컨텐츠는 고정 높이를 가지면 좋습니다.](#3-스크롤-위치-계산을-위해서-컨텐츠는-고정-높이를-가지면-좋습니다)
    * [4. 목록 페이지와 상세 페이지에서 동기화해야 하는 데이터](#4-목록-페이지와-상세-페이지에서-동기화해야-하는-데이터)
    * [5. store에 무한한 데이터를 모두 저장할 수 없습니다.](#5-store에-무한한-데이터를-모두-저장할-수-없습니다)
    * [6. 기타 고려사항](#6-기타-고려사항)
    * [7. 그 외 성능과 관련한 이슈](#7-그-외-성능과-관련한-이슈)
  * [React 자잘한 문법](#react-자잘한-문법)
    * [fetch](#fetch)
    * [enum](#enum)
    * [useEffect - async](#useeffect---async)
    * [useEffect와 router.query](#useeffect와-routerquery)
    * [.map](#map)
    * [.map & await](#map--await)
  * [React Library](#react-library)
    * [Axios](#axios)
      * [async 함수 정의](#async-함수-정의)
      * [Request/Response 정의](#requestresponse-정의)
      * [API 정의](#api-정의)
      * [API 사용](#api-사용)
    * [i18n 언어팩 적용](#i18n-언어팩-적용)
    * [react-notion-x](#react-notion-x)
    * [react-notion](#react-notion)
    * [react-mentions](#react-mentions)
    * [infinite scroll](#infinite-scroll)
    * [Object.keys와 Object.entries (makeQueryString)](#objectkeys와-objectentries--makequerystring-)
    * [`Object.keys`에서 interface의 key를 추출해서 사용할 수 없는 이유](#objectkeys-에서-interface의-key를-추출해서-사용할-수-없는-이유)
    * [Mapped Type](#mapped-type)
    * [Object.keys & Object.entries & Object.values](#objectkeys--objectentries--objectvalues)
    * [Partial & Pick & Omit](#partial--pick--omit)
    * [InView 처리 (react-intersection-observer)](#inview-처리--react-intersection-observer-)
  * [formik & yup & 깊은 복사](#formik--yup--깊은-복사)
    * [formik](#formik)
    * [yup](#yup)
    * [깊은 복사](#깊은-복사)
<!-- TOC -->

---

# Spring

## Annotation
- `@NotNull` : null
- `@NotEmpty` : null, ""
- `@NotBlank` : null, "", " "

## Spring Handler VO
- VO와 DTO를 나눠서 사용하기도 하지만 프로젝트에선 VO만 사용하기로 했다.
- 기본적으로 Front-end와 직접적으로 주고 받는 VO는 ~RequestVO, ~ResponseVO로 명명하고 Request/ResponseVO는 반드시 직접 사용하지 않고 조립해서 사용해야 한다.\
  DB Layer에서 RequestVO를 바로 사용해서도 안되고 SELECT 결과를 바로 ResponseVO로 사용하지 않아야 한다.
- VO는 크게 다음과 같이 나눴다. 테이블과 1대1 매핑되는 `테이블 VO`, Front-end와 통신할 때 사용하는 `RequestVO/ResponseVO`, SELECT 결과를 받는 `Find~VO` (주로 `테이블 VO`를 extends 받아 사용한다.)
- 이 외에 PUT/POST/DELETE에 따라 VO에 Add/Remove/Modify를 붙여서 사용하기도 한다.
- `테이블 VO`을 상속받아서 사용할 땐 @SuperBuilder를 사용한다.
  ```java
  @Data
  @NoArgsConstructor
  @AllArgsConstructor(access = AccessLevel.PRIVATE)
  @SuperBuilder
  public class TableVO { ... }
  
  
  @Data
  @NoArgsConstructor
  @EqualsAndHashCode(callSuper = false)
  @AllArgsConstructor(access = AccessLevel.PRIVATE)
  @SuperBuilder
  public class FindTableVO extends TableVO { ... }
  ```
- VO을 만들 때 사용하는 필드 값이 많다면 Builder 패턴을 사용하는 것보다 생성자를 만들어서 처리하는 것이 좋다. Service 단에 코드가 간결해지고 VO에 값에 대한 조립을 생성자에서 처리할 수 있다.
  ```java
  public class TableResponseVO {
      private String name;
      private String title;
      private String city;
      private List<String> hobby;
  
      public findTableVO(FindTableVO findTableVO) {
          this.name = findTableVO.getName();
          this.title = findTableVO.getTitle();
          this.city = findTableVO.getCity();
          this.hobby = findTableVO.getHobby.stream().map(HobbyResponseVO::new).collect(Collectors.toList());
      }
  }
  ```
- `테이블 VO`에선 다른 VO를 받는 생성자를 사용하면 안되고 다른 VO에서 `toTableVO()` 함수를 만들어서 사용해야 한다.
  ```java
  public class TableRequestVO {
      private String name;
      private String title;
      private String city;
  
      public TableVO toTableVO() {
          return TableVO.builder()
                          .name(this.name)
                          .title(this.title)
                          .city(this.city)
                          .build();
      }
  }
  ```
## Bean 간 순환 참조 문제
  - Reference : https://www.baeldung.com/circular-dependencies-in-spring
  - Spring Context가 모든 Bean을 로드할 때 일련의 순서로 Bean들을 생성한다.\
    만약에 BeanA -> BeanB -> BeanC 로 참조되어 있다면 Spring은 BacnC를 먼저 생성하고 BeanB를 생성하고 BeanC를 생성한다. 만약 순환 참조가 되어 있다면 Spring은 어떤 Bean을 먼저 생성해야할지 정하지 못한다. 이 때 Spring은 BeanCurrentlyInCreationException을 발생시킨다.
  - 이는 주로 constructor injection을 사용했을 때 발생할 수 있는 케이스이다.
  - 간단한 해결방법으론 @Lazy를 사용해서 생성해주면 된다.
    ```java
    @Component
    public class ClassA {
        private ClassB classB;
        
        @Autowired
        public ClassA(@Lazy ClassB classB){
            this.classB = classB;
        }
    }
    ```
  - 두번째 방법으론, 생성자 대신에 Setter/Getter를 사용하면 된다.
    ```java
    @Component
    public class ClassA {
        private ClassB classB;
        
        @Autowired
        public setClassB(ClassB classB){
            this.classB = classB;
        }
        
        public ClassB getClassB() {
            return classB;
        }
    }
    ```
    ```java
    @Component
    public class ClassB {
        private ClassA classA;
    
        @Autowired
        public setClassA(ClassA classA){
            this.classA = classA;
        }
    }
    ```
  - 세번째 방법으론 @PostConstruct 를 사용한 방법이다.
    ```java
    @Component
    public class ClassA {
        @Autowired
        private ClassB classB;
        
        @PostConstruct
        public void init() {
            classB.setClassA(this);
        }
        
        public ClassB getClassB() {
            return classB;
        }
    }
    ```
    ```java
    @Component
    public class ClassB {
        private ClassA classA;
        
        public void setClassA(ClassA classA) {
            this.classA = classA;
        }
    }
    ```

## GET Parameter에서 한글 깨지는 문제
- org.springframework.web.util.`UriUtils.encode("String Query", "UTF-8");`
- `uriComponentsBuilder.build(true).encode().toUri()`
  ```java
  public class KakaoServiceImpl {
    public KakaoBlogResponseDTO getKakaoBlog(KakaoBlogRequestDTO kakaoBlogRequestDTO) {
      try {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + kakaoToken);
      
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(kakaoHost + kakaoUrlBlog)
                        .queryParam("sort", kakaoBlogRequestDTO.getSort())
                        .queryParam("page", kakaoBlogRequestDTO.getPage())
                        .queryParam("size", kakaoBlogRequestDTO.getSize())
                        .queryParam("query", UriUtils.encode(kakaoBlogRequestDTO.getQuery(),"UTF-8"));
        
        HttpEntity<String> kakaoSearchBlogRequest = new HttpEntity<>(headers);
        ResponseEntity<KakaoBlogResponseDTO> kakaoSearchBlogResponse = restTemplate.exchange(
          uriComponentsBuilder.build(true).encode().toUri()
            , HttpMethod.GET
            , kakaoSearchBlogRequest
            , KakaoBlogResponseDTO.class
        );
        
        if (kakaoSearchBlogResponse.getStatusCode() != HttpStatus.OK)
            throw new ApplicationException("Fail to request Kakao API (" + kakaoUrlBlog + ") : StatusCode is " + kakaoSearchBlogResponse.getStatusCode(), StatusCodeMessageConstant.FAIL);
          else
            return kakaoSearchBlogResponse.getBody();
      } catch (Exception e) {
        throw new ApplicationException("Exception in requesting Kakao API (" + kakaoUrlBlog + ")");
      }
    }
  }
  ```

## 특정 Class와 필드 이름을 입력 받아서 해당 필드 기준으로 값을 정렬하는 함수
```java
public static void sortByFieldName(List target, String fieldName, String orderBy) {
    if (ObjectUtils.isEmpty(fieldName)
            || ObjectUtils.isEmpty(orderBy)
            || ObjectUtils.isEmpty(target)) {
        return;
    }

    Comparator comparator =
            (first, second) -> {
                try {
                    Field field = target.get(0).getClass().getDeclaredField(fieldName);
                    field.setAccessible(true);

                    Comparable firstValue = (Comparable) field.get(first);
                    Comparable secondValue = (Comparable) field.get(second);

                    if (CommonConstants.OrderBy.ASC.name().equalsIgnoreCase(orderBy)) {
                        if (firstValue == null && secondValue == null) {
                            return 0;
                        } else if (firstValue == null) {
                            return 1;
                        } else if (secondValue == null) {
                            return -1;
                        }
                        return firstValue.compareTo(secondValue);
                    } else if (CommonConstants.OrderBy.DESC.name().equalsIgnoreCase(orderBy)) {
                        if (firstValue == null && secondValue == null) {
                            return 0;
                        } else if (firstValue == null) {
                            return -1;
                        } else if (secondValue == null) {
                            return 1;
                        }
                        return secondValue.compareTo(firstValue);
                    } else {
                        return 0;
                    }
                } catch (NoSuchFieldException | IllegalAccessException e) {
                    return 0;
                }
            };
    Collections.sort(target, comparator);
}
```

## 특정 날짜가 시작일, 마감일 사이인지 체크하는 함수
```java
public static boolean isBetweenDateTime(
        Temporal targetDateTime,
        Temporal beginDateTime,
        Temporal endDateTime,
        boolean isIncludeToday) {
    if (Objects.isNull(targetDateTime)
            || Objects.isNull(beginDateTime)
            || Objects.isNull(endDateTime)) {
        return false;
    }

    if (targetDateTime instanceof LocalDate) {
        LocalDate targetDate = (LocalDate) targetDateTime;
        LocalDate beginDate = (LocalDate) beginDateTime;
        LocalDate endDate = (LocalDate) endDateTime;

        if (isIncludeToday) {
            return (targetDate.isAfter(beginDate) || targetDate.isEqual(beginDate))
                    && (targetDate.isBefore(endDate) || targetDate.isEqual(endDate));
        } else {
            return targetDate.isAfter(beginDate) && targetDate.isBefore(endDate);
        }
    } else if (targetDateTime instanceof LocalDateTime) {
        LocalDateTime targetDateAndTime = (LocalDateTime) targetDateTime;
        LocalDateTime beginDateAndTime = (LocalDateTime) beginDateTime;
        LocalDateTime endDateAndTime = (LocalDateTime) endDateTime;

        if (isIncludeToday) {
            return (targetDateAndTime.isAfter(beginDateAndTime)
                            || targetDateAndTime.isEqual(beginDateAndTime))
                    && (targetDateAndTime.isBefore(endDateAndTime)
                            || targetDateAndTime.isEqual(endDateAndTime));
        } else {
            return targetDateAndTime.isAfter(beginDateAndTime)
                    && targetDateAndTime.isBefore(endDateAndTime);
        }
    }
    return false;
}
```

## TCP 통신 - IP 단편화
- [Netty 관련 정리 및 코드](https://github.com/justdoanything/self-study/blob/main/03%20ApplicationModernization.md#netty와-nio)
- 문제상황 : 프로젝트에서 TCP Server/Client를 구축하고 테스트하는 과정에서 로컬에서 Server/Client를 구동해서 테스트하면 잘 됐는데 EKS, EC2 환경에서 하면 패킷이 유실되는 문제가 있었다. 원인은 IP 단편화 때문이었다.
- 원인 : IP fragmentation (단편화)
  - 패킷이 전송될 때 크기가 `MTU(Maximum Transmission Unit)`을 넘어가면 한번에 전송되지 않는다.
  - 각 라우터마다 MTU는 다를 수 있고 MTU가 너무 크면 라우터에 따라 재전송해야 하는 이슈가 있고 너무 작으면 송수신에 오버헤드가 커진다.
  - IP 프로토콜은 network layer의 거의 유일한 프로토콜로 '패킷을 어디로 어떻게 처리할지'를 담당한다. 전송되는 과정중에 fragmentation이 발생하기도 하며, 목적지에 도착하고 나서 재조합된다.
  - `MTU(Maximum Transmission Unit)`
    - MTU는 최대 전송 단위로서 TCP/IP Network 등과 같이 패킷 또는 프레임 기반의 네트워크에서 전송될 수 있는 최대 크기의 패킷 또는 프레임을 가리키며 대개 옥텟을 단위로 사용한다.
    - 4000바이트의 패킷을 전송하려고 하는데 MTU가 1500이다.
    - 패킷은 아래와 같이 분할된다.
      - 각 단편 모두 헤더를 가져야 하기 때문에 최대 1480 Bytes로 분할 될 수 있다.
      - 1번 2번 단편은 뒤에 이어질 단편이 있으므로 More Flag가 1이다.
      - offset은 8 Bytes 단위로 표시된다.

        | No  | Payload | Header | Flag | Offset |
        |-----|---------|--------|------|--------|
        | 1   | 1480    | 20     | 1    | 0      |
        | 2   | 1480    | 20     | 1    | 185    |
        | 3   | 1020    | 20     | 0    | 370    |
- 해결 : 구현했던 Decode 부분을 수정해서 IP 단편화 문제를 해결했다.
```java
@Override
protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
    ByteBuf copyBuf = in.duplicate();
    String commandString = readString(copyBuf);
    int packetLength = 0;
    if(commandString.length() > 6){
        packetLength = Integer.parseInt(commandString.substring(0,6));
    }else{
        return;
    }
    
    if (in.readableBytes() == packetLength) {
        out.add(in.readBytes(in.readableBytes()));
    }else{
        return;
    }
}

public String readString(ByteBuf buf) {
    String result = null;
    String charSet = "EUC_KR";
    int bytes = buf.bytesBefore((byte) 0);
    if (bytes == -1) {
        bytes = buf.readableBytes();
        result= buf.toString(buf.readerIndex(), bytes, Charset.forName(charSet));
        buf.skipBytes(bytes);
    } else {
        result = buf.toString(buf.readerIndex(), bytes, Charset.forName(charSet));
        buf.skipBytes(bytes + 1);
    }
    return result;
} 
```

---

# SQL
## ORDER BY INTSTR (검색 조건과 가장 많이 일치하는 순으로 정렬하고 싶을 때)
- 검색 조건과 가장 많이 일치하는 순으로 정렬하고 싶을 때 INTSTR(검색 조건으로 시작하는 인덱스를 반환)을 사용한다.
- `yong`으로 검색한 결과가 `yongwoo`, `leeyong`, `lyong` 라고 했을 때 _INSTR_ 을 사용하면 `GROUP BY 1, 4, 2, name` 순으로 되고 yongwoo, lyong, leeyong 순으로 정렬할 수 있다.
  ```sql
  SELECT *
  FROM member m
  WHERE m.name LIKE CONCAT('%', ${name}, '%')
  ORDER BY INSTR(name, #{name}), name
  ```

## ORDER BY FIELD (특정한 값을 우선적으로 정렬하고 싶을 때)
- 특정한 값을 우선적으로 정렬할 때 사용한다
  ```sql
  SELECT *
  FROM category c
  ORDER BY FIELD(c.category_name, '국내', '해외'), c.category_name, c.sort_order
  ```
- 예를들어 우선 순위로 조회한 값의 id가 [4, 7, 9, 5] 라고 할 때, [4, 7, 9, 5]는 정렬조건을 부여한 값이기 때문에 이 중에 상위 3개를 조회하고 싶다면 아래와 같이 해야한다.
  만약 그냥 조회한다면 [4, 5, 7, 9] 순으로 조회가 된다.
  ```sql
  SELECT *
  FROM community c
  WHERE ids IN
    <foreach collection="list" item="value" separator="," open="(" close=")">
        #{value}
    </foreach>
  ORDER BY FIELD(c.community_id,
    <foreach collection="list" item="value" separator=",">
        #{value}
    </foreach>
  LIMIT 3
  ```

## ORDER BY CASE (결과값의 첫자리가 숫자 -> 한글 -> 영어 -> 그 외(특수문자)로 정렬하고 싶을 때)
- name의 첫자리가 숫자 -> 한글 -> 영어 -> 그 외(특수문자)로 정렬하는 방법
  ```sql
  ORDER BY CASE
    WHEN name REGEXP '^[0-9]' THEN 1
    WHEN name REGEXP '^[ㄱ-ㅎ가-힣]' THEN 2
    WHEN name REGEXP '^[a-zA-Z]' THEN 3
    ELSE 4 END, name
  ```

## IGNORE CASE & INSERT ON DUPLICATATE UPDATE
- `INSERT IGNORE ...` : 중복 키 에러가 발생했을 때 신규로 입력되는 레코드는 무시하고 AUTO_INCREMENT 되지 않음.
- `REPLACE IGNORE ...` : 중복 키 에러가 발생했을 때 기존 레코드는 삭제하고 신규 레코드를 삽입하는 방식이다.
- `INSERT INTO ... ON DUPLICATE UPDATE ...` : 중복 키 에러가 발생했을 때 원하는 값을 직접 설정할 수 있다.
    ```sql
    INSERT INTO employee VALUES ('name', 'city')
    ON DUPLICATE KEY UPDATE city = VALUES(city)
    ```

## REGEXP & REPLACE (구분자로 여러 값을 한번에 조회하고 싶을 때)
- 구분자로 여러 값을 한번에 조회하고 싶을 때 사용
```sql
SELECT *
FROM users
WHERE user_name REGEXP REPLACE(REPLACE(#{userNames},' ',''),',','|')
```

## MySQL Table Lock 해결
- ALTER TABLE을 할 때 LOCK을 방지하기 위한 옵션
  ```sql
  ALTER TABLE ...
  , ALGORITHM=INPLACE, LOCK=NONE;
  ```
- 일반적으로 LOCK 걸린 테이블을 확인하고 해결하는 방법
  ```sql
  # LOCK 확인
  SHOW PROCESSLIST;

  # 프로세스 삭제
  KILL ${PID};
  ```
- 하지만 `SHOW PROCESSLIST`에서 발견되지 않는 lock이 존재할 수 있습니다. `SHOW OPEN TABLES` 명령을 통해서 In_use 값을 조회하거나 의심되는 테이블에 조건을 걸어서 조회할 수 있습니다.
  ```sql
  SHOW OPEN TABLES WHERE In_use > 0;
  ```
- `SHOW OPEN TABLES` 명령어를 통해서 의심되는 테이블을 찾았다고 해도 `SHOW PROCESSLIST`에서 PID가 조회되지 않기 때문에 어떤 조치를 취하기 어렵습니다.
- MySQL의 메타데이터 락
  - 공식문서 : https://dev.mysql.com/doc/refman/8.0/en/metadata-locking.html
  - MySQL의 메타데이터 락은 5.5 버전부터 생긴 개념으로 MySQL 서버는 메타데이터 락을 통해서 데이터베이스 개체(프로시저, 함수, 테이블, 트리거 등)에 대한 concurrent 요청을 관리하고 데이터 일관성을 보장합니다.
  - 메타데이터 락은 두 경우에 의해 발생됩니다.
    - 개체에 대한 변경작업을 시작할 때, 이 때 생기는 메타데이터 락은 ALTER 를 시도하는 세션을 kill 하면 됩니다.
    - 개체에 대한 변경작업을 마무리할 때, 메타데이터 락을 유발하는 세션을 찾아서 kill 해야하는데 holder session를 찾아야 합니다.
  - performance_schema 에서 threads 테이블과 processlist_info 테이블을 조합해서 어떤 세션이 메타데이터 락을 유발하고 있는지 찾고 kill 하면 됩니다.
    ```sql
    SELECT b.OBJECT_TYPE,
           b.OBJECT_SCHEMA,
           b.OBJECT_NAME,
           b.LOCK_TYPE,
           b.LOCK_STATUS,
           c.THREAD_ID,
           c.PROCESSLIST_ID,
           c.PROCESSLIST_INFO
    FROM information_schema.metadata_locks a
      JOIN performance_schema.metadata_locks b ON a.OWNER_THREAD_ID <> b.OWNER_THREAD_ID
                                                  AND a.OBJECT_NAME = b.OBJECT_NAME
                                                  AND a.LOCK_STATUS = 'PENDING'
      JOIN performance_schema.threads c ON b.OWNER_THREAD_ID = c.THREAD_ID;
    ```
- 참고자료 : https://leezzangmin.tistory.com/51

---

# React
## React Component
### Function - 핸드폰에 있는 주소록처럼 첫글자로 그룹핑하는 함수
- Spring에서 name이 결과값을 첫자리가 숫자 -> 한글 -> 영어 -> 그 외(특수문자)로 정렬해서 반환
  ```sql
  ORDER BY CASE
    WHEN name REGEXP '^[0-9]' THEN 1
    WHEN name REGEXP '^[ㄱ-ㅎ가-힣]' THEN 2
    WHEN name REGEXP '^[a-zA-Z]' THEN 3
    ELSE 4 END, name
  ```
- React에서 배열의 name 필드를 읽어서 첫글자의 자음, 알파벳, 특수문자 기준으로 그룹핑해서 반환
  ```javascript
  export const makeNameGroupByFirstChar = (data: { name: string; [key: string]: any }[], resultGroups: { title: string; [key: string]: any }[]) => {
    const koreanUnicodeStart = 44032
    const koreanUnicodeEnd = 55203
    const alphabetStart = 65
    const alphabetEnd = 90
    const numberStart = 48
    const numberEnd = 57
  
    const isKoreanChar = (char: string) => {
      const unicode = char.charCodeAt(0)
      return unicode >= koreanUnicodeStart && unicode <= koreanUnicodeEnd
    }
  
    const isAlphabetChar = (char: string) => {
      const unicode = char.charCodeAt(0)
      return unicode >= alphabetStart && unicode <= alphabetEnd
    }
  
    const isNumberChar = (char: string) => {
      const unicode = char.charCodeAt(0)
      return unicode >= numberStart && unicode <= numberEnd
    }
  
    const getFirstChar = (name: string) => {
      const firstChar = name.charAt(0)
  
      if (isKoreanChar(firstChar)) {
        const unicode = name.charCodeAt(0) - koreanUnicodeStart
        const index = Math.floor(unicode / 28 / 21)
        return String.fromCharCode(0x1100 + index)
      }
      
      if (isAlphabetChar(firstChar)) {
        return firstChar
      }
      
      if (isNumberChar(firstChar)) {
        return firstChar
      }
      
      return firstChar
    }
  
    data.forEach((item: any) => {
      if (!item.name) return
      const firstChar = getFirstChar(item.name)
      const existGroup = resultGroups.find(group => group.title === firstChar)
      if (existGroup) {
        existGroup.members.push(item)
      } else {
        resultGroups.push({title: firstChar, members: [item]})
      }
    });
  };
  ```

### MUI - BaseSelect (GroupComponent 다루기)
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

### MUI - Autocomplete
- 특정 리스트 안에서 입력한 값과 일치하는 아이템들을 보여주고 자동완성 해주는 컴포넌트
- 리스트에 [한국, 일본, 가나, 브라질, 프랑스, 미국] 이 있을 때 '한'을 입력하면 [한국]이 하단에 표시된다.
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

### Nextjs - getServerSideProps의 로그인 처리
- Nestjs + SpringBoot로 구성되어 있는 어플리케이션에서 SEO를 위해서 한 페이지의 데이터를 SSR로 가져와야 했다.
- 새로고침이 필요한 데이터라서 `getServerSideProps`를 사용해서 데이터를 가져왔고 로그인 구분 없이 보여주는 컨텐츠와 반응, 댓글 등 로그인 사용자의 정보를 보여줘야하는 컨텐츠가 섞여 있었다.
- Session에 로그인 된 사용자가 있으면 BE API를 호출할 때 로그인 정보를 담아서 호출하고 BE는 Session을 체크해서 로그인 사용자의 대한 정보를 추가로 응답했다.
- 여기서 발생했던 문제는 token 등 로그인 정보는 session과 localStorage에 있었는데 `getServerSideProps`에선 session, localStorage에 접근할 수 없었기 때문에 로그인 정보를 담아서 API를 호출할 수 없었다.
- 이를 해결하기 위해서 해당 페이지의 각 데이터 영역을 공통 컴포넌트 등으로 세분화하고 최초 로딩 시 SSR을 통해서 가져온 데이터로 화면을 렌더링하고 로그인 session이 존재하면 API를 다시 호출해서 로그인 사용자의 정보를 가져오고 useEffect, useMemo를 사용해서 로그인 사용자의 정보가 필요한 컴포넌트만 재랜더링하는 방식으로 구현했다.

---

## 무한 스크롤 구현 고려사항
무한 스크롤 기능은 피드처럼 많은 수의 컨텐츠를 보여주기 위해 사용합니다. 그리고 그 무한 스크롤 기능을 구현하기 위해선 `페이징 처리`, `스크롤 위치 저장`, `컨텐츠 데이터 실시간 동기화` 등 부가적인 기능들도 고려하면서 구현해야 합니다.

프로젝트에서 React와 SpringBoot를 활용한 Webview 방식의 애플리케이션의 무한 스크롤 기능을 구현해보면서 맞닥뜨렸던 문제들과 기획 단계에서 고려되었으면 좋았을 부분들을 정리해보았습니다.

### 1. 페이지와 컨텐츠의 구성 예시
페이지는 `(1)목록 페이지`, `(2)상세 페이지`, `(3)댓글 페이지`로 나누고 컨텐츠의 요소는 아래 사진과 같이 `(1)프로필 영역`(프로필, 닉네임), `(2)컨텐츠 영역`(제목, 내용, 사진), `(3)소셜 영역`(좋아요 수, 댓글 수, 조회수, 작성시간)으로 구성되어 있다고 가정하겠습니다.  

<img width="399" alt="image" src="https://github.com/justdoanything/self-study/assets/21374902/2c56c57a-b224-4cc2-b584-f75f0f35e32d">

### 2. 페이징과 정렬 기준
무한 스크롤 설명에 앞서 페이징 처리와 정렬을 같이 구현했을 때 고려해야할 사항이 있습니다.

피드와 같이 실시간으로 생성되는 데이터를 페이징 처리할 때 첫번째 컨텐츠의 `기준`이 없으면 데이터가 중복해서 나타날 수 있습니다. 

최신순으로 데이터를 노출할 때 N번째 페이지와 N+1번째 페이지를 호출하는 사이에 K개의 신규 컨텐츠가 등록되면 N+1번째 페이지에서 K개 만큼의 중복 데이터가 노출됩니다.

<img width="862" alt="image" src="https://github.com/justdoanything/self-study/assets/21374902/0b701dec-e0d9-4456-a930-5c631865597e">

이러한 중복 데이터를 방지하기 위해서 페이징 처리를 할 때 `기준`이 필요합니다. 만약 최신순으로 데이터를 가져 온다면 자동으로 채번되는 컨텐츠의 id 값을 이용할 수 있습니다.

첫번째 페이지에 첫번째 컨텐츠의 id 값을 기억하고 다음 페이지부터는 첫번째 컨텐츠의 id보다 작은 데이터만 조회하도록 하면 중복 데이터를 방지할 수 있습니다.
그리고 firstItemId를 초기화해주는 로직도 신경써서 구현해줘야 합니다.

- Page 관련 Request Parameter
  
  <img width="420" alt="image" src="https://github.com/justdoanything/self-study/assets/21374902/b61356a4-7226-478a-b1b5-7cc69ebc5ad2">

- firstItemId를 적용한 Query
  ```sql
  SELECT ...
  FROM ...
  WHERE ...
      <if test="firstItemId != null and firstItemId > 0">
        <if test="orderBy != null and 'recent'.equals(orderBy)">
          AND contend_id <![CDATA[<]]> #{firstItemId}
        </if>
        <if test="orderBy != null and 'oldest'.equals(orderBy)">
          AND contend_id <![CDATA[>]]> #{firstItemId}
        </if>
      </if>
  ```

`최신순`, `오래된 순`과 같이 기간을 기준으로 하는 정렬은 `채번되는 id 값`으로 `기준`을 정하기 쉽지만 `인기순`, `별점 높은 순`과 같은 정렬은 데이터의 순서가 실시간으로 바뀔 수 있기 때문에 기준을 잡기가 어렵습니다. 

따라서 실시간으로 생성되고 수정될 수 있는 컨텐츠를 페이징 처리하고 정렬을 구현한다고 했을 때 반드시 정렬 데이터를 특정할 수 있는 `기준`을 고려해야 합니다. 만약 그 기준을 정할 수 없다면 정렬을 구현할 수 없습니다.

**📌 Check Point**

✅ 페이징과 정렬을 구현할 때 실시간으로 데이터가 생성/삭제될 수 있음을 고려해서 명확한 `기준`을 정할 수 있는 정렬만 구현해야 합니다.

### 3. 스크롤 위치 계산을 위해서 컨텐츠는 고정 높이를 가지면 좋습니다.
무한 스크롤을 구현한 목록 페이지에서 상세 페이지로 갔다가 뒤로가기 등으로 돌아왔을 때 `기존에 가져왔던 컨텐츠`와 `스크롤 위치`는 유지되어야 합니다.

그리고 목록 페이지에서 컨텐츠는 store에서 관리되며 컨텐츠의 내용이 수정되거나 삭제될 수 있기 때문에 스크롤의 위치는 여러 상황에 따라 임의로 조정해줘야 합니다. 
이 때 컨텐츠의 높이가 내용의 길이나 사진 유무에 따라 가변적이라면 컨텐츠의 높이를 알기 어렵기 때문에 스크롤 위치 조정이 많이 어러워집니다.

일정한 규칙에 따라 광고나 배너 같은 추가 요소가 중간에 들어가 있다면 스크롤 위치 계산은 더 어려워집니다.

따라서 상황에 따라 스크롤 위치를 정확하게 계산하고 수정하기 위해선 컨텐츠의 요소는 모두 고정 높이를 가질 수 있도록 설계하는 것이 좋습니다.

**📌 Check Point**

✅ 상황에 따라 정확한 스크롤 위치를 조작할 수 있도록 컨텐츠 및 추가 요소의 높이는 고정적으로 설계해야 합니다.

✅ 많은 컨텐츠 데이터를 가져온 상태에서 목록 페이지를 다시 렌더링할 때 컨텐츠가 다 렌더링되기 전에 스크롤이 이동될 수 있기 때문에 정확한 스크롤 위치를 계산하고 이동시키는 부분은 중요합니다.  

### 4. 목록 페이지와 상세 페이지에서 동기화해야 하는 데이터
목록 페이지는 `기존에 가져왔던 컨텐츠`와 `스크롤 위치`는 유지되어야 하기 때문에 데이터를 가져올 때마다 store에 데이터를 저장하고 관리합니다.
그리고 상세 페이지는 페이지가 로드될 때 데이터를 `실시간`으로 가져와서 보여줍니다.

이러한 차이 때문에 주로 `소셜 데이터`(좋아요 여부, 좋아요 수, 댓글 수, 조회 수)가 목록 페이지와 상세 페이지에서 실시간으로 동기화 해줘야 하는 데이터 입니다.

좋아요 기능을 예로 들면 목록 페이지에서 좋아요를 누르면 좋아요 여부와 좋아요 수가 변경됩니다. 목록 페이지에 있는 데이터는 store에 있기 때문에 store 안에서 해당 컨텐츠 데이터를 찾아 좋아요 여부와 수를 수정해줘야 합니다. 

그리고 상세 페이지로 이동했을 때 상세 페이지는 데이터를 실시간으로 가져오기 때문에 좋아요 여부와 수는 정상적으로 표시됩니다.

하지만 상세 페이지에서 좋아요를 누르고 목록 페이지로 돌아왔을 때 목록 페이지에서는 좋아요 여부와 수가 변경되지 않습니다. 
따라서 상세 페이지에서 좋아요에 대한 이벤트를 처리할 때 store에 있는 목록 페이지 데이터를 찾아서 함께 수정해줘야 합니다.

이는 상세 페이지 뿐만 아니라 댓글 팝업 등에서도 `좋아요 등록/취소`, `댓글 등록/수정/삭제`, `조회수 증가` 등 특정 이벤트가 발생했을 때 목록 페이지에서도 데이터가 동일하게 표시될 수 있도록 store에 있는 데이터도 반드시 수정해줘야 합니다. 또한 `컨텐츠 내용 수정/삭제`도 처리해줘야 합니다.

추가적으로 좋아요 수, 댓글 수, 조회수는 실시간으로 변하는 데이터입니다. 만약 좋아요 이벤트를 처리할 때 store에 있는 컨텐츠 데이터의 좋아요 수를 +1 처리만 했다면 목록 페이지에서 상세 페이지로 다시 갔을 때 좋아요 수가 다를 수 있습니다.

왜냐하면 그 사이에 다른 유저가 좋아요를 눌렀을 때 목록 페이지에선 +1만 되지만 상세 페이지에선 실시간으로 데이터를 실시간으로 가져오기 때문에 +2가 된 값이 보이기 때문입니다.

그렇다고해서 store에 있는 데이터를 수정할 때마다 서버로부터 데이터를 받아 처리한다면 속도나 성능 문제가 발생할 수도 있습니다.

따라서 위와 같은 문제가 있음을 고려해서 데이터 차이를 무시한채 +-1 처리만할지, 속도나 성능에 이슈가 없어서 실시간으로 데이터를 받아서 수정할지 의사결정을 미리 해야 합니다. 그리고 개발자는 이를 고려하여 데이터 구조와 store를 설계하고 기능 구현할 때 고려해야 합니다.

이 부분은 실제로 개발했을 때 처리해줘야 하는 이벤트 형태도 많고 적용해야 하는 페이지도 많아서 어렵고 복잡했던 기능입니다. 
반드시 사전에 해당 기능을 구현하기 위해 고려해야할 사항과 제약사항을 인지하고 설계하면 좋을 것 같습니다. 

**📌 Check Point**

✅ 실시간으로 변할 수 있는 데이터를 목록 페이지, 상세 페이지 및 다른 페이지에서 실시간으로 동기화될 수 있도록 store에 있는 데이터를 수정해줘야 합니다.

✅ store에 있는 데이터를 수정할 때 +-1 처리만 할지 서버로부터 실시간으로 데이터를 받아서 컨텐츠를 수정할지 사전에 고려해서 설계해야 합니다.

✅ 컨텐츠 중간에 추가 요소들이 들어갈 경우, store에 컨텐츠를 저장할 때 중간요소도 다 같이 포함해서 저장하는 것이 좋습니다. 컨텐츠와 추가 요소를 따로 관리하면 목록 페이지에서 기존 데이터를 다시 렌더링할 때 로직이 다소 복잡해지기 때문입니다.

✅ 기존 프로젝트에서는 URL 기준으로 store를 관리했었는데 하나의 목록 페이지 안에서 여러 개의 탭으로 나눠서 무한 스크롤을 구현하고 store 데이터와 스크롤 위치를 관리해야하는데 어려움이 있었습니다. 요구사항을 충분히 이해하고 고려해서 store를 설계하고 구현해야 합니다.

✅ 이 문제는 목록 페이지와 상세 페이지만의 문제는 아닙니다. 댓글을 팝업창으로 구현했다면 상세 페이지에서 보이는 댓글 데이터도 조작해줘야 하고 목록 페이지에서 보이는 댓글 데이터를 조작해줘야 합니다.

### 5. store에 무한한 데이터를 모두 저장할 수 없습니다.
사용자가 페이지를 내릴 때마다 가져온 데이터는 store에 저장됩니다. 하지만 컨텐츠가 무한하다면 모든 데이터가 store에 계속 저장되고 유지될 수 없습니다.

특히 컨텐츠가 사진이나 동영상을 포함하고 있다면 store에 저장되는 용량도 커지기 때문에 성능에도 영향을 미칠 수 있습니다.

이를 해결하기 위해선 크게 2가지 방법을 사용할 수 있습니다.

1. store에 저장되는 데이터의 수를 제한합니다. 

    store에 저장된 데이터가 일정한 수를 넘으면 가장 오래된 데이터를 지우고 새로운 데이터를 추가합니다. 예를들어 사이즈를 200으로 정했다면 201~210번째 데이터를 추가할 때 1~10번째 데이터를 삭제합니다. 
  
    이렇게하면 사용자가 어느 순간에 스크롤을 최상단으로 올렸을 때 처음 봤던 컨텐츠를 볼 수 없습니다.
       
2. onScrollTop과 onScrollBottom을 모두 구현합니다.

    store에는 일정한 데이터의 수만 저장하고 있고 스크롤의 onTop과 onBottom 이벤트를 감지해서 이전 데이터와 이후 데이터를 적절하게 가져오고 store에 있는 데이터를 조정해줍니다.

    이렇게하면 사용자가 스크롤을 최상단으로 올렸을 때 처음 봤던 컨텐츠를 볼 수 있지만 이전 데이터를 새로 가져와야 하기 때문에 로딩이 발생할 수 있고 이전 데이터를 가져오면서 최근 데이터를 지우기 때문에 스크롤을 다시 내린다면 로딩이 발생할 수 있습니다. 
   
    따라서 가장 최근에 호출한 페이지 번호 를 기억하고 onScrollTop과 onScrollBottom을 처리할 때 Paging size와 num을 적절하게 조정해야 합니다.

**📌 Check Point**

✅ 무한 스크롤에 사용되는 컨텐츠는 가능한한 최대한 가볍게 설계애햐 합니다. 

✅ onScrollTop과 onScrollBottom을 모두 구현하면 난이도가 많이 상승하고 최상단과 최하단에 도달했는지 여부를 sticky component를 포함해서 구분을 잘 해줘야 합니다.

✅ store에 있는 데이터가 사라질 수 있기 때문에 store에 있는 데이터를 수정할 때 고려해야 합니다.

✅ store에 있는 데이터가 많을 경우 상세 페이지에서 목록 페이지로 돌아올 때 컨텐츠가 로딩되는 동안 흰 화면이 나오는 이슈가 있었습니다. 이 부분을 방지하기 위해서 스켈레톤이나 로딩 페이지를 적절하게 구현해야 합니다. 또는 InView를 사용해서 컨텐츠를 숨기는 방법도 있습니다.

### 6. 기타 고려사항
- 공통 컴포넌트를 설계할 때 외부에서 데이터를 제어할 수 있도록 설계하자. (with 댓글 컴포넌트)
  - 공통 컴포넌트를 만들 때 최하단 컴포넌트는 만들어진 데이터를 렌더링하기만 하고 데이터를 재조회하는 등 이벤트 제어는 상단 컴포넌트에서 컨트롤하도록 설계해야 한다.
  - 공통 컴포넌트를 사용하는 외부 컴포넌트에서 데이터 재조회 시점을 정하거나 댓글처럼 트래픽을 줄이기 위해 생성/수정/삭제 시 서버를 거치지 않고 가져온 데이터를 제어할 수 있도록 설계할 수도 있다. 
- 테스트 코드가 없다면 테스트 케이스를 최소한으로라도 작성하자.
  - 프로젝트 후반부로 갈수록 컴포넌트가 무거워지고 복잡한 로직들이 많아집니다. 요구사항과 기획이 바뀌고 여러 SE가 돌아가면서 같은 코드를 개발하고 리팩토링하고 버그수정을 하는 상황에서 사이드이펙트가 발생할 확률도 같이 높아집니다.  
  - 개발자가 각 페이지를 개발하고 MR을 올리기 전에 테스트할 때 최소한의 케이스를 wiki에 작성하고 다른 개발자가 해당 페이지를 수정할 때 재수행할 수 있도록 하면 좋을 것 같습니다.
  - 기획서가 모든 히스토리를 반영할 수 없고 개발자가 코드를 수정할 때 기존 이력을 모두 고려할 수 없기 때문에 테스트 케이스를 작성하면 좋을 것 같습니다. 
  - 기획서 및 요구사항이 바뀔 경우 그에 대한 테스트 케이스도 수정하고 프로젝트 후반에서 사이드 이펙트를 줄일 수 있는 방법을 찾으면 좋을 것 같습니다.  
  - 각 페이지는 서버에서 에러 응답이 왔을 경우, 빈 데이터가 왔을 경우 등 에러 케이스에 대한 처리를 해야 합니다.
- 썸네일 생성과 처리
  - 프로젝트에서 썸네일 이미지 생성을 AWS Lambda를 통해서 처리했었습니다. S3 특정 경로에 이미지나 동영상이 업로드됐을 때 Lambda를 통해 thumbnail 경로에 썸네일 이미지를 저장했습니다.
  - 따라서 썸네일 파일이 생성되기까지 시간 차이가 존재했었고 목록 페이지에서 썸네일 파일을 사용했기 때문에 게시글 등록 후 목록으로 바로 넘어가면 이미지가 정상적으로 노출되지 않을 수 있었습니다.
  - 이를 해결하기 위해서 이미지 컴포넌트 내부적으로 썸네일 경로에서 파일을 가져오고 에러가 발생하면 원본 경로에서 파일을 가져오도록 처리했습니다.
  - 문제는 동영상 썸네일이었는데 동영상은 썸네일 파일이 없으면 이미지 자체를 노출할 수 없었기 때문에 디폴트 이미지는 노출하는 방식으로 처리했습니다.
- 캐싱할 데이터에 커뮤니티 상태 변경 등 실시간으로 데이터를 반영해야할 데이터가 있는지 검토해야 합니다.

### 7. 그 외 성능과 관련한 이슈
1. MyBatis + MySQL을 사용할 때 비교하는 타입이 다를 경우 반드시 `CAST`를 사용해야 합니다.

  ```sql
    SELECT ...
    FROM ...
    WHERE reference_value1 = CAST(community_id AS CHAR)
  ```
2. Admin 화면처럼 검색 조건이 다양하고 검색되는 데이터의 양이 많아서 성능을 조정해야 할 때

Admin 화면처럼 상황에 따라 여러 조건이 있는 경우 조건에 따라 FROM, WHERE 절을 알맞게 조정해주고 검색 쿼리가 복잡한 경우 검색할 데이터의 id 값을 먼저 검색하고 두번째 쿼리에서 `ORDER BY FIELD`를 사용할 수 있습니다.

  ```sql
    SELECT c.community_id AS communityId
    FROM community c
       <if test="bbsName != null and bbsName != ''">
         LEFT JOIN bbs b ON c.bbs_id = b.bbs_id
       </if>
    <where>
       <if test="bbsName != null and bbsName != ''">
         AND b.bbs_name = #{bbsName}
       </if>
    </where> 
  ```
  ```sql
    SELECT c.community_id AS communityId
       <if test="bbsName != null and bbsName != ''">
         b.bbs_name AS bbsName
       </if>
    FROM community c
       <if test="bbsName != null and bbsName != ''">
         LEFT JOIN bbs b ON c.bbs_id = b.bbs_id
       </if>
    WHERE c.community_id IN
          <foreach collection="communityIds" item="id" open="(" separator="," close=")">
             #{id}
          </foreach>
     ORDER BY FIELD(c.community_id,
          <foreach collection="communityIds" item="id" separator=",">
             #{id}
          </foreach>
          )
  ```

## React 자잘한 문법
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

### enum
```js
export enum Method {
  GET = 'GET',
  POST = 'POST',
  PUT = 'PUT',
  DELETE = 'DELETE',
  PATCH = 'PATCH',
}
```

### useEffect - async
```js
useEffect(() => {
  const fetchData = async () => {
    const data = await (await fetch(url)).json();
    setData(data);
  };

  fetchData();
}, [data]);
```

### useEffect와 router.query
```js
const router = useRouter();
const { id } = router.query;

useEffect(() => {
  if (!router.isReady) return;
  console.log(id);
}, [router.isReady]);
```

### .map
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

### .map & await
```typescript
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
```typescript
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

## React Library
### Axios
#### async 함수 정의
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

#### Request/Response 정의
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

#### API 정의
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

#### API 사용
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

### react-mentions
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

### infinite scroll
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

### Object.keys와 Object.entries (makeQueryString)
- #### 현상
  - HTTP GET 요청을 보낼 때 파라미터로 보내는 객체의 특정 키 값만 UTF-8로 인코딩한 결과를 세팅해서 보내야 했다.
  - axios를 사용하고 있어서 `axios.get` 함수를 사용할 때 객체 형태의 파라미터를 인자로 넣으면 자동으로 인코딩하고 queryString으로 변환해서 호출해주고 있었다.
  - where에 들어가는 값에 대해서만 UTF-8 인코딩한 결과를 사용했어야 했다.
    ```javascript
    interface RequestParam {
      select: string;
      from: string;
      where: string;    // where에 들어가는 값은 UTF-8 인코딩을 해야한다.
      limit?: number;
      page?: number;
    }
    ```
- #### 문제상황
  - axios를 사용하는 컴포넌트에서 where에 들어가는 값을 억음부호(\`)로 묶어서 여러 변수와 공백값들을 사용됐었는데 axios 안에서 자동으로 인코딩된 결과에서 공백이 `%20`이 아니라 `+`로 나오는 현상이 있었다. 
  - axios를 사용하는 컴포넌트에서 where에 들어가는 값을 인코딩(encodeURIComponent)해서 넣으면 `axios.get` 요청이 만들어질 때 자동으로 인코딩을 또 하기 때문에 이중으로 인코딩되는 문제가 있었다. 
- #### 해결방법
  - axios는 파라미터로 넘어오는 값(객체)에 대해서 자동으로 인코딩하기 때문에 내가 사용하는 파라미터는 `axios.get` 함수의 인자로 넘기지 않고 axios를 호출하기 전에 queryString을 임의로 만든 후 URL에 붙여서 호출하려고 했다.
  - 파라미터 객체의 key 값들은 호출하는 컴포넌트에 따라 유동적으로 바뀌기 때문에 queryString을 만드는 함수를 깔끔하게 작성하고 싶었다.
  - 처음에 사용하려고 했던 함수
    - `Object.keys`를 사용해서 만들었던 함수는 에러를 반환했다. 
    - 에러 메세지 : `TS7053: Element implicitly has an 'any' type bacause expression of type 'string' can't be used to index type 'RequestParam'. No index signature with a parameter of type 'string' was found on type 'RequestParam'.`
    - interface의 key 값들을 추출해서 param[key]와 같이 key에 해당하는 값을 바로 접근하는 것은 type safe 하지 않아서 에러를 반환하고 있었다.
    
    ```javascript
    const makeQueryString = (param: ApiModel.ApiRequest) => {
      return Object.keys(param)
                      .map(key => {
                          if(key === 'where') {
                            return `${key}=${encodeURIComponent(param[key])}`;
                          }
                          return `${key}=${param[key]}`;
                      }).join('$');
    }
    ````
  - `Object.entries`를 사용해서 해결했었다.

    ```javascript
     const getSearchQueryString = (params: ApiModel.ApiRequest): string => {
       const { orderBy, ...remains } = params;
       const query = {
         ...remains,
         select: params.select.join(','),
         from: `${params.from}.${params.from}`,
         where: `${params.where} order by ${orderBy ?? 'createDateTime desc'}`,
         limit: params['limit'] ?? '10'
       };
 
       const queryString = Object.entries(query)
           .map(([key, value]) => {
             if (key === 'where' || key === 'custom' || key === 'keywords') {
               return `${key}=${encodeURIComponent(value)}`;
             }
             return `${key}=${value}`;
           })
       .join('&');
 
       return `?${queryString}`;
     };
    ```

### `Object.keys`에서 interface의 key를 추출해서 사용할 수 없는 이유
- 근본적으로 Typescript에서는 key 값으로 string 타입을 허용하지 않고 String Literal, number 타입만 허용한다. Typescript는 타입을 정하고 특정한 곳에 특정 타입만 허용하는 것이 중요 원칙이기 때문에 String Literal 자리에 string 타입을 사용해서 컴파일 에러가 발생한 것이다.
- ##### let? const? const:string?
  ```typescript
  let lets = "I am let.";                 // string (추론)
  const scont: string = "I am const.";    // string
  const cont = "I am narrowed type";      // string literal
  ```
  - `lets`는 let으로 선언되어 있기 때문에 재할당할 수 있고 어떤 문자열이든 넣을 수 있기 때문에 컴파일러는 `lets` => string 타입으로 추론한다.
  - `scont`은 :string으로 타입을 명시했기 대문에 `scont` => string 타입이다.
  - `cont`는 "I am narrowed type." 이 외의 값을 할당할 수 없기 때문에 string 이지만 조금 더 좁은 타입(narrowed type)으로 추론한다. 이것은 Literal Narrowing 이라고 한다.
- 여기서 추론이란 typescript 컴파일러가 제공해주는 기능으로 개발자가 명시적으로 타입을 선언하지 않으면 컴파일러가 할당되는 값을 기준으로 타입을 (추론해서) 정해준다.
- `cont`는 string literal 타입으로 아무 string 값을 갖는 변수가 아니라 더 구체적인 값 "I am narrowed type."만을 허용한 타입으로 인식한다.
- `lets` 또한 type을 명시하면 literal 타입이 될 수 있다.
  ```typescript
  type NarrowType = "I am narrowed type.";
  let nets: NarrowType = "I am narrowed type.";
  nets = "I am const"; // compile error!!
  
  type IntegrationType = "I am let." | " am const." | "I am narrowed type";
  ```
- 객체에 접근할 때 Literal 타입은 key로 사용이 가능하지만 단순한 string 타입은 사용할 수 없다.
  ```typescript
  const person = {
    name: "lee",
    age: 20
  };
    
  const literalKey = "name";
  console.log(person[literalKey]);  // lee
  console.log(person["age"]);       // 20
    
  for(const key of Object.keys(person)) {
    console.log(person[key]);   // compile error!!
  }
  ```
- string 타입으로 객체에 key로 접근하기 위해선 `Index Signature`를 선언하면 가능하다. 대신 `Index Signature`를 사용하면 모든 멤버가 같은 타입을 사용해야 한다.
  ```typescript
  type Person = {
    [index: string]: string;  // [index: number]: string; 도 가능하다.
    name: string;
    age: string;  // number를 사용하면 index type error가 발생한다.
  }
  
  const lee: Person = {
    name: "lee",
    age: "20"
  };
  const literalKey = "name";
  console.log(lee[literalKey]);  // lee
  console.log(lee["age"]);       // 20
  ```
- string, number 타입의 Index Signature를 동시에 사용할 수 있지만 literal 방식의 할당은 불가능하다.
  ```typescript
  interface LikeType {
    [key: number]: string,
    [key: string]: string
  };
  
  const likeObject: LikeType = {
    0: "1992",
    name: "park"
  };
  
  console.log(likeObject[0]);      // 1992
  console.log(likeObject["name"]); // park
  ```
- Reference : - https://soopdop.github.io/2020/12/01/index-signatures-in-typescript/

### Mapped Type
- 이미 선언된 타입의 프로퍼티에 어떤 조작을 가해서 새로운 타입을 만드는 것을 Mapped Type 이라고 한다. 프로퍼티를 빼거나 추가할 수 있고 Optional, ReadOnly 산태로 바꿀수도 있다.
  ```typescript
  type AllowKeys = "name" | "age";
  type AllowType = {
    [key in AllowKeys]: number
  };
  
  type SameAllowType = {
    name: number;
    age: number;
  }
  
  // 아래 타입은 error!! in 을 사용해야 한다.
  type NotAllowType = {
    [key: AllowKeys]: number;
  }
  ```

### Object.keys & Object.entries & Object.values
- #### Object.keys : Object 안에 있는 key만 담은 배열 반환
- #### Object.values : Object 안에 있는 value만 담은 배열 반환
- #### Object.entries : Object 안에 있는 [key, value] 형태의 배열을 반환
- #### Object.fromEntries() : 배열을 Object로 변환

### Partial & Pick & Omit
- #### Partial : 특정 타입의 부분 집합을 만족하는 타입을 정의할 수 있다.
  ```typescript
  interface UserProfile {
    username: string;
    email: string;
    age: number;
    profileUrl: string;
    city: string;
  }
  
  // 타입을 새로 만들어서 사용
  type PartialUserProfile = Partial<UserProfile>;
  const partialUser1: PartialUserProfile = {};
  const partialUser2: PartialUserProfile = { username: 'lee'};
  const partialUser3: PartialUserProfile = { age: 30, city: 'seoul' };
  
  // 특정 변수에 대해서 타입을 지정해서 사용
  const partialUser4: Partial<UserProfile> = { ... };
  const getPartialUser = (partialUser5: Partial<UserProfile>) => { ... }
  ```
- #### Pick : 특정 타입에서 몇 개의 속성을 선택하여 타입을 정의할 수 있다.
  ```typescript
  type PickUserProfile = Pick<UserProfile, 'username' | 'age' | 'city'>;
  const pickUserProfile: PickUserProfile = {
    username: 'lee',
    age: 30,
    city: 'seoul'
  }
  ```
- #### Omit : 특정 속성만 제거한 타입을 정의합니다. (pick의 반대)
  ```typescript
  type OmitUserProfile = Omit<UserProfile, 'username' | 'age' | 'city'>;
  const omitUserProfile: OmitUserProfile = {
    email: 'sample@example.com',
    profileUrl: '/profile/lee.jpg'
  }
  ```
- #### Partial을 직접 구현해보기 (Pick, Omit도 비슷한 방식으로 구현할 수 있다.)
  ```typescript
  // #1 - 기존 타입 사용
  type PartialUserProfile = {
    username?: UserProfile["username"];
    email?: UserProfile["email"];
    age?: UserProfile["age"];
    profileUrl?: UserProfile["profileUrl"];
    city?: UserProfile["city"];
  };
  
  // #2 - 맵드 타입 사용 (index signatures 사용)
  type PartialUserProfile = {
    [part in 'username' | 'email' | 'profilePhotoUrl']? = UserProfile[part];
  };
  
  // #3 - keyof 사용
  type PartialUserProfileKeys = keyof UserProfile;
  type PartialUserProfile = {
    [part in key of PartialUserProfileKeys]? = UserProfile[part];
  };
  
  // #4 - Generic Type 사용
  type PartialObject<T> = {
    [part in key of T]? = T[part]
  };
  ```

- Reference
  - https://kyounghwan01.github.io/blog/TS/fundamentals/utility-types/#partial
  
### InView 처리 (react-intersection-observer)
```typescript
import Box from '@/components/Box';
import { FC, PropsWithChildren, useState } from 'react';
import { InView, IntersectionOptions } from 'react-intersection-observer';

interface InViewProps extends IntersectionOptions, Pick<React.DetailedHTMLProps<React.HTMLAttributes<HTMLDivElement>, HTMLDivElement>, 'className' | 'style'> {}

const CommunityInView: FC<PropsWithChildren<InViewProps>> = ({ children, ...props }) => {
  const [isShow, setIsShow] = useState<boolean>(false);

  return (
    <InView {...props}
            onChange={(inView: boolean) => {
              setIsShow(inView);
            }}
      rootMargin={'48% 0px 48% 0px'}
    >
      {isShow ? children : <Box style={{ visibility: 'hidden' }}>{children}</Box>}
    </InView>
  );
};

export default CommunityInView;
```

## formik & yup & 깊은 복사
### formik
- 여러 form을 처리해주기 쉽게 만드는거.
- focus = touch로 (form 이 터치가 됐느냐)
- error = error로 잡아줌 (form에 값이 쓰여져있느냐)
- 기본 사용법 (https://krpeppermint100.medium.com/ts-formik-사용법-4f526888c81a)

### yup
- form에 대한 에러를 잡는 기준을 정해주는것.
- formik 안에 yup를 넣어서 yup이 에러 기준을 잡고 에러가 발생하면 formik이 감지함.

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

### 깊은 복사
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
