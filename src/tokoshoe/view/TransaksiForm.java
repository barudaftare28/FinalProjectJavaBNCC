package tokoshoe.view;

import tokoshoe.controller.TransaksiController;
import tokoshoe.model.Sepatu;
import tokoshoe.model.Struk;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.control.cell.PropertyValueFactory;
//import javafx.base;

public class TransaksiForm extends GridPane {
    private TableView<Sepatu> sepatuTable;
    private TableView<Struk> strukTable;
    private TextField uangDibayarInput;
    private Spinner<Integer> quantitySpinner;
    private Label totalHargaLabel;
    private Button addTransaksiButton, cetakStrukButton;
    private TransaksiController transaksiController;

    public TransaksiForm() {
        transaksiController = new TransaksiController(this);  // Inisialisasi TransaksiController dengan TransaksiForm

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
        sepatuTable = new TableView<>();
        setSepatuTable();
        this.add(initTransaksiForm(), 0, 0);
        this.add(initStrukTable(), 1, 0);
        this.add(sepatuTable, 0, 1);
        this.add(strukTable, 1, 1);

        // Load data into table
        transaksiController.loadSepatu();
        transaksiController.loadTransaksi();

        // Update total harga whenever quantity or selected sepatu changes
        quantitySpinner.valueProperty().addListener((obs, oldValue, newValue) -> updateTotalHarga());
        sepatuTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> updateTotalHarga());
    }

    // Setup the form for transaction
    private VBox initTransaksiForm() {
        VBox form = new VBox(10);

        quantitySpinner = new Spinner<>(1, 100, 1);
        uangDibayarInput = new TextField();
        totalHargaLabel = new Label("Total Harga: 0");

        addTransaksiButton = new Button("Tambah Transaksi");
        cetakStrukButton = new Button("Cetak Struk");

        addTransaksiButton.setOnAction(event -> transaksiController.addTransaksi());
        cetakStrukButton.setOnAction(event -> cetakStruk()); // Memanggil metode cetakStruk() saat tombol ditekan

        form.getChildren().addAll(
            createLabeledField("Kuantitas:", quantitySpinner),
            createLabeledField("Uang Dibayar:", uangDibayarInput),
            totalHargaLabel,
            addTransaksiButton,
            cetakStrukButton
        );

        return form;
    }

    private VBox initStrukTable() {
        strukTable = new TableView<>();
        setStrukTable();
        VBox tableBox = new VBox(strukTable);
        return tableBox;
    }

    private HBox createLabeledField(String labelText, Control inputField) {
        Label label = new Label(labelText);
        HBox field = new HBox(10);
        field.getChildren().addAll(label, inputField);
        return field;
    }

    // Setup the sepatu table for displaying available shoes
    private void setSepatuTable() {
        TableColumn<Sepatu, String> modelColumn = new TableColumn<>("Model");
        modelColumn.setCellValueFactory(new PropertyValueFactory<>("model"));

        TableColumn<Sepatu, String> merkColumn = new TableColumn<>("Merk");
        merkColumn.setCellValueFactory(new PropertyValueFactory<>("merk"));

        TableColumn<Sepatu, String> warnaColumn = new TableColumn<>("Warna");
        warnaColumn.setCellValueFactory(new PropertyValueFactory<>("warna"));

        TableColumn<Sepatu, Double> hargaColumn = new TableColumn<>("Harga");
        hargaColumn.setCellValueFactory(new PropertyValueFactory<>("harga"));

        sepatuTable.getColumns().addAll(modelColumn, merkColumn, warnaColumn, hargaColumn);
        sepatuTable.setPrefHeight(300);
        sepatuTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    // Setup the struk table for displaying transaction history
    private void setStrukTable() {
        TableColumn<Struk, Integer> kodeStrukColumn = new TableColumn<>("Kode Struk");
        kodeStrukColumn.setCellValueFactory(new PropertyValueFactory<>("kodeStruk"));

        TableColumn<Struk, String> modelColumn = new TableColumn<>("Model");
        modelColumn.setCellValueFactory(cellData -> cellData.getValue().getSepatu().modelProperty());

        TableColumn<Struk, Integer> kuantitasColumn = new TableColumn<>("Kuantitas");
        kuantitasColumn.setCellValueFactory(new PropertyValueFactory<>("kuantitas"));

        TableColumn<Struk, Double> totalHargaColumn = new TableColumn<>("Total Harga");
        totalHargaColumn.setCellValueFactory(new PropertyValueFactory<>("totalHarga"));

        strukTable.getColumns().addAll(kodeStrukColumn, modelColumn, kuantitasColumn, totalHargaColumn);
        strukTable.setPrefHeight(300);
        strukTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    public Sepatu getSelectedSepatu() {
        return sepatuTable.getSelectionModel().getSelectedItem();
    }

    public int getQuantityInput() {
        return quantitySpinner.getValue();
    }

    public double getUangDibayar() {
        try {
            return Double.parseDouble(uangDibayarInput.getText());
        } catch (NumberFormatException e) {
            showAlert("Invalid input", "Uang Dibayar must be a number.");
            return 0.0;
        }
    }

    public TableView<Struk> getTable() {
        return strukTable;
    }

    public void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void resetForm() {
        quantitySpinner.getValueFactory().setValue(1);
        uangDibayarInput.clear();
        totalHargaLabel.setText("Total Harga: 0");
    }

    // Calculate and update total harga label based on selected sepatu and quantity
    private void updateTotalHarga() {
        Sepatu selectedSepatu = sepatuTable.getSelectionModel().getSelectedItem();
        if (selectedSepatu != null) {
            int quantity = getQuantityInput();
            double totalHarga = selectedSepatu.getHarga() * quantity;
            totalHargaLabel.setText("Total Harga: " + totalHarga);
        } else {
            totalHargaLabel.setText("Total Harga: 0");
        }
    }

    // Function to handle printing struk to a .txt file and saving to database
    public void cetakStruk() {
        Sepatu selectedSepatu = getSelectedSepatu();
        if (selectedSepatu != null) {
            transaksiController.cetakStruk(new Struk(0, selectedSepatu, getQuantityInput(), getUangDibayar()));
        } else {
            showAlert("Error", "Tidak ada sepatu yang dipilih untuk mencetak struk.");
        }
    }
}
