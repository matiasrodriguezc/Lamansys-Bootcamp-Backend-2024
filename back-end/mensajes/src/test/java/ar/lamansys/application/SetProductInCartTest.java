package ar.lamansys.application;

import ar.lamansys.messages.application.*;
import ar.lamansys.messages.application.exception.CartNotExistsException;
import ar.lamansys.messages.application.exception.NotEnoughStockException;
import ar.lamansys.messages.application.exception.ProductInCartNotExistsException;
import ar.lamansys.messages.application.exception.messages.UserSessionNotExists;
import ar.lamansys.messages.application.messages.GetUserSession;
import ar.lamansys.messages.infrastructure.output.CartStorage;
import ar.lamansys.messages.infrastructure.output.ProductInCartStorage;
import ar.lamansys.messages.infrastructure.output.entity.Cart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertThrows;

/** Revisión
 *
 *      1. Se testea solo el happy path.
 *
 * */
public class SetProductInCartTest {

    @Mock
    private AssertCartExists assertCartExists;
    @Mock
    private GetUserSession getUserSession;
    @Mock
    private AssertProductInCartExists assertProductInCartExists;
    @Mock
    private AssertStockExists assertStockExists;
    @Mock
    private CartStorage cartStorage;
    @Mock
    private ProductInCartStorage productInCartStorage;

    private SetProductInCart setProductInCart;
    private DeleteProductInCart deleteProductInCart;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        setProductInCart = new SetProductInCart(assertCartExists, getUserSession, assertProductInCartExists, assertStockExists, cartStorage,productInCartStorage);
        deleteProductInCart = new DeleteProductInCart(assertCartExists, assertProductInCartExists, getUserSession, productInCartStorage);
    }

    @Test
    void modifyQuantity_ok() throws CartNotExistsException, ProductInCartNotExistsException, NotEnoughStockException, UserSessionNotExists {
        String cartId = "cart1";
        String productincartId = "product1";
        int quantity = 9;
        String clientId = "cliente1";
        String sellerId = "seller1";

        when(getUserSession.run()).thenReturn(clientId);
        when(cartStorage.getSellerId(cartId,clientId)).thenReturn(sellerId);

        //Act
        setProductInCart.run(cartId,productincartId,quantity);

        //Assert
        verify(productInCartStorage).setQuantity(cartId, productincartId, quantity);
        assertDoesNotThrow(() -> setProductInCart.run(cartId, productincartId, quantity));
    }

    @Test
    void deleteProduct_ok() throws UserSessionNotExists, CartNotExistsException, ProductInCartNotExistsException {
        //Arrange
        String cartId = "cart2";
        String productInCartId = "product7";

        when(getUserSession.run()).thenReturn("cliente2");

        //Act
        deleteProductInCart.run(cartId, productInCartId);

        //Assert
        verify(assertCartExists).run(cartId, "cliente2");
        verify(productInCartStorage).delete(cartId, productInCartId);
    }

}
