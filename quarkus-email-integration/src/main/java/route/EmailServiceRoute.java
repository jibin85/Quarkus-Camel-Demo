package route;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import processor.EmailAttachmentProcessor;

@ApplicationScoped
public class EmailServiceRoute extends RouteBuilder {

    @Inject
    EmailAttachmentProcessor emailAttachmentProcessor;

    @Override
    public void configure() throws Exception {
        // Route for sending plain text emails
        from("direct:sendTextEmail")
            .routeId("text-mail-route")
            .setHeader("subject", simple("${body.subject}"))
            .setHeader("to", simple("${body.to}"))
            .setBody(simple("${body.content}"))
            .to("{{gmail.destination}}")
            .log(LoggingLevel.INFO, "Successfully sent plainText mail to {{quarkus.camel.mail.username}}");

        // Route for sending HTML emails
        from("direct:sendHtmlEmail")
            .routeId("html-mail-route")
            .setHeader("subject", simple("${body.subject}"))
            .setHeader("to", simple("${body.to}"))
            .setHeader("content-type", constant("text/html"))
            .setBody(simple("${body.content}"))
            .to("{{gmail.destination}}")
            .log(LoggingLevel.INFO, "Successfully sent HTML mail to {{quarkus.camel.mail.username}}");

        // Route for sending emails with attachments
        from("direct:sendEmailWithAttachment")
            .routeId("attachment-mail-route")
            .process(emailAttachmentProcessor)
            .to("{{gmail.destination}}")
            .log(LoggingLevel.INFO, "Successfully sent mail with attachment to {{quarkus.camel.mail.username}}");
    }
}