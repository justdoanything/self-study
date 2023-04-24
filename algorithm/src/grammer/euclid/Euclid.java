package grammer.euclid;

// 최대공약수와 최소공배수
public class Euclid {
    public static void main(String[] args) {
        int a = 36;
        int b = 16;
        System.out.println("최대공약수 : " + gcd(a,b)); 
        System.out.println("최소공배수 : " + lcm(a,b)); 

        // 같은 수로 담아서 최대 몇봉지 만들 수 있어?
        int[] input = {12, 6, 24, 36};
        System.out.println(solution(input)); 

        System.err.println(gcd(21,23));        
        System.err.println(lcm(21,23));        
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

    private static int solution(int[] array){
        if(array.length == 0) {
            return 0;
        } else if (array.length == 1){
            return 1;
        } else{
            int tmp = gcd(array[0], array[1]);
            int min = array[0] < array[1] ? array[0] : array[1];
            for(int i=2; i<array.length; i++){
                if(min > array[i])
                    min = array[i];
                tmp = gcd(tmp, array[i]);
            }
            
            System.out.println(min + " / " + tmp);
            return min / tmp;
        }
    }
}


