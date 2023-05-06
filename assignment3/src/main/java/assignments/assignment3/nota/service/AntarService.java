package assignments.assignment3.nota.service;

import assignments.assignment3.nota.Nota;

public class AntarService implements LaundryService{
    private int counter = 0;
    @Override
    public String doWork() {
        // TODO
        counter += 1;
        return "Sedang mengantar...";
    }

    @Override
    public boolean isDone() {
        // TODO
        if(counter > 0){
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public long getHarga(int berat) {
        // TODO
        int totalHarga = berat * 500;
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
