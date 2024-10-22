package sftp.route;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.support.processor.idempotent.FileIdempotentRepository;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.io.File;

@ApplicationScoped
public class IdempotentFileRoute extends RouteBuilder {

    @Inject
    @ConfigProperty(name = "idempotentReference")
    private String idempotentReference;

    @Override
    public void configure() throws Exception {
        // Define the Camel route
        from("{{idempotentRepoSource}}")
            .routeId("simple-idempotent-repo-route")
            .idempotentConsumer(
                simple("FILE_NAME : ${file:name} ------- FILE_SIZE : ${file:size} bytes------- LAST_MODIFIED : ${file:modified}"),  // Use filename and size as the idempotent key
                FileIdempotentRepository.fileIdempotentRepository(
                        new File(idempotentReference),  // Directory to store the repository
                        250,                          // Maximum cache size (in-memory)
                        86400000                      // Maximum file age (in milliseconds, here set to 24 hours)
                )
            )
            .log("Registered \n ${header.CamelFileName} on Idempotent in-memory repository in \n {{idempotentRepoFileName}} at \n {{idempotentRepoFilePath}}")
            .to("{{idempotentRepoDestination}}")
            .log("File moved to output folder")
            .end();
    }
}