package tokoshoe.database;

import tokoshoe.model.Sepatu;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SepatuDAO {
    private Connection conn;

    public SepatuDAO() {
        this.conn = DbConnection.connect();
        if (conn == null) {
            throw new IllegalStateException("Cannot establish a connection to the database.");
        }
    }

    public void addSepatu(Sepatu sepatu) {
        String sql = "INSERT INTO sepatu (kode_sepatu, model, merk, warna, harga) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, sepatu.getKodeSepatu());
            ps.setString(2, sepatu.getModel());
            ps.setString(3, sepatu.getMerk());
            ps.setString(4, sepatu.getWarna());
            ps.setDouble(5, sepatu.getHarga());
            ps.executeUpdate();
        } catch (SQLException e) {
            // Use logging framework in production instead of e.printStackTrace()
            System.err.println("Error adding sepatu: " + e.getMessage());
        }
    }

    public void updateSepatu(Sepatu sepatu) {
        String sql = "UPDATE sepatu SET model=?, merk=?, warna=?, harga=? WHERE kode_sepatu=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, sepatu.getModel());
            ps.setString(2, sepatu.getMerk());
            ps.setString(3, sepatu.getWarna());
            ps.setDouble(4, sepatu.getHarga());
            ps.setString(5, sepatu.getKodeSepatu()); // Corrected from setInt to setString
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error updating sepatu: " + e.getMessage());
        }
    }

    public void deleteSepatu(String kodeSepatu) {
        String sql = "DELETE FROM sepatu WHERE kode_sepatu=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, kodeSepatu); // Corrected from setInt to setString
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Error deleting sepatu: " + e.getMessage());
        }
    }

    public List<Sepatu> getAllSepatu() {
        List<Sepatu> sepatuList = new ArrayList<>();
        String sql = "SELECT * FROM sepatu";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Sepatu sepatu = new Sepatu(
                    rs.getString("kode_sepatu"),
                    rs.getString("model"),
                    rs.getString("merk"),
                    rs.getString("warna"),
                    rs.getDouble("harga")
                );
                sepatuList.add(sepatu);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching sepatu list: " + e.getMessage());
        }
        return sepatuList;
    }
}
