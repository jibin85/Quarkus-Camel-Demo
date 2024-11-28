package repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import jakarta.ws.rs.core.Response;
import model.UpdateRequest;
import model.User;

import java.util.Collections;
import java.util.List;

import static constants.Constants.USER_NOT_FOUND;

@ApplicationScoped
@Named("userRepository")
public class UserRepository implements PanacheRepository<User>{

    //Bean to Get all users
    public List<User> getAllUsers() {
        try {
            // Fetch all users
            return listAll();  // Return the list directly
        } catch (Exception e) {
            // Handle error
            return Collections.emptyList();
        }
    }


    //Bean to Get a user by ID
    public Response getUserById(Long id) {
        try {
            User user = findById(id);
            if (user != null) {
                return Response.ok(user).build();  // Return the user if found
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(USER_NOT_FOUND)
                        .build();  // Return NOT_FOUND if user doesn't exist
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error fetching user: " + e.getMessage())
                    .build();
        }
    }

    //Bean to Create a new user
    public Response createUser(User user) {
        try {
            // Persist the new user
            persist(user);
            return Response.status(Response.Status.CREATED)
                    .entity(user)  // Return the created user in response
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error creating user: " + e.getMessage())
                    .build();
        }
    }

    // Update an existing user
    public Response updateUser(UpdateRequest updateRequest) {
        try {
            Long id = updateRequest.getId();
            User incomingUser = updateRequest.getUser();

            // Find the existing user to update
            User existingUser = findById(id);
            if (existingUser == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(USER_NOT_FOUND)
                        .build();  // Return NOT_FOUND if user doesn't exist
            }

            // Selective update
            if (incomingUser.getName() != null) {
                existingUser.setName(incomingUser.getName());
            }
            if (incomingUser.getEmail() != null) {
                existingUser.setEmail(incomingUser.getEmail());
            }

            // Persist the updated user
            persist(existingUser);
            return Response.ok(existingUser).build();  // Return the updated user
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error updating user: " + e.getMessage())
                    .build();
        }
    }

    // Delete a user
    public Response deleteUser(Long id) {
        try {
            boolean deleted = deleteById(id);
            if (!deleted) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity(USER_NOT_FOUND)
                        .build();  // Return NOT_FOUND if user doesn't exist
            } else {
                return Response.noContent().build();  // Return NO_CONTENT if deleted successfully
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error deleting user: " + e.getMessage())
                    .build();
        }
    }
}