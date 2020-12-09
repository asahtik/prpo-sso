package si.fri.prpo.govorilneure.api.v1.viri;

import com.kumuluz.ee.rest.beans.QueryParameters;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import si.fri.prpo.govorilneure.dtos.ProfesorDto;
import si.fri.prpo.govorilneure.entitete.Profesor;
import si.fri.prpo.govorilneure.zrna.ProfesorZrno;
import si.fri.prpo.govorilneure.zrna.UpravljanjeSestankovZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Path("profesorji")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class ProfesorVir {
    @Context
    protected UriInfo uriInfo;

    @Inject
    private UpravljanjeSestankovZrno uszrno;
    @Inject
    private ProfesorZrno profzrno;

    @Operation(description = "Vrne seznam profesorjev.", summary = "Seznam profesorjev")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Profesorji.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Profesor.class, type = SchemaType.ARRAY))},
                    headers = {@Header(name = "X-Total-Count", description = "Število vrnjenih profesorjev.")})
    })
    @GET
    public Response vrniProfesorje() {
        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        List<Profesor> profesorji = profzrno.getAll(query);
        long count = profzrno.getAllCount(query);
        return Response.ok(profesorji).header("X-Total-Count", count).build();
    }

    @Operation(description = "Vrne enega profesorja.", summary = "Profesor")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Profesor.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Profesor.class))}),
            @APIResponse(responseCode = "404", description = "Profesor ni najden.")
    })
    @GET
    @Path("{id}")
    public Response vrniProfesorja(@Parameter(description = "ID profesorja", required = true) @PathParam("id") int idProfesorja) {
        Profesor profesor = profzrno.getById(idProfesorja);
        if(profesor != null) return Response.status(Response.Status.OK).entity(profesor).build();
        else return Response.status(404).build();
    }

    @Operation(description = "Doda novega profesorja.", summary = "Nov profesor")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Nov profesor.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Profesor.class))}),
            @APIResponse(responseCode = "400", description = "Manjkajoči oz. neustrezni podatki."),
            @APIResponse(responseCode = "500", description = "Napaka na strežniku.")
    })
    @POST
    @Consumes({"application/si.fri.prpo.govorilneure.entitete.Profesor+json"})
    public Response dodajProfesorja(@RequestBody(description = "Entiteta Profesor", required = true) Profesor p) {
        Profesor ret = profzrno.getById(uszrno.dodajProfesorja(new ProfesorDto(p.getIme(), p.getPriimek(), p.getEmail())).getId());
        if(ret != null) return Response.status(Response.Status.OK).entity(ret).build();
        else return Response.status(500).build();
    }
}
