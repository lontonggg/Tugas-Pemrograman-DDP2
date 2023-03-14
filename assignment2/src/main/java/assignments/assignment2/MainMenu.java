package assignments.assignment2;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Scanner;

public class MainMenu {
    private static final Scanner input = new Scanner(System.in);
    private static SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy"); // Format tanggal
    private static Calendar cal = Calendar.getInstance(); // Untuk mengambil tanggal hari ini
    private static Nota[] notaList = new Nota[0]; // Array nota
    private static Member[] memberList = new Member[0]; // Array member
    // Main program
    public static void main(String[] args) {
        boolean isRunning = true;
        while (isRunning) {
            printMenu();
            System.out.print("Pilihan : ");
            String command = input.nextLine();
            System.out.println("================================");
            switch (command){
                case "1" -> handleGenerateUser();
                case "2" -> handleGenerateNota();
                case "3" -> handleListNota();
                case "4" -> handleListUser();
                case "5" -> handleAmbilCucian();
                case "6" -> handleNextDay();
                case "0" -> isRunning = false;
                default -> System.out.println("Perintah tidak diketahui, silakan periksa kembali.");
            }
        }
        System.out.println("Terima kasih telah menggunakan NotaGenerator!");
    }
    // Fitur untuk membuat user baru
    private static void handleGenerateUser() { 
        // Mengambil input nama dan no HP, serta membuat object Member baru
        System.out.println("Masukan nama Anda:");
        String nama = input.nextLine();
        System.out.println("Masukan nomor handphone Anda:");
        String noHp = validasiAngka("Validasi HP", "Field nomor HP hanya menerima digit");
        Member newMember = new Member(nama, noHp);
        // Cek apakah member sudah pernah terdaftar atau belum
        boolean memberDouble = false;
        for(Member member: memberList){
            if(member.getId().equals(newMember.getId())){
                System.out.printf("Member dengan nama %s dan nomor hp %s sudah ada!%n", nama, noHp);
                memberDouble = true;
                break;
            }
        }
        if(memberDouble == false){
            // Menambahkan member baru ke dalam array member
            Member[] newMemberList = new Member[memberList.length + 1]; // Membuat array baru dengan panjang lebih 1
            System.arraycopy(memberList, 0, newMemberList, 0, memberList.length); // Membuat copy dari array sebelumnya
            newMemberList[newMemberList.length-1] = newMember; 
            memberList = newMemberList;
            System.out.printf("Berhasil membuat member dengan id %s!%n", newMember.getId());
        }
    }
    // Fitur untuk membuat nota baru
    private static void handleGenerateNota() {
        System.out.println("Masukkan ID member:");
        String id = input.nextLine();
        Member member = null;
        boolean memberAda = false;
        for(Member members: memberList){ // Cek apakah member terdaftar
            if(members.getId().equals(id)){
                member = members; // Mengambil objek member yang ingin membuat nota
                memberAda = true;
                break;
            }
        }
        if(memberAda){
            String paketCucian = "";
            while(true){ // Memvalidasi paket laundry
                System.out.println("Masukkan paket laundry:");
                paketCucian = input.nextLine();
                if(paketCucian.equals("?")){ // Memunculkan paket laundry yang tersedia
                    showPaket();
                }
                else if(!paketCucian.equalsIgnoreCase("express") && !paketCucian.equalsIgnoreCase("fast") && !paketCucian.equalsIgnoreCase("reguler")){ // Jika paket yang di input tidak diketahui
                    System.out.println("Paket " + paketCucian + " tidak diketahui\n[ketik ? untuk mencari tahu jenis paket]");
                }
                else{ // Jika paket laundry sudah valid, keluar dari loop
                    break;
                }
            }
            System.out.println("Masukkan berat cucian Anda [Kg]"); 
            String beratCucian = validasiAngka("Validasi Berat", "Harap masukkan berat cucian Anda dalam bentuk bilangan positif."); // Mengambil input berat dan memvalidasinya
            int berat = Integer.parseInt(beratCucian); // Convert berat menjadi integer
            Nota nota = new Nota(member, paketCucian, berat, fmt.format(cal.getTime()));
            System.out.println(nota.generateNota(member.getId(), nota.getPaket(), nota.getBerat(), nota.getTanggalMasuk())); // Print nota
            Nota[] newNotaList = new Nota[notaList.length + 1];
            System.arraycopy(notaList, 0, newNotaList, 0, notaList.length);
            newNotaList[newNotaList.length-1] = nota;
            notaList = newNotaList;
        }
        else{
            System.out.printf("Member dengan id %s tidak ditemukan!%n", id);
        }
    }
    // Method untuk melihat nota yang sudah terdaftar
    private static void handleListNota(){  
        if(notaList.length == 0){ // Jika tidak ada nota yang terdaftar
            System.out.println("Terdaftar 0 nota dalam sistem.");
        }
        else{ 
            System.out.printf("Terdaftar %d nota dalam sistem.%n", notaList.length);
            for(Nota nota: notaList){ // Cek kesiapan nota
                String status;
                if(nota.notaIsReady()){ 
                    status = "Sudah dapat diambil!";
                }
                else{
                    status = "Belum bisa diambil :(";
                }
                System.out.printf("- [%d] Status      	: %s%n", nota.getIdNota(), status); // Print nota dan statusnya
            }
        }
    }
    // Method untuk melihat user-user yang sudah terdaftar
    private static void handleListUser() {
        if(memberList.length == 0){ // Jika tidak ada user terdaftar
            System.out.println("Terdaftar 0 member dalam sistem.");
        }
        else{
            System.out.printf("Terdaftar %d member dalam sistem.%n", memberList.length);
            for(Member members: memberList){
                System.out.printf("- %s : %s%n", members.getId(), members.getNama()); // Print member
            }
        }
    }
    // Method untuk mengambil cucian
    private static void handleAmbilCucian() {
        System.out.println("Masukan ID nota yang akan diambil:");
        int idNota = Integer.parseInt(validasiAngka("Validasi ID Nota", "ID nota berbentuk angka!")); // Validasi input nota
        boolean idNotaFound = false; 
        Nota notaToRemove = null;
        for(Nota nota: notaList){ // Cek apakah nota ada atau tidak
            if(idNota == nota.getIdNota()){
                idNotaFound = true;
                notaToRemove = nota;
            }
        }
        if(idNotaFound){
            if(notaToRemove.notaIsReady()){ // Nota ada dan siap di ambil
                System.out.printf("Nota dengan ID %d berhasil diambil!%n", idNota);
                int indexNota = 0; 
                for(int i = 0; i < notaList.length; i++){ // Mencari index dari nota
                    if(notaList[i].getIdNota() == idNota){
                        indexNota = i;
                    }
                }
                // Menghapus objek nota dari notaList
                Nota[] newNotaList = new Nota[notaList.length - 1]; // Membuat array baru dengan panjang array sebelumnya dikurangi 1
                for(int i = 0; i < notaList.length; i++){
                    if(i == indexNota){ // Item yang ingin di hapus tidak dimasukkan ke array baru
                        continue;
                    } 
                    else{
                        if(i >= indexNota){ // Item yang indexnya lebih dari index benda yang di hapus akan dimasukkan ke array dengan index dikurangi 1
                            newNotaList[i-1] = notaList[i];
                        }
                        else{
                            newNotaList[i] = notaList[i];
                        } 
                    }
                }
                notaList = newNotaList;
            }
            else{ // Nota ada tapi belum siap di ambil
                System.out.printf("Nota dengan ID %d gagal diambil!%n", idNota);
            }
            
        }
        else{ // Nota tidak ditemukan
            System.out.printf("Nota dengan ID %d tidak ditemukan!%n", idNota); 
        }
    }
    // Method untuk melanjutkan hari ke hari selanjutnya
    private static void handleNextDay() {
        cal.add(Calendar.DATE, 1); // Menambahkan 1 hari ke tanggal saat ini
        System.out.println("Dek Depe tidur hari ini... zzz...");
        for(Nota nota: notaList){
            nota.updateNota(); // Mengupdate setiap nota
            if(nota.notaIsReady()){ // Jika cucian sudah siap diambil
                System.out.printf("Laundry dengan nota ID %d sudah dapat diambil!%n", nota.getIdNota());
            }
        }
        System.out.println("Selamat pagi dunia!\nDek Depe: It's CuciCuci Time."); 
    }
    // Method untuk memunculkan menu utama
    private static void printMenu() {
        System.out.println("\nSelamat datang di CuciCuci!");
        System.out.printf("Sekarang Tanggal: %s\n", fmt.format(cal.getTime()));
        System.out.println("==============Menu==============");
        System.out.println("[1] Generate User");
        System.out.println("[2] Generate Nota");
        System.out.println("[3] List Nota");
        System.out.println("[4] List User");
        System.out.println("[5] Ambil Cucian");
        System.out.println("[6] Next Day");
        System.out.println("[0] Exit");
    }
    // Method untuk memunculkan paket laundry
    private static void showPaket() {
        System.out.println("+-------------Paket-------------+");
        System.out.println("| Express | 1 Hari | 12000 / Kg |");
        System.out.println("| Fast    | 2 Hari | 10000 / Kg |");
        System.out.println("| Reguler | 3 Hari |  7000 / Kg |");
        System.out.println("+-------------------------------+");
    }
    // Method untuk memvalidasi angka (No HP, Berat Cucian, ID Nota)
    public static String validasiAngka(String opsi, String errorMessage){
        String angka;
        boolean angkaValid = false;
        while(true){
            angka = input.nextLine();
            if(angka.length() != 0){
                for(int i = 0; i < angka.length(); i++){
                    if(Character.isDigit(angka.charAt(i))){ // Mengiterasi setiap digit di dan cek apakah digit atau bukan
                        angkaValid = true;
                        if(opsi.equals("Validasi Berat") && Character.getNumericValue(angka.charAt(i)) <= 0 && angka.length() == 1){ // Jika memvalidasi berat, maka angka harus > 0
                            System.out.println(errorMessage);
                            angkaValid = false;
                            break; // Break dari for loop dan kembali ke awal while loop
                        }
                    }
                    else{ 
                        System.out.println(errorMessage);
                        angkaValid = false;
                        break; // Break dari for loop dan kembali ke awal while loop
                    }
                }
                if(angkaValid){ // Jika angka sudah dipastikan valid, maka akan di return
                    return angka;
                }
            }
            else{
                System.out.println(errorMessage);
            }
        }
    }
}
