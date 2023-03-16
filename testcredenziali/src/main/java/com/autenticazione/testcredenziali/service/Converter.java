//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.autenticazione.testcredenziali.service;

import com.autenticazione.testcredenziali.controller.LoginController;
import com.documents4j.api.DocumentType;
import com.documents4j.api.IConverter;
import com.documents4j.job.LocalConverter;
import java.io.File;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class Converter {
    private static final Logger logger = Logger.getLogger(LoginController.class);
    @Value("${export.path}")
    private String path;
    @Autowired
    ZipFolder zipFolder;

    public Converter() {
    }

    public void convert(String username, String transactionId) throws InterruptedException, ExecutionException {
        System.out.println("converter");
        File docxFile = new File(this.path + "\\tabella_new" + this.zipFolder.time + "-" + username + ".docx");
        File target = new File(this.path + "\\tabella_new" + this.zipFolder.time + "-" + username + ".pdf");
        IConverter converter = ((LocalConverter.Builder)((LocalConverter.Builder)LocalConverter.builder().baseFolder(new File(this.path))).workerPool(20, 25, 2L, TimeUnit.SECONDS)).processTimeout(30L, TimeUnit.SECONDS).build();
        Future<Boolean> conversion = converter.convert(docxFile).as(DocumentType.MS_WORD).to(target).as(DocumentType.PDF).prioritizeWith(1000).schedule();
        if ((Boolean)conversion.get()) {
            System.out.println("File PDF creato");
            logger.info(transactionId + "pdf file conversion success:  " + target.getName());
        } else {
            logger.info(transactionId + "pdf file conversion failed:  " + target.getName());
            System.out.println("Errore creazione PDF");
        }

    }
}
