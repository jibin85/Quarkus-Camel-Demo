package resources;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.apache.camel.ProducerTemplate;

@ApplicationScoped
@Path("/jsonValidation")
public class JsonValidationResource {
    @Inject
    ProducerTemplate producerTemplate;
    @POST
    @Consumes("application/json")
    public Response jsonValidationRequest(String requestBody) {
        // Forward the request to the external HTTP backend
        String response = producerTemplate.requestBody("direct:jsonValidation", requestBody, String.class);
        return Response.ok(response).build();
    }
}
