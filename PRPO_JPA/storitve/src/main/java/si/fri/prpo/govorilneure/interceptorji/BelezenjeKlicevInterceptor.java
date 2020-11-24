package si.fri.prpo.govorilneure.interceptorji;

import si.fri.prpo.govorilneure.anotacije.BeleziKlice;
import java.util.logging.Logger;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.util.HashMap;

@Interceptor
@BeleziKlice
public class BelezenjeKlicevInterceptor {
    Logger log = Logger.getLogger(BelezenjeKlicevInterceptor.class.getName());
    HashMap<String,Integer> belezka = new HashMap<String,Integer>();
    @AroundInvoke
    public Object belezenjeKlicevInterceptor(InvocationContext context) throws Exception{

        String methodName = context.getMethod().getName();

        if(belezka.get(methodName) == null){
            belezka.put(methodName, 1);
        }
        else {
            belezka.put(methodName,(belezka.get(methodName)+1));
        }

        log.info("Metoda: " + methodName + " se je klicala " + belezka.get(methodName) + " krat");

        return context.proceed();
    }

}
