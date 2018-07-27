package re.infoport.view;



import com.google.gson.Gson;
import com.jcabi.ssh.Shell;
import com.jcabi.ssh.Ssh;
import com.jcabi.ssh.SshByPassword;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.KeyPair;
import com.jcraft.jsch.Session;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import org.bouncycastle.openssl.PEMWriter;
import org.bouncycastle.util.io.pem.PemWriter;
import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;
import org.jboss.resteasy.client.jaxrs.ResteasyWebTarget;
import re.infoport.model.Agent;
import re.infoport.model.Fichier;
import re.infoport.utils.AppConfig;
import re.infoport.utils.Authenticator;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import java.awt.event.WindowAdapter;
import java.io.*;
import java.net.UnknownHostException;


import java.nio.charset.Charset;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.SecureRandom;

public class FenetreMain {

    private JPanel panel1;
    private JTree tree1;
    private JButton button1;
    private FTPClient client;
    public static void main(String[] args) {
        JFrame frame = new JFrame("FenetreMain");
        FenetreMain f = new FenetreMain();
        frame.setContentPane(f.panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    frame.setBounds(0, 0, 500, 500);
        /*JFileChooser fileChooser=new JFileChooser();
        fileChooser.showDialog(f.panel1, "ok");

            File cle=fileChooser.getSelectedFile();*/
           // f.connectToSSH();
        f.consommeRestGET();
       f.consommeRestPOST();
        f.button1.addActionListener(e -> {
            f.connectToFtp();
        });
        // Faire une boucle for et autres
        f.tree1.addTreeSelectionListener(e ->
        {
            DefaultMutableTreeNode selected=(DefaultMutableTreeNode)f.tree1.getLastSelectedPathComponent();
            if (selected == null) return;

            try {
                Fichier fichier = (Fichier) selected.getUserObject();
                System.out.println(fichier);
                JFileChooser fileChooser=new JFileChooser();

                fileChooser.showDialog(f.panel1, "Sauvegarder");
                File save=fileChooser.getSelectedFile();
                f.download(f.client, fichier.getDir(),fichier.getNom(), save );
            }
            catch(Exception e1){

            }


        });



       // f.connectToSSH();
    }

    private void consommeRestPOST() {
       Agent a=new Agent();
        a.setNom("DepuisJ2se26");


        Gson gson=new Gson();
        String json=gson.toJson(a);

        //Define the API URI where API will be accessed
        ClientRequest request = new ClientRequest("http://localhost:8080/cannelogger/rest/agents");
        String auth = "infoport" + ":" + "infoport";

        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("ISO-8859-1")));
        String authHeader = "Basic " + new String(encodedAuth);
        //Set the accept header to tell the accepted response format
        request.body("application/json", json);

        //Send the request
        ClientResponse response = null;
        try {
            response = request.header(HttpHeaders.AUTHORIZATION, authHeader).post();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //First validate the api status code
        int apiResponseCode = response.getResponseStatus().getStatusCode();
        if(response.getResponseStatus().getStatusCode() != 201)
        {
            throw new RuntimeException("Failed with HTTP error code : " + apiResponseCode);
        }
    }

    private void consommeRestGET() {


        Client client = ClientBuilder.newClient();
      //  client.register(new Authenticator("infoport", "infoport"));
        WebTarget target = client.target("http://localhost:8080/cannelogger/rest/agents/1");
        //Response response = target.queryParam("name", "Bill").request().get();

        String auth = "infoport" + ":" + "infoport";
        byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("ISO-8859-1")));
        String authHeader = "Basic " + new String(encodedAuth);


        Response response = target.request().accept("application/json").header(HttpHeaders.AUTHORIZATION,authHeader ).get();
        System.out.println(response.getStatus());

            Agent a = response.readEntity(Agent.class);
        System.out.println(a);

    }

    public boolean download(FTPClient ftp,String remoteDir, String remoteFileName, File downloadFile) {

        try {

            if (ftp == null) {

                return false;
            }
            try (OutputStream os = new FileOutputStream(downloadFile)) {
                boolean storeRet = ftp.retrieveFile(remoteFileName, os);
                if (!storeRet) {

                    return false;
                }
            }
            return true;
        } catch (IOException e) {

            return false;
        }
    }
    public void connectToSSH() {

        try{
       /* KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048, new SecureRandom());
        PrivateKey privateKey = keyPairGenerator.genKeyPair().getPrivate();

        StringWriter writer = new StringWriter();

        try (PEMWriter pemWriter = new PEMWriter(writer)) {
            pemWriter.writeObject(privateKey);
        }

        String privateKeyStr = writer.toString();
        System.out.println(privateKeyStr);
       //     KeyPair.load(null, privateKeyStr.getBytes("US-ASCII"), null);*/
        Shell shell = null;
           /* JSch j=new JSch();
            j.addIdentity(f.getAbsolutePath());
           Session s= j.getSession(AppConfig.HOST);*/


            shell = new SshByPassword(AppConfig.HOST, 22, AppConfig.USER, AppConfig.PASSWORD);

            String stdout = new Shell.Plain(shell).exec("ls -ali");
            System.out.println(stdout);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
    public void  listFileFromDir(FTPClient client,String s,DefaultMutableTreeNode node) throws Exception{
        FTPFile[] files = client.listFiles(s);

        if(files.length>0){
            for(FTPFile fichier : files){
                // System.out.println(fichier.getName() +"  "+fichier.isDirectory());
                DefaultMutableTreeNode premier=new DefaultMutableTreeNode(new Fichier(fichier,s,fichier.getName()));

                node.add(premier);
                if(fichier.isDirectory()){
                    listFileFromDir(client,s+"/"+fichier.getName(),premier);
                }

            }
        }
    }
    public void connectToFtp(){
         client=new FTPClient();

        try {
            client.connect(AppConfig.HOST);
            System.out.print(client.getReplyString());
            int reply = client.getReplyCode();
            if(!FTPReply.isPositiveCompletion(reply)) {
                client.disconnect();
                System.err.println("FTP server refused connection.");
                System.exit(1);
            }
            else{
                client.login(AppConfig.USER, AppConfig.PASSWORD);
                DefaultMutableTreeNode racine=new DefaultMutableTreeNode("Home");

                FTPFile[] files = client.listFiles("~/");
                for(FTPFile fichier : files){
                   // System.out.println(fichier.getName() +"  "+fichier.isDirectory());
                    DefaultMutableTreeNode premier=new DefaultMutableTreeNode(fichier);

                    racine.add(premier);
                    if(fichier.isDirectory()){
                           listFileFromDir(client,fichier.getName(),premier);
                       }

                }
               tree1.setModel(new DefaultTreeModel(racine));
            }






        }
        catch(Exception e){
            System.out.println("probleme");
        }
    }

}
