package processor;

import com.example.weather.generated.GetWeatherResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.json.bind.JsonbBuilder;
import mock.WeatherSoapEndpoint;
import model.WeatherDto;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.logging.Logger;

@ApplicationScoped
public class WeatherResponseProcessor implements Processor {
    private static final Logger logger = Logger.getLogger(WeatherSoapEndpoint.class.getSimpleName());

    @Override
    public void process(Exchange exchange) throws Exception {
        logger.info("Class: WeatherResponseProcessor, Method: process ----- STARTED");
        GetWeatherResponse response = exchange.getIn().getBody(GetWeatherResponse.class);
        WeatherDto dto = new WeatherDto();
        dto.setTemperature(response.getTemperature());
        dto.setConditions(response.getConditions());
        dto.setHumidity(response.getHumidity());

        // Convert WeatherDto to JSON string
        String jsonString = JsonbBuilder.create().toJson(dto);
        exchange.getIn().setBody(jsonString);
        logger.info("Class: WeatherResponseProcessor, Method: process ----- EXECUTED");
    }
}
