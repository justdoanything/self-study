# Effective Java 3E / 조슈아 블로크 / 이복연 /인사이트

- 선택 매개 변수가 많을 땐 ?
  - Telescoping Constructor Pattern : 점층적 생성자 패턴, 1개, 2개, 3개, ..., n개를 받는 생성자를 생성
  - JavaBeans Pattern : 매개 변수가 없는 생성자를 만들고 setter 메소드를 사용하는 것
  - Builder Pattern
