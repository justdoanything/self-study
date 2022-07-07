package prj.test.kyh;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import prj.jpa.kyh.dto.MemberDto;
import prj.jpa.kyh.entity.Member;
import prj.jpa.kyh.repository.MemberRepository;

@DataJpaTest
@Transactional
@Rollback(false)
public class MemberRepositoryTest {

        @Autowired
        MemberRepository memberRepository;

        @Test
        public void memberRepositoryTest() {
                Member m1 = new Member().builder()
                                .name("첫번째")
                                .sex("남자")
                                .age(30)
                                .city("서울")
                                .build();
                Member m2 = new Member().builder()
                                .name("두번째")
                                .sex("여자")
                                .age(20)
                                .city("경기")
                                .build();
                Member m3 = new Member().builder()
                                .name("1")
                                .sex("남자")
                                .age(10)
                                .city("부산")
                                .build();

                Member savedMember1 = memberRepository.save(m1);
                Member savedMember2 = memberRepository.save(m2);
                Member savedMember3 = memberRepository.save(m3);

                Member findMember = memberRepository.findById(savedMember2.getId()).get();

                // init
                assertEquals(memberRepository.count(), 3);
                assertThat(findMember.getName()).isEqualTo(savedMember2.getName());
                assertThat(findMember).isEqualTo(savedMember2);

                // delete
                memberRepository.delete(savedMember2);
                memberRepository.delete(findMember);
                assertThat(memberRepository.count()).isEqualTo(2);

                // Query Builder
                assertThat(memberRepository.findByNameAndAgeGreaterThan("첫번째", 20).get(0)).isEqualTo(m1);
                assertThat(memberRepository.findByNameAndAgeGreaterThan("두번째", 20)).isNullOrEmpty();

                // @Query
                assertEquals(memberRepository.findUser("첫번째", 30).get(), savedMember1);
                assertEquals(memberRepository.findByNames(Arrays.asList("첫번째", "두번째")).size(), 1);

                // @Query + DTO
                List<MemberDto> members = memberRepository.findMemberDtos();
                members.forEach(System.out::println);
        }

}
