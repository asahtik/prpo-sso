package si.fri.prpo.govorilneure.zrna;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import si.fri.prpo.govorilneure.anotacije.BeleziKlice;
import si.fri.prpo.govorilneure.entitete.Termin;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@ApplicationScoped
@BeleziKlice
public class TerminZrno {

    private static final Logger log = Logger.getLogger(TerminZrno.class.getName());

    UUID uuid = UUID.randomUUID(); // For demonstrational purposes

    @PostConstruct
    private void postConstruct() {
        log.info("Created "+TerminZrno.class.getName()+" instance with UUID "+uuid.toString());
    }
    @PreDestroy
    private void preDestroy() {
        log.info("Destroyed "+TerminZrno.class.getName()+" instance!");
    }

    @PersistenceContext(unitName = "sledilnik-sestankov-jpa")
    private EntityManager em;

    // GET
    public List<Termin> getAll() {
        return (List<Termin>)em.createNamedQuery("Termin.getAll").getResultList();
    }

    public List<Termin> getAll(QueryParameters query) {
        return (List<Termin>) JPAUtils.queryEntities(em, Termin.class, query);
    }

    public long getAllCount(QueryParameters query) {
        return JPAUtils.queryEntitiesCount(em, Termin.class, query);
    }

    public List<Termin> getAllLocations() {
        return (List<Termin>)em.createNamedQuery("Termin.getAllLocations").getResultList();
    }

    public Termin getById(int id) {
        return (Termin)em.find(Termin.class, id);
    }

    // POST
    @Transactional
    public Termin add(Termin t) {
        if(t != null)
            em.persist(t);
        return t;
    }

    // PUT
    @Transactional
    public Termin update(int idTermina, Termin t) {
        Termin update = getById(idTermina);
        if(update == null) return null;
        t.setId(update.getId());
        em.merge(t);
        return t;
    }

    // DELETE
    @Transactional
    public boolean remove(int terminId) {
        Termin rm = getById(terminId);
        if(rm != null) {
            em.remove(rm);
            return true;
        }
        return false;
    }
}
