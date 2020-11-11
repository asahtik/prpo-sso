package si.fri.prpo.govorilneure.servlets;
import si.fri.prpo.govorilneure.entitete.Student;
import si.fri.prpo.govorilneure.zrna.StudentZrno;

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

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse res) throws IOException {
        PrintWriter w=res.getWriter();
        List<Student> studenti = studentZrno.getStudent();
        w.println("Students:");
        for (Student student : studenti){
            w.println(student.toString());
        }
    }
}



