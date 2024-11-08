package mock;

import com.example.weather.generated.GetWeatherRequest;
import com.example.weather.generated.GetWeatherResponse;
import com.example.weather.generated.WeatherPortType;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.ws.rs.Path;

import java.math.BigDecimal;
import java.util.logging.Logger;

@WebService(
        portName = "WeatherPortType",
        serviceName = "WeatherService",
        targetNamespace = "http://weather.example.com/"
)
@Path("/weatherService")
@ApplicationScoped
public class WeatherSoapEndpoint implements WeatherPortType {
    private static final Logger logger = Logger.getLogger(WeatherSoapEndpoint.class.getSimpleName());

    @WebMethod
    @Override
    public GetWeatherResponse getWeather(GetWeatherRequest parameters) {
        logger.info("Class: WeatherSoapEndpoint, Method: getWeather ----- STARTED");
        GetWeatherResponse response = new GetWeatherResponse();
        response.setTemperature(BigDecimal.valueOf(20.5));
        response.setConditions("Partly Cloudy");
        response.setHumidity(BigDecimal.valueOf(65.0));
        logger.info("Class: WeatherSoapEndpoint, Method: getWeather ----- EXECUTED");
        return response;
    }
}