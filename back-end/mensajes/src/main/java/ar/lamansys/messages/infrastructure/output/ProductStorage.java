package ar.lamansys.messages.infrastructure.output;

import ar.lamansys.messages.domain.ProductBO;
import java.util.List;

public interface ProductStorage {
    List<ProductBO> findBySeller(String id);

    boolean exists(String productId, String sellerId);

    boolean enough(String productId, int quantity, String sellerId);

    ProductBO findByProductId(String productId);

    void setStock(String productId, int newQuantity);
}
