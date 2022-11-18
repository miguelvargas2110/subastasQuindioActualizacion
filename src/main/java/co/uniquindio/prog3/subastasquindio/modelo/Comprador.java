package co.uniquindio.prog3.subastasquindio.modelo;

import java.io.Serializable;
import java.util.ArrayList;

public class Comprador extends Usuario implements Serializable {

    ArrayList<Puja> pujas = new ArrayList<>();

    public Comprador() {
    }

    public ArrayList<Puja> getPujas() {

        return pujas;

    }

    public void setPujas(ArrayList<Puja> pujas) {

        this.pujas = pujas;

    }
}
