package assignments.assignment1;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class NotaGenerator {
    private static final Scanner input = new Scanner(System.in); // Scanner
    // Menu Utama
    public static void main(String[] args) {
        boolean programRuns = true;
        while(programRuns){ // Main loop dari program
            printMenu(); // Memunculkan menu
            System.out.print("Pilihan : ");
            String pilihan = input.nextLine();
            System.out.println("================================");
            if(pilihan.equals("1") || pilihan.equals("2")){ 
                System.out.println("Masukkan nama Anda :");
                String nama = input.nextLine();
                System.out.println("Masukkan nomor handphone Anda:");
                String noHandphone = validasiAngka("Validasi No HP", "Nomor HP hanya menerima digit"); // Mengambil input no HP dan memvalidasinya
                if (pilihan.equals("1")){ // Pilihan 1 hanya mengeluarkan output ID
                    System.out.println("ID Anda : " + generateId(nama, noHandphone));
                }
                else if (pilihan.equals("2")){
                    String id = generateId(nama, noHandphone); // Generate ID
                    System.out.println("Masukkan tanggal terima");
                    String tanggalTerima = input.nextLine();
                    String paketLaundry = "";
                    while(true){ // Memvalidasi paket laundry
                        System.out.println("Masukkan paket laundry:");
                        paketLaundry = input.nextLine();
                        if(paketLaundry.equals("?")){ // Memunculkan paket laundry yang tersedia
                            showPaket();
                        }
                        else if(!paketLaundry.equals("express") && !paketLaundry.equals("fast") && !paketLaundry.equals("reguler")){ // Jika paket yang di input tidak diketahui
                            System.out.println("Paket " + paketLaundry + " tidak diketahui\n[ketik ? untuk mencari tahu jenis paket]");
                        }
                        else{ // Jika paket laundry sudah valid, keluar dari loop
                            break;
                        }
                    }
                    System.out.println("Masukkan berat cucian Anda [Kg]"); 
                    String beratCucian = validasiAngka("Validasi Berat", "Harap masukkan berat cucian Anda dalam bentuk bilangan positif."); // Mengambil input berat dan memvalidasinya
                    int berat = Integer.parseInt(beratCucian); // Convert berat menjadi integer
                    System.out.println(generateNota(id, paketLaundry, berat, tanggalTerima)); // Mengeluarkan output nota
                }
            }
            else if(pilihan.equals("0")){
                programRuns = false; // Menyelesaikan main loop dari program
            }
            else{
                System.out.println("Perintah tidak diketahui, silakan periksa kembali.");
            }
        }
        System.out.println("Terima kasih telah menggunakan NotaGenerator!");
    }
    // Method untuk memunculkan menu
    private static void printMenu() {
        System.out.println("Selamat datang di NotaGenerator!");
        System.out.println("==============Menu==============");
        System.out.println("[1] Generate ID");
        System.out.println("[2] Generate Nota");
        System.out.println("[0] Exit");
    }
    // Method untuk memunculkan paket
    private static void showPaket() {
        System.out.println("+-------------Paket-------------+");
        System.out.println("| Express | 1 Hari | 12000 / Kg |");
        System.out.println("| Fast    | 2 Hari | 10000 / Kg |");
        System.out.println("| Reguler | 3 Hari |  7000 / Kg |");
        System.out.println("+-------------------------------+");
    }
    // Method untuk geenerate ID
    public static String generateId(String nama, String nomorHP){
        String[] namaSplitted = nama.split(" ");
        String namaDepan = namaSplitted[0]; // Mengambil nama depan
        int checksum = 0;
        String gabunganNamaHP = namaDepan.toUpperCase() + "-" + nomorHP; // Menggabung nama dan no HP menjadi [nama]-[no HP]
        for(int i = 0; i < gabunganNamaHP.length(); i++){ // Mengiterasi string gabungan nama dan no HP
            if(gabunganNamaHP.charAt(i) >= 65 && gabunganNamaHP.charAt(i) <= 90){ // Jika character berupa huruf
                checksum += gabunganNamaHP.charAt(i) - 'A' + 1;
            }
            else if(Character.isDigit(gabunganNamaHP.charAt(i))){ // Jika character berupa angka
                checksum += Character.getNumericValue(gabunganNamaHP.charAt(i));
            }
            else{ // Jika character bukan huruf maupun angka
                checksum += 7;
            }
        }
        return String.format("%s-%02d", gabunganNamaHP, checksum);
    }
    // Method untuk generate nota
    public static String generateNota(String id, String paket, int berat, String tanggalTerima){
        int hargaPaketPerKg = 0;
        int waktuPengerjaan = 0;
        if (paket.equals("express")){
            hargaPaketPerKg = 12000;
            waktuPengerjaan = 1;
        }
        else if(paket.equals("fast")){
            hargaPaketPerKg = 10000;
            waktuPengerjaan = 2;
        }
        else if(paket.equals("reguler")){
            hargaPaketPerKg = 7000;
            waktuPengerjaan = 3;
        }
        if(berat < 2){
            berat = 2;
            System.out.println("Cucian kurang dari 2 kg, maka cucian akan dianggap sebagai 2 kg");
        }
        int totalHarga = hargaPaketPerKg * berat; // Menghitung total harga
        // Membuat format untuk tanggal menggunakan java.time
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate tanggalTerimaFormatted = LocalDate.parse(tanggalTerima, dateFormat);
        LocalDate tanggalSelesaiFormatted = tanggalTerimaFormatted.plusDays(waktuPengerjaan);
        String tanggalSelesai = tanggalSelesaiFormatted.format(dateFormat);
        // Mengeluarkan output nota
        System.out.println("Nota Laundry");
        return "ID    : " + id + "\nPaket : " + paket + "\nHarga :\n" + berat + " kg x " + hargaPaketPerKg + " = " + totalHarga + "\nTanggal Terima  : " + tanggalTerima + "\nTanggal Selesai : " + tanggalSelesai;
    }
    // Method untuk memvalidasi angka (No HP atau Berat)
    public static String validasiAngka(String opsi, String errorMessage){
        String angka;
        boolean angkaValid = false;
        while(true){
            angka = input.nextLine();
            for(int i = 0; i < angka.length(); i++){
                if(Character.isDigit(angka.charAt(i))){ // Mengiterasi setiap digit di dan cek apakah digit atau bukan
                    angkaValid = true;
                    if(opsi.equals("Validasi Berat") && angka.charAt(i) <= 0){ // Jika memvalidasi berat, maka angka harus > 0
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
    }
}
