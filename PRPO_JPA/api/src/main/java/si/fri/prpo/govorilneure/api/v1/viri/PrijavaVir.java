package si.fri.prpo.govorilneure.api.v1.viri;

import si.fri.prpo.govorilneure.dtos.PrijavaDto;
import si.fri.prpo.govorilneure.entitete.Prijava;
import si.fri.prpo.govorilneure.zrna.PrijavaZrno;
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
    @Inject
    private PrijavaZrno prijzrno;

    @GET // query params date format (ms or string)?
    public Response vrniPrijave() {
        List<Prijava> prijave = prijzrno.getAll();
        return Response.status(Response.Status.OK).entity(prijave).build();
    }

    @GET
    @Path("{id}")
    public Response vrniPrijavo(@PathParam("id") int idPrijave) {
        Prijava prijava = prijzrno.getById(idPrijave);
        if(prijava != null) return Response.status(Response.Status.OK).entity(prijava).build();
        else return Response.status(500).build();
    }

    @POST
    @Consumes({"application/si.fri.prpo.govorilneure.entitete.Prijava+json"})
    public Response dodajPrijavo(Prijava p) {
        PrijavaDto dto = new PrijavaDto(0, p.getEmail(), p.getStudent().getId(), p.getTermin().getId());
        dto.setTime(System.currentTimeMillis());
        Prijava ret = prijzrno.getById(uszrno.dodajPrijavo(dto).getId());
        if(ret != null) return Response.status(Response.Status.OK).entity(ret).build();
        else return Response.status(500).build();
    }

    @PUT
    @Consumes({"application/si.fri.prpo.govorilneure.entitete.Prijava+json"})
    public Response potrdiPrijavo(Prijava p) {
        PrijavaDto dto = new PrijavaDto(p.getId(), p.getTimestamp(), true, p.getEmail(), p.getStudent().getId(), p.getTermin().getId());
        Prijava ret = prijzrno.getById(uszrno.potrdiPrijavo(dto).getId());
        if (ret != null) return Response.status(Response.Status.OK).entity(ret).build();
        else return Response.status(500).build();
    }
}
