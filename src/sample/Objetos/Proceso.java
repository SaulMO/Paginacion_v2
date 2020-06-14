package sample.Objetos;

public class Proceso
{
    private int PID;
    private int tamano;
    private char estado;

    public Proceso(int PID,int tamano,char estado){
        this.estado = estado;
        this.PID = PID;
        this.tamano = tamano;
    }

    public char getEstado(){ return estado; }
    public void setEstado(char estado){
        //x ejecucion
        //s swapping
        //f salida
        this.estado = estado;
    }
    public int getPID(){ return PID; }
    public int getTamano(){ return tamano; }
    public void setTamano(int tamano){
        this.tamano = tamano;
    }
}