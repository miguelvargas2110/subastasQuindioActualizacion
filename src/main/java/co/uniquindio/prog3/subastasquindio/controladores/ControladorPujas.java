package co.uniquindio.prog3.subastasquindio.controladores;

import co.uniquindio.prog3.subastasquindio.excepciones.ExcepcionPujaNegativa;
import co.uniquindio.prog3.subastasquindio.modelo.Puja;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.util.List;
import java.util.ResourceBundle;

public class ControladorPujas implements Initializable {

    ObservableList<Puja> pujas;
    @FXML
    private TextField txtValorPuja;
    @FXML
    private TableView<Puja> tablaPujas;
    @FXML
    private TableColumn columnaNombreAnuncio;
    @FXML
    private TableColumn columnaValorInicial;
    @FXML
    private TableColumn columnaTipoProducto;
    @FXML
    private TableColumn columnaFechaInicio;
    @FXML
    private TableColumn columnaFechaFin;
    @FXML
    private TableColumn columnaValorPujado;
    @FXML
    private Label nombreAnuncio;
    @FXML
    private Label tipoProducto;
    @FXML
    private TextArea descripcion;
    @FXML
    private Label fechaInicio;
    @FXML
    private Label fechaFin;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnEditar;
    private final ListChangeListener<Puja> selectorTablaPujas = new ListChangeListener<Puja>() {

        @Override
        public void onChanged(Change<? extends Puja> c) {

            try {
                ponerPujaSeleccionada();
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

        }

    };

    @FXML
    private void Cancelar() {
        nombreAnuncio.setText("");
        tipoProducto.setText("");
        descripcion.setText("");
        fechaInicio.setText("");
        fechaFin.setText("");
        txtValorPuja.setText("");
        txtValorPuja.setDisable(true);

        btnEliminar.setDisable(true);
        btnEditar.setDisable(true);
        tablaPujas.getSelectionModel().clearSelection();
    }

    @FXML
    private void EditarPuja() {
        Puja pujaSeleccionado = getTablaPujaSeleccionada();
        if (txtValorPuja.getText() != "") {
            try {
                Double.parseDouble(txtValorPuja.getText());
                ControladorModelFactory.getInstance().validarValorPuja(Double.parseDouble(txtValorPuja.getText()), pujaSeleccionado.getAnuncioAsociado().getValorInicial());
                ControladorModelFactory.getInstance().editarPuja(pujaSeleccionado, Double.parseDouble(txtValorPuja.getText()));
                ControladorModelFactory.getInstance().guardarRegistroLog("Se ha editado el valor de la puja por el usuario " + ControladorModelFactory.getInstance().getSubastasQuindio().getUsuarioGlobalComprador().getNombre(), 1, "editarPuja");
                inicializarTabla();
                Cancelar();
            } catch (ExcepcionPujaNegativa excepcionPujaNegativa) {
                ControladorModelFactory.getInstance().guardarRegistroLog("Ha salado una excepcion de valor menor de valor inicial", 2, excepcionPujaNegativa.toString());
            } catch (NumberFormatException e) {
                ControladorModelFactory.getInstance().guardarRegistroLog("Ha saltado una excepcion  en el valor de la puja", 2, e.toString());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    private void EliminarPuja() throws IOException {
        Puja pujaSeleccionada = getTablaPujaSeleccionada();
        ControladorModelFactory.getInstance().eliminarPuja(pujaSeleccionada);
        inicializarTabla();
        ControladorModelFactory.getInstance().guardarRegistroLog("Se ha eliminado la puja por el usuario " + ControladorModelFactory.getInstance().getSubastasQuindio().getUsuarioGlobalComprador().getNombre(), 1, "eliminarAnuncio");
        Cancelar();
    }

    private void inicializarTabla() {

        pujas = FXCollections.observableArrayList();
        for (int i = 0; i < ControladorModelFactory.getInstance().getSubastasQuindio().getUsuarioGlobalComprador().getPujas().size(); i++) {
            ControladorModelFactory.getInstance().getSubastasQuindio().getUsuarioGlobalComprador().getPujas().get(i).setValorInicial();
            ControladorModelFactory.getInstance().getSubastasQuindio().getUsuarioGlobalComprador().getPujas().get(i).setFechaFin();
            ControladorModelFactory.getInstance().getSubastasQuindio().getUsuarioGlobalComprador().getPujas().get(i).setFechaInicio();
            ControladorModelFactory.getInstance().getSubastasQuindio().getUsuarioGlobalComprador().getPujas().get(i).setTipoProducto();
        }
        pujas.addAll(ControladorModelFactory.getInstance().getSubastasQuindio().getUsuarioGlobalComprador().getPujas());

        columnaNombreAnuncio.setCellValueFactory(new PropertyValueFactory<Puja, String>("nombreAnuncio"));
        columnaValorInicial.setCellValueFactory(new PropertyValueFactory<Puja, Double>("valorInicial"));
        columnaTipoProducto.setCellValueFactory(new PropertyValueFactory<Puja, String>("tipoProducto"));
        columnaFechaInicio.setCellValueFactory(new PropertyValueFactory<Puja, String>("fechaInicio"));
        columnaFechaFin.setCellValueFactory(new PropertyValueFactory<Puja, String>("fechaFin"));
        columnaValorPujado.setCellValueFactory(new PropertyValueFactory<Puja, String>("valorPuja"));

        tablaPujas.setItems(pujas);

    }

    public Puja getTablaPujaSeleccionada() {

        if (tablaPujas != null) {

            List<Puja> tabla = tablaPujas.getSelectionModel().getSelectedItems();

            if (tabla.size() == 1) {

                final Puja pujaSeleccionada = tabla.get(0);

                return pujaSeleccionada;

            }

        }

        return null;

    }

    private void ponerPujaSeleccionada() throws ParseException {

        final Puja puja = getTablaPujaSeleccionada();

        if (puja != null) {

            nombreAnuncio.setText(puja.getAnuncioAsociado().getNombreAnuncio());
            tipoProducto.setText(puja.getAnuncioAsociado().getTipoProducto());
            descripcion.setText(puja.getAnuncioAsociado().getDescripcion());
            fechaInicio.setText(puja.getAnuncioAsociado().getFechaPublicacion());
            fechaFin.setText(puja.getAnuncioAsociado().getFechaCaducidad());
            txtValorPuja.setText(String.valueOf(puja.getValorPuja()));

            txtValorPuja.setDisable(false);
            btnEliminar.setDisable(false);
            btnEditar.setDisable(false);

        }

    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.inicializarTabla();
        final ObservableList<Puja> tablaPujaSel = tablaPujas.getSelectionModel().getSelectedItems();
        tablaPujaSel.addListener(selectorTablaPujas);
        btnEliminar.setDisable(true);
        btnEditar.setDisable(true);
    }
}
