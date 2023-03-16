//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.autenticazione.testcredenziali.service;

import com.autenticazione.testcredenziali.controller.LoginController;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ZipFolder {
    private static final Logger logger = Logger.getLogger(LoginController.class);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");
    public String time;
    @Value("${export.path}")
    private String path;

    public ZipFolder() {
    }

    public void zipFolder(String username, String transactionId) {
        System.out.println("zipfolder");
        LocalDateTime now = LocalDateTime.now();
        this.time = now.format(this.formatter).toString();
        String srcFolder = this.path + "\\tabella";
        String destZipFile = this.path + "\\tabella_new.zip";
        String docxFile = this.path + "\\tabella_new" + this.time + "-" + username + ".docx";

        try {
            FileOutputStream fos = new FileOutputStream(destZipFile);
            ZipOutputStream zos = new ZipOutputStream(fos);
            this.zipDir(srcFolder, zos, transactionId);
            zos.close();
            File zipFile = new File(destZipFile);
            File docx = new File(docxFile);
            zipFile.renameTo(docx);
            logger.info(transactionId + " zipFolder success");
        } catch (IOException var11) {
            logger.error(transactionId + " zipFolder failed");
            var11.printStackTrace();
        }

    }

    public void zipDir(String srcFolder, ZipOutputStream zos, String transactionId) throws IOException {
        try {
            File srcFile = new File(srcFolder);
            if (srcFile.isDirectory()) {
                String[] files = srcFile.list();

                for(int i = 0; i < files.length; ++i) {
                    File f = new File(srcFile, files[i]);
                    this.zipDir(f.getAbsolutePath(), zos, f.getAbsolutePath().substring(srcFolder.length() + 1), transactionId);
                }
            } else {
                byte[] buffer = new byte[1024];
                FileInputStream fis = new FileInputStream(srcFile);
                zos.putNextEntry(new ZipEntry(srcFile.getName()));

                int length;
                while((length = fis.read(buffer)) > 0) {
                    zos.write(buffer, 0, length);
                }

                zos.closeEntry();
                fis.close();
            }
        } catch (Exception var8) {
        }

    }

    public void zipDir(String srcFolder, ZipOutputStream zos, String basePath, String transactionId) throws IOException {
        try {
            File srcFile = new File(srcFolder);
            if (srcFile.isDirectory()) {
                String[] files = srcFile.list();

                for(int i = 0; i < files.length; ++i) {
                    File f = new File(srcFile, files[i]);
                    this.zipDir(f.getAbsolutePath(), zos, basePath + "/" + f.getName(), transactionId);
                }
            } else {
                byte[] buffer = new byte[1024];
                FileInputStream fis = new FileInputStream(srcFile);
                zos.putNextEntry(new ZipEntry(basePath));

                int length;
                while((length = fis.read(buffer)) > 0) {
                    zos.write(buffer, 0, length);
                }

                zos.closeEntry();
                fis.close();
            }
        } catch (Exception var9) {
        }

    }
}
