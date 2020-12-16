package si.fri.prpo.govorilneure.dtos;

public class TerminDto {

    private int id;
    private long time;
    private int maxSt;
    private int lokacija = 0;
    private int profesor_id;

    public TerminDto(int id, long time, int maxSt, int lokacija, int profesor_id) {
        this.id = id;
        this.time = time;
        this.maxSt = maxSt;
        this.lokacija = lokacija;
        this.profesor_id = profesor_id;
    }

    public TerminDto(long time, int maxSt, int lokacija, int profesor_id) {
        this.time = time;
        this.maxSt = maxSt;
        this.lokacija = lokacija;
        this.profesor_id = profesor_id;
    }

    public TerminDto(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getMaxSt() {
        return maxSt;
    }

    public void setMaxSt(int maxSt) {
        this.maxSt = maxSt;
    }

    public int getLokacija() {
        return lokacija;
    }

    public void setLokacija(int lokacija) {
        this.lokacija = lokacija;
    }

    public int getProfesor_id() {
        return profesor_id;
    }

    public void setProfesor_id(int profesor_id) {
        this.profesor_id = profesor_id;
    }
}
