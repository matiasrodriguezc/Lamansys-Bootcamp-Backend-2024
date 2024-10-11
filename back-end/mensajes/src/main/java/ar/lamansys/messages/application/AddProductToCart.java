package ar.lamansys.messages.application;

import ar.lamansys.messages.application.exception.CartNotExistsException;
import ar.lamansys.messages.application.exception.NotEnoughStockException;
import ar.lamansys.messages.application.exception.ProductNotExistsException;
import ar.lamansys.messages.application.exception.UserNotExistsException;
import ar.lamansys.messages.application.exception.messages.UserSessionNotExists;
import ar.lamansys.messages.application.messages.GetUserSession;
import ar.lamansys.messages.domain.ProductInCartBO;
import ar.lamansys.messages.infrastructure.output.CartStorage;
import ar.lamansys.messages.infrastructure.output.ProductInCartStorage;
import ar.lamansys.messages.infrastructure.output.WishlistStorage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class AddProductToCart {
    private final GetUserSession getUserSession;
    private final AssertCartExists assertCartExists;
    private final CartStorage cartStorage;
    private final AssertProductExists assertProductExists;
    private final AssertStockExists assertStockExists;
    private final ProductInCartStorage productInCartStorage;
    private final NotifyUserCartChanges notifyUserCartChanges;
    private final WishlistStorage wishlistStorage;

    public void run(String cartId, ProductInCartBO productInCartBO) throws UserNotExistsException, UserSessionNotExists, CartNotExistsException, ProductNotExistsException, NotEnoughStockException {
        String userId = getUserSession.run();
        assertCartExists.run(cartId, userId);
        String sellerId = cartStorage.getSellerId(cartId, userId);
        String productInCartBOId = productInCartBO.getProductId();
        assertProductExists.run(productInCartBOId, sellerId);
        List<ProductInCartBO> products = new ArrayList<>();
        products.add(productInCartBO);
        assertStockExists.run(products, sellerId);
        productInCartBO.setCartId(cartId);
        productInCartStorage.save(productInCartBO);
        String response = "Producto con id:" +  productInCartBOId + " agregado con éxito al Carrito.";
        notifyUserCartChanges.run(response, sellerId);
        wishlistStorage.deleteById(userId, productInCartBOId); //borro de la wishlist los productos que agregue
    }
}