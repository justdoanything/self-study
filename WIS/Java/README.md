# Package
- book.effective : Effective Java 3E
- book.modern : Modern Java In Action
- modern : 자주 사용하는 기본 package 구성

# Notes
[📘 모던 자바 인 액션.md](https://github.com/justdoanything/self-study/blob/main/WIS/%F0%9F%93%9A%20Book/%F0%9F%93%98%20%EB%AA%A8%EB%8D%98%20%EC%9E%90%EB%B0%94%20%EC%9D%B8%20%EC%95%A1%EC%85%98.md)

[📘 Effective Java 3E.md](https://github.com/justdoanything/self-study/blob/main/WIS/%F0%9F%93%9A%20Book/%F0%9F%93%98%20Effective%20Java%203E.md)


# Trouble Shooting
- Bean 간 순환 참조 문제
Spring Context가 모든 Bean을 로드할 때 일련의 순서로 Bean들을 생성한다.
만약에 BeanA -> BeanB -> BeanC 로 참조되어 있다면 Spring은 BacnC를 먼저 생성하고 BeanB를 생성하고 BeanC를 생성한다.
만약 순환 참조가 되어 있다면 Spring은 어떤 Bean을 먼저 생성해야할지 정하지 못한다.
이 때 Spring은 BeanCurrentlyInCreationException을 발생시킨다.

constructor injection을 사용했을 때 발생할 수 있는 케이스이다.
