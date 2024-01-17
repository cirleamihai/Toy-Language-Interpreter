package View;

import Controller.Controller;
import Exceptions.MyException;
import Exceptions.ProgramEndedExc;
import Model.ADT.Heap;
import Model.ADT.MyIHeap;
import Model.PrgState;
import Model.Statements.IStmt;
import Model.Value.Value;
import Repository.IRepo;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class GUI extends Application {

    TextField prgStatesNr;
    TableView<HeapTableRowModel> heapTable;
    ListView<String> outListView;
    ListView<String> fileTableListView;
    ListView<String> programsView;
    TableView<SymTableRowModel> symTable;
    ListView<String> exeStackView;
    PrgState currentPrgState;

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
        scene.setOnMouseClicked(event -> {
            // Check if the click target is not part of the ListView
            if (!(event.getTarget() instanceof ListView)) {
                programsView.getSelectionModel().clearSelection();
            }
        });

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

        // getting the repo from the controller
        IRepo repo = controller.getRepo();
        currentPrgState = repo.getPrgList().get(0);

        // create the skeleton for the GUI
        createSkeleton(layout, controller, repo);

        // Create a scene with the layout
        Scene scene = new Scene(layout, 400, 400);

        // Set the scene to the stage and show the stage
        stage.setScene(scene);
        stage.show();
    }

    void createSkeleton(VBox layout, Controller controller, IRepo repo) {
        // add a textField with the number of prgStates from repo
        createNrProgramStates(layout, repo);

        // add the heap table
        createHeapTable(layout);

        // add the outList
        createOutList(layout);

        // add the fileTable
        createFileTable(layout);

        // add the prgStatesIds + symTable + exeStack
        createPrgStateIds(layout, repo);

        // add the runOneStep button
        addRunOneStepButton(layout, controller, repo);
    }

    void updateSkeleton(IRepo repo) {
        updateNrProgramStates(repo);
        updateHeapTable(repo);
        updateOutList(repo);
        updateFileTable(repo);
        updatePrgStateIds(repo);
        updateSymTable();
        updateExeStack();
    }

    private void createNrProgramStates(VBox layout, IRepo repo) {
        prgStatesNr = new TextField("Number of program States = " + repo.getPrgList().size());
        prgStatesNr.setEditable(false);
        prgStatesNr.prefWidthProperty().bind(prgStatesNr.textProperty().length().multiply(7)); // make it adjust the length of the text
        layout.getChildren().add(prgStatesNr);
    }

    private void updateNrProgramStates(IRepo repo) {
        prgStatesNr.setText("Number of program States = " + repo.getPrgList().size());
    }

    private void createHeapTable(VBox layout) {
        heapTable = new TableView<>();
        heapTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Define the columns
        TableColumn<HeapTableRowModel, Integer> memAddressesCol = new TableColumn<>("Addresses");
        memAddressesCol.setCellValueFactory(new PropertyValueFactory<>("addresses"));

        TableColumn<HeapTableRowModel, String> memValuesCol = new TableColumn<>("Values");
        memValuesCol.setCellValueFactory(new PropertyValueFactory<>("values"));

        // Add the columns to the table
        heapTable.getColumns().add(memAddressesCol);
        heapTable.getColumns().add(memValuesCol);

        // Create a list to hold the data
        ObservableList<HeapTableRowModel> data = FXCollections.observableArrayList();

        heapTable.setItems(data);
        Label title = new Label("Heap Table");
        layout.getChildren().addAll(title, heapTable);
    }

    private void updateHeapTable(IRepo repo) {
        // Heap Table
        MyIHeap<Integer, Value> heap = repo.getPrgList().get(0).getHeap();
        //noinspection SimplifyStreamApiCallChains
        List<Integer> memAddresses = StreamSupport.stream(heap.getAllKeys().spliterator(), false)
                .collect(Collectors.toList());

        //noinspection SimplifyStreamApiCallChains
        List<String> memValues = StreamSupport.stream(heap.getAllValues().spliterator(), false)
                .map(Object::toString)
                .collect(Collectors.toList());

        heapTable.getItems().clear();
        ObservableList<HeapTableRowModel> data = FXCollections.observableArrayList();
        for (int i = 0; i < memAddresses.size(); ++i) {
            data.add(new HeapTableRowModel(memAddresses.get(i), memValues.get(i)));
        }
        heapTable.setItems(data);
    }

    void createOutList(VBox layout) {
        /* Adding the OutList to the layout */
        ObservableList<String> data = FXCollections.observableArrayList();

        outListView = new ListView<>(data);

        // Create a Label for the title
        Label title = new Label("OUT List");
        layout.getChildren().addAll(title, outListView);
    }

    void updateOutList(IRepo repo) {
        List<String> outList = repo.getPrgList().get(0).getOut().getContent().stream()
                .map(Object::toString)
                .collect(Collectors.toList());

        outListView.getItems().clear();
        ObservableList<String> data = FXCollections.observableArrayList();
        data.addAll(outList);
        outListView.setItems(data);
    }

    void createFileTable(VBox layout) {
        /* Adding the FileTable to the layout */
        fileTableListView = new ListView<>(FXCollections.observableArrayList());

        // Create a Label for the title
        Label title = new Label("File Table");
        layout.getChildren().addAll(title, fileTableListView);
    }

    void updateFileTable(IRepo repo) {
        List<String> fileTable = repo.getPrgList().get(0).getFileTable().getContent().keySet().stream()
                .map(Object::toString)
                .collect(Collectors.toList());

        fileTableListView.getItems().clear();
        ObservableList<String> data = FXCollections.observableArrayList();
        data.addAll(fileTable);
        fileTableListView.setItems(data);
    }

    TableView<SymTableRowModel> createSymTableView() {
        TableView<SymTableRowModel> symTable = new TableView<>();
        symTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Define the columns
        TableColumn<SymTableRowModel, String> varNameCol = new TableColumn<>("Variable Name");
        varNameCol.setCellValueFactory(new PropertyValueFactory<>("variableName"));

        TableColumn<SymTableRowModel, String> valueCol = new TableColumn<>("Value");
        valueCol.setCellValueFactory(new PropertyValueFactory<>("value"));

        // Add the columns to the table
        symTable.getColumns().add(varNameCol);
        symTable.getColumns().add(valueCol);

        return symTable;
    }

    void updateSymTable() {
        symTable.getItems().clear();
        // Create a list to hold the data
        ObservableList<SymTableRowModel> data = FXCollections.observableArrayList();
        for (String key : currentPrgState.getSymTable().getAllKeys()) {
            data.add(new SymTableRowModel(key, currentPrgState.getSymTable().get(key).toString()));
        }
        symTable.setItems(data);
    }

    void updateExeStack() {
        exeStackView.getItems().clear();

        ObservableList<String> exeItems = FXCollections.observableArrayList();
        exeItems.addAll(currentPrgState.getStk().toStrList());

        exeStackView.setItems(exeItems);
    }

    void createPrgStateIds(VBox layout, IRepo repo) {
        /* Adding the PrgStatesIds to the layout */

        // PrgStatesIds
        List<PrgState> prgStates = repo.getPrgList();
        List<String> prgStatesIds = prgStates.stream()
                .map(PrgState::getStrID)
                .collect(Collectors.toList());

        // Convert the List to an ObservableList
        ObservableList<String> items = FXCollections.observableArrayList(prgStatesIds);
        ObservableList<String> exeItems = FXCollections.observableArrayList();

        // Create the program states list view
        programsView = new ListView<>(items);

        // Create the execution stack list view
        exeStackView = new ListView<>(exeItems);

        // Getting the SymTableView
        symTable = createSymTableView();

        programsView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue == null) {
                        return;
                    }

                    // Get the selected PrgState
                    currentPrgState = repo.getPrgById(newValue);

                    // Populate the SymTable
                    updateSymTable();

                    // Populate the execution stack
                    updateExeStack();
                });

        // Create a Label for the title
        Label title = new Label("Programs List");
        Label tableTitle = new Label("Symbol Table");
        Label exeTitle = new Label("Exe Stack");
        layout.getChildren().addAll(title, programsView, tableTitle, symTable, exeTitle, exeStackView);
    }

    void updatePrgStateIds(IRepo repo) {
        // PrgStatesIds
        List<PrgState> prgStates = repo.getPrgList();
        List<String> prgStatesIds = prgStates.stream()
                .map(PrgState::getStrID)
                .collect(Collectors.toList());

        programsView.getItems().clear();
        programsView.getItems().addAll(prgStatesIds);
    }

    void addRunOneStepButton(VBox layout, Controller controller, IRepo repo) {
        ExecutorService executor = Executors.newFixedThreadPool(2);
        repo.loadOgPrgList(); // we keep a copy of the original program

        Button runOneStepButton = new Button("Run One Step");
        runOneStepButton.setOnAction(event -> {
            try {
                controller.oneStepGUI(executor, repo);
                updateSkeleton(repo);
            } catch (RuntimeException e) {
                // Make a popup to show the message. Like some sort or alert
                e.printStackTrace();
            } catch (ProgramEndedExc e) {
                repo.setOgPrgList(); // We Reset The Program
                // Make a popup to show the message. Like some sort or alert
                e.printStackTrace();
            }
        });
        layout.getChildren().add(runOneStepButton);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
