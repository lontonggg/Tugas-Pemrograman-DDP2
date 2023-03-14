package assignments.assignment2;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Nota {
    // Variable class untuk menentukan ID nota
    private static int idCounter = 0;
     // Variable untuk tiap instance Nota
    private Member member;
    private String paket, tanggalMasuk;
    private int berat, sisaHariPengerjaan, idNota;
    private boolean isReady;

    public Nota(Member member, String paket, int berat, String tanggalMasuk) { // Constructor untuk class Nota
        this.member = member;
        this.paket = paket;
        this.berat = berat;
        this.tanggalMasuk = tanggalMasuk;
        this.idNota = idCounter;
        idCounter++;
    }
    public int getIdNota(){ // Method untuk mengambil ID Nota
        return this.idNota;
    }
    public boolean notaIsReady(){ // Method untuk cek kesiapan nota
        return this.isReady;
    }
    public void updateNota(){ // Method untuk update status nota
        if(this.sisaHariPengerjaan != 0){
            this.sisaHariPengerjaan --; // Mengurangi sisa hari pengerjaan jika berganti hari
        }
        if(sisaHariPengerjaan == 0){ // Jika sisa hari sudah 0 maka cucian siao di ambil
            this.isReady = true;
        }
        else{
            this.isReady = false;
        }
    }
    public String getPaket(){ // Method untuk mengambil paket dari cucian
        return this.paket;
    }
    public int getBerat(){ // Method untuk mengambil berat dari cucian
        return this.berat;
    }
    public String getTanggalMasuk(){ // Method untuk mengambil tanggal masuk dari cucian
        return this.tanggalMasuk;
    }
    // Method untuk membuat nota
    public String generateNota(String id, String paket, int berat, String tanggalTerima){
        int hargaPaketPerKg = 0;
        this.sisaHariPengerjaan = 0;
        if (paket.equalsIgnoreCase("express")){ // Paket express
            hargaPaketPerKg = 12000;
            this.sisaHariPengerjaan = 1;
        }
        else if(paket.equalsIgnoreCase("fast")){ // Paket fast
            hargaPaketPerKg = 10000;
            this.sisaHariPengerjaan = 2;
        }
        else if(paket.equalsIgnoreCase("reguler")){ // Paket reguler
            hargaPaketPerKg = 7000;
            this.sisaHariPengerjaan = 3;
        }
        if(berat < 2){ // Jika kurang dari 2 kg maka akan dianggap 2 kg
            berat = 2;
            System.out.println("Cucian kurang dari 2 kg, maka cucian akan dianggap sebagai 2 kg");
        }
        // Membuat format untuk tanggal menggunakan java.time
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // Format dari bentuk tanggal
        LocalDate tanggalTerimaFormatted = LocalDate.parse(tanggalTerima, dateFormat);
        LocalDate tanggalSelesaiFormatted = tanggalTerimaFormatted.plusDays(this.sisaHariPengerjaan);
        String tanggalSelesai = tanggalSelesaiFormatted.format(dateFormat);
        int totalHarga = hargaPaketPerKg * berat; // Menghitung total harga
        System.out.println("Berhasil menambahkan nota!");
        System.out.printf("[ID Nota = %d]%n", this.idNota);
        System.out.println("Nota Laundry");
        // Mengeluarkan output nota
        member.increaseBonus(); // Menambahkan bonus untuk tiap pemesanan
        if(member.getBonusCounter() == 3){ // Jika sudah memesan 3 kali, maka mendapat diskon 50%, bonus counter di reset
            int totalHargaDiskon = totalHarga / 2;
            member.resetBonus(); // Reset bonus
            return "ID    : " + id + "\nPaket : " + paket + "\nHarga :\n" + berat + " kg x " + hargaPaketPerKg + " = " 
            + totalHarga + " = " + totalHargaDiskon + " (Discount member 50%!!!)" +  "\nTanggal Terima  : " + tanggalTerima 
            + "\nTanggal Selesai : " + tanggalSelesai + "\nStatus      	:" + " Belum bisa diambil :(";
        }
        return "ID    : " + id + "\nPaket : " + paket + "\nHarga :\n" + berat + " kg x " + hargaPaketPerKg + " = " 
        + totalHarga + "\nTanggal Terima  : " + tanggalTerima + "\nTanggal Selesai : " + tanggalSelesai + "\nStatus      	:" + " Belum bisa diambil :(";  
    }
}
