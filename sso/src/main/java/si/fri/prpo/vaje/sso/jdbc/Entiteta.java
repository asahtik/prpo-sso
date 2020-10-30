package si.fri.prpo.vaje.sso.jdbc;

import java.io.Serializable;

public abstract class Entiteta implements Serializable {

    private static final long serialVersionUID=1L;

    @Override
    public abstract String toString();

    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
