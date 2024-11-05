package processor;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import model.PriceRequest;
import model.PriceResponse;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.logging.Logger;

@Slf4j
@ApplicationScoped
public class MrpCalculator implements Processor {
    private static final Logger logger = Logger.getLogger(MrpCalculator.class.getSimpleName());

    @Override
    public void process(Exchange exchange) throws Exception {
        logger.info("Class: MrpCalculator, Method: process ----- STARTED");
        PriceRequest request = exchange.getMessage().getBody(PriceRequest.class);
        PriceResponse response = calculatePrice(request);
        exchange.getIn().setBody(response);
        logger.info("Class: MrpCalculator, Method: process ----- EXECUTED");
    }

    private PriceResponse calculatePrice(PriceRequest request) {
        logger.info("Class: MrpCalculator, Method: calculatePrice ----- STARTED");
        double markupPercentage = 40.00;
        Double mrpPercentage = request.getOriginalRate()*(markupPercentage /100);
        Double mrpValue = request.getOriginalRate() + mrpPercentage;
        logger.info("Class: MrpCalculator, Method: calculatePrice ----- EXECUTED");
        return PriceResponse.builder()
                .originalRate(request.getOriginalRate())
                .addition(mrpPercentage)
                .mrp(mrpValue)
                .build();
    }
}

