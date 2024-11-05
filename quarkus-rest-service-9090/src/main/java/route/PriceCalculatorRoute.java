package route;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import model.PriceRequest;
import model.PriceResponse;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import processor.MrpCalculator;

import static dataFormatFactory.JacksonDataFormatFactory.json;


@ApplicationScoped
public class PriceCalculatorRoute extends RouteBuilder {
    @Inject
    private MrpCalculator calculatePrice;

    @Override
    public void configure() throws Exception {
        // Error Handler
        onException(Exception.class)
            .handled(true)
            .process(exchange -> {
                String errorMessage = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class).getMessage();
                exchange.getMessage().setBody(String.format("{\"error\": \"%s\"}", errorMessage));
            })
            .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
            .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(400));

        // REST endpoint
        rest("/api")
            .post("/calculate-price")
                .consumes("application/json")
                .produces("application/json")
                .description("Calculate MRP from original price")  // API documentation
                .responseMessage()                                // Response documentation
                    .code(200).message("Success")
                    .endResponseMessage()
                .responseMessage()
                    .code(400).message("Invalid input")
                    .endResponseMessage()
                .to("direct:calculatePrice");

        // Main Route
        from("direct:calculatePrice")
            .routeId("json-mrp-price-calculation-route")
            .unmarshal(json(PriceRequest.class))
            .process(calculatePrice)
            .marshal(json(PriceResponse.class))
            .log("Processed MRP calculation:\n RESPONSE BODY: \n${body}");
    }
}