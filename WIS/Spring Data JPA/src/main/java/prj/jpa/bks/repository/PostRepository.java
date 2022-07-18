package prj.jpa.bks.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import prj.jpa.bks.entity.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
    
    // Customize Function 사용 가능
    Page<Post> findByTitleContains(String title, PageRequest pageRequest);
    long countByTitleContains(String title);
}

