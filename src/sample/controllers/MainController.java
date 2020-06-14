package sample.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import sample.Main;
import sample.Objetos.Memoria;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable
{
    @FXML private TextField textFieldTamano;
    @FXML private ComboBox comboBoxPaginas;
    @FXML private ComboBox comboBoxMarcos;
    @FXML private Button btnStart;

    public static Memoria memoria; //Guardamos los valores inciales de la memoria
    private byte paginas;
    private int tamanoMemoria;
    private byte marcos;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        comboBoxPaginas.getItems().addAll(4,6,8,10,12,14,16);
        comboBoxPaginas.setValue(4);
        comboBoxMarcos.getItems().addAll(1,2,4,6,8);
        comboBoxMarcos.setValue(2);
        btnStart.setOnAction(evento);
    }

    private boolean comprobardatos(){
        boolean bandera = false;
        if ((textFieldTamano.getText().equals(""))) {
            alertaDatosFaltantes();
            bandera = false;
        } else {
            try {
                tamanoMemoria = Integer.parseInt(textFieldTamano.getText());
                paginas = Byte.parseByte(String.valueOf(comboBoxPaginas.getValue()));
                marcos = Byte.parseByte(String.valueOf(comboBoxMarcos.getValue()));
                bandera = true;
                if (tamanoMemoria<1024){
                    alertaNumeroIncorrecto();
                    bandera = false;
                }
            } catch (NumberFormatException e) {
                bandera = false;
                alertaNumeroIncorrecto();
            }
        }
        return bandera;
    }

    EventHandler<ActionEvent> evento = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            if (comprobardatos()) {
                memoria = new Memoria(tamanoMemoria,paginas,marcos);
                try {
                    Stage stage = Main.stage;
                    Parent root = FXMLLoader.load(getClass().getResource("../fxmlFiles/proceso.fxml"));
                    Scene scene = new Scene(root,1000,600);
                    stage.setTitle("Simulador Método Paginación Memoria RAM");
                    stage.setResizable(false);
                    stage.setScene(scene);
                    stage.show();
                }catch (IOException e){ e.printStackTrace(); }
            }
        }
    };

    private void alertaDatosFaltantes(){
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Error");
        alerta.setContentText("Datos Faltantes");
        alerta.showAndWait();
    }
    private void alertaNumeroIncorrecto(){
        Alert alerta = new Alert(Alert.AlertType.ERROR);
        alerta.setTitle("Error");
        alerta.setHeaderText("Error al ingresar datos");
        alerta.setContentText("Memoria [1024-2147483600) Pagina (4,6,8,10)");
        alerta.showAndWait();
    }
}