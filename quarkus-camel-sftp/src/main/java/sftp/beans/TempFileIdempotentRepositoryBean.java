package sftp.beans;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import org.apache.camel.spi.IdempotentRepository;
import org.apache.camel.support.processor.idempotent.FileIdempotentRepository;

import java.io.File;
import java.util.logging.Logger;

@ApplicationScoped
public class TempFileIdempotentRepositoryBean {

    private static final Logger logger= Logger.getLogger(TempFileIdempotentRepositoryBean.class.getSimpleName());
    String repoPath = System.getProperty("java.io.tmpdir")+"idempotentRepos/sftp-idempotent-repo.txt";

    @Produces
    public IdempotentRepository fileIdempotentRepository() {
        logger.info("Class: TempFileIdempotentRepositoryBean, Method: fileIdempotentRepository ---- STARTED");
        logger.info("Idempotent Repository Temp Path: " + repoPath);
        IdempotentRepository idempotentRepository = FileIdempotentRepository.fileIdempotentRepository(
                new File(repoPath),
                250,
                86400000
        );
        logger.info("Class: TempFileIdempotentRepositoryBean, Method: fileIdempotentRepository ---- EXECUTED");
        return idempotentRepository;
    }
}
