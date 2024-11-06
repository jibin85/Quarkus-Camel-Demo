package processor;

import emailRequest.EmailRequest;
import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.mail.util.ByteArrayDataSource;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.attachment.AttachmentMessage;

import java.util.logging.Logger;

@ApplicationScoped
public class EmailAttachmentProcessor implements Processor {
    private static final Logger logger = Logger.getLogger(EmailAttachmentProcessor.class.getSimpleName());
    @Override
    public void process(Exchange exchange) throws Exception {
        logger.info("Class: EmailAttachmentProcessor, Method: process ----- STARTED");
        EmailRequest request = exchange.getIn().getBody(EmailRequest.class);

        // Set email headers
        exchange.getIn().setHeader("subject", request.getSubject());
        exchange.getIn().setHeader("to", request.getTo());

        // Create attachment
        AttachmentMessage attMsg = exchange.getMessage(AttachmentMessage.class);

        // Create ByteArrayDataSource first
        DataSource dataSource = new ByteArrayDataSource(
                request.getAttachmentContent(),
                request.getAttachmentContentType()
        );

        // Create DataHandler with the DataSource
        DataHandler attachment = new DataHandler(dataSource);

        // Add attachment to message
        attMsg.addAttachment(request.getAttachmentName(), attachment);

        // Set email content
        exchange.getIn().setBody(request.getContent());
        logger.info("Class: EmailAttachmentProcessor, Method: process ----- EXECUTED");
    }
}
