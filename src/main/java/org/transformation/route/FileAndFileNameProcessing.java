/*
package org.transformation.route;

import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;

public class FileAndFileNameProcessing extends RouteBuilder {

    private static final String SOURCE = "{{source}}";
    private static final String DESTINATION = "{{destination}}";
    private static final String DESTINATION1 = "file:///C:/Preparations/Archived_Project/apache-camel-demo/src/test/LocalFileUtility/Output1/";
    
    @Override
    public void configure() throws Exception {
        readFileContent();
        alterFileContent();
        contentBasedRouting();
        toDRouting();
        
        from("direct:route3")
            .routeId("route3")
            .to(DESTINATION)
            .log(LoggingLevel.INFO, "Placed under Output Folder!");

        from("direct:route2")
            .routeId("route2")
            .to(DESTINATION1)
            .log(LoggingLevel.INFO, "Placed under Output1 Folder!");

        from("direct:invalidRouteIndicator")
            .routeId("invalid-route")
            .log(LoggingLevel.INFO, "Route Indicator is Invalid!");
    }

    private void readFileContent() {
        from(SOURCE + "transformation/{{particularFileFilter}}")
          .routeId("File-Read-Route")
          .log(LoggingLevel.INFO, "The file Content from ${file:name} is: ${body}");
    }

    private void alterFileContent() {
        from(SOURCE + "transformation/changeBody/{{particularFileFilter}}")
          .routeId("File-Write-Route")
          .log(LoggingLevel.INFO, "The file Content from ${file:name} is: ${body}")
          .setBody(
                exchange -> {
                    exchange.getIn().setBody("The Content is Changed");
                    return exchange.getIn().getBody();
                }
                )
          .log(LoggingLevel.INFO, "After Modification: ${body}")
        .to(DESTINATION)
          .log(LoggingLevel.INFO, "Placed file Succesfully");
    }

    private void contentBasedRouting() {
        from(SOURCE+"transformation/contentBasedRouting/{{particularFileFilter}}")
          .routeId("Content-based-route")
          .process("policyNumChecker")
          .log(LoggingLevel.INFO, "Processed Successfully")
          .choice()
            .when(exchangeProperty("isValid"))
              .setHeader(Exchange.FILE_NAME, simple("${file:name.noext}_${date:now:yyyyMMddhhmmss}.${file:name.ext}"))
              .to(DESTINATION)
              .log(LoggingLevel.INFO, "File Arrived at Output folder")  
            .otherwise()
              .setHeader(Exchange.FILE_NAME, simple("Modified_File_Name.${file:name.ext}"))
              .to(DESTINATION1)
              .log(LoggingLevel.INFO, "File Arrived at Output1 folder")
        .log(LoggingLevel.INFO, "Placed file Succesfully in appropriate Destinations!");
    }

    private void toDRouting() {
        from(SOURCE + "transformation/toDRoute/{{particularFileFilter}}")
            .routeId("To-D-Route")
            .log(LoggingLevel.INFO, "The file Content from ${file:name} is: ${body}")
            .process("routeChecker")
            .toD("direct:${exchangeProperty.routeValue}");
    }
}
*/
