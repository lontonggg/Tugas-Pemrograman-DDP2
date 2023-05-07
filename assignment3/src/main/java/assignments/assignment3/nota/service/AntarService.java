package assignments.assignment3.nota.service;

public class AntarService implements LaundryService{
    private int counter = 0;

    @Override
    public String doWork() {
        counter += 1; // Menambahkan counter
        return "Sedang mengantar...";
    }

    @Override
    public boolean isDone() {
        if(counter > 0){ // Jika sudah pernah dikerjakan minimal sekali
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public long getHarga(int berat) {
        int totalHarga = berat * 500; // Menghitung harga dari service
        if(totalHarga < 2000){
            totalHarga = 2000;
        }
        return totalHarga;
    }

    @Override
    public String getServiceName() {
        return "Antar";
    }
}
