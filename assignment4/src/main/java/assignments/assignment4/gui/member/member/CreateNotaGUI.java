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
        super(new BorderLayout()); // Setup layout, Feel free to make any changes
        this.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));
        this.memberSystemGUI = memberSystemGUI;
        this.fmt = NotaManager.fmt;
        this.cal = NotaManager.cal;

        // Set up main panel, Feel free to make any changes
        initGUI();
    }

    /**
     * Method untuk menginisialisasi GUI.
     * Selama funsionalitas sesuai dengan soal, tidak apa apa tidak 100% sama.
     * Be creative and have fun!
     * */
    private void initGUI() {
        // TODO
        JPanel mainPanel = new JPanel(new GridLayout(3, 1));
        JPanel inputPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        JPanel checkboxPanel = new JPanel(new GridLayout(2, 1,0,  0));
        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 20, 10));

        paketLabel = new JLabel("Paket Laundry:");

        String[] items = {"Express", "Fast", "Reguler"};
        paketComboBox = new JComboBox<>(items);
        paketComboBox.setSelectedIndex(0);

        showPaketButton = new JButton("Show Paket");
        showPaketButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                showPaket();
            }
        });

        beratLabel = new JLabel("Berat Cucian (Kg):");

        beratTextField = new JTextField();

        setrikaCheckBox = new JCheckBox("Tambah Setrika Service (1000/kg)");
        antarCheckBox = new JCheckBox("Tambah Antar Service (2000/kg pertama, kemudian 500/kg)");

        createNotaButton = new JButton("Buat Nota");
        createNotaButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                if(RegisterGUI.validasiAngka(beratTextField.getText()) == false || Integer.parseInt(beratTextField.getText()) <= 0){
                    JOptionPane.showMessageDialog(null, "Berat Cucian harus berisi angka", "Berat Invalid", JOptionPane.INFORMATION_MESSAGE);
                    beratTextField.setText("");
                }
                else{
                    createNota();
                }
            }
        });

        backButton = new JButton("Kembali");
        backButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                beratTextField.setText("");setrikaCheckBox.setSelected(false);antarCheckBox.setSelected(false);paketComboBox.setSelectedIndex(0);
                handleBack();
            }
        });

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

        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(checkboxPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.CENTER);
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
        // TODO
        String paket = (String) paketComboBox.getSelectedItem();
        int berat = Integer.parseInt(beratTextField.getText());
        if(berat < 2){
            berat = 2;
            JOptionPane.showMessageDialog(this, "Cucian kurang dari 2 kg, maka cucian akan dianggap sebagai 2 kg", "Info", JOptionPane.INFORMATION_MESSAGE);
        }
        Member member = memberSystemGUI.getLoggedInMember();
        Nota newNota = new Nota(member, berat, paket, fmt.format(cal.getTime()));
        newNota.addService(new CuciService());
        if(setrikaCheckBox.isSelected()){
            newNota.addService(new SetrikaService());
        }
        if(antarCheckBox.isSelected()){
            newNota.addService(new AntarService());
        }
        member.addNota(newNota);
        NotaManager.addNota(newNota);
        JOptionPane.showMessageDialog(this, "Nota berhasil dibuat!", "Success", JOptionPane.INFORMATION_MESSAGE);

        beratTextField.setText("");setrikaCheckBox.setSelected(false);antarCheckBox.setSelected(false);paketComboBox.setSelectedIndex(0);
    }

    /**
     * Method untuk kembali ke halaman home.
     * Akan dipanggil jika pengguna menekan "backButton"
     * */
    private void handleBack() {
        // TODO
        MainFrame mainFrame = MainFrame.getInstance();
        mainFrame.navigateTo(memberSystemGUI.getPageName());
    }
}
