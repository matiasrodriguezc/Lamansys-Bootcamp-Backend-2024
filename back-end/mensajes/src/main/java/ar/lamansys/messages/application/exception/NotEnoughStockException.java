package ar.lamansys.messages.application.exception;

public class NotEnoughStockException extends RuntimeException {
    public NotEnoughStockException(String productId) {
        super("La cantidad solicitada para el producto " + productId + " excede el stock disponible.");
    }
}

