package si.fri.prpo.govorilneure.entitete;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.util.List;

@Entity
@NamedQueries(value =
        {
                @NamedQuery(name = "Student.getAll", query = "SELECT s FROM Student s"),
                @NamedQuery(name = "Student.getByIme", query = "SELECT s FROM Student s WHERE s.ime = :ime"),
                @NamedQuery(name = "Student.getbyStizkazince", query = "SELECT s FROM Student s WHERE s.stizkaznice = :stizkaznice"),
                @NamedQuery(name = "Student.getAllStizkaznice", query = "SELECT s.stizkaznice FROM Student s")
        })

public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String ime;
    @Column
    private String priimek;
    @Column
    private String email;
    @Column
    private int stizkaznice;

    @JsonbTransient
    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Prijava> prijave;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPriimek() {
        return priimek;
    }

    public void setPriimek(String priimek) {
        this.priimek = priimek;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public int getStizkaznice() {
        return stizkaznice;
    }

    public void setStizkaznice(Integer stizkaznice) {
        this.stizkaznice = stizkaznice;
    }

    public List<Prijava> getPrijave() {
        return prijave;
    }

    public void setPrijave(List<Prijava> prijave) {
        this.prijave = prijave;
    }

    public Student() {
    }

    public Student(Integer id, String ime, String priimek, String email, int stizkaznice) {
        this.id = id;
        this.ime = ime;
        this.priimek = priimek;
        this.email = email;
        this.stizkaznice = stizkaznice;
    }
}
