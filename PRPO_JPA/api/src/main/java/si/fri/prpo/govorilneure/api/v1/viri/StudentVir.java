package si.fri.prpo.govorilneure.api.v1.viri;

import si.fri.prpo.govorilneure.dtos.StudentDto;
import si.fri.prpo.govorilneure.entitete.Prijava;
import si.fri.prpo.govorilneure.entitete.Student;
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

    @GET
    public Response vrniStudente() {
        List<Student> studenti = uszrno.vrniStudente();
        return Response.status(Response.Status.OK).entity(studenti).build();
    }

    @GET
    @Path("{id}")
    public Response vrniStudenta(@PathParam("id") int idStudenta) {
        Student student = uszrno.vrniStudenta(idStudenta);
        if(student != null) return Response.status(Response.Status.OK).entity(student).build();
        else return Response.status(500).build();
    }

    @GET
    @Path("{id}/prijave")
    public Response vrniPrijave(@PathParam("id") int idStudenta) {
        List<Prijava> prijave = uszrno.vrniPrijaveStudenta(idStudenta);
        if(prijave != null) return Response.status(Response.Status.OK).entity(prijave).build();
        else return Response.status(500).build();
    }

    @POST
    @Consumes({"application/si.fri.prpo.govorilneure.dtos.StudentDto+json"})
    public Response dodajStudenta(StudentDto dto) {
        Student ret = uszrno.dodajStudenta(dto);
        if(ret != null) return Response.status(Response.Status.OK).entity(ret).build();
        else return Response.status(500).build();
    }
}
