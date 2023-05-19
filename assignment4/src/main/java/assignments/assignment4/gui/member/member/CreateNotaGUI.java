package assignments.assignment4.gui.member.member;

import assignments.assignment3.nota.Nota;
import assignments.assignment3.nota.NotaManager;
import assignments.assignment3.nota.service.AntarService;
import assignments.assignment3.nota.service.CuciService;
import assignments.assignment3.nota.service.SetrikaService;
import assignments.assignment3.user.Member;
import assignments.assignment4.MainFrame;
import assignments.assignment4.gui.RegisterGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CreateNotaGUI extends JPanel {
    public static final String KEY = "CREATE_NOTA";
    private JPanel mainPanel;
    private JLabel paketLabel;
    private JComboBox<String> paketComboBox;
    private JButton showPaketButton;
    private JLabel beratLabel;
    private JTextField beratTextField;
    private JCheckBox setrikaCheckBox;
    private JCheckBox antarCheckBox;
    private JButton createNotaButton;
    private JButton backButton;
    private final SimpleDateFormat fmt;
    private final Calendar cal;
    private final MemberSystemGUI memberSystemGUI;

    public CreateNotaGUI(MemberSystemGUI memberSystemGUI) {
        super(new BorderLayout()); // Menginisiasi frame
        this.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));

        this.memberSystemGUI = memberSystemGUI;
        this.fmt = NotaManager.fmt;
        this.cal = NotaManager.cal;

        // Panel utama
        mainPanel = new JPanel(new GridLayout(3, 1));

        // Inisiasi GUI
        initGUI();

        // Menambahkan panel utama ke frame
        add(mainPanel, BorderLayout.CENTER);
    }

    /**
     * Method untuk menginisialisasi GUI.
     * Selama funsionalitas sesuai dengan soal, tidak apa apa tidak 100% sama.
     * Be creative and have fun!
     * */
    private void initGUI() {
        // Panel-panel bantuan
        JPanel inputPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        JPanel checkboxPanel = new JPanel(new GridLayout(2, 1,0,  0));
        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 20, 10));

        // Label untuk paket
        paketLabel = new JLabel("Paket Laundry:");

        // Combobox untuk pilihan paket
        String[] items = {"Express", "Fast", "Reguler"};
        paketComboBox = new JComboBox<>(items);
        paketComboBox.setSelectedIndex(0);

        // Button untuk menunjukkan paket yang ada
        showPaketButton = new JButton("Show Paket");
        showPaketButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                showPaket();
            }
        });

        // Label dan textfield untuk berat cucian
        beratLabel = new JLabel("Berat Cucian (Kg):");
        beratTextField = new JTextField();

        // Checkbox untuk services
        setrikaCheckBox = new JCheckBox("Tambah Setrika Service (1000/kg)");
        antarCheckBox = new JCheckBox("Tambah Antar Service (2000/kg pertama, kemudian 500/kg)");

        // Button untuk membuat nota
        createNotaButton = new JButton("Buat Nota");
        createNotaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                if(RegisterGUI.validasiAngka(beratTextField.getText()) == false || Integer.parseInt(beratTextField.getText()) <= 0){ // Jika berat nota kosong atau bukan bilangan bulat
                    JOptionPane.showMessageDialog(null, "Berat Cucian harus berisi angka", "Berat Invalid", JOptionPane.ERROR_MESSAGE);
                    beratTextField.setText(""); // Mengosongkan field
                }
                else{
                    createNota();
                }
            }
        });

        // Button untuk kembali ke window member
        backButton = new JButton("Kembali");
        backButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                beratTextField.setText("");setrikaCheckBox.setSelected(false);antarCheckBox.setSelected(false);paketComboBox.setSelectedIndex(0);
                handleBack();
            }
        });

        // Menambahkan items ke panel bantuan
        inputPanel.add(paketLabel);
        inputPanel.add(new JLabel());
        inputPanel.add(paketComboBox);
        inputPanel.add(showPaketButton);
        inputPanel.add(beratLabel);
        inputPanel.add(new JLabel());
        inputPanel.add(beratTextField);
        inputPanel.add(new JLabel());
        checkboxPanel.add(setrikaCheckBox);
        checkboxPanel.add(antarCheckBox);
        buttonPanel.add(createNotaButton);
        buttonPanel.add(backButton);

        // Menambahkan panel bantuan ke panel utama
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(checkboxPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Menampilkan list paket pada user.
     * Akan dipanggil jika pengguna menekan "showPaketButton"
     * */
    private void showPaket() {
        String paketInfo = """
                        <html><pre>
                        +-------------Paket-------------+
                        | Express | 1 Hari | 12000 / Kg |
                        | Fast    | 2 Hari | 10000 / Kg |
                        | Reguler | 3 Hari |  7000 / Kg |
                        +-------------------------------+
                        </pre></html>
                        """;

        JLabel label = new JLabel(paketInfo);
        label.setFont(new Font("monospaced", Font.PLAIN, 12));
        JOptionPane.showMessageDialog(this, label, "Paket Information", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Method untuk melakukan pengecekan input user dan mendaftarkan nota yang sudah valid pada sistem.
     * Akan dipanggil jika pengguna menekan "createNotaButton"
     * */
    private void createNota() {
        // Mengambil isi dari combobox dan textfields
        String paket = (String) paketComboBox.getSelectedItem(); 
        int berat = Integer.parseInt(beratTextField.getText());
        if(berat < 2){ // Jika berat kurang dari 2 kg
            berat = 2;
            JOptionPane.showMessageDialog(this, "Cucian kurang dari 2 kg, maka cucian akan dianggap sebagai 2 kg", "Info", JOptionPane.INFORMATION_MESSAGE);
        }

        Member member = memberSystemGUI.getLoggedInMember(); // Mengambil instance object yang sedang login
        Nota newNota = new Nota(member, berat, paket, fmt.format(cal.getTime())); // Menginisiasi object Nota

        newNota.addService(new CuciService()); // Menambahkan service cuci
        if(setrikaCheckBox.isSelected()){
            newNota.addService(new SetrikaService()); // Menambahkan service setrika
        }
        if(antarCheckBox.isSelected()){
            newNota.addService(new AntarService()); // Menambahkan service antar
        }
        member.addNota(newNota); // Menambahkan nota ke list nota member
        NotaManager.addNota(newNota); // Menambahkan nota ke NotaManager
        JOptionPane.showMessageDialog(this, "Nota berhasil dibuat!", "Success", JOptionPane.INFORMATION_MESSAGE);
        beratTextField.setText("");setrikaCheckBox.setSelected(false);antarCheckBox.setSelected(false);paketComboBox.setSelectedIndex(0); // Mengosongkan semua fields
    }

    /**
     * Method untuk kembali ke halaman home.
     * Akan dipanggil jika pengguna menekan "backButton"
     * */
    private void handleBack() {
        MainFrame mainFrame = MainFrame.getInstance();
        mainFrame.navigateTo(memberSystemGUI.getPageName()); // Kembali ke window member
    }
}
