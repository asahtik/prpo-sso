package si.fri.prpo.vaje.sso.jdbc;

public class Uporabnik extends Entiteta {
    private static final long serialVersionUID=1L;
    private String ime;
    private String priimek;
    private String uporabniskoIme;

    public Uporabnik(String ime, String priimek, String uporabniskoIme) {
        this.ime = ime;
        this.priimek = priimek;
        this.uporabniskoIme = uporabniskoIme;
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

    public String getUporabniskoIme() {
        return uporabniskoIme;
    }

    public void setUporabniskoIme(String uporabniskoIme) {
        this.uporabniskoIme = uporabniskoIme;
    }

    @Override
    public String toString() {
        return this.ime+", "+this.priimek+", uporabnisko ime: "+this.uporabniskoIme+".";
    }
}
