package processor;

import com.example.weather.generated.GetWeatherRequest;
import jakarta.enterprise.context.ApplicationScoped;
import mock.WeatherSoapEndpoint;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.logging.Logger;

@ApplicationScoped
public class WeatherRequestProcessor implements Processor {
    private static final Logger logger = Logger.getLogger(WeatherSoapEndpoint.class.getSimpleName());

    @Override
    public void process(Exchange exchange) throws Exception {
        logger.info("Class: WeatherRequestProcessor, Method: process ----- STARTED");
        GetWeatherRequest request = new GetWeatherRequest();
        request.setCity(exchange.getIn().getHeader("city", String.class));
        request.setCountry(exchange.getIn().getHeader("country", String.class));
        exchange.getIn().setBody(request);
        logger.info("Class: WeatherRequestProcessor, Method: process ----- EXECUTED");
    }
}
