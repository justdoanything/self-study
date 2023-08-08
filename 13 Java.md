Java
===

# 목차
<!-- TOC -->
* [0️⃣ Java](#0-java)
  * [SOLID (객체지향 설계) 원칙](#solid--객체지향-설계--원칙)
  * [캡슐화](#캡슐화)
  * [응집도와 결합도](#응집도와-결합도)
* [1️⃣ JVM](#1-jvm)
  * [1. JVM이란?](#1-jvm이란)
  * [2. JVM 동작 방식](#2-jvm-동작-방식)
  * [3. JVM 구조와 설명](#3-jvm-구조와-설명)
* [2️⃣ Garbage Collection (GC)](#2-garbage-collection--gc-)
  * [1. GC의 특징](#1-gc의-특징)
  * [2. Heap 메모리 구조](#2-heap-메모리-구조)
  * [3. Young 영역](#3-young-영역)
  * [4. Old 영역](#4-old-영역)
  * [5. Minor GC 과정](#5-minor-gc-과정)
  * [6. Major GC 과정](#6-major-gc-과정)
  * [7. GC의 종류](#7-gc의-종류)
* [3️⃣ REST API](#3-rest-api)
  * [1. REST API 종류와 역할](#1-rest-api-종류와-역할)
  * [2. PUT과 PATCH의 차이](#2-put과-patch의-차이)
* [4️⃣ ThreadLocal](#4-threadlocal)
  * [1. Thread 공통](#1-thread-공통)
  * [2. ThreadLocal](#2-threadlocal)
* [5️⃣ Java Design Pattern](#5-java-design-pattern)
  * [1) Creational Pattern](#1--creational-pattern)
  * [2) Structural Pattern](#2--structural-pattern)
  * [3) Behavioral Pattern](#3--behavioral-pattern)
<!-- TOC -->

---

# 0️⃣ Java
## SOLID (객체지향 설계) 원칙
- `SRP` (Single Responsibility Principle (단일 책임 원칙)) : 한 클래스는 하나의 책임만 가져야 한다.
- `OCP` (Open/Closed Principle (개방 폐쇠 원칙)) : 소프트웨어 요소는 확장에는 열려 있으나 변경에는 닫혀 있어야 한다.
- `LSP` (Liskov substitution Principle (리스코프 치환 원칙)) : 프로그램의 객체는 프로그램의 정확성을 깨드리지 않으면서 하위 타입의 인스턴스로 바꿀 수 있어야 한다.
- `ISP` (Interface Segregration Pringciple (인터페이스 분리 원칙)) : 특정 클라이언트를 위한 인터페이스 여러 개가 범용 인터페이스 하나보다 낫다.
- `DIP` (Dependency Inversion Principle (의존관계 역전 원칙)) : 프로그래머는 추상화에 의존해야지 구체화에 의존하면 안된다.
- Reference : https://ko.wikipedia.org/wiki/SOLID_(%EA%B0%9D%EC%B2%B4_%EC%A7%80%ED%96%A5_%EC%84%A4%EA%B3%84)

## 캡슐화
- 객체의 속성과 행위를 하나로 묶는다
- 실제 구현 내용은 내부에 감추어 은닉한다.

## 응집도와 결합도
- `높은` 응집도 : 모듈에 포함된 내부 요소들이 하나의 책임/목적을 위해 연결되어 있는지에 대한 정도
- `낮은` 결합도 : 다른 모듈과의 의존성을 나타내는 정도

# 1️⃣ JVM
## 1. JVM이란?
- Java는 플랫폼에 상관없이 사용할 수 있다.
    - JVM은 하나의 byte code인 .class를 사용하고 .class는 JVM 위에서 사용되고 JVM은 운영체제에 따라 알아서 실행파일을 만들고 실행합니다.
    - 따라서 운영체제와 상관없이 Java 언어로 프로그램을 만들 수 있습니다.
- JVM은 플랫폼에 종속적이다.
    - 플랫폼에 따라 JVM은 달리지며 설치되어야 합니다.
- byte code를 읽는 방식
    - JVM은 byte code를 명령어 단위로 읽어서 해석하는데 `Interpreter` 방식과 `JIT Compile` 방식 2가지를 혼용합니다.
    - Interpreter 방식 : byte code를 한 줄씩 해석해서 실행하는 방식. 하지만 속도가 느립니다.
        - JIT(Just In Time) Compile 방식 : btye code를 실제 실행하는 시점에 각 플랫폼에 맞는 Native Code로 변환시켜서 실행하는 방식. 하지만 Native Code로 변환시킬 때 비용이 많이 소요되므로 모든 코드를 JIT Compiler 방식으로 하지 않고 Interpreter 방식을 사용하다가 일정 기준이 넘어가면 JIT Compiler 방식으로 명령어를 실행합니다.
        - 인터프리터에서 JIT로 변경하는 기준은 ?
    - JIT Compiler
        - 같은 코드를 계속 해석하지 않고 코드를 실행할 때 컴파일 하면서 해당 코드를 caching 하고 이후에는 바뀐 코드만 다시 컴파일하고 기존에 있던 코드는 캐싱된 코드를 사용하기 때문에 Interpreter에 비해 속도가 월등히 빠릅니다.

<img width="657" alt="image" src="https://user-images.githubusercontent.com/21374902/205311306-c1e245fb-2a44-4ae6-98ca-eccb14409bbd.png">

## 2. JVM 동작 방식
- Java Application이 실행되면 JVM은 OS로부터 Memory를 할당한다.
- Java Compiler(javac)가 Java Code(.java)를 Java Byte Code(.class)로 Compile 한다.
- Class Loader를 통해서 JVM Runtime Data Area로 Loading 한다.
- Runtime Data Area에 Loading 된 Java Byte Code(.class)는 Excution Engine을 통해 해석한다.
- 해석된 Byte Code는 Runtime Data Area의 각 영역에 배치되어 수행되는 과정에서 Execution Engine에 의해 GC 동작과 Thread 동기화가 이뤄진다.

## 3. JVM 구조와 설명
- ### `Class Loader`
    - Java는 동적으로 Class를 불러오기 때문에 프로그램이 실행 중일 때 (Runtime), 모든 코드가 JVM과 연결된다. 이러한 동적으로 Class를 Load 해주는 것이 Class `Loader` 이다. `Class Loader`는 .class 파일을 묶어서 JVM이 운영체제로 부터 할당 받은 `Runtime Data Area`로 적재한다. (Compiler는 .java를 .class로 변환해준다.)

      <img width="342" alt="스크린샷 2022-12-10 15 36 02" src="https://user-images.githubusercontent.com/21374902/206893474-4bb88536-1f23-41a2-9eea-488275e4ec49.png">

- ### `Execution Engine`
    - JVM의 `Runtime Data Area`의 `Method Area`에 배치된 Byte Code(.class)을 `Execution Engine`에 제공하여 정의된대로 Byte Code를 실행시키는 역할을 한다. 짧게 말하면 Byte Code를 명령어 단위로 읽어서 실행시키는 Runtime Module 이라고 할 수 있다.
- ### `Garbage Collector (GC)`
    - 더이상 사용하지 않는 메모리를 자동으로 회수해주는 역할을 한다. 이는 개발자가 따로 메모리 관리를 하지 않아도 되서 프로그래밍이 쉬워진다. Heap 메모리에 생성되있는 객체들 중에서 참조되지 않는 객체들을 탐색하고 제거해주는 역할도 하며 시점은 특정할 수 없다. GC가 수행되면 GC 역할을 수행하는 Thread가 아닌 다른 Thread들은 모두 일시중지가 된다.

      <img width="355" alt="스크린샷 2022-12-10 15 36 07" src="https://user-images.githubusercontent.com/21374902/206893547-9e4f6e59-fb52-45a6-b72a-606564860d28.png">

- ### `Runtime Data Area`

  <img width="781" alt="스크린샷 2022-12-10 15 36 21" src="https://user-images.githubusercontent.com/21374902/206893557-0350bdbb-eee7-487c-9cf6-dd1d6af7d958.png">

    - JVM의 메모리 영역으로 Java Application을 실행할 때 사용되는 데이터들을 적재해서 사용하는 영역
    - 모든 Thread가 공유해서 사용하는 영역 (GC의 대상)
        - #### `Method Area` : 클래스 멤버 변수의 이름, 데이터 타입, 접근 제어자 정보와 같은 각종 필드 정보들과 메서드 정보, 데이터 Type 정보, Constant Pool, static변수, final class 등이 생성되는 영역
        - #### `Heap Area` : new 키워드로 생성된 객체와 배열이 생성되는 영역

          <img width="587" alt="스크린샷 2022-12-10 15 36 25" src="https://user-images.githubusercontent.com/21374902/206893577-dd6f728d-e975-4e47-99df-f5f503bfb358.png">

            - ##### `Young Generation` : 객체가 생성됐을 때 저장되는 영역으로 Heap 영역에 객체가 최초로 생성되면 `Eden` 영역에 할당된다. `Eden 영역에 데이터가 어느정도 쌓이게 되면 참조 정도에 따라 `Servivor`의 빈 공간으로 이동되거나 회수된다.
            - ##### `Tenured Generation` : `Young Generation` 영역이 어느정도 차게되면 참조 정도에 따라 `Old` 영역으로 이동되거나 회수 된다.
                - ###### `Minor GC` : `Young`과 `Tenured` 에서 실행되는 GC
                - ###### `Major GC` : `Old` 영역에 할당된 메모리가 허용치를 넘게되서 `Old` 영역 내 모든 객체들을 검사하고 사용하지 않는 객체는 삭제하는 작업`(Stop-The-World)`으로 시간이 오래 걸리고 그동안 모든 Thread는 중단된다.
            - ##### `Permanent Generation` : Java 8 부터 사라진 영역. Class, Method Code가 저장되는 영역이다.
- ### `각 Thread가 생성하는 영역`
    - #### `Stack Area` : 지역변수, 파라미터, 리턴 값, 연산에 사용되는 임시 값 등이 생성되는 영역
    - #### `PC Register` : Thread가 생성될 때마다 생성되는 영역으로 프로그램 카운터, 즉 현재 Thread가 실행되는 부분의 주소와 명령을 저장하고 있는 영역
    - #### `Native Method Stack` : Java 이외의 언어(C, C++, 어셈블리 등)로 작성된 Native Code를 실행할 때 사용되는 메모리 영역으로 일반적인 C 스택을 사용하고 보통 C/C++ 등의 코드를 수행하기 위한 스택을 말하며 `(JNI)` Java Compiler에 의해 변환된 Java Byte Code를 읽고 해석하는 역할을 하는 것이 `Java Interpreter`
## Reference
- [[Java] 자바 가상머신 JVM(Java Virtual Machine) 총정리](https://coding-factory.tistory.com/827)

---

# 2️⃣ Garbage Collection (GC)
## 1. GC의 특징
- GC란 JVM의 Heap 영역에서 동적으로 할당했던 메모리 중 필요 없게 된 메모리 객체를 모아서 주기적으로 제거하는 프로세스
- GC의 동작 시점을 알 수 없고 GC가 동작하는 동안에는 다른 동작을 모두 멈추기 때문에 Overhead가 발생할 수 있다.
- Application이 동작하면 객체들을 Head 영역에 생성되고 Method Area나 Stack Area 등 Root Area에서는 Heap Area에 생성 된 객체의 주소만 참조하는 형식으로 구성된다.
- Method가 끝나거나 생명 주기가 끝나거나 특정 이벤트로 인해서 Heap Area의 객체를 참조하고 있던`(Reachable)` 참조 변수들이 삭제되면 Heap Area에는 아무런 참조도 되지 않는 객체`(Unreachable)`들이 생기게 되고 이 객체들을 GC의 대상이 된다. 즉, GC는 객체를 `Reachable`/`Unreachable`로 구분하고 `Unreachable` 객체가 GC 대상이 된다.
- GC는 `Mark And Sweep` 알고리즘을 사용해서 동작한다.
    - `Mark(식별)` : Root로부터 그래프 순회를 돌면서 연결된 객체들을 찾아내어 각각 어떤 객체를 참조하고 있는지 Marking 한다.
    - `Sweep(제거)` : Unreachable한 객체들을 Heap Area에서 제거한다.
    - `Compact(재구성)` : Sweep 후에 분산된 객체들을 Heap의 시작 주소로 모아 메모리가 할당된 부분과 그렇지 않은 부분으로 압축한다. (GC 종류에 따라 하지 않는 경우도 있음)
    - GC의 Root로부터 해당 객체에 접근이 가능한지가 Sweep의 대상이 되는데 Heap 메모리 영역을 참조하는 method area, static 변수, stack, native method stack이 Root Space가 된다.

## 2. Heap 메모리 구조
- JVM의 Heap 영억은 Dynamic Reference 객체들이 저장되는 공간으로 GC의 대상이 되는 공간이다.
- Heap 영역은 2가지 전제로 설계되었다. (객체는 대부분 일회성이며 메모리에 오랫동안 남아 있는 경우는 드물다는 것)
    - 대부분의 객체는 금방 접근 불가능한 상태(`Unreachable`)가 된다.
    - 오래된 객체에서 새로운 객체로의 참조는 아주 적게 존대한다.
- 이러한 설계 조건을 바탕으로 효율적인 메모리 관리를 위해 객체에 생존 기간에 따라 물리적으로 Heap 영역을 `Young 영역`과 `Old 영역`으로 설계했다. (`Permanent 영역`은 Java 8부터 제거됨)
- `Permanent 영역` : 생성된 객체들의 주소값이 저장되는 공간으로 Class Loader에 의해 load 된 Class, Mathod에 대한 Meta 정보가 저장되는 영역이고 JVM에 의해 사용된다. Java 8 이후에는 Native Method Stack에 편입된다.

## 3. Young 영역
- 새롭게 생성된 객체가 할당되는 영역으로 대부분의 객체가 금방 `Unreachable` 상태가 되기 때문에 많은 객체가 생성되었다가 금방 사라진다.
- `Young 영역` 대상으로 이뤄지는 GC를 `Minor GC` 라고 한다.
- `Young 영역`은 3가지 영역으로 나뉜다.
    - `Eden` : `new`를 통해 생성된 객체가 위치하고 정기적으로 Garbage 수집 후 살아남은 객체들을 `Survivor` 영역으로 보낸다.
    - `Survivor 0`, `Survivor 1` : 최소 1번 이상 GC 대상에서 살아남은 객체가 존재하는 영역으로 `Survivor 0`과 `Survivor 1` 둘 중 하나는 꼭 비어 있어야 한다.

## 4. Old 영역
- `Young 영역`에서 `Reachable` 상태를 유지해서 살아남은 객체가 복사되는 영역으로 `Young 영역`보단 물리적으로 크게 할당되며 영역의 크기가 큰 만큼 Garbage는 `Young 영역`보다 적게 발생한다.
- `Old 영역` 대상으로 이뤄지는 GC를 `Majob GC` 또는 `Full GC` 라고 한다.

## 5. Minor GC 과정
- (1) 처음 생성된 객체는 __`Eden`__ 영역에 생성
- (2) __`Eden`__ 영역이 가득 찼을 때 `Minor GC` 실행 → `Reachable` 객체를 _`Mark`_
- (3) _`Mark`_ 된 객체들을 __`Survivor 0`__ 영역으로 이동
- (4) __`Eden`__ 영역에 `Unreachable` 객체를 _`Sweep`_
- (5) __`Survivor 0`__ 영역에 있는 객체들의 age를 1씩 증가
- (6) __`Eden`__ 영역이 가득 찼을 때 `Minor GC` 실행 → `Reachable` 객체를 _`Mark`_
- (7) __`Eden`__, __`Survivor 0`__ 에 있는 _`Mark`_ 된 객체들을 __`Survivor 2`__ 영역으로 이동
- (8) __`Eden`__, __`Survivor 0`__ 영역에 `Unreachable` 객체를 _`Sweep`_
- (9) __`Survivor 1`__ 영역에 있는 객체들의 age를 1씩 증가
- (10) 위 과정을 반복
  ```
  ❓ 객체의 `age` 란 ?
      Survivor 영역에서 살아남은 횟수를 의미하고 Object Header에 기록된다.
      age의 임계값에 다다르면 Promotion(Old 영역으로 이동) 여부를 결정한다.
      JVM의 일반적인 HotSpot JVM의 경우 age의 임계값은 31이다. 
      (Object Header에 age를 기록하는 부분이 6 bit 이기 때문이다.)
  ```

## 6. Major GC 과정
- `Minor GC` 과정에서 살아남은 객체들(age가 임계치에 다다른 객체들)은 `Old 영역`에 존재하고 `Old 영역`의 메모리가 부족해지면 `Major GC`가 발생한다.
- `Old 영역`에 있는 모든 객체들을 검사해서 참조되어 있지 않은 객체들을 삭제한다.
- `Young 영역`은 1초 내외로 `Minor GC`가 끝나지만 `Old 영역`은 큰 공간을 차지하고 있기 때문에 객체를 정리하는 시간이 오래 걸린다.
- 이 때, `Stop-the-world` 문제가 발생하고 CPU에 부담을 준다. 이를 해결하기 위해 GC 알고리즘은 발전해왔다. GC 알고리즘은 상황에 따라 설정을 통해 특정 알고리즘을 적용할 수 있다.
  ```
  ❓ 객체의 `Promotion` 이란?
      age의 임계치가 되서 Old 영역으로 이동되는 것
  ```

## 7. GC의 종류
- ### `Serial GC`
    - CPU 코어가 1개인 경우 사용하기 위해 개발된 가장 단순한 GC 알고리즘
    - Minor GC에는 `Mark-Sweep`, Major GC에는 `Mark-Sweep-Compact`를 사용한다.
    - CPU 코어가 1개이기 때문에 Stop-the-world 시간이 가장 길다.
    - 실행 명령어 : `java -XX:+UseSerialGC -jar Application.java`
- ### `Parallel GC`
    - Java 8의 기본 GC 알고리즘
    - Serial GC와 알고리즘은 같지만 Minor GC를 Multi Thread로 수행하고 Major GC는 Single Thread로 실행한다.
    - Serial GC에 비하면 Stop-the-world 시간이 감소한다.
    - 실행 명령어 : `java -XX:+UseParallelGC -XX:ParallelGCThreads=4 -jar Application.java `
        - `ParallelGCThreads` : 사용할 쓰레드의 갯수
- ### `Parallel Old GC` (Parallel Compacting Collector GC)
    - Parellel GC를 개선한 버전으로 Major GC에서도 Multi Thread로 실행한다.
    - 기존과 다른 `Mark-Summary-Compact` 방식을 사용한다.
    - 실행 명령어 : `java -XX:+UseParallelOldGC -XX:ParallelGCThreads=4 -jar Application.java`
        - `ParallelGCThreads` : 사용할 쓰레드의 갯수
  ```
  ❓ Mark-Summary-Compact
      Summary 단계는 Sweep과 달리 GC를 수행한 영역에 대한 객체 식별을 거친다.
  ```
- ### `CMS GC` (Concurrent Mark & Sweep GC)
    - Application의 Thread와 GC의 Thread를 동시에 실행해서 Stop-the-world 시간을 최대한으로 줄이기 위한 GC
    - 각 단계에서 한가지 일만 하기 때문에 Stop-the-world로 인한 부하가 가작 짧기 때문에 응답 속도가 매우 중요한 작업에서 많이 사용한다.
    - GC 대상을 파악하는 과정이 복잡한 여러단계로 수행되기 때문에 다른 GC에 비해 CPU 사용량이 높다.
    - Old 영역에 대한 Compact 작업이 기본적으로 이뤄지지 않기 때문에 메모리 파편화(Memory Fragmentation)가 발생하기 때문에 Compact 작업이 자주 일어나는지 확인해야 한다. Compact 작업에 따른 Stop-the-world 시간이 다른 GC보다 길어질 수 있다.
    - Java9 버전부터 deprecated 되었고 Java14 버전부터는 사용이 중지되었다.
    - 실행 명령어 : `java -XX:+UseConcMarkSweepGC -jar Application.java`
  ```
  ❓ CMS GC의 과정
        `Initial Mark` : Class Loader에서 가장 가까운 객체 중 살아있는 객체를 Mark 한다. (가장 짧은 Stop-the-world)
        `Concurrent Mark` : Initial Mark를 통해 살아있다고 확인한 객체에서 참조하는 객체를 Mark 한다.
        `Remark` : Concurrent Mark 단계에서 새로 추가되거나 참조가 끊긴 객체를 확인한다.
        `Concurrent Sweep` : Application Thread가 실행 중인 상태에서 Garbage를 수집한다.
  ```
- ### `G1 GC` (Garbage First)
    - CMS GC를 대체하기 위해 JDK 7 버전부터 Release 된 GC
    - Java 9 버전 이상부터 Default GC
    - 4 GB 이상의 메모리와 Stop-the-world 시간이 0.5초 정도 필요한 상황에 적합하고 Heap이 너무 작을 경우는 미사용 권장
    - Heap 영역을 Young, Old 영역으로 나누지 않고 Region 이라는 영역으로 체스같이 분할하여 상황에 따라 Eden, Survivor, Old 영역의 역할을 동적으로 부여
    - Garbage로 가득한 영역을 빠르게 회수하여 빈 공간을 확보하므로 GC 빈도가 줄어드는 효과가 있다.
    - Young Region 영역은 Parallel GC를 사용하고 Old Region 영역은 Stop-the-world와 Multi-thread 방식 둘 다 사용 가능하다.
    - 살아있는 객체들은 Young/Old의 GC 동안 Virtual Memory Address로 이동하기 때문에 메모리 파편화가 발생하지 않는다.
    - 실행 명령어 : `java -XX:+UseG1GC -jar Application.java`
 ```
 ❓ G1 GC의 Region
       기존의 GC들처럼 메모리를 탐색하면서 객체를 제거하지 않는 방식이 아닌 Garbage가 많은 Region을 우선적으로 GC 한다. 
       Heap 메모리를 Region 영역으로 나눠 탐색하고 Region 단위로 GC 하는 것이다.
       
       기존의 GC는 Eden -> Survivor0 -> Survivor1 으로 순차적으로 이동했지만
       G1 GC에서는 순차적으로 이동시키지 않고 효율적이라고 생각하는 위치로 객체를 Reallocate 시킨다.
 ```
 ```
 ❓ G1 GC의 과정
       `Initial Mark` : Old Region에 존재하는 객체들이 참조하는 Survivor Region을 Mark (Stop-the-world 발생)
       `Root Region Scan` : Initial Mark를 통해 찾은 Survivor Region에 대한 GC 대상 Scan 
       `Concurrent Mark` : 전체 Region을 Scan하고 GC 대상이 없는 Region은 제외
       `Remark` : 최종적인 GC 대상을 Remark (Stop-the-world 발생)
       `Cleanup` : 살아남은 객체가 가장 적은 Region을 대상으로 GC 수행 (Stop-the-world 발생)
                   비워진 Region을 재사용하기 위해 Free List에 추가
       `Copy` : GC가 동작했지만 완전히 비워지지 않은 Region은 새 Region으로 복사해서 Compaction 작업 수행
 ```   

- ### `Shenandoah GC`
    - JDK 12 버전부터 Release 된 GC (Redhat 에서 개발했다.)
    - CMS GC가 갖고 있는 메모리 파편화, G1 GC가 갖고 있는 pause 문제를 해결
    - 강력한 Concurrency와 가벼운 GC 로직으로 Heap 사이즈에 영향을 받지 않고 일정한 pause 시간이 소요되는 것이 특징
    - 실행 명령어 : `java -XX:+UseShenandoahGC -jar Application.java`

- ### `ZGC` (Z Garbage Collector)
    - JDK 15 버전부터 Release 된 GC (Redhat 에서 개발했다.)
    - 큰 사이즈의 메모리 (8MB ~ 16TB)를 low-latency로 처리하기 위해 고안된 GC
    - G1 GC의 Region 처럼 ZPage 라는 영역을 사용하며 Region은 크기가 고정이지만 ZPage는 2MB 배수로 크기를 동적으로 사용된다.
    - Heap 메모리의 크기가 증가해도 Stop-the-world 시간이 10ms를 넘지 않는 것이 큰 장점이다.
    - 실행 명령어 : `java -XX:+UnlockExperimentalVMOptions -XX:+UseZGC -jar Application.java`

## Reference
- [JAVA-☕-가비지-컬렉션GC-동작-원리-알고리즘-💯-총정리](https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EA%B0%80%EB%B9%84%EC%A7%80-%EC%BB%AC%EB%A0%89%EC%85%98GC-%EB%8F%99%EC%9E%91-%EC%9B%90%EB%A6%AC-%EC%95%8C%EA%B3%A0%EB%A6%AC%EC%A6%98-%F0%9F%92%AF-%EC%B4%9D%EC%A0%95%EB%A6%AC)

---

# 3️⃣ REST API
## 1. REST API 종류와 역할
메소드 | 설명                                         | 응답코드               | 에러코드
---|--------------------------------------------|--------------------|---
`GET` | 조회                                         | _Read Operation_   | 200 OK                 | 404 Not Found
`POST` | 정보 생성                                      | _Update Operation_ | 200 OK, 204 No-Content | 409 Conflict
`PUT` | 정보 변경 (속성 전체)                              | _Create Operation_ | 201 Created            | 400 Bad Request
`PATCH` | 정보 변경 (속성 일부)                              | _Create Operation_ | 201 Created            | 400 Bad Request
`DELETE` | 정보 삭제 | _Delete Operation_ | 204 No-Content     | 404 Not Found          
`HEAD` | 응답 헤더를 조회할 때 사용                            |                    | 200 OK                 |
`OPTIONS` | Allow 응답 헤더를 이용해 리소스에서 사용 가능한 메소드를 표기하는 용도 |                    | 200 OK             |
- CRUD 성격으로 구분할 수 없는 경우엔 `POST`를 사용한다.
- `HEAD`는 GET 요청을 통해 특정 리소스를 조회하기 전에 `결과 데이터 크기를 파악`하고 싶을 때 HEAD 메소드를 사용하면 된다. 응답 헤더의 `Content-Length` 값을 알면 데이터 크기를 알 수 있다.
## 2. PUT과 PATCH의 차이
- 간단히 요약하면 `PATCH`는 Resource에 일부분만 수정할 때 사용하고 `PUT`은 Resource의 모든 속성을 수정할 때 사용한다.
- `PUT`으로 요청할 때 Resource의 일부분만 보냈을 경우, 나머지는 기본값으로 수정되는게 원칙이다. 따라서 바꾸지 않을 속성도 같이 보내줘야 한다.
- `PATCH`는 요청한 일부분만 수정한다.

---

# 4️⃣ ThreadLocal
## 1. Thread 공통
- 한 Thread에서 읽고 쓰여질 수 있는 변수를 할당하여 접근할 수 있도록 한다. Multi Thread 환경에서 각 Thread 마다 get(), set() method를 통해 독립적으로 변수에 접근이 필요할 때 유용하다.
- Thread의 장점 : Multi Thread는 Multi Processing에 비해 `문맥 교환(Context Switch)`이라는 Overhead가 일어나지 않고 자원을 공유하기 때문에 Process 끼리 통신하는 것보다 비용이 적고 문맥교환이 적어서 효율적인 작업이 가능하다. CPU 사용률을 향상시키고 자원을 적게 소모하며 코드가 간결해진다.
- Thread의 단점 : 여러 Thread가 하나의 Process 내에서 자원을 공유하기 때문에 `동기화(Synchronization)` 문제, `교착상태(Deadlock)`와 같은 문제가 발생할 수 있다.
- Thread Safe
    - Multi Thread 환경에서 Thread끼리 객체를 공유할 때가 있는데 Thread가 동시에 접근하면 안되는 영역을 `임계 영역(Critical Section)` 이라고 하고 이 문제를 해결하기 위해서 `세마포어(Semaphore)`, `상호배제(Mutex)` 등의 개념이 있다.
    - `세마포어(Semaphore)` : 공유된 자원의 데이터를 여러 Process가 접근하지 못하도록 막는 것
    - `상호배제(Mutex)` : 공유된 자원의 데이터를 여러 Thread가 접근하지 못하도록 막는 것
    - Java의 `Synchronized` keyword를 사용해서 현재 데이터를 사용하고 있는 해당 Thread를 제외하고 다른 Thread는 데이터에 접근할 수 없도록 할 수 있고 이것을 `thread safe` 환경이라고 한다.
    - `thread safe`한 공유 자원에 다른 Thread가 접근하려고 하면 wait 상태가 되기 때문에 성능 저하가 발생할 수 있다.
## 2. ThreadLocal
- ThreadLocal은 Java에서 제공하는 Class 중에 하나이고 간단히 말하면 하나의 Thread에 의해서만 read/write가 가능한 변수라고 할 수 있다.
- 2개의 Thread가 같은 코드를 실행하고 하나의 ThreadLocal 변수를 참조하더라고 서로의 ThreadLocal은 각 Thread에서 독립적으로 사용되고 서로의 ThreadLocal을 볼 수 없다.
- 하나의 Thread에서 실행되는 코드가 동일한 객체를 사용해야할 때 `.set()`, `.get()` 를 사용해서 데이터를 사용하고 메모리 누수의 원인이 될 수 있기 때문에 사용이 끝나면 반드시 `.remove()`로 삭제해줘야 한다.
- 아래 예제코드에선 ClassA 에서 set(...)한 Date를 ClassB, ClassC 에서 get() 해서 사용한다. 즉 Parameter로 객체를 전달하지 않아도 여러 Thread에서 한 객체의 값을 참조하여 사용할 수 있다.
  ```java
  public static ThreadLocal<Date> tl = new ThreadLocal<>();
  
  class ClassA {
    public void execute() {
      tl.set(new Date());
      ClassB classB = new ClassB();
      classB.execute();
      tl.remove();
    }  
  }
  
  class ClassB {
    public void execute() {
      Date date = tl.get();
      ClassC classC = new ClassC();
      classC.execute();
    }  
  }
  
  class ClassC {
    public void execute() {
      Date date = tl.get();
    }  
  }      
  ```
## Reference
- https://yeonbot.github.io/java/ThreadLocal/

---

# 5️⃣ Java Design Pattern
### 🔰 예제코드 : https://github.com/justdoanything/self-study/tree/main/Java/src/main/java/book/pattern
## 1) Creational Pattern
- ###### Instance를 만드는 절차를 추상화해서 객체를 생성, 합성하는 방법이나 객체의 표현 방법을 시스템과 분리해준다.
- ###### 시스템이 어떤 Concrete Class를 사용하는지에 대한 정보를 캡슐화한다.
- ###### Class의 Instance들이 어떻게 만들어지고 결합되는지에 대한 부분을 가려준다.
- ### Factory Method Pattern
    - 여러 개의 Sub Class를 갖고 있는 Super Class가 있을 때 Input Parameter에 따라 하나의 Sub Class를 생성해주는 방식
    - Instance를 필요로 하는 Application에서 Sub Class에 대한 정보는 모른채 Instance를 생성할 수 있다.
    - Factory Class는 Singleton으로 구현하거나 Sub Class를 만드는 함수를 static으로 구현해야 한다.
    - Class 간의 종속성을 낮추고 결합도를 느슨하게 하며 확장을 쉽게 한다.
  ```java
  public abstract class Employee {
    public abstract String getId();
    public abstract String getName();
    public abstract String getDepartment();
  }
  ```
  ```java
  public class Developer extends Employee {
    private String id;
    private String name;
    private String department;

    public Developer(String id, String name, String department) {
      this.id = id;
      this.name = name;
      this.department = department;
    }

    @Override
    public String getId() {
      return this.id;
    }
    public String getName() {
      return this.name;
    }
    public String getDepartment() {
      return this.department;
    }
  }
  ```
  ```java
  public class Tester extends Employee {
    private String id;
    private String name;
    private String department;

    public Tester(String id, String name, String department) {
      this.id = id;
      this.name = name;
      this.department = department;
    }

    @Override
    public String getId() {
      return this.id;
    }
    public String getName() {
      return this.name;
    }
    public String getDepartment() {
      return this.department;
    }
  }
  ```
  ```java
  public class FactoryDesignPattern {
    public static Employee getEmployee(String type, String id, String name, String department) {
      switch(type){
        case Type.Developer : 
          return new Developer(id, name, department);
        case Type.Tester : 
          return new Tester(id, name, department);
        default :
          return null;
      }
    }
  }
  ```
- ### Abstract Factory Pattern
    - Factory Pattern와 유사한 Pattern으로 Factory of Factory 라고 볼 수 있다.
    - Factory Pattern에서 input parameter로 Sub Class를 구분했었다면 하나의 Factory Class를 input parameter로 받아서 Sub Class를 생성한다.
    - Interface를 위한 코드 접근법을 제공하고 Sub Class를 확장할 때 용이하다. 만약에 Designer Class를 추가하고자 한다면 DesignerFactory만 작성해주면 된다.
  ```java
  public abstract class Employee { ... }
  public class Developer extends Employee { ... }
  public class Tester extends Employee { ... }
  ```
  ```java
  public interface EmployeeAbstractFactory {
    public Employee createEmployee();
  }
  ```
  ```java
  public class DeveloperFactory implements EmployeeAbstractFactory {
    private String id;
    private String name;
    private String department;

    public DeveloperFactory(String id, String name, String department) {
      this.id = id;
      this.name = name;
      this.department = department;
    }

    @Override
    public Employee createEmployee() {
      return new Developer(id, name, department);
    }
  }
  ```
  ```java
  public class TesterFactory implements EmployeeAbstractFactory {
    private String id;
    private String name;
    private String department;

    public TesterFactory(String id, String name, String department) {
      this.id = id;
      this.name = name;
      this.department = department;
    }

    @Override
    public Employee createEmployee() {
      return new Terster(id, name, department);
    }
  }
  ```
  ```java
  public class EmployeeFactory {
    public static Employee getEmployee(EmployeeAbstractFactory factory) {
      return factory.createEmployee();
    }
  }
  ```
  ```java
  public class AbstractFactoryPattern {
    public static void main(String[] args) {
      Employee developer = EmployeeFactory.getEmployee(new DeveloperFactory("1","John","DEV"));
      Employee tester = EmployeeFactory.getEmployee(new TesterFactory("2","Dan","TEST"));
    }
  }
  ```
- ### Singleton Pattern
    - 프로그램 시작부터 종료까지 Singleton Class의 Instance는 메모리 상에 하나만 존재하고 어디에서나 접근할 수 있도록 하는 패턴이다. 어디에서나 접근 가능하도록 하는 방법으로 static을 떠올릴 수 있지만 Instance로 접근하는 방법을 자체적으로 관리하는 방법도 있다.
    - Singleton Class를 구현하는 방법은 여러가지가 있고 아래와 같은 공통적인 특징을 갖는다.
        - private 생성자를 정의해 외부로부터 Instance 생성을 차단한다.
        - Singleton을 구현하고자 하는 클래스 내부에 멤버 변수로 private static 객체 변수를 만든다.
        - public static 메소드를 통해 외부에서 Singleton에 접근할 수 있도록 접점을 제공한다.
    - Singleton Class 생성 방법
      ##### 1. `Eager Initialization`
        - Application에서 해당 Class를 사용하지 않아도 생성한다.
        - Exception에 대한 Handling도 제공하지 않는다.
          ```java
          public class SingletonClass {
            private static final SingletonClass instance = new SingletonClass();
            
            private SingletonClass(){ }
            
            public static SingletonClass getInstance() {
              return instance;
            }
          }
          ```
      ##### 2. `Static Block Initialization`
        - Application에서 해당 Class를 사용하지 않아도 생성한다.
        - Exception에 대한 Handling은 제공한다.
          ```java
          public class SingletonClass {
            private static SingletonClass instance;
            
            private SingletonClass(){}
  
            static {
              try {
                instance = new SingletonClass();
              }catch(Exception e){
                throw new RuntimeException("There is an exception in creating singleton class");
              }
            }
  
            public static SingletonClass getInstance() {
              return instance;
            }
          }
          ```
      ##### 3. `Lazy Initialization`
        - Application에서 해당 Class를 사용할 때 초기화 된다.
        - Multi-thread 환경에서 여러 thread가 동시에 getInstance()를 호출하면 동기화 문제가 발생할 수 있다. 따라서 이 방법은 single thread가 보장되는 환경에서 사용해야 한다.
          ```java
          public class SingletonClass {
            private static SingletonClass instance;
            
            private SingletonClass(){}
  
            public static SingletonClass getInstance() {
              if(instance == null) {
                instance = new SingletonClass();
              }
              return instance;
            }
          }
          ```
      ##### 4. `Thread Safe Singleton`
        - `Lazy Initialization` 방법의 Multi-thread 동기화 문제를 해결하기 위해서 getInstance() 함수에 synchronized를 사용한다.
        - synchronized는 임계 영역(Critical Section)을 형성해 해당 영역에 오직 하나의 Thread만 접근 가능하게 한다. 하지만 synchronized에 사용되는 비용이 크기 때문에 Application의 성능이 떨어질 수 있다.
        - 위 문제를 해결하기 위해 double checked locking을 사용하서 instance가 null일 때에만 synchronized을 실행하게 한다.
          ```java
          public class SingletonClass {
            private static SingletonClass instance;
  
            private SingletonClass(){}
  
            public static synchronized SingletonClass getInstance() {
              if(instance == null) {
                instance = new SingletonClass();
              }
              return instance;
            }
          }
          ```
          ```java
          public class SingletonClass {
            private static SingletonClass instance;
  
            private SingletonClass(){}
  
            /* double checked locking */
            public static SingletonClass getInstance() {
              if(instance == null) {
                synchronized (SingletonClass.class) {
                  if(instance == null) {
                    instance = new SingletonClass();
                  }
                }
              }
              return instance;
            }
          }
          ```
      ##### 5. `Bill Pugh Singleton Implementation`
        - inner static helper class를 사용하는 방식이다.
        - 위 방법들이 갖고 있는 문제를 거의 해결한 방식으로 현재 가장 보편적으로 사용되고 있다.
        - synchronized를 사용하지 않으며 getInstance()가 호출될 때 JVM에 Load 되고 instance를 생성한다.
          ```java
          public class SingletonClass {
            private SingletonClass(){}
  
            private static class SingletonClassHelper {
              private static final SingletonClass INSTANCE = new SingletonClass();
            }
  
            public static SingletonClass getInstance()  {
              return SingletonClassHelper.INSTANCE;
            }
          }
          ```
      ##### 6. `Enum Singleton`
        - 위 방법은 Java의 Reflection을 통해서 Singleton Class를 파괴할 수 있기 때문에 완벽하게 안전하지는 않다.
        - Application에서 해당 Class를 사용하지 않아도 생성한다.
          ```java
          public enum SingletonClass {
            INSTANCE;
  
            public static void doingMethod() {
              // doing
            }
          }
          ```
- ### Prototype Pattern
    - Instance를 만드는 절차를 추상화하는 패턴
    - Java의 clone() 메소드를 사용
    - DB로 부터 데이터를 가져와서 만든 하나의 객체를 사용해서 수정하거나 재사용할 경우, 그때마다 DB에서 데이터를 가져오지 않고 하나의 Prototype 객체를 복사해서 사용한다.
  ```java
  public class Employee implements Cloneable {
    private List<String> employees;
    
    public Employee() {
      this.employees = new ArrayList<>();
    }
    
    public Employee(List<String> list) {
      this.employees = list;
    }
    
    public void loadData() {
      employees.add("John");
      employees.add("Pika");
      employees.add("Pie");
      employees.add("Apple");
    }
    
    public List<String> getEmployees() {
      return this.employees;
    }

    public void addEmployee(String employee){
      this.employees.add(employee);
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
      List<String> temp = new ArrayList<>();
      for(String emp : this.employees){
        temp.add(emp);
      }
      return new Employee(temnp);
    }
  }
  ```
  ```java
  public class PtorotypeDesignPattern {
    public static void main(String[] args) throws ClloneNotSupportedException {
      Employee employee = new Employee();
      employee.loadData();

      Employee newEmployee1 = (Employee) employee.clone();
      Employee newEmployee2 = (Employee) employee.clone();
      
      newEmployee1.addEmployee("Teresa");
      newEmployee2.addEmployee("Dan");

      System.out.println(employee.getEmployees()); // [John, Pika, Pie, Apple]
      System.out.println(newEmployee1.getEmployees()); // [John, Pika, Pie, Apple, Teresa]
      System.out.println(newEmployee2.getEmployees()); // [John, Pika, Pie, Apple, Dan]
    }
  }
  ```
- ### Builder Pattern
    - Factory Pattern이나 Abstract Factory Pattern은 많은 속성 값을 갖고 있는 Class를 다룰 때 아래와 같은 이슈가 발생한다.
        - 많은 속성 값을 설정할 때 타입, 순서 등에 대한 관리가 어려워 에러가 발생할 확률이 높아진다.
        - 필요 없는 속성에 null을 설정해야 하는 경우가 있다.
        - 생성해야 하는 Sub Class가 무거워지고 복잡해지면서 Factory Class 또한 복잡해질 수 있다.
    - 내부에 Builder Class를 만들어 필수 속성값은 생성자를 통해 받고 선택적인 속성값은 함수를 통해 입력받은 후에 build() 함수를 통해 하나의 instance를 반환하는 방식이다.
    - Builder Class는 Static Nested Class로 생성하고 생성자는 public으로 필수 속성값에 대한 값을 Parameter로 받는다. 생성 대상이 되는 객체의 생성자는 private으로 Builder Class를 통해 속성값들을 설정한다.
    - 선택적인 속성 값 설정은 함수로 제공하고 각 함수의 반환값은 반드시 Builder Class 자신이어야 한다.
    - 생성할 Class는 getter 함수만 갖고 있으며 public 생성자는 존재하지 않아야 한다.
      ```java
      public class Employee {
        private int id;
        private String name;
        private String department;
  
        private int age;
        private String city;
        private boolean isLeader;
  
        public int getId() { return id; }
        public String getName() { return name; }
        public String getDepartment() { return department; }
  
        public int getAge() { return age; }
        public String getCity() { return city; }
        public boolean isLeader() { return isLeader; }
  
        private Employee(EmployeeBuilder builder) {
          this.id = builder.id;
          this.name = builder.name;
          this.department = builder.department;
          
          this.age = builder.age;
          this.city = builder.city;
          this.isLeader = builder.isLeader;
        }
  
        public static class EmployeeBuilder {
          private int id;
          private String name;
          private String department;
  
          private int age;
          private String city;
          private boolean isLeader; 
  
          public EmployeeBuilder(int id, String name, String department) {
            this.id = id;
            this.name = name;
            this.department = department;
          }
  
          public EmployeeBuilder age(int age){
            this.age = age;
            return this;
          }
  
          public EmployeeBuilder city(String city){
            this.city = city;
            return this;
          }
  
          public EmployeeBuilder isLeader(boolean isLeader){
            this.isLeader = isLeader;
            return this;
          }
  
          public Employee builder() {
            return new Employee(this);
          }
        }
      }
      ```
- ### Object Pool Pattern
    - Thread Pool 처럼 한정된 Resource서 자원을 재사용하는 방식을 적용할 때 사용하면 성능 개선에 좋다.
    - Connection을 관리하고 이를 재사용하고 공유할 수 있고 객체의 최대 수를 관리할 수 있다.
    - Class Instance의 빠른 초기화가 필요할 때 유용하다.
    - 사용 비용이 높은 객체가 자주 필요한 경우 유용하다.
    - 서로 다른 시간에 동일한 Resource를 필요로 하는 여러개의 Client가 있는 경우 유용하다.
      ```java
      public abstract class ObjectPool<T> {
        private ConcurrentLinkedQueue<T> pool;
        private ScheduledExecutorService executorService;
  
        protected abstract T createObject();
  
        private void initialize(final int minObjects) {
          pool = new ConcurrentLinkedQueue<T>();
          for(int i=0; i<minObjects; i++){
            pool.add(createObject());
          }
        }
  
        public ObjectPool(final int minObjects) {
          initialize(minObjects);
        }
  
        public ObjectPool(final int minObjects, final int maxObjects, final long validationInterval) {
          initialize(minObjects);
  
          executorService = Executors.newSingleThreadScheduledExecutor();
          executorService.scheduleWithFixedDelay(new Runnable() {
            @Override
            public void run() {
              int size = pool.size();
  
              if(size < minObjects) {
                int sizeToBeAdded = minObjects + size;
                for(int i=0; i<sizeToBeAdded; i++){
                  pool.add(createObject());
                }
              }else if(size > maxObjects) {
                int sizeToBeRemoved = size - maxObjects;
                for(int i=0; i<sizeToBeRemoved; i++){
                  pool.poll();
                }
              }
            }
          }, validationInterval, validationInterval, TimeUnit.SECONDS);
        }
  
        public T borrowObject() {
          T object;
          if((object == pool.poll()) == null) {
            object = createObject();
          }
          return object;
        }
  
        public void returnObject(T object) {
          if(object == null) {
            return;
          }
          this.poll.offer(object);
        }
  
        public void shutdown() {
          if(executorService != null) {
            executeService.shutdown();
          }
        }
      }
      ```
      ```java
      public class ExportingProcess {
        private long processNo;
  
        public ExportingProcess(long processNo) {
          this.processNo = processNo;
          System.out.println("Object with process no. " + processNo + " was created");
        }
  
        public long getProcessNo() {
          return processNo;
        }
      }
      ```
      ```java
      public class ExportingTask implements Runnable {
        private ObjectPool<ExportingProcess> pool;
        private int threadNo;
  
        public ExportingTask(ObjectPool<ExportingProcess> pool, int threadNo) {
          this.pool = pool;
          this.threadNo = threadNo;
        }
  
        public void run() {
          ExportingProcess exportingProcess = pool.borrowObject();
          System.out.printin("Thread " + threadNo + ": Object with process no. "
          + exportingProcess.getProcessNo() + " was borrowed");
  
          pool.returnObject(exportingProcess);
          System.out.printin("Thread " + threadNo + ": Object with process no. "
          + exportingProcess.getProcessNo() + " was returned");
        }
      }
      ```
      ```java
      public class ObjectPoolDemo {
        private ObjectPool<ExportingProcess> pool;
        private AtomicLong processNo = new AtomicLong(0);
  
        public void setUp() {
          pool = new ObjectPool<ExportingProcess>(4, 10, 5){
            @Override
            protected ExportingProcess createObject() {
              return new ExportingProcess(processNo.incrementAndGet());
            }
          }
        }
  
        public void tearDown() {
          pool.shutdown();
        }
  
        public void testObjectPool() {
          ExecutorService executor = Executors.newFixedTreadPool(8);
          executor.execute(new ExportingTask(pool, 1));
          executor.execute(new ExportingTask(pool, 2));
          executor.execute(new ExportingTask(pool, 3));
          executor.execute(new ExportingTask(pool, 4));
          executor.execute(new ExportingTask(pool, 5));
          executor.execute(new ExportingTask(pool, 6));
          executor.execute(new ExportingTask(pool, 7));
          executor.execute(new ExportingTask(pool, 8));
  
          executor.shoutdown();
          try {
            executor.awaitTermination(30, TimeUnit.SECONDS);
          }catch(InterruptedException e){
            e.printStackTrace();
          }
        }
  
        public static void main(String[] args) {
          ObjectPoolDemo opd = new ObjectPoolDemo();
          opd.setUp();
          opd.tearDown();
          opd.testObjectPool();
        }
      }
      ```

## 2) Structural Pattern
- ###### Structural Pattern은 Class들을 상속과 합성을 이용해서 더 큰 Class를 생성하는 방법을 제공하는 Pattern 이다.
- ###### Interface를 제공하거나 구현을 복잡하게 하는 것이 아니라 객체를 합성하는 방법을 제공한다.
- ###### Compile 단계가 아닌 Runtime 단계에서 복합 방법이나 대상을 변경할 수 있다는 유연성을 갖는다.
- ### Adapter Pattern
- Class의 Interface를 사용자가 기대하는 Interface 형태로 변환시키는 Pattern
- 서로 일치하지 않는 Interface를 갖는 Class들을 함께 동작시킨다.
- Class Adapter : 상속(Inheritance)을 이용한 방법
- Object Adapter : 합성(Composite)을 이용한 방법
  ```java
  public class Employee { // POJO Class
    private String department;

    public Employee(String department) {
      this.department = department;
    }

    public String getDepartment() {
      return department;
    }

    public void setDepartment(String name) {
      this.department = department;
    }

    public String toString() {
      return department;
    }
  }
  ```
  ```java
  public class Team {
    public List<Employee> createDevTeam(){
      return Arrays.asList(new Employee("DEV"));
    }
  }
  ```
  ```java
  public interface EmployeeAdapter {
    public List<Employee> createDevEmployees();
    public List<Employee> createTestEmployees();
    public List<Employee> createManagerEmployees();
  }
  ```
  ```java
  public class EmployeeClassAdapterImpl extends Team implements EmployeeAdapter {
    @Override
    public List<Employee> createDevEmployees(){
      return createDevTeam();
    }

    @Override
    public List<Employee> createTestEmployees(){
      List<Employee> emp = createDevTeam();
      return convertTeam(emp, "TEST");
    }

    @Override
    public List<Employee> createManagerEmployees(){
      List<Employee> emp = createDevTeam();
      return convertTeam(emp, "MANAGER");
    }

    private List<Employee> convertTeam(List<Employee> employees, String teamName) {
      employees.forEach(employee -> employee.setDepartment(teamName));
      return employees;
    }
  }
  ```
  ```java
  public class EmployeeObjectAdapterImpl implements EmployeeAdapter {
    private Team team = new Team();

    @Override
    public List<Employee> createDevEmployees(){
        return team.createDevTeam();
    }

    @Override
    public List<Employee> createTestEmployees(){
        List<Employee> emp = team.createDevTeam();
        return convertTeam(emp, "TEST");
    }

    @Override
    public List<Employee> createManagerEmployees(){
        List<Employee> emp = team.createDevTeam();
        return convertTeam(emp, "MANAGER");
    }

    private List<Employee> convertTeam(List<Employee> employees, String teamName) {
        employees.forEach(employee -> employee.setDepartment(teamName));
        return employees;
    }
  }
  ```
  ```java
  public class AdapterPattern {
    public static void main(String[] args) {
      EmployeeAdapter employeeClassAdapter = new EmployeeClassAdapterImpl();
      employeeClassAdapter.createDevEmployees().forEach(System.out::println);
      employeeClassAdapter.createTestEmployees().forEach(System.out::println);
      employeeClassAdapter.createManagerEmployees().forEach(System.out::println);

      EmployeeAdapter employeeObjectAdapter = new EmployeeClassAdapterImpl();
      employeeObjectAdapter.createDevEmployees().forEach(System.out::println);
      employeeObjectAdapter.createTestEmployees().forEach(System.out::println);
      employeeObjectAdapter.createManagerEmployees().forEach(System.out::println);
    }
  }
  ```
- ### Bridge Pattern
    - 추상화(abstraction)을 구현으로부터 분리하여 각각 독립적으로 변화할 수 있도록 하는 Pattern
    - 추상화와 구현을 독립적으로 다른 계층 구조를 가질 수 있도록 하고 외부로부터 구현을 숨기고 싶을 때 사용한다.
  ```java
  public abstract class Employee {
    protected Team team;

    public Employee(Team team) {
        this.team = team;
    }

    abstract public void applyTeam();
  }
  ```
  ```java
  public class EmployeeDev extends Employee {
    public EmployeeDev(Team team) {
        super(team);
    }

    @Override
    public void applyTeam() {
        System.out.print("This developer is in ");
        team.applyTeam();
    }
  }
  ```
  ```java
  public class EmployeeTester extends Employee {
    public EmployeeTester(Team team) {
        super(team);
    }

    @Override
    public void applyTeam() {
        System.out.print("This tester is in ");
        team.applyTeam();
    }
  }
  ```
  ```java
  public interface Team {
    public void applyTeam();
  }
  ```
  ```java
  public class TeamDev implements Team {
    @Override
    public void applyTeam() {
        System.out.println("Dev Team");
    }
  }
  ```
  ```java
  public class TeamTest implements Team {
    @Override
    public void applyTeam() {
        System.out.println("Test Team");
    }
  }
  ```
  ```java
  public class BridgePattern {
    public static void main(String[] args) {
        Employee devEmployee = new EmployeeDev(new TeamDev());
        devEmployee.applyTeam();

        Employee testerEmployee = new EmployeeTester(new TeamTest());
        testerEmployee.applyTeam();
    }
  }
  ```
- ### Composite Pattern
    - 어느 것도 구분하지 않고 한 가지 동작을 가능하도록 하는 것처럼 일괄적인 관리가 가능하도록 하는 Pattern
    - `Base Component` : Client가 Composition 내의 Objects를 다루기 위해 제공되는 Interface 로 모든 Objects에 공통되는 Method를 갖고 있다.
    - `Leaf` : Composition 내 Objects의 행동을 정의한다. 즉, Base Component를 구현한다.
    - `Composite` : Leaf Object로 이루어져 있으며 Base Component 내 명령들을 구현한다.
    - `Composite`도 `Base Component`를 구현하면서 다른 `Leaf`와 같이 사용할 수 있어야 한다.
  ```java
  /* Base Component */
  public interface Employee {
    public void doWork(String work);
  }
  ```  
  ```java
  /* Leaf Object */
  public class DevEmployee implements Employee {
    private String name;

    public DevEmployee(String name){
        this.name = name;
    }

    public String toString() {
        return name;
    }

    @Override
    public void doWork(String work) {
        System.out.println("DevEmployee " + name + " is doing " + work);
    }
  }
  ```
  ```java
  /* Leaf Object */
  public class TestEmployee implements Employee {
    private String name;

    public TestEmployee(String name){
        this.name = name;
    }

    public String toString() {
        return name;
    }

    @Override
    public void doWork(String work) {
        System.out.println("TestEmployee " + name + " is doing " + work);
    }
  }
  ```
  ```java
  /* Composite Object */
  public class Working implements Employee {
    private List<Employee> employees = new ArrayList<>();

    @Override
    public void doWork(String work) {
        for(Employee employee: employees) {
            employee.doWork(work);
        }
    }

    public void add(Employee employee) {
        System.out.println("Add Employee : " + employee);
        this.employees.add(employee);
    }

    public void remove(Employee employee) {
        System.out.println("Remove Employee : " + employee);
        this.employees.remove(employee);
    }

    public void clear() {
        System.out.println("Remove All of Employee");
        this.employees.clear();
    }
  }
  ```
  ```java
  public class CompositePattern {
    public static void main(String[] args) {
        Employee devEmployee = new DevEmployee("John");
        Employee testEmployee = new TestEmployee("Lin");

        // Leaf 객체들을 Grouping 후 사용
        Working working = new Working();
        working.add(devEmployee);
        working.add(testEmployee);
        working.doWork("Daily Scrum Meeting.");

        List<Employee> employees = new ArrayList<>();
        // Composite Object(Working Class)도 Base Component(Employee Class)를 구현하고 있기 때문에 다른 Leaf Class와 같이 사용할 수 있다.
        employees.add(working);
        employees.add(new DevEmployee("Beaver"));
        employees.add(new TestEmployee("Dan"));
        for(Employee employee: employees) {
            employee.doWork("Coffee Time");
        }
    }
  }
  ```
- ### Decorator Pattern
    - 상속과 합성을 사용해서 한 객체에 동적인 책임을 추가할 수 있게 한다.
    - Runtime 단계에서 여러 개의 Class의 특징을 모두 갖는 Class를 얻고 싶을 때 사용할 수 있다.
  ```java
  public interface Employee {
    public void assemble();
  }
  ```
  ```java
  public class NormalEmployee implements Employee {
    @Override
    public void assemble() {
        System.out.println("Normal Employee.");
    }
  }
  ```
  ```java
  public class EmployeeDecorator implements Employee {
    protected Employee employee;

    public  EmployeeDecorator(Employee employee) {
        this.employee = employee;
    }

    @Override
    public void assemble() {
        this.employee.assemble();
    }
  }
  ```
  ```java
  public class DevEmployee extends EmployeeDecorator {
    public DevEmployee(Employee employee){
        super(employee);
    }

    @Override
    public void assemble() {
        super.assemble();
        System.out.println("  Adding features of Dev Employee.");
    }
  }
  ```
  ```java
  public class TestEmployee extends EmployeeDecorator {
    public TestEmployee(Employee employee){
        super(employee);
    }

    @Override
    public void assemble() {
        super.assemble();
        System.out.println("  Adding features of Test Employee.");
    }
  }
  ```
  ```java
  public class DecoratorPattern {
    public static void main(String[] args) {
        Employee devEmployee = new DevEmployee(new NormalEmployee());
        devEmployee.assemble();
        System.out.println("********************");

        // 2개의 Class 특성을 갖는 Class 생성
        Employee multiEmployee = new DevEmployee(new TestEmployee(new NormalEmployee()));
        multiEmployee.assemble();
    }
  }
  ```
- ### Facade Pattern
- Sub System을 더 쉽게 사용할 수 있도록 Higher-level Interface를 정의하고 제공한다.
  ```java
  import java.sql.Connection;

  public class MySqlHelper {
    public static Connection getConnection() {
        return null;
    }

    public static void generatePdfReport(String tableName, Connection connection) {

    }

    public static void generateHtmlReport(String tableName, Connection connection) {

    }
  }
  ```
  ```java
  import java.sql.Connection;

  public class OracleHelper {
    public static Connection getConnection() {
        return null;
    }

    public static void generatePdfReport(String tableName, Connection connection) {

    }

    public static void generateHtmlReport(String tableName, Connection connection) {

    }
  }
  ```
  ```java
  import java.sql.Connection;

  public class FacadeHelper {
    public static enum DBTypes {
        MYSQL, ORACLE
    }

    public static enum ReportTypes {
        HTML, PDF
    }

    public static void generateReport(DBTypes dbTypes, ReportTypes reportTypes, String tableName) throws RuntimeException {
        Connection connection = null;
        switch (dbTypes) {
            case MYSQL:
                connection = MySqlHelper.getConnection();
                switch (reportTypes){
                    case HTML:
                        MySqlHelper.generateHtmlReport(tableName, connection);
                        break;
                    case PDF:
                        MySqlHelper.generatePdfReport(tableName, connection);
                        break;
                    default:
                        throw new RuntimeException("Report Type Error.");
                }
                break;
            case ORACLE:
                connection = OracleHelper.getConnection();
                switch (reportTypes){
                    case HTML:
                        OracleHelper.generateHtmlReport(tableName, connection);
                        break;
                    case PDF:
                        OracleHelper.generatePdfReport(tableName, connection);
                        break;
                    default:
                        throw new RuntimeException("Report Type Error.");
                }
                break;
            default:
                throw new RuntimeException("Database Type Error.");
        }
    }
  }
  ```
  ```java
  public class FacadePattern {
    public static void main(String[] args) {
        final String tableName = "Employee";

        FacadeHelper.generateReport(FacadeHelper.DBTypes.MYSQL, FacadeHelper.ReportTypes.HTML, tableName);
        FacadeHelper.generateReport(FacadeHelper.DBTypes.MYSQL, FacadeHelper.ReportTypes.PDF, tableName);

        FacadeHelper.generateReport(FacadeHelper.DBTypes.ORACLE, FacadeHelper.ReportTypes.HTML, tableName);
        FacadeHelper.generateReport(FacadeHelper.DBTypes.ORACLE, FacadeHelper.ReportTypes.PDF, tableName);
    }
  }
  ```
- ### Flyweight Pattern
    - 공유를 통해서 대량의 객체들을 효과적으로 지원하는 방법
    - 대량의 객체를 생성해야할 때 사용되며 메모리를 효율적으로 관리할 수 있다.
    - Flyweight Pattern이 유리한 상황
        - Application에 의해 생성되는 객체의 수가 많을 때
        - 생성된 객체가 오래되면 메모리에 상주하고 사용되는 횟수가 많을 때
        - 객체의 특성을 내적 속성(Intrinsic Properties)과 외적 속성(Extrinsic Properties)로 나눴을 때 외적 특성이 Client로부터 정의될 때
          ```
          ❓ 내적 속성 (Intrinsic Properties)
             객체를 유니크하게 하는 것
    
          ❓ 외적 속성 (Extrinsic Properties)
             Client로부터 설정되어 다른 동작을 수행하도록 하는 특성
          ```
    - 예제코드에서 Oval Class는 fill 이라는 내적 속성을 갖는다.
    - 예제코드에서 객체가 생성될 때 2초의 delay를 줘서 Flyweight Pattern의 효과를 확인할 수 있다.
    - Factory Class는 Client가 객체의 Instance를 생성할 때 사용하고 객체들은 Factory Class 내부에서 Map으로 관리됩니다. Client가 객체에 대한 Instance를 얻기 위해 호출할 때 기존에 객체가 있으면 Map에서 반환하고 없다면 새로운 객체를 생성하고 Map에 넣은 후 반환한다.
  ```java
  import java.awt.Color;
  import java.awt.Graphics;

  public interface Shape {
    public void draw(Graphics graphics, int x, int y, int width, int height, Color color);
  }
  ```
  ```java
  import java.awt.Color;
  import java.awt.Graphics;

  public class Line implements Shape {
    public Line() {
        System.out.println("Create Line");
        try{
            Thread.sleep(2000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(Graphics graphics, int x, int y, int width, int height, Color color) {
        graphics.setColor(color);
        graphics.drawLine(x, y, width, height);
    }
  }
  ```
  ```java
  import java.awt.Color;
  import java.awt.Graphics;

  public class Oval implements Shape {
    // 내적 속성 (intrinsic Property)
    private boolean fill;

    public Oval(boolean fill) {
        this.fill = fill;
        System.out.println("Create Oval with fill : " + fill);
        try {
            Thread.sleep(2000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    @Override
    public void draw(Graphics graphics, int x, int y, int width, int height, Color color) {
        graphics.setColor(color);
        graphics.drawOval(x, y, width, height);
        if(fill){
            graphics.fillOval(x, y, width, height);
        }
    }
  }
  ```
  ```java
  public class FlyweightFactory {
    public enum ShapeTypes {
        OVAL, OVAL_FILL, LINE;
    }
    public static final HashMap<ShapeTypes, Shape> shapes = new HashMap<>();

    public static Shape getShape(ShapeTypes shapeTypes) {
        Shape shape = shapes.get(shapeTypes);

        if(shape == null) {
            switch (shapeTypes){
                case LINE:
                    shape = new Line();
                    break;
                case OVAL:
                    shape = new Oval(false);
                    break;
                case OVAL_FILL:
                    shape = new Oval(true);
                    break;
                default:
                    throw new RuntimeException("Shape Types Error.");
            }
            shapes.put(shapeTypes, shape);
        }
        return shape;
    }
  }
  ```
  ```java
  import javax.swing.JButton;
  import javax.swing.JFrame;
  import javax.swing.JPanel;
  import java.awt.BorderLayout;
  import java.awt.Color;
  import java.awt.Container;
  import java.awt.Graphics;
  import java.awt.event.ActionEvent;
  import java.awt.event.ActionListener;

  public class FlyweightPattern extends JFrame {
    private final int width;
    private final int height;

    private static final FlyweightFactory.ShapeTypes shapes[] = { FlyweightFactory.ShapeTypes.LINE, FlyweightFactory.ShapeTypes.OVAL, FlyweightFactory.ShapeTypes.OVAL_FILL };
    private static final Color colors[] = { Color.RED, Color.GREEN, Color.YELLOW };

    public FlyweightPattern(int width, int height) {
        this.width = width;
        this.height = height;
        Container container = getContentPane();

        JButton jButton = new JButton("Draw");
        final JPanel jPanel = new JPanel();

        container.add(jPanel, BorderLayout.CENTER);
        container.add(jButton, BorderLayout.SOUTH);
        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Graphics graphics = jPanel.getGraphics();
                for(int i=0; i<20; i++){
                    Shape shape = FlyweightFactory.getShape(getRandomShape());
                    shape.draw(graphics, getRandomX(), getRandomY(), getRandomWidth(), getRandomHeight(), getRandomColor());
                }
            }
        });
    }

    private FlyweightFactory.ShapeTypes getRandomShape() {
        return shapes[(int) (Math.random() * shapes.length)];
    }

    private int getRandomX() {
        return (int) (Math.random() * width);
    }

    private int getRandomY() {
        return (int) (Math.random() * height);
    }

    private int getRandomWidth() {
        return (int) (Math.random() * (width / 10));
    }

    private int getRandomHeight() {
        return (int) (Math.random() * (height / 10));
    }

    private Color getRandomColor() {
        return colors[(int) (Math.random() * colors.length)];
    }

    public static void main(String[] args) {
        FlyweightPattern drawing = new FlyweightPattern(500,600);
    }
  }
  ```
- ### Proxy Pattern
    - 다른 객체로 접근하는 것을 통제하기 위해서 그 객체의 대리자(surrogate)나 자리표시자(placeholder)의 역할을 하는 객체를 제공하는 Pattern
    - Client에게 객체 자체를 제공하지 않고 앞단에서 특정 로직을 처리하는 Proxy 객체를 사용한다.
  ```java
  public interface CommandExecutor {
    public void runCommand(String command) throws Exception;
  }
  ```
  ```java
  public class CommandExecutorImpl implements CommandExecutor {
    @Override
    public void runCommand(String command) throws Exception {
        System.out.println("Execute Command : " + command);
        Runtime.getRuntime().exec(command);
    }
  }
  ```
  ```java
  public class CommandExecutorProxy implements CommandExecutor {
    private boolean isAdmin;
    private CommandExecutor commandExecutor;

    public CommandExecutorProxy(String user, String pwd) {
        if("Admin".equals(user) && "Password".equals(pwd)){
            isAdmin = true;
        }
        commandExecutor = new CommandExecutorImpl();
    }

    @Override
    public void runCommand(String command) throws Exception {
        if(isAdmin) {
            commandExecutor.runCommand(command);
        }else {
            if(command.trim().startsWith("rm")){
                throw new RuntimeException("This command cannot run if not admin.");
            }else{
                commandExecutor.runCommand(command);
            }
        }
    }
  }
  ```
  ```java
  public class ProxyPattern {
    public static void main(String[] args) {
        CommandExecutor commandExecutor = new CommandExecutorProxy("Admin", "WrongPassword");
        try{
            commandExecutor.runCommand("ls -ltr");
            commandExecutor.runCommand("rm -rf test.test");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
  }
  ```

## 3) Behavioral Pattern
- ###### 객체나 클래스 사이의 알고리즘이나 책임 분배에 관련된 Pattern
- ###### 한 객체가 혼자 수행할 수 없는 작업을 여러 개의 객체로 어떻게 분배할지, 객체 사이의 결합도를 어떻게 최소화할지에 중점을 둔다.
- ###### 구현 방법을 노출하지 않으면서 그 집합체 안에 들어있는 모든 항목에 접근하는 방법을 고려한다.
- ### Chain of Responsibility
    - 이벤트의 대한 처리를 특정 처리자에게 위임하고 체인에 속한 여러 처리자에 걸쳐 처리할 수 있다.
    - 서로 다른 클래스에 대해서 낮은 결합도로 동일한 이벤트에 대한 핸들링을 가능하게 할 수 있다.
    - 한 프로세스가 처리될 때 어떤 단계를 거칠지 정확하게 알 수 없기 때문에 프로세스가 끝나는 시간을 예측하기 어렵고 체인을 적절하게 구성하지 않으면 시간이 너무 오래 소요될 수 있다.
  ```java
  public abstract class EmployeeValidatorAbstract {
    private EmployeeValidatorAbstract nextValidator;

    private boolean hasNext() {
        return this.nextValidator != null;
    }

    public void setNextValidator(EmployeeValidatorAbstract nextValidator) {
        this.nextValidator = nextValidator;
    }

    public abstract void validate(List<Map<String, String>> dataset);

    public void executeValidation(List<Map<String, String>> dataset) {
        if(hasNext()){
            this.nextValidator.validate(dataset);
        }
    }
  }
  ```
  ```java
  public class DepartmentValidator extends EmployeeValidatorAbstract {
    @Override
    public void validate(List<Map<String, String>> dataset) {
        System.out.println("=========> dataset에 대한 부서 검증 프로세스를 시작합니다.");
        for(Map<String, String> data : dataset){
            System.out.println(data + "에 대한 부서 검증 결과는 " + EmployeeCode.DEPARTMENT.contains(data.get("DEPARTMENT")) + " 입니다.");
        }
        this.executeValidation(dataset);
    }
  }
  ```
  ```java
  public class NameValidator extends EmployeeValidatorAbstract {
    @Override
    public void validate(List<Map<String, String>> dataset) {
        System.out.println("=========> dataset에 대한 이름 검증 프로세스를 시작합니다.");
        for(Map<String, String> data : dataset){
            System.out.println(data + "에 대한 이름 검증 결과는 " + EmployeeCode.NAME.contains(data.get("NAME")) + " 입니다.");
        }
        this.executeValidation(dataset);
    }
  }
  ```
  ```java
  public class ChainOfResponsibilityPattern {
    public static void main(String[] args) {
        List<Map<String, String>> dataset = new ArrayList<>();
        dataset.add(new HashMap<>(){{
            put("DEPARTMENT", "DEV");
            put("NAME", "John");
        }});
        dataset.add(new HashMap<>(){{
            put("DEPARTMENT", "DEV");
            put("NAME", "Melisa");
        }});
        dataset.add(new HashMap<>(){{
            put("DEPARTMENT", "TEST");
            put("NAME", "Tony");
        }});
        dataset.add(new HashMap<>(){{
            put("DEPARTMENT", "TEST");
            put("NAME", "Hong");
        }});
        dataset.add(new HashMap<>(){{
            put("DEPARTMENT", "MANAGEMENT");
            put("NAME", "Stranger");
        }});
        dataset.add(new HashMap<>(){{
            put("DEPARTMENT", "MANAGEMENT");
            put("NAME", "Chief");
        }});

        DepartmentValidator departmentValidator = new DepartmentValidator();
        NameValidator nameValidator = new NameValidator();
        departmentValidator.setNextValidator(nameValidator);
        departmentValidator.validate(dataset);
    }
  }
  ```
- ### Command Pattern
    - 실행할 기능을 캡슐화해서 여러 기능을 실행할 수 있는 재사용성이 높은 Class를 설계하는 Pattern
    - 이벤트가 발생했을 때 실행될 기능이 다양하면서 변경이 많이 필요한 경우 이벤트를 발생시키는 Class를 변경하지 않고 재사용할 수 있다.
    - 실행될 기능을 캡슐화함으로써 기능의 실행을 요구하는 호출자(Invoker) 클래스와 실제 기능을 실행하는 수신자(Receiver) 클래스 사이의 의존성을 제거한다. 따라서 실행될 기능의 변경에도 호출자 클래스를 수정 없이 그대로 사용 할 수 있도록 해준다.
  ```java
  public interface Command {
    public abstract void execute();
  }
  ```
  ```java
  public class Button {
    private Command command;
    public Button(Command command){
        this.command = command;
    }

    public void setCommand(Command command){
        this.command = command;
    }

    public void pressed() {
        command.execute();
    }
  }
  ```
  ```java
  public class Alarm {
    public void start() {
        System.out.println("Start Alarm");
    }
  }
  ```
  ```java
  public class AlarmStartCommand implements Command {
    private Alarm alarm;

    public AlarmStartCommand(Alarm alarm){
        this.alarm = alarm;
    }

    @Override
    public void execute() {
        alarm.start();
    }
  }
  ```
  ```java
  public class Lamp {
    public void turnOn() {
        System.out.println("Lamp On");
    }
  }
  ```
  ```java
  public class LampOnCommand implements Command {
    private Lamp lamp;
    
    public LampOnCommand(Lamp lamp) {
        this.lamp = lamp;
    }

    @Override
    public void execute() {
        lamp.turnOn();
    }
  }
  ```
  ```java
  public class CommandPattern {
    public static void main(String[] args) {
        Command lampOnCommand = new LampOnCommand(new Lamp());
        Command alarmStartCommand = new AlarmStartCommand(new Alarm());

        Button button = new Button(lampOnCommand);
        button.pressed();
        button.pressed();

        button.setCommand(alarmStartCommand);
        button.pressed();
        button.pressed();

        button.setCommand(lampOnCommand);
        button.pressed();
    }
  }
  ```
- ### Interpreter Pattern
    - `AbstractExpression` : RegularExpression, 추상 구문 트리에 속한 모든 노드에 해당하는 클래스들이 공통으로 가져야 할 Interprete() 연상을 추상 연산으로 정의해야 한다.
    - `TerminalExpression` : LiteralExpression, 문법에 정의한 터미널 기호와 관련된 해석 방법을 구현합니다. 문장을 구성하는 모든 터미널 기호에 대해서 해당 클래스를 만들어야 한다.
    - `NonterminalExpression` : AlternationExpression, RepetitionExpression, SequenceExpressions, 문법의 오른편에 나타나는 모든 기호에 대해서 클래스를 정의해야 한다.
    - `Context` : 번역기에 대한 포괄적인 정보를 포함합니다.
    - `Client` : 언어로 정의한 특정 문장을 나타내는 추상 구문 트리입니다. 이 추상 구문 트리는 NonterminalExpression과 TerminalExpression 클래스의 인스턴스로 구성됩니다. 이 인스턴스의 Interprete() 연산을 호출한다.
  ```java
  /* AbstractExpression */
  public interface Logic {
    boolean evaluate();

    /* Context */
    public static class Values {
        static Map<String, Boolean> vars = new HashMap<>();
        static void assign(String key, boolean value) {
            if(key == null || key.length() <= 0){
                throw new RuntimeException("Assign falied");
            }
            vars.put(key, value ? Boolean.TRUE : Boolean.FALSE);
        }

        static boolean lookup(String key) {
            Object got = vars.get(key);
            return (Boolean) got;
        }
    }
  }
  ```
  ```java
  /* NonterminalExpression */
  public class AND implements Logic {
    Logic left, right;

    public AND(Logic left, Logic right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean evaluate() {
        return left.evaluate() && right.evaluate();
    }
  }
  ```
  ```java
  /* NonterminalExpression */
  public class OR implements Logic {
    Logic left, right;

    public OR(Logic left, Logic right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public boolean evaluate() {
        return left.evaluate() || right.evaluate();
    }
  }
  ```
  ```java
  /* NonterminalExpression */
  public class NOT implements Logic {
    Logic value;

    public NOT(Logic value) {
        this.value = value;
    }

    @Override
    public boolean evaluate() {
        return !value.evaluate();
    }
  }
  ```
  ```java
  /* TerminalExpression */
  public class Variable implements Logic {
    private String name;

    public Variable(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public boolean evaluate() {
        return Logic.Values.lookup(name);
    }
  }
  ```
  ```java
  public class InterpreterPattern {
    public static void main(String[] args) {
        Logic.Values.assign("A", true);
        Logic.Values.assign("B", false);

        Logic and = new AND(new Variable("A"), new Variable("B"));
        System.out.println(and.evaluate());

        Logic or = new OR(new Variable("A"), new Variable("B"));
        System.out.println(or.evaluate());

        Logic not = new AND(new Variable("A"), new NOT(new Variable("B")));
        System.out.println(not.evaluate());
    }
  }
  ```
- ### Iterator Pattern
    - 집합체 내에서 어떤 식으로 일이 처리되는지 몰라도 그 안에 들어있는 항목들에 대해서 반복작업을 수행할 수 있다.
    - `Iterator` : 집합체의 요소들을 순서대로 검색하기 위한 Interface
    - `ConcreateIterator` : Iterator 를 구현하는 Class
    - `Aggregate` : 여러 요소들로 이루어져 있는 Interface
    - `ConcreateAggregate` : Aggregate를 구현하는 Class
  ```java
  /* Aggregate */
  public interface Aggregate {
    Iterator getEmployeeIterator();
  }
  ```
  ```java
  public class Employee {
    private String teamName;
    private String name;

    public Employee(String teamName, String name){
        this.teamName = teamName;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String toString() {
        return teamName + "팀 소속 " + name;
    }
  }
  ```
  ```java
  /* ConcreateAggregate */
  public class Team implements Aggregate {
    private List<Employee> employeeList = new ArrayList<>();
    private String teamName;
    private int maxCount;

    public Team(String teamName, int maxCount){
        this.teamName = teamName;
        this.maxCount = maxCount;
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public void addEmployee(Employee employee) {
        if(employeeList.size() >= maxCount){
            System.out.println("팀원을 더이상 추가할 수 없습니다.");
        }else {
            System.out.println(teamName + "팀에 " + employee.getName() + "을 추가했습니다.");
            employeeList.add(employee);
        }
    }

    public void removeEmployee(Employee employee) {
        employeeList.remove(employee);
    }

    @Override
    public Iterator getEmployeeIterator() {
        return new TeamIterator(this);
    }
  }
  ```
  ```java
  /* ConcreateIterator */
  public class TeamIterator implements Iterator<Employee> {
    private Team team;
    private int index = 0;

    public TeamIterator(Team team){
        this.team = team;
    }

    @Override
    public boolean hasNext() {
        return index < team.getEmployeeList().size();
    }

    @Override
    public Employee next() {
        return team.getEmployeeList().get(index++);
    }
  }
  ```
  ```java
  public class IteratorPattern {
    public static void main(String[] args) {
        Team devTeam = new Team("DEV", 10);
        Team testTeam = new Team( "TEST", 3);

        Employee john = new Employee("DEV", "John");
        devTeam.addEmployee(john);
        devTeam.addEmployee(new Employee("DEV", "Anh"));
        devTeam.addEmployee(new Employee("DEV", "Dan"));
        devTeam.addEmployee(new Employee("DEV", "Hung"));
        devTeam.addEmployee(new Employee("DEV", "Tony"));

        testTeam.addEmployee(new Employee("TEST", "Anna"));
        testTeam.addEmployee(new Employee("TEST", "Drave"));
        testTeam.addEmployee(new Employee("TEST", "Polly"));
        testTeam.addEmployee(new Employee("TEST", "Zool"));
        testTeam.addEmployee(new Employee("TETS", "Chris"));

        Iterator iterator = devTeam.getEmployeeIterator();
        while(iterator.hasNext()){
            System.out.println(iterator.next());
        }

        iterator = testTeam.getEmployeeIterator();
        while(iterator.hasNext()){
            System.out.println(iterator.next());
        }

        devTeam.removeEmployee(john);
        iterator = devTeam.getEmployeeIterator();
        while(iterator.hasNext()){
            System.out.println(iterator.next());
        }
    }
  }
  ```
- ### Mediator Pattern
    - 객체들 시이에 너무 많은 관계가 얽혀 있어서 상호작용이 복잡할 때 객체들을 캡슐화함으로써 낮은 결합도를 만들어준다.
    - M개의 클래스 사이에 N개의 관계가 형성되어 있을 때 M:1 관계로 바꿔주기 위해 이 패턴을 사용한다.
    - M개의 클래스 사이에 관계를 제어하는 Mediator Class를 넣어서 관계를 캡슐화하고 관리하도록 한다.
    - `Mediator` : 객체 간의 상호참조를 위한 인터페이스를 제공하고 하위 객체(`Colleague`)들을 등록, 제거하는 메소드를 포함한다.
    - `ConcreteMediator` : `Mediator`를 구현하는 Class. 하위 객체(`Colleague`)들의 상호참조를 관리한다.
    - `Colleague` : 다른 `Colleague`와 상호참조를 위한 인터페이스
    - `ConcreteColleage` : `Colleague`를 구현하는 Class. `Mediator`을 통해 다른 `Colleague`와 상호참조한다.
  ```java
  /* Mediator */
  public interface ChatServer {
    void addUser(ChatClient chatClient);
    void deleteUser(ChatClient chatClient);
    void sendMessage(ChatClient chatClient, String message);
  }
  ```
  ```java
  /* Concrete Mediator */
  public class ChatServerImpl implements ChatServer {
    private final List<ChatClient> chatClients;

    public ChatServerImpl() {
        this.chatClients = new ArrayList<>();
    }

    @Override
    public void addUser(ChatClient chatClient) {
        System.out.println("[SERVER] " + chatClient.name + " joined this chat.");
        this.chatClients.add(chatClient);
    }

    @Override
    public void deleteUser(ChatClient chatClient) {
        System.out.println("[SERVER] " + chatClient.name + " has left this chat.");
        this.chatClients.remove(chatClient);
    }

    @Override
    public void sendMessage(ChatClient chatClient, String message) {
        for(ChatClient client: this.chatClients) {
            if(client != chatClient){
                client.receiveMessage(message);
            }
        }
    }
  }
  ```
  ```java
  /* Colleague */
  public abstract class ChatClient {
    protected ChatServer chatServer;
    protected String name;

    public ChatClient(ChatServer chatServer, String name) {
        this.chatServer = chatServer;
        this.name = name;
    }

    public abstract void sendMessage(String message);
    public abstract void receiveMessage(String message);
  }
  ```
  ```java
  /* Concrete Colleague */
  public class ChatClientImpl extends ChatClient {
    public ChatClientImpl(ChatServer chatServer, String name) {
        super(chatServer, name);
    }

    @Override
    public void sendMessage(String message) {
        System.out.println(this.name + " sends message : " + message);
        this.chatServer.sendMessage(this, message);
    }

    @Override
    public void receiveMessage(String message) {
        System.out.println("==> " + this.name + " receives message : " + message);
    }
  }
  ```
  ```java
  public class MediatorPattern {
    public static void main(String[] agrs) {
        ChatServer chatServer = new ChatServerImpl();
        ChatClient john = new ChatClientImpl(chatServer, "John");
        ChatClient cool = new ChatClientImpl(chatServer, "Cool");
        ChatClient dan = new ChatClientImpl(chatServer, "Dan");
        ChatClient tony = new ChatClientImpl(chatServer, "Tony");

        chatServer.addUser(john);
        chatServer.addUser(cool);
        chatServer.addUser(dan);
        chatServer.addUser(tony);
        System.out.println();

        john.sendMessage("Hello, guys.");
        System.out.println();

        cool.sendMessage("Nice to meet you guys.");
        System.out.println();

        chatServer.deleteUser(john);
        System.out.println();

        dan.sendMessage("Oh, John has left this chat.");
        System.out.println();
    }
  }
  ```
- ### Memento Pattern
    - 객체의 정보를 저장하고 사용자가 원하는 시점의 데이터를 복원할 수 있도록 하는 Pattern
    - `Memento` ; 객체의 상태를 갖고 있는 Class
    - `Originator` : 현재 상태를 저장하고 Memento 객체의 정보를 얻는 Class
    - `Caretaker` : `Memento` 클래스를 순서대로 저장하는 Class
  ```java
  public class Memento {
    private final String state;

    public Memento(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public String toString() {
        return state;
    }
  }
  ```
  ```java
  public class Originator {
    private String state;

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return state;
    }

    public Memento saveStateToMemento() {
        return new Memento(state);
    }

    public void getStateFromMemento(Memento memento) {
        state = memento.getState();
    }
  }
  ```
  ```java
  public class CareTaker {
    private final List<Memento> mementos = new ArrayList<>();

    public void addMemento(Memento memento) {
        this.mementos.add(memento);
    }

    public Memento getMemento(int index) {
        return mementos.get(index);
    }

    public int getMementoSize() {
        return mementos.size();
    }
  }
  ```
  ```java
  public class MementoPattern {
    public static void main(String[] args) {
        CareTaker careTaker = new CareTaker();
        Originator originator = new Originator();

        originator.setState("State #1");
        originator.setState("State #2");
        careTaker.addMemento(originator.saveStateToMemento());
        originator.setState("State #3");
        careTaker.addMemento(originator.saveStateToMemento());

        originator.setState("State #4");
        originator.setState("State #5");
        careTaker.addMemento(originator.saveStateToMemento());
        originator.setState("State #6");
        originator.setState("State #7");


        System.out.println("Current State : " + originator.getState());
        System.out.println("State Saved Count : " + careTaker.getMementoSize());
        System.out.println("State Saved First : " + careTaker.getMemento(0));
        System.out.println("State Saved Last : " + careTaker.getMemento(careTaker.getMementoSize()-1));
    }
  }
  ```
- ### Observer Pattern
    - 한 객체의 상태가 변경이 되면 해당 객체를 의존하고 있는 모든 객체에 상태 변경을 전파하는 Pattern
    - `Subject` : 상태가 변경되었는지 관찰하는 대상
    - `Observer` : `Subject`를 관찰하는 객체로 `Subject`에 의존성을 갖는다.
    - `Subject` : `Observer` = 1 : N 구조이다.
  ```java
  public class Post {
    private String title;

    public Post(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
  }
  ```
  ```java
  public interface Observer {
    void update(Post post);
  }
  ```
  ```java
  public class Subscriber implements Observer {
    public void update(Post post){
        System.out.println("notice : " + post.getTitle() + " is updated.");
    }
  }
  ```
  ```java
  public interface Subject {
    void addSubscriber(Subscriber subscriber);

    void removeSubscriber(Subscriber subscriber);

    void notifyObserver();
  }
  ```
  ```java
  public class Youtube implements Subject {
    private List<Subscriber> subscribers = new ArrayList<>();
    private Post post;

    @Override
    public void addSubscriber(Subscriber subscriber) {
        this.subscribers.add(subscriber);
    }

    @Override
    public void removeSubscriber(Subscriber subscriber) {
        this.subscribers.remove(subscriber);
    }

    @Override
    public void notifyObserver() {
        for(Subscriber subscriber: subscribers) {
            subscriber.update(post);
        }
    }

    public void uploadPost(Post post) {
        this.post = post;
    }
  }
  ```
  ```java
  public class ObserverPattern {
    public static void main(String[] args) {
        Subscriber subscriber1 = new Subscriber();
        Subscriber subscriber2 = new Subscriber();
        Subscriber subscriber3 = new Subscriber();

        Youtube youtube = new Youtube();
        youtube.addSubscriber(subscriber1);
        youtube.addSubscriber(subscriber2);
        youtube.addSubscriber(subscriber3);

        youtube.uploadPost(new Post("올해 바뀐 제도들에 대한 영상"));
        youtube.notifyObserver();
    }
  }
  ```
- ### State Pattern
    - 어떤 행동을 수행할 때 상태에 맞는 행동을 수행하도록 정의하고 싶을 때 사용하는 Pattern
    - 캡슐화를 위해 Interface를 생성하서 한 상태 클래스가 동작하는 범위를 지정하고 각 상태 클래스에서 동작을 구현한다.
    - 객체가 직접 상태를 체크하여 상태에 따라 행위를 호출하는게 아니라 상태를 객체화하여 상태가 행동을 할 수 있도록 위임하는 디자인 패턴이다.
  ```java
  public interface State {
    void insertCoin(MachineContext machineContext);
    void returnCoin(MachineContext machineContext);
    int getCoinCount();
    String getState();
  }
  ```
  ```java
  public class MachineContext {
    State state;

    public MachineContext() {
        state = new NoCoinState();
    }

    public void setState(State state) {
        this.state = state;
    }

    public String getState() {
        return state.getState();
    }

    public void insertCoin() {
        this.state.insertCoin(this);
    }

    public void returnCoin() {
        this.state.returnCoin(this);
    }
  }
  ```
  ```java
  public class CoinState implements State {
    private int coinCount = 1;

    @Override
    public void insertCoin(MachineContext machineContext) {
        coinCount++;
        machineContext.setState(this);
        System.out.println("코인이 삽입되었습니다. [코인 : " + coinCount + "]");
    }

    @Override
    public void returnCoin(MachineContext machineContext) {
        if(--coinCount == 0){
            machineContext.setState(new NoCoinState());
        }
        System.out.println("코인이 반환되었습니다. [남은 코인 : " + coinCount + "]");
    }

    @Override
    public String getState() {
        return "코인이 있는 상태입니다. [남은 코인 : " + coinCount + "]";
    }

    @Override
    public int getCoinCount() {
        return coinCount;
    }
  }
  ```
  ```java
  public class NoCoinState implements State {
    @Override
    public void insertCoin(MachineContext machineContext) {
        machineContext.setState(new CoinState());
        System.out.println("코인이 삽입되었습니다. [코인 : 1]");
    }

    @Override
    public void returnCoin(MachineContext machineContext) {
        System.out.println("코인이 없습니다.");
    }

    @Override
    public int getCoinCount() {
        return 0;
    }

    @Override
    public String getState() {
        return "코인이 없는 상태입니다.";
    }
  }
  ```
  ```java
  public class StatePattern {
    public static void main(String[] args) {
        MachineContext machineContext = new MachineContext();
        System.out.println(machineContext.getState());
        System.out.println("==============================");

        machineContext.insertCoin();
        machineContext.insertCoin();
        machineContext.insertCoin();
        machineContext.insertCoin();
        System.out.println(machineContext.getState());
        System.out.println("==============================");

        machineContext.returnCoin();
        machineContext.returnCoin();
        machineContext.returnCoin();
        machineContext.returnCoin();
        machineContext.returnCoin();
        machineContext.returnCoin();
        System.out.println(machineContext.getState());
        System.out.println("==============================");

        machineContext.insertCoin();
        System.out.println(machineContext.getState());
        System.out.println("==============================");
    }
  }
  ```
  ```java
  public interface PowerState {
    public void powerPush();
  }
  
  public class On implements PowerState {
    public void powerPush() {
      // 전원 Off
      System.out.println("전원 off");
    }
  }
  
  public class On implements PowerState {
    public void powerPush() {
      // 전원 On
      System.out.println("전원 on");
    }
  }
  
  public class Saving implements PowerState {
    public void powerPush() {
      // 절전 모드
      System.out.println("전원 절전 모드");
    }
  }
  
  /***************************************************/
  
  public class Laptop {
    private PowerState powerState;
  
    public Laptop() { this.powerState = new Off(); }
  
    public void setPowerState(PowerState powerState) { this.powerState = powerState; }
    public void powerPush() { powerState.powerPush(); }
  }
  
  /***************************************************/
  
  public class Client {
    public static void main(String[] agrs) {
      Laptop laptop = new Laptop();
      On on = new On();
      Off off = new Off();
      Saving saving = new Saving();
  
      laptop.powerPush();
      laptop.setPowerState(on);
      laptop.powerPush();
      laptop.setPowerState(saving);
      laptop.powerPush();
      laptop.setPowerState(off);
      laptop.powerPush();
      laptop.setPowerState(on);
    }
  }
  ```

- ### Strategy Pattern
    - 각 알고리즘(동작)이 교환 가능하도록 캡슐화해서 사용한다. 즉, 각 객체가 할 수 있는 동작들을 각각의 전략으로 만들어놓고 상황에 따라 전략만 바꿔서 사용한다.
    - `Strategy` : 전략을 사용하기 위한 Interface
    - `ConcreteStrategy` : 각 전략을 구현하는 Class
    - `Context` : Strategy Class를 호출해서 사용하는 Class

  ```java
  public interface SoundStrategy {
    void crying();
  }
  ```
  ```java
  public class CryStrategy implements SoundStrategy {
    @Override
    public void crying() {
        System.out.println("오리는 꽥꽥~!");
    }
  }
  ```
  ```java
  public class NoCryStrategy implements SoundStrategy {
    @Override
    public void crying() {
        System.out.println("울지 않음");
    }
  }
  ```
  ```java
  public class CryContext {
    private SoundStrategy soundStrategy;

    public void crying() {
        soundStrategy.crying();
    }

    public void setSoundStrategy(SoundStrategy soundStrategy) {
        this.soundStrategy = soundStrategy;
    }
  }
  ```
  ```java
  public class Person extends CryContext {
    public Person() {
        System.out.println("나는 사람이야~!");
    }
  }
  ```
  ```java
  public class Duck extends CryContext {
    public Duck() {
        System.out.println("나는 오리야~!");
    }
  }
  ```
  ```java
  public class StrategyPattern {
    public static void main(String[] args) {
        CryContext person = new Person();
        CryContext duck = new Duck();

        person.setSoundStrategy(new NoCryStrategy());
        duck.setSoundStrategy(new CryStrategy());

        person.crying();;
        duck.crying();

        person.setSoundStrategy(new CryStrategy());
        person.crying();
        duck.crying();
    }
  }
  ```

- ### Template Pattern
    - 상속을 통해 슈퍼클래스의 기능을 확장할 때 사용하는 가장 대표적인 방법으로 변하지 않는 기능은 슈퍼클래스에 만들어두고 자주 변경되며 확장할 기능은 서브클래스에서 만들도록 한다.
    - 알고리즘이 단계별로 나누어 지거나, 같은 역할을 하는 메소드이지만 여러곳에서 다른형태로 사용이 필요한 경우 유용한 패턴이다.
    - 비슷한 동작을 하는 클래스가 많을 때 상위 클래스(abstract class)에 공통 동작을 정의하고 하위 클래스에서 달라지는 동작들은 각 하위 클래스에서 정의할 수 있도록 abstract 함수로 작성한다.

  ```java
  public abstract class Pasta {
    protected void boilWater() {
        System.out.println("물을 끓인다.");
    }

    protected void putNoodle() {
        System.out.println("면을 넣는다.");
    }

    protected void pickUpNoodle() {
        System.out.println("면을 건진다.");
    }

    protected void coolNoodle() {
        System.out.println("면을 식힌다.");
    }

    protected void mixSource() {
        System.out.println("소스를 섞는다.");
    }

    protected void enjoyPasta() {
        System.out.println("파스타를 먹는다.");
    }

    protected abstract void doExtra();

    protected abstract void waitHotNoodle();

    public abstract void cookPasta();
  }
  ```
  ```java
  public class CreamPasta extends Pasta {
    @Override
    protected void doExtra() {
        System.out.println("다른 냄비에서 크림 소스를 데우고 있는다.");
    }

    @Override
    protected void waitHotNoodle() {
        System.out.println("15분 기다린다.");
    }

    @Override
    public void cookPasta() {
        System.out.println("크림 파스타를 만듭니다.");
        boilWater();
        putNoodle();
        waitHotNoodle();
        doExtra();
        pickUpNoodle();
        mixSource();
        enjoyPasta();
    }
  }
  ```
  ```java
  public class TomatoPasta extends Pasta {
    @Override
    protected void doExtra() {
        System.out.println("다른 재료들을 넣는다.");
    }

    @Override
    protected void waitHotNoodle() {
        System.out.println("10분 기다린다.");
    }

    @Override
    public void cookPasta() {
        System.out.println("토마토 파스타를 만듭니다.");
        boilWater();
        putNoodle();
        waitHotNoodle();
        doExtra();
        pickUpNoodle();
        coolNoodle();
        mixSource();
        enjoyPasta();
    }
  }
  ```
  ```java
  public class TemplatePattern {
    public static void main(String[] args) {
        Pasta creamPasta = new CreamPasta();
        creamPasta.cookPasta();

        System.out.println("========================");

        Pasta tomatoPasta = new TomatoPasta();
        tomatoPasta.cookPasta();
    }
  }
  ```

### Reference
- https://www.javatpoint.com/design-patterns-in-java
- https://readystory.tistory.com/category/JAVA/Design%20Pattern
- https://brownbears.tistory.com/
### 🔰 예제코드 : https://github.com/justdoanything/self-study/tree/main/Java/src/main/java/book/pattern
