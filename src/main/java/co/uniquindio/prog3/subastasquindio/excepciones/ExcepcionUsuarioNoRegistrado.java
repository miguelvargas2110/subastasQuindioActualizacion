package co.uniquindio.prog3.subastasquindio.excepciones;

public class ExcepcionUsuarioNoRegistrado extends Exception {
    public ExcepcionUsuarioNoRegistrado() {
    }

    public ExcepcionUsuarioNoRegistrado(String message) {
        super(message);
    }
}
