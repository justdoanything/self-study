package prj.modern.constant;

public enum SearchEngineTypeCode {
    KAKAO("001"),
    NAVER("002"),
    GOOGLE("003");

    private String code;

    SearchEngineTypeCode(String code){
        this.code = code;
    }

    public String code() {
        return this.code;
    }
}
