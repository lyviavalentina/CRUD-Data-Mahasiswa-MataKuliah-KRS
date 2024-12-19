import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class MainFrame extends JFrame {
    private CRUDDatabaseService crudService;
    private JTabbedPane tabbedPane;
    private JPanel mahasiswaPanel, mataKuliahPanel, krsPanel;
    private JTable mahasiswaTable, mataKuliahTable, krsTable;
    private DefaultTableModel mahasiswaModel, mataKuliahModel, krsModel;

    public MainFrame() {
        crudService = new CRUDDatabaseService();
        initComponents();
        setTitle("Sistem Akademik");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        // Mengatur Look and Feel modern
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Membuat Tabbed Pane
        tabbedPane = new JTabbedPane();
        
        // Panel Mahasiswa
        mahasiswaPanel = createMahasiswaPanel();
        tabbedPane.addTab("Mahasiswa", mahasiswaPanel);

        // Panel Mata Kuliah
        mataKuliahPanel = createMataKuliahPanel();
        tabbedPane.addTab("Mata Kuliah", mataKuliahPanel);

        // Panel KRS
        krsPanel = createKRSPanel();
        tabbedPane.addTab("KRS", krsPanel);

        // Menambahkan Tabbed Pane ke Frame
        add(tabbedPane);
    }

    private JPanel createMahasiswaPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Tabel Mahasiswa
        String[] mahasiswaColumns = {"ID", "NIM", "Nama"};
        mahasiswaModel = new DefaultTableModel(mahasiswaColumns, 0);
        mahasiswaTable = new JTable(mahasiswaModel);
        
        JScrollPane scrollPane = new JScrollPane(mahasiswaTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Panel Kontrol
        JPanel controlPanel = new JPanel();
        JButton addButton = new JButton("Tambah");
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Hapus");
        JButton refreshButton = new JButton("Refresh");

        controlPanel.add(addButton);
        controlPanel.add(editButton);
        controlPanel.add(deleteButton);
        controlPanel.add(refreshButton);

        panel.add(controlPanel, BorderLayout.SOUTH);

        // Event Listeners
        addButton.addActionListener(e -> tambahMahasiswa());
        editButton.addActionListener(e -> editMahasiswa());
        deleteButton.addActionListener(e -> hapusMahasiswa());
        refreshButton.addActionListener(e -> loadMahasiswaData());

        loadMahasiswaData();
        return panel;
    }

    private JPanel createMataKuliahPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Tabel Mata Kuliah
        String[] mataKuliahColumns = {"ID", "Nama Mata Kuliah"};
        mataKuliahModel = new DefaultTableModel(mataKuliahColumns, 0);
        mataKuliahTable = new JTable(mataKuliahModel);
        
        JScrollPane scrollPane = new JScrollPane(mataKuliahTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Panel Kontrol
        JPanel controlPanel = new JPanel();
        JButton addButton = new JButton("Tambah");
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Hapus");
        JButton refreshButton = new JButton("Refresh");

        controlPanel.add(addButton);
        controlPanel.add(editButton);
        controlPanel.add(deleteButton);
        controlPanel.add(refreshButton);

        panel.add(controlPanel, BorderLayout.SOUTH);

        // Event Listeners
        addButton.addActionListener(e -> tambahMataKuliah());
        editButton.addActionListener(e -> editMataKuliah());
        deleteButton.addActionListener(e -> hapusMataKuliah());
        refreshButton.addActionListener(e -> loadMataKuliahData());

        loadMataKuliahData();
        return panel;
    }

    private JPanel createKRSPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Tabel KRS
        String[] krsColumns = {"ID KRS", "ID Mahasiswa", "ID Mata Kuliah", "Semester", "Tahun Ajaran"};
        krsModel = new DefaultTableModel(krsColumns, 0);
        krsTable = new JTable(krsModel);
        
        JScrollPane scrollPane = new JScrollPane(krsTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Panel Kontrol
        JPanel controlPanel = new JPanel();
        JButton addButton = new JButton("Tambah");
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Hapus");
        JButton refreshButton = new JButton("Refresh");

        controlPanel.add(addButton);
        controlPanel.add(editButton);
        controlPanel.add(deleteButton);
        controlPanel.add(refreshButton);

        panel.add(controlPanel, BorderLayout.SOUTH);

        // Event Listeners
        addButton.addActionListener(e -> tambahKRS());
        editButton.addActionListener(e -> editKRS());
        deleteButton.addActionListener(e -> hapusKRS());
        refreshButton.addActionListener(e -> loadKRSData());

        loadKRSData();
        return panel;
    }

    // Metode CRUD untuk Mahasiswa
    private void loadMahasiswaData() {
        mahasiswaModel.setRowCount(0);
        List<Mahasiswa> mahasiswaList = crudService.readMahasiswa();
        for (Mahasiswa mhs : mahasiswaList) {
            mahasiswaModel.addRow(new Object[]{
                mhs.getIdmhs(), 
                mhs.getNim(), 
                mhs.getNama()
            });
        }
    }

    private void tambahMahasiswa() {
        JTextField idField = new JTextField();
        JTextField nimField = new JTextField();
        JTextField namaField = new JTextField();

        JPanel inputPanel = new JPanel(new GridLayout(3, 2));
        inputPanel.add(new JLabel("ID Mahasiswa:"));
        inputPanel.add(idField);
        inputPanel.add(new JLabel("NIM:"));
        inputPanel.add(nimField);
        inputPanel.add(new JLabel("Nama:"));
        inputPanel.add(namaField);

        int result = JOptionPane.showConfirmDialog(null, inputPanel, 
                "Tambah Mahasiswa", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                int id = Integer.parseInt(idField.getText());
                int nim = Integer.parseInt(nimField.getText());
                String nama = namaField.getText();
                
                crudService.createMahasiswa(id, nim, nama);
                loadMahasiswaData();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Input tidak valid!");
            }
        }
    }

    private void editMahasiswa() {
        int selectedRow = mahasiswaTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Pilih mahasiswa yang akan diedit");
            return;
        }

        int id = (int) mahasiswaModel.getValueAt(selectedRow, 0);
        int nim = (int) mahasiswaModel.getValueAt(selectedRow, 1);
        String nama = (String) mahasiswaModel.getValueAt(selectedRow, 2);

        JTextField nimField = new JTextField(String.valueOf(nim));
        JTextField namaField = new JTextField(nama);

        JPanel inputPanel = new JPanel(new GridLayout(2, 2));
        inputPanel.add(new JLabel("NIM:"));
        inputPanel.add(nimField);
        inputPanel.add(new JLabel("Nama:"));
        inputPanel.add(namaField);

        int result = JOptionPane.showConfirmDialog(null, inputPanel, 
                "Edit Mahasiswa", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                int newNim = Integer.parseInt(nimField.getText());
                String newNama = namaField.getText();
                
                crudService.updateMahasiswa(id, newNim, newNama);
                loadMahasiswaData();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Input tidak valid!");
            }
        }
    }

    private void hapusMahasiswa() {
        int selectedRow = mahasiswaTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Pilih mahasiswa yang akan dihapus");
            return;
        }

        int id = (int) mahasiswaModel.getValueAt(selectedRow, 0);
        int konfirmasi = JOptionPane.showConfirmDialog(null, 
            "Anda yakin ingin menghapus mahasiswa ini?", 
            "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
        
        if (konfirmasi == JOptionPane.YES_OPTION) {
            crudService.deleteMahasiswa(id);
            loadMahasiswaData();
        }
    }

    // Metode CRUD untuk Mata Kuliah
    private void loadMataKuliahData() {
        mataKuliahModel.setRowCount(0);
        List<Mata_Kuliah> mataKuliahList = crudService.readMata_Kuliah();
        for (Mata_Kuliah mk : mataKuliahList) {
            mataKuliahModel.addRow(new Object[]{
                mk.getIdmk(), 
                mk.getNama()
            });
        }
    }

    private void tambahMataKuliah() {
        JTextField idField = new JTextField();
        JTextField namaField = new JTextField();

        JPanel inputPanel = new JPanel(new GridLayout(2, 2));
        inputPanel.add(new JLabel("ID Mata Kuliah:"));
        inputPanel.add(idField);
        inputPanel.add(new JLabel("Nama Mata Kuliah:"));
        inputPanel.add(namaField);

        int result = JOptionPane.showConfirmDialog(null, inputPanel, 
                "Tambah Mata Kuliah", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                int id = Integer.parseInt(idField.getText());
                String nama = namaField.getText();
                
                crudService.createMata_Kuliah(id, nama);
                loadMataKuliahData();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Input tidak valid!");
            }
        }
    }

    private void editMataKuliah() {
        int selectedRow = mataKuliahTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Pilih mata kuliah yang akan diedit");
            return;
        }

        int id = (int) mataKuliahModel.getValueAt(selectedRow, 0);
        String nama = (String) mataKuliahModel.getValueAt(selectedRow, 1);

        JTextField namaField = new JTextField(nama);

        JPanel inputPanel = new JPanel(new GridLayout(1, 2));
        inputPanel.add(new JLabel("Nama Mata Kuliah:"));
        inputPanel.add(namaField);

        int result = JOptionPane.showConfirmDialog(null, inputPanel, 
                "Edit Mata Kuliah", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String newNama = namaField.getText();
            
            crudService.updateMata_Kuliah(id, newNama);
            loadMataKuliahData();
        }
    }

    private void hapusMataKuliah() {
        int selectedRow = mataKuliahTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Pilih mata kuliah yang akan dihapus");
            return;
        }

        int id = (int) mataKuliahModel.getValueAt(selectedRow, 0);
        int konfirmasi = JOptionPane.showConfirmDialog(null, 
            "Anda yakin ingin menghapus mata kuliah ini?", 
            "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
        
        if (konfirmasi == JOptionPane.YES_OPTION) {
            crudService.deleteMata_Kuliah(id);
            loadMataKuliahData();
        }
    }

    // Metode CRUD untuk KRS
    private void loadKRSData() {
        krsModel.setRowCount(0);
        List<KRS> krsList = crudService.readKRS();
        for (KRS krs : krsList) {
            krsModel.addRow(new Object[]{
                krs.getIdkrs(), 
                krs.getIdmhs(),
                krs.getIdmk(),
                krs.getSemester(),
                krs.getTahunajaran()
            });
        }
    }

    private void tambahKRS() {
        JTextField idKRSField = new JTextField();
        JTextField idMhsField = new JTextField();
        JTextField idMkField = new JTextField();
        JTextField semesterField = new JTextField();
        JTextField tahunAjaranField = new JTextField();

        JPanel inputPanel = new JPanel(new GridLayout(5, 2));
        inputPanel.add(new JLabel("ID KRS:"));
        inputPanel.add(idKRSField);
        inputPanel.add(new JLabel("ID Mahasiswa:"));
        inputPanel.add(idMhsField);
        inputPanel.add(new JLabel("ID Mata Kuliah:"));
        inputPanel.add(idMkField);
        inputPanel.add(new JLabel("Semester:"));
        inputPanel.add(semesterField);
        inputPanel.add(new JLabel("Tahun Ajaran:"));
        inputPanel.add(tahunAjaranField);

        int result = JOptionPane.showConfirmDialog(null, inputPanel, 
                "Tambah KRS", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                int idKRS = Integer.parseInt(idKRSField.getText());
                int idMhs = Integer.parseInt(idMhsField.getText());
                int idMk = Integer.parseInt(idMkField.getText());
                int semester = Integer.parseInt(semesterField.getText());
                int tahunAjaran = Integer.parseInt(tahunAjaranField.getText());
                
                crudService.createKRS(idKRS, idMhs, idMk, semester, tahunAjaran);
                loadKRSData();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Input tidak valid!");
            }
        }
    }

    private void editKRS() {
        int selectedRow = krsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Pilih KRS yang akan diedit");
            return;
        }

        int idKRS = (int) krsModel.getValueAt(selectedRow, 0);
        int idMhs = (int) krsModel.getValueAt(selectedRow, 1);
        int idMk = (int) krsModel.getValueAt(selectedRow, 2);
        int semester = (int) krsModel.getValueAt(selectedRow, 3);
        int tahunAjaran = (int) krsModel.getValueAt(selectedRow, 4);

        JTextField idMhsField = new JTextField(String.valueOf(idMhs));
        JTextField idMkField = new JTextField(String.valueOf(idMk));
        JTextField semesterField = new JTextField(String.valueOf(semester));
        JTextField tahunAjaranField = new JTextField(String.valueOf(tahunAjaran));

        JPanel inputPanel = new JPanel(new GridLayout(4, 2));
        inputPanel.add(new JLabel("ID Mahasiswa:"));
        inputPanel.add(idMhsField);
        inputPanel.add(new JLabel("ID Mata Kuliah:"));
        inputPanel.add(idMkField);
        inputPanel.add(new JLabel("Semester:"));
        inputPanel.add(semesterField);
        inputPanel.add(new JLabel("Tahun Ajaran:"));
        inputPanel.add(tahunAjaranField);

        int result = JOptionPane.showConfirmDialog(null, inputPanel, 
                "Edit KRS", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                int newIdMhs = Integer.parseInt(idMhsField.getText());
                int newIdMk = Integer.parseInt(idMkField.getText());
                int newSemester = Integer.parseInt(semesterField.getText());
                int newTahunAjaran = Integer.parseInt(tahunAjaranField.getText());
                
                crudService.updateKRS(idKRS, newIdMhs, newIdMk, newSemester, newTahunAjaran);
                loadKRSData();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Input tidak valid!");
            }
        }
    }

    private void hapusKRS() {
        int selectedRow = krsTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(null, "Pilih KRS yang akan dihapus");
            return;
        }

        int idKRS = (int) krsModel.getValueAt(selectedRow, 0);
        int konfirmasi = JOptionPane.showConfirmDialog(null, 
            "Anda yakin ingin menghapus KRS ini?", 
            "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
        
        if (konfirmasi == JOptionPane.YES_OPTION) {
            crudService.deleteKRS(idKRS);
            loadKRSData();
        }
    }

    // Metode main untuk menjalankan aplikasi
    public static void main(String[] args) {
        // Menggunakan Event Dispatch Thread untuk keamanan thread
        SwingUtilities.invokeLater(() -> {
            try {
                // Mengatur Look and Feel modern Nimbus
                for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                    if ("Nimbus".equals(info.getName())) {
                        UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
            } catch (Exception e) {
                // Jika Nimbus tidak tersedia, gunakan look and feel default
                e.printStackTrace();
            }

            // Membuat dan menampilkan frame utama
            MainFrame mainFrame = new MainFrame();
            mainFrame.setVisible(true);
        });
    }
}