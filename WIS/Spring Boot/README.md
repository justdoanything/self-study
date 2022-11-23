# This Branch is ...
- SpringBoot를 초기 구축할 때 자주 사용되는 패턴과 패키지를 정리하고 그 안에서 사용되는 Annotation, 문법 등을 더 자세하게 찾고 정리한다.
- SpringBoot 기본 패키지 구조를 구성해본다.
- JAVA 개발을 하면서 유용한 문법, 마주쳤던 에러 등을 정리한다.

# Command
- docker run -p 3330:3306 -e MYSQL_ROOT_PASSWORD=yongwoo -e MYSQL_DATABASE=spring -d --name spring-mysql mysql:8.0

# Memo
## SQL LIKE 검색 시 `INTSTR` 사용
  -  `yong`으로 검색한 결과가 `yongwoo`, `leeyong`, `lyong` 라고 했을 때 _INSTR_ 을 사용하면 `GROUP BY 1, 4, 2, name` 순으로 되고 yongwoo, lyong, leeyong 순으로 정렬할 수 있다.
  ```sql
  SELECT *
  FROM member m
  WHERE m.name LIKE CONCAT('%', ${name}, '%')
  ORDER BY INSTR(name, #{name}), name
  ```

## SQL ORDER BY 시 `FIELD` 사용
  - 특정한 값을 우선적으로 정렬할 때 사용한다
  ```sql
  SELECT *
  FROM category c
  ORDER BY FIED(c.category_name, '국내', '해외'), c.category_name, c.sort_order
  ```

## SQL IGNORE CASE
- `INSERT IGNORE ...` : 중복 키 에러가 발생했을 때 신규로 입력되는 레코드는 무시하고 AUTO_INCREMENT 되지 않음.
- `REPLACE IGNORE ...` : 중복 키 에러가 발생했을 때 기존 레코드는 삭제하고 신규 레코드를 삽입하는 방식이다. 
- `INSERT INTO ... ON DUPLICATE UPDATE ...` : 중복 키 에러가 발생했을 때 원하는 값을 직접 설정할 수 있다.
    ```sql
    INSERT INTO employee VALUES ('name', 'city')
    ON DUPLICATE KEY UPDATE city = VALUES(city)
    ```
- Reference : http://jason-heo.github.io/mysql/2014/03/05/manage-dup-key2.html

## Annotation
  - ### VO
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
## Trouble Shooting
- ### Bean 간 순환 참조 문제
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

Insert - 1 (여러개인 경우 1) 
Update - 업데이트 된 행의 개수 (없으면 0) 
Delete - 삭제 된 행의 개수 (없으면 0)