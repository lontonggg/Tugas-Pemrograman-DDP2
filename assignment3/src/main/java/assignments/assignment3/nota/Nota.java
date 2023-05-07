package assignments.assignment3.nota;
import assignments.assignment3.nota.service.LaundryService;
import assignments.assignment3.user.Member;
import static assignments.assignment3.nota.NotaManager.fmt;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class Nota {
    private Member member;
    private String paket;
    private LaundryService[] services = new LaundryService[0];
    private long baseHarga;
    private long totalHarga;
    private int sisaHariPengerjaan;
    private int estimasiTanggalSelesai;
    private int berat;
    private int id;
    private String tanggalMasuk;
    private String tanggalSelesai;
    private boolean isDone;
    private boolean isLate;
    static public int totalNota;

    // Constructor untuk Nota
    public Nota(Member member, int berat, String paket, String tanggal) {
        this.member = member;
        this.berat = berat;
        this.paket = paket;
        this.tanggalMasuk = tanggal;
        this.id = totalNota;
        this.totalHarga = this.calculateHarga();
        totalNota++;

        // Menentukan harga per kg berdasarkan paket
        if(this.paket.equalsIgnoreCase("Express")){
            this.baseHarga = 12000;
            this.estimasiTanggalSelesai = 1;
            this.sisaHariPengerjaan = 1;
        }
        else if(this.paket.equalsIgnoreCase("Fast")){
            this.baseHarga = 10000;
            this.estimasiTanggalSelesai = 2;
            this.sisaHariPengerjaan = 2;
        }
        else if(this.paket.equalsIgnoreCase("Reguler")){
            this.baseHarga = 7000;
            this.estimasiTanggalSelesai = 3;
            this.sisaHariPengerjaan = 3;
        }

    }

     // Method untuk menambahkan service untuk setiap nota
    public void addService(LaundryService service){
        LaundryService[] newArray = new LaundryService[this.services.length + 1]; // Membuat array baru dengan panjang lebih 1
        System.arraycopy(this.services, 0, newArray, 0, this.services.length); // Membuat copy dari array sebelumnya
        newArray[newArray.length-1] = service; 
        this.services = newArray;
        this.totalHarga = this.calculateHarga(); // Mengkalkulasi ulang harga dengan service tambahan
    }

    // Method untuk mengerjakan nota
    public String kerjakan(){ 
        for(LaundryService service: this.services){
            if(service.isDone() == false){
                return "Nota " + this.getId() + " : " + service.doWork(); // Mengerjakan service yang belum dikerjakan
            }
        }
        this.isDone = true;
        return "Nota " + this.getId() + " : " + "Sudah selesai."; // Jika semua service sudah dikerjakan
    }

    // Method untuk mengupdate Nota setelah 1 hari
    public void toNextDay() {
        boolean servicesDone = false; // Counter untuk melihat apakah semua services sudah selesai dikerjakan
        for(LaundryService service: this.services){
            isDone = service.isDone();
        }
        if(servicesDone){
            this.isDone = true;
        }
        if(this.isDone() == false){ // Jika services belum selesai
            this.sisaHariPengerjaan --;
            if(this.sisaHariPengerjaan < 0){ // Jika melewati waktu pengambilan, maka terlambat
                this.isLate = true;
                this.totalHarga = this.calculateHarga(); // Mengkalkulasi ulang harga dengan kompensasi keterlambatan
            }
        }
    }

    // Method untuk mengkalkulasi harga
    public long calculateHarga(){
        long totalService = 0;
        for(LaundryService service: this.services){
            totalService += service.getHarga(berat); // Method untuk menghitung harga services
        }
        totalHarga = (this.baseHarga * berat) + totalService; // Menghitung total harga
        if(this.isLate()){ // Jika terlambat
            totalHarga -= (this.sisaHariPengerjaan*-1 * 2000); // Kompensasi keterlambatan
        }
        if(this.totalHarga < 0){ // Jika terlambat, harga paling kecil adalah 0
            totalHarga = 0;
        }
        return totalHarga;
    }

    // Method untuk cek status nota apakah sudah selesai dikerjakan atau belum
    public String getNotaStatus(){
        if(this.isDone()){
            return "Nota " + this.getId() + " : Sudah selesai.";
        }
        else{
            return "Nota " + this.getId() + " : Belum selesai.";
        } 
    }

    @Override
    public String toString(){
        try {
            Date tanggalTerimaFormatted = fmt.parse(tanggalMasuk); // Mengubah tanggal masuk ke dalam bentuk object Date
            Calendar tanggalSelesaiCalendar = Calendar.getInstance(); 
            tanggalSelesaiCalendar.setTime(tanggalTerimaFormatted); 
            tanggalSelesaiCalendar.add(Calendar.DAY_OF_YEAR, estimasiTanggalSelesai); // Menambahkan hari dari waktu terima sesuai dengan waktu pengerjaan
            Date tanggalSelesaiFormatted = tanggalSelesaiCalendar.getTime();
            this.tanggalSelesai = fmt.format(tanggalSelesaiFormatted); // Ubah kedalam bentuk format
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String mainMessage = "[ID Nota = " + this.id + "]"
        + "\nID    : " + member.getId()
        + "\nPaket : " + this.getPaket()
        + "\nHarga : "
        + "\n" + this.getBerat() + " kg x " + this.baseHarga + " = " + (this.baseHarga*this.getBerat())
        + "\ntanggal terima  : " + this.tanggalMasuk
        + "\ntanggal selesai : " + this.tanggalSelesai
        + "\n--- SERVICE LIST ---"
        + "\n-Cuci @ Rp.0";
        for(LaundryService service: this.services){ // Menambahkan services
            if(service.getServiceName().equalsIgnoreCase("Cuci") == false){
                mainMessage += "\n-" + service.getServiceName() + " @ Rp." + service.getHarga(berat);
            }
        }
        mainMessage += "\nHarga Akhir: " + this.calculateHarga(); // Menghitung total harga
        if(this.isLate()){ // Jika terjadi keterlambatan
            mainMessage += " Ada kompensasi keterlambatan " + this.sisaHariPengerjaan * -1 + " * 2000 hari\n";
        }
        else{
            mainMessage += "\n";
        }
        return mainMessage;
    }

    // Getter methods
    public String getPaket() {
        return paket;
    }

    public int getBerat() {
        return berat;
    }

    public String getTanggal() {
        return tanggalMasuk;
    }

    public int getSisaHariPengerjaan(){
        return sisaHariPengerjaan;
    }
    public boolean isDone() {
        return isDone;
    }

    public LaundryService[] getServices(){
        return services;
    }

    public int getId(){
        return this.id;
    }

    public boolean isLate(){
        return this.isLate;
    }
}
