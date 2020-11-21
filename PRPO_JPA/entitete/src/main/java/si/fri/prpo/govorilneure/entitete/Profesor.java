package si.fri.prpo.govorilneure.entitete;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.util.List;

@Entity
@NamedQueries(value =
        {
                @NamedQuery(name = "Profesor.getAll", query = "SELECT p FROM Profesor p"),
                @NamedQuery(name = "Profesor.getByImePriimek", query = "SELECT p FROM Profesor p WHERE p.ime = :ime AND p.priimek = :priimek")
        })

public class Profesor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String ime;

    private String priimek;

    private String email;

    @JsonbTransient
    @OneToMany(mappedBy = "profesor", cascade = CascadeType.ALL)
    private List<Termin> termini;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Termin> getTermini() {
        return termini;
    }

    public void setTermini(List<Termin> termini) {
        this.termini = termini;
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

}
