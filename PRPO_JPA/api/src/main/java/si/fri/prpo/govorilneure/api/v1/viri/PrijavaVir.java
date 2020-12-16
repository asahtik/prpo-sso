package si.fri.prpo.govorilneure.api.v1.viri;

import com.kumuluz.ee.rest.beans.QueryParameters;
import com.kumuluz.ee.security.annotations.Secure;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.headers.Header;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import si.fri.prpo.govorilneure.dtos.PrijavaDto;
import si.fri.prpo.govorilneure.entitete.Prijava;
import si.fri.prpo.govorilneure.zrna.PrijavaZrno;
import si.fri.prpo.govorilneure.zrna.UpravljanjeSestankovZrno;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

@Path("prijave")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Secure
@ApplicationScoped
public class PrijavaVir {
    @Context
    protected UriInfo uriInfo;

    @Inject
    private UpravljanjeSestankovZrno uszrno;
    @Inject
    private PrijavaZrno prijzrno;

    @Operation(description = "Vrne seznam prijav.", summary = "Seznam prijav")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Vsi uporabniki",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Prijava.class, type = SchemaType.ARRAY))},
                    headers = {@Header(name = "X-Total-Count", description = "Število vrnjenih prijav.")})
    })
    @GET // query params date format (ms or string)?
    @PermitAll
    public Response vrniPrijave() {
        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        List<Prijava> prijave = prijzrno.getAll(query);
        long count = prijzrno.getAllCount(query);
        return Response.ok(prijave).header("X-Total-Count", count).build();
    }

    @Operation(description = "Vrne eno prijavo.", summary = "Prijava")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Prijava.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Prijava.class))}),
            @APIResponse(responseCode = "404", description = "Prijava ni najdena.")
    })
    @GET
    @Path("{id}")
    @PermitAll
    public Response vrniPrijavo(@Parameter(description = "ID prijave", required = true) @PathParam("id") int idPrijave) {
        Prijava prijava = prijzrno.getById(idPrijave);
        if(prijava != null) return Response.status(Response.Status.OK).entity(prijava).build();
        else return Response.status(404).build();
    }

    @Operation(description = "Ustvari novo prijavo.", summary = "Nova prijava")
    @APIResponses({
            @APIResponse(responseCode = "201", description = "Nova prijava.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Prijava.class))}),
            @APIResponse(responseCode = "400", description = "Manjkajoči oz. neustrezni podatki."),
            @APIResponse(responseCode = "500", description = "Napaka na strežniku.")
    })
    @POST
    @Consumes({"application/si.fri.prpo.govorilneure.entitete.Prijava+json"})
    @RolesAllowed({"user", "admin"})
    public Response dodajPrijavo(@RequestBody(description = "Entiteta Prijava", required = true) Prijava p) {
        PrijavaDto dto = new PrijavaDto(0, p.getEmail(), p.getStudent().getId(), p.getTermin().getId());
        dto.setTime(System.currentTimeMillis());
        Prijava ret = prijzrno.getById(uszrno.dodajPrijavo(dto).getId());
        if(ret != null) return Response.status(201).entity(ret).build();
        else return Response.status(500).build();
    }

    @Operation(description = "Potrdi obstoječo prijavo.", summary = "Potrdi prijavo")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Potrjena prijava.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Prijava.class))}),
            @APIResponse(responseCode = "400", description = "Manjkajoči oz. neustrezni podatki."),
            @APIResponse(responseCode = "404", description = "Prijava ni najdena.")
    })
    @PUT
    @Consumes({"application/si.fri.prpo.govorilneure.entitete.Prijava+json"})
    @RolesAllowed("admin")
    public Response potrdiPrijavo(@RequestBody(description = "Entiteta Prijava", required = true) Prijava p) {
        PrijavaDto dto = new PrijavaDto(p.getId(), p.getTimestamp(), true, p.getEmail(), p.getStudent().getId(), p.getTermin().getId());
        Prijava ret = prijzrno.getById(uszrno.potrdiPrijavo(dto).getId());
        if (ret != null) return Response.status(Response.Status.OK).entity(ret).build();
        else return Response.status(404).build();
    }
}
