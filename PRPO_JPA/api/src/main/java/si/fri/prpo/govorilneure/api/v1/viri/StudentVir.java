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
import si.fri.prpo.govorilneure.dtos.StudentDto;
import si.fri.prpo.govorilneure.entitete.Student;
import si.fri.prpo.govorilneure.zrna.StudentZrno;
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

@Path("studenti")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Secure
@ApplicationScoped
public class StudentVir {
    @Context
    protected UriInfo uriInfo;

    @Inject
    private UpravljanjeSestankovZrno uszrno;
    @Inject
    private StudentZrno szrno;

    @Operation(description = "Vrne seznam študentov.", summary = "Seznam študentov")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Študenti.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Student.class, type = SchemaType.ARRAY))},
                    headers = {@Header(name = "X-Total-Count", description = "Število vrnjenih študentov.")})
    })
    @GET
    @PermitAll
    public Response vrniStudente() {
        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        List<Student> studenti = szrno.getAll(query);
        long count = szrno.getAllCount(query);
        return Response.ok(studenti).header("X-Total-Count", count).build();
    }

    @Operation(description = "Vrne enega študenta.", summary = "Študent")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Študent.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Student.class))}),
            @APIResponse(responseCode = "404", description = "Študent ni najden.")
    })
    @GET
    @Path("{id}")
    @PermitAll
    public Response vrniStudenta(@Parameter(description = "ID študenta", required = true) @PathParam("id") int idStudenta) {
        Student student = szrno.getById(idStudenta);
        if(student != null) return Response.status(Response.Status.OK).entity(student).build();
        else return Response.status(404).build();
    }

    @Operation(description = "Doda novega študenta.", summary = "Nov študent")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Nov študent.",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Student.class))}),
            @APIResponse(responseCode = "400", description = "Manjkajoči oz. neustrezni podatki."),
            @APIResponse(responseCode = "500", description = "Napaka na strežniku.")
    })
    @POST
    @Consumes({"application/si.fri.prpo.govorilneure.entitete.Student+json"})
    @RolesAllowed("admins")
    public Response dodajStudenta(@RequestBody(description = "Entiteta Študent", required = true) Student s) {
        StudentDto dto = new StudentDto(s.getIme(), s.getPriimek(), s.getEmail(), s.getStizkaznice());
        Student ret = szrno.getById(uszrno.dodajStudenta(dto).getId());
        if(ret != null) return Response.status(Response.Status.OK).entity(ret).build();
        else return Response.status(500).build();
    }
}
