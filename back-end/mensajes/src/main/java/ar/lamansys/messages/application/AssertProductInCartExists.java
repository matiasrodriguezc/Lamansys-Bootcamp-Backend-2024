package ar.lamansys.messages.application;

import ar.lamansys.messages.application.exception.ProductInCartNotExistsException;
import ar.lamansys.messages.infrastructure.output.ProductInCartStorage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AssertProductInCartExists {
    private final ProductInCartStorage productInCartStorage;

    public void run(String cartId, String productInCartId) throws ProductInCartNotExistsException {
        if (! productInCartStorage.exists(cartId, productInCartId)){
            throw new ProductInCartNotExistsException(cartId, productInCartId);
        }
    }
}
