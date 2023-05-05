<!-- TOC -->
* [SQL 실행계획](#sql-실행계획)
* [SQL Indexing](#sql-indexing)
<!-- TOC -->


---

SQL 실행계획
===
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
        - SELECT 쿼리가 어떤 타입인지 나타낸다.
      
          | 타입                     | 설명                                                                                                                                                                                                                                                            |
          |------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
          | `SIMPLE`               | UNION 이나 Sub Query를 사용하지 않은 단순 SELECT. JOIN하고 있는 쿼리도 따로 보지 않고 하나의 `SIMPLE`로 본다.                                                                                                                                                                               |
          | `PRIMARY`              | UNION 이나 Sub Query가 포함된 SELECT에서 가장 바깥쪽에 있는 SELECT. PRIMARY인 쿼리는 반드시 하나만 존재한다.                                                                                                                                                                                |
          | `UNION`                | UNION을 사용한 SELECT에서 두번째 이후 쿼리는 모두 UNION 으로 표시된다. (첫번째 쿼리는 `DERIVED` 이다.)                                                                                                                                                                                      |
          | `UNION RESULT`         | UNION의 결과를 담는 임시 테이블을 의미한다. (Mysql 8.0 부터는 UNION ALL을 쓸 때 임시 테이블을 사용하지 않기로해서 보이지 않지만 UNION, UNION DISTINCT 쿼리는 임시 테이블에 결과를 담는다.)                                                                                                                              |
          | `SUBQUERY`             | FROM절 이외에 사용된 Sub Query에 표시된다. (FROM절에 사용된 Sub Query는 `DERIVED` 로 표시된다.)                                                                                                                                                                                      |
          | `DEPENDENT SUBQUERY`   | FROM절 이외에 사용된 Sub Query가 바깥쪽 SELECT에서 정의된 컬럼을 사용할 경우 해당 Sub Query에 표시된다.                                                                                                                                                                                      |
          | `DERIVED`              | FROM절에 사용된 Sub Query로 SELECT 결과로 Memory나 Disk에 임시 테이블을 만드는 경우에 표시된다.                                                                                                                                                                                          |
          | `DEPENDENT DERIVED`    | LATERAL JOIN을 사용했을 떄 표시된다. (Mysql 8.0 이전에는 FROM절의 Sub Query에 외부 컬럼을 사용할 수 없었지만 8.0 이후로는 가능하고 LATERAL JOIN으로 FROM절의 Sub Query가 외부 컬럼을 참좋라 수 있다.)                                                                                                               |
          | `UNCACHEABLE SUBQUERY` | `SUBQUERY`, `DEPENDENT SUBQUERY`는 Sub Query 결과를 Caching 할 수 있는데 툭정 조건 때문에 Caching 할 수 없을 때 표시된다. (사용자 변수가 Sub Query에 있거나 UUID(), RAND() 와 같은 결과값이 호출할 때마다 변경되는 함수가 Sub Query에 있을 때, NOT-DETERMINISTIC 속성의 Stored Function이 Sub Query에 들어간 경우 Caching 하지 못한다.) |
          | `DEPENDENT UNION`      | UNION을 사용한 SELECT에서 UNION으로 결합된 쿼리가 외부 쿼리에 영향을 받는 경우이다. 내부 쿼리가 외부 값을 참조하는 경우이다.                                                                                                                                                                               |
          | `MATERIALIZED`         | FROM절이나 IN 형태의 쿼리에 사용된 Sub Query를 최적화할 때 표시된다. 보통 Sub Query보다 외부 쿼리의 테이블을 먼저 읽어서 비효율적으로 실행되는데 Sub Query 내용을 임시 테이블로 구체화한 후 외부 테이블과 Join 하여 최적화한다. 이 때 Sub Query가 먼저 구체화 되었다는 것을 표시한다.                                                                         |
    - `table`
        - 실행 계획을 확인할 때 SELECT 쿼리가 아니고 테이블 기준으로 분류해서 나온다.
        - `<>` 로 감싸져 있으면 임시 테이블을 뜻한다.
    - `partitions`
        - Partitioning 으로 테이블이 관리되고 있을 때 어떤 Partitioning을 읽었는지 표시해준다.
    - `type`
        - ⭐️ index를 참조했는지 알려주는 중요한 컬럼이다.
        - ALL 을 빼면 index를 사용했다고 볼 수 있고 type 컬럼의 종류는 아래와 같다. (성능이 좋은 순으로 정렬되어 있다.)
      
          | 타입                | 설명                                                                                                                                                                                                                                                             |
          |----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|---|
          | `system`          | record가 0건 또는 1건만 존재하는 테이블을 접근할 때 표시된다.                                                                                                                                                                                                                        |
          | `const`           | Query에 Primary/Unique Key를 이용하는 WHERE 조건이 있으면거 결과가 반드시 1건을 반환하는 쿼리로 접근할 때 표시된다. (=UNIQUE INDEX SCAN) 단, DBMS가 결과가 1개인 것을 예측할 수 있어야 한다. QUERY 결과가 1건인 것이랑은 별개이다.                                                                                                |
          | `eq_ref`          | 여러 테이블이 JOIN 되는 QUERY 에서만 발생한다. JOIN에서 처음 읽은 테이블의 컬럼 값을 두번째 읽는 테이블의 Primary/Unique Index Column(NOT NULL)의 동등 조건에 사용될 때 (=반드시 1건만 존재한다는 보장이 있을 때) 사용되는 접근 방법                                                                                                   |
          | `ref`             | eq_ref 와 달리 JOIN 순서에 상관없이 사용되며 Primary/Unique Index 등의 제약도 상관없이 사용된다. Index 종류와 상관없이 동등 조건이 사용될 때 사용되는 접근 방법이다. (단, 레코드가 반드시 1건이라는 보장이 없으므로 eq_ref보다 느리지만 전체로 봤을 땐 아주 빠른 index 이다.)                                                                            |
          | `fulltext`        | 전문 검색 Index를 사용해서 Record에 접근하는 방법으로 검색할 컬럼에 Index가 있어야 한다. (MATCH ... AGAINST ... 구문을 사용해서 실행된다.)                                                                                                                                                              |
          | `ref_or_null`     | ref와 같은데 NULL 비교가 추가된 형태이다.                                                                                                                                                                                                                                    |
          | `unique_subqery`  | WHERE 조건에 IN 형태(Sub Query)를 갖을 때, Sub Query에서 중복되지 않는 Unique한 값만 반환될 때 사용된다.                                                                                                                                                                                   |
          | `index_subquery`  | IN 연산자 특성상 IN 괄호 조건에 나오는 목록에 중복값이 제거 되어야 한다. Unique 하지 않은 경우에 Index를 이용해서 중복을 제거한다.                                                                                                                                                                            |
          | `range`           | 인덱스를 하나의 값이 아니라 범위로 검색하는 경우에 사용된다. 주로 `<`, `>`, `IS NULL`, `BETWEEN`, `IN`, `LIKE` 등의 연산자로 Index를 검색하는 경우에 사용된다. 통상적으로 Index Scan 이라고 하면 `range`, `const`, `ref`를 묶어서 지칭한다.                                                                                    |
          | `index_merge`     | 2개 이상의 Index를 이용해서 각각의 검색 결과를 만든 후 결과를 합치는 접근 방식으로 실제 우선순위가 range 보다 높지만 효율적으로 동작히지 않는다.                                                                                                                                                                       |
          | `index`           | Index를 처음부터 끝까지 읽어야 하는 경우에 쓰이는 비효율적인 방식이다. 아래 2가지 조건에서 발생한다.<br/> - Index Scan(range, const, ref)이 불가능하고 Index에 포함된 컬럼만으로 처리할 수 있는 경우(데이터 파일을 읽지 않아도 되는 경우)<br/>- Index Scan(range, const, ref)이 불가능하고 Index를 이용해 정렬이나 Grouping 작업이 가능한 경우 (정렬 작업을 피할 수 있는 경우) |
          | `ALL`             | Full Table Scan으로 일반적인 조회 환경에서 가장 안좋은 방식이다. 잘못된 Index를 사용하는 것보다 이 방식이 효율적일 수 있다.                                                                                                                                                                               |
    - `possible_keys`
        - Optimizer가 Query를 처리하기 위해 여러 처리 방법을 고려하던 중에 사용된 후보 Index List
    - `key`
        - possible_keys 컬럼에서 보여진 후보 Index List 중 실제로 사용된 Index를 의미한다. Index를 사용하지 못한 경우 NULL로 표시된다.
    - `key_len`
        - Index가 다중 컬럼으로 만들어졌을 경우, Index 중에서 몇 byte까지 사용했는지 알려준다. 각 Index 컬럼에 할당된 byte를 알 수 있어서 몇 개의 Index 컬럼이 사용되었는지 추산할 수 있다.
        - dept_emp 테이블은 다중 컬럼(dept_no, emp_no)으로 만들어진 Primary가 있고 아래 Query로 테이블을 조회할 때 key_len = 4 가 나온다. dept_no가 INTEGER(byte=4) 이기 때문에 Index 중에서 앞에 dep_no만 쓰였다는걸 추론할 수 있다.
          ```sql
          SELECT *
          FROM dept_emp
          WHERE dept_no=3
          ```
    - `ref`
        - type 컬럼에서 접근 방법이 ref 이면 어떤 컬럼이 조건에 사용되었는지 보여준다.
        - 가공된 컬럼이 사용됐을 땐 `func` 라고 표시된다.
    - `row`
        - Optimizer가 비용을 산정하기 위해 얼마나 많은 레코드를 읽고 비교했는지 예측한 값으로 정확한 값은 아니고 통계에 의한 값이다.
    - `Extra`
        - MySQL이 Query를 어떻게 풀었는지 부가 정보를 표시해준다.
        - | 타입                                                                       | 설명                                                                                                                                                                                                                                                   |
          |------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|---|
          | `const row not found`                                                    | const 접근 방식으로 읽었으나 레코드가 0개인 것을 의미                                                                                                                                                                                                                    |
          | `distinct`                                                               | JOIN 할 때 중복된 값을 제거하고 필요한 필드만 JOIN 했음을 의미                                                                                                                                                                                                             |
          | `Full scan on NULL key`                                                  | WHERE 조건에 nullable 컬럼이 있는 경우 null 일 때 Full Scan을 하겠다는 의미                                                                                                                                                                                             |
          | `Impossible HAVING`<br/>(Query 수정 해야함)                                   | HAVING 조건에 만족하는 레코드가 없는 경우를 의미                                                                                                                                                                                                                       |
          | `Impossible WHERE`<br/>(Query 수정 해야함)                                    | WHERE 조건이 항상 False인 경우를 의미                                                                                                                                                                                                                           |
          | `Impossible WHERE noticed after reading const tables`<br/>(Query 수정 해야함) | Query를 실행해보니 WHERE 조건이 항상 False 라는 의미                                                                                                                                                                                                                |
          | `No matching min/max row`<br/>(Query 수정 해야함)                             | MIN(), MAX() 와 같은 집합 함수가 있는 Query의 WHERE 조건절에 일치하는 레코드가 하나도 없는 경우를 의미                                                                                                                                                                                |
          | `No matching row in const table`<br/>(Query 수정 해야함)                      | const 방식으로 접근할 때 일치하는 레코드가 없는 경우를 의미                                                                                                                                                                                                                 |
          | `No tables used`                                                         | FROM 절이 없거나 DUAL 테이블을 사용한 경우를 의미                                                                                                                                                                                                                     |
          | `Not exists`                                                             | A 테이블에는 존재하지만 B 테이블에 존재하지 않는 값을 조회할 때 `Anti Join`, `Left Outer Join`을 사용한다. <br/>- `Anti Join` : NOT INT, NOT EXISTS 연산자를 사용한 Query<br/>- `Left Outer Join` : 레코드가 많을 때 Anti Join보다 유리한 방식.                                                          | 
          | `Range checked for each record(index map: N)`                            | WHERE 조건에 변수가 2개 이상이 사용되고 그 값이 계속 바뀌는 경우, 레코드마다 인덱스를 탈지 Full Scan을 할지 결정해야 한다. ```SELECT * FROM employees e1, employees e2 WHERE e1.emp_no >= e2.emp_no```                                                                                           |
          | `Scanned N databases`                                                    | Database 메타 정보를 담고 있는 테이블을 조회하는 경우를 의미                                                                                                                                                                                                               |
          | `Select tables optimized away`                                           | SELECT에서 MIN(), MAX()만 사용하거나 GROUP BY로 MIN(), MAX()를 조회하는 쿼리가 적절한 인덱스를 사용해서 레코드 1개만 읽는 형태로 최적화됐을 경우 의미                                                                                                                                               |
          | `unique row not found`                                                   | 2개의 테이블이 Primary Key, Unique Column으로 Outer Join을 할 때 Outer Table에 일치하는 레코드가 없을 경우를 의미<br/>A 테이블에만 있는 레코드로 B 테이블에 조인하려고할 때 B 테이블과 조인하는 컬럼 값이 없는 경우도 생긴다.                                                                                             |
          | `Using filesort`<br/>(튜닝해야함)                                           | ORDER BY를 처리할 때 주로 인덱스를 이용하지만 적절한 인덱스를 사용하지 못한 경우 Mysql이 Sort Buffer에 레코드를 복사해서 정렬는 비효율적인 작업이라는 의미 (튜닝 대상이다.)<br/>`index`는 Full Scan이고 `Using index`는 Covering Index이다.                                                                              |
          | `Using index`                                                            | 데이터 파일을 읽지 않고 인덱스만 읽어서 처리할 수 있는 경우를 의미(=`Covering Index`)                                                                                                                                                                                            |
          | `Using index for group-by`                                               | GROUP BY 처리를 위해 인덱스를 이용하는 경우 즉 `Loose Index Scan`을 의미한다.                                                                                                                                                                                             |
          | `Using join buffer`                                                      | 일반적으로 Join에 사용되는 컬럼은 인덱스를 생성한다.<br/>- `Driving Table` : Join을 하기 위한 먼저 읽은 테이블<br/>- `Driven Table` : 나중에 읽는 테이블<br/>Driving Table에 JOIN 되 컬럼에 인덱스가 없으면 Join Buffer을 사용해서 Join을 하는데 이럴 경우 표시된다.<br/>조인 조건이 없는 `Cartesian Join`은 항상 Join Buffer를 사용한다. |
          | `Using intersect`                                                        | `type=index_merge`인 경우, 2개 이상의 인덱스를 사용될 수 있고 2개의 인덱스로부터 읽은 결과를 어떻게 합쳤는지 보여줄 때 사용된다.<br/>각 인덱스를 사용하는 조건이 `AND`로 연결된 경우 처리 결과에서 AND로 추출했다는 의미.                                                                                                         |
          | `Using union`                                                            | `type=index_merge`인 경우, 2개 이상의 인덱스를 사용될 수 있고 2개의 인덱스로부터 읽은 결과를 어떻게 합쳤는지 보여줄 때 사용된다.<br/>각 인덱스를 사용하는 조건이 `OR`로 연결된 경우 처리 결과에서 OR로 추출했다는 의미.                                                                                                           |
          | `Using sort_union`                                                       | `type=index_merge`인 경우, 2개 이상의 인덱스를 사용될 수 있고 2개의 인덱스로부터 읽은 결과를 어떻게 합쳤는지 보여줄 때 사용된다.<br/>`Using union`으로 처리될 수 없는 경우(=OR로 연결된 레코드가 대량의 range일 경우), Primary Key만 읽어서 먼저 정렬하고 병합한 후에 레코드를 읽어서 반환할 수 있다.                                                 |
          | `Using temporary`                                                        | Mysql은 쿼리를 처리하면서 중간 결과를 담아두기 위해서 임시 테이블을 사용하는데 이 임시 테이블을 사용했을 때 표시된다.<br/>임시 테이블이 생성되는 경우<br/>- FROM절에 Sub Query를 쓸 경우<br/>- `count(distinct name)`과 같이 Index를 사용할 수 없는 경우<br/>- `UNION`, `UNION ALL`이 사용된 쿼리<br/>- Index를 사용하지 못한 정렬 작업             |
          | `Using where`                                                            | Mysql Engine이 별도로 가공/필터 작업을 처리한 경우 표시된다. 범위 조건(`BETWEEN`)은 Storage Engine에서 처리되서 레코드를 반환해주지만 체크 조건은 Mysql Engine에서 처리된다<br/>`SELECT NAME FROM employee WHERE age BETWEEN 20 AND 30 AND gender = 'M'`                                                   |
    - `Filtered`
        - `EXPLAIN` 키워드에 `EXTENDED` 키워드를 붙이면 `Filtered` 컬럼을 볼 수 있다.
        - `Extra` 컬럼에 표시되는 `Using where`은 Storage Engine이 반환해준 레코드들을 MySQL Engine이 필터링하면 등장한다.
        - 이 때, 얼마나 많은 레코드들이 필터링 되는지 보여주는 컬림이다.
        - 필터링 후 몇퍼센트 정도 남았는지 표시해주는데 이는 `통계값이지 정확한 값이 아니다.`
          ```sql
          EXPLAIN EXTENDED
          SELECT *
          FROM dept_emp
          WHERE emp_no BETWEEN 1 AND 10
          ```
- Reference : https://jeong-pro.tistory.com/243

---

SQL Indexing
===

- ## Indexing을 타지 않는 경우
    - 인덱스 컬럼을 변형시킨 경우
        - 인덱스로 잡혀 있는 컬럼에 함수를 적용하거나 그 컬럼 자체를 변형시킨 경우 인덱스를 타지 않는다.
          ```sql
          SELECT * FROM employee WHERE SUBSTR(name, 5) = 'lee';
          SELECT * FROM employee WHERE age + 10 = 30; -- 인덱스를 타지 않음
          SELECT * FROM employee WHERE age = 30 - 10; -- 인덱스를 사용
          ```
    - NULL 조건을 사용했을 경우
        - NULL 조건식을 사용할 경우 `Table Full Scan`이 동작한다.
          ```sql
          SELECT * FROM employee WHERE name IS NULL;
          SELECT * FROM employee WHERE dept IS NOT NULL;
          ```
    - 부정 연산자를 사용한 경우
        - 무조건 인덱스를 타지 않는건 아니고 일반적으로 NOT에 사용된 값이 아닌 경우가 데이터가 많기 때문에 인덱스를 타지 않는다.
          ```sql
          SELECT * FROM employee WHERE age != 30; -- 인덱스를 타지 않음
          SELECT * FROM employee WHERE age > 20 AND age < 30; -- 인덱스를 사용
          ```
    - LIKE 문에서 전체 범위를 설정했을 경우
      ```sql
      SELECT * FROM employee WHERE name LIKE '%yongwoo%'; -- 인덱스를 타지 않음
      SELECT * FROM employee WHERE name LIKE 'yongwoo%'; -- 인덱스를 사용
      ```
    - IN 연산자를 사용했을 경우
        - 부정 연산자와 마찬가지도 무조건 인덱스를 타지 않는건 아니고 조건에 해당하는 데이터가 많다고 판단할 경우 `Tall Full Scan`으로 동작한다.
          ```sql
          SELECT * FROM employee WHERE age IN ( 29, 30, 31);
          ```
    - 복합 인덱스일 경우 첫 인덱스가 첫 조건으로 안들어갔을 경우
        - name, age가 복합 인덱스일 경우 name -> age 순으로 조건을 걸어야 인덱스를 탈 수 있다.
          ```sql
          SELECT * FROM employee WHERE age = 30 AND name = 'yongwoo'-- 인덱스를 타지 않음
          SELECT * FROM employee WHERE name = 'yongwoo' AND age = 30; -- 인덱스를 사용 
          ``` 
    - 인덱스 컬럼이 내부적으로 데이터를 변환할 경우
        - 인덱스 컬럼이 갖고 있는 정확한 데이터 타입을 넣어줘야 한다.
          ```sql
          SELECT * FROM employee WHERE age = '30' -- 인덱스를 타지 않음
          SELECT * FROM employee WHERE age = to_number('30') -- 인덱스를 사용
          ```
- Reference : https://hckcksrl.medium.com/index를-타지않는-쿼리-41f0417bfe03

---