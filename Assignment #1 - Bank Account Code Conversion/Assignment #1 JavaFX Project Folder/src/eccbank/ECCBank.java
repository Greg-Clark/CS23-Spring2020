/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eccbank;

/**
 *
 * @author Greg Clark
 */



import java.io.*;
import java.text.NumberFormat;
import java.util.Optional;
import java.util.Vector;

import javafx.application.Application;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class ECCBank extends Application {
    static BankOptionsFrame frame;
    static Vector<CheckingAccount> caVector;
    static CheckingAccount ca = null;
    static NumberFormat nf = NumberFormat.getCurrencyInstance();
    static String filename = "C:\\acct.dat";
    static boolean saved = true;
    static int accountNumber = 0;

    public void start(Stage stage)
    {
        caVector = new Vector<CheckingAccount>();

        frame = new BankOptionsFrame ("ECC Bank");
//        ta.setFont(Font.font("Monospaced", FontWeight.NORMAL, 12));
    }

    public static void addAccount() {
        String name;
        String balanceString;
        double balance;

        frame.hide();   //hides the addAccount scene
//        Font font = new Font("Arial", Font.BOLD, 12);
//        UIManager.put("OptionPane.messageFont", font);

        if (ca != null) {
            caVector.setElementAt(ca, accountNumber);
        }

        // get initial name and balance from the user
        TextInputDialog nameDialog = new TextInputDialog();
        nameDialog.setTitle("Input");
        nameDialog.setHeaderText("Enter the Account name:");
        Optional<String> nameResult = nameDialog.showAndWait();
        if(nameResult.isPresent())
        {
            name = nameResult.get();
        }
        else
            name = "";


        TextInputDialog balanceDialog = new TextInputDialog();
        balanceDialog.setTitle("Input");
        balanceDialog.setHeaderText("Enter your initial balance:");
        Optional<String> balanceResult = balanceDialog.showAndWait();
        if(balanceResult.isPresent())
        {
            balanceString = balanceResult.get();
        }
        else
            balanceString = "";


        try{
            balance = Double.parseDouble(balanceString);
            if(balance < 0)
            {
                throw new NumberFormatException();
            }
        }
        catch(NumberFormatException e)
        {
            invalidInputAlert();
            return;
        }
        ca = new CheckingAccount (name, balance);
        ca.setBelow500(false);
        caVector.addElement(ca);
        accountNumber = caVector.size() - 1;
        saved = false;

        frame.setTextArea("New account added for " + name);

        frame.show();
    }

    public static void invalidInputAlert()
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Warning!");
        alert.setHeaderText("Invalid input. Please enter a valid number");
        alert.showAndWait();
        frame.show();
    }

    public static void enterTransaction() {
        int transCode;
        int checkNum;
        double checkAmt;

        if (ca == null) {
            nullAccount();
            return;
        }


        frame.hide();
        //still need to add a different font
//        Font font = new Font("Arial", Font.BOLD, 12);
//        UIManager.put("OptionPane.messageFont", font);

        // get the transaction code from the user
        //    and process it with appropriate helper method
        transCode = getTransCode();

        try{
            switch (transCode) {
                case 0:
                    String message = "Transaction: End\n"
                            + "Current Balance: " + nf.format(ca.getBalance())
                            + "\nTotal Service Charge: " + nf.format(ca.getServiceCharge())
                            + "\nFinal Balance: " + nf.format(ca.getBalance()
                            - ca.getServiceCharge());
                    Alert messageAlert = new Alert(Alert.AlertType.INFORMATION);
                    messageAlert.setTitle("Message");
                    messageAlert.setHeaderText(message);
                    messageAlert.showAndWait();
                    break;
                case 1:
                    checkNum = getCheckNumber();
                    checkAmt = getCheckAmt();
                    processCheck(checkNum, checkAmt);
                    break;
                case 2:
                    processDeposit();
                    break;
                default:
                    Alert defaultAlert = new Alert(Alert.AlertType.INFORMATION);
                    defaultAlert.setTitle("Message");
                    defaultAlert.setHeaderText("Invalid Transaction Code");
                    defaultAlert.showAndWait();
            }
        }
        catch(NumberFormatException e)
        {
            invalidInputAlert();
            return;
        }

        frame.show();
    }

    public static int getTransCode()
    {
        String transCodeString;
        int tCode;
        TextInputDialog tcodeDialog = new TextInputDialog();
        tcodeDialog.setTitle("Input");
        tcodeDialog.setHeaderText("Enter your transaction code:");
        Optional<String> tcodeResult = tcodeDialog.showAndWait();
        if(tcodeResult.isPresent())
        {
            transCodeString = tcodeResult.get();
        }
        else
            transCodeString = "";
        try{
            tCode = Integer.parseInt(transCodeString);
        }
        catch(NumberFormatException e)
        {
            return -1;
        }
        return tCode;
    }

    public static int getCheckNumber()
    {
        String checkNumString;
        int cNum;
        TextInputDialog cNumDialog = new TextInputDialog();
        cNumDialog.setTitle("Input");
        cNumDialog.setHeaderText("Enter your check number:");
        Optional<String> cNumResult = cNumDialog.showAndWait();
        if(cNumResult.isPresent())
        {
            checkNumString = cNumResult.get();
        }
        else
            checkNumString = "";
        cNum = Integer.parseInt(checkNumString);
        if(cNum < 0)
        {
            throw new NumberFormatException();
        }

        return cNum;
    }

    public static double getCheckAmt()
    {
        String checkAmtString;
        double cAmt;
        TextInputDialog cAmtDialog = new TextInputDialog();
        cAmtDialog.setTitle("Input");
        cAmtDialog.setHeaderText("Enter your check amount");
        Optional<String> cAmtResult = cAmtDialog.showAndWait();
        if(cAmtResult.isPresent())
        {
            checkAmtString = cAmtResult.get();
        }
        else
            checkAmtString = "";
        cAmt = Double.parseDouble(checkAmtString);
        if(cAmt < 0)
        {
            throw new NumberFormatException();
        }

        return cAmt;
    }

    public static void processCheck(int checkNum, double amt)
    {
        Transaction t;
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
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Message");
        alert.setHeaderText(message);
        alert.showAndWait();
        saved = false;
    }

    public static void processDeposit()
    {
        DepositPanel dp = new DepositPanel();
        double cashAmt = dp.getCashAmount();
        double checkAmt = dp.getCheckAmount();
        double amt = cashAmt + checkAmt;
        Transaction t;
        ca.setBalance(amt, 2);
        t = new Deposit(ca.getTransCount(), 2, amt, cashAmt, checkAmt);
        ca.addTrans(t);
        ca.setServiceCharge(0.1);
        t = new Transaction(ca.getTransCount(), 0, 0.1);
        ca.addTrans(t);
        String message = ca.getName() 
            + "'s Account\nTransaction: Deposit in Amount of " + nf.format(amt)
            + "\nCurrent Balance: " + nf.format (ca.getBalance())
            + "\nService Charge: Deposit --- charge $0.10"
            + "\nTotal Service Charge: " + nf.format (ca.getServiceCharge());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Message");
        alert.setHeaderText(message);
        alert.showAndWait();
        saved = false;
    }

    public static void listAllTransactions() {
        if (ca == null) {
            nullAccount();
            return;
        }

//        Font font = new Font("Monospaced", Font.PLAIN, 12);
//        UIManager.put("OptionPane.messageFont", font);

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

        frame.setTextArea(message);
    }

    public static void listAllChecks() {
        if (ca == null) {
            nullAccount();
            return;
        }

//        Font font = new Font("Monospaced", Font.PLAIN, 12);
//        UIManager.put("OptionPane.messageFont", font);

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

        frame.setTextArea(message);
    }

    public static void listAllDeposits() {
        if (ca == null) {
            nullAccount();
            return;
        }

//        Font font = new Font("Monospaced", Font.PLAIN, 12);
//        UIManager.put("OptionPane.messageFont", font);

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

        frame.setTextArea(message);
    }

    public static void listAllServiceCharges() {
        if (ca == null) {
            nullAccount();
            return;
        }

//        Font font = new Font("Monospaced", Font.PLAIN, 12);
//        UIManager.put("OptionPane.messageFont", font);

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

        frame.setTextArea(message);
    }

    public static void findAccount() {
        String name;

//        Font font = new Font("Arial", Font.BOLD, 12);
//        UIManager.put("OptionPane.messageFont", font);

        frame.hide();
        TextInputDialog tid = new TextInputDialog();
        tid.setTitle("Input");
        tid.setHeaderText("Enter the Account name:");
        Optional<String> result = tid.showAndWait();
        if(result.isPresent())
        {
            name = result.get();
        }
        else
            name = "";



	// search through vector
	for (int index = 0; index < caVector.size(); index++)
	{
            CheckingAccount c = caVector.elementAt(index);
					
	    // check on the name of the account
	    if (name.equals(c.getName())) {
                frame.setTextArea("Found account for " + name);
                ca = caVector.elementAt(index);
                accountNumber = index;
                //frame.setVisible(true);
            frame.show();
                return;
            }
	}

        frame.setTextArea("Account not found for " + name);
        frame.show();
    }

    public static void listAllAccounts() {

//        Font font = new Font("Monospaced", Font.PLAIN, 12);
//        UIManager.put("OptionPane.messageFont", font);

        String message = "List of All Accounts:\n\n";
	for (int index = 0; index < caVector.size(); index++)
	{
            message += "Name: " + caVector.elementAt(index).getName()
                    + "\nBalance: " + nf.format(caVector.elementAt(index).getBalance())
                    + "\nTotal Service Charge: " + nf.format(caVector.elementAt(index).getServiceCharge())
                    + "\n\n";
        }

        frame.setTextArea(message);
    }

    public static void openFile() {
        frame.hide();

//        Font font = new Font("Arial", Font.BOLD, 12);
//        UIManager.put("OptionPane.messageFont", font);

        if (!saved)
        {
            String message = "The data in the application is not saved.\n"+
                "would you like to save it before reading the new file in?";
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Select an Option");
            alert.setHeaderText(message);
            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == ButtonType.OK)
            {
                chooseFile(2);
            }
        }
        chooseFile(1);	
	try {
            FileInputStream fis = new FileInputStream(filename);
            ObjectInputStream in = new ObjectInputStream(fis);
            caVector = (Vector<CheckingAccount>)in.readObject();
            ca = caVector.elementAt(0);
            accountNumber = 0;
            in.close();
            saved = true;
	}	
	catch (ClassNotFoundException e)	
        { 
            System.out.println(e);
        }
        catch (IOException ie) 
        { 
            System.out.println(ie);
        }
        frame.show();

    }

    public static void saveFile() {
        frame.hide();
//        Font font = new Font("Arial", Font.BOLD, 12);
//        UIManager.put("OptionPane.messageFont", font);

        chooseFile(2);
      	try {
            FileOutputStream fos = new FileOutputStream(filename);
            ObjectOutputStream out = new ObjectOutputStream(fos);
            out.writeObject(caVector);
            out.close();
            saved = true;
	}	
	catch (IOException e)	
        { 
            System.out.println(e);
        }
      	frame.show();
    }

    public static void chooseFile(int ioOption) 
    {  
         String message = "Would you like to use the current default file: \n" + filename;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Select an Option");
        alert.setHeaderText(message);
        Optional<ButtonType> result = alert.showAndWait();
        File file;
        if(result.get() == ButtonType.OK)
        {
            return;
        }
        FileChooser chooser = new FileChooser();
        if (ioOption == 1)
            file = chooser.showOpenDialog(frame);

        else
            file = chooser.showSaveDialog(frame);
        if(file != null)
            filename = file.getPath();
    }

    public static void nullAccount() {

//        Font font = new Font("Arial", Font.BOLD, 12);
//        UIManager.put("OptionPane.messageFont", font);

        frame.hide();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Select an Option");
        alert.setHeaderText("You must select an account first.");
        alert.showAndWait();

        frame.show();
    }
}
