public class KRS{
    private int idkrs;
    private int idmhs;
    private int idmk;
    private int semester;
    private int tahunajaran;
    
    KRS(int idkrs, int idmhs, int idmk, int semester, int tahunajaran){
        this.idkrs = idkrs;
        this.idmhs = idmhs;
        this.idmk = idmk;
        this.semester = semester;
        this.tahunajaran = tahunajaran;
    }
    
    public int getIdkrs(){
        return idkrs;
    }
    
    public int getIdmhs(){
        return idmhs;
    }
    
    public int getIdmk(){
        return idmk;
    }
    
    public int getSemester(){
        return semester;
    }
    
    public int getTahunajaran(){
        return tahunajaran;
    }
}