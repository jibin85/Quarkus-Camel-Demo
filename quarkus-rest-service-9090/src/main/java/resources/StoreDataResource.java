package resources;

import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.apache.camel.ProducerTemplate;

@Path("/store")
public class StoreDataResource {
    @Inject
    ProducerTemplate producerTemplate;

    @POST
    public Response storeData(String data) {
        // Send data to the Camel route
        producerTemplate.sendBody("direct:storeToFile", data);
        return Response.ok("Data stored successfully!").build();
    }
}
