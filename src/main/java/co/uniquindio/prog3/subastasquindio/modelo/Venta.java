package co.uniquindio.prog3.subastasquindio.modelo;

public class Venta {

    String nombreComprador = "";

    String nombreAnunciante = "";

    String nombreAnuncio = "";

    String valorPuja = "";

    public Venta() {
    }

    public Venta(String comprador, String anunciante, String anuncio, String puja) {
        this.nombreComprador = comprador;
        this.nombreAnunciante = anunciante;
        this.nombreAnuncio = anuncio;
        this.valorPuja = puja;
    }

    public String getNombreComprador() {
        return nombreComprador;
    }

    public void setNombreComprador(String nombreComprador) {
        this.nombreComprador = nombreComprador;
    }

    public String getNombreAnunciante() {
        return nombreAnunciante;
    }

    public void setNombreAnunciante(String nombreAnunciante) {
        this.nombreAnunciante = nombreAnunciante;
    }

    public String getNombreAnuncio() {
        return nombreAnuncio;
    }

    public void setNombreAnuncio(String nombreAnuncio) {
        this.nombreAnuncio = nombreAnuncio;
    }

    public String getValorPuja() {
        return valorPuja;
    }

    public void setValorPuja(String nombrePuja) {
        this.valorPuja = nombrePuja;
    }
}
