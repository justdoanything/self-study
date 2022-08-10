package src.programmers;

public class Main3 {
  
  public static void main(String[] args) {
    //["######____", "#___", "____", "#######"]
    String[]  result = solution(4, new String[]{"######____", "#___", "____", "#######"});

    for(int i=0; i<result.length; i++)
      System.out.println(result[i]);

  }

  public static String[] solution(int N, String[] oil_tanks) {
        String[] answer = new String[N];

        for(int i=0; i<oil_tanks.length; i++){
          int base = oil_tanks[i].length();
          int oil = 0;
          for(int j=0; j<base; j++){
            if(oil_tanks[i].charAt(j)=='#')
              oil++;
            else
              break;
          }
          answer[i] = (int)(oil*100)/base + "%";
        }

        return answer;
    }
}
