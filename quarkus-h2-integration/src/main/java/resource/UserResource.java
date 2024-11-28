package resource;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import model.UpdateRequest;
import model.User;
import org.apache.camel.ProducerTemplate;

import java.util.List;

@ApplicationScoped
@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
public class UserResource {
    private final ProducerTemplate producerTemplate;

    @GET
    public List<User> getAllUsers() {
        return producerTemplate.requestBody("direct:get-all-users", null, List.class);
    }

    @GET
    @Path("/{id}")
    public Response getUserById(@PathParam("id") Long id) {
        return producerTemplate.requestBody("direct:get-user-by-id", id, Response.class);
    }

    @POST
    @Transactional
    public Response createUser(User user) {
        return producerTemplate.requestBody("direct:create-user", user, Response.class);
    }

    @PUT
    @Path("/{id}")
    @Transactional
    public Response updateUser(@PathParam("id") Long id, User user) {
        // Create a wrapper object to pass both id and user
        UpdateRequest updateRequest = new UpdateRequest(id, user);
        return producerTemplate.requestBody("direct:update-user", updateRequest, Response.class);
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response deleteUser(@PathParam("id") Long id) {
        return producerTemplate.requestBody("direct:delete-user", id, Response.class);
    }
}