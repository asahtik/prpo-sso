package si.fri.prpo.govorilneure.api.v1.mappers;

import si.fri.prpo.govorilneure.izjeme.NeveljavenDtoIzjema;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class NeveljavenDtoExceptionMapper implements ExceptionMapper<NeveljavenDtoIzjema> {
    @Override
    public Response toResponse(NeveljavenDtoIzjema exception) {
        return Response
                .status(Response.Status.BAD_REQUEST)
                .entity("{\"napaka\":\"" + exception.getMessage() + "\"}")
                .build();
    }
}