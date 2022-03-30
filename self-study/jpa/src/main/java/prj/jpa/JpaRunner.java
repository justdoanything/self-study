package prj.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import prj.jpa.basic.Account;
import prj.jpa.basic.Study;

@Component
@Transactional
public class JpaRunner implements ApplicationRunner {
    
    @PersistenceContext
	EntityManager entityManager;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Account account = new Account();
		account.setUsername("yongwoo");
        account.setPassword("good");

        Study study = new Study();
        study.setUserName("studyOwner");
        study.setUserPassword("secret");
        study.setOwner(account);

        //account.getStudies().add(study);
        //study.setOwner(account);
        account.addStudy(study);
        //account.delete(study);

        Session session = entityManager.unwrap(Session.class);
        session.save(account);
        session.save(study);
    }
}
