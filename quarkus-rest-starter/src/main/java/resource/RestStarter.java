package resource;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/started")
public class RestStarter {
    @POST
    @Consumes("application/json")
    @Produces(MediaType.TEXT_PLAIN)
    public String requestFrom9090(String requestBody) {
        return "Started Quarkus REST application at port 7070 with request: " + requestBody;
    }
}
