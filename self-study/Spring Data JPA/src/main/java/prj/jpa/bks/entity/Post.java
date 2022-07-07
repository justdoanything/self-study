package prj.jpa.bks.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Post {
    @Id @GeneratedValue
    private Long id;
    private String title;
    @OneToMany(mappedBy = "post", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Set<Comment> comments = new HashSet<>();

    public void addComment(Comment comment) {
        this.comments.add(comment);
        comment.setPost(this);
    }

    @Override
    public String toString() {
        return "{\"id\":\"" + this.id + "\", \"title\":" + this.title + "\"}";
    }
}
