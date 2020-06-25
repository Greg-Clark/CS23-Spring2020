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


import java.util.Optional;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;


public class DepositPanel {
    double cashAmount;
    double checkAmount;

    public DepositPanel() {



        TextField cashField = new TextField();
        cashField.setPrefColumnCount(5);
        cashField.setPrefWidth(200);
        TextField checkField = new TextField();
        checkField.setPrefColumnCount(5);

        Dialog dialog = new Dialog();
        GridPane depositPanel = new GridPane();
        depositPanel.setVgap(10);
        depositPanel.setPadding(new Insets(20, 150, 10, 10));

        depositPanel.add(new Label("Cash"), 0, 0);
        depositPanel.add(cashField, 0, 1);
        depositPanel.add(new Label("Check"), 0, 2);
        depositPanel.add(checkField, 0, 3);

        dialog.getDialogPane().setContent(depositPanel);

        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButton, ButtonType.CANCEL);


        Optional<ButtonType> result = dialog.showAndWait();
        if(result.get() == okButton)
        {
            if (cashField.getText().equals("")) {
                cashAmount = 0.0;
            }
            else {
                cashAmount = Double.parseDouble(cashField.getText());
                if(cashAmount < 0)
                {
                    throw new NumberFormatException();
                }
            }
            if (checkField.getText().equals("")) {
                checkAmount = 0.0;
            }
            else {
                checkAmount = Double.parseDouble(checkField.getText());
                if(checkAmount < 0)
                {
                    throw new NumberFormatException();
                }
            }
        }
    }

    public double getCashAmount() {
        return cashAmount;
    }

    public double getCheckAmount() {
        return checkAmount;
    }
}
