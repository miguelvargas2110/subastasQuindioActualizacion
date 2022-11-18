package co.uniquindio.prog3.subastasquindio.controladores;

import co.uniquindio.prog3.subastasquindio.aplicacion.Aplicacion;
import co.uniquindio.prog3.subastasquindio.excepciones.ExcepcionPujaNegativa;
import co.uniquindio.prog3.subastasquindio.excepciones.ExcepcionUsuarioNoRegistrado;
import co.uniquindio.prog3.subastasquindio.modelo.Puja;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ControladorTransaccional implements Initializable {

    @FXML
    Label lblNombreAnunciante;

    @FXML
    Label lblNombreAnuncio;
    @FXML
    Label lblTipoProducto;
    @FXML
    Label lblFechaInicio;
    @FXML
    Label lblFechaFin;
    @FXML
    Label lblDescripcion;
    @FXML
    Label lblValorInicial;
    @FXML
    Label lblPuja;
    @FXML
    TextField txtValorPujar;
    @FXML
    ImageView imgView;
    @FXML
    TableView<Puja> tablaPujasGlobales;
    @FXML
    TableColumn columnaNombreComprador;
    @FXML
    TableColumn columnaValorPuja;
    ObservableList<Puja> pujas;
    Stage stage;
    Aplicacion aplicacion = new Aplicacion();

    @FXML
    private void Pujar() throws IOException {
        try {
            validarUsuarioLogueado();
            Double.parseDouble(txtValorPujar.getText());
            ControladorModelFactory.getInstance().validarValorPuja(Double.parseDouble(txtValorPujar.getText()), ControladorModelFactory.getInstance().getSubastasQuindio().getAnuncioGlobal().getValorInicial());
            Puja puja = ControladorModelFactory.getInstance().crearPuja(Double.parseDouble(txtValorPujar.getText()), ControladorModelFactory.getInstance().getSubastasQuindio().getAnuncioGlobal().getNombreAnuncio(), ControladorModelFactory.getInstance().getSubastasQuindio().getUsuarioGlobalComprador().getNombre(), ControladorModelFactory.getInstance().getSubastasQuindio().getAnuncioGlobal());
            ControladorModelFactory.getInstance().guardarPuja(puja, ControladorModelFactory.getInstance().getSubastasQuindio().getAnuncioGlobal().getNombreAnuncio());
            ControladorModelFactory.getInstance().guardarPujaArchivo(puja);
            lblPuja.setText("Se ha pujado exitosamente");
            ControladorModelFactory.getInstance().guardarRegistroLog("La puja del comprador " + ControladorModelFactory.getInstance().getSubastasQuindio().getUsuarioGlobalComprador().getNombre() + " con valor " + txtValorPujar.getText() + " se ha hecho exitosamente", 1, "guardarPuja");
            this.inicializarTabla();
        } catch (ExcepcionPujaNegativa excepcionPujaNegativa) {
            ControladorModelFactory.getInstance().guardarRegistroLog("Ha salado una excepcion de valor menor de valor inicial", 2, excepcionPujaNegativa.toString());
        } catch (NumberFormatException e) {
            ControladorModelFactory.getInstance().guardarRegistroLog("Ha saltado una excepcion  en el valor de la puja", 2, e.toString());
        } catch (ExcepcionUsuarioNoRegistrado e) {
            ControladorModelFactory.getInstance().guardarRegistroLog("El usuario no esta registrado e intento hacer una puja", 2, e.toString());
            Alert dialogoAlerta = new Alert(Alert.AlertType.CONFIRMATION);
            dialogoAlerta.setTitle("");
            dialogoAlerta.setHeaderText("");
            dialogoAlerta.initStyle(StageStyle.UTILITY);
            dialogoAlerta.setContentText("Usted no ha iniciado sesion\n¿Quiere iniciar sesion o registarse?");
            ButtonType btnIniciarSesion = new ButtonType("Iniciar Sesion");
            ButtonType btnRegistrarse = new ButtonType("Registrarse");
            ButtonType btnCancelar = new ButtonType("Cancelar");
            dialogoAlerta.getButtonTypes().setAll(btnRegistrarse, btnIniciarSesion, btnCancelar);
            Optional<ButtonType> opciones = dialogoAlerta.showAndWait();
            if (opciones.get() == btnRegistrarse) {
                aplicacion.Registro();
                stage.close();
                ControladorModelFactory.getInstance().getSubastasQuindio().getStageMenu1().close();
            } else if (opciones.get() == btnIniciarSesion) {
                aplicacion.Login();
                stage.close();
                ControladorModelFactory.getInstance().getSubastasQuindio().getStageMenu1().close();
            }
        }
    }

    private void validarUsuarioLogueado() throws ExcepcionUsuarioNoRegistrado {
        if (ControladorModelFactory.getInstance().getSubastasQuindio().getUsuarioGlobalComprador() == null) {
            throw new ExcepcionUsuarioNoRegistrado();
        }
    }

    private void inicializarTabla() {

        pujas = FXCollections.observableArrayList();
        if (ControladorModelFactory.getInstance().getSubastasQuindio().getAnuncioGlobal().getPujas() != null) {
            pujas.addAll(ControladorModelFactory.getInstance().getSubastasQuindio().getAnuncioGlobal().getPujas());
        }


        columnaNombreComprador.setCellValueFactory(new PropertyValueFactory<Puja, String>("nombreComprador"));
        columnaValorPuja.setCellValueFactory(new PropertyValueFactory<Puja, Double>("valorPuja"));

        tablaPujasGlobales.setItems(pujas);

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        File imgFile = new File(ControladorModelFactory.getInstance().getSubastasQuindio().getAnuncioGlobal().getFoto());
        Image image = new Image("file:" + imgFile.getAbsolutePath());
        imgView.setImage(image);
        lblNombreAnuncio.setText(ControladorModelFactory.getInstance().getSubastasQuindio().getAnuncioGlobal().getNombreAnuncio());
        lblDescripcion.setText("Descripcion: " + ControladorModelFactory.getInstance().getSubastasQuindio().getAnuncioGlobal().getDescripcion());
        lblTipoProducto.setText("Tipo del producto: " + ControladorModelFactory.getInstance().getSubastasQuindio().getAnuncioGlobal().getTipoProducto());
        lblNombreAnunciante.setText("Nombre del anunciante: " + ControladorModelFactory.getInstance().getSubastasQuindio().getAnuncioGlobal().getNombreAnunciante());
        lblFechaInicio.setText("Fecha de inicio: " + ControladorModelFactory.getInstance().getSubastasQuindio().getAnuncioGlobal().getFechaPublicacion());
        lblFechaFin.setText("Fecha de finalizacion: " + ControladorModelFactory.getInstance().getSubastasQuindio().getAnuncioGlobal().getFechaCaducidad());
        lblValorInicial.setText("Valor inicial: " + ControladorModelFactory.getInstance().getSubastasQuindio().getAnuncioGlobal().getValorInicial());

        this.inicializarTabla();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}