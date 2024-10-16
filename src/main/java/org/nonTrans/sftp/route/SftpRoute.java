package org.nonTrans.sftp.route;

import jakarta.enterprise.context.ApplicationScoped;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;

@ApplicationScoped
public class SftpRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        errorHandler(defaultErrorHandler()
                .logHandled(true)
                .maximumRedeliveries(3));
        downloadFromSftpToLocal();
        uploadFromLocalToSftp();
        intraSftpCommunication();
    }

    //IntraSFTP Communication
    private void intraSftpCommunication() {
        from("sftp://{{sftp.host}}:{{sftp.port}}/{{sftp.put}}"
                + "?username={{sftp.username}}"
                + "&password={{sftp.password}}"
                + "&delete=false"
                + "&localWorkDirectory=/tmp/sftp-work"
                + "&delay=60s"
                + "&stepwise=false"
                + "&reconnectDelay=5000"
                + "&maximumReconnectAttempts=5")
                .routeId("intra-sftp-route")
                .log(LoggingLevel.INFO, "Attempting to consume files from SFTP server")
                .log(LoggingLevel.INFO,"Connection details - Host: {{sftp.host}}, Port: {{sftp.port}}, Directory: {{sftp.put}}")
                .to("sftp://{{sftp.host}}:{{sftp.port}}/{{sftp.get}}"
                        + "?username={{sftp.username}}"
                        + "&password={{sftp.password}}"
                        + "&delete=false"
                        + "&localWorkDirectory=/tmp/sftp-work"
                        + "&delay=60s"
                        + "&stepwise=false"
                        + "&reconnectDelay=5000"
                        + "&maximumReconnectAttempts=5")
                .log(LoggingLevel.INFO, "File ${file:name} file received successfully")
                .log(LoggingLevel.INFO,"Connection details - Host: {{sftp.host}}, Port: {{sftp.port}}, Directory: {{sftp.get}}")
                .log(LoggingLevel.INFO, "Intra SFTP communication done successfully");
    }

    //To Upload Files to SFTP
    private void uploadFromLocalToSftp() {
        from("{{local.uploadable}}"
                + "?delete=false"
                + "&recursive=true"
                + "&noop=true"
                + "&delay=60s")
                .routeId("sftp-upload-route")
                .log(LoggingLevel.INFO, "Attempting to upload file ${file:name} to SFTP server from {{local.uploadable}}, body : ${body}")
                .log(LoggingLevel.INFO, "Connection details - Host: {{sftp.host}}, Port: {{sftp.port}}, Directory: {{local.tosftp.directory}}")
                .to("sftp://{{sftp.host}}:{{sftp.port}}/{{local.tosftp.directory}}"
                        + "?username={{sftp.username}}"
                        + "&password={{sftp.password}}"
                        + "&stepwise=false"
                        + "&reconnectDelay=5000"
                        + "&maximumReconnectAttempts=5")
                .log(LoggingLevel.INFO, "File ${file:name} uploaded successfully")
                .log(LoggingLevel.INFO, "SFTP upload operation completed successfully");
    }

    //To Download Files from SFTP
    private void downloadFromSftpToLocal() {
        from("sftp://{{sftp.host}}:{{sftp.port}}/{{sftp.toLocal.directory}}"
                + "?username={{sftp.username}}"
                + "&password={{sftp.password}}"
                + "&delete=false"
                + "&localWorkDirectory=/tmp/sftp-work"
                + "&delay=60s"
                + "&stepwise=false"
                + "&reconnectDelay=5000"
                + "&maximumReconnectAttempts=5")
                .routeId("sftp-download-route")
                .log(LoggingLevel.INFO, "Attempting to download files from SFTP server")
                .log(LoggingLevel.INFO,"Connection details - Host: {{sftp.host}}, Port: {{sftp.port}}, Directory: {{sftp.toLocal.directory}}")
                .log(LoggingLevel.INFO,"Downloaded file ${file:name} from SFTP server, body : ${body}")
                .to("{{local.downloads}}")
                .log(LoggingLevel.INFO,"Moved file ${file:name} to local directory: {{local.downloads}}")
                .log(LoggingLevel.INFO,"SFTP download completed successfully");
    }
}