package route;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import processor.WeatherRequestProcessor;
import processor.WeatherResponseProcessor;

@ApplicationScoped
public class WeatherServiceRoute extends RouteBuilder {

    @Inject
    WeatherRequestProcessor weatherRequestProcessor;

    @Inject
    WeatherResponseProcessor weatherResponseProcessor;

    @Override
    public void configure() throws Exception {
        // Error handling
        onException(Exception.class)
                .handled(true)
                .log("Error processing request: ${exception.message}")
                .setBody(simple("{\"error\": \"${exception.message}\"}"));

        // REST endpoint
        rest("/weather")
                .get("/{city}/{country}")
                .to("direct:getWeather");

        // Main route
        from("direct:getWeather")
                .routeId("rest-soap-integration-route")
                .log("Fetching weather for ${header.city}, ${header.country}")
                .process(weatherRequestProcessor)
                .to("{{cxf.endpoint}}")
                .process(weatherResponseProcessor)
                .setHeader(Exchange.CONTENT_TYPE, constant("application/json"))
                .log("Weather data processed successfully: \n RESPONSE BODY: \n${body}");
    }
}