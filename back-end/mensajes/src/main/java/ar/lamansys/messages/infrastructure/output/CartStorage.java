package ar.lamansys.messages.infrastructure.output;

import ar.lamansys.messages.domain.CartBO;
import ar.lamansys.messages.infrastructure.output.entity.Cart;

public interface CartStorage {
    Cart save(CartBO cartBO);
    String getCartId(String userId, String sellerId);
    boolean exists(String cartId, String userId);
    String getSellerId(String cartId, String userId);
    void deleteById(String cartId);
}
