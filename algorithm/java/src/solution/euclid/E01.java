package solution.euclid;

public class E01 {
    
    public static void main(String[] args) {
        int[] time1 = {6,8,5,12};
        System.out.println(solution(time1)); // 120

        int[] time2 = {4,7,20,12};
        System.out.println(solution(time2)); // 420

    }   

    public static int solution(int[] time){
        // 작업 시간이 같아질때 == 최소공배수
        int result = time[0];
        for(int i=1; i<time.length;i++){
            result = lcm(result, time[i]);
        }

        return result;
    }

    // 최대공약수
    private static int gcd(int a, int b){
        while(b != 0){
            int r = a%b;
            a = b;
            b = r;
        }
        return a;
    }

    // 최소공배수
    public static int lcm(int a, int b){
        return a*b/gcd(a,b);
    }
}
