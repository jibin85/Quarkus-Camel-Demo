package org.nonTrans.sftp.route;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.spi.IdempotentRepository;
import org.apache.camel.support.processor.idempotent.FileIdempotentRepository;
import org.nonTrans.TempFileIdempotentRepositoryBean;

import java.io.File;
import java.io.IOException;

@ApplicationScoped
public class SftpRoute extends RouteBuilder {

    @Inject
    TempFileIdempotentRepositoryBean tempFileIdempotentRepositoryBean;

    @Override
    public void configure() throws Exception {
        errorHandler(defaultErrorHandler()
                .logHandled(true)
                .maximumRedeliveries(3));
        downloadFromSftpToLocal();
        uploadFromLocalToSftp();
        intraSftpCommunication();
    }

    //To Download Files from SFTP
    private void downloadFromSftpToLocal() {
        from("{{sftp.toLocal.directory}}")
                .routeId("sftp-download-route")
                .idempotentConsumer(simple("FILE_NAME : ${file:name} ------- FILE_SIZE : ${file:size} bytes ------- LAST_MODIFIED : ${file:modified}"), tempFileIdempotentRepositoryBean.fileIdempotentRepository())
                .log(LoggingLevel.INFO,"Attempting to download files from SFTP server")
                .log(LoggingLevel.INFO,"Connection details \n Host: {{imss.server.ip}}, \n Port: {{imss.server.port}}, \n Directory: {{sftp.toLocal.directory}}")
                .to("{{local.downloads}}")
                .log(LoggingLevel.INFO,"Downloaded file ... \n FILE Name : ${file:name}, \n FILE PATH : {{local.downloads}}, \n FILE CONTENT : \n\n ${body}\n")
                .log(LoggingLevel.INFO,"SFTP download completed successfully");
    }

    //To Upload Files to SFTP
    private void uploadFromLocalToSftp() {
        from("{{local.uploadable}}")
                .routeId("sftp-upload-route")
                .log(LoggingLevel.INFO,"Attempting to upload \n FILE NAME : ${file:name} \n FILE PATH : {{local.uploadable}} \n FILE CONTENT : \n\n ${body}\n")
                .log(LoggingLevel.INFO,"Connection details \n Host: {{imss.server.ip}}, \n Port: {{imss.server.port}}, \n Directory: {{local.tosftp.directory}}")
                .to("{{local.tosftp.directory}}")
                .log(LoggingLevel.INFO,"${file:name} uploaded successfully")
                .log(LoggingLevel.INFO,"SFTP upload operation completed successfully");
    }

    //IntraSFTP Communication
    private void intraSftpCommunication() {
        from("{{sftp.put}}")
                .routeId("intra-sftp-route")
                .log(LoggingLevel.INFO,"Attempting to consume files from SFTP server")
                .log(LoggingLevel.INFO,"Input SFTP File Details \n Host: {{imss.server.ip}}, \n Port: {{imss.server.port}}, \n Directory: {{sftp.put}}, \n FILE CONTENT : \n\n ${body}\n")
                .to("{{sftp.get}}")
                .log(LoggingLevel.INFO,"FILE NAME : ${file:name} -- received successfully")
                .log(LoggingLevel.INFO,"Output SFTP File Details \n Host: {{imss.server.ip}}, \n Port: {{imss.server.port}}, \n Directory: {{sftp.get}}, \n FILE CONTENT : \n\n ${body}\n")
                .log(LoggingLevel.INFO,"Intra SFTP communication done successfully");
    }
}