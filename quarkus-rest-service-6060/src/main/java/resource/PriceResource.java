package resource;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.apache.camel.ProducerTemplate;

@Path("/api/price")
@ApplicationScoped
public class PriceResource {

    @Inject
    ProducerTemplate producerTemplate;

    @POST
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
    public Response calculatePrice(String requestXml) {
        try {
            String responseXml = producerTemplate.requestBody(
                    "direct:calculatePrice",
                    requestXml,
                    String.class
            );
            return Response.ok(responseXml).build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Error processing request: " + e.getMessage())
                    .build();
        }
    }
}
