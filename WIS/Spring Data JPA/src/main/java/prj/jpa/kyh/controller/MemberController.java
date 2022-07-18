package prj.jpa.kyh.controller;

import javax.annotation.PostConstruct;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import prj.jpa.kyh.dto.MemberDto;
import prj.jpa.kyh.entity.Member;
import prj.jpa.kyh.repository.MemberRepository;

@RestController
@RequiredArgsConstructor
public class MemberController {
    
    private final MemberRepository memberRepository;

    @GetMapping("/members/{id}")
    public String findMember(@PathVariable("id") Long id){
        Member member = memberRepository.findById(id).get();
        return member.getName();
    }
    @GetMapping("/members/entity/{id}")
    public String findMemberEntity(@PathVariable("id") Member member){
        return member.getName();
    }

    /*
     * Pageable Parameter 사용 가능
     * - page : index from 0
     * - size : default is 20
     * - sort
     * 
     * @PageableDefault 사용 가능
     */
    @GetMapping("/members/page")
    public Page<Member> findMemberPage(@PageableDefault(size = 10, sort="name") Pageable pageable){
        return memberRepository.findAll(pageable);
    }

    @GetMapping("/members/dto")
    public Page<MemberDto> findMemberDto(Pageable pageable){
        Page<Member> page = memberRepository.findAll(pageable);
        // Page<MemberDto> dtoPage = page.map(member -> new MemberDto(member.getId(), member.getName(), member.getCity()));

        // Member -> DTO으로 사용하는 방법
        Page<MemberDto> dtoPage = page.map(MemberDto::new);
        return dtoPage;
    }

    @PostConstruct
    public void init() {
        for(int i=0; i<100; i++){
            memberRepository.save(new Member().builder().name("user" + i).age(i).build());
        }
    }
}
