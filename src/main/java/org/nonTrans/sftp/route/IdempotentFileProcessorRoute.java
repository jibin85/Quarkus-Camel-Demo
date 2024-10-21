package org.nonTrans.sftp.route;

import jakarta.enterprise.context.ApplicationScoped;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.support.processor.idempotent.FileIdempotentRepository;

import java.io.File;

@ApplicationScoped
public class IdempotentFileProcessorRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        // Define the Camel route
        from("{{idempotentRepoSource}}")
                .idempotentConsumer(
                    simple("FILE_NAME : ${file:name} ------- FILE_SIZE : ${file:size} ------- LAST_MODIFIED : ${file:modified}"),  // Use filename and size as the idempotent key
                    FileIdempotentRepository.fileIdempotentRepository(
                            new File("idempotent-repo.txt"),  // Directory to store the repository
                            250,                          // Maximum cache size (in-memory)
                            86400000                      // Maximum file age (in milliseconds, here set to 24 hours)
                    )
                )
                .log("Registered on Idempotent in-memory repository in idempotent-repo.xlsx at D://HandsOn/quarkus-camel-demo/")
                .log("Processing file: ${header.CamelFileName}")
                .to("{{idempotentRepoDestination}}")
                .log("File moved to output folder")
                .end();
    }
}