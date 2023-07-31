package prj.modern.model.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CommonResponseVO {
    @ApiModelProperty(value = "성공, 실패 메세지", notes = "성공, 실패", example = "SUCCESS")
    private String message;

    @ApiModelProperty(value = "응답 데이터", notes = "응답 데이터")
    private Object data;
}
