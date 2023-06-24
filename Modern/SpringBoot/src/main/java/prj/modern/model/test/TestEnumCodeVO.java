package prj.modern.model.test;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import prj.modern.config.jackson.EnumDeserializer;
import prj.modern.constant.SearchEngineTypeCode;

import javax.validation.constraints.NotNull;

@Data
public class TestEnumCodeVO {
    @NotNull
    @JsonDeserialize(using = EnumDeserializer.class)
    private SearchEngineTypeCode searchEngineTypeCode;
}
