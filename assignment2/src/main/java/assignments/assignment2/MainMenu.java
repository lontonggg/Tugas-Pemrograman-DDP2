package assignments.assignment2;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Scanner;

import static assignments.assignment1.NotaGenerator.*;

public class MainMenu {
    private static final Scanner input = new Scanner(System.in);
    private static SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
    private static Calendar cal = Calendar.getInstance();
    private static Nota[] notaList;
    private static Member[] memberList = new Member[0];

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

    private static void handleGenerateUser() { 
        // Mengambil input nama dan no HP, serta membuat object Member baru
        System.out.println("Masukan nama Anda:");
        String nama = input.nextLine();
        System.out.println("Masukan nomor handphone Anda:");
        String noHp = validasiAngka("Validasi HP", "Nomor HP hanya menerima digit");
        Member newMember = new Member(nama, noHp);
        // Cek apakah member sudah pernah terdaftar atau belum
        boolean memberDouble = false;
        for(Member member: memberList){
            if(member.getNama().equals(newMember.getNama()) && member.getNoHp().equals(newMember.getNoHp())){
                System.out.printf("Member dengan nama %s dan nomor hp %s sudah ada!%n", nama, noHp);
                memberDouble = true;
                break;
            }
        }
        if(memberDouble == false){
            // Menambahkan member baru ke dalam array member
            Member[] newMemberList = new Member[memberList.length + 1];
            System.arraycopy(memberList, 0, newMemberList, 0, memberList.length);
            newMemberList[newMemberList.length-1] = newMember;
            memberList = newMemberList;
            System.out.printf("Berhasil membuat member dengan id %s!%n", newMember.getId());
            System.out.println(Arrays.toString(memberList));
        }
    }

    private static void handleGenerateNota() {
        // TODO: handle ambil cucian
        System.out.println("Masukkan ID member:");
        String id = input.nextLine();
        Member member = null;
        boolean memberAda = false;
        for(Member members: memberList){
            if(members.getId() == id){
                member = members;
                memberAda = true;
                break;
            }
            else{
                System.out.printf("Member dengan id %s tidak ditemukan!%n", id);
            }
        }

        if(memberAda){
            String paketLaundry = "";
            while(true){ // Memvalidasi paket laundry
                System.out.println("Masukkan paket laundry:");
                paketLaundry = input.nextLine();
                if(paketLaundry.equals("?")){ // Memunculkan paket laundry yang tersedia
                    showPaket();
                }
                else if(!paketLaundry.equalsIgnoreCase("express") && !paketLaundry.equalsIgnoreCase("fast") && !paketLaundry.equalsIgnoreCase("reguler")){ // Jika paket yang di input tidak diketahui
                    System.out.println("Paket " + paketLaundry + " tidak diketahui\n[ketik ? untuk mencari tahu jenis paket]");
                }
                else{ // Jika paket laundry sudah valid, keluar dari loop
                    break;
                }
            }
            System.out.println("Masukkan berat cucian Anda [Kg]"); 
            String beratCucian = validasiAngka("Validasi Berat", "Harap masukkan berat cucian Anda dalam bentuk bilangan positif."); // Mengambil input berat dan memvalidasinya
            int berat = Integer.parseInt(beratCucian); // Convert berat menjadi integer
            Nota nota = new Nota(member, paketLaundry, berat, fmt.format(cal.getTime()));
        }
    }

    private static void handleListNota() {
        // TODO: handle list semua nota pada sistem
        if(notaList.length == 0){
            System.out.println("Terdaftar 0 nota dalam sistem.");
        }
    }

    private static void handleListUser() {
        // TODO: handle list semua user pada sistem
        if(memberList.length == 0){
            System.out.println("Terdaftar 0 member dalam sistem.");
        }
        else{
            System.out.printf("Terdaftar %d member dalam sistem%n", memberList.length);
            for(Member members: memberList){
                System.out.printf("- %s : %s%n", members.getId(), members.getNama());
            }
        }
    }

    private static void handleAmbilCucian() {
        // TODO: handle ambil cucian
    }

    private static void handleNextDay() {
        // TODO: handle ganti hari
    }

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

    private static void showPaket() {
        System.out.println("+-------------Paket-------------+");
        System.out.println("| Express | 1 Hari | 12000 / Kg |");
        System.out.println("| Fast    | 2 Hari | 10000 / Kg |");
        System.out.println("| Reguler | 3 Hari |  7000 / Kg |");
        System.out.println("+-------------------------------+");
    }

    public static String validasiAngka(String opsi, String errorMessage){
        String angka;
        boolean angkaValid = false;
        while(true){
            angka = input.nextLine();
            for(int i = 0; i < angka.length(); i++){
                if(Character.isDigit(angka.charAt(i))){ // Mengiterasi setiap digit di dan cek apakah digit atau bukan
                    angkaValid = true;
                    if(opsi.equals("Validasi Berat") && Character.getNumericValue(angka.charAt(i)) <= 0){ // Jika memvalidasi berat, maka angka harus > 0
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
