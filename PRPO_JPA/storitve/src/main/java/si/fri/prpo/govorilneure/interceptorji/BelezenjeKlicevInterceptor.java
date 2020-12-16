package si.fri.prpo.govorilneure.interceptorji;

import si.fri.prpo.govorilneure.anotacije.BeleziKlice;
import si.fri.prpo.govorilneure.dtos.PrijavaDto;
import si.fri.prpo.govorilneure.dtos.ProfesorDto;
import si.fri.prpo.govorilneure.dtos.StudentDto;
import si.fri.prpo.govorilneure.dtos.TerminDto;
import si.fri.prpo.govorilneure.izjeme.NeveljavenDtoIzjema;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.util.HashMap;
import java.util.logging.Logger;

@Interceptor
@BeleziKlice
public class BelezenjeKlicevInterceptor {
    Logger log = Logger.getLogger(BelezenjeKlicevInterceptor.class.getName());
    HashMap<String,Integer> belezka = new HashMap<String,Integer>();

    public void izpisiBelezeneKlice(InvocationContext context){
        String methodName = context.getMethod().getDeclaringClass().getName() + "." + context.getMethod().getName();
        if(belezka.get(methodName) == null){
            belezka.put(methodName, 1);
        }
        else {
            belezka.put(methodName,(belezka.get(methodName)+1));
        }
        log.info("Metoda: " + methodName + " se je klicala " + belezka.get(methodName) + " krat");
    }

    @AroundInvoke
    public Object belezenjeKlicevInterceptor(InvocationContext context) throws Exception{


        if(context.getParameters().length == 1){
            if(context.getMethod().getName() == "potrdiPrijavo"){
                izpisiBelezeneKlice(context);
            }
            else{
                if(context.getParameters()[0] instanceof ProfesorDto){
                    ProfesorDto profesor = (ProfesorDto) context.getParameters()[0];
                    if(profesor.getEmail() == null || profesor.getEmail().isEmpty() ||
                            profesor.getIme() == null || profesor.getIme().isEmpty() ||
                            profesor.getPriimek() == null || profesor.getPriimek().isEmpty()) {

                        String msg = "Podan profesor ne vsebuje vseh obveznih podatkov (ime, priimek, email)";
                        log.severe(msg);
                        throw new NeveljavenDtoIzjema(msg);
                    }
                    else{
                        izpisiBelezeneKlice(context);
                    }
                }
                else if(context.getParameters()[0] instanceof StudentDto) {
                    StudentDto student = (StudentDto) context.getParameters()[0];
                    if(student.getEmail() == null || student.getEmail().isEmpty() ||
                            student.getIme() == null || student.getIme().isEmpty() ||
                            student.getPriimek() == null || student.getPriimek().isEmpty() ||
                            String.valueOf(student.getStIzkaznice()).length() != 8) {

                        String msg = "Podan student ne vsebuje vseh obveznih podatkov (ime, priimek, email, stevilka studentske izkaznice)";
                        log.severe(msg);
                        throw new NeveljavenDtoIzjema(msg);
                    }
                    else{
                        izpisiBelezeneKlice(context);
                    }
                }
                else if(context.getParameters()[0] instanceof TerminDto) {
                    TerminDto termin = (TerminDto) context.getParameters()[0];
                    if(termin.getLokacija() == 0 ||
                            termin.getMaxSt() == 0) {

                        String msg = "Podan Termin ne vsebuje podatka o lokaciji ali pa je max število udeležncev premajhno)";
                        log.severe(msg);
                        throw new NeveljavenDtoIzjema(msg);
                    }
                    else{
                        izpisiBelezeneKlice(context);
                    }
                }else if(context.getParameters()[0] instanceof PrijavaDto) { //do kle pride samo dodaj prijavo

                    PrijavaDto prijava = (PrijavaDto) context.getParameters()[0];
                    if(prijava.getEmail() == null || prijava.getEmail().isEmpty() ||
                            prijava.getStudentId() == null ||
                            prijava.getTerminId() == null) {

                        String msg = "Podana prijava ne vsebuje obveznih podatkov (student_id, termin_id, email, time))";
                        log.severe(msg);
                        throw new NeveljavenDtoIzjema(msg);
                    }
                    else{
                        izpisiBelezeneKlice(context);
                    }
                }
            }
        }
        return context.proceed();
    }

}
