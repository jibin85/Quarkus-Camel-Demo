package resource;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.apache.camel.ProducerTemplate;

@ApplicationScoped
@Path("/jsonValidation")
public class JsonValidationFrom9090 {
    @Inject
    ProducerTemplate producerTemplate;

    @POST
    public Response jsonValidation(String requestBody) {
        String response = producerTemplate.requestBody("direct:jsonRequestFrom9090", requestBody, String.class);
        return Response.ok(response).build();
    }
}
