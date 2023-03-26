package prj.modern.model.kakao;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class KakaoBlogMetaDTO {
    private boolean isEnd;
    private int pageableCount;
    private int totalCount;
    private String engine = "KAKAO";
    private String query;
}
