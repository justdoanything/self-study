package prj.jpa.bks.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Account {

    @Id @GeneratedValue
    private Long id;
    private String username;
    private String password;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name="street", column = @Column(name="homeStreet")),
        @AttributeOverride(name="city", column = @Column(name="homeCity")),
        @AttributeOverride(name="state", column = @Column(name="homeState")),
        @AttributeOverride(name="zipCode", column = @Column(name="homeZipCode"))

    })
    private Address homeAddress;

    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name="street", column = @Column(name="officeStreet")),
        @AttributeOverride(name="city", column = @Column(name="officeCity")),
        @AttributeOverride(name="state", column = @Column(name="officeState")),
        @AttributeOverride(name="zipCode", column = @Column(name="officeZipCode"))

    })
    private Address officeAddrss;

    @OneToMany(mappedBy = "owner")
    private Set<Study> studies = new HashSet<>();

    public void addStudy(Study study) {
        this.studies.add(study);
        study.setOwner(this);
    }

    public void deleteStudy(Study study) {
        this.studies.remove(study);
        study.setOwner(null);     
    }

}
