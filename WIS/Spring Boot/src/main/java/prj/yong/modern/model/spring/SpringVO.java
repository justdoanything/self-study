package prj.yong.modern.model.spring;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.Extension;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
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
// @Getter
// @Setter
/**
 * @Descroption
 */
// @Builder
/**
 * @Descroption
 */
@EqualsAndHashCode(callSuper = false)
/**
 * @Descroption
 */
@SuperBuilder

// 이 이외의 속성값들
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SpringVO {
    /**
     * @Description Swagger
     * @return
     */
    @ApiModelProperty(
            value = "",
            name = "",
            allowableValues = "",
            access = "",
            notes = "",
            dataType = "",
            required = true,
            position = 0,
            hidden = true,
            example = "",
            accessMode = ApiModelProperty.AccessMode.AUTO,
            reference = "",
            allowEmptyValue = true,
            extensions =
                    @Extension(
                            name = "",
                            properties = {}))
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
