package assignments.assignment4.gui;

import assignments.assignment3.LoginManager;
import assignments.assignment3.user.menu.EmployeeSystem;
import assignments.assignment3.user.menu.MemberSystem;
import assignments.assignment3.user.menu.SystemCLI;
import assignments.assignment4.MainFrame;
import assignments.assignment4.gui.member.employee.EmployeeSystemGUI;
import assignments.assignment4.gui.member.member.MemberSystemGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginGUI extends JPanel {
    public static final String KEY = "LOGIN";
    private JPanel mainPanel;
    private JLabel idLabel;
    private JTextField idTextField;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JButton backButton;
    private LoginManager loginManager;
    private JPanel fieldsPanel;
    private JPanel buttonPanel;

    public LoginGUI(LoginManager loginManager) {
        super(new BorderLayout()); // Inisiasi frame
        this.loginManager = loginManager;

        // Panel utama
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel untuk textfields dan labels
        fieldsPanel = new JPanel(new GridLayout(4,1, 10, 40));
        fieldsPanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));

        // Panel untuk buttons
        buttonPanel = new JPanel(new GridLayout(2,1, 50, 30));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(30, 280, 30, 280));

        // Inisiasi GUI
        initGUI();

        // Menambahkan panel ke frame
        add(mainPanel, BorderLayout.CENTER);
    }

    /**
     * Method untuk menginisialisasi GUI.
     * Selama funsionalitas sesuai dengan soal, tidak apa apa tidak 100% sama.
     * Be creative and have fun!
     * */
    private void initGUI() {

        // Membuat labels dan fields
        idLabel = new JLabel("Masukkan ID Anda:");
        idTextField = new JTextField();
        passwordLabel = new JLabel("Masukkan password Anda:");
        passwordField = new JPasswordField();

        // Button login 
        loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                if(idTextField.getText().length() == 0 || passwordField.getPassword().length == 0){ // Jika ada fields yang kosong
                    JOptionPane.showMessageDialog(null, "Semua field diatas wajib di isi!", "Empty Field", JOptionPane.ERROR_MESSAGE);
                }
                else{
                    handleLogin();
                }
                
            }
        });

        // Button kembali
        backButton = new JButton("Kembali");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                handleBack(); 
            }
        });

        // Menambahkan items ke panel masing-masing
        fieldsPanel.add(idLabel);  
        fieldsPanel.add(idTextField);
        fieldsPanel.add(passwordLabel);
        fieldsPanel.add(passwordField);
        buttonPanel.add(loginButton);
        buttonPanel.add(backButton);

        // Menambahkan panel bantuan ke panel utama
        mainPanel.add(fieldsPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Method untuk kembali ke halaman home.
     * Akan dipanggil jika pengguna menekan "backButton"
     * */
    private void handleBack() {
        idTextField.setText("");passwordField.setText(""); // Mengosongkan textfields
        MainFrame mainFrame = MainFrame.getInstance();
        mainFrame.navigateTo(HomeGUI.KEY); // Kembali memunculkan home window
    }

    /**
     * Method untuk login pada sistem.
     * Akan dipanggil jika pengguna menekan "loginButton"
     * */
    private void handleLogin() {
        // Mengambil isi dari fields
        String id = idTextField.getText();
        String passwordString = "";
        char[] password = passwordField.getPassword();
        for(char characters: password){
            passwordString += characters;
        }

        // Login member
        SystemCLI systemCLI = loginManager.getSystem(id);
        if(systemCLI == null){ // Jika gagal login
            JOptionPane.showMessageDialog(this, String.format("ID atau password invalid!", id), "Invalid ID or Password", JOptionPane.ERROR_MESSAGE);
            idTextField.setText("");passwordField.setText(""); // Mengosongkan fields
        }
        else{
            MainFrame mainFrame = MainFrame.getInstance();
            if(mainFrame.login(id, passwordString)){ // Jika berhasil login
                if(systemCLI instanceof MemberSystem){ // Jika member, maka akan memunculkan window untuk member
                    mainFrame.navigateTo(MemberSystemGUI.KEY);
                }
                else if(systemCLI instanceof EmployeeSystem){
                    mainFrame.navigateTo(EmployeeSystemGUI.KEY); // Jika employee, maka akan memunculkan window untuk employee
                }
                idTextField.setText("");passwordField.setText(""); // Mengosongkan textfield
            }
            else{ // Jika gagal login
                JOptionPane.showMessageDialog(this, String.format("ID atau password invalid!", id), "Invalid ID or Password", JOptionPane.ERROR_MESSAGE);
                idTextField.setText("");passwordField.setText(""); // Mengosongkan textfield
            }
        }
    }
}
