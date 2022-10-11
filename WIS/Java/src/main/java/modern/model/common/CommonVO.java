package modern.model.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CommonVO {
    @Email
    @NotNull
    @ApiModelProperty(value="", notes="", example = "")
    private String attribute1;
    private Object attribute2;

}
