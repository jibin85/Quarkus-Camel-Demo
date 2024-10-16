/*
package org.transformation.route;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.transformation.processor.MyAggregationStrategy;

public class MulticastAndPipelining extends RouteBuilder {

    private static final String SOURCE = "{{source}}";
    private static final String DESTINATION = "{{destination}}";
    private static final String DESTINATION1 = "file:///C:/Preparations/Archived_Project/apache-camel-demo/src/test/LocalFileUtility/Output1/";
    private static final String DESTINATION2 = "file:///C:/Preparations/Archived_Project/apache-camel-demo/src/test/LocalFileUtility/Output2/";
    
    @Override
    public void configure() throws Exception {
        from("direct:processFile")
            .routeId("file-processing-route")
            .log(LoggingLevel.INFO, "Processing file: ${header.CamelFileName}")
            .process(exchange -> {
                // Custom processing logic
                String body = exchange.getIn().getBody(String.class);
                exchange.getIn().setBody(body.toUpperCase()); // Example processing
            })
            .to(DESTINATION1)
            .log(LoggingLevel.INFO, "File written to destination1: ${header.CamelFileName}");

        from("direct:backupFile")
            .routeId("file-backup-route")
            .log(LoggingLevel.INFO, "Backing up file: ${header.CamelFileName}")
            .process(exchange -> {
                // Custom processing logic
                exchange.getIn().setBody("Modified Content via Pipeline processing"); // Example processing
            })
            .to(DESTINATION2)
            .log(LoggingLevel.INFO, "File Modified and sent to destination2: ${header.CamelFileName}");

        from("direct:processFile1")
            .routeId("file-process1-route")
            .log(LoggingLevel.INFO, "Processing file in processFile1: ${header.CamelFileName}")
            .process(exchange -> {
                String body = exchange.getIn().getBody(String.class);
                exchange.getIn().setBody(body + " - Processed by processFile1"); // Example processing
            });

        from("direct:processFile2")
            .routeId("file-process2-route")
            .log(LoggingLevel.INFO, "Processing file in processFile2: ${header.CamelFileName}")
            .process(exchange -> {
                String body = exchange.getIn().getBody(String.class);
                exchange.getIn().setBody(body + " - Processed by processFile2"); // Example processing
            });

        from("direct:processFile3")
            .routeId("file-process3-route")
            .log(LoggingLevel.INFO, "Processing file in processFile3: ${header.CamelFileName}")
            .process(exchange -> {
                String body = exchange.getIn().getBody(String.class);
                exchange.getIn().setBody(body + " - Processed by processFile3"); // Example processing
            });
        
        simpleMulticast();
        parallelProcessing();
        simplePipeline();
        aggregationStrategy();
    }

    private void simpleMulticast() {
        from(SOURCE + "Multicast/{{particularFileFilter}}")
                .routeId("simple-multicast-route1")
                .process(exchange -> {
                    exchange.getIn().setHeader("startTime", System.currentTimeMillis());
                })
                .multicast()
                .to(DESTINATION, DESTINATION1, DESTINATION2)
                .end()
                .process(exchange -> {
                    long startTime = exchange.getIn().getHeader("startTime", Long.class);
                    long processingTime = System.currentTimeMillis() - startTime;
                    exchange.getIn().setHeader("processingTime", processingTime);
                })
                .log("Processing time: ${header.processingTime} ms!");
    }

    private void parallelProcessing() {
        from(SOURCE + "Multicast/?fileName=Sample1.txt")
                .routeId("parallel-processing-route")
                .process(exchange -> {
                    exchange.getIn().setHeader("startTime", System.currentTimeMillis());
                })
                .multicast()
                .parallelProcessing()
                .to(DESTINATION, DESTINATION1, DESTINATION2)
                .end()
                .process(exchange -> {
                    long startTime = exchange.getIn().getHeader("startTime", Long.class);
                    long processingTime = System.currentTimeMillis() - startTime;
                    exchange.getIn().setHeader("processingTime", processingTime);
                })
                .log("Processing time: ${header.processingTime} ms!");
    }

    private void simplePipeline() {
        from(SOURCE + "Multicast/?fileName=Sample2.txt")
            .routeId("file-consumer-route")
            .pipeline()
                .to("direct:processFile")
                .to("direct:backupFile");
    }

    private void aggregationStrategy() {
        from(SOURCE + "Multicast/?fileName=aggregationSample.txt")
            .routeId("file-aggregation-route")
            .multicast(new MyAggregationStrategy()) // Use custom aggregation strategy
                .to("direct:processFile1")
                .to("direct:processFile2")
                .to("direct:processFile3")
            .end()
        .to(DESTINATION); // Final destination after aggregation
    }
}
*/
