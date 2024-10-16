package org.transformation.route.xml_fl_concepts;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.transformation.processor.InsuranceMappingFL;
import org.transformation.processor.PojoChecker;

import static org.transformation.dataFormat.BeanIODataFormatFactory.beanio;
import static org.transformation.dataFormat.JAXBDataFormatFactory.jaxb;

@ApplicationScoped
public class InsuranceRoute extends RouteBuilder {
    @Inject
    InsuranceMappingFL insuranceMappingFL;

    @Override
    public void configure() throws Exception {
        onException(Exception.class)
            .log("Defnition: onException, Description: ExceptionCaught")
            .handled(true)
            .maximumRedeliveries(2)
            .to("direct:errorHandlerRoute");


        from("{{insuranceSource}}")
            .routeId("insurance-route")
            .log(LoggingLevel.INFO, "---------- STARTS ----------")
            .unmarshal(beanio("insuranceRequestStream", "classpath:beanio/insurance-mappings.xml"))
            .log(LoggingLevel.INFO, "Unmarshalled Successfully! body: ${body}")
            .multicast().parallelProcessing()
                .to("direct:toXmlEndpoint","direct:toFixedLengthEndpoint")
            .end()
            .log(LoggingLevel.INFO, "XML and FL delivered Successfully!")
            .log(LoggingLevel.INFO, "------------ ENDS ------------");
        
        from("direct:toXmlEndpoint")
            .routeId("insurance-endpoint1-route")
                    .log(LoggingLevel.INFO, "XML ROUTE BEING DEBUGGED!");
//            .log(LoggingLevel.INFO, "---------- STARTS ----------")
//            .process("insuranceMappingXML")
//            .marshal(jaxb("com.insurance.xmlendpoint","classpath:xsd/validClaimSchema.xsd"))
//            .log(LoggingLevel.INFO, "Marshalled Successfully!")
//            .setHeader(Exchange.FILE_NAME, simple("${file:name.noext}_ResXML.${file:name.ext}"))
//            .to("{{xmlDestination}}")
//            .log(LoggingLevel.INFO, "${header.CamelFileName} successfully placed under {{xmlDestination}}")
//            .log(LoggingLevel.INFO, "------------ ENDS ------------");
                
        from("direct:toFixedLengthEndpoint")
            .routeId("insurance-endpoint2-route")
//            .log(LoggingLevel.INFO, "---------- STARTS ----------")
//            .process(insuranceMappingFL)
//                .log(LoggingLevel.INFO, "Processed Successfully!")
//            .marshal(beanio("insuranceResponseStream", "classpath:beanio/insurance-mappings.xml"))
//            .log(LoggingLevel.INFO, "Marshalled Successfully!")
//            .setHeader(Exchange.FILE_NAME, simple("${file:name.noext}_ResFL.${file:name.ext}"))
            .to("{{fixedLengthDestination}}");
//            .log(LoggingLevel.INFO, "${header.CamelFileName} successfully placed under {{fixedLengthDestination}}")
//            .log(LoggingLevel.INFO, "------------ ENDS ------------");
    }
}
