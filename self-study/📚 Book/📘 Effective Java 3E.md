# Effective Java 3E / 조슈아 블로크 지음 / 이복연 옮김 / 인사이트

### 2장 객체 생성자 파괴

- `생성자 대신 정적 팩터리메서드를 고려하라.`
  - 🏷 정적 팩터리 메서드와 public 생성자는 각자의 쓰임새가 있으니 상대적인 장단점을 이해하고 사용하는 것이 좋다. 그렇다고 하더라도 정적 팩터리를 사용하는게 유리한 경우가 더 많으므로 무작정 public 생성자를 제공하던 습관이 있다면 고치자.
- `생성자에 매개변수가 많다면 빌더를 고려하라.`
  - Telescoping Constructor Pattern : 점층적 생성자 패턴, 1개, 2개, 3개, ..., n개를 받는 생성자를 생성
  - JavaBeans Pattern : 매개 변수가 없는 생성자를 만들고 setter 메소드를 사용하는 것
  - Builder Pattern\
     `BuilderPattern.java`
  - 🏷 생성자나 정적 팩터리가 처리해야 할 매개변수가 많다면 빌더 패턴을 선택하는게 더 낫다. 매개변수 중 다수가 필수가 아니거나 같은 타입이면 특히 더 그렇다. 빌더는 점층적 생성자보다 클라이언트 코드를 읽고 쓰기가 훨씬 간결하고, 자바빈즈보다 훨씬 안전하다.
- `private 생성자나 열거 타입으로 싱글턴임을 보장하라.`\
   `Singleton.java`
- `인스턴스화를 막으려거든 private 생성자를 사용하라.`
- `자원을 직접 명시하지 말고 의존 객체 주입을 사용하라.`
  - 🏷 클래스가 내부적으로 하나 이상의 자원에 의존하고 그 자원이 클래스 동작에 영향을 준다면 싱글턴과 정적 유틸리티 클래스는 사용하지 않는 것이 좋다. 이 자원들을 클래스가 직접 만들게 해서도 안된다. 대신 필요한 자원을 (혹은 그 자원을 만들어주는 팩터리를) 생성자에 (혹은 정적 팩터리나 빌더에) 넘겨주자. 의존 객체 주입이라 하는 이 기법은 클래스의 유연성, 재사용성, 테스트 용이성을 기막히게 개선해준다.
- `불필요한 객체 생성을 피하라.`
- `다 쓴 객체 참조를 해제하라.`
  - 🏷 메모리 누수는 겉으로 잘 드러나지 않아 시스템에 수년간 잠복하는 사례도 있다. 이런 누수는 철저한 코드 리뷰나 힙 프로파일러 같은 디버깅 도구를 동원해야만 발견되기도 한다. 그래서 이런 종류의 문제는 예방법을 익혀두는 것이 매주 중요하다.
  - 💡 WeakHashMap으로 Cache에서 바로 제거하는 방법
- `finalizer와 cleaner 사용을 피하라.`
  - try-with-resources를 적극 사용하라.
  - cleaner는 안전망 역할이나 중요하지 않은 네이티브 자원 회수용으로만 사용하자. 물론 이런 경우라도 불확실성과 성능 저하에 주의해야 한다.\
     `CleanerSample.java`
- `try-finally보다는 try-with-resources를 사용하라.`

### 3장 모든 객체의 공통 메서드

- `equals는 일반 규약을 지켜 재정의하라.`
  - 값 클래스를 비교할 때(클래스가의 비교가 아니라 값을 비교할 때), equals를 재정의한다. 값이 같더라도 같은 인스턴스가 둘 이상 만들어지지 않음을 보장하는 통제 클래스는 재정의하지 않는다.
  - equals를 재정의할 때 따라야하는 일반 규약 : 반사성, 대칭성, 추이성, 일관성, null과 같지 않음이다.
  - equals 메서드 구현 방법
    - == 연산자를 사용해 입력이 자기 자신의 참조인지 확인한다.
    - instanceof 연산자로 입력이 올바른 타입인지 확인한다.
    - 입력을 올바른 타입으로 형변환한다.
    - 입력 객체와 자기 자신의 대응되는 '핵심' 필드들이 모두 일치하는지 하나씩 검사한다.
    - `CleanerSample.java`
  - 🏷 꼭 필요한 경우가 아니면 equals를 재정의하지 말자. 많은 경우에 Object의 euqlas가 여러분이 원하는 비교를 정확히 수행해준다. 재정의해야 할 때는 그 클래스의 핵심 필드 모두를 빠짐없이, 다섯 가지 규약을 확실히 지켜가며 비교해야 한다.
- `equals를 재정의하려거든 hashCode도 재정의하라.`
    <details>
      <summary>예제 코드</summary>

  ```java
  Map<PhoneNumber, String> m = new HashMap<>();
  m.put(new PhoneNumber(010, 1111, 2222), "영구");
  m.get(new PhoneNumber(010, 1111, 2222));  // null 을 반환한다.

  // 안좋은 hashCode
  @Override
  public int hashCode() { return 99; }

  // 올바른 hashCode
  @Override
  public int hashCode() {
    int result = Short.hashCode(areaCode);
    // 31은 홀수이면서 소수
    result = 31 * result + Short.hashCode(prefix);
    result = 31 * result + Short.hashCode(lineNum);
    return result;
  }

  @Override
  public int hashCode(){
    return Objects.hash(lineNum, prefix, areaCode);
  }
  ```

  </details>

  - 🏷 equals를 재정의할 때는 hashCode도 반드시 재정의해야 한다. 그렇지 않으면 프로그램이 제대로 동작하지 않을 것이다. 재정의한 hashCode는 Object의 API 문서에 기술된 일반 규약을 따라야 하며, 서로 다른 인스턴스라면 되도록 해시코드도 서로 다르게 구현해야 한다. 이렇게 구현하기가 어렵지는 않지만 조금 따분한 일이긴하다. AutoValue 프레임워크를 사용하면 멋진 equals와 hashCode를 자동으로 만들어준다. IDE들도 이런 기능을 일부 제공한다.

- `toString을 항상 재정의하라.`
  - 🏷 모든 구체 클래스에서 Object의 toString을 재정의하자. 상위 클래스에서 이미 알맞게 재정의한 경우는 예외다. toString을 재정의한 클래스는 사용하기도 즐겁고 그 클래스를 사용한 시스템을 디버깅하기 쉽게 해준다. toString은 해당 객체에 관한 명확하고 유용한 정보를 읽기 좋은 형태로 반환해야 한다.
- `clone 재정의는 주의해서 진행하라.`

---

🏷 💡
