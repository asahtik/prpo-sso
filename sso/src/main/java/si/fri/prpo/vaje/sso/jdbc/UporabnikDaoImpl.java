package si.fri.prpo.vaje.sso.jdbc;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class UporabnikDaoImpl implements BaseDao {

    private static UporabnikDaoImpl instance=null;

    private Connection con=null;
    private Logger log=Logger.getLogger(UporabnikDaoImpl.class.getName());

    public static UporabnikDaoImpl getInstance() {
        if(instance==null) instance=new UporabnikDaoImpl();
        return instance;
    }

    private UporabnikDaoImpl() {
        this.con=getConnection();
    }

    private Uporabnik getUporabnikFromRS(ResultSet rs) throws SQLException {

        String ime = rs.getString("ime");
        String priimek = rs.getString("priimek");
        String uporabniskoIme = rs.getString("uporabniskoime");
        return new Uporabnik(ime, priimek, uporabniskoIme);

    }

    @Override
    public Connection getConnection() {
        try {
            InitialContext initCtx = new InitialContext();
            DataSource ds = (DataSource) initCtx.lookup("jdbc/SimpleJdbcDS");
            return ds.getConnection();
        } catch (NamingException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Entiteta vrni(int id) {
        PreparedStatement ps = null;

        try {

            if (con == null) {
                con = getConnection();
            }

            String sql = "SELECT * FROM uporabnik WHERE id = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return getUporabnikFromRS(rs);
            } else {
                log.info("Uporabnik ne obstaja");
            }

        } catch (SQLException e) {
            log.severe(e.toString());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    log.severe(e.toString());
                }
            }
        }
        return null;
    }

    @Override
    public void vstavi(Entiteta ent) {
        PreparedStatement ps = null;

        try {

            if (con == null) {
                con = getConnection();
            }
            String sql = "INSERT INTO uporabnik (ime, priimek, uporabniskoime) VALUES (?, ?, ?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, ((Uporabnik)ent).getIme());
            ps.setString(2, ((Uporabnik)ent).getPriimek());
            ps.setString(3, ((Uporabnik)ent).getUporabniskoIme());
            int no=ps.executeUpdate();

        } catch (SQLException e) {
            log.severe(e.toString());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    log.severe(e.toString());
                }
            }
        }
    }

    @Override
    public void odstrani(int id) {
        PreparedStatement ps = null;

        try {

            if (con == null) {
                con = getConnection();
            }

            String sql = "DELETE FROM uporabnik WHERE id = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            int no = ps.executeUpdate();
            if(no==0) log.info("Uporabnik ne obstaja");

        } catch (SQLException e) {
            log.severe(e.toString());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    log.severe(e.toString());
                }
            }
        }
    }

    @Override
    public void posodobi(Entiteta ent) {
        PreparedStatement ps = null;

        try {

            if (con == null) {
                con = getConnection();
            }

            String sql = "UPDATE uporabnik SET ime = ?, priimek = ?, uporabniskoime = ? WHERE id = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, ((Uporabnik)ent).getIme());
            ps.setString(2, ((Uporabnik)ent).getPriimek());
            ps.setString(3, ((Uporabnik)ent).getUporabniskoIme());
            ps.setInt(4, ent.getId());
            int rs = ps.executeUpdate();

        } catch (SQLException e) {
            log.severe(e.toString());
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException e) {
                    log.severe(e.toString());
                }
            }
        }
    }

    @Override
    public List<Entiteta> vrniVse() {
        Statement st = null;
        List<Entiteta> ret=new ArrayList<Entiteta>();

        try {

            if (con == null) {
                con = getConnection();
            }

            String sql = "SELECT * FROM uporabnik";
            st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while(rs.next()) ret.add(getUporabnikFromRS(rs));
            return ret;

        } catch (SQLException e) {
            log.severe(e.toString());
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    log.severe(e.toString());
                }
            }
        }
        return null;
    }
}
