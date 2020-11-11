package si.fri.prpo.govorilneure.entitete;
import javax.persistence.*;
import java.util.List;

@Entity
@NamedQueries(value =
        {
                @NamedQuery(name = "Termini.getAll", query = "SELECT t FROM Termini t"),
                @NamedQuery(name = "Termini.getByUra", query = "SELECT t FROM Termini t WHERE t.ura = :ura"),
                @NamedQuery(name = "Termini.getByDatum", query = "SELECT t FROM Termini t WHERE t.datum = :datum"),
                @NamedQuery(name = "Termini.getAllLocation", query = "SELECT t.location FROM Termini t")
        })
public class Termini {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String datum;
    @Column
    private String ura;
    @Column
    private int maxSt;
    @Column
    private String location;

   @ManyToOne
   @JoinColumn(name = "profesor_id")
   private Profesor profesor;

   @OneToMany(mappedBy = "termin")
    private List<Prijava> prijave;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    public String getUra() {
        return ura;
    }

    public void setUra(String ura) {
        this.ura = ura;
    }

    public int getMaxSt() {
        return maxSt;
    }

    public void setMaxSt(int maxSt) {
        this.maxSt = maxSt;
    }

    public List<Prijava> getPrijave() {
        return prijave;
    }

    public void setPrijave(List<Prijava> prijave) {
        this.prijave = prijave;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }


}

