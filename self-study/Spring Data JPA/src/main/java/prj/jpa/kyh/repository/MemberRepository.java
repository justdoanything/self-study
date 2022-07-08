package prj.jpa.kyh.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import prj.jpa.kyh.dto.MemberDto;
import prj.jpa.kyh.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

    // Query Builder
    List<Member> findByNameAndAgeGreaterThan(String name, Integer age);

    Member findByName(String name);

    // @Query
    @Query("select m from Member m where m.name = :name and m.age = :age")
    Optional<Member> findUser(@Param("name") String name, @Param("age") Integer age);

    @Query("select m from Member m where m.name in :names")
    List<Member> findByNames(@Param("names") Collection<String> names);

    @Query("select m.name from Member m")
    List<String> findNames();

    // @Query + DTO
    @Query("select new prj.jpa.kyh.dto.MemberDto(m.id, m.name, m.city) from Member m")
    List<MemberDto> findMemberDtos();

    // Paging, Slice, Sort
    Page<Member> findByAgeGreaterThan(Integer age, Pageable pageable);

    @Query(value = "select m from Member m", countQuery = "select count(m) from Member m")
    Page<Member> findByAge(Integer age, Pageable pageable);

    // update
    @Modifying(clearAutomatically = true)
    @Query("update Member m set m.age = m.age + 1 where age >= :age")
    int bulkAgePlus(@Param("age") int age);
}
