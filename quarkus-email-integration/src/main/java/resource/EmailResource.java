package resource;

import emailRequest.EmailRequest;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.apache.camel.ProducerTemplate;

@Path("/email")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EmailResource {

    @Inject
    ProducerTemplate producerTemplate;

    @POST
    @Path("/text")
    public Response sendTextEmail(EmailRequest request) {
        try {
            producerTemplate.sendBody("direct:sendTextEmail", request);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/html")
    public Response sendHtmlEmail(EmailRequest request) {
        try {
            producerTemplate.sendBody("direct:sendHtmlEmail", request);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/attachment")
    public Response sendEmailWithAttachment(EmailRequest request) {
        try {
            producerTemplate.sendBody("direct:sendEmailWithAttachment", request);
            return Response.ok().build();
        } catch (Exception e) {
            return Response.serverError().entity(e.getMessage()).build();
        }
    }
}