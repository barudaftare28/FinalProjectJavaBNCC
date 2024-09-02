package tokoshoe.controller;

import tokoshoe.database.SepatuDAO;
import tokoshoe.view.SepatuForm;
import tokoshoe.model.Sepatu;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

public class SepatuController {
    private SepatuForm sepatuForm;
    private SepatuDAO sepatuDAO;

    public SepatuController(SepatuForm sepatuForm) {
        this.sepatuForm = sepatuForm;
        this.sepatuDAO = new SepatuDAO();
    }

    public void saveSepatu() {
        try {
            String kode = sepatuForm.getKodeInput();
            String model = sepatuForm.getModelInput();
            String merk = sepatuForm.getMerkInput();
            String warna = sepatuForm.getWarnaInput();
            double harga = sepatuForm.getHargaInput();
            
            if (model.isEmpty() || merk.isEmpty() || warna.isEmpty()) {
                sepatuForm.showAlert("Invalid Input", "Please fill all fields.");
                return;
            }

            Sepatu sepatu = new Sepatu(kode, model, merk, warna, harga);
            sepatuDAO.addSepatu(sepatu);
            sepatuForm.getTable().getItems().add(sepatu); // Pastikan tabel segera diperbarui
        } catch (NumberFormatException e) {
            sepatuForm.showAlert("Invalid Input", "Please ensure all fields are correctly filled.");
        }
    }


    public void updateSepatu() {
        Sepatu selectedSepatu = sepatuForm.getTable().getSelectionModel().getSelectedItem();
        if (selectedSepatu != null) {
            try {
                String kode = sepatuForm.getKodeInput();
                String model = sepatuForm.getModelInput();
                String merk = sepatuForm.getMerkInput();
                String warna = sepatuForm.getWarnaInput();
                double harga = sepatuForm.getHargaInput();

                if (kode.isEmpty() || model.isEmpty() || merk.isEmpty() || warna.isEmpty()) {
                    sepatuForm.showAlert("Invalid Input", "All fields must be filled.");
                    return;
                }

                selectedSepatu.setKodeSepatu(kode);
                selectedSepatu.setModel(model);
                selectedSepatu.setMerk(merk);
                selectedSepatu.setWarna(warna);
                selectedSepatu.setHarga(harga);

                sepatuDAO.updateSepatu(selectedSepatu);
                loadSepatu();
                sepatuForm.showAlert("Success", "Sepatu has been successfully updated.");
            } catch (NumberFormatException e) {
                sepatuForm.showAlert("Invalid Input", "Kode must be an integer and Harga must be a number.");
            }
        } else {
            sepatuForm.showAlert("No Selection", "No sepatu selected. Please select a sepatu from the table.");
        }
    }

    public void deleteSepatu() {
        Sepatu selectedSepatu = sepatuForm.getTable().getSelectionModel().getSelectedItem();
        if (selectedSepatu != null) {
            sepatuForm.showConfirmation("Confirm Deletion", "Are you sure you want to delete this sepatu?", () -> {
                try {
                    sepatuDAO.deleteSepatu(selectedSepatu.getKodeSepatu());
                    loadSepatu();
                    sepatuForm.showAlert("Success", "Sepatu has been successfully deleted.");
                } catch (Exception e) {
                    sepatuForm.showAlert("Error", "Failed to delete sepatu: " + e.getMessage());
                }
            });
        } else {
            sepatuForm.showAlert("No Selection", "No sepatu selected. Please select a sepatu from the table.");
        }
    }

    public void loadSepatu() {
        List<Sepatu> sepatuList = sepatuDAO.getAllSepatu();
        ObservableList<Sepatu> sepatuObservableList = FXCollections.observableArrayList(sepatuList);
        sepatuForm.getTable().setItems(sepatuObservableList);
    }
}
