package assignments.assignment3.nota;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NotaManager {
    public static SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
    public static Calendar cal = Calendar.getInstance();
    static public Nota[] notaList = new Nota[0];

    /**
     * Skips ke hari berikutnya dan update semua entri nota yang sesuai.
     */
    public static void toNextDay(){
        cal.add(Calendar.DATE, 1); // Menambahkan 1 hari pada calender yang digunakan oleh sistem
        for(Nota nota: notaList){
            nota.toNextDay(); // Mengupdate nota dan mengurangi sisa hari pengerjaan
        }
    }

    /**
     * Menambahkan nota baru ke NotaList.
     *
     * @param nota Nota object untuk ditambahkan.
     */
    public static void addNota(Nota nota){ // Method untuk menambahkan nota baru ke NotaList
        Nota[] newArray = new Nota[notaList.length + 1]; // Membuat array baru dengan panjang lebih 1
        System.arraycopy(notaList, 0, newArray, 0, notaList.length); // Membuat copy dari array sebelumnya
        newArray[newArray.length-1] = nota; 
        notaList = newArray;
    }
}
