package ar.lamansys.messages.application.exception;

public class ProductInCartNotExistsException extends Throwable{

    public ProductInCartNotExistsException(String cartId, String productId) {
        super(String.format("El producto con Id %s no estaba precargado en el carrito %s", productId, cartId));
    }
}
