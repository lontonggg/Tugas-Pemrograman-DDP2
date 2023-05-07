package assignments.assignment3.nota.service;

public class CuciService implements LaundryService{
    private int counter = 0;
    @Override
    public String doWork() {
        counter++; // Menambahkan counter setelah dikerjakan
        return "Sedang mencuci...";
    }

    @Override
    public boolean isDone() {
        if(counter > 0){ // JIka telah dikerjakan minimal sekali
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public long getHarga(int berat) { // Harga untuk cuci selalu 0
        return 0;
    }

    @Override
    public String getServiceName() {
        return "Cuci";
    }
}
