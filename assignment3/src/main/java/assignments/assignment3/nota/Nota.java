package assignments.assignment3.nota;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.print.attribute.standard.Severity;

import assignments.assignment3.nota.service.LaundryService;
import assignments.assignment3.user.Member;

import static assignments.assignment3.nota.NotaManager.cal;
import static assignments.assignment3.nota.NotaManager.fmt;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    public Nota(Member member, int berat, String paket, String tanggal) {
        //TODO
        this.member = member;
        this.berat = berat;
        this.paket = paket;
        this.tanggalMasuk = tanggal;
        this.id = totalNota;
        this.totalHarga = this.calculateHarga();
        totalNota++;

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

    public void addService(LaundryService service){
        //TODO
        LaundryService[] newArray = new LaundryService[this.services.length + 1]; // Membuat array baru dengan panjang lebih 1
        System.arraycopy(this.services, 0, newArray, 0, this.services.length); // Membuat copy dari array sebelumnya
        newArray[newArray.length-1] = service; 
        this.services = newArray;
        this.totalHarga = this.calculateHarga();
    }

    public String kerjakan(){
        for(LaundryService service: this.services){
            if(service.isDone() == false){
                return "Nota " + this.getId() + " : " + service.doWork();
            }
        }
        return "Nota " + this.getId() + " : " + "Sudah selesai.";
    }

    public void toNextDay() {
        // TODO
        boolean servicesDone = false;
        for(LaundryService service: this.services){
            servicesDone = service.isDone();
        }
        if(servicesDone){
            this.isDone = true;
        }
        else{
            this.sisaHariPengerjaan --;
            if(this.sisaHariPengerjaan < 0){
                this.isLate = true;
                this.totalHarga = this.calculateHarga();
            }
        }
    }

    public long calculateHarga(){
        // TODO
        long totalService = 0;
        for(LaundryService service: this.services){
            totalService += service.getHarga(berat);
        }
        
        totalHarga = (this.baseHarga * berat) + totalService;
        if(this.isLate()){
            totalHarga -= (this.sisaHariPengerjaan*-1 * 2000);
        }

        if(this.totalHarga < 0){
            totalHarga = 0;
        }

        return totalHarga;
    }

    public String getNotaStatus(){
        boolean isDone = false;
        for(LaundryService service: this.services){
            isDone = service.isDone();
        }
        if(isDone){
            return "Nota " + this.getId() + " : Sudah selesai.";
        }
        else{
            return "Nota " + this.getId() + " : Belum selesai.";
        } 
    }

    @Override
    public String toString(){
        // TODO
        try {
            Date tanggalTerimaFormatted = fmt.parse(tanggalMasuk);
            Calendar tanggalSelesaiCalendar = Calendar.getInstance();
            tanggalSelesaiCalendar.setTime(tanggalTerimaFormatted);
            tanggalSelesaiCalendar.add(Calendar.DAY_OF_YEAR, estimasiTanggalSelesai);
            Date tanggalSelesaiFormatted = tanggalSelesaiCalendar.getTime();
            this.tanggalSelesai = fmt.format(tanggalSelesaiFormatted);
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
        for(LaundryService service: this.services){
            if(service.getServiceName().equalsIgnoreCase("Cuci") == false){
                mainMessage += "\n-" + service.getServiceName() + " @ Rp." + service.getHarga(berat);
            }
        }
        mainMessage += "\nHarga Akhir: " + this.calculateHarga();
        if(this.isLate()){
            mainMessage += " Ada kompensasi keterlambatan " + this.sisaHariPengerjaan * -1 + " * 2000 hari";
        }
        else{
            mainMessage += "\n";
        }
        return mainMessage;
    }

    // Dibawah ini adalah getter
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
