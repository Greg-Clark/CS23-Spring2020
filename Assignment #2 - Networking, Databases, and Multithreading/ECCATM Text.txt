package eccbankatm;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.*;
import java.text.NumberFormat;
import java.util.Date;

public class ECCSever extends Application {
    private Statement stmt;
    private TextArea ta = new TextArea();
    private Connection connection;
    private PreparedStatement pstmt;
    private CheckingAccount ca = null;
    private NumberFormat nf = NumberFormat.getCurrencyInstance();
    private Transaction t = null;


    @Override
    public void start(Stage primaryStage) throws Exception, SQLException {




        // Create a scene and place it in the stage
        Scene scene = new Scene(new ScrollPane(ta), 450, 200);
        primaryStage.setTitle("ATM Server");
        primaryStage.setScene(scene);
        primaryStage.show();



        new Thread(() -> {

            try {
                // Create a server socket
                ServerSocket serverSocket = new ServerSocket(8000);
                Platform.runLater(() ->
                        ta.appendText("Server started at " + new Date() + '\n'));

                // Listen for a connection request connect client
                Socket socket = serverSocket.accept();

                // Create data input and output streams
                DataInputStream inputFromClient = new DataInputStream(socket.getInputStream());
                DataOutputStream outputToClient = new DataOutputStream(socket.getOutputStream());

                initializeDB();

                int clientDecision;

                while (true) {
                    clientDecision = inputFromClient.readInt();

                    //user closes window
                    if(clientDecision == -1)
                    {
                        pstmt = connection.prepareStatement("INSERT INTO account(username, acct) VALUES (?, ?)");
                        //if ca is empty and window is closed then delete given username
                        if(ca == null)
                        {
                            pstmt.execute("DELETE FROM account WHERE username = 'test'");
                        }
                        else
                        {
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            ObjectOutputStream oos = new ObjectOutputStream(baos);
                            oos.writeObject(ca);
                            byte[] caAsBytes = baos.toByteArray();
                            ByteArrayInputStream bais = new ByteArrayInputStream(caAsBytes);


                            pstmt.setString(1, ca.getName());
                            pstmt.setBinaryStream(2, bais, caAsBytes.length);
                            pstmt.executeUpdate();
                        }
                        pstmt.close();
                        connection.close();
                    }

                    //user is logging in
                    if(clientDecision == 0){
                        // Receive data from the client
                        //clientDecision = inputFromClient.readInt();
                        String usernameFromClient = inputFromClient.readUTF();
                        String pwFromClient = inputFromClient.readUTF();

                        Platform.runLater(() -> {
                            ta.appendText("User from client: " + usernameFromClient + '\n');
                            ta.appendText("Password from client: " + pwFromClient + '\n');

                        });

                        ResultSet resultSet = stmt.executeQuery
                                ("SELECT sql3336223.users.username, sql3336223.users.password"
                                        + " FROM sql3336223.users");

                        boolean logonKey = false;

                        while (resultSet.next()) {
                            if(resultSet.getString(1).equals(usernameFromClient) && resultSet.getString(2).equals(pwFromClient))
                            {
                                logonKey = true;
                            }
                        }

                        outputToClient.writeBoolean(logonKey);

                    }

                    //user is adding an account
                    if(clientDecision == 1)
                    {
                        String accountName = inputFromClient.readUTF();
                        double accountBalance = inputFromClient.readDouble();

                        Platform.runLater(() -> {
                            ta.appendText("New account added for " + accountName);
                            ta.appendText(" with a balance of $" + accountBalance + '\n');

                        });

                        ca = new CheckingAccount (accountName, accountBalance);
                        ca.setBelow500(false);

                    }

                    //user is processing a check
                    if(clientDecision == 2)
                    {
                        int checkNum = inputFromClient.readInt();
                        double amt = inputFromClient.readDouble();

                        ca.setBalance(amt, 1);
                        t = new Check(ca.getTransCount(), 1, amt, checkNum);
                        ca.addTrans(t);
                        ca.setServiceCharge(0.15);
                        t = new Transaction(ca.getTransCount(), 0, 0.15);
                        ca.addTrans(t);
                        String message = ca.getName() + "'s Account\nTransaction: Check #"
                                + checkNum + " in Amount of " + nf.format(amt)
                                + "\nCurrent Balance: " + nf.format (ca.getBalance())
                                + "\nService Charge: Check --- charge $0.15";
                        if (ca.getBalance() < 500 && !(ca.getBelow500())) {
                            ca.setServiceCharge(5.00);
                            ca.setBelow500(true);
                            message += "\nServiceCharge: Below $500 --- charge $5.00";
                            t = new Transaction(ca.getTransCount(), 0, 5.00);
                            ca.addTrans(t);
                        }
                        if (ca.getBalance() < 50) {
                            message += "\nWarning: Balance below $50";
                        }
                        if (ca.getBalance() < 0) {
                            ca.setServiceCharge(10.00);
                            message += "\nServiceCharge: Below $0 --- charge $10.00";
                            t = new Transaction(ca.getTransCount(), 0, 10.00);
                            ca.addTrans(t);
                        }

                        message += "\nTotal Service Charge: " + nf.format (ca.getServiceCharge());

                        ta.appendText(message);
                    }

                    //user is processing a deposit
                    if(clientDecision == 3)
                    {
                        double amount = inputFromClient.readDouble();
                        double cashAmount = inputFromClient.readDouble();
                        double checkAmount = inputFromClient.readDouble();



                        ca.setBalance(amount, 2);
                        t = new Deposit(ca.getTransCount(), 2, amount, cashAmount, checkAmount);
                        ca.addTrans(t);
                        ca.setServiceCharge(0.1);
                        t = new Transaction(ca.getTransCount(), 0, 0.1);
                        ca.addTrans(t);
                        String message = '\n' + ca.getName()
                                + "'s Account\nTransaction: Deposit in Amount of " + nf.format(amount)
                                + "\nCurrent Balance: " + nf.format (ca.getBalance())
                                + "\nService Charge: Deposit --- charge $0.10"
                                + "\nTotal Service Charge: " + nf.format (ca.getServiceCharge());
                        ta.appendText(message);
                    }



                }


            }
            catch(IOException | SQLException ex) {
                ex.printStackTrace();
            }
        }).start();
    }

        public void initializeDB() {
        try {
            // Load the JDBC driver load driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            //Class.forName("oracle.jdbc.driver.OracleDriver");
            //System.out.println("Driver loaded");
            ta.appendText("Driver Loaded\n");

            // Establish a connection connect to MySQL database
            connection = DriverManager.getConnection
                    ("jdbc:mysql://sql3.freemysqlhosting.net:3306/sql3336223", "sql3336223", "C$23Ass!gnment#2");
            //System.out.println("Database connected");
            ta.appendText("Database Connected\n");

            // Create a statement execute statement
            stmt = connection.createStatement();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
