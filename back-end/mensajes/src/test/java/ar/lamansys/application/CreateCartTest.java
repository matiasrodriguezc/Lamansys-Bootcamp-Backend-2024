package ar.lamansys.application;

import ar.lamansys.messages.application.AssertEveryProductExists;
import ar.lamansys.messages.application.AssertListNotEmpty;
import ar.lamansys.messages.application.AssertUserExists;
import ar.lamansys.messages.application.CreateCart;
import ar.lamansys.messages.application.exception.ListIsEmptyException;
import ar.lamansys.messages.application.exception.NotEnoughStockException;
import ar.lamansys.messages.application.exception.ProductNotExistsException;
import ar.lamansys.messages.application.exception.UserNotExistsException;
import ar.lamansys.messages.domain.ProductInCartBO;
import ar.lamansys.messages.infrastructure.output.CartStorage;
import ar.lamansys.messages.infrastructure.output.ProductInCartStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

/** Revisión
 *
 *      1. El test no verifica ningun caso de uso. En este caso es mejor eliminar la clase en lugar de dejar el codigo.
 *
 * */

public class CreateCartTest {

    @Mock
    private AssertUserExists assertUserExists;
    @Mock
    private AssertListNotEmpty assertListNotEmpty;
    @Mock
    private AssertEveryProductExists assertEveryProductExists;
    @Mock
    private CartStorage cartStorage;
    @Mock
    private ProductInCartStorage productInCartStorage;
    @InjectMocks
    private CreateCart createCart;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void cartSuccess() throws ListIsEmptyException, NotEnoughStockException, ProductNotExistsException, UserNotExistsException {
        //Arrange
        String clientId = "cliente1";
        String sellerId = "vendedor1";
        ProductInCartBO product1 = new ProductInCartBO("mayonesa", 90);
        ProductInCartBO product2 = new ProductInCartBO("ketchup", 70);
        ProductInCartBO product3 = new ProductInCartBO("mostaza", 80);

        List<ProductInCartBO> products = Stream.of(product1,product2,product3).collect(Collectors.toList());

        //simulo q existe un cart1
        when(cartStorage.getCartId(clientId,sellerId)).thenReturn("cart1");

        /*String cartId = createCart.run(clientId,sellerId,products);

        assertEquals("cart1", cartId);*/
    }

    
}
