package tokoshoe.model;

public class Struk {
    private int kodeStruk;
    private Sepatu sepatu;
    private int kuantitas;
    private double totalHarga;
    private double uangDibayar;
    private double kembalian;

    public Struk(int kodeStruk, Sepatu sepatu, int kuantitas, double uangDibayar) {
        this.kodeStruk = kodeStruk;
        this.sepatu = sepatu;
        this.kuantitas = kuantitas;
        this.totalHarga = sepatu.getHarga() * kuantitas;
        this.uangDibayar = uangDibayar;
        this.kembalian = uangDibayar - totalHarga;
    }

    // Getters and setters
    public int getKodeStruk() { return kodeStruk; }
    public void setKodeStruk(int kodeStruk) { this.kodeStruk = kodeStruk; }
    public Sepatu getSepatu() { return sepatu; }
    public void setSepatu(Sepatu sepatu) { this.sepatu = sepatu; recalculateTotalHarga(); }
    public int getKuantitas() { return kuantitas; }
    public void setKuantitas(int kuantitas) { this.kuantitas = kuantitas; recalculateTotalHarga(); }
    public double getTotalHarga() { return totalHarga; }
    public double getUangDibayar() { return uangDibayar; }
    public void setUangDibayar(double uangDibayar) { this.uangDibayar = uangDibayar; this.kembalian = calculateKembalian(); }
    public double getKembalian() { return kembalian; }

    private void recalculateTotalHarga() {
        this.totalHarga = sepatu.getHarga() * kuantitas;
        this.kembalian = calculateKembalian();
    }

    private double calculateKembalian() {
        double kembalian = uangDibayar - totalHarga;
        return kembalian >= 0 ? kembalian : 0;
    }
}
