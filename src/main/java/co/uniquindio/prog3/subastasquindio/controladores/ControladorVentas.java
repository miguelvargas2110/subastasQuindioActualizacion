package co.uniquindio.prog3.subastasquindio.controladores;

import co.uniquindio.prog3.subastasquindio.aplicacion.Aplicacion;
import co.uniquindio.prog3.subastasquindio.modelo.Anuncio;
import co.uniquindio.prog3.subastasquindio.modelo.Puja;
import co.uniquindio.prog3.subastasquindio.modelo.Venta;
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
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class ControladorVentas implements Initializable {

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

    /**
     * Metodo que crea la puja por cierto anuncio
     *
     * @throws IOException
     */
    @FXML
    private void elegirPuja() throws IOException {
        final Puja puja = getTablaPujaSeleccionada();
        Venta venta = ControladorModelFactory.getInstance().crearVenta(puja.getNombreComprador(), ControladorModelFactory.getInstance().getSubastasQuindio().getUsuarioGlobalAnunciante().getNombre(), puja.getAnuncioAsociado().getNombreAnuncio(), puja.valorPuja.toString());
        ControladorModelFactory.getInstance().guardarVentaArchivo(venta);
        lblPuja.setText("Se ha elegido exitosamente, se notificara al comprador para que se efectue el proceso de compra");
        ControladorModelFactory.getInstance().guardarRegistroLog(" se ha guardado la venta exitosamente", 1, "guardarVenta");
        Anuncio anuncio = puja.getAnuncioAsociado();
        puja.getAnuncioAsociado().setEstadoAnuncio(false);
        ControladorModelFactory.getInstance().editarAnuncioArchivo(anuncio, puja.getAnuncioAsociado(), ControladorModelFactory.getInstance().getSubastasQuindio().getUsuarioGlobalAnunciante().getNombre());
        this.stage.close();

    }

    /**
     * Metodo que inicializa la tabla de CRUD
     */
    private void inicializarTabla() {

        pujas = FXCollections.observableArrayList();
        if (ControladorModelFactory.getInstance().getSubastasQuindio().getAnuncioGlobal().getPujas() != null) {
            pujas.addAll(ControladorModelFactory.getInstance().getSubastasQuindio().getAnuncioGlobal().getPujas());
        }


        columnaNombreComprador.setCellValueFactory(new PropertyValueFactory<Puja, String>("nombreComprador"));
        columnaValorPuja.setCellValueFactory(new PropertyValueFactory<Puja, Double>("valorPuja"));

        tablaPujasGlobales.setItems(pujas);

    }

    public Puja getTablaPujaSeleccionada() {

        if (tablaPujasGlobales != null) {

            List<Puja> tabla = tablaPujasGlobales.getSelectionModel().getSelectedItems();

            if (tabla.size() == 1) {

                final Puja pujaSeleccionada = tabla.get(0);

                Alert dialogoAlerta = new Alert(Alert.AlertType.CONFIRMATION);
                dialogoAlerta.setTitle("");
                dialogoAlerta.setHeaderText("");
                dialogoAlerta.initStyle(StageStyle.UTILITY);
                dialogoAlerta.setContentText("Seguro que quiere seleccionar la puja con valor de: " + pujaSeleccionada.getValorPuja());
                ButtonType btnConfirmacion = new ButtonType("Si");
                ButtonType btnCancelar = new ButtonType("Cancelar");
                dialogoAlerta.getButtonTypes().setAll(btnConfirmacion, btnCancelar);
                Optional<ButtonType> opciones = dialogoAlerta.showAndWait();
                if (opciones.get() == btnConfirmacion) {
                    return pujaSeleccionada;
                }
            }

        }

        return null;

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