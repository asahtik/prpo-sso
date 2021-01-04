package si.fri.prpo.govorilneure.zrna;

import com.kumuluz.ee.configuration.utils.ConfigurationUtil;
import si.fri.prpo.govorilneure.anotacije.BeleziKlice;
import si.fri.prpo.govorilneure.dtos.*;
import si.fri.prpo.govorilneure.entitete.Prijava;
import si.fri.prpo.govorilneure.entitete.Profesor;
import si.fri.prpo.govorilneure.entitete.Student;
import si.fri.prpo.govorilneure.entitete.Termin;
import si.fri.prpo.govorilneure.odjemalci.CheckEmailOdjemalec;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ApplicationScoped
@BeleziKlice
public class UpravljanjeSestankovZrno {

    private Client httpClient;
    private String baseUrl;

    private static final Logger log = Logger.getLogger(UpravljanjeSestankovZrno.class.getName());

    @PostConstruct
    private void postConstruct() {
        log.info("Created "+UpravljanjeSestankovZrno.class.getName()+" instance!");

        httpClient = ClientBuilder.newClient();
        baseUrl = ConfigurationUtil.getInstance()
                .get("integrations.kanali.base-url")
                .orElse("http://localhost:8081/v1");

    }
    @PreDestroy
    private void preDestroy() {
        log.info("Destroyed "+UpravljanjeSestankovZrno.class.getName()+" instance!");
    }


    @Inject
    private ProfesorZrno prof;
    @Inject
    private TerminZrno term;
    @Inject
    private StudentZrno stud;
    @Inject
    private PrijavaZrno prij;



    public ProfesorDto dodajProfesorja(ProfesorDto profdto) {
        profdto.setIme(profdto.getIme().trim());
        profdto.setPriimek(profdto.getPriimek().trim());

        if(profdto.getIme() == null || profdto.getIme() == "" || profdto.getPriimek() == null || profdto.getPriimek() == "") {
            log.warning("Ne morem ustvariti profesorja brez imena in priimka!");
            return null;
        }
        if(profdto.getEmail() != null) {
            if(!CheckEmailOdjemalec.ustrezenEmail(profdto.getEmail())) return null;
        }

        Profesor p = new Profesor();
        p.setIme(profdto.getIme());
        p.setPriimek(profdto.getPriimek());
        p.setEmail(profdto.getEmail());
        prof.add(p);
        return new ProfesorDto(p.getId(), p.getIme(), p.getPriimek(), p.getEmail());
    }

    public StudentDto dodajStudenta(StudentDto studdto) {
        // Ime in priimek obvezna
        studdto.setIme(studdto.getIme().trim());
        studdto.setPriimek(studdto.getPriimek().trim());

        if(studdto.getIme() == null || studdto.getIme() == "" || studdto.getPriimek() == null || studdto.getPriimek() == "") {
            log.warning("Ne morem ustvariti studenta brez imena in priimka!");
            return null;
        }
        if((int)(Math.log10(studdto.getStIzkaznice())+1)!=8) {
            log.warning("Ne morem ustvariti studenta brez ustrezne stevilke izkaznice!");
            return null;
        }
        if(studdto.getEmail() != null) {
            if(!CheckEmailOdjemalec.ustrezenEmail(studdto.getEmail())) return null;
        }

        Student s = new Student();
        s.setIme(studdto.getIme());
        s.setPriimek(studdto.getPriimek());
        s.setEmail(studdto.getEmail());
        stud.add(s);
        return new StudentDto(s.getId(), s.getIme(), s.getPriimek(), s.getEmail(), s.getStizkaznice());
    }

    public TerminDto dodajTermin(TerminDto termdto) {
        Profesor p;
        if((p = prof.getById(termdto.getProfesor_id())) == null) {
            log.warning("Id profesorja neveljaven!");
            return null;
        }
        Termin t = new Termin();
        t.setTimestamp(termdto.getTime());
        t.setMaxSt(termdto.getMaxSt());
        t.setLocation(termdto.getLokacija());
        t.setProfesor(p);
        t = term.add(t);
        p.getTermini().add(t);


        pokliciKanal(t.getLocation(), t.getId());

        return new TerminDto(t.getId(), t.getTimestamp(), t.getMaxSt(), t.getLocation(), t.getProfesor().getId());
    }

    public PrijavaDto dodajPrijavo(PrijavaDto prijDto){

        //dto prijDto je za studenta in prijavo
        Student student;
        Termin termin;
        if((student = stud.getById(prijDto.getStudentId())) == null) {
            log.warning("Id studenta neveljaven!");
            return null;
        }
        if((termin = term.getById(prijDto.getTerminId())) == null) {
            log.warning("Id termina neveljaven!");
            return null;
        }

        //potrjena ne nastavim ker je ze v Prijava nastavljen na false
        Prijava prijava = new Prijava();
        prijava.setStudent(student);
        prijava.setTermin(termin);
        prijava.setTimestamp(prijDto.getTime());
        prijava.setEmail(prijDto.getEmail());
        prijava = prij.add(prijava);
        student.getPrijave().add(prijava);
        termin.getPrijave().add(prijava);
        return new PrijavaDto(prijava.getId(), prijava.getTimestamp(), false, prijava.getEmail(), prijava.getStudent().getId(), prijava.getTermin().getId());
    }

    public PrijavaDto potrdiPrijavo(PrijavaDto prijDto){
        //ali prijava obstaja
        Prijava prijava = prij.getById(prijDto.getId());

        if(prijava == null){
            log.info("Id prijave neveljaven!");
            return null;
        } else if(prijava.getPotrjena() == true){
            log.info("Prijava je ze potrjena!");
            //return null;
        } else {
            Termin t = prijava.getTermin();
            if (t == null) {
                log.warning("Napaka v podatkovni bazi (prijava nima termina)!");
                return null;
            }
            else {
                List<Prijava> prijaveTermina = t.getPrijave();
                int st = 0;
                for(Prijava i:prijaveTermina) {
                    if(i.getPotrjena()) st++;
                    if(st >= t.getMaxSt()) break;
                }
                if(st < t.getMaxSt()) prijava.setPotrjena(true);
                else {
                    log.info("Maksimalno stevilo udelezencev v terminu je dosezeno!");
                    return null;
                }
            }
        }
        prijava = prij.update(prijava.getId(), prijava);
        return new PrijavaDto(prijava.getId(), prijava.getTimestamp(), prijava.getPotrjena(), prijava.getEmail(), prijava.getStudent().getId(), prijava.getTermin().getId());
    }

    private void pokliciKanal(int lokacija, int id){
        try {
            httpClient
                    .target(baseUrl + "/kanali/" + String.valueOf(lokacija))
                    .request(MediaType.APPLICATION_JSON)
                    .put(Entity.json(new KanalDto(lokacija, id)));
        } catch (WebApplicationException | ProcessingException e) {
                log.severe(e.getMessage());
                throw new InternalServerErrorException(e);
        }
    }
}
