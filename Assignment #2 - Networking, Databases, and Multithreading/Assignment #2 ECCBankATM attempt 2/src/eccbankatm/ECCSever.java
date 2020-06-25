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
import java.util.Vector;

public class ECCSever extends Application {
    private Statement stmt;
    private TextArea ta = new TextArea();
    private Connection connection;
    private PreparedStatement pstmt;
    private CheckingAccount ca = null;
    private NumberFormat nf = NumberFormat.getCurrencyInstance();
    private Transaction t = null;
    private Vector<CheckingAccount> caVector = new Vector<CheckingAccount>();
    private int accountNumber = 0;


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
                            for(int i = 0; i < caVector.size(); i++)
                            {
                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                ObjectOutputStream oos = new ObjectOutputStream(baos);
                                oos.writeObject(caVector.elementAt(i));
                                byte[] caAsBytes = baos.toByteArray();
                                ByteArrayInputStream bais = new ByteArrayInputStream(caAsBytes);


                                pstmt.setString(1, caVector.elementAt(i).getName());
                                pstmt.setBinaryStream(2, bais, caAsBytes.length);
                                pstmt.executeUpdate();
                            }
                        }
                        pstmt.close();
                        connection.close();
                    }

                    //user is logging in
                    if(clientDecision == 0){
                        // Receive data from the client
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
                            ta.appendText(" with a balance of $" + accountBalance + "\n\n");

                        });
                        if(ca != null)
                        {
                            caVector.setElementAt(ca, accountNumber);
                        }
                        ca = new CheckingAccount (accountName, accountBalance);
                        ca.setBelow500(false);

                        caVector.addElement(ca);
                        accountNumber = caVector.size() - 1;
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

                        outputToClient.writeUTF(message);

                        //ta.appendText(message + "\n\n");
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


                        String message = ca.getName()
                                + "'s Account\nTransaction: Deposit in Amount of " + nf.format(amount)
                                + "\nCurrent Balance: " + nf.format (ca.getBalance())
                                + "\nService Charge: Deposit --- charge $0.10"
                                + "\nTotal Service Charge: " + nf.format (ca.getServiceCharge());

                        outputToClient.writeUTF(message);

                        //ta.appendText(message + "\n\n");
                    }


                    //client chooses exit on transaction tab
                    if(clientDecision == 4)
                    {
                        //this isn't really needed but lets me know that the server
                        //has received all the correct information
                        String message = "Transaction: End\n"
                                + "Current Balance: " + nf.format(ca.getBalance())
                                + "\nTotal Service Charge: " + nf.format(ca.getServiceCharge())
                                + "\nFinal Balance: " + nf.format(ca.getBalance()
                                - ca.getServiceCharge());
                        outputToClient.writeUTF(message);

                        //ta.appendText(message + "\n\n");
                    }

                    //client wants to list all transactions
                    if(clientDecision == 5)
                    {
                        String message = "List All Transactions\nName: " + ca.getName()
                                + "\nBalance: " + nf.format(ca.getBalance())
                                + "\nTotal Service Charge: " + nf.format(ca.getServiceCharge())
                                + "\n\nID     Type      Amount\n";
                        for (int i = 0; i < ca.getTransCount(); i++) {
                            message += String.format("%-7d", i);
                            switch (ca.getTrans(i).getTransId()) {
                                case 0:
                                    message += "Svc. Chg. ";
                                    break;
                                case 1:
                                    message += "Check     ";
                                    break;
                                case 2:
                                    message += "Deposit   ";
                                    break;
                            }
                            message += String.format("$%-7.2f\n", ca.getTrans(i).getTransAmount());
                        }
                        outputToClient.writeUTF(message);
                    }

                    //client wants to list all checks
                    if(clientDecision == 6)
                    {
                        String message = "List All Checks\nName: " + ca.getName()
                                + "\nBalance: " + nf.format(ca.getBalance())
                                + "\nTotal Service Charge: " + nf.format(ca.getServiceCharge())
                                + "\n\nID     Check     Amount\n";
                        for (int i = 0; i < ca.getTransCount(); i++) {
                            if (ca.getTrans(i).getTransId() == 1) {
                                Check c = (Check) ca.getTrans(i);
                                message += String.format("%-7d", i);
                                message += String.format("%-10d", c.getCheckNumber());
                                message += String.format("$%-7.2f\n", ca.getTrans(i).getTransAmount());
                            }
                        }
                        outputToClient.writeUTF(message);
                    }

                    //client wants to list all deposits
                    if(clientDecision == 7)
                    {
                        String message = "List All Deposits\nName: " + ca.getName()
                                + "\nBalance: " + nf.format(ca.getBalance())
                                + "\nTotal Service Charge: " + nf.format(ca.getServiceCharge())
                                + "\n\nID     Cash        Check        Amount\n";
                        for (int i = 0; i < ca.getTransCount(); i++) {
                            if (ca.getTrans(i).getTransId() == 2) {
                                Deposit d = (Deposit) ca.getTrans(i);
                                message += String.format("%-7d", i);
                                message += String.format("$%-10.2f ", d.getCashAmount());
                                message += String.format("$%-10.2f  ", d.getCheckAmount());
                                message += String.format("$%-10.2f\n", ca.getTrans(i).getTransAmount());
                            }
                        }
                        outputToClient.writeUTF(message);
                    }

                    //client wants to list all service charges
                    if(clientDecision == 8)
                    {
                        String message = "List All Service Charges\nName: " + ca.getName()
                                + "\nBalance: " + nf.format(ca.getBalance())
                                + "\nTotal Service Charge: " + nf.format(ca.getServiceCharge())
                                + "\n\nID     Amount\n";
                        for (int i = 0; i < ca.getTransCount(); i++) {
                            if (ca.getTrans(i).getTransId() == 0) {
                                message += String.format("%-7d", i);
                                message += String.format("$%-7.2f\n", ca.getTrans(i).getTransAmount());
                            }
                        }
                        outputToClient.writeUTF(message);
                    }

                    //client wants to search for an account
                    if(clientDecision == 9)
                    {

                        String name = inputFromClient.readUTF();
                        boolean found = false;
                        for (int index = 0; index < caVector.size(); index++)
                        {
                            CheckingAccount c = caVector.elementAt(index);

                            // check on the name of the account
                            if (name.equals(c.getName())) {
                                name = "Found account for " + name;

                                ca = caVector.elementAt(index);
                                accountNumber = index;
                                found = true;
                            }
                        }

                        if(!found)
                            name = "Account not found for " + name;

                        outputToClient.writeUTF(name);
                    }

                    //client wants to list all accounts
                    if(clientDecision == 10)
                    {
                        String message = "List of All Accounts:\n\n";
                        for (int index = 0; index < caVector.size(); index++)
                        {
                            message += "Name: " + caVector.elementAt(index).getName()
                                    + "\nBalance: " + nf.format(caVector.elementAt(index).getBalance())
                                    + "\nTotal Service Charge: " + nf.format(caVector.elementAt(index).getServiceCharge())
                                    + "\n\n";
                        }

                        outputToClient.writeUTF(message);
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
