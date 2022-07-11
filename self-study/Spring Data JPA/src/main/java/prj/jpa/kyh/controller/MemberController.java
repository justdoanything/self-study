package prj.jpa.kyh.controller;

import javax.annotation.PostConstruct;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
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

    @PostConstruct
    public void init() {
        memberRepository.save(new Member().builder().name("first").build());
    }
}
