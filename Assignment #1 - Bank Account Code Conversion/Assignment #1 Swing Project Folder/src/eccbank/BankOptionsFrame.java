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
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class BankOptionsFrame extends JFrame {
    public static final int WIDTH = 300;
    public static final int HEIGHT = 200;
    private JMenu fileMenu, accountsMenu, transactionsMenu;
    private JMenuItem openFile, saveFile, addAccount, listAllTransactions,
        listAllChecks, listAllDeposits, listAllServiceCharges, findAccount,
        listAllAccounts, enterTransaction;
    private JMenuBar bar;

    /** Creates a new instance of JFrameL */
    public BankOptionsFrame(String title) {
        super(title);

        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);       

        BankFrameListener listener = new BankFrameListener();
        addWindowListener(listener);

        BankMenuListener ml = new BankMenuListener();     

        fileMenu = new JMenu("File");
        
        openFile = new JMenuItem("Open File");
        openFile.addActionListener(ml);
        fileMenu.add(openFile);

        saveFile = new JMenuItem("Save File");
        saveFile.addActionListener(ml);
        fileMenu.add(saveFile);

        accountsMenu = new JMenu("Accounts");
        
        addAccount = new JMenuItem("Add New Account");
        addAccount.addActionListener(ml);
        accountsMenu.add(addAccount);

        listAllTransactions = new JMenuItem("List All Transactions");
        listAllTransactions.addActionListener(ml);
        accountsMenu.add(listAllTransactions);

        listAllChecks = new JMenuItem("List All Checks");
        listAllChecks.addActionListener(ml);
        accountsMenu.add(listAllChecks);

        listAllDeposits = new JMenuItem("List All Deposits");
        listAllDeposits.addActionListener(ml);
        accountsMenu.add(listAllDeposits);

        listAllServiceCharges = new JMenuItem("List All Service Charges");
        listAllServiceCharges.addActionListener(ml);
        accountsMenu.add(listAllServiceCharges);

        findAccount = new JMenuItem("Find An Account");
        findAccount.addActionListener(ml);
        accountsMenu.add(findAccount);

        listAllAccounts = new JMenuItem("List All Accounts");
        listAllAccounts.addActionListener(ml);
        accountsMenu.add(listAllAccounts);

        transactionsMenu = new JMenu("Transactions");
        
        enterTransaction = new JMenuItem("Enter Transaction");
        enterTransaction.addActionListener(ml);
        transactionsMenu.add(enterTransaction);

        bar = new JMenuBar( );
        bar.add(fileMenu);
        bar.add(accountsMenu);
        bar.add(transactionsMenu);
        setJMenuBar(bar);
    }

    //*****************************************************************
    //  Represents the listener for the menu items
    //*****************************************************************
    private class BankMenuListener implements ActionListener
    {
        //--------------------------------------------------------------
        //  Calls the method to process the option for which radio
        //  button was pressed.
        //--------------------------------------------------------------
        public void actionPerformed (ActionEvent event)
        {
         String source = event.getActionCommand();
            if (source == "Open File") {
               ECCBank.openFile();
            }
            else if (source == "Save File") {
               ECCBank.saveFile();
            }
            else if (source == "Add New Account") {
               ECCBank.addAccount();
            }
            else if (source == "List All Transactions") {
               ECCBank.listAllTransactions();
            }
            else if (source == "List All Checks") {
               ECCBank.listAllChecks();
            }
            else if (source == "List All Deposits") {
               ECCBank.listAllDeposits();
            }
            else if (source == "List All Service Charges") {
               ECCBank.listAllServiceCharges();
            }
            else if (source == "Find An Account") {
               ECCBank.findAccount();
            }
            else if (source == "List All Accounts") {
               ECCBank.listAllAccounts();
            }
            else if (source == "Enter Transaction") {
               ECCBank.enterTransaction();
            }
        }
    }

    private class BankFrameListener extends WindowAdapter
    {
        public void windowClosing(WindowEvent e) {
            int confirm;

            Font font = new Font("Arial", Font.BOLD, 12);
            UIManager.put("OptionPane.messageFont", font);
     
            if (!ECCBank.saved)
            {
                String message = "The data in the application is not saved.\n"+
                    "Would you like to save it before exiting the application?";
                confirm = JOptionPane.showConfirmDialog (null, message);
                if (confirm == JOptionPane.YES_OPTION)
                    ECCBank.chooseFile(2); 
            }
           System.exit(0);
        }
    }   
}
