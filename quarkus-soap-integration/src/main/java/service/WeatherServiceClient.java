package service;

import com.example.weather.generated.GetWeatherRequest;
import com.example.weather.generated.GetWeatherResponse;
import com.example.weather.generated.WeatherPortType;
import jakarta.xml.ws.Service;

import javax.xml.namespace.QName;
import java.net.URL;
import java.util.logging.Logger;

public class WeatherServiceClient {
    private static final Logger logger = Logger.getLogger(WeatherServiceClient.class.getSimpleName());
    public static void main(String[] args) {
        try {
            logger.info("Class: WeatherServiceClient, Method: process ----- STARTED");
            // Create URL for WSDL
            URL wsdlUrl = new URL("http://localhost:8686/services/weather?wsdl");

            // Create QName for service and port
            QName serviceQName = new QName("http://weather.example.com/", "WeatherService");
            QName portQName = new QName("http://weather.example.com/", "WeatherPort");

            // Create service instance
            Service service = Service.create(wsdlUrl, serviceQName);

            // Get service interface
            WeatherPortType weatherService = service.getPort(portQName, WeatherPortType.class);

            // Create request object
            GetWeatherRequest request = new GetWeatherRequest();
            request.setCity("London");
            request.setCountry("UK");

            // Call web service
            GetWeatherResponse response = weatherService.getWeather(request);

            // Process response
            logger.info("Temperature: " + response.getTemperature());
            logger.info("Conditions: " + response.getConditions());
            logger.info("Humidity: " + response.getHumidity());
            logger.info("Class: WeatherServiceClient, Method: process ----- EXECUTED");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}