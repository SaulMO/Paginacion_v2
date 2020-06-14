package sample.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import sample.Main;
import sample.Objetos.Marco;
import sample.Objetos.Memoria;
import sample.Objetos.Proceso;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ProcesoController implements Initializable
{
    @FXML Button btnSalir,btnGenerarProceso,btnSacarProceso;
    @FXML TextField txtProceso;
    @FXML ComboBox comboSacarProceso;
    @FXML GridPane tabla;
    @FXML TextArea txtAreaSwapp;
    @FXML Label lblFragmentacion;

    Label[] paginas;
    Label[][] celdas;
    Marco[] marcos;
    int tamanoMarco;
    Memoria memoria;
    ArrayList<Proceso> procesos;
    int tamanoTemporalProceso;
    int pidProcesoTemp;
    double fragmentacionMemoria;
    int[] colorMarco = {95,90,85,80,75,70,65,60};


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        iniciarComponentes();
        hacerTabla();
    }
    //Inicializa componentes necesarios para la función de la simulación
    private void iniciarComponentes(){
        //Swapping
        txtAreaSwapp.setEditable(false);
        txtAreaSwapp.appendText("Proceso\t\tTamaño\n\n");
        //Variables de La Tabla
        memoria = MainController.memoria;
        paginas = new Label[(int)memoria.getPaginas()];
        tamanoMarco = ((memoria.getMemoriaTamano() / (memoria.getPaginas()*memoria.getMarcos())));
        celdas = new Label[memoria.getPaginas()*memoria.getMarcos()][5];
        marcos = new Marco[memoria.getPaginas()*memoria.getMarcos()];
        //Controllar la tabla
        procesos = new ArrayList<>();
        btnGenerarProceso.setOnAction(evento);
        btnSacarProceso.setOnAction(evento);
        btnSalir.setOnAction(evento);
    }
    //Hace la tabla con un par de arreglos
    private void hacerTabla(){
        pidProcesoTemp = 1;
        byte colorMarco;
        byte contMarco = 0;
        String estiloPagina = ("-fx-text-fill: black;" +
                "-fx-background-color: #EB984E;" +
                "-fx-border-color: #000000");
        String estiloMarco;
        //marco,id,tamaño,uso,libre
        for (int i=0 ; i<memoria.getPaginas() ; i++) {
            paginas[i] = new Label("Pag. " + (i + 1));
            paginas[i].setStyle(estiloPagina);
            paginas[i].setAlignment(Pos.CENTER);
            paginas[i].setMinWidth(80);
            paginas[i].setMinHeight(memoria.getMarcos()*17);
            tabla.add(paginas[i], 0, ((i * memoria.getMarcos()) + 1), 1, memoria.getMarcos()); //Agregamos Páginas
            colorMarco = 95;
            for (int j = 0; j < memoria.getMarcos(); j++) {
                estiloMarco = ("-fx-text-fill: black;" +
                        "-fx-background-color: hsb(204,8%,"+colorMarco+"%)");
                marcos[contMarco] = new Marco((byte)(i+1),(byte)(j+1),null,tamanoMarco,0,tamanoMarco);
                celdas[contMarco][0] = new Label("M" + (j + 1)); //Marcos
                celdas[contMarco][0].setStyle(estiloMarco);
                celdas[contMarco][0].setAlignment(Pos.CENTER);
                celdas[contMarco][0].setMinWidth(80);
                celdas[contMarco][1] = new Label("null");  //ID
                celdas[contMarco][1].setStyle(estiloMarco);
                celdas[contMarco][1].setAlignment(Pos.CENTER);
                celdas[contMarco][1].setMinWidth(80);
                celdas[contMarco][2] = new Label(tamanoMarco+""); //TAMANO
                celdas[contMarco][2].setStyle(estiloMarco);
                celdas[contMarco][2].setAlignment(Pos.CENTER);
                celdas[contMarco][2].setMinWidth(80);
                celdas[contMarco][3] = new Label("0"); //Uso
                celdas[contMarco][3].setStyle(estiloMarco);
                celdas[contMarco][3].setAlignment(Pos.CENTER);
                celdas[contMarco][3].setMinWidth(80);
                celdas[contMarco][4] = new Label(tamanoMarco+"");  //libre
                celdas[contMarco][4].setStyle(estiloMarco);
                celdas[contMarco][4].setAlignment(Pos.CENTER);
                celdas[contMarco][4].setMinWidth(80);
                colorMarco = (byte) (colorMarco - 5);
                //Añadirlos a la tabla
                tabla.add(celdas[contMarco][0], 1, (contMarco+1)); //Agregamos marcos
                tabla.add(celdas[contMarco][1], 2, (contMarco+1)); //Agregamos id
                tabla.add(celdas[contMarco][2], 3, (contMarco+1)); //Agregamos tamano
                tabla.add(celdas[contMarco][3], 4, (contMarco+1)); //Agregamos uso
                tabla.add(celdas[contMarco][4], 5, (contMarco+1)); //Agregamos libre
                contMarco++;
            }
        }
    }
    //Valida si el proceso que se va a ingresar es correcto
    private void actualizarComboBox(){
        comboSacarProceso.getItems().removeAll();
        comboSacarProceso.getItems().clear();
        for (int i=0 ; i<procesos.size() ; i++){
            if (procesos.get(i).getEstado() == 'x')
                comboSacarProceso.getItems().add(procesos.get(i).getPID());
        }
    }
    //Alerta cuando no se ingresa correctamente un proceso
    private void alertaProcesoIncorrecto(){
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Error");
        alerta.setContentText("NO SE HA INGRESADO CORRECTAMENTE EL PROCESO");
        alerta.showAndWait();
    }
    //Verificar si el proceso se ingreso correctamente
    private boolean validarYCrearProceso(){
        boolean validacion;
        try{
            tamanoTemporalProceso = Integer.parseInt(txtProceso.getText());
            validacion = true;
        }catch(NumberFormatException e){
            validacion = false;
            alertaProcesoIncorrecto();
        }
        if (validacion){
            byte contMarcosLibres = 0;
            for (int i=0 ; i<marcos.length ; i++){
                if (marcos[i].esLibre())
                    contMarcosLibres++;
            }
            if (tamanoTemporalProceso <= (contMarcosLibres*tamanoMarco))
                procesos.add(new Proceso(pidProcesoTemp,tamanoTemporalProceso,'x'));
            else
                procesos.add(new Proceso(pidProcesoTemp,tamanoTemporalProceso,'s'));
            pidProcesoTemp++;
        }
        return validacion;
    }
    //Mete el proceso a tabla si el proceso cabe
    private void meterProcesoATabla(Proceso proceso){
        int tamanoTemp = proceso.getTamano();
        boolean aunNoEntra = true;
        byte recorrerMarco = 0;
        while(aunNoEntra){
            if (marcos[recorrerMarco].esLibre()){
                if (tamanoMarco >= proceso.getTamano()){
                    marcos[recorrerMarco].setProceso(proceso);
                    marcos[recorrerMarco].setUso(proceso.getTamano());
                    marcos[recorrerMarco].setLibre(tamanoMarco - proceso.getTamano());
                    aunNoEntra = false;
                }
                if (tamanoMarco < proceso.getTamano()){
                    tamanoTemp = proceso.getTamano() - tamanoMarco;
                    proceso.setTamano(tamanoMarco);
                    marcos[recorrerMarco].setUso(tamanoMarco);
                    marcos[recorrerMarco].setLibre(0);
                    marcos[recorrerMarco].setProceso(proceso);
                    proceso.setTamano(tamanoTemp);
                }
                celdas[recorrerMarco][1].setText("P"+marcos[recorrerMarco].getProceso().getPID());  //PID
                celdas[recorrerMarco][3].setText(marcos[recorrerMarco].getUso() + "");  //Uso
                celdas[recorrerMarco][4].setText(marcos[recorrerMarco].getLibre() + "");  //Libre
            }
            recorrerMarco++;
        }
        actualizarComboBox();
        sacarFragmentacion();
    }
    private void meterProcesoASwapping(Proceso proceso){
        txtAreaSwapp.appendText("P"+proceso.getPID()+"\t\t\t"+proceso.getTamano()+"\n");
    }
    private void sacarProceso(int PID){
        procesos.get(PID-1).setEstado('f');
        actualizarComboBox();
        for (int i=0 ; i<marcos.length ; i++){
            if (!marcos[i].esLibre()){
                if (marcos[i].getProceso().getPID() == PID){
                    marcos[i].setLibre(tamanoMarco);
                    marcos[i].setUso(0);
                    marcos[i].setProceso(null);

                    celdas[i][1].setText(marcos[i].getProceso()+"");
                    celdas[i][3].setText(marcos[i].getUso()+"");
                    celdas[i][4].setText(marcos[i].getLibre()+"");
                }
            }
        }
        sacarFragmentacion();
        meterDeSwapping();
    }
    private void meterDeSwapping(){
        int primerProcesoSwapping = -1;
        byte contMarcosLibres = 0;
        for (int i=0 ; i<procesos.size() ; i++){
            if (procesos.get(i).getEstado() == 's'){
                primerProcesoSwapping = i;
                i = procesos.size();
            }
        }
        if (primerProcesoSwapping>0){
            for (int i=0 ; i<marcos.length ; i++){
                if (marcos[i].esLibre())
                    contMarcosLibres++;
            }
            if (procesos.get(primerProcesoSwapping).getTamano() <= (contMarcosLibres*tamanoMarco)){
                procesos.get(primerProcesoSwapping).setEstado('x');
                meterProcesoATabla(procesos.get(primerProcesoSwapping));
                txtAreaSwapp.setText("");
                txtAreaSwapp.appendText("Proceso\t\tTamaño\n\n");
                for (int i=0 ; i<procesos.size() ; i++){
                    if (procesos.get(i).getEstado() == 's'){
                        txtAreaSwapp.appendText("P"+procesos.get(i).getPID()+"\t\t\t"+procesos.get(i).getTamano()+"\n");
                    }
                }
            }
        }

    }
    private void sacarFragmentacion(){
        fragmentacionMemoria = 0;
        for (int i=0 ; i< marcos.length ; i++){
            if (!marcos[i].esLibre()){
                if (marcos[i].getLibre() > 0){
                    fragmentacionMemoria = fragmentacionMemoria + marcos[i].getLibre();
                }
            }
        }
        lblFragmentacion.setText("Fragmentación = "+obtenerFragmentacion()+"%");
    }
    private double obtenerFragmentacion(){
        return ( (fragmentacionMemoria*100)/memoria.getMemoriaTamano() );
    }

    EventHandler<ActionEvent> evento = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            if (event.getSource() == btnSalir){
                try {
                    Stage stage = Main.stage;
                    Parent root = null;
                    root = FXMLLoader.load(getClass().getResource("../fxmlFiles/main.fxml"));
                    Scene scene = new Scene(root,1000,600);
                    stage.setTitle("Simulador Método Paginación Memoria RAM");
                    stage.setResizable(false);
                    stage.setScene(scene);
                    stage.show();
                }catch(IOException e){ e.printStackTrace(); }
            }
            if (event.getSource() == btnGenerarProceso){
                if (validarYCrearProceso()){
                    if (  procesos.get(procesos.size()-1).getEstado() == 'x')
                        meterProcesoATabla( procesos.get(procesos.size()-1) );
                    if ( procesos.get(procesos.size()-1).getEstado() == 's')
                        meterProcesoASwapping( procesos.get(procesos.size()-1) );
                }
            }
            if (event.getSource() == btnSacarProceso){
                sacarProceso((int)comboSacarProceso.getValue());
            }
        }
    };
}