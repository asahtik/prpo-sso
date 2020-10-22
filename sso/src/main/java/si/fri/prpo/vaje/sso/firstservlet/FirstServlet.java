package si.fri.prpo.vaje.sso.firstservlet;

import com.kumuluz.ee.configuration.utils.ConfigurationUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/servlet") //kje poslusa
public class FirstServlet extends HttpServlet {
    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse res) throws ServletException, IOException {
        System.out.println("dela");
        res.getWriter().println("Izpis na spletno stran");
        res.getWriter().println(ConfigurationUtil.getInstance().get("kumuluzee.name").get());
        res.getWriter().println(ConfigurationUtil.getInstance().get("kumuluzee.version").get());
        res.getWriter().println(ConfigurationUtil.getInstance().get("kumuluzee.env.name").get());
    }
}

