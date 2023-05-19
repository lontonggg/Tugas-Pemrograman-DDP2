package assignments.assignment4.gui;

import assignments.assignment3.LoginManager;
import assignments.assignment3.user.Member;
import assignments.assignment4.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterGUI extends JPanel {
    public static final String KEY = "REGISTER";
    private JPanel mainPanel;
    private JLabel nameLabel;
    private JTextField nameTextField;
    private JLabel phoneLabel;
    private JTextField phoneTextField;
    private JLabel passwordLabel;
    private JPasswordField passwordField;
    private JButton registerButton;
    private LoginManager loginManager;
    private JPanel fieldsPanel;
    private JPanel buttonPanel;
    private JButton backButton;

    public RegisterGUI(LoginManager loginManager) {
        super(new BorderLayout()); // Inisiasi frame
        this.loginManager = loginManager;

        // Panel utama
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel untuk textfield dan labels
        fieldsPanel = new JPanel(new GridLayout(6, 1, 10, 20));
        fieldsPanel.setBorder(BorderFactory.createEmptyBorder(15,0,0,0));

        // Panel untuk buttons
        buttonPanel = new JPanel(new GridLayout(2,1, 50, 30));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(30, 280, 15, 280));
        
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
        // Membuat labels dan textfields
        nameLabel = new JLabel("Masukkan nama Anda:");
        nameTextField = new JTextField();
        phoneLabel = new JLabel("Masukkan nomor handphone Anda:");
        phoneTextField = new JTextField();
        passwordLabel = new JLabel("Masukkan password Anda:");
        passwordField = new JPasswordField();

        // Button untuk register
        registerButton = new JButton("Register");
        registerButton.addActionListener(new ActionListener() { // Action listener untuk button register
            public void actionPerformed(ActionEvent e){
                // Jika ada textField yang kosong
                if(nameTextField.getText().length() == 0 || phoneTextField.getText().length() == 0 || passwordField.getPassword().length == 0){
                    JOptionPane.showMessageDialog(null, "Semua field diatas wajib di isi!", "Empty Field", JOptionPane.ERROR_MESSAGE);
                }
                // Jika no HP tidak valid
                else if(validasiAngka(phoneTextField.getText()) == false){
                    JOptionPane.showMessageDialog(null, "Nomor handphone harus berisi angka!", "Invalid Phone Number", JOptionPane.ERROR_MESSAGE);
                    phoneTextField.setText("");
                }
                else{
                    handleRegister();
                }
            }
        });

        // Button untuk kembali ke home window
        backButton = new JButton("Kembali");
        backButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                handleBack();
            }
        });


        // Menambahkan items ke panel masing-masing
        fieldsPanel.add(nameLabel);
        fieldsPanel.add(nameTextField);
        fieldsPanel.add(phoneLabel);
        fieldsPanel.add(phoneTextField);
        fieldsPanel.add(passwordLabel);
        fieldsPanel.add(passwordField);
        buttonPanel.add(registerButton);
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
        nameTextField.setText("");phoneTextField.setText("");passwordField.setText(""); // Mengkosongkan textfields
        MainFrame mainFrame = MainFrame.getInstance();
        mainFrame.navigateTo(HomeGUI.KEY); // Kembali memuncukan homeGUI
    }

    /**
    * Method untuk mendaftarkan member pada sistem.
    * Akan dipanggil jika pengguna menekan "registerButton"
    * */
    private void handleRegister() {
        // Mengambil isi dari textfield
        String nama = nameTextField.getText();
        String noHP = phoneTextField.getText();
        char[] password = passwordField.getPassword();
        String passwordString = "";
        for(char character: password){
            passwordString += character;
        }

        // Menginisiasi object member baru dengan menggunakan loginManager pada TP3
        Member registeredMember = loginManager.register(nama, noHP, passwordString);
        if(registeredMember == null){ // Jika user sudah pernah terdaftar
            JOptionPane.showMessageDialog(this, String.format("User dengan nama %s dan nomor hp %s sudah ada!", nama, noHP), "Registration Failed", JOptionPane.ERROR_MESSAGE);
            handleBack(); // Kembali ke home window
        }
        else{
            JOptionPane.showMessageDialog(this, String.format("Berhasil membuat user dengan ID %s!", registeredMember.getId()), "Registration Successful", JOptionPane.INFORMATION_MESSAGE);
            handleBack(); // Kembali ke homewindow
        }
    }

    // Method untuk memvalidasi angka (No HP dan berat cucian)
    public static boolean validasiAngka(String angka){
        boolean angkaValid = false;
            if(angka.length() != 0){
                for(int i = 0; i < angka.length(); i++){
                    if(Character.isDigit(angka.charAt(i))){ // Mengiterasi setiap digit di dan cek apakah digit atau bukan
                        angkaValid = true;
                    }
                    else{ 
                        angkaValid = false;
                        break; // Break dari for loop dan kembali ke awal while loop
                    }
                }
            }
        return angkaValid;
    }
    
}
