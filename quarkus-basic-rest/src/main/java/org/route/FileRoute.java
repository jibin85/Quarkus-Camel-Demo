package org.route;

import jakarta.enterprise.context.ApplicationScoped;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;

@ApplicationScoped
public class FileRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        // Route to consume messages and store them in a file
        from("direct:storeToFile")
            .log("Storing data: ${body}")
            .setHeader(Exchange.FILE_NAME, simple("output-${date:now:yyyyMMddHHmmss}.txt"))
            .to("{{file.output.directory}}");
    }
}
