package ar.lamansys.application;

import ar.lamansys.messages.application.DeleteCart;
import ar.lamansys.messages.infrastructure.output.CartStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

/** Revisión
 *
 *      1. Se testea solo el happy path.
 *
 * */
public class DeleteCartTest {

    @Mock
    private CartStorage cartStorage;
    @Mock
    private DeleteCart deleteCart;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        deleteCart = new DeleteCart(cartStorage);
    }

    @Test
    void deleteCart_ok() {
        String cartId = "cart1";

        deleteCart.run(cartId);

        verify(cartStorage).deleteById(cartId);
    }
}
