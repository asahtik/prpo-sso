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
        profdto.setIme(profdto.getIme().trim());
        profdto.setPriimek(profdto.getPriimek().trim());

        if(profdto.getIme() == null || profdto.getIme() == "" || profdto.getPriimek() == null || profdto.getPriimek() == "") {
            log.warning("Ne morem ustvariti profesorja brez imena in priimka!");
            return null;
        }
        if(profdto.getEmail() != null) {
            Pattern pattern = Pattern.compile("^.+@.+\\..+$", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(profdto.getEmail());
            if (!matcher.find()) profdto.setEmail(null);
        }

        Profesor p = new Profesor();
        p.setIme(profdto.getIme());
        p.setPriimek(profdto.getPriimek());
        p.setEmail(profdto.getEmail());
        return prof.add(p);
    }

    @Transactional
    public Profesor posodobiProfesorja(ProfesorDto profdto) {
        // id obvezen
        profdto.setIme(profdto.getIme().trim());
        profdto.setPriimek(profdto.getPriimek().trim());
        if(profdto.getIme() == "") profdto.setIme(null);
        if(profdto.getPriimek() == "") profdto.setPriimek(null);

        if(profdto.getEmail() != null) {
            Pattern pattern = Pattern.compile("^.+@.+\\..+$", Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(profdto.getEmail());
            if (!matcher.find()) profdto.setEmail(null);
        }

        Profesor old = prof.getById(profdto.getId());
        if(old == null) {
            log.warning("Podosobi: Profesor z id = "+profdto.getId()+" ne obstaja!");
            return null;
        }

        Profesor n = new Profesor();
        if(n.getIme() == null) n.setIme(old.getIme());
        if(n.getPriimek() == null) n.setPriimek(old.getPriimek());
        if(n.getEmail() == null) n.setEmail(old.getEmail());
        n.setTermini(old.getTermini());
        return prof.update(profdto.getId(), n);
    }

    @Transactional
    public boolean odstraniProfesorja(ProfesorDto profdto) {
        if(!prof.remove(profdto.getId())) {
            log.warning("Odstrani: Profesor z id = "+profdto.getId()+" ne obstaja!");
            return false;
        }
        return true;
    }

    @Transactional
    public Termin dodajTermin(TerminDto termdto) {
        // Ura obvezna (hh:mm), datum obvezen (yyyy-mm-dd), veljaven profesor id obvezen
        Pattern pattern;
        Matcher matcher;
        pattern = Pattern.compile("^[0-9]{2}(/|:|-)[0-9]{2}$");
        matcher = pattern.matcher(termdto.getUra());
        if(!matcher.find()) {
            log.warning("Ura termina je v neveljavnem formatu!");
            return null;
        }
        pattern = Pattern.compile("^[0-9]{4}(/|:|-|\\.)[0-9]{2}(/|:|-)[0-9]{2}$");
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
        p.getTermini().add(t);
        return t;
    }

    @Transactional
    public Termin posodobiTermin(TerminDto termdto) {
        // id obvezen
        termdto.setUra(termdto.getUra().trim());
        termdto.setDatum(termdto.getDatum().trim());
        termdto.setLokacija(termdto.getLokacija().trim());
        if(termdto.getUra() == "") termdto.setUra(null);
        if(termdto.getDatum() == "") termdto.setDatum(null);
        if(termdto.getLokacija() == "") termdto.setLokacija(null);

        Pattern pattern;
        Matcher matcher;
        if(termdto.getUra() != null) {
            pattern = Pattern.compile("^[0-9]{2}(/|:|-)[0-9]{2}$");
            matcher = pattern.matcher(termdto.getUra());
            if(!matcher.find()) termdto.setUra(null);
        }
        if(termdto.getDatum() != null) {
            pattern = Pattern.compile("^[0-9]{2}(/|:|-)[0-9]{2}$");
            matcher = pattern.matcher(termdto.getDatum());
            if(!matcher.find()) termdto.setDatum(null);
        }

        Termin old = term.getById(termdto.getId());
        if(old == null) {
            log.warning("Podosobi: Termin z id = "+termdto.getId()+" ne obstaja!");
            return null;
        }

        Termin n = new Termin();
        if(n.getUra() == null) n.setUra(old.getUra());
        if(n.getDatum() == null) n.setDatum(old.getDatum());
        if(n.getLocation() == null) n.setLocation(old.getLocation());
        return term.update(termdto.getId(), n);
    }

    @Transactional
    public boolean odstraniTermin(TerminDto termdto) {
        if(!term.remove(termdto.getId())) {
            log.warning("Odstrani: Termin z id = "+termdto.getId()+" ne obstaja!");
            return false;
        }
        return true;
    }
}
