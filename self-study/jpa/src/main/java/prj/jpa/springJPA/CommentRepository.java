package prj.jpa.springJPA;

import java.util.List;
import java.util.concurrent.Future;

import org.springframework.scheduling.annotation.Async;
import org.springframework.util.concurrent.ListenableFuture;

import prj.jpa.basic.Comment;

public interface CommentRepository extends CustomRepository<Comment, Long> {

    @Async
    Future<List<Comment>> findByCommentContains(String keyword);
    
    @Async
    ListenableFuture<List<Comment>> findByComment(String keyword);
}
