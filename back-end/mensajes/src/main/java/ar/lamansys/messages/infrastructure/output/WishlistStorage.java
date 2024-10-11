package ar.lamansys.messages.infrastructure.output;

import ar.lamansys.messages.domain.ProductInCartBO;

import java.util.List;

public interface WishlistStorage {

    void save(ProductInCartBO productInCart, String userId);

    List<String> findClientByProductIdAndStock(String productId, int stock);

    void deleteById(String clientId, String productId);
}
