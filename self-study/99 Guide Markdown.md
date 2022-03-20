# Markdown 사용법

visual code에서 `Markdown Preview Github Styling` extension을 설치하고 `ctrl+shift+V` 단축키를 부르면 미리보기 화면이 나온다.

---


```
영역
```

Big Header
===
Small Header
---
# H1
## H2
### H3
###### H6

> Code block Depth1
>> Code Block Depth2
>>> Code Block Depth3

1. 첫번째
3. 세번째
2. 두번째

* 빨강
  + 빨강
    - 빨강

This is code:

    This is code
    This is code

```java
public static void main(String[] args){

}
```

*single asterisks* \
_single underscores_ \
**double asterisks** \
__double underscores__ \
~~cancelline~~


표(Table)
헤더 셀을 구분할 때 3개 이상의 -(hyphen/dash) 기호가 필요합니다.
헤더 셀을 구분하면서 :(Colons) 기호로 셀(열/칸) 안에 내용을 정렬할 수 있습니다.
가장 좌측과 가장 우측에 있는 |(vertical bar) 기호는 생략 가능합니다.

값 | 의미 | 기본값
---|:---:|---:
`static` | 유형(기준) 없음 / 배치 불가능 | `static`
`relative` | 요소 **자신**을 기준으로 배치 |
`absolute` | 위치 상 **_부모_(조상)요소**를 기준으로 배치 |
`fixed` | **브라우저 창**을 기준으로 배치 |



<details>

  <summary>click to collapse</summary>

  this one starts expanded because of the "open"

</details>

출처 : [github ihoneymon](https://gist.github.com/ihoneymon/652be052a0727ad59601)
