package Backend;

import Panes.Player;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import javaTales.JavaTales;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.nio.file.Paths;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javax.crypto.*;
import javax.crypto.spec.*;
import java.io.*;
import java.net.*;
import javafx.scene.layout.VBox;
import sun.security.x509.IPAddressName;

import java.util.ArrayList;
import  javax.xml.bind.DatatypeConverter;
import java.nio.file.*;
import java.util.concurrent.LinkedBlockingQueue;


public class Backend implements Runnable{

    Boolean connected = false;
    Boolean isConnectionRequest = false;
    String initVector = "RandomInitVector";
    public JavaTales javaTales;
    public ConnectionToServer server;
    public LinkedBlockingQueue<ArrayList<String>> messages;
    public Socket socket;
    public Player player;
    public Player enemy;
    public int playerID;
    boolean enemyAdded = false;


    public void setJavaTales(JavaTales javaTales) {
        this.javaTales = javaTales;
    }

    public static String decrypt(String key, String initVector, String encrypted) {
        try {
            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

            byte[] original = cipher.doFinal(DatatypeConverter.parseBase64Binary(encrypted));

            return new String(original);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    String getKey(){
        String toRet = null;
        try{
            Path path = Paths.get("data");
            byte[] byteArray = Files.readAllBytes(path);
            toRet =DatatypeConverter.printBase64Binary(byteArray);
        }catch (Exception x){
            x.printStackTrace();
        }
        return toRet;
    }

    public void refreshAll(){
        javaTales.sceneController.removeAll();
        javaTales.sceneController.addScreen("Select Character", javaTales.selectChar.getPane());
        javaTales.sceneController.addScreen("Select Map", javaTales.selectMap.getPane());
        javaTales.sceneController.addScreen("MapScreen", javaTales.mapScreen.getPane());
        javaTales.sceneController.addScreen("GameOver", javaTales.gameOver.getPane());
        javaTales.sceneController.addScreen("JoinServer", javaTales.joinServer.getPane());
        javaTales.sceneController.addScreen("Intro", javaTales.intro.getPane());
    }

    public void refreshMapScreen(){
        javaTales.sceneController.removeScreen("Select Character");
        javaTales.sceneController.addScreen("Select Character", javaTales.mapScreen.getPane());
    }









    @Override


    public void run(){
        while(!connected){
            if(isConnectionRequest){
                try{
                    server = new ConnectionToServer(socket);
                    connected = true;
                }catch (IOException X){X.printStackTrace();}
                System.out.println("\nG77");
                while(true){
                    try{
                        ArrayList<String> message = messages.take();
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {



                                //A NEW PLAYER JOIN THE SERVER AND THE NEW CHARACTERS ARE ADDED TO THE MAP
                                if(message.get(0).equals("0")){
                                    System.out.println("Server says: add enemy");
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {

                                            if(!enemyAdded){
                                                javaTales.mapScreen.addEnemy();
                                                enemyAdded=true;
                                            }
                                        }
                                    });
                                    //The server has sent the playerID of the player
                                }else if(message.get(0).equals("ID")){
                                    playerID=Integer.parseInt(message.get(1));
                                    System.out.println("ID RECEIVED FROM SERVER: "+playerID);
                                    //A Database answer.
                                }else if(message.get(0).equals("2")){


                                }else if(message.get(0).equals("move")){

                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {

                                            javaTales.mapScreen.move(message.get(2),message.get(3));
                                        }
                                    });

                                }else{
                                    //Just if the password to login was correct, this information will be shown
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {

                                            //client.messageServer.appendText("\nand your password is: "+decrypt(getKey(),initVector,message.get(2)));
                                        }
                                    });

                                }
                            }
                        });
                        System.out.println("[backend]Message Received: " + message.get(0));
                    }
                    catch(InterruptedException e){ }
                }
            }
            try{
                Thread.sleep(1000);
                System.out.println("wait");
            }catch (InterruptedException x){x.printStackTrace();}
        }
    }


    public Backend(){
        player = new Player();
        System.out.println("\nG5");
        messages = new LinkedBlockingQueue<ArrayList<String>>();
        System.out.println("\nG6");

    }

    public void connectLocal(int port){
        try{
            System.out.println("\nG4");
            socket = new Socket(InetAddress.getLocalHost(), port);
            isConnectionRequest = true;
        }catch (IOException x){x.printStackTrace();}
    }
    public void connectNet(String ipAdress, int port){
        try{
            System.out.println("\nG4");
            socket = new Socket(InetAddress.getByName(ipAdress), port);
            isConnectionRequest = true;
        }catch (IOException x){x.printStackTrace();}
    }


    public ConnectionToServer getServer() {
        return server;
    }

    public void setServer(ConnectionToServer server) {
        this.server = server;
    }

    public LinkedBlockingQueue<ArrayList<String>> getMessages() {
        return messages;
    }

    public void setMessages(LinkedBlockingQueue<ArrayList<String>> messages) {
        this.messages = messages;
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    private class ConnectionToServer {
        ObjectInputStream in;
        ObjectOutputStream out;
        Socket socket;


        ConnectionToServer(Socket socket) throws IOException {
            this.socket = socket;
            System.out.println("\nG88");
            out = new ObjectOutputStream(socket.getOutputStream());
            System.out.println("\nG99");
            in = new ObjectInputStream(socket.getInputStream());



            Thread read = new Thread(){
                public void run(){
                    while(true){
                        try{
                            ArrayList<String> obj = (ArrayList<String>) in.readObject();
                            messages.put(obj);
                        }
                        catch(Exception e){ e.printStackTrace(); }
                    }
                }
            };

            read.setDaemon(true);
            read.start();
        }

        private void write(Object obj) {
            try{
                out.writeObject(obj);
            }
            catch(IOException e){ e.printStackTrace(); }
        }


    }

    public void send(ArrayList<String> obj) {
        server.write(obj);
    }
}