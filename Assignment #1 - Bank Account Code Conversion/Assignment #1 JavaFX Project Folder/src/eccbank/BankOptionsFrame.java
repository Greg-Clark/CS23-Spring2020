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

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import java.io.File;
import java.util.Optional;


public class BankOptionsFrame extends Stage {
    public static final int WIDTH = 300;
    public static final int HEIGHT = 200;
    private Menu fileMenu, accountsMenu, transactionsMenu;
    private MenuItem openFile, saveFile, addAccount, listAllTransactions,
        listAllChecks, listAllDeposits, listAllServiceCharges, findAccount,
        listAllAccounts, enterTransaction;
    private MenuBar bar;

    private TextArea textArea;

    public void setTextArea(String string)
    {
        textArea.setText(string);
    }


    public BankOptionsFrame(String title) {
        setTitle(title);

        setResizable(true);
        setWidth(WIDTH);
        setHeight(HEIGHT);



        fileMenu = new Menu("File");
        
        openFile = new MenuItem("Open File");
        openFile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ECCBank.openFile();
            }
        });
        fileMenu.getItems().add(openFile);

        saveFile = new MenuItem("Save File");
        saveFile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ECCBank.saveFile();
            }
        });
        fileMenu.getItems().add(saveFile);

        accountsMenu = new Menu("Accounts");
        
        addAccount = new MenuItem("Add New Account");
        addAccount.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ECCBank.addAccount();
            }
        });
        accountsMenu.getItems().add(addAccount);

        listAllTransactions = new MenuItem("List All Transactions");
        listAllTransactions.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ECCBank.listAllTransactions();
            }
        });
        accountsMenu.getItems().add(listAllTransactions);

        listAllChecks = new MenuItem("List All Checks");
        listAllChecks.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ECCBank.listAllChecks();
            }
        });
        accountsMenu.getItems().add(listAllChecks);

        listAllDeposits = new MenuItem("List All Deposits");
        listAllDeposits.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ECCBank.listAllDeposits();
            }
        });
        accountsMenu.getItems().add(listAllDeposits);

        listAllServiceCharges = new MenuItem("List All Service Charges");
        listAllServiceCharges.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ECCBank.listAllServiceCharges();
            }
        });
        accountsMenu.getItems().add(listAllServiceCharges);

        findAccount = new MenuItem("Find An Account");
        findAccount.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ECCBank.findAccount();
            }
        });
        accountsMenu.getItems().add(findAccount);

        listAllAccounts = new MenuItem("List All Accounts");
        listAllAccounts.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ECCBank.listAllAccounts();
            }
        });
        accountsMenu.getItems().add(listAllAccounts);

        transactionsMenu = new Menu("Transactions");
        
        enterTransaction = new MenuItem("Enter Transaction");
        enterTransaction.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ECCBank.enterTransaction();
            }
        });
        transactionsMenu.getItems().add(enterTransaction);

        bar = new MenuBar( );
        bar.getMenus().add(fileMenu);
        bar.getMenus().add(accountsMenu);
        bar.getMenus().add(transactionsMenu);

        textArea = new TextArea();
        textArea.setPrefHeight(10000);

        setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                if(!ECCBank.saved)
                {
                    String message = "The data in the application is not saved.\n"+
                            "Would you like to save it before exiting the application?";
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Select an Option");
                    alert.setHeaderText(message);
                    Optional<ButtonType> result = alert.showAndWait();
                    File file;
                    if(result.get() == ButtonType.OK)
                    {
                        ECCBank.chooseFile(2);
                    }
                    System.exit(0);
                }
            }
        });

        VBox vbox = new VBox(bar, textArea);
        Scene scene = new Scene(vbox, WIDTH, HEIGHT);
        setScene(scene);
        show();
    }
}
