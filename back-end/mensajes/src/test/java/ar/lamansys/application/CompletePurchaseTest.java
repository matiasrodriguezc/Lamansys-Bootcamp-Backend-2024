package ar.lamansys.application;

import ar.lamansys.messages.application.*;
import ar.lamansys.messages.application.exception.CartNotExistsException;
import ar.lamansys.messages.application.exception.NotEnoughStockException;
import ar.lamansys.messages.application.exception.ProductInCartNotExistsException;
import ar.lamansys.messages.application.exception.messages.UserSessionNotExists;
import ar.lamansys.messages.application.messages.GetUserSession;
import ar.lamansys.messages.infrastructure.output.CartStorage;
import ar.lamansys.messages.infrastructure.output.ProductInCartStorage;
import ar.lamansys.messages.infrastructure.output.impl.CartStorageImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/** Revisión
 *
 *      1. No se usa CartStorage.
 *
 *      2. Esta bueno testear las posibles excepciones que puede lanzar un caso de uso y no solo el happy path.
 *
 * */
class CompletePurchaseTest {

    @Mock
    private AssertCartExists assertCartExists;
    @Mock
    private GetUserSession getUserSession;
    @Mock
    private GetCartStatus getCartStatus;
    @Mock
    private UpdateProductStock updateProductStock;
    @Mock
    private DeleteProductInCart deleteProductInCart;
    @Mock
    private CartStorage cartStorage;
    @Mock
    private DeleteCart deleteCart;

    private CompletePurchase completePurchase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        completePurchase = new CompletePurchase(assertCartExists, getUserSession, getCartStatus, updateProductStock, deleteProductInCart, deleteCart);
    }

    @Test
    void completePurchase_ok() throws UserSessionNotExists, CartNotExistsException, NotEnoughStockException, ProductInCartNotExistsException {
        String cartId = "cart1";

        when(getUserSession.run()).thenReturn("client1");
        when(getCartStatus.getTotalCost(cartId)).thenReturn(100.0f);

        completePurchase.run(cartId);

        verify(assertCartExists).run(cartId, "client1");
        verify(getCartStatus).checkStockAvailability(cartId);
        verify(updateProductStock).run(cartId);
        verify(deleteProductInCart).run(cartId);
        verify(deleteCart).run(cartId);
    }

}