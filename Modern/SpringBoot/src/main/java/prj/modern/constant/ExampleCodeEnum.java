package prj.modern.constant;

public enum ExampleCodeEnum {
    READY("01"),
    GO("02"),
    CLOSE("03"),
    END("04");

    private String value;

    ExampleCodeEnum(String value){
        this.value = value;
    }

    public String value() {
        return this.value;
    }
}
