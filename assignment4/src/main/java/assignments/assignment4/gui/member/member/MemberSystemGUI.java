package assignments.assignment4.gui.member.member;

import assignments.assignment3.nota.Nota;
import assignments.assignment3.user.Member;
import assignments.assignment3.user.menu.SystemCLI;
import assignments.assignment4.MainFrame;
import assignments.assignment4.gui.member.AbstractMemberGUI;

import javax.swing.*;
import java.awt.event.ActionListener;

public class MemberSystemGUI extends AbstractMemberGUI {
    public static final String KEY = "MEMBER";

    public MemberSystemGUI(SystemCLI systemCLI) {
        super(systemCLI);
    }

    @Override
    public String getPageName(){
        return KEY;
    }

    public Member getLoggedInMember(){
        return loggedInMember;
    }

    /**
     * Method ini mensupply buttons yang sesuai dengan requirements MemberSystem.
     * Button yang disediakan method ini BELUM memiliki ActionListener.
     *
     * @return Array of JButton, berisi button yang sudah stylize namun belum ada ActionListener.
     * */
    @Override
    protected JButton[] createButtons() {
        JButton buttonLaundry = new JButton("Saya ingin laundry"); // Button untuk laundry
        JButton buttonNota = new JButton("Lihat detail nota saya"); // Button untuk melihat detail nota
        JButton[] buttonList = {buttonLaundry, buttonNota};
        return buttonList;
    }

    /**
     * Method ini mensupply ActionListener korespondensi dengan button yang dibuat createButtons()
     * sesuai dengan requirements MemberSystem.
     *
     * @return Array of ActionListener.
     * */
    @Override
    protected ActionListener[] createActionListeners() {
        return new ActionListener[]{
                e -> createNota(),
                e -> showDetailNota(),
        };
    }

    /**
     * Menampilkan detail Nota milik loggedInMember.
     * Akan dipanggil jika pengguna menekan button pertama pada createButtons
     * */
    private void showDetailNota() {
        JTextArea textArea = new JTextArea(20, 35); // Text area untuk detail nota
        Member member = this.getLoggedInMember(); // Mengambil instance member yang sedang login
        if(member.getNotaList().length == 0){ // Jika nota masih kosong
            textArea.setText("Belum pernah laundry di CuciCuci, hiks :'(");
        }
        else{
            String notaLengkap = "";
            for(Nota nota: member.getNotaList()){
                notaLengkap += nota.toString() + "\n\n"; // Menambahkan detail semua nota
            }
            textArea.setText(notaLengkap); // Mengisi textArea dengan detail nota
        }
        textArea.setCaretPosition(0); // agar TextArea muncul dari atas
        textArea.setEditable(false); // Agar TextArea tidak bisa di edit
        JScrollPane scrollPane = new JScrollPane(textArea);
        JOptionPane.showMessageDialog(null, scrollPane, "Detail Nota", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Pergi ke halaman CreateNotaGUI.
     * Akan dipanggil jika pengguna menekan button kedua pada createButtons
     * */
    private void createNota() {
        MainFrame mainFrame = MainFrame.getInstance();
        mainFrame.navigateTo(CreateNotaGUI.KEY); // Memunculkan window untuk membuat nota
    }
}
