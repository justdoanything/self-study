package prj.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import prj.jpa.basic.Account;
import prj.jpa.basic.Comment;
import prj.jpa.basic.Post;
import prj.jpa.basic.Study;

@Component
@Transactional
public class JpaRunner implements ApplicationRunner {
    
    @PersistenceContext
	EntityManager entityManager;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Session session = entityManager.unwrap(Session.class);

        // Account account = new Account();
		// account.setUsername("yongwoo");
        // account.setPassword("good");

        // Study study = new Study();
        // study.setUserName("studyOwner");
        // study.setUserPassword("secret");
        // study.setOwner(account);

        // //account.getStudies().add(study);
        // //study.setOwner(account);
        // account.addStudy(study);
        // //account.delete(study);

        // session.save(account);
        // session.save(study);

        /*******************************************************************/
        Post post = new Post();
        post.setTitle("JPA 강의");
        
        Comment comment1 = new Comment();
        comment1.setComment("잘 들고 있습니다1");
        post.addComment(comment1);

        Comment comment2 = new Comment();
        comment2.setComment("잘 들고 있습니다2");
        post.addComment(comment2);

        session.save(post);
        
        Post post2 = session.get(Post.class, 1l);
        session.delete(post2);

    }
}
