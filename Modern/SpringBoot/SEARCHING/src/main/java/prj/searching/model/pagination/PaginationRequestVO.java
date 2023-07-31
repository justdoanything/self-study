package prj.searching.model.pagination;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class PaginationRequestVO {
    @Builder.Default
    @ApiModelProperty(value = "Page 단위로 조회 시 한 번에 가져올 Page의 크기 (기본값: 10)", notes = "0보다 큰 양수", example = "10")
    private Integer pageSize = 10;

    @Builder.Default
    @ApiModelProperty(value = "Page 단위로 조회 시 데이터 조회 시작 건 수 (기본값: 1)", notes = "0보다 같거나 큰 양수", example = "1")
    private Integer start = 1;

    @ApiModelProperty(value = "데이터 조회 시 정렬하는 기준")
    private String sortType;
}
