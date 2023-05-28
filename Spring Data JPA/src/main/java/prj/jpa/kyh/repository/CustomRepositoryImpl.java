package prj.jpa.kyh.repository;

import java.util.List;

import javax.persistence.EntityManager;

import lombok.RequiredArgsConstructor;
import prj.jpa.kyh.entity.Member;

@RequiredArgsConstructor
public class CustomRepositoryImpl implements CustomRepository {

    private final EntityManager em;
    
    @Override
    public List<Member> findMemberCustom() {
        return em.createQuery("select m from Member m")
                    .getResultList();
    }
}
