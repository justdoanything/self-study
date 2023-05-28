package prj.jpa.kyh.entity;

import org.springframework.beans.factory.annotation.Value;

public interface MemberProjectionNameOnlyInterface {
    // select 절에 name 필드만 존재
    String getName();

    // select 절에서 전체 필드를 조회하고 결과를 조합
    @Value("#{target.name + ' ' + target.age}")
    String getNameAndAge();
}
