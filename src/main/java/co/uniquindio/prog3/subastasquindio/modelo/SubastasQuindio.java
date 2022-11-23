package co.uniquindio.prog3.subastasquindio.modelo;

import javafx.stage.Stage;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class SubastasQuindio implements Serializable {

    ArrayList<Usuario> listaUsuarios = new ArrayList<>();
    ArrayList<Anuncio> listaAnuncios = new ArrayList<>();
    ArrayList<Puja> listaPujas = new ArrayList<>();
    ArrayList<Venta> ventas = new ArrayList<>();
    Anunciante usuarioGlobalAnunciante = null;
    Comprador usuarioGlobalComprador = null;
    Anuncio anuncioGlobal = null;

    Stage stageMenu1 = new Stage();


    public SubastasQuindio() {
    }

    public ArrayList<Venta> getVentas() {
        return ventas;
    }

    public void setVentas(ArrayList<Venta> ventas) {
        this.ventas = ventas;
    }

    public ArrayList<Usuario> getListaUsuarios() {

        return listaUsuarios;

    }

    public void setListaUsuarios(ArrayList<Usuario> listaUsuarios) {

        this.listaUsuarios = listaUsuarios;

    }

    public ArrayList<Anuncio> getListaAnuncios() {

        return listaAnuncios;

    }

    public void setListaAnuncios(ArrayList<Anuncio> listaAnuncios) {

        this.listaAnuncios = listaAnuncios;

    }

    public ArrayList<Puja> getListaPujas() {

        return listaPujas;

    }

    public void setListaPujas(ArrayList<Puja> pujas) {

        this.listaPujas = pujas;

    }

    public Anunciante getUsuarioGlobalAnunciante() {
        return usuarioGlobalAnunciante;
    }

    public void setUsuarioGlobalAnunciante(Anunciante usuarioGlobalAnunciante) {
        this.usuarioGlobalAnunciante = usuarioGlobalAnunciante;
    }

    public Comprador getUsuarioGlobalComprador() {
        return usuarioGlobalComprador;
    }

    public void setUsuarioGlobalComprador(Comprador usuarioGlobalComprador) {
        this.usuarioGlobalComprador = usuarioGlobalComprador;
    }

    public Anuncio getAnuncioGlobal() {
        return anuncioGlobal;
    }

    public void setAnuncioGlobal(Anuncio anuncioGlobal) {
        this.anuncioGlobal = anuncioGlobal;
    }

    public Stage getStageMenu1() {
        return stageMenu1;
    }

    public void setStageMenu1(Stage stageMenu1) {
        this.stageMenu1 = stageMenu1;
    }

    public Anunciante crearAnunciante(String nombre, String correo, String contrasena) {

        Anunciante anunciante = new Anunciante();
        anunciante.setNombre(nombre);
        anunciante.setCorreo(correo);
        anunciante.setContrasena(contrasena);

        return anunciante;

    }

    public Venta crearVenta (String comprador, String anunciante, String anuncio, String puja){
        Venta venta = new Venta();

        venta.setNombreAnunciante(anunciante);
        venta.setNombreAnuncio(anuncio);
        venta.setNombreComprador(comprador);
        venta.setValorPuja(puja);

        return venta;

    }

    public Comprador crearComprador(String nombre, String correo, String contrasena) {

        Comprador comprador = new Comprador();

        comprador.setNombre(nombre);
        comprador.setCorreo(correo);
        comprador.setContrasena(contrasena);

        return comprador;

    }

    public Anuncio crearAnuncio(String nombreAnunciante, String nombreAnuncio, String tipoProducto, String descripcion, String fechaCaducidad, Double valorInicial, String rutaImagen) {

        Anuncio anuncio = new Anuncio();

        anuncio.setNombreAnunciante(nombreAnunciante);
        anuncio.setNombreAnuncio(nombreAnuncio);
        anuncio.setTipoProducto(tipoProducto);
        anuncio.setDescripcion(descripcion);
        anuncio.setFechaPublicacion(String.valueOf(LocalDate.now()));
        anuncio.setFechaCaducidad(fechaCaducidad);
        anuncio.setValorInicial(valorInicial);
        anuncio.setEstadoAnuncio(true);
        anuncio.setFoto(rutaImagen);

        return anuncio;
    }

    public void usuarioLogeado(String correo, String contrasena) {

        boolean flag = false;

        for (int i = 0; i < listaUsuarios.size() && !flag; i++) {
            System.out.println(listaUsuarios.get(i).getClass().getSimpleName());
            if (listaUsuarios.get(i).getCorreo().equals(correo) && listaUsuarios.get(i).getContrasena().equals(contrasena)) {
                if (listaUsuarios.get(i).getClass().getSimpleName().equals("Comprador")) {
                    setUsuarioGlobalComprador((Comprador) listaUsuarios.get(i));
                } else {
                    setUsuarioGlobalAnunciante((Anunciante) listaUsuarios.get(i));
                }
                flag = true;
            }
        }
    }

    public void guardarAnuncio(Anuncio anuncio) {

        if (getListaAnuncios() != null) {
            usuarioGlobalAnunciante.getAnuncios().add(anuncio);
        } else {
            usuarioGlobalAnunciante.getAnuncios().add(anuncio);
        }


    }

    public Puja crearPuja(Double valorPuja, String nombreAnuncio, String nombreComprador, Anuncio anuncio) {

        Puja puja = new Puja();

        puja.setNombreComprador(nombreComprador);
        puja.setValorPuja(valorPuja);
        puja.setNombreAnuncio(nombreAnuncio);
        puja.setAnuncioAsociado(anuncio);

        return puja;

    }

    public void guardarPuja(Puja puja, String nombreAnuncio) {

        for (int i = 0; i < listaAnuncios.size(); i++) {
            if (listaAnuncios.get(i).getNombreAnuncio().equals(nombreAnuncio)) {
                if (listaAnuncios.get(i).getPujas() != null) {
                    listaAnuncios.get(i).getPujas().add(puja);
                    usuarioGlobalComprador.getPujas().add(puja);
                } else {
                    ArrayList<Puja> pujas = new ArrayList<>();
                    pujas.add(puja);
                    listaAnuncios.get(i).setPujas(pujas);
                    usuarioGlobalComprador.getPujas().add(puja);
                }

            }
        }
    }

    public void editarPuja(Puja puja, Double valorPuja) {

        Puja pujaEditada = new Puja();

        for (int i = 0; i < listaPujas.size(); i++) {
            if (listaPujas.get(i).equals(puja)) {
                pujaEditada.setValorPuja(valorPuja);
                pujaEditada.setAnuncioAsociado(puja.getAnuncioAsociado());
                pujaEditada.setNombreAnuncio(puja.getNombreAnuncio());
                pujaEditada.setNombreComprador(puja.getNombreComprador());
                listaPujas.set(i, pujaEditada);
                break;
            }
        }

        for (int i = 0; i < usuarioGlobalComprador.getPujas().size(); i++) {
            if (usuarioGlobalComprador.getPujas().get(i).equals(puja)) {
                usuarioGlobalComprador.getPujas().set(i, pujaEditada);
                break;
            }
        }

        for (Anuncio anuncio : listaAnuncios) {
            for (int i = 0; i < anuncio.getPujas().size(); i++) {
                if (anuncio.getPujas().get(i).equals(puja)) {
                    anuncio.getPujas().set(i, pujaEditada);
                    break;
                }
            }
        }
    }

    public void eliminarPuja(Puja pujaSeleccionada) {
        for (int i = 0; i < listaPujas.size(); i++) {
            if (listaPujas.get(i).equals(pujaSeleccionada)) {
                listaPujas.remove(pujaSeleccionada);
            }
        }

        for (int i = 0; i < usuarioGlobalComprador.getPujas().size(); i++) {
            if (usuarioGlobalComprador.getPujas().get(i).equals(pujaSeleccionada)) {
                usuarioGlobalComprador.getPujas().remove(pujaSeleccionada);
            }
        }

        for (Anuncio anuncio : listaAnuncios) {
            for (int i = 0; i < anuncio.getPujas().size(); i++) {
                if (anuncio.getPujas().get(i).equals(pujaSeleccionada)) {
                    anuncio.getPujas().remove(pujaSeleccionada);
                    break;
                }
            }
        }
    }
}
