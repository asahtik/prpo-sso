package si.fri.prpo.govorilneure.dtos;

public class TerminDto {

    private String ura;
    private String datum;
    private int maxSt;
    private String lokacija;
    private int profesor_id;

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
