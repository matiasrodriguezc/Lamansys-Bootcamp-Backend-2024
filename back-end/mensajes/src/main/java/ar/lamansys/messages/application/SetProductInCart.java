package ar.lamansys.messages.application;

import ar.lamansys.messages.application.exception.CartNotExistsException;
import ar.lamansys.messages.application.exception.NotEnoughStockException;
import ar.lamansys.messages.application.exception.ProductInCartNotExistsException;
import ar.lamansys.messages.application.exception.UserNotExistsException;
import ar.lamansys.messages.application.exception.messages.UserSessionNotExists;
import ar.lamansys.messages.application.messages.GetUserSession;
import ar.lamansys.messages.domain.ProductBO;
import ar.lamansys.messages.domain.ProductInCartBO;
import ar.lamansys.messages.infrastructure.output.CartStorage;
import ar.lamansys.messages.infrastructure.output.ProductInCartStorage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class SetProductInCart {
    private final AssertCartExists assertCartExists;
    private final GetUserSession getUserSession;
    private final AssertProductInCartExists assertProductInCartExists;
    private final AssertStockExists assertStockExists;
    private final CartStorage cartStorage;
    private final ProductInCartStorage productInCartStorage;
    private final NotifyUserCartChanges notifyUserCartChanges;

    public void run(String cartId, String productInCartId, int newQuantity) throws UserNotExistsException,CartNotExistsException, UserSessionNotExists, ProductInCartNotExistsException, NotEnoughStockException {
        String userId = getUserSession.run();
        assertCartExists.run(cartId, userId);
        assertProductInCartExists.run(cartId, productInCartId);
        ProductInCartBO product = new ProductInCartBO(productInCartId, newQuantity);
        List<ProductInCartBO> productInCartBOList = new ArrayList<>();
        productInCartBOList.add(product);
        String sellerId = cartStorage.getSellerId(cartId, userId);
        assertStockExists.run(productInCartBOList, sellerId);
        productInCartStorage.setQuantity(cartId, productInCartId, newQuantity);
        String response = "Cantidad modificada del Producto con id:" + product.getProductId() + " con éxito.";
        notifyUserCartChanges.run(response, sellerId);
    }
}
