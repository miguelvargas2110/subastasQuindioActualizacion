package co.uniquindio.prog3.subastasquindio.controladores;


import co.uniquindio.prog3.subastasquindio.excepciones.ExcepcionPujaNegativa;
import co.uniquindio.prog3.subastasquindio.modelo.*;
import co.uniquindio.prog3.subastasquindio.persistencia.Persistencia;

import java.io.IOException;
import java.util.ArrayList;

public class ControladorModelFactory {

    private static ControladorModelFactory instancia;

    SubastasQuindio subastasQuindio = new SubastasQuindio();


    private ControladorModelFactory() {

        Persistencia.guardarRespaldoSubastasQuindioXML();

        cargarDatosDesdeArchivos();

        if (Persistencia.cargarRecursoSubastasQuindioXML() == null) {
            System.out.println("es null");
            guardarResourceXML();
            cargarResourceXML();
            guardarResourceBinario();
            //cargarResourceBinario();
        } else {
            //cargarResourceBinario();
            cargarResourceXML();
            guardarResourceBinario();
            getSubastasQuindio().setUsuarioGlobalComprador(null);
            getSubastasQuindio().setUsuarioGlobalAnunciante(null);
            guardarResourceXML();
        }

    }

    //------------------------------  Singleton ------------------------------------------------
    public static ControladorModelFactory getInstance() {
        if (instancia == null) {
            instancia = new ControladorModelFactory();
        }
        return instancia;
    }

    public void guardarRegistroLog(String mensaje, int nivel, String accion) {
        Persistencia.guardaRegistroLog(mensaje, nivel, accion);
    }

    private void cargarDatosDesdeArchivos() {

        try {

            Persistencia.cargarDatosArchivos(getSubastasQuindio());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cargarResourceBinario() {

        subastasQuindio = Persistencia.cargarRecursoSubastasQuindioBinario();
    }

    public void guardarResourceBinario() {

        Persistencia.guardarRecursoSubastasQuindioBinario(subastasQuindio);
    }

    public void cargarResourceXML() {

        subastasQuindio = Persistencia.cargarRecursoSubastasQuindioXML();
    }


    public void guardarResourceXML() {

        Persistencia.guardarRecursoSubastasQuindioXML(subastasQuindio);
    }


    public SubastasQuindio getSubastasQuindio() {
        return subastasQuindio;
    }

    //
    public void setUniversidad(SubastasQuindio subastasQuindio) {
        this.subastasQuindio = subastasQuindio;
    }

    //
//
    public Usuario crearAnunciante(String nombre, String correo, String contrasena) {

        Usuario usuario;

        usuario = getSubastasQuindio().crearAnunciante(nombre, correo, contrasena);

        return usuario;

    }

    public Usuario crearComprador(String nombre, String correo, String contrasena) {

        Usuario usuario;

        usuario = getSubastasQuindio().crearComprador(nombre, correo, contrasena);

        return usuario;

    }

    public void guardarUsuarioArchivo(Usuario usuario) throws IOException {

        subastasQuindio.getListaUsuarios().add(usuario);

        Persistencia.guardarUsuarios(subastasQuindio.getListaUsuarios());

        Persistencia.guardarRecursoSubastasQuindioXML(subastasQuindio);

    }

    public ArrayList<String> cargarTipoProductos() throws IOException {

        ArrayList<String> tipoProductos = Persistencia.cargarTipoProductosProperties();

        return tipoProductos;

    }

    public Anuncio crearAnuncio(String nombreAnunciante, String nombreAnuncio, String tipoProducto, String descripcion, String fechaCaducidad, Double valorInicial, String rutaImagen, String estadoAnuncio) {

        Anuncio anuncio;

        anuncio = getSubastasQuindio().crearAnuncio(nombreAnunciante, nombreAnuncio, tipoProducto, descripcion, fechaCaducidad, valorInicial, rutaImagen, estadoAnuncio);

        return anuncio;
    }


    public void guardarAnuncioArchivo(Anuncio anuncio, String nombre) throws IOException {

        subastasQuindio.getListaAnuncios().add(anuncio);

        Persistencia.guardarAnuncios(subastasQuindio.getListaAnuncios());

        Persistencia.guardarRecursoSubastasQuindioXML(subastasQuindio);

    }

    public void editarAnuncioArchivo(Anuncio anuncio, Anuncio anuncioMod, String nombre) throws IOException {

        subastasQuindio.getListaAnuncios().remove(anuncio);
        subastasQuindio.getListaAnuncios().add(anuncioMod);
        subastasQuindio.getUsuarioGlobalAnunciante().getAnuncios().remove(anuncio);
        subastasQuindio.getUsuarioGlobalAnunciante().getAnuncios().add(anuncioMod);

        Persistencia.guardarAnuncios(subastasQuindio.getListaAnuncios());

        Persistencia.guardarRecursoSubastasQuindioXML(subastasQuindio);
    }

    public void eliminarAnuncioArchivo(Anuncio anuncio) throws IOException {

        subastasQuindio.getListaAnuncios().remove(anuncio);

        subastasQuindio.getUsuarioGlobalAnunciante().getAnuncios().remove(anuncio);

        Persistencia.guardarAnuncios(subastasQuindio.getListaAnuncios());

        Persistencia.guardarRecursoSubastasQuindioXML(subastasQuindio);
    }

    public void usuarioLogeado(String correo, String contrasena) {

        getSubastasQuindio().usuarioLogeado(correo, contrasena);

    }

    public void guardarAnuncio(Anuncio anuncio) {

        getSubastasQuindio().guardarAnuncio(anuncio);

    }

    public Puja crearPuja(double valorPuja, String nombreAnuncio, String nombreComprador, Anuncio anuncio) {

        Puja puja;

        puja = getSubastasQuindio().crearPuja(valorPuja, nombreAnuncio, nombreComprador, anuncio);

        return puja;
    }

    public void editarPuja(Puja puja, Double valorPuja) throws IOException {

        getSubastasQuindio().editarPuja(puja, valorPuja);

        Persistencia.guardarPujas(subastasQuindio.getListaPujas());

        Persistencia.guardarRecursoSubastasQuindioXML(subastasQuindio);

    }

    public void guardarPuja(Puja puja, String nombreAnuncio) {
        ControladorModelFactory.getInstance().getSubastasQuindio().guardarPuja(puja, nombreAnuncio);
    }

    public void guardarPujaArchivo(Puja puja) throws IOException {

        subastasQuindio.getListaPujas().add(puja);

        Persistencia.guardarPujas(subastasQuindio.getListaPujas());

        Persistencia.guardarRecursoSubastasQuindioXML(subastasQuindio);

    }

    public void validarValorPuja(double valorPuja, double valorInicial) throws ExcepcionPujaNegativa {
        if (valorPuja < valorInicial) {
            throw new ExcepcionPujaNegativa();
        }
    }

    public void eliminarPuja(Puja pujaSeleccionada) throws IOException {

        subastasQuindio.eliminarPuja(pujaSeleccionada);
        Persistencia.guardarPujas(subastasQuindio.getListaPujas());

        Persistencia.guardarRecursoSubastasQuindioXML(subastasQuindio);
    }

    public Venta crearVenta(String comprador, String anunciante, String anuncio, String puja){
        Venta venta;

        venta = getSubastasQuindio().crearVenta(comprador, anunciante, anuncio, puja);

        return venta;
    }

    public void guardarVentaArchivo(Venta venta) throws IOException {

        subastasQuindio.getVentas().add(venta);

        Persistencia.guardarVentas(subastasQuindio.getVentas());

        Persistencia.guardarRecursoSubastasQuindioXML(subastasQuindio);

    }
}