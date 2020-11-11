package si.fri.prpo.govorilneure.zrna;
import si.fri.prpo.govorilneure.entitete.Student;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@ApplicationScoped
public class StudentZrno {

    @PersistenceContext(unitName = "sledilnik-sestankov-jpa")
    private EntityManager em;

    public List<Student> getStudent() {

        Query q = em.createNamedQuery("Student.getAll");
        List<Student> studenti = (List<Student>)q.getResultList();
        return studenti;

    }
}
