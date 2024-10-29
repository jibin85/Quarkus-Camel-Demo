package route;

import jakarta.enterprise.context.ApplicationScoped;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;

@ApplicationScoped
public class JsonValidationRequestRoute extends RouteBuilder {
    @Override
    public void configure() {
        from("direct:jsonRequestFrom9090")
            .process(exchange -> {
                String jsonRequest = exchange.getIn().getBody(String.class);
                String ackMessage = "JSON Message received : \n" + jsonRequest;
                exchange.getMessage().setBody(ackMessage);
            })
            .log(LoggingLevel.INFO, "Successfully Validated the JSON Request from 9090 in 7070");
    }
}
