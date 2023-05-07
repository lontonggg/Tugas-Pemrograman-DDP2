package assignments.assignment3.user.menu;
import assignments.assignment3.user.Member;
import assignments.assignment3.nota.*;
import assignments.assignment3.nota.service.AntarService;
import assignments.assignment3.nota.service.CuciService;
import assignments.assignment3.nota.service.LaundryService;
import assignments.assignment3.nota.service.SetrikaService;
import assignments.assignment1.*;

public class MemberSystem extends SystemCLI {
    /**
     * Memproses pilihan dari Member yang masuk ke sistem ini sesuai dengan menu specific.
     *
     * @param choice -> pilihan pengguna.
     * @return true jika user log.
     */
    @Override
    protected boolean processChoice(int choice, Member member) {
        boolean logout = false;
        if(choice == 1){
            // Memunculkan paket laundry yang tersedia
            System.out.println("Masukkan paket laundry:");
            System.out.println("+-------------Paket-------------+");
            System.out.println("| Express | 1 Hari | 12000 / Kg |");
            System.out.println("| Fast    | 2 Hari | 10000 / Kg |");
            System.out.println("| Reguler | 3 Hari |  7000 / Kg |");
            System.out.println("+-------------------------------+");
            String paket = in.nextLine();

            // Menggunakan method dari TP1 untuk memvalidasi angka
            System.out.println("Masukkan berat cucian Anda [Kg]"); 
            String beratCucian = NotaGenerator.validasiAngka("Validasi Berat", "Harap masukkan berat cucian Anda dalam bentuk bilangan positif."); // Mengambil input berat dan memvalidasinya
            int berat = Integer.parseInt(beratCucian);

            // JIka berat kurang dari 2
            if(berat < 2){
                berat = 2;
                System.out.println("Cucian kurang dari 2 kg, maka cucian akan dianggap sebagai 2 kg");
            }

            // Membuat instance baru untuk Nota
            Nota newNota = new Nota(member, berat, paket, NotaManager.fmt.format(NotaManager.cal.getTime()));

            // Membuat interface baru untuk service cuci
            LaundryService cuciService = new CuciService();
            newNota.addService(cuciService);

            // Service setrika
            System.out.println("Apakah kamu ingin cucianmu disetrika oleh staff professional kami?");
            System.out.println("Hanya tambah 1000 / kg");
            System.out.print("[Ketik x untuk tidak mau]: ");
            String setrika = in.nextLine();
            if(setrika.equalsIgnoreCase("x") == false){ // Jika input bukan x, maka akan menambahkan interface service
                LaundryService serviceSetrika = new SetrikaService();
                newNota.addService(serviceSetrika);
            }

            // Service antar
            System.out.println("Mau diantar oleh kurir kami? Dijamin aman dan cepat sampai tujuan!");
            System.out.println("Cuma 2000 / 4kg, kemudian 500 / kg");
            System.out.print("[Ketik x untuk tidak mau]: ");
            String antar = in.nextLine();
            if(antar.equalsIgnoreCase("x") == false){ // Jika input bukan x, maka akan menambahkan interface service
                LaundryService serviceAntar = new AntarService();
                newNota.addService(serviceAntar);
            }

            // Menambahkan nota ke array milik member dan juga pada array di notamanager
            member.addNota(newNota);
            NotaManager.addNota(newNota);
            System.out.println("Nota berhasil dibuat!\n");
        }
        else if(choice == 2){
            for(Nota nota: member.getNotaList()){ // Print setiap nota milik member
                System.out.println(nota);
            }
        }
        else if(choice == 3){
            logout = true; // Logout
        }
        return logout;
    }

    /**
     * Displays specific menu untuk Member biasa.
     */
    @Override
    protected void displaySpecificMenu() {
        System.out.println("1. Saya ingin laundry");
        System.out.println("2. Lihat detail nota saya");
        System.out.println("3. Logout");
    }

    /**
     * Menambahkan Member baru ke sistem.
     *
     * @param member -> Member baru yang akan ditambahkan.
     */
    public void addMember(Member member) { // Method untuk menambah member barau ke SystemCLI
        Member[] newArray = new Member[memberList.length + 1]; // Membuat array baru dengan panjang lebih 1
        System.arraycopy(memberList, 0, newArray, 0, memberList.length); // Membuat copy dari array sebelumnya
        newArray[newArray.length-1] = member; 
        memberList = newArray;
    }
}