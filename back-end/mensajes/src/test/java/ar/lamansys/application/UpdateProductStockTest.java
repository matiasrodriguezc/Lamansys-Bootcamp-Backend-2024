package ar.lamansys.application;

import ar.lamansys.messages.application.UpdateProductStock;
import ar.lamansys.messages.domain.ProductBO;
import ar.lamansys.messages.domain.ProductInCartBO;
import ar.lamansys.messages.infrastructure.output.ProductInCartStorage;
import ar.lamansys.messages.infrastructure.output.ProductStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/** Revisión
 *
 *      1. Se testea solo el happy path.
 *
 * */
public class UpdateProductStockTest {

    @Mock
    private ProductInCartStorage productInCartStorage;
    @Mock
    private ProductStorage productStorage;

    private UpdateProductStock updateProductStock;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        updateProductStock = new UpdateProductStock(productInCartStorage, productStorage);
    }

    @Test
    void updateStock_ok() {
        String cartId = "cart1";
        ProductInCartBO product1 = new ProductInCartBO("product1",3);
        ProductInCartBO product2 = new ProductInCartBO("product2",4);
        List<ProductInCartBO> products = List.of(product1, product2);

        when(productInCartStorage.findByCartId(cartId)).thenReturn(products);
        when(productStorage.findByProductId("product1")).thenReturn(new ProductBO("1","seller1","Product1",20,2000f));
        when(productStorage.findByProductId("product2")).thenReturn(new ProductBO("2","seller1","Product2",9,3500f));

        updateProductStock.run(cartId);

        verify(productStorage).setStock("product1",17); //20 en stock, 3 en el carrito
        verify(productStorage).setStock("product2",5); //9 en stock, 4 en el carrito
    }

    @Test
    void updateStockSingle_ok() {
        ProductInCartBO product = new ProductInCartBO("product3", 7);
        int newQuantity = 10;

        updateProductStock.run(product, newQuantity);

        verify(productStorage).setStock("product3",newQuantity);
    }
}
