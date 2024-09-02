package tokoshoe.controller;

import tokoshoe.model.Sepatu;
import tokoshoe.model.Struk;
import tokoshoe.view.TransaksiForm;
import tokoshoe.database.StrukDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class TransaksiController {
    private TransaksiForm transaksiForm;
    private StrukDAO strukDAO;

    public TransaksiController(TransaksiForm transaksiForm) {
        this.transaksiForm = transaksiForm;
        this.strukDAO = new StrukDAO();
    }

    // Method untuk menambahkan transaksi
    public void addTransaksi() {
        Sepatu selectedSepatu = transaksiForm.getSelectedSepatu();
        if (selectedSepatu == null) {
            transaksiForm.showAlert("Error", "Pilih sepatu yang ingin dibeli.");
            return;
        }

        int quantity = transaksiForm.getQuantityInput();
        double uangDibayar = transaksiForm.getUangDibayar();
        double totalHarga = selectedSepatu.getHarga() * quantity;

        if (uangDibayar < totalHarga) {
            transaksiForm.showAlert("Error", "Uang yang dibayarkan kurang dari total harga.");
            return;
        }

        double kembalian = uangDibayar - totalHarga;

        Struk struk = new Struk(0, selectedSepatu, quantity, uangDibayar);
        strukDAO.addStruk(struk); // Simpan transaksi ke database

        // Panggil metode cetakStruk() setelah transaksi berhasil
        cetakStruk(struk);

        transaksiForm.showAlert("Sukses", "Transaksi berhasil. Kembalian: " + kembalian);
        transaksiForm.resetForm(); // Reset form setelah transaksi berhasil
        loadTransaksi(); // Refresh data transaksi
    }

    // Method untuk mencetak struk ke dalam file .txt
    public void cetakStruk(Struk struk) {
        String namaFile = "struk_" + struk.getKodeStruk() + ".txt"; // Nama file berdasarkan kode struk
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(namaFile))) {
            writer.write("========== Struk Pembelian ==========\n");
            writer.write("Kode Struk: " + struk.getKodeStruk() + "\n");
            writer.write("Model Sepatu: " + struk.getSepatu().getModel() + "\n");
            writer.write("Merk Sepatu: " + struk.getSepatu().getMerk() + "\n");
            writer.write("Warna Sepatu: " + struk.getSepatu().getWarna() + "\n");
            writer.write("Harga Sepatu: " + struk.getSepatu().getHarga() + "\n");
            writer.write("Kuantitas: " + struk.getKuantitas() + "\n");
            writer.write("Total Harga: " + struk.getTotalHarga() + "\n");
            writer.write("Uang Dibayar: " + struk.getUangDibayar() + "\n");
            writer.write("Kembalian: " + struk.getKembalian() + "\n");
            writer.write("=====================================\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method untuk memuat data transaksi
    public void loadTransaksi() {
        List<Struk> strukList = strukDAO.getAllStruk();
        transaksiForm.getTable().setItems(FXCollections.observableArrayList(strukList));
    }

	public void loadSepatu() {
		// TODO Auto-generated method stub
		
	}

	public ObservableList<Struk> getStrukData() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
