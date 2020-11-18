package si.fri.prpo.govorilneure.servlets;
import si.fri.prpo.govorilneure.dtos.PrijavaDto;
import si.fri.prpo.govorilneure.dtos.ProfesorDto;
import si.fri.prpo.govorilneure.dtos.StudentDto;
import si.fri.prpo.govorilneure.dtos.TerminDto;
import si.fri.prpo.govorilneure.entitete.Prijava;
import si.fri.prpo.govorilneure.entitete.Profesor;
import si.fri.prpo.govorilneure.entitete.Student;
import si.fri.prpo.govorilneure.entitete.Termin;
import si.fri.prpo.govorilneure.zrna.*;

import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;
        import javax.servlet.http.HttpServlet;
        import javax.servlet.http.HttpServletRequest;
        import javax.servlet.http.HttpServletResponse;
        import java.io.IOException;
        import java.io.PrintWriter;
        import java.util.List;

@WebServlet("/servlet") //kje poslusa
public class servlet extends HttpServlet {
    @Inject
    private StudentZrno studentZrno;
    @Inject
    private PrijavaZrno prijavaZrno;
    @Inject
    private ProfesorZrno profesorZrno;
    @Inject
    private TerminZrno terminZrno;
    @Inject
    private UpravljanjeSestankovZrno upravljanjeSestankovZrno;

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse res) throws IOException {
        PrintWriter w=res.getWriter();
        upravljanjeSestankovZrno.dodajProfesorja(new ProfesorDto("Albert", "Einstein", "MC2@gmail.com"));
        upravljanjeSestankovZrno.dodajProfesorja(new ProfesorDto("Senor", "Carlos", "SC@gmail.com"));
        upravljanjeSestankovZrno.dodajStudenta(new StudentDto("Jaka", "Adut", "don@don.com", 63180144));
        upravljanjeSestankovZrno.dodajStudenta(new StudentDto("Janez", "Bidet", "jan@gmail.com", 63180144));
        upravljanjeSestankovZrno.dodajTermin(new TerminDto("13:45","2020-12-11",6,"Ljubljana",1));
        upravljanjeSestankovZrno.dodajTermin(new TerminDto("14:45","2020-12-11",6,"Ljubljana",1));
        upravljanjeSestankovZrno.dodajTermin(new TerminDto("15:45","2020-12-12",6,"Ljubljana",2));
        upravljanjeSestankovZrno.dodajPrijavo(new PrijavaDto("2020-12-10", "teams@teams.com", 1, 1));
        upravljanjeSestankovZrno.dodajPrijavo(new PrijavaDto("2020-12-10", "teams@teams.com", 1, 2));
        upravljanjeSestankovZrno.dodajPrijavo(new PrijavaDto("2020-12-09", "teams2@teams2.com", 2, 3));
        List<Profesor> profesorji = profesorZrno.getAll();
        for(Profesor p:profesorji)
            w.println("Profesor: id: " + p.getId() + ", ime: " + p.getIme() + ", priimek: " + p.getPriimek());
        List<Student> studenti = studentZrno.getAll();
        for(Student p:studenti)
            w.println("Student: id: " + p.getId() + ", ime: " + p.getIme() + ", priimek: " + p.getPriimek());
        List<Termin> termini = terminZrno.getAll();
        for(Termin t:termini)
            w.println("Termin: id: " + t.getId() + ", datum: " + t.getDatum() + ", ura: " + t.getUra() + ", max st: " + t.getMaxSt() + ", prof id: " + t.getProfesor().getId());
        List<Prijava> prijave = prijavaZrno.getAll();
        for(Prijava t:prijave)
            w.println("Prijava: id: " + t.getId() + ", datum: " + t.getDatum() + ", potrjeno: " + t.getPotrjena() + ", student id: " + t.getStudent().getId() + ", termin id: " + t.getTermin().getId());
        w.println("Potrditev prijave z id = 2");
        Prijava potr = upravljanjeSestankovZrno.potrdiPrijavo(new PrijavaDto(2));
        w.println("Prijava: id: " + potr.getId() + ", datum: " + potr.getDatum() + ", potrjeno: " + potr.getPotrjena() + ", student id: " + potr.getStudent().getId() + ", termin id: " + potr.getTermin().getId());
    }
}



