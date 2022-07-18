package prj.jpa.kyh.repository;

import java.util.List;

import prj.jpa.kyh.entity.Member;

public interface CustomRepository {
    List<Member> findMemberCustom();
}
