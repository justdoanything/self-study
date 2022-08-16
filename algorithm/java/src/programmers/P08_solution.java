package programmers;

public class P08_solution {
  
  public boolean solution(String ID) {
    boolean answer = true;
    return answer;
  }
}


/*

민식이는 유명 커뮤니티에 본인의 계정을 만들고 있습니다. 커뮤니티에 가입하기 위해서는, ID를 특정 규칙에 맞게 만들어야합니다.
tqwer.png

가입하고자 하는 ID가 주어졌을 때, ID Format에 맞는지 ID인지 검사하여 true / false를 반환하는 solution 함수를 제작하세요.

제한사항

ID는 [이름].[성] 형태로 구성됩니다.
이름 규칙 : 1 <= 이름의 글자 수 <= 10, 영문자
성 규칙 : 0 <= 성 글자 수 <= 5, 영문자
만약 성이 없다면, [이름] 형태로 되어 있어야 합니다.
알파벳 대소문자, "." 이외의 다른 문자는 존재하면 안됩니다.
입출력 예

ID	result
minco.choi	true
ab.abc.min	false
coco.min	true
minchul	true
co123.json	false
jhop.	false
입출력 예 설명
입출력 예 #2
[성].[이름] format에 맞지 않습니다.
입출력 예 #5
성과 이름에는 영문자만 허용됩니다.
입출력 예 #6
이름은 반드시 있어야 합니다.



테스트 1
입력값 〉	"minco.choi"
기댓값 〉	true
실행 결과 〉	테스트를 통과하였습니다.
테스트 2
입력값 〉	"inho.min"
기댓값 〉	true
실행 결과 〉	테스트를 통과하였습니다.
테스트 3
입력값 〉	"ab.abc.min"
기댓값 〉	false
실행 결과 〉	실행한 결괏값 true이 기댓값 false과 다릅니다.
테스트 4
입력값 〉	"jhop."
기댓값 〉	false
실행 결과 〉	실행한 결괏값 true이 기댓값 false과 다릅니다.
테스트 5
입력값 〉	"mingu.  ab"
기댓값 〉	false
실행 결과 〉	실행한 결괏값 true이 기댓값 false과 다릅니다.

*/