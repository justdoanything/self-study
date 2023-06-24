package prj.modern.constant;

public enum SearchEngineTypeCode {
    KAKAO("001"),
    NAVER("002"),
    GOOGLE("003");

    private String value;

    SearchEngineTypeCode(String value){
        this.value = value;
    }

    public String value() {
        return this.value;
    }
}
