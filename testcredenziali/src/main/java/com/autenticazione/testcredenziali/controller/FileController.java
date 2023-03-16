//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.autenticazione.testcredenziali.controller;

import com.autenticazione.testcredenziali.repository.NotificationRepository;
import jakarta.servlet.MultipartConfigElement;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.UUID;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

@RestController
@CrossOrigin
public class FileController {
    private static ThreadLocal<String> transactionId = new ThreadLocal();
    private static final Logger logger = Logger.getLogger(LoginController.class);
    @Autowired
    private MultipartConfigElement multipartConfig;
    @Autowired
    private NotificationRepository notificationRepository;
    @Value("${upload.path}")
    private String uploadPath;
    private static final String FILE_DIRECTORY = "incoming-files";

    public FileController() {
    }

    @RequestMapping(
            value = {"/download/{fileName}"},
            method = {RequestMethod.POST}
    )
    public StreamingResponseBody downloadFile(@PathVariable String fileName, @RequestParam String userName) throws IOException {
        transactionId.set(UUID.randomUUID().toString());
        File file = new File(uploadPath+"/" + userName + "/" + fileName);
        PrintStream var10000 = System.out;
        long var10001 = file.length() / 1024;
        var10000.println(var10001 * 1024 + " AAAAAAAAAAAAAAA");
        InputStream inputStream = new FileInputStream(file);
        return (outputStream) -> {
            FileCopyUtils.copy(inputStream, outputStream);
        };
    }

    @GetMapping({"/files/{userName}"})
    public ResponseEntity<String[]> getListFiles(@PathVariable String userName) {
        transactionId.set(UUID.randomUUID().toString());

        try {
            File directory = new File(uploadPath+"/" + userName);
            String[] files = directory.list();
            Logger var10000 = logger;
            String var10001 = (String)transactionId.get();
            var10000.info(var10001 + " ___ file list retrieve in directory" + directory.getName() + "success");
            transactionId.remove();
            return ResponseEntity.status(HttpStatus.OK).body(files);
        } catch (Exception var4) {
            logger.error((String)transactionId.get() + " ___ file list retrieve in directory failed");
            transactionId.remove();
            return null;
        }
    }

    @RequestMapping(
            value = {"/upload"},
            method = {RequestMethod.POST}
    )
    public ResponseEntity<String> uploadFile(@RequestParam("inputFile") MultipartFile file, @RequestParam("userName") String userName) {
        System.out.println("UPLOAD POST CALL SUCCESS");
        transactionId.set(UUID.randomUUID().toString());
        System.out.println("WHOLE CALL");
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        File directory = new File(this.uploadPath + "/" + userName);
        System.out.println(this.uploadPath + "UPLOADPATH");
        PrintStream var10000 = System.out;
        String var10001 = directory.getName();
        var10000.println(var10001 + " AAAAAAAAAAAAAAAAAAAAAAAAAA " + directory.getAbsolutePath());

        if (!directory.exists()) {
            directory.mkdirs();
        }

        File destFile = new File(directory, fileName);
        if (destFile.exists()) {
            destFile = this.getUniqueFileName(destFile, directory, (String)transactionId.get());
            System.out.println(destFile.getName() + " FINALNAME");
        }

        try {
            this.uploadFile(file, destFile, (String)transactionId.get());

            logger.info((String)transactionId.get() + " ___ file upload process success");
            transactionId.remove();
            return ResponseEntity.ok("File uploaded successfully");
        } catch (IOException var7) {
            logger.error((String)transactionId.get() + " ___ file upload process fail");
            transactionId.remove();
            return ResponseEntity.ok("File upload failed: " + var7.getMessage());
        }
    }

    private File getUniqueFileName(File file, File directory, String transactionId) {
        String fileName = file.getName();
        System.out.println(fileName + " OLDNAME");
        String nameWithoutExtension = fileName.replaceAll("\\.[^.]*$", "");
        String extension = this.getFileExtension(file);
        int count = 1;

        File newFile;
        for(newFile = file; newFile.exists(); ++count) {
            System.out.println(newFile.getName() + " EXISTS");
            String newName = nameWithoutExtension + "(" + count + ")" + extension;
            newFile = new File(directory, newName);
            System.out.println(newName + " NEWNAME");
        }

        PrintStream var10000 = System.out;
        String var10001 = newFile.getName();
        var10000.println("RETURNING " + var10001 + newFile.getAbsolutePath());
        return newFile;
    }

    private void uploadFile(MultipartFile file, File destFile, String transactionId) throws IOException {
        try {
            BufferedInputStream inputStream = new BufferedInputStream(file.getInputStream());

            try {
                BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(destFile));

                try {
                    byte[] buffer = new byte[1048576];

                    while(true) {
                        int bytesRead;
                        if ((bytesRead = inputStream.read(buffer)) == -1) {
                            logger.info(transactionId + " ___ file upload stream buffer success");
                            break;
                        }

                        outputStream.write(buffer, 0, bytesRead);
                    }
                } catch (Throwable var10) {
                    try {
                        outputStream.close();
                    } catch (Throwable var9) {
                        var10.addSuppressed(var9);
                    }

                    throw var10;
                }

                outputStream.close();
            } catch (Throwable var11) {
                try {
                    inputStream.close();
                } catch (Throwable var8) {
                    var11.addSuppressed(var8);
                }

                throw var11;
            }

            inputStream.close();
        } catch (Exception var12) {
            logger.error(transactionId + " ___ file upload stream buffer fail");
            throw var12;
        }
    }

    private String getFileSize(File file) {
        long size = file.length() / 1048576;
        return "" + size + "MB";
    }

    private String getFileExtension(File file) {
        String fileName = file.getName();
        int lastIndex = fileName.lastIndexOf(46);
        return lastIndex == -1 ? "" : fileName.substring(lastIndex);
    }

    @RequestMapping(
            value = {"/delete"},
            method = {RequestMethod.DELETE}
    )
    public ResponseEntity<String> deleteFile(@RequestParam String fileName, @RequestParam String userName) {
        transactionId.set(UUID.randomUUID().toString());

        Logger var10000;
        String var10001;
        try {
            File directory = new File(uploadPath+"/" + userName);
            File[] fileList = directory.listFiles();
            System.out.println("directory " + String.valueOf(directory));
            System.out.println(fileList);
            File target = null;
            File[] var6 = fileList;
            int var7 = fileList.length;

            for(int var8 = 0; var8 < var7; ++var8) {
                File file = var6[var8];
                System.out.println(file.getName());
                if (file.getName().equals(fileName)) {
                    System.out.println("file found " + file.getName());
                    file.delete();
                }
            }

            System.out.println(fileName);
            var10000 = logger;
            var10001 = (String)transactionId.get();
            var10000.info(var10001 + " ___ file delete success" + fileName);
            transactionId.remove();
            return ResponseEntity.ok("File deleted successfully");
        } catch (Exception var10) {
            var10000 = logger;
            var10001 = (String)transactionId.get();
            var10000.error(var10001 + " ___ file delete failed" + fileName);
            transactionId.remove();
            return ResponseEntity.ok("File file delete failed");
        }
    }
}
