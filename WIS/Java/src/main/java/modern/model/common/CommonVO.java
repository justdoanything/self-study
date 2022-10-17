package modern.model.common;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import modern.annotation.EnumValid;
import modern.annotation.StringValid;
import modern.constants.CommonEnumConstants.Gender;
import modern.constants.CommonStringConstants;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
public class CommonVO {
    @Email
    @NotNull
    @NotBlank
    @NotEmpty
    @ApiModelProperty(value="", notes="", example = "")
    @DateTimeFormat(style = "SS", iso = DateTimeFormat.ISO.DATE, pattern = "yyyy-MM-dd", fallbackPatterns = {"M/d/yy", "dd.MM.yyyy"})
    private String attribute1;
    private Object attribute2;

    @EnumValid(enumClass = Gender.class)
    private Gender gender;

    @StringValid(stringClass = CommonStringConstants.COMMON_MESSAGE)
    private String message;

}
