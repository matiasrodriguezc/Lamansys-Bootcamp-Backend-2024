package ar.lamansys.messages.application.exception;

public class ProductNotExistsException extends Throwable{

    public ProductNotExistsException(String productId, String sellerId) {
        super(String.format("El producto con id %s no existe y/o su dueño no es el de id %s.", productId, sellerId));
    }
}
