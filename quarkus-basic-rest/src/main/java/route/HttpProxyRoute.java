package route;

import jakarta.enterprise.context.ApplicationScoped;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;

@ApplicationScoped
public class HttpProxyRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        // Route to handle forwarding requests to the external backend
        from("direct:backend")
            .routeId("rest-http-integration-route")
            .to("{{testHttpDestination}}")
            .log(LoggingLevel.INFO, "Successfully Integrated with below http backend: \n {{testHttpDestination}}");

        from("direct:localhost")
            .log(LoggingLevel.INFO, "Started localhost route")
            .routeId("rest-http-localhost-route")
            .to("{{testLocalHost7070}}")
            .log(LoggingLevel.INFO, "Successfully Integrated with below http backend: \n {{testLocalHost7070}}");
    }
}
