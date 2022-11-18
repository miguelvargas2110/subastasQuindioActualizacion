package co.uniquindio.prog3.subastasquindio.modelo;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.io.Serializable;

public class Puja implements Serializable {

    public transient SimpleDoubleProperty valorPuja = new SimpleDoubleProperty();
    public transient SimpleStringProperty nombreAnuncio = new SimpleStringProperty();
    public transient SimpleStringProperty nombreComprador = new SimpleStringProperty();
    public transient SimpleObjectProperty<Anuncio> anuncioAsociado = new SimpleObjectProperty<>();
    public transient SimpleStringProperty fechaFin = new SimpleStringProperty();
    public transient SimpleStringProperty fechaInicio = new SimpleStringProperty();
    public transient SimpleStringProperty tipoProducto = new SimpleStringProperty();
    public transient SimpleDoubleProperty valorInicial = new SimpleDoubleProperty();

    public Puja() {
    }

    public double getValorPuja() {
        return valorPuja.get();
    }

    public void setValorPuja(double valorPuja) {
        this.valorPuja.set(valorPuja);
    }

    public String getNombreAnuncio() {
        return nombreAnuncio.get();
    }

    public void setNombreAnuncio(String nombreAnuncio) {
        this.nombreAnuncio.set(nombreAnuncio);
    }

    public SimpleStringProperty nombreAnuncioProperty() {
        return nombreAnuncio;
    }

    public String getNombreComprador() {
        return nombreComprador.get();
    }

    public void setNombreComprador(String nombreComprador) {
        this.nombreComprador.set(nombreComprador);
    }

    public SimpleStringProperty nombreCompradorProperty() {
        return nombreComprador;
    }

    public Anuncio getAnuncioAsociado() {
        return anuncioAsociado.get();
    }

    public void setAnuncioAsociado(Anuncio anuncioAsociado) {
        this.anuncioAsociado.set(anuncioAsociado);
    }

    public String getFechaFin() {
        return fechaFin.get();
    }

    public void setFechaFin() {
        this.fechaFin.set(getAnuncioAsociado().getFechaCaducidad());
    }

    public String getFechaInicio() {
        return fechaInicio.get();
    }


    public void setFechaInicio() {
        this.fechaInicio.set(getAnuncioAsociado().getFechaPublicacion());
    }

    public void setTipoProducto() {
        this.tipoProducto.set(getAnuncioAsociado().getTipoProducto());
    }

    public String getTipoProducto() {
        return tipoProducto.get();
    }

    public double getValorInicial() {
        return valorInicial.get();
    }

    public void setValorInicial() {
        this.valorInicial.set(getAnuncioAsociado().getValorInicial());
    }
}
