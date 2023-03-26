package prj.modern.model.search;

import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.util.ObjectUtils;
import prj.modern.constant.SearchConstant.DefaultBlogSortType;
import prj.modern.constant.valid.Enum;
import prj.modern.model.kakao.KakaoBlogRequestDTO;
import prj.modern.model.pagination.PaginationRequestVO;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = false)
public class SearchKakaoBlogRequestVO extends PaginationRequestVO {
    @Builder.Default
    @Enum(enumClass = DefaultBlogSortType.class)
    @ApiModelProperty(value = "블로그를 조회하는 정렬 기준 (기본값: ACCURACY)", notes = "블로그 조회 정렬 기준", example = "RECENCY", allowableValues = "ACCURACY, RECENCY")
    private String sortType = DefaultBlogSortType.ACCURACY.name();

    @ApiModelProperty(value = "특정 블로그 URL", notes = "특정 블로그 글만 검색하고 싶은 경우")
    private String blogUrl;

    @ApiModelProperty(value = "블로그를 검색하는 기준 키워드", notes = "블로그 검색 키워드", required = true)
    @NotEmpty
    private String keyword;

    @Min(1)
    @Max(50)
    @Builder.Default
    @ApiModelProperty(value = "Page 단위로 조회 시 한 번에 가져올 Page의 크기 (기본값: 10)", notes = "1~50 사이의 정수", example = "10")
    private Integer pageSize = 10;

    @Min(1)
    @Max(50)
    @Builder.Default
    @ApiModelProperty(value = "Page 단위로 조회 시 데이터 조회 시작 건 수 (기본값: 1)", notes = "1~50 사이의 정수", example = "1")
    private Integer start = 1;

    public KakaoBlogRequestDTO toKakaoBlogRequestDTO() {
        String query = ObjectUtils.isEmpty(blogUrl) ? keyword : blogUrl + " " + keyword;
        String sort = sortType.toLowerCase();
        return KakaoBlogRequestDTO.builder()
                .query(query)
                .sort(sort)
                .page(this.start)
                .size(this.pageSize)
                .build();
    }

    public SearchNaverBlogRequestVO toSearchNaverBlogRequestVO() {
        return SearchNaverBlogRequestVO.builder()
                .keyword(keyword)
                .sortType(sortType)
                .start(this.start)
                .pageSize(this.pageSize)
                .build();
    }
}
