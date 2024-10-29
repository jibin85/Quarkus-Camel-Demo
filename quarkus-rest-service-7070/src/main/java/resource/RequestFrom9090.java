package resource;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
@Path("/started")
public class RequestFrom9090 {
    @POST
    public Response requestFrom9090(String requestBody) {
        String greeting = "Hello, " + requestBody + " Greetings from 7070!";
        return Response.ok(greeting).build();
    }
}
