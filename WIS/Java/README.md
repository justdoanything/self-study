# Package
- book.effective : Effective Java 3E
- book.modern : Modern Java In Action
- modern : 자주 사용하는 기본 package 구성

# Notes
[📘 모던 자바 인 액션.md](https://github.com/justdoanything/self-study/blob/main/WIS/%F0%9F%93%9A%20Book/%F0%9F%93%98%20%EB%AA%A8%EB%8D%98%20%EC%9E%90%EB%B0%94%20%EC%9D%B8%20%EC%95%A1%EC%85%98.md)

[📘 Effective Java 3E.md](https://github.com/justdoanything/self-study/blob/main/WIS/%F0%9F%93%9A%20Book/%F0%9F%93%98%20Effective%20Java%203E.md)

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