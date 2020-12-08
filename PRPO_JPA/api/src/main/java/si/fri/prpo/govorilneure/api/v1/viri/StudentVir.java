package si.fri.prpo.govorilneure.api.v1.viri;

import com.kumuluz.ee.rest.beans.QueryParameters;
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

    @GET
    public Response vrniStudente() {
        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        List<Student> studenti = szrno.getAll();
        long count = szrno.getAllCount(query);
        return Response.ok(studenti).header("X-Total-Count", count).build();
    }

    @GET
    @Path("{id}")
    public Response vrniStudenta(@PathParam("id") int idStudenta) {
        Student student = szrno.getById(idStudenta);
        if(student != null) return Response.status(Response.Status.OK).entity(student).build();
        else return Response.status(500).build();
    }

    @POST
    @Consumes({"application/si.fri.prpo.govorilneure.entitete.Student+json"})
    public Response dodajStudenta(Student s) {
        StudentDto dto = new StudentDto(s.getIme(), s.getPriimek(), s.getEmail(), s.getStizkaznice());
        Student ret = szrno.getById(uszrno.dodajStudenta(dto).getId());
        if(ret != null) return Response.status(Response.Status.OK).entity(ret).build();
        else return Response.status(500).build();
    }
}
