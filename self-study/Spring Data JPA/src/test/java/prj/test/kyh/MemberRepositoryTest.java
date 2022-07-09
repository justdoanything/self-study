package prj.test.kyh;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import prj.jpa.kyh.dto.MemberDto;
import prj.jpa.kyh.entity.Member;
import prj.jpa.kyh.entity.Team;
import prj.jpa.kyh.repository.MemberRepository;
import prj.jpa.kyh.repository.TeamRepository;

@DataJpaTest
@Transactional
@Rollback(false)
public class MemberRepositoryTest {

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	TeamRepository teamRepository;

	@PersistenceContext
	EntityManager em;

	// @Test
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
		assertThat(memberRepository.count()).isEqualTo(3);
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

	// @Test
	public void pageAndSortTest() {
		memberRepository.save(new Member().builder().name("1").sex("남자").age(18).city("서울").build());
		memberRepository.save(new Member().builder().name("2").sex("남자").age(19).city("경기").build());
		memberRepository.save(new Member().builder().name("3").sex("남자").age(20).city("부산").build());
		memberRepository.save(new Member().builder().name("4").sex("여자").age(21).city("대구").build());
		memberRepository.save(new Member().builder().name("5").sex("여자").age(22).city("광주").build());
		memberRepository.save(new Member().builder().name("6").sex("남자").age(23).city("대전").build());
		memberRepository.save(new Member().builder().name("7").sex("남자").age(24).city("서울").build());
		memberRepository.save(new Member().builder().name("8").sex("남자").age(25).city("서울").build());
		memberRepository.save(new Member().builder().name("9").sex("여자").age(26).city("경기").build());
		memberRepository.save(new Member().builder().name("10").sex("여자").age(27).city("서울").build());
		memberRepository.save(new Member().builder().name("11").sex("남자").age(28).city("서울").build());
		memberRepository.save(new Member().builder().name("12").sex("남자").age(29).city("포항").build());
		memberRepository.save(new Member().builder().name("13").sex("여자").age(30).city("서울").build());
		memberRepository.save(new Member().builder().name("14").sex("여자").age(31).city("서울").build());
		memberRepository.save(new Member().builder().name("15").sex("여자").age(32).city("부산").build());

		assertEquals(memberRepository.count(), 15);

		int age = 25;
		PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC, "name"));

		// 반환 타입이 page 이면 count 쿼리를 자동으로 날린다.
		Page<Member> page = memberRepository.findByAgeGreaterThan(age, pageRequest);
		page.forEach(System.out::println);
		assertEquals(page.getTotalElements(), 7);
		assertEquals(page.isFirst(), true);
		assertEquals(page.hasNext(), Boolean.TRUE);

		List<Member> content = page.getContent();
		content.forEach(System.out::println);
		assertEquals(content.size(), 3);

		// Entity -> DTO
		Page<MemberDto> dtoPage = page.map(member -> new MemberDto(member.getId(), member.getName(), member.getCity()));

		// update, entityManager
		assertEquals(memberRepository.findByName("15").getAge(), 32);

		int resultCount = memberRepository.bulkAgePlus(25);
		assertEquals(resultCount, 8);
		assertEquals(memberRepository.findByName("15").getAge(), 33);

		// @Modifying(clearAutomatically = true) 옵션을 사용하지 않았으면 clear 해야함.
		// em.flush();
		// em.clear();
		assertEquals(memberRepository.findByName("15").getAge(), 33);

	}

	// @Test
	public void entityGraphTest() {
		Team teamA = new Team("teamA");
		Team teamB = new Team("teamB");

		teamRepository.save(teamA);
		teamRepository.save(teamB);

		memberRepository.save(new Member("1", "남자", 18, "서울", teamA));
		memberRepository.save(new Member("2", "남자", 19, "경기", teamA));
		memberRepository.save(new Member("3", "여자", 20, "부산", teamB));
		memberRepository.save(new Member("4", "여자", 21, "울산", teamB));
		memberRepository.save(new Member("5", "남자", 22, "포항", teamA));
		memberRepository.save(new Member("6", "여자", 23, "전주", teamA));

		Member selectedMember = memberRepository.findByName("4");
		assertEquals(selectedMember.getTeam(), teamB);

		teamB.getMembers().forEach(System.out::println);
		assertThat(teamB.getMembers().contains(selectedMember)).isTrue();

		em.flush();
		em.clear();

		// LAZY 이기 때문에 Member를 조회할 때 Team을 조회하지는 않는다.
		// 첫번째 teamA 를 만났을 때 Team을 조회하고
		// 첫번째 teamB 를 만났을 떄 Team을 조회한다.
		// 그 이후로는 Team은 조회하지 않는다.
		// List<Member> members = memberRepository.findAll();
		List<Member> members = memberRepository.findMemberFetchJoin();
		members.forEach((m) -> {
			System.out.println("==> member name = " + m.getName());
			System.out.println("==> member age = " + m.getAge());
			System.out.println("==> member team = " + m.getTeam().getName());
		});
	}

	@Test
	public void customRepositoryTest() {
        memberRepository.save(new Member().builder().name("1").sex("남자").age(18).city("서울").build());
		memberRepository.save(new Member().builder().name("2").sex("남자").age(19).city("경기").build());
		memberRepository.save(new Member().builder().name("3").sex("남자").age(20).city("부산").build());
        memberRepository.findMemberCustom();
    }
}