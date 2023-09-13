<!-- TOC -->
* [Spring](#spring)
  * [Annotation](#annotation)
  * [Spring Handler VO](#spring-handler-vo)
  * [Bean 간 순환 참조 문제](#bean-간-순환-참조-문제)
  * [GET Parameter에서 한글 깨지는 문제](#get-parameter에서-한글-깨지는-문제)
* [SQL](#sql)
  * [ORDER BY INTSTR](#order-by-intstr)
  * [ORDER BY FIELD](#order-by-field)
  * [ORDER BY CASE](#order-by-case)
  * [IGNORE CASE & INSERT ON DUPLICATATE UPDATE](#ignore-case--insert-on-duplicatate-update)
* [React](#react)
  * [React Component](#react-component)
    * [Function - 핸드폰에 있는 주소록처럼 첫글자로 그룹핑하는 함수](#function---핸드폰에-있는-주소록처럼-첫글자로-그룹핑하는-함수)
    * [MUI - BaseSelect (GroupComponent 다루기)](#mui---baseselect--groupcomponent-다루기-)
    * [MUI - Autocomplete](#mui---autocomplete)
    * [Nextjs - getServerSideProps의 로그인 처리](#nextjs---getserversideprops의-로그인-처리)
  * [React 자잘한 문법](#react-자잘한-문법)
    * [fetch](#fetch)
    * [enum](#enum)
    * [useEffect - async](#useeffect---async)
    * [useEffect와 router.query](#useeffect와-routerquery)
    * [.map](#map)
    * [.map & await](#map--await)
  * [React Library](#react-library)
    * [Axios](#axios)
      * [async 함수 정의](#async-함수-정의)
      * [Request/Response 정의](#requestresponse-정의)
      * [API 정의](#api-정의)
      * [API 사용](#api-사용)
    * [i18n 언어팩 적용](#i18n-언어팩-적용)
    * [react-notion-x](#react-notion-x)
    * [react-notion](#react-notion)
    * [react-mentions](#react-mentions)
    * [infinite scroll](#infinite-scroll)
    * [Object.keys와 Object.entries (makeQueryString)](#objectkeys와-objectentries--makequerystring-)
  * [formik & yup & 깊은 복사](#formik--yup--깊은-복사)
    * [formik](#formik)
    * [yup](#yup)
    * [깊은 복사](#깊은-복사)
<!-- TOC -->

---

# Spring

## Annotation
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
## Bean 간 순환 참조 문제
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

## GET Parameter에서 한글 깨지는 문제
- org.springframework.web.util.`UriUtils.encode("String Query", "UTF-8");`
- `uriComponentsBuilder.build(true).encode().toUri()`
  ```java
  public class KakaoServiceImpl {
    public KakaoBlogResponseDTO getKakaoBlog(KakaoBlogRequestDTO kakaoBlogRequestDTO) {
      try {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "KakaoAK " + kakaoToken);
      
        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromUriString(kakaoHost + kakaoUrlBlog)
                        .queryParam("sort", kakaoBlogRequestDTO.getSort())
                        .queryParam("page", kakaoBlogRequestDTO.getPage())
                        .queryParam("size", kakaoBlogRequestDTO.getSize())
                        .queryParam("query", UriUtils.encode(kakaoBlogRequestDTO.getQuery(),"UTF-8"));
        
        HttpEntity<String> kakaoSearchBlogRequest = new HttpEntity<>(headers);
        ResponseEntity<KakaoBlogResponseDTO> kakaoSearchBlogResponse = restTemplate.exchange(
          uriComponentsBuilder.build(true).encode().toUri()
            , HttpMethod.GET
            , kakaoSearchBlogRequest
            , KakaoBlogResponseDTO.class
        );
        
        if (kakaoSearchBlogResponse.getStatusCode() != HttpStatus.OK)
            throw new ApplicationException("Fail to request Kakao API (" + kakaoUrlBlog + ") : StatusCode is " + kakaoSearchBlogResponse.getStatusCode(), StatusCodeMessageConstant.FAIL);
          else
            return kakaoSearchBlogResponse.getBody();
      } catch (Exception e) {
        throw new ApplicationException("Exception in requesting Kakao API (" + kakaoUrlBlog + ")");
      }
    }
  }
  ```

---

# SQL
## ORDER BY INTSTR
- 검색 조건과 가장 많이 일치하는 순으로 정렬하고 싶을 때 INTSTR(검색 조건으로 시작하는 인덱스를 반환)을 사용한다.
- `yong`으로 검색한 결과가 `yongwoo`, `leeyong`, `lyong` 라고 했을 때 _INSTR_ 을 사용하면 `GROUP BY 1, 4, 2, name` 순으로 되고 yongwoo, lyong, leeyong 순으로 정렬할 수 있다.
  ```sql
  SELECT *
  FROM member m
  WHERE m.name LIKE CONCAT('%', ${name}, '%')
  ORDER BY INSTR(name, #{name}), name
  ```

## ORDER BY FIELD
- 특정한 값을 우선적으로 정렬할 때 사용한다
  ```sql
  SELECT *
  FROM category c
  ORDER BY FIELD(c.category_name, '국내', '해외'), c.category_name, c.sort_order
  ```
- 예를들어 우선 순위로 조회한 값의 id가 [4, 7, 9, 5] 라고 할 때, [4, 7, 9, 5]는 정렬조건을 부여한 값이기 때문에 이 중에 상위 3개를 조회하고 싶다면 아래와 같이 해야한다.
  만약 그냥 조회한다면 [4, 5, 7, 9] 순으로 조회가 된다.
  ```sql
  SELECT *
  FROM community c
  WHERE ids IN
    <foreach collection="list" item="value" separator="," open="(" close=")">
        #{value}
    </foreach>
  ORDER BY FIELD(c.community_id,
    <foreach collection="list" item="value" separator=",">
        #{value}
    </foreach>
  LIMIT 3
  ```

## ORDER BY CASE
- name이 결과값을 첫자리가 숫자 -> 한글 -> 영어 -> 그 외(특수문자)로 정렬하는 방법
  ```sql
  ORDER BY CASE
    WHEN name REGEXP '^[0-9]' THEN 1
    WHEN name REGEXP '^[ㄱ-ㅎ가-힣]' THEN 2
    WHEN name REGEXP '^[a-zA-Z]' THEN 3
    ELSE 4 END, name
  ```

## IGNORE CASE & INSERT ON DUPLICATATE UPDATE
- `INSERT IGNORE ...` : 중복 키 에러가 발생했을 때 신규로 입력되는 레코드는 무시하고 AUTO_INCREMENT 되지 않음.
- `REPLACE IGNORE ...` : 중복 키 에러가 발생했을 때 기존 레코드는 삭제하고 신규 레코드를 삽입하는 방식이다.
- `INSERT INTO ... ON DUPLICATE UPDATE ...` : 중복 키 에러가 발생했을 때 원하는 값을 직접 설정할 수 있다.
    ```sql
    INSERT INTO employee VALUES ('name', 'city')
    ON DUPLICATE KEY UPDATE city = VALUES(city)
    ```

---

# React
## React Component
### Function - 핸드폰에 있는 주소록처럼 첫글자로 그룹핑하는 함수
- Spring에서 name이 결과값을 첫자리가 숫자 -> 한글 -> 영어 -> 그 외(특수문자)로 정렬해서 반환
  ```sql
  ORDER BY CASE
    WHEN name REGEXP '^[0-9]' THEN 1
    WHEN name REGEXP '^[ㄱ-ㅎ가-힣]' THEN 2
    WHEN name REGEXP '^[a-zA-Z]' THEN 3
    ELSE 4 END, name
  ```
- React에서 배열의 name 필드를 읽어서 첫글자의 자음, 알파벳, 특수문자 기준으로 그룹핑해서 반환
  ```javascript
  export const makeNameGroupByFirstChar = (data: { name: string; [key: string]: any }[], resultGroups: { title: string; [key: string]: any }[]) => {
    const koreanUnicodeStart = 44032
    const koreanUnicodeEnd = 55203
    const alphabetStart = 65
    const alphabetEnd = 90
    const numberStart = 48
    const numberEnd = 57
  
    const isKoreanChar = (char: string) => {
      const unicode = char.charCodeAt(0)
      return unicode >= koreanUnicodeStart && unicode <= koreanUnicodeEnd
    }
  
    const isAlphabetChar = (char: string) => {
      const unicode = char.charCodeAt(0)
      return unicode >= alphabetStart && unicode <= alphabetEnd
    }
  
    const isNumberChar = (char: string) => {
      const unicode = char.charCodeAt(0)
      return unicode >= numberStart && unicode <= numberEnd
    }
  
    const getFirstChar = (name: string) => {
      const firstChar = name.charAt(0)
  
      if (isKoreanChar(firstChar)) {
        const unicode = name.charCodeAt(0) - koreanUnicodeStart
        const index = Math.floor(unicode / 28 / 21)
        return String.fromCharCode(0x1100 + index)
      }
      
      if (isAlphabetChar(firstChar)) {
        return firstChar
      }
      
      if (isNumberChar(firstChar)) {
        return firstChar
      }
      
      return firstChar
    }
  
    data.forEach((item: any) => {
      if (!item.name) return
      const firstChar = getFirstChar(item.name)
      const existGroup = resultGroups.find(group => group.title === firstChar)
      if (existGroup) {
        existGroup.members.push(item)
      } else {
        resultGroups.push({title: firstChar, members: [item]})
      }
    });
  };
  ```

### MUI - BaseSelect (GroupComponent 다루기)
- BaseSelect에서 multi check 옵션을 썼을 때 완료를 누르지 않을 땐 처음 진입했을 때의 checked list 값을 유지해야했는데 잘 되지 않았다.
- 해당 컴포넌트 진입(화면에 노출될 때)할 떄 기본적으로 체크된 값은 `defaultChecked` 를 사용해야한다. `checked` 값을 사용하려면 true/false 값을 각 항목마다 state로 관리해야 하는데 번거로움이 있다.
- 한 항목을 체크하고 체크를 해제했을 때 실행되는 `handleMultipleChecked` 함수는 체크를 하고 해제할 때마다 해당 함수만 나가는게 아니라 컴포넌트 전체를 나가는 문제 떄문에 어려웠었다.
- 따라서 체크를 할 때마다 `onChange` 함수를 통해 바깥쪽 값(state)를 변화시켜줘야 하고 해당 컴포넌트를 `onClose` 하거나 그냥 나갔을 때 기존 값을 유지해주기 위해서 기존 값을 해당 컴포넌트 밖에서 관리해줘야 했다.
- `value`와 `originValue`를 관리해서 완료 버튼을 눌렀을 땐, `onComplete` 를 실행해서 value와 originValue를 없데이트하고 해당 컴포넌트가 닫힐 때 발생하는 `handleCloseModal`에선 외부 변수를 기존 값으로 돌려준다.
- `handleMultipleChecked`에선 체크/해제가 될 때마다 외부 값에 추가 또는 삭제해줘야 한다.
```js
interface DefaultProps {
  title: string;
  value: string | string[];
  originValue?: string;
  optionList: string[];
  multiple: boolean;
  onChange: (param: string) => void;
  onComplete?: (param: string) => void;
}
const BaseSelect = ({
  title,
  value,
  originValue,
  optionList,
  multiple,
  onChange,
  onComplte,
}: DefaultProps) => {
  const [isModalVisible, setIsModalVisible] = useState(false);
  const [btnDisabled, setBtnDisabled] = useState(true);

  const handleMultipleChecked = (checked: boolean, checkedValue: any) => {
    let newCheckValue: string[] = value ? [...value] : [];
    if (checked) {
      if (!newCheckValue.includes(checkedValue)) {
        newCheckValue = [...newCheckValue, checkedValue];
      }
    } else {
      newCheckValue = [...newCheckValue.filter((item: any) => item !== checkedValue)];
    }
    onChange(newCheckValue);
    setBtnDisabled(newCheckValue?.length === 0);
  };
  
  const handleShowModal = (visible: boolean) => () => {
    setBtnDisabled(value?.length === 0);
    setIsModalVisible(visible);
  };
  
  const handleCloseModal = () => {
    if (multiple) {
      onChange(originValue);
    }
    setIsModalVisible(false);
  };
  
  const handleMultiComplete = () => {
    if (onComplete) onComplete(value);
    setIsModalVisible(false);
  };
  
  const handleOptionClick = (optionValue: string) => {
    onChange(optionValue);
    setIsModalVisible(false);
  };
  
  const setOptionList = () => {
    return (
      <List>
        {list.map((item: any) => (
          <ListItem key={item.value}>
            {!multiple && <ListItemButton onClick={() => handleOptionClick(item.value)}>{item.label}</ListItemButton>}
            {multiple && (
              <FormControlLabel
                control={
                  <Checkbox
                    onChange={(event, checked) => handleMultipleChecked(checked, item.value)}
                    defaultChecked={value.includes(item.value)}
                  />
                }
                label={item.label}
              />
            )}
          </ListItem>
        ))}
      </List>
    );
  };
  
  return (
    <RootStyled>
      <div>
        <Input value={value} readOnly />
        <button type="button" onClick={handleShowModal(true)}>
          {(!multiple && list.filter((item: any) => item.value === value)[0]?.label) ?? title}
          {multiple &&
            (value.length === 0
              ? title
              : value.length === 1
              ? list.filter((item: any) => item.value === value[0])[0]?.label
              : `${list.filter((item: any) => item.value === value[0])[0]?.label} 외 ${value.length - 1}개`)}
        </button>
        <DrawerStyled anchor="bottom" open={isModalVisible} onClose={handleCloseModal}>
          <Box sx={{ display: 'flex', alignItems: 'left', padding: '24px 16px 16px' }}>
            {title && <p>{title}</p>}
            <IconButtonStyled sx={{ marginLeft: 'auto' }} onClick={handleCloseModal}>
              <IconClose />
            </IconButtonStyled>
          </Box>
          <Box sx={{ padding: '0 16px 16px', alignItems: 'left', overflowY: 'auto' }}>{setOptionList()}</Box>
          {multiple && (
            <Box>
              <BaseButton variant="boxPurple" onClick={handleMultiComplete} disabled={btnDisabled} fullWidth>
                완료
              </BaseButton>
            </Box>
          )}
        </DrawerStyled>
      </div>
    </RootStyled>
  );
};
export default BaseSelectWithMulti;
```
```js
// 단일 선택 BaseSelect
<FormControl variant="standard">
  <FormLabel htmlFor="contentId">단일선택</FormLabel>
  <BaseSelectWithMulti
    value={selectOne}
    title="지역 선택"
    list={selectOntList}
    onChange={handleSelectOneChange}
  />
</FormControl>

// 멀티 선택 BaseSelect
<FormControl variant="standard">
  <FormLabel htmlFor="contentId">다중선택</FormLabel>
  <BaseSelectWithMulti
    value={selectMulti}
    originValue={originSelectMulti}
    title="근무 업종 선택"
    list={selectMultiList}
    onComplete={handleSelectMultiComplete}
    onChange={handleSelectMultiChange}
    multiple={true}
  />
</FormControl>
```

### MUI - Autocomplete
- 특정 리스트 안에서 입력한 값과 일치하는 아이템들을 보여주고 자동완성 해주는 컴포넌트
- 리스트에 [한국, 일본, 가나, 브라질, 프랑스, 미국] 이 있을 때 '한'을 입력하면 [한국]이 하단에 표시된다.
```js
...
const filter = createFilterOptions<Team>();

return (
  ...
  <Autocomplete
    id="combobox"
    options={data.team}
    getOptionLabel={(option)=>option.name || ''}
    value={data.team.filter((team) => team === member.team?.name) || ''}
    renderInput={(params) => <TextField { ...params} label="팀" />}
    sx={{width: 300}}
    disabled={isTeam ? true : false}
    filterOptions={(options, params) => {
      const filtered = filter(options, params);

      if(params.inputValue !== '' && !data.team.map((team) => team.name).includes(params.inputValue)){
        filtered.push({
          // new Team props
          name: `Add "${params.inputValue}"`,
          avtivated: true,
        });
      }
      return filtered;
    }}
  />

  ...
)
```

### Nextjs - getServerSideProps의 로그인 처리
- Nestjs + SpringBoot로 구성되어 있는 어플리케이션에서 SEO를 위해서 한 페이지의 데이터를 SSR로 가져와야 했다.
- 새로고침이 필요한 데이터라서 `getServerSideProps`를 사용해서 데이터를 가져왔고 로그인 구분 없이 보여주는 컨텐츠와 반응, 댓글 등 로그인 사용자의 정보를 보여줘야하는 컨텐츠가 섞여 있었다.
- Session에 로그인 된 사용자가 있으면 BE API를 호출할 때 로그인 정보를 담아서 호출하고 BE는 Session을 체크해서 로그인 사용자의 대한 정보를 추가로 응답했다.
- 여기서 발생했던 문제는 token 등 로그인 정보는 session과 localStorage에 있었는데 `getServerSideProps`에선 session, localStorage에 접근할 수 없었기 때문에 로그인 정보를 담아서 API를 호출할 수 없었다.
- 이를 해결하기 위해서 해당 페이지의 각 데이터 영역을 공통 컴포넌트 등으로 세분화하고 최초 로딩 시 SSR을 통해서 가져온 데이터로 화면을 렌더링하고 로그인 session이 존재하면 API를 다시 호출해서 로그인 사용자의 정보를 가져오고 useEffect, useMemo를 사용해서 로그인 사용자의 정보가 필요한 컴포넌트만 재랜더링하는 방식으로 구현했다.


## React 자잘한 문법
### fetch
```js
fetch("https://url", {
  method: "POST",
  headers: {
    "Content-Type": "application/json",
    "Access-Control-Allow-Origin": "*",
  },
  body: JSON.stringify({
    fromEmail: "test@email.com",
    toEmail: ["test@email.com"],
    mailSubject: "Test Mailing - Beaver",
    mailContent: "Test Mailing - Beaver",
  }),
}).then((response) => {
  console.log(response.status);
  if (response.status === 200) {
    alert("메일 전송 성공");
  } else {
    alert("메일 전송 실패 : " + response.status);
  }

  response.json().then((json) => {
    console.log(json);
  });
});
```

### enum
```js
export enum Method {
  GET = 'GET',
  POST = 'POST',
  PUT = 'PUT',
  DELETE = 'DELETE',
  PATCH = 'PATCH',
}
```

### useEffect - async
```js
useEffect(() => {
  const fetchData = async () => {
    const data = await (await fetch(url)).json();
    setData(data);
  };

  fetchData();
}, [data]);
```

### useEffect와 router.query
```js
const router = useRouter();
const { id } = router.query;

useEffect(() => {
  if (!router.isReady) return;
  console.log(id);
}, [router.isReady]);
```

### .map
```js
/*
* javascript의 map은 java stream의 map과는 다르다.
* 객체의 속성값을 변경하거나 일괄 처리를 하고 싶을 때 아래와 같이 사용한다.  
*/
const data = File[];
data.files
  .filter((item) => item.fileUrl.startsWith('/IMAGE'))
  .map((item) => {
    return { ...item, fileUrl: bucketBaseUrl + item.fileUrl };
  })
```

### .map & await
```typescript
/*
* .map과 await 같이 쓰기 (Notion & SSR)
*/
interface NotionPageContents {
  title: string;
  contents: ExtendedRecordMap;
}

export const getServerSideProps: GetServerSideProps = async () => {
  const notionApi = new NotionAPI();
  const notionPageList: NotionPageContents[] = await backApi.getContents(ContentsTypeCode.FEED).then((response) => {
    if (response) {
      return Promise.all(
        response
          .filter((item) => item.notionPageUrl)
          .map(async (item) => {
            const notionPageContents = item.notionPageUrl ? await notionApi.getPage(item.notionPageUrl);
            return {
              title: item.title,
              contents: notionPageContents,
            } as NotionPageContents;
          })
      );
    } else {
      return [];
    }
  });
  return { props: { notionPageList } };
}
```
```typescript
interface NotionPageContents {
  title: string;
  contents: ExtendedRecordMap;
}

export const getServerSideProps: GetServerSideProps = async () => {
  if (!context.query.contents) {
    return {
      redirect: {
        destination: '/login',
        permanent: false,
      },
    };
  }
  const notionApi = new NotionAPI();
  const contentsList: Contents[] = JSON.parse(context.query.contents as string);
  const notionPageList: NotionPageContents[] = await Promise.all(
    contentsList.map(async (item) => {
      const recordMap = item.notionPageUrl ? await notionApi.getPage(item.notionPageUrl) : '';
      return {
        title: item.title,
        contents: notionPageContents,
      } as NotionPageContents;
    })
  );
  return { props: { notionPageList } };
}
```

---

## React Library
### Axios
#### async 함수 정의
- API 사용 공통 함수 만들기
```js
// utils/ApiUtil.ts
export const commonCallApi = async (commonApiRequest: CommonApiRequest): Promise<CommonApiResponse> => {
  const url: string = commonApiRequest.url + getQueryStringFormat(commonApiRequest.params?.queryParams);
  const isLoading = commonApiRequest.config?.isLoading || false;
  let response: CommonApiResponse = {
    successOrNot: 'N',
    statusCode: StatusCode.UNKNOWN_ERROR,
    data: {},
  };

  switch (commonApiRequest.method) {
    case Method.GET:
      response = await getInstance(commonApiRequest.service, isLoading).get(url);
      break;
    case Method.POST:
      response = await getInstance(commonApiRequest.service, isLoading).post(url, commonApiRequest.params?.bodyParams);
      break;
    case Method.PUT:
      response = await getInstance(commonApiRequest.service, isLoading).put(url, commonApiRequest.params?.bodyParams);
      break;
    case Method.DELETE:
      response = await getInstance(commonApiRequest.service, isLoading).delete(url);
      break;
    case Method.PATCH:
      response = await getInstance(commonApiRequest.service, isLoading).patch(url, commonApiRequest.params?.bodyParams);
      break;
    default:
      break;
  }
  return response;
};
```
```js
const getInstance = (serviceName: string, isLoading: boolean, params?: any): AxiosInstance => {
  if (isLoading) {
    // @ts-ignore
    // eslint-disable-next-line
    window.loadingSpinner.setChange(true);

  }

  axios.defaults.headers.post['Content-Type'] = 'application/json';

  let baseURL = process.env.NEXT_PUBLIC_API_BASE_URL ;
  const sessionUtil = new SessionUtil();

  if (process.env.NODE_ENV === 'development') {
    switch (serviceName) {
    case Service.MZP_BE:
      baseURL += ':' + ServicePort.MZP_BE.toString();
      break;
    default:
      break;
    }
  }

  const instance = axios.create({
    baseURL: baseURL,
    params: params || {},
  });

  // 공통 요청 처리
  instance.interceptors.request.use(
    (config: AxiosRequestConfig): AxiosRequestConfig => {
      if (config?.headers) {
        config.headers['x-correlation-id'] =
          window.location.pathname === '/'
            ? 'root'.concat('_').concat(uuidv4())
            : window.location.pathname?.concat('_').concat(uuidv4()) || '';
        if (sessionUtil.getSessionInfo().sessionId) {
          config.headers['x-session-id'] = sessionUtil.getSessionInfo().sessionId || '';
        }
      }
      return config;
    },
    (error: any): Promise<any> => {
      return Promise.reject(error);
    },
  );

  // success / error 공통 처리
  instance.interceptors.response.use(
    (response: any): any => {
      if (isLoading) {
        // @ts-ignore
        // eslint-disable-next-line
        window.loadingSpinner.setChange(false);
      }

      const commonResponse: CommonResponse = response.data as CommonResponse;
      if (commonResponse.statusCode && commonResponse.statusCode === StatusCode.SESSION_EXPIRE) {
        sessionUtil.deleteSessionInfo();
        window.location.assign('/login');
      }
      return commonResponse;
    },

    (error: any): any => {
      if (isLoading) {
        // @ts-ignore
        // eslint-disable-next-line
        window.loadingSpinner.setChange(false);
      }

      const unknownError: CommonResponse = {
        successOrNot: 'N',
        statusCode: StatusCode.UNKNOWN_ERROR,
        data: {},
      };

      // eslint-disable-next-line
      if (error.response && error.response.status.toString().indexOf('40') === 0) {
        //TODO: 400대 에러 공통처리
      }
      return unknownError;
    },
  );

  return instance;
};
```
```js
const getQueryStringFormat = (queryParams?: QueryParams): string => {
  if (!queryParams) return '';
  const keys = Object.keys(queryParams);
  const queryString = keys
    .map((key) => `${key}=${encodeURIComponent(queryParams[key] as string)}`) // eslint-disable-line
    .join('&');
  return queryString ? `?${queryString}` : '';
};
```

#### Request/Response 정의
  ```js
  export interface CommonApiRequest {
    service: Service;
    url: string;
    method: Method;
    params?: ParamObject;
    config?: Config;
  }

  export interface CommonApiResponse<T = any> {
    result: string;
    statusCode: string;
    data?: T;
  }
  ```

#### API 정의
  ```js
  export interface SampleRequest {
    type: string;
    id: string;
    email: string;
    memberName: string;
    nickname?: string;
  }
  
  export const sampleApi = async(sampleRequest: SampleRequest, isLoading = true): Promise<CommonApiResponse> => {
    return commonCallApi({
      service: Service.BackEnd,
      url: '/health',
      method: Method.POST,
      params: {
        bodyParams: sampleRequest
      },
      config: {
        isLoading: isLoading
      }
    });
  }
  ```

#### API 사용
  ```js
  const [data, setData] = useState<SampleResponse>();
  useEffect( () => {
    (async () => {
      const response = await sample.sampleApi(sampleRequest);
    setData(response);
    })();
  }, []);
  ```
  ```js
  const sampleCallApi = async () => {
    const sampleRequest = {
      type: info.type,
      id: info.id,
      email,
      memberName,
      nickname,
    } as SampleRequest;
    const response = await sampleApi(sampleRequest);
    if (response.result === SuccessOrNot.Y) {
      // input your logic
    }
  };
  ```

### i18n 언어팩 적용
- `package.json` 에 i18next 관련 dependency 추가
  ```json
  {
    ...
    "i18next": "^21.6.14",
    "i18next-browser-languagedetector": "^6.1.4",
    "react-i18next": "^11.16.2",
    ...
  }
  ```
- `i18n.ts` 파일 생성해서 초기화
  ```js
  import i18n from "i18next";
  import LanguageDetector from "i18next-browser-languagedetector";
  import { initReactI18next } from "react-i18next";

  // transfer file for each languange
  import translationko from "./locales/ko/translation.json";
  import translationen from "./locales/en/translation.json";
  // Add this line to your app entrypoint. Usually it is src/index.js
  // import './i18n';

  // https://react.i18next.com/latest/i18next-instance
  // https://react.i18next.com/latest/using-with-hooks#using-the-withtranslation-hoc
  i18n
    // load translation using xhr -> see /public/locales
    // learn more: https://github.com/i18next/i18next-xhr-backend
    // .use(Backend)
    // detect user language
    // learn more: https://github.com/i18next/i18next-browser-languageDetector
    .use(LanguageDetector)
    // pass the i18n instance to react-i18next.
    .use(initReactI18next)
    // init i18next
    // for all options read: https://www.i18next.com/overview/configuration-options
    .init({
      detection: {
        lookupQuerystring: "locale",
        lookupCookie: "lang",
        lookupLocalStorage: "lang",
      },
      resources: {
        ko: { translation: translationko }, // Korean
        en: { translation: translationen }, // English
      },
      fallbackLng: "ko",
      debug: process.env.REACT_APP_I18N_DEBUG === "true",
      react: {
        useSuspense: false,
      },
    });

  export default i18n;
  ```

- `index.tsx`에 i18n 초기화
  ```js
  import "react-app-polyfill/ie9";
  import "react-app-polyfill/ie11";
  import "react-app-polyfill/stable";

  import "./i18n";

  import React from "react";
  import ReactDOM from "react-dom";
  import "./index.scss";
  import App from "./App";
  import reportWebVitals from "./reportWebVitals";

  ReactDOM.render(
    <React.StrictMode>
      <App />
    </React.StrictMode>,
    document.getElementById("root")
  );

  // If you want to start measuring performance in your app, pass a function
  // to log results (for example: reportWebVitals(console.log))
  // or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
  reportWebVitals();
  ```

- 언어 변경 이벤트 적용 (MUI 사용)
  ```js
  import MenuItem from "@mui/material/MenuItem";
  import FormControl from "@mui/material/FormControl";
  import Select, { SelectChangeEvent } from "@mui/material/Select";

  import { useTranslation } from "react-i18next";

  function navBar {
    const { t, i18n } = useTranslation();
    const [lang, setLang] = React.useState(i18n.language);
    const changeLaunage = (event: SelectChangeEvent) => {
      i18n.changeLanguage(event.target.value);
      setLang(event.target.value);
    };

    return (
      <div className="langBtn">
        <FormControl sx={{ m: 1, minWidth: 50 }}>
          <Select
            value={lang}
            onChange={changeLaunage}
            inputProps={{ "aria-label": "Without label" }}
          >
            <MenuItem value="ko">KOR</MenuItem>
            <MenuItem value="en">ENG</MenuItem>
          </Select>
        </FormControl>
      </div>;
    )
  }

  ```

- 언어팩을 적용할 Component에 `useTranslation` 추가
  ```js
  ...
  import { useTranslation } from 'react-i18next';
  ...

  const Sample = () => {

    const { t, i18n } = useTranslation();

    return (
      ...
      <div><h1>{t('test.title.main')}</h1></div>
    );
  };

  export default Sample;
  ```

- 각 언어에 맞는 번역 json 추가
  ```json
  // ./locales/ko/translation.json
  {
    "test": {
      "title": {
        "main": "한국어 메인입니다!"
      }
    }
  }
  ```

  ```json
  // ./locales/en/translation.json
  {
    "test": {
      "title": {
        "main": "This is English Main!"
      }
    }
  }
  ```

### react-notion-x
- 필요한 package
  - react-notion-x (https://github.com/NotionX/react-notion-x)
  - notion-client
- 유의사항
  - notion-client를 사용하기 때문에 SSR로 데이터를 호출해야 합니다.
  - notion page가 public page로 open되어 있어야 하며 private page은 notion-client에 인증키를 세팅해서 호출해야 합니다.
  - notion page의 style을 사용하기 위해서 반드시 styles.css를 import 해줘야 합니다.\
    `import 'react-notion-x/src/styles.css';`
  - notion page 내 기능이나 link 가 동작하지 않기 때문에 정적인 컨텐츠를 보여줄 때 사용합니다.
  - notion page 기능을 사용하려면 notion page를 popup으로 보여주는게 좋습니다.
- 샘플 코드
  ```js
  import 'react-notion-x/src/styles.css';

  // SSR 방식으로 데이터 호출
  export const getServerSideProps: GetServerSideProps = async () => {
    const notionApi = new NotionAPI();
    const recordMap = await notionApi.getPage(pageId || '');

    return { props: { recordMap } };
  };

  const ClubMySet = ({ recordMap }: InferGetServerSidePropsType<typeof getServerSideProps>) => {
    ...
    return (
      ...
      <div>
        // 데이터 렌더링
        <NotionRenderer recordMap={recordMap} fullPage={false} darkMode={false} />
      </div>
      ...
    )
  }
  ```

### react-notion
- 추가 : `react-notion` 보다 `react-notion-x`가 더 활용성이 좋다.
- 사용하는 Package
  - react-notion (https://github.com/splitbee/react-notion)
  - npm 버전에 따라 build가 되지 않을 수 있으니 node/npm 버전을 확인해야 한다.
  - notion page의 style을 제대로 적용하기 위해서 아래 styles.css를 import 해야 한다.\
    `import "react-notion/src/styles.css";`
- 예제
  - 새로운 창으로 띄우기 : `window.open(link, '', '_blank');`
    ```js
    <Link href="/notion/page">
      <Button variant="outlined"></Button>
    </Link>

    //-------------------------------------------------------------//
    import 'react-notion/src/styles.css';
    import 'prismjs/themes/prism-tomorrow.css';
    import React, { useState, ReactElement, useEffect } from 'react';

    import { NotionRenderer } from 'react-notion';

    const NoticePage = (): ReactElement => {
      const [notionData, setNotionData] = useState({});

      useEffect(() => {
        fetch('https://notion-api.splitbee.io/v1/page/${NOTION_PAGE_ID}')
          .then((response) => response.json())
          .then((json) => {
            setNotionData(json);
          });
      }, []);

      return (
        <div className="noticePage">
          {Object.keys(notionData).length ? (
            <NotionRenderer
              blockMap={notionData}
              fullPage={true}
              hideHeader={true}
            />
          ) : (
            <div>static page</div>
          )}
        </div>
      );
    };
    export default NoticePage;
    ```

### react-mentions
- Library 추가
  - yarn add @types/react-mentions
  - yarn add react-mentions

- 예제
  ```js
  <MentionsInput
    value={mentionValue}
    onChange={(e) => setMentionValue(e.target.value)}
    onKeyDown={function(event)}
    singleLine={true|false}
    onBlur={function(event, clickedSuggestion)}
    allowSpaceInQuery={true|false}
    suggestionsPortalHost={DOM Element}
    inputRef={React ref}
    allowSuggestionsAboveCursor={true|false}
    forceSuggestionsAboveCursor={true|false}
    a11ySuggestionsListLabel={string}
    customSuggestionsContainer={function(children)}
  >
    <Mention
      trigger="$"
      data={users}
      renderSuggestion={function (entry, search, highlightedDisplay, index, focused)}
      /* renderSuggestion={(suggestion, search, highlightedDisplay) => (
          <div className="user">{highlightedDisplay}</div>
        )} */
      markup="@__display__"
      /* 여러개의 Mention에 displayTransform, style을 적용하려면 markup 값이 달라야 한다.*/
      displayTransform={function (id, display)}
      /* displayTransform={(display) => `${"$" + display}`} */
      regex={RegExp}
      onAdd={function (id, display, startPos, endPos)}
      appendSpaceOnAdd={true|false}
    />
  </MentionsInput>
  ```
- git repo : https://github.com/signavio/react-mentions

### infinite scroll
- 사용하는 Package
  - react-infinite-scroll-component
- Sample Code
  - InfiniteScrollModule.tsx
    ```js
    import React from 'react';

    import InfiniteScroll from 'react-infinite-scroll-component';

    interface Props {
      dataList: any;
      fetchMoreData: () => void;
      showListItem: (props: any) => React.ReactNode;
      scrollThreshold?: number;
      repeatCss?: any;
    }

    const InfiniteScrollModule: React.FC<Props> = ({
      dataList,
      fetchMoreData,
      showListItem,
      scrollThreshold,
      repeatCss,
    }: Props) => {
      return (
        <React.Fragment>
          {dataList && (
            <InfiniteScroll
              dataLength={dataList.length}
              next={fetchMoreData}
              hasMore={true}
              loader={<></>}
              scrollThreshold={scrollThreshold || 1}
              scrollableTarget="infiniteScrollDiv"
            >
              {dataList.map((item: any, idx: number) => {
                return (
                  <React.Fragment key={idx}>
                    <div style={repeatCss}>{showListItem(item)}</div>
                  </React.Fragment>
                );
              })}
            </InfiniteScroll>
          )}
        </React.Fragment>
      );
    };

    export default React.memo(InfiniteScrollModule);
    ```
  - Module 사용
    ```js
    <InfiniteScrollModule
                    dataList={feedList}
                    fetchMoreData={() => handleScrollEnd()}
                    showListItem={showListItem}
                    scrollThreshold={0.75}
                    repeatCss={{ marginTop: '12px' }}
                  />
    ```

### Object.keys와 Object.entries (makeQueryString)
- #### 현상
  - HTTP GET 요청을 보낼 때 파라미터로 보내는 객체의 특정 키 값만 UTF-8로 인코딩한 결과를 세팅해서 보내야 했다.
  - axios를 사용하고 있어서 `axios.get` 함수를 사용할 때 객체 형태의 파라미터를 인자로 넣으면 자동으로 인코딩하고 queryString으로 변환해서 호출해주고 있었다.
  - where에 들어가는 값에 대해서만 UTF-8 인코딩한 결과를 사용했어야 했다.
    ```javascript
    interface RequestParam {
      select: string;
      from: string;
      where: string;    // where에 들어가는 값은 UTF-8 인코딩을 해야한다.
      limit?: number;
      page?: number;
    }
    ```
- #### 문제상황
  - axios를 사용하는 컴포넌트에서 where에 들어가는 값을 억음부호(\`)로 묶어서 여러 변수와 공백값들을 사용됐었는데 axios 안에서 자동으로 인코딩된 결과에서 공백이 `%20`이 아니라 `+`로 나오는 현상이 있었다. 
  - axios를 사용하는 컴포넌트에서 where에 들어가는 값을 인코딩(encodeURIComponent)해서 넣으면 `axios.get` 요청이 만들어질 때 자동으로 인코딩을 또 하기 때문에 이중으로 인코딩되는 문제가 있었다. 
- #### 해결방법
  - axios는 파라미터로 넘어오는 값(객체)에 대해서 자동으로 인코딩하기 때문에 내가 사용하는 파라미터는 `axios.get` 함수의 인자로 넘기지 않고 axios를 호출하기 전에 queryString을 임의로 만든 후 URL에 붙여서 호출하려고 했다.
  - 파라미터 객체의 key 값들은 호출하는 컴포넌트에 따라 유동적으로 바뀌기 때문에 queryString을 만드는 함수를 깔끔하게 작성하고 싶었다.
  - 처음에 사용하려고 했던 함수
    - `Object.keys`를 사용해서 만들었던 함수는 에러를 반환했다. 
    - 에러 메세지 : `TS7053: Element implicitly has an 'any' type bacause expression of type 'string' can't be used to index type 'RequestParam'. No index signature with a parameter of type 'string' was found on type 'RequestParam'.`
    - interface의 key 값들을 추출해서 param[key]와 같이 key에 해당하는 값을 바로 접근하는 것은 type safe 하지 않아서 에러를 반환하고 있었다.
    
    ```javascript
    const makeQueryString = (param: RequestParam) => {
      return Object.keys(param)
                      .map(key => {
                          if(key === 'where') {
                            return `${key}=${encodeURIComponent(param[key])}`;
                          }
                          return `${key}=${param[key]}`;
                      }).join('$');
    }
    ````
  - `Object.entries`를 사용해서 해결했었다.

    ```javascript
     const getSearchQueryString = (params: SearchModel.SearchRequest): string => {
       const { orderBy, ...remains } = params;
       const query = {
         ...remains,
         select: params.select.join(','),
         from: `${params.from}.${params.from}`,
         where: `${params.where} order by ${orderBy ?? '$relevance desc'}`,
         'default-hilite': params['default-hilite'] ?? 'off'
       };
 
       const queryString = Object.entries(query)
           .map(([key, value]) => {
             if (key === 'where' || key === 'custom' || key === 'hilite-keywords') {
               return `${key}=${encodeURIComponent(value)}`;
             }
             return `${key}=${value}`;
           })
       .join('&');
 
       return `?${queryString}`;
     };

    export const getDetailSearchResult = (params: SearchModel.SearchRequest): Promise<SearchModel.SearchResponse> => {
      return etcHttp.get<SearchModel.SearchResponse>(`${SEARCH_BASE_URL}${EAPI.SEARCH.GET_SEARCH}${getSearchQueryString(params)}`);
    };
    ```

- #### `Object.keys`에서 interface의 key를 추출해서 사용할 수 없는 이유
  - 근본적으로 Typescript에서는 key 값으로 string 타입을 허용하지 않고 String Literal, number 타입만 허용한다. Typescript는 타입을 정하고 특정한 곳에 특정 타입만 허용하는 것이 중요 원칙이기 때문에 String Literal 자리에 string 타입을 사용해서 컴파일 에러가 발생한 것이다.
  - ##### let? const? const:string?
    ```typescript
    let lets = "I am let.";                 // string (추론)
    const scont: string = "I am const.";    // string
    const cont = "I am narrowed type";      // string literal
    ```
    - `lets`는 let으로 선언되어 있기 때문에 재할당할 수 있고 어떤 문자열이든 넣을 수 있기 때문에 컴파일러는 `lets` => string 타입으로 추론한다.
    - `scont`은 :string으로 타입을 명시했기 대문에 `scont` => string 타입이다.
    - `cont`는 "I am narrowed type." 이 외의 값을 할당할 수 없기 때문에 string 이지만 조금 더 좁은 타입(narrowed type)으로 추론한다. 이것은 Literal Narrowing 이라고 한다.
  - 여기서 추론이란 typescript 컴파일러가 제공해주는 기능으로 개발자가 명시적으로 타입을 선언하지 않으면 컴파일러가 할당되는 값을 기준으로 타입을 (추론해서) 정해준다.
  - `cont`는 string literal 타입으로 아무 string 값을 갖는 변수가 아니라 더 구체적인 값 "I am narrowed type."만을 허용한 타입으로 인식한다.
  - `lets` 또한 type을 명시하면 literal 타입이 될 수 있다.
    ```typescript
    type NarrowType = "I am narrowed type.";
    let nets: NarrowType = "I am narrowed type.";
    nets = "I am const"; // compile error!!
    
    type IntegrationType = "I am let." | " am const." | "I am narrowed type";
    ```
  - 객체에 접근할 때 Literal 타입은 key로 사용이 가능하지만 단순한 string 타입은 사용할 수 없다.
    ```typescript
    const person = {
      name: "lee",
      age: 20
    };
      
    const literalKey = "name";
    console.log(person[literalKey]);  // lee
    console.log(person["age"]);       // 20
      
    for(const key of Object.keys(person)) {
      console.log(person[key]);   // compile error!!
    }
    ```
  - string 타입으로 객체에 key로 접근하기 위해선 Index Signature를 선언하면 가능하다.
    ```typescript
    type Person = {
      [index: string]: string;
      name: string;
      age: number;
    }
    
    const lee: Person = {
      name: "lee",
      age: 20
    };
    const literalKey = "name";
    console.log(lee[literalKey]);  // lee
    console.log(lee["age"]);       // 20

    ```

- #### `Object.keys`와 `Object.entries` 차이

- #### 더 알아보기
  - type Result = { [key in ...]}
  - Partial<>
  - Omit의 쓰임새
- Reference
  - https://soopdop.github.io/2020/12/01/index-signatures-in-typescript/
  

## formik & yup & 깊은 복사
### formik
- 여러 form을 처리해주기 쉽게 만드는거.
- focus = touch로 (form 이 터치가 됐느냐)
- error = error로 잡아줌 (form에 값이 쓰여져있느냐)
- 기본 사용법 (https://krpeppermint100.medium.com/ts-formik-사용법-4f526888c81a)

### yup
- form에 대한 에러를 잡는 기준을 정해주는것.
- formik 안에 yup를 넣어서 yup이 에러 기준을 잡고 에러가 발생하면 formik이 감지함.

```js
return (
  <Formik 
    initialValues={{ username: "beaver", password: "beaver" }} 
    onSubmit={(data, { setSubmitting }) => {
      setSubmitting(true)
    }}>
    {
      ({ values, handleChange, handleBlur, handleSubmit, isSubmitting }) => (
        <form onSubmit={handleSubmit}>
          <div>
            <TextField name="username" value={values.username} onChange={handleChange} />
          </div>
          <div>
            <TextField name="password" value={values.password} onChange={handleChange} />
          </div>
          <Button type="submit" disabled={isSubmitting}>Submit</Button>
        </form>
      )
    }
  </Formik>
);
```

### 깊은 복사
- 요구사항
  - 한 화면에서 기본정보, 보유 자산 종류, 관심 상품 정보 등 여러 form으로 나눠져있고 사용자는 원하는 값을 입력한다.
  - 사용자의 정보가 이미 있는 경우 기존에 저장되있던 데이터를 화면에 뿌려준다.
  - 각 form에는 + 버튼이 있어서 입력 받는 값은 동적으로 늘어나고 줄어들 수 있다.
  - 여러 값들을 입력한 후 저장 버튼을 누르면 화면에 나와있는 데이터를 모두 저장해야 한다.
- 구현
  - 상태관리로는 Redux를 사용했고 formik과 yup를 사용해서 여러 form에 있는 모든 필드에 데이터 검증을 하고 저장 버튼을 눌렀을 때 데이터를 가져오도록 했다.
  - 데이터를 저장할 때 바뀐 필드만 추려서 저장할지, 전체를 저장할지 정해야 했는데 바뀐 필드만 저장하는 방식을 채택했다. (전체를 저장하는게 더 쉬웠을 것 같다....)
  - 기본 값과 바뀐 값 전체를 1:1로 비교해서 바뀐 값만 저장을 했어야 했는데 여기에서 깊은 복사와 얕은 복사의 문제점이 발생했다.
- 문제
  - Redux는 상태 변화를 옵저버로 감지하는데 기존 값과 변화된 값을 비교하기 위해서 기존 값 A를 복사하면 A-1 까지는 깊은 복사가 잘된다. (새로운 주소가 생성된다.)
  - 화면이 여러 form으로 되어 있어서 값들을 props로 던져야했는데 A-1을 props로 넘기고 A-1의 값을 변경했을 때 옵저버가 A-1의 변화를 감지하고 initial value를 변경시켜 버렸다.
  - 그 이유는 A-1의 주소는 새로 따졌지만 proxy로 감싸져 있기 때문에 옵저버는 A-1의 변화를 감지하고 수정된 값을 initial value에 덮어써버린다.
  - 최종적으로 A에 대한 수정된 내용을 비교할 때 initial value는 이미 변경됐기 때문에 form에서 변경된 값으로 인지를 못했다. (변경된 값이 initial value가 됐기 때문에 Redux는 A가 변하지 않은 값으로 판단한다.)
  - SPA에선 변경된 값을 감지하는게 중요한대 (그래야 부분적으로 렌더링을 할 수 있으니까) props로 값들을 넘길때 얕은 복사가 이뤄지면서 데이터의 보존이 어려워졌다.

---