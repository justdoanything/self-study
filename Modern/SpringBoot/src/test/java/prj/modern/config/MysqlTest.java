package prj.modern.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import prj.modern.entity.SearchKeywordHistoryEntity;
import prj.modern.repository.SearchKeywordHistoryRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MysqlTest {

    @Autowired
    private SearchKeywordHistoryRepository searchKeywordHistoryRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Test
    public void healthCheckMysql() {
        SearchKeywordHistoryEntity entity1 = SearchKeywordHistoryEntity.builder().keyword("testKeyword").build();
        SearchKeywordHistoryEntity entity2 = SearchKeywordHistoryEntity.builder().keyword("testKeyword").build();

        entityManager.persist(entity1);
        entityManager.persist(entity2);
        entityManager.flush();

        List<SearchKeywordHistoryEntity> list = searchKeywordHistoryRepository.findAll();

        Assertions.assertEquals(2, list.size() );
    }
}
