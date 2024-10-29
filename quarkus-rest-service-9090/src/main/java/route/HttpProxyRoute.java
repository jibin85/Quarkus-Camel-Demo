package route;

import jakarta.enterprise.context.ApplicationScoped;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;

@ApplicationScoped
public class HttpProxyRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("direct:backend")
            .routeId("rest-http-integration-route")
            .to("{{testHttpDestination}}")
            .log(LoggingLevel.INFO, "Successfully Integrated with below http backend: \n {{testHttpDestination}}");

        from("direct:localhost")
            .routeId("rest-msg-send-service-route")
            .to("{{testLocalHost7070}}")
            .log(LoggingLevel.INFO, "Successfully sent request from localhost:9090 to {{testLocalHostURL}}");
    }
}
