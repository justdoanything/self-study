package prj.yong.jpa.jpa;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.Rollback;

import prj.jpa.basic.Post;
import prj.jpa.springJPA.PostRepository;

@DataJpaTest
public class JpaRunnerTest {
    @Autowired
    PostRepository postRepository;
    
    @Test
    @Rollback(false)
    public void postRepositoryTest() {
        Post post = new Post();
        post.setTitle("JPA 재밌어요!!");
        Assertions.assertThat(post.getId()).isNull();

        Post newPost = postRepository.save(post);
        Assertions.assertThat(post.getId()).isNotNull();

        Post post2 = new Post();
        Post post3 = new Post();
        Post post4 = new Post();
        Post post5 = new Post();

        post2.setTitle("2번째 타이틀입니다.");
        post3.setTitle("3번째 타이틀입니다.");
        post4.setTitle("4번째 타이틀입니다.");
        post5.setTitle("5번째 타이틀입니다.");

        postRepository.save(post2);
        postRepository.save(post3);
        postRepository.save(post4);
        postRepository.save(post5);
        
        List<Post> posts = postRepository.findAll();
        posts.forEach(System.out::println);
        Assertions.assertThat(posts.size()).isEqualTo(5);
        Assertions.assertThat(posts).contains(newPost);

        final int sliceSize = 10;
        Page<Post> page = postRepository.findAll(PageRequest.of(0, sliceSize));
        Assertions.assertThat(page.getTotalElements()).isEqualTo(5);
        Assertions.assertThat(page.getNumber()).isEqualTo(0);
        Assertions.assertThat(page.getSize()).isEqualTo(sliceSize);
        Assertions.assertThat(page.getNumberOfElements()).isEqualTo(5);

        System.out.println(postRepository.countByTitleContains("타이틀"));
    }
    
}
