package prj.modern.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import prj.modern.entity.SearchKeywordHistoryEntity;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SearchKeywordHistoryRepository extends JpaRepository<SearchKeywordHistoryEntity, Long> {
    @Query(value =  "SELECT keyword as keyword, COUNT(*) as keywordCount " +
                    "FROM search_keyword_history " +
                    "WHERE created_datetime BETWEEN :startDate AND :endDate " +
                    "GROUP BY keyword " +
                    "ORDER BY keywordCount DESC " +
                    "LIMIT :limitSize"
                    , nativeQuery = true)
    List<Object[]> findTopKeywordByPeriod(Integer limitSize, LocalDateTime startDate, LocalDateTime endDate);
}