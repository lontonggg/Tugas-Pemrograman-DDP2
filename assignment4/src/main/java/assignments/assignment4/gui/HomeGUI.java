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
        super(new BorderLayout()); // Inisiasi frame
        this.fmt = NotaManager.fmt;
        this.cal = NotaManager.cal;

        // Panel utama
        mainPanel = new JPanel(new BorderLayout());

        // Panel untuk buttons
        buttonPanel = new JPanel(new GridLayout(3, 1, 70, 70));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(70, 290, 70, 290));

        // Menginisiasi GUI
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
        // Label selamat datang
        titleLabel = new JLabel("Selamat Datang di CuciCuci System!", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        // Button untuk login
        loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                handleToLogin();
            }
        });

        // Button untuk register
        registerButton = new JButton("Register");
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                handleToRegister();
            }            
        });

        // Button untuk nextday
        toNextDayButton = new JButton("Next Day");
        toNextDayButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                handleNextDay();
            }
        });

        // Label untuk tanggal
        dateLabel = new JLabel(String.format("Hari ini: %s", fmt.format(cal.getTime())), JLabel.CENTER);

        // Menambahkan buttons ke panel button
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);
        buttonPanel.add(toNextDayButton);

        // Menambahkan labels dan panel bantuan ke panel utama
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
        mainFrame.navigateTo(RegisterGUI.KEY); // Lanjut ke register window
    }

    /**
     * Method untuk pergi ke halaman login.
     * Akan dipanggil jika pengguna menekan "loginButton"
     * */
    private static void handleToLogin() {
        MainFrame mainFrame = MainFrame.getInstance();
        mainFrame.navigateTo(LoginGUI.KEY); // Kembali ke home window
    }

    /**
     * Method untuk skip hari.
     * Akan dipanggil jika pengguna menekan "toNextDayButton"
     * */
    private void handleNextDay() {
        toNextDay(); // Menjalankan method toNextDay dari TP 3, menambahkan 1 hari ke cal dan mengupdate semua nota
        dateLabel.setText(String.format("Hari ini: %s", fmt.format(cal.getTime()))); // Mengupdate label
        JOptionPane.showMessageDialog(this, "Kamu tidur hari ini... zzz...", "This is Prince Paul's Bubble Party's ability!", JOptionPane.INFORMATION_MESSAGE);
    }
}
