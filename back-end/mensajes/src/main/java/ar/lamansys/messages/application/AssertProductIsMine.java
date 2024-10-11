package ar.lamansys.messages.application;

import ar.lamansys.messages.application.exception.ProductNotExistsException;
import ar.lamansys.messages.application.exception.messages.UserSessionNotExists;
import ar.lamansys.messages.application.messages.GetUserSession;
import ar.lamansys.messages.infrastructure.output.ProductStorage;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AssertProductIsMine {
    private final GetUserSession getUserSession;
    private final ProductStorage productStorage;

    public void run(String productId) throws UserSessionNotExists, ProductNotExistsException {
        String userId = getUserSession.run();
        if (! productStorage.findByProductId(productId).getSellerId().equals(userId)){
            throw new ProductNotExistsException(productId, userId);
        }
    }
}
