public class Mahasiswa{
    private int idmhs;
    private String nama;
    private int nim;
    
    Mahasiswa(int idmhs, int nim, String nama){
        this.idmhs = idmhs;
        this.nim = nim;
        this.nama = nama;
    }
    
    public int getIdmhs(){
        return idmhs;
    }
    
    public int getNim(){
        return nim;
    }
    
    public String getNama(){
        return nama;
    }
}