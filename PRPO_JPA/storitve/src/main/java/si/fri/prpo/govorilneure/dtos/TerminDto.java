package si.fri.prpo.govorilneure.dtos;

public class TerminDto {

    private int id;
    private String ura;
    private String datum;
    private int maxSt;
    private String lokacija;
    private int profesor_id;

    public TerminDto(int id, String ura, String datum, int maxSt, String lokacija, int profesor_id) {
        this.id = id;
        this.ura = ura;
        this.datum = datum;
        this.maxSt = maxSt;
        this.lokacija = lokacija;
        this.profesor_id = profesor_id;
    }

    public TerminDto(String ura, String datum, int maxSt, String lokacija, int profesor_id) {
        this.ura = ura;
        this.datum = datum;
        this.maxSt = maxSt;
        this.lokacija = lokacija;
        this.profesor_id = profesor_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUra() {
        return ura;
    }

    public void setUra(String ura) {
        this.ura = ura;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public int getMaxSt() {
        return maxSt;
    }

    public void setMaxSt(int maxSt) {
        this.maxSt = maxSt;
    }

    public String getLokacija() {
        return lokacija;
    }

    public void setLokacija(String lokacija) {
        this.lokacija = lokacija;
    }

    public int getProfesor_id() {
        return profesor_id;
    }

    public void setProfesor_id(int profesor_id) {
        this.profesor_id = profesor_id;
    }
}
