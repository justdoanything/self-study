package prj.jpa.kyh.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class MemberDto {

    private Long id;
    private String name;
    private String city;

}
