package org.transformation.processor;

import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;

public class MyAggregationStrategy implements AggregationStrategy {

    @Override
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        if (oldExchange == null) {
            // First message, return as the result
            return newExchange;
        }

        // Combine the bodies of both exchanges
        String oldBody = oldExchange.getIn().getBody(String.class);
        String newBody = newExchange.getIn().getBody(String.class);

        // Concatenate the results (example aggregation logic)
        String combinedBody = oldBody + "\n" + newBody + "\n";

        // Set the combined body in the old exchange (to be returned as the result)
        oldExchange.getIn().setBody(combinedBody);
        return oldExchange;
    }
}
