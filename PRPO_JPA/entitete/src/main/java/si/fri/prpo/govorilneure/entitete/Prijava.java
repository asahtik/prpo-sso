package si.fri.prpo.govorilneure.entitete;

import javax.persistence.*;
import java.util.List;

@Entity
@NamedQueries(value =
        {
                @NamedQuery(name = "Prijava.getAll", query = "SELECT p FROM Prijava p"),
                @NamedQuery(name = "Prijava.getByDatum", query = "SELECT p FROM Prijava p WHERE p.datum = :datum"),
                @NamedQuery(name = "Prijava.getByEmail", query = "SELECT p FROM Prijava p WHERE p.email = :email"),
                @NamedQuery(name = "Prijava.getAllEmail", query = "SELECT p.email FROM Prijava p")
        })

public class Prijava {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String datum;
    @Column
    private Boolean potrjena;
    @Column
    private String email;

    @ManyToOne
    @JoinColumn(name = "termini_id")
    private Termini termin;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    public Termini getTermin() {
        return termin;
    }

    public void setTermin(Termini termin) {
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

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
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
}
