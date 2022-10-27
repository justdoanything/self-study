package modern.constants;

public class CommonEnumConstants {
    /**
     * @Description
     * @Reference https://johnmarc.tistory.com/152
     *  Enum 객체의 비교에서 == 와 .equals()의 차이
     *  == 은 주소값을 비교하기 때문에 컴파일 시 타입 체크를 하고 다른 타입일 경우 컴파일 에러가 발생한다.
     *  하지만 NPE는 검증하지 않는다.
     *
     *  .equals() 는 Object 값을 비교할 때 내부적으로 == 을 사용하고 컴파일 단계에서 타입 체크를 하지 않는다.
     *  런타임에서 NPE는 잡을 수 있다.
     *
     */
    public enum Gender {
        MAN,
        WOMAN
    }
}
