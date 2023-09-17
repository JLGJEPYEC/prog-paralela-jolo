package proyectoparalelaftp;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import proyectoparalelaftp.Manejador;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
/**
 *
 * @author Andhersson Salazar
 */
public class ProyectoParalelaFTP {
    public static void main(String[] args) {
        Manejador solicitar = new Manejador();
        FTPClient ftpClient = solicitar.login();
        try {
            String remoteFilePath = "/so01.pptx";
            File localfile = new File("D:/so01.pptx");
            
            OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(localfile));
            boolean success = ftpClient.retrieveFile(remoteFilePath, outputStream);
            
            outputStream.close();
            
            if (success) {
                System.out.println("Archivo descargado.");
            }else {
                System.out.println("Archivo no se pudo descargar");
            }
            
            
        } catch (IOException ex) {
            System.out.println("Error: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                if (ftpClient.isConnected()) {
                    ftpClient.logout();
                    ftpClient.disconnect();
                }
            }catch(IOException ex) {
                ex.printStackTrace();
            }
            System.out.println("listo");
        }
    }
}
