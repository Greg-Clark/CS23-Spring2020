/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.example.cs23assignment3;

//import javafx.application.Application;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import javafx.scene.Scene;
//import javafx.scene.control.Menu;
//import javafx.scene.control.MenuBar;
//import javafx.scene.control.MenuItem;
//import javafx.scene.control.TextArea;
//import javafx.scene.text.Font;
//import javafx.stage.Stage;
//
//import java.io.DataInputStream;
//import java.io.DataOutputStream;
//import java.io.IOException;
//import java.net.Socket;
//import java.text.NumberFormat;
//import java.util.Optional;
//import javafx.application.Platform;
//import javafx.geometry.Insets;
//import javafx.scene.Node;
//import javafx.scene.control.Alert;
//import javafx.scene.control.Alert.AlertType;
//import javafx.scene.control.ButtonBar.ButtonData;
//import javafx.scene.control.ButtonType;
//import javafx.scene.control.Dialog;
//import javafx.scene.control.Label;
//import javafx.scene.control.PasswordField;
//import javafx.scene.control.TextField;
//import javafx.scene.control.TextInputDialog;
//import javafx.scene.input.KeyCombination;
//import javafx.scene.layout.GridPane;
//import javafx.scene.layout.VBox;
//import javafx.stage.WindowEvent;
//import javafx.util.Pair;
//
///**
// *
// * @author Greg Clark
// */
//public class ECCATM extends Application {
//    private static DataOutputStream toServer = null;
//    private static DataInputStream fromServer = null;
//
//
//    static CheckingAccount ca = null;
//    static NumberFormat nf = NumberFormat.getCurrencyInstance();
//    static TextArea ta;
//
//    @Override
//    public void start(Stage primaryStage) {
//        Boolean logon = false;
//        Optional<Pair<String,String>> op;
//
//
//
//        try {
//            // Create a socket to connect to the server request connection
//            Socket socket = new Socket("localhost", 8000);
//
//            // Create an input stream to receive data from the server input from server
//            fromServer = new DataInputStream(socket.getInputStream());
//
//            // Create an output stream to send data to the server output to server
//            toServer = new DataOutputStream(socket.getOutputStream());
//        }
//        catch (IOException ex) {
//            ta.appendText(ex.toString() + '\n');
//        }
//
//        while (logon == false)
//        {
//            op = logonAccount();
//            if (op.isPresent())
//            {
//                // Send username/password combination
//                String user = op.get().getKey();
//                String pw = op.get().getValue();
//
//
//                try {
//                    toServer.writeInt(0);
//                    toServer.writeUTF(user);
//                    toServer.writeUTF(pw);
//                    toServer.flush();
//
//                    logon = fromServer.readBoolean();
//                }
//                catch (IOException ex) {
//                    System.err.println(ex);
//                }
//            }
//        }
//
//        MenuBar menuBar = new MenuBar();
//
//        Menu menuAccounts = new Menu("Accounts");
//        Menu menuTransactions = new Menu("Transactions");
//        menuBar.getMenus().addAll(menuAccounts, menuTransactions);
//
//        MenuItem menuItemAddAccount = new MenuItem("Add An Account");
//        menuItemAddAccount.setOnAction(new EventHandler<ActionEvent>() {
//            public void handle(ActionEvent t) {
//                addAccount();
//            }
//        });
//
//        MenuItem menuItemListAllTrans = new MenuItem("List All Transactions");
//        menuItemListAllTrans.setOnAction(new EventHandler<ActionEvent>() {
//            public void handle(ActionEvent t) {
//                listAllTransactions();
//            }
//        });
//
//        MenuItem menuItemListAllChecks = new MenuItem("List All Checks");
//        menuItemListAllChecks.setOnAction(new EventHandler<ActionEvent>() {
//            public void handle(ActionEvent t) {
//                listAllChecks();
//            }
//        });
//
//        MenuItem menuItemListAllDeposits = new MenuItem("List All Deposits");
//        menuItemListAllDeposits.setOnAction(new EventHandler<ActionEvent>() {
//            public void handle(ActionEvent t) {
//                listAllDeposits();
//            }
//        });
//
//        MenuItem menuItemListAllSvcChgs = new MenuItem("List All Service Charges");
//        menuItemListAllSvcChgs.setOnAction(new EventHandler<ActionEvent>() {
//            public void handle(ActionEvent t) {
//                listAllServiceCharges();
//            }
//        });
//
//        MenuItem menuItemFindAccount = new MenuItem("Find An Account");
//        menuItemFindAccount.setOnAction(new EventHandler<ActionEvent>() {
//            public void handle(ActionEvent t) {
//                findAccount();
//            }
//        });
//
//        MenuItem menuItemListAllAccts = new MenuItem("List All Accounts");
//        menuItemListAllAccts.setOnAction(new EventHandler<ActionEvent>() {
//            public void handle(ActionEvent t) {
//                listAllAccounts();
//            }
//        });
//
//        menuAccounts.getItems().addAll(menuItemAddAccount,
//                menuItemListAllTrans, menuItemListAllChecks,
//                menuItemListAllDeposits, menuItemListAllSvcChgs,
//                menuItemFindAccount, menuItemListAllAccts);
//
//        MenuItem menuItemEnterTransaction = new MenuItem("Enter Transaction");
//        menuItemEnterTransaction.setOnAction(new EventHandler<ActionEvent>() {
//            public void handle(ActionEvent t) {
//                enterTransaction();
//            }
//        });
//        menuTransactions.getItems().add(menuItemEnterTransaction);
//
//        menuItemAddAccount.setAccelerator(
//                KeyCombination.keyCombination("Ctrl+A"));
//        menuItemListAllTrans.setAccelerator(
//                KeyCombination.keyCombination("Ctrl+T"));
//        menuItemListAllChecks.setAccelerator(
//                KeyCombination.keyCombination("Ctrl+C"));
//        menuItemListAllDeposits.setAccelerator(
//                KeyCombination.keyCombination("Ctrl+D"));
//        menuItemListAllSvcChgs.setAccelerator(
//                KeyCombination.keyCombination("Ctrl+V"));
//        menuItemFindAccount.setAccelerator(
//                KeyCombination.keyCombination("Ctrl+F"));
//        menuItemListAllAccts.setAccelerator(
//                KeyCombination.keyCombination("Ctrl+L"));
//        menuItemEnterTransaction.setAccelerator(
//                KeyCombination.keyCombination("Ctrl+E"));
//
//        ta = new TextArea();
//        ta.setFont(Font.font("Monospaced", 12));
//        ta.setPrefRowCount(20);
//        ta.setPrefColumnCount(50);
//
//        VBox vBox = new VBox(10);
//        vBox.getChildren().addAll(menuBar, ta);
//
//        Scene scene = new Scene(vBox, 325, 300);
//
//        primaryStage.setTitle("Checking Account Options");
//        primaryStage.setScene(scene);
//        primaryStage.show();
//
//        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
//            public void handle(WindowEvent t) {
//                try {
//                    toServer.writeInt(-1);
//                    toServer.flush();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//
//
//    }
//
//    /**
//     * @param args the command line arguments
//     */
//    public static void main(String[] args) {
//        launch(args);
//    }
//
//    public static Optional<Pair<String,String>> logonAccount() {
//        // Create the custom dialog.
//        Dialog<Pair<String, String>> dialog = new Dialog<>();
//        dialog.setTitle("Logon to Your Account");
//        dialog.setHeaderText("Please enter your user name and password");
//
//        // Set the button types
//        ButtonType loginButtonType = new ButtonType("Login", ButtonData.OK_DONE);
//        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);
//
//        // Create the username and password labels and fields.
//        GridPane grid = new GridPane();
//        grid.setHgap(10);
//        grid.setVgap(10);
//        grid.setPadding(new Insets(20, 150, 10, 10));
//
//        TextField username = new TextField();
//        username.setPromptText("Username");
//        PasswordField password = new PasswordField();
//        password.setPromptText("Password");
//
//        grid.add(new Label("Username:"), 0, 0);
//        grid.add(username, 1, 0);
//        grid.add(new Label("Password:"), 0, 1);
//        grid.add(password, 1, 1);
//
//        // Enable/Disable login button depending on whether a username was entered.
//        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
//        loginButton.setDisable(true);
//
//        // Do some validation (using the Java 8 lambda syntax).
//        username.textProperty().addListener((observable, oldValue, newValue) -> {
//            loginButton.setDisable(newValue.trim().isEmpty());
//        });
//
//        dialog.getDialogPane().setContent(grid);
//
//        // Request focus on the username field by default.
//        Platform.runLater(() -> username.requestFocus());
//
//        // Convert the result to a username-password-pair when the login button is clicked.
//        dialog.setResultConverter(dialogButton -> {
//            if (dialogButton == loginButtonType) {
//                return new Pair<>(username.getText(), password.getText());
//            }
//            return null;
//        });
//
//        Optional<Pair<String, String>> result = dialog.showAndWait();
//
//
//
//        return result;
//    }
//
//    public static void addAccount() {
//        String name;
//        String balanceString;
//        double balance;
//
//        // get initial name and balance from the user
//        TextInputDialog dialog = new TextInputDialog("Name");
//        dialog.setTitle("Enter Account Name");
//        dialog.setHeaderText(null);
//        dialog.setContentText("Enter the account name:");
//
//        Optional<String> result = dialog.showAndWait();
//        while (!result.isPresent()){
//            result = dialog.showAndWait();
//        }
//        name = result.get();
//
//        dialog = new TextInputDialog("Balance");
//        dialog.setTitle("Enter Initial Balance");
//        dialog.setHeaderText(null);
//        dialog.setContentText("Enter the initial balance:");
//
//        result = dialog.showAndWait();
//        while (!result.isPresent()){
//            result = dialog.showAndWait();
//        }
//        balanceString = result.get();
//        balance = Double.parseDouble(balanceString);
//
//
//
//
//        ca = new CheckingAccount (name, balance);
//        ca.setBelow500(false);
//
//        try {
//            toServer.writeInt(1);
//            toServer.writeUTF(ca.getName());
//            toServer.writeDouble(ca.getBalance());
//            toServer.flush();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        ta.setText ("New account added for " + name);
//    }
//
//    public static void enterTransaction() {
//        int transCode;
//        int checkNum;
//        double checkAmt;
//
//        if (ca == null) {
//            nullAccount();
//            return;
//        }
//
//        // get the transaction code from the user
//        //    and process it with appropriate helper method
//        Alert alert;
//        transCode = getTransCode();
//        switch (transCode) {
//            case 0:
//                String message = "Transaction: End\n"
//                    + "Current Balance: " + nf.format(ca.getBalance())
//                    + "\nTotal Service Charge: " + nf.format(ca.getServiceCharge())
//                    + "\nFinal Balance: " + nf.format(ca.getBalance()
//                        - ca.getServiceCharge());
//                alert = new Alert(AlertType.INFORMATION);
//                alert.setTitle("End");
//                alert.setHeaderText(null);
//                alert.setContentText(message);
//                alert.showAndWait();
//                break;
//            case 1:
//                checkNum = getCheckNumber();
//                checkAmt = getCheckAmt();
//                processCheck (checkNum, checkAmt);
//                break;
//            case 2:
//                processDeposit ();
//                break;
//            default:
//                alert = new Alert(AlertType.ERROR);
//                alert.setTitle("Invalid Transaction Code");
//                alert.setHeaderText(null);
//                alert.setContentText("Please enter a valid transaction code.");
//                alert.showAndWait();
//        }
//    }
//
//    public static int getTransCode()
//    {
//        String transCodeString;
//        int tCode;
//
//        TextInputDialog dialog = new TextInputDialog("0 - Exit; 1 - Check; 2 - Deposit");
//        dialog.setTitle("Enter Transaction Code");
//        dialog.setHeaderText(null);
//        dialog.setContentText("Enter your transaction code:");
//
//        Optional<String> result = dialog.showAndWait();
//        while (!result.isPresent()){
//            result = dialog.showAndWait();
//        }
//        transCodeString = result.get();
//
//        tCode = Integer.parseInt(transCodeString);
//        return tCode;
//    }
//
//    public static int getCheckNumber()
//    {
//        String checkNumString;
//        int cNum;
//
//        TextInputDialog dialog = new TextInputDialog("Check #");
//        dialog.setTitle("Enter Check Number");
//        dialog.setHeaderText(null);
//        dialog.setContentText("Enter your check number:");
//
//        Optional<String> result = dialog.showAndWait();
//        while (!result.isPresent()){
//            result = dialog.showAndWait();
//        }
//        checkNumString = result.get();
//
//        cNum = Integer.parseInt(checkNumString);
//        return cNum;
//    }
//
//    public static double getCheckAmt()
//    {
//        String checkAmtString;
//        double cAmt;
//
//        TextInputDialog dialog = new TextInputDialog("Check Amount");
//        dialog.setTitle("Enter Check Amount");
//        dialog.setHeaderText(null);
//        dialog.setContentText("Enter your check amount:");
//
//        Optional<String> result = dialog.showAndWait();
//        while (!result.isPresent()){
//            result = dialog.showAndWait();
//        }
//        checkAmtString = result.get();
//        cAmt = Double.parseDouble(checkAmtString);
//        return cAmt;
//    }
//
//    public static void processCheck(int checkNum, double amt)
//    {
//        Transaction t;
//        ca.setBalance(amt, 1);
//        t = new Check(ca.getTransCount(), 1, amt, checkNum);
//        ca.addTrans(t);
//        ca.setServiceCharge(0.15);
//        t = new Transaction(ca.getTransCount(), 0, 0.15);
//        ca.addTrans(t);
//        String message = ca.getName() + "'s Account\nTransaction: Check #"
//            + checkNum + " in Amount of " + nf.format(amt)
//            + "\nCurrent Balance: " + nf.format (ca.getBalance())
//            + "\nService Charge: Check --- charge $0.15";
//        if (ca.getBalance() < 500 && !(ca.getBelow500())) {
//            ca.setServiceCharge(5.00);
//            ca.setBelow500(true);
//            message += "\nServiceCharge: Below $500 --- charge $5.00";
//            t = new Transaction(ca.getTransCount(), 0, 5.00);
//            ca.addTrans(t);
//        }
//        if (ca.getBalance() < 50) {
//            message += "\nWarning: Balance below $50";
//        }
//        if (ca.getBalance() < 0) {
//            ca.setServiceCharge(10.00);
//            message += "\nServiceCharge: Below $0 --- charge $10.00";
//            t = new Transaction(ca.getTransCount(), 0, 10.00);
//            ca.addTrans(t);
//        }
//
//        try {
//            toServer.writeInt(2);
//            toServer.writeInt(checkNum);
//            toServer.writeDouble(checkNum);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
//        message += "\nTotal Service Charge: " + nf.format (ca.getServiceCharge());
//        Alert alert = new Alert(AlertType.INFORMATION);
//        alert.setTitle("Check Processed");
//        alert.setHeaderText(null);
//        alert.setContentText(message);
//        alert.showAndWait();
//
//        //send check details to the sever
//
//    }
//
//    public static void processDeposit()
//    {
//        DepositPanel dp = new DepositPanel();
//        double cashAmt = dp.getCashAmount();
//        double checkAmt = dp.getCheckAmount();
//        double amt = cashAmt + checkAmt;
//        Transaction t;
//        ca.setBalance(amt, 2);
//        t = new Deposit(ca.getTransCount(), 2, amt, cashAmt, checkAmt);
//        ca.addTrans(t);
//        ca.setServiceCharge(0.1);
//        t = new Transaction(ca.getTransCount(), 0, 0.1);
//        ca.addTrans(t);
//        String message = ca.getName()
//            + "'s Account\nTransaction: Deposit in Amount of " + nf.format(amt)
//            + "\nCurrent Balance: " + nf.format (ca.getBalance())
//            + "\nService Charge: Deposit --- charge $0.10"
//            + "\nTotal Service Charge: " + nf.format (ca.getServiceCharge());
//
//        //send deposit details to sever
//        try {
//            toServer.writeInt(3);                //client decision
//            toServer.writeDouble(amt);              //amount to change balance
//
//            toServer.writeDouble(cashAmt);          //cash amount
//            toServer.writeDouble(checkAmt);         //check amount
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        Alert alert = new Alert(AlertType.INFORMATION);
//        alert.setTitle("Deposit Processed");
//        alert.setHeaderText(null);
//        alert.setContentText(message);
//        alert.showAndWait();
//
//
//    }
//
//    public static void listAllTransactions() {
//        if (ca == null) {
//            nullAccount();
//            return;
//        }
//
//        String message = "List All Transactions\nName: " + ca.getName()
//            + "\nBalance: " + nf.format(ca.getBalance())
//            + "\nTotal Service Charge: " + nf.format(ca.getServiceCharge())
//            + "\n\nID     Type      Amount\n";
//        for (int i = 0; i < ca.getTransCount(); i++) {
//            message += String.format("%-7d", i);
//            switch (ca.getTrans(i).getTransId()) {
//                case 0:
//                    message += "Svc. Chg. ";
//                    break;
//                case 1:
//                    message += "Check     ";
//                    break;
//                case 2:
//                    message += "Deposit   ";
//                    break;
//            }
//            message += String.format("$%-7.2f\n", ca.getTrans(i).getTransAmount());
//        }
//
//        ta.setText(message);
//
//
//
//    }
//
//    public static void listAllChecks() {
//        if (ca == null) {
//            nullAccount();
//            return;
//        }
//
//        String message = "List All Checks\nName: " + ca.getName()
//            + "\nBalance: " + nf.format(ca.getBalance())
//            + "\nTotal Service Charge: " + nf.format(ca.getServiceCharge())
//            + "\n\nID     Check     Amount\n";
//        for (int i = 0; i < ca.getTransCount(); i++) {
//            if (ca.getTrans(i).getTransId() == 1) {
//                Check c = (Check) ca.getTrans(i);
//                message += String.format("%-7d", i);
//                message += String.format("%-10d", c.getCheckNumber());
//                message += String.format("$%-7.2f\n", ca.getTrans(i).getTransAmount());
//            }
//        }
//
//        ta.setText(message);
//    }
//
//    public static void listAllDeposits() {
//        if (ca == null) {
//            nullAccount();
//            return;
//        }
//
//        String message = "List All Deposits\nName: " + ca.getName()
//            + "\nBalance: " + nf.format(ca.getBalance())
//            + "\nTotal Service Charge: " + nf.format(ca.getServiceCharge())
//            + "\n\nID     Cash        Check        Amount\n";
//        for (int i = 0; i < ca.getTransCount(); i++) {
//            if (ca.getTrans(i).getTransId() == 2) {
//                Deposit d = (Deposit) ca.getTrans(i);
//                message += String.format("%-7d", i);
//                message += String.format("$%-10.2f ", d.getCashAmount());
//                message += String.format("$%-10.2f  ", d.getCheckAmount());
//                message += String.format("$%-10.2f\n", ca.getTrans(i).getTransAmount());
//            }
//        }
//
//        ta.setText(message);
//    }
//
//    public static void listAllServiceCharges() {
//        if (ca == null) {
//            nullAccount();
//            return;
//        }
//
//        String message = "List All Service Charges\nName: " + ca.getName()
//            + "\nBalance: " + nf.format(ca.getBalance())
//            + "\nTotal Service Charge: " + nf.format(ca.getServiceCharge())
//            + "\n\nID     Amount\n";
//        for (int i = 0; i < ca.getTransCount(); i++) {
//            if (ca.getTrans(i).getTransId() == 0) {
//                message += String.format("%-7d", i);
//                message += String.format("$%-7.2f\n", ca.getTrans(i).getTransAmount());
//            }
//        }
//
//        ta.setText(message);
//    }
//
//    public static void findAccount() {
//        String name;
//
//        TextInputDialog dialog = new TextInputDialog("Name");
//        dialog.setTitle("Enter Account Name");
//        dialog.setHeaderText(null);
//        dialog.setContentText("Enter the account name:");
//
//        Optional<String> result = dialog.showAndWait();
//        while (!result.isPresent()){
//            result = dialog.showAndWait();
//        }
//        name = result.get();
//
//	// search through vector
////	for (int index = 0; index < caVector.size(); index++)
////	{
////            CheckingAccount c = caVector.elementAt(index);
////
////	    // check on the name of the account
////	    if (name.equals(c.getName())) {
////                ta.setText("Found account for " + name);
////                ca = caVector.elementAt(index);
////                accountNumber = index;
////                return;
////            }
////	}
//
//        ta.setText("Account not found for " + name);
//    }
//
//    public static void listAllAccounts() {
//        String message = "List of All Accounts:\n\n";
////	for (int index = 0; index < caVector.size(); index++)
////	{
////            message += "Name: " + caVector.elementAt(index).getName()
////                    + "\nBalance: " + nf.format(caVector.elementAt(index).getBalance())
////                    + "\nTotal Service Charge: " + nf.format(caVector.elementAt(index).getServiceCharge())
////                    + "\n\n";
////        }
//
//        ta.setText(message);
//    }
//
////    public static void openFile(Stage primaryStage) {
////        if (!saved)
////        {
////            String message = "The data in the application is not saved.\n"+
////                "would you like to save it before reading the new file in?";
////            Alert alert = new Alert(AlertType.CONFIRMATION);
////            alert.setTitle("Save Accounts?");
////            alert.setHeaderText(null);
////            alert.setContentText(message);
////
////            ButtonType yes = new ButtonType("Yes", ButtonData.OK_DONE);
////            ButtonType no = new ButtonType("No", ButtonData.CANCEL_CLOSE);
////            alert.getButtonTypes().setAll(yes, no);
////
////            Optional<ButtonType> confirm = alert.showAndWait();
////            if (confirm.get() == yes)
////                chooseFile(2, primaryStage);
////        }
////        chooseFile(1, primaryStage);
////	try {
////            FileInputStream fis = new FileInputStream(filename);
////            ObjectInputStream in = new ObjectInputStream(fis);
////            caVector = (Vector<CheckingAccount>)in.readObject();
////            ca = caVector.elementAt(0);
////            accountNumber = 0;
////            in.close();
////            saved = true;
////	}
////	catch (ClassNotFoundException e)
////        {
////            System.out.println(e);
////        }
////        catch (IOException ie)
////        {
////            System.out.println(ie);
////        }
////    }
////
////    public static void saveFile(Stage primaryStage) {
////        chooseFile(2, primaryStage);
////      	try {
////            FileOutputStream fos = new FileOutputStream(filename);
////            ObjectOutputStream out = new ObjectOutputStream(fos);
////            out.writeObject(caVector);
////            out.close();
////            saved = true;
////	}
////	catch (IOException e)
////        {
////            System.out.println(e);
////        }
////    }
////
////    public static void chooseFile(int ioOption, Stage stage)
////    {
////        File file;
////        String message = "Would you like to use the current default file: \n" +
////            filename;
////        Alert alert = new Alert(AlertType.CONFIRMATION);
////        alert.setTitle("Current File?");
////        alert.setHeaderText(null);
////        alert.setContentText(message);
////
////        ButtonType yes = new ButtonType("Yes", ButtonData.OK_DONE);
////        ButtonType no = new ButtonType("No", ButtonData.CANCEL_CLOSE);
////        alert.getButtonTypes().setAll(yes, no);
////
////        Optional<ButtonType> confirm = alert.showAndWait();
////        if (confirm.get() == yes)
////            return;
////        FileChooser chooser = new FileChooser();
////        if (ioOption == 1) {
////            chooser.setTitle("Open File");
////            file = chooser.showOpenDialog (stage);
////        }
////        else {
////            chooser.setTitle("Save File");
////            file = chooser.showSaveDialog (stage);
////        }
////        if (file == null) {
////            return;
////        }
////        filename = file.getPath();
////    }
//
//    public static void nullAccount() {
//        Alert alert = new Alert(AlertType.ERROR);
//        alert.setTitle("Account Not Selected");
//        alert.setHeaderText(null);
//        alert.setContentText("You must select an account first.");
//        alert.showAndWait();
//    }
//
//
//
//}
//
