package ar.lamansys.application;

import ar.lamansys.messages.application.AssertCartExists;
import ar.lamansys.messages.application.AssertStockExists;
import ar.lamansys.messages.application.GetCartStatus;
import ar.lamansys.messages.application.exception.CartNotExistsException;
import ar.lamansys.messages.application.exception.NotEnoughStockException;
import ar.lamansys.messages.application.exception.messages.UserSessionNotExists;
import ar.lamansys.messages.application.messages.GetUserSession;
import ar.lamansys.messages.domain.ProductBO;
import ar.lamansys.messages.domain.ProductInCartBO;
import ar.lamansys.messages.infrastructure.output.CartStorage;
import ar.lamansys.messages.infrastructure.output.ProductInCartStorage;
import ar.lamansys.messages.infrastructure.output.ProductStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/** Revisión
 *
 *      1. Se testea solo el happy path.
 *
 * */
class GetCartStatusTest {
    @Mock
    private AssertCartExists assertCartExists;
    @Mock
    private GetUserSession getUserSession;
    @Mock
    private CartStorage cartStorage;
    @Mock
    private ProductInCartStorage productInCartStorage;
    @Mock
    private AssertStockExists assertStockExists;
    @Mock
    private ProductStorage productStorage;

    private GetCartStatus getCartStatus;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        getCartStatus = new GetCartStatus(assertCartExists, cartStorage, getUserSession, productInCartStorage, assertStockExists, productStorage);
    }

    @Test
    void stockAvailability_ok() throws CartNotExistsException, NotEnoughStockException, UserSessionNotExists {
        String cartId = "cart1";
        String clientId = "client1";
        ProductInCartBO product1 = new ProductInCartBO("yogurt", 4);
        ProductInCartBO product2 = new ProductInCartBO("vino", 9);
        List<ProductInCartBO> products = List.of(product1,product2);

        //Act
        when(getUserSession.run()).thenReturn(clientId);
        when(productInCartStorage.findByCartId(cartId)).thenReturn(products);

        getCartStatus.checkStockAvailability(cartId);

        verify(assertCartExists).run(cartId,clientId);
        verify(assertStockExists).run(products, cartStorage.getSellerId(cartId, getUserSession.run()));

    }

    @Test
    void getUnitPrice_ok() throws UserSessionNotExists, CartNotExistsException {
        String cartId = "cart2";
        String clientId = "client2";
        String productId = "product2";
        int price = 1300;

        when(getUserSession.run()).thenReturn(clientId);
        when(cartStorage.getSellerId(cartId, clientId)).thenReturn("seller1");
        when(productStorage.findByProductId(productId)).thenReturn(new ProductBO("1","seller1", "fideos", 10, price));

        float unitPrice = getCartStatus.getUnitPrice(productId);

        assertEquals(price, unitPrice);
        verify(assertCartExists).run(cartId,clientId);
        verify(productStorage).findByProductId(productId);

    }

    @Test
    void totalCost_ok() throws UserSessionNotExists, CartNotExistsException {
        String cartId = "cart3";
        float price1 = 5600;
        float price2 = 4200;
        ProductInCartBO product1 = new ProductInCartBO("product1", 4);
        ProductInCartBO product2 = new ProductInCartBO("product2", 7);
        product1.setCartId(cartId);
        product2.setCartId(cartId);
        List<ProductInCartBO> products = List.of(product1,product2);

        ProductBO productBO1 = new ProductBO("1","seller2", "Product1", 20, price1);
        ProductBO productBO2 = new ProductBO("2","seller2", "Product2", 40, price2);

        when(getUserSession.run()).thenReturn("client1");
        when(cartStorage.getSellerId(cartId, "client1")).thenReturn("seller2");
        when(productInCartStorage.findByCartId(cartId)).thenReturn(products);
        when(productStorage.findByProductId("product1")).thenReturn(productBO1);
        when(productStorage.findByProductId("product2")).thenReturn(productBO2);

        //expected
        float expectedPrice = (price1*4)+(price2*7);
        //execute
        float totalCost = getCartStatus.getTotalCost(cartId);

        assertEquals(expectedPrice,totalCost);
        verify(productInCartStorage).findByCartId(cartId);
        verify(productStorage).findByProductId("product1");
        verify(productStorage).findByProductId("product2");
    }

}