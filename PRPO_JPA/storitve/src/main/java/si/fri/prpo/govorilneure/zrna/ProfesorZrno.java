package si.fri.prpo.govorilneure.zrna;

import si.fri.prpo.govorilneure.entitete.Profesor;
import si.fri.prpo.govorilneure.entitete.Termin;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@ApplicationScoped
public class ProfesorZrno {

    private static final Logger log = Logger.getLogger(ProfesorZrno.class.getName());

    UUID uuid = UUID.randomUUID(); // For demonstrational purposes

    @PostConstruct
    private void postConstruct() {
        log.info("Created "+ProfesorZrno.class.getName()+" instance with UUID "+ uuid.toString());
    }
    @PreDestroy
    private void preDestroy() {
        log.info("Destroyed "+ProfesorZrno.class.getName()+" instance!");
    }

    @PersistenceContext(unitName = "sledilnik-sestankov-jpa")
    private EntityManager em;

    // GET
    public List<Profesor> getAll() {
        return (List<Profesor>)em.createNamedQuery("Profesor.getAll").getResultList();
    }

    public List<Profesor> getByImePriimek(String ime, String priimek) {
        Query q = em.createNamedQuery("Profesor.getByImePriimek");
        q.setParameter("ime", ime); q.setParameter("priimek", priimek);
        return (List<Profesor>)q.getResultList();
    }

    public Profesor getById(int id) {
        return em.find(Profesor.class, id);
    }

    // POST
    public Profesor add(Profesor p) {
        if(p != null)
            em.persist(p);
        return p;
    }

    // PUT
    public Profesor update(int idProfesorja, Profesor p) {
        Profesor update = getById(idProfesorja);
        if(update == null) return null;
        p.setId(update.getId());
        em.merge(p);
        return p;
    }

    // DELETE
    public boolean remove(int idProfesorja) {
        Profesor rm = getById(idProfesorja);
        if(rm != null) {
            em.remove(rm);
            return true;
        }
        return false;
    }
}
