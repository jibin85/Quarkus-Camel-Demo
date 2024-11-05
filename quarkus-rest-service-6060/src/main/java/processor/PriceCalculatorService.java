package processor;

import com.xml.model.PriceRequest;
import com.xml.model.PriceResponse;
import jakarta.enterprise.context.ApplicationScoped;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.logging.Logger;


@ApplicationScoped
public class PriceCalculatorService implements Processor {
    private static final Logger logger = Logger.getLogger(PriceCalculatorService.class.getSimpleName());

    @Override
    public void process(Exchange exchange) throws Exception {
        logger.info("Class: PriceCalculatorService, Method: process ----- STARTED");
        PriceRequest request = exchange.getMessage().getBody(PriceRequest.class);
        PriceResponse response = calculatePrice(request);
        exchange.getIn().setBody(response);
        logger.info("Class: PriceCalculatorService, Method: process ----- EXECUTED");
    }

    private PriceResponse calculatePrice(PriceRequest request) {
        logger.info("Class: PriceCalculatorService, Method: calculatePrice ----- STARTED");
        PriceResponse response = new PriceResponse();
        BigDecimal markupPercentage = BigDecimal.valueOf(40.00);
        BigDecimal mrpPercentage = request.getOriginalPrice().multiply(markupPercentage.divide(BigDecimal.valueOf(100), RoundingMode.HALF_UP)).setScale(2, RoundingMode.HALF_UP);
        BigDecimal originalPrice = request.getOriginalPrice();
        BigDecimal mrpRate = originalPrice.add(mrpPercentage);
        BigDecimal addition = mrpRate.subtract(originalPrice);
        response.setOriginalRate(originalPrice);
        response.setMrpRate(mrpRate);
        response.setAddition(addition);
        logger.info("Class: PriceCalculatorService, Method: calculatePrice ----- EXECUTED");
        return response;
    }
}

