package programmers.lv1;

public class lv1_키패드누르기_2020KakaoInternship {
    public String solution(int[] numbers, String hand) {
        StringBuffer sb = new StringBuffer();
        int[] position = new int[]{10,12}; // position[0] = 왼손위치 , position[1] = 오른손위치

        for(int i = 0; i<numbers.length;i++) {
            int number = numbers[i]==0?11:numbers[i]; //0인 경우 치환

            switch(numbers[i]) {
                case 1: case 4: case 7: sb.append("L"); position[0] = numbers[i]; break;
                case 3: case 6: case 9: sb.append("R"); position[1] = numbers[i]; break;
                default :
                    if(isLeft(number, position[0], position[1], hand)){
                        sb.append("L"); position[0] = numbers[i];
                    } else {
                        sb.append("R"); position[1] = numbers[i];
                    }
                    break;
            }
        }
        return sb.toString();
    }

    public Boolean isLeft(int number, int leftPos, int rightPos, String hand) {
        int leftDistance = 0;              // 왼손의 필요 거리
        int rightDistance = 0;             // 오른손 필요 거리

        //왼손, 오른손 중 위치가 0인경우 11로 치환
        if(leftPos == 0) {
            leftPos = 11;
        } else if(rightPos == 0) {
            rightPos = 11;
        }
        //경로 절대값 구하기
        leftDistance = Math.abs(number-leftPos);
        rightDistance = Math.abs(number-rightPos);

        //절대값/3 의 몫 : 세로거리, 절대값/3 의 나머지 : 가로거리
        leftDistance = (leftDistance/3) + (leftDistance%3);
        rightDistance = (rightDistance/3) + (rightDistance%3);

        if(leftDistance < rightDistance) { //왼손이 가까움
            return true;
        } else if(leftDistance > rightDistance) { //오른손이 가까움
            return false;
        } else { //동일 거리 : hand 비교
            if("left".equals(hand)) {
                return true;
            } else {
                return false;
            }
        }
    }
}
