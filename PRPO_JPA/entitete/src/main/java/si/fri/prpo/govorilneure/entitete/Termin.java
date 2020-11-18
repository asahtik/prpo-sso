package si.fri.prpo.govorilneure.entitete;
import javax.persistence.*;
import java.util.List;

@Entity
@NamedQueries(value =
        {
                @NamedQuery(name = "Termin.getAll", query = "SELECT t FROM Termin t"),
                @NamedQuery(name = "Termin.getById", query = "SELECT t FROM Termin t WHERE t.id = :id"),
                @NamedQuery(name = "Termin.getByUra", query = "SELECT t FROM Termin t WHERE t.ura = :ura"),
                @NamedQuery(name = "Termin.getByDatum", query = "SELECT t FROM Termin t WHERE t.datum = :datum"),
                @NamedQuery(name = "Termin.getAllLocations", query = "SELECT t.location FROM Termin t")
        })
public class Termin {
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

    @OneToMany(mappedBy = "termin", cascade = CascadeType.ALL)
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

    @Override
    public String toString() {
        return "Termin{" +
                "id=" + id +
                ", datum='" + datum + '\'' +
                ", ura='" + ura + '\'' +
                ", maxSt=" + maxSt +
                ", location='" + location + '\'' +
                ", profesor=" + profesor +
                ", prijave=" + prijave +
                '}';
    }
}

