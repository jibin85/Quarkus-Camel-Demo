package org.nonTrans;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;

public class SimpleFileMovement extends RouteBuilder {

    private static final String SOURCE = "{{source}}";
    private static final String DESTINATION = "{{destination}}";

    @Override
    public void configure() throws Exception {
        simpleSourceToDestination();

    }

    private void simpleSourceToDestination() {
        from(SOURCE)
                .log(LoggingLevel.INFO, "Picked up file!")
                .log(LoggingLevel.INFO, "Incoming File is  : ${file:name}")
                .routeId("Simple-File-Movement-Route")
                .to(DESTINATION)
                .log(LoggingLevel.INFO, "Placed file! File Body: ${body}");
    }
}
