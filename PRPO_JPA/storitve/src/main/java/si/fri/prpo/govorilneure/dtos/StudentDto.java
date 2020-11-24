package si.fri.prpo.govorilneure.dtos;

public class StudentDto {
    private int id;
    private String ime;
    private String priimek;
    private String email;
    private int stIzkaznice;

    public StudentDto(int id, String ime, String priimek, String email, int stIzkaznice) {
        this.id = id;
        this.ime = ime;
        this.priimek = priimek;
        this.email = email;
        this.stIzkaznice = stIzkaznice;
    }

    public StudentDto(String ime, String priimek, String email, int stIzkaznice) {
        this.ime = ime;
        this.priimek = priimek;
        this.email = email;
        this.stIzkaznice = stIzkaznice;
    }

    public StudentDto(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPriimek() {
        return priimek;
    }

    public void setPriimek(String priimek) {
        this.priimek = priimek;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getStIzkaznice() {
        return stIzkaznice;
    }

    public void setStIzkaznice(int stIzkaznice) {
        this.stIzkaznice = stIzkaznice;
    }
}
