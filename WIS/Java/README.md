# Package
- book.effective : Effective Java 3E
- book.modern : Modern Java In Action
- modern : 자주 사용하는 기본 package 구성

# Notes
[📘 모던 자바 인 액션.md](https://github.com/justdoanything/self-study/blob/main/WIS/%F0%9F%93%9A%20Book/%F0%9F%93%98%20%EB%AA%A8%EB%8D%98%20%EC%9E%90%EB%B0%94%20%EC%9D%B8%20%EC%95%A1%EC%85%98.md)

[📘 Effective Java 3E.md](https://github.com/justdoanything/self-study/blob/main/WIS/%F0%9F%93%9A%20Book/%F0%9F%93%98%20Effective%20Java%203E.md)

# Memo
## SQL 실행계획
  - SQL이 실행될 때 Optimizer가 수행하는 작업절차를 뜻한다.
  - 실행계획은 3단계로 분리되는데 `SQL 해석` → `실행계획 수립` → `실행` 순이다.
  - SQL은 SQL Parser가 Parser Tree를 만들고 Tree 기준으로 Optimizer가 분석하여 실행 계획을 만든다.
  - Optimizer는 비용 기반으로 쿼리 대상 테이블의 레코드 수와 통계 정보를 바탕으로 비용이 가장 적게 발생할 것 같은 방향으로 실행 계획을 생성한다.
  - 통계 정보가 정확할 수록 좋은 실행계획을 만들 수 있는데 `innodb_stats_sample_pages`은 통계 정보를 위해 분석할 수 있는 페이지의 수를 지정한다. (기본값: 8)
  - 통계 수집 중엔 테이블에 Read/Write가 안되기 때문에 성능을 고려해야 하고 최대 16~24개까지만 설정하는게 좋다.
  - Mysql 8.0 부터는 인덱스 말고 일반 컬럼에도 통계 정보를 수집하기 때문에 더 좋은 실행 계획을 수립할 수 있다.
  - 실행 계획은 SELECT절 앞에 `EXPLAIN`키워드를  사용해서 볼 수 있다. 실행 계획에서 나누는 필드는 아래 특성을 가진다.
    - `id` 
      - SELECT 쿼리를 구분하기 위한 용도로 JOIN으로 연결되어 있는 쿼리는 하나로 보기 때문에 같은 id 값을 가진다.
    - `select_type`
      - dd

  - Reference : https://jeong-pro.tistory.com/243
## SQL LIKE 검색 시 `INTSTR` 사용
  -  `yong`으로 검색한 결과가 `yongwoo`, `leeyong`, `lyong` 라고 했을 때 _INSTR_ 을 사용하면 `GROUP BY 1, 4, 2, name` 순으로 되고 yongwoo, lyong, leeyong 순으로 정렬할 수 있다.
  ```sql
  SELECT *
  FROM member m
  WHERE m.name LIKE CONCAT('%', ${name}, '%')
  ORDER BY INSTR(name, #{name}), name
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