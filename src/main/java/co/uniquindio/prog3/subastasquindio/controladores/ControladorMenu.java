package co.uniquindio.prog3.subastasquindio.controladores;

import co.uniquindio.prog3.subastasquindio.aplicacion.Aplicacion;
import co.uniquindio.prog3.subastasquindio.modelo.Anuncio;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ControladorMenu implements Initializable {

    Aplicacion aplicacion = new Aplicacion();
    ObservableList<Anuncio> anuncios;
    @FXML
    private TableView<Anuncio> tablaAnunciosMenu;
    private final ListChangeListener<Anuncio> selectorTablaAnuncios = new ListChangeListener<Anuncio>() {

        @Override
        public void onChanged(Change<? extends Anuncio> c) {
            if (getTablaAnuncioSeleccionado() != null) {
                ControladorModelFactory.getInstance().getSubastasQuindio().setAnuncioGlobal(getTablaAnuncioSeleccionado());
                tablaAnunciosMenu.getSelectionModel().clearSelection();
                aplicacion.Transaccional();
            }
        }
    };
    @FXML
    private TableColumn columnaNombreAnuncio;
    @FXML
    private TableColumn columnaNombreAnunciante;
    @FXML
    private TableColumn columnaFechaInicio;
    @FXML
    private TableColumn columnaFechaFinalizacion;
    @FXML
    private TableColumn columnaValorInicial;
    @FXML
    private Button btnRegistro;
    @FXML
    private Button btnIngresa;
    @FXML
    private Label lblNombreUsuario;
    @FXML
    private Button btnLogOut;
    @FXML
    private Button btnNombreCrud;
    private Stage stage;

    public void onActionLogin() {
        aplicacion.Login();
    }

    public void onActionRegistro() {
        aplicacion.Registro();
    }

    public void onActionRefrescar() {
        inicializarTabla();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
        ControladorModelFactory.getInstance().getSubastasQuindio().setStageMenu1(stage);
    }

    /**
     * Metodo que inicializa la tabla de CRUD
     */
    private void inicializarTabla() {

        anuncios = FXCollections.observableArrayList();
        if (ControladorModelFactory.getInstance().getSubastasQuindio().getListaAnuncios() != null) {
            anuncios.addAll(ControladorModelFactory.getInstance().getSubastasQuindio().getListaAnuncios());
        }

        columnaNombreAnuncio.setCellValueFactory(new PropertyValueFactory<Anuncio, String>("nombreAnuncio"));
        columnaNombreAnunciante.setCellValueFactory(new PropertyValueFactory<Anuncio, String>("nombreAnunciante"));
        columnaFechaInicio.setCellValueFactory(new PropertyValueFactory<Anuncio, String>("fechaPublicacion"));
        columnaFechaFinalizacion.setCellValueFactory(new PropertyValueFactory<Anuncio, String>("fechaCaducidad"));
        columnaValorInicial.setCellValueFactory(new PropertyValueFactory<Anuncio, Double>("valorInicial"));

        tablaAnunciosMenu.setItems(anuncios);

    }

    /**
     * Toma el anuncio que se selecciono de la tabla con el cursor y lo retorna
     *
     * @return
     */
    public Anuncio getTablaAnuncioSeleccionado() {

        if (tablaAnunciosMenu != null) {

            List<Anuncio> tabla = tablaAnunciosMenu.getSelectionModel().getSelectedItems();

            if (tabla.size() == 1) {

                final Anuncio anuncioSeleccionado = tabla.get(0);

                return anuncioSeleccionado;

            }

        }

        return null;

    }

    /**
     * Hace visible ciertos botones y oculta otros si el usuario ahora es un usuario logueado
     */
    @FXML
    private void usuarioLogueado() {
        lblNombreUsuario.setLayoutX(400);
        btnRegistro.setText("");
        btnRegistro.setLayoutX(0);
        btnRegistro.setPrefWidth(0);
        btnIngresa.setText("");
        btnIngresa.setLayoutX(0);
        btnIngresa.setPrefWidth(0);
        btnLogOut.setText("Log Out");
        btnLogOut.setPrefWidth(70);
        btnLogOut.setLayoutX(520);
        btnNombreCrud.setLayoutX(14);
    }

    /**
     * Al presionar un boton abre la ventana de Pujas o de Anuncio segun sea el usuario que se registro
     */
    @FXML
    private void abrir() {
        if (ControladorModelFactory.getInstance().getSubastasQuindio().getUsuarioGlobalComprador() != null) {
            aplicacion.Pujas();
        } else {
            aplicacion.Anuncio();
        }
    }

    /**
     * Hace visible ciertos botones y oculta otros si el usuario ahora es un usuario logueado ademas de cambiar a los usuarios global a null
     */
    @FXML
    private void logOut() {
        ControladorModelFactory.getInstance().getSubastasQuindio().setUsuarioGlobalAnunciante(null);
        ControladorModelFactory.getInstance().getSubastasQuindio().setUsuarioGlobalComprador(null);
        lblNombreUsuario.setText("");
        lblNombreUsuario.setLayoutX(0);
        btnRegistro.setText("Registrate");
        btnRegistro.setLayoutX(430);
        btnRegistro.setPrefWidth(80);
        btnIngresa.setText("Ingresa");
        btnIngresa.setLayoutX(520);
        btnIngresa.setPrefWidth(66);
        btnLogOut.setText("");
        btnLogOut.setLayoutX(0);
        btnLogOut.setPrefWidth(0);
        btnNombreCrud.setText("");
        btnNombreCrud.setLayoutX(0);
        btnNombreCrud.setPrefWidth(0);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        if (ControladorModelFactory.getInstance().getSubastasQuindio().getUsuarioGlobalComprador() != null) {
            lblNombreUsuario.setText(ControladorModelFactory.getInstance().getSubastasQuindio().getUsuarioGlobalComprador().getNombre());
            btnNombreCrud.setText("Pujas");
            btnNombreCrud.setPrefWidth(50);
            usuarioLogueado();
        } else if (ControladorModelFactory.getInstance().getSubastasQuindio().getUsuarioGlobalAnunciante() != null) {
            lblNombreUsuario.setText(ControladorModelFactory.getInstance().getSubastasQuindio().getUsuarioGlobalAnunciante().getNombre());
            btnNombreCrud.setText("Anuncios");
            btnNombreCrud.setPrefWidth(80);
            usuarioLogueado();
        }

        this.inicializarTabla();

        final ObservableList<Anuncio> tablaAnuncioSel = tablaAnunciosMenu.getSelectionModel().getSelectedItems();
        tablaAnuncioSel.addListener(selectorTablaAnuncios);
    }
}