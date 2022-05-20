package prj.jpa.basic;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Study {
    @Id @GeneratedValue
    private Long id;
    private String userName;
    private String userPassword;

    @ManyToOne
    private Account owner;
}
