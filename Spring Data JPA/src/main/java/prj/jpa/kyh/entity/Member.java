package prj.jpa.kyh.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data // @Setter: 실무에서 가급적 Setter는 사용하지 않기
@Builder
@AllArgsConstructor
@NoArgsConstructor // @NoArgsConstructor AccessLevel.PROTECTED: 기본 생성자 막고 싶은데, JPA 스팩상 PROTECTED로 열어두어야 함
@ToString(of = { "member_id", "name", "sex", "age", "city"}) // @ToString은 가급적 내부 필드만(연관관계 없는 필드만)
public class Member extends BaseEntity {
    @Id@GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String name;
    private String sex;
    private Integer age;
    private String city;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;
    
    public Member(String name, String sex, Integer age, String city, Team team) {
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.city = city;
        if(team != null)
            changeTeam(team);
    }
    
    // 양방향 연관관계 한번에 처리(연관관계 편의 메소드)
    public void changeTeam(Team team) {
        this.team = team;
        team.getMembers().add(this);
    }
}
