package si.fri.prpo.govorilneure.api.v1.viri;

import si.fri.prpo.govorilneure.dtos.PrijavaDto;
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

@Path("prijave")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@ApplicationScoped
public class PrijavaVir {
    @Inject
    private UpravljanjeSestankovZrno uszrno;

    @GET // query params date format (ms or string)?
    public Response vrniPrijave() {
        List<Prijava> prijave = uszrno.vrniPrijave();
        return Response.status(Response.Status.OK).entity(prijave).build();
    }

    @GET
    @Path("{id}")
    public Response vrniPrijavo(@PathParam("id") int idPrijave) {
        Prijava prijava = uszrno.vrniPrijavo(idPrijave);
        if(prijava != null) return Response.status(Response.Status.OK).entity(prijava).build();
        else return Response.status(500).build();
    }

    @POST
    @Consumes({"application/si.fri.prpo.govorilneure.dtos.PrijavaDto+json"})
    public Response dodajPrijavo(PrijavaDto dto) {
        dto.setTime(System.currentTimeMillis());
        Prijava ret = uszrno.dodajPrijavo(dto);
        if(ret != null) return Response.status(Response.Status.OK).entity(ret).build();
        else return Response.status(500).build();
    }

    @PUT
    @Consumes({"application/si.fri.prpo.govorilneure.dtos.PrijavaDto+json"})
    public Response potrdiPrijavo(PrijavaDto dto) {
        Prijava ret = uszrno.potrdiPrijavo(dto);
        if (ret != null) return Response.status(Response.Status.OK).entity(ret).build();
        else return Response.status(500).build();
    }
}
