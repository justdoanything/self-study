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

- 💦 `private 생성자나 열거 타입으로 싱글턴임을 보장하라.`

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

    ```

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

### 4장 클래스와 인터페이스

- `클래스와 멤버의 접근 권한을 최소화하라`
  - 🏷 프로그램 요소의 접근성은 가능한 한 최소한으로 하라. 꼭 필요한 것만 골라 최소한의 public API를 설계하자. 그 외에는 클래스, 인터페이스, 멤버가 의도치 않게 API로 공개 되는 일이 없도록 해야 한다. public 클래스는 상수용 public static final 필드 외에는 어떠한 public 필드도 가져서는 안된다. public static final 필드가 참조하는 객체가 불변인지 확인하라.
  - 모든 클래스와 멤버의 접근성을 가능한 한 좁혀야 한다. public을 최대한 줄이자.
  - public 클래스의 인스턴스 필드는 되도록 public이 아니어야 한다.
- `public 클래스에서는 public 필드가 아닌 접근자 메서드를 사용하라`
  - 🏷 public 클래스는 절대 가변 필드를 직접 노출해서는 안된다. 불변 필드라면 노출해도 덜 위험하지만 완전히 안심할 수는 없다. 하지만 package-private 클래스나 private 중첩 클래스에서는 종종 (불변이든 가변이든) 필드를 노출하는 편이 나을 때도 있다.
- `변경 가능성을 최소화하라.`

  - 불변 클래스 : 인스턴스 내부 값을 수정할 수 없는 클래스. 객체가 파괴되는 순간까지 절대 달라지지 않는다.
    - 객체의 상태를 변경하는 함수를 제공하지 않아야 한다.
    - 클래스를 확장할 수 없도록 해야 한다.
    - 모든 필드를 final로 선언해야 한다.
    - 모든 필드를 private로 선언해야 한다.
    - 자신 외에는 내부의 가변 컴포넌트에 접근할 수 없도록 해야 한다.
  - 불변 객체는 근본적으로 스레드로부터 안전하여 따로 동기화할 필요가 없다.
  - 자주 쓰는 값들을 상수(public static final)로 제공하는 것이 좋다.
  - 값이 다르면 반드시 독립된 객체로 만들어야 한다는 단점이 있다.
  - 함수형 프로그래밍 : 피연산자에 함수를 적용해서 그 결과를 반환하지만 피연산자 자체는 그대로인 프로그래밍 패턴.
    - 함수 이름도 add와 같은 동사를 쓰는 대신 plus와 같은 전치사를 사용한 이유는 함수가 객체의 값을 변경하지 않는다는 사실을 강조하기 위함이다.
    <details>
      <summary>예제 코드</summary>
      
      ```java
      public final class Complex {
        ...
        public Complex plus(Complex c){
          return new Complex(re + c.re, im + c.im);
        }

        public Complex minus(Complex c){
          return new Complex(re - c.re, im - c.im);
        }
      }
      ```
    </details>
      

  - 불변으로 만들 수 없는 클래스라도 변경할 수 있는 부분을 최소한으로 줄이고 모든 필드는 private final 이어야 좋다.

- `상속보다는 컴포지션을 사용하라`

  - 매서드 호출과 달리 상속은 캡슐화를 깨뜨린다.
  - 기존 클래스를 확장하는 대신 새로운 클래스를 만들고 private 필드로 기존 클래스의 인스턴스를 참조하게 하는 것처럼 기존 클래스가 새로운 클래스의 구성요소로 쓰인다는 뜻에서 `Composition` 이라고 부른다.
    <details>
      <summary>예제 코드</summary>
    
      ```java
      public class InstrumentedSet<E> extends ForwardingSet<E> {
        // 상속 대신 Composition을 사용한 클래스
        private int addCount = 0;

        public InstrumentedSet(Set<E> s) {
          super(s);
        }

        @Override
        public boolean add(E e) {
          addCount++;
          return super.add(e);
        }

        @Override
        public boolean addAll(Collection<? extends E> c) {
          addCount += c.size();
          return super.addAll(c);
        }

        public int getAddCount() {
          return addCount;
        }
      }

      public class ForwardingSet<E> implements Set<E> {
        // 재사용할 수 있는 전달 클래스
        private final Set<E> s;
        public ForwardingSet(Set<E> s) { this.s = s; }

        public void clear() { s.clear(); }
        public boolean contains(Object e) { return s.contains(o); }
        ...
      }
      ```
    </details>

  - 💦 다른 Set 인스턴스를 감싸고 있다는 뜻에서 InstrumentedSet 같은 클래스를 래퍼 클래스라고 한다. 다른 Set에 계측 기능을 덧씌운다는 뜻에서 데코레이션 패턴이라고 한다. 컴포지션과 전달의 조합은 넓은 의미로 위임이라고 부른다. 엄밀히 따지면 래퍼 객체가 내부 객체에 자기 자신의 참조를 넘기는 경우만 위임에 해당한다. 래퍼 클래스는 거의 단점이 없다. 한 가지, 래퍼 클래스가 콜백 프로임워크와는 어울리지 않는다는 점만 주의하면 된다. 콜백 프레임워크에서는 자기 자신의 참조를 다른 객체에 넘겨서 다음 호출(콜백)때 사용하도록 한다. 내부 객체는 자신을 감ㅈ싸고 있는 래퍼의 존재를 모르니 대신 자시(this)의 참조를 넘기고 콜백 때는 래퍼가 아닌 내부 객체를 호출하게 된다. 이를 SELF 문제라고 한다.
  - 🏷 상속은 강력하지만 캡슐화를 해친다는 문제가 있다. 상속은 상위 클래스와 하위 클래스가 순수한 is-a 관계일 때만 써야 한다. is-a 관계일 때도 안심할 수만은 없는 게, 하위 클래스의 패키지가 상위 클래스와 다르고, 상위 클래스가 확장을 고려해 설계되지 않았다면 여전히 문제가 될 수 있다. 상속의 취약점을 피하려면 상속 대신 컴포지션과 전달을 사용하자. 특히 래퍼 클래스로 구현할 적당한 인터페이스가 있다면 더욱 그렇다. 래퍼 클래스는 하위 클래스보다 견고하고 강력하다.

- `상속을 고려해 설계하고 문서화하라. 그러지 않았다면 상속을 금지하라`
  - java.util.AbstractCollection의 remove(Object o) 함수를 예로 들면 remove 함수는 iterator 메서드를 사용해서 객체를 순회하고 일치하는 원소가 있다면 제거한다. 여기에서 iterator 메서드를 재정의하면 remove 메서드의 동작에 영향을 줄 수 있다는 것을 알 수 있다. 이런 내용이 API 문서에 명시되어 있다. HashSet을 상속하여 add를 재정의한 것이 addAll에까지 영향을 준다는 사실을 알 수 없었는데 이와 대조적이다.
  - 클래스의 내부 동작 과정 중간에 끼어들 수 있는 훅(hook)을 잘 선별하여 protected 메서드 형태로 공개해야할 수도 있다. 상속용 클래스를 시험하는 방법은 직접 하위 클래스를 만들어보는 것이 유일하다. 상속용으로 설계한 클래스는 배포 전에 반드시 하위 클래스를 만들어서 검증해야 한다.
  - 상속용 클래스의 생성자는 직접적으로든 간접적으로든 재정의 가능 메서드를 호출해서는 안된다. 상위 클래스의 생성자가 하위 클래스의 생성자보다 먼저 실행되므로 하위 클래스에서 재정의한 메서드가 하위 클래스의 생성자보다 먼저 호출된다.
  - clone과 readObject 모두 직접적으로든 간접적으로든 재정의 가능 메서드를 호출해서는 안된다.
  - 이러한 문제들을 해결하기 가장 좋은 방법은 상속용으로 설계하지 않은 클래스의 상속을 금지하는 것이다. 클래스를 final로 선언하는 방법이 있고 모든 생성자를 private, package-private로 선언하고 public 정적 팩터리를 만들어주는 방법이 있다.
  - API나 클래스에 대한 설명을 @implSpec 자바독 태그를 붙여서 문서화하는 것이 좋다.
  - 🏷 상속용 클래스를 설계하기란 결코 만만치 않다. 클래스 내부에서 스스로를 어떻게 사용하는지(자기사용 패턴) 모두 문서로 남겨야 하며, 일단 문서화한 것은 그 클래스가 쓰이는 한 반드시 지켜야 한다. 그러지 않으면 그 내부 구현 방식을 믿고 활용하던 하위 클래스를 오동작하게 만들 수 있다. 다른 이가 효율 좋은 하위 클래스를 만들 수 있도록 일부 메서드를 protected로 제공해야 할수도 있다. 그러니 클래스를 확장해야 할 명확한 이유가 떠오르지 않으면 상속을 금지하는 편이 나을 것이다. 상속을 금지하려면 클래스를 final로 선언하거나 생성자 모두를 외부에서 접근할 수 없도록 만들면 된다.

- `추상 클래스보다는 인터페이스를 우선하라`
  - 추상 클래스와 인터페이스의 차이점
    - 추상 클래스는 "is a", "is kind of" 개념이고 인터페이스는 "has a", "is able to" 개념이다. 상속의 의미를 볼 때 "포유류는 동물이다.", "고래는 포유류다.", "고래는 동물이다."는 말이 되기 때문에 동물, 포유류는 추상 클래스가 될 수 있다. 인터페이스는 클래스의 공통 특징이 아니라 공통 기능에 정의할 때 사용한다. "동물", "식물", "포유류"가 추상 클래스가 된다면 "짖을 수 있는", "먹을 수 있는", "걸을 수 있는"이 인터페이스가 될 수 있다.
    - 추상 클래스는 상속을 받아서 기능을 이용하고 확장시키는데 있고 인터페이스는 함수의 구현을 강제하기 위한 것이다.
    - 추상 클래스는 다중 상속이 불가능하지만 인터페이스는 다중 상속이 가능하다.
  - 인터페이스는 인스턴스 필드를 가질 수 없고 public이 아닌 정적 멤버도 가질 수 없다.
  - 인터페이스와 추상 골격 구현 클래스를 함께 제공하는 식으로 인터페이스와 추상 클래스의 장점을 모두 취하는 방법도 있다. 추상 골격 클래스는 Abstract로 시작하는 것이 일반적이다. 관례상 인터페이스가 Interface로 시작한다면 AbstractInterface~ 로 짓는 것이 일반적이다.
    <details>
      <summary>예제 코드</summary>

      ```java
      static List<Integer> intArrayAsList(int[] a){
        Object.requireNonNull(a);

        return new AbstractList<Integer>() {
          @Override
          public Integer get(int i) {
            return a[i];
          }

          @Override
          public Integer set(int i, Integer val) {
            int oldVal = a[i];
            a[i] = val;
            return oldVal;
          }

          @Override
          public int size() {
            return a.length;
          }
        }
      }
      ```  
    </details>
  - 🏷 일반적으로 다중 구현용 타입으로는 인터페이스가 가장 적합하다. 복잡한 인터페이스라면 구현하는 수고를 덜어주는 골격 구현을 함께 제공하는 방법을 꼭 고려해보자. 골격 구현은 '가능한 한' 인터페이스의 디폴트 메서드를 제공하여 그 인터페이스를 구현한 모든 곳에서 활용하도록 하는 것이 좋다. '가능한 한'이라고 한 이유는, 인터페이스에 걸려 있는 구현상의 제약 때문에 골격 구현을 추상 클래스로 제공하는 경우가 더 흔하기 때문이다.

- `인퍼테이스는 구현하는 쪽을 생각해 설계하라`

- `인터페이스는 타입을 정의하는 용도로만 사용하라`
  - 상수 인터페이스 안티패턴은 인터페이스를 잘못 사용한 예다. 상수를 공개할 목적이라면 특정 클래스나 인터페이스 자체에 추가해야 한다. 대표적으론 Integer, Double의 MAX_VALUE, MIN_VALUE 이다. 열거 타입으로 나타내기 적합한 상수라면 열거 타입으로 만들어서 공개하면 된다.
  - 🏷 인터페이스는 타입을 정의하는 용도로만 사용해야 한다. 상수 공개용 수단으로 사용하지 말자.

- `태그 달린 클래스보다는 클래스 계층구조를 활용하라`
  - 태그 달린 클래스
    <details>
      <summary>예제 코드</summary>

      ```java
      class Figure {
        enum Shape { RECTANGLE, CIRCLE };

        // 태그 필드 - 현재 모양을 나타낸다.
        final Shape shape;
        ...
      }
      ```
    </details>
  - 태그 달린 클래스는 열거 타입 선언, 태그 필드, switch 등 불필요한 코드가 많으며 한 클래스에 여러 구현체가 혼합되어 있어서 가독성도 나쁘다.
  - 태그 달린 클래스를 클래스 계층구조로 바꿔야 한다. root가 될 추상 클래스를 정의하고 태그 값에 따른 동작이 달라지는 메서드들을 root 클래스의 추상 메서드로 선언한다. 
    <details>
      <summary>예제 코드</summary>

      ```java
      abstract class Figure {
        abstract double area();
      }
      
      class Circle extends Figure {
        final double radius;
        @Override
        double area() { return Math.PI * (radius * radius); }
      }

      class Rectangle extends Figure {
        final double length;
        final double width;
        @Override
        double area() { return length * width; }
      }
      ```
    </details>
  - 🏷 태그 달린 클래스를 써야 하는 상황은 거의 없다. 새로운 클래스를 작서앟는데 태그 필드가 등장한다면 태그를 없애고 계층구조로 대체하는 방법을 생각해보자. 기존 클래스가 태그 필드를 사용하고 있다면 계층구조로 리팩터링하는걸 고민해보자.

- `멤버 클래스는 되도록 static으로 만들어라`
  - 중첩 클래스란 다른 클래스 안에 정의된 클래스를 말한다. 중첩 클래스는 자신을 감싼 바깥 클래스에서만 쓰여야 하며 그 외의 쓰임새가 있다면 톱레벨 클래스로 만들어야 한다.
  - 중첩 클래스의 종류는 4가지로 `정적 멤버 클래스`, `비정적 멤버 클래스`, `익명 클래스`, `지역 클래스` 이다. 이 중에서 정적 멤버 클래스를 제외한 나머지는 내부 클래스에 해당한다.
  - 정적 클래스 : 다른 클래스 안에서 선언되고 바깥 클래스의 private 멤버에도 접근할 수 있다는 점만 제외하면 일반 클래스와 똑같다. 바깥 클래스와 같이 쓰일 때 유용한 public 도우미 클래스로 쓰인다. 예를들어 Calculator.Operation.PLUS, Calculator.Operation.MINUS 와 같은 연산을 참조할 수 있다.
  - 비정적 멤버 클래스 : static이 붙어 있고 없고의 차이이다. 비정적 멤버 클래스의 인스턴스는 바깥 클래스의 인스턴스와 암묵적으로 연결된다. 정규화된 this를 사용해서 바깥 인스턴스의 메서드를 호출하거나 바깥 인스턴스의 참조를 가져올 수 있다. ~클래스명.this~ 형태로 사용할 수 있다. 따라서 개념상으론 중첩 클래스의 인스턴스가 바깥 인스턴스와 독립적으로 존재할 수 있다면 정적 멤버 클래스로 만들어야 한다. 비정적 멤버 클래스는 바깥 인스턴스 없이는 생성할 수 없기 때문이다.
  - 멤버 클래스에서 바깥 인스턴스에 접근할 일이 없다면 무조건 static을 붙여서 정적 멤버 클래스로 만들자. static을 생략하면 바깥 인스턴스의 숨은 외부 참조를 갖게 되고 GC에 의해서 인스턴스가 수거되지 않으면 메모리 누수 등 예기치 못한 문제가 발생할 수 있다.
  - 익명 클래스 : 멤버와 달리 쓰이는 시점에 선언과 동시에 인스턴스가 만들어진다. 인터페이스를 구현할 수 없고 비정적인 문맥에서 사용될때만 바깥 클래스의 인스턴스를 참조할 수 있는 등 제약조건이 많다. 람다를 지원하기 전에는 종종 쓰였는데 현재는 람다에게 자리를 물려줬고 정적 팩터리 메서드를 구현할 때 주로 쓰인다. 
  - 지역 클래스 : 지역변수를 선언할 수 있는 곳이면 실질적으로 어디서든 선언할 수 있고 유효 범위도 지역변수와 같다. 다른 중첩 클래스와 공통점이 있는데 멤버 클래스처럼 이름이 있고 반복해서 사용할 수 있으며 익명 클래스처럼 비정적 문맥에서 사용될 때만 바깥 인스턴스를 참조할 수 있으며 정적 멤버는 가질 수 없다. 가독성을 위해 짧게 작성해야 한다.
  - 🏷 중첩 클래스에는 네가지가 있으며 각각의 쓰임이 다르다. 메서드 밖에서도 사용해야 하거나 메서드 안에 정의하기엔 너무 길다면 멤버 클래스로 만든다. 멤버 클래스의 인스턴스 각각이 바깥 인스턴스를 참조한다면 비정적으로, 그렇지 않으면 정적으로 만들자. 중첩 클래스가 한 메서드 안에서만 쓰이면서 그 인스턴스를 생성하는 지점이 단 한 곳이고 해당 타입으로 쓰기에 적합한 클래스나 인터페이스가 이미 있다면 익명 클래스로 만들고 그렇지 않으면 지역 클래스로 만들자.

- `톱레벨 클래스는 한 파일에 하나만 담으라`
  - Main이 다른 2개의 톱레벨 클래스를 참조하는 코드를 보자.
    <details>
      <summary>예제 코드</summary>

      ```java
      public class Main {
        public static void main(String[] args){
          System.out.println(Utensil.NAME + Dessert.NAME);
        }
      }

      class Utensil {
        static final String NAME = "pan";
      }

      class Dessert {
        static final String NAME = "cake";
      }

      // 별도의 파일 Dessert.java에 아래와 같이 정의되어 있다.
      class Utensil {
        static final String NAME = "pot";
      }

      class Dessert {
        static final String NAME = "pie";
      }
      ```
    </details>
  - 위 코드는 컴파일러에 어느 소스 파일을 먼저 건네느냐에 따라 동작이 달라진다. 따라서 톱레벨 클래스들은 서로 다른 소스 파일로 분리해야 한다.
  - 🏷 교훈을 명확하다. 소스 파일 하나에는 반드시 톱레벨 클래스(혹은 톱레벨 인터페이스)를 하나만 담자. 이 규칙만 따른다면 컴파일러가 한 클래스에 대해 정의를 여러개 만들어내는 일은 사라진다. 소스 파일을 어떤 순서로 컴파일하든 바이너리 파일이나 프로그램의 동작이 달라지는 일은 결코 일어나지 않을 것이다.

### 5장 제네릭

- `로 타입은 사용하지 말라`

- `비검사 경고를 제거하라`

- `배열보다는 리스트를 사용하라`

- `이왕이면 제네릭 타입으로 만들라`

- `이왕이면 제네릭 메서드로 만들라`

- `한정적 와일드카드를 사용해 API 유연성을 높이라`

- `제네릭과 가변인수를 함께 쓸 때는 신중하라`

- `타입 안전 이종 컨테이너를 고려하라`

### 6장 제네릭
- `int 상수 대신 열거 타입을 사용하라`

- `ordinal 메서드 대신 인스턴스 필드를 사용하라`

- `비트 필드 대신 EnumSet을 사용하라`

- `ordinal 인덱싱 대신 EnumMap을 사용하라`

- `확장할 수 있는 열거 타입이 필요하면 인터페이스를 사용하라`

- `명명 패턴보다 애너테이션을 사용하라`

- `@Override 애너테이션을 일관되게 사용하라`

- `정의하려는 것이 타입이라면 마커 인터페이스를 사용하라`




---

🏷 💡 📝 💦

<details>
  <summary>예제 코드</summary>
</details>
