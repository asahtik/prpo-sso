package si.fri.prpo.govorilneure.zrna;
import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import si.fri.prpo.govorilneure.anotacije.BeleziKlice;
import si.fri.prpo.govorilneure.entitete.Profesor;
import si.fri.prpo.govorilneure.entitete.Student;
import si.fri.prpo.govorilneure.entitete.Termin;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@ApplicationScoped
@BeleziKlice
public class StudentZrno {

    private static final Logger log = Logger.getLogger(StudentZrno.class.getName());

    UUID uuid = UUID.randomUUID(); // For demonstrational purposes

    @PostConstruct
    private void postConstruct() {
        log.info("Created "+StudentZrno.class.getName()+" instance with UUID "+ uuid.toString());
    }
    @PreDestroy
    private void preDestroy() {
        log.info("Destroyed "+StudentZrno.class.getName()+" instance!");
    }

    @PersistenceContext(unitName = "sledilnik-sestankov-jpa")
    private EntityManager em;

    // GET
    public List<Student> getAll() {
        return (List<Student>)em.createNamedQuery("Student.getAll").getResultList();
    }

    public List<Student> getAll(QueryParameters query) {
        return (List<Student>) JPAUtils.queryEntities(em, Student.class, query);
    }

    public long getAllCount(QueryParameters query) {
        return JPAUtils.queryEntitiesCount(em, Student.class, query);
    }

    public Student getById(int id) {
        return em.find(Student.class, id);
    }

    // POST
    @Transactional
    public Student add(Student p) {
        if(p != null)
            em.persist(p);
        return p;
    }

    // PUT
    @Transactional
    public Student update(int idStudenta, Student p) {
        Student update = getById(idStudenta);
        if(update == null) return null;
        p.setId(update.getId());
        em.merge(p);
        return p;
    }

    // DELETE
    @Transactional
    public boolean remove(int idStudenta) {
        Student rm = getById(idStudenta);
        if(rm != null) {
            em.remove(rm);
            return true;
        }
        return false;
    }
}
