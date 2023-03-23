package prj.searching.model.search;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import prj.searching.constant.SearchConstant.PopularKeywordPeriod;
import prj.searching.constant.valid.Enum;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SearchPopularKeywordRequestVO {
    @Min(value = 1, message = "1 이상 10 이하까지 가능합니다.")
    @Max(value = 10, message = "1 이상 10 이하까지 가능합니다.")
    @Builder.Default
    @ApiModelProperty(value = "인기 검색어 수 (기본값: 5)", notes = "인기 검색어 수", example = "5")
    private Integer size = 5;

    @Builder.Default
    @Enum(enumClass = PopularKeywordPeriod.class)
    @ApiModelProperty(value = "인기 검색어를 검색하는 기간 (기본값: ALL)", notes = "인기 검색어 검색 기간", example = "ALL", allowableValues = "MIN, HOUR, DAY, WEEK, MONTH, YEAR, ALL")
    private String period = PopularKeywordPeriod.ALL.name();
}
