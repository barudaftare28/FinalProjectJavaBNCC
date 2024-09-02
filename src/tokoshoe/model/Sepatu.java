package tokoshoe.model;

import javafx.beans.property.*;

public class Sepatu {
    private StringProperty kodeSepatu;
    private StringProperty model;
    private StringProperty merk;
    private StringProperty warna;
    private DoubleProperty harga;

    public Sepatu(String kodeSepatu, String model, String merk, String warna, double harga) {
        this.kodeSepatu = new SimpleStringProperty(kodeSepatu);
        this.model = new SimpleStringProperty(model);
        this.merk = new SimpleStringProperty(merk);
        this.warna = new SimpleStringProperty(warna);
        this.harga = new SimpleDoubleProperty(harga);
    }

    // Getters and setters with property methods
    public String getKodeSepatu() {
        return kodeSepatu.get();
    }

    public void setKodeSepatu(String kodeSepatu) {
        this.kodeSepatu.set(kodeSepatu);
    }

    public StringProperty kodeSepatuProperty() {
        return kodeSepatu;
    }

    public String getModel() {
        return model.get();
    }

    public void setModel(String model) {
        this.model.set(model);
    }

    public StringProperty modelProperty() {
        return model;
    }

    public String getMerk() {
        return merk.get();
    }

    public void setMerk(String merk) {
        this.merk.set(merk);
    }

    public StringProperty merkProperty() {
        return merk;
    }

    public String getWarna() {
        return warna.get();
    }

    public void setWarna(String warna) {
        this.warna.set(warna);
    }

    public StringProperty warnaProperty() {
        return warna;
    }

    public double getHarga() {
        return harga.get();
    }

    public void setHarga(double harga) {
        this.harga.set(harga);
    }

    public DoubleProperty hargaProperty() {
        return harga;
    }
}
