package si.fri.prpo.govorilneure.entitete;
import si.fri.prpo.govorilneure.pretvorniki.TStoString;

import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Entity
@NamedQueries(value =
        {
                @NamedQuery(name = "Termin.getAll", query = "SELECT t FROM Termin t"),
                @NamedQuery(name = "Termin.getById", query = "SELECT t FROM Termin t WHERE t.id = :id"),
                @NamedQuery(name = "Termin.getAllLocations", query = "SELECT t.location FROM Termin t")
        })
public class Termin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private long timestamp;
    @Column
    private int maxSt;
    @Column
    private String location;

    @ManyToOne
    @JoinColumn(name = "profesor_id")
    private Profesor profesor;

    @JsonbTransient
    @OneToMany(mappedBy = "termin", cascade = CascadeType.ALL)
    private List<Prijava> prijave;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
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
                ", ura in datum="+ TStoString.TStoStr(timestamp) +
                ", maxSt=" + maxSt +
                ", location='" + location + '\'' +
                ", profesor=" + profesor +
                ", prijave=" + prijave +
                '}';
    }
}

