package assignments.assignment3.user;

import assignments.assignment3.nota.Nota;
public class Member {
    protected String id;
    protected String password;
    protected String nama;
    protected Nota[] notaList = new Nota[0];

    // Constructor object member
    public Member(String nama, String id, String password) {
        this.nama = nama;
        this.id = id;
        this.password = password;
    }

    /**
     * Method otentikasi member dengan ID dan password yang diberikan.
     *
     * @param id -> ID anggota yang akan diautentikasi.
     * @param password -> password anggota untuk mengautentikasi.
     * @return true jika ID dan password sesuai dengan instance member, false jika tidak.
     */
    public boolean login(String id, String password) {
        return id.equals(this.id) && authenticate(password);
    }

    /**
     * Menambahkan nota baru ke NotaList instance member.
     *
     * @param nota Nota object untuk ditambahkan.
     */
    public void addNota(Nota nota) { // Method untuk menambahkan nota ke array
        Nota[] newArray = new Nota[this.notaList.length + 1]; // Membuat array baru dengan panjang lebih 1
        System.arraycopy(this.notaList, 0, newArray, 0, this.notaList.length); // Membuat copy dari array sebelumnya
        newArray[newArray.length-1] = nota; 
        this.notaList = newArray;
    }

    /**
     * Method otentikasi member dengan password yang diberikan.
     *
     * @param password -> sandi password anggota untuk mengautentikasi.
     * @return true jika ID dan password sesuai dengan instance member, false jika tidak.
     */
    protected boolean authenticate(String password) {
        if(password.equals(this.password)){ // Jika password yang di input sesuai dengan password member
            return true;
        } else{
            return false;
        }
    }

    // Getter methods
    public String getNama() {
        return nama;
    }

    public String getId() {
        return id;
    }

    public Nota[] getNotaList() {
        return notaList;
    }
}