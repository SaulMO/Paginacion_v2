package sample.Objetos;

public class Memoria
{
    private byte paginas;
    private int memoriaTamano;
    private byte marcos;

    public Memoria(int memoriaTamano,byte paginas,byte usoMarcos) {
        this.paginas = paginas;
        this.memoriaTamano = memoriaTamano;
        this.marcos = usoMarcos;
    }

    public byte getPaginas() { return paginas; }
    public int getMemoriaTamano() { return memoriaTamano; }
    public byte getMarcos() { return marcos; }
}
