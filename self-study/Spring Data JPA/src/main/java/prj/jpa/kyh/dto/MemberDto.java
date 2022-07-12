package prj.jpa.kyh.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import prj.jpa.kyh.entity.Member;

@Data
@Builder
@AllArgsConstructor
public class MemberDto {

    private Long id;
    private String name;
    private String city;

    /*
     * DTO -> Member는 사용하면 안되지만
     * Member -> DTO는 사용해도 괜찮다.
     */ 
    public MemberDto(Member member){
        this.id = member.getId();
        this.name = member.getName();
        this.city = member.getCity();
    }
}
