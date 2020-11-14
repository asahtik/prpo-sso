package si.fri.prpo.govorilneure.zrna;

import si.fri.prpo.govorilneure.dtos.ProfesorDto;
import si.fri.prpo.govorilneure.dtos.TerminDto;
import si.fri.prpo.govorilneure.entitete.Profesor;
import si.fri.prpo.govorilneure.entitete.Termin;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ApplicationScoped
public class UpravljanjeSestankovZrno {

    private static final Logger log = Logger.getLogger(UpravljanjeSestankovZrno.class.getName());

    @PostConstruct
    private void postConstruct() {
        log.info("Created "+UpravljanjeSestankovZrno.class.getName()+" instance!");
    }
    @PreDestroy
    private void preDestroy() {
        log.info("Destroyed "+UpravljanjeSestankovZrno.class.getName()+" instance!");
    }


    @Inject
    private ProfesorZrno prof;
    @Inject
    private TerminZrno term;

    @Transactional
    public Profesor dodajProfesorja(ProfesorDto profdto) {
        // Ime in priimek obvezna
        if(profdto.getIme() == null || profdto.getIme() == "" || profdto.getPriimek() == null || profdto.getPriimek() == "") {
            log.warning("Ne morem ustvariti profesorja brez imena in priimka!");
            return null;
        }
        Pattern pattern = Pattern.compile("^.+@.+\\..+$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(profdto.getEmail());
        if(!matcher.find()) profdto.setEmail(null);

        Profesor p = new Profesor();
        p.setIme(profdto.getIme());
        p.setPriimek(profdto.getPriimek());
        p.setEmail(profdto.getEmail());
        return prof.add(p);
    }

    @Transactional
    public Termin dodajTermin(TerminDto termdto) {
        // Ura obvezna (hh:mm), datum obvezen (yyyy-mm-dd), veljaven profesor id obvezen
        Pattern pattern;
        Matcher matcher;
        pattern = Pattern.compile("^[0-9]{2}(/|:|-)[0-9]{2}$", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(termdto.getUra());
        if(!matcher.find()) {
            log.warning("Ura termina je v neveljavnem formatu!");
            return null;
        }
        pattern = Pattern.compile("^[0-9]{4}(/|:|-|\\.)[0-9]{2}(/|:|-)[0-9]{2}$", Pattern.CASE_INSENSITIVE);
        matcher = pattern.matcher(termdto.getUra());
        if(!matcher.find()) {
            log.warning("Datum termina je v neveljavnem formatu!");
            return null;
        }
        Profesor p;
        if((p = prof.getById(termdto.getProfesor_id())) == null) {
            log.warning("Id profesorja neveljaven!");
            return null;
        }
        Termin t = new Termin();
        t.setUra(termdto.getUra());
        t.setDatum(termdto.getDatum());
        t.setMaxSt(termdto.getMaxSt());
        t.setLocation(termdto.getLokacija());
        t.setProfesor(p);
        t = term.add(t);
        p.getTermin().add(t);
        return t;
    }
}
