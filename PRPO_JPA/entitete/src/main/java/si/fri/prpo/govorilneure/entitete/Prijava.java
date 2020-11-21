package si.fri.prpo.govorilneure.entitete;

import si.fri.prpo.govorilneure.pretvorniki.TStoString;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@NamedQueries(value =
        {
                @NamedQuery(name = "Prijava.getAll", query = "SELECT p FROM Prijava p"),
                @NamedQuery(name = "Prijava.getByEmail", query = "SELECT p FROM Prijava p WHERE p.email = :email"),
                @NamedQuery(name = "Prijava.getAllEmail", query = "SELECT p.email FROM Prijava p")
        })

public class Prijava {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private Timestamp timestamp;
    @Column
    private Boolean potrjena = false;
    @Column
    private String email;

    @ManyToOne
    @JoinColumn(name = "termin_id")
    private Termin termin;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    public Termin getTermin() {
        return termin;
    }

    public void setTermin(Termin termin) {
        this.termin = termin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getPotrjena() {
        return potrjena;
    }

    public void setPotrjena(Boolean potrjena) {
        this.potrjena = potrjena;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    public String toString() {
        return "Prijava {" +
                "id=" + id +
                ", ura in datum="+ TStoString.TStoStr(timestamp) +
                ", potrjena=" + potrjena +
                ", termin='" + termin.getId() + '\'' +
                ", student=" + student.getId() +
                '}';
    }
}
