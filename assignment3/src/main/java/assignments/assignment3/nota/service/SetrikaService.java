package assignments.assignment3.nota.service;

public class SetrikaService implements LaundryService{
    private int counter = 0;
    @Override
    public String doWork() {
        counter++; // Menambahkan counter setiap dikerjakan
        return "Sedang menyetrika...";
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
        return berat * 1000; // Menghitung harga service
    }

    @Override
    public String getServiceName() {
        return "Setrika";
    }
}
