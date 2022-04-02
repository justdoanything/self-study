package prj.jpa.springJPA;

import org.springframework.data.jpa.repository.JpaRepository;

import prj.jpa.basic.Post;

public interface PostRepository extends JpaRepository<Post, Long> {
    
}

