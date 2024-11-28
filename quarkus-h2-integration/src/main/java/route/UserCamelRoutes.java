package route;

import jakarta.enterprise.context.ApplicationScoped;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;

import static constants.Constants.USER_REPOSITORY;

@ApplicationScoped
public class UserCamelRoutes extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        // Get All Users Route
        from("direct:get-all-users")
                .routeId("get-all-users-route")
                .bean(USER_REPOSITORY, "getAllUsers")
                .log(LoggingLevel.INFO, "Successfully fetched all users")
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200));

        // Get User by ID Route
        from("direct:get-user-by-id")
                .routeId("get-user-by-id-route")
                .bean(USER_REPOSITORY, "getUserById")
                .log(LoggingLevel.INFO, "Successfully fetched user by ID");

        // Create User Route
        from("direct:create-user")
                .routeId("create-user-route")
                .transacted()
                .bean(USER_REPOSITORY, "createUser") // Directly invoke the method in UserBeans
                .log(LoggingLevel.INFO, "User created successfully");

        // Update User Route
        from("direct:update-user")
                .routeId("update-user-route")
                .transacted()
                .bean(USER_REPOSITORY, "updateUser") // Directly invoke the method in UserBeans
                .log(LoggingLevel.INFO, "User updated successfully");

        // Delete User Route
        from("direct:delete-user")
                    .routeId("delete-user-route")
                .transacted()
                .bean(USER_REPOSITORY, "deleteUser") // Directly invoke the method in UserBeans
                .log(LoggingLevel.INFO, "User deleted successfully");
    }
}
