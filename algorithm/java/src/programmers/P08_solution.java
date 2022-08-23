package programmers;

public class P08_solution {
  
  public static void main(String[] args) {
    System.out.println(solution("minco.choi")); // T
    System.out.println(solution("inho.min"));   // T
    System.out.println(solution("ab.abc.min")); // F
    System.out.println(solution("jhop."));      // F
    System.out.println(solution("mingu.  ab")); // F

  }
  public static boolean solution(String ID) {
    boolean answer = true;

    // 이름.성
    // 이름 : 1 <= 글자수 <= 10, 영문자
    // 성 : 0 <= 글자수 <= 5, 영문자, 없을 수 있다.
    // 알파벳 대소문자와 .만 있어야함

    // 이름만 있는 경우
    if(!ID.contains(".")) {
      for(int i=0; i<ID.length(); i++){
        if((ID.charAt(i) >= 'a' && ID.charAt(i) <= 'z') || (ID.charAt(i) >= 'A' && ID.charAt(i) <= 'Z')){
        }else {
          return false;
        }
      }
    } else {
      // 이름.성 인 경우
      String[] arr = ID.split("\\.");
      if(arr.length != 2)
        return false;
      
      if(arr[0].length() >= 1 && arr[0].length() <= 10 && arr[1].length() > 0 && arr[1].length()<=5){
        for(int i=0; i<ID.length(); i++){
          if((ID.charAt(i) >= 'a' && ID.charAt(i) <= 'z') || (ID.charAt(i) >= 'A' && ID.charAt(i) <= 'Z') || ID.charAt(i) == '.'){
          
          }else {
            return false;
          }
        }  
      }else{
        return false;
      }

    }
    


    return answer;
  }
}