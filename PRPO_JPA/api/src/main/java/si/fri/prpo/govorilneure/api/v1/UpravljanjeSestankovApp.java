package si.fri.prpo.govorilneure.api.v1;

import com.kumuluz.ee.cors.annotations.CrossOrigin;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.servers.Server;

import javax.annotation.security.DeclareRoles;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@OpenAPIDefinition(info = @Info(title = "Upravljanje sestankov API", version = "v1"), servers = @Server(url = "http://localhost:8080/"))
@DeclareRoles({"admin", "user"})
@ApplicationPath("/v1")
@CrossOrigin
public class UpravljanjeSestankovApp extends Application {

}
