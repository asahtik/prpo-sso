package si.fri.prpo.vaje.sso.jdbc;

import java.sql.Connection;
import java.util.List;

public interface BaseDao {

    Connection getConnection();

    Entiteta vrni(int id);

    void vstavi(Entiteta ent);

    void odstrani(int id);

    void posodobi(Entiteta ent);

    List<Entiteta> vrniVse();
}
