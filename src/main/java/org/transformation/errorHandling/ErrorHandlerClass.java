package org.transformation.errorHandling;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;

public class ErrorHandlerClass extends RouteBuilder {
    @Override
    public void configure() throws Exception {
        from("direct:errorHandlerRoute")
                .routeId("error-handler-route")
                .setHeader(Exchange.FILE_NAME, simple("${file:name.noext}_${date:now:yyyyMMddhhmmss}.${file:name.ext}"))
                .to("{{errorFolder}}")
                .log(LoggingLevel.INFO, "File Moved to Error Folder, Path: {{errorFolder}}");
    }
}