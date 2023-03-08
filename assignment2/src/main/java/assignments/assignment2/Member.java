package assignments.assignment2;

import assignments.assignment1.NotaGenerator;

public class Member {
    private String nama, noHp, id;
    private int bonusCounter;

    // TODO: tambahkan attributes yang diperlukan untuk class ini
    public Member(String nama, String noHp) {
        // TODO: buat constructor untuk class ini
        this.nama = nama;
        this.noHp = noHp;
        this.id = generateId(this.nama, this.noHp);
    }

    // TODO: tambahkan methods yang diperlukan untuk class ini
    public String getNama(){
        return this.nama;
    }

    public String getNoHp(){
        return this.noHp;
    }

    public String getId(){
        return this.id;
    }

    public int getBonusCounter(){
        return this.bonusCounter;
    }

    public void increaseBonus(){
        this.bonusCounter += 1;
    }

    public static String generateId(String nama, String nomorHP){
        String[] namaSplitted = nama.split(" ");
        String namaDepan = namaSplitted[0]; // Mengambil nama depan
        int checksum = 0;
        String gabunganNamaHP = namaDepan.toUpperCase() + "-" + nomorHP; // Menggabung nama dan no HP menjadi [nama]-[no HP]
        for(int i = 0; i < gabunganNamaHP.length(); i++){ // Mengiterasi string gabungan nama dan no HP
            if(gabunganNamaHP.charAt(i) >= 65 && gabunganNamaHP.charAt(i) <= 90){ // Jika character berupa huruf
                checksum += gabunganNamaHP.charAt(i) - 'A' + 1;
            }
            else if(Character.isDigit(gabunganNamaHP.charAt(i))){ // Jika character berupa angka
                checksum += Character.getNumericValue(gabunganNamaHP.charAt(i));
            }
            else{ // Jika character bukan huruf maupun angka
                checksum += 7;
            }
        }
        return String.format("%s-%02d", gabunganNamaHP, checksum % 100);
    }

    public String toString(){
        return this.id;
    }
}