package tokoshoe.view;

import tokoshoe.controller.SepatuController;
import tokoshoe.model.Sepatu;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;

import java.util.Optional;

public class SepatuForm extends GridPane {
    private TableView<Sepatu> table;
    private TextField kodeInput, modelInput, merkInput, warnaInput, hargaInput;
    private Button addButton, updateButton, deleteButton;
    private SepatuController sepatuController;

    public SepatuForm() {
        sepatuController = new SepatuController(this);

        // Setup layout
        this.setPadding(new Insets(20));
        this.setHgap(20);
        this.setVgap(20);

        ColumnConstraints kolom1 = new ColumnConstraints();
        kolom1.setPercentWidth(50);
        ColumnConstraints kolom2 = new ColumnConstraints();
        kolom2.setPercentWidth(50);
        this.getColumnConstraints().addAll(kolom1, kolom2);

        // Initialize components
        table = new TableView<>();
        setTable();
        this.add(initForm(), 0, 0);
        this.add(initButtons(), 1, 0);
        this.add(table, 0, 1, 2, 1);

        // Load data into table
        sepatuController.loadSepatu();
    }

    // Setup the form for adding/editing sepatu
    private VBox initForm() {
        VBox form = new VBox(20);

        kodeInput = new TextField();
        modelInput = new TextField();
        merkInput = new TextField();
        warnaInput = new TextField();
        hargaInput = new TextField();

        form.getChildren().addAll(
        	createLabeledField("Kode:", kodeInput),
            createLabeledField("Model:", modelInput),
            createLabeledField("Merk:", merkInput),
            createLabeledField("Warna:", warnaInput),
            createLabeledField("Harga:", hargaInput)
        );

        return form;
    }

    private HBox createLabeledField(String labelText, Control inputField) {
        Label label = new Label(labelText);
        HBox field = new HBox(10);
        field.getChildren().addAll(label, inputField);
        return field;
    }

    // Setup buttons for CRUD operations
    private VBox initButtons() {
        VBox buttonsBox = new VBox(10);

        addButton = new Button("Add");
        updateButton = new Button("Update");
        deleteButton = new Button("Delete");

        addButton.setOnAction(event -> sepatuController.saveSepatu());
        updateButton.setOnAction(event -> sepatuController.updateSepatu());
        deleteButton.setOnAction(event -> sepatuController.deleteSepatu());

        buttonsBox.getChildren().addAll(addButton, updateButton, deleteButton);
        return buttonsBox;
    }


//    // Setup the table for displaying sepatu data
//    private void setTable() {
//    	
//    	TableColumn<Sepatu, String> kodeColumn = new TableColumn<>("Kode");
//    	kodeColumn.setCellValueFactory(new PropertyValueFactory<>("kode"));
//    	
//        TableColumn<Sepatu, String> modelColumn = new TableColumn<>("Model");
//        modelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));
//
//        TableColumn<Sepatu, String> merkColumn = new TableColumn<>("Merk");
//        merkColumn.setCellValueFactory(new PropertyValueFactory<>("merk"));
//
//        TableColumn<Sepatu, String> warnaColumn = new TableColumn<>("Warna");
//        warnaColumn.setCellValueFactory(new PropertyValueFactory<>("warna"));
//
//        TableColumn<Sepatu, Double> hargaColumn = new TableColumn<>("Harga");
//        hargaColumn.setCellValueFactory(new PropertyValueFactory<>("harga"));
//
//        table.getColumns().addAll(kodeColumn, modelColumn, merkColumn, warnaColumn, hargaColumn);
//        table.setPrefHeight(150);
//        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
////		String hargaa = String.valueOf(tokoshoe.getPrice()); // ubah integer dari price menu jadi string dengan valueOf()
//        table.getItems().add(new Sepatu("N001", "Air Max", "Nike", "Blue", 200000));
//
//
//        // Populate form fields when a row is selected
//        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
//            if (newSelection != null) {
//            	kodeInput.setText(String.valueOf(newSelection.getKodeSepatu()));
//                modelInput.setText(newSelection.getModel());
//                merkInput.setText(newSelection.getMerk());
//                warnaInput.setText(newSelection.getWarna());
//                hargaInput.setText(String.valueOf(newSelection.getHarga()));
//            }
//        });
//    }
    private ObservableList<Sepatu> sepatuList = FXCollections.observableArrayList();

    private void setTable() {
//    	table = new TableView<>();
		table = new TableView<Sepatu>();


        // Define the columns with proper generics
        TableColumn<Sepatu, String> kodeColumn = new TableColumn<>("Kode");
        TableColumn<Sepatu, String> modelColumn = new TableColumn<>("Model");
        TableColumn<Sepatu, String> merkColumn = new TableColumn<>("Merk");
        TableColumn<Sepatu, String> warnaColumn = new TableColumn<>("Warna");
        TableColumn<Sepatu, Double> hargaColumn = new TableColumn<>("Harga");

        // Set cell value factories to match the properties of the Sepatu class
        kodeColumn.setCellValueFactory(new PropertyValueFactory<Sepatu, String>("kodeSepatu"));
        modelColumn.setCellValueFactory(new PropertyValueFactory<Sepatu, String>("model"));
        merkColumn.setCellValueFactory(new PropertyValueFactory<Sepatu,String>("merk"));
        warnaColumn.setCellValueFactory(new PropertyValueFactory<Sepatu, String>("warna"));
        hargaColumn.setCellValueFactory(new PropertyValueFactory<Sepatu, Double>("harga"));
        

        // Add columns to the table
        table.getColumns().addAll(kodeColumn, modelColumn, merkColumn, warnaColumn, hargaColumn);
        table.setItems(sepatuList); // Set the items to the ObservableList
        table.setPrefHeight(150);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Example of adding an item to the table for testing
        table.getItems().add(new Sepatu("N001", "Air Max", "Nike", "Blue", 200000));

        // Populate form fields when a row is selected
        table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                kodeInput.setText(newSelection.getKodeSepatu());
                modelInput.setText(newSelection.getModel());
                merkInput.setText(newSelection.getMerk());
                warnaInput.setText(newSelection.getWarna());
                hargaInput.setText(String.valueOf(newSelection.getHarga()));
            }
        });
    }


    

		
//	table.setPrefHeight(150); // set tinggi dari tabel
//	table.getColumns().addAll(nameColumn, genderColumn, ageColumn, courseColumn); // tambah kolom ke tabel
//	table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS); // buat set semua column jadi sama ukuran
//		
//	// setting tipe data utk setiap kolom
//	// kolom.setCellValueFactory(new PropertyValueFactory<Class, TipeData)("nama atributnya"));
//	// jika muncul error "Can not retrieve property 'name' in PropertyValueFactory" -> ke module-info
//	// lalu pastikan ada "requires javafx.base" dan "javafx.base di opens application to..".
//	nameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("name"));
//	genderColumn.setCellValueFactory(new PropertyValueFactory<User, String>("gender"));
//	ageColumn.setCellValueFactory(new PropertyValueFactory<User, Integer>("age"));
//	courseColumn.setCellValueFactory(new PropertyValueFactory<User, String>("courses"));
//		
//	table.getItems().add(new User("Test User", "Female", 20, "Java, UI/UX"));
//		
//}

    
    public String getKodeInput() {
        return kodeInput.getText();
    }

    public String getModelInput() {
        return modelInput.getText();
    }

    public String getMerkInput() {
        return merkInput.getText();
    }

    public String getWarnaInput() {
        return warnaInput.getText();
    }

    public double getHargaInput() {
        try {
            return Double.parseDouble(hargaInput.getText());
        } catch (NumberFormatException e) {
            showAlert("Invalid input", "Harga must be a number.");
            return 0.0;
        }
    }

    public TableView<Sepatu> getTable() {
        return table; // Pastikan 'table' adalah instance dari TableView<Sepatu>
    }

    public void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void showConfirmation(String title, String content, Runnable onConfirm) {
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle(title);
        confirmAlert.setHeaderText(null);
        confirmAlert.setContentText(content);
        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            onConfirm.run();
        }
    }
}
