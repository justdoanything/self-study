package modern.model.spring;

import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.Extension;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

/**
 * @Descroption
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
/**
 * @Descroption
 */
@NoArgsConstructor
/**
 * @Descroption
 */
@Data
//@Getter
//@Setter
/**
 * @Descroption
 */
//@Builder
/**
 * @Descroption
 */
@EqualsAndHashCode(callSuper = false)
/**
 * @Descroption
 */
@SuperBuilder
public class SpringVO {
    /**
     * @Description Swagger
     * @return
     */
    @ApiModelProperty(
            value = ""
            , name = ""
            , allowableValues = ""
            , access = ""
            , notes = ""
            , dataType = ""
            , required = true
            , position = 0
            , hidden = true
            , example = ""
            , accessMode = ApiModelProperty.AccessMode.AUTO
            , reference = ""
            , allowEmptyValue = true
            , extensions = @Extension(name = "", properties = {})
    )
    /**
     * @Description Valid
     * @return
     */
    @NotNull
    @NotEmpty
    @NotBlank
    private String name;

    @Builder.Default
    private String valueHasDefault = "defaultValue";
}
