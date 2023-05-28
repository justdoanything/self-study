package prj.modern.model.search;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import prj.modern.model.popular.PopularKeywordDTO;

import java.math.BigInteger;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SearchPopularKeywordResponseVO {
    private String keyword;
    private BigInteger count;

    public SearchPopularKeywordResponseVO(PopularKeywordDTO popularKeywordDTO) {
        this.keyword = popularKeywordDTO.getKeyword();
        this.count = popularKeywordDTO.getKeywordCount();
    }
}
