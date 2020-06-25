package sample;

//
//// Java Program to create a text input
//// dialog and add it to the stage
//import javafx.application.Application;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.layout.*;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import javafx.scene.control.*;
//import javafx.stage.Stage;
//import javafx.scene.control.Alert.AlertType;
//import java.time.LocalDate;
//import java.util.Optional;
//
//public class Main extends Application {
//
//    String b = "click on this dick bitch";
//    // launch the application
//    public void start(Stage s)
//    {
//        // set title for the stage
//        s.setTitle("creating textInput dialog");
//
//        // create a tile pane
//        TilePane r = new TilePane();
//
//        // create a text input dialog
//        TextInputDialog td = new TextInputDialog();
//
//        td.setTitle("input");
//
//        // setHeaderText
//        td.setHeaderText("enter your name");
//
//        Optional<String> result = td.showAndWait();
//
//        result.ifPresent(name -> {
//            this.label.setText(name);
//        });
//
//        // create a button
//        Button d = new Button(b);
//
//        // create a event handler
//        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
//            public void handle(ActionEvent e)
//            {
//                // show the text input dialog
//                td.show();
//            }
//        };
//
//        // set on action of event
//        d.setOnAction(event);
//
//        // add button and label
//        r.getChildren().add(d);
//
//        // create a scene
//        Scene sc = new Scene(r, 500, 300);
//
//        // set the scene
//        s.setScene(sc);
//
//        s.show();
//    }
//
//    public static void main(String args[])
//    {
//        // launch the application
//        launch(args);
//    }
//}















//import java.text.NumberFormat;
//import java.util.Optional;
//import java.util.Vector;
//
//import javafx.application.Application;
//        import javafx.event.ActionEvent;
//        import javafx.event.EventHandler;
//        import javafx.geometry.Insets;
//        import javafx.scene.Scene;
//import javafx.scene.control.*;
//import javafx.scene.layout.VBox;
//        import javafx.stage.Stage;
//
//public class Main extends Application {
//
//    private Label label;
//
//   static NumberFormat nf = NumberFormat.getCurrencyInstance();
//    static String filename = "C:\\acct.dat";
//    static boolean saved = true;
//    static TextArea ta;
//    static int accountNumber = 0;
//
//    private void showInputTextDialog() {
//
//        TextInputDialog dialog = new TextInputDialog();
//
//        dialog.setTitle("input");
//        dialog.setHeaderText("Enter your name:");
//        dialog.setContentText("Name:");
//
//        Optional<String> result = dialog.showAndWait();
//
//        result.ifPresent(name -> {
//            this.label.setText(name);
//        });
//
//        TextInputDialog dialog2 = new TextInputDialog();
//        dialog.setTitle("Input2");
//        dialog.setHeaderText("Enter the Account name:");
//        Optional<String> result2 = dialog.showAndWait();
//
//        result2.ifPresent(name -> {
//            this.label.setText(name);
//        });
//    }
//
//    private void showAlert() {
//        String message = "Transaction: End\n"
//                + "Current Balance: $25.00"
//                + "\nTotal Service Charge: $15.65"
//                + "\nFinal Balance: $40.65";
//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        alert.setTitle("Message");
//        alert.setHeaderText(message);
//        alert.showAndWait();
//    }
//
//
//    @Override
//    public void start(Stage stage) {
//
//        VBox root = new VBox();
//        root.setPadding(new Insets(10));
//        root.setSpacing(10);
//
//        this.label = new Label();
//
//        Button button = new Button("Enter your name");
//
//        button.setOnAction(new EventHandler<ActionEvent>() {
//
//            @Override
//            public void handle(ActionEvent event) {
//                showAlert();
//            }
//        });
//
//        root.getChildren().addAll(button, label);
//
//        Scene scene = new Scene(root, 450, 250);
//        stage.setTitle("JavaFX TextInputDialog (o7planning.org)");
//        stage.setScene(scene);
//
//        stage.show();
//
//    }
//
//    public static void main(String args[]) {
//        launch(args);
//    }
//
//}

















//import javafx.application.Application;
//import javafx.geometry.Insets;
//import javafx.scene.Node;
//import javafx.scene.Scene;
//import javafx.scene.control.*;
//import javafx.scene.layout.*;
//import javafx.stage.Stage;
//
//import java.util.Optional;
//
//public class Main extends Application {
//    @Override
//    public void start(Stage stage) {
//        Button show = new Button("Show Dialog");
//
//        Dialog<ButtonType> dialog = new Dialog<>();
//        DialogPane dialogPane = new DialogPane() {
//            @Override
//            protected Node createButtonBar() {
//                ButtonBar buttonBar = (ButtonBar) super.createButtonBar();
//                buttonBar.setButtonOrder(ButtonBar.BUTTON_ORDER_NONE);
//
//                return buttonBar;
//            }
//        };
//        dialog.setDialogPane(dialogPane);
//        dialogPane.getButtonTypes().addAll(ButtonType.OK);
//        dialogPane.setContentText("Centered Button");
//
//        Region spacer = new Region();
//        ButtonBar.setButtonData(spacer, ButtonBar.ButtonData.BIG_GAP);
//        HBox.setHgrow(spacer, Priority.ALWAYS);
//        dialogPane.applyCss();
//        HBox hbox = (HBox) dialogPane.lookup(".container");
//        hbox.getChildren().add(spacer);
//
//        show.setOnAction(e -> {
//            Optional<ButtonType> result = dialog.showAndWait();
//            if (result.isPresent() && result.get() == ButtonType.OK) {
//                System.out.println("OK");
//            }
//        });
//
//        StackPane layout = new StackPane(
//                show
//        );
//        layout.setPadding(new Insets(50));
//
//        Scene scene = new Scene(layout);
//
//        stage.setScene(scene);
//        stage.show();
//    }
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
























//import javafx.application.Application;
//import javafx.scene.Scene;
//import javafx.scene.control.Button;
//import javafx.scene.layout.VBox;
//import javafx.stage.FileChooser;
//import javafx.stage.Stage;
//
//import java.io.File;
//
//public class Main extends Application {
//
//
//    @Override
//    public void start(Stage primaryStage) {
//        primaryStage.setTitle("JavaFX App");
//
//        FileChooser fileChooser = new FileChooser();
//
//        Button button = new Button("Select File");
//        button.setOnAction(e -> {
//            File selectedFile = fileChooser.showOpenDialog(primaryStage);
//        });
//
//        VBox vBox = new VBox(button);
//        Scene scene = new Scene(vBox, 960, 600);
//
//        primaryStage.setScene(scene);
//        primaryStage.show();
//    }
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//}
















//import javafx.application.Application;
//import javafx.geometry.Insets;
//import javafx.scene.Scene;
//import javafx.scene.control.ContentDisplay;
//import javafx.scene.control.Label;
//import javafx.scene.layout.Background;
//import javafx.scene.layout.BackgroundFill;
//import javafx.scene.layout.Border;
//import javafx.scene.layout.BorderPane;
//import javafx.scene.layout.BorderStroke;
//import javafx.scene.layout.BorderStrokeStyle;
//import javafx.scene.layout.BorderWidths;
//import javafx.scene.layout.CornerRadii;
//import javafx.scene.layout.GridPane;
//import javafx.scene.paint.Color;
//import javafx.scene.shape.Rectangle;
//import javafx.scene.text.Font;
//import javafx.stage.Stage;
//
///**
// *
// */
//public class Main extends Application {
//
//    private static final Border black = new Border(new BorderStroke(Color.BLACK,
//            BorderStrokeStyle.SOLID, new CornerRadii(8), new BorderWidths(2)));
//    private static final Border red = new Border(new BorderStroke(Color.RED,
//            BorderStrokeStyle.SOLID, new CornerRadii(8), new BorderWidths(2)));
//    private static final Border blue = new Border(new BorderStroke(Color.BLUE,
//            BorderStrokeStyle.SOLID, new CornerRadii(8), new BorderWidths(2)));
//    private static final Color yellow = Color.YELLOW.deriveColor(0, .9, 1, 1);
//
//    @Override
//    public void start(Stage primaryStage) {
//        primaryStage.setTitle("Test");
//
//        GridPane root = new GridPane();
//        root.setPadding(new Insets(16));
//        root.setVgap(16);
//        root.setBorder(black);
//        root.setBackground(new Background(new BackgroundFill(
//                Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
//
//        BorderPane top = new BorderPane();
//        top.setPadding(new Insets(16));
//        top.setBorder(red);
//        top.setLeft(createLabel("Label 1", yellow, 16));
//        Label topCenter = createLabel("Label 2", yellow, 64);
//        topCenter.setContentDisplay(ContentDisplay.CENTER);
//        BorderPane.setMargin(topCenter, new Insets(16));
//        top.setCenter(topCenter);
//        top.setRight(createLabel("Label 3", yellow, 16));
//        root.add(top, 0, 0);
//
//        BorderPane bot = new BorderPane();
//        bot.setPadding(new Insets(16));
//        bot.setBorder(blue);
//        bot.setCenter(createLabel("Label 4", Color.GREEN, 24));
//        root.add(bot, 0, 1);
//
//        Scene scene = new Scene(root);
//        primaryStage.setScene(scene);
//        primaryStage.show();
//    }
//
//    private Label createLabel(String text, Color color, int size) {
//        Rectangle r = new Rectangle(3 * size, 2 * size);
//        r.setFill(Color.TRANSPARENT);
//        r.setStroke(color);
//        r.setStrokeWidth(3);
//        Label l = new Label(text, r);
//        l.setContentDisplay(ContentDisplay.TOP);
//        l.setTextFill(color);
//        l.setFont(new Font(16));
//        return l;
//    }
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//}






















import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.Optional;

/**
 * ZetCode JavaFX tutorial
 *
 * This program positions three shapes
 * using absolute coordinates.
 *
 * Author: Jan Bodnar
 * Website: zetcode.com
 * Last modified: June 2015
 */

public class Main extends Application {

    @Override
    public void start(Stage stage) {

        initUI(stage);
    }

    private void initUI(Stage stage) {
        double cashAmount;
        double checkAmount;


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

        ButtonType okButton = new ButtonType("OK", ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(okButton, ButtonType.CANCEL);


        dialog.showAndWait();

        Optional<ButtonType> result = dialog.showAndWait();
        if(result.get() == ButtonType.OK)
        {
            if (cashField.getText().equals("")) {
                cashAmount = 0.0;
            }
            else {
                cashAmount = Double.parseDouble(cashField.getText());
            }
            if (checkField.getText().equals("")) {
                checkAmount = 0.0;
            }
            else {
                checkAmount = Double.parseDouble(checkField.getText());
            }
        }




//
//        Pane root = new Pane();
//
//        Rectangle rect = new Rectangle(25, 25, 50, 50);
//        rect.setFill(Color.CADETBLUE);
//
//        Line line = new Line(90, 40, 230, 40);
//        line.setStroke(Color.BLACK);
//
//        Circle circle = new Circle(130, 130, 30);
//        circle.setFill(Color.CHOCOLATE);
//        root.getChildren().addAll(rect, circle, line);

//        GridPane depositPanel = new GridPane();
//        depositPanel.setVgap(15);
//        depositPanel.add(new Label("Cash"), 1, 1);
//        depositPanel.add(cashField, 1, 2);
//        depositPanel.add(new Label("Checks"), 1, 3);
//        depositPanel.add(checkField, 1, 4);

//        Label labelCash = new Label("Cash");
//        Label labelChecks = new Label("Checks");
//        depositPanel.getChildren().addAll(cashField, labelCash, checkField, labelChecks);




//        Scene scene = new Scene(dialog, 250, 220);
//
//        stage.setTitle("Deposit Window");
//        stage.setScene(scene);
//        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}