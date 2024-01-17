package View;

import Controller.Controller;
import Exceptions.MyException;
import Model.Statements.IStmt;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class GUI extends Application {

    @Override
    public void start(Stage primaryStage) throws MyException {
        // Create a listView with all the controllers descriptions as buttons
        PrepareControllers prepareControllers = new PrepareControllers();
        List<IStmt> programStmts = prepareControllers.getProgramStatements();

        // Create a button for each controller
        List<Button> buttons = new ArrayList<>(programStmts.size());
        int i = 1;
        for (IStmt stmt : programStmts) {
            buttons.add(new Button(i + ". " + stmt.toString()));
            i += 1;
        }

        // Create a layout and add the button to it
        StackPane root = new StackPane();
        // add the buttons with padding
        VBox vBox = new VBox(10);
        vBox.setPadding(new Insets(10, 10, 10, 10));
        vBox.setAlignment(Pos.CENTER);
        vBox.getChildren().addAll(buttons);
        root.getChildren().add(vBox);


        // Create a Scene with the layout
        Scene scene = new Scene(root, 300, 250);

        // Set the scene to the stage and show the stage
        primaryStage.setTitle("JavaFX Test");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
