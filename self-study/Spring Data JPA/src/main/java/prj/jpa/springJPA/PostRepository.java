package prj.jpa.springJPA;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import prj.jpa.basic.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
    
    // Customize Function 사용 가능
    Page<Post> findByTitleContains(String title, PageRequest pageRequest);
    long countByTitleContains(String title);
}

