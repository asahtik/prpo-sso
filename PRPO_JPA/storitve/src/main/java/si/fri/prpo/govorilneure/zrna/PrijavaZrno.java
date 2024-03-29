package si.fri.prpo.govorilneure.zrna;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.rest.utils.JPAUtils;
import si.fri.prpo.govorilneure.anotacije.BeleziKlice;
import si.fri.prpo.govorilneure.entitete.Prijava;

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
public class PrijavaZrno {

    private static final Logger log = Logger.getLogger(PrijavaZrno.class.getName());

    UUID uuid = UUID.randomUUID(); // For demonstrational purposes

    @PostConstruct
    private void postConstruct() {
        log.info("Created "+PrijavaZrno.class.getName()+" instance with UUID "+ uuid.toString());
    }
    @PreDestroy
    private void preDestroy() {
        log.info("Destroyed "+PrijavaZrno.class.getName()+" instance!");
    }

    @PersistenceContext(unitName = "sledilnik-sestankov-jpa")
    private EntityManager em;

    // GET
    public List<Prijava> getAll() {
        return (List<Prijava>)em.createNamedQuery("Prijava.getAll").getResultList();
    }

    public List<Prijava> getAll(QueryParameters query) {
        return (List<Prijava>) JPAUtils.queryEntities(em, Prijava.class, query);
    }

    public long getAllCount(QueryParameters query) {
        return JPAUtils.queryEntitiesCount(em, Prijava.class, query);
    }

    public Prijava getById(int id) {
        return em.find(Prijava.class, id);
    }

    // POST
    @Transactional
    public Prijava add(Prijava p) {
        if(p != null)
            em.persist(p);
        return p;
    }

    // PUT
    @Transactional
    public Prijava update(int idPrijava, Prijava p) {
        Prijava update = getById(idPrijava);
        if(update == null) return null;
        p.setId(update.getId());
        em.merge(p);
        return p;
    }

    // DELETE
    @Transactional
    public boolean delete(int idPrijava) {
        Prijava rm = getById(idPrijava);
        if(rm != null) {
            em.remove(rm);
            return true;
        }
        return false;
    }
}
