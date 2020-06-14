package sample.Objetos;

public class Marco
{
    private byte pagina;
    private byte marco;
    private int tamano;
    private int uso;
    private int libre;
    private Proceso proceso;

    public Marco(byte pagina, byte marco, Proceso proceso, int tamano, int uso, int libre) {
        this.pagina = pagina;
        this.marco = marco;
        this.tamano = tamano;
        this.uso = uso;
        this.libre = libre;
        this.proceso = proceso;
    }

    public byte getPagina() { return pagina; }
    public byte getMarco() { return marco; }
    public int getTamano() { return tamano; }
    public int getUso() { return uso; }
    public int getLibre() { return libre; }
    public Proceso getProceso() {
        return proceso;
    }
    public boolean esLibre(){
        boolean bandera;
        if (proceso == null)
            bandera = true;
        else
            bandera = false;
        return bandera;
    }

    public void setUso(int uso) {
        this.uso = uso;
    }
    public void setLibre(int libre) {
        this.libre = libre;
    }
    public void setProceso(Proceso proceso) {
        this.proceso = proceso;
    }
}