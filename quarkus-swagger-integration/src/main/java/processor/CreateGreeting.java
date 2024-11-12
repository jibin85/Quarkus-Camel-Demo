package processor;

import jakarta.enterprise.context.ApplicationScoped;
import model.GreetingRequest;
import model.GreetingResponse;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.logging.Logger;

@ApplicationScoped
public class CreateGreeting implements Processor {

    private static final Logger logger = Logger.getLogger(CreateGreeting.class.getSimpleName());

    @Override
    public void process(Exchange exchange) throws Exception {
        logger.info("Class: CreateGreeting, Method: process ----- STARTED");
        GreetingRequest request = exchange.getIn().getBody(GreetingRequest.class);
        GreetingResponse response = new GreetingResponse();
        response.setMessage("Created greeting: " + request.getMessage());
        response.setTimestamp(System.currentTimeMillis());
        exchange.getMessage().setBody(response);
        logger.info("Class: CreateGreeting, Method: process ----- EXECUTED");
    }
}
