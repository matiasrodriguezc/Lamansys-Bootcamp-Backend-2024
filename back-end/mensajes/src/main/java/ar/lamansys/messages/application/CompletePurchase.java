package ar.lamansys.messages.application;

import ar.lamansys.messages.application.exception.CartNotExistsException;
import ar.lamansys.messages.application.exception.NotEnoughStockException;
import ar.lamansys.messages.application.exception.ProductInCartNotExistsException;
import ar.lamansys.messages.application.exception.UserNotExistsException;
import ar.lamansys.messages.application.exception.messages.UserSessionNotExists;
import ar.lamansys.messages.application.messages.GetUserSession;
import ar.lamansys.messages.infrastructure.output.CartStorage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class CompletePurchase {
    private final AssertCartExists assertCartExists;
    private final GetUserSession getUserSession;
    private final GetCartStatus getCartStatus;
    private final UpdateProductStock updateProductStock;
    private final DeleteProductInCart deleteProductInCart;
    private final DeleteCart deleteCart;
    private final CartStorage cartStorage;
    private final NotifyUserCartChanges notifyUserCartChanges;

    @Transactional
    public void run(String cartId) throws UserNotExistsException, UserSessionNotExists, CartNotExistsException, NotEnoughStockException, ProductInCartNotExistsException {
        String sellerId = cartStorage.getSellerId(cartId, getUserSession.run());
        assertCartExists.run(cartId, getUserSession.run());
        getCartStatus.checkStockAvailability(cartId);
        updateProductStock.run(cartId);
        deleteProductInCart.run(cartId);
        deleteCart.run(cartId);
        String response = "Compra realizada con éxito del Carrito id:" + cartId;
        notifyUserCartChanges.run(response, sellerId);
    }
}
