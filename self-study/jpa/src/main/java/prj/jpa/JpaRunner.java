package prj.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import prj.jpa.basic.Post;
import prj.jpa.springJPA.PostRepository;

@Component
@Transactional
public class JpaRunner implements ApplicationRunner {
    
    @PersistenceContext
	EntityManager entityManager;

    @Autowired
    PostRepository postRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Session session = entityManager.unwrap(Session.class);

        /*******************************************************************/
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
        
        // Post post = new Post();
        // post.setTitle("JPA 강의");
        
        // Comment comment1 = new Comment();
        // comment1.setComment("잘 들고 있습니다1");
        // post.addComment(comment1);

        // Comment comment2 = new Comment();
        // comment2.setComment("잘 들고 있습니다2");
        // post.addComment(comment2);

        // session.save(post);
        
        // Post post2 = session.get(Post.class, 1l);
        // session.delete(post2);

        /*******************************************************************/

        // /* JPQL 하는 방법 */
        // TypedQuery<Post> query =  entityManager.createQuery("select p from Post AS p", Post.class);
        // List<Post> posts = query.getResultList();
        // System.out.println("=====JPQL=====");
        // posts.forEach(System.out::println);

        // /* type safe한 방법*/
        // CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        // CriteriaQuery<Post> criteriaQuery = builder.createQuery(Post.class);
        // Root<Post> criteriaRoot = criteriaQuery.from(Post.class);
        // criteriaQuery.select(criteriaRoot);

        // List<Post> criteriaPost = entityManager.createQuery(criteriaQuery).getResultList();
        // System.out.println("=====Criteria=====");
        // criteriaPost.forEach(System.out::println);

        // /* NativeQuery */
        // List<Post> nativePosts = entityManager.createNativeQuery("select * from Post AS p", Post.class).getResultList();
        // System.out.println("=====Native=====");
        // nativePosts.forEach(System.out::println);

        /*******************************************************************/
        
        List<Post> list = postRepository.findAll();
        list.forEach(System.out::print);

    }
}
