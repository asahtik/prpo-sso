package si.fri.prpo.govorilneure.dtos;

public class KanalDto {

    private int lokacija, termin;

    public KanalDto(int lokacija, int termin) {
        this.lokacija = lokacija;
        this.termin = termin;
    }

    public int getLokacija() {
        return lokacija;
    }

    public void setLokacija(int lokacija) {
        this.lokacija = lokacija;
    }

    public int getTermin() {
        return termin;
    }

    public void setTermin(int termin) {
        this.termin = termin;
    }
}
