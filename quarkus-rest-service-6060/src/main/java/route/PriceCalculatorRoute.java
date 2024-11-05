package route;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.camel.builder.RouteBuilder;
import processor.PriceCalculatorService;

import static dataFormatFactory.JaxbDataFormatFactory.jaxb;

@ApplicationScoped
public class PriceCalculatorRoute extends RouteBuilder {

    @Inject
    PriceCalculatorService calculatorService;

    @Override
    public void configure() throws Exception {
        // Error handling route
        onException(Exception.class)
            .handled(true)
            .setBody(constant("Error processing price calculation request"))
            .log("Error occurred: ${exception.message}");

        // Main price calculation route
        from("direct:calculatePrice")
            .routeId("xml-mrp-price-calculation-route")
            .log("Received price calculation request: ${body}")
            .unmarshal(jaxb("com.xml.model", "classpath:xsd/mrpXmlScheme.xsd"))
//            .bean(calculatorService, "calculatePrice")
            .process(calculatorService)
            .marshal(jaxb("com.xml.model", "classpath:xsd/mrpXmlScheme.xsd"))
            .log("Calculated price response: \n RESPONSE BODY: \n${body}");
    }
}
