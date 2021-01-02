package si.fri.prpo.govorilneure.odjemalci;

import com.kumuluz.ee.configuration.utils.ConfigurationUtil;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import java.util.logging.Logger;

@ApplicationScoped
public class CheckEmailOdjemalec {

    private static Logger log = Logger.getLogger(CheckEmailOdjemalec.class.getName());

    private static Client http;
    private static String baseUrl;

    @PostConstruct
    private void postConstruct() {
        log.info("Inicializacija odjemalca " + CheckEmailOdjemalec.class.getName() + ".");
        http = ClientBuilder.newClient();
        baseUrl = ConfigurationUtil.getInstance()
                .get("integrations.odjemalci.email-check")
                .orElse("https://metropolis-api-email.p.rapidapi.com/analysis");
    }

    public static boolean ustrezenEmail(String email) {
        Object response = null;
        try {
            response = http.target(baseUrl).request(MediaType.APPLICATION_JSON)
                    .header("X-RapidAPI-Key", "bacdc0ce4fmsha9ca7e80b00396cp15900bjsnbff83fd208fe")
                    .header("X-RapidAPI-Host", "metropolis-api-email.p.rapidapi.com")
                    .get();
        } catch(WebApplicationException | ProcessingException e) {
            log.severe(e.getMessage());
            throw new InternalServerErrorException(e);
        }

        System.out.println(response.toString());
        return false;
    }
}
