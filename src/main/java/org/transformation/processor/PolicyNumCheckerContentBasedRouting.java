package org.transformation.processor;

import org.apache.camel.Configuration;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.Objects;


@Configuration("policyNumChecker")
public class PolicyNumCheckerContentBasedRouting implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        exchange.setProperty("isValid", false);
        if (Objects.nonNull(exchange.getIn().getBody())) {
            String policyNum = exchange.getIn().getBody(String.class);
            System.out.println("Body of content before modifying: "+policyNum);
            if (Objects.nonNull(policyNum) && Long.valueOf(policyNum) > 7500000) {
                exchange.setProperty("isValid", true);
            }
        } else {
            System.out.println("Body is null");
        }
    }
}
