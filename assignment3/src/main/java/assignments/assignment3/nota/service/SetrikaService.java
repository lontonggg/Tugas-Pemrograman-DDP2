package assignments.assignment3.nota.service;

public class SetrikaService implements LaundryService{
    private int counter = 0;
    @Override
    public String doWork() {
        // TODO
        counter++;
        return "Sedang menyetrika...";
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
        return berat * 1000;
    }

    @Override
    public String getServiceName() {
        return "Setrika";
    }
}
