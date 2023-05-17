package assignments.assignment4.gui;

import assignments.assignment3.LoginManager;
import assignments.assignment3.user.Member;
import assignments.assignment4.MainFrame;
import assignments.assignment1.*;

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
        super(new BorderLayout()); // Setup layout, Feel free to make any changes
        this.loginManager = loginManager;

        // Set up main panel, Feel free to make any changes
        mainPanel = new JPanel(new BorderLayout());
        fieldsPanel = new JPanel(new GridLayout(6, 1, 10, 25));
        buttonPanel = new JPanel(new GridLayout(2,1, 50, 30));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(30, 280, 0, 280));
    

        initGUI();

        add(mainPanel, BorderLayout.CENTER);
    }

    /**
     * Method untuk menginisialisasi GUI.
     * Selama funsionalitas sesuai dengan soal, tidak apa apa tidak 100% sama.
     * Be creative and have fun!
     * */
    private void initGUI() {
        // TODO
        nameLabel = new JLabel("Masukkan nama Anda:");
        nameTextField = new JTextField();
        phoneLabel = new JLabel("Masukkan nomor handphone Anda:");
        phoneTextField = new JTextField();
        passwordLabel = new JLabel("Masukkan password Anda:");
        passwordField = new JPasswordField();
        registerButton = new JButton("Register");
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                if(nameTextField.getText().length() == 0 || phoneTextField.getText().length() == 0 || passwordField.getPassword().length == 0){
                    JOptionPane.showMessageDialog(null, "Semua field diatas wajib di isi!", "Empty Field", JOptionPane.ERROR_MESSAGE);
                }
                else if(validasiAngka(phoneTextField.getText()) == false){
                    JOptionPane.showMessageDialog(null, "Nomor handphone harus berisi angka!", "Invalid Phone Number", JOptionPane.ERROR_MESSAGE);
                }
                else{
                    handleRegister();
                }
            }
        });

        backButton = new JButton("Kembali");
        backButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                nameTextField.setText("");phoneTextField.setText("");passwordField.setText("");
                handleBack();
            }
        });

        fieldsPanel.add(nameLabel);
        fieldsPanel.add(nameTextField);
        fieldsPanel.add(phoneLabel);
        fieldsPanel.add(phoneTextField);
        fieldsPanel.add(passwordLabel);
        fieldsPanel.add(passwordField);
        buttonPanel.add(registerButton);
        buttonPanel.add(backButton);

        mainPanel.add(fieldsPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Method untuk kembali ke halaman home.
     * Akan dipanggil jika pengguna menekan "backButton"
     * */
    private void handleBack() {
        MainFrame mainFrame = MainFrame.getInstance();
        mainFrame.navigateTo(HomeGUI.KEY);
    }

    /**
    * Method untuk mendaftarkan member pada sistem.
    * Akan dipanggil jika pengguna menekan "registerButton"
    * */
    private void handleRegister() {
        // TODO
        String nama = nameTextField.getText();
        String noHP = phoneTextField.getText();
        String ID = NotaGenerator.generateId(nama, noHP);
        char[] password = passwordField.getPassword();
        String passwordString = "";
        for(char character: password){
            passwordString += character;
        }

        Member registeredMember = loginManager.register(nama, noHP, passwordString);
        if(registeredMember == null){
            JOptionPane.showMessageDialog(this, String.format("User dengan nama %s dan nomor hp %s sudah ada!", nama, noHP), "Registration Failed", JOptionPane.ERROR_MESSAGE);
            nameTextField.setText("");phoneTextField.setText("");passwordField.setText("");
            handleBack();
        }
        else{
            nameTextField.setText("");phoneTextField.setText("");passwordField.setText("");
            JOptionPane.showMessageDialog(this, String.format("Berhasil membuat user dengan ID %s!", ID), "Registration Successful", JOptionPane.INFORMATION_MESSAGE);
            MainFrame mainFrame = MainFrame.getInstance();
            mainFrame.navigateTo(HomeGUI.KEY);
        }
    }

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
