package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class prueba extends Application
{
    Stage window;
    Label[] paginas = new Label[5];
    Label[][] celdas = new Label[10][5];

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("Simulador Método Paginación Memoria RAM");

        GridPane gp = new GridPane();
        gp.setPadding(new Insets(10, 10, 10, 10));
        gp.setGridLinesVisible(true);

        for (int i=0 ; i<5 ; i++)
        {
            paginas[i] = new Label();
            paginas[i].setText("jjjjjjjjjj");
            gp.add(paginas[i],0,i+1); //Agregamos Páginas
        }
        window.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}