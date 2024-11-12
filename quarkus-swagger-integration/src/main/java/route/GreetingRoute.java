package  route;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import model.GreetingRequest;
import model.GreetingResponse;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestParamType;
import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.servers.Server;
import processor.CreateGreeting;
import processor.UpdateGreeting;

import static constants.Constants.CONTENT_TYPE;
import static constants.Constants.MEDIA_TYPE_JSON;
import static dataFormatFactory.JacksonDataFormatFactory.json;


@ApplicationScoped
@OpenAPIDefinition(
        info = @Info(
                title = "Greeting API",
                version = "1.0.0",
                contact = @Contact(
                        name = "Example API Support",
                        email = "support@example.com")
        ),
        servers = {
                @Server(url = "http://localhost:9393", description = "Local Development Server")
        }
)
public class GreetingRoute extends RouteBuilder {

    @Inject
    CreateGreeting createGreeting;

    @Inject
    UpdateGreeting updateGreeting;

    @Override
    public void configure() throws Exception {
        restConfiguration()
                .apiContextRouteId("swagger-route")
                .enableCORS(true)
                .host("localhost:9393")
                .apiContextPath("/openapi")
                .apiProperty("api.title", "Greeting API")
                .apiProperty("api.version", "1.0.0")
                .component("platform-http");

        rest("/api")
                .tag("Greetings")
                .produces(MEDIA_TYPE_JSON)

                // GET default greeting
                .get("/greeting")
                    .description("Get a default greeting")
                    .outType(String.class)
                    .to("direct:defaultGreeting")

                // GET greeting with path parameter
                .get("/greeting/{name}")
                    .description("Get a personalized greeting")
                    .outType(String.class)
                    .param()
                        .name("name")
                        .type(RestParamType.path)
                        .description("The name to greet")
                        .dataType("string")
                        .required(true)
                    .endParam()
                    .to("direct:personalGreeting")

                // POST create custom greeting
                .post("/greeting")
                    .description("Create a custom greeting")
                    .produces(MEDIA_TYPE_JSON)
                    .consumes(MEDIA_TYPE_JSON)
                    .type(GreetingRequest.class)
                    .outType(GreetingResponse.class)
                    .to("direct:createGreeting")

                // PUT update greeting
                .put("/greeting/{name}")
                    .description("Update greeting for a specific name")
                    .consumes(MEDIA_TYPE_JSON)
                    .type(GreetingRequest.class)
                    .outType(GreetingResponse.class)
                    .param()
                        .name("name")
                        .type(RestParamType.path)
                        .description("The name to update greeting for")
                        .dataType("string")
                        .required(true)
                    .endParam()
                    .to("direct:updateGreeting");

        // Route implementations
        from("direct:defaultGreeting")
                .routeId("default-greeting-route")
                .setHeader(CONTENT_TYPE, constant(MEDIA_TYPE_JSON))
                .setBody(constant("{\"message\": \"Hello, World!\"}"))
                .log("Populated Default Greeting:\n RESPONSE BODY: \n${body}");

        from("direct:personalGreeting")
                .routeId("personal-greeting-route")
                .setHeader(CONTENT_TYPE, constant(MEDIA_TYPE_JSON))
                .setBody(exchange -> {
                    String name = exchange.getIn().getHeader("name", String.class);
                    return String.format("{\"message\": \"Hello, %s!\"}", name);
                })
                .log("Populated Personal Greeting:\n RESPONSE BODY: \n${body}");

        from("direct:createGreeting")
                .routeId("create-greeting-route")
                .unmarshal(json(GreetingRequest.class))
                .setHeader(CONTENT_TYPE, constant(MEDIA_TYPE_JSON))
                .process(createGreeting)
                .marshal(json(GreetingResponse.class))
                .log("Created Greeting:\n RESPONSE BODY: \n${body}");

        from("direct:updateGreeting")
                .routeId("update-greeting-route")
                .unmarshal(json(GreetingRequest.class))
                .setHeader(CONTENT_TYPE, constant(MEDIA_TYPE_JSON))
                .process(updateGreeting)
                .marshal(json(GreetingResponse.class))
                .log("Updated Greeting:\n RESPONSE BODY: \n${body}");
    }
}