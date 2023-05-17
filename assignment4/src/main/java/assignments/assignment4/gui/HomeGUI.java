package assignments.assignment4.gui;

import assignments.assignment3.nota.NotaManager;
import assignments.assignment4.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;


import static assignments.assignment3.nota.NotaManager.toNextDay;

public class HomeGUI extends JPanel {
    public static final String KEY = "HOME";
    private JLabel titleLabel;
    private JLabel dateLabel;
    private JPanel mainPanel;
    private JPanel buttonPanel;
    private JButton loginButton;
    private JButton registerButton;
    private JButton toNextDayButton;
    private final SimpleDateFormat fmt;
    private final Calendar cal;
   


    public HomeGUI(){
        super(new BorderLayout()); // Setup layout, Feel free to make any changes
        this.fmt = NotaManager.fmt;
        this.cal = NotaManager.cal;

        // Set up main panel, Feel free to make any changes
        mainPanel = new JPanel(new BorderLayout());
        buttonPanel = new JPanel(new GridLayout(3, 1, 50, 50));

        buttonPanel.setBorder(BorderFactory.createEmptyBorder(70, 280, 70, 280));
        
        initGUI();

        add(mainPanel, BorderLayout.CENTER);
    }


    /**
     * Method untuk menginisialisasi GUI.
     * Selama funsionalitas sesuai dengan soal, tidak apa apa tidak 100% sama.
     * Be creative and have fun!
     * */

    private void initGUI() {
        titleLabel = new JLabel("Selamat Datang di CuciCuci System!", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        
        loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                handleToLogin();
            }
        });

        registerButton = new JButton("Register");
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                handleToRegister();
            }            
        });

        toNextDayButton = new JButton("Next Day");
        toNextDayButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                handleNextDay();
            }
        });

        dateLabel = new JLabel(String.format("Hari ini: %s", fmt.format(cal.getTime())), JLabel.CENTER);

        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);
        buttonPanel.add(toNextDayButton);

        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(dateLabel, BorderLayout.SOUTH);
    }

    /**
     * Method untuk pergi ke halaman register.
     * Akan dipanggil jika pengguna menekan "registerButton"
     * */
    private static void handleToRegister() {
        MainFrame mainFrame = MainFrame.getInstance();
        mainFrame.navigateTo(RegisterGUI.KEY);
    }

    /**
     * Method untuk pergi ke halaman login.
     * Akan dipanggil jika pengguna menekan "loginButton"
     * */
    private static void handleToLogin() {
        MainFrame mainFrame = MainFrame.getInstance();
        mainFrame.navigateTo(LoginGUI.KEY);
    }

    /**
     * Method untuk skip hari.
     * Akan dipanggil jika pengguna menekan "toNextDayButton"
     * */
    private void handleNextDay() {
        toNextDay(); // Menambahkan 1 hari pada calender yang digunakan oleh sistem
        dateLabel.setText(String.format("Hari ini: %s", fmt.format(cal.getTime())));
        JOptionPane.showMessageDialog(this, "Kamu tidur hari ini... zzz...", "This is Prince Paul's Bubble Party's ability!", JOptionPane.INFORMATION_MESSAGE);
        // toNextDay();
    }
}
