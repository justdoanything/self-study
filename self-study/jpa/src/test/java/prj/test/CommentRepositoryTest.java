package prj.test;

import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import prj.jpa.basic.Comment;
import prj.jpa.springJPA.CommentRepository;

@DataJpaTest
public class CommentRepositoryTest {
    
    @Autowired
    CommentRepository commentRepository;

    @Test
    @Rollback(false)
    public void commentRepositoryTest(){
        Comment comment1 = new Comment();
        Comment comment2 = new Comment();
        Comment comment3 = new Comment();
        Comment comment4 = new Comment();
        
        comment1.setComment("테스트 코멘트1");
        comment2.setComment("테스트 코멘트2");
        comment3.setComment("테스트 코멘트3");
        comment4.setComment("테스트 코멘트4");

        commentRepository.save(comment1);
        commentRepository.save(comment2);
        commentRepository.save(comment3);
        commentRepository.save(comment4);

        Assertions.assertThat(commentRepository.count()).isEqualTo(4);
        System.out.println(commentRepository.count());

        Assertions.assertThat(commentRepository.findAll().size()).isEqualTo(4);
        List<Comment> comments = commentRepository.findAll();
        comments.forEach(System.out::println);
        
        Optional<Comment> byId = commentRepository.findById(100l);
        Assertions.assertThat(byId).isEmpty();
        Comment comment = byId.orElseThrow(IllegalArgumentException::new);
        
    }
}
