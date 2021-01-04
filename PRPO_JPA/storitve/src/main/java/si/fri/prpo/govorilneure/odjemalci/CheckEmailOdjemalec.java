package si.fri.prpo.govorilneure.odjemalci;


import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;



import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.ProcessingException;
import javax.ws.rs.WebApplicationException;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import java.util.logging.Logger;

@ApplicationScoped
public class CheckEmailOdjemalec {

    private static Logger log = Logger.getLogger(CheckEmailOdjemalec.class.getName());


    static  Response response;
    static String istrue;
    static String url;


    @PostConstruct
    private void postConstruct() {
        log.info("Inicializacija odjemalca " + CheckEmailOdjemalec.class.getName() + ".");

    }

    public static boolean ustrezenEmail(String email)  {

        url = "https://metropolis-api-email.p.rapidapi.com/analysis?email=" + URLEncoder.encode(email, StandardCharsets.UTF_8);
        try {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(url)
                    .get()
                    .addHeader("x-rapidapi-key", "b337492b5cmsh2eab1ffb0617765p180065jsnc898b6c22667")
                    .addHeader("x-rapidapi-host", "metropolis-api-email.p.rapidapi.com")
                    .build();

            response = client.newCall(request).execute();
            istrue=response.body().string();

            //valid = response.body().string().contains(": true");
        } catch(WebApplicationException | ProcessingException | IOException e) {
            log.severe(e.getMessage());
            throw new InternalServerErrorException(e);
        }
        System.out.println(istrue);
        return istrue.contains("true");
    }


}
