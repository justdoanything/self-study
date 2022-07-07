package prj.test.bks;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.lang.Nullable;
import org.springframework.test.annotation.Rollback;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import prj.jpa.bks.entity.Comment;
import prj.jpa.bks.repository.CommentRepository;

@DataJpaTest
public class CommentRepositoryTest {
    
    @Autowired
    CommentRepository commentRepository;

    @Test
    @Rollback(false)
    public void commentRepositoryTest() throws Exception {
        this.createComment("테스트 코멘트1");
        this.createComment("테스트 코멘트2");
        this.createComment("테스트 코멘트3");
        this.createComment("테스트 코멘트4");

        // Assertions.assertThat(commentRepository.count()).isEqualTo(4);
        // System.out.println(commentRepository.count());

        // Assertions.assertThat(commentRepository.findAll().size()).isEqualTo(4);
        // List<Comment> comments = commentRepository.findAll();
        // comments.forEach(System.out::println);
        
        // Optional<Comment> byId = commentRepository.findById(100l);
        // Assertions.assertThat(byId).isEmpty();
        // Comment comment = byId.orElseThrow(IllegalArgumentException::new);
        
        // Future<List<Comment>> future = commentRepository.findByCommentContains("테스트");
        // System.out.println("===============");
        // System.out.println("is done?" + future.isDone());
        
        // List<Comment> result = future.get();  // Blocking 됨.
        // result.forEach(System.out::println);

        /* 비동기 방식을 추천하지 않는 이유 */
        // 위에서 insert한 결과는 실제 database에 영향을 미치지 않기 때문에 select 되는 결과가 없다.
        ListenableFuture<List<Comment>> listenFuture = commentRepository.findByComment("테스트 코멘트1");
        listenFuture.addCallback(new ListenableFutureCallback<List<Comment>>() {
            @Override
            public void onFailure(Throwable ex){
                System.out.println(ex);
            }
            @Override
            public void onSuccess(@Nullable List<Comment> result) {
                System.out.println("============");
                result.forEach(System.out::println);
            }
        });
    }

    private void createComment(String commentString){
        Comment comment = new Comment();
        comment.setComment(commentString);
        commentRepository.save(comment);
    }
}
