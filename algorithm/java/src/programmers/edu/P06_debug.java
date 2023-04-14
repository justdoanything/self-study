package programmers.edu;

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