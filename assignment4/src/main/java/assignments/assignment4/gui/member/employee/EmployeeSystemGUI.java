package assignments.assignment4.gui.member.employee;

import assignments.assignment3.nota.Nota;
import assignments.assignment3.nota.NotaManager;
import assignments.assignment3.user.Member;
import assignments.assignment3.user.menu.SystemCLI;
import assignments.assignment4.gui.member.AbstractMemberGUI;

import javax.swing.*;
import java.awt.event.ActionListener;

public class EmployeeSystemGUI extends AbstractMemberGUI {
    public static final String KEY = "EMPLOYEE";

    public EmployeeSystemGUI(SystemCLI systemCLI) {
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
     * Method ini mensupply buttons yang sesuai dengan requirements Employee.
     * Button yang disediakan method ini BELUM memiliki ActionListener.
     *
     * @return Array of JButton, berisi button yang sudah stylize namun belum ada ActionListener.
     * */
    @Override
    protected JButton[] createButtons() {
        JButton nyuciButton = new JButton("It's nyuci time"); // Button untuk mencuci
        JButton displayButton = new JButton("Display List Nota"); // Button untuk melihat list nota
        JButton[] buttonList = {nyuciButton, displayButton};
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
                e -> cuci(),
                e -> displayNota(),
        };
    }

    /**
     * Menampilkan semua Nota yang ada pada sistem.
     * Akan dipanggil jika pengguna menekan button pertama pada createButtons
     * */
    private void displayNota() {
        String notaStatus = "";
        if(NotaManager.notaList.length == 0){ // Jika belum ada nota terdaftar
            notaStatus += "Belum ada nota"; 
            JOptionPane.showMessageDialog(this, notaStatus, "List Nota", JOptionPane.ERROR_MESSAGE);
        }
        else{
            for(Nota nota: NotaManager.notaList){
                notaStatus += nota.getNotaStatus() + "\n"; // Menambahkan status setiap nota
            }
            JOptionPane.showMessageDialog(this, notaStatus, "List Nota", JOptionPane.INFORMATION_MESSAGE);
        }
       
    }

    /**
     * Menampilkan dan melakukan action mencuci.
     * Akan dipanggil jika pengguna menekan button kedua pada createButtons
     * */
    private void cuci() {
        JOptionPane.showMessageDialog(this, String.format("Stand Back! %s beginning to nyuci!", this.getLoggedInMember().getNama()), "Nyuci Time", JOptionPane.INFORMATION_MESSAGE);

        String nyuciResults = "";
        if(NotaManager.notaList.length == 0){ // Jika belum ada nota terdaftar
            nyuciResults += "Nothing to cuci here";
            JOptionPane.showMessageDialog(this, nyuciResults, "Nyuci Results", JOptionPane.ERROR_MESSAGE);
        }
        else{
            for(Nota nota: NotaManager.notaList){
                nyuciResults += nota.kerjakan() + "\n"; // MEnambahkan status setiap nota setelah dikerjakan
            }
            JOptionPane.showMessageDialog(this, nyuciResults, "Nyuci Results", JOptionPane.INFORMATION_MESSAGE);
        }
        
    }
}
