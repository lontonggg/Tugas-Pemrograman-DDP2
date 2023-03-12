package assignments.assignment2;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Nota {
    private Member member;
    private String paket, tanggalMasuk;
    private int berat, sisaHariPengerjaan, idNota;
    private boolean isReady;
    private static int idCounter = 0;

    // TODO: tambahkan attributes yang diperlukan untuk class ini
    public Nota(Member member, String paket, int berat, String tanggalMasuk) {
        // TODO: buat constructor untuk class ini
        this.member = member;
        this.paket = paket;
        this.berat = berat;
        this.tanggalMasuk = tanggalMasuk;
        this.idNota = idCounter;
        idCounter++;
    }
    public int getIdNota(){
        return this.idNota;
    }
    public boolean notaIsReady(){
        return this.isReady;
    }
    public void updateNota(){
        if(this.sisaHariPengerjaan != 0){
            this.sisaHariPengerjaan --;
        }
        if(sisaHariPengerjaan == 0){
            this.isReady = true;
        }
        else{
            this.isReady = false;
        }
    }
    
    public String getPaket(){
        return this.paket;
    }

    public int getBerat(){
        return this.berat;
    }

    public String getTanggalMasuk(){
        return this.tanggalMasuk;
    }
    
    // TODO: tambahkan methods yang diperlukan untuk class ini
    public String generateNota(String id, String paket, int berat, String tanggalTerima){
        int hargaPaketPerKg = 0;
        this.sisaHariPengerjaan = 0;
        if (paket.equalsIgnoreCase("express")){
            hargaPaketPerKg = 12000;
            this.sisaHariPengerjaan = 1;
        }
        else if(paket.equalsIgnoreCase("fast")){
            hargaPaketPerKg = 10000;
            this.sisaHariPengerjaan = 2;
        }
        else if(paket.equalsIgnoreCase("reguler")){
            hargaPaketPerKg = 7000;
            this.sisaHariPengerjaan = 3;
        }
        if(berat < 2){
            berat = 2;
            System.out.println("Cucian kurang dari 2 kg, maka cucian akan dianggap sebagai 2 kg");
        }
        // Membuat format untuk tanggal menggunakan java.time
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate tanggalTerimaFormatted = LocalDate.parse(tanggalTerima, dateFormat);
        LocalDate tanggalSelesaiFormatted = tanggalTerimaFormatted.plusDays(this.sisaHariPengerjaan);
        String tanggalSelesai = tanggalSelesaiFormatted.format(dateFormat);
        int totalHarga = hargaPaketPerKg * berat; // Menghitung total harga
        System.out.println("Berhasil menambahkan nota!");
        System.out.printf("[ID Nota = %d]%n", this.idNota);
        System.out.println("Nota Laundry");
        // Mengeluarkan output nota
        member.increaseBonus();
        if(member.getBonusCounter() == 3){
            int totalHargaDiskon = totalHarga / 2;
            member.resetBonus();
            return "ID    : " + id + "\nPaket : " + paket + "\nHarga :\n" + berat + " kg x " + hargaPaketPerKg + " = " 
            + totalHarga + " = " + totalHargaDiskon + " (Discount member 50%!!!)" +  "\nTanggal Terima  : " + tanggalTerima 
            + "\nTanggal Selesai : " + tanggalSelesai + "\nStatus      	:" + " Belum bisa diambil :(";
        }
        return "ID    : " + id + "\nPaket : " + paket + "\nHarga :\n" + berat + " kg x " + hargaPaketPerKg + " = " 
        + totalHarga + "\nTanggal Terima  : " + tanggalTerima + "\nTanggal Selesai : " + tanggalSelesai + "\nStatus      	:" + " Belum bisa diambil :(";  
    }
}
