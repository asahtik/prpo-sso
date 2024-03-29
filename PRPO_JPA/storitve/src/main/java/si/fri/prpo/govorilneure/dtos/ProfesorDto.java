package si.fri.prpo.govorilneure.dtos;

public class ProfesorDto {

    private Integer id;
    private String ime;
    private String priimek;
    private String email;

    public ProfesorDto(Integer id, String ime, String priimek, String email) {
        this.id = id;
        this.ime = ime;
        this.priimek = priimek;
        this.email = email;
    }

    public ProfesorDto(String ime, String priimek, String email) {
        this.ime = ime;
        this.priimek = priimek;
        this.email = email;
    }

    public ProfesorDto(Integer id) {
        this.id = id;
    }

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

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
}
