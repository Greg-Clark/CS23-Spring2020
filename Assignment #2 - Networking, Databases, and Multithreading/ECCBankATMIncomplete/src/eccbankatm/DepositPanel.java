/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package eccbankatm;

/**
 *
 * @author sport
 */
import java.util.Optional;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.util.Pair;

public class DepositPanel {
    double cashAmount;
    double checkAmount;
    String cashString;
    String checkString;

    public DepositPanel() {
        
        Dialog<Pair<String, String>> dialog = new Dialog<>();        
        dialog.setTitle("Deposit Panel");
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(5);
        gridPane.setHgap(5);        
        
        Label cashLabel = new Label("Cash");
        TextField cashField = new TextField();
        cashField.setPromptText("Enter amount of cash to deposit.");
        HBox boxOne = new HBox();
        boxOne.getChildren().addAll(cashLabel, cashField);
        boxOne.setSpacing(10);
        gridPane.add(cashLabel, 0, 0);
        gridPane.add(cashField, 0, 1);

        Label checkLabel = new Label("Check");
        TextField checkField = new TextField();
        checkField.setPromptText("Enter amount in checks to deposit.");
        HBox boxTwo = new HBox();
        boxTwo.getChildren().addAll(checkLabel, checkField);
        boxTwo.setSpacing(10);
        gridPane.add(checkLabel, 0, 2);
        gridPane.add(checkField, 0, 3);

        dialog.getDialogPane().setContent(gridPane);
        
        Platform.runLater(() -> cashField.requestFocus());
        
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                return new Pair<>(cashField.getText(), checkField.getText());
            }
            return null;
        });
        
        Optional<Pair<String, String>> result = dialog.showAndWait();
        
        result.ifPresent(cashCheck -> {
            cashString = cashCheck.getKey();
            if (cashString.equals(""))
                cashAmount = 0;
            else
                cashAmount = Double.parseDouble(cashString);
            checkString = cashCheck.getValue();
            if (checkString.equals(""))
                checkAmount = 0;
            else
                checkAmount = Double.parseDouble(checkString);
        });
    }

    public double getCashAmount() {
        return cashAmount;
    }

    public double getCheckAmount() {
        return checkAmount;
    }
}
