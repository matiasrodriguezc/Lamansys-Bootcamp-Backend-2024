package ar.lamansys.messages.application.exception;

public class CartNotExistsException extends Throwable {

    public CartNotExistsException() {
        super(String.format("El carrito no existe o no le pertenece a este usuario."));
    }
}
