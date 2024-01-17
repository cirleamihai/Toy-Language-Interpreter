package View;

import Controller.Controller;
import Exceptions.MyException;
import Model.ADT.Heap;
import Model.ADT.MyIHeap;
import Model.Statements.IStmt;
import Model.Value.Value;
import Repository.IRepo;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class GUI extends Application {

    @Override
    public void start(Stage primaryStage) throws MyException {
        // Create a listView with all the controllers descriptions as buttons
        PrepareControllers prepareControllers = new PrepareControllers();
        List<IStmt> programStmts = prepareControllers.getProgramStatements();
        List<Controller> controllersList = prepareControllers.getControllers();

        // Create a button for each controller
        List<Button> buttons = new ArrayList<>(programStmts.size());
        for (int index = 0; index < programStmts.size(); index++) {
            Button btn = getButton(programStmts, index, controllersList);

            buttons.add(btn); // Add the button to the list of buttons
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
        primaryStage.setTitle("JavaFX A7");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    Button getButton(List<IStmt> programStmts, int index, List<Controller> controllersList) {
        IStmt stmt = programStmts.get(index);
        Controller controller = controllersList.get(index); // Assuming each statement has a corresponding controller

        // Create a button with the statement description
        Button btn = new Button((index + 1) + ". " + stmt.toString());
        int finalIndex = index; // Index for use within the lambda must be effectively final

        // Set the action for the button
        btn.setOnAction(event -> {
            try {
                // This method will be called when the button is clicked
                openNewWindow(controller, finalIndex); // Method to open the new window, passing the controller and index
            } catch (MyException e) {
                e.printStackTrace();
                // You can also add a dialog to show the error message to the user
            }
        });
        return btn;
    }

    private void openNewWindow(Controller controller, int index) throws MyException {
        // Create a new stage (window)
        Stage stage = new Stage();
        stage.setTitle("Program " + (index + 1));

        // Setup the new scene, for example with a VBox layout
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));
        layout.setAlignment(Pos.CENTER);

        // Here you can add your components, like TextFields, TableViews, etc. to the layout
        // based on the controller or the index
        // ...

        // add a textField with the number of prgStates from repo
        IRepo repo = controller.getRepo();
        TextField prgStatesNr = new TextField("Number of program States = " + repo.getPrgList().size());
        prgStatesNr.setEditable(false);
        prgStatesNr.prefWidthProperty().bind(prgStatesNr.textProperty().length().multiply(7)); // make it adjust the length of the text
        layout.getChildren().add(prgStatesNr);

//        // Heap Table
//        MyIHeap<Integer, Value> heap = repo.getPrgList().get(0).getHeap();
//        List<Integer> memAddresses = (List<Integer>) heap.getAllKeys();
//        List<String> memValues = StreamSupport.stream(heap.getAllValues().spliterator(), false)
//                .map(Object::toString)
//                .collect(Collectors.toList());
//
//
//        TableView<HeapTableRowModel> heapTable = new TableView<>();
//        heapTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
//
//        // Define the columns
//        TableColumn<HeapTableRowModel, Integer> memAddressesCol = new TableColumn<>("Column 1");
//        memAddressesCol.setCellValueFactory(new PropertyValueFactory<>("Addresses"));
//
//        TableColumn<HeapTableRowModel, String> memValuesCol = new TableColumn<>("Column 2");
//        memValuesCol.setCellValueFactory(new PropertyValueFactory<>("Values"));
//
////      Add the columns to the table
//        heapTable.getColumns().add(memAddressesCol);
//        heapTable.getColumns().add(memValuesCol);
//
//        // Create a list to hold the data
//        ObservableList<HeapTableRowModel> data = FXCollections.observableArrayList();
//        for (int i = 0; i < memAddresses.size(); ++i){
//            data.add(new HeapTableRowModel(memAddresses.get(i), memValues.get(i)));
//        }
//        heapTable.setItems(data);
//        layout.getChildren().add(heapTable);

        // Create a scene with the layout
        Scene scene = new Scene(layout, 400, 400);

        // Set the scene to the stage and show the stage
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
