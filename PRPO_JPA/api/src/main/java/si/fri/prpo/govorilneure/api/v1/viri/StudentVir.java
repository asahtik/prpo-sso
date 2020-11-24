package si.fri.prpo.govorilneure.api.v1.viri;

import si.fri.prpo.govorilneure.dtos.StudentDto;
import si.fri.prpo.govorilneure.entitete.Prijava;
import si.fri.prpo.govorilneure.entitete.Student;
import si.fri.prpo.govorilneure.zrna.StudentZrno;
import si.fri.prpo.govorilneure.zrna.UpravljanjeSestankovZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("studenti")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class StudentVir {
    @Inject
    private UpravljanjeSestankovZrno uszrno;
    @Inject
    private StudentZrno szrno;

    @GET
    public Response vrniStudente() {
        List<Student> studenti = szrno.getAll();
        return Response.status(Response.Status.OK).entity(studenti).build();
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
