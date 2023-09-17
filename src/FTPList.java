
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

/**
 * An example program that demonstrates how to list files and directories on a
 * FTP server using Apache Commons Net API.
 *
 * @author www.codejava.net
 */
public class FTPList {

    public static void main(String[] args) {
        String server = "www.peru-software.com";
        int port = 21;
        String user = "pp20172@peru-software.com";
        String pass = "fisi20172";

        FTPClient ftpClient = new FTPClient();

        int contSuccess = 0;
        int contFail = 0;

        try {

            ftpClient.connect(server, port);
            showServerReply(ftpClient);

            int replyCode = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                System.out.println("Connect failed");
                return;
            }else{
                System.out.println("ok. connection ok");
            }

            boolean success = ftpClient.login(user, pass);
            showServerReply(ftpClient);

            if (!success) {
                System.out.println("Could not login to the server");
                return;
            }

            // Lists files and directories
            FTPFile[] files1 = ftpClient.listFiles("/public_ftp");
            printFileDetails(files1);

            // uses simpler methods
            String[] files2 = ftpClient.listNames();
            printNames(files2);

            if (files2 != null && files2.length > 0) {
                for (String aFile : files2) {
                    System.out.println(aFile);

                    File localfile = new File("D:/resultado/" + aFile);

                    try {
                        OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(localfile));
                        boolean success2 = ftpClient.retrieveFile(aFile, outputStream);

                        outputStream.close();

                        if (success2) {
                            System.out.println("Archivo" + aFile + " descargado.");

                            contSuccess = contSuccess + 1;

                        } else {
                            System.out.println("El archivo " + aFile + " no se pudo descargar");

                            contFail = contFail + 1;
                            System.out.println(aFile);
                        }
                    } catch (Exception e) {
                    }
                }

                System.out.println("Se descargaron con Exito " + contSuccess + " archivos");
                System.out.println("No se descargaron " + contFail + " archivos");
            }

        } catch (IOException ex) {
            System.out.println("Oops! Something wrong happened");
            ex.printStackTrace();
        } finally {
            // logs out and disconnects from server
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private static void printFileDetails(FTPFile[] files) {
        DateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (FTPFile file : files) {
            String details = file.getName();
            if (file.isDirectory()) {
                System.out.println("dentro");
                details = "[" + details + "]";
            }
            details += "\t\t" + file.getSize();
            details += "\t\t" + dateFormater.format(file.getTimestamp().getTime());

            System.out.println(details + "holi");
        }
    }

    private static void printNames(String files[]) {
        if (files != null && files.length > 0) {
            for (String aFile : files) {
                System.out.println(aFile + "---");
            }
        }
    }

    private static void showServerReply(FTPClient ftpClient) {
        String[] replies = ftpClient.getReplyStrings();
        if (replies != null && replies.length > 0) {
            for (String aReply : replies) {
                System.out.println("SERVER: " + aReply);
            }
        }
    }

    private static void dowloandFies(String files[]) {

        OutputStream outputStream = null;
        try {
            File localfile = new File("C:/Users/argen/Documents/so01.pptx");
            outputStream = new BufferedOutputStream(new FileOutputStream(localfile));
            if (files != null && files.length > 0) {
                for (String aFile : files) {
                    System.out.println(aFile);

                }
            }

        } catch (FileNotFoundException ex) {

            Logger.getLogger(FTPList.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            try {
                outputStream.close();
            } catch (IOException ex) {
                Logger.getLogger(FTPList.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
