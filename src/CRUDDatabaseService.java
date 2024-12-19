import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CRUDDatabaseService {
    // CRUD Barang
    public void createMahasiswa(int idmhs, int nim, String nama) {
        String query = "INSERT INTO mahasiswa (idmhs, nim, nama) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idmhs);
            stmt.setInt(2, nim);
            stmt.setString(3, nama);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Mahasiswa> readMahasiswa() {
        List<Mahasiswa> mahasiswaList = new ArrayList<>();
        String query = "SELECT * FROM mahasiswa";
        try (Connection conn = DatabaseConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                mahasiswaList.add(new Mahasiswa(
                        rs.getInt("idmhs"),
                        rs.getInt("nim"),
                        rs.getString("nama")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mahasiswaList;
    }

    public void updateMahasiswa(int idmhs, int nim, String nama) {
        String query = "UPDATE mahasiswa SET nim = ?, nama = ? WHERE idmhs = ?";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, nim);
            stmt.setString(2, nama);
            stmt.setInt(3, idmhs);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteMahasiswa(int idmhs) {
        String query = "DELETE FROM mahasiswa WHERE idmhs = ?";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idmhs);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // CRUD Konsumen
    public void createMata_Kuliah(int idmk, String nama) {
        String query = "INSERT INTO matakuliah (idmk, nama) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idmk);
            stmt.setString(2, nama);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Mata_Kuliah> readMata_Kuliah() {
        List<Mata_Kuliah> matakuliahList = new ArrayList<>();
        String query = "SELECT * FROM matakuliah";
        try (Connection conn = DatabaseConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                matakuliahList.add(new Mata_Kuliah(
                        rs.getInt("idmk"),
                        rs.getString("nama")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return matakuliahList;
    }

    public void updateMata_Kuliah(int idmk, String nama) {
        String query = "UPDATE matakuliah SET nama = ? WHERE idmk = ?";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nama);
            stmt.setInt(2, idmk);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteMata_Kuliah(int idmk) {
        String query = "DELETE FROM matakuliah WHERE idmk = ?";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idmk);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // CRUD KRS
    public void createKRS(int idkrs, int idmhs, int idmk, int semester, int tahunajaran) {
        String query = "INSERT INTO krs (idkrs, idmhs, idmk, semester, tahunajaran) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.connect();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idkrs);
            stmt.setInt(2, idmhs);
            stmt.setInt(3, idmk);
            stmt.setInt(4, semester);
            stmt.setInt(5, tahunajaran);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<KRS> readKRS() {
        List<KRS> krsList = new ArrayList<>();
        String query = "SELECT * FROM krs";
        try (Connection conn = DatabaseConnection.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                krsList.add(new KRS(
                        rs.getInt("idkrs"),
                        rs.getInt("idmhs"),
                        rs.getInt("idmk"),
                        rs.getInt("semester"),
                        rs.getInt("tahunajaran")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return krsList;
    }

    public void updateKRS(int idkrs, int idmhs, int idmk, int semester, int tahunajaran) {
        String query = "UPDATE krs SET idmhs = ?, idmk = ?, semester = ?, tahunajaran = ? WHERE idkrs = ?";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idmhs);
            stmt.setInt(2, idmk);
            stmt.setInt(3, semester);
            stmt.setInt(4, tahunajaran);
            stmt.setInt(5, idkrs);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteKRS(int idkrs) {
        String query = "DELETE FROM krs WHERE idkrs = ?";
        try (Connection conn = DatabaseConnection.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idkrs);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}