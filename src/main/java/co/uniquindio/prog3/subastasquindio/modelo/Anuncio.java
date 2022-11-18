package co.uniquindio.prog3.subastasquindio.modelo;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.Serializable;
import java.util.ArrayList;

public class Anuncio implements Serializable {

    public transient SimpleStringProperty nombreAnuncio = new SimpleStringProperty();
    public transient SimpleStringProperty descripcion = new SimpleStringProperty();
    public transient SimpleStringProperty tipoProducto = new SimpleStringProperty();
    public transient SimpleStringProperty foto = new SimpleStringProperty(); //o Image foto;
    public transient SimpleStringProperty nombreAnunciante = new SimpleStringProperty();
    public transient SimpleStringProperty fechaPublicacion = new SimpleStringProperty();
    public transient SimpleStringProperty fechaCaducidad = new SimpleStringProperty();
    public transient SimpleDoubleProperty valorInicial = new SimpleDoubleProperty();
    public transient SimpleBooleanProperty estadoAnuncio = new SimpleBooleanProperty();
    public transient SimpleObjectProperty<ArrayList<Puja>> pujas = new SimpleObjectProperty<>();

    public Anuncio() {
    }

    public String getNombreAnuncio() {

        return nombreAnuncio.get();

    }

    public void setNombreAnuncio(String nombreProducto) {

        this.nombreAnuncio.set(nombreProducto);

    }

    public String getDescripcion() {

        return descripcion.get();

    }

    public void setDescripcion(String descripcion) {

        this.descripcion.set(descripcion);

    }

    public String getTipoProducto() {

        return tipoProducto.get();

    }

    public void setTipoProducto(String tipoProducto) {

        this.tipoProducto.set(tipoProducto);

    }

    public String getFoto() {

        return foto.get();

    }

    public void setFoto(String foto) {

        this.foto.set(foto);

    }

    public String getNombreAnunciante() {

        return nombreAnunciante.get();

    }

    public void setNombreAnunciante(String nombreAnunciante) {

        this.nombreAnunciante.set(nombreAnunciante);

    }

    public String getFechaPublicacion() {

        return fechaPublicacion.get();

    }

    public void setFechaPublicacion(String fechaPublicacion) {

        this.fechaPublicacion.set(fechaPublicacion);

    }

    public String getFechaCaducidad() {

        return fechaCaducidad.get();

    }

    public void setFechaCaducidad(String fechaCaducidad) {

        this.fechaCaducidad.set(fechaCaducidad);

    }

    public double getValorInicial() {

        return valorInicial.get();

    }

    public void setValorInicial(double valorInicial) {

        this.valorInicial.set(valorInicial);

    }

    public boolean getEstadoAnuncio() {

        return estadoAnuncio.get();

    }

    public void setEstadoAnuncio(boolean estadoAnuncio) {

        this.estadoAnuncio.set(estadoAnuncio);

    }

    public ArrayList<Puja> getPujas() {
        return pujas.get();
    }

    public void setPujas(ArrayList<Puja> pujas) {
        this.pujas.set(pujas);
    }
}
