package processor;

import jakarta.enterprise.context.ApplicationScoped;
import model.GreetingRequest;
import model.GreetingResponse;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.logging.Logger;

@ApplicationScoped
public class UpdateGreeting implements Processor {
    private static final Logger logger = Logger.getLogger(UpdateGreeting.class.getSimpleName());

    @Override
    public void process(Exchange exchange) throws Exception {
        logger.info("Class: UpdateGreeting, Method: process ----- STARTED");
        String name = exchange.getIn().getHeader("name", String.class);
        GreetingRequest request = exchange.getIn().getBody(GreetingRequest.class);
        GreetingResponse response = new GreetingResponse();
        response.setMessage("Updated greeting for " + name + ": " + request.getMessage());
        response.setTimestamp(System.currentTimeMillis());
        exchange.getMessage().setBody(response);
        logger.info("Class: UpdateGreeting, Method: process ----- EXECUTED");
    }
}
