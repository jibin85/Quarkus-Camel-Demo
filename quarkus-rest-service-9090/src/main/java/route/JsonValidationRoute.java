package route;

import jakarta.enterprise.context.ApplicationScoped;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;

@ApplicationScoped
public class JsonValidationRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("direct:jsonValidation")
            .routeId("json-validation-route")
            .to("{{testJsonRequestURL}}")
            .log(LoggingLevel.INFO, "Successfully sent JSON request from localhost:9090 to {{testJsonRequestURL}}");
    }
}
