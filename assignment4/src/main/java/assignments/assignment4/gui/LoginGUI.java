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
        super(new BorderLayout()); // Setup layout, Feel free to make any changes
        this.loginManager = loginManager;

        // Set up main panel, Feel free to make any changes
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        fieldsPanel = new JPanel(new GridLayout(4,1, 10, 55));
        buttonPanel = new JPanel(new GridLayout(2,1, 50, 30));

        buttonPanel.setBorder(BorderFactory.createEmptyBorder(30, 280, 20, 280));

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
        idLabel = new JLabel("Masukkan ID Anda:");

        idTextField = new JTextField();

        passwordLabel = new JLabel("Masukkan password Anda:");

        passwordField = new JPasswordField();

        loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                if(idTextField.getText().length() == 0 || passwordField.getPassword().length == 0){
                    JOptionPane.showMessageDialog(null, "Semua field diatas wajib di isi!", "Empty Field", JOptionPane.ERROR_MESSAGE);
                }
                else{
                    handleLogin();
                }
                
            }
        });

        backButton = new JButton("Kembali");
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                handleBack();
            }
        });

        fieldsPanel.add(idLabel);  
        fieldsPanel.add(idTextField);
        fieldsPanel.add(passwordLabel);
        fieldsPanel.add(passwordField);

        buttonPanel.add(loginButton);
        buttonPanel.add(backButton);

        mainPanel.add(fieldsPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    /**
     * Method untuk kembali ke halaman home.
     * Akan dipanggil jika pengguna menekan "backButton"
     * */
    private void handleBack() {
        idTextField.setText("");passwordField.setText("");
        MainFrame mainFrame = MainFrame.getInstance();
        mainFrame.navigateTo(HomeGUI.KEY);
    }

    /**
     * Method untuk login pada sistem.
     * Akan dipanggil jika pengguna menekan "loginButton"
     * */
    private void handleLogin() {
        // TODO
        String id = idTextField.getText();
        String passwordString = "";
        char[] password = passwordField.getPassword();
        for(char characters: password){
            passwordString += characters;
        }

        idTextField.setText("");passwordField.setText("");
        SystemCLI systemCLI = loginManager.getSystem(id);
        if(systemCLI == null){
            JOptionPane.showMessageDialog(this, String.format("User dengan ID %s tidak ditemukan!", id), "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
        else{
            MainFrame mainFrame = MainFrame.getInstance();
            if(mainFrame.login(id, passwordString)){
                if(systemCLI instanceof MemberSystem){
                    mainFrame.navigateTo(MemberSystemGUI.KEY);
                }
                else if(systemCLI instanceof EmployeeSystem){
                    mainFrame.navigateTo(EmployeeSystemGUI.KEY);
                }
            }
            else{
                JOptionPane.showMessageDialog(this, String.format("ID atau password invalid!", id), "Invalid ID or Password", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
