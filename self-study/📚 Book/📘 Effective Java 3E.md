# Effective Java 3E / 조슈아 블로크 지음 / 이복연 옮김 / 인사이트

### 2장 객체 생성자 파괴

- 💦 `생성자 대신 정적 팩터리메서드를 고려하라.`
  - 🏷 정적 팩터리 메서드와 public 생성자는 각자의 쓰임새가 있으니 상대적인 장단점을 이해하고 사용하는 것이 좋다. 그렇다고 하더라도 정적 팩터리를 사용하는게 유리한 경우가 더 많으므로 무작정 public 생성자를 제공하던 습관이 있다면 고치자.

- `생성자에 매개변수가 많다면 빌더를 고려하라.`
  - Telescoping Constructor Pattern : 점층적 생성자 패턴, 1개, 2개, 3개, ..., n개를 받는 생성자를 생성
  - JavaBeans Pattern : 매개 변수가 없는 생성자를 만들고 setter 메소드를 사용하는 것
  - Builder Pattern\
     `BuilderPattern.java`
  - 🏷 생성자나 정적 팩터리가 처리해야 할 매개변수가 많다면 빌더 패턴을 선택하는게 더 낫다. 매개변수 중 다수가 필수가 아니거나 같은 타입이면 특히 더 그렇다. 빌더는 점층적 생성자보다 클라이언트 코드를 읽고 쓰기가 훨씬 간결하고, 자바빈즈보다 훨씬 안전하다.

- 💦 `private 생성자나 열거 타입으로 싱글턴임을 보장하라.`\
  - 예제코드 : `Singleton.java`

- 💦`인스턴스화를 막으려거든 private 생성자를 사용하라.`

- 💦 `자원을 직접 명시하지 말고 의존 객체 주입을 사용하라.`
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
  - 🏷 equals를 재정의할 때는 hashCode도 반드시 재정의해야 한다. 그렇지 않으면 프로그램이 제대로 동작하지 않을 것이다. 재정의한 hashCode는 Object의 API 문서에 기술된 일반 규약을 따라야 하며, 서로 다른 인스턴스라면 되도록 해시코드도 서로 다르게 구현해야 한다. 이렇게 구현하기가 어렵지는 않지만 조금 따분한 일이긴하다. AutoValue 프레임워크를 사용하면 멋진 equals와 hashCode를 자동으로 만들어준다. IDE들도 이런 기능을 일부 제공한다.
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

- `toString을 항상 재정의하라.`
  - 🏷 모든 구체 클래스에서 Object의 toString을 재정의하자. 상위 클래스에서 이미 알맞게 재정의한 경우는 예외다. toString을 재정의한 클래스는 사용하기도 즐겁고 그 클래스를 사용한 시스템을 디버깅하기 쉽게 해준다. toString은 해당 객체에 관한 명확하고 유용한 정보를 읽기 좋은 형태로 반환해야 한다.

- 💦 `clone 재정의는 주의해서 진행하라.`
  - 🏷 Cloneable이 몰고 온 모든 문제를 퇴짚어봤을 때, 새로운 인터페이스를 만들 때는 절대 Clonable을 확장해서는 안되며, 새로운 클래스도 이를 구현해서는 안된다. final 클래스라면 Cloneable을 구현해도 위험이 크지 않지만, 성능 최적화 관점에서 검토한 후 별다른 문제가 없을 때만 드물게 허용해야 한다. 기본 원칙은 `복제 기능은 생성자와 팩터리를 이용하는게 최고`라는 것이다. 단, 배열만은 clone 메서드 방식이 가장 깔끔한, 이 규칙의 합당한 예외라 할 수 있다.
  - clone은 위험하다. `Conversion Constructor`와 `Conversion Factory`를 사용하자.\
    인스턴스를 인수로 받을 수 있고 HashSet은 TreeSet 타입으로 복제할 수 있다.
    <details>
      <summary>예제 코드</summary>
        
      ```java
      public class Yum {
        // Conversion Constructor
        public Yum(Yum yum) { ... }

        // Conversion Factory
        public static Yum newInstance(Yum yum) { ... }

    }

    ```
    </details>

  - 상속해서 쓰기 위한 클래스는 Cloneable을 사용하지 않는게 좋다. clone 함수를 재정의해서 super.clone()을 사용해도 자바가 공변 반환 타이핑을 지원하기 때문에 에러가 발생하진 얺는다. 하지만 클래스가 배열 변수를 갖는다면 원본 인스턴스와 똑같은 배열을 참조하기 때문에 원본과 복제 인스턴스가 같은 객체를 사용하는 문제가 발생한다.
    <details>
      <summary>예제 코드</summary>

      ```java
      private Object[] elements;

      @Override
      public CloneableInstance clone() {
        try{
            return (CloneableInstance) super.clone();
        }catch(CloneNotSupportedException e){
            throw new AssertionError(); // 일어날 수 없는 일이다.
        }
      }
      ```

    </details>

  - elements 객체를 clone 처리할 수 있지만 element가 final 필드였다면 이 방법도 먹히지 않는다. 이는 `가변 객체를 참조하는 필드는 final로 선언하라.` 라는 일반 용법과 충돌한다.
    <details>
      <summary>예제 코드</summary>

      ```java
      // 배열 변수도 같이 clone 처리해줘야 한다.
      @Override
      public CloneableInstance clone() {
        try {
          CloneableInstance result = (CloneableInstance) super.clone();
          result.elements = elements.clone();
          return result;
        }catch(CloneNotSupportedException e){
          throw new AssertionError();
        }
      }
      ```

    </details>

  - Bucket 배열의 경우엔 elements와 같이 clone을 할 경우, 복제본은 자신만의 배열을 갖지만 그 배열은 원본과 같은 연결 리스트를 참조하여 원본과 복제본 모두 예기치 않게 동작할 수 있다. 이를 해결하기 위해선 각 Bucket을 구성하는 연결 리스트를 복사해야 한다.
    - `bucket`: _A bucket is one element of HashMap array. It is used to store nodes. Two or more nodes can have the same bucket. In that case link list structure is used to connect the nodes. Buckets are different in capacity. A relation between bucket and capacity is as follows: capacity = number of buckets load factor. A single bucket can have more than one nodes, it depends on hashCode() method. The better your hashCode() method is, the better your buckets will be utilized.\
    ▶️ Reference : https://www.geeksforgeeks.org/internal-working-of-hashmap-java/`_
    - deepCopy() 메소드를 통해서 적절한 크기의 새로운 버킷을 만들고 버킷을 순회하면서 비어있지 않은 버킷에 대해 깊은 복사를 수행한다.
      <details>
        <summary>예제 코드</summary>

      ```java
      public class HashTable implements Clonable {
        private Entry[] buckets = ...;

        private static class Entry {
          final Object key;
          Object value;
          Entry next;

          Entry(Object key, Object value, Entry next){
            this.key = key;
            this.value = value;
            this.next = next;
          }

          // 이 엔트리가 가리키는 연결 리스트를 재귀적으로 복사
          Entry deepCopy(){
            return new Entry(key, value, next == null ? null : next.deepCopy());
          }

          @Override
          public HashTable clone() {
            try{
              HashTable result = (HashTable) super.clone();
              result.buckets = new Entry[buckets.length];
              for(int i=0; i<buckets.length; i++)
                if(buckets[i] != null)
                  result.buckets[i] = buckets[i].deepCopy();
              return result;
            } catch(CloneNotSupportedException e){
              throw new AssertionError();
            }
          }
        }
      }
      ```
      </details>
    - 📝 객체를 복사하고 싶을 때 clone 함수를 함부로 사용하면 안된다. 객체 안의 변수가 배열이나 Call by Reference 형태의 변수라면 원본과 복사본이 같은 변수를 수정하여 동작이 올바르지 못할 수 있다. 
    - 📝 객체를 복사하고 싶을 땐 `Conversion Constructor`와 `Conversion Factory`를 사용하고 배열의 경우만 예외로 처리하면 된다.

  - `Comparable을 구현할지 고려하라.`
    - 🏷 순서를 고려해야 하는 값 클래스를 작성한다면 꼭 Comparable 인터페이스를 구현하여 그 인스턴스들을 쉽게 정렬하고 검색하고 비교 기능을 제공하는 컬렉션과 어우러지도록 해야 한다. compareTo 메서드에서 필드의 값을 비교할 때 < 와 > 연산자는 쓰지 말아야 한다. 그 대신 박싱된 기본 타입 클래스가 제공하는 정적 compare 메서드나 Comparator 인터페이스가 제공하는 비교자 생성 메서드를 사용하자.
    - compareTo와 equals는 비슷하지만 2가지의 차이점이 있다.
      - compareTo를 구현했다는 것은 그 클래스의 인스턴스들에 `자연적인 순서가 있다는 뜻`이다. 이러한 객체들은 Arrays.sort(a);를 통해 손쉽게 정렬할 수 있다.
      - 중복을 제거하고 알파벳순으로 출력하는 예제
        <details>
          <summary>예제 코드</summary>

          ```java
          public class WordList {
            public static void main(String[] args){
              Set<String> s = new TreeSet<>();
              Collections.addAlls(s, args);
              System.out.println(s);
            }
          }
          ```
        </details>
    - compareTo 규약
      - 첫번째, 두 객체 참조 순서를 바꿔서 비교해도 예상한 결과가 나와야 한다.
      - 두번째, A < B, B < C 이면 A < C
      - 세번째, 크기가 같은 객체들끼리는 어떤 객체와 비교하더라고 항상 같아야 한다.
    - `equals와 같은 주의사항`
      - 기존 클래스를 확장한 구체 클래스에서 새로운 값 컴포너트를 추가했다면 compareTo 규악을 지킬 수 없다.
      - Compareable을 구현한 클래스를 확장해 값 컴포넌트를 추가하고 싶다면 확장하는 대신 독립된 클래스를 만들고 이 클래스에서 원래 클래스의 인스턴스를 가리키는 필드를 둬야 한다.
    - 클래스에서 핵심 필드를 먼저 비교하고 다른 필드를 비교하자.
      <details>
        <summary>PhoneNumber 클래스용 compareTo 예제 코드</summary>
            
        ```java
        public int compareTo(PhoneNumber pn) {
          int result = Short.compare(areaCode, pn.areaCode);
          if(result == 0){
            result = Short.compare(prefix, pn.prefix);
            if(result == 0){
              result = Short.compare(lineNum, pn.lineNum);
            }
          }
          return result;
        }
        ```
      </details>
    - 비교자 생성 메서드를 사용하면 코드는 훨씬 깔끔하지만 성능이 저하된다.
      <details>
        <summary>비교자 생서 메서드를 활용한 예제 코드</summary>
            
        ```java
        private static final Comparator<PhoneNumber> COMPARATOR = 
          comparingInt((PhoneNumber pn) -> pn.areaCode)
            .thenComparingInt(pn -> pn.prefix)
            .thenComparingInt(pn -> pn.lineNum);
        
        public int compareTo(PhoneNumber pn) {
          return COMPARATOR.compare(this, pn);
        }
        ```
      </details>
    - 값의 차이를 기준으로 두 값을 비교할 때 유의해야 한다.
      <details>
        <summary>잘못된 코드 - 추이성을 위배</summary>
            
        ```java
        static Comparator<Object> hashCodeOrder = new Comparator<>() {
          public int compare(Object o1, Object o2) {
            return o1.hashCode() - o2.hashCode();
          }
        }
        ```
      </details>

      <details>
        <summary>정적 compare 메서드를 활용한 비교자</summary>
            
        ```java
        static Comparator<Object> hashCodeOrder = new Comparator<>() {
          public int compare(Object o1, Object o2) {
            return Integer.compare(o1.hashCode(), o2.hashCode());
          }
        }
        ```
      </details>

      <details>
        <summary>비교자 생성 메서드를 활용한 비교자</summary>
            
        ```java
        static Comparator<Object> hashCodeOrder = 
          Comparator.comparingInt(o -> o.hashCode());
        ```
      </details>
      
---

🏷 💡 📝

<details>
  <summary>예제 코드</summary>
      
</details>
