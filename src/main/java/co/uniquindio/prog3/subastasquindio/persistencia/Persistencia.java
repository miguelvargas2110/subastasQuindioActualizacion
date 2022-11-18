package co.uniquindio.prog3.subastasquindio.persistencia;

import co.uniquindio.prog3.subastasquindio.excepciones.ExcepcionUsuario;
import co.uniquindio.prog3.subastasquindio.modelo.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class Persistencia {
    public static final String RUTA_ARCHIVO_USUARIOS = "src/main/resources/persistencia/archivos/archivoUsuarios.txt";
    public static final String RUTA_ARCHIVO_ANUNCIOS = "src/main/resources/persistencia/archivos/archivoAnuncios.txt";
    public static final String RUTA_ARCHIVO_PUJA = "src/main/resources/persistencia/archivos/archivoPujas.txt";
    public static final String RUTA_ARCHIVO_LOG = "src/main/resources/persistencia/log/SubastasLog.txt";
    public static final String RUTA_ARCHIVO_OBJETOS = "src/main/resources/persistencia/archivoObjetos.txt";
    public static final String RUTA_ARCHIVO_MODELO_SUBASTASQUINDIO_BINARIO = "src/main/resources/persistencia/model.dat";
    public static final String RUTA_ARCHIVO_MODELO_SUBASTASQUINDIO_XML = "src/main/resources/persistencia/model.xml";
    private static final String RUTA_ARCHIVO_PROPERTIES_TIPOPRODUCTO = "src/main/resources/persistencia/archivos/tipoProducto.properties";
    private static final String RUTA_ARCHIVO_RESPALDO = "src/main/resources/persistencia/respaldo/";

    public static void cargarDatosArchivos(SubastasQuindio subastasQuindio) throws IOException {


        //cargar archivo de usuarios
        ArrayList<Usuario> usuariosCargados = cargarUsuarios();

        ArrayList<Anuncio> anunciosCargados = cargarAnuncios();

        if (usuariosCargados != null && usuariosCargados.size() > 0)
            subastasQuindio.getListaUsuarios().addAll(usuariosCargados);

        if (anunciosCargados != null && anunciosCargados.size() > 0)
            subastasQuindio.getListaAnuncios().addAll(anunciosCargados);
        /*cargar archivos empleados
        ArrayList<Empleado> empleadosCargados = cargarEmpleados();

        if(empleadosCargados.size() > 0)
            SubastasQuindio.getListaEmpleados().addAll(empleadosCargados);
        */
        //cargar archivo objetos

        //cargar archivo empleados

        //cargar archivo prestamo

    }

    /**
     * Guarda en un archivo de texto todos la información de las personas almacenadas en el ArrayList
     *
     * @param listaUsuarios
     * @throws IOException
     */
    public static void guardarUsuarios(ArrayList<Usuario> listaUsuarios) throws IOException {
        String contenido = "";

        for (Usuario usuario : listaUsuarios) {
            if (usuario.getClass().getSimpleName().equals("Comprador")) {
                contenido += usuario.getNombre() + "@@" + usuario.getCorreo() + "@@" + usuario.getContrasena() + "@@Comprador@@" + listaUsuarios.indexOf(usuario) + "\n";
            } else {
                contenido += usuario.getNombre() + "@@" + usuario.getCorreo() + "@@" + usuario.getContrasena() + "@@Anunciante@@" + listaUsuarios.indexOf(usuario) + "\n";
            }

        }
        ArchivoUtil.guardarArchivo(RUTA_ARCHIVO_USUARIOS, contenido, false);

    }

    public static void guardarAnuncios(ArrayList<Anuncio> listaAnuncios) throws IOException {

        String contenido = "";

        for (Anuncio anuncio : listaAnuncios) {
            contenido += anuncio.getNombreAnunciante() + "@@" + anuncio.getNombreAnuncio() + "@@" + anuncio.getTipoProducto() + "@@" + anuncio.getDescripcion() + "@@" +
                    anuncio.getFechaPublicacion() + "@@" + anuncio.getFechaCaducidad() + "@@" + anuncio.getValorInicial() + "@@" +
                    anuncio.getEstadoAnuncio() + "\n";
        }
        ArchivoUtil.guardarArchivo(RUTA_ARCHIVO_ANUNCIOS, contenido, false);
    }

    public static void guardarPujas(ArrayList<Puja> listaPujas) throws IOException {

        String contenido = "";

        for (Puja puja : listaPujas) {
            contenido += puja.getNombreComprador() + "@@" + puja.getValorPuja() + "@@" + puja.getNombreAnuncio() + "\n";
        }
        ArchivoUtil.guardarArchivo(RUTA_ARCHIVO_PUJA, contenido, false);

    }

//	----------------------LOADS------------------------

    public static ArrayList<String> cargarTipoProductosProperties() throws IOException {

        ArrayList<String> tipoProductos = ArchivoUtil.leerProperties(RUTA_ARCHIVO_PROPERTIES_TIPOPRODUCTO);

        return tipoProductos;

    }

    /**
     * @return un Arraylist de personas con los datos obtenidos del archivo de texto indicado
     * @throws FileNotFoundException
     * @throws IOException
     */
    public static ArrayList<Usuario> cargarUsuarios() throws FileNotFoundException, IOException {
        ArrayList<Usuario> usuarios = new ArrayList<>();

        ArrayList<String> contenido = ArchivoUtil.leerArchivo(RUTA_ARCHIVO_USUARIOS);
        String linea = "";

        for (int i = 0; i < contenido.size(); i++) {
            linea = contenido.get(i);
            if (linea.split("@@")[3].equals("Anunciante")) {
                Anunciante anunciante = new Anunciante();
                ArrayList<Anuncio> anuncios = new ArrayList<>();
                anunciante.setNombre(linea.split("@@")[0]);
                anunciante.setCorreo(linea.split("@@")[1]);
                anunciante.setContrasena(linea.split("@@")[2]);
                anuncios = cargarAnunciosUsuario(anunciante);
                anunciante.setAnuncios(anuncios);
                usuarios.add(anunciante);
            } else {
                Comprador comprador = new Comprador();
                ArrayList<Puja> pujas = new ArrayList<>();
                comprador.setNombre(linea.split("@@")[0]);
                comprador.setCorreo(linea.split("@@")[1]);
                comprador.setContrasena(linea.split("@@")[2]);
                pujas = cargarPujasUsuario(comprador);
                comprador.setPujas(pujas);
                usuarios.add(comprador);
            }
        }
        return usuarios;
    }

    public static ArrayList<Anuncio> cargarAnuncios() throws IOException {
        ArrayList<Anuncio> anuncios = new ArrayList<>();

        try {
            ArrayList<String> contenido = ArchivoUtil.leerArchivo(RUTA_ARCHIVO_ANUNCIOS);
            String linea = "";

            for (int i = 0; i < contenido.size(); i++) {
                linea = contenido.get(i);
                Anuncio anuncio = new Anuncio();
                ArrayList<Puja> pujas = new ArrayList<>();
                anuncio.setNombreAnunciante(linea.split("@@")[0]);
                anuncio.setNombreAnuncio(linea.split("@@")[1]);
                anuncio.setTipoProducto(linea.split("@@")[2]);
                anuncio.setDescripcion(linea.split("@@")[3]);
                anuncio.setFechaPublicacion(linea.split("@@")[4]);
                anuncio.setFechaCaducidad(linea.split("@@")[5]);
                anuncio.setValorInicial(Double.parseDouble(linea.split("@@")[6]));
                anuncio.setEstadoAnuncio(Boolean.parseBoolean(linea.split("@@")[7]));
                pujas = cargarPujasAnuncio(anuncio);
                anuncio.setPujas(pujas);
                anuncios.add(anuncio);
            }
            return anuncios;
        } catch (FileNotFoundException e) {
            return null;
        }

    }

    private static ArrayList<Puja> cargarPujas() throws IOException {

        ArrayList<Puja> pujas = new ArrayList<>();


        try {
            ArrayList<String> contenido = ArchivoUtil.leerArchivo(RUTA_ARCHIVO_PUJA);
            String linea = "";
            ArrayList<String> contenidoAnuncios = ArchivoUtil.leerArchivo((RUTA_ARCHIVO_ANUNCIOS));
            String lineaAnuncio = "";
            for (int i = 0; i < contenido.size(); i++) {
                linea = contenido.get(i);
                Puja puja = new Puja();
                puja.setNombreComprador(linea.split("@@")[0]);
                puja.setValorPuja(Double.parseDouble(linea.split("@@")[1]));
                puja.setNombreAnuncio(linea.split("@@")[2]);
                for (int j = 0; j < contenidoAnuncios.size(); j++) {
                    lineaAnuncio = contenidoAnuncios.get(j);
                    if (puja.getNombreAnuncio().equals(lineaAnuncio.split("@@")[1])) {
                        Anuncio anuncio = new Anuncio();
                        anuncio.setNombreAnunciante(lineaAnuncio.split("@@")[0]);
                        anuncio.setNombreAnuncio(lineaAnuncio.split("@@")[1]);
                        anuncio.setTipoProducto(lineaAnuncio.split("@@")[2]);
                        anuncio.setDescripcion(lineaAnuncio.split("@@")[3]);
                        anuncio.setFechaPublicacion(lineaAnuncio.split("@@")[4]);
                        anuncio.setFechaCaducidad(lineaAnuncio.split("@@")[5]);
                        anuncio.setValorInicial(Double.parseDouble(lineaAnuncio.split("@@")[6]));
                        anuncio.setEstadoAnuncio(Boolean.parseBoolean(lineaAnuncio.split("@@")[7]));
                        puja.setAnuncioAsociado(anuncio);
                    }
                }
                pujas.add(puja);
            }
            return pujas;
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    private static ArrayList<Anuncio> cargarAnunciosUsuario(Anunciante anunciante) throws IOException {

        ArrayList<Anuncio> anunciosCargados = cargarAnuncios();

        ArrayList<Anuncio> anunciosUsuario = new ArrayList<>();

        for (int i = 0; anunciosCargados != null && i < anunciosCargados.size(); i++) {
            if (anunciante.getNombre().equals(anunciosCargados.get(i).getNombreAnunciante())) {
                anunciosUsuario.add(anunciosCargados.get(i));
            }
        }
        return anunciosUsuario;
    }

    private static ArrayList<Puja> cargarPujasUsuario(Comprador comprador) throws IOException {
        ArrayList<Puja> pujasCargadas = cargarPujas();

        ArrayList<Puja> pujasUsuario = new ArrayList<>();

        for (int i = 0; pujasCargadas != null && i < pujasCargadas.size(); i++) {
            if (comprador.getNombre().equals(pujasCargadas.get(i).getNombreComprador())) {
                pujasUsuario.add(pujasCargadas.get(i));
            }
        }
        return pujasUsuario;
    }

    private static ArrayList<Puja> cargarPujasAnuncio(Anuncio anuncio) throws IOException {
        ArrayList<Puja> pujasCargadas = cargarPujas();

        ArrayList<Puja> pujasAnuncio = new ArrayList<>();

        for (int i = 0; pujasCargadas != null && i < pujasCargadas.size(); i++) {
            if (anuncio.getNombreAnuncio().equals(pujasCargadas.get(i).getNombreAnuncio())) {
                pujasAnuncio.add(pujasCargadas.get(i));
            }
        }
        return pujasCargadas;
    }


    public static void guardaRegistroLog(String mensajeLog, int nivel, String accion) {

        ArchivoUtil.guardarRegistroLog(mensajeLog, nivel, accion, RUTA_ARCHIVO_LOG);
    }


    public static boolean iniciarSesion(String correo, String contrasena) throws IOException, ExcepcionUsuario {

        if (validarUsuario(correo, contrasena)) {
            return true;
        } else {
            throw new ExcepcionUsuario("Usuario no existe");
        }

    }

    private static boolean validarUsuario(String correo, String contrasena) throws IOException {
        ArrayList<Usuario> usuarios = Persistencia.cargarUsuarios();

        for (int indiceUsuario = 0; indiceUsuario < usuarios.size(); indiceUsuario++) {
            Usuario usuarioAux = usuarios.get(indiceUsuario);
            if (usuarioAux.getCorreo().equalsIgnoreCase(correo) && usuarioAux.getContrasena().equalsIgnoreCase(contrasena)) {
                return true;
            }
        }
        return false;
    }


//	----------------------SAVES------------------------

    /**
     * Guarda en un archivo de texto todos la información de las personas almacenadas en el ArrayList
     *
     * @param listaUsuarios
     * @throws IOException
     */

    public static void guardarObjetos(ArrayList<Usuario> listaUsuarios) throws IOException {
        String contenido = "";

        for (Usuario usuarioAux : listaUsuarios) {
            contenido += usuarioAux.getNombre() + "@@" + usuarioAux.getCorreo() + "@@" + usuarioAux.getContrasena() + "\n";
        }
        ArchivoUtil.guardarArchivo(RUTA_ARCHIVO_OBJETOS, contenido, true);
    }


    //------------------------------------SERIALIZACIÓN  y XML


    public static SubastasQuindio cargarRecursoSubastasQuindioBinario() {

        SubastasQuindio subastasQuindio = null;

        try {
            subastasQuindio = (SubastasQuindio) ArchivoUtil.cargarRecursoSerializado(RUTA_ARCHIVO_MODELO_SUBASTASQUINDIO_BINARIO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return subastasQuindio;
    }

    public static void guardarRecursoSubastasQuindioBinario(SubastasQuindio subastasQuindio) {

        try {
            ArchivoUtil.salvarRecursoSerializado(RUTA_ARCHIVO_MODELO_SUBASTASQUINDIO_BINARIO, subastasQuindio);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static SubastasQuindio cargarRecursoSubastasQuindioXML() {

        SubastasQuindio subastasQuindio = null;

        try {
            subastasQuindio = (SubastasQuindio) ArchivoUtil.cargarRecursoSerializadoXML(RUTA_ARCHIVO_MODELO_SUBASTASQUINDIO_XML);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return subastasQuindio;

    }


    public static void guardarRecursoSubastasQuindioXML(SubastasQuindio subastasQuindio) {

        try {
            ArchivoUtil.salvarRecursoSerializadoXML(RUTA_ARCHIVO_MODELO_SUBASTASQUINDIO_XML, subastasQuindio);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void guardarRespaldoSubastasQuindioXML() {

        SubastasQuindio subastasQuindio = new SubastasQuindio();
        ArchivoUtil.cargarFechaSistema();

        if (cargarRecursoSubastasQuindioXML() == null) {
            guardarRecursoSubastasQuindioXML(subastasQuindio);
            subastasQuindio = cargarRecursoSubastasQuindioXML();
            try {
                ArchivoUtil.salvarRecursoSerializadoXML(RUTA_ARCHIVO_RESPALDO + "copiaXML" + ArchivoUtil.fechaSistemaRespaldo + ".xml", subastasQuindio);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            subastasQuindio = cargarRecursoSubastasQuindioXML();
            try {
                ArchivoUtil.salvarRecursoSerializadoXML(RUTA_ARCHIVO_RESPALDO + "copiaXML" + ArchivoUtil.fechaSistemaRespaldo + ".xml", subastasQuindio);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
