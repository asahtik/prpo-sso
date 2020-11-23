package si.fri.prpo.govorilneure.api.v1.viri;

import si.fri.prpo.govorilneure.dtos.ProfesorDto;
import si.fri.prpo.govorilneure.entitete.Profesor;

import si.fri.prpo.govorilneure.entitete.Termin;
import si.fri.prpo.govorilneure.zrna.UpravljanjeSestankovZrno;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("profesorji")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class ProfesorVir {
    @Inject
    private UpravljanjeSestankovZrno uszrno;

    @GET
    public Response vrniProfesorje() {
        List<Profesor> profesorji = uszrno.vrniProfesorje();
        return Response.status(Response.Status.OK).entity(profesorji).build();
    }

    @GET
    @Path("{id}")
    public Response vrniProfesorja(@PathParam("id") int idProfesorja) {
        Profesor profesor = uszrno.vrniProfesorja(idProfesorja);
        if(profesor != null) return Response.status(Response.Status.OK).entity(profesor).build();
        else return Response.status(500).build();
    }

    @GET
    @Path("{id}/termini")
    public Response vrniTermine(@PathParam("id") int idProfesorja) {
        List<Termin> termini = uszrno.vrniTermineProfesorja(idProfesorja);
        if(termini != null) return Response.status(Response.Status.OK).entity(termini).build();
        else return Response.status(500).build();
    }

    @POST
    @Consumes({"application/si.fri.prpo.govorilneure.entitete.Profesor+json"})
    public Response dodajProfesorja(Profesor p) {
        ProfesorDto dto = new ProfesorDto(p.getIme(), p.getPriimek(), p.getEmail());
        Profesor ret = uszrno.dodajProfesorja(dto);
        if(ret != null) return Response.status(Response.Status.OK).entity(ret).build();
        else return Response.status(500).build();
    }
}
