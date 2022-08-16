package programmers;

class P06_debug {
	
	static boolean isDigit(char ch) { 
		return '0' <= ch && ch <= '9';
	}
	
	static boolean isAlpha(char ch) { 
		return ('a' <= ch && ch <= 'z') || ('A' <= ch && ch <= 'Z'); 
	}
	
	public static int solution(String s) {
		boolean flag = false; 
		int answer = 0;
		StringBuilder result = null; 
		for(int i =0 ; i < s.length(); i ++) { 
			if(isDigit(s.charAt(i)) && !flag) { 
				flag = true; 
				result = new StringBuilder();  
			}
			else if (isAlpha(s.charAt(i)) && flag) {
				flag = false; 
				answer += Integer.parseInt(result.toString()); 
			}
			
			if(flag) { 
				result.append(s.charAt(i));
			}
		}
		answer += Integer.parseInt(result.toString());
		
		return answer; 
	}

	public static void main(String[] args){
		System.out.println(solution("carrot3apple241pear530egg10banana37"));
	}
}
/*

문제 설명
물류 센터에서 전송된 데이터가 통신 오류로 인해 물품명과 수량이 모두 붙어서 기록이 되었습니다. 전송된 데이터는 하나의 문자열로 구성되어 있습니다.

Tire100Bolt200

모든 물품명 오른쪽에는 수량이 붙어 있습니다. 수량에 해당하는 문자열은 0으로 시작할 수 있고 수량은 0 이상의 정수입니다.

예를 들어 "carrot3apple241pear530egg10banana37"
데이터가 입력되었으면 기존의 데이터는 아래 표와 같습니다.

물품명	수량
carrot	3
apple	241
pear	530
egg	10
banana	37

하나의 문자열이 주어지면, 모든 물품들의 총 수량을 리턴 해주는 solution 함수를 제작하였습니다. 주어진 초기 코드는 버그가 나오는 코드입니다. 한 줄의 잘못된 코드를 찾아 고친 뒤 완성된 코드를 제출해주세요.


테스트 1
입력값 〉	"carrot3apple241pear530egg10banana37"
기댓값 〉	821
실행 결과 〉	테스트를 통과하였습니다.
테스트 2
입력값 〉	"A001B002"
기댓값 〉	3
실행 결과 〉	테스트를 통과하였습니다.
*/
