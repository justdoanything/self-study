package yong.constants;

public enum ContentsTypeCode {
    FEED("001"),
    COMMENT("002"),
    NOTICE("003"),
    COUPON("004"),
    VOTE("005");

    ContentsTypeCode(String code) {
        this.code = code;
    }

    private String code;

    public String code() {
        return code;
    }
}
