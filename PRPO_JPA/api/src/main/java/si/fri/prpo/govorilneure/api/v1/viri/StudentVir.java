package si.fri.prpo.govorilneure.api.v1.viri;

import com.kumuluz.ee.rest.beans.QueryParameters;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import si.fri.prpo.govorilneure.dtos.StudentDto;
import si.fri.prpo.govorilneure.entitete.Prijava;
import si.fri.prpo.govorilneure.entitete.Student;
import si.fri.prpo.govorilneure.zrna.StudentZrno;
import si.fri.prpo.govorilneure.zrna.UpravljanjeSestankovZrno;

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
@ApplicationScoped
public class StudentVir {
    @Context
    protected UriInfo uriInfo;

    @Inject
    private UpravljanjeSestankovZrno uszrno;
    @Inject
    private StudentZrno szrno;

    @Operation(description = "Vrne seznam studentov.", summary = "Seznam studentov")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Studenti.")
    })
    @GET
    public Response vrniStudente() {
        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        List<Student> studenti = szrno.getAll();
        long count = szrno.getAllCount(query);
        return Response.ok(studenti).header("X-Total-Count", count).build();
    }

    @Operation(description = "Vrne enega studenta.", summary = "Student")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Student."),
            @APIResponse(responseCode = "404", description = "Student ni najden.")
    })
    @GET
    @Path("{id}")
    public Response vrniStudenta(@Parameter(description = "ID studenta", required = true) @PathParam("id") int idStudenta) {
        Student student = szrno.getById(idStudenta);
        if(student != null) return Response.status(Response.Status.OK).entity(student).build();
        else return Response.status(404).build();
    }

    @Operation(description = "Doda novega studenta.", summary = "Nov student")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Nov student."),
            @APIResponse(responseCode = "500", description = "Napaka na strežniku.")
    })
    @POST
    @Consumes({"application/si.fri.prpo.govorilneure.entitete.Student+json"})
    public Response dodajStudenta(@RequestBody(description = "Entiteta Student", required = true) Student s) {
        StudentDto dto = new StudentDto(s.getIme(), s.getPriimek(), s.getEmail(), s.getStizkaznice());
        Student ret = szrno.getById(uszrno.dodajStudenta(dto).getId());
        if(ret != null) return Response.status(Response.Status.OK).entity(ret).build();
        else return Response.status(500).build();
    }
}
