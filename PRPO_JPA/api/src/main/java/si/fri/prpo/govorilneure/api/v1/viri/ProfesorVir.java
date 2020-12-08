package si.fri.prpo.govorilneure.api.v1.viri;

import com.kumuluz.ee.rest.beans.QueryParameters;
import si.fri.prpo.govorilneure.dtos.ProfesorDto;
import si.fri.prpo.govorilneure.entitete.Profesor;

import si.fri.prpo.govorilneure.entitete.Termin;
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

    @GET
    public Response vrniProfesorje() {
        QueryParameters query = QueryParameters.query(uriInfo.getRequestUri().getQuery()).build();
        List<Profesor> profesorji = profzrno.getAll();
        long count = profzrno.getAllCount(query);
        return Response.ok(profesorji).header("X-Total-Count", count).build();
    }

    @GET
    @Path("{id}")
    public Response vrniProfesorja(@PathParam("id") int idProfesorja) {
        Profesor profesor = profzrno.getById(idProfesorja);
        if(profesor != null) return Response.status(Response.Status.OK).entity(profesor).build();
        else return Response.status(500).build();
    }

    @POST
    @Consumes({"application/si.fri.prpo.govorilneure.entitete.Profesor+json"})
    public Response dodajProfesorja(Profesor p) {
        Profesor ret = profzrno.getById(uszrno.dodajProfesorja(new ProfesorDto(p.getIme(), p.getPriimek(), p.getEmail())).getId());
        if(ret != null) return Response.status(Response.Status.OK).entity(ret).build();
        else return Response.status(500).build();
    }
}
