//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.autenticazione.testcredenziali.service;

import com.autenticazione.testcredenziali.controller.LoginController;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import org.apache.log4j.Logger;
import org.docx4j.Docx4J;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DocxToPdfConverter {
    private static final Logger logger = Logger.getLogger(LoginController.class);
    @Autowired
    private ZipFolder zipFolder;
    @Value("${export.path}")
    private String path;

    public DocxToPdfConverter() {
    }

    public void convert(String username, String transactionId) {
        try {
            InputStream templateInputStream = new FileInputStream(this.path + "\\tabella_new" + this.zipFolder.time + "-" + username + ".docx");
            WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(templateInputStream);
            MainDocumentPart documentPart = wordMLPackage.getMainDocumentPart();
            String outputfilepath = this.path + "\\tabella_new" + this.zipFolder.time + "-" + username + ".pdf";
            System.out.println(outputfilepath);
            FileOutputStream os = new FileOutputStream(outputfilepath);
            if (os != null) {
                System.out.println("os");
            }

            Docx4J.toPDF(wordMLPackage, os);
            os.flush();
            os.close();
            logger.info(transactionId + "pdf file conversion success");
        } catch (Throwable var8) {
            logger.error(transactionId + "pdf file conversion failed");
            var8.printStackTrace();
        }

    }
}
