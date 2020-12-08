package si.fri.prpo.govorilneure.api.v1.viri;

import com.kumuluz.ee.rest.beans.QueryParameters;
import si.fri.prpo.govorilneure.dtos.TerminDto;
import si.fri.prpo.govorilneure.entitete.Termin;
import si.fri.prpo.govorilneure.zrna.TerminZrno;
import si.fri.prpo.govorilneure.zrna.UpravljanjeSestankovZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Path("termini")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped

public class TerminVir {
    @Context
    protected UriInfo uriInfo;

    @Inject
    private UpravljanjeSestankovZrno uszrno;
    @Inject
    private TerminZrno tzrno;

    @GET
    public Response vrniTermine() {
        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        List<Termin> termini = tzrno.getAll();
        long count = tzrno.getAllCount(query);
        return Response.ok(termini).header("X-Total-Count", count).build();
    }

    @GET
    @Path("{id}")
    public Response vrniTermin(@PathParam("id") int idTermina) {
        Termin termin = tzrno.getById(idTermina);
        if(termin != null) return Response.status(Response.Status.OK).entity(termin).build();
        else return Response.status(500).build();
    }

    @POST
    @Consumes({"application/si.fri.prpo.govorilneure.entitete.Termin+json"})
    public Response dodajTermin(Termin t) {
        TerminDto dto = new TerminDto(t.getTimestamp(), t.getMaxSt(), t.getLocation(), t.getProfesor().getId());
        Termin ret = tzrno.getById(uszrno.dodajTermin(dto).getId());
        if(ret != null) return Response.status(Response.Status.OK).entity(ret).build();
        else return Response.status(500).build();
    }
}
