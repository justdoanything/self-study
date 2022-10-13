package modern.model.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CommonVO {
    @Email
    @NotNull
    @NotBlank
    @NotEmpty
    @ApiModelProperty(value="", notes="", example = "")
    @DateTimeFormat(style = "SS", iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd", fallbackPatterns = {"M/d/yy", "dd.MM.yyyy"})
    private String attribute1;
    private Object attribute2;

}
