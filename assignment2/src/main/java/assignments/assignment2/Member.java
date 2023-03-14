package assignments.assignment2;

public class Member {
    // Variable untuk tiap instances member
    private String nama, noHp, id; 
    private int bonusCounter;

    public Member(String nama, String noHp) { // Constructor class member
        this.nama = nama;
        this.noHp = noHp;
        this.id = generateId(this.nama, this.noHp);
    }
    public String getNama(){ // Method yang mengambil nama member
        return this.nama;
    }
    public String getNoHp(){ // Method yang mengambil no HP member
        return this.noHp;
    }
    public String getId(){ // Method untuk mengambil ID member
        return this.id;
    }
    public int getBonusCounter(){ // Method untuk mengambil bonus counter member
        return this.bonusCounter;
    }
    public void increaseBonus(){ // Method untuk menambah bonus counter member
        this.bonusCounter += 1;
    }
    public void resetBonus(){ // Method untuk mereset bonus counter member
        this.bonusCounter = 0;
    }
    // Method untuk membuat ID member
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
}