package si.fri.prpo.vaje.sso.servlets;

import si.fri.prpo.vaje.sso.jdbc.BaseDao;
import si.fri.prpo.vaje.sso.jdbc.Entiteta;
import si.fri.prpo.vaje.sso.jdbc.Uporabnik;
import si.fri.prpo.vaje.sso.jdbc.UporabnikDaoImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/servlet") //kje poslusa
public class FirstServlet extends HttpServlet {
    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse res) throws IOException {
        PrintWriter w=res.getWriter();

        // Connect to database
        BaseDao uDao = UporabnikDaoImpl.getInstance();

        //Add new table entries
        Uporabnik u=new Uporabnik("McDonald", "Trump", "DonnyT");
        w.println("Dodajam uporabnika: " + u.toString());
        uDao.vstavi(u);
        u=new Uporabnik("Stefan", "Skellen", "TawnyOwl");
        w.println("Dodajam uporabnika: " + u.toString());
        uDao.vstavi(u);
        u=new Uporabnik("Borat", "Sagdijev", "IsNice");
        w.println("Dodajam uporabnika: " + u.toString());
        uDao.vstavi(u);

        w.println("Vracam uporabnika z id=2: "+ uDao.vrni(2).toString());

        w.println("Vracam vse uporabnike v tabeli:");
        List<Entiteta> l = uDao.vrniVse();
        for(Entiteta ent : l) w.println("\t- " + ent.toString());
        w.println("Brisem uporabnika z id=3.");
        uDao.odstrani(3);
        w.println("Vracam vse uporabnike v tabeli:");
        l = uDao.vrniVse();
        for(Entiteta ent : l) w.println("\t- " + ent.toString());

        w.println("Vracam uporabnika z id=1: "+ uDao.vrni(1).toString());
        w.println("Posodabljam vnos z id=1.");
        u=new Uporabnik("Joseph", "Biden", "joe");
        u.setId(1);
        uDao.posodobi(u);
        w.println("Vracam uporabnika z id=1: "+ uDao.vrni(1).toString());
    }
}


