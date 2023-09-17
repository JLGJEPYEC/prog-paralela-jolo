/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectoparalelaftp;


import java.io.IOException;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPReply;
/**
 *
 * @author jolo
 */
public class Manejador {
    public FTPClient login (){
        FTPClient fc = null;
        String server = "www.peru-software.com"; //
        int port = 21;
        String user ="pp20172@peru-software.com";
        String pass = "fisi20172"; 
        fc= new FTPClient();
        try {
            // realizar el login
            fc.connect(server, port);
            //no
            //showServerReply(fc);

            int replyCode = fc.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                System.out.println("Connect failed");
                return null;
            }else{
                System.out.println("ok. connection ok");
            }
            //ya esta
            boolean success = fc.login(user, pass);
            //showServerReply(fc);

            if (!success) {
                System.out.println("Could not login to the server");
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fc;
    }
    
    
    
    
}
