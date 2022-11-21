package co.uniquindio.prog3.subastasquindio.controladores;

import co.uniquindio.prog3.subastasquindio.aplicacion.Aplicacion;
import co.uniquindio.prog3.subastasquindio.excepciones.ExcepcionFechaAnuncioInvalida;
import co.uniquindio.prog3.subastasquindio.modelo.Anuncio;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

public class ControladorAnuncios implements Initializable {
    ObservableList<String> opcionesChoiceBox = FXCollections.observableArrayList();
    ArrayList<String> tipoProductos = ControladorModelFactory.getInstance().cargarTipoProductos();
    Stage stage;
    ObservableList<Anuncio> anuncios;
    String rutaImagen;
    String nombreUsuario = ControladorModelFactory.getInstance().getSubastasQuindio().getUsuarioGlobalAnunciante().getNombre();
    @FXML
    private ChoiceBox cbTipoProducto;
    @FXML
    private ImageView imgView;
    @FXML
    private Label lblRutaImagen;
    @FXML
    private TextField txtNombreAnuncio;
    @FXML
    private TextArea txtDescripcionAnuncio;
    @FXML
    private DatePicker txtFechaFinalizacionAnuncio;
    @FXML
    private TextField txtValorInicialAnuncio;
    @FXML
    private TableView<Anuncio> tablaAnuncios;
    @FXML
    private TableColumn columnaNombreAnuncio;
    @FXML
    private TableColumn columnaDescripcionAnuncio;
    @FXML
    private TableColumn columnaTipoProducto;
    @FXML
    private TableColumn columnaFechaInicio;
    @FXML
    private TableColumn columnaFechaFinalizacion;
    @FXML
    private TableColumn columnaValorInicial;
    @FXML
    private TableColumn columnaEstadoAnuncio;
    @FXML
    private Button btnPublicarAnuncio;
    @FXML
    private Button btnEditarAnuncio;
    @FXML
    private Button btnEliminarAnuncio;
    @FXML
    private Button btnFinalizarAnuncio;
    @FXML
    private Button btnSubirImagen;

    Aplicacion aplicacion = new Aplicacion();

    private final ListChangeListener<Anuncio> selectorTablaAnuncios = new ListChangeListener<Anuncio>() {

        @Override
        public void onChanged(Change<? extends Anuncio> c) {

            try {
                ponerAnuncioSeleccionado();
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }

        }

    };
    @FXML
    private Button btnCancelar;
    @FXML
    private Label lblAnuncio;

    public ControladorAnuncios() throws IOException {
    }

    /**
     * Metodo que al pulsar un boton se publique el anuncio guardando la informacion en los respectivos archivos de texto
     *
     * @throws ExcepcionFechaAnuncioInvalida
     * @throws IOException
     */
    @FXML
    private void PublicarAnuncio() throws ExcepcionFechaAnuncioInvalida, IOException {
        lblAnuncio.setText("");
        if (txtNombreAnuncio.getText() != "" && txtDescripcionAnuncio.getText() != "" && txtFechaFinalizacionAnuncio.getValue() != null && txtValorInicialAnuncio.getText() != "") {
            boolean ok = true;
            try {
                validarFecha(txtFechaFinalizacionAnuncio.getValue());
            } catch (ExcepcionFechaAnuncioInvalida e) {
                ControladorModelFactory.getInstance().guardarRegistroLog("Ha saltado una excepcion en Fecha de anuncio", 2, e.toString());
                lblAnuncio.setText("La fecha de finalizacion debe ser mayor a la fecha actual");
                ok = false;
            }
            try {
                Double.parseDouble(txtValorInicialAnuncio.getText());
            } catch (NumberFormatException e) {
                lblAnuncio.setText("El valor inicial debe estar en numeros");
                ControladorModelFactory.getInstance().guardarRegistroLog("Ha saltado una excepcion en valor inicial", 2, e.toString());
                ok = false;
            }
            if (ok && lblRutaImagen.getText() != "") {
                guardarImagen();
                Anuncio anuncio = ControladorModelFactory.getInstance().crearAnuncio(nombreUsuario, txtNombreAnuncio.getText(), cbTipoProducto.getValue().toString(), txtDescripcionAnuncio.getText(), txtFechaFinalizacionAnuncio.getValue().toString(), Double.valueOf(txtValorInicialAnuncio.getText()), rutaImagen, "Activo");
                ControladorModelFactory.getInstance().guardarAnuncio(anuncio);
                ControladorModelFactory.getInstance().guardarAnuncioArchivo(anuncio, nombreUsuario);
                lblAnuncio.setText("Se ha publicado el anuncio");
                ControladorModelFactory.getInstance().guardarRegistroLog("Se ha publicado el anuncio " + txtNombreAnuncio.getText() + " por el ususario " + ControladorModelFactory.getInstance().getSubastasQuindio().getUsuarioGlobalAnunciante().getNombre(), 1, "guardarAnuncio");
                this.inicializarTabla();
                Cancelar();
            }
        }
    }

    /**
     * metodo que guarda la imagen en resources de un producto con el nombre del anuncio y su respectiva extension original
     */
    private void guardarImagen() {
        try {
            Path origenPath = FileSystems.getDefault().getPath(lblRutaImagen.getText());
            rutaImagen = "src/main/resources/persistencia/archivos/imagenesProductos/" + txtNombreAnuncio.getText() + "." + obtenerExtension();
            Path destinoPath = FileSystems.getDefault().getPath(rutaImagen);
            Files.copy(origenPath, destinoPath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo que obtiene la extension de una imagen y la retorna
     *
     * @return
     */
    public String obtenerExtension() {
        String fileName = lblRutaImagen.getText();
        String fe = "";
        char ch;
        int len;
        if (fileName == null || (len = fileName.length()) == 0 || (ch = fileName.charAt(len - 1)) == '/' || ch == '\\' || ch == '.') {
            fe = "";
        }
        int dotInd = fileName.lastIndexOf('.'), sepInd = Math.max(fileName.lastIndexOf('/'), fileName.lastIndexOf('\\'));
        if (dotInd <= sepInd) {
            fe = "";
        } else {
            fe = fileName.substring(dotInd + 1).toLowerCase();
        }
        return fe;
    }

    /**
     * Metodo que valida si la fecha ingresada es correcta
     *
     * @param value
     * @throws ExcepcionFechaAnuncioInvalida
     */
    private void validarFecha(LocalDate value) throws ExcepcionFechaAnuncioInvalida {
        if (value.isBefore(LocalDate.now())) {
            throw new ExcepcionFechaAnuncioInvalida();
        }
    }

    /**
     * Metodo que Elimina un anuncio dandole al boton de eliminar anuncio
     *
     * @throws IOException
     */
    @FXML
    private void EliminarAnuncio() throws IOException {

        lblAnuncio.setText("");

        Anuncio anuncioSeleccionado = getTablaAnuncioSeleccionado();

        ControladorModelFactory.getInstance().eliminarAnuncioArchivo(anuncioSeleccionado);

        lblAnuncio.setText("Se ha eliminado el anuncio");

        ControladorModelFactory.getInstance().guardarRegistroLog("Se ha eliminado el anuncio " + txtNombreAnuncio.getText() + " por el ususario " + ControladorModelFactory.getInstance().getSubastasQuindio().getUsuarioGlobalAnunciante().getNombre(), 1, "eliminarAnuncio");

        inicializarTabla();

        Cancelar();
    }

    /**
     * Metodo que edita la informacion del anuncio en base a la informacion que tiene este mismo
     *
     * @throws IOException
     */
    @FXML
    private void EditarAnuncio() throws IOException {

        lblAnuncio.setText("");

        Anuncio anuncioSeleccionado = getTablaAnuncioSeleccionado();

        if (txtNombreAnuncio.getText() != "" && txtDescripcionAnuncio.getText() != "" && txtFechaFinalizacionAnuncio.getValue() != null && txtValorInicialAnuncio.getText() != "") {
            if (anuncioSeleccionado.getEstadoAnuncio().equals("Activo")) {
                boolean ok = true;
                try {
                    validarFecha(txtFechaFinalizacionAnuncio.getValue());
                } catch (ExcepcionFechaAnuncioInvalida e) {
                    ControladorModelFactory.getInstance().guardarRegistroLog("Ha saltado una excepcion en Fecha de anuncio", 2, e.toString());
                    lblAnuncio.setText("La fecha de finalizacion debe ser mayor a la fecha actual");
                    ok = false;
                }
                try {
                    Double.parseDouble(txtValorInicialAnuncio.getText());
                } catch (NumberFormatException e) {
                    lblAnuncio.setText("El valor inicial debe estar en numeros");
                    ControladorModelFactory.getInstance().guardarRegistroLog("Ha saltado una excepcion en valor inicial", 2, e.toString());
                    ok = false;
                }
                if (ok && lblRutaImagen.getText() != "") {
                    Anuncio anuncio = ControladorModelFactory.getInstance().crearAnuncio(nombreUsuario, txtNombreAnuncio.getText(), cbTipoProducto.getValue().toString(), txtDescripcionAnuncio.getText(), txtFechaFinalizacionAnuncio.getValue().toString(), Double.valueOf(txtValorInicialAnuncio.getText()), lblRutaImagen.getText(), "Activo");
                    ControladorModelFactory.getInstance().editarAnuncioArchivo(anuncioSeleccionado, anuncio, nombreUsuario);
                    guardarImagen();
                    lblAnuncio.setText("Se ha editado el anuncio");
                    Cancelar();
                    ControladorModelFactory.getInstance().guardarRegistroLog("Se ha editado el anuncio " + txtNombreAnuncio.getText() + " por el ususario " + ControladorModelFactory.getInstance().getSubastasQuindio().getUsuarioGlobalAnunciante().getNombre(), 1, "editarAnuncio");
                    this.inicializarTabla();
                }
            }
        }
    }

    /**
     * Metodo que pone en blanco o en null segun sea el caso cuando se le da al boton cancelar o cuando se termina una operacion del CRUd
     */
    @FXML
    private void Cancelar() {

        txtNombreAnuncio.setText("");
        cbTipoProducto.setValue(tipoProductos.get(0));
        txtDescripcionAnuncio.setText("");
        txtFechaFinalizacionAnuncio.setValue(null);
        txtValorInicialAnuncio.setText("");
        tablaAnuncios.getSelectionModel().clearSelection();
        lblRutaImagen.setText("");
        imgView.setImage(null);

        btnPublicarAnuncio.setDisable(false);
        btnEditarAnuncio.setDisable(true);
        btnEliminarAnuncio.setDisable(true);
        btnFinalizarAnuncio.setDisable(true);
        btnFinalizarAnuncio.setVisible(false);
        btnSubirImagen.setDisable(false);

        txtNombreAnuncio.setEditable(true);
        cbTipoProducto.setDisable(false);
        txtDescripcionAnuncio.setEditable(true);
        txtFechaFinalizacionAnuncio.setEditable(true);
        txtValorInicialAnuncio.setEditable(true);

    }

    /**
     * Metodo que toma una imagen que seleccionemos y la carga a archivos
     */
    @FXML
    private void SubirImagen() {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Buscar Imagen");

        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Archivos perzonalizados", ".jpg; *.png;.jpeg"));

        File imgFile = fileChooser.showOpenDialog(stage);

        // Mostar la imagen
        if (imgFile != null) {

            lblRutaImagen.setText(imgFile.getAbsolutePath());

            Image image = new Image("file:" + imgFile.getAbsolutePath());
            imgView.setImage(image);

        }


    }

    @FXML
    private void FinalizarAnuncio() throws IOException {

        lblAnuncio.setText("");

        Anuncio anuncioSeleccionado = getTablaAnuncioSeleccionado();

        String finalizarAnuncio = "";

        if(anuncioSeleccionado.getPujas() != null){
            finalizarAnuncio = "Esperando";
        }else{
            finalizarAnuncio = "Finalizado";
        }

        Anuncio anuncio = ControladorModelFactory.getInstance().crearAnuncio(nombreUsuario, txtNombreAnuncio.getText(), cbTipoProducto.getValue().toString(), txtDescripcionAnuncio.getText(), txtFechaFinalizacionAnuncio.getValue().toString(), Double.valueOf(txtValorInicialAnuncio.getText()), lblRutaImagen.getText(), finalizarAnuncio);
        ControladorModelFactory.getInstance().editarAnuncioArchivo(anuncioSeleccionado, anuncio, nombreUsuario);

        Cancelar();

        this.inicializarTabla();

        lblAnuncio.setText("Se ha finalizado el anuncio");

    }

    /**
     * Metodo que inicializa la tabla de CRUD
     */
    private void inicializarTabla() {

        anuncios = FXCollections.observableArrayList();
        anuncios.addAll(ControladorModelFactory.getInstance().getSubastasQuindio().getUsuarioGlobalAnunciante().getAnuncios());

        columnaNombreAnuncio.setCellValueFactory(new PropertyValueFactory<Anuncio, String>("nombreAnuncio"));
        columnaTipoProducto.setCellValueFactory(new PropertyValueFactory<Anuncio, String>("tipoProducto"));
        columnaDescripcionAnuncio.setCellValueFactory(new PropertyValueFactory<Anuncio, String>("descripcion"));
        columnaFechaInicio.setCellValueFactory(new PropertyValueFactory<Anuncio, String>("fechaPublicacion"));
        columnaFechaFinalizacion.setCellValueFactory(new PropertyValueFactory<Anuncio, String>("fechaCaducidad"));
        columnaValorInicial.setCellValueFactory(new PropertyValueFactory<Anuncio, Double>("valorInicial"));
        columnaEstadoAnuncio.setCellValueFactory(new PropertyValueFactory<Anuncio, Boolean>("estadoAnuncio"));

        tablaAnuncios.setItems(anuncios);

    }

    /**
     * Toma el anuncio que se selecciono de la tabla con el cursor y lo retorna
     *
     * @return
     */
    public Anuncio getTablaAnuncioSeleccionado() {

        if (tablaAnuncios != null) {

            List<Anuncio> tabla = tablaAnuncios.getSelectionModel().getSelectedItems();

            if (tabla.size() == 1) {

                final Anuncio anuncioSeleccionado = tabla.get(0);

                lblRutaImagen.setText(anuncioSeleccionado.getFoto());
                File imgFile = new File(lblRutaImagen.getText());
                Image image = new Image("file:" + imgFile.getAbsolutePath());
                imgView.setImage(image);

                return anuncioSeleccionado;

            }

        }

        return null;

    }

    /**
     * Toma el anuncio seleccionado y en base a ese anuncio llena automaticamente los espacios de texto para eliminarlo y/o editarlo
     *
     * @throws ParseException
     */
    private void ponerAnuncioSeleccionado() throws ParseException {

        final Anuncio anuncio = getTablaAnuncioSeleccionado();

        if (anuncio != null) {

            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            Date fecha = formato.parse(anuncio.getFechaCaducidad());

            txtNombreAnuncio.setText(anuncio.getNombreAnuncio());
            cbTipoProducto.setValue(anuncio.getTipoProducto());
            txtDescripcionAnuncio.setText(anuncio.getDescripcion());
            txtFechaFinalizacionAnuncio.setValue(LocalDate.parse(formato.format(fecha)));
            txtValorInicialAnuncio.setText(String.valueOf(anuncio.getValorInicial()));

            if(anuncio.getEstadoAnuncio().equals("Finalizado") || anuncio.getEstadoAnuncio().equals("Esperando")){
                btnPublicarAnuncio.setDisable(true);
                btnSubirImagen.setDisable(true);
                txtNombreAnuncio.setEditable(false);
                cbTipoProducto.setDisable(true);
                txtDescripcionAnuncio.setEditable(false);
                txtFechaFinalizacionAnuncio.setEditable(false);
                txtValorInicialAnuncio.setEditable(false);
            }else{
                btnPublicarAnuncio.setDisable(true);
                btnEditarAnuncio.setDisable(false);
                btnEliminarAnuncio.setDisable(false);
                btnFinalizarAnuncio.setVisible(true);
                btnFinalizarAnuncio.setDisable(false);
            }
        }

        if (anuncio.getEstadoAnuncio().equals("Esperando")){
            Alert dialogoAlerta = new Alert(Alert.AlertType.CONFIRMATION);
            dialogoAlerta.setTitle("");
            dialogoAlerta.setHeaderText("");
            dialogoAlerta.initStyle(StageStyle.UTILITY);
            dialogoAlerta.setContentText("Este anuncio ya se ha cerrado, por favor elija una puja");
            ButtonType btnElegirPuja = new ButtonType("Elegir puja");
            dialogoAlerta.getButtonTypes().setAll(btnElegirPuja);
            Optional<ButtonType> opciones = dialogoAlerta.showAndWait();
            if (opciones.get() == btnElegirPuja) {
                aplicacion.Transaccional2();
                ControladorVentas.setAnuncio(anuncio);
                this.stage.close();
            }
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        for (int i = 0; i < tipoProductos.size(); i++) {

            opcionesChoiceBox.add(tipoProductos.get(i));

        }

        cbTipoProducto.setValue(tipoProductos.get(0));
        cbTipoProducto.setItems(opcionesChoiceBox);

        this.inicializarTabla();

        final ObservableList<Anuncio> tablaAnuncioSel = tablaAnuncios.getSelectionModel().getSelectedItems();
        tablaAnuncioSel.addListener(selectorTablaAnuncios);

        Cancelar();

        try {
            ControladorModelFactory.getInstance().getSubastasQuindio().verificarAnuncios();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
