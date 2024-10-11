package ar.lamansys.messages.application;

import ar.lamansys.messages.application.exception.ListIsEmptyException;
import ar.lamansys.messages.application.exception.NotEnoughStockException;
import ar.lamansys.messages.application.exception.ProductNotExistsException;
import ar.lamansys.messages.application.exception.UserNotExistsException;
import ar.lamansys.messages.application.exception.messages.UserSessionNotExists;
import ar.lamansys.messages.application.messages.GetUserSession;
import ar.lamansys.messages.domain.CartBO;
import ar.lamansys.messages.domain.ProductInCartBO;
import ar.lamansys.messages.infrastructure.output.CartStorage;
import ar.lamansys.messages.infrastructure.output.ProductInCartStorage;
import ar.lamansys.messages.infrastructure.output.entity.Cart;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class CreateCart {
    private final AssertUserExists assertUserExists;
    private final AssertListNotEmpty assertListNotEmpty;
    private final AssertEveryProductExists assertEveryProductExists;
    private final CartStorage cartStorage;
    private final ProductInCartStorage productInCartStorage;
    private final GetUserSession getUserSession;
    private final NotifyUserCartChanges notifyUserCartChanges;

    @Transactional
    public String run(String sellerId, List<ProductInCartBO> productsInCartId) throws UserSessionNotExists, UserNotExistsException, ListIsEmptyException, ProductNotExistsException, NotEnoughStockException {
        assertUserExists.run(sellerId);
        assertListNotEmpty.run(productsInCartId);
        assertEveryProductExists.run(productsInCartId, sellerId);
        String clientId = getUserSession.run();
        CartBO cartBO = new CartBO(clientId, sellerId);
        Cart cart = cartStorage.save(cartBO);
        String cartId = cart.getId();
        productsInCartId.forEach(product -> product.setCartId(cartId));
        productInCartStorage.saveAll(productsInCartId);
        String response = "Carrito creado con éxito, id:" + cartId;
        notifyUserCartChanges.run(response, sellerId);
        return cartId;
    }
}


