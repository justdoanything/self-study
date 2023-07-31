package prj.searching.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import prj.searching.model.popular.PopularKeywordDTO;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Entity(name = "search_keyword_history")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
@SqlResultSetMapping(
        name = "MappingPopularKeyword",
        classes = {
                @ConstructorResult(
                        targetClass = PopularKeywordDTO.class,
                        columns = {
                                @ColumnResult(name = "keyword", type=String.class),
                                @ColumnResult(name = "keywordCount", type = BigInteger.class)
                        }
                )
        }
)
@NamedNativeQuery(
        name = "findTopKeywordByPeriod",
        query =  "SELECT keyword as keyword, COUNT(*) as keywordCount " +
                "FROM search_keyword_history " +
                "WHERE created_datetime BETWEEN :startDate AND :endDate " +
                "GROUP BY keyword " +
                "ORDER BY keywordCount DESC " +
                "LIMIT :limitSize",
        resultSetMapping = "MappingPopularKeyword"
)
public class SearchKeywordHistoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String keyword;
    @CreatedDate
    @Column(name = "created_datetime")
    private LocalDateTime createdDatetime;
}
