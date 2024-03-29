package prj.jpa.kyh.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import prj.jpa.kyh.dto.MemberDto;
import prj.jpa.kyh.entity.Member;
import prj.jpa.kyh.entity.MemberProjection;

public interface MemberRepository extends JpaRepository<Member, Long>, CustomRepository {

    /*
     * Query Builder 
     */
    List<Member> findByNameAndAgeGreaterThan(String name, Integer age);

    Member findByName(String name);

    /*
     * @Query 
     */
    @Query("select m from Member m where m.name = :name and m.age = :age")
    Optional<Member> findUser(@Param("name") String name, @Param("age") Integer age);

    @Query("select m from Member m where m.name in :names")
    List<Member> findByNames(@Param("names") Collection<String> names);

    @Query("select m.name from Member m")
    List<String> findNames();

    /*
     * @Query + DTO
     */
    @Query("select new prj.jpa.kyh.dto.MemberDto(m.id, m.name, m.city) from Member m")
    List<MemberDto> findMemberDtos();

    /*
     * Paging, Slice, Sort 
     */
    Page<Member> findByAgeGreaterThan(Integer age, Pageable pageable);

    @Query(value = "select m from Member m", countQuery = "select count(m) from Member m")
    Page<Member> findByAge(Integer age, Pageable pageable);

    /*
     * Update
     */
    @Modifying(clearAutomatically = true)
    @Query("update Member m set m.age = m.age + 1 where age >= :age")
    int bulkAgePlus(@Param("age") int age);

    /* 
     * N+1 문제와 fetch join, EntityGraph
     */
    // Member를 조회할 떄 연관된 Team을 모두 조회한다.
    @Query("select m from Member m left join fetch m.team")
    List<Member> findMemberFetchJoin();

    @Override
    @EntityGraph(attributePaths = {"team"})
    List<Member> findAll();

    /*
     * Hint & Lock
     */
    @QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value = "true"))
    Member findReadOnlyByName(String name);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    List<Member> findLockByName(String name);

    /*
     * Native Query
     */
    @Query(value = "select * from member where name = ?", nativeQuery = true)
    Member findNativeQueryByName(String name);

    @Query(value = "select m.member_id as id, m.name as name, t.name as teamName from member m left join team t where m.team_id = t.team_id", nativeQuery = true, countQuery = "select count(*) from member")
    Page<MemberProjection> findByProjection(Pageable pageable);

    
}
