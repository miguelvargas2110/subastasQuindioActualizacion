package co.uniquindio.prog3.subastasquindio.modelo;

import java.io.Serializable;
import java.util.ArrayList;

public class Anunciante extends Usuario implements Serializable {

    ArrayList<Anuncio> anuncios = new ArrayList<>();

    public Anunciante() {
    }

    public ArrayList<Anuncio> getAnuncios() {

        return anuncios;

    }

    public void setAnuncios(ArrayList<Anuncio> anuncios) {

        this.anuncios = anuncios;

    }
}
