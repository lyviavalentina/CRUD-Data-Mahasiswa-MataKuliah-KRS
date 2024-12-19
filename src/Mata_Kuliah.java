public class Mata_Kuliah{
        private int idmk;
    private String nama;
    
    Mata_Kuliah(int idmk, String nama){
        this.idmk = idmk;
        this.nama = nama;
    }
        
    public int getIdmk(){
        return idmk;
    }
    
    public String getNama(){
        return nama;
    }
}