package ar.lamansys.messages.application;

import ar.lamansys.messages.application.exception.CartNotExistsException;
import ar.lamansys.messages.application.exception.ProductInCartNotExistsException;
import ar.lamansys.messages.application.exception.UserNotExistsException;
import ar.lamansys.messages.application.exception.messages.UserSessionNotExists;
import ar.lamansys.messages.application.messages.GetUserSession;
import ar.lamansys.messages.domain.ProductInCartBO;
import ar.lamansys.messages.infrastructure.output.CartStorage;
import ar.lamansys.messages.infrastructure.output.ProductInCartStorage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/** Revisión
 *
 *      1. Los casos de uso suelen tener un unico metodo run().
 *
 * */

@AllArgsConstructor
@Service
public class DeleteProductInCart {
    private final AssertCartExists assertCartExists;
    private final AssertProductInCartExists assertProductInCartExists;
    private final GetUserSession getUserSession;
    private final ProductInCartStorage productInCartStorage;
    private final NotifyUserCartChanges notifyUserCartChanges;
    private final CartStorage cartStorage;

    public void run(String cartId, String productInCartId) throws UserNotExistsException, UserSessionNotExists, CartNotExistsException, ProductInCartNotExistsException {
        String userId = getUserSession.run();
        assertCartExists.run(cartId, userId);
        assertProductInCartExists.run(cartId, productInCartId);
        productInCartStorage.delete(cartId, productInCartId);
        String response = "Producto con id:" + productInCartId + " borrado con éxito del Carrito.";
        notifyUserCartChanges.run(response, cartStorage.getSellerId(cartId, userId));
    }

    public void run(String cartId) throws UserNotExistsException, UserSessionNotExists, CartNotExistsException, ProductInCartNotExistsException {
        assertCartExists.run(cartId, getUserSession.run());
        for (ProductInCartBO productInCartBO : productInCartStorage.findByCartId(cartId)) {
                run(cartId, productInCartBO.getProductId());
        }
    }

}