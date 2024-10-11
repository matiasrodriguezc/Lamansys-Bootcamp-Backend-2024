package ar.lamansys.messages.infrastructure.output;

import ar.lamansys.messages.domain.ProductInCartBO;
import ar.lamansys.messages.infrastructure.output.entity.ProductInCart;

import java.util.List;

public interface ProductInCartStorage {
    ProductInCart save (ProductInCartBO productInCart);

    boolean exists(String cartId, String productInCartId);

    void setQuantity(String cartId, String productInCartId, int newQuantity);

    void delete(String cartId, String productInCartId);

    List<ProductInCartBO> findByCartId(String cartId);

    void saveAll(List<ProductInCartBO> productInCarts);
}
