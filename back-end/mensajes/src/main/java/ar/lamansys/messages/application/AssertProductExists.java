package ar.lamansys.messages.application;

import ar.lamansys.messages.application.exception.ProductNotExistsException;
import ar.lamansys.messages.infrastructure.output.ProductStorage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AssertProductExists {
    private final ProductStorage productStorage;

    public void run(String productId, String sellerId) throws ProductNotExistsException {
        if (! productStorage.exists(productId, sellerId)){
            throw new ProductNotExistsException(productId, sellerId);
        }
    }
}
