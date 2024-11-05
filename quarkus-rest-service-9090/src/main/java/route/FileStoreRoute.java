package route;

import jakarta.enterprise.context.ApplicationScoped;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;

import java.io.IOException;

@ApplicationScoped
public class FileStoreRoute extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        onException(IOException.class)
                .handled(true)
                .log(LoggingLevel.ERROR, "Failed to write to file: ${exception.message}")
                .end();

        // Route to consume messages and store them in a file
        from("direct:storeToFile")
            .routeId("rest-file-data-storage-route")
            .setBody(exchange -> {
                String template = "${date:now:HH:mm:ss.SSS} | Message ID: ${exchangeId} | Content: ${body}\n";
                return exchange.getContext().resolveLanguage("simple")
                        .createExpression(template)
                        .evaluate(exchange, String.class);
            })
            .log("Storing data: ${body}")
            .setHeader(Exchange.FILE_NAME, simple("output-${date:now:yyyyMMdd}.txt"))
            .to("{{file.output.directory}}");
    }
}
