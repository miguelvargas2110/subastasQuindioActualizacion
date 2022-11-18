package co.uniquindio.prog3.subastasquindio.aplicacion;

import co.uniquindio.prog3.subastasquindio.controladores.ControladorLogin;
import co.uniquindio.prog3.subastasquindio.controladores.ControladorMenu1;
import co.uniquindio.prog3.subastasquindio.controladores.ControladorRegistro;
import co.uniquindio.prog3.subastasquindio.controladores.ControladorTransaccional;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Aplicacion extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Aplicacion.class.getResource("/vistas/Menu1.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Menu");
        stage.setScene(scene);
        ControladorMenu1 controladorMenu1 = fxmlLoader.getController();
        controladorMenu1.setStage(stage);
        stage.show();
    }

    public void Anuncio() {
        FXMLLoader fxmlLoader = new FXMLLoader(Aplicacion.class.getResource("/vistas/Anuncios.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Anuncios");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void Login() {
        FXMLLoader fxmlLoader = new FXMLLoader(Aplicacion.class.getResource("/vistas/Login.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Login");
            stage.setScene(scene);
            ControladorLogin controladorLogin = fxmlLoader.getController();
            controladorLogin.setStage(stage);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void Pujas() {
        FXMLLoader fxmlLoader = new FXMLLoader(Aplicacion.class.getResource("/vistas/Pujas.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Pujas");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void Registro() {
        FXMLLoader fxmlLoader = new FXMLLoader(Aplicacion.class.getResource("/vistas/Register.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Registro");
            stage.setScene(scene);
            ControladorRegistro controladorRegistro = fxmlLoader.getController();
            controladorRegistro.setStage(stage);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void Transaccional() {
        FXMLLoader fxmlLoader = new FXMLLoader(Aplicacion.class.getResource("/vistas/Transaccional.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load());
            Stage stage = new Stage();
            stage.setTitle("Transaccional");
            stage.setScene(scene);
            ControladorTransaccional controladorTransaccional = fxmlLoader.getController();
            controladorTransaccional.setStage(stage);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
