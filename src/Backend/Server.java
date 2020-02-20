package Backend;

import java.io.*;
import java.net.*;
import java.sql.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;



/*--------------------------PSEUDOCODE-------------------------------------------
 *
 * Login to Javabook
 *
 * create login table if not exist
 *
 * check if user already exists
 *
 * SQL = SELECT count(*) FROM Login WHERE Username = 'example'
 *
 * IF(response==0){
 * SQL = insert into login }
 *
 *
 * else{
 * send to client message saying that the username has been taken}
 *
 *
 * */

// JDBC driver name and database URL




public class Server extends Application {

    Connection conn = null;
    Statement stmt = null;
    String sql;

    ArrayList<String> toUserResponseArray = new ArrayList<>();
    String query = "INSERT INTO logincm (Username, Password, FirstName, LastName) VALUES (?,?,?,?)";
    String request = "SELECT Firstname, Lastname, Username, Password from logincm where Username= ? and Password = ?";
    int count = 0;
    List<ClientHandler> clientList;
    LinkedBlockingQueue<ArrayList<String>>messages;
    ServerSocket serverSocket;


    private class ClientHandler {
        ObjectInputStream in;
        ObjectOutputStream out;
        Socket socket;
        int ID;

        ClientHandler(Socket socket, int ID) throws IOException {
            this.socket = socket;
            this.ID = ID;
            in = new ObjectInputStream(socket.getInputStream());
            out = new ObjectOutputStream(socket.getOutputStream());

            Thread read = new Thread(){
                public void run(){
                    boolean connected = true;
                    while(connected){
                        try{
                            ArrayList<String> obj= (ArrayList<String>) in.readObject();
                            messages.put(obj);

                        }
                        catch(Exception e){
                            connected = false;
                            e.printStackTrace();
                        }
                    }
                }
            };

            read.setDaemon(true); // terminate when main ends
            read.start();
        }

        public Socket getSocket() {
            return socket;
        }

        public void write(Object obj) {
            try{
                out.writeObject(obj);
                out.flush();
            }
            catch(IOException e){ e.printStackTrace(); }
        }
    }

    public void sendToOne(int index, ArrayList<String> message)throws IndexOutOfBoundsException {
        clientList.get(index).write(message);
    }

    public void sendToAllExcept(int index, ArrayList<String> message)throws IndexOutOfBoundsException {
        for (ClientHandler client : clientList){
            if(client.ID!=index){
                System.out.println("SENDING MESSAGE TO: "+client.getSocket());
                client.write(message);
            }

        }
    }

    public void sendToAll(Object message) {
        for (ClientHandler client : clientList){
            System.out.println("SENDING MESSAGE TO: "+client.getSocket());
            client.write(message);
        }
    }




    @Override
    public void start(Stage primaryStage) {



        final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
        final String DB_URL = "jdbc:mysql://localhost/javabook"
                + "?useUnicode=true&useJDBCCompliantTimezoneShift=true"
                + "&useLegacyDatetimeCode=false&serverTimezone=UTC";




        // This textArea is used to display information
        TextArea text = new TextArea();
        Button button = new Button("Push me");
        TextField textField = new TextField("Command");
        VBox vbox = new VBox();
        vbox.getChildren().addAll(text,button, textField);


        button.setOnAction(e->{
            ArrayList<String> message = new ArrayList<>();
            message.add(textField.getText());

            sendToAll(message);
        });

        // The scene is created
        Scene scene = new Scene(new ScrollPane(vbox), 450, 200);
        primaryStage.setTitle("Database Server"); // Set the stage title
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage

        try{

            //Register JDBC driver
            Class.forName(JDBC_DRIVER);


            //Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, "scott","tiger");

            Platform.runLater(() ->
                    text.appendText("\nThe connection with the database has been established."+ '\n'+System.lineSeparator()));


            //Statement is created
            stmt = conn.createStatement();

            //sql code to create the table to save the login information
            sql= "CREATE TABLE IF NOT EXISTS logincm(" +
                    "ID int PRIMARY KEY AUTO_INCREMENT," +
                    "Username varchar(50) UNIQUE," +
                    "Password varchar(50) ," +
                    "FirstName varchar(50)," +
                    "LastName varchar (50))";

            //The table Login is created if it doesnt exist
            stmt.executeUpdate(sql);
            // The server socket is created
            Platform.runLater(() ->
                    text.appendText("\nThe table has been created"));
        }catch(SQLException se){
            //Handle errors for JDBC
            se.printStackTrace();
        }catch(Exception e){
            //Handle errors for Class.forName
            e.printStackTrace();
        }finally{
            //finally block used to close resources
            try{
                if(stmt!=null)
                    stmt.close();
            }catch(SQLException se2){
            }// nothing we can do
            try{
                if(conn!=null)
                    conn.close();
            }catch(SQLException se){
                se.printStackTrace();
            }//end finally try
        }//end try




        clientList = Collections.synchronizedList(new ArrayList<ClientHandler>());
        messages = new LinkedBlockingQueue<ArrayList<String>>();
        try{
            serverSocket = new ServerSocket(6000,1,InetAddress.getLocalHost());
            text.appendText("SERVER IP: "+serverSocket.getInetAddress().getHostAddress());
            text.appendText("\nSERVER PORT: "+serverSocket.getLocalPort());
        }catch (IOException x){x.printStackTrace();}

        Thread accept = new Thread() {
            public void run(){
                int playerCounter=0;
                while(true){
                    try{
                        Socket s = serverSocket.accept();
                        clientList.add(new ClientHandler(s,playerCounter));

                        System.out.println("New client!");
                        if(clientList.size()==2){
                            System.out.println("Enemy added to the map");
                            toUserResponseArray.add("0");
                            sendToAll(toUserResponseArray);
                        }

                        playerCounter++;
                    }
                    catch(IOException e){ e.printStackTrace(); }
                }
            }
        };
        accept.setDaemon(true);
        accept.start();

        Thread messageHandling = new Thread() {
            public void run(){
                while(true){
                    try{
                        toUserResponseArray=new ArrayList<>();
                        Boolean userSuccess = false;
                        ArrayList<String> message = messages.take();
                        // Do some handling here...

                       if(message.get(0).equals("move")){

                           String temp;
                           temp=message.get(2);
                           message.set(2, temp+"E");

                           sendToAllExcept(Integer.parseInt(message.get(1)),message);
                       }else if(message.get(0)=="3"){
                           Platform.runLater(() -> {
                               text.appendText("\nSQL request received from client. "+message.get(0));
                           });

                           //The connection is created.
                           conn = DriverManager.getConnection(DB_URL, "scott","tiger");

                           //If the request of the client is an ArrayList of size bigger than 2 it means
                           //that is a registration request, since such array contains four elements
                           if(message.size()>2){
                               //The preparedStatement is set to Insert a new User in the Logincm table
                               PreparedStatement preparedStatement = conn.prepareStatement(query);


                               //The entries from the user are entered in the preparedStatement
                               preparedStatement.setString(1,message.get(0));
                               preparedStatement.setString(2,message.get(1));
                               preparedStatement.setString(3,message.get(2));
                               preparedStatement.setString(4,message.get(3));

                               try{
                                   //The prepared statement is executed
                                   preparedStatement.executeUpdate();
                                   userSuccess = true;
                                   Platform.runLater(() ->
                                           text.appendText("\nThe user has been registered successfully."));
                                   toUserResponseArray.add("\nThe user has been registered successfully.");
                                   toUserResponseArray.add("0");

                                   sendToAll(toUserResponseArray);

                               }

                               //This program intentionally lets the Database throw SQLIntegrityConstraintViolationException
                               //to check if a request from the Client wants to use a username that is already in use.
                               //If that's the case, the Exception is catched and an error message is sent to the client
                               catch (SQLIntegrityConstraintViolationException c){
                                   try{
                                       Platform.runLater(() ->
                                               text.appendText("\nThe username is already in use. Register denied."));
                                       toUserResponseArray.add("\nThe specified username is already in use. Try with another one.");
                                       toUserResponseArray.add("1");
                                       sendToAll(toUserResponseArray);

                                       conn.close();

                                   }/*catch (IOException x){
                                    x.printStackTrace();
                                }*/catch (SQLException x){
                                       x.printStackTrace();
                                   }
                               }


                               //This if statement is used to close the connection and the socket in case that
                               //an error occurs.
                               if(userSuccess){
                                   conn.close();

                               }

                               //If the size of the ArrayList received is equal to 2 it means that is a Login request
                           }else{

                               userSuccess=false;

                               //The preparedStatement is set and executed
                               PreparedStatement preparedStatement = conn.prepareStatement(request);
                               preparedStatement.setString(1,message.get(0));
                               preparedStatement.setString(2,message.get(1));
                               ResultSet resultSet = preparedStatement.executeQuery();


                               //If the result of the request has at least one result this code will be executed
                               if(resultSet.next()){
                                   Platform.runLater(() ->
                                           text.appendText("\nThe user "+message.get(0)+" has logged successfully."));
                                   userSuccess = true;

                                   //The result of the query is separated in individual strings
                                   String firstname = resultSet.getString("Firstname");
                                   String lastname = resultSet.getString("Lastname");
                                   String username = resultSet.getString("Username");
                                   String password = resultSet.getString("Password");

                                   //The response to the request is set and sent to the client
                                   toUserResponseArray.add("\nWelcome "+username+"! Your registered name is "+firstname+" "+lastname);
                                   toUserResponseArray.add("0");
                                   toUserResponseArray.add(password);

                                   sendToAll(toUserResponseArray);

                                   //The connection, input, output and the socket are closed.
                                   conn.close();


                                   //If the request had no resutls this code will be executed
                               }else{
                                   try{
                                       Platform.runLater(() ->
                                               text.appendText("\nNo users where found with the specified username and data."));
                                       toUserResponseArray.add("\nThe username or the password is incorrect. Please, try again.");
                                       toUserResponseArray.add("2");
                                       sendToAll(toUserResponseArray);
                                       conn.close();

                                   }/*catch (IOException x){
                                    x.printStackTrace();
                                }*/catch (SQLException x){
                                       x.printStackTrace();
                                   }
                               }





                           }
                       }
                    }
                    catch(Exception x){x.printStackTrace(); }
                }
            }
        };

        messageHandling.setDaemon(true);
        messageHandling.start();





    }

    /**
     * The main method is only needed for the IDE with limited
     * JavaFX support. Not needed for running from the command line.
     */
    public static void main(String[] args) {
        launch(args);
    }
}