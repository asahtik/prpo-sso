package si.fri.prpo.govorilneure.api.v1.viri;

import si.fri.prpo.govorilneure.dtos.TerminDto;
import si.fri.prpo.govorilneure.entitete.Termin;
import si.fri.prpo.govorilneure.zrna.UpravljanjeSestankovZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("termini")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped

public class TerminVir {
    @Inject
    private UpravljanjeSestankovZrno uszrno;

    @GET // query params date format (ms or string)?
    public Response vrniTermine() {
        List<Termin> termini = uszrno.vrniTermine();
        return Response.status(Response.Status.OK).entity(termini).build();
    }

    @GET
    @Path("{id}")
    public Response vrniTermin(@PathParam("id") int idTermina) {
        Termin termin = uszrno.vrniTermin(idTermina);
        if(termin != null) return Response.status(Response.Status.OK).entity(termin).build();
        else return Response.status(500).build();
    }

    @POST
    @Consumes({"application/si.fri.prpo.govorilneure.entitete.Termin+json"})
    public Response dodajTermin(Termin t) {
        TerminDto dto = new TerminDto(t.getTimestamp().getTime(), t.getMaxSt(), t.getLocation(), t.getProfesor().getId());
        Termin ret = uszrno.dodajTermin(dto);
        if(ret != null) return Response.status(Response.Status.OK).entity(ret).build();
        else return Response.status(500).build();
    }
}
