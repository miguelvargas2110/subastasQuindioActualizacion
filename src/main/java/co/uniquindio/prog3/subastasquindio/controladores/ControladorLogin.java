package co.uniquindio.prog3.subastasquindio.controladores;

import co.uniquindio.prog3.subastasquindio.aplicacion.Aplicacion;
import co.uniquindio.prog3.subastasquindio.excepciones.ExcepcionUsuario;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import static co.uniquindio.prog3.subastasquindio.persistencia.Persistencia.iniciarSesion;

public class ControladorLogin {

    Aplicacion aplicacion = new Aplicacion();
    @FXML
    TextField correoUsuario;
    @FXML
    TextField contrasenaUsuario;
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void onActionLogin() {

        try {
            ControladorModelFactory.getInstance().getSubastasQuindio().setUsuarioGlobalAnunciante(null);
            ControladorModelFactory.getInstance().getSubastasQuindio().setUsuarioGlobalComprador(null);
            iniciarSesion(correoUsuario.getText(), contrasenaUsuario.getText());
            ControladorModelFactory.getInstance().usuarioLogeado(correoUsuario.getText(), contrasenaUsuario.getText());
            ControladorModelFactory.getInstance().guardarRegistroLog("El usuario con correo " + correoUsuario.getText() + " ha iniciado sesion", 1, "iniciarSesion");

            ControladorModelFactory.getInstance().getSubastasQuindio().getStageMenu1().close();
            stage.close();
            aplicacion.start(ControladorModelFactory.getInstance().subastasQuindio.getStageMenu1());

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ExcepcionUsuario e) {
            ControladorModelFactory.getInstance().guardarRegistroLog("El usuario no esta registrado", 2, e.toString());
        }
    }
}
