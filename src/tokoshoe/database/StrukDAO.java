package tokoshoe.database;

import tokoshoe.model.Struk;
import tokoshoe.model.Sepatu;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StrukDAO {
    private Connection conn;

    public StrukDAO() {
        conn = DbConnection.connect();
    }

    public void addStruk(Struk struk) {
        if (struk == null || struk.getSepatu() == null) {
            System.out.println("Struk atau Sepatu tidak boleh null");
            return;
        }

        try {
            String sql = "INSERT INTO struk (kode, model, merk, warna, harga, kuantitas, uang_dibayar, kembalian) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, struk.getSepatu().getKodeSepatu());  // Gunakan setInt untuk kode sepatu
            ps.setString(2, struk.getSepatu().getModel());
            ps.setString(3, struk.getSepatu().getMerk());
            ps.setString(4, struk.getSepatu().getWarna());
            ps.setDouble(5, struk.getSepatu().getHarga());
            ps.setInt(6, struk.getKuantitas());
            ps.setDouble(7, struk.getUangDibayar());
            ps.setDouble(8, struk.getKembalian());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<Struk> getAllStruk() {
        List<Struk> strukList = new ArrayList<>();
        try {
            String sql = "SELECT * FROM struk";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Sepatu sepatu = new Sepatu(
                	rs.getString("kode"),
                    rs.getString("model"),
                    rs.getString("merk"),
                    rs.getString("warna"),
                    rs.getDouble("harga")
                );
                Struk struk = new Struk(
                    rs.getInt("kode_struk"),
                    sepatu,
                    rs.getInt("kuantitas"),
                    rs.getDouble("uang_dibayar")
                );
                strukList.add(struk);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return strukList;
    }
}
