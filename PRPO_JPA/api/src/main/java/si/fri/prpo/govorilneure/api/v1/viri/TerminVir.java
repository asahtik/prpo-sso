package si.fri.prpo.govorilneure.api.v1.viri;

import com.kumuluz.ee.rest.beans.QueryParameters;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
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

    @Operation(description = "Vrne seznam terminov.", summary = "Seznam terminov")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Termini.")
    })
    @GET
    public Response vrniTermine() {
        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        List<Termin> termini = tzrno.getAll();
        long count = tzrno.getAllCount(query);
        return Response.ok(termini).header("X-Total-Count", count).build();
    }

    @Operation(description = "Vrne en termin.", summary = "Termin")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Termin."),
            @APIResponse(responseCode = "404", description = "Termin ni najden.")
    })
    @GET
    @Path("{id}")
    public Response vrniTermin(@Parameter(description = "ID termina", required = true) @PathParam("id") int idTermina) {
        Termin termin = tzrno.getById(idTermina);
        if(termin != null) return Response.status(Response.Status.OK).entity(termin).build();
        else return Response.status(404).build();
    }

    @Operation(description = "Doda nov termin.", summary = "Nov termin")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Nov termin."),
            @APIResponse(responseCode = "500", description = "Napaka na strežniku.")
    })
    @POST
    @Consumes({"application/si.fri.prpo.govorilneure.entitete.Termin+json"})
    public Response dodajTermin(@RequestBody(description = "Entiteta Termin", required = true) Termin t) {
        TerminDto dto = new TerminDto(t.getTimestamp(), t.getMaxSt(), t.getLocation(), t.getProfesor().getId());
        Termin ret = tzrno.getById(uszrno.dodajTermin(dto).getId());
        if(ret != null) return Response.status(Response.Status.OK).entity(ret).build();
        else return Response.status(500).build();
    }
}
