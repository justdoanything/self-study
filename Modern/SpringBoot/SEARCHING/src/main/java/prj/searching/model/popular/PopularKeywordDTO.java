package prj.searching.model.popular;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Setter
@AllArgsConstructor
public class PopularKeywordDTO {
    private String keyword;
    private BigInteger keywordCount;

    public PopularKeywordDTO(Object[] objects){
        keyword = (String) objects[0];
        keywordCount = (BigInteger) objects[1];
    }
}
