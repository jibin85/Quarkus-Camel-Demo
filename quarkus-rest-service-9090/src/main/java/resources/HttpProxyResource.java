package resources;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.apache.camel.ProducerTemplate;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@ApplicationScoped
@Path("/proxy")
public class HttpProxyResource {

    @Inject
    @ConfigProperty(name = "testHttpDestination")
    private String httpDestination;

    @Inject
    @ConfigProperty(name = "testLocalHostURL")
    private String localhostUrl;

    private String ACK_MSG_HTTP;
    private String ACK_MSG_LOCALHOST;

    @Inject
    ProducerTemplate producerTemplate;

    @PostConstruct
    public void init() {
        // Initialize ACK_MSG after httpDestination is injected
        ACK_MSG_HTTP = "Successfully Integrated with http backend at " + httpDestination + "\n Response Body: \n";
        ACK_MSG_LOCALHOST = "Successfully Integrated with http localhost backend at " + localhostUrl + "\n Response Body: \n";
    }

    @POST
    @Path("/http-backend")
    @Consumes("application/json")
    public Response forwardRequest(String requestBody) {
        // Forward the request to the external HTTP backend
        String response = ACK_MSG_HTTP + producerTemplate.requestBody("direct:backend", requestBody, String.class);
        return Response.ok(response).build();
    }

    @POST
    @Path("/localhost-backend")
    public Response forwardRequestLocalhost(String requestBody) {
        String response = ACK_MSG_LOCALHOST + producerTemplate.requestBody("direct:localhost", requestBody, String.class);
        return Response.ok(response).build();
    }
}
