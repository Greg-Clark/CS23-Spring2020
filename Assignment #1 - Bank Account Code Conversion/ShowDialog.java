/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package showdialog;

import java.util.Optional;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 *
 * @author eambrosio
 */
public class ShowDialog extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        Button btnInfo = new Button();
        btnInfo.setText("Show Information Dialog");
        btnInfo.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("This is the header.");
                alert.setContentText("This is the body.");
                
                alert.showAndWait();
            }
        });
        
        Button btnConfirm = new Button();
        btnConfirm.setText("Show Confirmation Dialog");
        btnConfirm.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setHeaderText("Yo!!!!!");
                alert.setContentText("Are you OK with this?");
                
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    System.out.println("Clicked OK!");
                }
                else {
                    System.out.println("Clicked Cancel!");
                }
            }
        });

        Button btnInput = new Button();
        btnInput.setText("Show Input Dialog");
        btnInput.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                TextInputDialog alert = new TextInputDialog("Input");
                alert.setTitle("Input Dialog");
                alert.setHeaderText("Yo!!!!!");
                alert.setContentText("Enter your name:");
                
                Optional<String> result = alert.showAndWait();
                if (result.isPresent()) {
                    System.out.println("Your name is " + result.get());
                }
            }
        });
        
        HBox root = new HBox();
        root.getChildren().addAll(btnInfo, btnConfirm, btnInput);
        
        Scene scene = new Scene(root, 300, 250);
        
        primaryStage.setTitle("Dialogs!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
