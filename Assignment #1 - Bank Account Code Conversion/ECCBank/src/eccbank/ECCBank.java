/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eccbank;

/**
 *
 * @author sport
 */
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.io.*;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import java.text.NumberFormat;
import java.util.Vector;

public class ECCBank {
    static BankOptionsFrame frame;
    static Vector<CheckingAccount> caVector;
    static CheckingAccount ca = null;
    static NumberFormat nf = NumberFormat.getCurrencyInstance();
    static String filename = "C:\\acct.dat";
    static boolean saved = true;
    static JTextArea ta;
    static int accountNumber = 0;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        caVector = new Vector<CheckingAccount>();

        frame = new BankOptionsFrame ("ECC Bank");
        frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);

        ta = new JTextArea(10, 50);
        ta.setFont(new Font("Monospaced", Font.PLAIN, 12));
        frame.getContentPane().add(ta);

        frame.pack();
        frame.setVisible(true);

        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension screenSize = tk.getScreenSize();
        int screenHeight = screenSize.height;
        int screenWidth = screenSize.width;
        frame.setLocation(screenWidth / 2 - 150, screenHeight / 2 - 50);
    }

    public static void addAccount() {
        String name;
        String balanceString;
        double balance;

        frame.setVisible(false);
        Font font = new Font("Arial", Font.BOLD, 12);
        UIManager.put("OptionPane.messageFont", font);

        if (ca != null) {
            caVector.setElementAt(ca, accountNumber);
        }

        // get initial name and balance from the user
        name = 
            JOptionPane.showInputDialog("Enter the account name:");
        balanceString = 
            JOptionPane.showInputDialog("Enter your initial balance:");
        balance = Double.parseDouble(balanceString);
        ca = new CheckingAccount (name, balance);
        ca.setBelow500(false);
        caVector.addElement(ca);
        accountNumber = caVector.size() - 1;
        saved = false;

        ta.setText ("New account added for " + name);
        frame.setVisible(true);
    }

    public static void enterTransaction() {
        int transCode;
        int checkNum;
        double checkAmt;

        if (ca == null) {
            nullAccount();
            return;
        }

        frame.setVisible(false);
        Font font = new Font("Arial", Font.BOLD, 12);
        UIManager.put("OptionPane.messageFont", font);

        // get the transaction code from the user
        //    and process it with appropriate helper method
        transCode = getTransCode();
        switch (transCode) {
            case 0:
                String message = "Transaction: End\n"
                    + "Current Balance: " + nf.format(ca.getBalance())
                    + "\nTotal Service Charge: " + nf.format(ca.getServiceCharge())
                    + "\nFinal Balance: " + nf.format(ca.getBalance()
                        - ca.getServiceCharge());
                JOptionPane.showMessageDialog(null, message);
                break;
            case 1:
                checkNum = getCheckNumber();
                checkAmt = getCheckAmt();
                processCheck (checkNum, checkAmt);
                break;
            case 2:
                processDeposit ();
                break;
            default:
                JOptionPane.showMessageDialog(null, "Invalid transaction code");
        }
        frame.setVisible(true);
    }

    public static int getTransCode()
    {
        String transCodeString;
        int tCode;
        transCodeString =
            JOptionPane.showInputDialog("Enter your transaction code:");
        tCode = Integer.parseInt(transCodeString);
        return tCode;
    }

    public static int getCheckNumber()
    {
        String checkNumString;
        int cNum;
        checkNumString =
            JOptionPane.showInputDialog("Enter your check number:");
        cNum = Integer.parseInt(checkNumString);
        return cNum;
    }

    public static double getCheckAmt()
    {
        String checkAmtString;
        double cAmt;
        checkAmtString =
            JOptionPane.showInputDialog("Enter your check amount:");
        cAmt = Double.parseDouble(checkAmtString);
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
        JOptionPane.showMessageDialog(null, message);
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
        JOptionPane.showMessageDialog(null, message);
        saved = false;
    }

    public static void listAllTransactions() {
        if (ca == null) {
            nullAccount();
            return;
        }

        Font font = new Font("Monospaced", Font.PLAIN, 12);
        UIManager.put("OptionPane.messageFont", font);

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

        ta.setText(message);
    }

    public static void listAllChecks() {
        if (ca == null) {
            nullAccount();
            return;
        }

        Font font = new Font("Monospaced", Font.PLAIN, 12);
        UIManager.put("OptionPane.messageFont", font);

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

        ta.setText(message);
    }

    public static void listAllDeposits() {
        if (ca == null) {
            nullAccount();
            return;
        }

        Font font = new Font("Monospaced", Font.PLAIN, 12);
        UIManager.put("OptionPane.messageFont", font);

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

        ta.setText(message);
    }

    public static void listAllServiceCharges() {
        if (ca == null) {
            nullAccount();
            return;
        }

        Font font = new Font("Monospaced", Font.PLAIN, 12);
        UIManager.put("OptionPane.messageFont", font);

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

        ta.setText(message);
    }

    public static void findAccount() {
        String name;

        Font font = new Font("Arial", Font.BOLD, 12);
        UIManager.put("OptionPane.messageFont", font);

        frame.setVisible(false);
        name = JOptionPane.showInputDialog ("Enter the account name: ");

	// search through vector
	for (int index = 0; index < caVector.size(); index++)
	{
            CheckingAccount c = caVector.elementAt(index);
					
	    // check on the name of the account
	    if (name.equals(c.getName())) {
                ta.setText("Found account for " + name);
                ca = caVector.elementAt(index);
                accountNumber = index;
                frame.setVisible(true);
                return;
            }
	}

        ta.setText("Account not found for " + name);
        frame.setVisible(true);
    }

    public static void listAllAccounts() {
        Font font = new Font("Monospaced", Font.PLAIN, 12);
        UIManager.put("OptionPane.messageFont", font);

        String message = "List of All Accounts:\n\n";
	for (int index = 0; index < caVector.size(); index++)
	{
            message += "Name: " + caVector.elementAt(index).getName()
                    + "\nBalance: " + nf.format(caVector.elementAt(index).getBalance())
                    + "\nTotal Service Charge: " + nf.format(caVector.elementAt(index).getServiceCharge())
                    + "\n\n";
        }

        ta.setText(message);
    }

    public static void openFile() {
        frame.setVisible(false);
        Font font = new Font("Arial", Font.BOLD, 12);
        UIManager.put("OptionPane.messageFont", font);
        int confirm;  
        if (!saved)
        {
            String message = "The data in the application is not saved.\n"+
                "would you like to save it before reading the new file in?";
            confirm = JOptionPane.showConfirmDialog (null, message);
            if (confirm == JOptionPane.YES_OPTION)
                chooseFile(2); 
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
        frame.setVisible(true);
    }

    public static void saveFile() {
        frame.setVisible(false);
        Font font = new Font("Arial", Font.BOLD, 12);
        UIManager.put("OptionPane.messageFont", font);
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
        frame.setVisible(true);
    }

    public static void chooseFile(int ioOption) 
    {  
        int status, confirm;       
                
        String message = "Would you like to use the current default file: \n" +
            filename;
        confirm = JOptionPane.showConfirmDialog (null, message);
        if (confirm == JOptionPane.YES_OPTION)
            return;
        JFileChooser chooser = new JFileChooser();
        if (ioOption == 1)
            status = chooser.showOpenDialog (null);
        else
            status = chooser.showSaveDialog (null);
        if (status == JFileChooser.APPROVE_OPTION)
        {
            File file = chooser.getSelectedFile();
            filename = file.getPath();
        }
    }

    public static void nullAccount() {
        Font font = new Font("Arial", Font.BOLD, 12);
        UIManager.put("OptionPane.messageFont", font);

        frame.setVisible(false);
        JOptionPane.showMessageDialog (null, "You must select an account first.");
        frame.setVisible(true);
    }
}
