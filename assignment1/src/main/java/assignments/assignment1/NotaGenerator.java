package assignments.assignment1;

import java.util.Scanner;

public class NotaGenerator {
    private static final Scanner input = new Scanner(System.in);

    /**
     * Method main, program utama kalian berjalan disini.
     */
    public static void main(String[] args) {
        // TODO: Implement interface menu utama
        boolean programRuns = true;
        while(programRuns){
            printMenu();
            System.out.print("Pilihan : ");
            String pilihan = input.nextLine();
            System.out.println("================================");
            if(pilihan.equals("1")){
                System.out.println("Masukkan nama Anda :");
                String nama = input.next();
                input.nextLine();
                System.out.println("Masukkan nomor handphone Anda:");
                String noHandphone = validasiAngka();
                System.out.println("ID Anda : " + generateId(nama, noHandphone));
            }
            else if(pilihan.equals("2")){
                // Bikin fitur 2
            }
            else if(pilihan.equals("0")){
                programRuns = false;
            }
            else{
                System.out.println("Perintah tidak diketahui, silakan periksa kembali.");
            }
        }
        System.out.println("Terima kasih telah menggunakan NotaGenerator!");
    }

    /**
     * Method untuk menampilkan menu di NotaGenerator.
     */
    private static void printMenu() {
        System.out.println("Selamat datang di NotaGenerator!");
        System.out.println("==============Menu==============");
        System.out.println("[1] Generate ID");
        System.out.println("[2] Generate Nota");
        System.out.println("[0] Exit");
    }

    /**
     * Method untuk menampilkan paket.
     */
    private static void showPaket() {
        System.out.println("+-------------Paket-------------+");
        System.out.println("| Express | 1 Hari | 12000 / Kg |");
        System.out.println("| Fast    | 2 Hari | 10000 / Kg |");
        System.out.println("| Reguler | 3 Hari |  7000 / Kg |");
        System.out.println("+-------------------------------+");
    }

    /**
     * Method untuk membuat ID dari nama dan nomor handphone.
     * Parameter dan return type dari method ini tidak boleh diganti agar tidak mengganggu testing
     *
     * @return String ID anggota dengan format [NAMADEPAN]-[nomorHP]-[2digitChecksum]
     */
    public static String generateId(String nama, String nomorHP){
        // TODO: Implement generate ID sesuai soal.
        int checksum = 0;
        String gabunganNamaHP = nama.toUpperCase() + "-" + nomorHP;
        for(int i = 0; i < gabunganNamaHP.length(); i++){
            if(gabunganNamaHP.charAt(i) >= 65 && gabunganNamaHP.charAt(i) <= 90){
                checksum += gabunganNamaHP.charAt(i) - 'A' + 1;
            }
            else if(Character.isDigit(gabunganNamaHP.charAt(i))){
                checksum += Character.getNumericValue(gabunganNamaHP.charAt(i));
            }
            else{
                checksum += 7;
            }
        }
        return String.format("%s-%02d", gabunganNamaHP, checksum);
    }

    /**
     *
     * Method untuk membuat Nota.
     * Parameter dan return type dari method ini tidak boleh diganti agar tidak mengganggu testing.
     *
     * @return string nota dengan format di bawah:
     *         <p>ID    : [id]
     *         <p>Paket : [paket]
     *         <p>Harga :
     *         <p>[berat] kg x [hargaPaketPerKg] = [totalHarga]
     *         <p>Tanggal Terima  : [tanggalTerima]
     *         <p>Tanggal Selesai : [tanggalTerima + LamaHariPaket]
     */

    public static String generateNota(String id, String paket, int berat, String tanggalTerima){
        // TODO: Implement generate nota sesuai soal.
        return null;
    }

    public static String validasiAngka(){
        String angka;
        boolean angkaValid = false;
        while(true){
            angka = input.nextLine();
            for(int i = 0; i < angka.length(); i++){
                if(Character.isDigit(angka.charAt(i))){
                    angkaValid = true;
                }
                else{
                    System.out.println("Nomor HP hanya menerima digit");
                    angkaValid = false;
                    break;
                }
            }
            if(angkaValid){
                return angka;
            }
        }
    }
}
