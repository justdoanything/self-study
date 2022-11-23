package prj.yong.modern.model.spring;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.Extension;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Descroption
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Data
// @Getter
// @Setter
@Builder
// @SuperBuilder
// @EqualsAndHashCode(callSuper = false)
public class SpringRequestVO {
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
    private String valueHasDefault = "";
}
