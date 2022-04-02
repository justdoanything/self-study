package prj.jpa.springJPA;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

@NoRepositoryBean
public interface CustomRepository<T, Id extends Serializable> extends Repository<T, Id> {
    <E extends T> E save(E entity);
    List<T> findAll();
    long count();
}
